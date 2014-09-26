/*
 * File created on Dec 18, 2013 
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * An {@link AttributeIntrospector} for a method-based attribute. 
 *
 * @author Carl Harris
 */
class MethodAttributeIntrospector 
    extends AbstractAttributeIntrospector {

  private final Method getter;
  private final Method setter;
  private final String name;
  private Accessor accessor;
  
  /**
   * Constructs a new instance.
   * @param parent parent bean introspector
   * @param name attribute name
   * @param getter getter method
   * @param setter setter method
   */
  public MethodAttributeIntrospector(BeanIntrospector parent,
      String name, Method getter, Method setter) {
    super(parent);
    this.name = name;
    this.getter = getter;
    this.setter = setter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return getter.getReturnType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getDeclaringClass() {
    return getter.getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    return getter.getAnnotation(annotationClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getGenericType() {
    return getter.getGenericReturnType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Accessor getAccessor() {
    if (accessor == null) {
      accessor = new MethodAttributeAccessor(setter);
    }
    return accessor;
  }

}
