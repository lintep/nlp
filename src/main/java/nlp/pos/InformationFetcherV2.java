package nlp.pos;

import java.util.Collection;
import java.util.HashMap;

import nlp.pos.validation.SentenceV2;

public class InformationFetcherV2 {


	public InformationFetcherV2(Collection<SentenceV2> sentences) {
		for (SentenceV2 sentence : sentences) {
			String sentenceString = sentence.getSentenceString();
		}
	}

	public HashMap<String, Integer> getTokenId() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<Long, Integer> getPosTaggedTTF() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<Long, Integer> getLanguageModelTaggedTTF() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
