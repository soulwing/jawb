/*
 * File created on Dec 18, 2013 
 *
 * Copyright (c) 2013 Carl Harris, Jr.
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
package org.soulwing.jawb.spi;

import java.util.Date;

/**
 * An object that provides access to the type and value of a bound cell.
 *
 * @author Carl Harris
 */
public interface BoundCell {

  /**
   * Gets the cell reference representing the location of this cell.
   * @return cell reference
   */
  BoundCellReference getReference();
  
  /**
   * Tests the receiver to determine it it represents a blank cell value.
   * @return {@code true} if this value is blank
   */
  boolean isBlank();
  
  /**
   * Gets the cell's value as either a {@code Double}, {@code Boolean},
   * {@code String}, or {@code null} according to its contents.
   * @return cell value
   */
  Object getValue();
  
  /**
   * Gets the receiver's value as a boolean.
   * @return boolean value of this cell
   * @throws IllegalStateException if invoked on a value that is not of 
   *    boolean type
   */
  boolean getBooleanValue();
  
  /**
   * Gets the receiver's value as a {@link Date}.
   * @return date value of this cell
   * @throws IllegalStateException if invoked on a value that is not of 
   *    date type
   */
  Date getDateValue();
  
  /**
   * Gets the receiver's value as a double.
   * @return double value of this cell
   * @throws IllegalStateException if invoked on a value that is not of 
   *    numeric type
   */
  double getNumericValue();
  
  /**
   * Gets the receiver's value as a string.
   * @return string value of this cell
   * @throws IllegalStateException if invoked on a value that is not of 
   *    numeric type
   */
  String getStringValue();
  
}
