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
package org.soulwing.jawb.impl;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.Bound;
import org.soulwing.jawb.annotation.Sheet;

/**
 * A {@link BindingStrategy} for an attribute with a Java bean type.
 *
 * @author Carl Harris
 */
class BeanTypeBindingStrategy implements BindingStrategy {

  public static final BindingStrategy INSTANCE =
      new BeanTypeBindingStrategy();
  
  private BeanTypeBindingStrategy() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Binding createBinding(AttributeIntrospector introspector,
      EvaluatorFactory evaluatorFactory) throws WorkbookBindingException {
    
    if (introspector.getAnnotation(Bound.class) == null
        && introspector.getAnnotation(Sheet.class) == null) {
      return null;
    }    

    BeanIntrospector boundClass = evaluatorFactory.createBeanIntrospector(
        introspector, introspector.getType());
    
    return new BaseBinding(introspector.getAccessor(),
        evaluatorFactory.createBeanEvaluator(boundClass));
  }

}
