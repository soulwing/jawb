/*
 * File created on Dec 20, 2013 
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

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.Cell;
import org.soulwing.jawb.spi.Evaluator;

/**
 * A {@link BindingStrategy} for an attribute with a simple data type
 * bound to the value of a single cell.
 *
 * @author Carl Harris
 */
class SimpleTypeBindingStrategy implements BindingStrategy {

  public static final BindingStrategy INSTANCE =
      new SimpleTypeBindingStrategy();
  
  private SimpleTypeBindingStrategy() {    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Binding createBinding(AttributeIntrospector introspector,
      EvaluatorFactory evaluatorFactory) throws WorkbookBindingException {
    Binding binding = null;
    Cell cell = introspector.getAnnotation(Cell.class);
    if (!TypeUtil.isSupportedSimpleType(introspector.getType()) 
        && cell != null) {
      throw new WorkbookBindingException("unsupported data type for attribute "
          + introspector.getName() + " in class " 
          + introspector.getDeclaringClass().getName());
    }
    if (cell != null) {
      
      Evaluator cellEvaluator = evaluatorFactory.createCellEvaluator(
          introspector.getSheetReference(), 
          cell.value(), 
          introspector.getType());
      
      binding = new BaseBinding(introspector.getAccessor(), cellEvaluator);
    }
    return binding;
  }

}
