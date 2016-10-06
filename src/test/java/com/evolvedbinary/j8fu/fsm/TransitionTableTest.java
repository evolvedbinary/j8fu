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

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.EnumMap;

import static com.evolvedbinary.j8fu.fsm.TransitionTable.transitionTable;
import static com.evolvedbinary.j8fu.fsm.TransitionTableTest.State.*;
import static com.evolvedbinary.j8fu.fsm.TransitionTableTest.Event.*;
import static org.junit.Assert.assertEquals;

public class TransitionTableTest {

    enum State {
        A,
        B,
        C
    }

    enum Event {
        ToA,
        ToB,
        ToC
    }

    @Test
    public void fluentBuilder() throws NoSuchFieldException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        final TransitionTable<State, Event> table = transitionTable(State.class, Event.class)
                .when(A).on(ToB).switchTo(B)
                .when(B).on(ToC).switchTo(C)
                .when(B).on(ToA).switchTo(A)
                .when(C).on(ToA).switchTo(A)
        .build();

        final Field fTransition = TransitionTable.class.getDeclaredField("transition");
        fTransition.setAccessible(true);
        @SuppressWarnings("unchecked")
        final EnumMap<State, EnumMap<Event, State>> transition = (EnumMap<State, EnumMap<Event, State>>)fTransition.get(table);

        assertEquals(3, transition.size());

        final EnumMap<Event, State> expectedATransitions = new EnumMap<>(Event.class);
        expectedATransitions.put(ToB, B);
        final EnumMap<Event, State> actualATransitions = transition.get(A);
        assertEquals(1, actualATransitions.size());
        assertEquals(expectedATransitions, actualATransitions);

        final EnumMap<Event, State> expectedBTransitions = new EnumMap<>(Event.class);
        expectedBTransitions.put(ToC, C);
        expectedBTransitions.put(ToA, A);
        final EnumMap<Event, State> actualBTransitions = transition.get(B);
        assertEquals(2, actualBTransitions.size());
        assertEquals(expectedBTransitions, actualBTransitions);

        final EnumMap<Event, State> expectedCTransitions = new EnumMap<>(Event.class);
        expectedCTransitions.put(ToA, A);
        final EnumMap<Event, State> actualCTransitions = transition.get(C);
        assertEquals(1, actualCTransitions.size());
        assertEquals(expectedCTransitions, actualCTransitions);
    }

    @Test
    public void fluentBuilder_shortCutApi() throws NoSuchFieldException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        final TransitionTable<State, Event> table = transitionTable(State.class, Event.class)
                .when(A)
                    .on(ToB).switchTo(B)
                        .on(ToC).switchTo(C)
                            .on(ToA).switchTo(A)
                .when(B).on(ToA).switchTo(A)
                .build();

        final Field fTransition = TransitionTable.class.getDeclaredField("transition");
        fTransition.setAccessible(true);
        @SuppressWarnings("unchecked")
        final EnumMap<State, EnumMap<Event, State>> transition = (EnumMap<State, EnumMap<Event, State>>)fTransition.get(table);

        assertEquals(3, transition.size());

        final EnumMap<Event, State> expectedATransitions = new EnumMap<>(Event.class);
        expectedATransitions.put(ToB, B);
        final EnumMap<Event, State> actualATransitions = transition.get(A);
        assertEquals(1, actualATransitions.size());
        assertEquals(expectedATransitions, actualATransitions);

        final EnumMap<Event, State> expectedBTransitions = new EnumMap<>(Event.class);
        expectedBTransitions.put(ToC, C);
        expectedBTransitions.put(ToA, A);
        final EnumMap<Event, State> actualBTransitions = transition.get(B);
        assertEquals(2, actualBTransitions.size());
        assertEquals(expectedBTransitions, actualBTransitions);

        final EnumMap<Event, State> expectedCTransitions = new EnumMap<>(Event.class);
        expectedCTransitions.put(ToA, A);
        final EnumMap<Event, State> actualCTransitions = transition.get(C);
        assertEquals(1, actualCTransitions.size());
        assertEquals(expectedCTransitions, actualCTransitions);
    }

}
