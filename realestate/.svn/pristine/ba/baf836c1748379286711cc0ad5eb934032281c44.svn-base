package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.service.AcceptBdcdyService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-13
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LogMessageClient logMessageClient;

    @Autowired
    private AcceptBdcdyService acceptBdcdyService;

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询Repo
     */
    @Autowired
    private Repo repo;

    @ResponseBody
    @RequestMapping("/refresh")
    public void refreshConfig(String code){
        LpbConfig.initLpbConfigByCode(code,"");
    }

    @ResponseBody
    @RequestMapping("listlog")
    public Page<AuditLogDto> listLog(Pageable pageable, String event, String principal, Long begin, Long end){
        return logMessageClient.listAuditLogs(pageable,event,principal,begin,end);
    }

    @ResponseBody
    @RequestMapping("/aoplog/open")
    public void openAopLog(){
        BuildingUtils.openAopLog();
    }

    @ResponseBody
    @RequestMapping("/aoplog/close")
    public void closeAopLog(){
        BuildingUtils.closeAopLog();
    }


    @GetMapping("/testbdcdywithqlr")
    Page<Map> testBdcdyWithQlrByPage(Pageable pageable,
                                      @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                      @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                     @RequestParam(name = "qlr",required = false) String qlr){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        Map<String,Object> queryMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm,zdtzm,paramMap);
        return repo.selectPaging("testBdcdyWithQlrByPageOrder", queryMap, pageable);
    }

    @GetMapping("/testbdcdywithqlr2")
    @ResponseStatus(code = HttpStatus.OK)
    Page<Map> testBdcdyWithQlr2ByPage(Pageable pageable,
                                     @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                     @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                     @RequestParam(name = "qlr",required = false) String qlr){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        Map<String,Object> queryMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm,zdtzm,paramMap);
        return repo.selectPaging("testBdcdyWithQlr2ByPageOrder", queryMap, pageable);
    }

    @GetMapping("/testbdcdywithqlr3")
    Page<Map> testBdcdy(Pageable pageable,
                        @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                        @RequestParam(name = "zdtzm",required = false) String zdtzm,
                        @RequestParam(name = "qlr",required = false) String qlr){

        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        return acceptBdcdyService.listTdAndDzwBdcdyByPage(pageable,qlxzAndZdtzm,zdtzm,"F","",paramMap);
    }

    private Map<String,Object> getTdDzwQueryBdcdyMap(String qlxzAndZdtzm, String zdtzm,Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(paramMap);
        String[] qlxzAndZdtzmArr = new String[0];
        String[] zdtzmArr = new String[0];
        if(StringUtils.isNotBlank(qlxzAndZdtzm)){
            qlxzAndZdtzmArr = qlxzAndZdtzm.split(",");
        }
        if(StringUtils.isNotBlank(zdtzm)){
            zdtzmArr = zdtzm.split(",");
        }
        resultMap.put("qlxzAndZdtzm", Arrays.asList(qlxzAndZdtzmArr));
        resultMap.put("zdtzm",Arrays.asList(zdtzmArr));
        return resultMap;
    }


    @GetMapping("/testbdcdywithqlr4")
    Page<Map> testBdcdyWithQlr4ByPage(Pageable pageable,
                                     @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                     @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                     @RequestParam(name = "qlr",required = false) String qlr){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        Map<String,Object> queryMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm,zdtzm,paramMap);
        return repo.selectPaging("testBdcdyWithQlr4ByPageOrder", queryMap, pageable);
    }


    @GetMapping("/testbdcdywithqlr5")
    Page<Map> testBdcdyWithQlr5ByPage(Pageable pageable,
                                      @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                      @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                      @RequestParam(name = "qlr",required = false) String qlr){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        Map<String,Object> queryMap = getTdDzwQueryBdcdyMap(qlxzAndZdtzm,zdtzm,paramMap);
        return repo.selectPaging("testBdcdyWithQlr5ByPageOrder", queryMap, pageable);
    }
}
