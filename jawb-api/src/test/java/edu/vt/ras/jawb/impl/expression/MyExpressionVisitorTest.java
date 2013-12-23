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

import static org.hamcrest.Matchers.nullValue;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;

/**
 * Tests for {@link MyExpressionVisitor}.
 *
 * @author Carl Harris
 */
public class MyExpressionVisitorTest {
  
  private Mockery mockery = new Mockery();
  
  private WorkbookBindingProvider provider = 
      mockery.mock(WorkbookBindingProvider.class);
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class, "A1");
  
  @Test
  public void test() throws Exception {
    mockery.checking(new Expectations() { { 
      allowing(provider).createCellReference(
          with(nullValue(String.class)), with(any(String.class)));
      will(returnValue(ref));
    } });
    
    ANTLRInputStream inputStream = new ANTLRInputStream("IF(A1 ~= 'NOT USED', 0.0, A1)");
    ExpressionLexer lexer = new ExpressionLexer(inputStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    
    ExpressionParser parser = new ExpressionParser(tokenStream);
    MyExpressionVisitor visitor = new MyExpressionVisitor();
    visitor.setProvider(provider);
    Operand operand = visitor.visit(parser.statement());
    System.out.println(operand);
  }
  
}
