/*
 * File created on Sep 29, 2014 
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
package org.soulwing.jawb;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Carl Harris
 */
public class WorkbookValidationException extends WorkbookBindingException {

  private static final long serialVersionUID = 8078398918449342806L;

  private final Set<ConstraintViolation<Object>> constraintViolations;
  
  /**
   * Constructs a new instance.
   * @param bean the subject bean
   * @param constraintViolations set of constraint violations
   */
  public WorkbookValidationException(
      Object bean, Set<ConstraintViolation<Object>> constraintViolations) {
    super("bean validation exception");
    this.constraintViolations = constraintViolations;
  }

  /**
   * Gets the {@code constraintViolations} property.
   * @return property value
   */
  public Set<ConstraintViolation<Object>> getConstraintViolations() {
    return constraintViolations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("bean validation constraint violation(s): ");
    for (ConstraintViolation<Object> violation : constraintViolations) {
      sb.append("'").append(violation.getPropertyPath())
        .append("' ").append(violation.getMessage()).append(" ");
    }
    return sb.toString();
  }

}
