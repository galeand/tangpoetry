package com.sse.tangpoetry.controller;

import com.sse.tangpoetry.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PoetryController {
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
        switch (name) {
            case "统计": {
                List<String> intro = new ArrayList<>();
                intro.add(" 古诗是中华民族乃至全世界的瑰宝, 我们应该传承下去,此数据库共收录全唐诗共计43030首," +
                        "其中收集唐朝诗人写诗最多的人如下：白居易、杜甫、李白、佚名、齐己、刘禹锡、元稹、李商隐、贯休、韦应物");
                intro.add("《全唐诗》是清康熙四十四年（1705年），彭定求、沈三曾、杨中讷、汪士鋐、汪绎、俞梅、徐树本、车鼎晋、" +
                        "潘从律、查嗣瑮10人奉敕编校，“得诗四万八千九百余首，凡二千二百余人”,共计900卷，目录12卷。");
                intro.add("全唐诗的编修过程是这样的：康熙四十二年(1703年)，清圣祖玄烨即考虑编纂此书，至四十四年(1705年)三月，" +
                        "他第五次南巡至苏州时，将主持修书的任务交给江宁织造曹寅，并将内府所藏季振宜《唐诗》一部发下，" +
                        "作为校刊底本。同年五月，由曹寅主持，在扬州开局修书，参加校刊编修的有赋闲江南的在籍翰林官彭定求、" +
                        "沈三曾、杨中讷、潘从律、汪士綋，徐树本、车鼎晋，汪绎、查嗣瑮、俞梅等十人。至次年十月，全书即编成奏上。" +
                        "得诗四万八千九百余首，凡二千二百余人”， 共计900卷，目录12卷。(来自百科)");
                map.put("intros", intro);
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

    @PostMapping("/login")
    public String login(@RequestParam("user") String user,
                        @RequestParam("pwd") String pwd,
                        Map<String, Object> logsMap) {
        if (user.equals("admin") && pwd.equals("12345")) {
            logsMap.put("logs", logsList);
            return "main";
        }
        return "login";
    }


}
