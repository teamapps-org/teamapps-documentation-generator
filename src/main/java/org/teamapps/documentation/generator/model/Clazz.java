package org.teamapps.documentation.generator.model;

import org.teamapps.documentation.generator.grammar.java9.Java9Parser;

import java.util.List;

public class Clazz {

	private final Java9Parser.NormalClassDeclarationContext classDeclarationContext;

	public Clazz(Java9Parser.NormalClassDeclarationContext classDeclarationContext) {
		this.classDeclarationContext = classDeclarationContext;
	}

	public String getIdentifier() {
		return classDeclarationContext.identifier().getText();
	}

	public List<Java9Parser.MethodDeclarationContext> getDocumentationMethods() {
//		classDeclarationContext.classBody().classBodyDeclaration().stream()
//				.map(decl -> decl.classMemberDeclaration().methodDeclaration()).
		return null;
	}
}
