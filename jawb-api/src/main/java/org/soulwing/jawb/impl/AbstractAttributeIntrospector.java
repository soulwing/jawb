/*
 * File created on Dec 18, 2013 
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
package org.soulwing.jawb.impl;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.soulwing.jawb.annotation.Sheet;

/**
 * A abstract base for {@link AttributeIntrospector} implementations.
 *
 * @author Carl Harris
 */
abstract class AbstractAttributeIntrospector 
    implements AttributeIntrospector {

  private final BeanIntrospector parent;
    
  /**
   * Constructs a new instance.
   * @param parent parent bean introspector
   */
  protected AbstractAttributeIntrospector(BeanIntrospector parent) {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BeanIntrospector getParent() {
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getSubType() {
    if (isCollectionType()) {
      ParameterizedType type = (ParameterizedType) getGenericType();
      return (Class<?>) type.getActualTypeArguments()[0];
    }
    if (isArrayType()) {
      return getType().getComponentType();
    }
    throw new UnsupportedOperationException(
        "only arrays and collections have a sub-type");
  }

  /**
   * Gets the receiver's generic type specification.
   * @return generic type
   */
  protected abstract Type getGenericType();

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isArrayType() {
    return getType().isArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollectionType() {
    return Collection.class.isAssignableFrom(getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAbstractType() {
    return getType().isInterface() 
        || (getType().getModifiers() & Modifier.ABSTRACT) != 0;
  }

  /**
   * {@inheritDoc}
   */
  public String getSheetReference() {
    Sheet sheet = getAnnotation(Sheet.class);
    if (sheet != null) {
      return sheet.value();
    }
    return parent.getSheetReference();
  }

}
