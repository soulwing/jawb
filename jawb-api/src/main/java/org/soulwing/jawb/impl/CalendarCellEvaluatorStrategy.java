/*
 * File created on Dec 17, 2013 
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
package org.soulwing.jawb.impl;

import java.util.Calendar;

import org.soulwing.jawb.TypeMismatchException;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundCell;

/**
 * A strategy for binding a cell containing a date to a Java {@link Calendar}.
 *
 * @author Carl Harris
 */
class CalendarCellEvaluatorStrategy implements CellEvaluatorStrategy {

  public static final CellEvaluatorStrategy INSTANCE = 
      new CalendarCellEvaluatorStrategy();
  
  private CalendarCellEvaluatorStrategy() {    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundCell cell, Class<?> targetType, String format) 
      throws WorkbookBindingException {

    if (!Calendar.class.isAssignableFrom(targetType)) {
      return null;
    }

    try {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(cell.getDateValue());
      return calendar;
    }
    catch (IllegalStateException ex) {
      throw new TypeMismatchException(cell.getReference(), targetType);
    }
  }

}
