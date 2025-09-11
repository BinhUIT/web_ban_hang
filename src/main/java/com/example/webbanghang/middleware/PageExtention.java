package com.example.webbanghang.middleware;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageExtention {
    public static <T> Page<T> paginateList(List<T> list, Pageable pageable) {
        int total = list.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<T> subList = list.subList(start, end);

        return new PageImpl<>(subList, pageable, total);
    }
}
