package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzTokenMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzTokenService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/22
 * @description token实现类
 */
@Service
public class BdcDzzzTokenServiceImpl implements BdcDzzzTokenService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzTokenServiceImpl.class);
    @Resource
    private BdcDzzzTokenMapper bdcDzzzTokenMapper;
    @Autowired
    private BdcDzzzService bdcDzzzService;

    @Override
    public DzzzResponseModel addDzzzToken(BdcDzzzTokenDo bdcDzzzTokenDo) {
        if (null != bdcDzzzTokenDo && StringUtils.isNotBlank(bdcDzzzTokenDo.getYymc())) {
            BdcDzzzTokenDo tokenDo = bdcDzzzTokenMapper.queryTokenByTokenName(bdcDzzzTokenDo.getYymc().trim());
            if (null != tokenDo) {
                return bdcDzzzService.dzzzResponseFalse("该应用名称已存在！", null);
            }
            bdcDzzzTokenDo.setYyid(UUIDGenerator.generate());
            int result = 0;
            try {
                result = bdcDzzzTokenMapper.insertBdcDzzzToken(bdcDzzzTokenDo);
            } catch (Exception e) {
                logger.error("BdcDzzzTokenServiceImpl:getToken", e);
            }
            if (result != 0) {
                return bdcDzzzService.dzzzResponseSuccess("应用增加成功", null);
            }
        }
        return bdcDzzzService.dzzzResponseFalse("应用增加失败", null);
    }

    @Override
    public int deleteTokenByTokenId(String yyid) {
        return bdcDzzzTokenMapper.deleteTokenByTokenId(yyid);
    }

    @Override
    public BdcDzzzTokenDo queryBdcDzzzToken(String yyid) {
        return bdcDzzzTokenMapper.queryBdcDzzzToken(yyid);
    }

    @Override
    public BdcDzzzTokenDo queryTokenByTokenName(String yymc) {
        return bdcDzzzTokenMapper.queryTokenByTokenName(yymc);
    }

    @Override
    public DzzzResponseModel updateBdcDzzzTokenById(BdcDzzzTokenDo bdcDzzzTokenDo) {
        int result = bdcDzzzTokenMapper.updateBdcDzzzTokenById(bdcDzzzTokenDo);
        if (1 == result) {
            return bdcDzzzService.dzzzResponseSuccess("应用修改成功", null);
        }
        return bdcDzzzService.dzzzResponseFalse("应用修改失败", null);
    }
}
