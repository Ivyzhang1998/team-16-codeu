package com.google.codeu.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.Set;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.FoodItem;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;


@WebServlet("/food-data")
public class FoodItemServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");
    Set<String> allfood = datastore.getAllFoodItems();
    Gson gson = new Gson();
    String json = gson.toJson(allfood);
    response.getOutputStream().println(json);
  }

  //One-time data upload of known foods into datastore
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/FoodInfo.csv"));
    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cells = line.split(",");
      String foodname = cells[0];
      double co2 = Double.parseDouble(cells[1]);

      FoodItem food = new FoodItem(foodname, co2);
      System.out.println(food.getName());
      System.out.println("hahahahahaha my name is ivy hahahaha");
      datastore.storeFood(food);

    }
    scanner.close();
  }

}
