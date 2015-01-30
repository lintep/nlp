package nlp.pos.data;

import com.google.gson.Gson;
import tools.util.file.BufferedIterator;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonCorpusHandler {

	public static void convertOneLevelPos(String jsonFileAddress,
			String resultJsonFileAddress,
			NormalizerTokenizerInterface normalizerTokenizer, String delim)
			throws IOException {

		Gson gson = new Gson();
		int i = 0;
		tools.util.file.BufferedIterator bufferedIterator = new BufferedIterator(
				jsonFileAddress);
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(
				resultJsonFileAddress, false);
		while (bufferedIterator.hasNext()) {
			String json = bufferedIterator.next();
			PosTaggedInstanceSentence posTaggedInstanceSentence = gson
					.fromJson(json, PosTaggedInstanceSentence.class);
			TokenPos[] oldTokenPosArray = posTaggedInstanceSentence
					.getTokenPosArray();
			TokenPos[] newTokenPosArray = new TokenPos[oldTokenPosArray.length];
			for (int j = 0; j < oldTokenPosArray.length; j++) {
				newTokenPosArray[j] = new TokenPos(
						normalizerTokenizer
								.normalize(oldTokenPosArray[j].token),
						oldTokenPosArray[j].pos.split(delim)[0]);
			}
			PosTaggedInstanceSentence convertedPosTaggedInstanceSentence = new PosTaggedInstanceSentence(
					posTaggedInstanceSentence.getId(), newTokenPosArray);
			printWriter
					.println(gson.toJson(convertedPosTaggedInstanceSentence));
			i++;
			if (i % 10000 == 0)
				System.out.println(i + " json converted.");
		}
		bufferedIterator.close();
		printWriter.close();
		System.out.println("convert complete with " + i + " json.");
	}

	public static void convertTwoLevelPos(String jsonFileAddress,
			String resultJsonFileAddress,
			NormalizerTokenizerInterface normalizerTokenizer, String delim)
			throws IOException {

		Gson gson = new Gson();
		int i = 0;
		tools.util.file.BufferedIterator bufferedIterator = new BufferedIterator(
				jsonFileAddress);
		PrintWriter printWriter = tools.util.file.Write.getPrintWriter(
				resultJsonFileAddress, false);
		while (bufferedIterator.hasNext()) {
			String json = bufferedIterator.next();
			PosTaggedInstanceSentence posTaggedInstanceSentence = gson
					.fromJson(json, PosTaggedInstanceSentence.class);
			TokenPos[] oldTokenPosArray = posTaggedInstanceSentence
					.getTokenPosArray();
			TokenPos[] newTokenPosArray = new TokenPos[oldTokenPosArray.length];
			for (int j = 0; j < oldTokenPosArray.length; j++) {
				String[] poses=oldTokenPosArray[j].pos.split(delim);
				newTokenPosArray[j] = new TokenPos(
						normalizerTokenizer
								.normalize(oldTokenPosArray[j].token),
								poses.length>1?poses[0]+delim+poses[0]:poses[0]);
			}
			PosTaggedInstanceSentence convertedPosTaggedInstanceSentence = new PosTaggedInstanceSentence(
					posTaggedInstanceSentence.getId(), newTokenPosArray);
			printWriter
					.println(gson.toJson(convertedPosTaggedInstanceSentence));
			i++;
			if (i % 10000 == 0)
				System.out.println(i + " json converted.");
		}
		bufferedIterator.close();
		printWriter.close();
		System.out.println("convert complete with " + i + " json.");
	}

}
