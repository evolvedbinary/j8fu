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
package com.evolvedbinary.j8fu.fsm;

/**
 * A simple abstract FSM (Finite State Machine)
 *
 * Transition from one State to another State
 * is performed by processing an Event.
 *
 * The event processor is a parameter which allows
 * the user to control exactly the available states
 * and state transitions, see {@link EventProcessor}
 *
 * Each State and Event is a {@link java.lang.Enum}
 * which should lead to simple descriptions and low-overhead
 *
 * @param <State> An Enum which represents the possible states of the FSM
 * @param <Event> An Enum which represents all possible events which trigger state transitions
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public abstract class FSM<State extends Enum<State>, Event extends Enum<Event>> {
    protected final EventProcessor<State, Event> eventProcessor;

    /**
     * @param eventProcessor An Event Processor which is used to manage state transitions
     */
    public FSM(final EventProcessor<State, Event> eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    /**
     * Get the current state
     *
     * @return The current state
     */
    public abstract State getCurrentState();

    /**
     * Process an event against the current state
     *
     * The event may result in a state change,
     * remaining in the current state or an {@link IllegalStateException}
     *
     * @param event The event to act on in the current state
     * @return The state after the event is processed, this may be a new state
     *   or the same state if there was no change
     * @throws IllegalStateException if the {@link EventProcessor} determines that
     *   the Event is invalid for the current state.
     */
    public abstract State process(final Event event) throws IllegalStateException;
}
