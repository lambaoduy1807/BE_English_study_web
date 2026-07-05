package com.english_study.repository;

import com.english_study.model.entity.Word;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

@Repository
public interface WordRepository extends MongoRepository<Word, String> {
    @Query("{ 'vocabID' : ?0 }")
    List<Word> findByVocabID(String vocabID);

    @Query("{ 'vocabID' : ?0, 'word' : ?1 }")
    Word findByVocabIDAndWord(String vocabID, String word);

    void deleteByVocabID(String vocabID);
}
