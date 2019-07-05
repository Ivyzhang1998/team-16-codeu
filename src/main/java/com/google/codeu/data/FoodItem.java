//Food information
package com.google.codeu.data;

import java.util.UUID;
public class FoodItem {

  private UUID id;
  private String name;
  private double co2PerYear;

  public FoodItem(String name, double co2PerYear) {
    this.name = name;
    this.foodID = UUID.randomUUID();
    this.co2PerYear = co2PerYear;
  }

  public String getName() {
    return name;
  }

  public UUID getID() {
    return id;
  }

  public double getCO2PerYear() {
    return co2PerYear;
  }
}
