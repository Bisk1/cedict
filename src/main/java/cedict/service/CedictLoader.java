package cedict.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CedictLoader {
	private static Logger log = Logger.getLogger(CedictLoader.class.getName());
	private static Pattern linePattern = Pattern.compile("(\\S+) (\\S+) \\[(.*?)\\] (.*)");

	private boolean processed = false;

	private TranslationService translationService;

	@Autowired
	public CedictLoader(TranslationService translationService) {
		this.translationService = translationService;
	}

	public synchronized void run() {
		if (processed) {
			return;
		}
		try {
			String line;
			int i = 0;
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("./classes/cedict_ts.u8"), "UTF8"))) {
				// new InputStreamReader(new
				// FileInputStream("./src/main/resources/cedict_ts.u8"),
				// "UTF8"))) {
				while ((line = br.readLine()) != null) {
					processLine(line);
					log.info(Integer.toString(i++));
				}
				processed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void processLine(String line) {
		Matcher m = linePattern.matcher(line);
		if (m.find()) {
			if (m.groupCount() != 4) {
				throw new RuntimeException("Unmatching line: " + line);
			}
			// String traditional = m.group(1); I don't need traditional
			// characters
			String simplified = m.group(2);
			String pinyin = m.group(3);
			String[] translations = m.group(4).replaceFirst("^/", "").split("\\/");
			translationService.add(simplified, pinyin, translations);

		}
	}

}
