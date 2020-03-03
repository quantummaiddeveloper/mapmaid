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

import de.quantummaid.mapmaid.testsupport.domain.repositories.MyRepository;
import de.quantummaid.mapmaid.testsupport.domain.valid.*;
import de.quantummaid.mapmaid.testsupport.givenwhenthen.Given;
import org.junit.jupiter.api.Test;

import static de.quantummaid.mapmaid.MapMaid.aMapMaid;
import static de.quantummaid.mapmaid.builder.recipes.scanner.ClassScannerRecipe.addAllReferencedClassesIn;

public final class BuilderSpecs {

    @Test
    public void givenValidDataTransferObject_whenBuildingWithDataTransferObject_thenReturnsCorrectDeserializer() {
        Given.given(
                aMapMaid()
                        .withManuallyAddedTypes(AComplexType.class, AString.class, ANumber.class)
                        .build()
        )
                .when().theDefinitionsAreQueried()
                .theDefinitionsContainExactlyTheSerializedObjects(AComplexType.class)
                .theDefinitionsContainExactlyTheCustomPrimitives(AString.class, ANumber.class);
    }

    @Test
    public void givenValidCustomPrimitive_whenBuildingWithCustomPrimitive_thenReturnsCorrectDeserializer() {
        Given.given(
                aMapMaid().mapping(AString.class).build()
        )
                .when().theDefinitionsAreQueried()
                .theDefinitionsContainExactlyTheCustomPrimitives(AString.class)
                .theDefinitionsContainExactlyTheSerializedObjects();
    }

    @Test
    public void allKnownCollectionsAreSupported() {
        Given.given(
                aMapMaid()
                        .mapping(AComplexTypeWithDifferentCollections.class)
                        .build()
        )
                .when().theDefinitionsAreQueried()
                .theDefinitionsContainExactlyTheSerializedObjects(AComplexTypeWithDifferentCollections.class)
                .theDefinitionsContainExactlyTheCustomPrimitives(ANumber.class);
    }

    @Test
    public void referencesInClassesCanBeScanned() {
        Given.given(
                aMapMaid()
                        .usingRecipe(addAllReferencedClassesIn(MyRepository.class))
                        .build()
        )
                .when().theDefinitionsAreQueried()
                .theDefinitionsContainExactlyTheSerializedObjects()
                .theDefinitionsContainExactlyTheCustomPrimitives(APrimitiveBoolean.class, APrimitiveInteger.class);
    }
}
