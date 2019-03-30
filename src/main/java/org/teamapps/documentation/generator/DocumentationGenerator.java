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
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class DocumentationGenerator {

	public DocumentationGenerator() {
	}

	public void generateDocumentation(Reader javaClassReader, TemplateLoader freemarkerTemplateLoader, File targetHtmlFile, Map<String, Object> additionalRootVariables) {
		try {
			Java9Parser parser = ParserUtil.createJava9arser(javaClassReader);
			Java9Parser.OrdinaryCompilationContext compilationContext = parser.ordinaryCompilation();
			Java9Parser.NormalClassDeclarationContext classDecl = compilationContext.typeDeclaration().get(0).classDeclaration().normalClassDeclaration();
			Files.createDirectories(targetHtmlFile.getParentFile().toPath());

			DocClass docClass = new DocClass(classDecl, ((BufferedTokenStream) parser.getTokenStream()));

			generateHtml(docClass, targetHtmlFile, freemarkerTemplateLoader, additionalRootVariables);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	private void generateHtml(DocClass docClass, File targetHtmlFile, TemplateLoader freemarkerTemplateLoader, Map<String, Object> additionalRootVariables) throws IOException, TemplateException {
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
		try (Writer out = new OutputStreamWriter(new FileOutputStream(targetHtmlFile))) {
			temp.process(root, out);
		}
	}

}
