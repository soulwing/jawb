/*
 * File created on Dec 24, 2013 
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


/**
 * A converter for date/time values.
 *
 * @author Carl Harris
 */
public interface DateTimeConverter {

  /**
   * Converts a date in ISO 8601 format (YYYY-MM-DD) to an Excel serial date.
   * @param date the date string to convert
   * @return serial date
   * @throws IllegalArgumentException if the date is ill-formatted
   */
  double convertDate(String date);
  
  /**
   * Converts a time in ISO 8601 format (HH:MM:SS) to an Excel serial time
   * @param time the time string to convert
   * @return serial time
   * @throws IllegalArgumentException if the time is ill-formatted
   */
  double convertTime(String time);
}
