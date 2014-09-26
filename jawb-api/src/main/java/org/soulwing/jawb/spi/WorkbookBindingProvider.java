/*
 * File created on Dec 20, 2013 
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
package org.soulwing.jawb.spi;

import org.soulwing.jawb.WorkbookExtractor;

/**
 * A service provider for workbook binding.
 *
 * @author Carl Harris
 */
public interface WorkbookBindingProvider {
  
  /**
   * Creates a cell reference from a string representation.
   * @param sheetRef string representation of a sheet reference
   * @param ref string representation of a cell reference
   * @return cell reference object
   */
  BoundCellReference createCellReference(String sheetRef,
      String ref);

  
  /**
   * Creates an extractor.
   * @param evaluator evaluator that will be used to extract and bind 
   *    the Java model
   * @return extractor object
   */
  WorkbookExtractor createExtractor(Evaluator evaluator);

  /**
   * Gets the provider's expression factory.
   * @return
   */
  ExpressionFactory getExpressionFactory();
  
  /**
   * Gets the provider's date/time converter.
   * @return date/time converter
   */
  DateTimeConverter getDateTimeConverter();
  
}
