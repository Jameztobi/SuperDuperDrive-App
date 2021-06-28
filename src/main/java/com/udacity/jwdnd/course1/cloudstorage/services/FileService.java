package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private Integer userid;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void uploadFile(MultipartFile multipartFile, Integer userid) throws IOException {
        Files file = new Files();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setFilesize(multipartFile.getSize());
        file.setContenttype(multipartFile.getContentType());
        file.setFiledata(multipartFile.getBytes());
        file.setUserid(userid);

        try{
            fileMapper.insert(file);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

//    public boolean isFileNameAvailable(String filename, Integer userid) {
//        return fileMapper.checkFile(filename, userid) == null;
//    }

    public List<Files> getFile(Integer userid){
        return fileMapper.getUserFile(userid);
    }

    public void deleteFile(Integer userid, Integer fileid){
        fileMapper.delete(userid, fileid);
    }

}
