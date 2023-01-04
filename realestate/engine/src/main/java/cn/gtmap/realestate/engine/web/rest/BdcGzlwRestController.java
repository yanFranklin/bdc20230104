package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShLogDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzlwRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.engine.core.service.BdcGzlwService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/9/3
 * @description
 */
@RestController
@Api(tags = "不动产规则例外rest服务")
public class BdcGzlwRestController implements BdcGzlwRestService {
    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcGzlwService bdcGzlwService;


    @Override
    public void addShxxData(@RequestBody BdcGzlwShDO bdcGzlwShDO) {
        bdcGzlwService.addShxxData(bdcGzlwShDO);
    }

    @Override
    public Page<Map> queryBdcGzlw(Pageable pageable, @RequestParam(name = "paramJson") String paramJson) {
        Map map = JSONObject.parseObject(paramJson, Map.class);
        return repo.selectPaging("listBdcGzlwShByPage", map, pageable);
    }

    @Override
    public Page<Map> bdcgzlwGroupByBdcdyh(Pageable pageable, String paramJson) {
        Map map = JSONObject.parseObject(paramJson, Map.class);
        return repo.selectPaging("listBdcGzlwShBdcdyhByPage", map, pageable);
    }

    @Override
    public Integer updateBdcGzlwxx(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList) {
        int i = 0;
        if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
            for (BdcGzlwShDO bdcGzlwShDO : bdcGzlwShDOList) {
                i += entityMapper.updateByPrimaryKeySelective(bdcGzlwShDO);
            }
        }
        return i;
    }

    @Override
    public Integer updateBdcGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO) {

        return entityMapper.updateByPrimaryKeySelective(bdcGzlwShDO);
    }

    @Override
    public Integer addBdcGzlwLog(@RequestBody List<BdcGzlwShLogDO> bdcGzlwShLogDOList) {
        Integer insertCount = 0;

        if (CollectionUtils.isNotEmpty(bdcGzlwShLogDOList)) {
            insertCount = entityMapper.insertBatchSelective(bdcGzlwShLogDOList);
        }

        return insertCount;
    }

    @Override
    public List<BdcGzlwShDO> queryBdcGzlwSh(String gzlslid) {
        List<BdcGzlwShDO> bdcGzlwShDOList = new ArrayList<>();

        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcGzlwShDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            bdcGzlwShDOList = entityMapper.selectByExample(example);
        }
        return bdcGzlwShDOList;
    }

    @Override
    public void deleteBdcGzlwSh(String gzlwid) {
        if (StringUtils.isNotBlank(gzlwid)) {
            entityMapper.deleteByPrimaryKey(BdcGzlwShDO.class, gzlwid);
        }
    }

    @Override
    public void updateBdcGzlwShzt(String processInsId, String currentUserName) {
        bdcGzlwService.updateBdcGzlwShzt(processInsId, currentUserName);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过gzlslid 删除所有相关的规则例外信息
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "通过gzlslid 删除所有相关的规则例外信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    @Override
    public void deleteBdcGzlwShByGzlslid(@RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcGzlwShDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            //通过gzlslid删除例外数据
            entityMapper.deleteByExample(example);
            //获取受理编号
            String slbh = bdcXmFeignService.querySlbh(gzlslid);
            if (StringUtils.isNotBlank(slbh)) {
                Example exampleSbh = new Example(BdcGzlwShDO.class);
                exampleSbh.createCriteria().andEqualTo("slbh", slbh);
                //通过slbh删除例外数据
                entityMapper.deleteByExample(exampleSbh);
            }
        }
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "通过规则例外实体 删除当前所有的规则例外信息")
    @Transactional(rollbackFor = Exception.class)
    public void deleteBdcGzlwShByGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO) {
        if (CheckParameter.checkAnyParameter(bdcGzlwShDO)) {
            List<BdcGzlwShDO> bdcGzlwShDOList = entityMapper.selectByObj(bdcGzlwShDO);
            if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
                for (BdcGzlwShDO bdcGzlwSh : bdcGzlwShDOList) {
                    entityMapper.deleteByPrimaryKey(BdcGzlwShDO.class, bdcGzlwSh.getGzlwid());
                }
            }
        } else {
            throw new AppException("规则例外实体为空 无法删除");
        }
    }

    @Override
    public List<BdcXmDO> queryAllBdcXmXzql(@RequestBody BdcXmDO bdcXmDO) {
        // 根据不动产项目 权利类型 权属状态查询所有当前权利类型的不动产项目数据
        Example example = new Example(BdcXmDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bdcdyh", bdcXmDO.getBdcdyh());
        criteria.andEqualTo("qllx", bdcXmDO.getQllx());
        criteria.andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcGzlwShDO> listBdcGzlwByBdcGzlwShQO(@RequestBody BdcGzlwShQO bdcGzlwShQO) {
        if (!CheckParameter.checkAnyParameter(bdcGzlwShQO)) {
            throw new AppException("规则例外查询实体为空 无法进行查询。");
        }
        Example example = new Example(BdcGzlwShDO.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bdcGzlwShQO.getBdcdyh())){
            criteria.andEqualTo("bdcdyh", bdcGzlwShQO.getBdcdyh());
        }
        if(StringUtils.isNotBlank(bdcGzlwShQO.getSlbh())){
            criteria.andEqualTo("slbh", bdcGzlwShQO.getSlbh());
        }
        if(StringUtils.isNotBlank(bdcGzlwShQO.getGzid())){
            criteria.andEqualTo("gzid", bdcGzlwShQO.getGzid());
        }
        if(StringUtils.isNotBlank(bdcGzlwShQO.getGzmc())){
            criteria.andEqualTo("gzmc", bdcGzlwShQO.getGzmc());
        }
        if(StringUtils.isNotBlank(bdcGzlwShQO.getXmid())){
            criteria.andEqualTo("xmid", bdcGzlwShQO.getXmid());
        }
        if(null != bdcGzlwShQO.getShzt()){
            criteria.andEqualTo("shzt", bdcGzlwShQO.getShzt());
        }
        if(null !=bdcGzlwShQO.getPcshzt()) {
            criteria.andNotEqualTo("shzt",bdcGzlwShQO.getPcshzt());
        }
        return entityMapper.selectByExample(example);
    }

    @Override
    @ApiOperation(value = "批量添加审核信息")
    public BdcGzlwShDO addShxxDataPl(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList,
                                     @RequestParam(value = "slbh") String slbh,
                                     @RequestParam(value = "lwyy") String lwyy){
        return bdcGzlwService.addShxxDataPl(bdcGzlwShDOList, slbh, lwyy);

    }

}
