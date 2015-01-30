package nlp.pos;

import nlp.pos.validation.SentenceV2;
import tools.util.BitCodec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Tokenizer {

	BitCodec bitCodec;
	int bitCodecLimitLength;
	HashMap<String, Integer> tokenId;
	HashMap<Long, Double> posTokenizerFScore;
	double minimumValue = 0.00000000000000001;
	double threshold = 0.4;

	public Tokenizer(HashMap<String, Integer> tokenId,
			HashMap<Long, Integer> posTaggedTTF,
			HashMap<Long, Integer> languageModelTaggedTTF,
			int bitCodecLimitLength) {
		initialize(tokenId, posTaggedTTF, languageModelTaggedTTF,
				bitCodecLimitLength);
	}

	private void initialize(HashMap<String, Integer> tokenId,
			HashMap<Long, Integer> posTaggedTTF,
			HashMap<Long, Integer> languageModelTaggedTTF,
			int bitCodecLimitLength) {
		this.tokenId = tokenId;
		this.bitCodecLimitLength = bitCodecLimitLength;
		this.bitCodec = new BitCodec();
		this.posTokenizerFScore = new HashMap<Long, Double>();
		for (Long triGramId : posTaggedTTF.keySet()) {
			if (languageModelTaggedTTF.containsKey(triGramId)) {
				this.posTokenizerFScore.put(triGramId,
						(double) posTaggedTTF.get(triGramId)
								/ languageModelTaggedTTF.get(triGramId));
			} else {
				this.posTokenizerFScore.put(triGramId, minimumValue);
			}
		}
	}

	public Tokenizer(Collection<SentenceV2> sentences, int bitCodecLimitLength) {
		InformationFetcherV2 posInformationFetcherV2 = new InformationFetcherV2(
				sentences);
		initialize(posInformationFetcherV2.getTokenId(),
				posInformationFetcherV2.getPosTaggedTTF(),
				posInformationFetcherV2.getLanguageModelTaggedTTF(),
				bitCodecLimitLength);
	}

	public String tokenize2(String inputString) throws Exception {
		String result = "";
//		long uniGramCode;
		long biGramCode;
		long triGramCode;
		// double uniGramCodeFScore;
		double biGramCodeFScore;
		double triGramCodeFScore;
		String[] inTokens = inputString.split(" ");
		int i = 0;
		while (i < inTokens.length) {
			if (tokenId.containsKey(inTokens[i])) {
//				uniGramCode = this.bitCodec
//						.encode(0, 0, this.tokenId.get(inTokens[i]),
//								this.bitCodecLimitLength);
				// uniGramCodeFScore = posTokenizerFScore.get(uniGramCode);
				if (i + 1 < inTokens.length && this.tokenId.containsKey(inTokens[i + 1])) {
					biGramCode = this.bitCodec.encode(0,
							this.tokenId.get(inTokens[i]),
							this.tokenId.get(inTokens[i + 1]),
							this.bitCodecLimitLength);
					biGramCodeFScore = getPosTokenizerFScore(biGramCode);
					if(biGramCodeFScore>0)
						System.out.println("biGramCodeFScore:"+biGramCodeFScore);
					if (i + 2 < inTokens.length
							&& tokenId.containsKey(inTokens[i + 2])) {
						triGramCode = this.bitCodec.encode(
								this.tokenId.get(inTokens[i]),
								this.tokenId.get(inTokens[i + 1]),
								this.tokenId.get(inTokens[i + 2]),
								this.bitCodecLimitLength);
						triGramCodeFScore = getPosTokenizerFScore(triGramCode);
						if(triGramCodeFScore>0)
							System.out.println("triGramCodeFScore:"+triGramCodeFScore);
						if (triGramCodeFScore > threshold) {
							result += inTokens[i] + " " + inTokens[i + 1] + " "
									+ inTokens[i + 2] + "\n";
							i += 2;
						} else {
							if (biGramCodeFScore > threshold) {
								result += inTokens[i] + " " + inTokens[i + 1]
										+ "\n";
								i += 1;
							} else {
								result += inTokens[i] + "\n";
							}
						}
					} else {
						if (biGramCodeFScore > threshold) {
							result += inTokens[i] + " " + inTokens[i + 1]
									+ "\n";
							i += 1;
						} else {
							result += inTokens[i] + "\n";
						}
					}
				} else {
					result += inTokens[i] + "\n";
				}
			} else {
				result += inTokens[i] + "\n";
			}
			i++;
		}
		return result;
	}

	
	public List<String> tokenize(String inputString) throws Exception {
		List<String> result = new ArrayList<String>();
//		long uniGramCode;
		long biGramCode;
		long triGramCode;
		// double uniGramCodeFScore;
		double biGramCodeFScore;
		double triGramCodeFScore;
		String[] inTokens = inputString.split(" ");
		int i = 0;
		while (i < inTokens.length) {
			if (tokenId.containsKey(inTokens[i])) {
//				uniGramCode = this.bitCodec
//						.encode(0, 0, this.tokenId.get(inTokens[i]),
//								this.bitCodecLimitLength);
				// uniGramCodeFScore = posTokenizerFScore.get(uniGramCode);
				if (i + 1 < inTokens.length && this.tokenId.containsKey(inTokens[i + 1])) {
					biGramCode = this.bitCodec.encode(0,
							this.tokenId.get(inTokens[i]),
							this.tokenId.get(inTokens[i + 1]),
							this.bitCodecLimitLength);
					biGramCodeFScore = getPosTokenizerFScore(biGramCode);
//					if(biGramCodeFScore>0)
//						System.out.println("biGramCodeFScore:"+biGramCodeFScore);
					if (i + 2 < inTokens.length
							&& tokenId.containsKey(inTokens[i + 2])) {
						triGramCode = this.bitCodec.encode(
								this.tokenId.get(inTokens[i]),
								this.tokenId.get(inTokens[i + 1]),
								this.tokenId.get(inTokens[i + 2]),
								this.bitCodecLimitLength);
						triGramCodeFScore = getPosTokenizerFScore(triGramCode);
//						if(triGramCodeFScore>0)
//							System.out.println("triGramCodeFScore:"+triGramCodeFScore);
						if (triGramCodeFScore > threshold) {
							result.add(inTokens[i] + " " + inTokens[i + 1] + " "
									+ inTokens[i + 2]);
							i += 2;
						} else {
							if (biGramCodeFScore > threshold) {
								result.add(inTokens[i] + " " + inTokens[i + 1]);
								i += 1;
							} else {
								result.add(inTokens[i]);
							}
						}
					} else {
						if (biGramCodeFScore > threshold) {
							result.add(inTokens[i] + " " + inTokens[i + 1]);
							i += 1;
						} else {
							result.add(inTokens[i]);
						}
					}
				} else {
					result.add(inTokens[i]);
				}
			} else {
				result.add(inTokens[i]);
			}
			i++;
		}
		return result;
	}
	
	private double getPosTokenizerFScore(long triGramCode) {
		return this.posTokenizerFScore.containsKey(triGramCode) ? this.posTokenizerFScore
				.get(triGramCode) : 0;
	}
}
