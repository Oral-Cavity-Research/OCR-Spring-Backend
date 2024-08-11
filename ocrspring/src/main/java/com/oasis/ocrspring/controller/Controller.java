package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.model.*;
import com.oasis.ocrspring.model.draftModels.DraftEntry;
import com.oasis.ocrspring.model.draftModels.DraftImage;
import com.oasis.ocrspring.model.draftModels.DraftReport;
import com.oasis.ocrspring.service.*;
import com.oasis.ocrspring.service.draftServices.DraftEntryService;
import com.oasis.ocrspring.service.draftServices.DraftImageService;
import com.oasis.ocrspring.service.draftServices.DraftReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private UserService userservice;
    //    @Autowired
//    private PatientService patientService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RequestService requestService;

    @Autowired
    private TeleconEntriesService teleconEntriesService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RefreshtokenService refreshTokenService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private DraftEntryService draftEntryService;
    @Autowired
    private DraftImageService draftImageService;
    @Autowired
    private DraftReportService draftReportService;


    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/allUserDetails")
    public List<User> getAllUserDetails() {


        return userservice.allUserDetails();

    }

    //    @GetMapping("/allPatientDetails")
//    public List<Patient> allPatientDetails(){
//
//        System.out.println("Get all user details");
//        return patientService.allPatientDetails();
//    }
    @GetMapping("/allRoleDetails")
    public List<Role> AllRoleDetails() {

        System.out.println("Get all user details");
        return roleService.allRoleDetails();
    }

    @GetMapping("/allRequestDetails")
    public List<Request> AllRequestDetails() {

        return requestService.AllRequestDetails();
    }

    @GetMapping("/allTeleconEntryDetails")
    public List<TeleconEntry> AllTeleconEntryDetails() {

        return teleconEntriesService.allTeleconEntriesDetails();
    }

    @GetMapping("/allReviewDetails")
    public List<Review> AllReviewDetails() {

        return reviewService.allReviewDetails();
    }

    @GetMapping("/allReportDetails")
    public List<Report> AllReportDetails() {

        return reportService.allReportDetails();
    }

    @GetMapping("/allRefreshTokenDetails")
    public List<RefreshToken> AllRefreshTokenDetails() {

        return refreshTokenService.allRefreshtokenDetails();
    }

    @GetMapping("/allOptionDetails")
    public List<Option> AllOptionDetails() {

        return optionService.allOptionDetails();
    }

    //    @GetMapping("/allImageDetails")
//    public List<Image> allImageDetails() {
//
//        return imageService.allImageDetails();
//    }
    @GetMapping("/allHospitalDetails")
    public List<Hospital> AllHospitalDetails() {

        return hospitalService.allHospitalDetails();
    }

    @GetMapping("/allAssignmentDetails")
    public List<Assignment> AllAssignmentDetails() {

        return assignmentService.allAssignmentDetails();
    }

    @GetMapping("/allDraftEntryDetails")
    public List<DraftEntry> AllDraftEntryDetails() {

        return draftEntryService.allDraftEntryDetails();
    }

    @GetMapping("/allDraftImageDetails")
    public List<DraftImage> AllDraftImageDetails() {

        return draftImageService.allDraftImageDetails();
    }

    @GetMapping("/allDraftReportDetails")
    public List<DraftReport> AllDraftReportDetails() {

        return draftReportService.allDraftReportDetails();
    }

    @PostMapping("/postUserDetails")
    public User postUserDetails(@RequestBody User user) {
        return userservice.createUser(user);
    }
//    @PostMapping("/signup")
//    public ResponseEntity<String> createUser(@RequestBody User user){
//
//        if(service.getuserDetailsByEmail(user.getEmail())!=null){
//            return new ResponseEntity<>("user Already Exists", HttpStatus.CONFLICT);
//        }else{
//            service.createUser(user);
//            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
//        }
}
