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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Iterator;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.annotation.Sheet;

/**
 * Unit tests for {@link BeanClassIntrospector}.
 *
 * @author Carl Harris
 */
public class BeanClassIntrospectorTest {

  private static final String SHEET_REFERENCE = "sheet";    

  private static final String OVERRIDE_SHEET_REFERENCE = "override";    

  @Test
  public void testGetAttributes() throws Exception {
    BeanClassIntrospector introspector = 
        new BeanClassIntrospector(Object.class);
    Set<AttributeIntrospector> attributes = introspector.getAttributes();
    assertThat(attributes, not(nullValue()));
    assertThat(introspector.getAttributes(), sameInstance(attributes));
  }
  
  @Test
  public void testGetAttributesBeanWithFields() throws Exception {
    BeanClassIntrospector introspector = 
        new BeanClassIntrospector(BeanWithFields.class);
    Set<AttributeIntrospector> attributes = introspector.getAttributes();
    AttributeIntrospector publicField = findField("publicField", attributes);
    assertThat(publicField, not(nullValue()));
    assertThat(publicField, instanceOf(FieldAttributeIntrospector.class));
    AttributeIntrospector privateField = findField("privateField", attributes);
    assertThat(privateField, not(nullValue()));
    assertThat(privateField, instanceOf(FieldAttributeIntrospector.class));
  }

  @Test
  public void testGetAttributesBeanWithMethods() throws Exception {
    BeanClassIntrospector introspector = 
        new BeanClassIntrospector(BeanWithMethods.class);
    Set<AttributeIntrospector> attributes = introspector.getAttributes();
    AttributeIntrospector publicField = findField("publicField", attributes);
    assertThat(publicField, not(nullValue()));
    assertThat(publicField, instanceOf(MethodAttributeIntrospector.class));
    AttributeIntrospector protectedField = 
        findField("protectedField", attributes);
    assertThat(protectedField, nullValue());
  }

  private AttributeIntrospector findField(String name, 
      Set<AttributeIntrospector> attributes) {
    Iterator<AttributeIntrospector> i = attributes.iterator();
    while (i.hasNext()) {
      AttributeIntrospector attribute = i.next();
      if (attribute.getName().equals(name)) {
        return attribute;
      }
    }
    return null;
  }
  
  @Test
  public void testGetSheetReferenceNoAnnotation() throws Exception {
    BeanClassIntrospector introspector =
        new BeanClassIntrospector(BeanWithoutSheet.class);
    assertThat(introspector.getSheetReference(), nullValue());
  }

  @Test
  public void testGetSheetReferenceFromAnnotation() throws Exception {
    BeanClassIntrospector introspector =
        new BeanClassIntrospector(BeanWithSheet.class);
    assertThat(introspector.getSheetReference(), equalTo(SHEET_REFERENCE));
  }

  @Test
  public void testGetSheetReferenceInheritFromParentBean() throws Exception {
    Mockery mockery = new Mockery();
    
    final AttributeIntrospector parentAttribute = 
        mockery.mock(AttributeIntrospector.class);
    final BeanClassIntrospector parentBean =
        new BeanClassIntrospector(BeanWithSheet.class);
    
    mockery.checking(new Expectations() { { 
      oneOf(parentAttribute).getAnnotation(Sheet.class);
      will(returnValue(null));
      oneOf(parentAttribute).getParent();
      will(returnValue(parentBean));
    } });
    
    BeanClassIntrospector introspector = 
        new BeanClassIntrospector(parentAttribute, 
            BeanWithoutSheet.class);
    
    assertThat(introspector.getSheetReference(), equalTo(SHEET_REFERENCE));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testGetSheetReferenceOverrideFromAttribute() throws Exception {
    Mockery mockery = new Mockery();
    
    final AttributeIntrospector parentAttribute = 
        mockery.mock(AttributeIntrospector.class);
    final Sheet sheet = mockery.mock(Sheet.class);
    mockery.checking(new Expectations() { { 
      atLeast(1).of(parentAttribute).getAnnotation(Sheet.class);
      will(returnValue(sheet));
      oneOf(sheet).value();
      will(returnValue(OVERRIDE_SHEET_REFERENCE));
    } });
    
    BeanClassIntrospector introspector = 
        new BeanClassIntrospector(parentAttribute, 
            BeanWithoutSheet.class);
    
    assertThat(introspector.getSheetReference(), equalTo(OVERRIDE_SHEET_REFERENCE));
    mockery.assertIsSatisfied();
  }

  @SuppressWarnings("unused")
  public static class BeanWithFields {
    public String publicField;
    private String privateField;
  }
  
  public static class BeanWithMethods {
    public String getPublicField() {
      return null;
    }
    protected String getProtectedField() {
      return null;
    }
  }
  
  public static class BeanWithoutSheet {
  }
  
  @Sheet(SHEET_REFERENCE)
  public static class BeanWithSheet {
  }
  
  
}
