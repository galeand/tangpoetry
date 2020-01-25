package com.sse.tangpoetry.controller;

import com.sse.tangpoetry.bean.*;
import com.sse.tangpoetry.constant.BaseConstant;
import com.sse.tangpoetry.enums.PoetryTypeEnum;
import com.sse.tangpoetry.enums.QueryTypeEnum;
import com.sse.tangpoetry.service.IpRecordService;
import com.sse.tangpoetry.service.PoetryService;
import com.sse.tangpoetry.service.SongCiService;
import com.sse.tangpoetry.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PoetryController implements BaseController{
    private static final Logger logger = LoggerFactory.getLogger("PoetryController.class");
    @Autowired
    private PoetryService poetryService;
    @Autowired
    private SongCiService songCiService;
    @Autowired
    private IpRecordService ipRecordService;

    @RequestMapping("/tang")
    public String tangPoetry(@RequestParam("name") String name,
                             @RequestParam("select") Integer select,
                             HttpServletRequest request,
                             Map<String, Object> map) {
        logger.info("输入的关键字：" + name);
        if (name.trim().equals("") || name.trim().length() <= 0) {
            map.put("intros", Collections.singletonList("输入不能为空或者空格"));
            return "index";
        }
        PoetryTypeEnum type = PoetryTypeEnum.typeOf(select);
        IpRecord record = new IpRecord(name, request, type);
        //记录访问ip信息
        ipRecordService.ipRecordHandler(record);
        //判断是否查询宋词
        if (select.equals(PoetryTypeEnum.SONG_CI.getCode())) {
            Map<String, Object> retMap = handSONGCI(name);
            if (Objects.nonNull(retMap)){
                map.putAll(retMap);
            }else {
                map.put("intros", "输入有误，请重新输入");
            }
        }else {
            name = name.trim();//去掉字符首尾空格
            if (name.startsWith("“") && name.endsWith("”") || name.startsWith("\"") && name.endsWith("\"")) {
                name = name.substring(1, 3);//针对“统计”这个关键字进行相关处理；
            }

            switch (name) {
                case "统计": {
                    map.put("intros", BaseConstant.TANGPOETRY_INTRO);
                    break;
                }
                default: {
                    //判定输入的应该是诗人的名字
                    if (name.endsWith("诗人")) {
                        String name2 = name.substring(0, name.length() - 2);
                        List<Object> listByPoet = poetryService.getPoetryByPoet(name2);
                        if (listByPoet.size() <= 0) {
                            map.put("error", "Maybe this poet was not from the Tang Dynasty.Please re-enter");
                            return "index";
                        }
                        int num = (int) listByPoet.get(0);
                        if (num > 0) {
                            String numStr = "关键字:" + name + "\t\t\t\t(按照诗人名字进行查询)\t\t<总共查到" + num + "条记录>";
                            map.put("num", numStr);
                            map.put("poetries", listByPoet.get(1));
                        }
                    } else {//按照标题来查
                        List<Object> listByTitle = poetryService.getPoetryByTitle(name);
                        int num = (int) listByTitle.get(0);
                        if (num > 0) {
                            String numStr = "关键字:" + name + "\t\t\t\t(按照唐诗名进行模糊查询)\t\t<总共查到" + num + "条记录>";
                            map.put("num", numStr);
                            map.put("poetries", listByTitle.get(1));
                        } else {//错误处理
                            List<Object> poetryByContent = poetryService.getPoetryByContent(name);
                            int numByContent = (int) poetryByContent.get(0);
                            if (numByContent > 0) {//标题没有命中，再按照内容来查一次
                                String numStr = "关键字:" + name + "\t\t\t\t(按照诗句进行查询)\t\t<总共查到" + numByContent + "条记录>";
                                map.put("num", numStr);
                                map.put("poetries", poetryByContent.get(1));
                            } else {
                                map.put("error", "Wrong Title or Wrong content!Please re-enter");
                            }
                        }
                    }
                    break;
                }

            }
        }

        return "index";
    }

    @PostMapping("/main")
    public String login(@RequestParam("user") String user,
                        @RequestParam("pwd") String pwd,
                        Map<String, Object> logsMap) {
        if (user.equals("admin") && pwd.equals("12345")) {
            List<String> list = ipRecordService.getAll();
            logsMap.put("logs", list);
            return "main";
        }
        return "login";
    }

    //这个controller是为了来测试eureka实现分布式远程调用
    @ResponseBody
    @GetMapping("/tangtest") //为什么这里后面不能加pathVariable呢，进行远程调用的时候
    public Object tangPoetryPRCByEureka(@RequestParam String content) {
        List<Object> list = poetryService.getPoetryByTitle(content);

        return list;
    }

    private Map<String, Object> handSONGCI(String name){
        String key = name.trim();//去掉首位空格
        if (key.equals("统计")){
            Map<String, Object> map = new HashMap<>();
            map.put("intros", BaseConstant.SONGCI_INTRO);
            return map;
        }
        if (key.endsWith("诗人") || key.endsWith("词人") || key.endsWith("作者")){
            key = key.substring(0, key.length()-2);
        }
        //先按照词人名查找一次，然后再按照标题或者词句查找一次，最后组装结果
        SongCiDto retDto = songCiService.songCiHandler(key);
        if (Objects.nonNull(retDto)){
            return assembleSongCiDto(retDto);
        }
        return null;
    }

    private Map<String, Object> assembleSongCiDto(SongCiDto retDto){
        Map<String, Object> map = new HashMap<>();
        QueryTypeEnum type = retDto.getType();
        if (Objects.nonNull(type)) {
            switch (type){
                case TITLE:
                    List<String> intro = new ArrayList<>();
                    intro.add("<查询type:" + retDto.getType() + ",条数:" + retDto.getSongCiList().size() + ">");
                    intro.add("词人:" + retDto.getAuthor());
                    intro.add("生平:" + retDto.getIntro());
                    List<String> songCi = transformSongCi(retDto.getSongCiList());
                    map.put("intros", intro);
                    map.put("poetries", songCi);
                    break;
                case AUTHOR:
                    List<String> intro2 = new ArrayList<>();
                    intro2.add("<查询type:" + retDto.getType() + ",条数:" + retDto.getSongCiList().size() + ">");
                    intro2.add("词人:" + retDto.getAuthor());
                    intro2.add("生平:" + retDto.getIntro());
                    List<String> songCi2 = transformSongCi(retDto.getSongCiList());
                    map.put("intros", intro2);
                    map.put("poetries", songCi2);
                    break;
                case CONTENT:
                    List<String> intro3 = new ArrayList<>();
                    intro3.add("<查询type:" + retDto.getType() + ",条数:" + retDto.getSongCiList().size() + ">");
                    intro3.add("词人:" + retDto.getAuthor());
                    intro3.add("生平:" + retDto.getIntro());
                    List<String> songCi3 = transformSongCi(retDto.getSongCiList());
                    map.put("intros", intro3);
                    map.put("poetries", songCi3);
                    break;
                case NICKNAME:
                    List<String> intro4 = new ArrayList<>();
                    intro4.add("<查询type:" + retDto.getType() + ",条数:" + retDto.getSongCiList().size() + ">");
                    intro4.add("词人:" + retDto.getAuthor());
                    intro4.add("生平:" + retDto.getIntro());
                    List<String> songCi4 = transformSongCi(retDto.getSongCiList());
                    map.put("intros", intro4);
                    map.put("poetries", songCi4);
                    break;
                default:
                    break;
            }
        }
        if (map.size()>0){
            return map;
        }else {
            return null;
        }
    }

    public List<String> transformSongCi(List<SongCiEveryDto> songCiList){

        List<String> resList = new ArrayList<>();

        for (SongCiEveryDto every: songCiList){
            if (Objects.isNull(every) || Objects.isNull(every.getParagraphs()) || Objects.isNull(every.getRhythmic())) {
                continue;
            }
            resList.add("《" + every.getRhythmic() + "》");
            resList.add(every.getName());
            addContent(resList, every.getParagraphs());
            resList.add("-----------------------------");

        }
        return resList;
    }

    private static void addContent(List<String> resList, String content) {
        content = content.replaceAll("\",\"", "");
        content = content.replaceAll("\"", "");
        content = content.substring(1, content.length()-1);

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
                    if (i==content.length()-1){
                        //最后一句
                        String last = resList.remove(resList.size() - 1);
                        last = last + content.substring(startIndex, i + 1);
                        resList.add(last);
                    }

                }
            }
        }

    }
}
