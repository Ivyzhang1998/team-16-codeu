//Meal entered by the users
package com.google.codeu.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserMeal {

  private UUID id; //Use it to match with user information (based on Message object)
  private List<String> foods;
  private List<Double> amounts;
  private Date date; //Date(int year, int month, int date)
  private String imageUrl;

  public UserMeal (List<String> foods, List<Double> amounts, Date date , String imageUrl) {
    this.id = UUID.randomUUID();
    this.foods = foods;
    this.amounts = amounts;
    this.date = date;
    this.imageUrl = imageUrl;
  }

  public UUID getID() {
    return id;
  }

  public List<String> getFoods() {
    return foods;
  }

  public List<Double> getAmounts() {
    return amounts;
  }

  public Date getDate(){
    return date;
  }

  public String getImageUrl(){
     return imageUrl;
  }

}
