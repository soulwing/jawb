/*
 * File created on Dec 31, 2013 
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
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.CellEnumValue;
import org.soulwing.jawb.impl.EnumCellEvaluatorStrategy;
import org.soulwing.jawb.spi.BoundCell;

/**
 * Unit tests for {@link EnumCellEvaluatorStrategy}
 *
 * @author Carl Harris
 */
public class EnumCellEvaluatorStrategyTest {
  
  private Mockery mockery = new Mockery();
  private BoundCell cell = mockery.mock(BoundCell.class);
  
  @Test
  public void testWithAnnotation() throws Exception {
    final String cellValue = "Red";
    mockery.checking(new Expectations() { { 
      oneOf(cell).getValue();
      will(returnValue(cellValue));
    } });
    
    Object result = 
        EnumCellEvaluatorStrategy.INSTANCE.evaluate(cell, Color.class);
    mockery.assertIsSatisfied();
    assertThat(result, equalTo((Object) Color.RED));
  }

  @Test
  public void testWithoutAnnotation() throws Exception {
    final String cellValue = "BLUE";
    mockery.checking(new Expectations() { { 
      oneOf(cell).getValue();
      will(returnValue(cellValue));
    } });
    
    Object result = 
        EnumCellEvaluatorStrategy.INSTANCE.evaluate(cell, Color.class);
    mockery.assertIsSatisfied();
    assertThat(result, equalTo((Object) Color.BLUE));
  }
  
  @Test
  public void testWithNameWhenAnnotated() throws Exception {
    final String cellValue = "GREEN";
    mockery.checking(new Expectations() { { 
      oneOf(cell).getValue();
      will(returnValue(cellValue));
    } });
    
    try {
      EnumCellEvaluatorStrategy.INSTANCE.evaluate(cell, Color.class);
      fail("expected WorkbookBindingException");
    }
    catch (WorkbookBindingException ex) {
      mockery.assertIsSatisfied();
    }
  }
  

  public enum Color {
    @CellEnumValue("Red")
    RED,
    @CellEnumValue("Green")
    GREEN,
    BLUE;
  }

}
