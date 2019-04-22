package com.sse.tangpoetry;

import com.sse.tangpoetry.bean.Poet;
import com.sse.tangpoetry.bean.Poetry;
import com.sse.tangpoetry.mapper.PoetMapper;
import com.sse.tangpoetry.mapper.PoetryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TangpoetryApplicationTests {
    @Autowired
    PoetMapper poetMapper;
    @Autowired
    PoetryMapper poetryMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
        List<Poetry> list = poetryMapper.getPoetryLiketitle("静夜");
        System.out.println(list.size());
        System.out.println(list);
//        Poetry poetry = poetryMapper.getPoetryByTitle("静夜思");
//        System.out.println(poetry.toString());

//        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from poetries where title like '%行路难%'");
//        System.out.println(list.get(0));


    }


    //通过诗人查找古诗，考虑到每个诗人写的古诗太多，所以用每一行表示一首古诗
    @Test
    public void getPoetryByPoet() {
        String poetName = "韦应物";
        Poet poet = poetMapper.getPoetByName(poetName);
        Integer poet_id = poet.getId();//这里通过诗人名字找到诗人id，然后通过诗人id去poetry库里面查找诗句
        List<Poetry> poetryList = poetryMapper.getPeotryByPoet_id(poet_id);
        List<String> resList = new ArrayList<>(30);
        for (int i = 0; i < poetryList.size(); i++) {
            Poetry poetry = poetryList.get(i);
            String title = poetry.getTitle();
            String content = poetry.getContent();
            String res = "《" + title + "》" + "\t诗人:" + poetName + "\t" + "{" + content + "}";
            resList.add(res);
        }
        resList.forEach(val -> {
            System.out.println(val);
        });
    }

}
