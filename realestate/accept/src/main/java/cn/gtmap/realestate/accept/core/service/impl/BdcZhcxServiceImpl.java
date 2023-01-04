package cn.gtmap.realestate.accept.core.service.impl;


import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.mapper.BdcZhcxLogMapper;
import cn.gtmap.realestate.accept.core.service.BdcZhcxService;
import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description 不动产综合查询服务
 */
@Service
public class BdcZhcxServiceImpl implements BdcZhcxService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcZhcxLogMapper bdcZhcxLogMapper;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    /**
     * @param bdcZhcxLogDO
     * @description 综合查询
     */
    @Override
    public void insertZhcxLog(BdcZhcxLogDO bdcZhcxLogDO) {
        if(null == bdcZhcxLogDO) {
            throw new MissingArgumentException("保存综合查询台账查询和打印操作日志：未定义日志信息");
        }

        if(StringUtils.isBlank(bdcZhcxLogDO.getRzid())) {
            bdcZhcxLogDO.setRzid(UUIDGenerator.generate16()); //设置UUID为主键id；
        }

        if(StringUtils.isAnyBlank(bdcZhcxLogDO.getCxr(), bdcZhcxLogDO.getCxzh())) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto) {
                bdcZhcxLogDO.setCxr(userDto.getAlias());
                bdcZhcxLogDO.setCxzh(userDto.getUsername());
                bdcZhcxLogDO.setCxrid(userDto.getId());

                List<OrganizationDto> orgs = userDto.getOrgRecordList();
                if(CollectionUtils.isNotEmpty(orgs) && null != orgs.get(0)) {
                    bdcZhcxLogDO.setSzbm(orgs.get(0).getName());
                    bdcZhcxLogDO.setSzbmdm(orgs.get(0).getCode());
                }
            }
        }

        bdcZhcxLogDO.setCzsj(new Date());

        entityMapper.insertSelective(bdcZhcxLogDO);
    }

    @Override
    public List<BdcGzltjXmxxDTO> countRyPrintxx(GzltjQO gzltjQO) {
        return bdcZhcxLogMapper.countZhcxRyPrintxx(gzltjQO);
    }

    @Override
    public List<BdcGzltjXmxxDTO> countRyZhcxxx(GzltjQO gzltjQO) {
        return bdcZhcxLogMapper.countZhcxRyCxxx(gzltjQO);
    }

    @Override
    public List<BdcGzltjXmxxDTO> countBmPrintxx(GzltjQO gzltjQO) {
        return bdcZhcxLogMapper.countZhcxBmPrintxx(gzltjQO);
    }

    @Override
    public List<BdcGzltjXmxxDTO> countBmZhcxxx(GzltjQO gzltjQO) {
        return bdcZhcxLogMapper.countZhcxBmCxxx(gzltjQO);
    }

    @Override
    public Integer countYbtjZhcx(BdcYbtjQO bdcYbtjQO) {
        return bdcZhcxLogMapper.countYbtjZhcx(bdcYbtjQO);
    }

}
