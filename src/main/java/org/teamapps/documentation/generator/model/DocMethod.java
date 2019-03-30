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
