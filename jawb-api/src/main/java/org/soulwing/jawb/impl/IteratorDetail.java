/*
 * File created on Dec 24, 2013 
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

import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.spi.WorkbookBindingProvider;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Detailed properties for a sheet/row/column iterator.
 * 
 * @author Carl Harris
 */
class IteratorDetail {

  private final int count;
  private final int increment;
  private final String stop;
  private final String skip;

  /**
   * Constructs a new instance.
   * @param annotation sheet iterator annotation
   */
  public IteratorDetail(IterateSheets annotation) {
    this(annotation.count(), annotation.increment(), annotation.stop(),
        annotation.skip());
  }

  /**
   * Constructs a new instance.
   * @param annotation row iterator annotation
   */
  public IteratorDetail(IterateRows annotation) {
    this(annotation.count(), annotation.increment(), annotation.stop(),
        annotation.skip());
  }

  /**
   * Constructs a new instance.
   * @param annotation column iterator annotation
   */
  public IteratorDetail(IterateColumns annotation) {
    this(annotation.count(), annotation.increment(), annotation.stop(),
        annotation.skip());
  }

  /**
   * Constructs a new instance.
   * @param count
   * @param increment
   * @param stop
   * @param skip
   */
  private IteratorDetail(int count, int increment, String stop, String skip) {
    this.count = count;
    this.increment = increment;
    this.stop = stop;
    this.skip = skip;
  }

  /**
   * Creates a new sheet iterator.
   * @param provider binding provider
   * @return iterator
   */
  public WorkbookIterator createSheetIterator(WorkbookBindingProvider provider) {
    return new SheetIterator(count, increment, stop, skip, provider);
  }

  /**
   * Creates a new row iterator.
   * @param provider binding provider
   * @return iterator
   */
  public WorkbookIterator createRowIterator(WorkbookBindingProvider provider) {
    return new RowIterator(count, increment, stop, skip, provider);
  }

  /**
   * Creates a new column iterator.
   * @param provider binding provider
   * @return iterator
   */
  public WorkbookIterator createColumnIterator(
      WorkbookBindingProvider provider) {
    return new ColumnIterator(count, increment, stop, skip, provider);
  }

}
