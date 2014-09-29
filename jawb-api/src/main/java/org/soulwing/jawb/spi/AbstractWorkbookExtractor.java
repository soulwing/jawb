/*
 * File created on Sep 29, 2014 
 *
 * Copyright (c) 2014 Carl Harris, Jr.
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
package org.soulwing.jawb.spi;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Validator;

import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.WorkbookExtractor;

/**
 * An abstract base for {@link WorkbookExtractor} implementations.
 *
 * @author Carl Harris
 */
public abstract class AbstractWorkbookExtractor implements WorkbookExtractor {

  private final Evaluator evaluator;
  
  private Validator validator;
  

  /**
   * Constructs a new instance.
   * @param evaluator
   */
  protected AbstractWorkbookExtractor(Evaluator evaluator) {
    this.evaluator = evaluator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Object extract(InputStream inputStream)
      throws WorkbookBindingException, IOException {
    return evaluator.evaluate(newWorkbook(inputStream, validator));
  }
  
  /**
   * Creates a new bound workbook for the given input stream.
   * @param inputStream input stream from which the workbook is to be loaded
   * @param validator a Beans Validation {@link Validator} or {@code null}
   *    if no validation is to be performed
   * @return bound workbook
   * @throws WorkbookBindingException
   * @throws IOException
   */
  protected abstract BoundWorkbook newWorkbook(InputStream inputStream, 
      Validator validator) throws WorkbookBindingException, IOException;
  
}
