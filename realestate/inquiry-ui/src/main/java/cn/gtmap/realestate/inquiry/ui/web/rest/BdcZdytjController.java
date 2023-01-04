package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZdytjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZdytjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZdytjFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/09/08
 * @description 自定义统计
 */
@RestController
@RequestMapping(value = "/zdytj")
public class BdcZdytjController extends BaseController{

    @Autowired
    private BdcZdytjFeignService bdcZdytjFeignService;

    /**
     * 获取配置台账分页数据
     *
     * @param zdytjQO
     * @param pageable
     * @return
     * @date 2022/09/08
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping("/list/page")
    public Page<BdcZdytjDO> listDtcxPage(BdcZdytjQO zdytjQO, @LayuiPageable Pageable pageable){
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        return bdcZdytjFeignService.listZdytjPage(zdytjQO,pageable.getPageNumber(),pageable.getPageSize(), sort);
    }

    /**
     * 获取配置台账
     *
     * @return java.lang.Object
     * @date 2022/09/08
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object xxgkcxConfig(){
        JSONObject result = new JSONObject();

        JSONArray gridArray = JSONArray.parseArray("[" +
                "            {type: 'checkbox', fixed: 'left'}," +
                "            {title: '序号',width: 60, type:'numbers'}," +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '查询名称', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '查询代号', width: 100, align:'center'}, " +
                "            {field: 'url', title: '页面地址',  align:'center'}, " +
                "            {fixed: 'right', title: '操作', width: 200, align:'center',templet:'#rowTools'}, " +
                "]");
        result.put("grid",gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'新增', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "]");
        result.put("toolbar",toolbarArray);

        // 行内操作按钮
        JSONArray toolArray = JSONArray.parseArray("[" +
                "{layEvent:'change',class:'bdc-secondary-btn', text:'修改', renderer: 'changeOne'}," +
                "{layEvent:'del',class:'layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn', text:'删除', renderer: 'delOne'}]");
        result.put("tool",toolArray);

        return result;
    }

    /**
     * @param bdcZdytjDO
     * @return void
     * @date 2022/09/08
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/save/all")
    public void saveAll(BdcZdytjDO bdcZdytjDO){
        bdcZdytjFeignService.saveAll(bdcZdytjDO);
    }

    /**
     * 获取查询配置，用于前台渲染页面
     *
     * @param cxdh
     * @return
     * @date 2022/09/08
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping(value = "/get/{cxdh}")
    public BdcZdytjDO listCxConfig(@PathVariable("cxdh") String cxdh){

        if (StringUtils.isBlank(cxdh)){
            throw new MissingArgumentException("查询服务代号不能为空");
        }

        return bdcZdytjFeignService.getCxxxByCxdh(cxdh);
    }

    /**
     * 测试配置的sql是否能正常运行
     *
     * @param bdcZdytjDO
     * @return
     * @date 2022/09/22
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/checksql")
    public Boolean checkSql(BdcZdytjDO bdcZdytjDO){
        return bdcZdytjFeignService.checkSql(bdcZdytjDO);
    }

    /**
     * 获取查询配置，用于前台渲染页面
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2022/09/08
     * @author hanyi
     */
    @GetMapping(value = "/gettbxx/{cxdh}")
    public BdcZdttjTbxxDTO gettbxx(@PathVariable("cxdh") String cxdh){

        if (StringUtils.isBlank(cxdh)){
            throw new MissingArgumentException("查询服务代号");
        }

        return bdcZdytjFeignService.getTbxxByCxdh(cxdh);
    }

    /**
     * 删除对应的cxid的查询配置
     *
     * @param cxid
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @DeleteMapping("del/{cxid}")
    public void delCxConfig(@PathVariable("cxid") String cxid){
        if (StringUtils.isBlank(cxid)){
            throw new MissingArgumentException("ID");
        }
        bdcZdytjFeignService.delCxConfig(cxid);
    }


    /**
     * 检查查询代号是否已使用
     *
     * @param cxdh 查询代号
     * @return int 1表示未使用，0表示已使用
     * @date 2022/09/19
     * @author wuao
     */
    @GetMapping("check/cxdh")
    public int checkCxdhExist(@RequestParam("cxdh") String cxdh){
        BdcZdytjDO bdcZdytjDO = bdcZdytjFeignService.getCxxxByCxdh(cxdh);
        if (bdcZdytjDO == null){
            return 1;
        }
        return 0;
    }
}
