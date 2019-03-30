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
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.teamapps.documentation.generator.annotation.TeamAppsDocClass;
import org.teamapps.documentation.generator.annotation.TeamAppsDocMethod;
import org.teamapps.documentation.generator.grammar.java9.Java9Lexer;
import org.teamapps.documentation.generator.grammar.java9.Java9Parser;

import java.util.List;

public class AstUtil {

	public static Java9Parser.NormalAnnotationContext getDocAnnotation(Java9Parser.MethodDeclarationContext methodDecl) {
		for (Java9Parser.MethodModifierContext modifier : methodDecl.methodModifier()) {
			if (modifier.annotation() != null) {
				Java9Parser.AnnotationContext annotation = modifier.annotation();
				if (annotation.normalAnnotation() != null && annotation.normalAnnotation().typeName().getText().equals(TeamAppsDocMethod.class.getSimpleName())) {
					return annotation.normalAnnotation();
				}
			}
		}
		return null;
	}

	public static Java9Parser.NormalAnnotationContext getDocAnnotation(Java9Parser.NormalClassDeclarationContext classDecl) {
		for (Java9Parser.ClassModifierContext modifier : classDecl.classModifier()) {
			if (modifier.annotation() != null) {
				Java9Parser.AnnotationContext annotation = modifier.annotation();
				if (annotation.normalAnnotation() != null && annotation.normalAnnotation().typeName().getText().equals(TeamAppsDocClass.class.getSimpleName())) {
					return annotation.normalAnnotation();
				}
			}
		}
		return null;
	}

	public static String extractMethodJavadoc(BufferedTokenStream tokenStream, Java9Parser.MethodDeclarationContext methodDeclaration) {
		Token start = methodDeclaration.getStart();
		int tokenIndex = start.getTokenIndex();
		List<Token> commentChannel = tokenStream.getHiddenTokensToLeft(tokenIndex, Java9Lexer.COMMENTS);
		if (commentChannel != null) {
			Token cmt = commentChannel.get(0);
			if (cmt != null) {
				String commentText = cmt.getText();
				if (commentText.startsWith("/**")) {
					return commentText
							.replaceAll("\\s+\\*+/", "")
							.replaceAll("\r?\n\\s*\\* ", "\n")
							.replaceAll("(/\\*+)\\s*\r?\n?", "");

				}
			}
		}
		return null;
	}

	public static String getStringAnnotationValue(Java9Parser.NormalAnnotationContext annotation, String attributeName) {
		Java9Parser.ElementValueContext annotationValue = getAnnotationValue(annotation, attributeName);
		if (annotationValue != null) {
			String title = annotationValue.getText();
			return title.substring(1, title.length() - 1);
		} else {
			return null;
		}
	}

	public static Boolean getBooleanAnnotationValue(Java9Parser.NormalAnnotationContext annotation, String attributeName, boolean defaultValue) {
		Java9Parser.ElementValueContext value = getAnnotationValue(annotation, attributeName);
		if (value != null) {
			return Boolean.parseBoolean(value.getText());
		} else {
			return defaultValue;
		}
	}

	public static Java9Parser.ElementValueContext getAnnotationValue(Java9Parser.NormalAnnotationContext annotation, String attributeName) {
		return annotation.elementValuePairList().elementValuePair().stream()
				.filter(elementValuePairContext -> elementValuePairContext.identifier().getText().equals(attributeName))
				.map(elementValuePairContext -> elementValuePairContext.elementValue())
				.findFirst().orElse(null);
	}

	public static String getFullText(ParserRuleContext context) {
		if (context.getStart() == null || context.getStop() == null || context.getStart().getStartIndex() < 0 || context.getStop().getStopIndex() < 0) {
			return context.getText(); // Fallback
		}
		return context.getStart().getInputStream().getText(Interval.of(context.getStart().getStartIndex(), context.getStop().getStopIndex()));
	}

}
