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

import java.util.Iterator;
import java.util.ServiceLoader;

import edu.vt.ras.jawb.impl.AnnotationBindingIntrospector;
import edu.vt.ras.jawb.impl.EvaluatorFactory;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;


/**
 * A context for binding an Excel workbook to graph of Java bean classes.
 *
 * @author Carl Harris
 */
public class WorkbookBindingContext {

  private final Evaluator evaluator; 
  private final WorkbookBindingProvider provider;
  
  /**
   * Constructs a new instance.
   * @param evaluator evaluator for the context's bound class
   * @param provider binding provider
   */
  private WorkbookBindingContext(Evaluator evaluator, 
      WorkbookBindingProvider provider) {
    this.evaluator = evaluator;
    this.provider = provider;
  }
  
  /**
   * Creates a new extractor.
   * @return extract instance
   */
  public WorkbookExtractor createExtractor() {
    return provider.createExtractor(evaluator);
  }
  
  /**
   * Creates a new workbook binding context by introspecting the 
   * given bound class.
   * @param boundClass root binding class
   * @return context
   * @throws WorkbookBindingException
   */
  public static WorkbookBindingContext newInstance(Class<?> boundClass) 
      throws WorkbookBindingException {
    WorkbookBindingProvider provider = getProvider();
    EvaluatorFactory evaluatorFactory = 
        new AnnotationBindingIntrospector(provider);
    Evaluator evaluator = evaluatorFactory.createEvaluator(
        evaluatorFactory.createBeanIntrospector(null, boundClass));
    return new WorkbookBindingContext(evaluator, provider);
  }

  /**
   * Locates a binding provider.
   * @return binding provider
   * @throws WorkbookBindingException if no provider can be found
   */
  private static WorkbookBindingProvider getProvider() 
      throws WorkbookBindingException {
    ServiceLoader<WorkbookBindingProvider> loader =
        ServiceLoader.load(WorkbookBindingProvider.class);
    
    Iterator<WorkbookBindingProvider> i = loader.iterator();
    if (!i.hasNext()) {
      throw new WorkbookBindingException("no provider available");
    }
    
    return i.next();
  }
  
}
