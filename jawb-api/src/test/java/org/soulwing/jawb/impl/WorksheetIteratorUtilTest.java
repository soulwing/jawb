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
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.impl.BeanIntrospector;
import org.soulwing.jawb.impl.ColumnIterator;
import org.soulwing.jawb.impl.EvaluatorFactory;
import org.soulwing.jawb.impl.FieldAttributeIntrospector;
import org.soulwing.jawb.impl.RowIterator;
import org.soulwing.jawb.impl.SheetIterator;
import org.soulwing.jawb.impl.WorkbookIteratorUtil;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link WorksheetIteratorUtil}.
 *
 * @author Carl Harris
 */
public class WorksheetIteratorUtilTest {

  private Mockery mockery = new Mockery();
  
  private EvaluatorFactory evaluatorFactory = 
      mockery.mock(EvaluatorFactory.class);
  
  private BeanIntrospector parent = mockery.mock(BeanIntrospector.class);
  
  @Before
  public void setUp() throws Exception {
    mockery.checking(new Expectations() { {
      oneOf(parent).getSheetReference();
      will(returnValue(null));
    } });    
  }
  
  @Test
  public void testWithIterateSheets() throws Exception {
    final WorkbookIterator iterator = new SheetIterator(0, 0, null, null, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createSheetIterator(with(any(IterateSheets.class)));
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        parent, MockBean.class.getField("sheetAnnotated"));
    WorkbookIterator result = WorkbookIteratorUtil.createIteratorForAttribute(
        attribute, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(iterator));    
  }
  
  @Test
  public void testWithIterateRows() throws Exception {
    final WorkbookIterator iterator = new RowIterator(0, 0, null, null, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createRowIterator(with(any(IterateRows.class)));
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        parent, MockBean.class.getField("rowAnnotated"));
    WorkbookIterator result = WorkbookIteratorUtil.createIteratorForAttribute(
        attribute, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(iterator));    
  }
  
  @Test
  public void testWithIterateColumns() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createColumnIterator(with(any(IterateColumns.class)));
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        parent, MockBean.class.getField("columnAnnotated"));
    WorkbookIterator result = WorkbookIteratorUtil.createIteratorForAttribute(
        attribute, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(iterator));    
  }
  
  
  public static class MockBean {
    
    @IterateSheets(count = 1, increment = -1)
    public Object[] sheetAnnotated;

    @IterateRows(count = 1, increment = -1)
    public Object[] rowAnnotated;

    @IterateColumns(count = 1, increment = -1)
    public Object[] columnAnnotated;

  }
  
}
