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
package com.evolvedbinary.j8fu;

import org.junit.Test;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class EitherTest {

    @Test
    public void equality() {
        assertEquals(Left(false), Left(false));
        assertNotEquals(Left(false), Left(true));
        assertNotEquals(Left(false), Right(true));

        assertEquals(Right(true), Right(true));
        assertNotEquals(Right(true), Right(false));
    }

    @Test
    public void mapGenerics() {
        final Either<Throwable, SubClassA> either1 = Right(new SubClassA());
        final Either<Throwable, Either<Throwable, Other>> result = either1.map(this::mapper1);
        assertTrue(result.isRight());
    }

    @Test
    public void flatMapGenerics() {
        final Either<Throwable, SubClassA> either1 = Right(new SubClassA());
        final Either<Throwable, Other> result = either1.flatMap(this::mapper1);
        assertTrue(result.isRight());
    }

    private Either<Throwable, Other> mapper1(final Base base) {
        return Right(new Other());
    }

    public interface Base {}

    public class SubClassA implements Base {}

    public class Other {}
}
