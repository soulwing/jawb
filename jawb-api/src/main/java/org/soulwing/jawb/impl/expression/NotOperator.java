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

/**
 * A logical NOT operator for an expression.
 *
 * @author Carl Harris
 */
class NotOperator extends UnaryOperator {

  /**
   * Constructs a new instance.
   * @param a operand
   */
  public NotOperator(Operand a) {
    super(a);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Value evaluate(Value a) throws WorkbookBindingException {
    return new Value(Value.Type.BOOLEAN, !a.isTrue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDisplayFormat() {
    return "!%s";
  }
  
}
