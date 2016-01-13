package cedict;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public List<WordZh> wordZhTextMatches(@PathVariable(value = "wordZhText") String wordZhText) {
		return wordZhRepository.findByText(wordZhText);
	}

	@RequestMapping(value = "/zh/{wordZhText}/{pinyin}", method = RequestMethod.GET, produces = { "application/json" })
	public WordZh wordZhTextAndPinyinMatch(@PathVariable(value = "wordZhText") String wordZhText,
			@PathVariable(value = "pinyin") String wordZhPinyin) {
		Optional<WordZh> wordZh = wordZhRepository.findByTextAndPinyin(wordZhText, wordZhPinyin);
		if (wordZh.isPresent()) {
			return wordZh.get();
		}
		return null;
	}

	@RequestMapping(value = "/en/{wordEnText}", method = RequestMethod.GET, produces = { "application/json" })
	public WordEn wordEnTextMatch(@PathVariable(value = "wordEnText") String wordEnText) {
		Optional<WordEn> wordEn = wordEnRepository.findByText(wordEnText);
		if (wordEn.isPresent()) {
			return wordEn.get();
		}
		return null;
	}
}
