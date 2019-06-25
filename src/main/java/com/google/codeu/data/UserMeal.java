/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import java.util.*;
/** A single message posted by a user. */
public class UserMeal {

  private UUID mealid; //Use it to match with user information (based on Message object)
  private Map Food_Amount; //a meal might contain multiple meals. We store each kind of food and their amount into a map
  //e.g: Map<String, double> -> (foodname, amount)
  private Date date; //Date(int year, int month, int date)
  private String imageurl;

  public UserMeal(Map Food_Amount,Date date,String imageurl) {
    this.mealid = UUID.randomUUID();
    this.Food_Amount = Food_Amount;
    this.date = date;
    this.imageurl = imageurl;
  }

  public UUID getMealID() {
    return mealid;
  }

  public Map getfood_amount() {
    return Food_Amount;
  }

  public Date getDate(){
    return date;
  }

  public String getImageurl(){
     return imageurl;
  }

}
