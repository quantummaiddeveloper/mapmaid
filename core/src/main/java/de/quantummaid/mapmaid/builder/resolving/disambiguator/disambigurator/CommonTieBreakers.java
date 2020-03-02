/*
 * Copyright (c) 2019 Richard Hauswald - https://quantummaid.de/.
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

package de.quantummaid.mapmaid.builder.resolving.disambiguator.disambigurator;

import de.quantummaid.mapmaid.builder.resolving.disambiguator.disambigurator.tiebreaker.IrrefutableHint;
import de.quantummaid.mapmaid.mapper.deserialization.deserializers.TypeDeserializer;
import de.quantummaid.mapmaid.mapper.deserialization.deserializers.serializedobjects.MethodSerializedObjectDeserializer;

import static de.quantummaid.mapmaid.builder.resolving.disambiguator.disambigurator.tiebreaker.TieBreakingReason.aTieBreakingReason;
import static de.quantummaid.mapmaid.builder.resolving.disambiguator.disambigurator.tiebreaker.TieBreakingReason.notATieBreakingReason;
import static java.lang.String.format;

public final class CommonTieBreakers {

    private CommonTieBreakers() {
    }

    public static IrrefutableHint<TypeDeserializer> serializedObjectFactoryNamed(final String name) {
        return element -> {
            if (!(element instanceof MethodSerializedObjectDeserializer)) {
                return notATieBreakingReason();
            }
            final String methodName = ((MethodSerializedObjectDeserializer) element).method().method().getName();
            if (methodName.equals(name)) {
                return aTieBreakingReason(format("factory method named '%s'", name));
            } else {
                return notATieBreakingReason();
            }
        };
    }
}