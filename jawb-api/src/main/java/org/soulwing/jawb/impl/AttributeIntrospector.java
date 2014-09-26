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

import java.lang.annotation.Annotation;

/**
 * An introspector for an attribute of a bound class.
 *
 * @author Carl Harris
 */
public interface AttributeIntrospector {

  /**
   * Gets the bean introspector that created this attribute introspector.
   * @return parent introspector
   */
  BeanIntrospector getParent();
  
  /**
   * Gets the name of the subject attribute.
   * @return attribute name
   */
  String getName();
  
  /**
   * Gets the subject attribute's data type
   * @return data type
   */
  Class<?> getType();
  
  /**
   * Gets the class that declares the attribute described by the receiver.
   * @return class
   */
  Class<?> getDeclaringClass();
  
  /**
   * Gets the sub-type for an attribute of an array or collection type.
   * @return sub-type
   * @throws UnsupportedOperationException if the attribute is neither 
   *    an array nor a collection
   */
  Class<?> getSubType();

  /**
   * Gets the given annotation from the subject attribute definition.
   * @param annotationClass the attribute to retrieve
   * @return annotation or {@code null} if no such annotation exists on
   *    the subject attribute's definition
   */
  <T extends Annotation> T getAnnotation(Class<T> annotationClass);
  
  /**
   * Tests whether the subject attribute is an array type.
   * @return {@code true} if the subject attribute is declared as an array
   */
  boolean isArrayType();
  
  /**
   * Tests whether the subject attribute is a collection type.
   * @return {@code true} if the subject attriubute is declared as a
   *    collection
   */
  boolean isCollectionType();
  
  /**
   * Tests whether the subject attribute type is abstract.
   * @return {@code true} if the type is an interface or abstract class
   */
  boolean isAbstractType();
  
  /**
   * Gets an accessor for the subject attribute.
   * @return accessor instance
   */
  Accessor getAccessor();

  /**
   * Gets the sheet reference associated with this attribute.
   * @return sheet reference
   */
  String getSheetReference();
  
}
