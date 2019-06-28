package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/chartsdata")
public class ChartsDataServlet extends HttpServlet{
	
	private JsonArray bookRatingArray;

	/*
	 * Class that will hold the data for each element in the CSV file
	 * Could go in a separate file if needed.
	 * 
	 * */
	private static class bookRating {
		String title;
		double rating;

		private bookRating(String title, double rating) {
			this.title = title;
			this.rating = rating;
		}
	}
	
	/*
	 * Reads the Book Ratings CSV File and adds the elements to a JsonArray
	 * */
	@Override
	public void init() {
		bookRatingArray = new JsonArray();
		Gson gson = new Gson();
		Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/book-ratings.csv"));
		scanner.nextLine(); // skips first line (the csv header)
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] cells = line.split(",");

			String curTitle = cells[5];
			double curRating = Double.parseDouble(cells[6]);

			bookRatingArray.add(gson.toJsonTree(new bookRating(curTitle, curRating)));
		}
		scanner.close();
	}

	/*
	 * Sends a JSON response after a GET request
	 * Example JSON Response: [{movieTitle, rating},{movieTitle, rating}]
	 * */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/json");
		response.getOutputStream().println(bookRatingArray.toString());
	}
}
