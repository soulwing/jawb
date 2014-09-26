/*
 * File created on Dec 20, 2013 
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
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.soulwing.jawb.impl.BeanEvaluator;
import org.soulwing.jawb.impl.Binding;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link BeanEvaluator}.
 *
 * @author Carl Harris
 */
public class BeanEvaluatorTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);

  private Binding binding = mockery.mock(Binding.class);
  
  private BeanEvaluator evaluator = new BeanEvaluator(MockBean.class);
  
  @Before
  public void setUp() throws Exception {
    evaluator.addBinding(binding);
  }
  
  @Test
  public void testEvaluate() throws Exception {
    mockery.checking(new Expectations() { {
      oneOf(binding).bind(with(same(workbook)), with(any(MockBean.class)));
    } });
    
    Object parent = new Object();
    evaluator.setParent(parent);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBean.class));
    MockBean bean = (MockBean) obj;
    assertThat(bean.beforeInvoked, equalTo(true));
    assertThat(bean.afterInvoked, equalTo(true));
    assertThat(bean.parent, sameInstance(parent));
  }
  
  public static class MockBean {
    
    private boolean beforeInvoked;
    private boolean afterInvoked;
    private Object parent;
    
    void beforeExtract(Object parent) {
      beforeInvoked = true;
      this.parent = parent;
    }
    
    void afterExtract(Object parent) {
      afterInvoked = true;
      assertThat(parent, sameInstance(this.parent));
    }
  }
  
}
