package cn.gtmap.realestate.exchange.service.impl.xuancheng;

import cn.gtmap.realestate.common.util.Base64Util;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.core.dto.xuancehng.sjcl.FjclDTO;
import cn.gtmap.realestate.exchange.core.dto.xuancehng.sjcl.FjxxHlwCxDTO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0  2022-12-19
 * @description 互联网请求附件材料
 */
@Service
public class HlwCxFhtzdtServiceImpl {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HlwCxFhtzdtServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @description 户室图
     * @Date 2022/12/19
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    public FjxxHlwCxDTO queryFjxxFht(String bdcdyh) throws Exception {
        FjxxHlwCxDTO fjxxHlwCxDTO = new FjxxHlwCxDTO();
        List<FjclDTO> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("Method", 0);
        String beanName = "xcDchyFcfht";
        if (StringUtils.isNotBlank(bdcdyh)) {
            map.put("Bdcdyh", bdcdyh);
        }
        Object object = exchangeBeanRequestService.request(beanName, map);
        String base64 = "";
        String fileName = "";
        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            List<Map<String, String>> paths = JSONObject.parseObject(JSONObject.toJSONString(jsonObj.get("Paths")), List.class);
            LOGGER.info("不动产单元号，获取数据:{},{}", bdcdyh, paths);
            if (CollectionUtils.isNotEmpty(paths)) {
                for (Map<String, String> mapPath : paths) {
                    FjclDTO fjclDTO = new FjclDTO();
                    String url = mapPath.get("Url");
                    fileName = url.substring(url.lastIndexOf("/") + 1);
                    byte[] data = Base64Utils.getFile(url);
                    base64 = Base64Util.encode(data);
                    fjclDTO.setBase64(base64);
                    fjclDTO.setFileName(fileName);
                    list.add(fjclDTO);
                }
                fjxxHlwCxDTO.setCode("1");
                fjxxHlwCxDTO.setMsg("查询成功");
            }
        }
        fjxxHlwCxDTO.setFileList(list);
        return fjxxHlwCxDTO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @description 宗地图
     * @Date 2022/12/19
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    public FjxxHlwCxDTO queryFjxxZdt(String bdcdyh) {
        FjxxHlwCxDTO fjxxHlwCxDTO = new FjxxHlwCxDTO();
        List<FjclDTO> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("Method", 2);
        String beanName = "xcDchyZddcb";
        if (StringUtils.isNotBlank(bdcdyh)) {
            map.put("Bdcdyh", bdcdyh);
        }
        Object object = exchangeBeanRequestService.request(beanName, map);
        String base64 = "";
        String fileName = "";
        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            List<Map<String, String>> paths = JSONObject.parseObject(JSONObject.toJSONString(jsonObj.get("Paths")), List.class);
            LOGGER.info("不动产单元号，获取数据:{},{}", bdcdyh, paths);
            if (CollectionUtils.isNotEmpty(paths)) {
                for (Map<String, String> mapPath : paths) {
                    FjclDTO fjclDTO = new FjclDTO();
                    String url = mapPath.get("Url");
                    fileName = url.substring(url.lastIndexOf("/") + 1);
                    byte[] data = Base64Utils.getFile(url);
                    base64 = Base64Util.encode(data);
                    fjclDTO.setBase64(base64);
                    fjclDTO.setFileName(fileName);
                    list.add(fjclDTO);
                }
                fjxxHlwCxDTO.setCode("1");
                fjxxHlwCxDTO.setMsg("查询成功");
            }
        }
        fjxxHlwCxDTO.setFileList(list);
        return fjxxHlwCxDTO;
    }
}
