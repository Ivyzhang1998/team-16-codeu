package com.google.codeu.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/chartsdata")
public class ChartsDataServlet extends HttpServlet{
	
	private JsonArray userMealArray;

	/*
	 * Class that models the JSON for our dummy data.
	 * carbonFootprint will eventually be calculated from the map of FoodItems
	 * For now, it is assigned a random value
	 * */
	private static class UserMeal {
		double carbonFootprint;
		LocalDate date;

		private UserMeal(double carbonFootprint, LocalDate date) {
			this.carbonFootprint = carbonFootprint;
			this.date = date;
		}
	}
	
	/*
	 * Generates Dummy data to be sent in GET requests
	 * Creates 21 user meals. 7 days * 3 Meals per day.
	 * TODO: Connect this servlet with FoodItems and UserMeals in the Datastore.
	 * */
	@Override
	public void init() {
		userMealArray = new JsonArray();
		Gson gson = new Gson();
		for (int i = 0; i < 7; i++) {
			LocalDate mealDate = LocalDate.now().minusDays(i);
			for(int j = 0; j < 3; j++) {
				//Carbon Footprint will be a random number between 1 and 50
				double carbonFootprint = Math.random() * 49 + 1;
				UserMeal userMeal = new UserMeal(carbonFootprint, mealDate);
				userMealArray.add(gson.toJsonTree(userMeal));
			}
		}
	}

	/*
	 * Sends a JSON response after a GET request
	 * Example JSON Response: [{movieTitle, rating},{movieTitle, rating}]
	 * */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json");
		response.getOutputStream().println(userMealArray.toString());
	}
}
