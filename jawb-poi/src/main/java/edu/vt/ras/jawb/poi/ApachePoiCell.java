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
import org.apache.poi.ss.usermodel.DateUtil;

import edu.vt.ras.jawb.spi.BoundCell;
import edu.vt.ras.jawb.spi.BoundCellReference;

/**
 * A {@link BoundCell} that delegates to a POI {@link Cell}.
 *
 * @author Carl Harris
 */
class ApachePoiCell implements BoundCell {

  private final Cell delegate;
  private final BoundCellReference ref;
  private final boolean useDate1904;
  
  public ApachePoiCell(Cell delegate, BoundCellReference ref, 
      boolean useDate1904) {
    this.delegate = delegate;
    this.ref = ref;
    this.useDate1904 = useDate1904;  
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference getReference() {
    return ref;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBlank() {
    return delegate == null || delegate.getCellType() == Cell.CELL_TYPE_BLANK;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {
    if (isBlank()) return null;
    try {
      return getStringValue();
    }
    catch (IllegalStateException ex1) {
      try {
        return getNumericValue();
      }
      catch (IllegalStateException ex2) {
        try {
          return getBooleanValue();
        }
        catch (IllegalStateException ex3) {
          throw new IllegalStateException("unrecognized cell content");
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getBooleanValue() {
    if (isBlank()) {
      throw new IllegalStateException("blank value");
    }
    return delegate.getBooleanCellValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDateValue() {
    if (isBlank()) {
      return null;
    }
    double numericValue = delegate.getNumericCellValue();
    if (!DateUtil.isValidExcelDate(numericValue)) {
      throw new IllegalStateException("not a valid date");
    }
    return DateUtil.getJavaDate(numericValue, useDate1904);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getNumericValue() {
    if (isBlank()) {
      throw new IllegalStateException("blank value");
    }
    return delegate.getNumericCellValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringValue() {
    if (isBlank()) {
      throw new IllegalStateException("blank value");
    }
    return delegate.getStringCellValue();
  }

}
