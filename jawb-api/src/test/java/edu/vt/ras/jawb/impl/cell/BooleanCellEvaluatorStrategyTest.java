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
package edu.vt.ras.jawb.impl.cell;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.TypeMismatchException;
import edu.vt.ras.jawb.impl.cell.BooleanCellEvaluatorStrategy;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCellValue;

/**
 * Unit tests for {@link BooleanCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class BooleanCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  private BoundCellValue value = mockery.mock(BoundCellValue.class);

  @Test
  public void testEvaluateWithWrapperTarget() throws Exception {
    final boolean result = false;
    mockery.checking(new Expectations() { { 
      oneOf(value).getType();
      will(returnValue(BoundCellValue.Type.BOOLEAN));
      oneOf(value).getBooleanValue();
      will(returnValue(result));
    } } );
       
    assertThat(BooleanCellEvaluatorStrategy.INSTANCE
        .evaluate(ref, value, Boolean.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testEvaluateWithPrimitiveTarget() throws Exception {
    final boolean result = false;
    mockery.checking(new Expectations() { { 
      oneOf(value).getType();
      will(returnValue(BoundCellValue.Type.BOOLEAN));
      oneOf(value).getBooleanValue();
      will(returnValue(result));
    } } );
       
    assertThat(
        BooleanCellEvaluatorStrategy.INSTANCE.evaluate(ref, value, boolean.class),
        equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testEvaluateWithNonBooleanValue() throws Exception {
    mockery.checking(new Expectations() { { 
      atLeast(1).of(value).getType();
      will(returnValue(BoundCellValue.Type.BLANK));
    } } );
       
    try {
      BooleanCellEvaluatorStrategy.INSTANCE.evaluate(ref, value, boolean.class);
      fail("expected TypeMismatchException");
    }
    catch (TypeMismatchException ex) {
      mockery.assertIsSatisfied();
    }
  }

}
