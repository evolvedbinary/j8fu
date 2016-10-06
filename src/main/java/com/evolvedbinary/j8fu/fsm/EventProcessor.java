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

import com.evolvedbinary.j8fu.Either;

/**
 * An Event Processor is used to control the transition from
 * one State to another State by an Event.
 *
 * This is really just a
 * {@code java.util.function.BiFunction<State, Event, Either<IllegalStateException, State>}
 * which looks like {@code f(currentState, event) -> newState}
 *
 * One example of an {@link EventProcessor} is {@link TransitionTable}, which
 * is based on a static state transition table
 *
 * @param <State> An Enum which represents the possible states of the FSM
 * @param <Event> An Enum which represents all possible events which trigger
 *   state transitions
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@FunctionalInterface
public interface EventProcessor<State, Event> {

    /**
     * Given the current state and an event which acts upon that state
     * the function should return a new state
     *
     * @param currentState The current state of the FSM
     * @param event The evnet which acts upon the {@code currentState}
     *
     * @return Either An {@link IllegalStateException} if a state
     *   transition cannot be identified, otherwise the new state. The new
     *   state may be the same as the {@code currentState} if the event does
     *   not trigger a state transition
     */
    Either<IllegalStateException, State> apply(final State currentState, final Event event);
}
