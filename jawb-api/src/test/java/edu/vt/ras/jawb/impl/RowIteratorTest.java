/*
 * File created on Dec 21, 2013 
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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;

/**
 * Unit tests for {@link RowIterator}.
 *
 * @author Carl Harris
 */
public class RowIteratorTest {

  private Mockery mockery = new Mockery();
  
  private BoundCellReference ref = mockery.mock(BoundCellReference.class);
  
  private BoundWorkbook workbook = mockery.mock(BoundWorkbook.class);
  
  private WorkbookBindingProvider provider =
      mockery.mock(WorkbookBindingProvider.class);

  @Test
  public void testApplyBias() throws Exception {
    mockery.checking(new Expectations() { {  
      oneOf(ref).applyBias(with(0), with(0), with(0), with(workbook));
      oneOf(ref).applyBias(with(0), with(1), with(0), with(workbook));
    } });

    RowIterator iterator = new RowIterator(2, 1, "", "", provider);
    while (iterator.hasNext()) {
      iterator.next();
      iterator.applyBias(ref, workbook);
    }
    mockery.assertIsSatisfied();
  }

}
