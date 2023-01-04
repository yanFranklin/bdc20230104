package cn.gtmap.realestate.exchange.web.rest.lianyungang;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwCDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwHDO;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.FwqsxxRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.component.BdcDmToDsfZdComponent;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttZdjbxxOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.NameFilter;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangyinghao
 */
@OpenController(consumer = "连云港房产推送")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/fcts")
@Api(tags = "省金融平台服务")
public class FctsRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FctsRestController.class);

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    CommonService commonService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    FwqsxxRestService fwqsxxRestService;

    @Autowired
    KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    BdcDmToDsfZdComponent bdcDmToDsfZdComponent;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;


    @Value("${fcts.ywhts.url:}")
    protected String ywhtsUrl;

    @Value("${fcts.head.authorization:}")
    protected String authorization;

    @Value("${fcts.head.token:}")
    protected String token;

    @Value("${fcts.head.username:}")
    protected String username;

    @Value("${fcts.head.password:}")
    protected String password;

    @Value("${fcts.head.regioncode:}")
    protected String regioncode;

    @Value("${fcts.head.orgid:}")
    protected String orgid;

    @Value("${fcts.zxxmdjxl:}")
    protected String zxxmdjxl;


    @Autowired
    protected BdcSlSjclFeignService bdcSlSjclFeignService;

    @OpenApi(apiDescription = "推送数据给房产部门", apiName = "", openLog = false)
    @PostMapping("/djxm/djtz")
    public void fctsDjtz(@RequestParam(name = "processInsId") String processInsId,
                         @RequestParam(name = "currentUserName",required = false) String currentUserName) {
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) || null == bdcXmDOS.get(0)) {
            return;
        }
        //将业务数据推送
        LOGGER.info("开始推送房产部门数据:{}", processInsId);

        try {
            Map<String, Object> head = Maps.newHashMap();
            head.put("Authorization", authorization);

            //
            JSONObject param = new JSONObject();
            JSONObject headMap = new JSONObject();
            List<Map> datalist = new ArrayList<Map>();
            headMap.put("username", username);
            headMap.put("password", password);
            headMap.put("regioncode", regioncode);
            headMap.put("token", token);
            headMap.put("orgid", orgid);
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                JSONObject dataMap = new JSONObject();
                dataMap.put("ywh", bdcXmDO.getXmid());
                datalist.add(dataMap);
            }
            param.put("head", headMap);
            param.put("data", datalist);
            String response = httpClientUtils.sendPostJsonHeaderRequest(ywhtsUrl,
                    JSON.toJSONString(param),
                    "推送房产部门数据",
                    head);
            JSONObject res = JSONObject.parseObject(response);
            if (head != null && res.getBoolean("success")) {
                LOGGER.info("推送房产部门数据成功");
            } else {
                LOGGER.info("推送房产部门数据失败:{}", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("推送房产部门数据失败");
        }
    }

    //去除token限制
    @OpenApi(apiDescription = "房产部门获取登记数据", apiName = "", openLog = false)
    @PostMapping("/djxm/xx")
    public Object djxmxx(@RequestBody JSONObject param) {
        if (Objects.isNull(param)
                || (!param.containsKey("data"))
                || (!param.getJSONObject("data").containsKey("ywh"))
        ) {
            LOGGER.info("");
            throw new AppException("参数错误");
        }
        String xmid = param.getJSONObject("data").getString("ywh");
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(xmid));
        if (CollectionUtils.isEmpty(bdcXmDOS) || null == bdcXmDOS.get(0)) {
            throw new AppException("参数错误");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        JSONObject response = new JSONObject();
        //将业务数据推送
        LOGGER.info("开始返回房产部门详细项目数据:{}", xmid);
        try {
            //
            JSONObject headMap = new JSONObject();
            headMap.put("returncode", "0000");
            headMap.put("msg", "");
            headMap.put("total", "");
            headMap.put("records", "");
            headMap.put("page", "");
            headMap.put("pageSize", "");
            response.put("head", headMap);

            List<Map> datalist = new ArrayList<Map>();
            datalist.add(initYwxx(bdcXmDO));
            response.put("data", datalist);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("推送房产部门数据失败");
            JSONObject headMap = new JSONObject();
            headMap.put("returncode", "0001");
            headMap.put("msg", e.getMessage());
            headMap.put("total", "");
            headMap.put("records", "");
            headMap.put("page", "");
            headMap.put("pageSize", "");
            response.put("head", headMap);
            return response;
        }
    }

    private JSONObject initYwxx(BdcXmDO bdcXmDO) {
        JSONObject fwxxResponse = new JSONObject();
        //根据项目id获取权利
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcXmDO.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        List<BdcQlrDO> bdcYwrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
        BdcZsDO bdcZsDO = null;
        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
            bdcZsDO = bdcZsDOS.get(0);
        }

        BdcYxmQO bdcYxmQO = new BdcYxmQO();
        bdcYxmQO.setXmidList(Arrays.asList(bdcXmDO.getXmid()));
        List<BdcXmDO> ybdcXmDOS = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        BdcXmDO yxm = null;
        if (CollectionUtils.isNotEmpty(ybdcXmDOS)) {
            yxm = ybdcXmDOS.get(0);
        }
        //将字段名称调整为大写
        NameFilter nameFilter = (Object o, String name, Object value) -> name.toUpperCase();
        //gx_zdjbxx
        Map paramMap = new HashMap();
        paramMap.put("bdcdyh", bdcXmDO.getBdcdyh());
        paramMap.put("ywh", bdcXmDO.getXmid());
        List<KttZdjbxxOldDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxListOld(paramMap);
        if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
            KttZdjbxxOldDO kttZdjbxxOldDO = kttZdjbxxList.get(0);
            String bsm = this.bdcdjMapper.getBsm().toString();
            if (bsm.length() > 10) {
                //作为唯一标识码固定为10位
                bsm = bsm.substring(bsm.length() - 10);
            } else {
                bsm = String.format("%010d", Integer.valueOf(bsm));
            }
            kttZdjbxxOldDO.setBsm(Integer.valueOf(bsm));
            if(Objects.isNull(kttZdjbxxOldDO.getCreatetime())){
                kttZdjbxxOldDO.setCreatetime(bdcXmDO.getDjsj());
            }
            if(Objects.isNull(kttZdjbxxOldDO.getUpdatetime())){
                kttZdjbxxOldDO.setUpdatetime(bdcXmDO.getJssj());
            }
            kttZdjbxxOldDO.setQllx(bdcXmDO.getQllx().toString());


            //字典转换
            if (Objects.nonNull(kttZdjbxxOldDO.getQlsdfs())) {
                kttZdjbxxOldDO.setQlsdfs(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(kttZdjbxxOldDO.getQlsdfs()), zdMap.get("qlsdfs")));
            }
            fwxxResponse.put("gx_zdjbxx", JSON.parseObject(JSON.toJSONString(kttZdjbxxOldDO, nameFilter)));
        }


        //gx_zrz
        //bdcXmDO.setBdcdyh("340102201001GB00005F99990001");
        List<Map> mapList = fwqsxxRestService.queryQsxxKttFwZrzList(bdcXmDO.getBdcdyh());
        if (CollectionUtils.isNotEmpty(mapList)) {
            Map map = mapList.get(0);
            JSONObject gx_zrz = new JSONObject();
            gx_zrz.put("BDCDYH", bdcXmDO.getBdcdyh());
            gx_zrz.put("CREATETIME", map.get("DCSJ"));
            gx_zrz.put("GHYT", map.get("GHYT"));
            gx_zrz.put("UPDATETIME", map.get("GXRQ"));
            gx_zrz.put("ZT", map.get("ZT"));
            fwxxResponse.put("gx_zrz", JSON.parseObject(JSON.toJSONString(gx_zrz, nameFilter)));
        }

        //gx_h
        //bdcXmDO.setBdcdyh("340104000000GB00000F31340011");
        boolean sfdz = false;
        if (bdcXmDO != null && CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx())) {
            sfdz = true;
        }
        List<KttFwHDO> kttFwHList = fwqsxxRestService.queryQsxxKttFwHList(bdcXmDO.getBdcdyh(), sfdz);
        if (CollectionUtils.isNotEmpty(kttFwHList)) {
            //没有单元号
            KttFwHDO kttFwHDO = kttFwHList.get(0);
            if(Objects.isNull(kttFwHDO.getCreatetime())){
                kttFwHDO.setCreatetime(bdcXmDO.getDjsj());
            }
            if(Objects.isNull(kttFwHDO.getUpdatetime())){
                kttFwHDO.setUpdatetime(bdcXmDO.getJssj());
            }
            kttFwHDO.setZl(bdcXmDO.getZl());
            fwxxResponse.put("gx_h", JSON.parseObject(JSON.toJSONString(kttFwHDO, nameFilter)));
        }

        //gx_c
        //bdcXmDO.setBdcdyh("340124162011GB00026F00010001");
        List<KttFwCDO> kttFwCList = fwqsxxRestService.queryQsxxKttFwCList(bdcXmDO.getBdcdyh());
        if (CollectionUtils.isNotEmpty(kttFwCList)) {
            KttFwCDO kttFwCDO = kttFwCList.get(0);
            JSONObject cxx = JSON.parseObject(JSON.toJSONString(kttFwCDO));
            cxx.put("UPDATETIME", bdcXmDO.getSlsj());
            fwxxResponse.put("gx_c", JSON.parseObject(JSON.toJSONString(cxx, nameFilter)));
        }

        //gx_qlr
        HashMap<String, String> map = new HashMap();
        map.put("ywh", bdcXmDO.getXmid());
        List<ZttGyQlrDO> zttGyQlrDOS = bdcdjMapper.queryAllZttGyQlrList(map);
        for (ZttGyQlrDO zttGyQlrDO : zttGyQlrDOS) {
            if(Objects.isNull(zttGyQlrDO.getCreatetime())){
                zttGyQlrDO.setCreatetime(bdcXmDO.getDjsj());
            }
            if(Objects.isNull(zttGyQlrDO.getUpdatetime())){
                zttGyQlrDO.setUpdatetime(bdcXmDO.getJssj());
            }
        }
        fwxxResponse.put("gx_qlr", JSON.parseArray(JSON.toJSONString(zttGyQlrDOS, nameFilter)));

        //gx_sj  收件材料
        List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(bdcXmDO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
            JSONArray sjclList = new JSONArray();
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOS) {
                JSONObject sjcl = new JSONObject();
                sjcl.put("CREATETIME", bdcXmDO.getSlsj());
                sjcl.put("SFBCSJ", bdcSlSjclDO.getSfbcsj());
                sjcl.put("SFEWSJ", bdcSlSjclDO.getSfewsj());
                sjcl.put("SFSJSY", bdcSlSjclDO.getSfsjsy());
                sjcl.put("SJLX", bdcSlSjclDO.getSjlx());
                sjcl.put("SJMC", bdcSlSjclDO.getClmc());
                sjcl.put("SJSJ", bdcXmDO.getSlsj());
                sjcl.put("SJSL", bdcSlSjclDO.getFs());
                sjcl.put("UPDATETIME", bdcXmDO.getSlsj());
                sjcl.put("YS", bdcSlSjclDO.getYs());
                sjcl.put("YWH", bdcXmDO.getXmid());
                sjclList.add(sjcl);
            }
            fwxxResponse.put("gx_sj", JSON.parseArray(JSON.toJSONString(sjclList, nameFilter)));
        }

        //gx_ywxx 业务信息
        JSONObject ywxx = new JSONObject();
        ywxx.put("BZ", bdcXmDO.getBz());
        ywxx.put("CREATETIME", bdcXmDO.getDjsj());
        ywxx.put("DJLX", bdcXmDO.getDjlx());
        ywxx.put("JSSJ", bdcXmDO.getJssj());
        ywxx.put("KSSJ", bdcXmDO.getSlsj());
        ywxx.put("QLLX", bdcXmDO.getQllx());
        ywxx.put("SLH", bdcXmDO.getSlbh());
        ywxx.put("SPXTLX", bdcXmDO.getXmly());
        ywxx.put("SPXTYWH", bdcXmDO.getSpxtywh());
        ywxx.put("UPDATETIME", bdcXmDO.getJssj());
        ywxx.put("XMZT", "1");
        ywxx.put("XZQDM", bdcXmDO.getQxdm());
        ywxx.put("YWH", bdcXmDO.getXmid());


        String SQLX = bdcDmToDsfZdComponent.conver("BDC_ZD_SQLX", "FCTS", bdcXmDO.getDjxl().toString()).toString();
        ywxx.put("SQLX", SQLX);
        if (Objects.nonNull(yxm)) {
            ywxx.put("YBDCQZH", yxm.getBdcqzh());
            ywxx.put("YSPXTYWH", yxm.getSpxtywh());
            ywxx.put("YYWH", yxm.getXmid());
        }
        ywxx.put("ZL", bdcXmDO.getZl());
        fwxxResponse.put("gx_ywxx", JSON.parseObject(JSON.toJSONString(ywxx, nameFilter)));
        //登记信息
        if (bdcQl instanceof BdcDyaqDO) {
            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
            JSONObject dyxx = JSON.parseObject(JSON.toJSONString(bdcDyaqDO));
            dyxx.put("BDCDJZMH", bdcXmDO.getBdcqzh());
            dyxx.put("CREATETIME", bdcXmDO.getDjsj());
            dyxx.put("UPDATETIME", bdcXmDO.getJssj());
            dyxx.put("YWH", bdcXmDO.getXmid());

            if (Objects.nonNull(bdcZsDO)) {
                dyxx.put("FJ", bdcZsDO.getFj());
                dyxx.put("QLQTZK", bdcZsDO.getQlqtzk());
                dyxx.put("ZSZT", bdcXmDO.getQszt());
            }
            dyxx.put("QSZT", bdcXmDO.getQszt());
            dyxx.put("QXDM", bdcXmDO.getQxdm());
            if (Objects.nonNull(yxm)) {
                dyxx.put("SCYWH", yxm.getXmid());
            }

            if (CollectionUtils.isNotEmpty(bdcYwrDOS)) {
                String DYR = bdcYwrDOS.stream()
                        .map(BdcQlrDO::getQlrmc)
                        .collect(Collectors.joining(","));
                dyxx.put("DYR", DYR);
            }
            fwxxResponse.put("gx_dyaq", JSON.parseObject(JSON.toJSONString(dyxx, nameFilter)));

        } else if (bdcQl instanceof BdcCfDO) {
            BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
            JSONObject cfxx = JSON.parseObject(JSON.toJSONString(bdcCfDO));
            cfxx.put("CREATETIME", bdcXmDO.getDjsj());
            cfxx.put("UPDATETIME", bdcXmDO.getJssj());
            cfxx.put("YWH", bdcXmDO.getXmid());
            cfxx.put("QSZT", bdcXmDO.getQszt());
            cfxx.put("QXDM", bdcXmDO.getQxdm());
            cfxx.put("CFQSSJ", bdcCfDO.getCfqssj());
            cfxx.put("CFSJ", bdcCfDO.getCfsj());
            cfxx.put("DJSJ", bdcCfDO.getDjsj());
            fwxxResponse.put("gx_cfdj", JSON.parseObject(JSON.toJSONString(cfxx, nameFilter)));

        } else if (bdcQl instanceof BdcFdcqDO) {
            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
            JSONObject fdxx = JSON.parseObject(JSON.toJSONString(bdcFdcqDO));

            //产权来源
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(bdcXmDO.getXmid());
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                String cqly = bdcXmFbDOList.get(0).getCqly();
                if (StringUtils.isNotBlank(cqly)) {
                    //字典转换
                    fdxx.put("CQLY", StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(cqly), zdMap.get("cqly")));
                }
            }

            if (Objects.nonNull(bdcZsDO)) {
                fdxx.put("QLQTZK", bdcZsDO.getQlqtzk());
            }
            fdxx.put("BDCQZH", bdcXmDO.getBdcqzh());
            fdxx.put("ZSZT", bdcXmDO.getQszt());
            fdxx.put("FDCJYJG", bdcFdcqDO.getJyjg());
            fdxx.put("FDZL", bdcFdcqDO.getZl());
            fdxx.put("CREATETIME", bdcXmDO.getDjsj());
            fdxx.put("UPDATETIME", bdcXmDO.getJssj());
            fdxx.put("YWH", bdcXmDO.getXmid());
            fdxx.put("QSZT", bdcXmDO.getQszt());
            fdxx.put("QXDM", bdcXmDO.getQxdm());
            fwxxResponse.put("gx_fdcq2", JSON.parseObject(JSON.toJSONString(fdxx, nameFilter)));

        } else if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            JSONObject ygxx = JSON.parseObject(JSON.toJSONString(bdcYgDO));
            ygxx.put("ZWLXJSQX", bdcYgDO.getZwlxjssj());
            ygxx.put("ZWLXKSQX", bdcYgDO.getZwlxqssj());

            if (Objects.nonNull(bdcZsDO)) {
                ygxx.put("BDCDJZMH", bdcZsDO.getBdcqzh());
                ygxx.put("FJ", bdcZsDO.getFj());
                ygxx.put("QLQTZK", bdcZsDO.getQlqtzk());
                ygxx.put("ZSZT", bdcXmDO.getQszt());
            }

            if (CollectionUtils.isNotEmpty(bdcYwrDOS)) {
                String YWR = bdcYwrDOS.stream()
                        .map(BdcQlrDO::getQlrmc)
                        .collect(Collectors.joining(","));
                ygxx.put("YWR", YWR);
            }

            ygxx.put("BDCZL", bdcXmDO.getZl());
            ygxx.put("JYHTH", bdcXmDO.getJyhth());
            ygxx.put("CREATETIME", bdcXmDO.getDjsj());
            ygxx.put("UPDATETIME", bdcXmDO.getJssj());
            ygxx.put("YWH", bdcXmDO.getXmid());
            ygxx.put("QSZT", bdcXmDO.getQszt());
            ygxx.put("QXDM", bdcXmDO.getQxdm());
            fwxxResponse.put("gx_ygdj", JSON.parseObject(JSON.toJSONString(ygxx, nameFilter)));


        } else if (StringUtils.isNotBlank(zxxmdjxl) && zxxmdjxl.contains(bdcXmDO.getDjxl())) {
            JSONObject zxxx = new JSONObject();
            if (Objects.nonNull(yxm)) {
                zxxx.put("BDCQZH", yxm.getBdcqzh());
                zxxx.put("ZXYWH", yxm.getXmid());
            }
            zxxx.put("BDCDYH", bdcXmDO.getBdcdyh());
            zxxx.put("DBR", "无");
            zxxx.put("DJJG", bdcXmDO.getDjjg());
            zxxx.put("DJSJ", bdcXmDO.getDjsj());
            zxxx.put("QXDM", bdcXmDO.getQxdm());
            zxxx.put("YSDM", "无");
            zxxx.put("ZXSJ", bdcXmDO.getJssj());
            zxxx.put("CREATETIME", bdcXmDO.getDjsj());
            zxxx.put("UPDATETIME", bdcXmDO.getJssj());
            zxxx.put("YWH", bdcXmDO.getXmid());
            //获取原权利
            fwxxResponse.put("gx_zxdj", JSON.parseObject(JSON.toJSONString(zxxx, nameFilter)));
        }
        return fwxxResponse;
    }


    @OpenApi(apiDescription = "房产部门获取登记数据回调", apiName = "", openLog = false)
    @PostMapping("/djxm/jszt")
    public void djxmxxjszt(@RequestBody JSONObject param) {
        LOGGER.info("房产部门获取登记数据回调{}", JSON.toJSONString(param));
        if (Objects.isNull(param)
                || (!param.containsKey("data"))
        ) {
            LOGGER.info("");
            throw new AppException("参数错误");
        }
        String xmid = param.getJSONObject("data").getString("ywh");
        String jszt = param.getJSONObject("data").getString("jszt");
        //Date jssj = param.getJSONObject("data").getDate("jssj");
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(xmid));
        if (CollectionUtils.isEmpty(bdcXmDOS) || null == bdcXmDOS.get(0)) {
            throw new AppException("参数错误");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        //添加操作日志
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        BeanUtils.copyProperties(bdcXmDO, bdcCzrzDO);
        bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzzt(Integer.valueOf(jszt));
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_FCTS.key());
        bdcCzrzDO.setCzyy("房产部门获取登记数据回调");
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }
}
