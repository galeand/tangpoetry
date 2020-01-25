package com.sse.tangpoetry.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sse.tangpoetry.bean.IpRecord;
import com.sse.tangpoetry.bean.UserAccessTable;
import com.sse.tangpoetry.constant.BaseConstant;
import com.sse.tangpoetry.enums.PoetryTypeEnum;
import com.sse.tangpoetry.mapper.UserAccessTableMapper;
import com.sse.tangpoetry.util.IpUtils;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @name: IpRecordService
 * @author: yf.xiang
 * @create: 2020-01-25 13:32
 * @description:
 */
@Service
public class IpRecordService {
    private static final Logger logger  = LoggerFactory.getLogger(IpRecordService.class);

    @Autowired
    private UserAccessTableMapper ipMapper;

    public void ipRecordHandler(IpRecord record){
        if (Objects.isNull(record)){
            return;
        }
        String key = record.getKey();
        HttpServletRequest request = record.getRequest();
        PoetryTypeEnum type = record.getType();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        logger.info("查询关键字:{}", key);
        logger.info("查询类型:{}", type.getDesc()) ;
        logger.info("查询时间:{}",time);
        //记录查询ip，对应解析ip地址对应的物理地址
        String ip = IpUtils.getIpAddr(request);
        logger.info("请求的ip:{}", ip);
        List<String> deviceInfoList = IpUtils.getDeviceAndOS(request);
        logger.info("请求的设备信息:{}", deviceInfoList);

        if (!execute(ip, key, deviceInfoList, type)){
            logger.info("记录访问ip失败,请求:{}", request);
        }
    }

    public Boolean execute(String ip, String keyWord, List<String> deviceInfo, PoetryTypeEnum type) {
        String url = BaseConstant.BASE_IP_URL+"?ip="+ip;
        String res = IpUtils.get(url);
        String ret = null;
        if (!StringUtils.isEmpty(res)) {
            JSONObject jsonObject = JSON.parseObject(res);
            if (jsonObject.containsKey("data")){
                ret = jsonObject.getString("data");
            }
        }
        logger.info("ip对应的物理地址:{}",ret);

        if (!StringUtils.isEmpty(ret)){
            JSONObject retJSON = JSON.parseObject(ret);
            String ipRealAddress = retJSON.getString("country")+","+retJSON.getString("city")+","
                    +retJSON.getString("regison")+","+retJSON.getString("county")+","+retJSON.getString("area")+","+retJSON.get("isp");
            String addressJson = ret;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String accessTime = df.format(new Date());
            String createdAt = accessTime;
            String userAgent = null;
            String os = null;
            String device = null;
            if (deviceInfo.size() == 3) {
                userAgent = deviceInfo.get(0);
                os = deviceInfo.get(1);
                device = deviceInfo.get(2);
            }
            return ipMapper.insert(ip, ipRealAddress, addressJson, accessTime, keyWord, createdAt, "", userAgent, device, os, type.getDesc());
        }
        return false;
    }

    public List<String> getAll(){
        List<UserAccessTable> list = ipMapper.selectAll();
        List<String> ret = new ArrayList<>();
        list.forEach(val -> {
            String ip = val.getIp();
            String keyWord = val.getKeyWord();
            String address = val.getIpRealAddress();
            String time = val.getCreatedAt();
            String[] addressArr = address.split(",");
            List<String> addressList = new ArrayList<>();
            for (String s:addressArr){
                if (!s.isEmpty()){
                    addressList.add(s);
                }
            }
            String realAddress = addressList.stream().collect(Collectors.joining("-"));
            String device = val.getDevice();
            String os = val.getOs();
            String possiblePerson = val.getPossiblePersonName();
            String res = "【关键字:" + keyWord + ",type:" + val.getType() + ",查询时间:" + time + ",访问ip:" + ip + ",ip对应的物理地址:" + realAddress + ",访问设备:" + device + ",设备系统:" + os + ",可能访问人:"+possiblePerson+
                    "】";
            ret.add(res);
        });
        return ret;
    }
}
