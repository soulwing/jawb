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
package edu.vt.ras.jawb.spi;


/**
 * An iterator that is used to bias a workbook cell reference.
 *
 * @author Carl Harris
 */
public abstract class WorkbookIterator {

  private final int count;
  private final int increment;
  private final WorkbookBindingProvider provider;
  private int iteration;
  
  /**
   * Constructs a new instance.
   * @param count number of steps in the iteration
   * @param increment offset to apply at each iteration
   * @param provider binding provider
   */
  protected WorkbookIterator(int count, int increment, WorkbookBindingProvider provider) {
    this.count = count;
    this.increment = increment;
    this.provider = provider;
  }

  /**
   * Gets the workbook binding provider.
   * @return provider
   */
  protected WorkbookBindingProvider getProvider() {
    return provider;
  }
  
  /**
   * Tests whether more steps remain in the receiver's iteration.
   * @return {@code true} if more steps remain
   */
  public final boolean hasNext() {
    return iteration < count;
  }

  /**
   * Directs the receiver to move to the next step in the iteration.
   * <p>
   * Has no effect if the iteration is already completed.
   */
  public void next() {
    if (iteration >= count) return;
    iteration++;
  }

  /**
   * Resets the receiver such that the iteration will start over at the
   * beginning.
   */
  public void reset() {
    iteration = 0;
  }

  /**
   * Gets the offset specified by the receiver's current state
   * @return current offset
   */
  protected int getOffset() {
    if (iteration == 0) {
      throw new IllegalStateException("iteration not started");
    }
    return (iteration - 1)*increment;
  }
  
  /**
   * Applies the bias appropriate for the receiver's current state
   * to a given cell reference.
   * @param ref subject cell reference
   * @param workbook workbook context
   * @return a new cell reference that represents the reference that results
   *    from applying the receiver's row/column offset to {@code ref}
   *    
   */
  public abstract BoundCellReference applyBias(BoundCellReference ref, 
      BoundWorkbook workbook);
  
}
