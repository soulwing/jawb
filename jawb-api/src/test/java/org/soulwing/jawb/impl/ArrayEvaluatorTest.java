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
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.ArrayEvaluator;
import org.soulwing.jawb.impl.ColumnIterator;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Evaluator;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link ArrayEvaluator}.
 *
 * @author Carl Harris
 */
public class ArrayEvaluatorTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private WorkbookIterator iterator = new ColumnIterator(1, 1, "", "", null);
  
  private Evaluator elementEvaluator = mockery.mock(Evaluator.class);
  
  private ArrayEvaluator evaluator = new ArrayEvaluator(
      Object.class, elementEvaluator, iterator);
  
  @Test
  public void testEvaluate() throws Exception {
    final Object element = new Object();
    mockery.checking(new Expectations() { { 
      oneOf(workbook).pushIterator(with(same(iterator)));
      oneOf(elementEvaluator).evaluate(with(same(workbook)));
      will(returnValue(element));
      oneOf(workbook).popIterator(with(same(iterator)));
    } });
    
    Object[] elements = (Object[]) evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(elements.length, equalTo(1));
    assertThat(elements[0], sameInstance(element));
  }
  
}
