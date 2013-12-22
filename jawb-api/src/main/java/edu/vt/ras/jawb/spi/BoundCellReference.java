/*
 * File created on Dec 18, 2013 
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
package edu.vt.ras.jawb.spi;

/**
 * A reference to a bound cell.
 *
 * @author Carl Harris
 */
public interface BoundCellReference {

  /**
   * Applies sheet, row, and column offsets to the receiver to produce a new
   * cell reference.
   * @param sheetOffset sheet offset
   * @param rowOffset row offset
   * @param columnOffset column offset
   * @param workbook workbook context
   * @return new cell reference that has been biased according to the given
   *    offsets
   */
  BoundCellReference applyBias(int sheetOffset, int rowOffset, 
      int columnOffset, BoundWorkbook workbook);
  
}
