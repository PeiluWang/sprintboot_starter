package org.pillow.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class HttpRequestUtil {

	
	/** 
     * 获取前台传过来的参数 
     * 
     * @param request 
     * @return 
     */  
    public static String getParam(HttpServletRequest request) {  
        Map properties = request.getParameterMap();  
        Map returnMap = new HashMap();  
        Iterator entries = properties.entrySet().iterator();  
        Map.Entry entry;  
        String name = "";  
        String value = "";  
        while (entries.hasNext()) {  
            entry = (Map.Entry) entries.next();  
            name = (String) entry.getKey();  
            Object valueObj = entry.getValue();  
            value = null;  
            if (null == valueObj) {  
                value = "";  
            } else if (valueObj instanceof String[]) {  
                String[] values = (String[]) valueObj;  
                for (int i = 0; i < values.length; i++) {  
                    if (value == null)  
                        value = (values[i] == null ? "" : values[i]);  
                    else  
                        value += "," + (values[i] == null ? "" : values[i]);  
                }  
            } else {  
                value = valueObj.toString();  
            }  
            returnMap.put(name, value);  
        }
        return new JSONObject(returnMap).toString();
    }
	
	
	/**
	 * 获取来访者ip
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!isIpValid(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!isIpValid(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!isIpValid(ip)) {
			ip = request.getHeader("HTTP_Client_IP");
		}
		if (!isIpValid(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (!isIpValid(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多代理情况，获取第一个
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
	
	
	/**
	 * 检查ip字符串是否合规
	 * @param ip
	 * @return
	 */
	private static boolean isIpValid(String ip){
		if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
			return false;
		}
		return true;
	}
	
}
