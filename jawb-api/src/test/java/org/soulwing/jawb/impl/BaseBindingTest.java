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
package org.soulwing.jawb.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.soulwing.jawb.impl.Accessor;
import org.soulwing.jawb.impl.BaseBinding;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Evaluator;

/**
 * Unit tests for {@link BaseBinding}.
 *
 * @author Carl Harris
 */
public class BaseBindingTest {

  private Mockery mockery = new Mockery();
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private Accessor accessor = mockery.mock(Accessor.class);
  
  private Evaluator evaluator = mockery.mock(Evaluator.class);
  
  private BaseBinding binding = new BaseBinding(accessor, evaluator);
  
  @Test
  public void testBind() throws Exception {
    final Object result = new Object();
    final Object boundObject = new Object();
    mockery.checking(new Expectations() { { 
      oneOf(evaluator).evaluate(workbook);
      will(returnValue(result));
      oneOf(accessor).setValue(with(same(boundObject)), with(same(result)));
    } });
    
    binding.bind(workbook, boundObject);
    mockery.assertIsSatisfied();    
  }
  
}
