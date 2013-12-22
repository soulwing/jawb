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
package edu.vt.ras.jawb.impl;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * A factory that produces {@link Evaluator} objects.
 *
 * @author Carl Harris
 */
public interface EvaluatorFactory {

  /**
   * Creates a new evaluator for a given bound class.
   * @param boundClass an introspector for the subject bound class
   * @return evaluator
   * @throws WorkbookBindingException
   */
  Evaluator createEvaluator(BeanIntrospector boundClass)
      throws WorkbookBindingException;
  
  /**
   * Creates a bound cell reference.
   * @param sheetReference sheet reference
   * @param ref string representation of the cell reference.
   * @param targetType type of target attribute
   * @return bound cell reference
   */
  Evaluator createCellEvaluator(String sheetReference,
      String ref, Class<?> targetType);

  /**
   * Creates a bean introspector for a bound class.
   * @param parent parent attribute introspector, if any
   * @param boundClass the subject bound class
   * @return introspector
   */
  BeanIntrospector createBeanIntrospector(AttributeIntrospector parent,
      Class<?> boundClass);
  
  /**
   * Creates a new iterator for sheets.
   * @param count number of steps in the iteration
   * @param increment number of sheets at each step
   * @return
   */
  WorkbookIterator createSheetIterator(int count, int increment);

  /**
   * Creates a new iterator for rows.
   * @param count number of steps in the iteration
   * @param increment number of rows at each step
   * @return
   */
  WorkbookIterator createRowIterator(int count, int increment);
  
  /**
   * Creates a new iterator for columns.
   * @param count number of steps in the iteration
   * @param increment number of colums at each step
   * @return
   */
  WorkbookIterator createColumnIterator(int count, int increment);
  
}
