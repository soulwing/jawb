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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link CollectionEvaluator}.
 *
 * @author Carl Harris
 */
public class CollectionEvaluatorTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private WorkbookIterator iterator = new ColumnIterator(1, 1, null, null, null);
  
  private ParentableEvaluator elementEvaluator = 
      mockery.mock(ParentableEvaluator.class);
  
  private CollectionEvaluator evaluator = new CollectionEvaluator(
      ArrayList.class, elementEvaluator, iterator);
  
  @Test
  public void testEvaluate() throws Exception {
    final Object parent = new Object();
    final Object element = new Object();
    mockery.checking(new Expectations() { { 
      oneOf(elementEvaluator).setParent(with(parent));
      oneOf(elementEvaluator).setParent(with(nullValue(Object.class)));
      oneOf(workbook).pushIterator(with(same(iterator)));
      oneOf(elementEvaluator).evaluate(with(same(workbook)));
      will(returnValue(element));
      oneOf(workbook).popIterator(with(same(iterator)));
    } });
    
    evaluator.setParent(parent);
    Object result = evaluator.evaluate(workbook);
    assertThat(result, instanceOf(List.class));
    ArrayList elements = (ArrayList) result;
    mockery.assertIsSatisfied();
    assertThat(elements.isEmpty(), equalTo(false));
    assertThat(elements.get(0), sameInstance(element));
  }
  
  public interface ParentableEvaluator extends Evaluator, Parentable {    
  }
  
}
