package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

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
}
