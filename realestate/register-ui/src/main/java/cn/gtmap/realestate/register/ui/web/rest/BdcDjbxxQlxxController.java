package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFdcq3GyxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcDjbqlQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJtcyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcFdcq3GyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcTdcbjyqNytqtsyqFeginService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.ui.core.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/30
 * @description 登记簿权利信息请求处理控制器
 */
@RestController
@RequestMapping("/rest/v1.0/djbxx/qlxx")
public class BdcDjbxxQlxxController {
    /**
     * 分页变量名称定义
     */
    private static final String PAGE_NUMBER = "number";
    private static final String PAGE_SIZE = "size";
    private static final String PAGE_CONTENT = "content";
    private static final String TOTAL_ELEMENTS = "totalElements";
    /**
     * 实体属性或对应方法名称常量定义
     */
    private static final String DJLX = "djlx";
    private static final String FWXZ = "fwxz";
    private static final String FWJG = "fwjg";
    private static final String FWYT = "fwyt";
    private static final String YGDJZL = "ygdjzl";
    private static final String GJZWLX = "gjzwlx";
    private static final String QLLX = "qllx";
    private static final String CFLX = "cflx";
    private static final String DYFS = "dyfs";

    /**
     * 登记簿权利查询qszt的值
     */
    private static final Integer[] QSZT = {1, 2};

    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    @GetMapping("/zd")
    public Map<String, List<Map>> listZd() {
        return bdcZdFeignService.listBdcZd();
    }

    @Autowired
    private BdcFdcq3GyxxFeignService bdcFdcq3GyxxFeignService;

    @Autowired
    private BdcJtcyFeignService jtcyFeignService;

    @Autowired
    private BdcQlrFeignService qlrFeignService;

    @Autowired
    private BdcTdcbjyqNytqtsyqFeginService tdcbjyqNytqtsyqFeginService;

    /**
     * 版本信息
     */
    @Value("${html.version:}")
    private String version;

