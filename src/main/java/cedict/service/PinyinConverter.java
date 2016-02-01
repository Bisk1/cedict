package cedict.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cedict.model.WordZh;
import cedict.repository.WordZhRepository;

@Service
public class PinyinConverter {
	private static Logger log = Logger.getLogger(PinyinConverter.class.getName());

	private WordZhRepository wordZhRepository;

	private Pattern NUMERIC_CHARACTER_PINYIN_PATTERN = Pattern.compile("[A-ZÜa-zü]+[1-5]");

	private String diacriticsKeys = "aeiouüAEIOUÜ";
	private Map<Integer, String> toneToDiacriticMapping = new HashMap<>();

	@Autowired
	public PinyinConverter(WordZhRepository wordZhRepository) {
		this.wordZhRepository = wordZhRepository;
		toneToDiacriticMapping.put(1, "āēīōūǖĀĒĪŌŪǕ");
		toneToDiacriticMapping.put(2, "áéíóúǘÁÉÍÓÚǗ");
		toneToDiacriticMapping.put(3, "ǎěǐǒǔǚǍĚǏǑǓǙ");
		toneToDiacriticMapping.put(4, "àèìòùǜÀÈÌÒÙǛ");
		toneToDiacriticMapping.put(5, "aeiouüAEIOUÜ");
	}

	public void convertAll() {
		int counter = 0;
		for (WordZh wordZh : wordZhRepository.findAll()) {
			String oldPinyin = wordZh.getPinyin();
			String diacriticPinyin = convertWordPinyin(oldPinyin);
			wordZh.setPinyin(diacriticPinyin);
			wordZhRepository.save(wordZh);
			counter++;
			System.out.println(counter);
			
		}
	}

	public String convertWordPinyin(String numericPinyin) {
		StringBuffer diactricPinyin = new StringBuffer();
		numericPinyin = numericPinyin.replaceAll("u:", "ü");
		numericPinyin = numericPinyin.replaceAll("U:", "Ü");
		Matcher matcher = NUMERIC_CHARACTER_PINYIN_PATTERN.matcher(numericPinyin);
		while (matcher.find()) {
			String characterNumericPinyin = matcher.group(0);
			String characterDiacriticPinyin = null;
			try {
				characterDiacriticPinyin = convertCharacterPinyin(characterNumericPinyin);
			} catch (RuntimeException e) {
				log.error("Could not convert pinyin: " + characterNumericPinyin, e);
				characterDiacriticPinyin = characterNumericPinyin;
			}
			matcher.appendReplacement(diactricPinyin, characterDiacriticPinyin);
		}
		return matcher.appendTail(diactricPinyin).toString();
	}

	private String convertCharacterPinyin(String numericPinyin) {
		String tonelessPinyin = numericPinyin.substring(0, numericPinyin.length() - 1);
		int tone = numericPinyin.charAt(numericPinyin.length() - 1) - '0';
		int markerPosition = findMarkerPosition(tonelessPinyin);
		char characterToMark = tonelessPinyin.charAt(markerPosition);
		int diacriticNumber = diacriticsKeys.indexOf(characterToMark);
		Character diacritic = null;
		try {
			diacritic = toneToDiacriticMapping.get(tone).charAt(diacriticNumber);
		} catch (RuntimeException e) {
			diacritic = characterToMark;
		}
		String diacriticPinyin = tonelessPinyin.substring(0, markerPosition) + diacritic
				+ tonelessPinyin.substring(markerPosition + 1, tonelessPinyin.length());
		return diacriticPinyin;
	}

	private int findMarkerPosition(String pinyin) {
		pinyin = pinyin.toLowerCase();
		if (pinyin.contains("a")) {
			return pinyin.indexOf('a');
		}
		if (pinyin.contains("e")) {
			return pinyin.indexOf('e');
		}
		if (pinyin.contains("ou")) {
			return pinyin.indexOf("ou");
		}
		for (int i = pinyin.length() - 1; i >= 0; i--) {
			char chr = pinyin.charAt(i);
			if (chr == 'o' || chr == 'u' || chr == 'i' || chr == 'y' || chr == 'ü') {
				return i;
			}
		}
		// only exceptional cases make it here
		if (pinyin.equals("r") || pinyin.equals("xx")) {
			return 0;
		}
		throw new RuntimeException("Could not determine marker position for pinyin: '" + pinyin + "'");

	}

}
