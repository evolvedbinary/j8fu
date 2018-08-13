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

import java.util.Optional;
import java.util.function.Supplier;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;
import static com.evolvedbinary.j8fu.Try.*;
import static org.junit.Assert.*;

public class TryTest {

    @Test
    public void try_construction() throws Throwable {
        final Try<String, Throwable> try1 = Try(() -> "hello");
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello", try1.get());

        final Try<String, Throwable> try2 = Try(() -> { throw new IllegalStateException("goodbye"); });
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }
    }

    @Test
    public void taggedTryUnchecked() throws AnException {
        final Try<String, AnException> try1 = TaggedTryUnchecked(AnException.class, () -> "hello");
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello", try1.get());

        final Try<String, AnException> try2 = TaggedTryUnchecked(AnException.class, () -> { throw new AnException("goodbye"); });
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final AnException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }

        try {
            TaggedTryUnchecked(AnException.class, () -> { throw new IllegalStateException("unexpected goodbye"); });
            fail("Invalid TaggedTryUnchecked() construction should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("unexpected goodbye", e.getMessage());
        }
    }

    @Test
    public void taggedTryEither() throws AnException {
        final Either<Throwable, Try<String, AnException>> either1 = TaggedTryEither(AnException.class, () -> "hello");
        assertTrue(either1.isRight());
        assertTrue("hello", either1.right().get().isSuccess());
        assertEquals("hello", either1.right().get().get());

        final Either<Throwable, Try<String, AnException>> either2 = TaggedTryEither(AnException.class, () -> { throw new AnException("goodbye"); });
        assertTrue(either2.isRight());
        assertTrue(either2.right().get().isFailure());
        try {
            either2.right().get().get();
            fail("Try#get() should have thrown an exception");
        } catch (final AnException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }

        final Either<Throwable, Try<String, AnException>> either3 = TaggedTryEither(AnException.class, () -> { throw new IllegalStateException("unexpected goodbye"); });
        assertTrue(either3.isLeft());
        assertTrue(either3.left().get() instanceof IllegalStateException);
        assertEquals("unexpected goodbye", either3.left().get().getMessage());
    }

    @Test
    public void taggedTryChecked() throws Throwable {
        final Try<String, AnException> try1 = TaggedTryChecked(AnException.class, () -> "hello");
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello", try1.get());

        final Try<String, AnException> try2 = TaggedTryChecked(AnException.class, () -> { throw new AnException("goodbye"); });
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final AnException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }

        try {
            TaggedTryChecked(AnException.class, () -> { throw new IllegalStateException("unexpected goodbye"); });
            fail("Invalid TaggedTryChecked() construction should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("unexpected goodbye", e.getMessage());
        }
    }

    @Test
    public void getOrElse() {
        final Try<String, Throwable> try1 = Try(() -> "hello");
        assertEquals("hello", try1.getOrElse("wrong"));

        final Try<String, Throwable> try2 = Try(() -> { throw new IllegalStateException("goodbye"); });
        assertEquals("safe", try2.getOrElse("safe"));
    }

    @Test
    public void getOrElse_supplier() {
        final Try<String, Throwable> try1 = Try(() -> "hello");
        assertEquals("hello", try1.getOrElse((Supplier<String>)() -> "wrong"));

        final Try<String, Throwable> try2 = Try(() -> { throw new IllegalStateException("goodbye"); });
        assertEquals("safe", try2.getOrElse((Supplier<String>)() -> "safe"));
    }

    @Test
    public void orElse() throws Throwable {
        final Try<? super String, ? super Throwable> try1 = Try(() -> "hello")
                .orElse(Try(() -> "hello2"));
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello", try1.get());

        final Try<? super String, ? super Throwable> try2 = Try(() -> "hello")
                .orElse(Try(() -> { throw new IllegalStateException("goodbye"); }));
        assertTrue(try2.isSuccess());
        assertFalse(try2.isFailure());
        assertEquals("hello", try2.get());

        final Try<? super Object, ? super Throwable> try3 = Try(() -> { throw new IllegalStateException("goodbye"); })
                .orElse(Try(() -> "hello2"));
        assertTrue(try3.isSuccess());
        assertFalse(try3.isFailure());
        assertEquals("hello2", try3.get());

        final Try<? super Object, ? super Throwable> try4 = Try(() -> { throw new IllegalStateException("goodbye"); })
                .orElse(Try(() -> { throw new IllegalStateException("goodbye2"); }));
        assertFalse(try4.isSuccess());
        assertTrue(try4.isFailure());
        try {
            try4.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye2", e.getMessage());
        }
    }

    @Test
    public void orElse_supplier() throws Throwable {
        final Try<? super String, ? super Throwable> try1 = Try(() -> "hello")
                .orElse(() -> Try(() -> "hello2"));
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello", try1.get());

        final Try<? super String, ? super Throwable> try2 = Try(() -> "hello")
                .orElse(() -> Try(() -> { throw new IllegalStateException("goodbye"); }));
        assertTrue(try2.isSuccess());
        assertFalse(try2.isFailure());
        assertEquals("hello", try2.get());

        final Try<? super Object, ? super Throwable> try3 = Try(() -> { throw new IllegalStateException("goodbye"); })
                .orElse(() -> Try(() -> "hello2"));
        assertTrue(try3.isSuccess());
        assertFalse(try3.isFailure());
        assertEquals("hello2", try3.get());

        final Try<? super Object, ? super Throwable> try4 = Try(() -> { throw new IllegalStateException("goodbye"); })
                .orElse(() -> Try(() -> { throw new IllegalStateException("goodbye2"); }));
        assertFalse(try4.isSuccess());
        assertTrue(try4.isFailure());
        try {
            try4.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye2", e.getMessage());
        }
    }

    @Test
    public void flatMap() throws Throwable {
        final Try<String, Throwable> try1 = Try(() -> "hello")
                .flatMap(t1 -> Try.Try(() -> "hello2"));
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello2", try1.get());

        final Try<String, Throwable> try2 = Try(() -> "hello")
                .flatMap(t1 -> Try(() -> { throw new IllegalStateException("goodbye"); }));
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }

        final Try<String, Throwable> try3 = Try.<String>Try(() -> { throw new IllegalStateException("goodbye"); })
                .flatMap(t1 -> Try(() -> "hello"));
        assertFalse(try3.isSuccess());
        assertTrue(try3.isFailure());
        try {
            try3.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }

        final Try<String, Throwable> try4 = Try.<String>Try(() -> { throw new IllegalStateException("goodbye"); })
                .flatMap(t1 -> Try(() -> { throw new IllegalStateException("goodbye2"); }));
        assertFalse(try4.isSuccess());
        assertTrue(try4.isFailure());
        try {
            try4.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }
    }

    @Test
    public void map() throws Throwable {
        final Try<String, Throwable> try1 = Try(() -> "hello")
                .map(t1 -> "hello2");
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("hello2", try1.get());

        final Try<String, Throwable> try2 = Try.<String>Try(() -> { throw new IllegalStateException("goodbye"); })
                .map(t1 -> "hello");
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("goodbye", e.getMessage());
        }
    }

    @Test
    public void fold() {
        final String try1 = Try(() -> "hello")
                .fold(f -> "failure", s -> "success");
        assertEquals("success", try1);

        final String try2 = Try(() -> { throw new IllegalStateException("goodbye"); })
                .fold(f -> "failure", s -> "success");
        assertEquals("failure", try2);
    }

    @Test
    public void toOption() {
        final Optional<String> try1 = Try(() -> "hello")
                .toOption();
        assertTrue(try1.isPresent());
        assertEquals("hello", try1.get());

        final Optional<String> try2 = Try.<String>Try(() -> { throw new IllegalStateException("goodbye"); })
                .toOption();
        assertFalse(try2.isPresent());
    }

    @Test
    public void toEither() {
        final Either<Throwable, String> try1 = Try(() -> "hello")
                .toEither();
        assertTrue(try1.isRight());
        assertEquals("hello", try1.right().get());

        final Either<Throwable, String> try2 = Try.<String>Try(() -> { throw new IllegalStateException("goodbye"); })
                .toEither();
        assertTrue(try2.isLeft());
        assertEquals("goodbye", try2.left().get().getMessage());
    }

    @Test
    public void fromEither() throws Throwable {
        final Try<String, Throwable> try1 = Try.fromEither(Right("success"));
        assertTrue(try1.isSuccess());
        assertFalse(try1.isFailure());
        assertEquals("success", try1.get());

        final Try<String, IllegalStateException> try2 = Try.fromEither(Left(new IllegalStateException("not-ok")));
        assertFalse(try2.isSuccess());
        assertTrue(try2.isFailure());
        try {
            try2.get();
            fail("Try#get() should have thrown an exception");
        } catch (final IllegalStateException e) {
            //this exception is expected!
            assertEquals("not-ok", e.getMessage());
        }
    }

    @Test
    public void uncheckedThrow() {
        final AnException anException = new AnException("something");
        try {
            UncheckedThrow.throwAsUnchecked(anException);
        } catch (final Exception e) {
            assertEquals(anException, e);
        }
    }

    private static class AnException extends Exception {
        public AnException(final String message) {
            super(message);
        }
    }
}
