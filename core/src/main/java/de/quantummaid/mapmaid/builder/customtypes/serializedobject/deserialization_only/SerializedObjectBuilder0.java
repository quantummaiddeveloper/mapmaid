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

package de.quantummaid.mapmaid.builder.customtypes.serializedobject.deserialization_only;

import de.quantummaid.mapmaid.builder.GenericType;
import de.quantummaid.mapmaid.builder.customtypes.DeserializationOnlyType;
import de.quantummaid.mapmaid.builder.customtypes.serializedobject.Builder;
import de.quantummaid.mapmaid.builder.customtypes.serializedobject.Deserializer0;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static de.quantummaid.mapmaid.builder.GenericType.genericType;
import static de.quantummaid.mapmaid.builder.customtypes.serializedobject.Builder.emptyBuilder;
import static de.quantummaid.mapmaid.builder.customtypes.serializedobject.deserialization_only.Common.createDeserializationOnlyType;
import static de.quantummaid.mapmaid.builder.customtypes.serializedobject.deserialization_only.SerializedObjectBuilder1.serializedObjectBuilder1;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SerializedObjectBuilder0<X> {
    private final Builder builder;

    public static <X> SerializedObjectBuilder0<X> serializedObjectBuilder0(final GenericType<X> type) {
        return new SerializedObjectBuilder0<>(emptyBuilder(type));
    }

    public <A> SerializedObjectBuilder1<X, A> withField(final String name,
                                                        final Class<A> type) {
        return withField(name, genericType(type));
    }

    public <A> SerializedObjectBuilder1<X, A> withField(final String name,
                                                           final GenericType<A> type) {
        this.builder.addDeserializationField(type, name);
        return serializedObjectBuilder1(this.builder);
    }

    public DeserializationOnlyType<X> deserializedUsing(final Deserializer0<X> deserializer) {
        this.builder.setDeserializer(deserializer);
        return createDeserializationOnlyType(this.builder);
    }
}
