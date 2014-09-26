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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * A binding for an array type.
 *
 * @author Carl Harris
 */
class ArrayEvaluator extends CollectionEvaluator {

  private final Class<?> targetType;
  
  /**
   * Constructs a new instance.
   * @param targetType target array type
   * @param elementEvaluator
   * @param iterator;
   */
  public ArrayEvaluator(Class<?> targetType,
      Evaluator elementEvaluator, WorkbookIterator iterator) {
    super(LinkedList.class, elementEvaluator, iterator);
    this.targetType = targetType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    return convertToArray((Collection) super.evaluate(workbook));
  }

  /**
   * Converts a collection of elements to an equivalent array.
   * @param elements collection of elements
   * @return array of elements
   */
  private Object convertToArray(Collection elements) {
    Object[] array = (Object[]) Array.newInstance(targetType, elements.size());
    int index = 0;
    Iterator i = elements.iterator();
    while (i.hasNext()) {
      Object element = i.next();
      array[index++] = element;
    }
    return array;
  }

}
