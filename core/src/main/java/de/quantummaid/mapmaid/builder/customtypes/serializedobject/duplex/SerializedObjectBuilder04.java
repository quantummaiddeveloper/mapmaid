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

package de.quantummaid.mapmaid.builder.customtypes.serializedobject.duplex;

import de.quantummaid.mapmaid.builder.customtypes.DuplexType;
import de.quantummaid.mapmaid.builder.customtypes.serializedobject.Builder;
import de.quantummaid.mapmaid.builder.customtypes.serializedobject.Deserializer04;
import de.quantummaid.mapmaid.builder.customtypes.serializedobject.Query;
import de.quantummaid.reflectmaid.GenericType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static de.quantummaid.mapmaid.builder.customtypes.serializedobject.duplex.Common.createDuplexType;
import static de.quantummaid.mapmaid.builder.customtypes.serializedobject.duplex.SerializedObjectBuilder05.serializedObjectBuilder05;
import static de.quantummaid.reflectmaid.GenericType.genericType;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SerializedObjectBuilder04<X, A, B, C, D> {
    private final Builder builder;

    public static <X, A, B, C, D> SerializedObjectBuilder04<X, A, B, C, D> serializedObjectBuilder04(final Builder builder) {
        return new SerializedObjectBuilder04<>(builder);
    }

    public <E> SerializedObjectBuilder05<X, A, B, C, D, E> withField(final String name,
                                                                     final Class<E> type,
                                                                     final Query<X, E> query) {
        return withField(name, genericType(type), query);
    }

    @SuppressWarnings("unchecked")
    public <E> SerializedObjectBuilder05<X, A, B, C, D, E> withField(final String name,
                                                                     final GenericType<E> type,
                                                                     final Query<X, E> query) {
        this.builder.addDuplexField(type, name, (Query<Object, Object>) query);
        return serializedObjectBuilder05(this.builder);
    }

    public DuplexType<X> deserializedUsing(final Deserializer04<X, A, B, C, D> deserializer) {
        this.builder.setDeserializer(deserializer);
        return createDuplexType(this.builder);
    }
}