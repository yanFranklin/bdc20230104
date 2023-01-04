package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlQlxxymDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlqtzkFjDataDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcFdcq3GyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.ui.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/13
 * @description 不动产权利信息表单请求处理
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcQlxxController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQlxxController.class.getName());
    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcQlxxController.class.getName();

    /**
     * 常量定义
     */
    // 不动产单元号
    public static final String BDCDYH = "bdcdyh";
    // 供役地不动产单元号
    public static final String GYDBDCDYH = "gydbdcdyh";
    // 设值不动产单元号
    public static final String SET_BDCDYH = "setBdcdyh";

    /**
     * 权利操作服务
     */
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    /**
     * 权利人操作服务
     */
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * 实体操作服务
     */
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;

    /**
     * 字典服务
     */
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * 项目服务
     */
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    /**
     * 初始化证书服务
     */
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;

    /**
     * 历史关系服务
     */
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    /**
     * 建筑物共有信息服务接口
     */
    @Autowired
    private BdcFdcq3GyxxFeignService bdcFdcq3GyxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcSdFeignService bdcSdFeignService;

    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;

    @Value("${html.version}")
    String version;

    /**
     *  验证建筑面积=分摊建筑面积+专有建筑面积，默认为：true（开启） false（不开启）
     */
    @Value("${yzjzmj:true}")
    private boolean yzjzmj;

    /**
     * 权利信息 预告页面保存时数据库取得价格和交易金额同时保存取得价格
     */
    @Value("${qlxx.yg.qdjg_jyje:false}")
    private boolean qdjgJyje;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动更新使用期限
     */
    @Value("${syqx.zdgx:false}")
    private Boolean updateSyqx;

    /**
     * @param xmid 项目ID
     * @return {BdcQl} 权利信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询项目关联的权利信息
     */
    @GetMapping("/qlxx/{xmid}")
    public BdcQl queryBdcQl(@PathVariable("xmid") String xmid) {
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (null == bdcQl) {
            return null;
        }

        /**
         * 不动产单元号需要格式化显示
         */
        // 地役权对应格式化字段是：供役地不动产单元号
        if (bdcQl instanceof BdcDyiqDO) {
            ((BdcDyiqDO) bdcQl).setGydbdcdyh(BdcdyhToolUtils.formatBdcdyh(((BdcDyiqDO) bdcQl).getGydbdcdyh()));
            return bdcQl;
        }

        // 非地役权（假设有BDCDYH）对应格式化字段是：不动产单元号
        try {
            // 获取格式化不动产单元号
            Field field = bdcQl.getClass().getDeclaredField(BDCDYH);
            field.setAccessible(true);
            String bdcdyh = (String) field.get(bdcQl);
            bdcdyh = BdcdyhToolUtils.formatBdcdyh(bdcdyh);

            // 更新不动产单元号为格式化数据
            Method method = bdcQl.getClass().getMethod(SET_BDCDYH, String.class);
            method.invoke(bdcQl, bdcdyh);
        } catch (NoSuchFieldException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            // 此处异常无需输出错误日志，因为允许某些权利无不动产单元号获取属性时报错
            LOGGER.warn("{}：当前权利不存在不动产单元号，项目ID：{}", CLASS_NAME, xmid);
        }
        return bdcQl;
    }

    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目信息
     */
    @GetMapping("/qlxx/xmxx/{xmid}")
    public BdcXmDO queryXmxx(@PathVariable(name = "xmid") String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return null;
    }


    /**
     * @param gzlslid 工作量实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　BdcXmDO 项目信息
     * @description　根据工作流实例id获取一个项目信息
     */
    @GetMapping("/qlxx/oneXm/{gzlslid}")
    public BdcXmDO queryOneXm(@PathVariable(name = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return queryOneXm(bdcXmQO);
    }

    @GetMapping("/qlxx/oneXm/xmxx/{xmid}")
    public BdcXmDO queryOneXmxx(@PathVariable(name = "xmid") String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        BdcXmQO tempbdcXmQO = new BdcXmQO();
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            tempbdcXmQO.setGzlslid(gzlslid);
        }
        return queryOneXm(tempbdcXmQO);
    }

    /**
     * @param gzlslid 工作量实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　BdcXmDO 项目信息
     * @description　根据工作流实例id和权利类型获取一个项目信息
     */
    @GetMapping("/qlxx/oneXm/dywqd/{gzlslid}/{qllx}")
    public BdcXmDO queryDywqdOneXm(@PathVariable(name = "gzlslid") String gzlslid,
                                   @PathVariable(name = "qllx") String qllx) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        List list = new ArrayList(0);
        list.add(qllx);
        bdcXmQO.setQllxs(list);
        bdcXmQO.setGzlslid(gzlslid);
        return queryOneXm(bdcXmQO);
    }

    @GetMapping("/qlxx/oneXm/dywqd/xmxx/{xmid}/{qllx}")
    public BdcXmDO queryOneXmxx(@PathVariable(name = "xmid") String xmid,
                                @PathVariable(name = "qllx") String qllx) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        BdcXmQO tempbdcXmQO = new BdcXmQO();
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            tempbdcXmQO.setGzlslid(gzlslid);
            List list = new ArrayList(0);
            list.add(qllx);
            bdcXmQO.setQllxs(list);
        }
        return queryOneXm(tempbdcXmQO);
    }

    private BdcXmDO queryOneXm(BdcXmQO bdcXmQO) {
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmTemp = bdcXmDOList.get(0);
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setSlbh(bdcXmTemp.getSlbh());
            bdcXmDO.setQlr(bdcXmTemp.getQlr());
            // 计算定着物总面积
            Double dzwzmj = 0.0;
            //计算宗地总面积
            Double zdzmj = 0.0;
            // 获取义务人信息
            Set<String> ywrSet = new HashSet();
            Set<String> qlrSet = new HashSet();
            Set<String> bdcdyhSet = new HashSet();
            for (BdcXmDO bdcXmDOParam : bdcXmDOList) {
                // 定着物/宗地总面积
                if (StringUtils.equals(CommonConstantUtils.BDCLX_TZM_F, BdcdyhToolUtils.getDzwTzm(bdcXmTemp.getBdcdyh())) && null != bdcXmDOParam.getDzwmj()) {
                    dzwzmj += bdcXmDOParam.getDzwmj();
                } else if (null != bdcXmDOParam.getZdzhmj()) {
                    zdzmj += bdcXmDOParam.getZdzhmj();
                }

                // 义务人信息
                String[] ywrArr = StringUtils.split(bdcXmDOParam.getYwr(), CommonConstantUtils.ZF_YW_DH);
                if (ArrayUtils.isNotEmpty(ywrArr)) {
                    for (String ywr : ywrArr) {
                        ywrSet.add(ywr);
                    }
                }

                // 权利人信息
                String[] qlrArr = StringUtils.split(bdcXmDOParam.getQlr(), CommonConstantUtils.ZF_YW_DH);
                if (ArrayUtils.isNotEmpty(qlrArr)) {
                    for (String qlr : qlrArr) {
                        qlrSet.add(qlr);
                    }
                }

                bdcdyhSet.add(bdcXmDOParam.getBdcdyh());
            }

            bdcXmDO.setDzwmj(dzwzmj);
            bdcXmDO.setZdzhmj(zdzmj);
            if (CollectionUtils.isNotEmpty(ywrSet)) {
                bdcXmDO.setYwr(StringUtils.join(ywrSet, CommonConstantUtils.ZF_YW_DH));
            }
            if (CollectionUtils.isNotEmpty(qlrSet)) {
                bdcXmDO.setQlr(StringUtils.join(qlrSet, CommonConstantUtils.ZF_YW_DH));
            }
            return bdcXmDO;
        }
        return null;
    }

    @GetMapping("/qlxx/tdsyqx/{xmid}")
    public BdcDldmSyqxGxDO queryDldmSyqxGx(@PathVariable("xmid") String xmid) {
        return bdcZdFeignService.queryDldmSyqxGx(xmid);
    }


    /**
     * @param qlid 权利ID
     * @return {List}  房地产权的项目内多幢项目信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取房地产权的项目内多幢项目信息
     */
    @GetMapping("/qlxx/{qlid}/fdcqxm")
    public List<BdcFdcqFdcqxmDO> listBdcFdcqFdcqxm(@PathVariable("qlid") String qlid) {
        return bdcQllxFeignService.listFdcqXm(qlid);
    }

    /**
     * @param qlid 权利ID
     * @return {List} 建筑物区分所有权业主共有部分登记信息_共有部分信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取建筑物区分所有权业主共有部分登记信息_共有部分息
     */
    @GetMapping("/qlxx/{qlid}/fdcq3/gyxx")
    public List<BdcFdcq3GyxxDO> listFdcq3Gyxx(@PathVariable("qlid") String qlid) {
        return bdcQllxFeignService.listFdcq3Gyxx(qlid);
    }

    /**
     * @param json 土地所有权表单json数据
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新土地所有权信息
     */
    @PutMapping(value = "/qlxx/tdsyq")
    public int updateTdsyq(@RequestBody String json) {
        // 不动产单元号前端显示为格式化数据且只读，直接不更新，避免更新错误数据，其它权利同理
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcTdsyqDO.class.getName());
    }

    /**
     * @param json 建设用地使用权、宅基地使用权
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新建设用地使用权、宅基地使用权信息
     */
    @PutMapping(value = "/qlxx/jsydsyq")
    public int updateJsydsyq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcJsydsyqDO.class.getName());
    }

    /**
     * @param json 房地产权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新房地产权登记信息
     */
    @PutMapping(value = "/qlxx/fdcq")
    public int updateFdcq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcFdcqDO.class.getName());
    }

    /**
     * @param json 同步更新相关的项目信息（只对单个项目更新）
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @PutMapping(value = "/qlxx/xmxx")
    public int updateXmxx(@RequestBody String json) {
        int result = 0;
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey(Constants.XMID) && StringUtils.isNotBlank(jsonObject.getString(Constants.XMID))) {
            //更新使用期限
            if(Boolean.TRUE.equals(updateSyqx)){
                if(StringUtils.isNotBlank(jsonObject.getString(Constants.XMID))) {
                    bdcRyzdFeignService.updateSyqx(jsonObject.getString(Constants.XMID));
                }
            }
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setXmid(jsonObject.getString(Constants.XMID));
            boolean needUpdate = false;
            // 建筑面积
            if (jsonObject.containsKey(Constants.JZMJ)) {
                bdcXmDO.setDzwmj(jsonObject.getDouble(Constants.JZMJ));
                needUpdate = true;
            }
            // 坐落
            if (jsonObject.containsKey(Constants.ZL)) {
                bdcXmDO.setZl(jsonObject.getString(Constants.ZL));
                needUpdate = true;
            }
            // 房屋规划用途
            if (jsonObject.containsKey(Constants.FWGHYT)) {
                bdcXmDO.setDzwyt(jsonObject.getInteger(Constants.FWGHYT));
                needUpdate = true;
            }
            // 保存交易合同号
            if (jsonObject.containsKey(Constants.JYHTH)) {
                bdcXmDO.setJyhth(jsonObject.getString(Constants.JYHTH));
                needUpdate = true;
            }
            // 备注
            if (jsonObject.containsKey(Constants.CFBZ)) {
                bdcXmDO.setBz(jsonObject.getString(Constants.CFBZ));
                needUpdate = true;
            }
            // 宗地宗海用途
            if (jsonObject.containsKey(Constants.ZDZHYT)) {
                bdcXmDO.setZdzhyt(jsonObject.getString(Constants.ZDZHYT));
                needUpdate = true;
            }
            //定着物用途
            if (jsonObject.containsKey("dzwyt") && Objects.nonNull(jsonObject.getInteger("dzwyt"))) {
                bdcXmDO.setDzwyt(jsonObject.getInteger("dzwyt"));
                needUpdate = true;
            }
            //定着物用途名称
            if (jsonObject.containsKey("dzwytmc")) {
                bdcXmDO.setDzwytmc(jsonObject.getString("dzwytmc"));
                needUpdate = true;
            }

            if (needUpdate) {
                try {
                    return bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSON(bdcXmDO).toString(), BdcXmDO.class.getName());
                } catch (Exception e) {
                    LOGGER.error("同步更新相关的项目信息异常！{}", e.getCause().getMessage());
                    result = -1;
                }
            }


        }
        return result;
    }

    /**
     * @param json 房地产权登记信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新房地产权（项目内多幢）项目信息 （会有多条，提交的JSON数据为JSON数组）
     */
    @PutMapping(value = "/qlxx/fdcq/fdcqxm")
    public int updateFdcqXmxx(@RequestBody String json) {
        int count = 0;
        for (Object obj : JSON.parseArray(json)) {
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcFdcqFdcqxmDO.class.getName());
        }

        return count;
    }

    /**
     * @param json 建筑物区分所有权业主共有部分登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新建筑物区分所有权业主共有部分登记信息
     */
    @PutMapping(value = "/qlxx/fdcq3")
    public int updateFdcq3(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcFdcq3DO.class.getName());
    }

    /**
     * @param json 建筑物区分所有权业主共有部分登记信息_共有部分信
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新建筑物区分所有权业主共有部分登记信息_共有部分信息（会有多条，提交的JSON数据为JSON数组）
     */
    @PutMapping(value = "/qlxx/fdcq3/gyxx")
    public int updateFdcq3Gyxx(@RequestBody String json) {
        int count = 0;
        for (Object obj : JSON.parseArray(json)) {
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcFdcq3GyxxDO.class.getName());
        }

        return count;
    }

    /**
     * @param json 构（建）筑物所有权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新构（建）筑物所有权登记信息
     */
    @PutMapping(value = "/qlxx/gjzwsyq")
    public int updateGjzwsyq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcGjzwsyqDO.class.getName());
    }

    /**
     * @param json 海域（含无居民海岛） 使用权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新海域（含无居民海岛） 使用权登记信息
     */
    @PutMapping(value = "/qlxx/hysyq")
    public int updateHysyq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcHysyqDO.class.getName());
    }

    /**
     * @param json 土地承包经营权、农用地的其他使用权登记信息（非林地）
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新土地承包经营权、农用地的其他使用权登记信息（非林地）
     */
    @PutMapping(value = "/qlxx/tdcbnydsyq")
    public int updateTdcbnydsyq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcTdcbnydsyqDO.class.getName());
    }

    /**
     * @param json 林权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新林权登记信息
     */
    @PutMapping(value = "/qlxx/lq")
    public int updateLq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcLqDO.class.getName());
    }

    /**
     * @param json 地役权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新地役权登记信息
     */
    @PutMapping(value = "/qlxx/dyiq")
    public int updateDyiq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(GYDBDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcDyiqDO.class.getName());
    }

    /**
     * @param json 抵押权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新抵押权登记信息
     */
    @PutMapping(value = "/qlxx/dyaq")
    public int updateDyaq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcDyaqDO.class.getName());
    }

    /**
     * @param json 预告登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新预告登记信息
     */
    @PutMapping(value = "/qlxx/yg")
    public int updateYg(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);

        if(qdjgJyje && null != jsonObject.get("qdjg")) {
            // 设置交易金额和取得价格一致
            jsonObject.put("jyje", jsonObject.get("qdjg"));
        }

        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcYgDO.class.getName());
    }

    /**
     * @param json 异议登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新异议登记信息
     */
    @PutMapping(value = "/qlxx/yy")
    public int updateYy(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcYyDO.class.getName());
    }

    /**
     * @param json 查封登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新查封登记信息
     */
    @PutMapping(value = "/qlxx/cf")
    public int updateCf(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcCfDO.class.getName());
    }

    /**
     * @param json 其他相关权利登记信息（取水权、探矿权、采矿权等）
     * @return {int} 更新记录条数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新其他相关权利登记信息（取水权、探矿权、采矿权等）
     */
    @PutMapping(value = "/qlxx/qtxgql")
    public int updateQtxgql(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcQtxgqlDO.class.getName());
    }

    /**
     * @param json 土地经营权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新土地经营权登记信息
     */
    @PutMapping(value = "/qlxx/tdjyq")
    public int updateTdjyq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcNydjyqDO.class.getName());
    }

    /**
     * @param json 居住权登记信息
     * @return {int} 更新记录条数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新居住权登记信息
     */
    @PutMapping(value = "/qlxx/jzq")
    public int updateJzq(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(BDCDYH);
        return bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), BdcJzqDO.class.getName());
    }

    /**
     * @param json 数据json
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利其他状况和附记
     */
    @PutMapping(value = "/qlxx/qlqtzkAndFj")
    public void updateQlqtzkAndFj(@RequestBody String json) {
        List<String> modelList = new ArrayList();
        modelList.add(CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ);
        modelList.add(CommonConstantUtils.XT_QLQTZK_FJ_MODE_QLQTZK);

        BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
        bdcQlqtzkFjQO.setJsonStr(json);
        bdcQlqtzkFjQO.setModeList(modelList);

        bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
    }

    /**
     * @param xmid 项目ID
     * @return {List} 权利人信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询项目关联的权利人信息
     */
    @GetMapping("/qlr/{xmid}")
    public List<BdcQlrDO> queryBdcQlr(@PathVariable("xmid") String xmid) {
        BdcQlrQO bdcQlr = new BdcQlrQO();
        bdcQlr.setXmid(xmid);
        bdcQlr.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        return bdcQlrFeignService.listBdcQlr(bdcQlr);
    }

    /**
     * @param xmid 项目ID
     * @return {List} 权利信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询项目关联的义务人信息
     */
    @GetMapping("/ywr/{xmid}")
    public List<BdcQlrDO> queryBdcYwr(@PathVariable("xmid") String xmid) {
        BdcQlrQO bdcQlr = new BdcQlrQO();
        bdcQlr.setXmid(xmid);
        bdcQlr.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        return bdcQlrFeignService.listBdcQlr(bdcQlr);
    }

    /**
     * @return {Map} 字典集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权利用到的字典
     */
    @GetMapping("/qlxx/zd")
    public Map<String, List<Map>> listZd() {
        return bdcZdFeignService.listBdcZd();
    }


    /**
     * @param xmid 建设用地使用权、宅基地使用权
     * @return {int} 更新记录条数
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新建设用地使用权、宅基地使用权信息
     */
    @PostMapping(value = "/qlxx/delete/fj/{xmid}")
    public void clearQlfj(@PathVariable("xmid") String xmid) {
        bdcZsInitFeignService.clearQlFj(xmid);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid] 工作流实例ID
     * @return: List<BdcXmDO> 项目信息集合
     * @description 通过工作流实例id获取所有的项目信息
     */
    @PostMapping("/qlxx/allxm/{gzlslid}")
    public List<BdcXmDO> queryAllXm(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("未获取到工作流实例ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }

    /**
     * @param xmid 项目ID
     * @return 权利其他状况模板
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 根据项目ID获取权利其他状况或者附记模板模式(2 : 权利其他状况 3 : 附记)
     */
    @ResponseBody
    @GetMapping("/queryQlqtzkFjMb")
    public String queryQlqtzkFjMb(String xmid, String mode) {
        // 获取模板类型
        String mbnr = bdcZsInitFeignService.queryQlqtzkFj(xmid, mode);
        List<String> modeList = new ArrayList();
        modeList.add(mode);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.BFQLQTZK, mbnr);
        jsonObject.put(Constants.FJ, mbnr);
        jsonObject.put(Constants.XMID, xmid);

        BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
        bdcQlqtzkFjQO.setModeList(modeList);
        bdcQlqtzkFjQO.setJsonStr(JSONObject.toJSONString(jsonObject));
        // 将模板内容直接更新到数据库
        bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
        return mbnr;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 项目
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 根据工作流实例ID查询不动产xm（用于简单流程、批量流程、组合流程）
     */
    @ResponseBody
    @PatchMapping("/clearQlqtzk")
    public Object queryBdcXmByProcessInsId(String processInsId) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                    return bdcXmDO;
                }
            }
            return bdcXmDOList.get(0);
        }
        return null;
    }

    /**
     * @param json         集合
     * @param className    类名字符串
     * @param processInsId
     * @return 批量更新权利信息
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 更新权利
     */
    @ResponseBody
    @PatchMapping("/clearFj")
    public Integer updateQl(@RequestBody String json, @RequestParam(name = "className", required = false) String className, @RequestParam("processInsId") String processInsId, @RequestParam(name = "xmids", required = false) String xmids, @RequestParam(name = "onexmid", required = false) String onexmid, @RequestParam(name = "zxlc", required = false) String zxlc) throws Exception {
        JSONObject obj = JSONObject.parseObject(json);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(xmids)) {
            map.put("xmids", xmids.split(CommonConstantUtils.ZF_YW_DH));
        } else {
            map.put("gzlslid", processInsId);
        }
        if (StringUtils.isBlank(className) && StringUtils.isNotBlank(onexmid)) {
            //注销流程查询原权利，非注销流程查询当前权利
            BdcQl bdcQl = (Boolean.parseBoolean(zxlc)) ? listBdcYql(onexmid) : bdcQllxFeignService.queryQlxx(onexmid);
            if (bdcQl != null) {
                className = bdcQl.getClass().getName();
            }
        }
        //注销流程查询原权利，非注销流程查询当前权利
        if (Boolean.parseBoolean(zxlc)) {
            map.put("sfyql", Boolean.parseBoolean(zxlc));
        }
        bdcDjxxUpdateQO.setWhereMap(map);
        bdcDjxxUpdateQO.setClassName(className);
        return bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
    }

    private BdcQl listBdcYql(String xmid) {
        BdcQl bdcQl = null;
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList) && StringUtils.isNotBlank(bdcXmLsgxDOList.get(0).getYxmid())) {
            bdcQl = bdcQllxFeignService.queryQlxx(bdcXmLsgxDOList.get(0).getYxmid());
        }
        return bdcQl;
    }

    /**
     * @return xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    @GetMapping("/qlxx/fdcq3/qlrxx")
    public String getFdcq3Qlr(@RequestParam("xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("没有参数xmid");
        }
        return bdcFdcq3GyxxFeignService.getFdcq3Qlr(xmid);
    }

    /**
     * 变更抵押批量组合流程 同步土地抵押mj
     *
     * @param slbh
     * @param tddymj
     * @param bdcdyh
     */
    @GetMapping(value = "/qlxx/dyaq/updateDyaqMjByXmid")
    public void updateDyaqMjByXmid(@RequestParam("slbh") String slbh, @RequestParam("tddymj") String tddymj, @RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isNotBlank(slbh) && StringUtils.isNotBlank(bdcdyh)) {
            LOGGER.info("变更抵押批量组合流程 同步土地抵押mj,slbh:{},bdcdyh:{},tddymj:{}", slbh, bdcdyh, tddymj);
            List<BdcQl> listBdcQl = bdcQllxFeignService.listQlxxBySlbh(slbh);
            if (CollectionUtils.isNotEmpty(listBdcQl)) {
                for (BdcQl bdcQl : listBdcQl) {
                    if (bdcQl instanceof BdcDyaqDO && ((BdcDyaqDO) bdcQl).getBdcdyh().equals(bdcdyh)) {
                        LOGGER.info("开始同步抵押权标,bdcdyh:{},tddymj:{}", bdcdyh, tddymj);
                        // 找到这个dyaq 更新tddymj
                        BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                        bdcDyaqDO.setTddymj(Double.parseDouble(tddymj));
                        bdcQllxFeignService.updateDyaq(bdcDyaqDO);
                        break;
                    }
                }
            }
        }
    }

    @GetMapping("/qlxx/sfdya")
    public Object queryQlxxAndSfdya(@RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("查询权利缺失工作流实例id");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcSlQlxxymDTO> bdcSlQlxxymDTOList = new ArrayList<>(bdcXmList.size());
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            for (BdcXmDO bdcXmDO : bdcXmList) {
                BdcSlQlxxymDTO bdcSlQlxxymDTO = new BdcSlQlxxymDTO();
                bdcSlQlxxymDTO.setDydj(this.checkDydj(bdcXmDO));
                bdcSlQlxxymDTO.setBdcXm(bdcXmDO);
                bdcSlQlxxymDTOList.add(bdcSlQlxxymDTO);
            }
        }
        return bdcSlQlxxymDTOList;
    }

    /**
     * 校验当前项目是否为抵押类
     */
    private boolean checkDydj(BdcXmDO bdcXmDO) {
        boolean dydj = false;
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        if (bdcQl == null) {
            bdcQl = bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
        }
        //如果是预告权判断是否预告抵押
        if (bdcQl != null) {
            if (bdcQl instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                    dydj = true;
                }
            }
        }
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
            dydj = true;
        }
        return dydj;
    }

    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目信息
     */
    @GetMapping("/qlxx/xmxx/getGhytByXmid/{xmid}")
    public String getGhytByXmid(@PathVariable("xmid") String xmid) {
        return bdcQllxFeignService.getGhytByXmid(xmid);
    }


    /**
     * @param xmid
     * @param mode
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/8/31 9:08
     */
    @ResponseBody
    @GetMapping("/queryQlqtzkFjMb/nthm")
    public Object queryQlqtzkFjMbNthm(String xmid, String mode, Boolean sfgx) {
        BdcQlqtzkFjDataDTO bdcQlqtzkFjDataDTO = new BdcQlqtzkFjDataDTO();
        // 获取模板类型
        String mbnr = bdcZsInitFeignService.queryQlqtzkFj(xmid, mode);
        List<String> modeList = new ArrayList();
        modeList.add(mode);
        if (Objects.nonNull(sfgx) && sfgx) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.BFQLQTZK, (StringUtils.isNotBlank(mbnr) ? mbnr : ""));
            jsonObject.put(Constants.FJ, (StringUtils.isNotBlank(mbnr) ? mbnr : ""));
            jsonObject.put(Constants.XMID, xmid);
            bdcQlqtzkFjDataDTO.setQlqtzkmbnr(mbnr);
            bdcQlqtzkFjDataDTO.setFjmbnr(mbnr);
            BdcQlqtzkFjQO bdcQlqtzkFjQO = new BdcQlqtzkFjQO();
            bdcQlqtzkFjQO.setModeList(modeList);
            bdcQlqtzkFjQO.setJsonStr(JSONObject.toJSONString(jsonObject));
            // 将模板内容直接更新到数据库
            bdcZsInitFeignService.updateQlqtzkAndFj(bdcQlqtzkFjQO);
        }
        return bdcQlqtzkFjDataDTO;
    }


    /**
     * @param xmid 项目ID
     * @return BdcXmDO 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目信息
     */
    @GetMapping("/qlxx/xmfb/{xmid}")
    public BdcXmFbDO queryXmfb(@PathVariable(name = "xmid") String xmid) {
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(xmid);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            return bdcXmFbDOList.get(0);
        }
        return null;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存项目附表信息
     * @date : 2020/6/11 17:36
     */
    @PatchMapping("/qlxx/xmfb")
    public Integer updatBdcXmfb(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXmFbDO.class.getName());
    }

    @GetMapping("/updateqlqtzkfj")
    public void updateqlqtzkfj(String xmid) {
        queryQlqtzkFjMbNthm(xmid, CommonConstantUtils.XT_QLQTZK_FJ_MODE_QLQTZK, true);
        queryQlqtzkFjMbNthm(xmid, CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ, true);
    }

    /**
     * 根据主键ID查询锁定信息
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/sdxx/{sdxxid}")
    public Object querySdxx(@PathVariable("sdxxid") String sdxxid) {
        if(StringUtils.isBlank(sdxxid)) {
            return null;
        }
        return bdcSdFeignService.queryBdcSdxxById(sdxxid);
    }


    @ResponseBody
    @GetMapping("/getYzjzmj")
    public boolean getYzjzmj() {
        return yzjzmj;
    }


}
