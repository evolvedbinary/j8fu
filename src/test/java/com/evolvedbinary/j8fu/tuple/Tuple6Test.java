/**
 * Copyright © 2016, Evolved Binary Ltd. <tech@evolvedbinary.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.evolvedbinary.j8fu.tuple;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static com.evolvedbinary.j8fu.tuple.Tuple.Tuple;
import static org.junit.Assert.*;

public class Tuple6Test {

    @Test
    public void constuctors() {
        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());
        assertEquals(4, tuple._4.intValue());
        assertEquals(5, tuple._5.intValue());
        assertEquals(6, tuple._6.intValue());

        tuple = new Tuple6<>(1, Tuple(2,3,4,5,6));
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());
        assertEquals(4, tuple._4.intValue());
        assertEquals(5, tuple._5.intValue());
        assertEquals(6, tuple._6.intValue());

        tuple = new Tuple6<>(Tuple(1,2,3,4,5), 6);
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());
        assertEquals(4, tuple._4.intValue());
        assertEquals(5, tuple._5.intValue());
        assertEquals(6, tuple._6.intValue());
    }

    @Test
    public void get() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(1, tuple.get_1().intValue());
        assertEquals(2, tuple.get_2().intValue());
        assertEquals(3, tuple.get_3().intValue());
        assertEquals(4, tuple.get_4().intValue());
        assertEquals(5, tuple.get_5().intValue());
        assertEquals(6, tuple.get_6().intValue());
    }

    @Test
    public void testHashCode() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple1 = new Tuple6<>(1,2,3,4,5,6);
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple2 = new Tuple6<>(7,8,9,10,11,12);

        final Set<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>> set = new HashSet<>();
        set.add(tuple1);
        set.add(tuple2);
        set.add(tuple1);
        set.add(tuple2);

        assertEquals(2, set.size());
        assertTrue(set.contains(tuple1));
        assertTrue(set.contains(tuple2));
    }

    @Test
    public void testEquals() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple1 = new Tuple6<>(1,2,3,4,5,6);
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple2 = new Tuple6<>(7,8,9,10,11,12);

        assertNotEquals(tuple1, Tuple(1,2,3,4,5));
        assertEquals(tuple1, tuple1);
        assertNotEquals(tuple1, tuple2);
        assertEquals(tuple1, Tuple(1,2,3,4,5,6));

        assertNotEquals(tuple1, Tuple(1,'x',3,4,5,6));
        assertNotEquals(tuple1, Tuple(1,2,'x',4,5,6));
        assertNotEquals(tuple1, Tuple(1,2,3,'x',5,6));
        assertNotEquals(tuple1, Tuple(1,2,3,4,'x',6));
        assertNotEquals(tuple1, Tuple(1,2,3,4,5,'x'));

        assertEquals(Tuple(null, null, null, null, null, null), Tuple(null, null, null, null, null, null));
        assertNotEquals(tuple1, Tuple(null, null, null, null, null, null));
        assertNotEquals(Tuple(null, null, null, null, null, null), tuple1);
        assertNotEquals(tuple1, null);
    }

    @Test
    public void isPrefixOf() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple1 = new Tuple6<>(1,2,3,4,5,6);
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple2 = new Tuple6<>(7,8,9,10,11,12);

        assertFalse(tuple1.isPrefixOf(null));
        assertFalse(tuple1.isPrefixOf(tuple1));
        assertFalse(tuple1.isPrefixOf(tuple2));
        assertFalse(tuple1.isPrefixOf(Tuple(1,2,3,4,5)));
    }

    @Test
    public void isPostfixOf() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple1 = new Tuple6<>(1,2,3,4,5,6);
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple2 = new Tuple6<>(7,8,9,10,11,12);

        assertFalse(tuple1.isPostfixOf(null));
        assertFalse(tuple1.isPostfixOf(tuple1));
        assertFalse(tuple1.isPostfixOf(tuple2));

        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,5,6)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3,4,5,6)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3,4,5,6)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x',4,5,6)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,'x',5,6)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,'x',6)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,5,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(7,8,9,10,11,12)));

        assertTrue(tuple1.isPostfixOf(Tuple(1,2,3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x',4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,'x',5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(6,7,8,9,10)));

        assertTrue(tuple1.isPostfixOf(Tuple(1,2,3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x',4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(5,6,7,8)));

        assertTrue(tuple1.isPostfixOf(Tuple(1,2,3)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(4,5,6)));

        assertTrue(tuple1.isPostfixOf(Tuple(1,2)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(3,4)));
    }

    @Test
    public void foldLeft() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(Arrays.asList(1,2,3,4,5,6), tuple.<List<Integer>>foldLeft(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void foldRight() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(Arrays.asList(6,5,4,3,2,1), tuple.<List<Integer>>foldRight(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void fold() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(21, tuple.fold(t6 -> t6._1 + t6._2 + t6._3 + t6._4 + t6._5 + t6._6).intValue());
    }

    @Test
    public void flatMap() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        assertEquals(Tuple(1,2,6,12,20,30), tuple.flatMap(t6 -> Tuple(t6._1 * t6._1, t6._2 * t6._1, t6._3 * t6._2, t6._4 * t6._3, t6._5 * t6._4, t6._6 * t6._5)));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void after() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        tuple.after(0);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void before() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(1,2,3,4,5,6);
        tuple.before(7);
    }

    @Test
    public void testToString() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = new Tuple6<>(11, 22, 33, 44, 55, 66);
        assertEquals("Tuple6(11, 22, 33, 44, 55, 66)", tuple.toString());
    }
}
