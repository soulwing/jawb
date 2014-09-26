/*
 * File created on Dec 22, 2013 
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

import java.util.ArrayList;
import java.util.Collection;

import org.soulwing.jawb.annotation.Sheet;
import org.soulwing.jawb.impl.AttributeIntrospector;
import org.soulwing.jawb.impl.BeanClassIntrospector;
import org.soulwing.jawb.impl.FieldAttributeIntrospector;

/**
 * Unit tests for {@link FieldAttributeIntrospector}.
 *
 * @author Carl Harris
 */
public class FieldAttributeIntrospectorTest 
    extends AbstractAttributeIntrospectorTest {

  protected AttributeIntrospector createIntrospector(String fieldName)
      throws NoSuchFieldException {
    return new FieldAttributeIntrospector(
        new BeanClassIntrospector(Bean.class), Bean.class.getField(fieldName));
  }
  
  @Sheet(DEFAULT_SHEET_REFERENCE)
  public static class Bean {
    public Object simpleAttribute;
    public Object[] arrayAttribute;
    public Collection<Object> collectionAttribute;
    public ArrayList<Object> concreteCollectionAttribute;
    @Sheet(SHEET_REFERENCE)
    public Object annotatedAttribute;
  }

}
