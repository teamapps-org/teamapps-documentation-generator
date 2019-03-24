package org.teamapps.documentation.generator.model;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.teamapps.documentation.generator.grammar.java9.Java9Parser;

import java.util.List;
import java.util.stream.Collectors;

import static org.teamapps.documentation.generator.model.AstUtil.getDocAnnotation;

public class DocClass {

	private final Java9Parser.NormalClassDeclarationContext classDeclaration;
	private final BufferedTokenStream tokenStream;

	private final List<DocMethod> methods;
	private final Java9Parser.NormalAnnotationContext docClassAnnotation;

	public DocClass(Java9Parser.NormalClassDeclarationContext classDeclaration, BufferedTokenStream tokenStream) {
		this.classDeclaration = classDeclaration;
		this.tokenStream = tokenStream;
		this.docClassAnnotation = AstUtil.getDocAnnotation(classDeclaration);

		this.methods = classDeclaration.classBody().classBodyDeclaration().stream()
				.filter(decl -> decl.classMemberDeclaration() != null && decl.classMemberDeclaration().methodDeclaration() != null)
				.map(decl -> decl.classMemberDeclaration().methodDeclaration())
				.filter(methodDecl -> getDocAnnotation(methodDecl) != null)
				.map(methodDecl -> new DocMethod(methodDecl, tokenStream))
				.collect(Collectors.toList());
	}

	public String getTitle() {
		return docClassAnnotation != null ? AstUtil.getStringAnnotationValue(docClassAnnotation, "title") : null;
	}

	public List<DocMethod> getDocMethods() {
		return methods;
	}
}
