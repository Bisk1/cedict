package cedict.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cedict.model.WordZh;

public class CustomWordZhListSerializer extends JsonSerializer<List<WordZh>> {

	@Override
	public void serialize(List<WordZh> wordZhs, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		List<List<String>> wordAndPinyinPairs = new ArrayList<List<String>>();
		for (WordZh wordZh : wordZhs) {
			List<String> wordAndPinyinPair = new ArrayList<String>();
			wordAndPinyinPair.add(wordZh.getText());
			wordAndPinyinPair.add(wordZh.getPinyin());
			wordAndPinyinPairs.add(wordAndPinyinPair);
		}
		generator.writeObject(wordAndPinyinPairs);
	}

}
