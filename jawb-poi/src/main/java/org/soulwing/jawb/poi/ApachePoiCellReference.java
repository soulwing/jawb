/*
 * File created on Dec 21, 2013 
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
package org.soulwing.jawb.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.soulwing.jawb.spi.BoundCellReference;
import org.soulwing.jawb.spi.BoundWorkbook;

/**
 * A {@link BoundCellReference} that delegates to a POI {@link CellReference}
 *
 * @author Carl Harris
 */
class ApachePoiCellReference implements BoundCellReference {

  private final CellReference delegate;
 
  /**
   * Constructs a new instance.
   * @param delegate cell reference delegate
   */
  public ApachePoiCellReference(CellReference delegate) {
    this.delegate = delegate;
  }

  /**
   * Gets the {@code delegate} property.
   * @return
   */
  public CellReference getDelegate() {
    return delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference applyBias(int sheetOffset, int rowOffset,
      int columnOffset, BoundWorkbook workbook) {
    
    Workbook wb = ((ApachePoiWorkbook) workbook).getDelegate();
    
    int sheetIndex = delegate.getSheetName() != null ? 
        wb.getSheetIndex(delegate.getSheetName()) : 0;
    sheetIndex += sheetOffset;
    String sheetName = wb.getSheetName(sheetIndex);
    
    int rowIndex = delegate.getRow();
    if (!delegate.isRowAbsolute()) {
      rowIndex += rowOffset;
    }
    
    short columnIndex = delegate.getCol();
    if (!delegate.isColAbsolute()) {
      columnIndex += columnOffset;
    }
    
    return new ApachePoiCellReference(
        new CellReference(sheetName, rowIndex, columnIndex, 
            delegate.isRowAbsolute(), delegate.isColAbsolute()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return delegate.formatAsString();
  }

}
