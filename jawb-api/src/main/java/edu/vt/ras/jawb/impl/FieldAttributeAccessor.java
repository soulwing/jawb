package edu.vt.ras.jawb.impl;

import java.lang.reflect.Field;

import edu.vt.ras.jawb.WorkbookBindingException;

class FieldAttributeAccessor implements Accessor {
  
  private final Field field;

  /**
   * Constructs a new instance.
   * @param stringValue
   */
  public FieldAttributeAccessor(Field field) {
    this.field = field;
    field.setAccessible(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return field.getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Object boundObject, Object value)
      throws WorkbookBindingException {
    try {
      field.set(boundObject, value);
    }
    catch (IllegalAccessException ex) {
      throw new WorkbookBindingException(ex);
    }
  }
  
}