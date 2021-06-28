package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getUserNotes(Integer userid);


    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription=#{notedescription} WHERE noteid = #{noteid} AND userid = #{userid}")
    boolean update(String notetitle, String notedescription, Integer noteid, Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    boolean delete(Integer noteid, Integer userid);

}
