/*
 * File created on Dec 24, 2013 
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
package org.soulwing.jawb.spi;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.impl.expression.ExpressionCompiler;
import org.soulwing.jawb.impl.expression.ExpressionErrorListener;
import org.soulwing.jawb.impl.expression.ExpressionLexer;
import org.soulwing.jawb.impl.expression.ExpressionParser;
import org.soulwing.jawb.impl.expression.Operand;
import org.soulwing.jawb.impl.expression.Value;

/**
 * An {@link ExpressionFactory} that creates compiled expressions.
 * 
 * @author Carl Harris
 */
public class CompiledExpressionFactory implements ExpressionFactory {

  private final WorkbookBindingProvider provider;

  /**
   * Constructs a new instance.
   * @param provider
   */
  public CompiledExpressionFactory(WorkbookBindingProvider provider) {
    this.provider = provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createExpressionEvaluator(String expression,
      String sheetReference) {
    return new ExpressionEvaluator(
        compileStatement(expression, sheetReference));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createPredicateEvaluator(String expression,
      String sheetReference) {
    return new PredicateEvaluator(
        compileStatement(expression, sheetReference));
  }

  private Operand compileStatement(String statement, String sheetReference) {
    ANTLRInputStream inputStream = new ANTLRInputStream(statement);
    ExpressionLexer lexer = new ExpressionLexer(inputStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    ExpressionErrorListener errorListener = new ExpressionErrorListener();
    ExpressionParser parser = new ExpressionParser(tokenStream);
    parser.removeErrorListeners();
    parser.addErrorListener(errorListener);
    ExpressionCompiler visitor = new ExpressionCompiler();
    visitor.setProvider(provider);
    visitor.setSheetReference(sheetReference);
    Operand operand = visitor.visit(parser.statement());
    if (errorListener.hasErrors()) {
      throw errorListener.getException();
    }
    return operand;
  }

  private static class ExpressionEvaluator implements Evaluator {
    
    private final Operand operand;

    /**
     * Constructs a new instance.
     * @param operand
     */
    public ExpressionEvaluator(Operand operand) {
      this.operand = operand;
    }
    
    @Override
    public Object evaluate(BoundWorkbook workbook)
        throws WorkbookBindingException {
      Value value = operand.evaluate(workbook);
      if (Loggers.EXPRESSION.isDebugEnabled()) {
        Loggers.EXPRESSION.debug("{}={}", operand.toString(workbook), value);
      }
      return value.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return operand.toString();
    }
    
  }
  
  private static class PredicateEvaluator implements Evaluator {
    
    private final Operand operand;
        
    /**
     * Constructs a new instance.
     * @param operand
     */
    public PredicateEvaluator(Operand operand) {
      this.operand = operand;
    }

    @Override
    public Object evaluate(BoundWorkbook workbook)
        throws WorkbookBindingException {
      Value value = operand.evaluate(workbook);
      if (Loggers.EXPRESSION.isDebugEnabled()) {
        Loggers.EXPRESSION.debug("{}=[{}]", operand.toString(workbook), value);
      }
      return value.isTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return operand.toString();
    }

  }
}
