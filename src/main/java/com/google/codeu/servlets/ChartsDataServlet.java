package com.google.codeu.servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@WebServlet("/chartsdata/*")
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
		this.userMealArray = new JsonArray();
		Gson gson = new Gson();
		for (int i = 0; i < 7; i++) {
			LocalDate mealDate = LocalDate.now().minusDays(i);
			for(int j = 0; j < 3; j++) {
				//Carbon Footprint will be a random number between 1 and 50
				double carbonFootprint = Math.random() * 49 + 1;
				UserMeal userMeal = new UserMeal(carbonFootprint, mealDate);
				this.userMealArray.add(gson.toJsonTree(userMeal));
			}
		}
	}

	/*
	 * Sends a JSON response after a GET request
	 * Parses the wild-card route, and calls respective helper method to get data
	 * for that route.
	 * Example JSON Response: [{carbonFootprint, date},{carbonFootprint, date}]
	 * */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json");
		String requestUrl = request.getRequestURI();
		
		if(requestUrl.endsWith("all")) {
			response.getOutputStream().println(this.listMealsForUser());
		}
		if(requestUrl.endsWith("week")) {
			response.getOutputStream().println(this.lastSevenDays());
		}
	}
	
	/*
	 * Returns the string representation of the userMeals JSON array
	 */
	private String listMealsForUser() {
		return this.userMealArray.toString();
	}
	
	/*
	 * Calculates the average carbon footprint  of each day of the last week
	 * Returns the string representation of the JSON array.
	 * Example output: {"2019-06-30" : 20.5675, "2019-06-32" : 13.765}
	 * 
	 * */
	private String lastSevenDays() {
		LocalDate date = LocalDate.now();
		HashMap<LocalDate, ArrayList<Double>> carbonEntriesByDay = new HashMap<>();
		HashMap<LocalDate, Double> averageCarbonPerDay = new HashMap<>();
		Gson gson = new Gson();
		//Organize meals by Date -> [Meal1, Meal2, Meal,3]
		for(int i = 0; i < this.userMealArray.size(); i++) {
			UserMeal meal = gson.fromJson(this.userMealArray.get(i), UserMeal.class);
			//Only include meals from within the last week
			if(meal.date.isBefore(date.minusDays(7))) {
				break;
			}		
			carbonEntriesByDay.putIfAbsent(meal.date, new ArrayList<Double>());
			carbonEntriesByDay.get(meal.date).add(meal.carbonFootprint);
		}
		//Store the average: Date -> Average Carbon Footprint
		for(LocalDate day: carbonEntriesByDay.keySet()) {
			ArrayList<Double> values = carbonEntriesByDay.get(day);
			double average = this.average(values);
			averageCarbonPerDay.put(day, average);
		}
		
		return gson.toJson(averageCarbonPerDay).toString();
	}
	
	/*
	 * Calculates the average of a list of double values
	 * Returns sum if empty, the average otherwise
	 * 
	 * */
	private Double average(List<Double> values) {
		double sum = 0.0;
		if(!values.isEmpty()) {
			for(double number: values) {
				sum += number;
			}
			return sum / values.size();
		}
		return sum;
	}
}
