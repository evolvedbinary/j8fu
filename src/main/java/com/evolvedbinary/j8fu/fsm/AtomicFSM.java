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
package com.evolvedbinary.j8fu.fsm;

import net.jcip.annotations.ThreadSafe;
import com.evolvedbinary.j8fu.Either;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A thread-safe implementation of {@link FSM} which uses
 * a single {@link java.util.concurrent.atomic.AtomicReference} to
 * ensure safe concurrent access
 *
 * Note that {@link #process(Enum)} uses tail-call recursion
 * to modify the state if there are multiple competing threads;
 * This means that whilst we are thread-safe, thread isolation
 * is not Serializable, if you require stricter semantics
 * then see {@link BlockingFSM}.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@ThreadSafe
public class AtomicFSM<State extends Enum<State>, Event extends Enum<Event>> extends FSM<State, Event> {

    private final AtomicReference<State> currentState;

    /**
     * @param initialState The initial state of the FSM
     * @param eventProcessor An Event Processor which is used to manage state transitions
     */
    public AtomicFSM(final State initialState, final EventProcessor<State, Event> eventProcessor) {
        super(eventProcessor);
        this.currentState = new AtomicReference<>(initialState);
    }

    @Override
    public State getCurrentState() {
        return currentState.get();
    }

    @Override
    public State process(final Event event) throws IllegalStateException {
        final State state = getCurrentState();
        final Either<IllegalStateException, State> result = eventProcessor.apply(state, event);

        if(result.isLeft()) {
            throw result.left().get();
        }

        final State newState = result.right().get();

        if (newState == state) {
            // 'ignore(...)' was specified, there is no state transition to make
            return state;
        }

        if (currentState.compareAndSet(state, newState)) {
            return result.right().get();
        }

        return process(event);
    }
}
