package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.errorResponseDto;
import com.oasis.ocrspring.dto.messageDto;
import com.oasis.ocrspring.dto.patientTeleconRequest;
import com.oasis.ocrspring.dto.patientTeleconResponse;
import com.oasis.ocrspring.model.Patient;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.model.User;
import com.oasis.ocrspring.repository.TeleconEntriesRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class TeleconEntriesService {
    @Autowired
    private TeleconEntriesRepository TeleconEntriesRepo;
    @Autowired
    private PatientService patientService;

    public List<TeleconEntry> AllTeleconEntriesDetails(){

        return TeleconEntriesRepo.findAll();
    }
    public TeleconEntry findByID(String id){
        return TeleconEntriesRepo.findById(id).orElse(null);
    }
    public TeleconEntry findOne(String patient_id, String clinician_id){
        TeleconEntry patient =  TeleconEntriesRepo.findByPatientAndClinicianId(patient_id,clinician_id).orElse(null);
        return patient;
    }
    public void save(TeleconEntry teleconEntry){
        TeleconEntriesRepo.save(teleconEntry);
    }
    public ResponseEntity<?> patientTeleconEntry(String patient_id,
                                                 String clinician_id,
                                                 patientTeleconRequest newPatient ){ //path patient id

        try{
            Patient patient = patientService.findPatient(patient_id,clinician_id);//patient_id, String clinician_id
            if(patient != null){
                TeleconEntry newEntry = new TeleconEntry();
                newEntry.setPatient(patient.getId());
                newEntry.setClinicianId(patient.getclinicianId());
                newEntry.setStart_time(LocalDateTime.parse(newPatient.getStart_time(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                newEntry.setEnd_time(LocalDateTime.parse(newPatient.getEnd_time(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                newEntry.setComplaint(newPatient.getComplaint());
                newEntry.setFindings(newPatient.getFindings());
                newEntry.setCurrent_habits(newPatient.getCurrent_habits());
                newEntry.setReviewers(new ArrayList<>());
                newEntry.setReviews(new ArrayList<>());
                newEntry.setImages(new ArrayList<>());
                newEntry.setReports(new ArrayList<>());
                newEntry.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                newEntry.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

                try {
                    TeleconEntriesRepo.save(newEntry);
                    return ResponseEntity.status(200).body(new patientTeleconResponse(newEntry));
                }
                catch(Exception ex){
                    return ResponseEntity.status(500).body(new messageDto("Tele consultation entry failed"));
                }
            }
            else{
                return ResponseEntity.status(404).body(new messageDto("Patient is not registered" ));
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(new errorResponseDto("Internal Server Error!",e.toString()));
        }

    }
}

