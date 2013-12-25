/*
 * File created on Dec 23, 2013 
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
package edu.vt.ras.jawb.impl.expression;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundWorkbook;

/**
 * An abstract base for {@link Operand} implementations.
 *
 * @author Carl Harris
 */
abstract class AbstractOperand implements Operand {

  /**
   * {@inheritDoc}
   */
  @Override
  public final Value evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    try {
      return doEvaluate(workbook);
    }
    catch (RuntimeException ex) {
      return new Value(ex);
    }
  }

  /**
   * Evaluates this operand.
   * <p>
   * This method may thrown any unchecked exception to indicate that an
   * error should be returned as the result of the evaluation.
   * @param workbook
   * @return evaluation result
   * @throws RuntimeException
   * @throws WorkbookBindingException
   */
  protected abstract Value doEvaluate(BoundWorkbook workbook)
      throws RuntimeException, WorkbookBindingException;

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {
    return toString(null);
  }

}
