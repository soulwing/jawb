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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.ValueOfCellEvaluatorStrategy;
import org.soulwing.jawb.spi.BoundCell;

/**
 * Unit tests for {@link ValueOfCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class ValueOfCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCell cell = mockery.mock(BoundCell.class);
  
  @Test
  public void testWithDoubleTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Double.class, null), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testWithFloatTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Float.class, null), equalTo((Object)((float) result)));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithLongTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Long.class, null), equalTo((Object)((long) result)));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithIntegerTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Integer.class, null), equalTo((Object)((int) result)));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithShortTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Short.class, null), equalTo((Object)((short) result)));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithByteTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, Byte.class, null), equalTo((Object)((byte) result)));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithBigDecimalTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, BigDecimal.class, null), 
        equalTo((Object)(BigDecimal.valueOf(result))));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testWithBigIntegerTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } });
    
    assertThat(ValueOfCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, BigInteger.class, null), 
        equalTo((Object)(BigInteger.valueOf((long) result))));
    mockery.assertIsSatisfied();
  }

}
