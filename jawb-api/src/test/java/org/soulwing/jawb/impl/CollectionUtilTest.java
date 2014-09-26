/*
 * File created on Dec 21, 2013 
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.AttributeIntrospector;
import org.soulwing.jawb.impl.CollectionUtil;

/**
 * Unit tests for {@link CollectionUtil}.
 *
 * @author Carl Harris
 */
public class CollectionUtilTest {

  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector =
      mockery.mock(AttributeIntrospector.class);
  

  @Test
  public void testGetCollectionTypeWithAbstractListType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isAbstractType();
      will(returnValue(true));
      oneOf(introspector).getType();
      will(returnValue(List.class));
    } });
    
    Object collectionClass =
        CollectionUtil.getCollectionType(introspector);
    
    mockery.assertIsSatisfied();
    assertThat(collectionClass, equalTo((Object) ArrayList.class));
  }

  @Test
  public void testGetCollectionTypeWithAbstractCollectionType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isAbstractType();
      will(returnValue(true));
      oneOf(introspector).getType();
      will(returnValue(Collection.class));
    } });
    
    Object collectionClass =
        CollectionUtil.getCollectionType(introspector);
        
    mockery.assertIsSatisfied();
    assertThat(collectionClass, equalTo((Object) LinkedHashSet.class));
  }

  @Test
  public void testGetCollectionTypeWithConcreteType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isAbstractType();
      will(returnValue(true));
      oneOf(introspector).getType();
      will(returnValue(ArrayList.class));
    } });
    
    Object collectionClass =
        CollectionUtil.getCollectionType(introspector);
        
    mockery.assertIsSatisfied();
    assertThat(collectionClass, equalTo((Object) ArrayList.class));
  }

}
