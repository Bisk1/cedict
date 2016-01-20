package cedict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cedict.controller.error.RestError;
import cedict.controller.error.WordEnTextNotFoundException;
import cedict.controller.error.WordZhTextAndPinyinNotFoundException;
import cedict.controller.error.WordZhTextNotFoundException;
import cedict.model.WordEn;
import cedict.model.WordZh;
import cedict.repository.WordEnRepository;
import cedict.repository.WordZhRepository;

@RestController
@RequestMapping("/api")
public class CedictRestController {

	private WordEnRepository wordEnRepository;
	private WordZhRepository wordZhRepository;

	@Autowired
	public CedictRestController(WordEnRepository wordEnRepository, WordZhRepository wordZhRepository) {
		this.wordEnRepository = wordEnRepository;
		this.wordZhRepository = wordZhRepository;
	}

	@RequestMapping(value = "/zh/{wordZhText}", method = RequestMethod.GET, produces = { "application/json" })
	public List<WordZh> wordZhByText(@PathVariable(value = "wordZhText") String wordZhText) {
		List<WordZh> wordZhs = wordZhRepository.findByText(wordZhText);
		if (wordZhs.isEmpty()) {
			throw new WordZhTextNotFoundException(wordZhText);
		}
		return wordZhs;
	}

	@RequestMapping(value = "/zh/{wordZhText}/{pinyin}", method = RequestMethod.GET, produces = { "application/json" })
	public WordZh wordZhByTextAndPinyin(@PathVariable(value = "wordZhText") String wordZhText,
			@PathVariable(value = "pinyin") String wordZhPinyin) {
		WordZh wordZh = wordZhRepository.findByTextAndPinyin(wordZhText, wordZhPinyin);
		if (wordZh == null) {
			throw new WordZhTextAndPinyinNotFoundException(wordZhText, wordZhPinyin);
		}
		return wordZh;
	}

	@RequestMapping(value = "/en/{wordEnText}", method = RequestMethod.GET, produces = { "application/json" })
	public WordEn wordEnByText(@PathVariable(value = "wordEnText") String wordEnText) {
		WordEn wordEn = wordEnRepository.findByText(wordEnText);
		if (wordEn == null) {
			throw new WordEnTextNotFoundException(wordEnText);
		}
		return wordEn;
	}

	@ExceptionHandler(WordZhTextNotFoundException.class)
	public ResponseEntity<RestError> wordZhTextNotFoundException(WordZhTextNotFoundException e) {
		String text = e.getText();
		RestError error = new RestError(4, "Chinese word with text [" + text + "] not found");
		return new ResponseEntity<RestError>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WordZhTextAndPinyinNotFoundException.class)
	public ResponseEntity<RestError> wordZhTextAndPinyinNotFoundException(WordZhTextAndPinyinNotFoundException e) {
		String text = e.getText();
		String pinyin = e.getPinyin();
		RestError error = new RestError(4, "Chinese word with text [" + text + "] and pinyin [" + pinyin + "] not found");
		return new ResponseEntity<RestError>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WordEnTextNotFoundException.class)
	public ResponseEntity<RestError> wordEnTextNotFoundException(WordEnTextNotFoundException e) {
		String text = e.getText();
		RestError error = new RestError(4, "English word with text [" + text + "] not found");
		return new ResponseEntity<RestError>(error, HttpStatus.NOT_FOUND);
	}
}
