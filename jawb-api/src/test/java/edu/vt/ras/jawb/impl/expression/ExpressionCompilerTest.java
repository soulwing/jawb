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
package edu.vt.ras.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import edu.vt.ras.jawb.impl.expression.Value.Type;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.DateTimeConverter;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;

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
    Operand op = compileStatement("A1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(ReferenceOperand.class));
  }

  @Test
  public void testLiteralNumberOperand() throws Exception {
    Operand op = compileStatement("1.0");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) 1.0));
  }

  @Test
  public void testLiteralBooleanOperand() throws Exception {
    Operand op = compileStatement("true");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(value.getValue(), equalTo((Object) true));
  }

  @Test
  public void testLiteralStringOperand() throws Exception {
    Operand op = compileStatement("'string'");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
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
      will(returnValue(0.0));
    } });
    Operand op = compileStatement(date);
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
  }

  @Test
  public void testLiteralTimeOperand() throws Exception {
    final String time = "23:59:59";
    final DateTimeConverter converter = mockery.mock(DateTimeConverter.class);
    mockery.checking(new Expectations() { { 
      oneOf(provider).getDateTimeConverter();
      will(returnValue(converter));
      oneOf(converter).convertTime(with(time));
      will(returnValue(0.0));
    } });
    Operand op = compileStatement(time);
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
  }

  @Test
  public void testNestedExpression() throws Exception {
    Operand op = compileStatement("(0)");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(NestedExpressionOperand.class));
  }

  @Test
  public void testIfFunction() throws Exception {
    Operand op = compileStatement("if(true, true, false)");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(IfFunction.class));
  }

  @Test
  public void testUnaryPlusExpression() throws Exception {
    Operand op = compileStatement("+1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(LiteralOperand.class));
  }
  
  @Test
  public void testUnaryMinusExpression() throws Exception {
    Operand op = compileStatement("-1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(UnaryOperator.class));
    Value value = op.evaluate(null);
    assertThat(value.getType(), equalTo(Type.NUMBER));
    assertThat(value.getValue(), equalTo((Object) (double) -1.0));
  }
  
  @Test
  public void testNotExpression() throws Exception {
    Operand op = compileStatement("!true");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(UnaryOperator.class));
  }

  @Test
  public void testMultiplyExpression() throws Exception {
    Operand op = compileStatement("1 * 1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(MultiplicationOperator.class));
  }
  
  @Test
  public void testDivideExpression() throws Exception {
    Operand op = compileStatement("1 / 1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(DivisionOperator.class));
  }
  
  @Test
  public void testAddExpression() throws Exception {
    Operand op = compileStatement("1 + 1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(AdditionOperator.class));
  }
  
  @Test
  public void testSubtractExpression() throws Exception {
    Operand op = compileStatement("1 - 1");
    mockery.assertIsSatisfied();
    assertThat(op, instanceOf(SubtractionOperator.class));
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
