package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcFdcq3GyxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/17.
 * @description
 */
@Service
public class BdcFdcq3GyxxServiceImpl implements BdcFdcq3GyxxService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * 通过权利id建筑物区分所有权业主共有部分登记信息_共有部分
     * @param qlid 建筑物区分所有权共有部分qlid
     * @return List<BdcFdcq3GyxxDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcFdcq3GyxxDO> queryFdcq3GyxxListByQlid(String qlid) {
        if(StringUtils.isBlank(qlid)){
            return  Collections.emptyList();
        }
        Example example = new Example(BdcFdcq3GyxxDO.class);
        example.createCriteria().andEqualTo("qlid", qlid);
        List<BdcFdcq3GyxxDO> gyxxDOList = entityMapper.selectByExample(example);

        // 登记机构、登簿人、登记时间 改成从主表获取 added by zhuyong 20200610
        if(CollectionUtils.isEmpty(gyxxDOList)) {
            return Collections.emptyList();
        }

        // 获取主表的信息
        BdcFdcq3DO bdcFdcq3DO = entityMapper.selectByPrimaryKey(BdcFdcq3DO.class, qlid);
        if(null == bdcFdcq3DO) {
            return gyxxDOList;
        }

        // 读取主表 登记机构、登簿人、登记时间
        for(BdcFdcq3GyxxDO gyxxDO : gyxxDOList) {
            if(null == gyxxDO) {
                continue;
            }
            gyxxDO.setDjjg(bdcFdcq3DO.getDjjg());
            gyxxDO.setDjsj(bdcFdcq3DO.getDjsj());
            gyxxDO.setDbr(bdcFdcq3DO.getDbr());
        }

        return gyxxDOList;
    }
}
