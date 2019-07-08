package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.codeu.data.Datastore;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/stats")
public class StatsPageServlet extends HttpServlet{

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    int messageCount = datastore.getTotalMessageCount();
    int userCount = datastore.getUsers().size();

    request.setAttribute("messageCount", messageCount);
    request.setAttribute("userCount", userCount);
    
    request.getRequestDispatcher("/WEB-INF/views/stats.jsp").forward(request, response);
  }
  
}
