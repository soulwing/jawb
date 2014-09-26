/*
 * File created on Dec 20, 2013 
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
package org.soulwing.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.StringCellEvaluatorStrategy;
import org.soulwing.jawb.spi.BoundCell;

/**
 * Unit tests for {@link StringCellEvaluatorStrategy}.
 *
 * @author Carl Harris
 */
public class StringCellEvaluatorStrategyTest {

  private Mockery mockery = new Mockery();
  
  private BoundCell cell = mockery.mock(BoundCell.class);

  @Test
  public void testEvaluateWithStringValue() throws Exception {
    final String result = new String();
    mockery.checking(new Expectations() { { 
      oneOf(cell).getValue();
      will(returnValue(result));
    } } );
       
    assertThat(StringCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, String.class), equalTo((Object) result));
    mockery.assertIsSatisfied();
  }
  
  @Test
  public void testEvaluateWithNonStringValue() throws Exception {
    final Number result = Integer.valueOf(0);
    mockery.checking(new Expectations() { { 
      oneOf(cell).getValue();
      will(returnValue(result));
    } } );
       
    assertThat(StringCellEvaluatorStrategy.INSTANCE
        .evaluate(cell, String.class),  equalTo((Object)"0"));
    mockery.assertIsSatisfied();
  }

}
