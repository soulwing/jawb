/*
 * File created on Dec 23, 2013 
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
package org.soulwing.jawb.impl.expression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.soulwing.jawb.impl.expression.Value;

/**
 * Unit tests for {@link Value}.
 *
 * @author Carl Harris
 */
public class ValueTest {

  @Test
  public void testErrorValue() throws Exception {
    RuntimeException ex = new RuntimeException();
    Value value = new Value(ex);
    assertThat(value.getType(), equalTo(Value.Type.ERROR));
    assertThat(value.getValue(), sameInstance((Object) ex));
  }

  @Test
  public void testBlankValue() throws Exception {
    Value value = new Value();
    assertThat(value.getType(), equalTo(Value.Type.BLANK));
    assertThat(value.getValue(), nullValue());
    assertThat(value.isTrue(), equalTo(false));
    assertThat(value.toNumber(), equalTo(0.0));
    assertThat(value.toString(), equalTo(""));
  }
  
  @Test
  public void testValueWithNull() throws Exception {
    Value value = new Value(Value.Type.STRING, null);
    assertThat(value.getType(), equalTo(Value.Type.BLANK));
    assertThat(value.getValue(), nullValue());
  }

  @Test
  public void testValueWithEmptyString() throws Exception {
    Value value = new Value(Value.Type.STRING, "");
    assertThat(value.getType(), equalTo(Value.Type.BLANK));
    assertThat(value.getValue(), nullValue());
  }

  @Test
  public void testIsTrueWithFalse() throws Exception {
    Value value = new Value(Value.Type.BOOLEAN, false);
    assertThat(value.isTrue(), equalTo(false));
  }
  
  @Test
  public void testIsTrueWithTrue() throws Exception {
    Value value = new Value(Value.Type.BOOLEAN, true);
    assertThat(value.isTrue(), equalTo(true));
  }
  
  @Test
  public void testIsTrueWithZero() throws Exception {
    Value value = new Value(Value.Type.NUMBER, 0.0);
    assertThat(value.isTrue(), equalTo(false));
  }

  @Test
  public void testIsTrueWithNonZero() throws Exception {
    Value value = new Value(Value.Type.NUMBER, 1.0);
    assertThat(value.isTrue(), equalTo(true));
  }

  @Test
  public void testIsTrueWithEmptyString() throws Exception {
    Value value = new Value(Value.Type.STRING, "");
    assertThat(value.isTrue(), equalTo(false));
  }

  @Test
  public void testIsTrueWithNonEmptyString() throws Exception {
    Value value = new Value(Value.Type.STRING, "not empty");
    assertThat(value.isTrue(), equalTo(true));
  }

  @Test(expected = RuntimeException.class)
  public void testIsTrueWithError() throws Exception {
    RuntimeException ex = new RuntimeException();
    Value value = new Value(ex);
    value.isTrue();
  }

  @Test
  public void testToNumberWithFalse() throws Exception {
    Value value = new Value(Value.Type.BOOLEAN, false);
    assertThat(value.toNumber(), equalTo(0.0));
  }

  @Test
  public void testToNumberWithTrue() throws Exception {
    Value value = new Value(Value.Type.BOOLEAN, true);
    assertThat(value.toNumber(), equalTo(1.0));
  }

  @Test
  public void testToNumberWithNumber() throws Exception {
    Value value = new Value(Value.Type.NUMBER, 1.0);
    assertThat(value.toNumber(), equalTo(1.0));
  }

  @Test
  public void testToNumberWithNumericString() throws Exception {
    Value value = new Value(Value.Type.STRING, "1.0");
    assertThat(value.toNumber(), equalTo(1.0));
  }

  @Test(expected = NumberFormatException.class)
  public void testToNumberWithNonNumericString() throws Exception {
    Value value = new Value(Value.Type.STRING, "not a number");
    value.toNumber();
  }

  @Test(expected = RuntimeException.class)
  public void testToNumberWithError() throws Exception {
    RuntimeException ex = new RuntimeException();
    Value value = new Value(ex);
    value.toNumber();
  }

  @Test
  public void testToStringWithBoolean() throws Exception {
    Value value = new Value(Value.Type.BOOLEAN, true);
    assertThat(value.toString(), equalTo(Boolean.toString(true)));
  }

  @Test
  public void testToStringWithNumber() throws Exception {
    Value value = new Value(Value.Type.NUMBER, 1.0);
    assertThat(value.toString(), equalTo(Double.toString(1.0)));
  }

  @Test
  public void testToStringWithString() throws Exception {
    Value value = new Value(Value.Type.NUMBER, 1.0);
    assertThat(value.toString(), equalTo(Double.toString(1.0)));
  }


