package nlp.normalizer.simple;

public class NormalizerLanguageModel {

	public static String getNormalizedString(String input)
	{
		String normalizedString = UniqeNormalizer.getNormalizedString(input);
		StringBuilder sb = new StringBuilder(normalizedString.length());
		for (int i = 0 ; i < input.length(); i ++) {
			char ch = normalizedString.charAt(i);
			sb.append(getNormalizedChar(ch));
		}
		return sb.toString();
	}

	private static char getNormalizedChar(char ch) {
		switch(ch) {
		case '.': return '.';
		case '\n': return '.';
		case ';': return '.';
		case ':': return '.';
		case ',': return '.';
		case '?': return '.';
		case '!': return '.';
		case '-': return ' ';
		case '/': return ' ';
		case '\\': return ' ';
		case ')': return ' ';
		case '(': return ' ';
		case '|': return ' ';
		case '"': return ' ';
		case '_': return ' ';
		case '+': return ' ';
		case '=': return ' ';
		case '*': return ' ';
		case '#': return ' ';
		case '&': return ' ';
		case '$': return ' ';
		case '@': return ' ';
		case '%': return ' ';
		case '\'': return ' ';
		}
	return ch;
	}
}
