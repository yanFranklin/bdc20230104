package cn.gtmap.realestate.accept.service.impl.jyxx;

import cn.gtmap.realestate.accept.service.BdcSlJyxxAbstractService;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.getybahtxx.response.YbahtDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/3
 * @description 南通版本交易信息处理
 */
@Service
public class BdcSlJyxxAbstractServiceImpl_nantong implements BdcSlJyxxAbstractService {

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJyxxAbstractServiceImpl_nantong.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 是否调用交易附件
      */
    @Value("${nantong.jyfj:true}")
    private Boolean jyfj;

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(1);
        set.add(CommonConstantUtils.SYSTEM_VERSION_NT);
        return set;
    }

    @Override
    public void uploadJyFj(FcjyBaxxDTO fcjyBaxxDTO,String gzlslid){
        if(fcjyBaxxDTO != null &&fcjyBaxxDTO.getBdcSlJyxx() != null &&StringUtils.isNotBlank(fcjyBaxxDTO.getBdcSlJyxx().getHtbh()) &&StringUtils.isNotBlank(gzlslid) &&jyfj) {
            String htbh =fcjyBaxxDTO.getBdcSlJyxx().getHtbh();
            JSONObject json = new JSONObject();
            json.put("code", htbh);
            LOGGER.info("开始调用南通获取已备案合同exchange接口,接口的beanName：getYbaHtxx,接口的参数：{}", json);
            Object response = exchangeInterfaceFeignService.requestInterface("getYbaHtxx", json);
            if (response != null) {
                LOGGER.info("合同编号:{},调取获取已备案合同接口成功,返回结果：{}", htbh, response);
                JSONObject ybahtObject = JSONObject.parseObject(JSONObject.toJSONString(response));
                if (ybahtObject.get("code") != null && "1".equals(ybahtObject.get("code").toString()) &&ybahtObject.get("data") != null) {
                    JSONObject ybahtDTO = JSONObject.parseObject(JSONObject.toJSONString(ybahtObject.get("data")));
                    if(ybahtDTO.get("file_url") != null) {
                        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", htbh, "合同备案附件", CommonConstantUtils.WJHZ_PDF);
                        bdcPdfDTO.setPdfUrl(ybahtDTO.get("file_url").toString());
                        try {
                            bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                        } catch (Exception e) {
                            LOGGER.error("上传合同附件失败，异常信息" + e);
                            throw new AppException("上传合同附件失败");
                        }
                    }
                }
            }
        }

    }
}
