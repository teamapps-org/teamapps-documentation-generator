package org.teamapps.documentation.generator.annotation;

public @interface TeamAppsDocumentation {

	public String title();

	public boolean includeMethodDeclaration() default false;

}
