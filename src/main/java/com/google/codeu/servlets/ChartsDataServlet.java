package com.google.codeu.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/chartsdata/*")
public class ChartsDataServlet extends HttpServlet{
	
	private DatastoreService datastore;

	/*
	 * Class that models the JSON for our dummy data.
	 * carbonFootprint will eventually be calculated from the map of FoodItems
	 * For now, it is assigned a random value
	 * */
	private static class UserMeal {
		private UUID id;
		private List<String> foods;
		private List<Double> amounts;
		private Date date;

		private UserMeal(UUID id, List<String> foods, List<Double> amounts, Date date) {
			this.id = id;
			this.foods = foods;
			this.amounts = amounts;
			this.date = date;
		}
	}
	
	/*
	 * Generates Dummy data to be sent in GET requests
	 * TODO: Connect this servlet with FoodItems and UserMeals in the Datastore.
	 * */
	@Override
	public void init() {
		this.datastore = DatastoreServiceFactory.getDatastoreService();
	}

	/*
	 * Sends a JSON response after a POST request
	 * Calls respective helper method to get data based on param analysisType
	 * for that route.
	 * */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String analysisType = request.getParameter("analysisType");
		
		if(analysisType.isEmpty()) {
			response.sendError(400, "Invalid analysis type");
		}
		else if(analysisType.equals("lastSevenDays")) {
			this.getLastSevenDays(request, response);
		}
		else if(analysisType.equals("breakdown")) {
			this.getBreakdown(request, response);
		}
		else {
			response.sendError(400, "Invalid analysis type");
		}
	}
	
	/*
	 * Returns JSON response of UserMeals by category: breakfast, lunch, dinner, and snack
	 * Example response: { "breakfast": [meal1, meal2], "lunch": [meal1, meal2] };
	 * */
	private void getBreakdown(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		String user = request.getParameter("user");
		
		JsonObject json = new JsonObject();
		json.addProperty("breakfast", this.categoryQuery(user, 0));
		json.addProperty("lunch", this.categoryQuery(user, 1));
		json.addProperty("dinner", this.categoryQuery(user, 2));
		json.addProperty("snack", this.categoryQuery(user, 3));
		
		response.getOutputStream().println(json.toString());
	}
	
	/*
	 * 
	 * Queries the datastore for meals by mealType: breakfast, lunch, dinner, or snack
	 * Returns the string representation of the JSON array
	 * 
	 * */
	private String categoryQuery(String user, int mealType) {
		FilterPredicate userFilter = new Query.FilterPredicate("userId", Query.FilterOperator.EQUAL, user);
		FilterPredicate mealTypeFilter = new Query.FilterPredicate("mealType", Query.FilterOperator.EQUAL, mealType);
		
		Query query = new Query("UserMeal")
				.setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays.asList(userFilter, mealTypeFilter)));
		
		PreparedQuery results = datastore.prepare(query);
		List<UserMeal> meals = this.processMealQuery(results);
		Gson gson = new Gson();
		String JsonArray = gson.toJson(meals);
		return JsonArray;
	}

	/*
	 * Returns JSON response for user meals from the last seven days
	 * */
	private void getLastSevenDays(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		String user = request.getParameter("user");
		
		//Get date from 7 days ago
		Instant now = Instant.now();
		Instant before = now.minus(Duration.ofDays(7));
		Date sevenDaysAgo = Date.from(before);
		
		FilterPredicate dateFilter = new Query.FilterPredicate("date", Query.FilterOperator.GREATER_THAN, sevenDaysAgo);
		FilterPredicate userFilter = new Query.FilterPredicate("userId", Query.FilterOperator.EQUAL, user);
		
		Query query = new Query("UserMeal")
							.setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays.asList(dateFilter, userFilter)));

		PreparedQuery results = datastore.prepare(query);
		List<UserMeal> meals = this.processMealQuery(results);
		Gson gson = new Gson();
		String JsonResponse = gson.toJson(meals);
		response.getOutputStream().println(JsonResponse);
	}
	
	/*
	 * Iterates through query results and returns a list of messages
	 *
	 * @return a list of messages.
	 */
	private List<UserMeal> processMealQuery(PreparedQuery queryResults) {
		List<UserMeal> meals = new ArrayList<>();

		for (Entity entity : queryResults.asIterable()) {
			try {
				String idString = entity.getKey().getName();
				UUID id = UUID.fromString(idString);
				List<String> foods = (List<String>) entity.getProperty("foods");
				List<Double> amounts = (List<Double>) entity.getProperty("amounts");
				Date date = (Date) entity.getProperty("date");

				UserMeal meal = new UserMeal(id, foods, amounts, date);
				meals.add(meal);
			} catch (Exception e) {
				System.err.println("Error reading message.");
				System.err.println(entity.toString());
				e.printStackTrace();
			}
		}

		return meals;
	}
}
