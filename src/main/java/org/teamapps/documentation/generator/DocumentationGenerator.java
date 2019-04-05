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

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.teamapps.documentation.generator.grammar.java9.Java9Parser;
import org.teamapps.documentation.generator.model.DocClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class DocumentationGenerator {

	public DocumentationGenerator() {
	}

	public void generateDocumentation(Reader javaClassReader, TemplateLoader freemarkerTemplateLoader, File targetHtmlFile, Map<String, Object> additionalRootVariables) {
		try {
			Files.createDirectories(targetHtmlFile.getParentFile().toPath());
			try (Writer writer = new OutputStreamWriter(new FileOutputStream(targetHtmlFile), StandardCharsets.UTF_8)) {
				generateDocumentation(javaClassReader, freemarkerTemplateLoader, writer, additionalRootVariables);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void generateDocumentation(Reader javaClassReader, TemplateLoader freemarkerTemplateLoader, Writer targetHtmlFile, Map<String, Object> additionalRootVariables) {
		try {
			Java9Parser parser = ParserUtil.createJava9arser(javaClassReader);
			Java9Parser.OrdinaryCompilationContext compilationContext = parser.ordinaryCompilation();
			Java9Parser.NormalClassDeclarationContext classDecl = compilationContext.typeDeclaration().get(0).classDeclaration().normalClassDeclaration();

			DocClass docClass = new DocClass(classDecl, ((BufferedTokenStream) parser.getTokenStream()));

			generateHtml(docClass, targetHtmlFile, freemarkerTemplateLoader, additionalRootVariables);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	private void generateHtml(DocClass docClass, Writer writer, TemplateLoader freemarkerTemplateLoader, Map<String, Object> additionalRootVariables) throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setTemplateLoader(freemarkerTemplateLoader);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);

		Map<String, Object> root = new HashMap<>();
		root.putAll(additionalRootVariables);
		root.put("class", docClass);

		Template temp = cfg.getTemplate("documentation.ftlh");
		temp.process(root, writer);
	}

}
