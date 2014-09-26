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
package org.soulwing.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.annotation.Bound;
import org.soulwing.jawb.annotation.Cell;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.impl.AnnotationEvaluatorFactory;
import org.soulwing.jawb.impl.BeanIntrospector;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookBindingProvider;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link AnnotationEvaluatorFactory}.
 *
 * @author Carl Harris
 */
public class AnnotationEvaluatorFactoryTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private BoundCell value = mockery.mock(BoundCell.class);
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);

  private WorkbookBindingProvider provider = 
      mockery.mock(WorkbookBindingProvider.class);
  
  private AnnotationEvaluatorFactory introspector = 
      new AnnotationEvaluatorFactory(provider);
  
  @Test
  public void testCreateEvaluatorWithAnnotatedField() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));   
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithFieldAnnotation.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithFieldAnnotation.class));
    MockBeanWithFieldAnnotation bean = (MockBeanWithFieldAnnotation) obj;
    assertThat(bean.stringValue, sameInstance(result));
  }

  @Test
  public void testCreateEvaluatorWithAnnotatedMethod() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithMethodAnnotation.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithMethodAnnotation.class));
    MockBeanWithMethodAnnotation bean = (MockBeanWithMethodAnnotation) obj;
    assertThat(bean.stringValue, sameInstance(result));
  }

  @Test
  public void testCreateEvaluatorForComposedBean() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithComposedBean.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();    
    assertThat(obj, instanceOf(MockBeanWithComposedBean.class));
    MockBeanWithComposedBean bean = (MockBeanWithComposedBean) obj;
    assertThat(bean.beanValue.stringValue, sameInstance(result));
  }

  @Test
  public void testCreateEvaluatorForArrayOfSimpleType() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    mockery.checking(new Expectations() { { 
      oneOf(workbook).pushIterator(with(any(WorkbookIterator.class)));
      oneOf(workbook).popIterator(with(any(WorkbookIterator.class)));
    } });
    
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithArrayOfSimpleType.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);

    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithArrayOfSimpleType.class));
    MockBeanWithArrayOfSimpleType bean = (MockBeanWithArrayOfSimpleType) obj;
    assertThat(bean.strings[0], sameInstance(result));
   
  }

  @Test
  public void testCreateEvaluatorForCollectionOfSimpleType() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    mockery.checking(new Expectations() { { 
      oneOf(workbook).pushIterator(with(any(WorkbookIterator.class)));
      oneOf(workbook).popIterator(with(any(WorkbookIterator.class)));
    } });
    
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithCollectionOfSimpleType.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);

    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithCollectionOfSimpleType.class));
    MockBeanWithCollectionOfSimpleType bean = (MockBeanWithCollectionOfSimpleType) obj;
    assertThat(bean.strings.get(0), sameInstance(result));
  }

  @Test
  public void testCreateEvaluatorForArrayOfBeanType() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    mockery.checking(new Expectations() { { 
      oneOf(workbook).pushIterator(with(any(WorkbookIterator.class)));
      oneOf(workbook).popIterator(with(any(WorkbookIterator.class)));
    } });
    
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithArrayOfBeanType.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);

    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithArrayOfBeanType.class));
    MockBeanWithArrayOfBeanType bean = (MockBeanWithArrayOfBeanType) obj;
    assertThat(bean.beans[0].stringValue, sameInstance(result));
  }

  @Test
  public void testCreateEvaluatorForCollectionOfBeanType() throws Exception {
    final String result = new String();
    mockery.checking(stringValueExpectations(result));
    mockery.checking(new Expectations() { { 
      oneOf(workbook).pushIterator(with(any(WorkbookIterator.class)));
      oneOf(workbook).popIterator(with(any(WorkbookIterator.class)));
    } });
    
    BeanIntrospector boundClass = introspector.createBeanIntrospector(
        null, MockBeanWithCollectionOfBeanType.class);
    Evaluator evaluator = introspector.createBeanEvaluator(boundClass);

    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, instanceOf(MockBeanWithCollectionOfBeanType.class));
    MockBeanWithCollectionOfBeanType bean = 
        (MockBeanWithCollectionOfBeanType) obj;
    assertThat(bean.beans.get(0).stringValue, sameInstance(result));
  }


  private Expectations stringValueExpectations(final String result) {
    return new Expectations() { {
      oneOf(value).isBlank();
      will(returnValue(false));
      exactly(1).of(provider).createCellReference(
          with(nullValue(String.class)), with(any(String.class)));
      will(returnValue(ref));
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(value));
      oneOf(value).getValue();
      will(returnValue(result));
    } };
  }

  public static class MockBeanWithFieldAnnotation {
    
    @Cell("DONTCARE")
    private String stringValue;
    
  }

  public static class MockBeanWithMethodAnnotation {
    
    private String stringValue;
  
    @Cell("DONTCARE")
    public String getStringValue() {
      return stringValue;
    }
    
    public void setStringValue(String stringValue) {
      this.stringValue = stringValue;
    }
  }

  public static class MockBeanWithComposedBean {
    
    @Bound
    private MockBeanWithFieldAnnotation beanValue;
    
  }
  
  public static class MockBeanWithArrayOfSimpleType {
    
    @Cell("DONTCARE")
    @IterateRows(count = 1)
    private String[] strings;
    
  }

  public static class MockBeanWithCollectionOfSimpleType {
    
    @Cell("DONTCARE")
    @IterateRows(count = 1)
    private List<String> strings;
    
  }
  

  public static class MockBeanWithArrayOfBeanType {
    
    @IterateRows(count = 1)
    private MockBeanWithFieldAnnotation[] beans;
    
  }

  public static class MockBeanWithCollectionOfBeanType {
    
    @IterateRows(count = 1)
    private List<MockBeanWithFieldAnnotation> beans;
    
  }


}
