package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.EatenMeal;
import com.google.codeu.data.FoodItem;
import com.google.protobuf.ByteString;
import org.apache.http.HttpStatus;
import org.jsoup.HttpStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.codeu.data.EatenMeal.*;

/**
 * When the user submits the form, Blobstore processes the file upload
 * and then forwards the request to this servlet. This servlet can then
 * process the request using the file URL we get from Blobstore.
 */
@WebServlet("/my-form-handler")
public class FormHandlerServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String formId = request.getParameter("form-id");

        if (formId.isEmpty()) {
            throw new HttpStatusException("Form id must not be empty.", HttpStatus.SC_BAD_REQUEST, request.getRequestURI());
        } else if (formId.equals("eaten")) {
            handleEatenFood(request, response);
        } else if (formId.equals("co2")) {
            handleCO2food(request, response);
        } else {
            throw new HttpStatusException("Invalid form given.", HttpStatus.SC_BAD_REQUEST, request.getRequestURI());
        }
    }

    /**
     * Returns a URL that points to the uploaded file, or null if the user didn't upload a file.
     */
    private String getUploadedFileUrl(BlobKey blobKey){
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        return imagesService.getServingUrl(options);
    }

    /**
     * Returns the BlobKey that points to the file uploaded by the user, or null if the user didn't upload a file.
     */
    private BlobKey getBlobKey(HttpServletRequest request, String formInputElementName){
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get(formInputElementName);

        // User submitted form without selecting a file, so we can't get a BlobKey. (devserver)
        if(blobKeys == null || blobKeys.isEmpty()) {
            return null;
        }

        // Our form only contains a single file input, so get the first index.
        BlobKey blobKey = blobKeys.get(0);

        // User submitted form without selecting a file, so the BlobKey is empty. (live server)
        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
        if (blobInfo.getSize() == 0) {
            blobstoreService.delete(blobKey);
            return null;
        }
        // check that uploaded file is an image
        if (!blobInfo.getContentType().substring(0,5).equals("image")) {
            System.out.println(blobInfo.getContentType().substring(0,5));
            return null;
        }
        return blobKey;
    }

    /**
     * Blobstore stores files as binary data. This function retrieves the
     * binary data stored at the BlobKey parameter.
     */
    private byte[] getBlobBytes(BlobKey blobKey) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

        int fetchSize = BlobstoreService.MAX_BLOB_FETCH_SIZE;
        long currentByteIndex = 0;
        boolean continueReading = true;
        while (continueReading) {
            // end index is inclusive, so we have to subtract 1 to get fetchSize bytes
            byte[] b = blobstoreService.fetchData(blobKey, currentByteIndex, currentByteIndex + fetchSize - 1);
            outputBytes.write(b);

            // if we read fewer bytes than we requested, then we reached the end
            if (b.length < fetchSize) {
                continueReading = false;
            }

            currentByteIndex += fetchSize;
        }

        return outputBytes.toByteArray();
    }

    /**
     * Uses the Google Cloud Vision API to generate a list of labels that apply to the image
     * represented by the binary data stored in imgBytes.
     */
    private List<EntityAnnotation> getImageLabels(byte[] imgBytes) throws IOException {
        ByteString byteString = ByteString.copyFrom(imgBytes);
        Image image = Image.newBuilder().setContent(byteString).build();

        Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        ImageAnnotatorClient client = ImageAnnotatorClient.create();
        BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
        client.close();
        List<AnnotateImageResponse> imageResponses = batchResponse.getResponsesList();
        AnnotateImageResponse imageResponse = imageResponses.get(0);

        if (imageResponse.hasError()) {
            System.err.println("Error getting image labels: " + imageResponse.getError().getMessage());
            return null;
        }

        return imageResponse.getLabelAnnotationsList();
    }

    private void handleCO2food(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        FoodItem foodItem = addFoodItemForUser(request);
        // Output some HTML that shows the data the user entered.

        out.println("<p>You entered that  "  + foodItem.getName() + " produces " +  foodItem.getCO2PerYear() + " kg of CO2 per year.</p>");
        out.println("<p>Thank you!</p>");
    }

    private void handleEatenFood(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        BlobKey blobKey = getBlobKey(request, "image");
        EatenMeal meal = addMealForUser(request, blobKey);

        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        if (blobKey == null) {
            out.println("<p>You ate " + meal.getAmount() + " of "  + meal.getFood() +
                    " on " + format.format(meal.getDate()) + ".</p>");
            return;
        }

        // Get the labels of the image that the user uploaded.
        byte[] blobBytes = getBlobBytes(blobKey);
        List<EntityAnnotation> imageLabels = getImageLabels(blobBytes);


        // Output some HTML that shows the data the user entered.

        out.println("<p>Here's the "  + meal.getFood() + " you uploaded</p>");
        out.println("<a href=\"" + meal.getImageUrl() + "\">");
        out.println("<img src=\"" + meal.getImageUrl() + "\" />");
        out.println("</a>");
        out.println("<p>You ate " + meal.getAmount() + " of this on " + format.format(meal.getDate()) + ".</p>");
        out.println("<p>Here are the labels we extracted:</p>");
        out.println("<ul>");

        for(EntityAnnotation label : imageLabels){
            out.println("<li>" + label.getDescription() + " " + label.getScore());
        }
        out.println("</ul>");
    }

    /**
     * Creates a EatenMeal from the form entered by the user and then stores that meal into Datastore.
     *
     * @param request
     * @param blobKey
     * @return meal that was stored
     */
    private EatenMeal addMealForUser(HttpServletRequest request, BlobKey blobKey) throws HttpStatusException {
        Datastore datastore = new Datastore();
        // Get the message entered by the user.
        String foodName = request.getParameter("foodName");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String date = request.getParameter("date");
        String mealTag = request.getParameter("mealType");
        String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
        String imageUrl;

        MealType mealType;
        if (mealTag.equals("breakfast")) {
            mealType = MealType.BREAKFAST;
        } else if(mealTag.equals("lunch")) {
            mealType = MealType.LUNCH;
        } else if(mealTag.equals("dinner")) {
            mealType = MealType.DINNER;
        } else if(mealTag.equals("snack")) {
            mealType = MealType.SNACK;
        } else {    //shouldn't end up in else, but in case
            throw new HttpStatusException("Meal tag is of an improper type.", HttpStatus.SC_BAD_REQUEST, request.getRequestURI());
        }

        // Get the URL of the image that the user uploaded to Blobstore.
        // If user didn't upload an image, want to replace the null with an empty string to avoid null pointer exceptions
        if (blobKey == null) {
            imageUrl = "";
        } else {
            imageUrl = getUploadedFileUrl(blobKey);
        }

        String[] yearMonthDay = date.split("-");
        Date mealDate = new Date(Integer.parseInt(yearMonthDay[0]), Integer.parseInt(yearMonthDay[1]) - 1, Integer.parseInt(yearMonthDay[2]));

        EatenMeal meal =  new EatenMeal(userId, foodName, amount, mealDate, imageUrl, mealType);
        datastore.storeMeal(meal);
        return meal;
    }

    /**
     * Creates a FoodItem from the form entered by the user and then stores that meal into Datastore.
     *
     * @param request
     * @return meal that was stored
     */
    private FoodItem addFoodItemForUser(HttpServletRequest request) throws HttpStatusException {
        Datastore datastore = new Datastore();

        // Get the message entered by the user.
        String foodName = request.getParameter("foodName");
        Double co2Amount = Double.parseDouble(request.getParameter("co2"));

        FoodItem foodItem = new FoodItem(foodName, co2Amount);

        datastore.storeFood(foodItem);
        return foodItem;
    }
}
