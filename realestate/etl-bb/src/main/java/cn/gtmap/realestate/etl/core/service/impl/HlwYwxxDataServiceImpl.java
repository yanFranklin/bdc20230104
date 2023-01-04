package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/12
 * @description
 */
@Service
public class HlwYwxxDataServiceImpl implements HlwYwxxDataService {
    @Resource(name = "hlwEntityMapper")
    private EntityMapper hlwEntityMapper;


    @Override
    public GxWwSqxmDo gxWwsqxmByHlwXmid(String hlwxmid) {
        if (StringUtils.isBlank(hlwxmid)) {
            throw new MissingArgumentException("互联网主键项目id不能为空");
        }
        return hlwEntityMapper.selectByPrimaryKey(GxWwSqxmDo.class, hlwxmid);
    }

    @Override
    public List<GxWwSqxxDo> listGxWwSqxxByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("互联网主键项目id不能为空");
        }
        Example exampleSqxx = new Example(GxWwSqxxDo.class);
        exampleSqxx.setOrderByClause("bdbzzqse desc");
        exampleSqxx.createCriteria().andEqualTo("xmid", xmid);
        return hlwEntityMapper.selectByExample(exampleSqxx);
    }

    @Override
    public List<GxWwSqxxQlrDo> listGxWwSqxxQlrBySqxxid(String sqxxid) {
        if (StringUtils.isNotBlank(sqxxid)) {
            Example exampleQlr = new Example(GxWwSqxxQlrDo.class);
            exampleQlr.createCriteria().andEqualTo("sqxxid", sqxxid);
            return hlwEntityMapper.selectByExample(exampleQlr);
        }
        return new ArrayList();
    }

    @Override
    public List<GxWwSqxxClxxDo> listGxWwSqxxClxxBySqxxid(String sqxxId) {
        if (StringUtils.isNotBlank(sqxxId)) {
            Example exampleClxx = new Example(GxWwSqxxClxxDo.class);
            exampleClxx.createCriteria().andEqualTo("sqxxid", sqxxId);
            List<GxWwSqxxClxxDo> wwSqxxClxxDoList = hlwEntityMapper.selectByExample(exampleClxx);
            if (CollectionUtils.isNotEmpty(wwSqxxClxxDoList)) {
                for (GxWwSqxxClxxDo gxWwSqxxClxxDo : wwSqxxClxxDoList) {
                    gxWwSqxxClxxDo.setFjxx(listGxWwSqxxFjxxByClid(gxWwSqxxClxxDo.getClid()));
                }
            }
            return wwSqxxClxxDoList;
        }
        return new ArrayList();
    }

    @Override
    public List<GxWwSqxxFjxxDo> listGxWwSqxxFjxxByClid(String clxxid) {
        if (StringUtils.isNotBlank(clxxid)) {
            Example exampleFjxx = new Example(GxWwSqxxFjxxDo.class);
            exampleFjxx.createCriteria().andEqualTo("clid", clxxid);
            return hlwEntityMapper.selectByExample(exampleFjxx);
        }
        return new ArrayList();
    }

    @Override
    public List<GxWwSwxxDo> listGxWwSwxxBySqxxid(String sqxxid) {
        if (StringUtils.isNotBlank(sqxxid)) {
            Example exampleSwxx = new Example(GxWwSwxxDo.class);
            exampleSwxx.createCriteria().andEqualTo("sqid", sqxxid);
            List<GxWwSwxxDo> gxwwSwxxDoList = hlwEntityMapper.selectByExample(exampleSwxx);
            if (CollectionUtils.isNotEmpty(gxwwSwxxDoList)) {
                for (GxWwSwxxDo gxWwSwxxDo : gxwwSwxxDoList) {
                    gxWwSwxxDo.setSwmx(listGxWwSwmxBySwid(gxWwSwxxDo.getSwid()));
                }
            }
            return gxwwSwxxDoList;
        }
        return new ArrayList();
    }

    @Override
    public List<GxWwSwmxDo> listGxWwSwmxBySwid(String swid) {
        if (StringUtils.isNotBlank(swid)) {
            Example exampleSwmx = new Example(GxWwSwmxDo.class);
            exampleSwmx.createCriteria().andEqualTo("swid", swid);
            return hlwEntityMapper.selectByExample(exampleSwmx);
        }
        return new ArrayList();
    }
}
