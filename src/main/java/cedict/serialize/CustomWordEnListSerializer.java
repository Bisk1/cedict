package cedict.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cedict.model.WordEn;

public class CustomWordEnListSerializer extends JsonSerializer<List<WordEn>> {

	@Override
	public void serialize(List<WordEn> wordEns, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		List<String> words = new ArrayList<String>();
		for (WordEn wordEn : wordEns) {
			words.add(wordEn.getText());
		}
		generator.writeObject(words);
	}

}
