package com.google.codeu.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.codeu.data.Datastore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/eaten-food-form")
public class EatenFoodPageServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Datastore datastore = new Datastore();
        List<String> allFoods = new ArrayList<>(datastore.getAllFoodItems());
        request.setAttribute("foodList", allFoods);

        request.getRequestDispatcher("/WEB-INF/views/eaten-food-form.jsp").forward(request, response);

    }
}
