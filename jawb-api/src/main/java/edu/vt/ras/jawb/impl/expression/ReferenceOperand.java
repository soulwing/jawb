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
import edu.vt.ras.jawb.spi.BoundCell;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundWorkbook;

/**
 * An operand that represents a cell reference in an expression.
 *
 * @author Carl Harris
 */
class ReferenceOperand implements Operand {

  private final BoundCellReference ref;
 
  /**
   * Constructs a new instance.
   * @param ref cell reference
   */
  public ReferenceOperand(BoundCellReference ref) {
    this.ref = ref;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Value evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {    
    BoundCell cell = workbook.evaluateCell(ref);
    Object value = cell.getValue();
    if (value == null) return new Value();
    if (value instanceof String) return new Value(Value.Type.STRING, value);
    if (value instanceof Double) return new Value(Value.Type.NUMBER, value);
    if (value instanceof Boolean) return new Value(Value.Type.BOOLEAN, value);
    throw new IllegalStateException("unrecognized data type");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(BoundWorkbook workbook) {
    if (workbook == null) {
      return ref.toString();
    }
    return workbook.applyBias(ref).toString();
  }

}
