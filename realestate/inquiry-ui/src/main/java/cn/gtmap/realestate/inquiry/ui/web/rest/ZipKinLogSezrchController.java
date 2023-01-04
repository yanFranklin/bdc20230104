package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.util.ReadFileUtils;
import cn.gtmap.realestate.common.util.TokenUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述：查询日志
 *
 * @author EO
 * @date 2021/8/17 10:57
 */
@RestController
public class ZipKinLogSezrchController{
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipKinLogSezrchController.class);

    @Autowired
    private TokenUtils tokenUtils;

    @Value("${zipkin.log.ip:192.168.2.99:8003}")
    private String appLogIp;

    @Value("${zipkin.log.path:/home/realestate/hefei/realestate-inquiry-ui-3.1.0/logs/}")
    private String logPath;

    /**
     * 获取app日志所在的服务器
     *
     * @return
     */
    @GetMapping("/getAppLogIp")
    public String getAppLogIp(){
        return appLogIp;
    }

    @PostMapping("/download")
    public void downLoadLogTxt(HttpServletRequest request,HttpServletResponse response,@RequestParam("zipData") String zipData) throws Exception{
        LOGGER.info("进行下载的数据" + zipData);
        // 获取时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDHHmmSS");
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition","attachment;filename=" + simpleDateFormat.format(new Date()) + ".txt");
        // 得到输出流
        ServletOutputStream out = response.getOutputStream();
//        Date date = new Date("timestamp").toLocaleString();
        // 获取app 前台如果和后台不在一个服务器，则去后台app服务器取日志
        Map<String,List<String>> map = new LinkedHashMap<>();
        JSONArray array = JSONArray.parseArray(zipData);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date date = new Date();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.size(); i++){
            JSONObject object = array.getJSONObject(i);
            List<String> tempList = new ArrayList<>();
            // 如果只有一条 取一条数据
            if (object.getJSONArray("annotations") == null){
                JSONArray binArray = object.getJSONArray("binaryAnnotations");
                tempList.add(object.getString("timestamp").substring(0,object.getString("timestamp").length() - 3));
                tempList.add(binArray.getJSONObject(0).getJSONObject("endpoint").getString("ipv4"));
                tempList.add(binArray.getJSONObject(0).getJSONObject("endpoint").getString("port"));
                map.put(binArray.getJSONObject(0).getJSONObject("endpoint").getString("serviceName"),
                        tempList);
                break;
            }
            JSONArray binArr = object.getJSONArray("annotations");
            for (int j = 0; j < binArr.size(); j++){
                JSONObject annAry = binArr.getJSONObject(j);
//                String timestamp = endpoint.getString("timestamp");
                stringBuilder.append(annAry.getString("timestamp"));
                if (!map.containsKey(annAry.getJSONObject("endpoint").getString("serviceName"))){
                    tempList.add(stringBuilder.substring(0,stringBuilder.length() - 3));
                    tempList.add(annAry.getJSONObject("endpoint").getString("ipv4"));
                    tempList.add(annAry.getJSONObject("endpoint").getString("port"));
                    map.put(annAry.getJSONObject("endpoint").getString("serviceName"),tempList);
                }
                stringBuilder.delete(0,stringBuilder.length());

            }

        }

        Iterator<Map.Entry<String,List<String>>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,List<String>> entry = it.next();
            if (StringUtils.isEmpty(getAPPPath(entry.getKey().replace("-app","")))){
                it.remove();//使用迭代器的remove()方法删除元素
            }
        }
        LOGGER.info("筛选后的数据:" + JSONObject.toJSONString(map));
        // 创建临时文件以供下载
        File tempTxt = File.createTempFile("log",".txt");
        for (Map.Entry<String,List<String>> entry : map.entrySet()){
            String fileName = entry.getKey().replace("-app","") + ReadFileUtils.getLogFile(entry.getValue().get(0));
            Long tempTime = Long.valueOf(entry.getValue().get(0));
//            if (fileName.contains("inquiry-ui")){
//                readLogFile(tempTxt,String.valueOf(tempTime),String.valueOf(tempTime + 30000),logPath + fileName,entry.getKey(),response);
//            }else{
//                List<String> logList = getAppLog(entry.getKey().replace("-app",""),fileName,String.valueOf(tempTime),String.valueOf(tempTime + 30000));
            List<String> logList = sendGet(entry.getKey().replace("-app",""),fileName,String.valueOf(tempTime),String.valueOf(tempTime + 60000),entry.getValue().get(1) + ":" + entry.getValue().get(2));
            for (String log : logList){
                out.write(log.getBytes(StandardCharsets.UTF_8));
            }

        }
        // 通过file对象得到输入流
        FileInputStream in = new FileInputStream(tempTxt);
        byte[] car = new byte[1024];
        int len = 0;
        while ((len = in.read(car)) != -1){
            out.write(car,0,len);
        }
        // 关闭流
        in.close();
        out.close();

    }

    private String getAPPPath(String app){
        switch (app){
            case "accept-ui":
                return "/realestate-accept-ui";
            case "accept":
                return "/realestate-accept";
            case "building-ui":
                return "/building-ui";
            case "building":
                return "/building";
            case "certificate":
                return "/realestate-certificate";
            case "check":
                return "/realestate-check";
            case "realestate-config-ui":
                return "/realestate-config-ui";
            case "realestate-config":
                return "/realestate-config";
            case "e-certificate":
                return "/realestate-e-certificate";
            case "engine-ui":
                return "/realestate-engine-ui";
            case "engine":
                return "/realestate-engine";
            case "etl":
                return "/realestate-etl";
            case "exchange":
                return "/realestate-exchange";
            case "init":
                return "/init";
            case "inquiry":
                return "/realestate-inquiry";
            case "inquiry-ui":
                return "/realestate-inquiry-ui";
            case "portal-ui":
                return "/realestate-portal-ui";
            case "realestate-public":
                return "/realestate-public";
            case "register-ui":
                return "/realestate-register-ui";
            case "register":
                return "/realestate-register";
            case "rules":
                return "/realestate-rules";
            default:
                return null;
        }
    }

    // HTTP GET请求
    private List<String> sendGet(String app,String fileName,String start,String end,String appIp) throws Exception{
        // realestate-zipkin/rest/v1.0/download
        List list = new ArrayList();

        // eg:"http://192.168.50.24:8599/realestate-inquiry/zipkin/rest/v1.0/download?app=app&fileName=fileName&start=start&end=end&access_token=tokenUtils.getAccessToken();
        String url = "http://" + appIp + getAPPPath(app) + "/rest/v1.0/zipkin/download?app=" + app + "&fileName=" + fileName + "&start=" + start + "&end=" + end + "&access_token=" + tokenUtils.getAccessToken();
        LOGGER.info("调用app的路径为:" + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        try{
            if (response.code() == 200){
                // 这里需要注意，response.body().string()是获取返回的结果，此句话只能调用一次，再次调用获得不到结果。
                list = JSONObject.parseObject(response.body().string(),List.class);
                LOGGER.info("调用接口返回:" + JSONObject.toJSONString(list));
            }
        }catch (Exception e){
            LOGGER.info("日志下载失败:" + JSONObject.toJSONString(list));
            list.add("=============" + app + "下载失败,下载路径为" + url + ",请查看具体路径是否配置=============================\r\n");
        }

        return list.isEmpty() ? Collections.EMPTY_LIST : list;
    }
}