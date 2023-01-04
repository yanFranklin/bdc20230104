package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXtJgpzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
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
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/27
 * @description
 */
@RestController
@Api(tags = "机构配置")
public class BdcXtJgpzRestController implements BdcXtJgpzRestService {
    @Autowired
    private EntityMapper entityMapper;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存机构配置信息")
    public BdcXtJgDO saveJgpz(@RequestBody BdcXtJgDO bdcXtJgDO) {
        if (!CheckParameter.checkAnyParameter(bdcXtJgDO)){
            throw new AppException("传入参数为空！");
        }

        if (StringUtils.isBlank(bdcXtJgDO.getJgid())){
            bdcXtJgDO.setJgid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcXtJgDO);
        } else {
            Example example= new Example(BdcXtJgDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("jgid", bdcXtJgDO.getJgid());
            entityMapper.updateByExampleSelectiveNotNull(bdcXtJgDO, example);
        }
        return bdcXtJgDO;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询机构配置信息")
    public BdcXtJgDO queryJgpz(String jgid) {
        if (StringUtils.isBlank(jgid)){
            throw new MissingArgumentException("jgid");
        }

        return entityMapper.selectByPrimaryKey(BdcXtJgDO.class, jgid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除机构配置信息")
    public void deleteJgpz(@RequestParam(name = "jgidList") List<String> jgidList) {
        if (CollectionUtils.isEmpty(jgidList)){
            throw new MissingArgumentException("jgidList");
        }

        for (String jgid : jgidList) {
            entityMapper.deleteByPrimaryKey(BdcXtJgDO.class, jgid);
        }
    }

    /**
     * @param bdcXtJgDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存系统机关数据
     * @date : 2020/9/9 10:37
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存机构配置信息")
    public int saveJgpzPl(@RequestBody List<BdcXtJgDO> bdcXtJgDOList) {
        if(CollectionUtils.isEmpty(bdcXtJgDOList)) {
            throw new AppException("新增系统机关信息不可为空");
        }
        int count = 0;
        // 分批导入
        List<List> partList = ListUtils.subList(bdcXtJgDOList, 1000);
        for (List<BdcXtJgDO> partBdcXtJgDOList : partList) {
            count+=entityMapper.insertBatchSelective(partBdcXtJgDOList);
        }
        return count;
    }
}
