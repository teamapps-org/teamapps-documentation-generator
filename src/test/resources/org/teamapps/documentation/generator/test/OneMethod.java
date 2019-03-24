package org.teamapps.documentation.generator.test;

import org.teamapps.documentation.generator.annotation.TeamAppsDocMethod;

public class OneMethod {

	/**
	 * Some good old <b>JavaDoc</b> documentation with
	 * <ol>
	 *     <li>list</li>
	 *     <li>items</li>
	 * </ol>
	 * and so on...
	 */
	@TeamAppsDocMethod(
			title = "Method Title 1"
	)
	public void theMethodNameHasNoMeaning() {
		String asdf = "something";
	}

	@TeamAppsDocMethod(
			title = "Method Title 2",
			includeMethodDeclaration = true
	)
	public void someMethodWithoutDocumentation() {
		String asdf = "something";
	}

	public void nonAnnotatedMethod() {
		
	}

}
