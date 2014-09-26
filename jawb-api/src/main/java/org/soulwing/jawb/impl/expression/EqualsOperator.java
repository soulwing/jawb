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

import org.soulwing.jawb.WorkbookBindingException;

/**
 * An equality operator for an expression.
 *
 * @author Carl Harris
 */
class EqualsOperator implements BinaryOperator {

  /**
   * {@inheritDoc}
   */
  @Override
  public Value evaluate(Value a, Value b) throws WorkbookBindingException {
    return new Value(Value.Type.BOOLEAN, a.equals(b));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "==";
  }

}
