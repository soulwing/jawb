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

import java.util.Collection;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * A binding for a collection type.
 *
 * @author Carl Harris
 */
class CollectionEvaluator implements Evaluator, Parentable {

  private final Class<? extends Collection> targetType;
  private final Evaluator elementEvaluator;
  private final WorkbookIterator iterator;
  
  private Object parent;
  
  /**
   * Constructs a new instance.
   * @param targetType target collection type
   * @param elementEvaluator
   * @param iterator;
   */
  public CollectionEvaluator(Class<? extends Collection> targetType,
      Evaluator elementEvaluator, WorkbookIterator iterator) {
    this.targetType = targetType;
    this.elementEvaluator = elementEvaluator;
    this.iterator = iterator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(Object parent) {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {

    if (elementEvaluator instanceof Parentable) {
      ((Parentable) elementEvaluator).setParent(parent);
    }
    
    Collection elements = newCollection();
    iterator.reset();
    workbook.pushIterator(iterator);
    while (iterator.hasNext()) {
      iterator.next();
      if (iterator.stop(workbook)) break;
      if (iterator.skip(workbook)) continue;
      Object element = elementEvaluator.evaluate(workbook);
      elements.add(element);
    }
    workbook.popIterator(iterator);

    if (elementEvaluator instanceof Parentable) {
      ((Parentable) elementEvaluator).setParent(null);
    }

    return elements;
  }

  /**
   * Creates a new instance of the target collection type.
   * @return collection object
   * @throws WorkbookBindingException
   */
  private Collection newCollection() throws WorkbookBindingException {
    try {
      return targetType.newInstance();
    }
    catch (InstantiationException ex) {
      throw new WorkbookBindingException(ex);
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex);
    }
  }
  
}
