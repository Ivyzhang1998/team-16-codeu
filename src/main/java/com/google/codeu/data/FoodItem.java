//Food information
package com.google.codeu.data;

import java.util.UUID;
public class FoodItem {

  private UUID foodId;
  private String name;
  private double co2PerYear;

  public FoodItem(String name, double co2PerYear) {
    this.name = name;
    this.foodId = UUID.randomUUID();
    this.co2PerYear = co2PerYear;
  }

  public String getName() {
    return name;
  }

  public UUID getID() {
    return foodId;
  }

  public double getCO2PerYear() {
    return co2PerYear;
  }
}
