package org.teamapps.documentation.generator.model;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.teamapps.documentation.generator.grammar.java9.Java9Parser;

import static org.teamapps.documentation.generator.model.AstUtil.*;
import static org.teamapps.documentation.generator.model.StringUtil.normalizeIndentation;

public class DocMethod {
	private final Java9Parser.MethodDeclarationContext methodDeclaration;
	private final BufferedTokenStream tokenStream;
	private final Java9Parser.NormalAnnotationContext docAnnotation;
	private final String javaDoc;

	public DocMethod(Java9Parser.MethodDeclarationContext methodDecl, BufferedTokenStream tokenStream) {
		this.methodDeclaration = methodDecl;
		this.tokenStream = tokenStream;
		this.javaDoc = extractMethodJavadoc(tokenStream, methodDeclaration);
		this.docAnnotation = getDocAnnotation(methodDeclaration);
		// this.methodDeclaration.methodModifier().removeIf(methodModifierContext -> {
		// 	boolean isDocAnnotation = methodModifierContext.annotation() != null && methodModifierContext.annotation().normalAnnotation() == docAnnotation;
		// 	System.out.println(isDocAnnotation);
		// 	return isDocAnnotation;
		// });
	}

	public String getTitle() {
		return getStringAnnotationValue(docAnnotation, "title");
	}

	public String getJavaDoc() {
		return javaDoc;
	}

	public boolean isIncludeMethodDeclaration() {
		return getBooleanAnnotationValue(docAnnotation, "includeMethodDeclaration", false);
	}

	public String getBodyCode() {
		return normalizeIndentation(getFullText(this.methodDeclaration.methodBody().block().blockStatements()));
	}

	public String getFullMethodCode() {
		return normalizeIndentation(getFullText(this.methodDeclaration));
	}

}
