package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityExistsException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsbhmbQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.config.service.BdcXtZsbhmbService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description 业务配置系统：证书编号模板配置处理Service
 */
@Service
public class BdcXtZsbhmbServiceImpl implements BdcXtZsbhmbService {
    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;

    /**
     * MyBatis ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsbhmbQO 查询条件
     * @return {Page} 证书编号模板分页数据
     * @description  查询证书编号模板数据列表
     */
    @Override
    public Page<BdcXtZsbhmbDO> queryBdcXtZsbhmb(Pageable pageable, BdcXtZsbhmbQO zsbhmbQO) {
        return repo.selectPaging("listBdcXtZsbhmbByPageOrder", zsbhmbQO, pageable);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDO 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description
     *      保存证书编号模板配置
     *    <p>
     *        1、基本原则：年份、区县代码、登记部门代码 组合成唯一索引 <br>
     *        2、新增场景：需要验证对应年份、地区模板记录是否已经存在，已经存在，不允许新增<br>
     *        3、编辑场景：<br>
     *          3.1、当前记录的模板年份、地区没有改变，可以直接更新<br>
     *          3.2、当前记录的模板年份、地区改变，需要验证是否已经存在该年份、地区对应的记录，如果已经存在不允许编辑
     *    </p>
     */
    @Override
    public int saveBdcXtZsbhmb(BdcXtZsbhmbDO bdcXtZsbhmbDO) {
        // 根据年度、区县代码查询模板记录
        Example example = new Example(BdcXtZsbhmbDO.class);
        example.createCriteria().andEqualTo("nf", bdcXtZsbhmbDO.getNf()).
                andEqualTo("qxdm", bdcXtZsbhmbDO.getQxdm()).
                andEqualTo("djbmdm", bdcXtZsbhmbDO.getDjbmdm());
        List<BdcXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);

        // 判断指定年份和区县代码模板配置是否重复
        if(CollectionUtils.isNotEmpty(zsbhmbDOList) &&
                !StringUtils.equals(bdcXtZsbhmbDO.getZsbhmbid(), zsbhmbDOList.get(0).getZsbhmbid())){
            throw new EntityExistsException("配置系统-配置证号模板：该地区指定年度证书编号模板配置已经存在！");
        }

        // 查询为空且提交模板ID为空，说明是新增数据
        if(StringUtils.isBlank(bdcXtZsbhmbDO.getZsbhmbid())){
            bdcXtZsbhmbDO.setZsbhmbid(UUIDGenerator.generate());
        }

        return entityMapper.saveOrUpdate(bdcXtZsbhmbDO, bdcXtZsbhmbDO.getZsbhmbid());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDOList 证书编号模板集合
     * @return {int} 操作数据记录数
     * @description 删除证书编号模板（设置事务，保证批量删除）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBdcXtZsbhmb(List<BdcXtZsbhmbDO> bdcXtZsbhmbDOList){
        if(CollectionUtils.isEmpty(bdcXtZsbhmbDOList)){
            return 0;
        }

        int count = 0;
        for (BdcXtZsbhmbDO bdcXtZsbhmbDO : bdcXtZsbhmbDOList){
            count += entityMapper.delete(bdcXtZsbhmbDO);
        }
        return count;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省区代码
     * @description 从证书模板配置中获取省区代码（正常来说所有模板配置的省区代码是一致的）
     */
    @Override
    public String querySqdm() {
        Example example = new Example(BdcXtZsbhmbDO.class);
        example.createCriteria().andIsNotNull("sqdm");
        List<BdcXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(zsbhmbDOList)){
            return zsbhmbDOList.get(0).getSqdm();
        }
        return null;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zsbhmbid 证书编号模板ID
     * @return｛String｝复制后新的证书编号模板ID
     * @description 复制当前证书编号模板的信息，并创建一个新的证书编号模板
     *    <p>
     *        因年份、区县代码、登记部门代码 组合成唯一索引，新的证书编号年份自动加1
     *    </p>
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String copyBdcXtZsbhmb(String zsbhmbid) {
        //查询当前编号对应的证书编号模板信息
        BdcXtZsbhmbDO bdcXtZsbhmbDO = this.entityMapper.selectByPrimaryKey(BdcXtZsbhmbDO.class, zsbhmbid);
        if(null == bdcXtZsbhmbDO || StringUtils.isEmpty(bdcXtZsbhmbDO.getZsbhmbid())){
            throw new AppException("未获取到证书编号模板信息！");
        }
        //年份自动加1
        int newNf = Integer.parseInt(bdcXtZsbhmbDO.getNf()) + 1;
        bdcXtZsbhmbDO.setNf(String.valueOf(newNf));

        bdcXtZsbhmbDO.setZsbhmbid(null);

        return this.insertBdcXtZsbhmb(bdcXtZsbhmbDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcXtZsbhmbDO
     * @return 新增的证书编号模板ID
     */
    @Override
    public String insertBdcXtZsbhmb(BdcXtZsbhmbDO bdcXtZsbhmbDO) {
        if(bdcXtZsbhmbDO != null){
            if(StringUtils.isBlank(bdcXtZsbhmbDO.getZsbhmbid())){
                bdcXtZsbhmbDO.setZsbhmbid(UUIDGenerator.generate());
            }
           entityMapper.insertSelective(bdcXtZsbhmbDO);
        }
        return bdcXtZsbhmbDO.getZsbhmbid();
    }
}
