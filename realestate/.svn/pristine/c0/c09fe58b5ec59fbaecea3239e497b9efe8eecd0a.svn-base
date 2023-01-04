package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcGgptxxService;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.GgptxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.GgptxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
@Service
public class BdcGgptxxServiceImpl implements BdcGgptxxService {
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Override
    public List<GgptxxDTO> queryGgptxx(String param) {
        List<GgptxxDTO> list = new ArrayList<>();
        if (StringUtils.isBlank(param)){
            throw new MissingArgumentException("查询条件为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(param);
        GgptxxQO ggptxxQO = JSONObject.parseObject(String.valueOf(jsonObject.get("obj")),GgptxxQO.class);
        if (StringUtils.isBlank(ggptxxQO.getAppKey())){
            throw new MissingArgumentException("文件类型不能为空");
        }
        String beanName = "ggpt_gcjsxmcx";
        Object object = exchangeInterfaceFeignService.requestInterface(beanName, ggptxxQO);

        if (null != object){
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            list =JSONObject.parseArray(String.valueOf(jsonObj.get("content")), GgptxxDTO.class);
        }
        return list;
    }

    @Override
    public Object downloadGgfj(String fjid, String gzlslid) throws IOException {
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setFoldName("工改平台附件");
        String dyfjid = null;

        if (StringUtils.isNotBlank(gzlslid)){
            bdcPdfDTO.setGzlslid(gzlslid);
        }else {
            throw new MissingArgumentException("工作流实例id为空");
        }

        if (StringUtils.isBlank(fjid)){
            throw new MissingArgumentException("附件id信息不能为空");
        }

        String beanName = "ggpt_gcjsfjcx";
        Map paramMap = new HashMap(1);
        paramMap.put("fjid",fjid);

        Object object = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(String.valueOf(object));
            String base64 = String.valueOf(jsonObj.get("content"));
            if (StringUtils.isNotBlank(base64)){
                bdcPdfDTO.setBase64str(base64);
                dyfjid = bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
            }
        }
        if (StringUtils.isBlank(dyfjid)){
            throw new MissingArgumentException("上传至大云附件失败");
        }
        return dyfjid;
    }
}
