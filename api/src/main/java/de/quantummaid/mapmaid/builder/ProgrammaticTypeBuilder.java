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

package de.quantummaid.mapmaid.builder;

import de.quantummaid.mapmaid.RequiredCapabilities;
import de.quantummaid.mapmaid.reason.Reason;
import de.quantummaid.reflectmaid.GenericType;

import static de.quantummaid.mapmaid.reason.Reason.manuallyAdded;
import static de.quantummaid.mapmaid.reason.Reason.reason;
import static de.quantummaid.reflectmaid.GenericType.genericType;

public interface ProgrammaticTypeBuilder<T extends ProgrammaticTypeBuilder<T>> {

    default T withType(final Class<?> type,
                       final RequiredCapabilities capabilities) {
        return withType(type, capabilities, manuallyAdded());
    }

    default T withType(final Class<?> type,
                       final RequiredCapabilities capabilities,
                       final String reason) {
        return withType(type, capabilities, reason(reason));
    }

    default T withType(final Class<?> type,
                       final RequiredCapabilities capabilities,
                       final Reason reason) {
        return withType(genericType(type), capabilities, reason);
    }

    default T withType(final GenericType<?> type,
                       final RequiredCapabilities capabilities,
                       final String reason) {
        return withType(type, capabilities, reason(reason));
    }

    T withType(GenericType<?> type,
               RequiredCapabilities capabilities,
               Reason reason);
}
