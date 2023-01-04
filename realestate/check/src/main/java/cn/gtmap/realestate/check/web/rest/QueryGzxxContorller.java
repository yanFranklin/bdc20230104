package cn.gtmap.realestate.check.web.rest;


import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.check.core.vo.SfhlGzVO;
import cn.gtmap.realestate.check.service.GzxxService;
import cn.gtmap.realestate.check.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.check.CheckGzYyJjfaDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.domain.check.CheckZdGzfzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则信息增删改查
 * @author xyq
 */
@Controller
@RequestMapping("/rest/v1.0/queryGzxx")
public class QueryGzxxContorller extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(QueryGzxxContorller.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private GzxxService gzxxService;
    @Autowired
    private ProcessDefinitionClient processDefinitionClient;


    @GetMapping(value = "/lcList")
    @ResponseBody
    public List<ProcessDefData> lc() {
        return processDefinitionClient.getAllProcessDefData();
    }

    @GetMapping(value = "/gzxxList")
    @ResponseBody
    public List<CheckGzxxDO> gzxxList() {
        return entityMapper.select(new CheckGzxxDO());
    }

    @GetMapping(value = "/gzfzList")
    @ResponseBody
    public List<CheckZdGzfzDO> gzfzList() {
        return entityMapper.select(new CheckZdGzfzDO());
    }

    /**
     * 列表显示
     * @param pageable
     * @param gzcode 规则Code
     * @param tsxx 提示信息
     * @return
     * @author lst
     */
    @ResponseBody
    @RequestMapping("/getGzxxListPagesJson")
    public Object getGzxxList(@LayuiPageable Pageable pageable, String gzcode, String tsxx,Integer sfhl) {
        HashMap<String, Object> map = new HashMap<String, Object>(3);
        if (StringUtils.isNotBlank(gzcode)) {
            map.put("gzcode", gzcode);
        }
        if (StringUtils.isNotBlank(tsxx)) {
            map.put("tsxx", tsxx);
        }
        if (sfhl != null) {
            map.put("sfhl", sfhl);
        }
        return addLayUiCode(repository.selectPaging("getGzxxListByPage", map, pageable));
    }

    /**
     * 保存和修改
     * @param model
     * @param bdcGzxx 规则信息实体
     * @return
     * @author xyq
     */
    @ResponseBody
    @RequestMapping(value = "/saveGzxx")
    public Map saveLimitField(Model model,@RequestBody CheckGzxxDO bdcGzxx) {
        HashMap map = new HashMap(2);
        String result = "保存成功";
        try {
            if (StringUtils.isNoneBlank(bdcGzxx.getGzid())){
                entityMapper.updateByPrimaryKeyNull(bdcGzxx);
            } else {
                bdcGzxx.setGzid(UUIDGenerator.generate());
                entityMapper.insertSelective(bdcGzxx);
        }
        } catch (Exception e) {
            logger.error("失败",e);
            result = "保存失败";
        }
        map.put("result", result);
        map.put("gzid", bdcGzxx.getGzid());
        return map;
    }

    /**
     * 删除
     * @param model
     * @param ids 规则id 支持多条删除
     * @return
     * @author xyq
     */
    @ResponseBody
    @RequestMapping("/delGzxx")
    public Map delGzxx(Model model,@RequestBody List<String> ids) {
        HashMap map = new HashMap(1);
        String result = "删除成功！";
        try {
            if (CollectionUtils.isNotEmpty(ids)){
                for (String id:ids) {
                    // 删除原因和解决方案
                    Example example = new Example(CheckGzYyJjfaDO.class);
                    example.createCriteria().andEqualTo("gzid",id);
                    entityMapper.deleteByExample(example);
                    // 删除规则信息
                    entityMapper.deleteByPrimaryKey(CheckGzxxDO.class, id);
                }
            }
        } catch (Exception e) {
            logger.error("失败",e);
            result = "删除失败！";
        }
        map.put("result", result);
        return map;
    }

    /**
     * 批量更新是否忽略字段
     * @return
     * @author
     */
    @ResponseBody
    @RequestMapping("/sfhlGzxx")
    public Map sfhlGzxx(@RequestBody SfhlGzVO sfhlGzVO) {
        HashMap map = new HashMap(1);
        String result = "操作成功！";
        try {
            if (CollectionUtils.isNotEmpty(sfhlGzVO.getGzidList()) &&sfhlGzVO.getSfhl() != null){
                gzxxService.updateBatchCheckGzxxSfhl(sfhlGzVO.getSfhl(),sfhlGzVO.getGzidList());
            }
        } catch (Exception e) {
            logger.error("操作失败",e);
            result = "操作失败！";
        }
        map.put("result", result);
        return map;
    }

    /**
     * 动态获取前台展示的SQL
     * @param model
     * @param gzcode 规则Code 对应的是Mybatis里的id
     * @return
     * @author xyq
     */
    @ResponseBody
    @RequestMapping(value = "querySql")
    public Map querySql(Model model, String gzcode) {
        HashMap map = new HashMap(1);
        if(StringUtils.isNoneBlank(gzcode)){
            MappedStatement mappedStatement = null;
            try {
                 mappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement("cn.gtmap.realestate.check.core.mapper.SsMapper." + gzcode);
            }catch(IllegalArgumentException e){
                // 并非吃掉异常，因为getMappedStatement方法中获取map中的值ID不存在会报错，所以不存在值换成标准路径获取  ccx 2019-04-30
                if(mappedStatement == null){
                    try {
                        mappedStatement = sqlSessionFactory.getConfiguration().getMappedStatement("cn.gtmap.realestate.check.core.mapper.RuleSqlMapper."+gzcode);
                    }catch (Exception o){}
                }
                if(mappedStatement == null){
                    map.put("querySql","") ;
                }
            }
            if (mappedStatement != null) {
                BoundSql boundSql = mappedStatement.getBoundSql(null);
                if (boundSql != null) {
                    map.put("querySql",boundSql.getSql()) ;
                }
            }
        }
        return map;
    }


    /**
     * 质检情况汇总列表显示
     * @param pageable
     * @param groupid 分组dm
     * @param gzid 规则id
     * @return
     * @author lst
     */
    @ResponseBody
    @RequestMapping("/getGzxxGroupPagesJson")
    public Object getGzxxGroupPagesJson(@LayuiPageable Pageable pageable, Integer gzfz, String gzid, String gzcode, String glcws) {
        HashMap<String, Object> map = new HashMap<String, Object>(4);
        if (StringUtils.isNotBlank(gzid)) {
            map.put("gzid", gzid);
        }
        if (gzfz!=null) {
            map.put("gzfz", gzfz);
        }
        if (StringUtils.isNotBlank(gzcode)) {
            map.put("gzcode", gzcode);
        }
        if (StringUtils.isNotBlank(glcws) && StringUtils.equals(glcws, CommonConstantUtils.SF_S_DM.toString())) {
            map.put("glcws", glcws);
        }
        return  addLayUiCode(repository.selectPaging("getGzxxGroupByPage", map, pageable));
    }


    /**
     * 质检情况汇总列表显示
     * @param pageable
     * @param groupid 分组dm
     * @param gzid 规则id
     * @return
     * @author lst
     */
    @ResponseBody
    @RequestMapping("/exportGzxxGroup")
    public void exportGzxxGroup(String groupmc, String gzid, String gzcode, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<String, Object>(3);
        if (StringUtils.isNotBlank(gzid)) {
            map.put("gzid", gzid);
        }
        if (StringUtils.isNotBlank(groupmc)) {
            map.put("gzfz", groupmc);
        }
        if (StringUtils.isNotBlank(gzcode)) {
            map.put("gzcode", gzcode);
        }
        List<Map<String,Object>> list = gzxxService.queryGzxxList(map);
        gzxxService.export(list,response);
    }


    /**
     * 查询错误项目数据  ccx 2019-05-13
     * @param pageable
     * @param gzid
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCwXmData")
    public Object queryCwXmData(@LayuiPageable Pageable pageable, String gzid) {
        HashMap<String, Object> map = new HashMap<String, Object>(1);
        if (StringUtils.isNotBlank(gzid)) {
            map.put("gzid", gzid);
        }
        return  addLayUiCode(repository.selectPaging("queryCwXmDataByPage", map, pageable));
    }
}




