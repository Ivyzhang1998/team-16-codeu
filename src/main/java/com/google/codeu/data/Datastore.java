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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.codeu.data.FoodItem;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.*;

/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());

    datastore.put(messageEntity);
  }

    /** Stores FoodItem in Datastore.
      * Prevent the same food from being entered into the database more than once
      */

  public void storeFood(FoodItem food) {
    if (exists(food)){
       return;
    }
    else {
       Entity foodEntity = new Entity("FoodItem", food.getID().toString());
       foodEntity.setProperty("Name", food.getName());
       foodEntity.setProperty("CO2", food.getCO2PerYear());
       datastore.put(foodEntity);
       return;
    }
  }

  public boolean exists(FoodItem oneFood) {
    Set<String> existingFoods = this.getAllFoodItems();
    Set<String> lowercaseFoods = new HashSet<>();
    for (String food : existingFoods) {
      lowercaseFoods.add(food.toLowerCase());
    }
    return lowercaseFoods.contains(oneFood.getName().toLowerCase());
  }

  /** Stores Meal in Datastore. */
  public void storeMeal(EatenMeal meal) {
    Entity mealEntity = new Entity("EatenMeal", meal.getID().toString());
    mealEntity.setProperty("Food", meal.getFood());
    mealEntity.setProperty("UserId", meal.getUserId());
    mealEntity.setProperty("Amount", meal.getAmount());
    mealEntity.setProperty("MealType", meal.getMealType().ordinal());
    mealEntity.setProperty("Date", meal.getDate());
    datastore.put(mealEntity);
  }


  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String user) {
    Query query =
        new Query("Message")
            .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    List<Message> messages = this.processMessageQuery(results);

    return messages;
  }

  /**
   * Gets messages from all users.
   *
   * @return a list of messages from all users. List is sorted by time descending.
   */
  public List<Message> getAllMessages(){
	  Query query = new Query("Message")
	    .addSort("timestamp", SortDirection.DESCENDING);
	  PreparedQuery results = datastore.prepare(query);

	  List<Message> messages = this.processMessageQuery(results);

	  return messages;
 }

  /**
   * Iterates through query results and returns a list of messages
   *
   * @return a list of messages.
   */
  private List<Message> processMessageQuery(PreparedQuery queryResults) {
	  List<Message> messages = new ArrayList<>();

	  for (Entity entity : queryResults.asIterable()) {
		try {
			String idString = entity.getKey().getName();
			UUID id = UUID.fromString(idString);
			String user = (String) entity.getProperty("user");
			String text = (String) entity.getProperty("text");
			long timestamp = (long) entity.getProperty("timestamp");

			Message message = new Message(id, user, text, timestamp);
			messages.add(message);
		} catch (Exception e) {
			System.err.println("Error reading message.");
			System.err.println(entity.toString());
			e.printStackTrace();
		}
	  }

	  return messages;
  }

  /** Returns the total number of messages for all users.
   *
   *  @return total number of messages posted by all users, limited to 1000.
   *  */
  public int getTotalMessageCount(){
    Query query = new Query("Message");
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }

  /**Fetch a list of users based on all of the messages stored in Dataset
   *
   * @return a list of users who have posted messages
   *
   */
  public Set<String> getUsers(){
      Set<String> users = new HashSet<>();
      Query query = new Query("Message");
      PreparedQuery results = datastore.prepare(query);
      for(Entity entity : results.asIterable()) {
         users.add((String) entity.getProperty("user"));
      }
      return users;
  }

  public Set<String> getAllFoodItems(){
    Set<String> foodnames = new HashSet<>();
    Query query = new Query("FoodItem");
    PreparedQuery results = datastore.prepare(query);
    for(Entity entity : results.asIterable()) {
       foodnames.add((String) entity.getProperty("Name"));
    }
    return foodnames;
  }

  public double getCO2forOneUnitFood(String FoodName){
    Map<String,Double> namesToCO2 = new HashMap<>();
    Query query = new Query("FoodItem");
    PreparedQuery results = datastore.prepare(query);
    for(Entity entity : results.asIterable()) {
       namesToCO2.put((String) entity.getProperty("Name"), (double)entity.getProperty("CO2"));
    }
    if(namesToCO2.containsKey(FoodName)){
      return namesToCO2.get(FoodName);
    }
    return 0;

  }




}
