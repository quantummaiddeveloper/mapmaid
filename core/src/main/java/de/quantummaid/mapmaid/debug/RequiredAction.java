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

package de.quantummaid.mapmaid.debug;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequiredAction {
    private final boolean unreasoned;
    private final boolean changed;

    public static RequiredAction unreasoned() {
        return new RequiredAction(true, false);
    }

    public static RequiredAction requirementsChanged() {
        return new RequiredAction(false, true);
    }

    public static RequiredAction nothingChanged() {
        return new RequiredAction(false, false);
    }

    public <T> T map(
            final Supplier<T> nothingChanged,
            final Supplier<T> requirementsChanged,
            final Supplier<T> becameUnreasoned
    ) {
        if (unreasoned) {
            return becameUnreasoned.get();
        }
        if (changed) {
            return requirementsChanged.get();
        }
        return nothingChanged.get();
    }
}
