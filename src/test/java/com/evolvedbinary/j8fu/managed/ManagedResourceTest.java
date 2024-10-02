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

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.evolvedbinary.j8fu.managed.ManagedResource.managed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ManagedResourceTest {

    @Test
    public void simpleAcquireRelease() {
        final SimpleService simpleService = new SimpleService();

        final String result = managed(simpleService, SimpleService::createResource, (service, resource) -> resource.close()).map(resource -> {
            return resource.sayHello();
        });

        assertEquals("hello", result);
    }

    @Test
    public void legacyAcquireRelease() {
        final LegacyService legacyService = new LegacyService();

        final String result = managed(legacyService, LegacyService::createResource, (service, resource) -> service.closeResource(resource)).map(resource -> {
            return resource.sayHello();
        });

        assertEquals("hello2", result);
        assertFalse(legacyService.hasOpenResources());
    }




    public class SimpleService {
        public SimpleResource createResource() {
            return new SimpleResource();
        }
    }

    public class SimpleResource implements AutoCloseable {
        private boolean closed = false;

        public String sayHello() {
            if(closed) {
                throw new IllegalStateException();
            }

            return "hello";
        }

        @Override
        public void close() {
            closed = true;
        }
    }

    public class LegacyService {
        private final Set<LegacyResource> resources = new HashSet<>();

        public LegacyResource createResource() {
            final LegacyResource legacyResource = new LegacyResource();
            resources.add(legacyResource);
            return legacyResource;
        }

        public void closeResource(final LegacyResource legacyResource) {
            resources.remove(legacyResource);
            legacyResource.release();
        }

        public boolean hasOpenResources() {
            return !resources.isEmpty();
        }
    }

    public class LegacyResource {
        private boolean released = false;

        public String sayHello() {
            if(released) {
                throw new IllegalStateException();
            }

            return "hello2";
        }

        void release() {
            released = true;
        }
    }
}
