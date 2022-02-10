package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 网站工具类
 * @author Alex
 * @version 1.0
 * @date 2022/2/2 12:19
 */
public class CommonUtil {

    private CommonUtil(){

    }

    /**
     * 生成随机字符串
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * md5加密方法
     * @param key
     * @return
     */
    public static String md5Encode(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 判断是否为空的方法
     * @param obj
     * @return true/false
     */
    public static boolean isEmtpy(Object obj)
    {
        if (null == obj)
        {
            return true;
        }
        else if (obj instanceof String)
        {
            String str = (String) obj;
            return str.trim().length() == 0;
        }
        else if (obj instanceof Collection)
        {
            Collection<?> coll = (Collection<?>) obj;
            return coll.size() == 0;
        }
        else if (obj instanceof Map)
        {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.size() == 0;
        }
        else if (obj.getClass().isArray())
        {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    /**
     * 获取json字符串
     * @param code 操作码
     * @param msg 提示信息
     * @param map 业务数据
     * @return json字符串
     */
    public static String getJsonString(int code,String msg,Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);

        if (!isEmtpy(map)) {
            for(String key:map.keySet()){
                jsonObject.put(key,map.get(key));
            }
        }

        return jsonObject.toJSONString();
    }

    /**
     * 获取json字符串方法重载
     * @param code
     * @param msg
     * @return
     */
    public static String getJsonString(int code,String msg){
        return getJsonString(code,msg,null);
    }

    /**
     * 获取json字符串方法重载
     * @param code
     * @return
     */
    public static String getJsonString(int code){
        return getJsonString(code,null,null);
    }

    public static String getFormatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formatDateString = sdf.format(date);
        return formatDateString;

    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",25);
        System.out.println(getJsonString(0,"操作成功",map));

        System.out.println(getFormatDate(new Date()));
        System.out.println(generateUUID().substring(0,6));
    }
}
