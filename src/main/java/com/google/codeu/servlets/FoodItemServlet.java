package com.google.codeu.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

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

  //One-time data upload of known foods into datastore
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/FoodInfo.csv"));
    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cells = line.split(",");
      String foodname = cells[1];
      double co2 = Double.parseDouble(cells[2]);

      FoodItem food = new FoodItem(foodname, co2);
      datastore.storeFood(food);

    }
    scanner.close();
  }

}
