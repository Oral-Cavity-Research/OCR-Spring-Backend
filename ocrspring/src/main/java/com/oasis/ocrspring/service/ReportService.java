package com.oasis.ocrspring.service;

import com.oasis.ocrspring.dto.ReportsRequestDto;
import com.oasis.ocrspring.dto.UploadReportResponse;
import com.oasis.ocrspring.model.Report;
import com.oasis.ocrspring.model.TeleconEntry;
import com.oasis.ocrspring.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepo;
    @Autowired
    private TeleconEntriesService teleconServ;
    @Value("src/main/Storage/Reports")
    private String reportUploadDir;

    public List<Report> AllReportDetails(){
        System.out.println("appeared in service layer");
        return reportRepo.findAll();
    }
    public ResponseEntity<UploadReportResponse> uploadReports(ReportsRequestDto data,
                                                              String id,
                                                              List<MultipartFile> files){
        List<Report> uploadedReports = new ArrayList<>();//Report model list
        List<String> uploadFiles = new ArrayList<>();//List of file uri's
        List<String> ReportIds = new ArrayList<>();

        TeleconEntry teleconEntry = teleconServ.findByID(id);
        if (teleconEntry != null ){ //auth part should be added
            try{
                for(MultipartFile file: files) {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    try{
                        Path path = Paths.get(reportUploadDir + File.separator+ fileName);
                        if(!Files.exists(path)){//if the path doesn't exist create em
                            Files.createDirectories(path);
                        }
                        Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                        //useful for creating uri to check the report
                        String fileDownUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/files")
                                .path(fileName)
                                .toUriString();
                        uploadFiles.add(fileDownUri);

                        // creating a report instance and saving it on the database
                        Report report = new Report();
                        report.setTelecon_id(data.getTeleconId());
                        report.setReport_name(data.getReportName());
                        report.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        report.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        reportRepo.save(report);
                        uploadedReports.add(report); //Report model list
                        ReportIds.add(report.getId());

                    }
                    catch(Exception ex){
                        return ResponseEntity.status(500).body(new UploadReportResponse(null,"Internal Server Error"));
                    }

            }
                //to make sure not to overwritten on the existing IDs
                List<String> reportIds = uploadedReports.stream().map(Report :: getId).toList();
                List<String> existedReportIds = teleconEntry.getReports();
                if(existedReportIds.isEmpty()){
                    existedReportIds = new ArrayList<>();
                }
                //reportIds.addAll(existedReportIds);
                existedReportIds.addAll(reportIds);
                teleconEntry.setReports(existedReportIds);
                teleconEntry.setUpdatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
                teleconServ.save(teleconEntry);

                return ResponseEntity.status(200).body(new UploadReportResponse(uploadedReports, "Reports Uploaded Successfully"));
            }catch (Exception e){
                //throw new RuntimeException("Failed to store the report");
                return ResponseEntity.status(500).body(new UploadReportResponse(null
                        ,"Internal Server Error"));
        }

        }else{
                return ResponseEntity.status(404).body(new UploadReportResponse(null
                        ,"Entry Not Found"));
            }
    }
    public String getAuthUser(String id){
        return "642849d55a89306e01036070";
    }
//    public ResponseEntity<?> reportFileUpload(List<MultipartFile> files){
//
//    }
}
