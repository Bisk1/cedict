package cedict.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cedict.serialize.CustomWordEnListSerializer;

@Entity
public class WordZh {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	private String text;
	private String pinyin;
	@ManyToMany
	@JoinTable(name = "wordzh_worden", joinColumns = { @JoinColumn(name = "wordzh") }, inverseJoinColumns = {
			@JoinColumn(name = "worden") })
	@JsonSerialize(using = CustomWordEnListSerializer.class)
	private List<WordEn> translations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<WordEn> getTranslations() {
		return translations;
	}

	public void setTranslations(List<WordEn> translations) {
		this.translations = translations;
	}
}
