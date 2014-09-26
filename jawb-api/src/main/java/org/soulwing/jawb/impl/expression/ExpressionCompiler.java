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

import org.soulwing.jawb.spi.WorkbookBindingProvider;

import org.soulwing.jawb.impl.expression.ExpressionBaseVisitor;
import org.soulwing.jawb.impl.expression.ExpressionParser.AdditiveOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.AndOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.EqualityOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.ExclusiveOrOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.ExpressionContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.FunctionContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.IsTypeOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.MultiplicativeOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.OperandContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.RelationalOpContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.StatementContext;
import org.soulwing.jawb.impl.expression.ExpressionParser.UnaryOpContext;

/**
 * A visitor that compiles an expression.
 *
 * @author Carl Harris
 */
public class ExpressionCompiler extends ExpressionBaseVisitor<Operand> {

  private String sheetReference;
  private WorkbookBindingProvider provider;
  
  /**
   * Gets the {@code sheetReference} property.
   * @return
   */
  public String getSheetReference() {
    return sheetReference;
  }

  /**
   * Sets the {@code sheetReference} property.
   * @param sheetReference
   */
  public void setSheetReference(String sheetReference) {
    this.sheetReference = sheetReference;
  }

  /**
   * Gets the {@code provider} property.
   * @return
   */
  public WorkbookBindingProvider getProvider() {
    return provider;
  }

  /**
   * Sets the {@code provider} property.
   * @param provider
   */
  public void setProvider(WorkbookBindingProvider provider) {
    this.provider = provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitStatement(StatementContext ctx) {
    if (ctx.expression() == null) {
      return new LiteralOperand(new Value());
    }
    return super.visit(ctx.expression());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitExpression(ExpressionContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.exclusiveOrOp(0)));
    for (int i = 1, max = ctx.exclusiveOrOp().size(); i < max; i++) {
      operator.addOperation(inclusiveOrOperator(ctx), 
          visit(ctx.exclusiveOrOp(i)));
    }
    return operator;
  }

