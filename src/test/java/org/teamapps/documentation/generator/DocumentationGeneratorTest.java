package org.teamapps.documentation.generator;

import org.junit.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DocumentationGeneratorTest {

	@Test
	public void testOneMethod() throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/org/teamapps/documentation/generator/OneMethod.java"), StandardCharsets.UTF_8);
		new DocumentationGenerator().generateDocumentation(inputStreamReader, new File("target/generator-test-output/OneMethod.html"));
	}

	@Test
	public void asdf() throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/org/teamapps/documentation/generator/OneMethod.java"), StandardCharsets.UTF_8);
		new DocumentationGenerator().asdf(inputStreamReader);
	}
}