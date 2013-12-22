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
package edu.vt.ras.jawb.poi;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCell;
import edu.vt.ras.jawb.spi.BoundWorkbook;
import edu.vt.ras.jawb.spi.WorkbookIterator;

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
    CellReference cellRef = ((ApachePoiCellReference) applyBias(ref))
        .getDelegate();
    String sheetName = cellRef.getSheetName();
    Sheet sheet = sheetName != null ? 
        delegate.getSheet(sheetName) : delegate.getSheetAt(0);
    System.out.println(cellRef.formatAsString());
    Row row = sheet.getRow(cellRef.getRow());
    Cell cell = row.getCell(cellRef.getCol());
    return new ApachePoiCell(cell, useDate1904);
  }

  /**
   * Applies all of the currently applied biases to the given cell reference.
   * @param ref subject cell reference
   * @return biased cell reference
   */
  private BoundCellReference applyBias(BoundCellReference ref) {
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
  }

}
