/*
 * File created on Dec 25, 2013 
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
package org.soulwing.jawb.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Loggers for diagnostic logging.
 * <p>
 * Provider implementations should use these loggers appropriately 
 * in preference to creating provider-specific categories.
 *
 * @author Carl Harris
 */
public interface Loggers {

  /**
   * Prefix for provider-specific categories.
   */
  String PROVIDER = Loggers.class.getPackage().getName();
  
  /**
   * Prefix for general categories.
   */
  String BASE = PROVIDER.substring(0, PROVIDER.indexOf(".spi"));
      
  /**
   * Logger for messages regarding cell evaluation. 
   */
  Logger EVALUATION = LoggerFactory.getLogger(BASE + ".evaluation");
  
  /**
   * Logger for expression evaluation.
   */
  Logger EXPRESSION = LoggerFactory.getLogger(BASE + ".expression");
  
  /**
   * Logger for messages regarding iterators.
   */
  Logger ITERATION = LoggerFactory.getLogger(BASE + ".iteration");
  
}
