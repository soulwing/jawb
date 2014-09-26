/*
 * File created on Dec 24, 2013 
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
package org.soulwing.jawb.poi;

import org.junit.Test;
import org.soulwing.jawb.poi.ApachePoiDateTimeConverter;

/**
 * Unit tests for {@link ApachePoiDateTimeConverter}.
 *
 * @author Carl Harris
 */
public class ApachePoiDateTimeConverterTest {

  private ApachePoiDateTimeConverter converter = 
      new ApachePoiDateTimeConverter();
  
  @Test
  public void testConvertDate() {
    converter.convertDate("2013-12-24");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConvertDateBadFormat() {
    converter.convertDate("2013/12/24");
  }

  @Test
  public void testConvertTime() {
    converter.convertTime("23:59:59");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConvertTimeBadFormat() {
    converter.convertTime("99-99-99");
  }

}
