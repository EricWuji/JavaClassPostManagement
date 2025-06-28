package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DirectoryMapper {
    @Select("select directory_id from directory where directory_name = #{directoryName}")
    Integer getDirectoryIdByName(@Param("directoryName") String directoryName);
}
