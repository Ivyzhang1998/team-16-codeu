//Meal entered by the users
package com.google.codeu.data;

import java.util.Date;
import java.util.UUID;

public class EatenMeal {

  private UUID id;
  private String userId;
  private String food;
  private Double amount;
  private Date date; //Date(int year, int month, int date)
  private String imageUrl;
  private MealType mealType;

  public EatenMeal (String userId, String foods, Double amount, Date date , String imageUrl, MealType mealType) {
    this.id = UUID.randomUUID();
    this.userId = userId;
    this.food = foods;
    this.amount = amount;
    this.date = date;
    this.imageUrl = imageUrl;
    this.mealType = mealType;
  }

  public UUID getID() {
    return id;
  }

  public String getUserId() { return userId; }

  public String getFood() {
    return food;
  }

  public Double getAmount() {
    return amount;
  }

  public Date getDate(){
    return date;
  }

  public String getImageUrl(){
     return imageUrl;
  }

  public MealType getMealType(){
    return mealType;
  }

  public enum MealType {
    BREAKFAST, LUNCH, DINNER, SNACK;
  }
}
