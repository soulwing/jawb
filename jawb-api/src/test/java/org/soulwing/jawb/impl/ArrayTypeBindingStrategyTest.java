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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.impl.ArrayTypeBindingStrategy;
import org.soulwing.jawb.impl.AttributeIntrospector;
import org.soulwing.jawb.impl.BeanIntrospector;
import org.soulwing.jawb.impl.Binding;
import org.soulwing.jawb.impl.ColumnIterator;
import org.soulwing.jawb.impl.EvaluatorFactory;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

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
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    final Evaluator elementEvaluator = mockery.mock(Evaluator.class);
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
      allowing(introspector).getSubType();
      will(returnValue(Object.class));
      allowing(introspector).getType();
      will(returnValue(Object[].class));
      oneOf(evaluatorFactory).createBeanIntrospector(introspector, 
          Object.class);
      will(returnValue(beanIntrospector));
      oneOf(evaluatorFactory).createBeanEvaluator(beanIntrospector);
      will(returnValue(elementEvaluator));
      oneOf(introspector).getAccessor();
      oneOf(evaluatorFactory).createColumnIterator(columns);
      will(returnValue(iterator));
      oneOf(introspector).getSheetReference();
      will(returnValue(null));
      oneOf(evaluatorFactory).createArrayEvaluator(Object.class, 
          elementEvaluator, iterator);
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
