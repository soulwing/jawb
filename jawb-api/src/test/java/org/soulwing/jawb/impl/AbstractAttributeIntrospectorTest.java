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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.soulwing.jawb.impl.AttributeIntrospector;

/**
 * An abstract base for {@link AttributeIntrospector} tests.
 *
 * @author Carl Harris
 */
public abstract class AbstractAttributeIntrospectorTest {

  public static final String SHEET_REFERENCE = "sheet";

  public static final String DEFAULT_SHEET_REFERENCE = "default";

  protected abstract AttributeIntrospector createIntrospector(
      String attribute) throws Exception;

  @Test
  public void testSimpleAttribute() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("simpleAttribute");
    assertThat(introspector.getName(), equalTo("simpleAttribute"));
    assertThat(introspector.getType(), equalTo((Object) Object.class));
    assertThat(introspector.isArrayType(), equalTo(false));
    assertThat(introspector.isCollectionType(), equalTo(false));
    try {
      introspector.getSubType();
      fail("expected UnsupportedOperationException");
    }
    catch (UnsupportedOperationException ex) {
      assertThat(true, equalTo(true));
    }
  }

  @Test
  public void testArrayAttribute() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("arrayAttribute");
    assertThat(introspector.getName(), equalTo("arrayAttribute"));
    assertThat(introspector.getType(), equalTo((Object) Object[].class));
    assertThat(introspector.getSubType(), equalTo((Object) Object.class));
    assertThat(introspector.isArrayType(), equalTo(true));
    assertThat(introspector.isCollectionType(), equalTo(false));
  }

  @Test
  public void testCollectionAttribute() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("collectionAttribute");
    assertThat(introspector.getName(), equalTo("collectionAttribute"));
    assertThat(introspector.getType(), equalTo((Object) Collection.class));
    assertThat(introspector.getSubType(), equalTo((Object) Object.class));
    assertThat(introspector.isArrayType(), equalTo(false));
    assertThat(introspector.isCollectionType(), equalTo(true));
    assertThat(introspector.isAbstractType(), equalTo(true));
  }

  @Test
  public void testConcreteCollectionAttribute() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("concreteCollectionAttribute");
    assertThat(introspector.getName(), equalTo("concreteCollectionAttribute"));
    assertThat(introspector.getType(), equalTo((Object) ArrayList.class));
    assertThat(introspector.getSubType(), equalTo((Object) Object.class));
    assertThat(introspector.isArrayType(), equalTo(false));
    assertThat(introspector.isCollectionType(), equalTo(true));
    assertThat(introspector.isAbstractType(), equalTo(false));
  }

  @Test
  public void testGetSheetReferenceFromAttribute() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("annotatedAttribute");
    assertThat(introspector.getSheetReference(), equalTo(SHEET_REFERENCE));
  }

  @Test
  public void testGetSheetReferenceFromBean() throws Exception {
    AttributeIntrospector introspector = 
        createIntrospector("simpleAttribute");
    assertThat(introspector.getSheetReference(), 
        equalTo(DEFAULT_SHEET_REFERENCE));
  }
  
}
