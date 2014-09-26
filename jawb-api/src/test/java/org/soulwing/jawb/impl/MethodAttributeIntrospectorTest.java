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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.soulwing.jawb.annotation.Sheet;
import org.soulwing.jawb.impl.AttributeIntrospector;
import org.soulwing.jawb.impl.BeanClassIntrospector;
import org.soulwing.jawb.impl.MethodAttributeIntrospector;

/**
 * Unit tests for {@link MethodAttributeIntrospector}.
 *
 * @author Carl Harris
 */
public class MethodAttributeIntrospectorTest 
    extends AbstractAttributeIntrospectorTest {

  protected AttributeIntrospector createIntrospector(String attribute)
      throws NoSuchMethodException {
    StringBuilder sb = new StringBuilder();
    sb.append("get");
    sb.append(Character.toUpperCase(attribute.charAt(0)));
    sb.append(attribute.substring(1));
    String methodName = sb.toString();
    Method getter = Bean.class.getMethod(methodName);
    Method setter = Bean.class.getMethod(methodName.replaceFirst("get", "set"),
        getter.getReturnType());
    return new MethodAttributeIntrospector(
        new BeanClassIntrospector(Bean.class), attribute, getter, setter);
  }
  
  @Sheet(DEFAULT_SHEET_REFERENCE)
  public interface Bean {
    public Object getSimpleAttribute();
    public void setSimpleAttribute(Object attribute);
    public Object[] getArrayAttribute();
    public void setArrayAttribute(Object[] attribute);
    public Collection<Object> getCollectionAttribute();
    public void setCollectionAttribute(Collection<Object> attribute);
    public ArrayList<Object> getConcreteCollectionAttribute();
    public void setConcreteCollectionAttribute(ArrayList<Object> attribute);
    @Sheet(SHEET_REFERENCE)
    public Object getAnnotatedAttribute();
    public void setAnnotatedAttribute(Object attribute);
  }

}
