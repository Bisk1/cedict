package cedict.controller.error;

public class WordEnTextNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6154151501844712371L;
	
	private String text;
	
	public WordEnTextNotFoundException(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
