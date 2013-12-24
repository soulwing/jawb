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

import org.apache.poi.ss.util.CellReference;

import edu.vt.ras.jawb.WorkbookExtractor;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.CompiledExpressionFactory;
import edu.vt.ras.jawb.spi.DateTimeConverter;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.ExpressionFactory;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;

/**
 * An implementation of {@link WorkbookBindingProvider} based on
 * Apache POI.
 *
 * @author Carl Harris
 */
public class ApachePoiBindingProvider implements WorkbookBindingProvider {

  private final DateTimeConverter dateTimeConverter =
      new ApachePoiDateTimeConverter();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference createCellReference(String sheetRef,
      String ref) {
    CellReference cellRef = new CellReference(ref);
    if (sheetRef != null && cellRef.getSheetName() == null) {
      cellRef = new CellReference(sheetRef + "!" + ref);
    }
    return new ApachePoiCellReference(cellRef);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkbookExtractor createExtractor(Evaluator evaluator) {
    return new ApachePoiExtractor(evaluator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExpressionFactory getExpressionFactory() {
    return new CompiledExpressionFactory(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DateTimeConverter getDateTimeConverter() {
    return dateTimeConverter;
  }

}
