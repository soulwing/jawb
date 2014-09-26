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
package org.soulwing.jawb.poi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.DateUtil;
import org.soulwing.jawb.spi.DateTimeConverter;

/**
 * A {@link DateTimeConverter} implementation that utilizes POI's
 * {@link DateUtil}.
 *
 * @author Carl Harris
 */
class ApachePoiDateTimeConverter implements DateTimeConverter {

  /**
   * {@inheritDoc}
   */
  @Override
  public double convertDate(String date) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return DateUtil.getExcelDate(df.parse(date));
    }
    catch (ParseException ex) {
      throw new IllegalArgumentException("invalid date format");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double convertTime(String time) {
    return DateUtil.convertTime(time);
  }
  
}
