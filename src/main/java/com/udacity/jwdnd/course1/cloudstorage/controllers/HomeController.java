package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
        this.userService=userService;

    }

    @GetMapping
    public String getHomePage(NoteForm noteForm, CredentialForm credentialForm,  Model model,  Authentication authentication){
        Users user=userService.getUser(authentication.getName());
        model.addAttribute("notes", noteService.getNote(user.getuserid()));
        model.addAttribute("files", fileService.getFile(user.getuserid()));
        model.addAttribute("credentials", credentialService.getCredential(user.getuserid()));
        return "home";
    }

    @PostMapping("/logout")
    public String getLoginOut(){
        return "redirect:/login";
    }




}
