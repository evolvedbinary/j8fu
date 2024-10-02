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

import com.evolvedbinary.j8fu.function.FunctionE;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;
import static com.evolvedbinary.j8fu.Either.valueOrThrow;
import static java.util.function.Function.identity;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;


public class EitherTest {

    @Test
    public void right() {
        assertFalse(Right(1).isLeft());
        assertTrue(Right(1).isRight());
        assertEquals("Right(123)", Right(123).toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void right_invalidProjection() {
        Right(1).left().get();
    }

    @Test
    public void left() {
        assertTrue(Left(1).isLeft());
        assertFalse(Left(1).isRight());
        assertEquals("Left(123)", Left(123).toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void left_invalidProjection() {
        Left(1).right().get();
    }

    @Test
    public void equality() {
        assertEquals(Left(false), Left(false));
        assertNotEquals(Left(false), Left(true));
        assertNotEquals(Left(false), Right(true));
        assertNotEquals(Left(false), null);
        assertNotEquals(Left(false), "other object");

        assertEquals(Right(true), Right(true));
        assertNotEquals(Right(true), Right(false));
        assertNotEquals(Right(true), null);
        assertNotEquals(Right(true), "other object");
    }

    @Test
    public void map() {
        Either<Integer, String> either1 = Right("hello");
        either1 = either1.map(str -> str + " world");
        assertEquals(Right("hello world"), either1);

        Either<Integer, String> either2 = Left(1234);
        either2 = either2.map(str -> str + " world");
        assertEquals(Left(1234), either2);
    }

    @Test
    public void leftMap() {
        Either<Double, String> either1 = Right("hello");
        either1 = either1.leftMap(Math::sqrt);
        assertEquals(Right("hello"), either1);

        Either<Double, String> either2 = Left(100d);
        either2 = either2.leftMap(Math::sqrt);
        assertEquals(Left(10d), either2);
    }

    @Test
    public void flatMap() {
        Either<Integer, String> either1 = Right("hello");
        Either<Integer, String> either2 = Right("world");
        final Either<Integer, String> eitherResult1 = either1.flatMap(str1 -> either2.map(str2 -> str1 + " " + str2));
        assertEquals(Right("hello world"), eitherResult1);

        Either<Integer, String> either3 = Left(10);
        Either<Integer, String> either4 = Right("hello");
        final Either<Integer, String> eitherResult2 = either3.flatMap(str1 -> either4.map(str2 -> str1 + " " + str2));
        assertEquals(Right("hello"), either1);
    }

    @Test
    public void fold() {

        final Function<Integer, String> intToString = i -> i.toString();
        final Function<Double, String> doubleToString = d -> d.toString();

        final Either<Integer, String> either1 = Right("hello");
        final String result1 = either1.fold(intToString, identity());
        assertEquals("hello", result1);

        final Either<Integer, String> either2 = Left(52);
        final String result2 = either2.fold(intToString, identity());
        assertEquals("52", result2);

        final Either<Integer, Double> either3 = Right(100.786);
        final String result3 = either3.fold(intToString, doubleToString);
        assertEquals("100.786", result3);

        final Either<Integer, Double> either4 = Left(1234);
        final String result4 = either4.fold(intToString, doubleToString);
        assertEquals("1234", result4);
    }

    @Test
    public void foldE() throws Throwable {
        final FunctionE<Integer, String, Throwable> intToString = i -> i.toString();
        final FunctionE<Double, String, Throwable> doubleToString = d -> d.toString();

        final Either<Integer, String> either1 = Right("hello");
        final String result1 = either1.foldE(intToString, FunctionE.identity());
        assertEquals("hello", result1);

        final Either<Integer, String> either2 = Left(52);
        final String result2 = either2.foldE(intToString, FunctionE.identity());
        assertEquals("52", result2);

        final Either<Integer, Double> either3 = Right(100.786);
        final String result3 = either3.foldE(intToString, doubleToString);
        assertEquals("100.786", result3);

        final Either<Integer, Double> either4 = Left(1234);
        final String result4 = either4.foldE(intToString, doubleToString);
        assertEquals("1234", result4);
    }

    @Test
    public void foldE_leftException() throws Throwable {
        final FunctionE<Integer, String, Throwable> intException = FunctionE.lift(new Exception("intException"));

        final Either<Integer, String> either1 = Right("hello");
        final String result1 = either1.foldE(intException, FunctionE.identity());
        assertEquals("hello", result1);

        final Either<Integer, String> either2 = Left(52);
        try {
            either2.foldE(intException, FunctionE.identity());
            fail("We should have thrown an exception folding on the left");
        } catch (final Exception e) {
            //this exception is expected!
            assertEquals("intException", e.getMessage());
        }
    }

    @Test
    public void foldE_RightException() throws Throwable {
        final FunctionE<String, Integer, Throwable> intException = FunctionE.lift(new Exception("intException"));

        final Either<Integer, String> either1 = Right("hello");
        try {
            either1.foldE(FunctionE.identity(), intException);
            fail("We should have thrown an exception folding on the right");
        } catch (final Exception e) {
            //this exception is expected!
            assertEquals("intException", e.getMessage());
        }

        final Either<Integer, String> either2 = Left(52);
        final Integer result2 = either2.foldE(FunctionE.identity(), intException);
        assertEquals(52, result2.intValue());
    }

    @Test
    public void getOrElse() {
        final Either<Integer, String> either1 = Right("hello");
        assertEquals("hello", either1.getOrElse("goodbye"));

        final Either<Integer, String> either2 = Left(99);
        assertEquals("goodbye", either2.getOrElse("goodbye"));
    }

    @Test
    public void getOrElse_lazy() {
        final Either<Integer, String> either1 = Right("hello");
        assertEquals("hello", either1.getOrElse(() -> "goodbye"));

        final Either<Integer, String> either2 = Left(99);
        assertEquals("goodbye", either2.getOrElse(() -> "goodbye"));
    }

    @Test
    public void orElse() {
        final Either<Integer, String> either1 = Right("hello");
        final Either<Integer, String> either2 = Right("goodbye");
        assertEquals(Right("hello"), either1.orElse(either2));

        final Either<Integer, String> either3 = Left(123);
        final Either<Integer, String> either4 = Right("goodbye");
        assertEquals(Right("goodbye"), either3.orElse(either4));

        final Either<Integer, String> either5 = Right("hello");
        final Either<Integer, String> either6 = Left(123);
        assertEquals(Right("hello"), either5.orElse(either6));

        final Either<Integer, String> either7 = Left(123);
        final Either<Integer, String> either8 = Left(789);
        assertEquals(Left(789), either7.orElse(either8));
    }

    @Test
    public void orElse_lazy() {
        final Either<Integer, String> either1 = Right("hello");
        assertEquals(Right("hello"), either1.orElse(() -> Right("goodbye")));

        final Either<Integer, String> either3 = Left(123);
        assertEquals(Right("goodbye"), either3.orElse(() -> Right("goodbye")));

        final Either<Integer, String> either5 = Right("hello");
        assertEquals(Right("hello"), either5.orElse(() -> Left(123)));

        final Either<Integer, String> either7 = Left(123);
        assertEquals(Left(789), either7.orElse(() -> Left(789)));
    }

    @Test
    public void valueOr() {
        final Either<Integer, String> either1 = Right("hello");
        assertEquals("hello", either1.valueOr(left -> left.toString()));

        final Either<Integer, String> either3 = Left(123);
        assertEquals("123", either3.valueOr(left -> left.toString()));

        final Either<Integer, String> either5 = Right("hello");
        assertEquals("hello", either5.valueOr(left -> left.toString()));

        final Either<Integer, String> either7 = Left(789);
        assertEquals("789", either7.valueOr(left -> left.toString()));
    }

    @Test
    public void valueOrThrow_instance() throws IllegalStateException {
        final Either<IllegalStateException, String> either1 = Right("hello");
        assertEquals("hello", either1.valueOrThrow(identity()));

        final IllegalStateException t = new IllegalStateException("some exception");
        final Either<IllegalStateException, String> either2 = Left(t);
        try {
            either2.valueOrThrow(identity());
            fail("Expected IllegalStateException");
        } catch (final IllegalStateException e) {
            assertEquals(t, e);
        }
    }

    @Test
    public void valueOrThrow_static() throws IllegalStateException {
        final Either<IllegalStateException, String> either1 = Right("hello");
        assertEquals("hello", valueOrThrow(either1));

        final IllegalStateException t = new IllegalStateException("some exception");
        final Either<IllegalStateException, String> either2 = Left(t);
        try {
            valueOrThrow(either2);
            fail("Expected IllegalStateException");
        } catch (final IllegalStateException e) {
            assertEquals(t, e);
        }
    }

    @Test
    public void swap() {
        final Either<Integer, String> either1 = Right("hello");
        final Either<String, Integer> either2 = either1.swap();
        assertTrue(either2.isLeft());

        final Either<Integer, String> either3 = Left(123);
        final Either<String, Integer> either4 = either3.swap();
        assertFalse(either4.isLeft());
    }

    @Test
    public void toOptional() {
        final Either<Integer, String> either1 = Right("hello");
        final Optional<String> optional1 = either1.toOptional();
        assertTrue(optional1.isPresent());
        assertEquals("hello", optional1.get());

        final Either<Integer, String> either2 = Left(5678);
        final Optional<String> optional2 = either2.toOptional();
        assertFalse(optional2.isPresent());
    }

    @Test
    public void tryCatch() {
        final Either<Throwable, String> either1 = Either.tryCatch(() -> "ok");
        assertTrue(either1.isRight());
        assertEquals("ok", either1.right().get());

        final Either<Throwable, String> either2 = Either.tryCatch(() -> { throw new IllegalArgumentException("not-ok"); });
        assertTrue(either2.isLeft());
        assertTrue(either2.left().get() instanceof IllegalArgumentException);
        assertEquals("not-ok", either2.left().get().getMessage());
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
