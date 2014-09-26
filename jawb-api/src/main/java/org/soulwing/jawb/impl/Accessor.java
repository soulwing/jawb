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
package org.soulwing.jawb.impl;

import org.soulwing.jawb.WorkbookBindingException;

/**
 * An object that provides access to an attribute of some bound object.
 *
 * @author Carl Harris
 */
public interface Accessor {

  /**
   * Gets the data type for the attribute associated with this accessor.
   * @return data type
   */
  Class<?> getType();
  
  /**
   * Sets the value of the attribute represented by the receiver on an
   * instance of a bound object.
   * @param boundObject the target bound object
   * @param value the value to set
   * @throws WorkbookBindingException
   */
  void setValue(Object boundObject, Object value) 
      throws WorkbookBindingException;
  
}
