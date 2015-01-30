package nlp.tokenizer.simple;

public class TokenizerLanguageModel {

	static public String[] simpleTokenizeSentence(String string) {
		String normalizeString = nlp.normalizer.simple.NormalizerLanguageModel
				.getNormalizedString(string);
		String[] normalizedSentence = normalizeString.split("\\.");
		String[] result = new String[normalizedSentence.length];
		for (int i = 0; i < normalizedSentence.length; i++) {
			String tokensString = "";
			String str;
			for (String token : normalizedSentence[i].split(" ")) {
				str = token;
				str = str.replaceAll("[a-z|A-Z]+", " !!!");
				str = str.replaceAll("[0-9]+", " @@@");
				// if
				// (token.matches("([a-zAZ]+[0-9a-zAZ]*)|([0-9]+[0-9a-zAZ]*)"))
				// str = " !!!";
				// if (token
				// .matches("([0-9]+[.]*[0-9]*)|([.][0-9]+)|([۰-۹]+[.]*[۰-۹]*)|([.][۰-۹]+)"))
				// str = " @@@";
				tokensString += str + " ";
			}
			result[i] = "<s> " + tokensString.replaceAll("(\\s)+", " ")
					+ "</s>";
		}
		return result;
	}

	char[] replacedBySpaceChar;
	String[] replacedBySpaceString;
	char[] removed;

	public TokenizerLanguageModel(char[] replacedBySpaceChar,String[] replacedBySpaceString, char[] removed) {
		this.replacedBySpaceChar = replacedBySpaceChar;
		this.replacedBySpaceString=replacedBySpaceString;
		this.removed = removed;
	}

	String tempString = "";

	public String[] tokenize(String sentence) {
		tempString = sentence;
		if (this.removed != null) {
			for (char ch : this.removed) {
				tempString = tempString.replace(ch + "", "");
			}
		}

		if (this.replacedBySpaceChar != null) {
			for (char ch : this.replacedBySpaceChar) {
				tempString = tempString.replace(ch, ' ');
			}
		}

		tempString = tempString.replaceAll("(\\s)+", " ");

		String[] tempSplit = tempString.split(" ");
		String[] result = new String[tempSplit.length];
		String str;
		int i=0;
		for (String token : tempSplit) {			
			str = token;
			str = str.toLowerCase();
			str = str.replaceAll("[0-9]+", "@@@");// for double number floating
													// point should be handled
													// before this methods.
			result[i]=str;
			i++;
		}
		// result[i] = "<s> " + tokensString.replaceAll("(\\s)+"," ") +
		// "</s>";
		return result;
	}
}
