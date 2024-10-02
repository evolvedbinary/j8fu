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
package com.evolvedbinary.j8fu;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static java.util.Optional.of;
import static java.util.Optional.empty;
import static com.evolvedbinary.j8fu.tuple.Tuple.*;
import static org.junit.Assert.fail;

public class OptionalUtilTest {

    private final static String LEFT = "left";
    private final static String RIGHT = "right";

    private final static Optional<String> OPT_LEFT = of(LEFT);
    private final static Optional<String> OPT_RIGHT = of(RIGHT);
    private final static Optional<String> EMPTY_OPT_LEFT = empty();
    private final static Optional<String> EMPTY_OPT_RIGHT = empty();

    @Test
    public void or() {
        assertEquals(EMPTY_OPT_RIGHT, OptionalUtil.or(EMPTY_OPT_LEFT, EMPTY_OPT_RIGHT));
        assertEquals(OPT_LEFT, OptionalUtil.or(OPT_LEFT, EMPTY_OPT_RIGHT));
        assertEquals(OPT_LEFT, OptionalUtil.or(OPT_LEFT, OPT_RIGHT));
        assertEquals(OPT_RIGHT, OptionalUtil.or(EMPTY_OPT_LEFT, OPT_RIGHT));
    }

    @Test
    public void or_supplier() {
        assertEquals(EMPTY_OPT_RIGHT, OptionalUtil.or(EMPTY_OPT_LEFT, () -> EMPTY_OPT_RIGHT));
        assertEquals(OPT_LEFT, OptionalUtil.or(OPT_LEFT, () -> EMPTY_OPT_RIGHT));
        assertEquals(OPT_LEFT, OptionalUtil.or(OPT_LEFT, () -> OPT_RIGHT));
        assertEquals(OPT_RIGHT, OptionalUtil.or(EMPTY_OPT_LEFT, () -> OPT_RIGHT));
    }

    @Test
    public void and() {
        assertEquals(empty(), OptionalUtil.and(empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), empty()));

        assertEquals(empty(), OptionalUtil.and(empty(), of(2)));

