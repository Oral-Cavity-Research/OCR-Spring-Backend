package com.oasis.ocrspring.service.draftServices;

import com.oasis.ocrspring.model.draftModels.DraftEntry;
import com.oasis.ocrspring.repository.draftRepos.DraftEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftEntryService {
    @Autowired
    private DraftEntryRepository draftEntryRepo;

    public List<DraftEntry> allDraftEntryDetails() {
        return draftEntryRepo.findAll();
    }
}
