package cedict.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class PinyinConverterTest {

	private PinyinConverter service = new PinyinConverter(null);

	@Test
	public void shouldConvert1Character() {
		String converted = service.convertWordPinyin("wo3");
		assertEquals("wǒ", converted);
	}

	@Test
	public void shouldConvert2Characters() {
		String converted = service.convertWordPinyin("wo3men5");
		assertEquals("wǒmen", converted);
	}

	@Test
	public void shouldConvert3Characters() {
		String converted = service.convertWordPinyin("fei1chang2hao3");
		assertEquals("fēichánghǎo", converted);
	}

	@Test
	public void shouldConvert3Vowels() {
		String converted = service.convertWordPinyin("miao3");
		assertEquals("miǎo", converted);
	}

	@Test
	public void shouldConvertUmlaut() {
		String converted = service.convertWordPinyin("nu:3de5");
		assertEquals("nǚde", converted);
	}
	
	@Test
	public void shouldPreserveCapitalization() {
		String converted = service.convertWordPinyin("nU:3De5AI4qiNg2");
		assertEquals("nǙDeÀIqíNg", converted);
	}

	@Test
	public void shouldConvertSoftR() {
		String converted = service.convertWordPinyin("A quan2 r5");
		assertEquals("A quán r", converted);
	}

	@Test
	public void shouldConvertXx() {
		String converted = service.convertWordPinyin("xx5");
		assertEquals("xx", converted);
	}
}
