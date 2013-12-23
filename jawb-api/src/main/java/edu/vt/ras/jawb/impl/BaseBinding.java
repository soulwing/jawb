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
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.Evaluator;


/**
 * A binding for a simple attribute (e.g. a String stringValue on a bean).
 *
 * @author Carl Harris
 */
class BaseBinding implements Binding {

  private final Accessor accessor;
  private final Evaluator evaluator;
    
  /**
   * Constructs a new instance.
   * @param accessor accessor for the bound attribute
   * @param evaluator evaluator for the bound cell
   */
  public BaseBinding(Accessor accessor, Evaluator evaluator) {
    this.accessor = accessor;
    this.evaluator = evaluator;
  }

  /**
   * {@inheritDoc}
   */
  public void bind(BoundWorkbook workbook, Object boundObject) 
      throws WorkbookBindingException {
    
    if (evaluator instanceof BeanEvaluator) {
      ((BeanEvaluator) evaluator).setParent(boundObject);
    }
    
    Object result = evaluator.evaluate(workbook);
    if (result == null && accessor.getType().isPrimitive()) return;
    accessor.setValue(boundObject, result);
    
    if (evaluator instanceof BeanEvaluator) {
      ((BeanEvaluator) evaluator).setParent(null);
    }

  }
  
}
