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

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;

import edu.vt.ras.jawb.spi.BoundCellValue;

/**
 * A {@link BoundCellValue} that delegates to a POI {@link CellValue}.
 *
 * @author Carl Harris
 */
class ApachePoiCellValue implements BoundCellValue {

  private final CellValue delegate;
  private final boolean useDate1904;
  
  /**
   * Constructs a new instance.
   * @param delegate
   * @param workbook
   */
  public ApachePoiCellValue(CellValue delegate, boolean useDate1904) {
    this.delegate = delegate;
    this.useDate1904 = useDate1904;  
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType() {
    if (delegate == null) return Type.BLANK;
    switch (delegate.getCellType()) {
      case Cell.CELL_TYPE_BLANK:
        return Type.BLANK;
      case Cell.CELL_TYPE_BOOLEAN:
        return Type.BOOLEAN;
      case Cell.CELL_TYPE_ERROR:
        return Type.ERROR;
      case Cell.CELL_TYPE_NUMERIC:        
        return Type.NUMERIC;
      case Cell.CELL_TYPE_STRING:
        return Type.STRING;
      default:
        throw new IllegalStateException("unrecognized type");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getBooleanValue() {
    return delegate.getBooleanValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValidDate() {
    return delegate.getCellType() == Cell.CELL_TYPE_NUMERIC
        && DateUtil.isValidExcelDate(delegate.getNumberValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDateValue() {
    return DateUtil.getJavaDate(delegate.getNumberValue(), useDate1904);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getNumericValue() {
    return delegate.getNumberValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringValue() {
    return delegate.getStringValue();
  }

}
