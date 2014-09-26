/*
 * File created on Dec 29, 2013 
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.annotation.CellEnumValue;
import org.soulwing.jawb.spi.BoundCell;

/**
 * A strategy for binding a cell to a field that has an enumeration type.
 *
 * @author Carl Harris
 */
public class EnumCellEvaluatorStrategy implements CellEvaluatorStrategy {

  public static final CellEvaluatorStrategy INSTANCE =
      new EnumCellEvaluatorStrategy();
  
  private final Map<Class<?>, Map<String, Object>> cache =
      new HashMap<Class<?>, Map<String, Object>>();
  
  private EnumCellEvaluatorStrategy() {    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(BoundCell cell, Class<?> targetType)
      throws WorkbookBindingException {
    try {
      Map<String, Object> valueMap = cache.get(targetType);
      if (valueMap == null) {
        valueMap = createValueMap(targetType);
        if (valueMap == null) {
          return null;
        }
        cache.put(targetType, valueMap);
      }
      Object key = cell.getValue().toString();
      Object value = valueMap.get(key);
      if (value == null) { 
        throw new WorkbookBindingException("enum " + targetType.getName()
            + " does not have a value that matches '" + key + "'");
      }
      return value;
    }
    catch (NoSuchFieldException ex) {
      throw new WorkbookBindingException(ex);
    }
  }
  
  private Map<String, Object> createValueMap(Class<?> targetType) 
      throws NoSuchFieldException {
    Map<String, Object> valueMap = new HashMap<String, Object>();
    Object[] constants = targetType.getEnumConstants();
    if (constants == null) return null;
    for (Object obj : constants) {
      Enum e = (Enum) obj;
      Field field = targetType.getField(e.name());
      CellEnumValue ann = field.getAnnotation(CellEnumValue.class);
      valueMap.put(ann != null ? ann.value() : e.name(), obj);
    }
    return valueMap;
  }
  
}
