package cedict.controller.error;

public class RestError {
	private int code;
	private String message;

	public RestError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
