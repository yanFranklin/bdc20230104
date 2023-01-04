package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.supervise.core.qo.LfYzhQO;
import cn.gtmap.realestate.supervise.core.dto.LfYzhtjDTO;
import cn.gtmap.realestate.supervise.core.mapper.LfZssyjgMapper;
import cn.gtmap.realestate.supervise.service.LfZssyjgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/25
 * @description 证书使用监管逻辑处理
 */
@Service
public class LfZssyjgServiceImpl implements LfZssyjgService {
    private static Logger logger = LoggerFactory.getLogger(LfZssyjgServiceImpl.class);

    @Autowired
    private Repo repo;

    @Autowired
    private LfZssyjgMapper zssyjgMapper;

    @Autowired
    private EntityMapper entityMapper;


    /**
     * 查询证书印制号
     * @param pageable 分页对象
     * @param lfYzhQO 查询条件
     * @return 证书印制号信息
     */
    @Override
    public Page<BdcYzhDO> listBdcZsyzh(Pageable pageable, LfYzhQO lfYzhQO) {
        if(!CheckParameter.checkAnyParameter(lfYzhQO)) {
            throw new MissingArgumentException("查询印制号缺少参数信息！");
        }
        return repo.selectPaging("listZsyzhByPageOrder", lfYzhQO, pageable);
    }

    /**
     * 查询印制号详细信息
     * @param yzhid 印制号ID
     * @return {BdcYzhDTO} 印制号信息
     */
    @Override
    public BdcYzhDTO queryBdcYzh(String yzhid) {
        if (StringUtils.isBlank(yzhid)) {
            throw new MissingArgumentException("查询印制号缺少参数：印制号ID");
        }

        BdcYzhDTO bdcYzhDTO = new BdcYzhDTO();
        BdcYzhDO bdcYzhDO = entityMapper.selectByPrimaryKey(BdcYzhDO.class, yzhid);
        if(null == bdcYzhDO) {
            return null;
        }

        BeansResolveUtils.clonePropsValueWithParentProps(bdcYzhDO, bdcYzhDTO);
        List<BdcYzhsymxDO> bdcYzhsymxDOList = this.queryBdcYzhsymx(bdcYzhDTO.getYzhid());
        bdcYzhDTO.setBdcYzhsymxDOList(bdcYzhsymxDOList);
        return bdcYzhDTO;
    }

    /**
     * 废弃印制号统计（统计哪些人废弃了多少印制号）
     * @param lfYzhQO 印制号查询条件
     * @return {List} 统计信息
     */
    @Override
    public List<LfYzhtjDTO> fqyzhtj(LfYzhQO lfYzhQO) {
        // 统计可以不传过滤参数
        return zssyjgMapper.fqyzhtj(lfYzhQO);
    }

    /**
     * 查询印制号的使用明细
     * @param yzhid 印制号ID
     * @return {list} 印制号使用明细
     */
    private List<BdcYzhsymxDO> queryBdcYzhsymx(String yzhid) {
        Example example = new Example(BdcYzhsymxDO.class);
        example.createCriteria().andEqualTo("yzhid", yzhid);
        return entityMapper.selectByExampleNotNull(example);
    }
}
