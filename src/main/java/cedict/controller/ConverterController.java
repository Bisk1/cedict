package cedict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cedict.service.PinyinConverter;

//@Controller
class ConverterController {
	private PinyinConverter pinyinConverter;

	@Autowired
	public ConverterController(PinyinConverter pinyinConverter) {
		this.pinyinConverter = pinyinConverter;
	}

	@RequestMapping(value = "convert", method = RequestMethod.GET)
	public String convertAll() {
		pinyinConverter.convertAll();
		return "redirect:/";
	}

}
