package org.example.filter;

import org.apache.ibatis.session.SqlSession;
import org.example.mapper.FilterMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;

public class Filter {

    public static String filter(String input) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            FilterMapper filterMapper = session.getMapper(FilterMapper.class);
            List<String> filter = filterMapper.getAllFilters();
            // if input contains the filter word, make it as ***
            for (String word : filter) {
                if (input.contains(word)) {
                    input = input.replaceAll(word, "***");
                }
            }
            return input;
        }
    }
}
