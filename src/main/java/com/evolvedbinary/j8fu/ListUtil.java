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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Functional utility methods for working with {@link java.util.List}.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class ListUtil {

  /**
   * Applies a binary operator to a start value and all elements from this list, going left to right.
   *
   * @param <A> the type of the elements from the list.
   * @param <B> the type of the initial and result value.
   *
   * @param xs the list of elements.
   * @param z the initial value.
   * @param op the binary operation to apply.
   *
   * @return the result of applying the binary operator pair-wise starting with the initial value and the first value of the list, and then subsequently each element from the list.
   */
  static <A, B> B foldLeft(final List<A> xs, final B z, final BiFunction<B, A, B> op) {
    B element = z;
    for (final A x : xs) {
      element = op.apply(element, x);
    }
    return element;
  }

  /**
   * Applies a binary operator to all elements of this list and a start value, going right to left.
   *
   * @param <A> the type of the elements from the list.
   * @param <B> the type of the initial and result value.
   *
   * @param xs the list of elements.
   * @param z the initial value.
   * @param op the binary operation to apply.
   *
   * @return the result of applying the binary operator pair-wise starting with the initial value and last value of the list, and then subsequently each element from the list.
   */
  static <A, B> B foldRight(final List<A> xs, final B z, final BiFunction<A, B, B> op) {
    // copy the list before reversing it
    final List<A> reverseList = new ArrayList<>(xs);
    Collections.reverse(reverseList);

    B element = z;
    for (final A x : reverseList) {
      element = op.apply(x, element);
    }
    return element;
  }
}