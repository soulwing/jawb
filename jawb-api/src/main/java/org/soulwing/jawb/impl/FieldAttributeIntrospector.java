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
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * An {@link AttributeIntrospector} for a stringValue.
 *
 * @author Carl Harris
 */
class FieldAttributeIntrospector 
    extends AbstractAttributeIntrospector {

  private final Field field;
  private Accessor accessor;
  
  /**
   * Constructs a new instance.
   * @param parent parent bean introspector
   * @param field field delegate
   */
  public FieldAttributeIntrospector(BeanIntrospector parent,
      Field field) {
    super(parent);
    this.field = field;
    this.accessor = new FieldAttributeAccessor(field);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return field.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return field.getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getDeclaringClass() {
    return field.getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    return field.getAnnotation(annotationClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getGenericType() {
    return field.getGenericType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Accessor getAccessor() {
    if (accessor == null) {
      accessor = new FieldAttributeAccessor(field);
    }
    return accessor;
  }
  
}
