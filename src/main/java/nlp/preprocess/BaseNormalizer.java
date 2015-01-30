package nlp.preprocess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class BaseNormalizer {

	char[] charMap;
	StringBuilder resultStringBuilder;
	HashSet<Character> skippedChar;
	
	public BaseNormalizer(HashMap<Character, Character> charMap,HashSet<Character> skippedChar) {
		this.charMap=new char[Character.MAX_VALUE];
		for (Entry<Character, Character> chToCh : charMap.entrySet()) {
			this.charMap[chToCh.getKey()]=chToCh.getValue();
		}
		this.resultStringBuilder=new StringBuilder();
		this.skippedChar=new HashSet<Character>(skippedChar);
	}

	
	public String normalize(String text){
		resultStringBuilder.setLength(0);
		for (int i = 0; i < text.length(); i++) {
			if(this.skippedChar.contains(text.charAt(i)))
				continue;
			resultStringBuilder.append(text.charAt(i));
		}
		return resultStringBuilder.toString();
	}
}
