//Meal entered by the users
package com.google.codeu.data;

import java.util.Date;
import java.util.UUID;
import com.google.codeu.data.Datastore;

public class EatenMeal {

  private UUID id;
  private String userId;
  private String food;
  private Double amount;
  private Date date; //Date(int year, int month, int date)
  private String imageUrl;
  private MealType mealType;
  private Datastore datastore;

  public EatenMeal (String userId, String food, Double amount, Date date , String imageUrl, MealType mealType) {
    this.id = UUID.randomUUID();
    this.userId = userId;
    this.food = food;
    this.amount = amount;
    this.date = date;
    this.imageUrl = imageUrl;
    this.mealType = mealType;
  }

  public UUID getID() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

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

  //When storing this property our datastore entity, we store the ordinal value instead, ie. breakfast = 0, lunch = 1
  public enum MealType {
    BREAKFAST, LUNCH, DINNER, SNACK;
  }

  //looks up the eaten food and then multiplies the CO2 cost of that food * the amount eaten.
  public double getCO2(){
    datastore = new Datastore();
    double CO2 = amount * datastore.getCO2forOneUnitFood(food);
    return CO2;
  }
}
