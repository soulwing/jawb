/*
 * File created on Dec 24, 2013 
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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * A listener for expression errors.
 *
 * @author Carl Harris
 */
public class ExpressionErrorListener extends BaseErrorListener {

  private final List<String> errors = new ArrayList<String>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void syntaxError(Recognizer<?, ?> recognizer,
      Object offendingSymbol, int line, int charPositionInLine, String msg,
      RecognitionException e) {
    errors.add(String.format("@%d:%d %s", line, charPositionInLine, msg));
  }

  /**
   * Tests whether one or more errors occurred.
   * @return {@code true} if one or more errors occurred
   */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }
  
  /**
   * Gets an exception that represents that errors that occurred.
   * @return exception
   */
  public RuntimeException getException() {
    StringBuilder sb = new StringBuilder();
    sb.append("expression syntax errors(s): ");
    for (String error : errors) {
      sb.append(error).append('\n');
    }
    return new IllegalArgumentException(sb.toString());
  }
  
}
