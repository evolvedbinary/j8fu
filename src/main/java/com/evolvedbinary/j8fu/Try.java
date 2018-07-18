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

import com.evolvedbinary.j8fu.function.SupplierE;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;

/**
 * Provides functional abstractions for attempting
 * to execute some code inside a try/catch.
 *
 * Some inspiration was taken from {@code scala.util.Try}
 * and ScalaZ.
 *
 * @param <T> Type of result of executing the code
 * @param <E> Type of the exception which may be thrown be executing the code.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public abstract class Try<T, E extends Throwable> {

    /**
     * Try to execute a function.
     *
     * @param attempt the function to execute.
     *
     * @return Success, or Failure if an exception occurs.
     *
     * @param <T> Type of result of executing the function.
     */
    public static <T> Try<T, Throwable> Try(final SupplierE<T, Throwable> attempt) {
        try {
            return new Success<>(attempt.get());
        } catch (final Throwable e) {
            return new Failure<>(e);
        }
    }

    /**
     * Try to execute a function.
     *
     * Similar to #Try(SupplierE)} but
     * attempts to type the excepted exception.
     *
     * NOTE: this method will throw an exception
     * if the exception is not of the expected type,
     * even though it is not declared as a checked exception.
     *
     * @param exceptionTag the class of the exception
     *            that is expected.
     * @param attempt the function to execute.
     *
     * @return Success, or Failure if the expected exception occurs.
     *
     * @param <T> Type of result of executing the function.
     * @param <E> Type of the expected exception.
     *
     * //@throws Throwable if the exception is of an unexpected type.
     */
    @SuppressWarnings("unchecked")
    public static <T, E extends Throwable> Try<T, E> TaggedTryUnchecked(final Class<E> exceptionTag, final SupplierE<T, E> attempt) {
        try {
            return new Success<>(attempt.get());
        } catch (final Throwable e) {
            if (e.getClass().isAssignableFrom(exceptionTag)) {
                return new Failure<>((E)e);
            } else {
                UncheckedThrow.throwAsUnchecked(e);
                return null; // will be thrown above!
            }
        }
    }

    /**
     * Try to execute a function.
     *
     * Similar to #Try(SupplierE)} but
     * attempts to type the excepted exception.
     *
     * @param exceptionTag the class of the exception
     *            that is expected.
     * @param attempt the function to execute.
     *
     * @return Success, or Failure if the expected exception occurs.
     *
     * @param <T> Type of result of executing the function.
     * @param <E> Type of the expected exception.
     *
     * @throws Throwable if the exception is of an unexpected type.
     */
    @SuppressWarnings("unchecked")
    public static <T, E extends Throwable> Try<T, E> TaggedTryChecked(final Class<E> exceptionTag, final SupplierE<T, E> attempt) throws Throwable {
        try {
            return new Success<>(attempt.get());
        } catch (final Throwable e) {
            if (e.getClass().isAssignableFrom(exceptionTag)) {
                return new Failure<>((E)e);
            } else {
               throw e;
            }
        }
    }


    /**
     * Try to execute a function.
     *
     * Similar to {@link #TaggedTryUnchecked(Class, SupplierE)} but
     * contains any unexpected exception on the left of an Either.
     *
     * An alternative approach is to use {@link Either#tryCatch(SupplierE)}.
     *
     * @param exceptionTag the class of the exception
     *            that is expected.
     * @param attempt the function to execute.
     *
     * @return Left if an unexpected exception occurs,
     *     or, Right of Success or Failure if the expected exception occurs.
     *
     * @param <T> Type of result of executing the function.
     * @param <E> Type of the expected exception.
     */
    @SuppressWarnings("unchecked")
    public static <T, E extends Throwable> Either<Throwable, Try<T, E>> TaggedTryEither(final Class<E> exceptionTag, final SupplierE<T, E> attempt) {
        try {
            return Right(new Success<>(attempt.get()));
        } catch (final Throwable e) {
            if (e.getClass().isAssignableFrom(exceptionTag)) {
                return Right(new Failure<>((E)e));
            } else {
                return Left(e);
            }
        }
    }

    /**
     * Constructs a Try from a disjunction.
     *
     * @param either the disjunction to construct the Try from.
     *
     * @return Left for Failure, or Right for Success.
     *
     * @param <L> Type of Failure parameter
     * @param <R> Type of Success parameter
     */
    public static <L extends Throwable, R> Try<R, L> fromEither(final Either<L, R> either) {
        return either.fold(Try::Failure, Try::Success);
    }

    /**
     * Returns true if the {@link Try} is a {@link Success}.
     *
     * @return true if Success, false otherwise.
     */
    public abstract boolean isSuccess();

    /**
     * Returns true if the {@link Try} is a {@link Failure}.
     *
     * @return true if Failure, false otherwise.
     */
    public abstract boolean isFailure();

    /**
     * Get's the value if Success, or otherwise
     * returns the default value.
     *
     * @param defaultValue the default value.
     *
     * @return the success value, otherwise the default value.
     *
     * @param <U> the type of the default value.
     */
    public abstract <U extends T> T getOrElse(final U defaultValue);

    /**
     * Get's the value if Success, or otherwise
     * returns the default value;
     * A lazy version of {@link #getOrElse(Object)}.
     *
     * @param defaultValue the default value.
     *
     * @return the success value, otherwise the default value.
     *
     * @param <U> the type of the default value.
     */
    public abstract <U extends T> T getOrElse(final Supplier<U> defaultValue);

    /**
     * Get's the Try if Success, or otherwise
     * returns the default value.
     *
     * @param defaultTry the default value.
     *
     * @return this if Success, otherwise the default try.
     */
    public abstract Try<? super T, ? super E> orElse(final Try<? super T, ? super E> defaultTry);

    /**
     * Get's the Try if Success, or otherwise
     * returns the default value;
     * A lazy version of {@link #orElse(Try)}.
     *
     * @param defaultTry the default value.
     *
     * @return this if Success, otherwise the default try.
     */
    public abstract Try<? super T, ? super E> orElse(final Supplier<Try<? super T, ? super E>> defaultTry);

    /**
     * Gets the value if Success, or otherwise throws
     * the Exception.
     *
     * @return the value if Success.
     *
     * @throws E if Failure.
     */
    public abstract T get() throws E;

    /**
     * Applies the function if Success.
     *
     * @param function a mapping function.
     *
     * @return the result of the mapping function if Success,
     *     otherwise {@code this}.
     *
     * @param <U> The type of the mapped value
     * @param <EE> The type of the mapped exception
     */
    public abstract <U, EE extends Throwable> Try<U, EE> flatMap(final Function<T, Try<U, EE>> function);

    /**
     * Applies the function if Success.
     *
     * @param function a mapping function.
     *
     * @return the result of the mapping function if Success,
     *     otherwise {@code this}.
     *
     * @param <U> The type of the mapped value
     * @param <EE> The type of the mapped exception
     */
    public abstract <U, EE extends Throwable> Try<U, EE> map(final Function<T, U> function);

    /**
     * Catamorphism. Run the first given function if Failure,
     * otherwise the second given function if Success.
     *
     * @param <U> The result type from performing the fold
     * @param ff A function that may be applied to the Failure
     * @param sf A function that may be applied to the Success
     *
     * @return The result of evaluating the ff or sf
     *
     * @param <U> The return type of the fold function
     */
    public abstract <U> U fold(final Function<E, U> ff, final Function<T, U> sf);

    /**
     * Returns an Optional from this Try.
     *
     * @return {@link Optional#EMPTY} if Failure,
     *     otherwise Optional value.
     */
    public abstract Optional<T> toOption();

    /**
     * Returns an Either from this Try.
     *
     * @return an Either, where the Left represents
     *     Failure and the Right represents Success.
     */
    public abstract Either<E, T> toEither();

    /**
     * Constructor function for {@link Failure}.
     *
     * @param exception the exception.
     *
     * @return A Failure of the exception.
     *
     * @param <T> Type of result of executing the code
     * @param <E> Type of the exception which may be thrown be executing the code.
     */
    private static <T, E extends Throwable> Failure<T, E> Failure(final E exception) {
        return new Failure<>(exception);
    }

    public static class Failure<T, E extends Throwable> extends Try<T, E> {
        private final E exception;

        private Failure(final E exception) {
            this.exception = exception;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public <U extends T> T getOrElse(final U defaultValue) {
            return defaultValue;
        }

        @Override
        public <U extends T> T getOrElse(final Supplier<U> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public Try<? super T, ? super E> orElse(final Try<? super T, ? super E> defaultTry) {
            return defaultTry;
        }

        @Override
        public Try<? super T, ? super E> orElse(final Supplier<Try<? super T, ? super E>> defaultTry) {
            return defaultTry.get();
        }

        @Override
        public T get() throws E {
            throw exception;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <U, EE extends Throwable> Try<U, EE> flatMap(final Function<T, Try<U, EE>> function) {
            return (Try<U, EE>)this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <U, EE extends Throwable> Try<U, EE> map(final Function<T, U> function) {
            return (Try<U, EE>)this;
        }

        @Override
        public <U> U fold(final Function<E, U> ff, final Function<T, U> sf) {
            return ff.apply(exception);
        }

        @Override
        public Optional<T> toOption() {
            return Optional.empty();
        }

        @Override
        public Either<E, T> toEither() {
            return Left(exception);
        }
    }

    /**
     * Constructor function for {@link Success}.
     *
     * @param value the value.
     *
     * @return A Success of the value.
     *
     * @param <T> Type of result of executing the code
     * @param <E> Type of the exception which may be thrown be executing the code.
     */
    private static <T, E extends Throwable> Success<T, E> Success(final T value) {
        return new Success<>(value);
    }

    public static final class Success<T, E extends Throwable> extends Try<T, E> {
        private final T value;

        private Success(final T value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public <U extends T> T getOrElse(final U defaultValue) {
            return value;
        }

        @Override
        public <U extends T> T getOrElse(final Supplier<U> defaultValue) {
            return value;
        }

        @Override
        public Try<? super T, ? super E> orElse(final Try<? super T, ? super E> defaultTry) {
            return this;
        }

        @Override
        public Try<? super T, ? super E> orElse(final Supplier<Try<? super T, ? super E>> defaultTry) {
            return this;
        }

        @Override
        public T get() throws E {
            return value;
        }

        @Override
        public <U, EE extends Throwable> Try<U, EE> flatMap(final Function<T, Try<U, EE>> function) {
            return function.apply(value);
        }

        @Override
        public <U, EE extends Throwable> Try<U, EE> map(final Function<T, U> function) {
            return Success(function.apply(value));
        }

        @Override
        public <U> U fold(final Function<E, U> ff, final Function<T, U> sf) {
            return sf.apply(value);
        }

        @Override
        public Optional<T> toOption() {
            return Optional.of(value);
        }

        @Override
        public Either<E, T> toEither() {
            return Right(value);
        }
    }

    /**
     * Convenience class for throwing
     * a checked exception as an
     * unchecked exception.
     */
    private static final class UncheckedThrow {
        private UncheckedThrow(){}

        public static void throwAsUnchecked(final Throwable ex){
            UncheckedThrow.<RuntimeException>throwsUnchecked(ex);
        }

        @SuppressWarnings("unchecked")
        private static <T extends Throwable> void throwsUnchecked(final Throwable toThrow) throws T {
            throw (T) toThrow;
        }
    }
}