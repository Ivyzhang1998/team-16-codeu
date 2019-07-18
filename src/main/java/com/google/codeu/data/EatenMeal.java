//Meal entered by the users
package com.google.codeu.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.google.codeu.data.Datastore;

public class EatenMeal {

  private UUID id; //Use it to match with user information (based on Message object)
  private List<String> foods;
  private List<Double> amounts;
  private Date date; //Date(int year, int month, int date)
  private String imageUrl;
  private MealType mealType;
  private Datastore datastore;

  public EatenMeal (List<String> foods, List<Double> amounts, Date date , String imageUrl, MealType mealType) {
    this.id = UUID.randomUUID();
    this.foods = foods;
    this.amounts = amounts;
    this.date = date;
    this.imageUrl = imageUrl;
    this.mealType = mealType;
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

  public MealType getMealType(){
    return mealType;
  }

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
