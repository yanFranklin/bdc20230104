package cn.gtmap.realestate.exchange.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-18
 * @description （开发自用）根据JSON 输出实体类属性的 工具方法
 */
public class GenerateEntityByJSONUtil {


    public static void main2(String[] args) {
        String json = "{\n" +
                "  \"data\": {\n" +
                "    \"sfcj\": \"是否创建项目0，1\",\n" +
                "    \"sqdjlx\": \"20\",\n" +
                "    \"ywh\": \"20190719131108630986\",\n" +
                "    \"yybmbm\": \"1\",\n" +
                "    \"zsly\": \"2\",\n" +
                "    \"bdcdyxx\": [\n" +
                "      {\n" +
                "        \"mj\": \"138.37\",\n" +
                "        \"sfcf\": \"1\",\n" +
                "        \"sfdy\": \"1\",\n" +
                "        \"zl\": \"滨湖区西藏路1471号滨湖名邸5幢2303\",\n" +
                "        \"bdcdybh\": \"12306811\",\n" +
                "        \"bdcdyh\": \"340111306009GB00032F00050045\",\n" +
                "        \"fczh\": \"房权证合产字第8110263388号\",\n" +
                "        \"yxmid\": \"\",\n" +
                "        \"tdzh\": \"土地证第8110263388号\",\n" +
                "        \"tdxmid\": \"\",\n" +
                "        \"cqxx\": {\n" +
                "          \"htbh\": \"1563186436646\",\n" +
                "          \"xmid\": \"1563186436646\",\n" +
                "          \"htqdrq\": \"2019-07-15\",\n" +
                "          \"htzt\": \"0\",\n" +
                "          \"jyjg\": \"2200000.0\",\n" +
                "          \"barq\": \"2019-07-19\",\n" +
                "          \"xlsqdjlx\": \"11\"\n" +
                "        },\n" +
                "        \"dyxx\": {\n" +
                "          \"bdbzzqse\": \"121212.0\",\n" +
                "          \"xmid\": \"1563186436646\",\n" +
                "          \"dyfs\": \"0\",\n" +
                "          \"zwlxjsqx\": \"2029-07-01\",\n" +
                "          \"zwlxksqx\": \"2019-07-01\",\n" +
                "          \"xlsqdjlx\": \"11\",\n" +
                "          \"qlrxx\": [\n" +
                "            {\n" +
                "              \"qlrlx\": \"qlr\",\n" +
                "              \"qlrlxdh\": \"17755138626\",\n" +
                "              \"qlrmc\": \"银行\",\n" +
                "              \"qlrsfzjzl\": \"1\",\n" +
                "              \"qlrzjh\": \"34012319850810624X\",\n" +
                "              \"gyfs\": \"\",\n" +
                "              \"gybl\": \"\",\n" +
                "              \"sh\": \"\",\n" +
                "              \"sfbdhj\":\"\",\n" +
                "              \"skjm\":\"\",\n" +
                "              \"fczh\":\"\",\n" +
                "              \"fwtc\":\"\",\n" +
                "              \"gybl\":\"\",\n" +
                "              \"hyzt\":\"\",\n" +
                "              \"jtcy\":\"\",\n" +
                "              \"qlrdlr\": \"张三\",\n" +
                "              \"qlrdlrzjzl\": \"1\",\n" +
                "              \"qlrdlrzjh\": \"34012319850810624X\",\n" +
                "              \"qlrdlrlxdh\": \"15950519301\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        String preFix = "InitZyDyRequest";
        String outerName = "DTO";
        console(json,preFix,outerName);
    }

    private static void console(String json,String classPreFix,String outerName){
        JSONObject jsonObject = JSONObject.parseObject(json);
        consoleClass(classPreFix,outerName, jsonObject);
    }

    private static String consoleClass(String preFix,String name,JSONObject jsonObject){
        StringBuilder sb = new StringBuilder();
        name = preFix + firstUpper(name);
        for(String key:jsonObject.keySet()){
            Object temp = jsonObject.get(key);
            if(temp instanceof JSONObject){
                String tempName = consoleClass(preFix,key,(JSONObject) temp);
                sb.append("private "+tempName+" "+key+";\n\n");
            }else if(temp instanceof JSONArray){
                JSONObject temp2 = ((JSONArray) temp).getJSONObject(0);
                String tempName = consoleClass(preFix,key,temp2);
                sb.append("private List<"+tempName+"> "+key+";\n\n");
            }else{
                sb.append("private String "+key+";\n\n");
            }
        }
        System.out.println("---------- "+name+" begin ----------");
        System.out.println(sb.toString());
        System.out.println("---------- "+name+" end ----------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return name;
    }

    public static String firstUpper(String str) {
        String first = str.substring(0,1);
        return first.toUpperCase() + str.substring(1, str.length());
    }
}
