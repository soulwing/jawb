/*
 * File created on Dec 18, 2013 
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
package edu.vt.ras.jawb.impl;

import java.util.Collection;

import edu.vt.ras.jawb.WorkbookBindingException;
import edu.vt.ras.jawb.impl.cell.CellEvaluator;
import edu.vt.ras.jawb.spi.Evaluator;
import edu.vt.ras.jawb.spi.WorkbookBindingProvider;
import edu.vt.ras.jawb.spi.WorkbookIterator;

/**
 * A {@link BindingFactory} that utilizes annotations to identify
 * binding characteristics.
 *
 * @author Carl Harris
 */
public class AnnotationBindingIntrospector implements EvaluatorFactory {

  private static final BindingStrategy[] strategies = {
    ArrayOfSimpleTypeBindingStrategy.INSTANCE,
    CollectionOfSimpleTypeBindingStrategy.INSTANCE,
    SimpleTypeBindingStrategy.INSTANCE,
    ArrayTypeBindingStrategy.INSTANCE,
    CollectionTypeBindingStrategy.INSTANCE,
    BeanTypeBindingStrategy.INSTANCE
  };
  
  private final WorkbookBindingProvider provider;
  
  /**
   * Constructs a new instance.
   * @param provider
   */
  public AnnotationBindingIntrospector(WorkbookBindingProvider provider) {
    this.provider = provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createBeanEvaluator(BeanIntrospector boundClass)
      throws WorkbookBindingException {
    BeanEvaluator evaluator = new BeanEvaluator(boundClass.getType());
    for (AttributeIntrospector introspector : boundClass.getAttributes()) {
      Binding binding = createBinding(introspector);
      if (binding != null) {
        evaluator.addBinding(binding);
      }
    }
    return evaluator;
  }

  /**
   * Creates a binding for an attribute.
   * @param introspector introspector for the subject attribute
   * @return binding or {@code null} if no binding is needed for the
   *    given attribute
   * @throws WorkbookBindingException
   */
  private Binding createBinding(AttributeIntrospector introspector)
      throws WorkbookBindingException {
    
    for (BindingStrategy strategy : strategies) {
      Binding binding = strategy.createBinding(introspector, this);
      if (binding != null) return binding;
    }
    return null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createArrayEvaluator(Class<?> targetType,
      Evaluator elementEvaluator, WorkbookIterator iterator) {
    return new ArrayEvaluator(targetType, elementEvaluator, iterator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createCollectionEvaluator(
      Class<? extends Collection> targetType, Evaluator elementEvaluator,
      WorkbookIterator iterator) {
    return new CollectionEvaluator(targetType, elementEvaluator, iterator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Evaluator createCellEvaluator(String sheetRef,
      String ref, Class<?> targetType) {
    return new CellEvaluator(provider.createCellReference(sheetRef, ref), 
        targetType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BeanIntrospector createBeanIntrospector(AttributeIntrospector parent,
      Class<?> boundClass) {
    return new BeanClassIntrospector(parent, boundClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkbookIterator createSheetIterator(int count, int increment) {
    return new SheetIterator(count, increment, provider);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkbookIterator createRowIterator(int count, int increment) {
    return new RowIterator(count, increment, provider);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkbookIterator createColumnIterator(int count, int increment) {
    return new ColumnIterator(count, increment, provider);
  }
  
}
