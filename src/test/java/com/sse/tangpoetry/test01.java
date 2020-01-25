package com.sse.tangpoetry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sse.tangpoetry.bean.Poetry;
import com.sse.tangpoetry.bean.SongCi;
import com.sse.tangpoetry.bean.SongCiAuthor;
import com.sse.tangpoetry.bean.SongCiDto;
import com.sse.tangpoetry.mapper.SongCiAuthorMapper;
import com.sse.tangpoetry.mapper.SongCiMapper;
import com.sse.tangpoetry.service.PoetryService;
import com.sse.tangpoetry.service.SongCiService;
import com.sse.tangpoetry.service.impl.TestService;
import com.sse.tangpoetry.util.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.FileTime;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class test01 {
    private static final Logger logger = LoggerFactory.getLogger("test01.class");
    @Autowired
    private PoetryService poetryService;
    @Autowired
    private TestService testService;
    @Autowired
    private SongCiMapper songCiMapper;
    @Autowired
    private SongCiAuthorMapper songCiAuthorMapper;
    @Autowired
    private SongCiService songCiService;

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

    @Test
    public void convertJsonFileToDb() throws FileNotFoundException {
//        for (int index=1000; index<=21000; index+=1000) {
//
//            String filePath = "/Users/mac/Desktop/诗词/chinese-poetry/ci/ci.song." + index + ".json";
//
//            String songCiStr = FileUtils.readJsonFile(filePath);
//
//            JSONArray jsonArray = JSON.parseArray(songCiStr);
//            logger.info("file:{}", filePath);
//            logger.info("file json size:{}", jsonArray.size());
//
//            jsonArray.parallelStream().map(x -> {
//                JSONObject jsonObject = JSONObject.parseObject(x.toString());
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String time = df.format(new Date());
//                //gradle引入lombok插件有问题，不能用
//                SongCi one = new SongCi();
//                one.setAuthor(jsonObject.get("author").toString());
//                one.setParagraphs(jsonObject.get("paragraphs").toString());
//                one.setRhythmic(jsonObject.get("rhythmic").toString());
//                one.setCreatedAt(Timestamp.valueOf(time));
//                one.setUpdatedAt(Timestamp.valueOf(time));
//                boolean re = songCiMapper.insertOneRecord(one);
//                logger.info("ci title:{}", jsonObject.get("rhythmic").toString());
//                logger.info("insert time:{}", time);
//                logger.info("re:{}", re);
//                return true;
//            }).collect(Collectors.toList());
//        }

    }

    @Test
    public void insertSongCiAuthorToDb(){
//        String file = "/Users/mac/Desktop/诗词/chinese-poetry/ci/author.song.json";
//
//        String songCiAuthorStr = FileUtils.readJsonFile(file);
//        JSONArray jsonArray = JSON.parseArray(songCiAuthorStr);
//        jsonArray.parallelStream().map(x -> {
//            JSONObject jsonObject = JSONObject.parseObject(x.toString());
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time = df.format(new Date());
//            //gradle引入lombok插件有问题，不能用
//            SongCiAuthor one = new SongCiAuthor();
//            one.setName(jsonObject.get("name").toString());
//            one.setDescription(jsonObject.get("description").toString());
//            one.setCreatedAt(Timestamp.valueOf(time));
//            one.setUpdatedAt(Timestamp.valueOf(time));
//            boolean re = songCiAuthorMapper.insertOneRecord(one);
//            logger.info("name:{}", jsonObject.get("name").toString());
//            logger.info("insert time:{}", time);
//            logger.info("re:{}", re);
//            return true;
//        }).collect(Collectors.toList());

//        SongCiAuthor val = songCiAuthorMapper.getSongCiAuthorByName("苏轼");
//        logger.info(val.toString());

        SongCiDto ret = songCiService.songCiHandler("统计");

        System.out.println(ret);

    }
}
