package nlp.languagemodel.data;

public class TriGram extends BiGram{

	protected String term3;
	
	public TriGram(String term1,String term2,String term3,long frequency) {
		super(term1,term2, frequency);
		this.term3=term3;
	}

	public String getTerm3() {
		return term3;
	}
	
}
