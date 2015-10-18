/*
 *  Copyright 2015 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.flavour.routing.parsing;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Alexey Andreev
 */
public class PathParserTest {
    @Test
    public void parsesPath() {
        PathParserBuilder builder = new PathParserBuilder();
        builder.path().text("/users");
        builder.path().text("/users/").escapedRegex("[0-9]+");
        builder.path().text("/users/").escapedRegex("[0-9]+").text("/edit");
        PathParser parser = builder.build();

        assertThat(parser.parse("/users").getCaseIndex(), is(0));

        PathParserResult result = parser.parse("/users/23");
        assertThat(result.getCaseIndex(), is(1));
        assertThat(result.start(0), is(7));
        assertThat(result.end(0), is(9));

        result = parser.parse("/users/23/edit");
        assertThat(result.getCaseIndex(), is(2));
        assertThat(result.start(0), is(7));
        assertThat(result.end(0), is(9));
    }
}