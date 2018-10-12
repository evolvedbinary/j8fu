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

import java.util.EnumMap;

/**
 * An {@link EventProcessor} which uses a static table of possible state
 * transitions.
 *
 * @param <State> An Enum which represents the possible states of the FSM
 * @param <Event> An Enum which represents all possible events which trigger
 *   state transitions
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
public class TransitionTable<State extends Enum<State>, Event extends Enum<Event>>
        implements EventProcessor<State, Event> {
    private final EnumMap<State, EnumMap<Event, State>> transition;
    private final boolean ignoreUnknownEvents;

    /**
     * Constructs a Transition Table
     *
     * See also {@link #transitionTable(Class, Class)}
     *
     * @param ignoreUnknownEvents When true if we receive an event in a state
     *   for which there is no known state transition we just return the
     *   current state, else if false, we throw
     *   an {@link IllegalStateException}
     * @param transitionTable A map of maps describing the state transitions
     *   i.e. {@code currentState -> (event -> newState)}
     */
    public TransitionTable(final boolean ignoreUnknownEvents, final EnumMap<State, EnumMap<Event, State>> transitionTable) {
        this.ignoreUnknownEvents = ignoreUnknownEvents;
        this.transition = transitionTable;
    }

    /**
     * Constructs a Transition Table
     *
     * See also {@link #transitionTable(Class, Class)}
     *
     * Similar to {@link #TransitionTable(boolean, EnumMap)}
     * where {@code ignoreUnknownEvents} is set to false
     *
     * @param transitionTable A map of maps describing the state transitions
     *   i.e. {@code currentState -> (event -> newState)}
     */
    public TransitionTable(final EnumMap<State, EnumMap<Event, State>> transitionTable) {
        this(false, transitionTable);
    }

    @Override
    public Either<IllegalStateException, State> apply(final State currentState, final Event event) {
        final EnumMap<Event, State> transitions = transition.get(currentState);
        if(!ignoreUnknownEvents) {
            if (transitions == null) {
                throw new IllegalStateException("No known transitions from current state '" + currentState.name() + "'");
            }
        }

        final State newState = transitions.get(event);
        if (newState == null) {
            if(!ignoreUnknownEvents) {
                throw new IllegalStateException("No known transition from current state '" + currentState.name() + "' for event '" + event.name() + "'");
            } else {
                return Either.Right(currentState);
            }
        } else {
            return Either.Right(newState);
        }
    }

    /**
     * Creates a {@link TransitionTableBuilder} which allows
     * the user to build a state transition table using
     * a fluent builder pattern
     *
     * @param stateType The Class of the State Enum for the FSM
     * @param eventType The Class of the Event Enum for the FSM
     *
     * @return the transition table builder
     *
     * @param <State> An Enum which represents the possible states of the FSM
     * @param <Event> An Enum which represents all possible events which trigger
     *   state transitions
     */
    public static <State extends Enum<State>, Event extends Enum<Event>> TransitionTableBuilder<State, Event> transitionTable(final Class<State> stateType, final Class<Event> eventType) {
        return new TransitionTableBuilder<>(stateType, eventType);
    }

    /**
     * A builder which allows the user to easily
     * construct a state transition table, see {@link TransitionTable#transitionTable(Class, Class)}
     */
    public static class TransitionTableBuilder<State extends Enum<State>, Event extends Enum<Event>> {
        private final EnumMap<State, EnumMap<Event, State>> transitionTable;
        private final Class<Event> eventType;

        private TransitionTableBuilder(final Class<State> stateType, final Class<Event> eventType) {
            this.transitionTable = new EnumMap<>(stateType);
            this.eventType = eventType;
        }

        /**
         * When the currentState is...
         *
         * @param currentState The state to specify a transition from
         * @return A builder for specifying the event
         */
        public TransitionTableBuilder.EventBuilder when(final State currentState) {
            return new EventBuilder(currentState);
        }

        public class EventBuilder {
            private final State currentState;

            private EventBuilder(final State currentState) {
                this.currentState = currentState;
            }

            /**
             * On the Event...
             *
             * @param event the Event to act upon for the currentState
             * @return A builder for specifying the state to switch to
             */
            public NewStateBuilder on(final Event event) {
                return new NewStateBuilder(currentState, event);
            }

            public TransitionTableBuilderS ignore(final Event event) {
                EnumMap<Event, State> transition = transitionTable.get(currentState);
                if(transition == null) {
                    transition = new EnumMap<>(eventType);
                }

                // Mapping from when(currentState).on(event).switchTo(currentState) -- signifies ignore!
                transition.put(event, currentState);

                transitionTable.put(currentState, transition);

                return new TransitionTableBuilderS(currentState);
            }
        }

        public class NewStateBuilder {
            private final State currentState;
            private final Event event;

            private NewStateBuilder(final State currentState, final Event event) {
                this.currentState = currentState;
                this.event = event;
            }

            /**
             * Then switch to the newState
             *
             * @param newState The new state to switch to
             * @return A builder for specifying the next transition
             */
            public TransitionTableBuilderS switchTo(final State newState) {
                EnumMap<Event, State> transition = transitionTable.get(currentState);
                if(transition == null) {
                    transition = new EnumMap<>(eventType);
                }

                transition.put(event, newState);

                transitionTable.put(currentState, transition);

                return new TransitionTableBuilderS(newState);
            }
        }

        public class TransitionTableBuilderS {
            private final State prevState;

            /**
             * @param prevState is used in case the user wants to chain transitions
             *   with {@link #on(Event)}
             */
            private TransitionTableBuilderS(final State prevState) {
                this.prevState = prevState;
            }

            /**
             * Add another transition from the previous state
             * e.g. when(x).on(y).switchTo(z).on(a).switchTo(b)
             * gives the state changes:
             *
             * {@code
             *  (x, y) -> z
             *  (z, a) -> b
             * }
             *
             * @param event the Event to act upon for the currentState
             * @return A builder for specifying the state to switch to
             */
            public NewStateBuilder on(final Event event) {
                return new NewStateBuilder(prevState, event);
            }

            /**
             * When the currentState is...
             *
             * @param currentState The state to specify a transition from
             * @return A builder for specifying the event
             */
            public TransitionTableBuilder.EventBuilder when(final State currentState) {
                return new EventBuilder(currentState);
            }

            /**
             * Builds the {@link TransitionTable}
             *
             * @param ignoreUnknownEvents When true if the {@link TransitionTable}
             *   receives an event in a state for which there is no known state
             *   transition we just return the current state, else if false,
             *   we throw an {@link IllegalStateException}
             *
             * @return The transition table
             */
            public TransitionTable<State, Event> build(final boolean ignoreUnknownEvents) {
                return new TransitionTable<>(ignoreUnknownEvents, transitionTable);
            }

            /**
             * Builds the {@link TransitionTable}
             *
             * Similar to {@link #build(boolean)}
             * where {@code ignoreUnknownEvents} is set to false
             *
             * @return The transition table
             */
            public TransitionTable<State, Event> build() {
                return new TransitionTable<>(transitionTable);
            }
        }
    }
}
