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
package com.evolvedbinary.j8fu.tuple;

import java.beans.ConstructorProperties;
import java.util.function.BiFunction;

/**
 * A tuple of three values
 *
 * @param <T1> The type of the first value
 * @param <T2> The type of the second value
 * @param <T3> the type of the third value
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class Tuple3<T1, T2, T3> implements Tuple {
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;

    @ConstructorProperties({"_1", "_2", "_3"})
    public Tuple3(final T1 _1, final T2 _2, final T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public Tuple3(final T1 _1, final Tuple2<T2, T3> t23) {
        this._1 = _1;
        this._2 = t23._1;
        this._3 = t23._2;
    }

    public Tuple3(final Tuple2<T1, T2> t12, final T3 _3) {
        this._1 = t12._1;
        this._2 = t12._2;
        this._3 = _3;
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

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Tuple3 && obj != null) {
            final Tuple3 other = (Tuple3)obj;
            return isEqual(_1, other._1)
                    && isEqual(_2, other._2)
                    && isEqual(_3, other._3);
        }
        return false;
    }

    @Override
    public boolean isPrefixOf(final Tuple other) {
        if(other == null) {
            return false;
        } else if(other instanceof Tuple4) {
            final Tuple4 tuple4 = (Tuple4)other;
            return isEqual(_1, tuple4._1)
                    && isEqual(_2, tuple4._2);
        } else if(other instanceof Tuple5) {
            final Tuple5 tuple5 = (Tuple5) other;
            return isEqual(_1, tuple5._1)
                    && isEqual(_2, tuple5._2);
        } else if(other instanceof Tuple6) {
            final Tuple6 tuple6 = (Tuple6) other;
            return isEqual(_1, tuple6._1)
                    && isEqual(_2, tuple6._2);
        } else {
            return false;
        }
    }

    @Override
    public boolean isPostfixOf(final Tuple other) {
        if(other == null || !(other instanceof Tuple2)) {
            return false;
        }

        final Tuple2 tuple2 = (Tuple2)other;
        return isEqual(_1, tuple2._1)
                && isEqual(_2, tuple2._2);
    }

    @Override
    public <T> T foldLeft(final T startValue, final BiFunction<T, Object, T> op) {
        return op.apply(op.apply(op.apply(startValue, _1), _2), _3);
    }

    @Override
    public <T> T foldRight(final T startValue, final BiFunction<T, Object, T> op) {
        return op.apply(op.apply(op.apply(startValue, _3), _2), _1);
    }

    @Override
    public <T0> Tuple4<T0, T1, T2, T3> after(final T0 _0) {
        return new Tuple4<>(_0, this);
    }

    @Override
    public <T4> Tuple4<T1, T2, T3, T4> before(final T4 _4) {
        return new Tuple4<>(this, _4);
    }
}
