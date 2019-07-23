package com.sse.tangpoetry.service.impl;

import com.sse.tangpoetry.bean.Poetry;
import com.sse.tangpoetry.mapper.PoetryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private PoetryMapper poetryMapper;

//    public List<Poetry> queryByTitle(String title) {
////        List<Poetry> list = poetryMapper.getAllPoetry(title);
////        return list;
//    }

}
