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

import java.util.Collection;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

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
  Evaluator createBeanEvaluator(BeanIntrospector boundClass)
      throws WorkbookBindingException;
  
  /**
   * Creates an array evaluator.
   * @param targetType target array element type
   * @param elementEvaluator element evaluator
   * @param iterator workbook iterator
   * @return evaluator
   */
  Evaluator createArrayEvaluator(Class<?> targetType,
      Evaluator elementEvaluator, WorkbookIterator iterator);

  /**
   * Creates a collection evaluator.
   * @param targetType target collection type
   * @param elementEvaluator element evaluator
   * @param iterator workbook iterator
   * @return evaluator
   */
  Evaluator createCollectionEvaluator(
      Class<? extends Collection> targetType,
      Evaluator elementEvaluator, WorkbookIterator iterator);
  
  /**
   * Creates a cell evaluator.
   * @param sheetReference sheet reference
   * @param ref string representation of the cell reference.
   * @param targetType type of target attribute
   * @param format format string
   * @return evaluator
   */
  Evaluator createCellEvaluator(String sheetReference,
      String ref, Class<?> targetType, String format);

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
   * @param annotation iterator details
   * @return iterator
   */
  WorkbookIterator createSheetIterator(IterateSheets annotation);

  /**
   * Creates a new iterator for rows.
   * @param annotation iterator details
   * @return iterator
   */
  WorkbookIterator createRowIterator(IterateRows annotation);
  
  /**
   * Creates a new iterator for columns.
   * @param annotation iterator details
   * @return iterator
   */
  WorkbookIterator createColumnIterator(IterateColumns annotation);
  
}
