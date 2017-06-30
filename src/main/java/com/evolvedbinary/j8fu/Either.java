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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A disjoint union, more basic than but similar to {@code scala.util.Either}
 * with some influences from ScalaZ \/
 *
 * @param <L> Type of left parameter
 * @param <R> Type of right parameter
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public abstract class Either<L, R> {

    private final boolean isLeft;

    Either(final boolean isLeft) {
        this.isLeft = isLeft;
    }

    public final boolean isLeft() {
        return isLeft;
    }

    public final boolean isRight() {
        return !isLeft;
    }

    public final LeftProjection<L, R> left() {
        return new LeftProjection<>(this);
    }

    public final RightProjection<L, R> right() {
        return new RightProjection<>(this);
    }

    /**
     * Map on the right-hand-side of the disjunction
     *
     * @param f The function to map with
     * @return A disjunction after the map is applied to the right
     *
     * @param <T> The return type of the map function
     */
    @SuppressWarnings("unchecked")
    public final <T> Either<L, T> map(final Function<R, T> f) {
        if(isLeft()) {
            return (Left<L, T>)this;
        } else {
            return Right(f.apply(((Right<L, R>)this).value));
        }
    }

    /**
     * Bind through on the right-hand-side of this disjunction
     *
     * @param f the function to bind through
     * @return A disjunction after the map is applied to the right
     *
     * @param <LL> The left-type of the map function
     * @param <T> The return type of the map function
     */
    @SuppressWarnings("unchecked")
    public final <LL extends L, T> Either<LL, T> flatMap(final Function<R, Either<LL, T>> f) {
        if(isLeft) {
            return (Left<LL, T>)this;
        } else {
            return f.apply(((Right<L, R>)this).value);
        }
    }

    /**
     * Map on the left-hand-side of the disjunction
     *
     * @param f The function to map with
     * @return A disjunction after the map is applied to the left
     *
     * @param <T> The return type of the map function
     */
    @SuppressWarnings("unchecked")
    public final <T> Either<T, R> leftMap(final Function<L, T> f) {
        if(isLeft) {
            return Left(f.apply(((Left<L, R>)this).value));
        } else {
            return (Right<T, R>)this;
        }
    }

    /**
     * Catamorphism. Run the first given function if left,
     * otherwise the second given function
     *
     *
     * @param <T> The result type from performing the fold
     * @param lf A function that may be applied to the left-hand-side
     * @param rf A function that may be applied to the right-hand-side
     *
     * @return The result of evaluating the lf or rf
     *
     * @param <T> The return type of the fold function
     */
    public final <T> T fold(final Function<L, T> lf, final Function<R, T> rf) {
        if(isLeft) {
            return lf.apply(((Left<L, R>)this).value);
        } else {
            return rf.apply(((Right<L, R>)this).value);
        }
    }

    /**
     * Return the right value of this disjunction or the given default if left
     *
     * @param defaultValue A value to use if this is a Left
     * @return The right value or the defaultValue
     */
    public final R getOrElse(final R defaultValue) {
        if(isLeft()) {
            return defaultValue;
        } else {
            return ((Right<L, R>)this).value;
        }
    }

    /**
     * Return the right value of this disjunction or the given default if left
     *
     * @param lazyDefault A supplier of a value to use if this is a Left
     * @return The right value or the evaluated lazyDefault
     */
    public final R getOrElse(final Supplier<R> lazyDefault) {
        if(isLeft()) {
            return lazyDefault.get();
        } else {
            return ((Right<L, R>)this).value;
        }
    }

    /**
     * Return this if it is a right, otherwise, return the given value
     *
     * @param defaultValue A default value to use if this is a Left
     * @return This or the defaultValue
     */
    public final Either<L, R> orElse(final Either<L, R> defaultValue) {
        if(isLeft()) {
            return defaultValue;
        } else {
            return this;
        }
    }

    /**
     * Return this if it is a right, otherwise, return the given value
     *
     * @param lazyDefault A supplier of a value to use if this is a Left
     * @return This or the evaluated lazyDefault
     */
    public final Either<L, R> orElse(final Supplier<Either<L, R>> lazyDefault) {
        if(isLeft()) {
            return lazyDefault.get();
        } else {
            return this;
        }
    }

    /**
     * Return the value from the right-hand-side of this disjunction or
     * run the function on the left-hand-side
     *
     * @param <RR> The result type
     * @param lf A function that may be applied to the left-hand-side
     *
     * @return The value from the right, or a calculated value from the left
     */
    public final <RR extends R> RR valueOr(final Function<L, RR> lf) {
        if(isLeft()) {
            return lf.apply(((Left<L, R>)this).value);
        } else {
            return ((Right<L, RR>)this).value;
        }
    }

    /**
     * Flip the left/right values in this disjunction.
     *
     * @return An Either with the left and right swapped
     */
    public Either<R, L> swap() {
        return fold(Either::Right, Either::Left);
    }

    /**
     * Return an empty optional or optional with the element from the right of this disjunction.
     *
     * Useful to sweep errors under the carpet.
     *
     * @return An Optional.of(right) or else {@link Optional#EMPTY}
     */
    public Optional<R> toOptional() {
        return fold(l -> Optional.empty(), Optional::of);
    }

    /**
     * Constructor for an {@link Either.Left}
     *
     * @param value the value of the left
     * @return The {@link Either.Left} instance
     *
     * @param <L> the type of the Left of the disjunction
     * @param <R> the type of the Right of the disjunction
     */
    public static <L, R> Either<L, R> Left(final L value) {
        return new Left<>(value);
    }

    /**
     * Constructor for an {@link Either.Right}
     *
     * @param value the value of the right
     * @return The {@link Either.Right} instance
     *
     * @param <L> the type of the Left of the disjunction
     * @param <R> the type of the Right of the disjunction
     */
    public static <L, R> Either<L, R> Right(final R value) {
        return new Right<>(value);
    }

    public static class Left<L, R> extends Either<L, R> {
        final L value;
        private Left(final L value) {
            super(true);
            this.value = value;
        }

        @Override
        public String toString() {
            return "Left(" + value + ')';
        }

        @Override
        public boolean equals(final Object other) {
            if(other == null) {
                return false;
            }

            if(other instanceof Left) {
                return value.equals(((Left)other).value);
            } else{
                return value.equals(other);
            }
        }
    }

    public static class Right<L, R> extends Either<L, R> {
        final R value;
        private Right(final R value) {
            super(false);
            this.value = value;
        }

        @Override
        public String toString() {
            return "Right(" + value + ')';
        }

        @Override
        public boolean equals(final Object other) {
            if(other == null) {
                return false;
            }

            if(other instanceof Right) {
                return value.equals(((Right)other).value);
            } else{
                return value.equals(other);
            }
        }
    }

    public final class LeftProjection<L, R> {
        final Either<L, R> e;
        private LeftProjection(final Either<L, R> e) {
            this.e = e;
        }
        
        public final L get() {
            if(e.isLeft()) {
                return ((Left<L, R>)e).value;
            } else {
                throw new NoSuchElementException("Either#Left value on Right");
            }
        }
    }
    
    public final class RightProjection<L, R> {
        final Either<L, R> e;
        private RightProjection(final Either<L, R> e) {
            this.e = e;
        }
        
        public final R get() {
            if(e.isRight()) {
                return ((Right<L, R>)e).value;
            } else {
                throw new NoSuchElementException("Either#Right value on Left");
            }
        }
    }
}
