/*
 * File created on Dec 21, 2013 
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

import edu.vt.ras.jawb.annotation.IterateColumns;
import edu.vt.ras.jawb.annotation.IterateRows;
import edu.vt.ras.jawb.annotation.IterateSheets;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * A static utility method for creating a workbook iterator from the
 * annotations on an attribute.
 *
 * @author Carl Harris
 */
class WorkbookIteratorUtil {

  /**
   * Attempts to create an iterator for a given attribute based on the
   * iteration annotation applied to the attribute.
   * @param introspector introspector for the subject attribute
   * @param evaluatorFactory evaluator factory
   * @return iterator or {@code null} if the subject attribute was declared
   *    with no iteration annotation
   */
  public static WorkbookIterator createIteratorForAttribute(
      AttributeIntrospector introspector, EvaluatorFactory evaluatorFactory) {
    WorkbookIterator iterator = null;
  
    IterateColumns columns = introspector.getAnnotation(IterateColumns.class);
    IterateRows rows = introspector.getAnnotation(IterateRows.class);
    IterateSheets sheets = introspector.getAnnotation(IterateSheets.class);
    if (columns != null) {
      iterator = evaluatorFactory.createColumnIterator(columns);
    }
    else if (rows != null) {      
      iterator = evaluatorFactory.createRowIterator(rows);
    }
    else if (sheets != null) {      
      iterator = evaluatorFactory.createSheetIterator(sheets);
    }
    return iterator;
  }

}
