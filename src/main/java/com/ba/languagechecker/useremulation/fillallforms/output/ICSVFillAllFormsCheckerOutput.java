package com.ba.languagechecker.useremulation.fillallforms.output;

import java.util.List;

import com.ba.languagechecker.entities.PageResult;
import com.ba.languagechecker.entities.SentenceResult;

public interface ICSVFillAllFormsCheckerOutput {
	
	public void outputPageResults(final List<SentenceResult> wrongSentences);

}
