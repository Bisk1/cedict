package cedict.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cedict.model.WordZh;

public interface WordZhRepository extends JpaRepository<WordZh, Long> {
	WordZh findByTextAndPinyin(String text, String pinyin);

	List<WordZh> findByText(String text);
}
