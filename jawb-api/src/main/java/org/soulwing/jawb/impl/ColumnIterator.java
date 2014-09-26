/*
 * File created on Dec 21, 2013 
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

import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.WorkbookBindingProvider;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * A {@link WorkbookIterator} that iterates column offsets.
 * 
 * @author Carl Harris
 */
class ColumnIterator extends WorkbookIterator {

  /**
   * Constructs a new instance.
   * @param count number of steps in the iteration
   * @param increment offset to apply at each iteration
   * @param stop expression to evaluate to stop iteration
   * @param skip expression to evaluate to skip the result of an iteration
   * @param provider binding provider
   */
  public ColumnIterator(int count, int increment, String stop, String skip,
      WorkbookBindingProvider provider) {
    super(count, increment, stop, skip, provider);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference applyBias(BoundCellReference ref,
      BoundWorkbook workbook) {
    return ref.applyBias(0, 0, getOffset(), workbook);
  }

}
