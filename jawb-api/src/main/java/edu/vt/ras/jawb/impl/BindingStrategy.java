/*
 * File created on Dec 20, 2013 
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
package edu.vt.ras.jawb.impl;

import edu.vt.ras.jawb.WorkbookBindingException;


/**
 * A strategy for producing a {@link Binding}.
 *
 * @author Carl Harris
 */
interface BindingStrategy {

  /**
   * Attempts to create a binding for a given attribute of a bound class.
   * @param introspector introspector for the subject attribute
   * @param evaluatorFactory evaluator factory
   * @return binding or {@code null} if the receiver does not know how to
   *    bind the type represented by the subject attribute
   * @throws WorkbookBindingException
   */
  Binding createBinding(AttributeIntrospector introspector,
      EvaluatorFactory evaluatorFactory) throws WorkbookBindingException;
  
}
