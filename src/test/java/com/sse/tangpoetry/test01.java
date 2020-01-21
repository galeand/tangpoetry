package com.sse.tangpoetry;

import com.sse.tangpoetry.bean.Poetry;
import com.sse.tangpoetry.service.PoetryService;
import com.sse.tangpoetry.service.impl.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Function;

@SpringBootTest
@RunWith(SpringRunner.class)
public class test01 {
    @Autowired
    private PoetryService poetryService;
    @Autowired
    private TestService testService;

    @Test
    public void test() {
        List<Object> list = poetryService.getPoetryByContent("扬州");
        System.out.println(list);
    }

    @Test
    public void test02() {
//        List<Poetry> list = testService.queryByTitle("李白");
//        System.out.println("查询到：" + list.size() + "条数据。");
//        for (Poetry poetry : list) {
//            System.out.println(poetry);
//        Function
    }


}
