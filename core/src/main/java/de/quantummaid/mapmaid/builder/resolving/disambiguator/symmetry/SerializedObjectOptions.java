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

package de.quantummaid.mapmaid.builder.resolving.disambiguator.symmetry;

import de.quantummaid.mapmaid.builder.detection.serializedobject.SerializationFieldOptions;
import de.quantummaid.mapmaid.builder.resolving.disambiguator.SerializersAndDeserializers;
import de.quantummaid.mapmaid.mapper.deserialization.deserializers.TypeDeserializer;
import de.quantummaid.mapmaid.mapper.serialization.serializers.TypeSerializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

import static java.util.Collections.singletonList;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SerializedObjectOptions {
    private final SerializationFieldOptions serializationFieldOptions;
    private final List<TypeDeserializer> deserializers;

    public static SerializedObjectOptions serializedObjectOptions(final SerializationFieldOptions serializationFieldOptions,
                                                                  final List<TypeDeserializer> deserializers) {
        return new SerializedObjectOptions(serializationFieldOptions, deserializers);
    }

    public List<TypeDeserializer> deserializers() {
        return this.deserializers;
    }

    public SerializationFieldOptions serializationFieldOptions() {
        return this.serializationFieldOptions;
    }

    // TODO
    public SerializersAndDeserializers toSerializersAndDeserializers() {
        final List<TypeSerializer> serializers;
        if (this.serializationFieldOptions != null) {
            serializers = singletonList(this.serializationFieldOptions.instantiateAll());
        } else {
            serializers = null;
        }
        return SerializersAndDeserializers.serializersAndDeserializers(serializers, this.deserializers);
    }
}
