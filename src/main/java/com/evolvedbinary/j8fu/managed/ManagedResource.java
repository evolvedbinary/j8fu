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
package com.evolvedbinary.j8fu.managed;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A resource whose acquisition and release is managed
 * by a mapping function
 *
 * Not dissimilar to <a href="https://github.com/jsuereth/scala-arm">scala-arm</a>
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class ManagedResource<T, AR> {
    private final T subject;
    private final Function<T, AR> acquireFn;
    private final BiConsumer<T, AR> releaseFn;

    private ManagedResource(final T subject, final Function<T, AR> acquireFn, final BiConsumer<T, AR> releaseFn) {
        this.subject = subject;
        this.acquireFn = acquireFn;
        this.releaseFn = releaseFn;
    }

    public <U> U map(final Function<AR, U> mapper) {
        AR acquireResult = null;
        try {
            acquireResult = acquireFn.apply(subject);
            return mapper.apply(acquireResult);
        } finally {
            releaseFn.accept(subject, acquireResult);
        }
    }

    public static <T, AR> ManagedResource<T, AR> managed(final T subject, final Function<T, AR> acquireFn, final BiConsumer<T, AR> releaseFn) {
        return new ManagedResource<>(subject, acquireFn, releaseFn);
    }
}
