/*
 * File created on Dec 17, 2013 
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
package edu.vt.ras.jawb.impl.cell;

import java.util.Calendar;

import edu.vt.ras.jawb.TypeMismatchException;
import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCellValue;

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
  public Object evaluate(BoundCellReference ref, BoundCellValue value, Class<?> targetType) 
      throws WorkbookBindingException {

    if (!Calendar.class.isAssignableFrom(targetType)) {
      return null;
    }

    if (!value.getType().equals(BoundCellValue.Type.NUMERIC)
        || !value.isValidDate()) {
      throw new TypeMismatchException(ref, value.getType(), targetType);
    }
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(value.getDateValue());
    return calendar;
  }

}