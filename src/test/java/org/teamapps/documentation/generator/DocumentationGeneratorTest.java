/*-
 * ========================LICENSE_START=================================
 * TeamApps
 * ---
 * Copyright (C) 2014 - 2019 TeamApps.org
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.teamapps.documentation.generator;

import freemarker.cache.ClassTemplateLoader;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class DocumentationGeneratorTest {

	@Test
	public void test() {
		InputStream inputStream = getClass().getResourceAsStream("/org/teamapps/documentation/generator/test/TestDocClass.java");
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		new DocumentationGenerator().generateDocumentation(
				inputStreamReader,
				new ClassTemplateLoader(DocumentationGenerator.class.getClassLoader(), "/org/teamapps/documentation/generator/test"),
				new File("target/generator-test-output/TestDocClass.html"),
				Collections.emptyMap()
		);
	}

}
