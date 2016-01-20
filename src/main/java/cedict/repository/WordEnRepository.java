package cedict.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cedict.model.WordEn;

public interface WordEnRepository extends JpaRepository<WordEn, Long> {
	WordEn findByText(String text);
}
