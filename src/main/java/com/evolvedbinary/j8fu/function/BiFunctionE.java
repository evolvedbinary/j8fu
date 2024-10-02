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
package com.evolvedbinary.j8fu.function;

import com.evolvedbinary.j8fu.Either;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;

/**
 * Similar to {@link java.util.function.BiFunction} but
 * permits a single statically known Exception to be thrown
 *
 * @param <T> Function parameter 1 type
 * @param <U> Function parameter 2 type
 * @param <R> Function return type
 * @param <E> Function throws exception type
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@FunctionalInterface
public interface BiFunctionE<T, U, R, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     *
     * @throws E An exception of type {@code E}
     */
    R apply(final T t, final U u) throws E;

    /**
     * Partially applies this function to the given argument.
     *
     * @param t the first function argument
     * @return the partially applied function
     *
     * @throws E An exception of type {@code E}
     */
    default FunctionE<U, R, E> apply(final T t) throws E {
        return u -> apply(t, u);
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <R2> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     *
     * @throws NullPointerException if after is null
     */
    default <R2> BiFunctionE<T, U, R2, E> andThen(final FunctionE<? super R, ? extends R2, ? extends E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <R2> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     *
     * @throws NullPointerException if after is null
     */
    default <R2> BiFunctionE<T, U, R2, E> andThen(final Function<? super R, ? extends R2> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

    /**
     * Returns a bi-function that applies this bi-function and returns the
     * result as an {@link Either}.
     *
     * @return a bi-function which will return either the exception {@code E} or the result {@code R}.
     */
    @SuppressWarnings("unchecked")
    default BiFunction<T, U, Either<E, R>> toBiFunction() {
        return (T t, U u) -> {
            try {
                return Right(apply(t, u));
            } catch (final Throwable e) {
                return Left((E)e);
            }
        };
    }

    /**
     * Lifts a standard {@code BiFunction<T, R>} to a {@code BiFunctionE<T, R, E>}.
     *
     * @param function the function to lift.
     *
     * @return the BiFunctionE.
     *
     * @param <T> the type of the first input object to the function
     * @param <U> the type of the second input object to the function
     * @param <R> the type of the output object to the function
     * @param <E> Function throws exception type
     */
    static <T, U, R, E extends Throwable> BiFunctionE<T, U, R, E> lift(final BiFunction<T, U, R> function) {
        return function::apply;
    }

    /**
     * Lifts an exception of type {@code <E>} to a {@code BiFunctionE<T, T, E>}
     * which will always throw the exception.
     *
     * @param exception the exception to lift.
     *
     * @return the BiFunctionE.
     *
     * @param <T> the type of the input object to the function
     * @param <U> the type of the second input object to the function
     * @param <R> the type of the output object to the function
     * @param <E> Function throws exception type
     */
    static <T, U, R, E extends Throwable> BiFunctionE<T, U, R, E> lift(final E exception) {
        return (t, u) -> { throw exception; };
    }
}
