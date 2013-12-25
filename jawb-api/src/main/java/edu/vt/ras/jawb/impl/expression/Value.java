/*
 * File created on Dec 23, 2013 
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
package edu.vt.ras.jawb.impl.expression;


/**
 * An immutable typed value for an expression.
 *
 * @author Carl Harris
 */
public class Value implements Comparable<Value> {

  /**
   * An enumeration of data types.
   */
  public enum Type {
    BLANK,
    BOOLEAN,
    NUMBER,
    STRING,
    ERROR;
  }
  
  private final Type type;
  private final Object value;
  
  /**
   * Constructs a new instance of type BLANK.
   */
  public Value() {
    this(Type.BLANK, null);
  }
  
  /**
   * Constructs a new instance of type ERROR.
   * @param ex any runtime exception
   */
  public Value(RuntimeException ex) {
    this(Type.ERROR, ex);
  }
  
  /**
   * Constructs a new instance.
   * @param type data type
   * @param value value
   */
  public Value(Type type, Object value) {
    if (value == null) {
      type = Type.BLANK;
    }
    if (type == Type.STRING && ((String) value).isEmpty()) {
      type = Type.BLANK;
      value = null;
    }
    this.type = type;
    this.value = value;
  }

  /**
   * Gets the data type of this operand.
   * @return data type
   */
  public Type getType() {
    return type;
  }

  /**
   * Gets the value of this operand.
   * @return result value
   */
  public Object getValue() {
    return value;
  }
  
  /**
   * Coerces the receiver to a boolean value. 
   * @return {@code true} if the receiver evaluates to {@code true}
   */
  public boolean isTrue() {
    switch (type) {
      case BLANK:
        return false;
      case BOOLEAN:
        return (Boolean) value;
      case NUMBER:
        return ((Double) value) != 0.0;
      case STRING:
        return !((String) value).isEmpty();
      case ERROR:
        throw (RuntimeException) value;
      default:
        throw new IllegalStateException("unrecognized data type");
    }
  }

  /**
   * Coerces the receiver to a number.
   * @return numeric value of the receiver
   * @throws NumberFormatException if the value cannot be coerced to a number
   */
  public double toNumber() {
    switch (type) {
      case BLANK:
        return 0.0;
      case BOOLEAN:
        return (boolean) value ? 1.0 : 0.0;
      case NUMBER:
        return (double) value;
      case STRING:
        try {
          return Double.valueOf((String) value);
        }
        catch (NumberFormatException ex) { 
          throw new NumberFormatException(
              "cannot convert string to number: " + value);
        }
      case ERROR:
        throw (RuntimeException) value;
      default:
        throw new IllegalStateException("unrecognized type");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (type == Type.ERROR) throw (RuntimeException) value;
    if (type == Type.BLANK) return "";
    return value.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    if (value == null) return 0;
    return 17*type.hashCode() + value.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Value)) return false;
    Value that = (Value) obj;
    if (this.type == that.type) {
      if (this.type == Type.BLANK) return true;
      return this.value.equals(that.value);
    }
    if (this.type == Type.NUMBER || that.type == Type.NUMBER) {
      try {
        return this.toNumber() == that.toNumber();
      }
      catch (NumberFormatException ex) {
        return false;
      }
    }
    if (this.type == Type.BOOLEAN || that.type == Type.BOOLEAN) {
      return this.isTrue() == that.isTrue();
    }
    return this.toString().equals(that.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Value that) {
    if (this == that) return 0;
    if (this.type == Type.BLANK || that.type == Type.BLANK) {
      if (this.type == that.type) return 0;
      if (this.equals(that)) return 0;
      return this.type == Type.BLANK ? -1 : 1;
    }
    if (this.type == Type.NUMBER || that.type == Type.NUMBER) {
      return (int) Math.signum(this.toNumber() - that.toNumber());
    }
    if (this.type == Type.BOOLEAN || that.type == Type.BOOLEAN) {
      boolean a = this.isTrue();
      boolean b = that.isTrue();
      if (a == b) return 0;
      if (a) return 1;
      return -1;
    }
    return this.toString().compareTo(that.toString());
  }
  
}
