package com.sse.tangpoetry.util;

import com.sse.tangpoetry.bean.IpRecord;
import com.sse.tangpoetry.enums.PoetryTypeEnum;
import com.sse.tangpoetry.service.IpRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @name: IpUtils
 * @author: yf.xiang
 * @create: 2020-01-25 12:55
 * @description:
 */
public class IpUtils {
    private static final Logger logger = LoggerFactory.getLogger("IpUtils.class");

    public static String get(String httpUrl){
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

    public static String getIpAddr(HttpServletRequest request){
        String ipAddr = null;
        try{
            ipAddr = request.getHeader("x-forwarded-for");
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)){
                ipAddr = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)){
                ipAddr = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)){
                ipAddr = request.getRemoteAddr();
                if (ipAddr.equals("127.0.0.1")){
                    //根据网卡取本机配置的ip
                    InetAddress inet = null;
                    inet = InetAddress.getLocalHost();
                    ipAddr = inet.getHostAddress();
                }
            }
            //// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddr != null && ipAddr.length() > 15){
                if (ipAddr.indexOf(",") > 0){
                    ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
                }
            }
        }catch (Exception e){
            logger.info("获取ip异常:{}", e.toString());
            ipAddr = "";
        }
        return ipAddr;
    }

    public static List<String> getDeviceAndOS(HttpServletRequest request){
        List<String> list = new ArrayList<>();
        // 在有可能抛出异常的地方使用try...catch...语句来进行异常的捕获
        try {
            String header = request.getHeader("User-Agent");
            logger.info("用户请求头:{}", header);
            String userInfo = header.substring(header.indexOf("(") + 1, header.indexOf(")"));
            String[] userInfoArr = userInfo.split(";");
            String os = null;
            String device = null;
            if (userInfoArr.length >= 3) {
                //手机访问
                os = userInfoArr[1] + "\t\t" + userInfoArr[0];
                //这里获取手机设备型号时有问题，还没有太好的解决方法
                device = userInfoArr[2];
            } else if (userInfoArr.length == 2) {
                //浏览器访问
                os = userInfoArr[1] + "\t\t" + userInfoArr[0];
                device = "Brower";
            }
            list.add(header);
            list.add(os);
            list.add(device);
        }catch (Exception e){
            logger.info("获取设备信息异常:{}", e.toString());
        }
        return list;
    }
}
