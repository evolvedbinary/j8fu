/*
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

import static com.evolvedbinary.j8fu.tuple.Tuple.Tuple;
import static org.junit.Assert.*;

public class Tuple2Test {

    @Test
    public void constuctors() {
        Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
    }

    @Test
    public void get() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(1, tuple.get_1().intValue());
        assertEquals(2, tuple.get_2().intValue());
    }

    @Test
    public void testHashCode() {
        final Tuple2<Integer, Integer> tuple1 = new Tuple2<>(1,2);
        final Tuple2<Integer, Integer> tuple2 = new Tuple2<>(3,4);

        final Set<Tuple2<Integer, Integer>> set = new HashSet<>();
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
        final Tuple2<Integer, Integer> tuple1 = new Tuple2<>(1,2);
        final Tuple2<Integer, Integer> tuple2 = new Tuple2<>(3,4);

        assertNotEquals(tuple1, Tuple(1,2,3));
        assertEquals(tuple1, tuple1);
        assertNotEquals(tuple1, tuple2);
        assertEquals(tuple1, Tuple(1,2));
        assertNotEquals(tuple1, Tuple('x',2));
        assertNotEquals(tuple1, Tuple(1,'x'));

        assertEquals(Tuple(null, null), Tuple(null, null));
        assertNotEquals(tuple1, Tuple(null, null));
        assertNotEquals(Tuple(null, null), tuple1);
        assertNotEquals(tuple1, null);
    }

    @Test
    public void testEqualsArrays() {
        // array tests
        final Tuple2<int[], char[]> tupleArrays1 = new Tuple2<>(new int[] {1,2,3}, new char[]{'a','b','c'});
        final Tuple2<int[], char[]> tupleArrays2 = new Tuple2<>(new int[] {4,5,6}, new char[]{'d','e','f'});

        assertEquals(tupleArrays1, tupleArrays1);
        assertNotEquals(tupleArrays1, tupleArrays2);
        assertEquals(tupleArrays1, Tuple(new int[] {1,2,3}, new char[]{'a','b','c'}));
        assertNotEquals(tupleArrays1, Tuple(new int[] {9,2,3}, new char[]{'a','b','c'}));
        assertNotEquals(tupleArrays1, Tuple(new int[] {1,2,3}, new char[]{'z','b','c'}));

        assertNotEquals(tupleArrays1, Tuple(new int[0], new char[0]));
        assertNotEquals(tupleArrays1, Tuple(new int[0], null));
        assertNotEquals(tupleArrays1, Tuple(null, new char[0]));
        assertNotEquals(tupleArrays1, Tuple(null, null));

        assertNotEquals(Tuple(new int[0], new char[0]), tupleArrays1);
        assertNotEquals(Tuple(new int[0], null), tupleArrays1);
        assertNotEquals(Tuple(null, new char[0]), tupleArrays1);
        assertNotEquals(Tuple(null, null), tupleArrays1);
    }

    @Test
    public void isPrefixOf() {
        final Tuple2<Integer, Integer> tuple1 = new Tuple2<>(1,2);
        final Tuple2<Integer, Integer> tuple2 = new Tuple2<>(3,4);

        assertFalse(tuple1.isPrefixOf(null));
        assertFalse(tuple1.isPrefixOf(tuple1));
        assertFalse(tuple1.isPrefixOf(tuple2));

        assertFalse(tuple1.isPrefixOf(Tuple(1,2)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(3,4)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(4,5,6)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,'x',4)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(5,6,7,8)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4,5)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4,5)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,'x',4,5)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x',5)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(6,7,8,9,10)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5,6)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4,5,6)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4,5,6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,'x',4,5,6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x',5,6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,'x',6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(7,8,9,10,11,12)));
    }

    @Test
    public void isPostfixOf() {
        final Tuple2<Integer, Integer> tuple1 = new Tuple2<>(1,2);
        final Tuple2<Integer, Integer> tuple2 = new Tuple2<>(3,4);

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

        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3,4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x',4,5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,'x',5)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(6,7,8,9,10)));

        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3,4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x',4)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(5,6,7,8)));

        assertFalse(tuple1.isPostfixOf(Tuple(1,2,3)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2,3)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x',3)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,2,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(4,5,6)));

        assertFalse(tuple1.isPostfixOf(Tuple(1,2)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(3,4)));
    }

    @Test
    public void foldLeft() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(Arrays.asList(1,2), tuple.<List<Integer>>foldLeft(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void foldRight() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(Arrays.asList(2,1), tuple.<List<Integer>>foldRight(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void fold() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(3, tuple.fold(t6 -> t6._1 + t6._2).intValue());
    }

    @Test
    public void flatMap() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(Tuple(1,2), tuple.flatMap(t6 -> Tuple(t6._1 * t6._1, t6._2 * t6._1)));
    }

    @Test
    public void after() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(Tuple(0,1,2), tuple.after(0));
    }

    @Test
    public void before() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(1,2);
        assertEquals(Tuple(1,2,3), tuple.before(3));
    }

    @Test
    public void testToString() {
        final Tuple2<Integer, Integer> tuple = new Tuple2<>(11, 22);
        assertEquals("Tuple2(11, 22)", tuple.toString());
    }
}
