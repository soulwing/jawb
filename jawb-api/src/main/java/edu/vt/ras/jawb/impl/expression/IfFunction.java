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

import java.util.ArrayList;
import java.util.List;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundWorkbook;

/**
 * An operand that represents the IF function in an expression.
 *
 * @author Carl Harris
 */
class IfFunction extends AbstractOperand {

  private final List<Operand> expressions = new ArrayList<Operand>();
  
  /**
   * Adds a condition/antecedent expression to the receiver.
   * @param expression
   */
  public void addExpression(Operand expression) {
    expressions.add(expression);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected Value doEvaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    if (expressions.size() < 2) {
      throw new IllegalStateException("IF requires at least two expressions");
    }
    
    int i = 0;
    int max = expressions.size() / 2;
    while (i < max) {
      Operand condition = expressions.get(2*i);
      Operand antecedent = expressions.get(2*i + 1);
      if (condition.evaluate(workbook).isTrue()) {
        return antecedent.evaluate(workbook);
      }
      i++;
    }
    
    if (2*i < expressions.size()) {
      return expressions.get(2*i).evaluate(workbook);
    }
    
    return new Value();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("if(");
    for (int i = 0, max = expressions.size() / 2; i < max; i++) {
      sb.append(expressions.get(2*i));
      sb.append(", ");
      sb.append(expressions.get(2*i + 1));
      if (i < max - 1) {
        sb.append(", ");
      }
    }
    if (expressions.size() % 2 != 0) {
      sb.append(",");
      sb.append(expressions.get(expressions.size() - 1));
    }
    sb.append(")");
    return sb.toString();
  }
  

}
