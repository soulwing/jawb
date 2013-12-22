/*
 * File created on Dec 22, 2013 
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

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.annotation.Sheet;

/**
 * A {@link BeanIntrospector} implmented using the Reflection API.
 *
 * @author Carl Harris
 */
class BeanClassIntrospector implements BeanIntrospector {

  private final AttributeIntrospector parent;
  private final Class<?> type;
  private Set<AttributeIntrospector> attributes;

  /**
   * Constructs a new instance.
   * @param type bean type to introspect
   */
  public BeanClassIntrospector(Class<?> type) {
    this(null, type);
  }

  /**
   * Constructs a new instance.
   * @param parent parent introspector
   * @param type bean type to introspect
   */
  public BeanClassIntrospector(AttributeIntrospector parent, Class<?> type) {
    this.parent = parent;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeIntrospector getParent() {
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<AttributeIntrospector> getAttributes() 
      throws WorkbookBindingException {
    if (attributes == null) {
      attributes = new LinkedHashSet<AttributeIntrospector>();
      for (Field field : type.getDeclaredFields()) {
        FieldAttributeIntrospector introspector = 
            new FieldAttributeIntrospector(this, field);
        attributes.add(introspector);
      }
      try {
        PropertyDescriptor[] properties = 
            Introspector.getBeanInfo(type).getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
          MethodAttributeIntrospector introspector = 
              new MethodAttributeIntrospector(this,
                  property.getName(), 
                  property.getReadMethod(), 
                  property.getWriteMethod());
          attributes.add(introspector);
        }  
      }
      catch (IntrospectionException ex) {
        throw new WorkbookBindingException(ex);
      }
    }
    return attributes;
  }

  /**
   * Gets the {@code sheetReference} property.
   * @return
   */
  public String getSheetReference() {
    if (parent != null) {
      Sheet parentSheet = parent.getAnnotation(Sheet.class);
      if (parentSheet != null) {
        return parentSheet.value();
      }
    }
    Sheet sheet = type.getAnnotation(Sheet.class);
    if (sheet != null) {
      return sheet.value();
    }
    if (parent != null) {
      return parent.getParent().getSheetReference();
    }
    return null;
  }

}
