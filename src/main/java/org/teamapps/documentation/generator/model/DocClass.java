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
