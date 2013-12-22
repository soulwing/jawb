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
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.annotation.IterateColumns;
import edu.vt.ras.jawb.annotation.IterateRows;
import edu.vt.ras.jawb.annotation.IterateSheets;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link WorksheetIteratorUtil}.
 *
 * @author Carl Harris
 */
public class WorksheetIteratorUtilTest {

  private Mockery mockery = new Mockery();
  
  private EvaluatorFactory evaluatorFactory = 
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithIterateSheets() throws Exception {
    final WorkbookIterator iterator = new SheetIterator(1, -1, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createSheetIterator(1, -1);
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        null, MockBean.class.getField("sheetAnnotated"));
    WorkbookIterator result = WorkbookIteratorUtil.createIteratorForAttribute(
        attribute, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(iterator));    
  }
  
  @Test
  public void testWithIterateRows() throws Exception {
    final WorkbookIterator iterator = new RowIterator(1, -1, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createRowIterator(1, -1);
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        null, MockBean.class.getField("rowAnnotated"));
    WorkbookIterator result = WorkbookIteratorUtil.createIteratorForAttribute(
        attribute, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(iterator));    
  }
  
  @Test
  public void testWithIterateColumns() throws Exception {
    final WorkbookIterator iterator = new RowIterator(1, -1, null);
    mockery.checking(new Expectations() { { 
      oneOf(evaluatorFactory).createColumnIterator(1, -1);
      will(returnValue(iterator));
    } });
    
    FieldAttributeIntrospector attribute = new FieldAttributeIntrospector(
        null, MockBean.class.getField("columnAnnotated"));
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
