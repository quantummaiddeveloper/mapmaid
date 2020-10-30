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

package de.quantummaid.mapmaid.mapper.deserialization.validation;

import static java.lang.String.valueOf;

public final class UnrecognizedExceptionOccurredException extends RuntimeException {
    public final Throwable unmappedException;
    public final String originalInput;

    private UnrecognizedExceptionOccurredException(
            final String msg,
            final Throwable unmappedException,
            final String originalInput) {
        super(msg, unmappedException);
        this.unmappedException = unmappedException;
        this.originalInput = originalInput;
    }

    static UnrecognizedExceptionOccurredException fromException(
            final String messageProvidingDebugInformation,
            final TrackingPosition position,
            final Throwable unmappedException,
            final Object originalInput) {
        final String msg = "Unrecognized exception deserializing field '" + position.render() +
                "': " + messageProvidingDebugInformation;
        return new UnrecognizedExceptionOccurredException(msg, unmappedException, valueOf(originalInput));
    }
}
