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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.annotation.Cell;

/**
 * Unit tests for {@link SimpleTypeBindingStrategy}.
 *
 * @author Carl Harris
 */
public class SimpleTypeBindingStrategyTest {

  private static final String CELL_REFERENCE = "CELL_REFERENCE";
  
  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector = 
      mockery.mock(AttributeIntrospector.class);
  
  private EvaluatorFactory evaluatorFactory =
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithAnnotatedSupportedType() throws Exception {
    final Cell cell = getCellAnnotation();
    mockery.checking(new Expectations() { { 
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      atLeast(1).of(introspector).getType();
      will(returnValue(String.class));
      oneOf(introspector).getAccessor();
      oneOf(introspector).getSheetReference();
      will(returnValue("sheetRef"));
      oneOf(evaluatorFactory).createCellEvaluator(with("sheetRef"),
          with(CELL_REFERENCE),
          with(String.class));
    } });
    
    Binding binding = SimpleTypeBindingStrategy.INSTANCE.createBinding(
        introspector, evaluatorFactory);    
    mockery.assertIsSatisfied();    
    assertThat(binding, instanceOf(BaseBinding.class));
  }

  @Test
  public void testWithNoAnnotation() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(null));
      oneOf(introspector).getType();
      will(returnValue(String.class));
    } });
    
    Binding binding = SimpleTypeBindingStrategy.INSTANCE.createBinding(
        introspector, evaluatorFactory);    
    mockery.assertIsSatisfied();    
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithUnsupportedType() throws Exception {
    final Cell cell = getCellAnnotation();
    mockery.checking(new Expectations() { { 
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      oneOf(introspector).getType();
      will(returnValue(Object.class));
      oneOf(introspector).getName();
      will(returnValue("someAttribute"));
      oneOf(introspector).getDeclaringClass();
      will(returnValue(MockBean.class));
    } });

    try {
      SimpleTypeBindingStrategy.INSTANCE.createBinding(introspector, 
          evaluatorFactory);
      fail("expected WorkbookBindingException");      
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
      assertThat(ex.getMessage().contains("someAttribute"), equalTo(true));
      assertThat(ex.getMessage().contains(MockBean.class.getName()), 
          equalTo(true));
    } 
  }
  

  private Cell getCellAnnotation() throws Exception {
    return MockBean.class.getField("stringValue").getAnnotation(Cell.class);
  }
  
  public static class MockBean {
    
    @Cell(CELL_REFERENCE)
    public String stringValue;
    
  }
}
