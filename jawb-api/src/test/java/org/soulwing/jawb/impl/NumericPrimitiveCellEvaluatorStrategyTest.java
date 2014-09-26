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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.NumericPrimitiveCellEvaluatorStrategy;
import org.soulwing.jawb.spi.BoundCell;

/**
 * Unit tests for {@link NumericPrimitiveCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class NumericPrimitiveCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCell cell = mockery.mock(BoundCell.class);
  
  @Test
  public void testWithDoubleTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, double.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testWithFloatTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, float.class), equalTo((Object) (float) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithLongTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, long.class), equalTo((Object) (long) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithIntTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, int.class), equalTo((Object) (int) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithShortTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, short.class), equalTo((Object) (short) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithByteTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(NumericPrimitiveCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, byte.class), equalTo((Object) (byte) result));
    mockery.assertIsSatisfied();
  }
  
}
