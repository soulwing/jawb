/*
 * File created on Dec 17, 2013 
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
package org.soulwing.jawb.spi;


/**
 * A workbook that is bound to a Java object model.
 *
 * @author Carl Harris
 */
public interface BoundWorkbook {

  /**
   * Evaluates the contents of a cell.
   * @param ref cell reference
   * @return evaulation result
   */
  BoundCell evaluateCell(BoundCellReference ref);
  
  /**
   * Applies the workbook's reference bias to the given cell reference.
   * <p>
   * This method is exposed on the interface to allow diagnostic output
   * to display the actual location of iterated cell references.
   * 
   * @param ref the subject cell reference
   * @return In the face of one or more active workbook iterators, the 
   *    return value will be a new cell reference that has been biased by 
   *    the current state of each of the active iterators. Otherwise, the 
   *    return value {@code ref}.
   */
  BoundCellReference applyBias(BoundCellReference ref);  

  /**
   * Pushes a new iterator onto the receiver's stack of iterators.
   * @param iterator the iterator to push
   */
  void pushIterator(WorkbookIterator iterator);
  
  /**
   * Pops the top of the stack of iterators.
   * @param iterator expected top-of-stack iterator
   * @throws IllegalStateException if the top-of-stack iterator is not
   *    the same object as {@code iterator}
   */
  void popIterator(WorkbookIterator iterator);
  
}
