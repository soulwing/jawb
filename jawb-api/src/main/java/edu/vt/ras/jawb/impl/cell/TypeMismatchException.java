/*
 * File created on Dec 17, 2013 
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
package edu.vt.ras.jawb.impl.cell;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.spi.BoundCellReference;
import edu.vt.ras.jawb.spi.BoundCellValue;


/**
 * An exception thrown when a cell bound to an attribute has an incompatible
 * data type.
 *
 * @author Carl Harris
 */
public class TypeMismatchException extends WorkbookBindingException {

  private static final long serialVersionUID = 1456249282208759934L;

  /**
   * Constructs a new instance.
   * @param ref bound cell reference
   * @param cellType source cell type
   * @param attributeType target binding type
   */
  public TypeMismatchException(BoundCellReference ref, 
      BoundCellValue.Type cellType, Class<?> attributeType) {
    super(String.format(
        "cell of type %s at location %s cannot be bound to attribute of type %s",
        cellType, ref, attributeType.getName())); 
  }
  
}
