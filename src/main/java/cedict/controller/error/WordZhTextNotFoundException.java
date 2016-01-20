package cedict.controller.error;

public class WordZhTextNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6154151501844712371L;
	
	private String text;
	
	public WordZhTextNotFoundException(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
