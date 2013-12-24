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

import edu.vt.ras.jawb.impl.expression.ExpressionParser.AdditiveOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.AndOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.EqualityOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.ExclusiveOrOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.ExpressionContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.FunctionContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.IsTypeOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.MultiplicativeOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.OperandContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.RelationalOpContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.StatementContext;
import edu.vt.ras.jawb.impl.expression.ExpressionParser.UnaryOpContext;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Carl Harris
 */
public class MyExpressionVisitor extends ExpressionBaseVisitor<Operand> {

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
    return super.visit(ctx.expression());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitExpression(ExpressionContext ctx) {
    Operand a = super.visit(ctx.exclusiveOrOp(0));
    if (ctx.exclusiveOrOp(1) == null) return a;
    
    Operand b = super.visit(ctx.exclusiveOrOp(1));
    if (ctx.OP_OR() != null) {
      return new InclusiveOrOperator(a, b);
    }
    throw new IllegalStateException("unrecognized OR operator");    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitExclusiveOrOp(ExclusiveOrOpContext ctx) {
    Operand a = super.visit(ctx.andOp(0));
    if (ctx.andOp(1) == null) return a;
    
    Operand b = super.visit(ctx.andOp(1));
    if (ctx.OP_XOR() != null) {
      return new ExclusiveOrOperator(a, b);
    }
    throw new IllegalStateException("unrecognized XOR operator");    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitAndOp(AndOpContext ctx) {
    Operand a = super.visit(ctx.equalityOp(0));
    if (ctx.equalityOp(1) == null) return a;
    
    Operand b = super.visit(ctx.equalityOp(1));
    if (ctx.OP_AND() != null) {
      return new AndOperator(a, b);
    }
    throw new IllegalStateException("unrecognized AND operator");    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitEqualityOp(EqualityOpContext ctx) {
    Operand a = super.visit(ctx.relationalOp(0));
    if (ctx.relationalOp(1) == null) return a;
    
    Operand b = super.visit(ctx.relationalOp(1));
    if (ctx.OP_EQ() != null) {
      return new EqualsOperator(a, b);
    }
    if (ctx.OP_NEQ() != null) {
      return new NotEqualsOperator(a, b);
    }
    throw new IllegalStateException("unrecognized relational operator");    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitRelationalOp(RelationalOpContext ctx) {
    if (ctx.isTypeOp() != null) {
      return super.visit(ctx.isTypeOp());
    }
    
    Operand a = super.visit(ctx.additiveOp(0));
    if (ctx.additiveOp(1) == null) return a;
    
    Operand b = super.visit(ctx.additiveOp(1));
    if (ctx.OP_LT() != null) {
      return new LessThanOperator(a, b);
    }
    if (ctx.OP_LEQ() != null) {
      return new LessOrEqualsOperator(a, b);
    }
    if (ctx.OP_GT() != null) {
      return new GreaterThanOperator(a, b);
    }
    if (ctx.OP_GEQ() != null) {
      return new GreaterOrEqualsOperator(a, b);
    }
    if (ctx.OP_MATCH() != null) {
      return new MatchOperator(a, b);
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
    if (ctx.OP_IS() != null) {
      return new IsOperator(a, b);
    }
    if (ctx.OP_IS_NOT() != null) {
      return new IsNotOperator(a, b);
    }
    throw new IllegalStateException("unrecognized IS operator");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitAdditiveOp(AdditiveOpContext ctx) {
    Operand a = super.visit(ctx.multiplicativeOp(0));
    if (ctx.multiplicativeOp(1) == null) return a;
    
    Operand b = super.visit(ctx.multiplicativeOp(1));
    if (ctx.OP_PLUS() != null) {
      return new AdditionOperator(a, b);
    }
    if (ctx.OP_MINUS() != null) {
      return new SubtractionOperator(a, b);
    }
    throw new IllegalStateException("unrecognized additive operator");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitMultiplicativeOp(MultiplicativeOpContext ctx) {
    Operand a = super.visit(ctx.unaryOp(0));
    if (ctx.unaryOp(1) == null) return a;
    
    Operand b = super.visit(ctx.unaryOp(1));
    if (ctx.OP_MULT() != null) {
      return new MultiplicationOperator(a, b);
    }
    if (ctx.OP_DIV() != null) {
      return new DivisionOperator(a, b);
    }
    throw new IllegalStateException("unrecognized multiplicative operator");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand visitUnaryOp(UnaryOpContext ctx) {
    Operand operand = super.visit(ctx.function());
    if (ctx.OP_MINUS() != null) {
      return new NegationOperator(operand);
    }
    if (ctx.OP_NOT() != null) {
      return new NotOperator(operand);
    }
    return operand;
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
      return new LiteralOperand(Value.Type.STRING, ctx.STRING().getText());
    }
    if (ctx.DATE() != null) {
      // TODO
    }
    if (ctx.TIME() != null) {
      // TODO
    }
    throw new IllegalStateException("unrecognized operand");
  }

  
}
