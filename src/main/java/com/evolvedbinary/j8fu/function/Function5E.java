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

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Similar to {@link FunctionE} but
 * permits five statically known Exceptions to be thrown
 *
 * @param <T> Function parameter type
 * @param <R> Function return type
 * @param <E1> Function throws exception type
 * @param <E2> Function throws exception type
 * @param <E3> Function throws exception type
 * @param <E4> Function throws exception type
 * @param <E5> Function throws exception type
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@FunctionalInterface
public interface Function5E<T, R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     *
     * @throws E1 An exception of type {@code E1}
     * @throws E2 An exception of type {@code E2}
     * @throws E3 An exception of type {@code E3}
     * @throws E4 An exception of type {@code E4}
     * @throws E5 An exception of type {@code E5}
     */
    R apply(final T t) throws E1, E2, E3, E4, E5;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function5E)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final Function5E<? super V, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunction5E<? super A, ? super B, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunction5E<? super A, ? super B, ? super C, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function4E)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final Function4E<? super V, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunction4E<? super A, ? super B, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunction4E<? super A, ? super B, ? super C, ? extends T, ? extends E1, ? extends E2, ? extends E3, ? extends E4> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function3E)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final Function3E<? super V, ? extends T, ? extends E1, ? extends E2, ? extends E3> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunction3E<? super A, ? super B, ? extends T, ? extends E1, ? extends E2, ? extends E3> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunction3E<? super A, ? super B, ? super C, ? extends T, ? extends E1, ? extends E2, ? extends E3> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function2E)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final Function2E<? super V, ? extends T, ? extends E1, ? extends E2> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunction2E<? super A, ? super B, ? extends T, ? extends E1, ? extends E2> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunction2E<? super A, ? super B, ? super C, ? extends T, ? extends E1, ? extends E2> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(FunctionE)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final FunctionE<? super V, ? extends T, ? extends E1> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunctionE<? super A, ? super B, ? extends T, ? extends E1> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunctionE<? super A, ? super B, ? super C, ? extends T, ? extends E1> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(Function)
     */
    default <V> Function5E<V, R, E1, E2, E3, E4, E5> compose(final Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B> BiFunction5E<A, B, R, E1, E2, E3, E4, E5> compose(final BiFunction<? super A, ? super B, ? extends T> before) {
        Objects.requireNonNull(before);
        return (a, b) -> apply(before.apply(a, b));
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <A> the type of input to the first argument of the {@code before}
     *           function, and to the composed function
     * @param <B> the type of input to the second argument of the {@code before}
     *           function, and to the composed function
     * @param <C> the type of input to the third argument of the {@code before}
     *           function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     */
    default <A, B, C> TriFunction5E<A, B, C, R, E1, E2, E3, E4, E5> compose(final TriFunction<? super A, ? super B, ? super C, ? extends T> before) {
        Objects.requireNonNull(before);
        return (a, b, c) -> apply(before.apply(a, b, c));
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
     *
     * @see #compose(Function5E)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final Function5E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
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
     *
     * @see #compose(Function4E)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final Function4E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3, ? extends E4> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
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
     *
     * @see #compose(Function3E)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final Function3E<? super R, ? extends R2, ? extends E1, ? extends E2, ? extends E3> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
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
     *
     * @see #compose(Function2E)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final Function2E<? super R, ? extends R2, ? extends E1, ? extends E2> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
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
     *
     * @see #compose(FunctionE)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final FunctionE<? super R, ? extends R2, ? extends E1> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
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
     *
     * @see #compose(Function)
     */
    default <R2> Function5E<T, R2, E1, E2, E3, E4, E5> andThen(final Function<? super R, ? extends R2> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @param <E1> Function throws exception type
     * @param <E2> Function throws exception type
     * @param <E3> Function throws exception type
     * @param <E4> Function throws exception type
     * @param <E5> Function throws exception type
     * @return a function that always returns its input argument
     */
    static <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> Function5E<T, T, E1, E2, E3, E4, E5> identity() {
        return t -> t;
    }
}
