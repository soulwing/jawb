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
package org.soulwing.jawb.impl;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * An object that evaluates a (portion of) a workbook to produce and store
 * an attribute value on a bound object.
 *
 * @author Carl Harris
 */
interface Binding {

  /**
   * Binds the value resulting from evaluating a portion of a given workbook
   * onto an attribute of the given bound object.
   * @param workbook the subject workbook
   * @param boundObject the target bound object
   * @throws WorkbookBindingException
   */
  void bind(BoundWorkbook workbook, Object boundObject)
      throws WorkbookBindingException;
  
}
