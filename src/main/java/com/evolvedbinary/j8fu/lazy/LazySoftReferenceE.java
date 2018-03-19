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
package com.evolvedbinary.j8fu.lazy;

import com.evolvedbinary.j8fu.Either;
import net.jcip.annotations.NotThreadSafe;

import java.lang.ref.SoftReference;
import java.util.function.Supplier;

import static com.evolvedbinary.j8fu.Either.valueOrThrow;

/**
 * A Lazily initialized Soft Reference (which is NOT thread safe), which may throw
 * and exception from initialisation.
 *
 * Will be initialized on the first call to {@link #get()}.
 * May also be re-initialized on subsequent calls to {@link #get()}
 * if the soft reference has been cleared; see the description of {@link #get()}
 * for further details.
 *
 * @param <T> The type of the lazily soft referenced value.
 * @param <E> The type of an exception that may occur
 *     at initialisation.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@NotThreadSafe
public class LazySoftReferenceE<T, E extends Throwable> {
    private final Supplier<Either<E, T>> initializer;
    private SoftReference<Either<E, T>> softRef = null;

    /**
     * @param initializer A function which provides the value
     *     or an exception.
     */
    public LazySoftReferenceE(final Supplier<Either<E, T>> initializer) {
        this.initializer = initializer;
    }

    /**
     * Gets the value of the soft reference.
     *
     * If the soft reference has been cleared
     * it will be automatically re-initalized by
     * invoking the initializer. If you don't
     * want this to be re-initialized you
     * should consult {@link #isInitialized()}
     * and {@link #isCleared()} before calling
     * this.
     *
     * @return The value of the lazy value
     *
     * @throws E an exception which occurred during initialisation.
     */
    public T get() throws E {
        if(softRef == null) {
            softRef = new SoftReference<>(initializer.get());
        }

        Either<E, T> val = softRef.get();
        if (val == null) {
            val = initializer.get();
            softRef = new SoftReference<>(val);
        }

        return valueOrThrow(val);
    }

    /**
     * Returns true if the soft reference has been initialized.
     *
     * @return true if the soft reference has been initialized.
     */
    public boolean isInitialized() {
        return softRef != null;
    }

    /**
     * Returns true if the soft reference has been cleared by GC.
     *
     * @return true if the soft reference has been cleared.
     */
    public boolean isCleared() {
        return softRef != null && softRef.get() == null;
    }
}
