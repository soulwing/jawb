/*
 * File created on Dec 20, 2013 
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

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Evaluator;

/**
 * An evaluator for the contents of a given cell.
 *
 * @author Carl Harris
 */
class CellEvaluator implements Evaluator {

  private static final CellEvaluatorStrategy[] strategies = {
    BooleanCellEvaluatorStrategy.INSTANCE,
    CalendarCellEvaluatorStrategy.INSTANCE,
    DateCellEvaluatorStrategy.INSTANCE,
    EnumCellEvaluatorStrategy.INSTANCE,
    StringCellEvaluatorStrategy.INSTANCE,
    NumericPrimitiveCellEvaluatorStrategy.INSTANCE,
    ValueOfCellEvaluatorStrategy.INSTANCE
  };
  
  private final BoundCellReference ref;
  private final Class<?> targetType;
  private final String format;
  
  /**
   * Constructs a new instance.
   * @param ref cell reference
   * @param targetType data type for the resulting value
   * @param format format string
   */
  public CellEvaluator(BoundCellReference ref, Class<?> targetType,
      String format) {
    this.ref = ref;
    this.targetType = targetType;
    this.format = format;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    BoundCell value = workbook.evaluateCell(ref);
    Object obj = null;
    if (value.isBlank()) {
      return null;
    }
    for (CellEvaluatorStrategy strategy : strategies) {
      obj = strategy.evaluate(value, targetType, format);
      if (obj != null) break;
    }
    return obj;
  }

}
