package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.config.SdqConfig;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.DianFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NantongQiFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NantongShuiFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/15
 * @description 水电气
 */
@RestController
@RequestMapping("/slym/sdq")
public class SlymSdqController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlymSdqController.class);

    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;

    @Autowired
    private DianFeignService dianFeignService;

    @Autowired
    private NantongQiFeignService nantongGasFeignService;

    @Autowired
    private NantongShuiFeignService nantongWaterFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    SdqConfig sdqConfig;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据工作流实例ID查询水电气业务
     */
    @GetMapping("/querySdqyw/{gzlslid}")
    @ResponseBody
    public List<BdcSdqghDO> querySdqyw(@PathVariable("gzlslid") String gzlslid) {
        BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
        bdcSdqghQO.setGzlslid(gzlslid);
        return this.queryBdcSdqgh(gzlslid, null);
    }

    /**
     * 查询不动产水电气过户信息
     */
    private List<BdcSdqghDO> queryBdcSdqgh(String gzlslid, Integer ywlx){
        BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
        bdcSdqghQO.setGzlslid(gzlslid);
        if(null != ywlx){
            bdcSdqghQO.setYwlx(ywlx);
        }
        return bdcSdqghFeignService.querySdqyw(bdcSdqghQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新水电气
     */
    @ResponseBody
    @PostMapping("/sdqxx")
    public Integer updateSdqxx(@RequestBody String json) {
        if(StringUtils.isBlank(json)){
            throw new AppException("空参数异常！");
        }
        JSONArray array = JSONArray.parseArray(json);
        int count = 0;
        if(array.size()>0){
            for(int i =0; i< array.size();i++){
                JSONObject obj = array.getJSONObject(i);
                if(obj ==null ||StringUtils.isBlank(obj.getString("gzlslid"))){
                    throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID.");
                }
                List<BdcSdqghDO> bdcSdqghDOList = this.queryBdcSdqgh(obj.getString("gzlslid"), obj.getInteger("ywlx"));
                if(CollectionUtils.isNotEmpty(bdcSdqghDOList)){
                    obj.put("id",bdcSdqghDOList.get(0).getId());
                    count+= bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcSdqghDO.class.getName());
                }else{
                    obj.put("id",UUIDGenerator.generate16());
                    count+= bdcSdqghFeignService.insertSdqghDO(JSONObject.parseObject(JSON.toJSONString(obj),BdcSdqghDO.class));
                }
            }
        }
        return count;
    }


    /**
     * 校验用电过户信息，比对接口返回户主名称与义务人名称是否一致
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return true：比对通过一致， false: 比对失败不一致
     */
    @ResponseBody
    @GetMapping("/hy/ydghxx")
    public Map<String,Object> checkYdghxx(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="ydhh")String ydhh){
        if(StringUtils.isAnyBlank(gzlslid, ydhh)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或用电户号信息");
        }
        Object response = this.dianFeignService.queryDianYhxx(ydhh,userManagerUtils.getCurrentUserName());
        if(Objects.isNull(response)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到电过户基本信息");
        }
        Map<String, String> map = (Map) response;
        String electricFteeName = map.get("electricFeeName");
        if(StringUtils.isBlank(electricFteeName)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到电过户户名信息");
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        boolean hyjg = false;
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, null);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            List<String> ywrmcList = bdcQlrDOList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.toList());
            resultMap.put("ywrmc", StringUtils.join(ywrmcList, CommonConstantUtils.ZF_YW_DH));
            if(ywrmcList.contains(electricFteeName)){
                hyjg= true;
            }
        }
        resultMap.put("hyjg", hyjg);
        return resultMap;
    }

    /**
     * 校验供水过户信息
     * @param gzlslid 工作流实例ID
     * @param consno 户号
     * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @return
     */
    @GetMapping("/check/water/consno")
    public CommonResponse<Integer> checkWater(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="consno")String consno, @RequestParam(name="dwdm")String dwdm){
        if(StringUtils.isAnyBlank(gzlslid, consno, dwdm)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或用水户号信息或供水单位代码");
        }
        return nantongWaterFeignService.sghjc(gzlslid, consno, dwdm);
    }

    /**
     * 校验供气过户信息
     * @param gzlslid 工作流实例ID
     * @param consno 户号
     * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @return  {
     *   sfgh":"",             是否可过户  0:可过户1：不可过户
     *     }
     */
    @GetMapping("/check/gas/consno")
    public CommonResponse<Integer> checkGas(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="consno")String consno){
        if(StringUtils.isAnyBlank(gzlslid, consno)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或用气户号信息");
        }
        return nantongGasFeignService.qghjc(gzlslid, consno);
    }



    /**
     * @param gzlslid 工作流实例id
     * @param ywlx 水电气业务类型
     * @param dwdm 单位代码
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  水电气单位配置项
     */
    @ResponseBody
    @GetMapping("/sdqdwxx/{gzlslid}")
    public Map<String, Object> getSdqDwxx(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "ywlx",required = false) String ywlx,
                                          @RequestParam(name = "dwdm",required = false) String dwdm) {
        Map<String, Object> dwxxs = new HashMap();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)){
            throw new AppException("工作流实例id："+gzlslid+",该不动产项目不存在");
        }
        String qxdm = bdcXmDOS.get(0).getQxdm();
        if (Objects.isNull(sdqConfig.getGqdwxx()) || !sdqConfig.getGqdwxx().containsKey(qxdm)) {
            LOGGER.info("区县代码：{},没有配置供气单位信息" ,qxdm);
            return dwxxs;
        }
        if (Objects.isNull(sdqConfig.getGsdwxx()) || !sdqConfig.getGsdwxx().containsKey(qxdm)) {
            LOGGER.info("区县代码：{},没有配置供水单位信息" ,qxdm);
            return dwxxs;
        }

        //查所有
        if (StringUtils.isBlank(dwdm) && StringUtils.isBlank(ywlx)){
            dwxxs.put("gqdw",sdqConfig.getGqdwxx().get(qxdm));
            dwxxs.put("gsdw",sdqConfig.getGsdwxx().get(qxdm));
        }else if (StringUtils.isNotBlank(dwdm) && StringUtils.isNotBlank(ywlx)){
            // 查区县下的某个单位
            if (BdcSdqEnum.Q.key() == Integer.parseInt(ywlx)) {
                dwxxs.put("gqdw", sdqConfig.getGqdwxx().get(qxdm).get(dwdm));
            }
            if (BdcSdqEnum.S.key() == Integer.parseInt(ywlx)) {
                dwxxs.put("gsdw", sdqConfig.getGsdwxx().get(qxdm).get(dwdm));
            }

        }
        return dwxxs;
    }

}
