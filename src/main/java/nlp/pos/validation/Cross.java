package nlp.pos.validation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nlp.pos.MaximumLikelehood;
import nlp.pos.tagger.hmm.HMM;


import tools.util.Str;
import tools.util.Time;
import tools.util.collection.KeyValue;

public class Cross {

	public static void main(String[] args) throws Exception {
		String basePath="/home/saeed/Data/bijankhan/posFetcher/";
//		int foldCount=5;
		for (int foldCount = 5; foldCount <= 20; foldCount+=5) {
			for (int i = 1; i <= 5; i++) {
	//		int i = 2;
				Cross crossValidation=new Cross();
				String sentencePosFileAddress=basePath+"_dataset_level_"+i;
				int sentenceCount=330085;
				KeyValue<Integer, Integer> evaluate = crossValidation.evaluate(sentencePosFileAddress, sentenceCount, foldCount,basePath+"maximumLikelehoodResult/log-lavel"+i+"-fold"+foldCount);
				String result=evaluate.getKey()+"/"+evaluate.getValue()+" = "+Math.round(10000*((double)evaluate.getKey()/evaluate.getValue()))/(double)100;
				System.out.println(result);
				tools.util.file.Write.stringToTextFile("level("+i+") with "+crossValidation.getPosCount()+" POS tag:"+result, basePath+"maximumLikelehoodResult/level"+i+"-fold"+foldCount,true);
			}
		}
	}
	
	HashSet<String> posSet=new HashSet<String>();
	public int getPosCount() {
		return posSet.size();
	}

	ArrayList<KeyValue<Integer, KeyValue<String, String>>> datasetSentences;
	List<HashMap<Integer, KeyValue<String, String>>> folds;
	
	public KeyValue<Integer, Integer> evaluate(String sentencePosFileAddress,int sentenceCount,int foldCount,String logFileAddress) throws Exception{
		loadDataset(sentencePosFileAddress, sentenceCount);
		loadFolds(foldCount);
		
		Set<KeyValue<String, String>> trainData=new HashSet<KeyValue<String,String>>(sentenceCount);
//		MaximumLikelehood maximumLikelehood=null;
		Set<String> testSentences=new HashSet<String>();
		int resultAllCount=0;
		int resultTrueCount = 0;
		HashMap<String, ArrayList<KeyValue<String, Double>>> testSentencesMapResult=new HashMap<String, ArrayList<KeyValue<String,Double>>>();
		String log = "";
		for (int i = 0; i < this.folds.size(); i++) {
			Time.setStartTimeForNow();
			trainData.clear();
			testSentencesMapResult.clear();
			for (int j = 0; j < this.folds.size(); j++) {
				if(i!=j)
					trainData.addAll(this.folds.get(j).values());
			}
//			HMM hmmPosTaggerHmm=new HMM(trainData);
//			if(i==0)
			MaximumLikelehood maximumLikelehood=new MaximumLikelehood(trainData, "fold "+(i+1));
//			else
//				maximumLikelehood.reload(trainData, "fold "+(i+1));
			testSentences.clear();
			for (KeyValue<String, String> keyValue : this.folds.get(i).values()) {
				testSentences.add(keyValue.getKey());
			}
			maximumLikelehood.getPosArrayList(testSentences, testSentencesMapResult);
			KeyValue<Integer, Integer> evaluateWithTrueCount = Sentence.evaluateWithTrueCount(testSentencesMapResult, this.folds.get(i));
			resultTrueCount+=evaluateWithTrueCount.getKey();
			resultAllCount += evaluateWithTrueCount.getValue();
			log="fold "+(i+1)+" evaluation complete with end stage("+evaluateWithTrueCount.getKey()+"/"+evaluateWithTrueCount.getValue()+":"+(double)evaluateWithTrueCount.getKey()/evaluateWithTrueCount.getValue()+")  all:("+resultTrueCount+"/"+resultAllCount+":"+(double)resultTrueCount/resultAllCount+")";
			tools.util.file.Write.stringToTextFile(log, logFileAddress, true);
			System.out.println(log);
		}
		return new KeyValue<Integer, Integer>(resultTrueCount, resultAllCount);
	}

	private void loadDataset(String sentencePosFileAddress,int sentenceCount) throws Exception{
		Time.setStartTimeForNow();
		this.datasetSentences=new ArrayList<KeyValue<Integer,KeyValue<String, String>>>(sentenceCount);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(sentencePosFileAddress), Charset.forName("utf8")));
		String newLine="";
		String[] newLineSplit = new String[2];
		int sentenceCounter=0;
		int defectedSentenceCount = 0;
		while ((newLine = reader.readLine()) != null) {
			sentenceCounter++;
			newLineSplit = newLine.split("\t");
			for (String pos : newLineSplit[1].split("(\\s)+")) {
				this.posSet.add(pos);
			}
			if(newLineSplit.length==2)
				this.datasetSentences.add(new KeyValue<Integer, KeyValue<String, String>>(
				sentenceCounter,new KeyValue<String, String>(newLineSplit[0], newLineSplit[1]) )
										  );
			else
				defectedSentenceCount++;
			if(sentenceCounter%10000==0)
				System.out.println(sentenceCounter+" line loaded.");
		}
		reader.close();
		System.out.println("POS sentence file read complete in "+Time.getTimeLengthForNow()+" ms.");
		if(defectedSentenceCount*10/sentenceCounter>5)
			throw new Exception(sentencePosFileAddress+" pos file not valid with line:<token[1] ... token[k]>\\t<pos[1] ... pos[k]>");		
	}
	
	private void loadFolds(int foldCount) {
		Time.setStartTimeForNow();
		this.folds=new ArrayList<HashMap<Integer,KeyValue<String,String>>>(foldCount);
		List<HashSet<Integer>> partitionedRandomIntegerSet = tools.util.collection.RandomSet.getPartitionedRandomIntegerSet(this.datasetSentences.size(), foldCount);
		for (HashSet<Integer> hashSet : partitionedRandomIntegerSet) {
			HashMap<Integer, KeyValue<String, String>> newMap = new HashMap<Integer,KeyValue<String,String>>(hashSet.size());
			for (Integer index : hashSet) {
				newMap.put(index, datasetSentences.get(index).getValue());
			}
			this.folds.add(newMap);
		}
		System.out.println("loadFolds for "+foldCount+" fold complete in "+Time.getTimeLengthForNow()+" ms.");
	}
	
}
