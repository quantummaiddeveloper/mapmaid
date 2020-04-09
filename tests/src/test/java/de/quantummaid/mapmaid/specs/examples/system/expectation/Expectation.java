/*
 * Copyright (c) 2020 Richard Hauswald - https://quantummaid.de/.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.quantummaid.mapmaid.specs.examples.system.expectation;

import de.quantummaid.mapmaid.specs.examples.system.Result;
import de.quantummaid.reflectmaid.ResolvedType;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public interface Expectation {

    static Expectation initializationFailed(final String message) {
        return result -> {
            assertThat("exception occurred during initialization", result.initializationException().isPresent(), is(true));
            final Throwable exception = result.initializationException().get();
            exception.printStackTrace();
            assertThat(exception.getMessage(), containsString(message));
        };
    }

    static Expectation serializationFailedForNotSupported(final ResolvedType type) {
        return serializationFailed(format("No serializer configured for type '%s'", type.description()));
    }

    static Expectation serializationFailed(final String message) {
        return result -> {
            assertThat("exception occurred during serialization", result.serializationException().isPresent(), is(true));
            final Throwable exception = result.serializationException().get();
            assertThat(exception.getMessage(), containsString(message));
        };
    }

    static Expectation deserializationFailedForNotSupported(final ResolvedType type) {
        return deserializationFailed(format("No deserializer configured for '%s'", type.description()));
    }

    static Expectation deserializationFailed(final String message) {
        return result -> {
            assertThat("exception occurred during deserialization", result.deserializationException().isPresent(), is(true));
            final Throwable exception = result.deserializationException().get();
            assertThat(exception.getMessage(), containsString(message));
        };
    }

    void ensure(Result result);
}