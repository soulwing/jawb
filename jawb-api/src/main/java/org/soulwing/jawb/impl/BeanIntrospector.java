/*
 * File created on Dec 22, 2013 
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

import java.util.Set;

import org.soulwing.jawb.WorkbookBindingException;

/**
 * An introspector for a bound class.
 *
 * @author Carl Harris
 */
public interface BeanIntrospector {

  /**
   * Gets the parent attribute introspector, if any.
   * @return parent introspector or {@code null} if this introspector 
   *    represents the root bound class
   */
  AttributeIntrospector getParent();
  
  /**
   * Gets the bound bean type.
   * @return class of the bound bean
   */
  Class<?> getType();
  
  /**
   * Gets the attributes of the given bean.
   * @return set of attribute introspectors
   * @throws WorkbookBindingException
   */
  Set<AttributeIntrospector> getAttributes() 
      throws WorkbookBindingException;

  /**
   * Gets the applicable sheet reference for this bean.
   * @return sheet reference
   */
  String getSheetReference();
  
}
