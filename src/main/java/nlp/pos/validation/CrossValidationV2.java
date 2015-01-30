package nlp.pos.validation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import nlp.pos.MaximumLikelehood;
import nlp.pos.Tokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tools.util.Time;
import tools.util.collection.KeyValue;

public class CrossValidationV2 {

	private ArrayList<SentenceV2> datasetAllSentence;
	private ArrayList<HashMap<Integer,SentenceV2>> folds;
	
	private HashMap<Integer,SentenceV2> trainSet;
	private HashMap<Integer,SentenceV2> testSet;
	
	Tokenizer posTokenizer;
	
	public CrossValidationV2(String posDataSetSonFileAddress) throws IOException, ParseException {
		loadDataset(posDataSetSonFileAddress);		
		this.trainSet=new HashMap<Integer,SentenceV2>();
		this.testSet=new HashMap<Integer,SentenceV2>();
	}

	public void evaluate(int foldCount){
		loadFolds(foldCount);
		for (int i = 0; i < this.folds.size(); i++) {
			Time.setStartTimeForNow();
			trainSet.clear();
			testSet.clear();
			for (int j = 0; j < this.folds.size(); j++) {
				if(i==j){
					testSet.putAll(this.folds.get(j));
				}
				else{
					trainSet.putAll(this.folds.get(j));
				}
			}
			this.posTokenizer=new Tokenizer(trainSet.values(), 21);
		}
	}
	private void loadDataset(String posDataSetSonFileAddress)
			throws FileNotFoundException, IOException, ParseException {
		this.datasetAllSentence=new ArrayList<SentenceV2>();
		Time.setStartTimeForNow();
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=new JSONObject();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(posDataSetSonFileAddress);
		String newLine;
		int sentenceCounter = 0;
		while ((newLine=fileBufferReader.readLine())!=null) {
			jsonObject=(JSONObject) parser.parse(newLine);
			datasetAllSentence.add(new SentenceV2(jsonObject));
			if(sentenceCounter%10000==0)
				System.out.println(sentenceCounter+" line loaded.");
		}
		fileBufferReader.close();
		System.out.println("POS dataset read complete with "+sentenceCounter+" sentence in "+Time.getTimeLengthForNow()+" ms.");
	}
	
	private void loadFolds(int foldCount) {
		Time.setStartTimeForNow();
		this.folds=new ArrayList<HashMap<Integer,SentenceV2>>(foldCount);
		List<HashSet<Integer>> partitionedRandomIntegerSet = tools.util.collection.RandomSet.getPartitionedRandomIntegerSet(this.datasetAllSentence.size(), foldCount);
		for (HashSet<Integer> randomIntegerSet : partitionedRandomIntegerSet) {
			HashMap<Integer, SentenceV2> newMap = new HashMap<Integer,SentenceV2>(randomIntegerSet.size());
			for (Integer index : randomIntegerSet) {
				newMap.put(index, datasetAllSentence.get(index));
			}
			this.folds.add(newMap);
		}
		System.out.println("loadFolds for "+foldCount+" fold complete in "+Time.getTimeLengthForNow()+" ms.");		
	}

	
}
