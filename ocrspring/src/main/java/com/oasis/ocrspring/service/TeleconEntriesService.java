package com.oasis.ocrspring.service;

import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeleconEntriesService {
    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;

    public List<TeleconEntry> AllTeleconEntriesDetails(){

        return TeleconEntriesRepo.findAll();
    }
}
