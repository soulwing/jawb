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

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.WorkbookExtractor;
import org.soulwing.jawb.spi.Evaluator;

/**
 * A {@link WorkbookExtractor} implementation based on Apache POI.
 *
 * @author Carl Harris
 */
class ApachePoiExtractor implements WorkbookExtractor {

  private final Evaluator evaluator;
  
  /**
   * Constructs a new instance.
   * @param evaluator
   */
  public ApachePoiExtractor(Evaluator evaluator) {
    this.evaluator = evaluator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object extract(InputStream inputStream)
      throws WorkbookBindingException, IOException {
    try {
      OPCPackage pkg = OPCPackage.open(inputStream);
      XSSFWorkbook workbook = new XSSFWorkbook(pkg);
      return evaluator.evaluate(new ApachePoiWorkbook(workbook));
    }
    catch (InvalidFormatException ex) {
      throw new WorkbookBindingException(ex.getMessage(), ex);
    }
  }

}
