/*
 * File created on Dec 20, 2013 
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
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.annotation.Bound;

/**
 * Unit tests for {@link BeanTypeBindingStrategy}.
 *
 * @author Carl Harris
 */
public class BeanTypeBindingStrategyTest {
  
  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector = 
      mockery.mock(AttributeIntrospector.class);
  
  private BeanIntrospector beanIntrospector =
      mockery.mock(BeanIntrospector.class);
  
  private EvaluatorFactory evaluatorFactory =
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithAnnotatedBeanType() throws Exception {
    final Bound bound = mockery.mock(Bound.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).getAnnotation(Bound.class);
      will(returnValue(bound));
      oneOf(introspector).getType();
      will(returnValue(MockBean.class));
      oneOf(introspector).getAccessor();
      oneOf(evaluatorFactory).createBeanIntrospector(
          with(introspector), with(MockBean.class));
      will(returnValue(beanIntrospector));
      oneOf(evaluatorFactory).createBeanEvaluator(beanIntrospector);
    } });
    
    Binding binding = BeanTypeBindingStrategy.INSTANCE.createBinding(
        introspector, evaluatorFactory);    
    mockery.assertIsSatisfied();    
    assertThat(binding, instanceOf(BaseBinding.class));
  }

  @Test
  public void testWithNoAnnotation() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).getAnnotation(Bound.class);
      will(returnValue(null));
    } });
    
    Binding binding = BeanTypeBindingStrategy.INSTANCE.createBinding(
        introspector, evaluatorFactory);    
    mockery.assertIsSatisfied();    
    assertThat(binding, nullValue());
  }

  public static class MockBean {
    
    public MockBean beanValue;
    
  }
  
}