  @Test(expected = RuntimeException.class)
  public void testToStringWithError() throws Exception {
    RuntimeException ex = new RuntimeException();
    Value value = new Value(ex);
    value.toString();
  }

  @Test
  public void testEqualsWithSameReference() throws Exception {
    Value a = new Value();
    assertThat(a.equals(a), equalTo(true));
  }
  
  @Test
  public void testEqualsWithBlankBlank() throws Exception {
    Value a = new Value();
    Value b = new Value();
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithBooleanBoolean() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, true);
    Value b = new Value(Value.Type.BOOLEAN, true);
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithBooleanString() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, true);
    Value b = new Value(Value.Type.STRING, "true");
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithStringString() throws Exception {
    Value a = new Value(Value.Type.STRING, "string");
    Value b = new Value(Value.Type.STRING, "string");
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithNumberBoolean() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 1.0);
    Value b = new Value(Value.Type.BOOLEAN, true);
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithNumberString() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 1.0);
    Value b = new Value(Value.Type.STRING, "1.0");
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithNumberBlank() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value();
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithBooleanBlank() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, false);
    Value b = new Value();
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testEqualsWithStringBlank() throws Exception {
    Value a = new Value(Value.Type.STRING, "");
    Value b = new Value();
    assertThat(a.equals(b), equalTo(true));
    assertThat(b.equals(a), equalTo(true));
  }

  @Test
  public void testNotEqualsWithNumberNumber() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value(Value.Type.NUMBER, 1.0);
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithNumberBoolean() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value(Value.Type.BOOLEAN, true);
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithNumberString() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value(Value.Type.STRING, "string");
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithNumberBlank() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 1.0);
    Value b = new Value();
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithBooleanString() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, false);
    Value b = new Value(Value.Type.STRING, "true");
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithBooleanBlank() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, true);
    Value b = new Value();
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithStringString() throws Exception {
    Value a = new Value(Value.Type.STRING, "string1");
    Value b = new Value(Value.Type.STRING, "string2");
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testNotEqualsWithStringBlank() throws Exception {
    Value a = new Value(Value.Type.STRING, "string");
    Value b = new Value();
    assertThat(a.equals(b), equalTo(false));
    assertThat(b.equals(a), equalTo(false));
  }

  @Test
  public void testCompareNumberNumber() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 1.0);
    Value b = new Value(Value.Type.NUMBER, 2.0);
    Value c = new Value(Value.Type.NUMBER, 1.0);
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareNumberBoolean() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value(Value.Type.BOOLEAN, true);
    Value c = new Value(Value.Type.BOOLEAN, false);
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareNumberString() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 0.0);
    Value b = new Value(Value.Type.STRING, "1.0");
    Value c = new Value(Value.Type.STRING, "0.0");
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareNumberBlank() throws Exception {
    Value a = new Value(Value.Type.NUMBER, 1.0);
    Value b = new Value();
    assertThat(a.compareTo(b), equalTo(1));
    assertThat(b.compareTo(a), equalTo(-1));
  }

  @Test
  public void testCompareBooleanBoolean() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, false);
    Value b = new Value(Value.Type.BOOLEAN, true);
    Value c = new Value(Value.Type.BOOLEAN, false);
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareBooleanString() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, false);
    Value b = new Value(Value.Type.STRING, "true");
    Value c = new Value(Value.Type.STRING, "");
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareBooleanBlank() throws Exception {
    Value a = new Value(Value.Type.BOOLEAN, true);
    Value b = new Value();
    assertThat(a.compareTo(b), equalTo(1));
    assertThat(b.compareTo(a), equalTo(-1));
  }

  @Test
  public void testCompareStringString() throws Exception {
    Value a = new Value(Value.Type.STRING, "string1");
    Value b = new Value(Value.Type.STRING, "string2");
    Value c = new Value(Value.Type.STRING, "string1");
    assertThat(a.compareTo(b), equalTo(-1));
    assertThat(b.compareTo(a), equalTo(1));
    assertThat(a.compareTo(c), equalTo(0));
    assertThat(c.compareTo(a), equalTo(0));
  }

  @Test
  public void testCompareStringBlank() throws Exception {
    Value a = new Value(Value.Type.STRING, "string1");
    Value b = new Value();
    Value c = new Value(Value.Type.STRING, "");
    assertThat(a.compareTo(b), equalTo(1));
    assertThat(b.compareTo(a), equalTo(-1));
    assertThat(b.compareTo(c), equalTo(0));
    assertThat(c.compareTo(b), equalTo(0));
  }

}
