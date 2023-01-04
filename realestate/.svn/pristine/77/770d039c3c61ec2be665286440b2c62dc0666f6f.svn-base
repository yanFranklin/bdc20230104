package cn.gtmap.realestate.check.web.rest;

import cn.gtmap.realestate.check.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.check.CheckGzYyJjfaDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lst 2019/2/28
 * @description  原因及解决方案
 */
@Controller
@RequestMapping("/rest/v1.0/queryYyAndJjfa")
public class QueryYyAndJjfaController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(QueryYyAndJjfaController.class);
    @Autowired
    private EntityMapper entityMapper;


    /**
     * 获取原因及解决方案数据
     * @param gzid
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryYyAndJjfa")
    public List<CheckGzYyJjfaDO> queryYyAndJjfa(String gzid) {
        List<CheckGzYyJjfaDO> resultList = new ArrayList<CheckGzYyJjfaDO>();
        if(StringUtils.isNotBlank(gzid)){
            Example example = new Example(CheckGzYyJjfaDO.class);
            example.createCriteria().andEqualTo("gzid",gzid);
            resultList = entityMapper.selectByExample(example);
        }
        return resultList;
    }


    @ResponseBody
    @RequestMapping("/queryYyAndJjfaPageJson")
    public Object queryYyAndJjfaPageJson(@LayuiPageable Pageable pageable, String gzid) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gzid", gzid);
        return  addLayUiCode(repository.selectPaging("queryYyAndJjfaByPage", map, pageable));
    }

    /**
     * 保存原因及解决方案数据
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveYyAndJjfa")
    public Map saveYyAndJjfa(CheckGzYyJjfaDO bdcGzYyJjfa) {
        Map map = new HashMap();
        String result = "保存成功";
        try {
            if (StringUtils.isNoneBlank(bdcGzYyJjfa.getId())) {
                entityMapper.updateByPrimaryKeySelective(bdcGzYyJjfa);
            }else {
                bdcGzYyJjfa.setId(UUIDGenerator.generate());
                entityMapper.insertSelective(bdcGzYyJjfa);
            }
        } catch (Exception e) {
            logger.error(null, e);
            result = "保存失败";
        }
        map.put("result", result);
        return map;
    }
    /**
     * 保存原因及解决方案数据
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delYyAndJjfa")
    public Map delYyAndJjfa(@RequestBody List<String> ids) {
        Map map = new HashMap();
        String result = "删除成功";
        try {
            if(CollectionUtils.isNotEmpty(ids)){
                for (String id:ids) {
                    entityMapper.deleteByPrimaryKey(CheckGzYyJjfaDO.class,id);
                }
            }else{
                result = "删除失败";
            }
        } catch (Exception e) {
            logger.error(null, e);
            result = "删除失败";
        }
        map.put("result", result);
        return map;
    }
}
