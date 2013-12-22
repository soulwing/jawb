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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCellValue;

/**
 * A strategy for binding a cell to a value obtained by invoking a static
 * {@code valueOf(double)} method on the target type.
 *
 * @author Carl Harris
 */
class ValueOfCellEvaluatorStrategy implements CellEvaluatorStrategy {
  
  public static final CellEvaluatorStrategy INSTANCE = 
      new ValueOfCellEvaluatorStrategy();
  
  private ValueOfCellEvaluatorStrategy() {    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundCellReference ref, BoundCellValue value, Class<?> targetType)
      throws WorkbookBindingException {

    if (!value.getType().equals(BoundCellValue.Type.NUMERIC)) {
      return null;
    }
    
    Method method = TypeUtil.findValueOfMethod(targetType);
    if (method == null) {
      return null;
    }

    try {
      Class<?> argType = method.getParameterTypes()[0];
      return method.invoke(targetType, TypeUtil.wrapValue(argType, 
          value.getNumericValue()));
    }
    catch (InvocationTargetException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }

  }

}