package org.soulwing.jawb.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.soulwing.jawb.WorkbookBindingException;

class MethodAttributeAccessor implements Accessor {
  
  private final Method method;

  /**
   * Constructs a new instance.
   * @param method
   */
  public MethodAttributeAccessor(Method method) {
    this.method = method;
    method.setAccessible(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return method.getReturnType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Object boundObject, Object value)
      throws WorkbookBindingException {
    try {
      method.invoke(boundObject, value);
    }
    catch (InvocationTargetException ex) {
      throw new WorkbookBindingException(ex.getCause());
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex);
    }
  }
  
}