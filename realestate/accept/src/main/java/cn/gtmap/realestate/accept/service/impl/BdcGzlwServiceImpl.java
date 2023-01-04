package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.config.BfjyjfQllxZgzidConfig;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.service.BdcGzlwService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShLogDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZgzFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description 规则例外服务
 */
@Service
public class BdcGzlwServiceImpl implements BdcGzlwService {
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcBhService bdcBhService;
    @Autowired
    private BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    private BfjyjfQllxZgzidConfig bfjyjfQllxZgzidConfig;
    @Autowired
    private BdcGzZgzFeignService bdcGzZgzFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理编号生成版本
     */
    @Value("${slbhscfs.version:}")
    private String slbhgs;

    @Override
    public BdcGzlwShDO addShxxData(Map data, String slbh, String xmid) {
        // 创建规则例外审核信息
        BdcGzlwShDO bdcGzlwShDO = new BdcGzlwShDO();
        bdcGzlwShDO.setGzlwid(UUIDGenerator.generate());

        if (StringUtils.isBlank(MapUtils.getString(data, "bdcdyh"))) {
            throw new NullPointerException("不动产单元号为空");
        }
        if (StringUtils.isBlank(slbh)) {
            throw new NullPointerException("受理编号为空");
        }
        if (StringUtils.isBlank(xmid)) {
            throw new NullPointerException("项目id为空");
        }
        if (StringUtils.isBlank(MapUtils.getString(data, "gzid"))) {
            throw new NullPointerException("规则id为空");
        }
        if (StringUtils.isBlank(MapUtils.getString(data, "gzmc"))) {
            throw new NullPointerException("规则名称为空");
        }
        // 南通受理编号前要加不动产单元号特征码
        if (StringUtils.equals(slbhgs, Constants.VERSION_NT)) {
            String tzm =bdcBhService.queryTzmByBdcdyh(MapUtils.getString(data, "bdcdyh"));
            if(!slbh.contains(tzm)) {
                slbh = tzm + slbh;
            }
        }
        bdcGzlwShDO.setSlbh(slbh);
        bdcGzlwShDO.setXmid(xmid);
        bdcGzlwShDO.setBdcdyh(MapUtils.getString(data, "bdcdyh"));
        bdcGzlwShDO.setBdcqzh(MapUtils.getString(data, "bdcqzh"));
        bdcGzlwShDO.setZl(MapUtils.getString(data, "zl"));
        bdcGzlwShDO.setGzid(MapUtils.getString(data, "gzid"));
        bdcGzlwShDO.setGzmc(MapUtils.getString(data, "gzmc"));
        bdcGzlwShDO.setShzt(CommonConstantUtils.GZLW_SHZT_XJ);
        bdcGzlwShDO.setCjr(userManagerUtils.getCurrentUserName());
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null){
            bdcGzlwShDO.setCjrmc(userDto.getAlias());
        }
        bdcGzlwShDO.setLwyy(MapUtils.getString(data, "lwyy"));
        bdcGzlwShDO.setCjsj(new Date());
        bdcGzlwFeignService.addShxxData(bdcGzlwShDO);

