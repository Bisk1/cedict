package cedict.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cedict.model.WordEn;
import cedict.model.WordZh;
import cedict.repository.WordEnRepository;
import cedict.repository.WordZhRepository;

@Service
public class TranslationService {

	private WordEnRepository wordEnRepository;
	private WordZhRepository wordZhRepository;

	@Autowired
	public TranslationService(WordEnRepository wordEnRepository, WordZhRepository wordZhRepository) {
		this.wordEnRepository = wordEnRepository;
		this.wordZhRepository = wordZhRepository;
	}

	public void add(String wordZhText, String wordZhPinyin, String[] wordEnTexts) {
		if (wordZhText.length() > 255 || wordZhPinyin.length() > 255) {
			return;
		}
		WordZh wordZh = findOrCreateWordZh(wordZhText, wordZhPinyin);
		for (String wordEnText : wordEnTexts) {
			if (wordEnText.length() > 255) {
				return;
			}
			WordEn wordEn = findOrCreateWordEn(wordEnText);
			wordZh.getTranslations().add(wordEn);
		}
		wordZhRepository.save(wordZh);
	}

	private WordEn findOrCreateWordEn(String text) {
		Optional<WordEn> wordEnFromDb = wordEnRepository.findByText(text);
		if (wordEnFromDb.isPresent()) {
			return wordEnFromDb.get();
		} else {
			WordEn wordEn = new WordEn();
			wordEn.setText(text);
			wordEn.setTranslations(new ArrayList<>());
			return wordEnRepository.save(wordEn);
		}
	}

	private WordZh findOrCreateWordZh(String text, String pinyin) {
		Optional<WordZh> wordZhFromDb = wordZhRepository.findByTextAndPinyin(text, pinyin);
		if (wordZhFromDb.isPresent()) {
			return wordZhFromDb.get();
		} else {
			WordZh wordZh = new WordZh();
			wordZh.setText(text);
			wordZh.setPinyin(pinyin);
			wordZh.setTranslations(new ArrayList<>());
			return wordZhRepository.save(wordZh);
		}
	}
}
