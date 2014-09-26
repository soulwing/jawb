/*
 * File created on Dec 23, 2013 
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
package org.soulwing.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.expression.IfFunction;
import org.soulwing.jawb.impl.expression.Operand;
import org.soulwing.jawb.impl.expression.Value;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link IfFunction}.
 *
 * @author Carl Harris
 */
public class IfFunctionTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private IfFunction function = new IfFunction();
  
  @Test
  public void testConditionTrue() throws Exception {
    final Operand condition = mockery.mock(Operand.class, "condition");
    final Operand antecedent = mockery.mock(Operand.class, "antecedent");
    final Operand otherwise = mockery.mock(Operand.class, "otherwise");
    
    final Value conditionResult = new Value(Value.Type.BOOLEAN, true);
    final Value antecedentResult = new Value();
    
    function.addExpression(condition);
    function.addExpression(antecedent);
    function.addExpression(otherwise);
    
    mockery.checking(new Expectations() { { 
      oneOf(condition).evaluate(workbook);
      will(returnValue(conditionResult));
      oneOf(antecedent).evaluate(workbook);
      will(returnValue(antecedentResult));
    } });
    
    Value result = function.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(antecedentResult));
  }

  @Test
  public void testConditionFalse() throws Exception {
    final Operand condition = mockery.mock(Operand.class, "condition");
    final Operand antecedent = mockery.mock(Operand.class, "antecedent");
    final Operand otherwise = mockery.mock(Operand.class, "otherwise");
    
    final Value conditionResult = new Value(Value.Type.BOOLEAN, false);
    final Value otherwiseResult = new Value();
    
    function.addExpression(condition);
    function.addExpression(antecedent);
    function.addExpression(otherwise);
    
    mockery.checking(new Expectations() { { 
      oneOf(condition).evaluate(workbook);
      will(returnValue(conditionResult));
      oneOf(otherwise).evaluate(workbook);
      will(returnValue(otherwiseResult));
    } });
    
    Value result = function.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(result, sameInstance(otherwiseResult));
  }

  @Test
  public void testConditionFalseNoOtherwiseExpression() throws Exception {
    final Operand condition = mockery.mock(Operand.class, "condition");
    final Operand antecedent = mockery.mock(Operand.class, "antecedent");
    
    final Value conditionResult = new Value(Value.Type.BOOLEAN, false);
    
    function.addExpression(condition);
    function.addExpression(antecedent);
    
    mockery.checking(new Expectations() { { 
      oneOf(condition).evaluate(workbook);
      will(returnValue(conditionResult));
    } });
    
    Value result = function.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(result.getType(), equalTo(Value.Type.BLANK));
  }

}
