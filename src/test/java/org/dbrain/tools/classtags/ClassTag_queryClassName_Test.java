/*
 * Copyright [2015] [Eric Poitras]
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.dbrain.tools.classtags;

import org.dbrain.tools.classtags.samples.complex.ComplexClass1;
import org.dbrain.tools.classtags.samples.complex.ComplexClass2;
import org.dbrain.tools.classtags.samples.complex.ComplexTag1;
import org.dbrain.tools.classtags.samples.complex.ComplexTag2;
import org.dbrain.tools.classtags.samples.inheritance.InheritedClass1;
import org.dbrain.tools.classtags.samples.inheritance.InheritedClass2;
import org.dbrain.tools.classtags.samples.inheritance.InheritedTag;
import org.dbrain.tools.classtags.samples.multiple.MultiClass;
import org.dbrain.tools.classtags.samples.multiple.MultiTag1;
import org.dbrain.tools.classtags.samples.simple.SimpleClass1;
import org.dbrain.tools.classtags.samples.simple.SimpleClass2;
import org.dbrain.tools.classtags.samples.simple.SimpleTag;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Integration tests with Classtags.
 */
public class ClassTag_queryClassName_Test {

    /**
     * Simple query.
     */
    @Test
    public void testQuerySimple1() throws Exception {
        List<String> result1 = ClassTagQuery.listClassNameByTag( SimpleTag.class );

        Assert.assertEquals( 2, result1.size() );
        Assert.assertTrue( result1.contains( SimpleClass1.class.getName() ) );
        Assert.assertTrue( result1.contains( SimpleClass2.class.getName() ) );
    }


    /**
     * Simple query.
     */
    @Test
    public void testQuerySimple2() throws Exception {
        List<ClassTags> result1 = new ClassTagQuery().filter( ct -> ct.containsTag( SimpleTag.class ) ).listAsClassTags();

        Assert.assertEquals( 2, result1.size() );
        Assert.assertTrue( result1.stream().anyMatch( ct -> ct.getClassName().equals( SimpleClass1.class.getName() ) ) );
        Assert.assertTrue( result1.stream().anyMatch( ct -> ct.getClassName().equals( SimpleClass2.class.getName() ) ) );
        Assert.assertTrue( result1.stream().allMatch( ct -> ct.containsTag( SimpleTag.class ) ) );
    }

    /**
     * Simple query.
     */
    @Test
    public void testQuerySimple3() throws Exception {
        List<Class> result1 = new ClassTagQuery().filter( ct -> ct.containsTag( SimpleTag.class ) ).list();

        Assert.assertEquals( 2, result1.size() );
        Assert.assertTrue( result1.contains( SimpleClass1.class ) );
        Assert.assertTrue( result1.contains( SimpleClass2.class ) );
    }


    /**
     * Complex query.
     */
    @Test
    public void testQueryComplex1() throws Exception {
        Map<String, ClassTags> result1 = new ClassTagQuery().filter( ct -> ct.containsTag( ComplexTag1.class ) ).mapTagsByClassName();

        Assert.assertEquals( 2, result1.size() );
        ClassTags sc1 = result1.get( ComplexClass1.class.getName() );
        ClassTags sc2 = result1.get( ComplexClass2.class.getName() );
        Assert.assertNotNull( sc1 );
        Assert.assertNotNull( sc2 );
        Assert.assertEquals( sc1.getTags().size(), 1 );
        Assert.assertEquals( sc2.getTags().size(), 2 );
        Assert.assertTrue( sc1.getTags().contains( ComplexTag1.class.getName() ) );
        Assert.assertTrue( sc2.getTags().contains( ComplexTag1.class.getName() ));
        Assert.assertTrue( sc2.getTags().contains( ComplexTag2.class.getName() ));

    }


    /**
     * Test multiple tags on the same class.
     */
    @Test
    public void testQueryMulti() throws Exception {
        List<String> result1 = ClassTagQuery.listClassNameByTag( MultiTag1.class );
        List<String> result2 = ClassTagQuery.listClassNameByTag( MultiTag1.class );
        List<String> result3 = ClassTagQuery.listClassNameByTag( MultiTag1.class );

        Assert.assertEquals( 1, result1.size() );
        Assert.assertEquals( 1, result2.size() );
        Assert.assertEquals( 1, result3.size() );
        Assert.assertTrue( result1.contains( MultiClass.class.getName() ) );
        Assert.assertTrue( result2.contains( MultiClass.class.getName() ) );
        Assert.assertTrue( result3.contains( MultiClass.class.getName() ) );
    }


    /**
     * Simple query that return one class.
     */
    @Test
    public void testInheritedTag() throws Exception {
        List<String> result = ClassTagQuery.listClassNameByTag( InheritedTag.class );

        Assert.assertEquals( 2, result.size() );
        Assert.assertTrue( result.contains( InheritedClass1.class.getName() ) );
        Assert.assertTrue( result.contains( InheritedClass2.class.getName() ) );
    }

}
