package nlp.preprocess;

import java.util.HashSet;

import com.google.common.base.Splitter;

public class BaseTokenizer {

	char[] splitter;
//		Splitter splitter

	public BaseTokenizer(HashSet<Character> splitter) {
		this.splitter=new char[splitter.size()];
		int i=0;
		Splitter.on((char)this.splitter[0]);
		for (char ch : splitter) {
			this.splitter[i]=ch;
			i++;
		}
	}
	
//	public String tokenize(String text){
//	}
}
