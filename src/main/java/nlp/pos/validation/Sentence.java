package nlp.pos.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tools.util.collection.KeyValue;

public class Sentence {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String cpos = "N,COM,SING";
		char posDelimiter = ',';
		int offset = 0;
		int i = 5;
		for (int j = 0; j <i; j++) {
			offset+=cpos.indexOf(posDelimiter, offset)+1;
		}
		System.out.println(cpos.substring(0, cpos.indexOf(posDelimiter,offset)<0?cpos.length():cpos.indexOf(posDelimiter,offset))+" ");
//		String[] originalPos = new String[] { "a", "sd", "df" };
//		String[] inputPos = new String[] { "sd", "df" };
//		System.out.println(evaluateWithTrueCount(originalPos, inputPos));
	}

	public static int evaluateWithTrueCount(String[] originalPos,
			ArrayList<KeyValue<String, Double>> inputPosToken) throws Exception {
		if (originalPos.length != inputPosToken.size())
			throw new Exception("input pos count("+inputPosToken.size()+") not equal with original pos("+originalPos.length+")");
		int trueCount = 0;
		for (int i = 0; i < inputPosToken.size(); i++) {
			if (originalPos[i].equals(inputPosToken.get(i).getKey()))
				trueCount++;
		}
		return trueCount;
	}
	
	public static int evaluateWithTrueCount(String[] originalPos,
			String[] inputPos) throws Exception {
		if (originalPos.length != inputPos.length)
			throw new Exception("input pos count not equal with original pos");
		int trueCount = 0;
		for (int i = 0; i < inputPos.length; i++) {
			if (originalPos[i].equals(inputPos[i]))
				trueCount++;
		}
		return trueCount;
	}

	public static double evaluateWithPrecision(String[] originalPos,
			String[] inputPos) throws Exception {
		return evaluateWithTrueCount(originalPos, inputPos)
				/ (double) originalPos.length;
	}

	public static KeyValue<Integer, Integer> evaluateWithTrueCount(
			HashMap<String, ArrayList<KeyValue<String, Double>>> inputSentencesMapResult,
			HashMap<Integer, KeyValue<String, String>> originalPos)
			throws Exception {
		int tokenCount = 0;
		int currectTokenCount = 0;
		String sentence;
		String pos;
		for (Entry<Integer, KeyValue<String, String>> entry : originalPos.entrySet()) {
			sentence = entry.getValue().getKey();
			pos= entry.getValue().getValue();
			String[] originPosToken = pos.split("(\\s)+");
			ArrayList<KeyValue<String, Double>> inputPosToken = inputSentencesMapResult.get(sentence);
			currectTokenCount += evaluateWithTrueCount(originPosToken, inputPosToken);
			tokenCount+=originPosToken.length;
		}
		return new KeyValue<Integer, Integer>(currectTokenCount, tokenCount);
	}

}

