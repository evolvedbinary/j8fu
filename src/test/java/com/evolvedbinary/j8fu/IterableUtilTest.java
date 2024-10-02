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

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IterableUtilTest {

  @Test
  public void foldLeft() {
    final List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
    final Integer result1 = IterableUtil.foldLeft(list1, 10, Integer::sum);
    assertEquals(25, result1.intValue());

    final List<String> list2 = Arrays.asList("a", "b", "c", "d", "e");
    final String result2 = IterableUtil.foldLeft(list2, "z", (b, a) -> b + "," + a);
    assertEquals("z,a,b,c,d,e", result2);

    final List<String> list3 = Arrays.asList("a", "b", "c", "d", "e");
    final String result3 = IterableUtil.foldLeft(list3, "z", (b, a) -> a + "," + b);
    assertEquals("e,d,c,b,a,z", result3);

    final List<Integer> list4 = Arrays.asList(1, 2, 3, 4, 5);
    final String result4 = IterableUtil.foldLeft(list4, "n:", (b, a) -> (b.length() > 2 ? b + "," : "") + b.substring(0, 2) + a);
    assertEquals("n:1,n:2,n:3,n:4,n:5", result4);
  }

  @Test
  public void foldRight() {
    final List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
    final Integer result1 = IterableUtil.foldRight(list1, 10, Integer::sum);
    assertEquals(25, result1.intValue());

    final List<String> list2 = Arrays.asList("a", "b", "c", "d", "e");
    final String result2 = IterableUtil.foldRight(list2, "z", (a, b) -> a + "," + b);
    assertEquals("a,b,c,d,e,z", result2);

    final List<String> list3 = Arrays.asList("a", "b", "c", "d", "e");
    final String result3 = IterableUtil.foldRight(list3, "z", (a, b) -> b + "," + a);
    assertEquals("z,e,d,c,b,a", result3);

    final List<Integer> list4 = Arrays.asList(1, 2, 3, 4, 5);
    final String result4 = IterableUtil.foldRight(list4, "n:", (a, b) -> (b.length() > 2 ? b + "," : "") + b.substring(0, 2) + a);
    assertEquals("n:5,n:4,n:3,n:2,n:1", result4);
  }
}
