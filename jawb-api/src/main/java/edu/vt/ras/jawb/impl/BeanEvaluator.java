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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

  private static final String BEFORE_EXTRACT_METHOD = "beforeExtract";

  private static final String AFTER_EXTRACT_METHOD = "afterExtract";

  private final Set<Binding> bindings = new LinkedHashSet<Binding>();

  private final Class<?> targetType;
  
  private CallbackMethod beforeExtractMethod;  
  private CallbackMethod afterExtractMethod;
  
  private Object parent;
  
  /**
   * Constructs a new instance.
   * @param parent parent bean evaluator
   * @param targetType the target bean type
   */
  public BeanEvaluator(Class<?> targetType) {
    this.targetType = targetType;
  }

  /**
   * Sets the parent bean.
   * @param parent parent bean reference
   */
  public void setParent(Object parent) {
    this.parent = parent;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundWorkbook workbook)
      throws WorkbookBindingException {
    try {
      Object bean = targetType.newInstance();
      invokeCallback(beforeExtractMethod(), bean, parent);
      for (Binding binding : bindings) {
        binding.bind(workbook, bean);
      }
      invokeCallback(afterExtractMethod(), bean, parent);
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
    
  /**
   * Invokes the specified callback method
   * @param callback callback to invoke
   * @param target callback target
   * @param parent parent object that will be passed to callback
   * @throws WorkbookBindingException
   */
  private void invokeCallback(CallbackMethod callback, Object target, 
      Object parent) 
      throws WorkbookBindingException {
    try {
      callback.invoke(target, parent);
    }
    catch (InvocationTargetException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex);
    }
  }
  
  /**
   * Gets the {@code beforeExtract} callback for the target type.
   * @return callback object (never {@code null})
   */
  private CallbackMethod beforeExtractMethod() {
    if (beforeExtractMethod == null) {
      beforeExtractMethod = getExtractMethod(BEFORE_EXTRACT_METHOD);
    }
    return beforeExtractMethod;
  }
  
  /**
   * Gets the {@code afterExtract} callback for the target type.
   * @return callback object (never {@code null})
   */
  private CallbackMethod afterExtractMethod() {
    if (afterExtractMethod == null) {
      afterExtractMethod = getExtractMethod(AFTER_EXTRACT_METHOD);
    }
    return afterExtractMethod;
  }

  /**
   * Gets a callback method for the target type.
   * @param name name of the callback method
   * @return callback object (never {@code null})
   */
  private CallbackMethod getExtractMethod(String name) {
    try {
      Method method = targetType.getDeclaredMethod(name, Object.class);
      method.setAccessible(true);
      return new DelegatingCallbackMethod(method);
    }
    catch (NoSuchMethodException ex) {
      return new NullCallbackMethod();
    }
  }
  
  /**
   * An extractor callback method.
   */
  private interface CallbackMethod {
    
    /**
     * Invokes the callback
     * @param target target object instance
     * @param parent parent object instance
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void invoke(Object target, Object parent) 
        throws InvocationTargetException, IllegalAccessException;
  }
  
  /**
   * A no-op callback.
   */
  private static class NullCallbackMethod implements CallbackMethod {

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(Object target, Object parent) {      
    }
    
  }
  
  /**
   * A {@link CallbackMethod} that delegates to a {@link Method}.
   */
  private static class DelegatingCallbackMethod implements CallbackMethod {

    private final Method method;
        
    /**
     * Constructs a new instance.
     * @param method
     */
    public DelegatingCallbackMethod(Method method) {
      this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(Object target, Object parent) 
        throws InvocationTargetException, IllegalAccessException {
      method.invoke(target, parent);
    }
    
  }
  
}
