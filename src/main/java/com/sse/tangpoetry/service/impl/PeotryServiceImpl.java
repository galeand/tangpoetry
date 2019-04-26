package com.sse.tangpoetry.service.impl;

import com.sse.tangpoetry.bean.Poet;
import com.sse.tangpoetry.bean.Poetry;
import com.sse.tangpoetry.mapper.PoetMapper;
import com.sse.tangpoetry.mapper.PoetryMapper;
import com.sse.tangpoetry.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PeotryServiceImpl implements PoetryService {
    @Autowired
    PoetMapper poetMapper;
    @Autowired
    PoetryMapper poetryMapper;

    public List<Object> getPoetryByPoet(String poetName) {
        Poet poet = this.poetMapper.getPoetByName(poetName);
        System.out.println(poet);
        List<Object> result = new ArrayList();
        if (poet != null) {
            Integer poet_id = poet.getId();
            List<Poetry> poetryList = this.poetryMapper.getPeotryByPoet_id(poet_id);
            int num = poetryList.size();
            result.add(num);
            if (num > 0) {
                List<String> resList = new ArrayList(30);

                for (int i = 0; i < poetryList.size(); ++i) {
                    Poetry poetry = (Poetry) poetryList.get(i);
                    String title = poetry.getTitle();
                    String res = "《" + title + "》--" + poetName;
                    resList.add(res);
                    resList.add("\n");
                }

                result.add(resList);
            }
        }

        return result;
    }

    public List<Object> getPoetryByTitle(String title) {
        List<Poetry> titleList = this.poetryMapper.getPoetryLiketitle(title);
        int num = titleList.size();
        List<Object> result = new ArrayList();
        result.add(num);
        if (num > 0) {
            List<String> resList = new ArrayList();

            for (int i = 0; i < titleList.size(); ++i) {
                Poetry poetry = (Poetry) titleList.get(i);
                Integer poet_id = poetry.getPoet_id();
                Poet poet = this.poetMapper.getPoetByPoet_id(poet_id);
                String poetName = poet.getName();
                resList.add("《" + poetry.getTitle() + "》");
                resList.add(poetName);
                addContent(resList, poetry.getContent());
                resList.add("-----------------------------");
            }

            result.add(resList);
        }

        return result;
    }

    private static void addContent(List<String> resList, String content) {
        List<Character> punctuation = new ArrayList();
        punctuation.addAll(Arrays.asList('，', '。', '？', '！', '、', ',', '?', '!', ';', '；'));
        int startIndex = 0;

        for (int i = 0; i < content.length(); ++i) {
            startIndex = i;
            int count = 0;

            while (i < content.length()) {
                ++i;
                if (i < content.length() && punctuation.contains(content.charAt(i))) {
                    ++count;
                    if (count >= 2) {
                        resList.add(content.substring(startIndex, i + 1));
                        break;
                    }
                }
            }
        }

    }

    public List<Object> getPoetryByContent(String content) {
        List<Poetry> poetryLikeContent = this.poetryMapper.getPoetryLikeContent(content);
        int num = poetryLikeContent.size();
        List<Object> result = new ArrayList();
        result.add(num);
        if (num > 0) {
            List<String> resList = new ArrayList();

            for (int i = 0; i < poetryLikeContent.size(); ++i) {
                Poetry poetry = (Poetry) poetryLikeContent.get(i);
                Integer poet_id = poetry.getPoet_id();
                Poet poet = this.poetMapper.getPoetByPoet_id(poet_id);
                String poetName = poet.getName();
                String s = addContentByLike(poetry.getContent(), content);
                resList.add(s + "《" + poetry.getTitle() + "》--" + poetName);
            }

            result.add(resList);
        }

        return result;
    }

    private static String addContentByLike(String totalcontent, String subcontent) {
        List<Character> punctuation = new ArrayList();
        punctuation.addAll(Arrays.asList('，', '。', '？', '！', '、', ',', '?', '!', ';', '；'));
        String result = "";
        int startIndex = 0;
        List<String> divide = new ArrayList();

        int i;
        for (i = 0; i < totalcontent.length(); ++i) {
            startIndex = i;

            while (i < totalcontent.length()) {
                ++i;
                if (i < totalcontent.length() && punctuation.contains(totalcontent.charAt(i))) {
                    divide.add(totalcontent.substring(startIndex, i));
                    break;
                }
            }
        }

        if (divide.contains(subcontent)) {
            i = divide.indexOf(subcontent);
            if (i < divide.size() - 1) {
                    result = subcontent + "," + (String) divide.get(i + 1);
                }
        }

        return result;
    }
}
