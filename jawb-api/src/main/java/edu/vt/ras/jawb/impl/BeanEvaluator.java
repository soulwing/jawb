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

import java.util.LinkedHashSet;
import java.util.Set;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.Evaluator;


/**
 * An {@link Evaluator} for a Java bean type.
 *
 * @author Carl Harris
 */
class BeanEvaluator implements Evaluator {

  private final Set<Binding> bindings = new LinkedHashSet<Binding>();

  private final Class<?> targetType;
  
  /**
   * Constructs a new instance.
   * @param targetType the target bean type
   */
  public BeanEvaluator(Class<?> targetType) {
    this.targetType = targetType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    try {
      Object bean = targetType.newInstance();
      for (Binding binding : bindings) {
        binding.bind(workbook, bean);
      }
      return bean;
    }
    catch (InstantiationException ex) {
      throw new WorkbookBindingException(ex);
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex);
    }
  }

  /**
   * Adds a binding to the receiver.
   * @param binding
   */
  public void addBinding(Binding binding) {
    bindings.add(binding);
  }
}
