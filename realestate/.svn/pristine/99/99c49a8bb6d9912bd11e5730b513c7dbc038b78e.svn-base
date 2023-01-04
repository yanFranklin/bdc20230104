package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtQlqtzkFjPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcXtJrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjlxDjxlQllxVO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjxlPzVO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcSlSjclPzVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcPzFzDTO;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcDjgxVO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/6/12
 * @description 流程配置
 */
@RestController
@RequestMapping("/rest/v1.0/lcpz")
public class BdcLcpzController extends BaseController {

    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcSlXztzPzFeignService bdcSlXztzPzFeignService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;
    @Autowired
    BdcYxbdcdyKgPzFeignService bdcYxbdcdyKgPzFeignService;
    @Autowired
    BdcSlSfxmPzFeignService bdcSlSfxmPzFeignService;
    @Autowired
    BdcSlSjclPzFeignService bdcSlSjclPzFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcXtQlqtzkFjPzFeignService bdcXtQlqtzkFjPzFeignService;
    @Autowired
    BdcDjyySjclPzFeignService bdcDjyySjclPzFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcXtJrFeignService bdcXtJrFeignService;

    /**
     * @param zds 字段名称（djlx,qllx等） 以英文逗号 分隔
     * @return Map {"qllx",[{DM:1,MC:权利1}]}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取字典信息
     */
    @PostMapping("/zdxx")
    public Map queryBdcZdxx(@RequestBody String zds, @RequestParam(value = "djxl", required = false) String djxl) {
        if (StringUtils.isBlank(zds)) {
            throw new MissingArgumentException("获取字典表的名称不能为空！");
        }
        List<String> zdList = Lists.newArrayList(zds.split(","));
        Map<String, Object> zdMap = Maps.newHashMap();
        zdList.forEach(zd -> {
            /**
             * 单元号权利类型为 除去限制权利之后的权利类型字典表
             */
            if (StringUtils.equals(zd, CommonConstantUtils.BDC_DYHQLLX)) {
                List<Map> zdxx = bdcZdFeignService.queryBdcZd(CommonConstantUtils.BDC_QLLX);
                if (CollectionUtils.isNotEmpty(zdxx)) {
                    zdxx = zdxx.stream().filter(bdczd ->
                            !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdczd.get(CommonConstantUtils.ZD_DM))).collect(Collectors.toList());
                    zdMap.put(zd, zdxx);
                    return;
                }
            }
            //查询登记小类和登记原因的关系作为字典项给页面
            if (StringUtils.equals("djyy", zd) && StringUtils.isNotBlank(djxl)) {
                BdcDjxlDjyyQO bdcDjxlDjyyQO =new BdcDjxlDjyyQO();
                bdcDjxlDjyyQO.setDjxl(djxl);
                List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList = bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
                if (CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOList)) {
                    zdMap.put("djyy", bdcDjxlDjyyGxDOList);
                }else {
                    zdMap.put("djyy", new ArrayList<>());
                }
                return;
            }
            List<Map> zdxx = bdcZdFeignService.queryBdcZd(zd);
            if (CollectionUtils.isEmpty(zdxx)) {
                zdxx = bdcSlZdFeignService.queryBdcSlzd(zd);
            }
            zdMap.put(zd, zdxx);
        });
        return zdMap;
    }

    /**
     * @param gzldyid
     * @param djxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    @GetMapping("/bdcDjxlPz")
    public Object queryBdcDjxlPz(String gzldyid, String djxl) {
        if (StringUtils.isAnyBlank(gzldyid, djxl)) {
            throw new AppException("工作流定义ID 和 登记小类代码 不能为空！");
        }
        List<BdcDjxlPzDO> bdcDjxlPzDOS = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyid(gzldyid);
        bdcDjxlPzDOS = bdcDjxlPzDOS.stream().filter(bdcDjxlPzDO -> StringUtils.equals(djxl, bdcDjxlPzDO.getDjxl())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOS)) {
            return bdcDjxlPzDOS.get(0);
        }
        return new BdcDjxlPzDO();
    }

    /**
     * @param gzldyid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取受理限制台账
     */
    @GetMapping("/bdcSlxztz")
    public Object queryBdcSlxztz(String gzldyid) {
        if (StringUtils.isAnyBlank(gzldyid)) {
            throw new AppException("工作流定义ID 不能为空！");
        }
        return bdcSlXztzPzFeignService.queryBdcSlXztzPzDOByGzldyid(gzldyid);
    }

    /**
     * @param gzldyid
     * @return BdcYxbdcdyKgPzDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取已选不动产单元开关配置
     */
    @GetMapping("/bdcYxbdcdykg")
    public Object queryBdcYxBdcdykg(String gzldyid,String djxl) {
        if (StringUtils.isAnyBlank(gzldyid)) {
            throw new AppException("工作流定义ID 不能为空！");
        }
        List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOS = bdcYxbdcdyKgPzFeignService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
        if(CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)){
            BdcYxbdcdyKgPzDO lcYxbdcdyKgPz =null;
            for(BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO:bdcYxbdcdyKgPzDOS) {
                //首先匹配djxl
                if (StringUtils.isNotBlank(djxl) &&StringUtils.equals(djxl, bdcYxbdcdyKgPzDO.getDjxl())){
                    return bdcYxbdcdyKgPzDO;
                }else if(StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl())){
                    lcYxbdcdyKgPz =bdcYxbdcdyKgPzDO;
                }
            }
            //djxl匹配不上,以流程的配置来
            if(lcYxbdcdyKgPz != null) {
                return lcYxbdcdyKgPz;
            }
        }
        return null;
    }

    /**
     * @param djxl 登记小类
     * @return 收费项目配置
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收费项目配置
     */
    @GetMapping("/bcSlSfxmPz")
    public Object queryBdcSlSfxmPz(String djxl) {
        if (StringUtils.isAnyBlank(djxl)) {
            throw new AppException("登记小类 不能为空！");
        }
        return bdcSlSfxmPzFeignService.listBdcSlSfxmPzByDjxl(djxl);
    }

    /**
     * @param pageable
     * @param bdcDjxlPzQO
     * @return Page<BdcDjxlPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类分页配置
     */
    @GetMapping("/bdcDjxlPz/list")
    public Object listBdcDjxlPz(@LayuiPageable Pageable pageable, BdcDjxlPzQO bdcDjxlPzQO) {
        String bdcDjxlPzJson = JSON.toJSONString(bdcDjxlPzQO);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<ProcessDefData> processDefDataList = workFlowUtils.getAllProcessDefData();
        Page<BdcDjxlPzDO> bdcDjxlPzDOPage = bdcDjxlPzFeignService.listBdcDjxlPzByPage(pageable, bdcDjxlPzJson);
        List<BdcDjxlPzVO> bdcDjxlPzVOList = Lists.newArrayListWithCapacity(bdcDjxlPzDOPage.getContent().size());
        bdcDjxlPzDOPage.getContent().forEach(djxlPzDO -> {
            BdcDjxlPzVO bdcDjxlPzVO = new BdcDjxlPzVO();
            /**
             * 复制属性
             */
            BeansResolveUtils.clonePropertiesValue(djxlPzDO, bdcDjxlPzVO);
            /**
             * 转换 登记小类
             */
            bdcDjxlPzVO.setDjxlMc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(djxlPzDO.getDjxl()), zdMap.get("djxl")));
            /**
             * 转换 不动产类型
             */
            bdcDjxlPzVO.setBdclxMc(StringToolUtils.convertBeanPropertyValueOfZd(djxlPzDO.getBdclx(), zdMap.get("bdclx")));
            /**
             * 转换 权利类型
             */
            bdcDjxlPzVO.setQllxMc(StringToolUtils.convertBeanPropertyValueOfZd(djxlPzDO.getQllx(), zdMap.get("qllx")));
            /**
             * 转换 单元号权利类型
             */
            bdcDjxlPzVO.setDyhqllxMc(StringToolUtils.convertBeanPropertyValueOfZd(djxlPzDO.getDyhqllx(), zdMap.get("qllx")));
            /**
             * 转换 工作流定义名称
             */
            List<ProcessDefData> gzldyList = processDefDataList.stream().filter(processDefData -> StringUtils.equals(processDefData.getKey(), djxlPzDO.getGzldyid())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(gzldyList)) {
                bdcDjxlPzVO.setGzldymc(gzldyList.get(0).getName());
            }
            bdcDjxlPzVOList.add(bdcDjxlPzVO);
        });
        /**
         * 封装返回分页信息
         */
        Pageable pageRequest = new PageRequest(bdcDjxlPzDOPage.getNumber(), bdcDjxlPzDOPage.getSize());
        return super.addLayUiCode(new PageImpl(bdcDjxlPzVOList, pageRequest, bdcDjxlPzDOPage.getTotalElements()));

    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 登记类型、登记小类、权利类型、登记原因 配置分页数据
     */
    @GetMapping("/bdcDjlxDjxlQllxDjyy/list")
    public Object queryDjlxDjxlQllx(@LayuiPageable Pageable pageable, BdcDjlxDjxlQllxVO bdcDjlxDjxlQllxVO) {
        String bdcDjlxDjxlQllxJson = JSON.toJSONString(bdcDjlxDjxlQllxVO);
        Page<BdcDjlxDjxlQllxVO> bdcDjlxDjxlQllxVOPage = bdcCshXtPzFeignService.listBdcDjlxDjxlQllx(pageable, bdcDjlxDjxlQllxJson);
        return addLayUiCode(bdcDjlxDjxlQllxVOPage);
    }

    /**
     * @param bdcDjxlPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增登记小类配置
     */
    @PutMapping("/bdcDjxlPz")
    public BdcDjxlPzDO insertBdcDjxlPz(@RequestBody BdcDjxlPzDO bdcDjxlPzDO) {
        if (bdcDjxlPzDO == null) {
            throw new MissingArgumentException("新增登记小类配置 不能为空！");
        }
        //校验顺序号
        BdcDjxlPzDO bdcDjxlPzQO =new BdcDjxlPzDO();
        bdcDjxlPzQO.setGzldyid(bdcDjxlPzDO.getGzldyid());
        bdcDjxlPzQO.setDyhqllx(bdcDjxlPzDO.getDyhqllx());
        int maxSxh =bdcDjxlPzFeignService.queryDjxlPzMaxSxh(bdcDjxlPzQO);
        if(bdcDjxlPzDO.getSxh() -1 >maxSxh){
            throw new AppException("顺序号输入错误,当前顺序号要小于等于" +(maxSxh+1));
        }
        bdcDjxlPzDO.setPzxgsj(new Date());
        /**
         * 新增登记小类配置
         */
        bdcDjxlPzFeignService.saveBdcDjxlPzDO(bdcDjxlPzDO);
        return bdcDjxlPzDO;
    }

    /**
     * @param bdcDjxlPzDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除登记小类配置
     */
    @DeleteMapping("/bdcDjxlPz")
    public int deleteBdcDjxlPz(@RequestBody List<BdcDjxlPzDO> bdcDjxlPzDOList) {
        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            throw new MissingArgumentException("删除的 登记小类配置 不能为空！");
        }
        /**
         * 删除登记小类配置
         */
        return bdcDjxlPzFeignService.deleteBdcDjxlPzDO(bdcDjxlPzDOList);
    }
    /**
     * @param bdcDjlxDjxlQllxVO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增登记类型、登记小类、权利类型、登记原因
     */
    @PutMapping("/bdcDjlxDjxlQllxDjyy")
    public void insertDjlxDjxlQllx(BdcDjlxDjxlQllxVO bdcDjlxDjxlQllxVO, BdcDjxlPzDO bdcDjxlPzDO) {
        if (bdcDjlxDjxlQllxVO == null) {
            throw new MissingArgumentException("新增登记类型、登记小类、权利类型、登记原因 不能为空！");
        }

        /**
         * 新增 登记类型 登记小类关系
         */
        BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO = new BdcDjlxDjxlGxDO();
        bdcDjlxDjxlGxDO.setId(StringUtils.isBlank(bdcDjlxDjxlQllxVO.getDjlxDjxlGxId()) ? UUIDGenerator.generate16() : bdcDjlxDjxlQllxVO.getDjlxDjxlGxId());
        bdcDjlxDjxlGxDO.setDjxl(bdcDjlxDjxlQllxVO.getDjxlDm());
        bdcDjlxDjxlGxDO.setDjlx(bdcDjlxDjxlQllxVO.getDjlxDm());
        bdcCshXtPzFeignService.insertBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
        /**
         * 新增 登记小类 登记原因 关系
         */
        BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO = new BdcDjxlDjyyGxDO();
        bdcDjxlDjyyGxDO.setDjxl(bdcDjlxDjxlQllxVO.getDjxlDm());
        bdcDjxlDjyyGxDO.setDjyy(bdcDjlxDjxlQllxVO.getDjyy());
        bdcDjxlDjyyGxDO.setSfmr(bdcDjlxDjxlQllxVO.getDjyySfmr());
        bdcDjxlDjyyGxDO.setId(StringUtils.isBlank(bdcDjlxDjxlQllxVO.getDjxlDjyyGxId()) ? UUIDGenerator.generate16() : bdcDjlxDjxlQllxVO.getDjxlDjyyGxId());
        bdcCshXtPzFeignService.insertBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
        /**
         * 新增 登记小类 权利类型 关系
         */
        BdcDjxlQllxGxDO bdcDjxlQllxGxDO = new BdcDjxlQllxGxDO();
        bdcDjxlQllxGxDO.setId(StringUtils.isBlank(bdcDjlxDjxlQllxVO.getDjxlQllxGxId()) ? UUIDGenerator.generate16() : bdcDjlxDjxlQllxVO.getDjxlQllxGxId());
        bdcDjxlQllxGxDO.setDjxl(bdcDjlxDjxlQllxVO.getDjxlDm());
        bdcDjxlQllxGxDO.setQllx(bdcDjlxDjxlQllxVO.getQllxDm());
        bdcCshXtPzFeignService.insertBdcDjxlQllxGx(bdcDjxlQllxGxDO);
    }

    /**
     * @param djxl 登记小类
     * @return 登记类型和登记小类关系表、登记小类和权利类型关系表、登记小类和登记原因关系表
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/bdcDjgx")
    public BdcDjgxVO queryBdcDjgx(String djxl) {
        if (StringUtils.isAnyBlank(djxl)) {
            throw new AppException("登记小类 不能为空！");
        }
        BdcCshFwkgDO bdcCshFwkgDO = new BdcCshFwkgDO();
        bdcCshFwkgDO.setDjxl(djxl);
        List<BdcCshFwkgDO> bdcCshFwkgDOS = bdcCshXtPzFeignService.listBdcCshFwkg(bdcCshFwkgDO);
        BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO = new BdcDjlxDjxlGxDO();
        bdcDjlxDjxlGxDO.setDjxl(djxl);
        List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOS = bdcCshXtPzFeignService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
        BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO = new BdcDjxlDjyyGxDO();
        bdcDjxlDjyyGxDO.setDjxl(djxl);
        List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOS = bdcCshXtPzFeignService.listBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
        BdcDjxlQllxGxDO bdcDjxlQllxGxDO = new BdcDjxlQllxGxDO();
        bdcDjxlQllxGxDO.setDjxl(djxl);
        List<BdcDjxlQllxGxDO> bdcDjxlQllxGxDOS = bdcCshXtPzFeignService.listBdcDjxlQllxGx(bdcDjxlQllxGxDO);
        BdcDjgxVO bdcDjgxVO = new BdcDjgxVO();
        if (CollectionUtils.isNotEmpty(bdcCshFwkgDOS)) {
            bdcDjgxVO.setBdcCshFwkgDO(bdcCshFwkgDOS.get(0));
        }
        if (CollectionUtils.isNotEmpty(bdcDjlxDjxlGxDOS)) {
            bdcDjgxVO.setBdcDjlxDjxlGxDO(bdcDjlxDjxlGxDOS.get(0));
        }
        if (CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOS)) {
            bdcDjgxVO.setBdcDjxlDjyyGxDO(bdcDjxlDjyyGxDOS.get(0));
        }
        if (CollectionUtils.isNotEmpty(bdcDjxlQllxGxDOS)) {
            bdcDjgxVO.setBdcDjxlQllxGxDO(bdcDjxlQllxGxDOS.get(0));
        }
        return bdcDjgxVO;
    }

    /**
     * @param djxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收费项目配置
     */
    @GetMapping("/sfxmpz/page")
    public Object querySfxmpzPage(@LayuiPageable Pageable pageable, String djxl, String sfxmbz, String sfxmmc) {
        if (StringUtils.isBlank(djxl)) {
            throw new MissingArgumentException("登记小类参数 不能为空！");
        }
        BdcSlSfxmPzDO bdcSlSfxmPzDO = new BdcSlSfxmPzDO();
        bdcSlSfxmPzDO.setDjxl(djxl);
        if (StringUtils.isNotBlank(sfxmmc)) {
            bdcSlSfxmPzDO.setSfxmmc(sfxmmc);
        }
        if (StringUtils.isNotBlank(sfxmbz)) {
            bdcSlSfxmPzDO.setSfxmbz(sfxmbz);
        }
        String bdcSlSfxmPzJson = JSON.toJSONString(bdcSlSfxmPzDO);
        Page<BdcSlSfxmPzDO> sfxmpzPage = bdcSlSfxmPzFeignService.listBdcSlSfxmPzByPage(pageable, bdcSlSfxmPzJson);
        return super.addLayUiCode(sfxmpzPage);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/sfxmbz")
    public Object querySfxmpzBz(String sfxmdm) {
        if (StringUtils.isBlank(sfxmdm)) {
            throw new MissingArgumentException("收费项目代码 不能为空！");
        }
        List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOS = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(sfxmdm);
        if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOS)) {
            return bdcSlSfxmSfbzDOS.get(0);
        }
        return Lists.newArrayList();
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存收费项目配置
     */
    @PutMapping("/sfxmpz")
    public Object insertSfxmpz(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        if (bdcSlSfxmPzDO == null || StringUtils.isBlank(bdcSlSfxmPzDO.getDjxl())) {
            throw new MissingArgumentException("保存的收费项目配置或者登记小类不能为空！");
        }
        return bdcSlSfxmPzFeignService.saveBdcSlSfxmPzDO(bdcSlSfxmPzDO);
    }

    /**
     * @param  bdcSlSfxmPzDO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取最大的收费项目配置序号
     */
    @PostMapping("/querySfxmPzMaxSxh")
    public Object querySfxmPzMaxSxh(@RequestBody BdcSlSfxmPzDO bdcSlSfxmPzDO) {
        if (bdcSlSfxmPzDO == null || StringUtils.isBlank(bdcSlSfxmPzDO.getDjxl())) {
            throw new MissingArgumentException("收费项目配置或者登记小类不能为空！");
        }
        return bdcSlSfxmPzFeignService.querySfxmPzMaxSxh(bdcSlSfxmPzDO);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除收费项目配置
     */
    @DeleteMapping("/sfxmpz")
    public Object deleteSfxmpz(@RequestBody List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList) {
        if (CollectionUtils.isEmpty(bdcSlSfxmPzDOList)) {
            throw new MissingArgumentException("保存的收费项目配置不能为空！");
        }
        return bdcSlSfxmPzFeignService.deleteBdcSlSfxmPzDO(bdcSlSfxmPzDOList);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料
     */
    @GetMapping("/sjclpz/page")
    public Object querySjclPage(@LayuiPageable Pageable pageable, String djxl, String clmc, Integer sjlx) {
        if (StringUtils.isBlank(djxl)) {
            throw new MissingArgumentException("登记小类参数 不能为空！");
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<BdcSlSjclPzVO> bdcSlSjclPzVOS = Lists.newArrayList();
        BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
        bdcSlSjclPzDO.setDjxl(djxl);
        if (StringUtils.isNotBlank(clmc)) {
            bdcSlSjclPzDO.setClmc(clmc);
        }
        if (sjlx != null) {
            bdcSlSjclPzDO.setSjlx(sjlx);
        }
        String bdcSlSjclPzJson = JSON.toJSONString(bdcSlSjclPzDO);
        Page<BdcSlSjclPzDO> sjclPzByPage = bdcSlSjclPzFeignService.listBdcSlSjclPzByPage(pageable, bdcSlSjclPzJson);
        /**
         * 获取收件类型名称
         */
        sjclPzByPage.getContent().forEach(sjclPzDO -> {
            /**
             *复制属性值
             */
            BdcSlSjclPzVO bdcSlSjclPzVO = new BdcSlSjclPzVO();
            BeansResolveUtils.clonePropertiesValue(sjclPzDO, bdcSlSjclPzVO);
            if (MapUtils.isNotEmpty(zdMap)) {
                String sjlxMc = StringToolUtils.convertBeanPropertyValueOfZd(sjclPzDO.getSjlx(), zdMap.get("sjlx"));
                bdcSlSjclPzVO.setSjlxMc(sjlxMc);
            }

            bdcSlSjclPzVO.setSqbmMc(sjclPzDO.getSqbm());
            bdcSlSjclPzVOS.add(bdcSlSjclPzVO);
        });

        /**
         * 封装返回分页信息
         */
        Pageable pageRequest = new PageRequest(sjclPzByPage.getNumber(), sjclPzByPage.getSize());
        return super.addLayUiCode(new PageImpl(bdcSlSjclPzVOS, pageRequest, sjclPzByPage.getTotalElements()));
    }



    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存收件材料配置
     */
    @PutMapping("/sjclpz")
    public Object insertSjclpz(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO) {
        if (bdcSlSjclPzDO == null || StringUtils.isBlank(bdcSlSjclPzDO.getDjxl())) {
            throw new MissingArgumentException("保存的收件材料配置或者登记小类不能为空！");
        }
        bdcSlSjclPzDO.setClmc(StringUtils.trim(bdcSlSjclPzDO.getClmc()));
        return bdcSlSjclPzFeignService.saveBdcSlSjclPzDO(bdcSlSjclPzDO);
    }

    /**
     * @param  bdcSlSjclPzDO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料配置最大序号
     */
    @PostMapping("/querySjclPzMaxSxh")
    public Object queryDjyySjclPzMaxSxh(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO) {
        if (bdcSlSjclPzDO == null || StringUtils.isBlank(bdcSlSjclPzDO.getDjxl())) {
            throw new MissingArgumentException("收件材料配置或者登记小类不能为空！");
        }
        return bdcSlSjclPzFeignService.querySjclPzMaxSxh(bdcSlSjclPzDO);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @DeleteMapping("/sjclpz")
    public Object deleteSjclpz(@RequestBody List<BdcSlSjclPzDO> bdcSlSjclPzDOList) {
        if (CollectionUtils.isEmpty(bdcSlSjclPzDOList)) {
            throw new MissingArgumentException("删除的收件材料配置不能为空！");
        }
        return bdcSlSjclPzFeignService.deleteBdcSlSjclPzDO(bdcSlSjclPzDOList);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料
     */
    @GetMapping("/djyysjclpz/page")
    public Object queryDjyySjclPage(@LayuiPageable Pageable pageable, BdcDjyySjclPzDO bdcDjyySjclPzDO) {
        if (StringUtils.isBlank(bdcDjyySjclPzDO.getDjxl())) {
            throw new MissingArgumentException("登记小类参数 不能为空！");
        }
        String bdcSlSjclPzJson = JSON.toJSONString(bdcDjyySjclPzDO);
        return addLayUiCode(bdcDjyySjclPzFeignService.listBdcDjyySjclPzByPage(pageable, bdcSlSjclPzJson));

    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存登记原因收件材料配置
     */
    @PutMapping("/djyysjclpz")
    public Object insertDjyysjclpz(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO) {
        if (bdcDjyySjclPzDO == null || StringUtils.isBlank(bdcDjyySjclPzDO.getDjxl())) {
            throw new MissingArgumentException("保存的登记原因收件材料配置或者登记小类不能为空！");
        }
        return bdcDjyySjclPzFeignService.saveBdcDjyySjclPz(bdcDjyySjclPzDO);
    }

    /**
     * @param  bdcDjyySjclPzDO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料登记原因配置最大序号
     */
    @PostMapping("/queryDjyySjclPzMaxSxh")
    public Object queryDjyySjclPzMaxSxh(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO) {
        if (bdcDjyySjclPzDO == null || StringUtils.isBlank(bdcDjyySjclPzDO.getDjxl())) {
            throw new MissingArgumentException("收件材料登记原因配置或者登记小类不能为空！");
        }
        return bdcDjyySjclPzFeignService.queryDjyySjclPzMaxSxh(bdcDjyySjclPzDO);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @DeleteMapping("/djyysjclpz")
    public Object deleteDjyySjclpz(@RequestBody List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList) {
        if (CollectionUtils.isEmpty(bdcDjyySjclPzDOList)) {
            throw new MissingArgumentException("删除的登记原因收件材料配置不能为空！");
        }
        return bdcDjyySjclPzFeignService.deleteBdcDjyySjclPzList(bdcDjyySjclPzDOList);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/djyy/page")
    public Object listBdcDjxlDjyyPage(@LayuiPageable Pageable pageable, BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO){
        if(bdcDjxlDjyyGxDO==null || StringUtils.isBlank(bdcDjxlDjyyGxDO.getDjxl())){
            throw new MissingArgumentException("登记小类不能为空！");
        }
        String bdcDjxlDjyyGxDOJson=JSONObject.toJSONString(bdcDjxlDjyyGxDO);
        Page<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOPage=bdcCshXtPzFeignService.listBdcDjxlDjyyPage(pageable,bdcDjxlDjyyGxDOJson);
        changeSxh(bdcDjxlDjyyGxDOPage);
        return addLayUiCode(bdcDjxlDjyyGxDOPage);
    }

    /**
     * 对没有顺序号的数据进行排序并更新
     */
    private void changeSxh(Page<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOPage){
        List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList = bdcDjxlDjyyGxDOPage.getContent();
        int startNum = bdcDjxlDjyyGxDOPage.getNumber()*10;
        int i = 1;
        List<BdcDjxlDjyyGxDO> unsortList = new ArrayList<>(bdcDjxlDjyyGxDOList.size());
        for(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO:bdcDjxlDjyyGxDOList){
            if(Objects.isNull(bdcDjxlDjyyGxDO.getSxh())){
                bdcDjxlDjyyGxDO.setSxh(startNum + i);
                unsortList.add(bdcDjxlDjyyGxDO);
            }
            i++;
        }
        if(CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOList)){
            this.bdcCshXtPzFeignService.batchUpdateBdcDjxlDjyyGx(unsortList);
        }
    }

    /**
     * 批量修改登记原因配置
     * @param bdcDjxlDjyyGxDOList 登记原因配置集合
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping("/djyy/pl")
    public void plUpdateDjyyPz(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        if(CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOList)){
            this.bdcCshXtPzFeignService.batchUpdateBdcDjxlDjyyGx(bdcDjxlDjyyGxDOList);
        }
    }

    /**
     * 查询登记原因配置
     * @param bdcDjxlDjyyGxDO 查询对象
     * @return 登记原因配置集合
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/djyy")
    public List<BdcDjxlDjyyGxDO> queryDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO){
        if(Objects.isNull(bdcDjxlDjyyGxDO) || StringUtils.isBlank(bdcDjxlDjyyGxDO.getDjxl())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失查询参数");
        }
        return bdcCshXtPzFeignService.listBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
    }


    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存登记小类、登记原因关系
     */
    @PutMapping("/djyy")
    public Object insertDjyy(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        if (bdcDjxlDjyyGxDO == null || StringUtils.isBlank(bdcDjxlDjyyGxDO.getDjxl())|| StringUtils.isBlank(bdcDjxlDjyyGxDO.getDjyy())) {
            throw new MissingArgumentException("保存的登记小类或者登记原因不能为空！");
        }
        if(CommonConstantUtils.SF_S_DM.equals(bdcDjxlDjyyGxDO.getSfmr())) {
            //登记原因默认值只允许一个
            BdcDjxlDjyyGxDO djxlDjyyGxDO = new BdcDjxlDjyyGxDO();
            djxlDjyyGxDO.setDjxl(bdcDjxlDjyyGxDO.getDjxl());
            djxlDjyyGxDO.setSfmr(CommonConstantUtils.SF_S_DM);
            List<BdcDjxlDjyyGxDO> djxlDjyyGxDOList = bdcCshXtPzFeignService.listBdcDjxlDjyyGx(djxlDjyyGxDO);
            if(StringUtils.isNotBlank(bdcDjxlDjyyGxDO.getGzldyid()) &&CollectionUtils.isNotEmpty(djxlDjyyGxDOList)){
                djxlDjyyGxDOList = djxlDjyyGxDOList.stream().filter(djyyGxDO -> StringUtils.isBlank(djyyGxDO.getGzldyid()) ||StringUtils.equals(bdcDjxlDjyyGxDO.getGzldyid(), djyyGxDO.getGzldyid())).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(djxlDjyyGxDOList)) {
                if(djxlDjyyGxDOList.size() >1 ){
                    throw new AppException("当前登记小类登记原因默认值存在多个");
                }else if(StringUtils.isBlank(bdcDjxlDjyyGxDO.getId()) ||!StringUtils.equals(bdcDjxlDjyyGxDO.getId(),djxlDjyyGxDOList.get(0).getId())){
                    throw new AppException("当前登记小类已存在登记原因默认值");
                }

            }
        }
        if(StringUtils.isBlank(bdcDjxlDjyyGxDO.getId())){
            bdcDjxlDjyyGxDO.setId(UUIDGenerator.generate16());
        }
        return bdcCshXtPzFeignService.insertBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
    }

    /**
     * 根据登记小类获取登记原因配置最大顺序号
     * @param djxl 登记小类
     * @return 最大的顺序号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/djyy/max/{djxl}")
    public int queryDjyyMaxCount(@PathVariable(name = "djxl")String djxl){
        return this.bdcCshXtPzFeignService.queryDjyyMaxCount(djxl);
    }

    /**
     * @param bdcDjxlDjyyGxDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除登记原因
     */
    @DeleteMapping("/djyy")
    public Object deleteDjyy(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        if (CollectionUtils.isEmpty(bdcDjxlDjyyGxDOList)) {
            throw new MissingArgumentException("删除的登记小类、登记原因关系配置不能为空！");
        }
        return bdcCshXtPzFeignService.deleteBdcDjxlDjyyGx(bdcDjxlDjyyGxDOList);
    }

    /**
     * @param param   param 保存的字段的json 字符串
     * @param gzldymc 工作流定义名称
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存 字典表：bdcZdDjxlDO 登记小类  bdcDjxlPzDO 登记小类配置 bdcSlXztzPzDO 受理选择台账配置
     */
    @PutMapping("/insert/step1")
    public BdcDjxlPzDO insertStep1(String param, String gzldymc) {
        if (StringUtils.isAnyBlank(param, gzldymc)) {
            throw new AppException("保存的 登记小类配置 或者工作流定义名称不能为空！");
        }
        BdcDjxlPzDO bdcDjxlPzDO = JSONObject.parseObject(param, BdcDjxlPzDO.class);
        String action = StringUtils.isNotBlank(bdcDjxlPzDO.getPzid()) ? "update" : "insert";
        bdcDjxlPzDO.setPzid(UUIDGenerator.generate16());
        BdcZdDjxlDO bdcZdDjxlDO = new BdcZdDjxlDO();
        bdcZdDjxlDO.setDm(Integer.parseInt(bdcDjxlPzDO.getDjxl()));
        bdcZdDjxlDO.setMc(gzldymc);
        String className = bdcZdDjxlDO.getClass().getName();
        bdcZdFeignService.saveBdcZdxx(JSON.toJSONString(bdcZdDjxlDO), className, action);
        bdcDjxlPzFeignService.saveBdcDjxlPzDO(bdcDjxlPzDO);
        return bdcDjxlPzDO;
    }

    /**
     * @param gzldyid
     * @param param
     * @return BdcSlXztzPzDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存  受理选择台账配置
     */
    @PutMapping("/insert/step2")
    public BdcSlXztzPzDO insertStep2(String param, String gzldyid) {
        if (StringUtils.isAnyBlank(param, gzldyid)) {
            throw new AppException("保存的 受理选择台账配置 和 工作流定义ID 不能为空！");
        }
        BdcSlXztzPzDO bdcSlXztzPzDO = JSONObject.parseObject(param, BdcSlXztzPzDO.class);
        if (StringUtils.isBlank(bdcSlXztzPzDO.getPzid())) {
            bdcSlXztzPzDO.setGzldyid(gzldyid);
            bdcSlXztzPzDO.setPzid(UUIDGenerator.generate16());
            bdcSlXztzPzFeignService.saveBdcSlXztzPzDO(bdcSlXztzPzDO);
        }else{
            bdcSlEntityFeignService.updateByJsonEntity(param, BdcSlXztzPzDO.class.getName());
        }

        return bdcSlXztzPzDO;
    }

    /**
     * @param param
     * @return BdcYxbdcdyKgPzDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存已选不动产单元开关配置
     */
    @PutMapping("/insert/step3")
    public BdcYxbdcdyKgPzDO insertStep3(String param) {
        if (StringUtils.isBlank(param)) {
            throw new AppException("保存的 不动产已选不动产单元开关配置 不能为空！");
        }
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO = JSONObject.parseObject(param, BdcYxbdcdyKgPzDO.class);
        if(StringUtils.isBlank(bdcYxbdcdyKgPzDO.getPzid())){
            bdcYxbdcdyKgPzDO.setPzid(UUIDGenerator.generate16());
            bdcYxbdcdyKgPzFeignService.saveBdcYxbdcdyKgPzDO(bdcYxbdcdyKgPzDO);
        }else {
            bdcSlEntityFeignService.updateByJsonEntity(param, BdcYxbdcdyKgPzDO.class.getName());
        }

        return bdcYxbdcdyKgPzDO;
    }

    /**
     * @param param
     * @param gzldymc
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存收费项目配置
     */
    @PutMapping("/insert/step4")
    public BdcSlSfxmPzDO insertStep4(String param, String gzldymc) {
        if (StringUtils.isBlank(param)) {
            throw new AppException("保存的 收费项目配置 不能为空！");
        }
        BdcSlSfxmPzDO bdcSlSfxmPzDO = JSONObject.parseObject(param, BdcSlSfxmPzDO.class);
        bdcSlSfxmPzDO.setSfxmpzid(UUIDGenerator.generate16());
        bdcSlSfxmPzFeignService.saveBdcSlSfxmPzDO(bdcSlSfxmPzDO);
        return bdcSlSfxmPzDO;
    }

    /**
     * @param gzldymc
     * @param param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存收件材料配置
     */
    @PutMapping("/insert/step5")
    public BdcSlSjclPzDO insertStep5(String param, String gzldymc) {
        if (StringUtils.isBlank(param)) {
            throw new AppException("保存的 收件材料配置 不能为空！");
        }
        BdcSlSjclPzDO bdcSlSjclPzDO = JSONObject.parseObject(param, BdcSlSjclPzDO.class);
        bdcSlSjclPzFeignService.saveBdcSlSjclPzDO(bdcSlSjclPzDO);
        bdcSlSjclPzDO.setPzid(UUIDGenerator.generate16());
        return bdcSlSjclPzDO;
    }


    /**
     * @param param
     * @param djxlQllxId
     * @param djxlDjyyId
     * @param djlxDjxlId
     * @param cshfwkgId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存 登记小类、登记类型、登记原因、权利类型
     */
    @PutMapping("/insert/step6")
    public Object insertStep6(String param, String djxlQllxId, String djxlDjyyId, String djlxDjxlId, String cshfwkgId) {
        if (StringUtils.isBlank(param)) {
            throw new AppException("保存的 登记小类、登记类型、登记原因、权利类型 以及初始化服务开关信息不能为空！");
        }
        JSONObject obj = JSONObject.parseObject(param);
        BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO = JSONObject.parseObject(param, BdcDjlxDjxlGxDO.class);
        bdcDjlxDjxlGxDO.setId(StringUtils.isBlank(djlxDjxlId) ? UUIDGenerator.generate16() : djlxDjxlId);
        BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO = JSONObject.parseObject(param, BdcDjxlDjyyGxDO.class);
        bdcDjxlDjyyGxDO.setId(StringUtils.isBlank(djxlDjyyId) ? UUIDGenerator.generate16() : djxlDjyyId);
        BdcDjxlQllxGxDO bdcDjxlQllxGxDO = JSONObject.parseObject(param, BdcDjxlQllxGxDO.class);
        bdcDjxlQllxGxDO.setId(StringUtils.isBlank(djxlQllxId) ? UUIDGenerator.generate16() : djxlQllxId);
        BdcCshFwkgDO bdcCshFwkgDO = JSONObject.parseObject(param, BdcCshFwkgDO.class);
        bdcCshFwkgDO.setId(StringUtils.isBlank(cshfwkgId) ? UUIDGenerator.generate16() : cshfwkgId);

        if(StringUtils.isNotBlank(cshfwkgId)) {
            obj.put("id",cshfwkgId);
            bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcCshFwkgDO.class.getName());
        }else{
            obj.put("id",UUIDGenerator.generate16());
            bdcEntityFeignService.insertByJsonEntity(JSONObject.toJSONString(obj), BdcCshFwkgDO.class.getName());
        }
        if(StringUtils.isNotBlank(djlxDjxlId)) {
            obj.put("id",djlxDjxlId);
            bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcDjlxDjxlGxDO.class.getName());
        }else{
            obj.put("id",UUIDGenerator.generate16());
            bdcEntityFeignService.insertByJsonEntity(JSONObject.toJSONString(obj), BdcDjlxDjxlGxDO.class.getName());
        }
        if(StringUtils.isNotBlank(djxlQllxId)) {
            obj.put("id",djxlQllxId);
            bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcDjxlQllxGxDO.class.getName());
        }else{
            obj.put("id",UUIDGenerator.generate16());
            bdcEntityFeignService.insertByJsonEntity(JSONObject.toJSONString(obj), BdcDjxlQllxGxDO.class.getName());
        }
        BdcDjgxVO bdcDjgxVO = new BdcDjgxVO();
        bdcDjgxVO.setBdcCshFwkgDO(bdcCshFwkgDO);
        bdcDjgxVO.setBdcDjlxDjxlGxDO(bdcDjlxDjxlGxDO);
        bdcDjgxVO.setBdcDjxlDjyyGxDO(bdcDjxlDjyyGxDO);
        bdcDjgxVO.setBdcDjxlQllxGxDO(bdcDjxlQllxGxDO);
        return bdcDjgxVO;
    }

    /**
     * @param gzldyid
     * @param newdjxl
     * @param bfzDjxl 被复制登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制登记小类配置
     */
    @GetMapping("/fz/djxlPz")
    public BdcPzFzDTO fzDjxlpz(String gzldyid, String newdjxl, String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,newdjxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        String djxl=bfzDjxl;
        BdcDjxlPzDO bdcDjxlPzDO=bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(gzldyid,djxl);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(bdcDjxlPzDO==null){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置登记小类配置");
            return bdcPzFzDTO;
        }
        bdcDjxlPzDO.setPzid(UUIDGenerator.generate16());
        bdcDjxlPzDO.setDjxl(newdjxl);
        int num=bdcDjxlPzFeignService.saveBdcDjxlPzDO(bdcDjxlPzDO);

        bdcPzFzDTO.setFzsl(num);
        bdcPzFzDTO.setFznr("登记小类配置数据");
        return bdcPzFzDTO;
    }

    /**
     * @param gzldyid
     * @param newdjxl
     * @param bfzDjxl 被复制登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制 收费项目配置
     */
    @GetMapping("/fz/sfxmpz")
    public BdcPzFzDTO fzSfxmpz(String gzldyid,String newdjxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,newdjxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        String djxl=bfzDjxl;
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList=bdcSlSfxmPzFeignService.listBdcSlSfxmPzByDjxl(bfzDjxl);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(bdcSlSfxmPzDOList)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置收费项目配置");
            return bdcPzFzDTO;
        }
        bdcSlSfxmPzDOList.forEach(bdcSlSfxmPzDO -> {
            BdcSlSfxmPzDO sfxmPzDO=new BdcSlSfxmPzDO();
            sfxmPzDO.setDjxl(newdjxl);
            bdcSlSfxmPzDO.setXh(bdcSlSfxmPzFeignService.querySfxmPzMaxSxh(sfxmPzDO));
            bdcSlSfxmPzDO.setSfxmpzid(UUIDGenerator.generate16());
            bdcSlSfxmPzFeignService.saveBdcSlSfxmPzDO(bdcSlSfxmPzDO);
        });
        bdcPzFzDTO.setFzsl(bdcSlSfxmPzDOList.size());
        bdcPzFzDTO.setFznr("收费项目配置");
        return bdcPzFzDTO;
    }

    /**
     * @param gzldyid
     * @param newdjxl
     * @param bfzDjxl 被复制登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制 收件材料 配置
     */
    @GetMapping("/fz/sjclpz")
    public BdcPzFzDTO fzSjclpz(String gzldyid,String newdjxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,newdjxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        String djxl=bfzDjxl;
        List<BdcSlSjclPzDO> slSjclPzDOS=bdcSlSjclPzFeignService.listBdcSlSjclPzByDjxl(djxl);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(slSjclPzDOS)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置收件材料配置");
            return bdcPzFzDTO;
        }
        slSjclPzDOS.forEach(bdcSlSjclPzDO -> {
            BdcSlSjclPzDO slSjclPzDO=new BdcSlSjclPzDO();
            slSjclPzDO.setDjxl(newdjxl);
            bdcSlSjclPzDO.setXh(bdcSlSjclPzFeignService.querySjclPzMaxSxh(slSjclPzDO));
            bdcSlSjclPzFeignService.saveBdcSlSjclPzDO(bdcSlSjclPzDO);
        });
        bdcPzFzDTO.setFzsl(slSjclPzDOS.size());
        bdcPzFzDTO.setFznr("收件材料配置");
        return bdcPzFzDTO;
    }

    /**
     * @param gzldyid
     * @param djxl
     * @param bfzDjxl 被复制登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制不动产关系配置
     */
    @GetMapping("/fz/djlxDjxl")
    public BdcPzFzDTO fzBdcDjlxDjxl(String gzldyid,String djxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,djxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO=new BdcDjlxDjxlGxDO();
        bdcDjlxDjxlGxDO.setDjxl(bfzDjxl);
        List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOS=bdcCshXtPzFeignService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(bdcDjlxDjxlGxDOS)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置 登记类型登记小类关系表");
            return bdcPzFzDTO;
        }
        bdcDjlxDjxlGxDOS.forEach(djlxDjxl->{
            djlxDjxl.setId(UUIDGenerator.generate16());
            djlxDjxl.setDjxl(djxl);
            bdcCshXtPzFeignService.insertBdcDjlxDjxlGx(djlxDjxl);
        });
        bdcPzFzDTO.setFzsl(bdcDjlxDjxlGxDOS.size());
        bdcPzFzDTO.setFznr("登记类型登记小类关系表");
        return bdcPzFzDTO;
    }

    /**
     * @param gzldyid
     * @param djxl
     * @param bfzDjxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制登记小类权利类型
     */
    @GetMapping("/fz/djxlQllx")
    public BdcPzFzDTO fzBdcDjxlQllx(String gzldyid,String djxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,djxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        BdcDjxlQllxGxDO bdcDjxlQllxGxDO=new BdcDjxlQllxGxDO();
        bdcDjxlQllxGxDO.setDjxl(bfzDjxl);
        List<BdcDjxlQllxGxDO> djlxQllxList=bdcCshXtPzFeignService.listBdcDjxlQllxGx(bdcDjxlQllxGxDO);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(djlxQllxList)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置 登记小类权利类型关系表");
            return bdcPzFzDTO;
        }
        djlxQllxList.forEach(djxlQllxGxDO->{
            djxlQllxGxDO.setId(UUIDGenerator.generate16());
            djxlQllxGxDO.setDjxl(djxl);
            bdcCshXtPzFeignService.insertBdcDjxlQllxGx(djxlQllxGxDO);
        });
        bdcPzFzDTO.setFzsl(djlxQllxList.size());
        bdcPzFzDTO.setFznr("登记小类权利类型关系表");
        return bdcPzFzDTO;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/fz/djxlDjyy")
    public BdcPzFzDTO fzBdcDjxlDjyy(String gzldyid,String djxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,djxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        /**
         * 获取被复制登记小类 登记原因 关系表
         */
        BdcDjxlDjyyGxDO djxlDjyyGxDO=new BdcDjxlDjyyGxDO();
        djxlDjyyGxDO.setDjxl(bfzDjxl);
        List<BdcDjxlDjyyGxDO> djxlDjyyGxDOList=bdcCshXtPzFeignService.listBdcDjxlDjyyGx(djxlDjyyGxDO);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(djxlDjyyGxDOList)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置 登记小类登记原因关系表");
            return bdcPzFzDTO;
        }
        djxlDjyyGxDOList.forEach(djxlDjyy->{
            djxlDjyy.setId(UUIDGenerator.generate16());
            djxlDjyy.setDjxl(djxl);
            bdcCshXtPzFeignService.insertBdcDjxlDjyyGx(djxlDjyy);
        });
        bdcPzFzDTO.setFzsl(djxlDjyyGxDOList.size());
        bdcPzFzDTO.setFznr("登记小类 登记原因关系表");
        return bdcPzFzDTO;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/fz/cshfwkg")
    public BdcPzFzDTO fzCshFwkg(String gzldyid,String djxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,djxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        /**
         * 获取被复制 登记小类 初始化服务开关数据
         */
        BdcCshFwkgDO cshFwkgDO=new BdcCshFwkgDO();
        cshFwkgDO.setDjxl(bfzDjxl);
        List<BdcCshFwkgDO> cshFwkgDOS=bdcCshXtPzFeignService.listBdcCshFwkg(cshFwkgDO);
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        if(CollectionUtils.isEmpty(cshFwkgDOS)){
            bdcPzFzDTO.setFzsl(0);
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置 初始化房屋开关配置");
            return bdcPzFzDTO;
        }
        cshFwkgDOS.forEach(bdcCshFwkgDO->{
            bdcCshFwkgDO.setId(UUIDGenerator.generate16());
            bdcCshFwkgDO.setDjxl(djxl);
            bdcCshXtPzFeignService.insertBdcCshFwkg(bdcCshFwkgDO);
        });
        bdcPzFzDTO.setFzsl(cshFwkgDOS.size());
        bdcPzFzDTO.setFznr("初始化服务开关配置表");
        return bdcPzFzDTO;
    }
    /**
     * @param gzldyid
     * @param djxl
     * @param bfzDjxl 被复制登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制权利其他状况配置
     */
    @GetMapping("/fz/qlqtzkfjpz")
    public BdcPzFzDTO fzQlqtzkfjpz(String gzldyid,String djxl,String bfzDjxl){
        if(StringUtils.isAnyBlank(gzldyid,djxl,bfzDjxl)){
            throw new MissingArgumentException("工作流定义ID、登记小类和被复制的登记小类不能为空！");
        }
        BdcPzFzDTO bdcPzFzDTO=new BdcPzFzDTO();
        /**
         * 获取被复制登记小类 权利其他状况配置
         *
         */
        BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO=new BdcXtQlqtzkFjPzDO();
        bdcXtQlqtzkFjPzDO.setDjxl(bfzDjxl);
        List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDOS=bdcXtQlqtzkFjPzFeignService.listQlqtzkFjpz(bdcXtQlqtzkFjPzDO);
        if(CollectionUtils.isEmpty(bdcXtQlqtzkFjPzDOS)){
            bdcPzFzDTO.setFznr("被复制的登记小类尚未配置 权利其他状况附记配置表");
            bdcPzFzDTO.setFzsl(0);
            return bdcPzFzDTO;
        }

        bdcXtQlqtzkFjPzDOS.forEach(qlqtzkFjpz -> {
            qlqtzkFjpz.setDjxl(djxl);
            qlqtzkFjpz.setPzid(UUIDGenerator.generate16());
            bdcXtQlqtzkFjPzFeignService.saveBdcXtQlqtzkFjPz(qlqtzkFjpz);
        });
        bdcPzFzDTO.setFzsl(bdcXtQlqtzkFjPzDOS.size());
        bdcPzFzDTO.setFznr("权利其他状况附记配置表");
        return bdcPzFzDTO;
    }

    @GetMapping("/jrpz")
    public Object queryXtJrpz(@LayuiPageable Pageable pageable, BdcXtJrQO bdcXtJrQO) {
        if (StringUtils.isNotBlank(bdcXtJrQO.getDjxl())) {
            List<BdcXtJrDO> bdcXtJrDOList = bdcXtJrFeignService.listBdcXtJr(bdcXtJrQO);
            if (CollectionUtils.isNotEmpty(bdcXtJrDOList)) {
                return addLayUiCode(new PageImpl(bdcXtJrDOList, pageable, bdcXtJrDOList.size()));
            } else {
                return addLayUiCode(PageUtils.listToPage(new ArrayList(), new PageRequest(0, 10)));
            }
        }
        return addLayUiCode(PageUtils.listToPage(new ArrayList(), new PageRequest(0, 10)));

    }

    @PostMapping("/jrywpz")
    public void saveXtJrPz(@RequestBody BdcXtJrDO bdcXtJrDO) {
        //现根据相关数据查询是否存在相同的数据，已存在不允许重复配置
        if (Objects.nonNull(bdcXtJrDO)) {
            //先验证当前保存的数据是否服务和代码是否正确
            if (StringUtils.isNotBlank(bdcXtJrDO.getYwfwdm())) {
                Map ywdmMap = bdcXtJrFeignService.queryJrYwdm(bdcXtJrDO.getYwfwdm());
                if (!StringUtils.equals(bdcXtJrDO.getYwdm(), MapUtils.getString(ywdmMap, "ywdm", ""))) {
                    throw new AppException("当前填写的数据业务服务代码和接入业务名称不对应");
                }
            }
            if (StringUtils.isBlank(bdcXtJrDO.getId())) {
                //id为空表示新增的数据，检查是否已存在相同配置
                BdcXtJrQO bdcXtJrQO = new BdcXtJrQO();
                bdcXtJrQO.setDjxl(bdcXtJrDO.getBdcdjlxdm());
                bdcXtJrQO.setQllx(bdcXtJrDO.getBdcqllxdm());
                bdcXtJrQO.setBdclx(String.valueOf(bdcXtJrDO.getBdclx()));
                if (Objects.nonNull(bdcXtJrDO.getSfdz())) {
                    bdcXtJrQO.setBdcdyfwlxList(Collections.singletonList(String.valueOf(bdcXtJrDO.getSfdz())));
                }
                List<BdcXtJrDO> bdcXtJrDOList = bdcXtJrFeignService.listBdcXtJr(bdcXtJrQO);
                if (CollectionUtils.isNotEmpty(bdcXtJrDOList)) {
                    throw new AppException("存在相同接入业务配置不允许保存");
                }
                bdcXtJrDO.setId(UUIDGenerator.generate16());
                bdcEntityFeignService.insertByJsonEntity(JSON.toJSONString(bdcXtJrDO), bdcXtJrDO.getClass().getName());
            } else {
                bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcXtJrDO), bdcXtJrDO.getClass().getName());
            }
        }

    }

    @DeleteMapping("/jrywpz")
    public void deleteJrywPz(@RequestBody List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            for (String id : idList) {
                bdcXtJrFeignService.deleteBdcXtJr(id);
            }
        }
    }

    @PutMapping("/bdcDjxlPz/sfsb")
    public void updateBdcDjxlPz(@RequestBody BdcDjxlPzDO bdcDjxlPzDO) {
        if (Objects.nonNull(bdcDjxlPzDO)) {
            bdcSlEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcDjxlPzDO), bdcDjxlPzDO.getClass().getName());
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报配置校验
     * @date : 2022/7/8 10:15
     */
    @PostMapping("/sbpzjy")
    public int sbpzjy(@RequestBody List<String> pzidList) {
        int count = 0;
        if (CollectionUtils.isEmpty(pzidList)) {
            count = bdcDjxlPzFeignService.sbpzjy("");
        } else {
            for (String pzid : pzidList) {
                count += bdcDjxlPzFeignService.sbpzjy(pzid);
            }
        }
        return count;
    }

    /**
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取登记类型登记小类关系
     * @date : 2022/7/22 14:55
     */
    @GetMapping("/djlxdjxlgx")
    public Object queryDjxlDjxlGx(String djxl) {
        if (StringUtils.isNotBlank(djxl)) {
            BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO = new BdcDjlxDjxlGxDO();
            bdcDjlxDjxlGxDO.setDjxl(djxl);
            return bdcCshXtPzFeignService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
        }
        return null;
    }

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证接入配置是否存在
     * @date : 2022/7/22 14:55
     */
    @GetMapping("/yzjryw")
    public void yzJrywSfcz(String djxl, String qllx, Integer bdclx, String bdcdyfwlx, String pzid) {
        if (StringUtils.isNoneBlank(djxl, qllx, pzid) && Objects.nonNull(bdclx)) {
            BdcXtJrQO bdcXtJrQO = new BdcXtJrQO();
            bdcXtJrQO.setDjxl(djxl);
            bdcXtJrQO.setQllx(qllx);
            bdcXtJrQO.setBdclx(String.valueOf(bdclx));
            List<Map> djxlZdList = bdcZdFeignService.queryBdcZd("djxl");
            List<Map> qllxZdList = bdcZdFeignService.queryBdcZd("qllx");
            if (StringUtils.isNotBlank(bdcdyfwlx)) {
                for (String lx : bdcdyfwlx.split(CommonConstantUtils.ZF_YW_DH)) {
                    bdcXtJrQO.setBdcdyfwlxList(Collections.singletonList(lx));
                    List<BdcXtJrDO> bdcXtJrDOList = bdcXtJrFeignService.listBdcXtJr(bdcXtJrQO);
                    if (CollectionUtils.isEmpty(bdcXtJrDOList)) {
                        //新增数据
                        BdcXtJrDO bdcXtJrDO = new BdcXtJrDO();
                        bdcXtJrDO.setBdcdjlxdm(djxl);
                        bdcXtJrDO.setBdclx(bdclx);
                        bdcXtJrDO.setBdcqllxdm(qllx);
                        bdcXtJrDO.setId(UUIDGenerator.generate16());
                        bdcXtJrDO.setSfdz(Integer.valueOf(lx));
                        bdcXtJrDO.setBdcdjlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(djxl, djxlZdList));
                        bdcXtJrDO.setBdcqllxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qllx, qllxZdList));
                        bdcEntityFeignService.insertByJsonEntity(JSON.toJSONString(bdcXtJrDO), bdcXtJrDO.getClass().getName());
                    }
                }
            } else {
                List<BdcXtJrDO> bdcXtJrDOList = bdcXtJrFeignService.listBdcXtJr(bdcXtJrQO);
                if (CollectionUtils.isEmpty(bdcXtJrDOList)) {
                    //新增数据
                    BdcXtJrDO bdcXtJrDO = new BdcXtJrDO();
                    bdcXtJrDO.setBdcdjlxdm(djxl);
                    bdcXtJrDO.setBdclx(bdclx);
                    bdcXtJrDO.setBdcqllxdm(qllx);
                    bdcXtJrDO.setId(UUIDGenerator.generate16());
                    bdcXtJrDO.setBdcdjlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(djxl, djxlZdList));
                    bdcXtJrDO.setBdcqllxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qllx, qllxZdList));
                    bdcEntityFeignService.insertByJsonEntity(JSON.toJSONString(bdcXtJrDO), bdcXtJrDO.getClass().getName());
                }
            }
            //是否上报设置为是
            if (StringUtils.isNotBlank(pzid)) {
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzDOByPzid(pzid);
                bdcDjxlPzDO.setSfsb(CommonConstantUtils.SF_S_DM);
                bdcDjxlPzFeignService.saveBdcDjxlPzDO(bdcDjxlPzDO);
            }
        }
    }

}
