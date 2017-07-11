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

/**
 * Similar to {@link FunctionE} but
 * permits six statically known Exceptions to be thrown
 *
 * @param <T> Function parameter type
 * @param <R> Function return type
 * @param <E1> Function throws exception type
 * @param <E2> Function throws exception type
 * @param <E3> Function throws exception type
 * @param <E4> Function throws exception type
 * @param <E5> Function throws exception type
 * @param <E6> Function throws exception type
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@FunctionalInterface
public interface Function6E<T, R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable,
        E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> {
    R apply(final T t) throws E1, E2, E3, E4, E5, E6;

    default <V> Function6E<T, V, E1, E2, E3, E4, E5, E6> andThen(Function6E<? super R, ? extends V, ? extends E1,
            ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
}
