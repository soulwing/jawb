/*
 * File created on Dec 21, 2013 
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
package org.soulwing.jawb.poi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.poi.util.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.soulwing.jawb.WorkbookBindingContext;
import org.soulwing.jawb.WorkbookBindingException;
import org.soulwing.jawb.WorkbookExtractor;
import org.soulwing.jawb.annotation.Cell;
import org.soulwing.jawb.annotation.CellFormat;
import org.soulwing.jawb.annotation.IterateColumns;
import org.soulwing.jawb.annotation.IterateRows;
import org.soulwing.jawb.annotation.Sheet;

/**
 * Some integration tests for {@link WorkbookExtractor}.
 *
 * @author Carl Harris
 */
public class WorkbookExtractorTest {

  
  private static final String WORKBOOK_RESOURCE = "extractor-test.xlsx";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
  }
  
  @Test
  public void testBeanWithSimpleTypes() throws Exception {
    WorkbookBindingContext context = WorkbookBindingContext.newInstance(
        Person.class);
    extract(context);
  }

  @Test
  public void testBeanWithListOfBeans() throws Exception {
    WorkbookBindingContext context = WorkbookBindingContext.newInstance(
        Persons.class);
    extract(context);
  }

  @Test
  public void testBeanWithListOfBeansAndValidation() throws Exception {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    WorkbookBindingContext context = WorkbookBindingContext.newInstance(
        Persons.class);
    WorkbookExtractor extractor = context.createExtractor();
    extractor.setValidator(validatorFactory.getValidator());
    extract(extractor);
  }

  @Test
  public void testBeanNestedRowIteration() throws Exception {
    WorkbookBindingContext context = WorkbookBindingContext.newInstance(
        Cars.class);
    extract(context);
  }

  @Test
  public void testBeanNestedColumnIteration() throws Exception {
    WorkbookBindingContext context = WorkbookBindingContext.newInstance(
        Budget.class);
    extract(context);
  }



  private void extract(WorkbookBindingContext context) throws IOException,
      WorkbookBindingException {
    WorkbookExtractor extractor = context.createExtractor();
    extract(extractor);
  }

  private void extract(WorkbookExtractor extractor) throws IOException,
      WorkbookBindingException {
    InputStream inputStream = getWorkbookStream();
    try {
      Object bean = extractor.extract(inputStream);
      System.out.println(bean);
      
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }
  }
  
  private InputStream getWorkbookStream() throws IOException {
    InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream(WORKBOOK_RESOURCE);
    if (inputStream == null) {
      throw new FileNotFoundException(WORKBOOK_RESOURCE);
    }
    return inputStream;
  }
  
  public static class Persons {

    @IterateRows(count = 2)
    private List<Person> persons;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }
    
  }
  
  public static class Person {
    
    @Cell("A2")
    private String prefix;
    
    @Cell("B2")
    private String firstName;
    
    @NotNull
    @Size(min = 1)
    @Cell("C2")
    private String lastName;
    
    @Cell("D2")
    private String suffix;
    
    @Cell("E2")
    private int age;

    @Past
    @Cell("F2")
    private Date birthdate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }
    
  }
  
  public static class Cars {
    
    @IterateRows(count = 2, increment = 7)
    private List<CarsByOrigin> carsByOrigin;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

  }
  
  public static class CarsByOrigin {
    
    @Cell("D6")
    private String origin;
    @IterateRows(count = 4)
    private Car[] cars;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

  }
  
  public static class Car {

    @Cell("D8")
    private String make;
    @Cell("E8")
    private String model;
    @Cell("F8")
    private int year;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }
    
  }
 
  @Sheet("Sheet2")
  public static class Budget {
  
    @Cell("A1")
    @CellFormat("%.0f")
    private String number;
    
    @IterateColumns(count = 3, increment = 5)
    private Period[] periods;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }
    
  }
  
  public static class Period {
    
    @Cell("C2")
    private String name;
    @Cell("C3")
    private Date beginDate;
    @Cell("D3")
    private Date endDate;
    @IterateRows(count = 2)
    private PersonCost[] costs;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

  }
  
  public static class PersonCost {
  
    @Cell("$A6")
    private String name;
    @Cell("C6")
    private BigDecimal effort;
    @Cell("D6")
    private BigDecimal salary;
    @Cell("E6")
    private BigDecimal months;
    @Cell("F6")
    private BigDecimal fringes;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

  }
  
}
