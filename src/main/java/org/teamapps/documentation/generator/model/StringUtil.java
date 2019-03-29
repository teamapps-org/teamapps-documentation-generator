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
