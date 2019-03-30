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

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

	public static String normalizeIndentation(String s) {
		String[] lines = s.split("\r?\n");
		Pattern whiteSpacesAtBeginningOfLine = Pattern.compile("^\\s*");
		int minIndentAfterFirstLine = Arrays.stream(lines, 1, lines.length)
				.filter(StringUtils::isNotBlank)
				.mapToInt(line -> {
					Matcher matcher = whiteSpacesAtBeginningOfLine.matcher(line);
					matcher.find();
					if (matcher.group().length() == 0) {
						System.err.println(line);
					}
					return matcher.group().length();
				})
				.min().orElse(0);
		return Arrays.stream(lines)
				.map(line -> {
					line = line.replaceAll("^\\s{" + minIndentAfterFirstLine + "}", "");
					return line;
				})
				.collect(Collectors.joining("\n"));
	}

}
