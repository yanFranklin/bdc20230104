package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcDjyySjclPzMapper;
import cn.gtmap.realestate.accept.core.service.BdcDjyySjclPzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 登记原因收件材料配置服务实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 14:36
 **/
@Service
public class BdcDjyySjclPzServiceImpl implements BdcDjyySjclPzService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    BdcDjyySjclPzMapper bdcDjyySjclPzMapper;

    /**
     * @param bdcDjyySjclPzQO 查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/11 14:41
     */
    @Override
    public List<BdcDjyySjclPzDO> querySjclPz(BdcDjyySjclPzQO bdcDjyySjclPzQO) {

        //至少一个参数得有数据
        if(!CheckParameter.checkAnyParameter(bdcDjyySjclPzQO)) {
            throw new AppException("查询登记原因收件材料配置缺失必要参数");
        }
        Example example = new Example(BdcDjyySjclPzDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcDjyySjclPzQO.getDjxl())) {
            criteria.andEqualTo("djxl", bdcDjyySjclPzQO.getDjxl());
        }
        if (StringUtils.isNotBlank(bdcDjyySjclPzQO.getDjyy())) {
            criteria.andEqualTo("djyy", bdcDjyySjclPzQO.getDjyy());
        }
        if (Objects.nonNull(bdcDjyySjclPzQO.getSfbc())) {
            criteria.andEqualNvlTo("sfbc", bdcDjyySjclPzQO.getSfbc(), CommonConstantUtils.SF_F_DM);
        }
        example.setOrderByClause("xh");
        List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcDjyySjclPzDOList)) {
            bdcDjyySjclPzDOList = Collections.emptyList();
        }
        return bdcDjyySjclPzDOList;
    }

    @Override
    public Page<BdcDjyySjclPzDO> listBdcDjyySjclPzByPage(Pageable pageable, String bdcDjyySjclPzJson){
        BdcDjyySjclPzDO bdcDjyySjclPzDO = new BdcDjyySjclPzDO();
        if(StringUtils.isNotBlank(bdcDjyySjclPzJson)) {
            bdcDjyySjclPzDO = JSONObject.parseObject(bdcDjyySjclPzJson,BdcDjyySjclPzDO.class);
        }
        return repo.selectPaging("listBdcDjyySjclPzByPage", bdcDjyySjclPzDO, pageable);

    }

    @Override
    public int saveBdcDjyySjclPz(BdcDjyySjclPzDO bdcDjyySjclPzDO){
        if (bdcDjyySjclPzDO == null) {
            throw new MissingArgumentException("缺失参数");
        }
        if (StringUtils.isBlank(bdcDjyySjclPzDO.getPzid())) {
            bdcDjyySjclPzDO.setPzid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcDjyySjclPzDO, bdcDjyySjclPzDO.getPzid());

    }

    @Override
    public int deleteBdcDjyySjclPzList(List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList){
        int count = 0;
        if(CollectionUtils.isEmpty(bdcDjyySjclPzDOList)){
            return count;
        }
        for(BdcDjyySjclPzDO bdcDjyySjclPzDO:bdcDjyySjclPzDOList) {
            count +=entityMapper.delete(bdcDjyySjclPzDO);
        }
        return count;

    }

    @Override
    public int queryDjyySjclPzMaxSxh(BdcDjyySjclPzDO bdcDjyySjclPzDO){
        if(bdcDjyySjclPzDO==null ||StringUtils.isBlank(bdcDjyySjclPzDO.getDjxl())){
            throw new MissingArgumentException("获取收件材料登记原因配置最大序号 参数不能为空！");
        }
        return bdcDjyySjclPzMapper.queryDjyySjclPzMaxSxh(bdcDjyySjclPzDO);

    }
}
