package nlp.pos.validation;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import tools.util.collection.KeyValue;

public class SentenceV2 {

	final String tokenPosSplitter ="\n";
	JSONObject jsonObject;
	int id;
	ArrayList<KeyValue<String, String>> tokenPos;
	
	public int getTokenCount(){
		return this.tokenPos.size();
	}
	
	public String getPos(int index){
		return this.tokenPos.get(index).getValue();
	}
	
	public String getToken(int index){
		return this.tokenPos.get(index).getKey();
	}
	
	public SentenceV2(JSONObject jsonObject) {
		this.jsonObject=jsonObject;
		this.id=(Integer)this.jsonObject.get("id");
		JSONArray jsonArray=(JSONArray)this.jsonObject.get("tokens");
		this.tokenPos=new ArrayList<KeyValue<String,String>>(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			String[] split = jsonArray.get(i).toString().split(tokenPosSplitter);
			tokenPos.add(new KeyValue<String, String>(split[0], split[1]));
		}
		evalCurrentEnd = this.tokenPos.get(currentIndex).getKey().trim().split(" ").length;
	}
	
	public String getSentenceString() {
		String str="";
		for (KeyValue<String, String> keyValue : tokenPos) {
			str += keyValue.getKey()+" ";
		}
		return str.trim();
	}
	
	
	int evalCurrentStart=0;
	int evalCurrentEnd=0;
	
	int currentIndex=0;
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public int getEvalCurrentStart() {
		return evalCurrentStart;
	}
	
	public int getEvalNextStart() {
		return evalCurrentEnd+1;
	}
	
	public int getEvalCurrentEnd() {
		return evalCurrentEnd;
	}
		
//	int evalNextStart=0;
//	int evalNextEnd=0;
//	
	public void setNextToken(){
		currentIndex++;
		evalCurrentStart+=evalCurrentEnd+1;
		evalCurrentEnd += this.tokenPos.get(currentIndex).getKey().trim().split(" ").length;
	}
	
	public void evaluate(SentenceV2 testedSentence, Integer trueTokenOneLength, Integer trueTokenMorThenOneLength, Integer falseTokenOneLength, Integer falseTokenMorThenOneLength) {
		for (int i = 0; i < getTokenCount(); i++) {
			if( (this.evalCurrentStart ==testedSentence.getEvalCurrentStart()) && (this.evalCurrentEnd==testedSentence.getEvalCurrentEnd()) ){
				if( (this.evalCurrentEnd-this.evalCurrentStart)==1){
					trueTokenOneLength++;
				}
				else{
					trueTokenMorThenOneLength++;					
				}
				setNextToken();
				testedSentence.setNextToken();
			}
			else{
				if( (this.evalCurrentEnd-this.evalCurrentStart)==1){
					falseTokenOneLength++;
				}
				else{
					falseTokenMorThenOneLength++;
				}
				
				while (testedSentence.getEvalNextStart()<=this.evalCurrentEnd) {
					testedSentence.setNextToken();
				}
				
				setNextToken();
			}
		}
	}
	
}
