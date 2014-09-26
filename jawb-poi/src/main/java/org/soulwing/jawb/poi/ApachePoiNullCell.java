/*
 * File created on Sep 26, 2014 
 *
 * Copyright (c) 2014 Virginia Polytechnic Institute and State University
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
package org.soulwing.jawb.poi;

import java.util.Date;

import org.soulwing.jawb.spi.BoundCell;
import org.soulwing.jawb.spi.BoundCellReference;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Carl Harris
 */
public class ApachePoiNullCell implements BoundCell {

  private final BoundCellReference ref;
  
  /**
   * Constructs a new instance.
   * @param ref
   */
  public ApachePoiNullCell(BoundCellReference ref) {
    this.ref = ref;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BoundCellReference getReference() {
    return ref;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBlank() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getBooleanValue() {
    throw new IllegalStateException("blank value");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDateValue() {
    throw new IllegalStateException("blank value");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getNumericValue() {
    throw new IllegalStateException("blank value");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringValue() {
    throw new IllegalStateException("blank value");
  }

}
