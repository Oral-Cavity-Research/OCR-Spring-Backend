package com.oasis.ocrspring.repository.draftRepos;

import com.oasis.ocrspring.model.draftModels.DraftEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DraftEntryRepository extends MongoRepository<DraftEntry,String> {
}
