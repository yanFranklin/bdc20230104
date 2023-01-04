package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.engine.*;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.mapper.BdcGzSjlMapper;
import cn.gtmap.realestate.engine.core.service.BdcGzGxService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzDaoService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 15:12
 * @description 子规则实现类
 */
@Slf4j
@Service
public class BdcGzZgzServiceImpl implements BdcGzZgzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZgzServiceImpl.class);


    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcGzZgzDaoService bdcGzZgzDaoService;
    @Autowired
    private BdcGzGxService bdcGzGxService;

    @Autowired
    private BdcGzSjlMapper bdcGzSjlMapper;

    @Autowired
    private BdcGzDmService bdcGzDmService;



    /**
     * 获取子规则数据列表
     * @param pageable   pageable
     * @param bdcGzZgzQO bdcGzZgzDO
     * @return BdcGzZgzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcGzZgzDO> listBdcGzZgzPage(Pageable pageable, BdcGzZgzQO bdcGzZgzQO) {
        return repo.selectPaging("listBdcGzZgzByPage",bdcGzZgzQO,pageable);
    }


    /**
     * @author: <a href="mailto:zhangxinyu2@gtmap.cn">zhangxinyu2</a>
     * @param: String gzid
     * @return: BdcGzZgzDO
     * @description 根据主键gzid查找子规则
     */
    @Override
    public BdcGzZgzDO getZgz(String gzid) {
        if (StringUtils.isBlank(gzid)) {
            throw new NullPointerException("查询对象不可为空！");
        }
        return entityMapper.selectByPrimaryKey(BdcGzZgzDO.class, gzid);

    }

    /**
     * @author: <a href="mailto:zhangxinyu2@gtmap.cn">zhangxinyu2</a>
     * @param: BdcGzGxDO bdcGzGxDO
     * @return: boolean 关联为true,不关联为false
     * @description 判断组合规则和子规则是否关联
     */
    @Override
    public boolean queryBdcGzGx(BdcGzGxDO bdcGzGxDO) {
        if(StringUtils.isNotBlank(bdcGzGxDO.getZhid()) && StringUtils.isNotBlank(bdcGzGxDO.getGzid()) ){
            Example example = new Example(BdcGzGxDO.class);
            example.createCriteria().andEqualTo("zhid", bdcGzGxDO.getZhid()).andEqualTo("gzid", bdcGzGxDO.getGzid());
            List<BdcGzGxDO> bdcGzGxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(bdcGzGxDOList)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 删除子规则
     *
     * @param bdcGzZgzDOList bdcGzZgzDOList
     * @return 删除的数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void deleteBdcGzZgz(List<BdcGzZgzDO> bdcGzZgzDOList) {
        if(CollectionUtils.isEmpty(bdcGzZgzDOList)){
            throw new  AppException("需要删除的子规则不能为空！");
        }
        //循环删除子规则及子表（数据流，数据流参数）数据
        for(BdcGzZgzDO bdcGzZgzDO : bdcGzZgzDOList){
            delBdcZgzCombo(bdcGzZgzDO);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDO 子规则信息
     * @description 删除单个子规则
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBdcGzZgz(BdcGzZgzDO bdcGzZgzDO) {
        delBdcZgzCombo(bdcGzZgzDO);
    }

    /**
     * @param zhid
     * @return bdcGzZgzDTOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据gzid获取BdcGzZgzDTO列表
     */
    @Override
    public List<BdcGzZgzDTO> queryBdcGzZgzDTOListByZhid(String zhid) {
        List<BdcGzZgzDTO> bdcGzZgzDTOList = new ArrayList<>();
        if(StringUtils.isNotBlank(zhid)){
            List<BdcGzGxDO> bdcGzGxDOList = bdcGzGxService.queryBdcGzGxListByZhid(zhid);
            if(CollectionUtils.isNotEmpty(bdcGzGxDOList)){
                for(BdcGzGxDO bdcGzGxDO : bdcGzGxDOList){
                    if(bdcGzGxDO != null && StringUtils.isNotBlank(bdcGzGxDO.getGzid())){
                        BdcGzZgzDTO bdcGzZgzDTO = queryBdcGzZgzDTOByGzid(bdcGzGxDO.getGzid());
                        if(null != bdcGzZgzDTO) {
                            bdcGzZgzDTOList.add(bdcGzZgzDTO);
                        }
                    }
                }
            }
        }
        return bdcGzZgzDTOList;
    }


    /**
     * @param gzid
     * @return bdcGzZgzDTO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据gzid获取BdcGzZgzDTO
     */
    @Override
    public BdcGzZgzDTO queryBdcGzZgzDTOByGzid(String gzid) {
        if(StringUtils.isBlank(gzid)){
            return null;
        }

        BdcGzZgzDO bdcGzZgzDO = bdcGzZgzDaoService.queryBdcGzZgzDO(gzid);
        if(bdcGzZgzDO == null) {
            return null;
        }

        BdcGzZgzDTO bdcGzZgzDTO = new BdcGzZgzDTO();
        BeanUtils.copyProperties(bdcGzZgzDO,bdcGzZgzDTO);
        //获取规则数据流DTO列表
        List<BdcGzSjlDTO> bdcGzSjlDTOList = queryBdcGzSjlDTOByGzid(gzid);
        bdcGzZgzDTO.setBdcGzSjlDTOList(bdcGzSjlDTOList);

        //获取关联的表达式、提示信息列表
        bdcGzZgzDTO.setBdcGzBdsTsxxDOList(this.queryBdcGzBdsTsxxDOByGzid(gzid));
        return bdcGzZgzDTO;
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@return bdcGzSjlDTO
     *@description 通过gzid获取规则数据流DTO列表
     */
    @Override
    public List<BdcGzSjlDTO> queryBdcGzSjlDTOByGzid(String gzid){
        List<BdcGzSjlDTO> bdcGzSjlDTOList = new ArrayList<>();
        if(StringUtils.isNotBlank(gzid)){
            List<BdcGzSjlDO> bdcGzSjlDOList = bdcGzZgzDaoService.listBdcGzSjlDOByGzid(gzid);
            if(CollectionUtils.isNotEmpty(bdcGzSjlDOList) && bdcGzSjlDOList.get(0) != null){
                for(BdcGzSjlDO bdcGzSjlDO : bdcGzSjlDOList){
                    BdcGzSjlDTO bdcGzSjlDTO = new BdcGzSjlDTO();
                    BeanUtils.copyProperties(bdcGzSjlDO,bdcGzSjlDTO);
                    if(StringUtils.isNotBlank(bdcGzSjlDO.getSjlid())){
                        List<BdcGzSjlCsDO> bdcGzSjlCsDOList = bdcGzZgzDaoService.queryBdcGzSjlCsDOListBySjlid(bdcGzSjlDO.getSjlid());
                        bdcGzSjlDTO.setBdcGzSjlCsDOList(bdcGzSjlCsDOList);
                    }
                    bdcGzSjlDTOList.add(bdcGzSjlDTO);
                }
            }
        }
        return bdcGzSjlDTOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 子规则ID
     * @return {List} 表达式、提示信息集合
     * @description 获取子规则关联的表达式、提示信息集合
     */
    @Override
    public List<BdcGzBdsTsxxDO> queryBdcGzBdsTsxxDOByGzid(String gzid) {
        if(StringUtils.isBlank(gzid)){
            return Collections.emptyList();
        }

        Example example = new Example(BdcGzBdsTsxxDO.class);
        example.createCriteria().andEqualTo("gzid", gzid);
        return entityMapper.selectByExample(example);
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZgzDO
     *@description 删除子规则及子表（数据流，数据流参数）数据
     */
    public void delBdcZgzCombo(BdcGzZgzDO bdcGzZgzDO){
        if(bdcGzZgzDO != null){
            //删除子规则数据流信息
            if(StringUtils.isNotBlank(bdcGzZgzDO.getGzid())){
                delBdcZgzSjlCombo(bdcGzZgzDO.getGzid());
            }
            //删除子规则数据
            bdcGzZgzDaoService.delBdcGzZgz(bdcGzZgzDO);
        }
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@description 删除子规则数据流及其子表（数据流参数）数据
     */
    public void delBdcZgzSjlCombo(String gzid){
        if(StringUtils.isNotBlank(gzid)){
            List<BdcGzSjlDO> bdcGzSjlDOList = bdcGzZgzDaoService.listBdcGzSjlDOByGzid(gzid);

            if(CollectionUtils.isNotEmpty(bdcGzSjlDOList)){
                for(BdcGzSjlDO bdcGzSjlDO : bdcGzSjlDOList){
                    if(null == bdcGzSjlDO) {
                        continue;
                    }

                    if(StringUtils.isNotBlank(bdcGzSjlDO.getSjlid())){
                        //删除数据流参数
                        bdcGzZgzDaoService.delBdcGzSjlCsBySjlid(bdcGzSjlDO.getSjlid());
                    }
                    //删除数据流
                    bdcGzZgzDaoService.delBdcGzSjl(bdcGzSjlDO);
                }
            }

            //删除表达式、提示信息
            bdcGzZgzDaoService.deleteGzBdsTsxxByGzid(gzid);
        }
    }

    /**
     * 保存子规则及关联的数据流、表达式等信息（每次保证子规则记录ID不变，关联的数据流、表达式等ID可以变，但是关联关系维护不变）
     * @param bdcGzZgzDTO 子规则DTO
     * @return {String} 子规则主键ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveBdcGzZgz(BdcGzZgzDTO bdcGzZgzDTO) {
        if(null == bdcGzZgzDTO || (CollectionUtils.isEmpty(bdcGzZgzDTO.getBdcGzBdsTsxxDOList()) && StringUtils.isBlank(bdcGzZgzDTO.getJydm()))){
            return null;
        }

        BdcGzZgzDO bdcGzZgzDO = this.getBdcGzZgzDO(bdcGzZgzDTO);

        // 1、先判断新增、编辑
        if(StringUtils.isBlank(bdcGzZgzDO.getGzid())){
            bdcGzZgzDO.setGzid(UUIDGenerator.generate());
        } else {
            // 如果已经存在，则先删除已有记录
            this.delBdcZgzCombo(bdcGzZgzDO);
        }

        // 2、保存子规则信息
        bdcGzZgzDaoService.insertBdcGzZgz(bdcGzZgzDO);

        // 3、保存数据流信息
        List<BdcGzSjlDTO> bdcGzSjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
        List<String> sjlIdList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(bdcGzSjlDTOList)){

            int sjlsx = 0;
            for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzSjlDTOList){
                String sjlId = UUIDGenerator.generate();
                // 3.1 保存数据流基本信息
                bdcGzSjlDTO.setGzid(bdcGzZgzDO.getGzid());
                bdcGzSjlDTO.setSjlid(sjlId);
                BdcGzSjlDO bdcGzSjlDO = this.getBdcGzSjlDO(bdcGzSjlDTO);
                bdcGzSjlDO.setSjlsx(sjlsx++);
                bdcGzZgzDaoService.insertBdcGzSjl(bdcGzSjlDO);
                sjlIdList.add(sjlId);

                // 3.2 保存数据流参数信息
                List<BdcGzSjlCsDO> bdcGzSjlCsDOList = bdcGzSjlDTO.getBdcGzSjlCsDOList();
                if(CollectionUtils.isEmpty(bdcGzSjlCsDOList)){
                    // 允许没有参数
                    continue;
                }
                for(BdcGzSjlCsDO bdcGzSjlCsDO : bdcGzSjlCsDOList){
                    bdcGzSjlCsDO.setSjlid(bdcGzSjlDTO.getSjlid());
                    bdcGzSjlCsDO.setSjlcsid(UUIDGenerator.generate());
                    bdcGzZgzDaoService.insertBdcGzSjlCs(bdcGzSjlCsDO);
                }
            }
            // 删除旧的数据流参数
            bdcGzSjlDTOList.forEach(bdcGzSjlDTO -> {
                List<String> sjlCsIdList = bdcGzSjlDTO.getBdcGzSjlCsDOList().stream().distinct().map(BdcGzSjlCsDO::getSjlcsid).collect(Collectors.toList());
                if(StringUtils.isNotEmpty(bdcGzSjlDTO.getSjlid()) && CollectionUtils.isNotEmpty(sjlCsIdList)){
                    LOGGER.info("deleteBdcGzSjlCsByCondition param,sjlId:{},sjlCsIdList:{}",bdcGzSjlDTO.getSjlid(),sjlCsIdList);
                    bdcGzSjlMapper.deleteBdcGzSjlCsByCondition(bdcGzZgzDTO.getGzid(),sjlCsIdList);
                }
            });

        }else{
            // 删除数据流
            if(StringUtils.isNotEmpty(bdcGzZgzDTO.getGzid())){
                LOGGER.info("deleteBdcGzSjlByCondition param,gzId:{},sjlIdList:{}",bdcGzZgzDTO.getGzid(),sjlIdList);
                bdcGzSjlMapper.deleteBdcGzSjlByCondition(bdcGzZgzDTO.getGzid(),sjlIdList);
            }
        }


        // 4、保存规则表达式、验证信息
        List<BdcGzBdsTsxxDO> bdcGzBdsTsxxDOList = bdcGzZgzDTO.getBdcGzBdsTsxxDOList();
        if(CollectionUtils.isNotEmpty(bdcGzBdsTsxxDOList)){
            for(BdcGzBdsTsxxDO bdcGzBdsTsxxDO : bdcGzBdsTsxxDOList){
                bdcGzBdsTsxxDO.setBdsid(UUIDGenerator.generate());
                bdcGzBdsTsxxDO.setGzid(bdcGzZgzDO.getGzid());
                bdcGzZgzDaoService.insertBdcGzBdsTsxx(bdcGzBdsTsxxDO);
            }
        }

        // 5、重新生成动态校验代码编译缓存结果
        if(StringUtils.isNotBlank(bdcGzZgzDTO.getJydm())) {
            bdcGzDmService.updateJavaCodeCompileCache(bdcGzZgzDTO);
        }

        return bdcGzZgzDO.getGzid();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则DTO
     * @return {String} 子规则主键ID
     * @description 直接新增子规则（实体中定义的ID都不改变，原样导入）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBdcGzZgz(BdcGzZgzDTO bdcGzZgzDTO) {
        if(null == bdcGzZgzDTO){
            return;
        }

        // 1、保存子规则基本信息
        BdcGzZgzDO bdcGzZgzDO = new BdcGzZgzDO();
        BeanUtils.copyProperties(bdcGzZgzDTO, bdcGzZgzDO);
        entityMapper.insertSelective(bdcGzZgzDO);

        // 2、保存数据流信息
        List<BdcGzSjlDTO> bdcGzSjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
        if(CollectionUtils.isNotEmpty(bdcGzSjlDTOList)){
            for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzSjlDTOList){
                // 2.1 保存数据流基本信息
                BdcGzSjlDO bdcGzSjlDO = new BdcGzSjlDO();
                BeanUtils.copyProperties(bdcGzSjlDTO, bdcGzSjlDO);
                entityMapper.insertSelective(bdcGzSjlDO);

                // 2.2 保存数据流参数信息
                List<BdcGzSjlCsDO> bdcGzSjlCsDOList = bdcGzSjlDTO.getBdcGzSjlCsDOList();
                if(CollectionUtils.isNotEmpty(bdcGzSjlCsDOList)){
                    bdcGzSjlCsDOList.stream().forEach(bdcGzSjlCsDO -> entityMapper.insertSelective(bdcGzSjlCsDO));
                }
            }
        }

        // 3、保存规则表达式、验证信息
        List<BdcGzBdsTsxxDO> bdcGzBdsTsxxDOList = bdcGzZgzDTO.getBdcGzBdsTsxxDOList();
        if(CollectionUtils.isNotEmpty(bdcGzBdsTsxxDOList)){
            bdcGzBdsTsxxDOList.stream().forEach(bdcGzBdsTsxxDO -> entityMapper.insertSelective(bdcGzBdsTsxxDO));
        }
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: {gzid} 子规则ID
     * @return: ｛String｝ 拷贝的子规则主键ID
     * @description 拷贝子规则信息，生成新的子规则
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String copyBdcGzZgz(String gzid) {
        //1、获取当前子规则的信息，包含关联的数据流、表达式、关联等信息
        BdcGzZgzDTO bdcGzZgzDTO = this.queryBdcGzZgzDTOByGzid(gzid);
        if(StringUtils.isEmpty(bdcGzZgzDTO.getGzid())){
            throw new NullPointerException("未获取到复制的子规则信息。");
        }
        //2、调用保存方法进行子规则信息、数据流信息、数据流参数、以及表达式和验证信息的保存
        bdcGzZgzDTO.setGzid(null);
        bdcGzZgzDTO.setPzrq(new Date());
        return this.saveBdcGzZgz(bdcGzZgzDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则DTO
     * @description 根据子规则DTO获取对应DO对象
     */
    private BdcGzZgzDO getBdcGzZgzDO(BdcGzZgzDTO bdcGzZgzDTO) {
        BdcGzZgzDO bdcGzZgzDO = new BdcGzZgzDO();
        bdcGzZgzDO.setGzid(bdcGzZgzDTO.getGzid());
        bdcGzZgzDO.setGzmc(bdcGzZgzDTO.getGzmc());
        bdcGzZgzDO.setYtsm(bdcGzZgzDTO.getYtsm());
        bdcGzZgzDO.setYxj(bdcGzZgzDTO.getYxj());
        bdcGzZgzDO.setPzr(bdcGzZgzDTO.getPzr());
        bdcGzZgzDO.setPzrq(bdcGzZgzDTO.getPzrq());
        bdcGzZgzDO.setJydm(bdcGzZgzDTO.getJydm());
        bdcGzZgzDO.setPcqh(bdcGzZgzDTO.getPcqh());

        return bdcGzZgzDO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO 数据流DTO
     * @description 根据数据流DTO获取对应DO
     */
    private BdcGzSjlDO getBdcGzSjlDO(BdcGzSjlDTO bdcGzSjlDTO) {
        BdcGzSjlDO bdcGzSjlDO = new BdcGzSjlDO();
        bdcGzSjlDO.setGzid(bdcGzSjlDTO.getGzid());
        bdcGzSjlDO.setSjlid(bdcGzSjlDTO.getSjlid());
        bdcGzSjlDO.setSjlmc(bdcGzSjlDTO.getSjlmc());
        bdcGzSjlDO.setSjly(bdcGzSjlDTO.getSjly());
        bdcGzSjlDO.setQqyy(bdcGzSjlDTO.getQqyy());
        bdcGzSjlDO.setFwff(bdcGzSjlDTO.getFwff());
        bdcGzSjlDO.setSjnr(bdcGzSjlDTO.getSjnr());
        bdcGzSjlDO.setJgblbs(bdcGzSjlDTO.getJgblbs());
        bdcGzSjlDO.setJgblmc(bdcGzSjlDTO.getJgblmc());

        return bdcGzSjlDO;
    }
}
