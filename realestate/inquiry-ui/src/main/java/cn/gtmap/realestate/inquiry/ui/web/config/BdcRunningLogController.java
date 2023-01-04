package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.inquiry.ui.config.ElasticLowerClient;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.3, 2022/7/6
 * @description 运行日志Controller
 */
@Controller
@RequestMapping("/running/log")
public class BdcRunningLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcRunningLogController.class);

    @Autowired(required = false)
    private ElasticLowerClient elasticLowerClient;

    /**
     * 运行日志页面地址
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String logView() {
        return "/view/log/runninglog.html";
    }

    /**
     * 调用es 查询日志内容
     * @param queryStr  查询的参数
     * @param index  es 索引
     * @param size  分页大小
     * @param from  从第几条数据开始
     * @return 查询出来的数据
     */
    @ResponseBody
    @RequestMapping("/query")
    public String query(@RequestBody String queryStr, String index, String size, String from) {
        String message = "";
        String indexStr = "";
        try {
            //检查ES索引是否存在
            String[] indexs = index.split(",");
            List<String> reindexs = elasticLowerClient.getExistIndices(indexs);
            if(!CollectionUtils.isEmpty(reindexs)){
                StringBuilder stringBuilder = new StringBuilder();
                for (String reindex : reindexs){
                    stringBuilder.append(reindex).append(",");
                }
                if(StringUtils.isNotBlank(stringBuilder)){
                    indexStr = stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
                }
            }
            if ("".equals(indexStr)) {
                return message;
            }
            String url = "/" + indexStr + "/_search?from=" + from + "&size=" + size;
            return elasticLowerClient.get(url, queryStr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * 获取服务器所有内容
     * @param index 索引
     */
    @ResponseBody
    @RequestMapping("/getServerInfo")
    public String query(String index) {
        String res = elasticLowerClient.cat(index);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(res.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                String aa = br.readLine();
                if (StringUtils.isEmpty(aa)) {
                    break;
                }
                list.add(aa);
            }
            List<Map<String, String>> listMap = new ArrayList<>();
            if (list.size() > 0) {
                String[] title = list.get(0).split("\\s+");
                for (int i = 1; i < list.size(); i++) {
                    String[] values = list.get(i).split("\\s+");
                    Map<String, String> map = new HashMap<>();
                    for (int j = 0; j < title.length; j++) {
                        map.put(title[j], values[j]);
                    }
                    listMap.add(map);
                }
            }
            return JSON.toJSONString(listMap);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return "";
    }

    /**
     * 根据索引删除es日志内容
     * @param index  es 索引
     * @param adminPassWord 密码
     */
    @ResponseBody
    @RequestMapping("/deleteIndex")
    public Map<String, Object> deleteIndex(String index, String adminPassWord) {
        Map<String, Object> map = new HashMap<>();
        boolean re = elasticLowerClient.deleteIndex(index);
        map.put("acknowledged", re);
        return map;
    }

}
