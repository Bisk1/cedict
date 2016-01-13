package cedict.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cedict.model.WordEn;

public interface WordEnRepository extends JpaRepository<WordEn, Long> {
	Optional<WordEn> findByText(String text);
}
