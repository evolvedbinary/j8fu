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
package com.evolvedbinary.j8fu.tuple;

import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A tuple of five values
 *
 * @param <T1> The type of the first value
 * @param <T2> The type of the second value
 * @param <T3> the type of the third value
 * @param <T4> the type of the fourth value
 * @param <T5> the type of the fourth value
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class Tuple5<T1, T2, T3, T4, T5> implements Tuple {
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;
    public final T4 _4;
    public final T5 _5;

    @ConstructorProperties({"_1", "_2", "_3", "_4", "_5"})
    public Tuple5(final T1 _1, final T2 _2, final T3 _3, final T4 _4, final T5 _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public Tuple5(final T1 _1, final Tuple4<T2, T3, T4, T5> t2345) {
        this._1 = _1;
        this._2 = t2345._1;
        this._3 = t2345._2;
        this._4 = t2345._3;
        this._5 = t2345._4;
    }

    public Tuple5(final Tuple4<T1, T2, T3, T4> t1234, final T5 _5) {
        this._1 = t1234._1;
        this._2 = t1234._2;
        this._3 = t1234._3;
        this._4 = t1234._4;
        this._5 = _5;
    }


    /**
     * Get the value of _1
     *
     * Used for compatibility with javabeans
     *
     * @return value of _1
     */
    public T1 get_1() {
        return _1;
    }

    /**
     * Get the value of _2
     *
     * Used for compatibility with javabeans
     *
     * @return value of _2
     */
    public T2 get_2() {
        return _2;
    }

    /**
     * Get the value of _3
     *
     * Used for compatibility with javabeans
     *
     * @return value of _3
     */
    public T3 get_3() {
        return _3;
    }

    /**
     * Get the value of _4
     *
     * Used for compatibility with javabeans
     *
     * @return value of _4
     */
    public T4 get_4() {
        return _4;
    }

    /**
     * Get the value of _5
     *
     * Used for compatibility with javabeans
     *
     * @return value of _5
     */
    public T5 get_5() {
        return _5;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj instanceof Tuple5) {
            final Tuple5 other = (Tuple5)obj;
            if (other == this) {
                return true;
            }
            return isEqual(_1, other._1)
                    && isEqual(_2, other._2)
                    && isEqual(_3, other._3)
                    && isEqual(_4, other._4)
                    && isEqual(_5, other._5);
        }
        return false;
    }

    @Override
    public boolean isPrefixOf(final Tuple other) {
        if(other == null) {
            return false;
        } else if(other instanceof Tuple6) {
            final Tuple6 tuple6 = (Tuple6) other;
            return isEqual(_1, tuple6._1)
                    && isEqual(_2, tuple6._2)
                    && isEqual(_3, tuple6._3)
                    && isEqual(_4, tuple6._4)
                    && isEqual(_5, tuple6._5);
        } else {
            return false;
        }
    }

    @Override
    public boolean isPostfixOf(final Tuple other) {
        if(other == null) {
            return false;
        } else if(other instanceof Tuple2) {
            final Tuple2 tuple2 = (Tuple2)other;
            return isEqual(_1, tuple2._1)
                    && isEqual(_2, tuple2._2);
        } else if(other instanceof Tuple3) {
            final Tuple3 tuple3 = (Tuple3)other;
            return isEqual(_1, tuple3._1)
                    && isEqual(_2, tuple3._2)
                    && isEqual(_3, tuple3._3);
        } else if(other instanceof Tuple4) {
            final Tuple4 tuple4 = (Tuple4)other;
            return isEqual(_1, tuple4._1)
                    && isEqual(_2, tuple4._2)
                    && isEqual(_3, tuple4._3)
                    && isEqual(_4, tuple4._4);
        } else {
            return false;
        }
    }

    @Override
    public <T> T foldLeft(final T startValue, final BiFunction<T, Object, T> op) {
        return op.apply(op.apply(op.apply(op.apply(op.apply(startValue, _1), _2), _3), _4), _5);
    }

    @Override
    public <T> T foldRight(final T startValue, final BiFunction<T, Object, T> op) {
        return op.apply(op.apply(op.apply(op.apply(op.apply(startValue, _5), _4), _3), _2), _1);
    }

    /**
     * Applies the function to the tuple and returns the result.
     *
     * @param f the folding function
     *
     * @return the result
     *
     * @param <T> the type of the result
     */
    public <T> T fold(final Function<Tuple5<T1, T2, T3, T4, T5>, T> f) {
        return f.apply(this);
    }

    /**
     * Takes a function which maps the values of the tuple.
     *
     * @param f the mapping function
     *
     * @return the resultant tuple
     *
     * @param <U1> The mapped type of the first value
     * @param <U2> The mapped type of the second value
     * @param <U3> the mapped type of the third value
     * @param <U4> the mapped type of the fourth value
     * @param <U5> the mapped type of the fifth value
     */
    public <U1, U2, U3, U4, U5> Tuple5<U1, U2, U3, U4, U5> flatMap(final Function<Tuple5<T1, T2, T3, T4, T5>, Tuple5<U1, U2, U3, U4, U5>> f) {
        return f.apply(this);
    }

    @Override
    public <T0> Tuple after(final T0 _0) {
        return new Tuple6<>(_0, this);
    }

    @Override
    public <T5> Tuple before(final T5 _5) {
        return new Tuple6<>(this, _5);
    }

    @Override
    public String toString() {
        return "Tuple5(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ", " + _5 + ")";
    }
}
