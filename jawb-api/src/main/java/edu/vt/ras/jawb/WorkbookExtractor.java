/*
 * File created on Dec 21, 2013 
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
package edu.vt.ras.jawb;

import java.io.IOException;
import java.io.InputStream;

/**
 * An object that extracts the information from an Excel workbook 
 * to create a graph of Java bean instances.
 *
 * @author Carl Harris
 */
public interface WorkbookExtractor {

  /**
   * Extracts the Excel workbook presented by the given stream to create
   * a Java bean instance and its constituent descendants.
   * 
   * @param inputStream encoded workbook representation
   * @return an instance of the class bound to the associated binding context
   * @throws WorkbookBindingException
   * @throws IOException
   */
  Object extract(InputStream inputStream) 
      throws WorkbookBindingException, IOException;
  
}
