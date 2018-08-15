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

import static com.evolvedbinary.j8fu.tuple.Tuple.Tuple;
import static org.junit.Assert.*;

public class Tuple3Test {

    @Test
    public void constuctors() {
        Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());

        tuple = new Tuple3<>(1, Tuple(2,3));
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());

        tuple = new Tuple3<>(Tuple(1,2), 3);
        assertEquals(1, tuple._1.intValue());
        assertEquals(2, tuple._2.intValue());
        assertEquals(3, tuple._3.intValue());
    }

    @Test
    public void get() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(1, tuple.get_1().intValue());
        assertEquals(2, tuple.get_2().intValue());
        assertEquals(3, tuple.get_3().intValue());
    }

    @Test
    public void testHashCode() {
        final Tuple3<Integer, Integer, Integer> tuple1 = new Tuple3<>(1,2,3);
        final Tuple3<Integer, Integer, Integer> tuple2 = new Tuple3<>(4,5,6);

        final Set<Tuple3<Integer, Integer, Integer>> set = new HashSet<>();
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
        final Tuple3<Integer, Integer, Integer> tuple1 = new Tuple3<>(1,2,3);
        final Tuple3<Integer, Integer, Integer> tuple2 = new Tuple3<>(4,5,6);

        assertNotEquals(tuple1, Tuple(1,2,3,4,5));
        assertNotEquals(tuple1, Tuple(1,2));
        assertEquals(tuple1, tuple1);
        assertNotEquals(tuple1, tuple2);
        assertEquals(tuple1, Tuple(1,2,3));

        assertNotEquals(tuple1, Tuple(1,'x',3));
        assertNotEquals(tuple1, Tuple(1,2,'x'));

        assertEquals(Tuple(null, null, null), Tuple(null, null, null));
        assertNotEquals(tuple1, Tuple(null, null, null));
        assertNotEquals(Tuple(null, null, null), tuple1);
        assertNotEquals(tuple1, null);
    }

    @Test
    public void isPrefixOf() {
        final Tuple3<Integer, Integer, Integer> tuple1 = new Tuple3<>(1,2,3);
        final Tuple3<Integer, Integer, Integer> tuple2 = new Tuple3<>(4,5,6);

        assertFalse(tuple1.isPrefixOf(null));
        assertFalse(tuple1.isPrefixOf(tuple1));
        assertFalse(tuple1.isPrefixOf(tuple2));

        assertFalse(tuple1.isPrefixOf(Tuple(1,2)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(3,4)));

        assertFalse(tuple1.isPrefixOf(Tuple(1,2,3)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,2,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(4,5,6)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,2,'x',4)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(5,6,7,8)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4,5)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4,5)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,2,'x',4,5)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x',5)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(6,7,8,9,10)));

        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5,6)));
        assertFalse(tuple1.isPrefixOf(Tuple('x',2,3,4,5,6)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,'x',3,4,5,6)));
        assertFalse(tuple1.isPrefixOf(Tuple(1,2,'x',4,5,6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,'x',5,6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,'x',6)));
        assertTrue(tuple1.isPrefixOf(Tuple(1,2,3,4,5,'x')));
        assertFalse(tuple1.isPrefixOf(Tuple(7,8,9,10,11,12)));
    }

    @Test
    public void isPostfixOf() {
        final Tuple3<Integer, Integer, Integer> tuple1 = new Tuple3<>(1,2,3);
        final Tuple3<Integer, Integer, Integer> tuple2 = new Tuple3<>(4,5,6);

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

        assertTrue(tuple1.isPostfixOf(Tuple(1,2)));
        assertFalse(tuple1.isPostfixOf(Tuple('x',2)));
        assertFalse(tuple1.isPostfixOf(Tuple(1,'x')));
        assertFalse(tuple1.isPostfixOf(Tuple(3,4)));
    }

    @Test
    public void foldLeft() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(Arrays.asList(1,2,3), tuple.<List<Integer>>foldLeft(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void foldRight() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(Arrays.asList(3,2,1), tuple.<List<Integer>>foldRight(new ArrayList<>(), (list, x) -> {list.add((Integer)x); return list;}));
    }

    @Test
    public void fold() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(6, tuple.fold(t6 -> t6._1 + t6._2 + t6._3).intValue());
    }

    @Test
    public void flatMap() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(Tuple(1,2,6), tuple.flatMap(t6 -> Tuple(t6._1 * t6._1, t6._2 * t6._1, t6._3 * t6._2)));
    }

    @Test
    public void after() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(Tuple(0,1,2,3), tuple.after(0));
    }

    @Test
    public void before() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(1,2,3);
        assertEquals(Tuple(1,2,3,4), tuple.before(4));
    }

    @Test
    public void testToString() {
        final Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(11, 22, 33);
        assertEquals("Tuple3(11, 22, 33)", tuple.toString());
    }
}
