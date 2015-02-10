/*
 * File created on Dec 21, 2013 
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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.Cell;
import org.soulwing.jawb.annotation.CellFormat;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.IterateSheets;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link CollectionOfSimpleTypeBindingStrategy}.
 *
 * @author Carl Harris
 */
public class CollectionOfSimpleTypeBindingStrategyTest {

  private Mockery mockery = new Mockery();
  
  private AttributeIntrospector introspector =
      mockery.mock(AttributeIntrospector.class);
  
  private EvaluatorFactory evaluatorFactory = 
      mockery.mock(EvaluatorFactory.class);
  
  @Test
  public void testWithAnnotatedCollectionOfSupportedType() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    final Evaluator elementEvaluator = mockery.mock(Evaluator.class);
    final Cell cell = mockery.mock(Cell.class);
    final IterateColumns columns = mockery.mock(IterateColumns.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(true));
      atLeast(1).of(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      oneOf(introspector).getAnnotation(CellFormat.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(columns));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
      oneOf(cell).value();
      will(returnValue("ref"));
      atLeast(1).of(introspector).getSheetReference();
      will(returnValue("sheetRef"));
      oneOf(evaluatorFactory).createCellEvaluator("sheetRef", "ref", String.class, null);
      will(returnValue(elementEvaluator));
      allowing(introspector).isAbstractType();
      will(returnValue(true));
      allowing(introspector).getType();
      will(returnValue(List.class));
      oneOf(introspector).getAccessor();
      oneOf(evaluatorFactory).createColumnIterator(columns);
      will(returnValue(iterator));
      oneOf(evaluatorFactory).createCollectionEvaluator(ArrayList.class,
          elementEvaluator, iterator);
    } });
    
    Binding binding = CollectionOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, not(nullValue()));
  }

  @Test
  public void testWithNonCollectionType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(false));
      allowing(introspector).getSubType();
      will(returnValue(String.class));
    } });
    
    Binding binding = CollectionOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithCollectionOfUnsupportedType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(Object.class));
    } });
    
    Binding binding = CollectionOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithNonAnnotatedCollectionOfSupportedType() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
    } });
    
    Binding binding = CollectionOfSimpleTypeBindingStrategy.INSTANCE
        .createBinding(introspector, evaluatorFactory);
    mockery.assertIsSatisfied();
    assertThat(binding, nullValue());
  }

  @Test
  public void testWithCollectionOfSupportedTypeMissingCell() throws Exception {
    final WorkbookIterator iterator = new ColumnIterator(0, 0, null, null, null);
    final IterateColumns columns = mockery.mock(IterateColumns.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(columns));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
      allowing(columns).count();
      will(returnValue(1));
      allowing(columns).increment();
      will(returnValue(1));
      allowing(introspector).isAbstractType();
      will(returnValue(true));
      oneOf(evaluatorFactory).createColumnIterator(columns);
      will(returnValue(iterator));
      oneOf(introspector).getSheetReference();
      will(returnValue(null));
    } });
    
    try {
      CollectionOfSimpleTypeBindingStrategy.INSTANCE
          .createBinding(introspector, evaluatorFactory);
      fail("expected WorkbookBindingException");
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
    }
  }

  @Test
  public void testWithCollectionOfSupportedTypeMissingIteration() throws Exception {
    final Cell cell = mockery.mock(Cell.class);
    mockery.checking(new Expectations() { { 
      oneOf(introspector).isCollectionType();
      will(returnValue(true));
      oneOf(introspector).getSubType();
      will(returnValue(String.class));
      oneOf(introspector).getAnnotation(Cell.class);
      will(returnValue(cell));
      oneOf(introspector).getAnnotation(IterateColumns.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateRows.class);
      will(returnValue(null));
      oneOf(introspector).getAnnotation(IterateSheets.class);
      will(returnValue(null));
    } });
    
    try {
      CollectionOfSimpleTypeBindingStrategy.INSTANCE
          .createBinding(introspector, evaluatorFactory);
      fail("expected WorkbookBindingException");
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
    }
  }

}
