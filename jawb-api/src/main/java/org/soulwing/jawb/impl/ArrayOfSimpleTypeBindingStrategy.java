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
import org.soulwing.jawb.spi.WorkbookIterator;


/**
 * A {@link BindingStrategy} for an attribute with an array of a supported
 * simple type.
 *
 * @author Carl Harris
 */
class ArrayOfSimpleTypeBindingStrategy implements BindingStrategy {

  public static final BindingStrategy INSTANCE =
      new ArrayOfSimpleTypeBindingStrategy();
  
  private ArrayOfSimpleTypeBindingStrategy() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Binding createBinding(AttributeIntrospector introspector,
      EvaluatorFactory evaluatorFactory) throws WorkbookBindingException {
    
    if (!introspector.isArrayType()) return null;
    
    Class<?> type = introspector.getSubType();
    if (!TypeUtil.isSupportedSimpleType(type)) return null;

    Cell cell = introspector.getAnnotation(Cell.class);
    WorkbookIterator iterator = WorkbookIteratorUtil.createIteratorForAttribute(
        introspector, evaluatorFactory);
    
    if (cell == null && iterator == null) {
      return null;
    }
    
    if (iterator == null || cell == null) {
      throw new WorkbookBindingException(
          "an array of simple types must have include annotations for "
          + " cell and iteration");
    }
    
    Evaluator cellEvaluator = evaluatorFactory.createCellEvaluator(
        introspector.getSheetReference(), cell.value(), type);
    
    Evaluator evaluator = evaluatorFactory.createArrayEvaluator(type, 
        cellEvaluator, iterator);
    
    return new BaseBinding(introspector.getAccessor(), evaluator);
  }

}
