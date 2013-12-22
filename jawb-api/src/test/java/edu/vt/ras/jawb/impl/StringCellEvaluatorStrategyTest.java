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
package edu.vt.ras.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.TypeMismatchException;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCell;

/**
 * Unit tests for {@link StringCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class StringCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  private BoundCell cell = mockery.mock(BoundCell.class);

  @Test
  public void testEvaluateWithStringValue() throws Exception {
    final String result = new String();
    mockery.checking(new Expectations() { { 
      oneOf(cell).getStringValue();
      will(returnValue(result));
    } } );
       
    assertThat(StringCellEvaluatorStrategy.INSTANCE
        .evaluate(ref, cell, String.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testEvaluateWithNonStringValue() throws Exception {
    mockery.checking(new Expectations() { { 
      oneOf(cell).getStringValue();
      will(throwException(new IllegalStateException()));
    } } );
       
    try {
      StringCellEvaluatorStrategy.INSTANCE.evaluate(ref, cell, String.class);
      fail("expected TypeMismatchException");
    }
    catch (TypeMismatchException ex) {
      mockery.assertIsSatisfied();
    }
  }

}