        assertEquals(of(Tuple(1,2)), OptionalUtil.and(of(1), of(2)));
    }

    @Test
    public void and_3() {
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), empty()));

        assertEquals(empty(), OptionalUtil.and(empty(), empty(), of(3)));
        assertEquals(empty(), OptionalUtil.and(empty(), of(2), of(3)));

        assertEquals(of(Tuple(1,2,3)), OptionalUtil.and(of(1), of(2), of(3)));
    }

    @Test
    public void and_4() {
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), empty()));

        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), of(4)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), of(3), of(4)));
        assertEquals(empty(), OptionalUtil.and(empty(), of(2), of(3), of(4)));

        assertEquals(of(Tuple(1,2,3,4)), OptionalUtil.and(of(1), of(2), of(3), of(4)));
    }

    @Test
    public void and_5() {
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), of(4), empty()));

        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty(), of(5)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), of(4), of(5)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), of(3), of(4), of(5)));
        assertEquals(empty(), OptionalUtil.and(empty(), of(2), of(3), of(4), of(5)));

        assertEquals(of(Tuple(1,2,3,4,5)), OptionalUtil.and(of(1), of(2), of(3), of(4), of(5)));
    }

    @Test
    public void and_6() {
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), empty(), empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), empty(), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), empty(), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), of(4), empty(), empty()));
        assertEquals(empty(), OptionalUtil.and(of(1), of(2), of(3), of(4), of(5), empty()));

        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty(), empty(), of(6)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), empty(), of(5), of(6)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), empty(), of(4), of(5), of(6)));
        assertEquals(empty(), OptionalUtil.and(empty(), empty(), of(3), of(4), of(5), of(6)));
        assertEquals(empty(), OptionalUtil.and(empty(), of(2), of(3), of(4), of(5), of(6)));

        assertEquals(of(Tuple(1,2,3,4,5,6)), OptionalUtil.and(of(1), of(2), of(3), of(4), of(5), of(6)));
    }

    @Test
    public void toEither() {
        assertTrue(OptionalUtil.toEither(EMPTY_OPT_RIGHT).isLeft());
        assertNull(OptionalUtil.toEither(EMPTY_OPT_RIGHT).left().get());

        assertTrue(OptionalUtil.toEither(OPT_RIGHT).isRight());
        assertEquals(OPT_RIGHT.get(), OptionalUtil.toEither(OPT_RIGHT).right().get());
    }

    @Test
    public void toEitherOpt() {
        assertTrue(OptionalUtil.toEitherOpt(EMPTY_OPT_RIGHT).isLeft());
        assertEquals(Optional.empty(), OptionalUtil.toEitherOpt(EMPTY_OPT_RIGHT).left().get());

        assertTrue(OptionalUtil.toEitherOpt(OPT_RIGHT).isRight());
        assertEquals(OPT_RIGHT.get(), OptionalUtil.toEitherOpt(OPT_RIGHT).right().get());
    }

    @Test
    public void toLeft() {
        assertTrue(OptionalUtil.toLeft(EMPTY_OPT_LEFT, RIGHT).isRight());
        assertEquals(RIGHT, OptionalUtil.toLeft(EMPTY_OPT_LEFT, RIGHT).right().get());

        assertTrue(OptionalUtil.toLeft(OPT_LEFT, RIGHT).isLeft());
        assertEquals(OPT_LEFT.get(), OptionalUtil.toLeft(OPT_LEFT, RIGHT).left().get());
    }

    @Test
    public void toLeft_supplier() {
        assertTrue(OptionalUtil.toLeft(EMPTY_OPT_LEFT, () -> RIGHT).isRight());
        assertEquals(RIGHT, OptionalUtil.toLeft(EMPTY_OPT_LEFT, () -> RIGHT).right().get());

        assertTrue(OptionalUtil.toLeft(OPT_LEFT, () -> RIGHT).isLeft());
        assertEquals(OPT_LEFT.get(), OptionalUtil.toLeft(OPT_LEFT, () -> RIGHT).left().get());
    }

    @Test
    public void toRight() {
        assertTrue(OptionalUtil.toRight(LEFT, EMPTY_OPT_RIGHT).isLeft());
        assertEquals(LEFT, OptionalUtil.toRight(LEFT, EMPTY_OPT_RIGHT).left().get());

        assertTrue(OptionalUtil.toRight(LEFT, OPT_RIGHT).isRight());
        assertEquals(OPT_RIGHT.get(), OptionalUtil.toRight(LEFT, OPT_RIGHT).right().get());
    }

    @Test
    public void toRight_supplier() {
        assertTrue(OptionalUtil.toRight(() -> LEFT, EMPTY_OPT_RIGHT).isLeft());
        assertEquals(LEFT, OptionalUtil.toRight(() -> LEFT, EMPTY_OPT_RIGHT).left().get());

        assertTrue(OptionalUtil.toRight(() -> LEFT, OPT_RIGHT).isRight());
        assertEquals(OPT_RIGHT.get(), OptionalUtil.toRight(() -> LEFT, OPT_RIGHT).right().get());
    }

    @Test
    public void toList() {
        assertTrue(OptionalUtil.toList(empty()).isEmpty());
        assertEquals(Arrays.asList(RIGHT), OptionalUtil.toList(OPT_RIGHT));

        // should be a mutable list
        final List<String> list = OptionalUtil.toList(OPT_RIGHT);
        list.add(LEFT);
        assertEquals(Arrays.asList(RIGHT, LEFT), list);
    }

    @Test
    public void toImmutableList() {
        assertTrue(OptionalUtil.toImmutableList(empty()).isEmpty());
        assertEquals(Arrays.asList(RIGHT), OptionalUtil.toImmutableList(OPT_RIGHT));

        // should be an immutable list
        final List<String> list = OptionalUtil.toImmutableList(OPT_RIGHT);
        try {
            list.add(LEFT);
            fail("We should not be able to modify an Immutable list");
        } catch (final UnsupportedOperationException e) {
            //no-op - this exception is expected!
        }
    }
}
