package cn.gtmap.realestate.exchange.util.zrpay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 生成签名原串
 */
public class CreateSignOriginStr {

	/**
	 * 拼接原串
	 */
	@SuppressWarnings("rawtypes")
	public static String splicingSign(String characterEncoding,SortedMap<String,Object> parameters,Set<String> set){
		StringBuffer sbuffer = new StringBuffer();
        Set es = parameters.entrySet();
		Iterator it = es.iterator();
        while(it.hasNext()) {
            Entry entry = (Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(set.contains(k)) {
            	sbuffer.append(v);
            }else {
            	if(!k.equals("SIGN_INF")) {
            		sbuffer.append(k + "=" + v + "&");  
            	}
            }              
        }
        return sbuffer.toString();
    }
	

	/**
	 * 生成原串
	 */
	public static String createSign(String json, Set<String> set) {
		
		SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
		
		//转换JSON对象
		JSONObject jsonObject  = JSON.parseObject(json);
		
		//遍历循坏外字段
        for (Entry<String, Object> entry : jsonObject.entrySet()) {
        	
            if(null != entry.getValue() 
            		&& !"".equals(entry.getValue()) 
            		&& !set.contains(entry.getKey())) {
            	sortedMap.put(entry.getKey(), entry.getValue());
            }
        }
        
       
    	if(!set.isEmpty()) {
        	//处理集合字段
            Iterator<String> it = set.iterator();
    		while (it.hasNext()) {
    			String listKey = it.next();
    			//System.out.println("List Key---------" + listKey);
    			
    			//String listValue = processingSet(jsonObject,listKey);
    			StringBuilder listValue = new StringBuilder().append(listKey).append("=")
    					.append(jsonObject.getString(listKey)).append("&");
    			sortedMap.put(listKey,listValue.toString());
    		}
        }
		
   
        //字段拼接
        String sign = splicingSign("UTF-8", sortedMap, set);
        sign = sign.substring(0, sign.length()-1);
		return sign;
	}


	
	/**
	 * 处理集合内字段
	 */
	public static String processingSet(JSONObject jsonObj, String listKey) {
		SortedMap<String, Object> sortedMap = null;
		Set<String> set = new HashSet<String>();
		//返回拼接的原串
		StringBuffer sbuffer = new StringBuffer();
		
		//根据key获取集合json字符串
        String list = jsonObj.getString(listKey);
        //System.out.println("获取List json字符串----------" + list);
        if(list != null && list != "") {
        	//list转换对象
    	    JSONArray jarr = JSONArray.parseArray(list);
    	    
    	    //创建json集合
    	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
    		for (int i = 0; i < jarr.size(); i++) {
    			jsonValues.add(jarr.getJSONObject(i));
    		}

    		//将json集合按照SN排序
    		Collections.sort(jsonValues, new Comparator<JSONObject>() {
    			public int compare(JSONObject a, JSONObject b) {
    				String valA = a.getString("SN");
    				String valB = b.getString("SN");
    				return valA.compareTo(valB);
    			}
    		});
    		
    		//暂存每一个子元素拼接的值
    		String s = "";
    		//遍历排序后的集合
    		for(JSONObject j : jsonValues) {
    			sortedMap = new TreeMap<String, Object>();
    			for (Entry<String, Object> entry : j.entrySet()) {
    				
    	        	//System.out.println("排序后的集合:" + entry.getKey() + ":" + entry.getValue());
    	        	
    	        	if(null != entry.getValue() && !"".equals(entry.getValue())) {
    	        		
    	        		sortedMap.put(entry.getKey(), entry.getValue());
    	            }
    			}
        		s = splicingSign("UTF-8", sortedMap , set);
        		sbuffer.append(s);
    		}
    		//System.out.println("List拼接的原串:" + sbuffer.toString());
    		return sbuffer.toString();
        }
        
        return sbuffer.toString();
	    
	}
}
