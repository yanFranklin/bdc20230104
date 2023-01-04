package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzcxj.ChangzhouDjbResponseModel;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzcxj.ChangzhouDjbplResponseData;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.qo.BdcDjbQO;
import cn.gtmap.realestate.exchange.service.inf.changzhou.ChangZhouZzcxjService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/4/15
 * @description 常州自助查询机，查询登记簿信息
 */
@Service
public class ChangZhouZzcxjserviceImpl implements ChangZhouZzcxjService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangZhouZzcxjService.class);

    private static final String CXLY_ZZCXJ = "自助查询机";

    private static final String PROJECT_PATH = "/rest/v1.0/print/";

    @Value("${url.acceptUrl:}")
    private String acceptUrl;

    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcZsxmFeignService bdcZsxmFeignService;

    /**
     * 查询登记簿信息
     *
     * @param bdcDjbQO 请求参数
     * @return 登记簿信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @Override
    public ChangzhouDjbResponseModel<List<ChangzhouDjbplResponseData>> queryDjbxx(BdcDjbQO bdcDjbQO) {
        if (bdcDjbQO == null || StringUtils.isBlank(bdcDjbQO.getQlrmc()) || StringUtils.isBlank(bdcDjbQO.getQlrzjh())) {
            LOGGER.error("查询登记簿信息,缺少权利人名称或权利人证件号！");
            return ChangzhouDjbResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "缺少权利人名称或权利人证件号", null);
        }
        LOGGER.info("常州自助查询机查询登记簿信息,查询参数: {}", bdcDjbQO.toString());
        List<BdcZsDO> bdcZsDOS = bdcdjMapper.getZsByQlr(bdcDjbQO);
        List<ChangzhouDjbplResponseData> responseDataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
            for (BdcZsDO bdcZsDO : bdcZsDOS) {
                ChangzhouDjbplResponseData responseData = new ChangzhouDjbplResponseData();
                List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
                if (CollectionUtils.isEmpty(bdcXmDOList)) {
                    LOGGER.info("该证书关联的不动产项目不存在，zsid：{}", bdcZsDO.getZsid());
                    continue;
                }
                List<String> xmidList = bdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                List<String> bdcdyhList = bdcXmDOList.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
                // 查询来源
                responseData.setCxly(CXLY_ZZCXJ);
                // 行政区，区县代码
                if (bdcZsDO.getQxdm() != null) {
                    responseData.setXzq(zdDmToMc(bdcZsDO.getQxdm(), "qx"));
                }
                // 坐落,不动产单元号,多个加“等”
                responseData.setBdcdyh(bdcZsDO.getBdcdyh());
                responseData.setZl(bdcZsDO.getZl());
                // 土地信息
                List<BdcDjbTdxxDTO> fwtdxxList = bdcdjMapper.getFwTdxxByXmid(xmidList);
                List<BdcDjbTdxxDTO> tdxxList = bdcdjMapper.getTdxxByXmid(xmidList);
                if (CollectionUtils.isNotEmpty(fwtdxxList)) {
                    BeanUtils.copyProperties(fwtdxxList.get(0), responseData);
                } else if (CollectionUtils.isNotEmpty(tdxxList)) {
                    BeanUtils.copyProperties(tdxxList.get(0), responseData);
                }
                // 房屋信息
                List<BdcDjbFwxxDTO> fwxxList = bdcdjMapper.getFwxxByXmid(xmidList);
                responseData.setFwxx(fwxxList);
                // 权属状况--权利人信息
                List<BdcDjbQlrxxDTO> qlrxxList = bdcdjMapper.getQlrByZsid(bdcZsDO.getZsid());
                responseData.setQlrxx(qlrxxList);
                // 抵押权信息
                List<BdcDjbDyxxDTO> dyxxList = bdcdjMapper.queryDyaqByDyh(bdcdyhList);
                responseData.setDyxx(dyxxList);
                // 居住权信息
                List<BdcDjbJzxxDTO> jzxxList = bdcdjMapper.queryBdcJzqByDyh(bdcdyhList);
                responseData.setJzxx(jzxxList);
                // 限制权信息
                List<BdcDjbXzxxDTO> xzxxList = bdcdjMapper.queryBdcCf(bdcdyhList);
                responseData.setXzxx(xzxxList);
                // 征收冻结信息（冻结单位，冻结时间） 需要新增字段
                List<BdcDjbZsdjxxDTO> zsdjxxList = bdcdjMapper.queryBdcZsdj(bdcZsDO.getZsid(), bdcdyhList);
                responseData.setZsdjxx(zsdjxxList);
                // 附记
                String fj = bdcdjMapper.queryFjByXmid(xmidList.get(0));
                responseData.setFj(fj);
                // 交易查询二维码
                String mainPath = acceptUrl + PROJECT_PATH;
                String czjycxewm = mainPath + "jycx/" + bdcXmDOList.get(0).getXmid() + "?dylx=" + "bdcdjbcxbdcqz";
                responseData.setJycx(czjycxewm);
                // 查询时间,查档人
                responseData.setCxsj(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                responseData.setCdr(bdcDjbQO.getQlrmc());
                responseDataList.add(responseData);
            }
            return ChangzhouDjbResponseModel.ok(Constants.RESPONSE_SUCCESS_CODE, "success", responseDataList);
        } else {
            return ChangzhouDjbResponseModel.ok(Constants.RESPONSE_SUCCESS_CODE, "无数据", null);
        }

    }

    private String zdDmToMc(String dm, String zdmc) {
        String mc = "";
        List<Map> zdList = bdcZdFeignService.queryBdcZd(zdmc);
        if (CollectionUtils.isNotEmpty(zdList)) {
            mc = StringToolUtils.convertBeanPropertyValueOfZdByString(dm, zdList);
        }
        return mc;
    }
}
