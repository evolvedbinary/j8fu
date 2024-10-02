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

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import com.evolvedbinary.j8fu.Either;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe implementation of {@link FSM} which uses
 * a single {@link ReentrantReadWriteLock} to
 * ensure safe concurrent access
 *
 * Note that we make use of the fair scheduler policy
 * of {@link ReentrantReadWriteLock}, however this does not guarantee
 * absolute fairness. This means that whilst we are thread-safe,
 * thread isolation is not Serializable, if you require Serializable updates
 * then you should implement your own FSM based
 * around <a href="http://tutorials.jenkov.com/java-concurrency/starvation-and-fairness.html#fairlock">Fair Lock</a>
 * or better.
 *
 * @author <a href="mailto:adam@evolvedbinary.com">Adam Retter</a>
 */
@ThreadSafe
public class BlockingFSM<State extends Enum<State>, Event extends Enum<Event>> extends FSM<State, Event> {

    private final boolean optimistic;
    private final ReadWriteLock stateLock = new ReentrantReadWriteLock(true);   // fair scheduling (but not perfectly!)
    @GuardedBy("stateLock") private State currentState;

    public BlockingFSM(final State initialState, final EventProcessor<State, Event> eventProcessor) {
        this(false, initialState, eventProcessor);
    }

    /**
     * @param optimistic Determines whether State Transitions should be
     *   calculated optimistically in {@link #process(Enum)}. Optimistic
     *   calculations offer potentially better performance under light
     *   concurrency. When calculating optimistically if the current state
     *   changes during the call to
     *   {@link EventProcessor#apply(Object, Object)} then the
     *   state transition must be recalculated within an exclusive lock.
     * @param initialState The initial state of the FSM
     * @param eventProcessor An Event Processor which is used to manage state transitions
     */
    public BlockingFSM(final boolean optimistic, final State initialState,
            final EventProcessor<State, Event> eventProcessor) {
        super(eventProcessor);
        this.optimistic = optimistic;
        this.currentState = initialState;
    }

    @Override
    public State getCurrentState() {
        stateLock.readLock().lock();
        try {
            return currentState;
        } finally {
            stateLock.readLock().unlock();
        }
    }

    @Override
    public State process(final Event event) throws IllegalStateException {
        final State optimisticState;
        Either<IllegalStateException, State> result = null;

        // should we calculate the state transition optimistically,
        // i.e. without exclusively locking the currentState
        if(optimistic) {
            optimisticState = getCurrentState();
            result = eventProcessor.apply(optimisticState, event);
        } else {
            optimisticState = null;
        }

        stateLock.writeLock().lock();
        try {
            // if we didn't attempt to calculate the state transition optimistically or
            // if the state has changed since we optimistically calculated the transition,
            // then (re-)calculate the state transition
            if(!optimistic || optimisticState != currentState) {
                result = eventProcessor.apply(currentState, event);
            }

            if(result.isLeft()) {
                throw result.left().get();
            } else {
                final State newState = result.right().get();
                // check if 'ignore(...)' was specified, i.e. there is no state transition to make
                if (newState != currentState) {
                    this.currentState = newState;
                }

                return currentState;
            }
        } finally {
            stateLock.writeLock().unlock();
        }
    }
}
