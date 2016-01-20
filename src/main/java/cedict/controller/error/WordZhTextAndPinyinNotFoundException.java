package cedict.controller.error;

public class WordZhTextAndPinyinNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6154151501844712371L;
	
	private String text;
	private String pinyin;
	
	public WordZhTextAndPinyinNotFoundException(String text, String pinyin) {
		this.text = text;
		this.pinyin = pinyin;
	}
	
	public String getText() {
		return text;
	}
	
	public String getPinyin() {
		return pinyin;
	}
}
