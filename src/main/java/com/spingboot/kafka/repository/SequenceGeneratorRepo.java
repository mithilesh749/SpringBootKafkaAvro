package com.spingboot.kafka.repository;

import com.spingboot.kafka.models.DatabaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
public interface SequenceGeneratorRepo extends MongoRepository<DatabaseSequence, String> {

    default long generateSequence(String seqName) {
        DatabaseSequence counter = findById(seqName).orElse(new DatabaseSequence());
        if(StringUtils.isEmpty(counter.getId())) {
            counter.setSeq(1);
            counter.setId(seqName);
        } else {
            counter.setSeq(counter.getSeq()+1);
        }
        save(counter);
        return counter.getSeq();
    }
}
