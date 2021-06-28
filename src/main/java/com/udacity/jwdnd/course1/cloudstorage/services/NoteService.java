package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public int addNote(NoteForm noteform){
        return noteMapper.insert(new Notes(noteform.getNotetitle(), noteform.getNotedescription(), noteform.getUserid()));
    }

    public List<Notes> getNote(Integer userid){
        return noteMapper.getUserNotes(userid);
    }

    public void updateNote(String notetitle, String notedescription, Integer noteid, Integer userid){
        noteMapper.update(notetitle, notedescription, noteid, userid);
    }
    public void deleteNote(Integer noteid, Integer userid){
        noteMapper.delete(noteid, userid);
    }
}
