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

import com.evolvedbinary.j8fu.tuple.*;

import java.util.*;
import java.util.function.Supplier;

import static com.evolvedbinary.j8fu.Either.Left;
import static com.evolvedbinary.j8fu.Either.Right;
import static com.evolvedbinary.j8fu.tuple.Tuple.Tuple;

/**
 * Functional utility methods that are missing from {@link java.util.Optional}
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public interface OptionalUtil {

    /**
     * Return the left Optional if present, else the right Optional.
     *
     * @param left The left of the disjunction
     * @param right The right of the disjunction
     *
     * @return left if present, else right
     *
     * @param <T> The type of the Optionals
     */
    static <T> Optional<T> or(final Optional<T> left, final Optional<T> right) {
        if(left.isPresent()) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * A lazy version of {@link #or(Optional, Optional)}.
     *
     * @param left The left of the disjunction
     * @param right A lazily evaluated supplier of Optional for the right of the disjunction,
     *              only evaluated if the left is empty
     *
     * @return left if present, else the evaluation of the the right
     *
     * @param <T> The type of the Optionals
     */
    static <T> Optional<T> or(final Optional<T> left, final Supplier<Optional<T>> right) {
        if(left.isPresent()) {
            return left;
        } else {
            return right.get();
        }
    }

    /**
     * Constructs a Tuple if both Optionals are present.
     *
     * @param _1 The first Optional
     * @param _2 The second Optional
     *
     * @param <T1> The type of the first value
     * @param <T2> The type of the second value
     *
     * @return An Optional Tuple, or an empty Optional.
     */
    static <T1, T2> Optional<Tuple2<T1, T2>> and(final Optional<T1> _1, final Optional<T2> _2) {
        return _1.flatMap(t1 -> _2.map(t2 -> Tuple(t1, t2)));
    }

    /**
     * Constructs a Tuple if all three Optionals are present.
     *
     * @param _1 The first Optional
     * @param _2 The second Optional
     * @param _3 The third Optional
     *
     * @param <T1> The type of the first value
     * @param <T2> The type of the second value
     * @param <T3> The type of the third value
     *
     * @return An Optional Tuple, or an empty Optional.
     */
    static <T1, T2, T3> Optional<Tuple3<T1, T2, T3>> and(final Optional<T1> _1, final Optional<T2> _2, final Optional<T3> _3) {
        return _1.flatMap(t1 -> _2.flatMap(t2 -> _3.map(t3 -> Tuple(t1, t2, t3))));
    }

    /**
     * Constructs a Tuple if all four Optionals are present.
     *
     * @param _1 The first Optional
     * @param _2 The second Optional
     * @param _3 The third Optional
     * @param _4 The fourth Optional
     *
     * @param <T1> The type of the first value
     * @param <T2> The type of the second value
     * @param <T3> The type of the third value
     * @param <T4> The type of the fourth value
     *
     * @return An Optional Tuple, or an empty Optional.
     */
    static <T1, T2, T3, T4> Optional<Tuple4<T1, T2, T3, T4>> and(final Optional<T1> _1, final Optional<T2> _2, final Optional<T3> _3, final Optional<T4> _4) {
        return _1.flatMap(t1 -> _2.flatMap(t2 -> _3.flatMap(t3 -> _4.map(t4 -> Tuple(t1, t2, t3, t4)))));
    }

    /**
     * Constructs a Tuple if all five Optionals are present.
     *
     * @param _1 The first Optional
     * @param _2 The second Optional
     * @param _3 The third Optional
     * @param _4 The fourth Optional
     * @param _5 The fifth Optional
     *
     * @param <T1> The type of the first value
     * @param <T2> The type of the second value
     * @param <T3> The type of the third value
     * @param <T4> The type of the fourth value
     * @param <T5> The type of the fifth value
     *
     * @return An Optional Tuple, or an empty Optional.
     */
    static <T1, T2, T3, T4, T5> Optional<Tuple5<T1, T2, T3, T4, T5>> and(final Optional<T1> _1, final Optional<T2> _2, final Optional<T3> _3, final Optional<T4> _4, final Optional<T5> _5) {
        return _1.flatMap(t1 -> _2.flatMap(t2 -> _3.flatMap(t3 -> _4.flatMap(t4 -> _5.map(t5 -> Tuple(t1, t2, t3, t4, t5))))));
    }

    /**
     * Constructs a Tuple if all six Optionals are present.
     *
     * @param _1 The first Optional
     * @param _2 The second Optional
     * @param _3 The third Optional
     * @param _4 The fourth Optional
     * @param _5 The fifth Optional
     * @param _6 The sixth Optional
     *
     * @param <T1> The type of the first value
     * @param <T2> The type of the second value
     * @param <T3> The type of the third value
     * @param <T4> The type of the fourth value
     * @param <T5> The type of the fifth value
     * @param <T6> The type of the sixth value
     *
     * @return An Optional Tuple, or an empty Optional.
     */
    static <T1, T2, T3, T4, T5, T6> Optional<Tuple6<T1, T2, T3, T4, T5, T6>> and(final Optional<T1> _1, final Optional<T2> _2, final Optional<T3> _3, final Optional<T4> _4, final Optional<T5> _5, final Optional<T6> _6) {
        return _1.flatMap(t1 -> _2.flatMap(t2 -> _3.flatMap(t3 -> _4.flatMap(t4 -> _5.flatMap(t5 -> _6.map(t6 -> Tuple(t1, t2, t3, t4, t5, t6)))))));
    }

    /**
     * If the optional is present, then its value is returned as the
     * right of a disjunction, otherwise the left of the disjunction is
     * returned with the value {@code null}.
     *
     * This is basically the same as calling {@link #toRight(Object, Optional)}
     * with {@code null} as the left argument.
     *
     * @param optional the optional to create an Either from.
     * @return The disjunction.
     */
    static <L, R> Either<L,R> toEither(final Optional<R> optional) {
        return toRight((L)null, optional);
    }

    /**
     * If the optional is present, then its value is returned as the
     * right of a disjunction, otherwise the left of the disjunction is
     * returned with the value {@code Optional.empty()}.
     *
     * This is basically the same as calling {@link #toRight(Object, Optional)}
     * with {@code Optional.empty()} as the left argument.
     *
     * @param optional the optional to create an Either from.
     * @return The disjunction.
     */
    static <L, R> Either<Optional<L>, R> toEitherOpt(final Optional<R> optional) {
        return optional.map(Either::<Optional<L>, R>Right).orElseGet(() -> Left(Optional.empty()));
    }

    /**
     * If the optional is present, then its value is returned as the
     * Left of a disjunction, otherwise the right value is returned.
     *
     * @param left The optional which could form the left
     * @param right The value for the right, if the optional is empty
     *
     * @return The disjunction.
     */
    static <L, R> Either<L,R> toLeft(final Optional<L> left, final R right) {
        return left.map(Either::<L, R>Left).orElseGet(() -> Right(right));
    }

    /**
     * A lazy version of {@link #toLeft(Optional, Object)}.
     *
     * @param left The optional which could form the left
     * @param right The value for the right, if the optional is empty
     *
     * @return The disjunction.
     */
    static <L, R> Either<L,R> toLeft(final Optional<L> left, final Supplier<R> right) {
        return left.map(Either::<L, R>Left).orElseGet(() -> Right(right.get()));
    }

    /**
     * If the optional is present, them its value is returned as the
     * Right of a disjunction, otherwise the left value is returned.
     *
     * @param left The value for the left, if the optional is empty
     * @param right The optional which could form the right
     *
     * @return The disjunction.
     */
    static <L, R> Either<L,R> toRight(final L left, final Optional<R> right) {
        return right.map(Either::<L, R>Right).orElseGet(() -> Left(left));
    }

    /**
     * A lazy version of {@link #toRight(Object, Optional)}.
     *
     * @param left The value for the left, if the optional is empty
     * @param right The optional which could form the right
     *
     * @return The disjunction.
     */
    static <L, R> Either<L,R> toRight(final Supplier<L> left, final Optional<R> right) {
        return right.map(Either::<L, R>Right).orElseGet(() -> Left(left.get()));
    }

    /**
     * Creates a List from an Optional.
     *
     * The list will contain zero of one items.
     *
     * @param optional The optional to construct the list from.
     *
     * @return the list.
     *
     * @param <T> The type of the value contained in the Optional.
     */
    static <T> List<T> toList(final Optional<T> optional) {
        return optional.map(value -> {
            final List<T> list = new ArrayList<>();
            list.add(value);
            return list;
        }).orElseGet(() -> new ArrayList<>());
    }

    /**
     * Creates an Immutable List from an Optional.
     *
     * The list will contain zero of one items.
     *
     * @param optional The optional to construct the list from.
     *
     * @return the list.
     *
     * @param <T> The type of the value contained in the Optional.
     */
    static <T> List<T> toImmutableList(final Optional<T> optional) {
        return optional.map(Arrays::asList)
            .orElse(Collections.emptyList());
    }

    /**
     * Flattens an Optional of Optional, to an Optional.
     *
     * @param optional the optional of optional
     *
     * @return the Optional
     */
    static <T> Optional<T> flatten(final Optional<Optional<T>> optional) {
        return optional.orElse(Optional.empty());
    }

    /**
     * Flattens an Optional of Optional of Optional, to an Optional.
     *
     * @param optional the optional of optional of optional
     *
     * @return the Optional
     */
    static <T> Optional<T> flatten2(final Optional<Optional<Optional<T>>> optional) {
        return optional.orElse(Optional.empty()).orElse(Optional.empty());
    }
}
