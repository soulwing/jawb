/*
 * File created on Dec 24, 2013 
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
package edu.vt.ras.jawb.spi;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.impl.expression.ExpressionCompiler;
import edu.vt.ras.jawb.impl.expression.ExpressionErrorListener;
import edu.vt.ras.jawb.impl.expression.ExpressionLexer;
import edu.vt.ras.jawb.impl.expression.ExpressionParser;
import edu.vt.ras.jawb.impl.expression.Operand;

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
    final Operand operand = compileStatement(expression, sheetReference);
    return new Evaluator() {
      @Override
      public Object evaluate(BoundWorkbook workbook)
          throws WorkbookBindingException {
        return operand.evaluate(workbook).getValue();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createPredicateEvaluator(String expression, 
      String sheetReference) {
    final Operand operand = compileStatement(expression, sheetReference);
    return new Evaluator() {
      @Override
      public Object evaluate(BoundWorkbook workbook)
          throws WorkbookBindingException {
        return operand.evaluate(workbook).isTrue();
      }
    };
  }

  private Operand compileStatement(String statement,
      String sheetReference) {
    ANTLRInputStream inputStream = new ANTLRInputStream(statement);
    ExpressionLexer lexer = new ExpressionLexer(inputStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    ExpressionErrorListener errorListener = new ExpressionErrorListener();
    ExpressionParser parser = new ExpressionParser(tokenStream);
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
  

}
