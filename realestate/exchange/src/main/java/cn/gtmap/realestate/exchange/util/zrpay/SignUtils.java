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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 签名工具类
 */
public class SignUtils {

    @SuppressWarnings("rawtypes")
    public static String splicingSign(String characterEncoding, SortedMap<String, Object> parameters, Set<String> set) {
        StringBuffer sbuffer = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (set.contains(k)) {
                sbuffer.append(v);
            } else {
                if (!k.equals("SIGN_INF")
                        && !k.equals("Svc_Rsp_St")
                        && !k.equals("Svc_Rsp_CD")
                        && !k.equals("Rsp_Inf")
                ) {
                    sbuffer.append(k + "=" + v + "&");
                }
            }
        }
        return sbuffer.toString();
    }

    public static String createSign(String json, Set<String> set) {
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
        JSONObject jsonObject = JSONObject.parseObject(json);
        for (Entry<String, Object> entry : jsonObject.entrySet()) {

            if (null != entry.getValue()
                    && !"".equals(entry.getValue())
                    && !set.contains(entry.getKey())) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        }

        if (!set.isEmpty()) {
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String listKey = it.next();
                String listValue = processingSet(jsonObject, listKey);
                sortedMap.put(listKey, listValue);
            }
        }
        String sign = splicingSign("UTF-8", sortedMap, set);
        sign = sign.substring(0, sign.length() - 1);
        return sign;
    }

    public static String processingSet(JSONObject jsonObj, String listKey) {
        SortedMap<String, Object> sortedMap = null;
        Set<String> set = new HashSet<String>();
        StringBuffer sbuffer = new StringBuffer();
        String list = jsonObj.getString(listKey);
        if (list != null && list != "") {
            JSONArray jarr = JSONArray.parseArray(list);
            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jarr.size(); i++) {
                jsonValues.add(jarr.getJSONObject(i));
            }
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                public int compare(JSONObject a, JSONObject b) {
                    String valA = a.getString("SN");
                    String valB = b.getString("SN");
                    return valA.compareTo(valB);
                }
            });
            String s = "";
            for (JSONObject j : jsonValues) {
                sortedMap = new TreeMap<String, Object>();
                for (Entry<String, Object> entry : j.entrySet()) {
                    if (null != entry.getValue() && !"".equals(entry.getValue())) {

                        sortedMap.put(entry.getKey(), entry.getValue());
                    }
                }
                s = splicingSign("UTF-8", sortedMap, set);
                sbuffer.append(s);
            }
            return sbuffer.toString();
        }
        return sbuffer.toString();
    }

}
