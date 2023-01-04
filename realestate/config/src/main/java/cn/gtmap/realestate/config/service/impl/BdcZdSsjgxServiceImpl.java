package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.config.core.mapper.BdcZdSsjgxMapper;
import cn.gtmap.realestate.config.service.BdcZdSsjgxService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BdcZdSsjgxServiceImpl implements BdcZdSsjgxService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BdcZdSsjgxMapper bdcZdSsjgxMapper;

    @Autowired
    private Repo repo;

    @Autowired
    private EntityMapper entityMapper;


    private static final String ZD_SSJGX_ZML_LEY = "config:ssjgxzdxx:zmldm:";


    /**
     * 查询BdcZdSsjGxDO，bdcZdSsjGxQO为空默认查全部激活状态的数据
     *
     * @param bdcZdSsjGxQO
     * @return
     */
    @Override
    public List<BdcZdSsjGxDO> listSsjgx(BdcZdSsjGxQO bdcZdSsjGxQO) {
        return bdcZdSsjgxMapper.listSsjgx(bdcZdSsjGxQO);
    }

    /**
     * 通过子目录代码获取BdcZdSsjGxDO
     *
     * @param zmldm
     * @return
     */
    @Override
    public BdcZdSsjGxDO getSsjgxByZmldm(String zmldm) {
        if (StringUtil.isNotBlank(zmldm)){
            if (redisUtils.isExistKey(ZD_SSJGX_ZML_LEY + zmldm)){
                return JSON.parseObject(redisUtils.getStringValue(ZD_SSJGX_ZML_LEY + zmldm),BdcZdSsjGxDO.class);
            }
            BdcZdSsjGxDO ssjGxDO = bdcZdSsjgxMapper.getSsjgxByZmldm(zmldm);
            if(Objects.nonNull(ssjGxDO)){
                redisUtils.addStringValue(ZD_SSJGX_ZML_LEY + zmldm,JSON.toJSONString(ssjGxDO),7200);
            }
            return ssjGxDO;
        }
        return null;
    }

    /**
     * 通过父目录代码获取BdcZdSsjGxDO
     *
     * @param fmldms
     * @return
     */
    @Override
    public List<BdcZdSsjGxDO> getSsjgxByFmldms(String fmldms) {
        if (StringUtil.isNotBlank(fmldms)){
            List<BdcZdSsjGxDO> ssjGxDOS = bdcZdSsjgxMapper.getSsjgxByFmldms(Arrays.asList(fmldms.split(",")));
            return ssjGxDOS;
        }
        return null;
    }

    @Override
    public Page<BdcZdSsjGxDO> listBdcZdSsjGxByPage(Pageable pageable, BdcZdSsjGxDO bdcZdSsjGxDO){
        return repo.selectPaging("listBdcZdSsjGxByPage", bdcZdSsjGxDO, pageable);
    }

    @Override
    public void deleteBdcZdSsjGxByZmldm(String zmldm){
        if(StringUtils.isNotBlank(zmldm)){
            Example example =new Example(BdcZdSsjGxDO.class);
            example.createCriteria().andEqualTo("zmldm",zmldm);
            entityMapper.deleteByExampleNotNull(example);
            redisUtils.deleteKey(ZD_SSJGX_ZML_LEY + zmldm);
        }
    }

    @Override
    public void saveBdcZdSsjgx(BdcZdSsjGxDO bdcZdSsjGxDO){
        if(bdcZdSsjGxDO ==null || StringUtils.isBlank(bdcZdSsjGxDO.getZmldm())){
            throw new AppException("保存省市级共享字典数据失败,缺失数据");
        }
        if(StringUtils.isBlank(bdcZdSsjGxDO.getId())){
            bdcZdSsjGxDO.setId(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcZdSsjGxDO);
            redisUtils.addStringValue(ZD_SSJGX_ZML_LEY + bdcZdSsjGxDO.getZmldm(),JSON.toJSONString(bdcZdSsjGxDO),7200);
        }else{
            entityMapper.updateByPrimaryKeySelective(bdcZdSsjGxDO);
            BdcZdSsjGxDO zdSsjGxDO =entityMapper.selectByPrimaryKey(BdcZdSsjGxDO.class,bdcZdSsjGxDO.getId());
            redisUtils.addStringValue(ZD_SSJGX_ZML_LEY + bdcZdSsjGxDO.getZmldm(),JSON.toJSONString(zdSsjGxDO),7200);
        }

    }

    @Override
    public BdcZdSsjGxDO queryBdcZdSsjGx(String id){
        if(StringUtils.isNotBlank(id)){
            return entityMapper.selectByPrimaryKey(BdcZdSsjGxDO.class,id);
        }
        return null;

    }
}
