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
package edu.vt.ras.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.annotation.IterateColumns;
import edu.vt.ras.jawb.annotation.IterateRows;
import edu.vt.ras.jawb.annotation.IterateSheets;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link ArrayTypeBindingStrategy}.
 *
 * @author Carl Harris
 */
public class ArrayTypeBindingStrategyTest {

  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector =
      mockery.mock(AttributeIntrospector.class);
  
  private BeanIntrospector beanIntrospector = 
      mockery.mock(BeanIntrospector.class);
  
  private EvaluatorFactory evaluatorFactory = 
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithAnnotatedArrayType() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(1, 1, null);
    final IterateColumns columns = mockery.mock(IterateColumns.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(columns));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
      oneOf(columns).count();
      will(returnValue(1));
      oneOf(columns).increment();
      will(returnValue(1));
      allowing(introspector).getSubType();
      will(returnValue(Object.class));
      allowing(introspector).getType();
      will(returnValue(Object[].class));
      oneOf(evaluatorFactory).createBeanIntrospector(introspector, 
          Object.class);
      will(returnValue(beanIntrospector));
      oneOf(evaluatorFactory).createEvaluator(beanIntrospector);
      oneOf(introspector).getAccessor();
      oneOf(evaluatorFactory).createColumnIterator(1, 1);
      will(returnValue(iterator));
    } });
    
    Binding binding = ArrayTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, not(nullValue()));
  }

  @Test
  public void testWithNonArrayType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(false));
    } });
    
    Binding binding = ArrayTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithNonAnnotatedArrayType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isArrayType();
      will(returnValue(true));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
    } });
    
    Binding binding = ArrayTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

}