        String bdcGzlwSh = JSON.toJSONString(bdcGzlwShDO);
        String encrypt = RSAEncryptUtils.encrypt(bdcGzlwSh);
        List<BdcGzlwShLogDO> bdcGzlwShLogDOList = new ArrayList<>();
        BdcGzlwShLogDO bdcGzlwShLogDO = new BdcGzlwShLogDO();
        bdcGzlwShLogDO.setGzlwlogid(UUIDGenerator.generate());
        bdcGzlwShLogDO.setScrq(new Date());
        bdcGzlwShLogDO.setCz(encrypt);
        bdcGzlwShLogDOList.add(bdcGzlwShLogDO);
        bdcGzlwFeignService.addBdcGzlwLog(bdcGzlwShLogDOList);
        return bdcGzlwShDO;
    }

    @Override
    public void addShxxDataWithoutSlbh(BdcCshSlxmDTO bdcCshSlxmDTO, String qllx) {
        Map<String, List<String>> qllxMap = bfjyjfQllxZgzidConfig.getQllxMap();

        if (bdcCshSlxmDTO != null && CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList())
                && CollectionUtils.isNotEmpty(qllxMap.get(qllx))) {
            for (String gzid : qllxMap.get(qllx)) {
                BdcGzZgzDTO bdcGzZgzDTO = bdcGzZgzFeignService.queryBdcGzZgzDTO(gzid);
                // 根据原项目id查询所有对应项目
                String yxmid = getYxmidByGzlslId(bdcCshSlxmDTO.getGzlslid());
                BdcXmDO yBdcXmDO = null;
                if(StringUtils.isNotBlank(yxmid)) {
                    BdcXmQO yBdcXmQO = new BdcXmQO();
                    yBdcXmQO.setXmid(yxmid);
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(yBdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        yBdcXmDO = bdcXmDOList.get(0);
                    }
                }
                if (yBdcXmDO == null) {
                    throw new AppException("不存在的不动产项目");
                }
                if (yBdcXmDO.getQllx() == null || StringUtils.isBlank(yBdcXmDO.getBdcdyh())) {
                    throw new AppException("不动产项目数据存在问题");
                }

                List<BdcXmDO> bdcXmDOListXzql = bdcGzlwFeignService.queryAllBdcXmXzql(yBdcXmDO);

                if (CollectionUtils.isEmpty(bdcXmDOListXzql)) {
                    throw new AppException("无法查询到对应权利数据");
                }

                List<BdcGzlwShDO> list = bdcGzlwFeignService.queryBdcGzlwSh(bdcCshSlxmDTO.getGzlslid());
                for (BdcXmDO bdcXmDO : bdcXmDOListXzql) {
                    if (bdcGzZgzDTO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                        for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcCshSlxmDTO.getBdcSlYwxxDTOList()) {
                            if (StringUtils.isNotBlank(bdcSlYwxxDTO.getBdcdyh())) {
                                //先判断之前的数据是否已经有改bdcdyh的例外，如果有先删除
                                deleteGzlwByBdcdyh(list, bdcSlYwxxDTO.getBdcdyh(), bdcXmDO.getXmid(), gzid);
                                // 创建规则例外审核信息
                                BdcGzlwShDO bdcGzlwShDO = new BdcGzlwShDO();
                                bdcGzlwShDO.setGzlwid(UUIDGenerator.generate());
                                bdcGzlwShDO.setXmid(bdcXmDO.getXmid());
                                bdcGzlwShDO.setBdcdyh(bdcSlYwxxDTO.getBdcdyh());
                                bdcGzlwShDO.setZl(bdcSlYwxxDTO.getZl());
                                bdcGzlwShDO.setGzid(gzid);
                                bdcGzlwShDO.setGzmc(bdcGzZgzDTO.getGzmc());
                                bdcGzlwShDO.setSlbh(CommonConstantUtils.BDC_GZ_SJL_RC_MRZ);
                                bdcGzlwShDO.setShzt(CommonConstantUtils.GZLW_SHZT_XJ);
                                bdcGzlwShDO.setCjr(userManagerUtils.getCurrentUserName());
                                UserDto userDto = userManagerUtils.getCurrentUser();
                                if (userDto != null) {
                                    bdcGzlwShDO.setCjrmc(userDto.getAlias());
                                }
                                bdcGzlwShDO.setCjsj(new Date());
                                bdcGzlwShDO.setGzlslid(bdcCshSlxmDTO.getGzlslid());
                                bdcGzlwFeignService.addShxxData(bdcGzlwShDO);

                                String bdcGzlwSh = JSON.toJSONString(bdcGzlwShDO);
                                String encrypt = RSAEncryptUtils.encrypt(bdcGzlwSh);
                                List<BdcGzlwShLogDO> bdcGzlwShLogDOList = new ArrayList<>();
                                BdcGzlwShLogDO bdcGzlwShLogDO = new BdcGzlwShLogDO();
                                bdcGzlwShLogDO.setGzlwlogid(UUIDGenerator.generate());
                                bdcGzlwShLogDO.setScrq(new Date());
                                bdcGzlwShLogDO.setCz(encrypt);
                                bdcGzlwShLogDOList.add(bdcGzlwShLogDO);
                                bdcGzlwFeignService.addBdcGzlwLog(bdcGzlwShLogDOList);
                            }
                        }
                    }
                }
            }
        }
    }

    private void deleteGzlwByBdcdyh(List<BdcGzlwShDO> list, String bdcdyh, String xmid, String gzid) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcGzlwShDO bdcGzlwShDO : list) {
                if (StringUtils.equals(bdcGzlwShDO.getBdcdyh(), bdcdyh)
                        && StringUtils.equals(bdcGzlwShDO.getXmid(), xmid)
                        && StringUtils.equals(bdcGzlwShDO.getGzid(), gzid)) {
                    bdcGzlwFeignService.deleteBdcGzlwSh(bdcGzlwShDO.getGzlwid());
                }
            }
        }
    }

    private String getYxmidByGzlslId(String gzlslid) {
        String yxmid = "";
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                    if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                        yxmid = bdcXmLsgxDO.getYxmid();
                    }
                }
            }
        }
        return yxmid;
    }
}
