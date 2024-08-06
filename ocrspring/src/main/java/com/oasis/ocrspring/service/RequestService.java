package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.GetOneResponseDto;
import com.oasis.ocrspring.dto.RequestResponseDto;
import com.oasis.ocrspring.model.Request;
import com.oasis.ocrspring.repository.RequestRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RequestService {
    @Autowired
    private RequestRepository RequestRepo;
    public ResponseEntity AllRequestDetails(){
        try{
            List<Request> requestList = RequestRepo.findAll();
            List<RequestResponseDto> responseList = new ArrayList<RequestResponseDto>();
            for(Request element: requestList){
                responseList.add(new RequestResponseDto(element));
            }

            return ResponseEntity.status(200).body(responseList);
        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    public ResponseEntity<?> GetOneRequest(ObjectId id){
        try{
            Request request = RequestRepo.findById(id);
            GetOneResponseDto response = new GetOneResponseDto(request);
            return ResponseEntity.status(200).body(response);

            }
        catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }

            }
    }

//    public Request createRequest(Request Request){
//
//        return RequestRepo.save(Request);
//    }
//}
