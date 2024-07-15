package com.oasis.ocrspring.controller;

import com.oasis.ocrspring.repository.HospitalRepository;
import com.oasis.ocrspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/self")
public class user {
    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private HospitalRepository hospitalRepo;
    @ApiIgnore
    public void redirrect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swaggr-ui.html");
    }
    @GetMapping("/")
    public String getUser(String _id){
        return "/api/user/self/"+_id;
    }
    @GetMapping("/hospitals")
    public String getHospitalList(){
        return "/api/user/self/hospitals";
    }
    @PostMapping("/update")
    public String updateUserDetail(){
        return "/api/user/self/update";
    }
}
