package com.ba.languagechecker.pagechecker;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextManipulator {
	private static final String EMPTY_STRING = "";
	private static final String BODY_ELEMENT_NAME = "body";
	private static final String REFERENCES_PATTERN_EXPR_SIGN = "\".*\"";

	private static final String REFERENCES_PATTERN_EXPR_GL = "<<.*>>";

	private static Logger _log = Logger.getLogger(TextManipulator.class
			.getCanonicalName());

	public static String getTextWithoutReferences(final String text) {
		return text.replaceAll(REFERENCES_PATTERN_EXPR_SIGN, EMPTY_STRING)
				.replaceAll(REFERENCES_PATTERN_EXPR_GL, EMPTY_STRING);

	}

	public static String getBodyOfHtml(final String html,
			final Collection<String> divElementsToClear) {
		final Document document = Jsoup.parse(html);
		_log.debug(html);
		for (String div_id : divElementsToClear) {
			final Elements divs = document.select(div_id);
			if (divs == null)
				continue;
			final Element div = divs.first();
			if (div == null)
				continue;
			_log.debug("removed div " + div.text());
			div.text(EMPTY_STRING);
		}
		final String body = document.select(BODY_ELEMENT_NAME).text();
		_log.debug(body);
		return body;
	}
}
