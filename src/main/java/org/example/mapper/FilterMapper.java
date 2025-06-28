package org.example.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilterMapper {
    @Select("select * from filter")
    List<String> getAllFilters();
}
