/*
 * File created on Dec 23, 2013 
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
package org.soulwing.jawb.impl.expression;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * A operand that represents a unary operator;
 *
 * @author Carl Harris
 */
abstract class UnaryOperator extends AbstractOperand {

  private final Operand a;
  
  /**
   * Constructs a new instance.
   * @param a operand
   */
  public UnaryOperator(Operand a) {
    this.a = a;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final Value doEvaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    return evaluate(a.evaluate(workbook));
  }

  /**
   * Performs the receiver's operation on the given operand values.
   * @param a operand value
   * @return operation result
   * @throws WorkbookBindingException
   */
  protected abstract Value evaluate(Value a) throws WorkbookBindingException;

  /**
   * Gets a format string for {@link String#format(String, Object...)} that
   * will be used to provide a common implementation of {@link #toString()}.
   * @return format string with a single string placeholder for the unary
   *    operand
   */
  protected abstract String getDisplayFormat();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(BoundWorkbook workbook) {
    return String.format(getDisplayFormat(), a.toString(workbook));
  }

}
