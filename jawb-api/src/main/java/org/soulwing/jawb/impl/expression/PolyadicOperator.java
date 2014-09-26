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
package org.soulwing.jawb.impl.expression;

import java.util.ArrayList;
import java.util.List;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * An operand that represents a binary operator.
 *
 * @author Carl Harris
 */
class PolyadicOperator extends AbstractOperand {

  private final List<Operand> operands = 
      new ArrayList<Operand>();
  
  private final List<BinaryOperator> operators = 
      new ArrayList<BinaryOperator>();
  
  /**
   * Constructs a new instance.
   * @param operand left-most operand
   */
  public PolyadicOperator(Operand operand) {
    operands.add(operand);
  }
  
  /**
   * Adds an operation to this operator.
   * @param operator operator for this operation
   * @param operand right-hand operand
   */
  public void addOperation(BinaryOperator operator, Operand operand) {
    operators.add(operator);
    operands.add(operand);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected final Value doEvaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    Value result = operands.get(0).evaluate(workbook);
    if (operators.size() > 0) {
      for (int i = 0, max = operators.size(); i < max; i++) {
        result = operators.get(i).evaluate(
            result, operands.get(i + 1).evaluate(workbook));
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(BoundWorkbook workbook) {
    StringBuilder sb = new StringBuilder();
    sb.append(operands.get(0).toString(workbook));
    if (operators.size() > 0) {
      for (int i = 0, max = operators.size(); i < max; i++) {
        sb.append(' ').append(operators.get(i)).append(' ');
        sb.append(operands.get(i + 1).toString(workbook));
      }
    }    
    return sb.toString();
  }

}