  private BinaryOperator inclusiveOrOperator(ExpressionContext ctx) {
    if (ctx.OP_OR() != null) {
      return new InclusiveOrOperator();
    }
    throw new IllegalStateException("unrecognized OR operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitExclusiveOrOp(ExclusiveOrOpContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.andOp(0)));
    for (int i = 1, max = ctx.andOp().size(); i < max; i++) {
      operator.addOperation(exclusiveOrOperator(ctx), 
          visit(ctx.andOp(i)));
    }
    return operator;
  }

  private BinaryOperator exclusiveOrOperator(ExclusiveOrOpContext ctx) {
    if (ctx.OP_XOR() != null) {
      return new ExclusiveOrOperator();
    }
    throw new IllegalStateException("unrecognized XOR operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitAndOp(AndOpContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.equalityOp(0)));
    for (int i = 1, max = ctx.equalityOp().size(); i < max; i++) {
      operator.addOperation(andOperator(ctx), 
          visit(ctx.equalityOp(i)));
    }
    return operator;
  }

  private BinaryOperator andOperator(AndOpContext ctx) {
    if (ctx.OP_AND() != null) {
      return new AndOperator();
    }
    throw new IllegalStateException("unrecognized AND operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitEqualityOp(EqualityOpContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.relationalOp(0)));
    for (int i = 1, max = ctx.relationalOp().size(); i < max; i++) {
      operator.addOperation(equalityOperator(ctx), 
          visit(ctx.relationalOp(i)));
    }
    return operator;
  }

  private BinaryOperator equalityOperator(EqualityOpContext ctx) {
    if (ctx.equalityOperator().OP_EQ() != null) {
      return new EqualsOperator();
    }
    if (ctx.equalityOperator().OP_NEQ() != null) {
      return new NotEqualsOperator();
    }
    throw new IllegalStateException("unrecognized equality operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitRelationalOp(RelationalOpContext ctx) {
    if (ctx.isTypeOp() != null) {
      return super.visit(ctx.isTypeOp());
    }
    
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.additiveOp(0)));
    for (int i = 1, max = ctx.additiveOp().size(); i < max; i++) {
      operator.addOperation(relationalOperator(ctx), 
          visit(ctx.additiveOp(i)));
    }
    return operator;
  }

  private BinaryOperator relationalOperator(RelationalOpContext ctx) {
    if (ctx.relationalOperator().OP_LT() != null) {
      return new LessThanOperator();
    }
    if (ctx.relationalOperator().OP_LEQ() != null) {
      return new LessOrEqualsOperator();
    }
    if (ctx.relationalOperator().OP_GT() != null) {
      return new GreaterThanOperator();
    }
    if (ctx.relationalOperator().OP_GEQ() != null) {
      return new GreaterOrEqualsOperator();
    }
    if (ctx.relationalOperator().OP_MATCH() != null) {
      return new MatchOperator();
    }
    throw new IllegalStateException("unrecognized relational operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitIsTypeOp(IsTypeOpContext ctx) {
    Operand a = super.visit(ctx.additiveOp());
    Operand b = new LiteralOperand(Value.Type.STRING, ctx.TYPE().getText());
    PolyadicOperator operator = new PolyadicOperator(a);
    operator.addOperation(isOperator(ctx), b);
    return operator;
  }

  private BinaryOperator isOperator(IsTypeOpContext ctx) {
    if (ctx.isOperator().OP_IS() != null) {
      return new IsOperator();
    }
    if (ctx.isOperator().OP_IS_NOT() != null) {
      return new IsNotOperator();
    }
    throw new IllegalStateException("unrecognized IS operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitAdditiveOp(AdditiveOpContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.multiplicativeOp(0)));
    for (int i = 1, max = ctx.multiplicativeOp().size(); i < max; i++) {
      operator.addOperation(additiveOperator(ctx, i - 1), 
          visit(ctx.multiplicativeOp(i)));
    }
    return operator;
  }

  private BinaryOperator additiveOperator(AdditiveOpContext ctx, int i) {
    if (ctx.additiveOperator(i).OP_PLUS() != null) {
      return new AdditionOperator();
    }
    if (ctx.additiveOperator(i).OP_MINUS() != null) {
      return new SubtractionOperator();
    }
    throw new IllegalStateException("unrecognized additive operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitMultiplicativeOp(MultiplicativeOpContext ctx) {
    PolyadicOperator operator = new PolyadicOperator(
        visit(ctx.unaryOp(0)));
    for (int i = 1, max = ctx.unaryOp().size(); i < max; i++) {
      operator.addOperation(multiplicativeOperator(ctx, i - 1), 
          visit(ctx.unaryOp(i)));
    }
    return operator;
  }

  private BinaryOperator multiplicativeOperator(MultiplicativeOpContext ctx,
      int index) {
    if (ctx.multiplicativeOperator(index).OP_MULT() != null) {
      return new MultiplicationOperator();
    }
    if (ctx.multiplicativeOperator(index).OP_DIV() != null) {
      return new DivisionOperator();
    }
    throw new IllegalStateException("unrecognized multiplicative operator");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitUnaryOp(UnaryOpContext ctx) {
    Operand operand = super.visit(ctx.function());
    if (ctx.unaryOperator() == null
        || ctx.unaryOperator().OP_PLUS() != null) {
      return operand;
    }
    if (ctx.unaryOperator().OP_MINUS() != null) {
      return new NegationOperator(operand);
    }
    if (ctx.unaryOperator().OP_NOT() != null) {
      return new NotOperator(operand);
    }
    throw new IllegalStateException("unrecognized unary operator");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitFunction(FunctionContext ctx) {
    if (ctx.FN_IF() != null) {
      IfFunction function = new IfFunction();
      for (ExpressionContext expr : ctx.expression()) {
        function.addExpression(super.visit(expr));
      }
      return function;
    }
    if (ctx.LPAREN() != null) {
      return new NestedExpressionOperand(super.visit(ctx.expression(0)));
    }
    if (ctx.operand() != null) {
      return super.visit(ctx.operand());
    }
    throw new IllegalStateException("unrecognized function");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitOperand(OperandContext ctx) {
    if (ctx.REF() != null) {
      return new ReferenceOperand( 
          provider.createCellReference(sheetReference, ctx.REF().getText()));
    }
    if (ctx.NUMBER() != null) {
      return new LiteralOperand(Value.Type.NUMBER, 
          Double.parseDouble(ctx.NUMBER().getText()));
    }
    if (ctx.BOOLEAN() != null) {
      return new LiteralOperand(Value.Type.BOOLEAN, 
          Boolean.parseBoolean(ctx.BOOLEAN().getText().toLowerCase()));      
    }
    if (ctx.STRING() != null) {
      String text = ctx.STRING().getText();
      return new LiteralOperand(Value.Type.STRING, 
          text.substring(1, text.length() - 1));
    }
    if (ctx.DATE() != null) {
      return new LiteralOperand(Value.Type.NUMBER, 
          provider.getDateTimeConverter().convertDate(
              ctx.DATE().getText()));
    }
    if (ctx.TIME() != null) {
      return new LiteralOperand(Value.Type.NUMBER, 
          provider.getDateTimeConverter().convertTime(
              ctx.TIME().getText()));
    }
    throw new IllegalStateException("unrecognized operand");
  }

  
}
