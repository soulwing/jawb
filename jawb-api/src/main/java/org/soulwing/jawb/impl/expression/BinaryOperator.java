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
package org.soulwing.jawb.impl.expression;

import org.soulwing.jawb.WorkbookBindingException;

/**
 * A binary operator for an expression.
 *
 * @author Carl Harris
 */
interface BinaryOperator {

  /**
   * Performs the binary operation assigned to the receiver.
   * @param a left-hand argument
   * @param b right-hand argument
   * @return operation result
   */
  Value evaluate(Value a, Value b) throws WorkbookBindingException;
  
}
