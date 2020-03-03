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

package de.quantummaid.mapmaid.docs.examples.system;

import de.quantummaid.mapmaid.MapMaid;
import de.quantummaid.mapmaid.builder.MapMaidBuilder;
import de.quantummaid.mapmaid.builder.RequiredCapabilities;
import de.quantummaid.mapmaid.docs.examples.system.expectation.Expectation;
import de.quantummaid.mapmaid.docs.examples.system.mode.ExampleMode;
import de.quantummaid.mapmaid.shared.types.ResolvedType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static de.quantummaid.mapmaid.Collection.smallMap;
import static de.quantummaid.mapmaid.docs.examples.system.Result.emptyResult;
import static de.quantummaid.mapmaid.docs.examples.system.expectation.DeserializationSuccessfulExpectation.deserializationWas;
import static de.quantummaid.mapmaid.docs.examples.system.expectation.Expectation.*;
import static de.quantummaid.mapmaid.docs.examples.system.expectation.SerializationSuccessfulExpectation.serializationWas;
import static de.quantummaid.mapmaid.docs.examples.system.mode.FixedExampleMode.*;
import static de.quantummaid.mapmaid.docs.examples.system.mode.NormalExampleMode.*;
import static de.quantummaid.mapmaid.mapper.marshalling.MarshallingType.json;
import static de.quantummaid.mapmaid.shared.types.ResolvedType.resolvedType;
import static java.lang.String.format;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScenarioBuilder {
    private final ResolvedType type;
    private String serializedForm;
    private Object deserializedForm;
    private final Map<ExampleMode, List<Expectation>> scenarios = smallMap();

    public static ScenarioBuilder scenarioBuilderFor(final Class<?> type) {
        return new ScenarioBuilder(resolvedType(type));
    }

    public static ScenarioBuilder scenarioBuilderFor(final ResolvedType type) {
        return new ScenarioBuilder(type);
    }

    public ScenarioBuilder withSerializedForm(final String serializedForm) {
        this.serializedForm = serializedForm;
        return this;
    }

    public ScenarioBuilder withDeserializedForm(final Object deserializedForm) {
        this.deserializedForm = deserializedForm;
        return this;
    }

    public ScenarioBuilder withScenario(final ExampleMode exampleMode, final Expectation... expectation) {
        this.scenarios.put(exampleMode, List.of(expectation));
        return this;
    }

    public ScenarioBuilder withAllScenariosFailing(final String message, final BiConsumer<MapMaidBuilder, RequiredCapabilities> fix) {
        withAllScenariosFailing(message);
        withFixedScenarios(fix);
        return this;
    }

    public ScenarioBuilder withAllScenariosFailing(final String message) {
        withDuplexFailing(message);
        withDeserializationFailing(message);
        withSerializationFailing(message);
        return this;
    }

    public ScenarioBuilder withFixedScenarios(final BiConsumer<MapMaidBuilder, RequiredCapabilities> fix) {
        withScenario(fixedWithAllCapabilities(fix), deserializationWas(this.deserializedForm), serializationWas(this.serializedForm));
        withScenario(fixedDeserializationOnly(fix), deserializationWas(this.deserializedForm), serializationFailedForNotSupported(this.type));
        withScenario(fixedSerializationOnly(fix), serializationWas(this.serializedForm), deserializationFailedForNotSupported(this.type));
        return this;
    }

    public ScenarioBuilder withDuplexFailing() {
        return withDuplexFailing(format("%s: unable to detect duplex", this.type.description()));
    }

    public ScenarioBuilder withDuplexFailing(final String message) {
        withScenario(withAllCapabilities(), initializationFailed(message));
        return this;
    }

    public ScenarioBuilder withSerializationFailing() {
        return withSerializationFailing(format("%s: unable to detect serializer", this.type.description()));
    }

    public ScenarioBuilder withSerializationFailing(final String message) {
        withScenario(serializationOnly(), initializationFailed(message));
        return this;
    }

    public ScenarioBuilder withDeserializationFailing() {
        return withDeserializationFailing(format("%s: unable to detect deserializer", this.type.description()));
    }

    public ScenarioBuilder withDeserializationFailing(final String message) {
        withScenario(deserializationOnly(), initializationFailed(message));
        return this;
    }

    public ScenarioBuilder withAllScenariosSuccessful() {
        withDuplexSuccessful();
        withSerializationOnlySuccessful();
        withDeserializationSuccessful();
        return this;
    }

    public ScenarioBuilder withDuplexSuccessful() {
        withScenario(withAllCapabilities(), deserializationWas(this.deserializedForm), serializationWas(this.serializedForm));
        return this;
    }

    public ScenarioBuilder withSerializationOnlySuccessful(final String serializedForm) {
        withScenario(serializationOnly(), serializationWas(serializedForm), deserializationFailedForNotSupported(this.type));
        return this;
    }

    public ScenarioBuilder withSerializationOnlySuccessful() {
        return withSerializationOnlySuccessful(this.serializedForm);
    }

    public ScenarioBuilder withDeserializationSuccessful() {
        withScenario(deserializationOnly(), deserializationWas(this.deserializedForm), serializationFailedForNotSupported(this.type));
        return this;
    }

    public ScenarioBuilder withDeserializationOnly() {
        withScenario(deserializationOnly(), deserializationWas(this.deserializedForm), serializationFailedForNotSupported(this.type));
        withSerializationFailing();
        withDuplexFailing();
        return this;
    }

    public ScenarioBuilder withSerializationOnly() {
        withScenario(serializationOnly(), serializationWas(this.serializedForm), deserializationFailedForNotSupported(this.type));
        withScenario(deserializationOnly(), initializationFailed(format("%s: unable to detect deserializer", this.type.description())));
        withDuplexFailing();
        return this;
    }

    public void run() {
        this.scenarios.forEach(this::run);
    }

    private void run(final ExampleMode mode, final List<Expectation> expectations) {
        final Result result = runScenario(mode);
        expectations.forEach(expectation -> expectation.ensure(result));
    }

    private Result runScenario(final ExampleMode mode) {
        final Result result = emptyResult();

        final MapMaid mapMaid;
        try {
            mapMaid = mode.provideMapMaid(this.type);
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
            result.withInitializationException(throwable);
            return result;
        }

        try {
            final String serialized = mapMaid.serializer()
                    .serialize(this.deserializedForm, this.type, json(), stringObjectMap -> stringObjectMap);
            result.withSerializationResult(serialized);
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
            result.withSerializationException(throwable);
        }

        try {
            final Object deserialized = mapMaid.deserializer().deserialize(this.serializedForm, this.type, json());
            result.withDeserializationResult(deserialized);
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
            result.withDeserializationException(throwable);
        }

        return result;
    }
}