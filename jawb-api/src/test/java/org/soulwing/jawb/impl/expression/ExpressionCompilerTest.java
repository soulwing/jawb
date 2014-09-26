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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.soulwing.jawb.impl.expression.ExpressionCompiler;
import org.soulwing.jawb.impl.expression.Operand;
import org.soulwing.jawb.impl.expression.Value;
import org.soulwing.jawb.impl.expression.Value.Type;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.DateTimeConverter;
import org.soulwing.jawb.spi.WorkbookBindingProvider;

import org.soulwing.jawb.impl.expression.ExpressionLexer;
import org.soulwing.jawb.impl.expression.ExpressionParser;

/**
 * Tests for {@link ExpressionCompiler}.
 *
 * @author Carl Harris
 */
public class ExpressionCompilerTest {
  
  private Mockery mockery = new Mockery();
  
  private WorkbookBindingProvider provider = 
      mockery.mock(WorkbookBindingProvider.class);
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class, "A1");
  
  @Before
  public void setUp() {
    mockery.checking(cellReferenceExpectations());
  }

  @Test
  public void testReferenceOperand() throws Exception {
    final BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
    final BoundCell cell = mockery.mock(BoundCell.class);
    mockery.checking(new Expectations() { { 
      oneOf(workbook).evaluateCell(with(ref));
      will(returnValue(cell));
      allowing(cell).getReference();
      will(returnValue(ref));
      oneOf(cell).getValue();
      will(returnValue(1.0));
    } });
    
    Operand op = compileStatement("A1");
    Value value = op.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }

  @Test
  public void testLiteralNumberOperand() throws Exception {
    Operand op = compileStatement("1.0");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }

  @Test
  public void testLiteralBooleanOperand() throws Exception {
    Operand op = compileStatement("true");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));
  }

  @Test
  public void testLiteralStringOperand() throws Exception {
    Operand op = compileStatement("'string'");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.STRING));
    assertThat(value.getValue(), equalTo((Object) "string"));
  }

  @Test
  public void testLiteralDateOperand() throws Exception {
    final String date = "2013-12-24";
    final DateTimeConverter converter = mockery.mock(DateTimeConverter.class);
    mockery.checking(new Expectations() { { 
      oneOf(provider).getDateTimeConverter();
      will(returnValue(converter));
      oneOf(converter).convertDate(with(date));
      will(returnValue(1.0));
    } });
    
    Operand op = compileStatement(date);
    Value value = op.evaluate(null);
    mockery.assertIsSatisfied();
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));    
  }

  @Test
  public void testLiteralTimeOperand() throws Exception {
    final String time = "23:59:59";
    final DateTimeConverter converter = mockery.mock(DateTimeConverter.class);
    mockery.checking(new Expectations() { { 
      oneOf(provider).getDateTimeConverter();
      will(returnValue(converter));
      oneOf(converter).convertTime(with(time));
      will(returnValue(1.0));
    } });
    
    Operand op = compileStatement(time);
    Value value = op.evaluate(null);
    mockery.assertIsSatisfied();
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));    
  }

  @Test
  public void testNestedExpression() throws Exception {
    Operand op = compileStatement("(1.0)");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }

  @Test
  public void testIfFunction() throws Exception {
    Operand op = compileStatement("if(true, true, false)");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));
  }

  @Test
  public void testUnaryPlusExpression() throws Exception {
    Operand op = compileStatement("+1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }
  
  @Test
  public void testUnaryMinusExpression() throws Exception {
    Operand op = compileStatement("-1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) (double) -1.0));
  }
  
  @Test
  public void testNotExpression() throws Exception {
    Operand op = compileStatement("!true");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));
  }

  @Test
  public void testMultiplyExpression() throws Exception {
    Operand op = compileStatement("2 * 2");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 4.0));
  }
  
  @Test
  public void testDivideExpression() throws Exception {
    Operand op = compileStatement("2 / 2");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }
  
  @Test
  public void testAddExpression() throws Exception {
    Operand op = compileStatement("1 + 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 2.0));
  }
  
  @Test
  public void testSubtractExpression() throws Exception {
    Operand op = compileStatement("1 - 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 0.0));
  }

  @Test
  public void testCompoundArithmeticExpression() throws Exception {
    Operand op = compileStatement("(1 + 3 - 1) * 3 / -3");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) (double) -3.0));
  }
  
  @Test
  public void testLessThanExpressionTrue() throws Exception {
    Operand op = compileStatement("0 < 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));   
  }

  @Test
  public void testLessThanExpressionFalse() throws Exception {
    Operand op = compileStatement("1 < 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));   
  }

  @Test
  public void testLessOrEqualsExpressionTrue() throws Exception {
    Operand op = compileStatement("1 <= 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));   
  }

  @Test
  public void testLessOrEqualsExpressionFalse() throws Exception {
    Operand op = compileStatement("1 <= 0");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));   
  }

  @Test
  public void testGreaterThanExpressionTrue() throws Exception {
    Operand op = compileStatement("1 > 0");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));   
  }

  @Test
  public void testGreaterThanExpressionFalse() throws Exception {
    Operand op = compileStatement("1 > 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));   
  }

  @Test
  public void testGreaterOrEqualsExpressionTrue() throws Exception {
    Operand op = compileStatement("1 >= 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));   
  }

  @Test
  public void testGreaterOrEqualsExpressionFalse() throws Exception {
    Operand op = compileStatement("0 >= 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));   
  }

  @Test
  public void testMatchExpressionTrue() throws Exception {
    Operand op = compileStatement("'string1-string2-string3' ~= 'string2'");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));   
  }

  @Test
  public void testMatchExpressionFalse() throws Exception {
    Operand op = compileStatement("'string1-string2-string3' ~= 'string0'");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));   
  }
  
  @Test
  public void testIsExpressionTrue() throws Exception {
    Operand op = compileStatement("'string' is string");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testIsExpressionFalse() throws Exception {
    Operand op = compileStatement("1.0 is string");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }
  
  @Test
  public void testIsNotExpressionTrue() throws Exception {
    Operand op = compileStatement("1.0 is not string");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testIsNotExpressionFalse() throws Exception {
    Operand op = compileStatement("'string' is not string");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testEqualsExpressionTrue() throws Exception {
    Operand op = compileStatement("1 == 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testEqualsExpressionFalse() throws Exception {
    Operand op = compileStatement("0 == 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testNotEqualsExpressionTrue() throws Exception {
    Operand op = compileStatement("0 != 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }
  
  @Test
  public void testNotEqualsExpressionFalse() throws Exception {
    Operand op = compileStatement("1 != 1");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testAndExpressionTrue() throws Exception {
    Operand op = compileStatement("true && true");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testAndExpressionFalse() throws Exception {
    Operand op = compileStatement("true && false");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testExclusiveOrExpressionTrue() throws Exception {
    Operand op = compileStatement("true ^ false");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testExclusiveOrExpressionFalse() throws Exception {
    Operand op = compileStatement("true ^ true");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testInclusiveOrExpressionTrue() throws Exception {
    Operand op = compileStatement("true || false");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }

  @Test
  public void testInclusiveOrExpressionFalse() throws Exception {
    Operand op = compileStatement("false || false");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) false));       
  }

  @Test
  public void testCompoundLogicalStatement() throws Exception {
    Operand op = compileStatement(
        "(0 != 1 && 1 == 1) || 0 > 1 && 0 < 1 ^ 'string' is string");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));       
  }
  
  @Test
  public void testEmptyStatement() throws Exception {
    Operand op = compileStatement("");
    mockery.assertIsSatisfied();
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BLANK));    
  }
  
  private Operand compileStatement(String statement) {
    ANTLRInputStream inputStream = new ANTLRInputStream(statement);
    ExpressionLexer lexer = new ExpressionLexer(inputStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    
    ExpressionParser parser = new ExpressionParser(tokenStream);
    ExpressionCompiler visitor = new ExpressionCompiler();
    visitor.setProvider(provider);
    Operand operand = visitor.visit(parser.statement());
    return operand;
  }
  
  private Expectations cellReferenceExpectations() {
    return new Expectations() { { 
      allowing(provider).createCellReference(
          with(nullValue(String.class)), with(any(String.class)));
      will(returnValue(ref));
    } };
  }
  
}
