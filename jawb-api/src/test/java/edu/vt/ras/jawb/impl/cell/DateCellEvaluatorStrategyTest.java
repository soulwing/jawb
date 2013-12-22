/*
 * File created on Dec 20, 2013 
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
package edu.vt.ras.jawb.impl.cell;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.impl.cell.DateCellEvaluatorStrategy;
import edu.vt.ras.jawb.impl.cell.TypeMismatchException;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCellValue;

/**
 * Unit tests for {@link DateCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class DateCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  private BoundCellValue value = mockery.mock(BoundCellValue.class);

  @Test
  public void testEvaluateWithDateValue() throws Exception {
    final Date result = new Date();
    mockery.checking(new Expectations() { { 
      oneOf(value).getType();
      will(returnValue(BoundCellValue.Type.NUMERIC));
      oneOf(value).isValidDate();
      will(returnValue(true));
      oneOf(value).getDateValue();
      will(returnValue(result));
    } } );
       
    assertThat(DateCellEvaluatorStrategy.INSTANCE
        .evaluate(ref, value, Date.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testEvaluateWithNonDateValue() throws Exception {
    mockery.checking(new Expectations() { { 
      atLeast(1).of(value).getType();
      will(returnValue(BoundCellValue.Type.BLANK));
    } } );
       
    try {
      DateCellEvaluatorStrategy.INSTANCE.evaluate(ref, value, Date.class);
      fail("expected TypeMismatchException");
    }
    catch (TypeMismatchException ex) {
      mockery.assertIsSatisfied();
    }
  }

}
