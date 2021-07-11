package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;
    private String errorMessage;
    private String successMessage;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService=encryptionService;
        this.userService = userService;
    }


    @PostMapping
    public String createCredential(CredentialForm credentialForm, @RequestParam Integer credentialid, Model model, Authentication authentication){
        Users user=this.userService.getUser(authentication.getName());
        credentialForm.setUserid(user.getuserid());
        if(credentialForm.getUrl()=="" || credentialForm.getPassword()==""||credentialForm.getUsername()==""){
            errorMessage="All fields must be filled";
        }

        if(credentialid==null){
            this.credentialService.addCredential(credentialForm);
            successMessage="You have successfully added your new credentials";
        }
        else{
            this.credentialService.updateCredential(credentialForm);
            successMessage="You have successfully updated your credentials";
        }


        if(this.errorMessage!=null){
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }
        model.addAttribute("successMessage", successMessage);
        return "result";

    }

    @GetMapping("/deleteCred/{credentialid}")
    public String deleteCred(@PathVariable Integer credentialid, CredentialForm credentialForm, Model model, Authentication authentication){
        Users user=userService.getUser(authentication.getName());
        credentialService.deleteCredential(credentialid, user.getuserid());
        successMessage="You have successfully deleted your credential";
        model.addAttribute("successMessage", successMessage);
        return "result";
    }

}
