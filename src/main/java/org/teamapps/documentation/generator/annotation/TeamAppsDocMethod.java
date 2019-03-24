package org.teamapps.documentation.generator.annotation;

public @interface TeamAppsDocMethod {

	String title();

	boolean includeMethodDeclaration() default false;

}
