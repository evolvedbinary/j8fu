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

import java.util.function.BiFunction;

/**
 * Base interface for a Tuple of values
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public interface Tuple {

    /**
     * Determines whether v1 is equal to v2
     *
     * The values are also considered equal if
     * both are null
     *
     * @param v1 The first value
     * @param v2 The second value
     *
     * @return true if the values are both null or equal
     *
     * @param <T> The value type
     */
    default <T> boolean isEqual(final T v1, final T v2) {
        if(v1 == null && v2 == null) {
            return true;
        } if(v1 == null) {
            return false;
        } else {
            return v1.equals(v2);
        }
    }

    /**
     * Is this TupleN a prefix of the TupleN+x
     *
     * e.g. (1,2,3).isPrefixOf(1,2,3,4) == true
     *
     * @param other A tuple to compare against
     * @return true if this tupl3 is a prefix of the other tuple
     */
    boolean isPrefixOf(final Tuple other);

    /**
     * Is this TupleN+x a postfix of the TupleN
     *
     * e.g. (1,2,3,4).isPostfixOf(1,2) == true
     *
     * @param other A tuple to compare against
     * @return true if this tuple is a postfix of the other tuple
     */
    boolean isPostfixOf(final Tuple other);

    /**
     * Applies a binary operator to a start value and all elements of this
     * tuple, going from left to right.
     *
     * @param startValue The start value
     * @param op The operator to apply
     *
     * @return The result of applying op between startValue and consecutive
     *     elements from left to right
     *
     * @param <T> The value type
     */
    <T> T foldLeft(final T startValue, final BiFunction<T, Object, T> op);

    /**
     * Applies a binary operator to a start value and all elements of this
     * tuple, going from right to left.
     *
     * @param startValue The start value
     * @param op The operator to apply
     *
     * @return The result of applying op between startValue and consecutive
     *     elements from right to left
     *
     * @param <T> The value type
     */
    <T> T foldRight(final T startValue, final BiFunction<T, Object, T> op);
}
