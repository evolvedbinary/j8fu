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

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Functional utility methods that are missing from {@link java.util.Optional}
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class OptionalUtil {

    /**
     * Return the left Optional if present, else the right Optional
     *
     * @param left The left of the disjunction
     * @param right The right of the disjunction
     *
     * @return left if present, else right
     *
     * @param <T> The type of the Optionals
     */
    public static <T> Optional<T> or(final Optional<T> left, final Optional<T> right) {
        if(left.isPresent()) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * A lazy version of {@link OptionalUtil#or(Optional, Optional)}
     *
     * @param left The left of the disjunction
     * @param right A lazily evaluated supplier of Optional for the right of the disjunction,
     *              only evaluated if the left is empty
     *
     * @return left if present, else the evaluation of the the right
     *
     * @param <T> The type of the Optionals
     */
    public static <T> Optional<T> or(final Optional<T> left, final Supplier<Optional<T>> right) {
        if(left.isPresent()) {
            return left;
        } else {
            return right.get();
        }
    }
}
