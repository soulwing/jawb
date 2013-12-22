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
package edu.vt.ras.jawb.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import edu.vt.ras.jawb.WorkbookBindingException;

/**
 * Static utility methods for collection types.
 *
 * @author Carl Harris
 */
class CollectionUtil {

  /**
   * Gets the appropriate concrete collection type for an attribute.
   * @param introspector introspector for the subject attribute
   * @return concrete collection class
   * @throws WorkbookBindingException
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends Collection> getCollectionType(
      AttributeIntrospector introspector) throws WorkbookBindingException {
    if (introspector.isAbstractType()) {
      if (List.class.isAssignableFrom(introspector.getType())) {
        return ArrayList.class;
      }
      else {
        return LinkedHashSet.class;
      }
    }
    else {
      try {
        introspector.getType().getConstructor();
        return (Class<? extends Collection>) introspector.getType();
      }
      catch (NoSuchMethodException ex) {
        throw new WorkbookBindingException("collection type "
            + introspector.getType().getName() 
            + " does not declare a public no-arg constructor");
      }
    }
  }

}
