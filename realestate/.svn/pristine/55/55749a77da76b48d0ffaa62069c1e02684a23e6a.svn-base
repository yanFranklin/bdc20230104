package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFsPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcFsPzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a herf="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/08/03
 * @description 非税配置
 */
@RestController
@Api(tags = "非税配置")
public class BdcFsPzRestController implements BdcFsPzRestService {
    @Autowired
    private EntityMapper entityMapper;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新非税配置信息")
    public int updateFspz(@RequestBody BdcFsPzDO bdcFsPzDO) {
        int result = 0;
        if (!CheckParameter.checkAnyParameter(bdcFsPzDO)) {
            throw new AppException("传入参数为空！");
        }
        if (StringUtils.isNotBlank(bdcFsPzDO.getFspzid())) {
            Example example = new Example(BdcFsPzDO.class, false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fspzid", bdcFsPzDO.getFspzid());
            result = entityMapper.updateByExampleSelectiveNotNull(bdcFsPzDO, example);
        }
        return result;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询非税配置信息")
    public BdcFsPzDO queryFspz(@RequestParam(name = "fspzid", required = false) String fspzid, @RequestParam(name = "userid", required = false) String userid) {
        if (StringUtils.isBlank(fspzid) && StringUtils.isBlank(userid)) {
            throw new MissingArgumentException("查询非税配置信息,缺少参数fspzid或userid");
        }
        Example example = new Example(BdcFsPzDO.class, false);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(fspzid)) {
            criteria.andEqualTo("fspzid", fspzid);
        }
        if (StringUtils.isNotBlank(userid)) {
            criteria.andEqualTo("userid", userid);
        }
        List<BdcFsPzDO> bdcFsPzDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcFsPzDOList)) {
            return bdcFsPzDOList.get(0);
        }
        return null;
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除非税配置信息")
    public void deleteFspz(@RequestParam(name = "fspzidList") List<String> fspzidList) {
        if (CollectionUtils.isEmpty(fspzidList)) {
            throw new MissingArgumentException("删除非税配置，缺少参数fspzidList");
        }
        for (String id : fspzidList) {
            entityMapper.deleteByPrimaryKey(BdcFsPzDO.class, id);
        }
    }

    /**
     * @param fsPzDOList
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量保存非税配置
     * @date : 2022/8/4
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量保存非税配置信息")
    public int saveFspzPl(@RequestBody List<BdcFsPzDO> fsPzDOList) {
        if (CollectionUtils.isEmpty(fsPzDOList)) {
            throw new AppException("新增非税配置不可为空");
        }
        return entityMapper.insertBatchSelective(fsPzDOList);
    }
}
