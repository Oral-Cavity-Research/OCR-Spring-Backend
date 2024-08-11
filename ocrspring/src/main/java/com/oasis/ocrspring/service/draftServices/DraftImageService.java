package com.oasis.ocrspring.service.draftServices;

import com.oasis.ocrspring.model.draftModels.DraftImage;
import com.oasis.ocrspring.repository.draftRepos.DraftimageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftImageService {
    @Autowired
    private DraftimageRepository draftImageRepo;

    public List<DraftImage> allDraftImageDetails() {
        System.out.println("appeared in service layer");
        return draftImageRepo.findAll();
    }
}
