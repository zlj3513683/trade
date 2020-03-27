package com.posppay.newpay.modules.xposp.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DealController extends HttpServlet {
    public static Map<String,Object> getParameterMap(HttpServletRequest request){
        Map<String,String[]> map = new HashMap<String,String[]>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        map = request.getParameterMap();
        Iterator entries = map.entrySet().iterator();
        Map.Entry entry;
        String name ="";
        String value=null;
        while (entries.hasNext()){
            entry=(Map.Entry)entries.next();
            name = (String) entry.getKey();
            Object objvalue = entry.getValue();
            if(objvalue == null){
                value = null;
            }else if(objvalue instanceof String[]){
                /**条件如果成立，objvalue就是一个数组，需要将它转换成为字符串，并拼接上逗号，并吧末尾的逗号去掉*/
                String[] values = (String[]) objvalue;
                for(int i=0;i<values.length;i++){
                    value = values[i]+",";//这里我拼接的是英文的逗号。
                }
                value = value.substring(0,value.length()-1);//截掉最后一个逗号。
            }else{
                value = objvalue.toString();
            }
            returnMap.put(name , value);
        }
        Iterator it = returnMap.keySet().iterator();
        while (it.hasNext()){
            Object key = it.next();
            if(returnMap.get(key) == null || "".equals (((String)returnMap.get(key)).trim())){
                returnMap.put((String) key, null);
            }
        }
        return returnMap;
    }
}

