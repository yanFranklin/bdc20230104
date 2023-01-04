package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.service.BdcYzhSymxService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/10
 * @description 印制号使用明细管理服务
 */
@Service
public class BdcYzhSymxServiceImpl implements BdcYzhSymxService {
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    EntityMapper entityMapper;


    /**
     * @param bdcYzhSyqkQO 印制号使用情况信息
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存印制号使用明细
     */
    @Override
    public BdcYzhsymxDO insertBdcYzhSymx(BdcYzhSyqkQO bdcYzhSyqkQO) {
        if (StringUtils.isBlank(bdcYzhSyqkQO.getYzhid())) {
            throw new MissingArgumentException("生成印制号使用明细，缺少yzhid字段信息！");
        }
        if (StringUtils.isBlank(bdcYzhSyqkQO.getZsid())) {
            throw new MissingArgumentException("生成印制号使用明细，缺少zsid值！");
        }

        String slbh = bdcYzhSyqkQO.getSlbh();
        String sybmmc = bdcYzhSyqkQO.getSybmmc();
        String syr = bdcYzhSyqkQO.getSyr();
        String syrid = bdcYzhSyqkQO.getSyrid();

        // 印制号作废需要保存当前项目的受理编号 added by zhuyong 20190727
        // 无论什么状态下都保存slbh modified by cyc 2019-11-17
        if (StringUtils.isBlank(slbh)) {
            List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(bdcYzhSyqkQO.getZsid());
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
                slbh = (bdcXmDOList.get(0).getSlbh());
            }
        }

        // added by cyc at 2019-07-19新增使用部门和使用人
        if (StringUtils.isBlank(syr)) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            syr = userDto.getAlias();
            syrid = userDto.getId();
        }
        // 使用部门名称
        if (StringUtils.isBlank(sybmmc)) {
            sybmmc = userManagerUtils.getOrganizationByUserName(userManagerUtils.getCurrentUserName());
        }

        BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
        bdcYzhsymxDO.setSlbh(slbh);
        bdcYzhsymxDO.setSybmmc(sybmmc);
        bdcYzhsymxDO.setSyr(syr);
        bdcYzhsymxDO.setSyrid(syrid);
        bdcYzhsymxDO.setSyyy(bdcYzhSyqkQO.getSyyy());
        bdcYzhsymxDO.setSyqk(bdcYzhSyqkQO.getSyqk());
        bdcYzhsymxDO.setSysj(new Date());
        bdcYzhsymxDO.setYzhid(bdcYzhSyqkQO.getYzhid());
        bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
        int result = entityMapper.insertSelective(bdcYzhsymxDO);
        if (result > 0) {
            return bdcYzhsymxDO;
        }
        return null;
    }

    /**
     * @param yzhid 印制号ID
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询印制号的使用明细
     */
    @Override
    public List<BdcYzhsymxDO> queryBdcYzhsymx(String yzhid) {
        if (StringUtils.isBlank(yzhid)) {
            throw new MissingArgumentException("缺失印制号ID");
        }
        Example example = new Example(BdcYzhsymxDO.class);
        example.createCriteria().andEqualTo("yzhid", yzhid);
        List<BdcYzhsymxDO> bdcYzhsymxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(bdcYzhsymxDOList)) {
            return bdcYzhsymxDOList;
        }
        return new ArrayList<>();
    }
}
