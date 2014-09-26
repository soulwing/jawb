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
package org.soulwing.jawb.poi;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;
import org.soulwing.jawb.spi.Loggers;
import org.soulwing.jawb.spi.WorkbookIterator;

/**
 * A {@link BoundWorkbook} that delegates to a POI {@link Workbook}.
 *
 * @author Carl Harris
 */
class ApachePoiWorkbook implements BoundWorkbook {

  private final Deque<WorkbookIterator> iterators = 
      new LinkedList<WorkbookIterator>();
  
  private final Workbook delegate;
  private final boolean useDate1904;
  
  /**
   * Constructs a new instance.
   * @param delegate
   */
  public ApachePoiWorkbook(Workbook delegate) {
    this.delegate = delegate;
    this.useDate1904 = ((XSSFWorkbook) delegate).getCTWorkbook()
        .getWorkbookPr().getDate1904();
  }

  /**
   * Gets the {@code delegate} property.
   * @return
   */
  public Workbook getDelegate() {
    return delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCell evaluateCell(BoundCellReference ref) {
    ref = applyBias(ref);
    CellReference cellRef = ((ApachePoiCellReference) ref).getDelegate();
    String sheetName = cellRef.getSheetName();
    Sheet sheet = sheetName != null ? 
        delegate.getSheet(sheetName) : delegate.getSheetAt(0);
    Row row = sheet.getRow(cellRef.getRow());
    
    ApachePoiCell cell = new ApachePoiCell(row.getCell(cellRef.getCol()), 
        ref, useDate1904);
    
    if (Loggers.EVALUATION.isDebugEnabled()) {
      Loggers.EVALUATION.debug("{}=[{}]", ref, cell.getValue());
    }
    
    return cell;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference applyBias(BoundCellReference ref) {
    Iterator<WorkbookIterator> i = iterators.iterator();
    while (i.hasNext()) {
      WorkbookIterator iterator = i.next();
      ref = iterator.applyBias(ref, this);
    }
    return ref;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void pushIterator(WorkbookIterator iterator) {
    if (Loggers.ITERATION.isDebugEnabled()) {
      Loggers.ITERATION.debug("pushed iterator: {}", iterator);
    }
    iterators.push(iterator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void popIterator(WorkbookIterator iterator) {
    WorkbookIterator top = iterators.pop();
    if (top != iterator) {
      throw new IllegalStateException("unexpected top-of-stack iterator");
    }
    if (Loggers.ITERATION.isDebugEnabled()) {
      Loggers.ITERATION.debug("popped iterator: {}", iterator);
    }
  }

}
