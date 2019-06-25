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


/** A single message posted by a user. */
public class FoodItem {

  private String name;
  private String amount;
  private double co2peryear;

  public FoodItem(String name, String amount, double co2peryear) {
    this.name = name;
    this.amount = amount;
    this.co2peryear = co2peryear;
  }

  public String getName() {
    return name;
  }

  public String getAmount() {
    return amount;
  }


  public double getCO2() {
    return co2peryear;
  }
}
