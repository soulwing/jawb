/*
 * File created on Dec 23, 2013 
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
package org.soulwing.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.expression.ReferenceOperand;
import org.soulwing.jawb.impl.expression.Value;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link ReferenceOperand}.
 *
 * @author Carl Harris
 */
public class ReferenceOperandTest {

  private Mockery mockery = new Mockery();
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  private BoundCell cell = mockery.mock(BoundCell.class);
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private ReferenceOperand operand = new ReferenceOperand(ref);
  
  @Test
  public void testEvaluateWithNumber() throws Exception {
    final double result = 1.0;
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(ref);
      will(returnValue(cell));
      allowing(cell).getReference();
      will(returnValue(ref));
      oneOf(cell).getValue();
      will(returnValue(result));
    } });
    
    Value value = operand.evaluate(workbook);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testEvaluateWithBoolean() throws Exception {
    final boolean result = true;
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(ref);
      will(returnValue(cell));
      allowing(cell).getReference();
      will(returnValue(ref));
      oneOf(cell).getValue();
      will(returnValue(result));
    } });
    
    Value value = operand.evaluate(workbook);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testEvaluateWithString() throws Exception {
    final String result = "string";
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(ref);
      will(returnValue(cell));
      allowing(cell).getReference();
      will(returnValue(ref));
      oneOf(cell).getValue();
      will(returnValue(result));
    } });
    
    Value value = operand.evaluate(workbook);
    assertThat(value.getType(), equalTo(Value.Type.STRING));
    assertThat(value.getValue(), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }

  @Test
  public void testEvaluateWithBlank() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(ref);
      will(returnValue(cell));
      allowing(cell).getReference();
      will(returnValue(ref));
      oneOf(cell).getValue();
      will(returnValue(null));
    } });
    
    Value value = operand.evaluate(workbook);
    assertThat(value.getType(), equalTo(Value.Type.BLANK));
    assertThat(value.getValue(), nullValue());
    mockery.assertIsSatisfied();
  }

}
