package cedict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cedict.service.CedictLoader;

//@Controller uncomment to expose method to load new dictionary
class LoaderController {
	private CedictLoader cedictLoader;

	@Autowired
	public LoaderController(CedictLoader cedictLoader) {
		this.cedictLoader = cedictLoader;
	}

	@RequestMapping(value = "load", method = RequestMethod.GET)
	public String runLoader() {
		cedictLoader.run();
		return "redirect:/";
	}

}
