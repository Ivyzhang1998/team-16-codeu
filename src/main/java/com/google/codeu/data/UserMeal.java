//Meal entered by the users
package com.google.codeu.data;
import java.util.UUID;
public class UserMeal {

  private UUID id; //Use it to match with user information (based on Message object)
  private Map<String,double> foodAmount; //a meal might contain multiple meals. We store each kind of food and their amount into a map
  private Date date; //Date(int year, int month, int date)
  private String imageUrl;

  public UserMeal(Map<String,double> foodAmount , Date date , String imageUrl) {
    this.id = UUID.randomUUID();
    this.foodAmount = foodAmount;
    this.date = date;
    this.imageUrl = imageUrl;
  }

  public UUID getID() {
    return id;
  }

  public Map<String,double> getfood_amount() {
    return foodAmount;
  }

  public Date getDate(){
    return date;
  }

  public String getImageUrl(){
     return imageUrl;
  }

}
