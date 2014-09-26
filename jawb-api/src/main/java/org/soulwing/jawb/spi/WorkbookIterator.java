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
package org.soulwing.jawb.spi;

import org.soulwing.jawb.WorkbookBindingException;


/**
 * An iterator that is used to bias a workbook cell reference.
 *
 * @author Carl Harris
 */
public abstract class WorkbookIterator {

  private final int count;
  private final int increment;
  private final String stop;
  private final String skip;
  private final WorkbookBindingProvider provider;
  private int iteration;
  private String sheetReference;
  private Evaluator stopExpression;
  private Evaluator skipExpression;
  
  /**
   * Constructs a new instance.
   * @param count number of steps in the iteration
   * @param increment offset to apply at each iteration
   * @param stop expression to evaluate to stop iteration
   * @param skip expression to evaluate to skip the result of an iteration
   * @param provider binding provider
   */
  protected WorkbookIterator(int count, int increment, 
      String stop, String skip, WorkbookBindingProvider provider) {
    this.count = count;
    this.increment = increment;
    this.stop = stop;
    this.skip = skip;
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
   * Gets the {@code sheetReference} property.
   * @return
   */
  public String getSheetReference() {
    return sheetReference;
  }

  /**
   * Sets the {@code sheetReference} property.
   * @param sheetReference
   */
  public void setSheetReference(String sheetReference) {
    this.sheetReference = sheetReference;
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
   * Evaluates the stop expression.
   * @param workbook the subject workbook 
   * @return stop expression result
   * @throws WorkbookBindingException
   */
  public boolean stop(BoundWorkbook workbook) 
      throws WorkbookBindingException {
    if (stop == null || stop.trim().isEmpty()) return false;
    return (boolean) stopExpression().evaluate(workbook);
  }

  /**
   * Evaluates the skip expression.
   * @param workbook the subject workbook 
   * @return skip expression result
   * @throws WorkbookBindingException
   */
  public boolean skip(BoundWorkbook workbook) 
      throws WorkbookBindingException {
    if (skip == null || skip.trim().isEmpty()) return false;
    return (boolean) skipExpression().evaluate(workbook);
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

  /**
   * Gets the expression to evaluate to determine if the iteration should
   * be stopped.
   * @return expression evaluator
   */
  private Evaluator stopExpression() {
    if (stopExpression == null) {
      stopExpression = provider.getExpressionFactory()
          .createPredicateEvaluator(stop, sheetReference);
    }
    return stopExpression;
  }

  /**
   * Gets the expression to evaluate to determine if the result of the 
   * current iteration should be skipped
   * @return expression evaluator
   */
  private Evaluator skipExpression() {
    if (skipExpression == null) {
      skipExpression = provider.getExpressionFactory()
          .createPredicateEvaluator(skip, sheetReference);
    }
    return skipExpression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" count=").append(count);
    sb.append(" increment=").append(increment);
    sb.append(" stop=\"").append(stop).append("\"");
    sb.append(" skip=\"").append(skip).append("\"");
    return sb.toString();
  }

}
