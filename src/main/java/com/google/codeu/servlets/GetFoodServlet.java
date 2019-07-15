package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.gson.Gson;
import java.io.IOException;

import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles fetching all users for the community page.
 */
@WebServlet("/food-list")
public class GetFoodServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

 /**
   * Responds with a JSON representation of all users.
   * Example JSON: ["user@test.com", "user2@test.com", "user3@example.com"]
   */


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");
    Set<String> allfood = datastore.getAllFoodItems();
    Gson gson = new Gson();
    String json = gson.toJson(allfood);
    response.getOutputStream().println(json);
    /**let other programs (Front-End) to read directly into variables */
  }
}
