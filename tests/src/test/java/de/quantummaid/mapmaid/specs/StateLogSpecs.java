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

package de.quantummaid.mapmaid.specs;

import com.google.gson.Gson;
import de.quantummaid.mapmaid.MapMaid;
import de.quantummaid.mapmaid.builder.resolving.processing.log.LogEntry;
import de.quantummaid.mapmaid.builder.resolving.processing.log.LoggedState;
import de.quantummaid.mapmaid.builder.resolving.processing.log.StateLog;
import de.quantummaid.mapmaid.domain.AString;
import de.quantummaid.reflectmaid.GenericType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static de.quantummaid.mapmaid.builder.customtypes.SerializationOnlyType.serializedObject;
import static de.quantummaid.mapmaid.testsupport.givenwhenthen.Marshallers.jsonMarshaller;
import static de.quantummaid.mapmaid.testsupport.givenwhenthen.Unmarshallers.jsonUnmarshaller;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class StateLogSpecs {

    @Test
    public void stateLogIsSerializable() {
        final MapMaid mapMaid1 = MapMaid.aMapMaid()
                .serializing(serializedObject(StateLog.class)
                        .withField("entries", GenericType.genericType(List.class, LogEntry.class), StateLog::entries))
                .serializing(serializedObject(LoggedState.class)
                        .withField("detectionRequirementReasons", String.class, LoggedState::buildDetectionRequirementReasons)
                        .withField("type", String.class, LoggedState::buildTypeDescription)
                        .withField("state", String.class, LoggedState::buildStateName)
                )
                .withAdvancedSettings(advancedBuilder -> advancedBuilder.usingJsonMarshaller(jsonMarshaller(), jsonUnmarshaller()))
                .build();

        final MapMaid mapMaid2 = MapMaid.aMapMaid()
                .serializingAndDeserializing(AString.class)
                .build();
        final StateLog stateLog = mapMaid2.debugInformation().stateLog();

        final String json = mapMaid1.serializeToJson(stateLog);

        final Gson gson = new Gson();
        final Map<?, ?> actualMap = gson.fromJson(json, Map.class);
        final String expectedJson = "" +
                "{\n" +
                "  \"entries\": [\n" +
                "    {\n" +
                "      \"changedStates\": [\n" +
                "        {\n" +
                "          \"detectionRequirementReasons\": \"serialization: 1, deserialization: 0, object: 0, primitive: 0, manual serializer: false, manual deserializer: false\",\n" +
                "          \"type\": \"de.quantummaid.mapmaid.domain.AString\",\n" +
                "          \"state\": \"ToBeDetected\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"signal\": \"add serialization to AString\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"changedStates\": [\n" +
                "        {\n" +
                "          \"detectionRequirementReasons\": \"serialization: 1, deserialization: 1, object: 0, primitive: 0, manual serializer: false, manual deserializer: false\",\n" +
                "          \"type\": \"de.quantummaid.mapmaid.domain.AString\",\n" +
                "          \"state\": \"ToBeDetected\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"signal\": \"add deserialization to AString\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"changedStates\": [\n" +
                "        {\n" +
                "          \"detectionRequirementReasons\": \"serialization: 1, deserialization: 1, object: 0, primitive: 0, manual serializer: false, manual deserializer: false\",\n" +
                "          \"type\": \"de.quantummaid.mapmaid.domain.AString\",\n" +
                "          \"state\": \"Resolving\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"signal\": \"detect\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"changedStates\": [\n" +
                "        {\n" +
                "          \"detectionRequirementReasons\": \"serialization: 1, deserialization: 1, object: 0, primitive: 0, manual serializer: false, manual deserializer: false\",\n" +
                "          \"type\": \"de.quantummaid.mapmaid.domain.AString\",\n" +
                "          \"state\": \"Resolved\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"signal\": \"resolve\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        final Map<?, ?> expectedMap = gson.fromJson(expectedJson, Map.class);
        assertThat(actualMap, is(expectedMap));
    }
}
