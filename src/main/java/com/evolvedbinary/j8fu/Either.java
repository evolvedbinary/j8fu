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

import com.evolvedbinary.j8fu.function.FunctionE;
import com.evolvedbinary.j8fu.function.SupplierE;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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
     * Catamorphism. Run the first given function if left,
     * otherwise the second given function
     *
     * @param <T> The result type from performing the fold
     * @param lf A function that may be applied to the left-hand-side
     * @param rf A function that may be applied to the right-hand-side
     *
     * @return The result of evaluating the lf or rf
     *
     * @param <T> The return type of the fold function
     * @param <LE> The exception type of the left-hand-side function.
     * @param <RE> The exception type of the right-hand-side function.
     *
     * @throws LE The exception from the left-hand-side function.
     * @throws RE The exception from the right-hand-side function.
     */
    public final <T, LE extends Throwable, RE extends Throwable> T foldE(
            final FunctionE<L, T, LE> lf, final FunctionE<R, T, RE> rf) throws LE, RE {
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
    @SuppressWarnings("unchecked")
    public final <RR extends R> RR valueOr(final Function<L, RR> lf) {
        if(isLeft()) {
            return lf.apply(((Left<L, R>)this).value);
        } else {
            return ((Right<L, RR>)this).value;
        }
    }

    /**
     * Return the value from the right-hand-side of this disjunction or
     * run the function on the left-hand-side to generate an exception
     * which will be thrown.
     *
     * This function is designed for integration with legacy Java code which throws
     * exceptions.
     *
     * @param <LT> The type of the exception
     * @param lf A function that may be applied to the left-hand-side to generate an exception
     *
     * @return The value from the right
     * @throws LT if a left value is present
     */
    public final <LT extends Throwable> R valueOrThrow(final Function<L, LT> lf) throws LT {
        if(isLeft()) {
            throw lf.apply(((Left<L, R>)this).value);
        } else {
            return ((Right<L, R>)this).value;
        }
    }

    /**
     * If this is a Right, ensures that the right value of this disjunction satisfies the given predicate,
     * or returns left with the given value.
     *
     * @param left the value to use for the left if the predicate is invalidated
     * @param rightPredicate the predicate to validate on the right of the disjunction
     *
     * @return either `this` if the predicate holds, or else a disjunction with the left value of {@code left}
     */
    public final Either<L, R> ensure(final L left, final Predicate<R> rightPredicate) {
        if (isLeft()) {
            return this;
        } else {
            if (rightPredicate.test(this.right().get())) {
                return this;
            } else {
                return Left(left);
            }
        }
    }

    /**
     * If this is a Right, ensures that the right value of this disjunction satisfies the given predicate,
     * or returns left with the given value.
     *
     * @param left the value to use for the left if the predicate is invalidated
     * @param rightPredicate the predicate to validate on the right of the disjunction
     *
     * @return either `this` if the predicate holds, or else a disjunction with the left value of {@code left}
     */
    public final Either<L, R> ensure(final Supplier<L> left, final Predicate<R> rightPredicate) {
        if (isLeft()) {
            return this;
        } else {
            if (rightPredicate.test(this.right().get())) {
                return this;
            } else {
                return Left(left.get());
            }
        }
    }

    /**
     * If this is a Left, ensures that the left value of this disjunction satisfies the given predicate,
     * or returns right with the given value.
     *
     * @param leftPredicate the predicate to validate on the left of the disjunction
     * @param right the value to use for the right if the predicate is invalidated
     *
     * @return either `this` if the predicate holds, or else a disjunction with the right value of {@code right}
     */
    public final Either<L, R> ensure(final Predicate<L> leftPredicate, final R right) {
        if (isLeft()) {
            if (leftPredicate.test(this.left().get())) {
                return this;
            } else {
                return Right(right);
            }
        } else {
            return this;
        }
    }

    /**
     * If this is a Left, ensures that the left value of this disjunction satisfies the given predicate,
     * or returns right with the given value.
     *
     * @param leftPredicate the predicate to validate on the left of the disjunction
     * @param right the value to use for the right if the predicate is invalidated
     *
     * @return either `this` if the predicate holds, or else a disjunction with the right value of {@code right}
     */
    public final Either<L, R> ensure(final Predicate<L> leftPredicate, final Supplier<R> right) {
        if (isLeft()) {
            if (leftPredicate.test(this.left().get())) {
                return this;
            } else {
                return Right(right.get());
            }
        } else {
            return this;
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
     * Executes the attempt inside a try/catch and returns a
     * disjunction representing the result.
     *
     * This function is designed for integration with legacy Java code which throws
     * exceptions.
     *
     * @param <R> the type of the Right of the disjunction
     * @param attempt the code to attempt to execute.
     *
     * @return Left if an exception occurs, otherwise Right.
     */
    public static <R> Either<Throwable, R> tryCatch(final SupplierE<R, Throwable> attempt) {
        try {
            return Right(attempt.get());
        } catch (final Throwable e) {
            return Left(e);
        }
    }

    /**
     * Return the value from the right-hand-side of the disjunction or
     * throw an exception of the left-hand-side.
     *
     * This function is designed for integration with legacy Java code which throws
     * exceptions.
     *
     * @param <L> the throwable type of the Left of the disjunction
     * @param <R> the type of the Right of the disjunction
     * @param either a disjunction
     *
     * @return The value from the right
     *
     * @throws L if a left value is present
     */
    public static <L extends Throwable, R> R valueOrThrow(final Either<L, R> either) throws L {
        if(either.isLeft()) {
            throw ((Left<L, R>)either).value;
        } else {
            return ((Right<L, R>)either).value;
        }
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

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + 0;  // + 0 for Left
            return result;
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

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + 1;  // + 1 for Right
            return result;
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
