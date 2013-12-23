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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.spi.BoundWorkbook;

/**
 * Unit tests for {@link InclusiveOrOperator}.
 *
 * @author Carl Harris
 */
public class InclusiveOrOperatorTest {

  private Mockery mockery = new Mockery();
  
  private Operand a = mockery.mock(Operand.class, "operandA");
  
  private Operand b = mockery.mock(Operand.class, "operandB");

  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);

  private InclusiveOrOperator operator = new InclusiveOrOperator(a, b);

  
  @Test
  public void testEvaluate() throws Exception {
    final Value x = new Value(Value.Type.BOOLEAN, false);
    final Value y = new Value(Value.Type.BOOLEAN, true);
    
    mockery.checking(new Expectations() { {
      oneOf(a).evaluate(workbook);
      will(returnValue(x));
      oneOf(b).evaluate(workbook);
      will(returnValue(y));
    } });
    
    Value result = operator.evaluate(workbook);
    mockery.assertIsSatisfied();
    assertThat(result.getType(), equalTo(Value.Type.BOOLEAN));
    assertThat(result.getValue(), equalTo((Object) true));    
  }
  
}
