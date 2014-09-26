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
package org.soulwing.jawb.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.soulwing.jawb.impl.ColumnIterator;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.WorkbookBindingProvider;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * Unit tests for {@link ColumnIterator}.
 *
 * @author Carl Harris
 */
public class WorkbookIteratorTest {

  @Test
  public void testCountUpByOne() throws Exception {
    MockIterator iterator = new MockIterator(2, 1, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(1));
    assertThat(iterator.hasNext(), equalTo(false));
  }

  @Test
  public void testCountDownByOne() throws Exception {
    MockIterator iterator = new MockIterator(2, -1, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(-1));
    assertThat(iterator.hasNext(), equalTo(false));
  }

  @Test
  public void testCountUpByTwo() throws Exception {
    MockIterator iterator = new MockIterator(2, 2, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(2));
    assertThat(iterator.hasNext(), equalTo(false));
  }

  @Test
  public void testReset() throws Exception {
    MockIterator iterator = new MockIterator(2, 1, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(1));
    assertThat(iterator.hasNext(), equalTo(false));
    iterator.reset();
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(1));
    assertThat(iterator.hasNext(), equalTo(false));
  }
  
  @Test
  public void testOverrun() throws Exception {
    MockIterator iterator = new MockIterator(1, 1, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));
    assertThat(iterator.hasNext(), equalTo(false));
    iterator.next();
    assertThat(iterator.getOffset(), equalTo(0));    
  }

  @Test(expected = IllegalStateException.class)
  public void testUnderrun() throws Exception {
    MockIterator iterator = new MockIterator(1, 1, null, null, null);
    assertThat(iterator.hasNext(), equalTo(true));
    assertThat(iterator.getOffset(), equalTo(0));
  }
  

  private static class MockIterator extends WorkbookIterator {

    public MockIterator(int count, int increment, String stop, String skip,
        WorkbookBindingProvider provider) {
      super(count, increment, stop, skip, provider);
    }

    @Override
    public BoundCellReference applyBias(BoundCellReference ref, BoundWorkbook workbook) {
      return null;
    }
    
    protected int getOffset() {
      return super.getOffset();
    }
  }
}
