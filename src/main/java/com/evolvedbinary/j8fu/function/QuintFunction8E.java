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
package com.evolvedbinary.j8fu.function;

import com.evolvedbinary.j8fu.Either;

import java.util.Objects;
import java.util.function.Function;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;

/**
 * Similar to {@link QuintFunctionE} but
 * permits eight statically known Exceptions to be thrown
 *
 * @param <T> Function parameter 1 type
 * @param <U> Function parameter 2 type
 * @param <V> Function parameter 3 type
 * @param <W> Function parameter 4 type
 * @param <X> Function parameter 5 type
 * @param <R> Function return type
 * @param <E1> Function throws exception type
 * @param <E2> Function throws exception type
 * @param <E3> Function throws exception type
 * @param <E4> Function throws exception type
 * @param <E5> Function throws exception type
 * @param <E6> Function throws exception type
 * @param <E7> Function throws exception type
 * @param <E8> Function throws exception type
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@FunctionalInterface
public interface QuintFunction8E<T, U, V, W, X, R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable,
        E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable, E8 extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param v the third function argument
     * @param w the fourth function argument
     * @param x the fifth function argument
     * @return the function result
     *
     * @throws E1 An exception of type {@code E1}
     * @throws E2 An exception of type {@code E2}
     * @throws E3 An exception of type {@code E3}
     * @throws E4 An exception of type {@code E4}
     * @throws E5 An exception of type {@code E5}
     * @throws E6 An exception of type {@code E6}
     * @throws E7 An exception of type {@code E7}
     * @throws E8 An exception of type {@code E8}
     */
    R apply(final T t, final U u, final V v, final W w, final X x) throws E1, E2, E3, E4, E5, E6, E7, E8;

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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function8E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7, ? extends E8> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function7E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function6E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function5E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function4E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function3E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function2E<? super R, ? extends R2, ? extends E1, ? extends E2> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final FunctionE<? super R, ? extends R2, ? extends E1> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
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
     * @throws NullPointerException if after is null
     */
    default <R2> QuintFunction8E<T, U, V, W, X, R2, E1, E2, E3, E4, E5, E6, E7, E8> andThen(final Function<? super R, ? extends R2> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w, X x) -> after.apply(apply(t, u, v, w, x));
    }

    /**
     * Returns a quint-function that applies this quint-function and returns the
     * result as an {@link Either}.
     *
     * @return a quint-function which will return either a throwable or the result {@code R}.
     */
    default QuintFunction<T, U, V, W, X, Either<Throwable, R>> toQuintFunction() {
        return (T t, U u, V v, W w, X x) -> {
            try {
                return Right(apply(t, u, v, w, x));
            } catch (final Throwable e) {
                return Left(e);
            }
        };
    }

    /**
     * Lifts a standard QuintFunction<T, R> to a QuintFunction8E<T, R, E1, E2, E3, E4, E5, E6, E7, E8>
     *
     * @param function the function to lift.
     *
     * @return the QuintFunction8E.
     *
     * @param <T> the type of the first input object to the function
     * @param <U> the type of the second input object to the function
     * @param <V> the type of the third input object to the function
     * @param <W> the type of the fourth input object to the function
     * @param <X> the type of the fifth input object to the function
     * @param <R> the type of the output object to the function
     * @throws E1 An exception of type {@code E1}
     * @throws E2 An exception of type {@code E2}
     * @throws E3 An exception of type {@code E3}
     * @throws E4 An exception of type {@code E4}
     * @throws E5 An exception of type {@code E5}
     * @throws E6 An exception of type {@code E6}
     * @throws E7 An exception of type {@code E7}
     * @throws E8 An exception of type {@code E8}
     */
    static <T, U, V, W, X, R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable, E8 extends Throwable> QuintFunction8E<T, U, V, W, X, R, E1, E2, E3, E4, E5, E6, E7, E8> lift(final QuintFunction<T, U, V, W, X, R> function) {
        return function::apply;
    }

    /**
     * Lifts an exception of type E to a QuintFunction8E<T, T, E1, E2, E3, E4, E5, E6, E7, E8>
     * which will always throw the exception.
     *
     * @param exception the exception to lift.
     *
     * @return the QuintFunction8E.
     *
     * @param <T> the type of the input object to the function
     * @param <U> the type of the second input object to the function
     * @param <V> the type of the third input object to the function
     * @param <W> the type of the fourth input object to the function
     * @param <X> the type of the fifth input object to the function
     * @param <R> the type of the output object to the function
     * @throws E1 An exception of type {@code E1}
     * @throws E2 An exception of type {@code E2}
     * @throws E3 An exception of type {@code E3}
     * @throws E4 An exception of type {@code E4}
     * @throws E5 An exception of type {@code E5}
     * @throws E6 An exception of type {@code E6}
     * @throws E7 An exception of type {@code E7}
     * @throws E8 An exception of type {@code E8}
     */
    static <T, U, V, W, X, R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable, E8 extends Throwable> QuintFunction8E<T, U, V, W, X, R, E1, E2, E3, E4, E5, E6, E7, E8> lift(final E1 exception) {
        return (t, u, v, w, x) -> { throw exception; };
    }
}
