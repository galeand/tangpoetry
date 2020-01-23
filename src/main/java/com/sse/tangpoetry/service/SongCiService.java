package com.sse.tangpoetry.service;


import com.sse.tangpoetry.bean.SongCi;
import com.sse.tangpoetry.bean.SongCiAuthor;
import com.sse.tangpoetry.bean.SongCiDto;
import com.sse.tangpoetry.bean.SongCiEveryDto;
import com.sse.tangpoetry.enums.QueryTypeEnum;
import com.sse.tangpoetry.mapper.SongCiAuthorMapper;
import com.sse.tangpoetry.mapper.SongCiMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @name: SongCiService
 * @author: yf.xiang
 * @create: 2020-01-23 13:45
 * @description:
 */
@Service
public class SongCiService {
    @Autowired
    private SongCiMapper songCiMapper;
    @Autowired
    private SongCiAuthorMapper songCiAuthorMapper;

    public SongCiDto songCiHandler(String key) {
        if (Strings.isEmpty(key)) {
            return null;
        }
        SongCiDto songCiDto = new SongCiDto();
        //词人和词牌名
        SongCiAuthor author = songCiAuthorMapper.getSongCiAuthorByName(key);
        if (Objects.nonNull(author)){
            songCiDto.setAuthor(author.getName());
            songCiDto.setIntro(author.getDescription());
            List<SongCi> songCiList = songCiMapper.getSongCiByAuthor(key);
            if (!CollectionUtils.isEmpty(songCiList)){
                List<SongCiEveryDto> list = getSongCiList(songCiList);
                songCiDto.setSongCiList(list);
                songCiDto.setType(QueryTypeEnum.AUTHOR);
                return songCiDto;
            }
        }else{
            List<SongCi> songCiByTitleList = songCiMapper.getSongCiByTitle(key);
            if (!CollectionUtils.isEmpty(songCiByTitleList)){
                List<SongCiEveryDto> list = getSongCiList(songCiByTitleList);
                songCiDto.setSongCiList(list);
                String authorName = songCiByTitleList.get(0).getAuthor();
                SongCiAuthor author1 = songCiAuthorMapper.getSongCiAuthorByName(authorName);
                songCiDto.setAuthor(authorName);
                songCiDto.setIntro(author1.getDescription());
                songCiDto.setType(QueryTypeEnum.TITLE);
                return songCiDto;
            }
        }
        //词人别称和词句
        List<SongCi> songCiByContent = songCiMapper.getSongCiByContent(key);
        if (!CollectionUtils.isEmpty(songCiByContent)){
            List<SongCiEveryDto> list = getSongCiList(songCiByContent);
            songCiDto.setSongCiList(list);
            String authorName = songCiByContent.get(0).getAuthor();
            SongCiAuthor author1 = songCiAuthorMapper.getSongCiAuthorByName(authorName);
            songCiDto.setAuthor(author1.getName());
            songCiDto.setIntro(author1.getDescription());
            songCiDto.setType(QueryTypeEnum.CONTENT);
            return songCiDto;
        }else {
            SongCiAuthor songCiAuthorByDesc = songCiAuthorMapper.getSongCiAuthorByDesc(key);
            if (Objects.nonNull(songCiAuthorByDesc)) {
                songCiDto.setType(QueryTypeEnum.NICKNAME);
                songCiDto.setIntro(songCiAuthorByDesc.getDescription());
                String authorName = songCiDto.getAuthor();
                List<SongCi> songCiList = songCiMapper.getSongCiByAuthor(authorName);
                List<SongCiEveryDto> songCiList1 = getSongCiList(songCiList);
                if (!CollectionUtils.isEmpty(songCiList1)) {
                    songCiDto.setSongCiList(songCiList1);
                    return songCiDto;
                }
            }
        }

        return null;
    }

    private List<SongCiEveryDto> getSongCiList(List<SongCi> list){
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<SongCiEveryDto> list1 = new ArrayList<>(64);
        list.parallelStream().map(x -> {
            SongCiEveryDto every = new SongCiEveryDto();
            every.setParagraphs(x.getParagraphs());
            every.setRhythmic(x.getRhythmic());
            every.setName(x.getAuthor());
            list1.add(every);
            return true;
        }).collect(Collectors.toList());
        return list1;
    }
}
