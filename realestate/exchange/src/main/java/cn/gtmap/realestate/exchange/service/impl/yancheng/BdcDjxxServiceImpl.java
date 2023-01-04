package cn.gtmap.realestate.exchange.service.impl.yancheng;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmAndQlrDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsXmAndQlrQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxReqDto;
import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxResDto;
import cn.gtmap.realestate.exchange.service.yancheng.BdcDjxxService;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BdcDjxxServiceImpl implements BdcDjxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjxxServiceImpl.class);


    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据身份证号查询不动产登记信息
     * @Date 2022/5/12 16:16
     **/
    @Override
    public BdcDjxxResDto listDjxx(BdcDjxxReqDto req) {
        if (StrUtil.isEmpty(req.getIdnum())) {
            throw new AppException(9999, "身份证参数不能为空");
        }
        // 获取权利人
        List<BdcZsXmAndQlrDTO> list = bdcZsFeignService.listDjxx(buildQQ(req.getBdcdyh(), req.getQlrmc(), req.getIdnum()));
        LOGGER.info("根据身份证号查询不动产登记信息feign返回的消息为{}", list);
        BdcDjxxResDto.BdcDjxxDataDto dataDto = BdcDjxxResDto.BdcDjxxDataDto.buildList(list);
        // 证件转换
        getzsData(dataDto);
        // 权利类型字典值转化
        converQllx(dataDto);
        return BdcDjxxResDto.buildSuccess(dataDto);
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //构建查询条件
     * @Date 2022/5/19 10:24
     **/
    private BdcZsXmAndQlrQO buildQQ(String bdcdyh, String qlrmc, String idNum) {
        BdcZsXmAndQlrQO qo = new BdcZsXmAndQlrQO();
        qo.setBdcdyh(bdcdyh);
        qo.setQlrmc(qlrmc);
        qo.setSfzh(idNum);
        return qo;
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //权利类型字段转化
     * @Date 2022/5/12 16:14
     **/
    private void converQllx(BdcDjxxResDto.BdcDjxxDataDto dataDto){
        List<Map> zdMapList = bdcZdFeignService.queryBdcZd("qllx");
        if (CollectionUtils.isEmpty(dataDto.getList())) {
            return;
        }
        for (BdcDjxxResDto.BdcDjxxDataItemDto item : dataDto.getList()) {
            if (StrUtil.isNotEmpty(item.getQllx())) {
                for (Map map : zdMapList) {
                    if (StrUtil.equals(item.getQllx(), MapUtils.getString(map, "DM"))) {
                        item.setQllx(map.get("MC").toString());
                    }
                }
            }
        }
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description // 转化json为实体类
     * @Date 2022/5/12 16:15
     **/
    private void getzsData(BdcDjxxResDto.BdcDjxxDataDto dataDto) {
        if (CollectionUtils.isEmpty(dataDto.getList())) {
            return;
        }
        for (BdcDjxxResDto.BdcDjxxDataItemDto dto : dataDto.getList()) {
            String key = dto.getStorageId();
            if (Objects.isNull(key)) {
                continue;
            }
            BaseResultDto baseResultDto = storageClient.downloadBase64(key);
            if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                if (baseResultDto.getMsg().startsWith("data:")) {
                    dto.setFileData(StrUtil.subAfter(baseResultDto.getMsg(),";base64,",true));
                    dto.setFileType("pdf");
                }
            }
        }
    }
}
