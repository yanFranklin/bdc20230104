package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.core.mapper.BdcFdcq3GyxxMapper;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 建筑物区分所有权业主共有部分登记信息_共有部分
 */
@Service
public class BdcFdcq3GyxxServiceImpl implements BdcFdcq3GyxxService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcq3GyxxMapper bdcFdcq3GyxxMapper;
    @Autowired
    BdcQlrFeignService qlrFeignService;


    /**
     * @param xmid
     * @return List<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建筑物区分所有权业主共有部分登记信息_共有部分
     */
    @Override
    public List<BdcFdcq3GyxxDO> queryListBdcQlByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcFdcq3GyxxDO.class);
            example.setOrderByClause("djsj ASC,slbh ASC");
            example.createCriteria().andEqualTo("xmid", xmid);
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh 不动产单元号（或地籍号）
     * @return List<BdcFdcq3GyxxDO> 查询现势的共有信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据单元号或者地籍号，查询现势的共有信息
     */
    @Override
    public List<BdcFdcq3GyxxDO> queryListFdcq3Gyxx(String bdcdyh) {
        Map map = new HashMap(2);
        map.put("djh", StringUtils.substring(bdcdyh, 0, 19));

        List<Integer> qsztList = new ArrayList();
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        map.put("qsztList", qsztList);
        return bdcFdcq3GyxxMapper.listFdcq3GyxxByPage(map);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return xmid 项目ID
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    @Override
    public String getFdcq3Qlr(String xmid) {
        if(StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("没有参数xmid");
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcQlrQO.setXmid(xmid);
        List<BdcQlrDO> bdcQlrDOList = qlrFeignService.listBdcQlr(bdcQlrQO);

        return StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", ",");
    }
}
