package org.teamapps.documentation.generator;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.teamapps.documentation.generator.grammar.java9.Java9BaseListener;
import org.teamapps.documentation.generator.grammar.java9.Java9Lexer;
import org.teamapps.documentation.generator.grammar.java9.Java9Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentationGenerator {

	public DocumentationGenerator() {
	}

	public void generateDocumentation(Reader javaClassReader, File targetHtmlFile) {
		try {
			Java9Parser parser = ParserUtil.createJava9arser(javaClassReader);
			Java9Parser.OrdinaryCompilationContext compilationContext = parser.ordinaryCompilation();
			Java9Parser.NormalClassDeclarationContext classDecl = compilationContext.typeDeclaration().get(0).classDeclaration().normalClassDeclaration();


			Files.createDirectories(targetHtmlFile.getParentFile().toPath());
			generateHtml(classDecl, targetHtmlFile);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	public void asdf(Reader javaClassReader) throws IOException {
		class X extends Java9BaseListener {
			BufferedTokenStream tokens;

			public X(BufferedTokenStream tokens) {
				this.tokens = tokens;
			}

			@Override
			public void enterClassMemberDeclaration(Java9Parser.ClassMemberDeclarationContext ctx) {
				Token semi = ctx.getStop();
				int tokenIndex = semi.getTokenIndex();
				List<Token> cmtChannel = tokens.getHiddenTokensToLeft(tokenIndex, Java9Lexer.COMMENTS);
				if (cmtChannel != null) {
					Token cmt = cmtChannel.get(0);
					if (cmt != null) {
						String txt = cmt.getText().substring(2);
						System.out.println(txt);
					}
				}
			}


		}

		Java9Lexer lexer = new Java9Lexer(CharStreams.fromReader(javaClassReader));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java9Parser parser = new Java9Parser(tokens);
		RuleContext tree = parser.ordinaryCompilation();
		ParseTreeWalker walker = new ParseTreeWalker();
		X x = new X(tokens);
		walker.walk(x, tree);
	}

	private void generateHtml(Java9Parser.NormalClassDeclarationContext classDecl, File targetHtmlFile) throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setTemplateLoader(new ClassTemplateLoader(DocumentationGenerator.class.getClassLoader(), "/org/teamapps/documentation/template"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);

		Map<String, Object> root = new HashMap<>();
		root.put("clazz", classDecl);
//		root.put("");

		classDecl.identifier().getText();

		Template temp = cfg.getTemplate("documentation.ftlh");
		try (Writer out = new OutputStreamWriter(new FileOutputStream(targetHtmlFile))) {
			temp.process(root, out);
		}
	}

}
