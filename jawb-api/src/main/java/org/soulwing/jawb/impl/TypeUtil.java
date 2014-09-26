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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Static utility methods for working with Java types.
 *
 * @author Carl Harris
 */
public class TypeUtil {

  public static final Class<?> numericTypes[] = {
    double.class, float.class, long.class, int.class, 
    short.class, byte.class 
  };

  /**
   * Test whether a given type is a numeric primitive type.
   * @param type the subject type
   * @return {@code true} if {@code type} is a numeric primitive
   */
  public static boolean isNumericPrimitive(Class<?> type) {
    return Arrays.asList(numericTypes).contains(type);
  }
  
  /**
   * Creates a wrapper object for a double value cast to a given primitive
   * type.
   * @param argType desired primitive type 
   * @param value the value to wrap
   * @return a wrapper of a type that corresponds to {@link argType}.
   */
  public static Object wrapValue(Class<?> argType, double value) {
    if (argType.equals(double.class)) {
      return value;
    }
    if (argType.equals(float.class)) {
      return (float) value;
    }
    if (argType.equals(long.class)) {
      return (long) value;
    }
    if (argType.equals(int.class)) {
      return (int) value;
    }
    if (argType.equals(short.class)) {
      return (short) value;
    }
    if (argType.equals(byte.class)) {
      return (byte) value;
    }
    throw new IllegalArgumentException("unrecognized type");
  }

  /**
   * Finds a static {@code valueOf} method on the target type that supports 
   * an single primitive argument with the best possible downcast from
   * {@code double}.
   * @param targetType target type in which to find the method
   * @return a suitable {@code valueOf} method or {@code null} if no such
   *    method exists
   */
  public static Method findValueOfMethod(Class<?> targetType) {
    for (Class<?> argType : numericTypes) {
      Method method = findValueOfMethod(targetType, argType);
      if (method != null) return method;
    }
    return null;
  }
  
  /**
   * Finds a static {@code valueOf} method for in a given type that takes
   * the given type as an argument.
   * @param targetType target type to search
   * @param argType argument type for the method
   * @return method or {@code null} if no such method exists
   */
  public static Method findValueOfMethod(Class<?> targetType, Class<?> argType) {
    try {
      Method method = targetType.getMethod("valueOf", argType);
      if ((method.getModifiers() & Modifier.STATIC) == 0) {
        return null;
      }
      return method;
    }
    catch (NoSuchMethodException ex) {
      return null;
    }
  }

  /**
   * Tests whether the given type is a supported simple type.
   * @param type the type to test
   * @return {@code true} if {@code type} is a supported simple type
   */
  public static boolean isSupportedSimpleType(Class<?> type) {
    if (String.class.equals(type)) return true;
    if (Boolean.class.equals(type)) return true;
    if (boolean.class.equals(type)) return true;
    if (Calendar.class.isAssignableFrom(type)) return true;
    if (Date.class.isAssignableFrom(type)) return true;
    if (Enum.class.isAssignableFrom(type)) return true;
    if (TypeUtil.isNumericPrimitive(type)) return true;
    if (TypeUtil.findValueOfMethod(type) != null) return true;
    return false;
  }

}