    /**
     * 是否查询注销项目
     */
    @Value("#{'${djb.sfcxzxxm:}'.split(',')}")
    private List<String> sfcxzxxm;

    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取登记簿建设用地使用权、宅基地使用权登记信息
     */
    @GetMapping("/jsydsyq")
    public Page<BdcDjbxxJsydsyqZjdsyqVO> queryJsydsyqZjdsyq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取用地使用权、宅基地使用权权利信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_JSYDSYQ);
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLXX_QLLX_JSYDSYQ)){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcJsydsyqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcJsydsyqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxJsydsyqZjdsyqVO> jsydsyqZjdsyqVOList = new ArrayList<>(bdcQlList.size());
        for (BdcJsydsyqDO jsydsyqDO : bdcQlList) {
            BdcDjbxxJsydsyqZjdsyqVO jsydsyqZjdsyqVO = new BdcDjbxxJsydsyqZjdsyqVO();
            BeansResolveUtils.clonePropertiesValue(jsydsyqDO, jsydsyqZjdsyqVO);
            jsydsyqZjdsyqVO.setZxdjrq(jsydsyqDO.getZxdjsj());
            jsydsyqZjdsyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(jsydsyqDO.getDjlx(), zdMap.get(DJLX)));
            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(jsydsyqDO.getXmid());
            jsydsyqZjdsyqVO.setQlr(bdcXmDO.getQlr());
            jsydsyqZjdsyqVO.setQlrlx(bdcXmDO.getQlrlx());
            jsydsyqZjdsyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
            jsydsyqZjdsyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            jsydsyqZjdsyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
            jsydsyqZjdsyqVO.setQszt(bdcXmDO.getQszt());

            jsydsyqZjdsyqVOList.add(jsydsyqZjdsyqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(jsydsyqZjdsyqVOList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取登记簿权利
     */
    @GetMapping("/bdcQlDjb")
    public Page queryBdcQljb(BdcDjbqlQO bdcDjbqlQO) {
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(bdcDjbqlQO.getQllx())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List bdcQlList = new ArrayList();
        if (StringUtils.equals(bdcDjbqlQO.getQllx(), String.valueOf(CommonConstantUtils.QLLX_YG_DM))) {
            List bdcQlListTemp = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcYgDO.class);
            for (int i = 0; i < bdcQlListTemp.size(); i++) {
                BdcdjbYgVO bdcdjbYgVO = new BdcdjbYgVO();
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQlListTemp.get(i);
                BeanUtils.copyProperties(bdcYgDO, bdcdjbYgVO);
                /**
                 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
                 * @description 字典项转换
                 */
                bdcdjbYgVO.setYgdjzlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getYgdjzl(), zdMap.get(YGDJZL)));
                bdcdjbYgVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getDjlx(), zdMap.get(DJLX)));
                bdcdjbYgVO.setFwxzmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getFwxz(), zdMap.get(FWXZ)));
                bdcdjbYgVO.setGhytmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getGhyt(), zdMap.get(FWYT)));
                bdcdjbYgVO.setDjrq(bdcYgDO.getDjsj());
                // 获取项目信息
                BdcXmDO bdcXmDO = queryBdcXm(bdcdjbYgVO.getXmid());
                bdcdjbYgVO.setQlr(bdcXmDO.getQlr());
                bdcdjbYgVO.setQlrzjh(bdcXmDO.getQlrzjh());
                bdcdjbYgVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
                bdcdjbYgVO.setYwr(bdcXmDO.getYwr());
                bdcdjbYgVO.setYwrzjh(bdcXmDO.getYwrzjh());
                bdcdjbYgVO.setYwrzjzl(bdcXmDO.getYwrzjzl());
                bdcdjbYgVO.setBdcqzmh(bdcXmDO.getBdcqzh());
                bdcdjbYgVO.setQszt(bdcXmDO.getQszt());
                Integer jzzr = bdcYgDO.getJzzr();
                if (jzzr != null) {
                    bdcdjbYgVO.setJzzrmc(jzzr == 1 ? "是" : "否");
                }
                bdcQlList.add(bdcdjbYgVO);
            }
        }
        if (StringUtils.equals(bdcDjbqlQO.getQllx(), String.valueOf(CommonConstantUtils.QLLX_FDCQ_DM))) {
            List bdcQlListTemp = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcFdcqDO.class);
            for (int i = 0; i < bdcQlListTemp.size(); i++) {
                BdcdjbFdcqVO bdcdjbFdcqVO = new BdcdjbFdcqVO();
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQlListTemp.get(i);
                BeanUtils.copyProperties(bdcFdcqDO, bdcdjbFdcqVO);
                /**
                 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
                 * @description 字典项转换
                 */
                bdcdjbFdcqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getDjlx(), zdMap.get(DJLX)));
                bdcdjbFdcqVO.setFwjgmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwjg(), zdMap.get(FWJG)));
                bdcdjbFdcqVO.setFwxzmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwxz(), zdMap.get(FWXZ)));
                bdcdjbFdcqVO.setGhytmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getGhyt(), zdMap.get(FWYT)));
                // 日期
                bdcdjbFdcqVO.setDjrq(bdcFdcqDO.getDjsj());
                bdcdjbFdcqVO.setZxdjrq(bdcFdcqDO.getZxdjsj());
                bdcdjbFdcqVO.setTdsyqsrq(bdcFdcqDO.getTdsyqssj());
                bdcdjbFdcqVO.setTdsyjsrq(bdcFdcqDO.getTdsyjssj());
                // 获取项目信息
                BdcXmDO bdcXmDO = queryBdcXm(bdcFdcqDO.getXmid());
                bdcdjbFdcqVO.setQlr(bdcXmDO.getQlr());
                bdcdjbFdcqVO.setQlrlx(bdcXmDO.getQlrlx());
                bdcdjbFdcqVO.setQlrzjh(bdcXmDO.getQlrzjh());
                bdcdjbFdcqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
                bdcdjbFdcqVO.setBdcqzh(bdcXmDO.getBdcqzh());
                bdcdjbFdcqVO.setQszt(bdcXmDO.getQszt());
                // 获取土地证（旧数据会有土地证和房产证同时存在的情况）
                // 默认为国有建设用地使用权
                Integer tdqllx = CommonConstantUtils.QLLX_GYJSYDSYQ_DM;
                // 集体建设用地使用权/房屋所有权
                if (CommonConstantUtils.QLLX_JTJSSYQ_FWSYQ_DM.equals(bdcXmDO.getQllx())) {
                    tdqllx = CommonConstantUtils.QLLX_JTJSSYQ_DM;
                }
                BdcXmDO tdBdcXmDO = bdcDjbxxFeignService.queryFwTdXm(bdcXmDO, tdqllx);
                if (null != tdBdcXmDO && StringUtils.isNotBlank(tdBdcXmDO.getBdcqzh())) {
                    bdcdjbFdcqVO.setBdcqzh(bdcXmDO.getBdcqzh() + CommonConstantUtils.ZF_ZW_FH + "土地证：" + tdBdcXmDO.getBdcqzh());
                }

                List<BdcFdcqFdcqxmDO> bdcfdcqxmList = bdcDjbxxFeignService.listBdcFdcqxm(bdcFdcqDO.getQlid());
                if (CollectionUtils.isNotEmpty(bdcfdcqxmList)) {
                    List bdcfdcqxmListTemp = new ArrayList();
                    for (BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO : bdcfdcqxmList) {
                        BdcdjbFdcqxmVO bdcdjbFdcqxmVO = new BdcdjbFdcqxmVO();
                        BeanUtils.copyProperties(bdcFdcqFdcqxmDO, bdcdjbFdcqxmVO);
                        bdcdjbFdcqxmVO.setFwjgmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqFdcqxmDO.getFwjg(), zdMap.get(FWJG)));
                        bdcdjbFdcqxmVO.setGhytmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqFdcqxmDO.getGhyt(), zdMap.get(FWYT)));

                        bdcfdcqxmListTemp.add(bdcdjbFdcqxmVO);
                    }
                    bdcdjbFdcqVO.setBdcFdcqXmList(bdcfdcqxmListTemp);
                } else {
                    bdcdjbFdcqVO.setBdcFdcqXmList(new ArrayList<BdcdjbFdcqxmVO>());
                }
                bdcQlList.add(bdcdjbFdcqVO);
            }
        }
        if (StringUtils.equals(bdcDjbqlQO.getQllx(), String.valueOf(CommonConstantUtils.QLLX_HY_DM))) {
            List<BdcHysyqDO> bdcQlListTemp = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcHysyqDO.class);
            if (CollectionUtils.isNotEmpty(bdcQlListTemp)) {

                for (BdcHysyqDO bdcHysyqDO : bdcQlListTemp) {
                    BdcdjbHysyqVO bdcdjbHysyqVO = new BdcdjbHysyqVO();
                    BeanUtils.copyProperties(bdcHysyqDO, bdcdjbHysyqVO);
                    bdcdjbHysyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcHysyqDO.getDjlx(), zdMap.get(DJLX)));
                    bdcdjbHysyqVO.setSyqqsrq(bdcHysyqDO.getSyqqssj());
                    bdcdjbHysyqVO.setSyqjsrq(bdcHysyqDO.getSyqjssj());
                    bdcdjbHysyqVO.setDjrq(bdcHysyqDO.getDjsj());
                    bdcdjbHysyqVO.setZxdjrq(bdcHysyqDO.getZxdjsj());
                    // 获取项目信息
                    BdcXmDO bdcXmDO = queryBdcXm(bdcHysyqDO.getXmid());
                    bdcdjbHysyqVO.setQlr(bdcXmDO.getQlr());
                    bdcdjbHysyqVO.setQlrlx(bdcXmDO.getQlrlx());
                    bdcdjbHysyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
                    bdcdjbHysyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
                    bdcdjbHysyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
                    bdcdjbHysyqVO.setQszt(bdcXmDO.getQszt());

                    bdcQlList.add(bdcdjbHysyqVO);
                }
            }
        }
        if (StringUtils.equals(bdcDjbqlQO.getQllx(), String.valueOf(CommonConstantUtils.QLLX_GZW_DM))) {
            List<BdcGjzwsyqDO> bdcQlListTemp = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcGjzwsyqDO.class);
            if (CollectionUtils.isNotEmpty(bdcQlListTemp)) {

                for (BdcGjzwsyqDO bdcGjzwsyqDO : bdcQlListTemp) {
                    BdcdjbGjzwsyqVO bdcdjbGjzwsyqVO = new BdcdjbGjzwsyqVO();
                    BeanUtils.copyProperties(bdcGjzwsyqDO, bdcdjbGjzwsyqVO);
                    bdcdjbGjzwsyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcGjzwsyqDO.getDjlx(), zdMap.get(DJLX)));
                    bdcdjbGjzwsyqVO.setGjzwlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcGjzwsyqDO.getGjzwlx(), zdMap.get(GJZWLX)));
                    bdcdjbGjzwsyqVO.setTdhysyqsrq(bdcGjzwsyqDO.getTdhysyqssj());
                    bdcdjbGjzwsyqVO.setTdhysyjsrq(bdcGjzwsyqDO.getTdhysyjssj());
                    bdcdjbGjzwsyqVO.setDjrq(bdcGjzwsyqDO.getDjsj());
                    bdcdjbGjzwsyqVO.setZxdjrq(bdcGjzwsyqDO.getZxdjsj());
                    // 获取项目信息
                    BdcXmDO bdcXmDO = queryBdcXm(bdcGjzwsyqDO.getXmid());
                    bdcdjbGjzwsyqVO.setGjzwsyqr(bdcXmDO.getQlr());
                    bdcdjbGjzwsyqVO.setQlrlx(bdcXmDO.getQlrlx());
                    bdcdjbGjzwsyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
                    bdcdjbGjzwsyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
                    bdcdjbGjzwsyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
                    bdcdjbGjzwsyqVO.setQszt(bdcXmDO.getQszt());

                    bdcQlList.add(bdcdjbGjzwsyqVO);
                }
            }
        }

        appendZxxxToFj(bdcQlList);

        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcQlList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * 登记簿附记显示 注销相关信息<br>
     * 蚌埠登记簿附记需要显示注销信息
     *
     * @param bdcQlList 权利信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void appendZxxxToFj(List bdcQlList) {

        // 盐城附记
        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)){
            for (Object o : bdcQlList) {
                if (!(o instanceof BdcQl)) {
                    continue;
                }

                BdcQl bdcQl = (BdcQl) o;
                if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcQl.getQszt())) {
                    continue;
                }
                bdcQl.setFj("已注销");
            }
        }

        //蚌埠附记
        if(CommonConstantUtils.SYSTEM_VERSION_BB.equals(version)){
            for (Object o : bdcQlList) {
                if (!(o instanceof BdcQl)) {
                    continue;
                }

                BdcQl bdcQl = (BdcQl) o;
                if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcQl.getQszt())) {
                    continue;
                }
                String zxywh = StringUtils.EMPTY;
                Date zxdjsj = null;

                if (bdcQl instanceof BdcTdsyqDO) {
                    zxywh = ((BdcTdsyqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcTdsyqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcFdcqDO) {
                    zxywh = ((BdcFdcqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcFdcqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcFdcq3DO) {
                    zxywh = ((BdcFdcq3DO) bdcQl).getZxywh();
                    zxdjsj = ((BdcFdcq3DO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcGjzwsyqDO) {
                    zxywh = ((BdcGjzwsyqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcGjzwsyqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcHysyqDO) {
                    zxywh = ((BdcHysyqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcHysyqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcJsydsyqDO) {
                    zxywh = ((BdcJsydsyqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcJsydsyqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcLqDO) {
                    zxywh = ((BdcLqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcLqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcNydjyqDO) {
                    zxdjsj = ((BdcNydjyqDO) bdcQl).getZxdjsj();
                } else if (bdcQl instanceof BdcTdcbnydsyqDO) {
                    zxywh = ((BdcTdcbnydsyqDO) bdcQl).getZxywh();
                    zxdjsj = ((BdcTdcbnydsyqDO) bdcQl).getZxdjsj();
                }

                appendFj(bdcQl, zxywh, zxdjsj);
            }
        }
    }

    /**
     * 拼接注销信息到附记
     *
     * @param bdcQl  权利
     * @param zxywh  注销业务号
     * @param zxdjsj 注销登记时间
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void appendFj(BdcQl bdcQl, String zxywh, Date zxdjsj) {
        StringBuilder fjBuilder = new StringBuilder(StringUtils.isNotBlank(bdcQl.getFj()) ? bdcQl.getFj() : "")
                .append("\n产权已注销;");

        if (StringUtils.isNotBlank(zxywh)) {
            fjBuilder.append("\n注销业务号：").append(zxywh).append(";");
        }

        if (zxdjsj != null) {
            String zxdjsjStr = DateUtils.formateYmdZw(zxdjsj);
            if (StringUtils.isNotBlank(zxdjsjStr)) {
                fjBuilder.append("\n注销时间：").append(zxdjsjStr).append(";");
            }
        }

        bdcQl.setFj(fjBuilder.toString());
    }

    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取土地所有权登记信息登记信息
     */
    @GetMapping("/tdsyq")
    public Page<BdcDjbxxTdsyqVO> queryTdsyq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取土地所有权登记信息 权利信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_TDSYQ);
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLXX_QLLX_TDSYQ)){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcTdsyqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcTdsyqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxTdsyqVO> tdsyqVOList = new ArrayList<>(bdcQlList.size());
        for (BdcTdsyqDO tdsyqDO : bdcQlList) {
            BdcDjbxxTdsyqVO tdsyqVO = new BdcDjbxxTdsyqVO();
            BeansResolveUtils.clonePropertiesValue(tdsyqDO, tdsyqVO);
            tdsyqVO.setZxdjrq(tdsyqDO.getZxdjsj());
            tdsyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(tdsyqDO.getDjlx(), zdMap.get(DJLX)));
            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(tdsyqDO.getXmid());
            tdsyqVO.setQlr(bdcXmDO.getQlr());
            tdsyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
            tdsyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            tdsyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
            tdsyqVO.setQszt(bdcXmDO.getQszt());

            tdsyqVOList.add(tdsyqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(tdsyqVOList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取土地承包经营权、农用地的其他使用权登记信息
     */
    @GetMapping("/tdcbjyqNyddqtsyq")
    public Page<BdcDjbxxTdcbjyqNyddqtsyqVO> queryTdcbjyqNyddqtsyq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取土地所有权登记信息 权利信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_TDCBJYQNYDDQTSYQ);
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLXX_QLLX_TDCBJYQNYDDQTSYQ)){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcTdcbnydsyqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcTdcbnydsyqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxTdcbjyqNyddqtsyqVO> tdcbjyqNyddqtsyqList = new ArrayList<>(bdcQlList.size());
        for (BdcTdcbnydsyqDO tdcbnydsyqDO : bdcQlList) {
            BdcDjbxxTdcbjyqNyddqtsyqVO tdcbjyqNyddqtsyqVO = new BdcDjbxxTdcbjyqNyddqtsyqVO();
            BeansResolveUtils.clonePropertiesValue(tdcbnydsyqDO, tdcbjyqNyddqtsyqVO);
            tdcbjyqNyddqtsyqVO.setZxdjrq(tdcbnydsyqDO.getZxdjsj());
            // 登记类型
            tdcbjyqNyddqtsyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getDjlx(), zdMap.get(DJLX)));
            // 水域滩涂类型
            tdcbjyqNyddqtsyqVO.setSyttlxmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getSyttlx(), zdMap.get("syttlx")));
            // 养殖业方式
            tdcbjyqNyddqtsyqVO.setYzyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getYzyfs(), zdMap.get("yzyfs")));
            // 土地所有权性质
            tdcbjyqNyddqtsyqVO.setTdsyqxzmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getTdsyqxz(), zdMap.get("tdsyqxz")));
            //承包方式
            tdcbjyqNyddqtsyqVO.setCbfsmc(null == tdcbnydsyqDO.getCbfs() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(tdcbnydsyqDO.getCbfs()), zdMap.get("cbfs")));
            //是否基本农田
            tdcbjyqNyddqtsyqVO.setSfjbntmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getSfjbnt(), zdMap.get("sf")));
            //承包土地用途
            tdcbjyqNyddqtsyqVO.setCbtdytmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getCbtdyt(), zdMap.get("cbtdyt")));
            //土地等级
            tdcbjyqNyddqtsyqVO.setCbdldjmc(null == tdcbnydsyqDO.getCbdldj() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(tdcbnydsyqDO.getCbdldj()), zdMap.get("cbdldj")));

            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(tdcbnydsyqDO.getXmid());
            tdcbjyqNyddqtsyqVO.setQlr(bdcXmDO.getQlr());
            tdcbjyqNyddqtsyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
            tdcbjyqNyddqtsyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            tdcbjyqNyddqtsyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
            tdcbjyqNyddqtsyqVO.setQszt(bdcXmDO.getQszt());
            //获取权利人信息
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(tdcbnydsyqDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOS = qlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                tdcbjyqNyddqtsyqVO.setCbftxdz(bdcQlrDOS.get(0).getTxdz());
                tdcbjyqNyddqtsyqVO.setCbflxdh(bdcQlrDOS.get(0).getDh());
            }
            //获取宗地信息
            BdcBdcdjbZdjbxxDO bdcdjbZdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(tdcbnydsyqDO.getBdcdyh());
            tdcbjyqNyddqtsyqVO.setBdcBdcdjbZdjbxxDO(bdcdjbZdjbxxDO);
            //获取家庭成员信息
            BdcQlrQO qlrQO = new BdcQlrQO();
            qlrQO.setXmid(tdcbnydsyqDO.getXmid());
            qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcJtcyDO> bdcJtcyDOList = jtcyFeignService.listBdcJtcyByQlr(qlrQO);
            for (BdcJtcyDO jtcyDO : bdcJtcyDOList) {
                jtcyDO.setYhzgx(null == jtcyDO.getYhzgx() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(jtcyDO.getYhzgx()), zdMap.get("yhzgx")));
                jtcyDO.setXb(null == jtcyDO.getXb() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(jtcyDO.getXb()), zdMap.get("xb")));
            }
            tdcbjyqNyddqtsyqVO.setJtcyDOList(bdcJtcyDOList);

            tdcbjyqNyddqtsyqList.add(tdcbjyqNyddqtsyqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(tdcbjyqNyddqtsyqList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    @GetMapping("/tdcbjyqNyddqtsyq/{qlrzjh}")
    public BdcTdcbjyqNydqtsyqHuKouVO queryTdcbjyqByOne(@PathVariable(name = "qlrzjh") String qlrzjh) {
        if (StringUtils.isBlank(qlrzjh)) {
            throw new AppException("缺少参数，请检查！");
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 获取土地所有权登记信息 权利信息
        List<BdcTdcbnydsyqDO> bdcQlList = new ArrayList<>();
        BdcTdcbjyqNydqtsyqHuKouVO tdcbjyqNyddqtsyqVO = new BdcTdcbjyqNydqtsyqHuKouVO();
        List<BdcTdcbjyqDkxxByHuKouVO> dkxxByHuKouVOList = new ArrayList<>();
        String jyhth = "";
        //查权利人xmid
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcQlrQO.setZjh(qlrzjh);
        List<BdcQlrDO> qlrDOList = qlrFeignService.listBdcQlr(bdcQlrQO);
        bdcQlrQO.setQlrid(qlrDOList.get(0).getQlrid());


        //组织权利信息
        if (CollectionUtils.isNotEmpty(qlrDOList)) {
            tdcbjyqNyddqtsyqVO.setCbftxdz(qlrDOList.get(0).getTxdz());
            tdcbjyqNyddqtsyqVO.setCbflxdh(qlrDOList.get(0).getDh());
            tdcbjyqNyddqtsyqVO.setQlr(qlrDOList.get(0).getQlrmc());
            tdcbjyqNyddqtsyqVO.setQlrzjh(qlrDOList.get(0).getZjh());
            //获取宗地代码
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setXmid(qlrDOList.get(0).getXmid());
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList) &&StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcdyh()) &&StringUtils.length(bdcXmDOList.get(0).getBdcdyh()) == CommonConstantUtils.BDCDYH_LENGTH){
                tdcbjyqNyddqtsyqVO.setZddm(bdcXmDOList.get(0).getBdcdyh().substring(0,19));
            }
            for (BdcQlrDO qlrDO : qlrDOList) {
                BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
                bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_TDCBJYQNYDDQTSYQ);
                bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
                bdcDjbqlQO.setXmid(qlrDO.getXmid());
                BdcTdcbnydsyqDO bdcQlLists = tdcbjyqNytqtsyqFeginService.queryByxmid(qlrDO.getXmid());
                if (null != bdcQlLists && StringUtils.isNotBlank(bdcQlLists.getQlid())) {
                    bdcQlList.add(bdcQlLists);
                }
            }

            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                BdcTdcbnydsyqDO tdcbnydsyqDO = bdcQlList.get(0);
                BeansResolveUtils.clonePropertiesValue(bdcQlList.get(0), tdcbjyqNyddqtsyqVO);
                tdcbjyqNyddqtsyqVO.setZxdjrq(tdcbnydsyqDO.getZxdjsj());
                // 登记类型
                tdcbjyqNyddqtsyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getDjlx(), zdMap.get(DJLX)));
                // 水域滩涂类型
                tdcbjyqNyddqtsyqVO.setSyttlxmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getSyttlx(), zdMap.get("syttlx")));
                // 养殖业方式
                tdcbjyqNyddqtsyqVO.setYzyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getYzyfs(), zdMap.get("yzyfs")));
                // 土地所有权性质
                tdcbjyqNyddqtsyqVO.setTdsyqxzmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getTdsyqxz(), zdMap.get("tdsyqxz")));
                //承包方式
                tdcbjyqNyddqtsyqVO.setCbfsmc(null == tdcbnydsyqDO.getCbfs() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(tdcbnydsyqDO.getCbfs()), zdMap.get("cbfs")));
                //是否基本农田
                tdcbjyqNyddqtsyqVO.setSfjbntmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getSfjbnt(), zdMap.get("sf")));
                //承包土地用途
                tdcbjyqNyddqtsyqVO.setCbtdytmc(StringToolUtils.convertBeanPropertyValueOfZd(tdcbnydsyqDO.getCbtdyt(), zdMap.get("cbtdyt")));
                //土地等级
                tdcbjyqNyddqtsyqVO.setCbdldjmc(null == tdcbnydsyqDO.getCbdldj() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(tdcbnydsyqDO.getCbdldj()), zdMap.get("cbdldj")));

                tdcbjyqNyddqtsyqVO.setQqdkzs(bdcQlList.size());
                Double mj = 0.0;

                for (BdcTdcbnydsyqDO bdctdcbnydsyq1 : bdcQlList) {
                    //从项目表获取jyhtbh
                    BdcXmDO bdcXmDO = queryBdcXm(bdctdcbnydsyq1.getXmid());
                    if (StringUtils.isNotBlank(bdcXmDO.getJyhth()) && !jyhth.contains(bdcXmDO.getJyhth())) {
                        jyhth += bdcXmDO.getJyhth() + ",";
                    }
                    if (null != bdctdcbnydsyq1.getCbmj()) {
                        mj += bdctdcbnydsyq1.getCbmj();
                    }
                    BdcBdcdjbZdjbxxDO bdcdjbZdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(bdctdcbnydsyq1.getBdcdyh());
                    BdcTdcbjyqDkxxByHuKouVO dkxxByHuKouVO = new BdcTdcbjyqDkxxByHuKouVO();
                    BeansResolveUtils.clonePropertiesValue(bdcdjbZdjbxxDO, dkxxByHuKouVO);
                    dkxxByHuKouVO.setFbfmc(bdctdcbnydsyq1.getFbfmc());
                    dkxxByHuKouVO.setFbfdm(bdctdcbnydsyq1.getFbfdm());
                    //查询义务人法人信息
                    BdcQlrQO bdcYwrQO = new BdcQlrQO();
                    bdcYwrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    bdcYwrQO.setXmid(bdctdcbnydsyq1.getXmid());
                    List<BdcQlrDO> ywrDOList = qlrFeignService.listBdcQlr(bdcYwrQO);
                    if (CollectionUtils.isNotEmpty(ywrDOList)) {
                        dkxxByHuKouVO.setFbffzr(ywrDOList.get(0).getFrmc());
                    }
                    dkxxByHuKouVO.setCbhtmj(bdctdcbnydsyq1.getCbmj());
                    dkxxByHuKouVO.setScmj(bdctdcbnydsyq1.getScmj());
                    dkxxByHuKouVO.setCbqssj(bdctdcbnydsyq1.getCbqssj());
                    dkxxByHuKouVO.setCbjssj(bdctdcbnydsyq1.getCbjssj());
                    dkxxByHuKouVO.setCbtdyt(tdcbjyqNyddqtsyqVO.getCbtdytmc());
                    dkxxByHuKouVO.setDldj(tdcbjyqNyddqtsyqVO.getCbdldjmc());
                    dkxxByHuKouVO.setDl(null == bdcdjbZdjbxxDO.getYt() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcdjbZdjbxxDO.getYt()), zdMap.get("tdyt")));
                    dkxxByHuKouVO.setSfjbnt(tdcbjyqNyddqtsyqVO.getSfjbntmc());
                    dkxxByHuKouVO.setBz(tdcbjyqNyddqtsyqVO.getFj());
                    dkxxByHuKouVO.setHtbh(bdcXmDO.getJyhth());
                    dkxxByHuKouVO.setCbfs(null == bdctdcbnydsyq1.getCbfs() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdctdcbnydsyq1.getCbfs()), zdMap.get("cbfs")));

                    dkxxByHuKouVOList.add(dkxxByHuKouVO);
                }
                tdcbjyqNyddqtsyqVO.setHtdm(jyhth);
                tdcbjyqNyddqtsyqVO.setCbqqzmj(mj);
                tdcbjyqNyddqtsyqVO.setDkxxByHuKouVOList(dkxxByHuKouVOList);


            }
            //组织家庭成员信息
            List<BdcJtcyDO> bdcJtcyDOList = jtcyFeignService.listBdcJtcyByQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcJtcyDOList)) {
                for (BdcJtcyDO jtcyDO : bdcJtcyDOList) {
                    jtcyDO.setYhzgx(null == jtcyDO.getYhzgx() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(jtcyDO.getYhzgx()), zdMap.get("yhzgx")));
                    jtcyDO.setXb(null == jtcyDO.getXb() ? "" : StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(jtcyDO.getXb()), zdMap.get("xb")));
                }
                tdcbjyqNyddqtsyqVO.setJtcyDOList(bdcJtcyDOList);
            }
            return tdcbjyqNyddqtsyqVO;

        }
        return null;
    }

    /**
     * @param qlrzjh 权利人证件号(多个用英文逗号隔开)
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据权利人证件号获取承包经营权目录
     */
    @GetMapping("/tdcbjyqml")
    public List<BdcQlrDO> queryTdcbjyqml(@RequestParam("qlrzjh") String qlrzjh) {
        if(StringUtils.isBlank(qlrzjh)){
            throw new AppException("查询登记簿缺失参数");
        }
        List<BdcQlrDO> bdcQlrDOList =new ArrayList<>();
        for(String zjh:qlrzjh.split(CommonConstantUtils.ZF_YW_DH)) {
            if(StringUtils.isNotBlank(zjh)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setZjh(zjh);
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> qlrDOList = qlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    bdcQlrDOList.add(qlrDOList.get(0));
                }
            }
        }
        return bdcQlrDOList;

    }


    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取林权登记信息登记信息
     */
    @GetMapping("/lq")
    public Page<BdcDjbxxLqVO> queryLq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取林权信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_LQ);
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLXX_QLLX_LQ)){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcLqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcLqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxLqVO> lqVOList = new ArrayList<>(bdcQlList.size());
        for (BdcLqDO lqDO : bdcQlList) {
            BdcDjbxxLqVO lqVO = new BdcDjbxxLqVO();
            BeansResolveUtils.clonePropertiesValue(lqDO, lqVO);
            lqVO.setZxdjrq(lqDO.getZxdjsj());
            // 登记类型
            lqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getDjlx(), zdMap.get(DJLX)));
            // 林种
            lqVO.setLzmc(StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getLz(), zdMap.get("lz")));
            // 林地所有权性质
            lqVO.setLdsyqxzmc(StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getLdsyqxz(), zdMap.get("tdsyqxz")));
            // 起源
            lqVO.setQymc(StringToolUtils.convertBeanPropertyValueOfZd(lqDO.getQy(), zdMap.get("qy")));
            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(lqDO.getXmid());
            lqVO.setLdqlr(bdcXmDO.getQlr());
            lqVO.setQlrzjh(bdcXmDO.getQlrzjh());
            lqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            lqVO.setQlrlx(bdcXmDO.getQlrlx());
            lqVO.setBdcqzh(bdcXmDO.getBdcqzh());
            lqVO.setQszt(bdcXmDO.getQszt());


            lqVOList.add(lqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(lqVOList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取地役权登记信息
     */
    @GetMapping("/dyiq")
    public Page<BdcDjbxxDyiqVO> queryDyiq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取地役权信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLXX_QLLX_DYQ);
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLXX_QLLX_DYQ)){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcDyiqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcDyiqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxDyiqVO> dyiqVOList = new ArrayList<>(bdcQlList.size());
        for (BdcDyiqDO dyiqDO : bdcQlList) {
            BdcDjbxxDyiqVO dyiqVO = new BdcDjbxxDyiqVO();

            // 拷贝权利信息
            BeansResolveUtils.clonePropertiesValue(dyiqDO, dyiqVO);
            // 登记类型
            dyiqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(dyiqDO.getDjlx(), zdMap.get(DJLX)));
            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(dyiqDO.getXmid());
            dyiqVO.setXydqlr(bdcXmDO.getQlr());
            dyiqVO.setXydqlrzjh(bdcXmDO.getQlrzjh());
            dyiqVO.setXydqlrzjzl(bdcXmDO.getQlrzjzl());
            dyiqVO.setGydqlr(bdcXmDO.getYwr());
            dyiqVO.setGydqlrzjh(bdcXmDO.getYwrzjh());
            dyiqVO.setGydqlrzjzl(bdcXmDO.getYwrzjzl());
            dyiqVO.setBdcqzmh(bdcXmDO.getBdcqzh());
            dyiqVO.setQszt(bdcXmDO.getQszt());
            dyiqVOList.add(dyiqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(dyiqVOList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 权利查询参数QO
     * @return Page<BdcdjbDyaqVO> 抵押权分页对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登记簿抵押权页面修改
     */
    @GetMapping("/dyaq")
    public Page<BdcdjbDyaqVO> queryDyaq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 获取抵押权信息
        bdcDjbqlQO.setQllx("BdcDyaqDO");
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLLX_DYAQ_DM.toString())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcdjbDyaqVO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcdjbDyaqVO.class);

        appendZxxxToFj(bdcQlList);

        for (BdcdjbDyaqVO bdcdjbDyaqVO : bdcQlList) {
            // 登记类型字典项转换
            bdcdjbDyaqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbDyaqVO.getDjlx(), zdMap.get(DJLX)));
            bdcdjbDyaqVO.setDyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbDyaqVO.getDyfs(), zdMap.get(DYFS)));
            // 页面展示日期重新赋值
            bdcdjbDyaqVO.setDjrq(bdcdjbDyaqVO.getDjsj());
            bdcdjbDyaqVO.setZwlxqsrq(bdcdjbDyaqVO.getZwlxqssj());
            bdcdjbDyaqVO.setZwlxjsrq(bdcdjbDyaqVO.getZwlxjssj());
            bdcdjbDyaqVO.setZxdydjrq(bdcdjbDyaqVO.getZxdydjsj());
            // 获取权利人信息
            BdcXmDO bdcXmDO = queryBdcXm(bdcdjbDyaqVO.getXmid());
            bdcdjbDyaqVO.setDyqr(bdcXmDO.getQlr());
            bdcdjbDyaqVO.setDyqrzjh(bdcXmDO.getQlrzjh());
            bdcdjbDyaqVO.setDyqrzjzl(bdcXmDO.getQlrzjzl());
            bdcdjbDyaqVO.setDyr(bdcXmDO.getYwr());
            bdcdjbDyaqVO.setBdcqzmh(bdcXmDO.getBdcqzh());
            bdcdjbDyaqVO.setQszt(bdcXmDO.getQszt());
            Integer jzzr = bdcdjbDyaqVO.getJzzr();
            if (jzzr != null) {
                bdcdjbDyaqVO.setJzzrmc(jzzr == 1 ? "是" : "否");
            }
        }
        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcQlList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 权利查询参数QO
     * @return Page<BdcdjbCfVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登记簿查封分页查询
     */
    @GetMapping("/cf")
    public Page<BdcdjbCfVO> queryCf(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 获取抵押权信息
        bdcDjbqlQO.setQllx("BdcCfDO");
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLLX_CF.toString())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcdjbCfVO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcdjbCfVO.class);

        appendZxxxToFj(bdcQlList);

        for (BdcdjbCfVO bdcdjbCfVO : bdcQlList) {
            // 字典项转换
            bdcdjbCfVO.setCflxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbCfVO.getCflx(), zdMap.get(CFLX)));
            // 时间日期转换
            bdcdjbCfVO.setCfqsrq(bdcdjbCfVO.getCfqssj());
            bdcdjbCfVO.setCfjsrq(bdcdjbCfVO.getCfjssj());
            bdcdjbCfVO.setDjrq(bdcdjbCfVO.getDjsj());
            bdcdjbCfVO.setJfdjrq(bdcdjbCfVO.getJfdjsj());
        }
        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcQlList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 登记簿权利查询对象
     * @return Page<BdcdjbQtxgqlVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 其他相关权利查询
     */
    @GetMapping("/qtxgql")
    public Page<BdcdjbQtxgqlVO> queryQtxgql(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 获取其他相关权利
        bdcDjbqlQO.setQllx("BdcQtxgqlDO");
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains("99")){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcdjbQtxgqlVO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcdjbQtxgqlVO.class);

        appendZxxxToFj(bdcQlList);

        for (BdcdjbQtxgqlVO bdcdjbQtxgqlVO : bdcQlList) {
            bdcdjbQtxgqlVO.setDjrq(bdcdjbQtxgqlVO.getDjsj());
            bdcdjbQtxgqlVO.setZxdjrq(bdcdjbQtxgqlVO.getZxdjsj());
            bdcdjbQtxgqlVO.setQlqsrq(bdcdjbQtxgqlVO.getQlqssj());
            bdcdjbQtxgqlVO.setQljsrq(bdcdjbQtxgqlVO.getQljssj());
            // 登记类型名称
            bdcdjbQtxgqlVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbQtxgqlVO.getDjlx(), zdMap.get(DJLX)));
            // 权利类型名称
            bdcdjbQtxgqlVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbQtxgqlVO.getQllx(), zdMap.get(QLLX)));
            // 设置权利人信息
            BdcXmDO bdcXmDO = queryBdcXm(bdcdjbQtxgqlVO.getXmid());
            bdcdjbQtxgqlVO.setQlr(bdcXmDO.getQlr());
            bdcdjbQtxgqlVO.setQlrlx(bdcXmDO.getQlrlx());
            bdcdjbQtxgqlVO.setQlrzjh(bdcXmDO.getQlrzjh());
            bdcdjbQtxgqlVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            bdcdjbQtxgqlVO.setBdcqzh(bdcXmDO.getBdcqzh());
            bdcdjbQtxgqlVO.setQszt(bdcXmDO.getQszt());
        }
        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcQlList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 查询参数
     * @return {Page} 权利分页信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取土地经营权登记信息
     */
    @GetMapping("/tdjyq")
    public Page<BdcDjbxxTdjyqVO> queryTdjyq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 获取土地经营权登记信息 权利信息
        bdcDjbqlQO.setQllx(CommonConstantUtils.QLLX_NYDJYQ.toString());
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLLX_NYDJYQ.toString())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcNydjyqDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcNydjyqDO.class);

        appendZxxxToFj(bdcQlList);

        List<BdcDjbxxTdjyqVO> tdjyqVOList = new ArrayList<>(bdcQlList.size());
        for (BdcNydjyqDO nydjyqDO : bdcQlList) {
            BdcDjbxxTdjyqVO tdjyqVO = new BdcDjbxxTdjyqVO();
            BeansResolveUtils.clonePropertiesValue(nydjyqDO, tdjyqVO);
            // 登记类型
            tdjyqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(nydjyqDO.getDjlx(), zdMap.get(DJLX)));
            // 经营用途
            if(nydjyqDO.getJyyt() != null) {
                tdjyqVO.setJyytmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(nydjyqDO.getJyyt()), zdMap.get("cbtdyt")));
            }


            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(nydjyqDO.getXmid());
            tdjyqVO.setQlr(bdcXmDO.getQlr());
            tdjyqVO.setQlrzjh(bdcXmDO.getQlrzjh());
            tdjyqVO.setQlrzjzl(bdcXmDO.getQlrzjzl());
            tdjyqVO.setBdcqzh(bdcXmDO.getBdcqzh());
            tdjyqVO.setQszt(bdcXmDO.getQszt());

            //获取宗地信息
            BdcBdcdjbZdjbxxDO bdcdjbZdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(nydjyqDO.getBdcdyh());
            tdjyqVO.setBdcBdcdjbZdjbxxDO(bdcdjbZdjbxxDO);
            tdjyqVOList.add(tdjyqVO);
        }

        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(tdjyqVOList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param bdcDjbqlQO 权利查询参数QO
     * @return Page<BdcdjbJzqVO> 居住权分页对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记簿居住权页面修改
     */
    @GetMapping("/jzq")
    public Page<BdcdjbJzqVO> queryJzq(BdcDjbqlQO bdcDjbqlQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 获取抵押权信息
        bdcDjbqlQO.setQllx("BdcJzqDO");
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLLX_JZQ.toString())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        JSONObject jsonObject = JSON.parseObject(bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO));
        List<BdcdjbJzqVO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcdjbJzqVO.class);

        appendZxxxToFj(bdcQlList);

        for (BdcdjbJzqVO bdcdjbJzqVO : bdcQlList) {
            // 登记类型字典项转换
            bdcdjbJzqVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcdjbJzqVO.getDjlx(), zdMap.get(DJLX)));
            // 页面展示日期重新赋值
            bdcdjbJzqVO.setDjrq(bdcdjbJzqVO.getDjsj());
            bdcdjbJzqVO.setJzqksrq(bdcdjbJzqVO.getJzqkssj());
            bdcdjbJzqVO.setJzqjsrq(bdcdjbJzqVO.getJzqjssj());
            // 获取权利人信息
            BdcXmDO bdcXmDO = queryBdcXm(bdcdjbJzqVO.getXmid());
            bdcdjbJzqVO.setJzqr(bdcXmDO.getQlr());
            bdcdjbJzqVO.setJzqrzjh(bdcXmDO.getQlrzjh());
            bdcdjbJzqVO.setJzqrzjzl(bdcXmDO.getQlrzjzl());
            bdcdjbJzqVO.setYwr(bdcXmDO.getYwr());
            bdcdjbJzqVO.setBdcqzmh(bdcXmDO.getBdcqzh());
            bdcdjbJzqVO.setDjyy(bdcXmDO.getDjyy());
            bdcdjbJzqVO.setZl(bdcXmDO.getZl());
            bdcdjbJzqVO.setQszt(bdcXmDO.getQszt());
        }
        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcQlList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目信息
     */
    private BdcXmDO queryBdcXm(String xmid) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        if (StringUtils.isBlank(xmid)) {
            return bdcXmDO;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return bdcXmDO;
        }
        return bdcXmDOList.get(0);
    }

    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目附表信息
     */
    private BdcXmFbDO queryBdcXmFb(String xmid) {
        BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
        if (StringUtils.isBlank(xmid)) {
            return bdcXmFbDO;
        }
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(xmid);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isEmpty(bdcXmFbDOList)) {
            return bdcXmFbDO;
        }
        return bdcXmFbDOList.get(0);
    }

    /**
     * @param bdcDjbqlQO
     * @return Page<BdcYyVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记簿中的异议信息
     */
    @GetMapping("/bdcYy")
    public Page<BdcYyVO> queryBdcYyList(BdcDjbqlQO bdcDjbqlQO) {
        bdcDjbqlQO.setQsztList(Arrays.asList(QSZT));
        if(CollectionUtils.isNotEmpty(sfcxzxxm) && sfcxzxxm.contains(CommonConstantUtils.QLLX_YY.toString())){
            bdcDjbqlQO.setSfcxzx(true);
        }
        String bdcYy = bdcDjbxxFeignService.bdcQlJsonByPage(bdcDjbqlQO);
        if (StringUtils.isEmpty(bdcYy)) {
            return new PageImpl<>(Lists.newArrayList());
        }
        JSONObject jsonObject = JSON.parseObject(bdcYy);
        List<BdcYyDO> bdcQlList = JSON.parseArray(JSON.toJSONString(jsonObject.get(PAGE_CONTENT)), BdcYyDO.class);

        appendZxxxToFj(bdcQlList);

        if (CollectionUtils.isEmpty(bdcQlList)) {
            return new PageImpl<>(Lists.newArrayList());
        }
        List<BdcYyVO> bdcYyVoList = new ArrayList<>(bdcQlList.size());
        for (BdcYyDO yyDO : bdcQlList) {
            BdcYyVO bdcYyVO = new BdcYyVO();
            BeansResolveUtils.clonePropertiesValue(yyDO, bdcYyVO);
            // 获取项目信息
            BdcXmDO bdcXmDO = queryBdcXm(yyDO.getXmid());
            bdcYyVO.setSqr(bdcXmDO.getQlr());
            bdcYyVO.setSqrzjh(bdcXmDO.getQlrzjh());
            bdcYyVO.setSqrzjzl(bdcXmDO.getQlrzjzl());
            bdcYyVO.setBdcqzmh(bdcXmDO.getBdcqzh());
            bdcYyVO.setQszt(bdcXmDO.getQszt());
            bdcYyVoList.add(bdcYyVO);
        }
        // 封装返回分页信息
        Pageable pageable = new PageRequest(jsonObject.getInteger(PAGE_NUMBER), jsonObject.getInteger(PAGE_SIZE));
        return new PageImpl(bdcYyVoList, pageable, jsonObject.getInteger(TOTAL_ELEMENTS));
    }

    /**
     * @param pageable
     * @param bdcdyh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取建筑物区分所有权业主共有部分登记信息
     */
    @GetMapping(value = "/gyxx/{bdcdyh}")
    @ResponseStatus(HttpStatus.OK)
    public Page<BdcFdcq3GyxxDTO> listGyxx(Pageable pageable, @PathVariable(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isEmpty(bdcdyh)) {
            throw new MissingArgumentException("bdcdyh");
        }
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setBdcdyh(bdcdyh);
        bdcDjbqlQO.setSize(pageable.getPageSize());
        bdcDjbqlQO.setPage(pageable.getPageNumber());
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        bdcDjbqlQO.setSort(sort);
        return bdcDjbxxFeignService.bdcFdcq3GyxxDOByPageJson(bdcDjbqlQO);
    }



    /**
     * @return xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    @GetMapping(value = "/fdcq3/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public String queryQlr(@PathVariable(name = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("没有参数xmid");
        }
        return bdcFdcq3GyxxFeignService.getFdcq3Qlr(xmid);
    }

    /**
     * 根据gzlslid查询地块信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:23 2020/8/25
     */
    @GetMapping(value = "/tdcbjyqdkxx/{gzlslid}")
    public List<BdcDkxxDTO> queryByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("没有参数xmid");
        }
        return tdcbjyqNytqtsyqFeginService.queryDkxxByGzlslid(gzlslid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    @PostMapping(value = "/tdcbjyq/dkxx")
    public List<BdcDkxxDTO> queryTdcbjyqDkxx(@RequestBody BdcDkxxQO bdcDkxxQO) {
        if (null == bdcDkxxQO) {
            throw new MissingArgumentException("未定义查询参数");
        }
        return tdcbjyqNytqtsyqFeginService.queryTdcbjyqDkxx(bdcDkxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    @GetMapping(value = "/tdcbjyq/dkxx/beforeinit/{xmid}")
    public List<BdcDkxxDTO> queryByGzlslidBeforeinit(@PathVariable(name = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("没有参数xmid");
        }

        BdcDkxxQO bdcDkxxQO = new BdcDkxxQO();
        bdcDkxxQO.setXmid(xmid);
        return tdcbjyqNytqtsyqFeginService.queryTdcbjyqDkxxBeforeZsInit(bdcDkxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param lzrzjh 领证人证件号
     * @return {List} 家庭成员信息列表
     * @description 农村不动产确权登记家庭成员信息列表
     */
    @GetMapping(value = "/ncbdcqqdj/jtcyxx/{lzrzjh}")
    public List<BdcJtcyDO> queryNcbdcqqdjJtcyxx(@PathVariable(name = "lzrzjh") String lzrzjh) {
        if (StringUtils.isBlank(lzrzjh)) {
            throw new MissingArgumentException("领证人证件号为空");
        }
        List<BdcJtcyDO> bdcJtcyDOS = jtcyFeignService.queryJtcyxxByHzzjh(lzrzjh);
        return bdcJtcyDOS;
    }
}