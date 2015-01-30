package nlp.pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;

import tools.util.collection.HashSert;

public class SimplePatternEctractor {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		String resultPath="/home/saeed/Data/pdf text/mscStudent/linux/result/";
		tools.util.Directory.create(resultPath);
//		extractLevel2("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.patternLevel2.txt");
//		extractBigram("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.bigram.txt");
//		extractLevel3("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.patternLevel3.txt");
//		extractTrigram("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.trigram.txt");
//		extractLevel4("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.patternLevel4.txt");
//		extractFourgram("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.fourgram.txt");
//		extractFivegram("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence.fivegram.txt");
		for (int i = 2; i <= 10; i++) {
			extractGram("/home/saeed/Data/pdf text/mscStudent/linux/akular.normalizedSentence",resultPath+"akular.normalizedSentence."+i+".Gram.txt",i);
		}
	}
	
	private static void extractLevel2(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length; i++) {
				for (int j = i+1; j < tokens.length; j++) {
					stringBuilder.setLength(0);
					stringBuilder.append(tokens[i]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[j]);
					result.put(stringBuilder.toString());
					counter++;
					if(counter%100000==0)
						System.out.println(counter+" level 2 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
				}
			}
		}
		System.out.println("load complete: "+counter+" level 2 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}
	
	private static void extractBigram(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length-1; i++) {
					stringBuilder.setLength(0);
					stringBuilder.append(tokens[i]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+1]);
					result.put(stringBuilder.toString());
					counter++;
					if(counter%100000==0)
						System.out.println(counter+" BiGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
			}
		}
		System.out.println("load complete: "+counter+" BiGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" BiGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" BiGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractLevel3(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length; i++) {
				for (int j = i+1; j < tokens.length; j++) {
					for (int k = j+1; k < tokens.length; k++) {
						stringBuilder.setLength(0);
						stringBuilder.append(tokens[i]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[j]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[k]);
						result.put(stringBuilder.toString());
						counter++;
						if(counter%100000==0)
							System.out.println(counter+" level 3 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
					}
				}
			}
		}
		System.out.println("load complete: "+counter+" level 3 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractTrigram(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length-2; i++) {
					stringBuilder.setLength(0);
					stringBuilder.append(tokens[i]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+1]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+2]);
					result.put(stringBuilder.toString());
					counter++;
					if(counter%100000==0)
						System.out.println(counter+" TriGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
			}
		}
		System.out.println("load complete: "+counter+" TriGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" TriGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" TriGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractLevel4(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length; i++) {
				for (int j = i+1; j < tokens.length; j++) {
					for (int k = j+1; k < tokens.length; k++) {
						for (int l = j+1; l < tokens.length; l++) {
							stringBuilder.setLength(0);
							stringBuilder.append(tokens[i]);
							stringBuilder.append(delim);
							stringBuilder.append(tokens[j]);
							stringBuilder.append(delim);
							stringBuilder.append(tokens[k]);
							stringBuilder.append(delim);
							stringBuilder.append(tokens[l]);
							result.put(stringBuilder.toString());
							counter++;
							if(counter%100000==0)
								System.out.println(counter+" level 4 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
						}
					}
				}
			}
		}
		System.out.println("load complete: "+counter+" level 4 tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" pattern writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractFourgram(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			for (int i = 0; i < tokens.length-3; i++) {
					stringBuilder.setLength(0);
					stringBuilder.append(tokens[i]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+1]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+2]);
					stringBuilder.append(delim);
					stringBuilder.append(tokens[i+3]);
					result.put(stringBuilder.toString());
					counter++;
					if(counter%100000==0)
						System.out.println(counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
			}
		}
		System.out.println("load complete: "+counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractFivegram(String normalizedSentenseFileAddress,String resultFileAddress) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			if(tokens.length>4)
				for (int i = 0; i < tokens.length-4; i++) {
						stringBuilder.setLength(0);
						stringBuilder.append(tokens[i]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[i+1]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[i+2]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[i+3]);
						stringBuilder.append(delim);
						stringBuilder.append(tokens[i+4]);
						result.put(stringBuilder.toString());
						
						counter++;
						if(counter%100000==0)
							System.out.println(counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
				}
		}
		System.out.println("load complete: "+counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

	private static void extractGram(String normalizedSentenseFileAddress,String resultFileAddress,int gramCount) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException {
		tools.util.Time.setStartTimeForNow();
		char delim=' ';
		int counter=0;
		HashSert<String> result=new HashSert<String>();
		StringBuilder stringBuilder=new StringBuilder();
		BufferedReader fileBufferReader = tools.util.file.Reader.getFileBufferReader(normalizedSentenseFileAddress);
		String sentence;
		int lineCounter=0;
		while ((sentence=fileBufferReader.readLine()) != null) {			
			lineCounter++;
			String[] tokens = sentence.split(" ");
			if(tokens.length>=gramCount)
				for (int i = 0; i < tokens.length-gramCount+1; i++) {
					stringBuilder.setLength(0);
					stringBuilder.append(tokens[i]);
					for (int j = i+1; j < i+gramCount; j++) {
						stringBuilder.append(delim);						
						stringBuilder.append(tokens[j]);
					}
						result.put(stringBuilder.toString());
						
						counter++;
						if(counter%100000==0)
							System.out.println(counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
				}
		}
		System.out.println("load complete: "+counter+" FourGram tokens fetched with "+result.size()+" pattern for "+lineCounter+" sentence in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		fileBufferReader.close();
		
		counter=0;
		tools.util.Time.setStartTimeForNow();
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(resultFileAddress,false);
		for (Entry<String, Long> entry : tools.util.sort.Collection.mapSortedByValuesDecremental(result.getHashMap())) {
			counter++;
			stringBuilder.setLength(0);
			stringBuilder.append(entry.getKey());
			stringBuilder.append("\t");
			stringBuilder.append(entry.getValue());
			printWriter.println(entry);
			if(counter%100000==0)
				System.out.println(counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
		}
		printWriter.close();
		System.out.println("write complete: "+counter+" FourGram writed to file in "+tools.util.Time.getTimeLengthForNow()+"ms.");
	}

}
