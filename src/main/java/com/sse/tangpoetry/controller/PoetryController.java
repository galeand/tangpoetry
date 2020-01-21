package com.sse.tangpoetry.controller;

import com.sse.tangpoetry.constant.BaseConstant;
import com.sse.tangpoetry.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PoetryController implements BaseController{
    @Autowired
    private PoetryService poetryService;

    private static List<String> logsList = new ArrayList<>(100);

    @RequestMapping("/tang")
    public String tangPoetry(@RequestParam("name") String name,
                             Map<String, Object> map) {
        System.out.println("输入的关键字：" + name);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        logsList.add("关键字：" + name + "\t\t;时间：" + time);

        name = name.trim();//去掉字符首尾空格
        if (name.startsWith("“") && name.endsWith("”") || name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, 3);//针对“统计”这个关键字进行相关处理；
        }
        if (name.equals("") || name.length() <= 0) {
            name = "空";
        }
        switch (name) {
            case "统计": {
                map.put("intros", BaseConstant.TANGPOETRY_INTRO);
                break;
            }
            case "空": {
                map.put("intros", Collections.singletonList("输入不能为空或者空格"));
                break;
            }
            default: {

                //判定输入的应该是诗人的名字
                if (name.endsWith("诗人")) {
                    String name2 = name.substring(0, name.length() - 2);
                    System.out.println(name2);
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

        return "index";
    }

//    @RequestMapping("/level1/login")
//    public String logs(Map<String, Object> logsMap) {
//        logsMap.put("logs", logsList);
//        return "level1/main";
//    }

    @PostMapping("/main")
    public String login(@RequestParam("user") String user,
                        @RequestParam("pwd") String pwd,
                        Map<String, Object> logsMap) {
        if (user.equals("admin") && pwd.equals("12345")) {
            logsMap.put("logs", logsList);
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


}
