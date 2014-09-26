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
package org.soulwing.jawb.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundCell;

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
  public Object evaluate(BoundCell value, Class<?> targetType)
      throws WorkbookBindingException {

    Method method = TypeUtil.findValueOfMethod(targetType);
    if (method == null) {
      return null;
    }

    try {
      Class<?> argType = method.getParameterTypes()[0];
      return method.invoke(targetType, TypeUtil.wrapValue(argType, 
          value.getNumericValue()));
    }
    catch (IllegalStateException ex) {
      return null;
    }
    catch (InvocationTargetException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }

  }
  

}