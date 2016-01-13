package cedict;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cedict.model.WordEn;
import cedict.model.WordZh;
import cedict.repository.WordEnRepository;
import cedict.repository.WordZhRepository;

@Controller
@RequestMapping("/")
public class CedictController {
	private WordEnRepository wordEnRepository;
	private WordZhRepository wordZhRepository;

	@Autowired
	public CedictController(WordEnRepository wordEnRepository, WordZhRepository wordZhRepository) {
		this.wordEnRepository = wordEnRepository;
		this.wordZhRepository = wordZhRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		Page<WordZh> wordZhs = wordZhRepository.findAll(new PageRequest(0, 15));
		Page<WordEn> wordEns = wordEnRepository.findAll(new PageRequest(0, 15));
		model.put("wordZhs", wordZhs);
		model.put("wordEns", wordEns);
		return "home";
	}

	@RequestMapping("/status")
	public String getStatus(Map<String, Object> model) {
		long wordEnCount = wordEnRepository.count();
		long wordZhCount = wordZhRepository.count();
		model.put("wordEnCount", wordEnCount);
		model.put("wordZhCount", wordZhCount);
		return "status";
	}

}