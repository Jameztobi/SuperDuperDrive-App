package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    private String errorMessage= null;
    private String successMessage= null;


    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping
    public String CreateNote(NoteForm noteForm,  Authentication authentication, Model model, RedirectAttributes redirectAttributes){
        Users user=userService.getUser(authentication.getName());
        Integer userid=user.getuserid();
        noteForm.setUserid(userid);
        if (noteForm.getNoteid()==null){
            noteService.addNote(noteForm);
            successMessage="You have successfully added a note";
        }
        else{
            noteService.updateNote(noteForm.getNotetitle(), noteForm.getNotedescription(), noteForm.getNoteid(), userid);
            successMessage="Your note changes have been successfully updated";
        }

        if(errorMessage!=null){
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }
        model.addAttribute("successMessage", successMessage);
        return "result";

    }

    @GetMapping("/deleteNotes/{noteid}")
    public String deleteNotes(@PathVariable Integer noteid, NoteForm noteForm, Model model, Authentication authentication){
        Users user=userService.getUser(authentication.getName());
        noteService.deleteNote(noteid, user.getuserid());

        successMessage="Your note has been deleted successfully";
        model.addAttribute("successMessage", successMessage);
        return "result";
    }

    
}
