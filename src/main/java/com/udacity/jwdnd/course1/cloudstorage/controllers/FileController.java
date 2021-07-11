package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private UserService userService;
    private String errorMessage;
    private String successMessage;


    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model){
           Users user=userService.getUser(authentication.getName());
           if(multipartFile.isEmpty()){
               errorMessage="No file was uploaded";
               model.addAttribute("errorMessage", errorMessage);
               errorMessage=null;
               return "result";
           }
           Integer userid=user.getuserid();

           if(fileService.getFileDuplicates(multipartFile.getOriginalFilename(),userid)){
               errorMessage="Duplicate files not allowed";
               model.addAttribute("errorMessage", errorMessage);
               errorMessage=null;
               return "result";
           }

           try{
               fileService.uploadFile(multipartFile, userid);
           }
           catch (Exception e){
               e.printStackTrace();
               errorMessage=e.getMessage();
           }

           if(errorMessage!=null){
               model.addAttribute("errorMessage", errorMessage);
               return "result";
           }

         model.addAttribute("successMessage", "You have successfully uploaded your file");
         return "result";
    }

    @GetMapping("/deleteFiles/{fileid}")
    public String deleteFiles(@PathVariable Integer fileid, Model model, Authentication authentication){
        Users user=userService.getUser(authentication.getName());
        fileService.deleteFile(fileid, user.getuserid());
        model.addAttribute("successMessage", "You have successfully deleted the file");
        return "result";
    }

    @GetMapping("/downloadFile/{fileid}")
    public ResponseEntity downloadFile(@PathVariable Integer fileid, Authentication auth,
                                       Model model) throws IOException {
        List<Files> file = fileService.getFile(fileid);
        String contentType = file.get(0).getContenttype();
        String fileName = file.get(0).getFilename();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.get(0).getFiledata());
    }


    public ResponseEntity<Object> downloadFile(@PathVariable("fileId") Integer fileId, Model model) {
        List<Files> usrFile = this.fileService.getFile(fileId);
        byte[] data = usrFile.get(0).getFiledata();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        InputStreamResource resource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", usrFile.get(0).getFilename()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(data.length).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }
}
