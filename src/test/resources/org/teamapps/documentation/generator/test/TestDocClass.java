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
package org.teamapps.documentation.generator.test;

import org.teamapps.documentation.generator.annotation.TeamAppsDocClass;
import org.teamapps.documentation.generator.annotation.TeamAppsDocMethod;

import java.util.Arrays;

@TeamAppsDocClass(title = "Some Title for the whole class")
public class TestDocClass {

	/**
	 * Some good old <b>JavaDoc</b> documentation with
	 * <ol>
	 *     <li>list</li>
	 *     <li>items</li>
	 * </ol>
	 * and so on...
	 */
	@TeamAppsDocMethod(title = "Method Title 1", images = {"image1.png", "image2.png"})
	public void theMethodNameHasNoMeaning() {
		WebController controller = new SimpleWebController(context -> {
			Panel panel = new Panel(Icons.FOLDER3, "Mein Panel");
			panel.setStretchContent(false);
			panel.setPadding(10);

			CurrencyField currencyField = new CurrencyField();
			currencyField.setCurrencyList(Arrays.asList(Currency.EUR, Currency.USD));
			currencyField.setPrecision(4);

			CurrencyValue value = currencyField.getValue();

			panel.setContent(currencyField);
			return panel;
		});

		new TeamAppsJettyEmbeddedServer(controller, Files.createTempDir()).start();
	}

	@SomeOtherAnnotation
	@TeamAppsDocMethod(title = "Method Title 2", includeMethodDeclaration = true)
	@YetAnotherAnnotation(foo = "bar")
	public void someMethodWithoutDocumentation() {
		WebController controller = new SimpleWebController(context -> {
			Panel panel = new Panel(Icons.FOLDER3, "Mein Panel");
			panel.setStretchContent(false);
			panel.setPadding(10);


			NumberField numberField = new NumberField(1);

			numberField.setMinValue(0);
			numberField.setMaxValue(100);
			numberField.setSliderStep(0.5);
			numberField.setSliderMode(NumberFieldSliderMode.VISIBLE_IF_FOCUSED);

			// numberField.onValueChanged.addListener();

			Slider slider = new Slider();
			slider.setMin(0);
			slider.setMax(100);

			slider.getValue();

	        // slider.onValueChanged.addListener();
			                 
			panel.setContent(numberField);
			return panel;
		});

		new TeamAppsJettyEmbeddedServer(controller, Files.createTempDir()).start();
	}

	public void nonAnnotatedMethod() {
		String x = "asdf";
	}

}
