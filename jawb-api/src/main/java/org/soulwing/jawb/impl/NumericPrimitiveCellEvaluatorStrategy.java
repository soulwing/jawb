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

import org.soulwing.jawb.TypeMismatchException;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundCell;

/**
 * A {@link CellEvaluatorStrategy} for numeric primitive target types.
 *
 * @author Carl Harris
 */
class NumericPrimitiveCellEvaluatorStrategy 
    implements CellEvaluatorStrategy {

  public static final CellEvaluatorStrategy INSTANCE =
      new NumericPrimitiveCellEvaluatorStrategy();
  
  private NumericPrimitiveCellEvaluatorStrategy() {    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundCell cell, Class<?> targetType) throws WorkbookBindingException {
    
    if (!TypeUtil.isNumericPrimitive(targetType)) {
      return null;
    }
    
    try {
      return TypeUtil.wrapValue(targetType, cell.getNumericValue());
    }
    catch (IllegalStateException ex) {
      throw new TypeMismatchException(cell.getReference(), targetType);
    }

  }

}
