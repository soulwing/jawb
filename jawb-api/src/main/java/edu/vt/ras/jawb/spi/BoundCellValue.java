/*
 * File created on Dec 18, 2013 
 *
 * Copyright (c) 2013 Virginia Polytechnic Institute and State University
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
 *
 */
package edu.vt.ras.jawb.spi;

import java.util.Date;

/**
 * An object that provides access to the type and value of a bound cell.
 *
 * @author Carl Harris
 */
public interface BoundCellValue {

  enum Type {
    BLANK,
    BOOLEAN,
    ERROR,
    NUMERIC,
    STRING;
  }
  
  /**
   * Gets the receiver's data type.
   * @return data type
   */
  Type getType();
  
  /**
   * Gets the receiver's value as a boolean.
   * <p>
   * This method may throw a runtime exception if invoked on a value of
   * a type other than {@link Type#BOOLEAN}.
   * @return
   */
  boolean getBooleanValue();
  
  /**
   * Tests whether the receiver contains a value that is valid as a date.
   * @return {@code true} if the value can represent a date
   */
  boolean isValidDate();
  
  /**
   * Gets the receiver's value as a {@link Date}.
   * <p>
   * This method may throw a runtime exception if invoked on a value of
   * a type other than {@link Type#DATE}.
   * @return
   */
  Date getDateValue();
  
  /**
   * Gets the receiver's value as a double.
   * <p>
   * This method may throw a runtime exception if invoked on a value of
   * a type other than {@link Type#NUMERIC}.
   * @return
   */
  double getNumericValue();
  
  /**
   * Gets the receiver's value as a string.
   * <p>
   * This method may throw a runtime exception if invoked on a value of
   * a type other than {@link Type#STRING}.
   * @return
   */
  String getStringValue();
  
}
