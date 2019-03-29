package org.teamapps.documentation.generator;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DocumentationGeneratorTest {

	@Test
	public void test() {
		InputStream inputStream = getClass().getResourceAsStream("/org/teamapps/documentation/generator/test/TestDocClass.java");
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		new DocumentationGenerator().generateDocumentation(inputStreamReader, new File("target/generator-test-output/TestDocClass.html"));
	}

}