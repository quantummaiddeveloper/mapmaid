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

package de.quantummaid.mapmaid.testsupport.domain.valid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class AComplexTypeWithNestedCollections {
    public final AString[][][][] nestedArray;
    public final List<List<List<List<ANumber>>>> nestedList;
    public final List<List<AString>[]>[] nestedMix1;
    public final List<List<ANumber[]>[]> nestedMix2;

    public static AComplexTypeWithNestedCollections deserialize(final AString[][][][] nestedArray,
                                                                final List<List<List<List<ANumber>>>> nestedList,
                                                                final List<List<AString>[]>[] nestedMix1,
                                                                final List<List<ANumber[]>[]> nestedMix2) {
        return new AComplexTypeWithNestedCollections(nestedArray, nestedList, nestedMix1, nestedMix2);
    }
}
