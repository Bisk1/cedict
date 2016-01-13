package cedict.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cedict.serialize.CustomWordZhListSerializer;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WordEn {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	private String text;
	@ManyToMany(mappedBy = "translations")
	@JsonSerialize(using = CustomWordZhListSerializer.class)
	private List<WordZh> translations;

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

	public List<WordZh> getTranslations() {
		return translations;
	}

	public void setTranslations(List<WordZh> translations) {
		this.translations = translations;
	}
}
