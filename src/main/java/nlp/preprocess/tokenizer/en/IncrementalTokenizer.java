package nlp.preprocess.tokenizer.en;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import tools.util.collection.HashSertInteger;
import tools.util.file.Write.HashMapWriteType;
import tools.util.sort.Collection.SortType;

public class IncrementalTokenizer {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		IncrementalTokenizer incrementalTokenizer=new IncrementalTokenizer();
		incrementalTokenizer.getTokenizerToken(10000, 0.7, 1, 1);
	}
	
	HashMap<String, Integer> tokensDistribution;
	HashSet<String> tokensWithSmallerThanThreeChars;
	
	
//	public IncrementalTokenizer(HashMap<String, Integer> inTokenDistribution,HashSet<String> inTokenWithSmallerThanThreeChars) {
//		this.tokensDistribution=new HashMap<String, Integer>(inTokenDistribution);
//		this.tokensWithSmallerThanThreeChars=new HashSet<String>(inTokenWithSmallerThanThreeChars);
//	}
	
	public IncrementalTokenizer() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		String baseAddress = tools.util.File.getParent(tools.util.File.getParent(tools.util.File.getCurrentDirectory()))+"/NLP/src/nlp/preprocess/tokenizer/en/";
		String fileAddressSmallTokens=baseAddress+"tokensSmallerThanThreeChar.dist";;
		this.tokensWithSmallerThanThreeChars=new HashSet<String>(tools.util.file.Reader.getStringFromTextFile(fileAddressSmallTokens, false));
		String fileAddressTokensDist=baseAddress+"rawTermToken.dist";
		this.tokensDistribution=new HashMap<String, Integer>();
		for (Entry<String, Integer> entry : tools.util.file.Reader.getKeyValueStringIntegerFromTextFile(fileAddressTokensDist, 0, false, "\t").entrySet()) {
			if(entry.getKey().length()<3){
				if(this.tokensWithSmallerThanThreeChars.contains(entry.getKey()))
					this.tokensDistribution.put(entry.getKey(), entry.getValue());
			}
			else
				this.tokensDistribution.put(entry.getKey(), entry.getValue());
		}
	}
	
	public HashMap<String,Integer> getTokenizerToken(int startMinCount,double startDecrementStepRatio,int endMinCount,double endIncrementRatio){
		HashMap<String, Integer> validTokens = new HashMap<String, Integer>(this.tokensDistribution);
		HashSertInteger<String> tempValidTokens = new HashSertInteger<String>(this.tokensDistribution.size());
		int endMaxFrequency = 0;
		int minFrequency=0;
		
		for (int i = 1; i < 100; i++) {
			TokenizerEnglishSimple tokenizerEnglishSimple=new TokenizerEnglishSimple(validTokens,0,false);
			endMaxFrequency=(int)(endMinCount+i*Math.pow(endIncrementRatio, i-1));
			double x = Math.pow(startDecrementStepRatio, i-1);//==1?0:Math.pow(startDecrementStepRatio, i-1);
			minFrequency=(int) (startMinCount*x);
			int delta = minFrequency-endMaxFrequency;
			if(delta<=0)
				break;
			for (Entry<String, Integer> entry : validTokens.entrySet()) {
				if(entry.getValue()<=endMaxFrequency){
					String[] split = tokenizerEnglishSimple.tokenize(entry.getKey()).split(" ");
//					if(split.length>1)
//						System.out.println(split);
					for (String token : split) {
						tempValidTokens.put(token, entry.getValue());
					}
				}
				else
					tempValidTokens.put(entry.getKey(), entry.getValue());
			}
			validTokens.clear();
			validTokens.putAll(tempValidTokens.getHashMap());
			tempValidTokens.clear();
			tools.util.file.Write.mapToTextFileSortedByValue(validTokens, "/media/saeed/Temp/pdf data set/sentence/inc.token"+i, SortType.DECREMENTAL, HashMapWriteType.KEYVALUE);
			System.out.println(x+"(delta:"+minFrequency+"-"+endMaxFrequency+"="+delta+")step "+i+" complete.");
		}
		return validTokens;
	}
}
