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
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCell;

/**
 * A strategy for evaluating the value of a cell.
 *
 * @author Carl Harris
 */
interface CellEvaluatorStrategy {

  /**
   * Attempts to evaluate the given cell value to produce an instance of
   * the target type.
   * @param ref cell reference
   * @param cell evaluated cell
   * @param targetType target binding type
   * @return evaluated value or {@code null} if the receiver does not 
   *    support the target type
   * @throws WorkbookBindingException
   */
  Object evaluate(BoundCellReference ref, BoundCell cell, 
      Class<?> targetType)
      throws WorkbookBindingException;
  
}
