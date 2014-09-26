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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.annotation.CellEnumValue;
import org.soulwing.jawb.impl.CellEvaluator;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link CellEvaluator}.
 *
 * @author Carl Harris
 */
public class CellEvaluatorTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private BoundCell cell = mockery.mock(BoundCell.class);
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  @Test
  public void testWithBooleanPrimitiveTarget() throws Exception {
    final boolean result = true;
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getBooleanValue();
      will(returnValue(result));
    } });
    
    CellEvaluator evaluator = new CellEvaluator(ref, boolean.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, sameInstance((Object) result));
  }
  
  @Test
  public void testWithBooleanWrapperTarget() throws Exception {
    final boolean result = true;
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getBooleanValue();
      will(returnValue(result));
    } });
    
    CellEvaluator evaluator = new CellEvaluator(ref, Boolean.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, sameInstance((Object) result));
  }
  
  @Test
  public void testWithCalendarTarget() throws Exception {
    final Calendar result = Calendar.getInstance();
    result.setTime(new Date());
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getDateValue();
      will(returnValue(result.getTime()));
    } });
    
    CellEvaluator evaluator = new CellEvaluator(ref, Calendar.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) result));
  }
  
  @Test
  public void testWithDateTarget() throws Exception {
    final Date result = new Date();
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getDateValue();
      will(returnValue(result));
    } });
    
    CellEvaluator evaluator = new CellEvaluator(ref, Date.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, sameInstance((Object) result));
  }
  
  @Test
  public void testWithDoublePrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));
    CellEvaluator evaluator = new CellEvaluator(ref, double.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) result));
  }

  @Test
  public void testWithDoubleWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));    
    CellEvaluator evaluator = new CellEvaluator(ref, Double.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) result));
  }
  
  @Test
  public void testWithFloatPrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));
    CellEvaluator evaluator = new CellEvaluator(ref, float.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((float) result)));
  }
  
  @Test
  public void testWithFloatWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));
    CellEvaluator evaluator = new CellEvaluator(ref, Float.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((float) result)));
  }
  
  @Test
  public void testWithLongPrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, long.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((long) result)));
  }

  @Test
  public void testWithLongWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, Long.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((long) result)));
  }

  @Test
  public void testWithIntegerPrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, int.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((int) result)));
  }
  
  @Test
  public void testWithIntegerWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, Integer.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((int) result)));
  }

  @Test
  public void testWithShortPrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, short.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((short) result)));
  }

  @Test
  public void testWithShortWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, Short.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((short) result)));
  }

  @Test
  public void testWithBytePrimitiveTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, byte.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((byte) result)));
  }

  @Test
  public void testWithByteWrapperTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, Byte.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) ((byte) result)));
  }

  @Test
  public void testWithBigDecimalTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, BigDecimal.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) BigDecimal.valueOf(result)));
  }

  @Test
  public void testWithBigIntegerTarget() throws Exception {
    final double result = 1.0;
    mockery.checking(numericValueExpectations(result));  
    CellEvaluator evaluator = new CellEvaluator(ref, BigInteger.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) BigInteger.valueOf((long) result)));
  }

  @Test
  public void testWithEnumTargetUsingAnnotation() throws Exception {
    final String result = "Value 1";
    mockery.checking(enumValueExpectations(result));
    
    CellEvaluator evaluator = new CellEvaluator(ref, MockEnum.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) MockEnum.VALUE1));
  }

  @Test
  public void testWithEnumTargetUsingName() throws Exception {
    final String result = "VALUE2";
    mockery.checking(enumValueExpectations(result));
    
    CellEvaluator evaluator = new CellEvaluator(ref, MockEnum.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, equalTo((Object) MockEnum.VALUE2));
  }

  private Expectations enumValueExpectations(final String result) {
    return new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getValue();
      will(returnValue(result));
    } };
  }

  public enum MockEnum {
    @CellEnumValue("Value 1")
    VALUE1,
    VALUE2;
  }
  
  @Test
  public void testWithStringTarget() throws Exception {
    final String result = new String();
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getValue();
      will(returnValue(result));
    } });
    
    CellEvaluator evaluator = new CellEvaluator(ref, String.class);
    Object obj = evaluator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(obj, sameInstance((Object) result));
  }

  private Expectations numericValueExpectations(final double result) {
    return new Expectations() { { 
      oneOf(workbook).evaluateCell(with(same(ref)));
      will(returnValue(cell));
      oneOf(cell).isBlank();
      will(returnValue(false));
      oneOf(cell).getNumericValue();
      will(returnValue(result));
    } };
  }
  

}
