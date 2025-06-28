package org.example.utils;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.DirectoryMapper;

public class DirectoryUtils {
    public static Integer getDirectoryIdByName(String directoryName) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            DirectoryMapper directoryMapper = session.getMapper(DirectoryMapper.class);
            return directoryMapper.getDirectoryIdByName(directoryName);
        }
    }
}
