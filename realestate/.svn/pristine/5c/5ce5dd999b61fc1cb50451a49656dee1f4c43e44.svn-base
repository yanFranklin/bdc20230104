package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-13
 * @description 与购物车相关接口
 */
@Controller
@RequestMapping("/lpb/cart")
public class LpbCartController extends BaseController{

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用户信息工具
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    private BdcGzyzFeignService bdcGzyzFeignService;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LpbCartController.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramJson
     * @param fwHsDOListJson
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description 添加购物车之前的规则验证
     */
    @RequestMapping("/yzgz")
    @ResponseBody
    public List<Map<String,Object>> yzgz(@NotBlank(message = "购物车参数不能为空") String paramJson,
                                         String fwHsDOListJson,
                                         String fwDcbIndex,
                                         @NotBlank(message = "户室类型不能为空") String hslx,String qjgldm){

        if(StringUtils.isBlank(fwHsDOListJson) && StringUtils.isBlank(fwDcbIndex)){
            throw new IllegalArgumentException("户室主键不能为空");
        }
        BdcCshSlxmDTO dto;
        try {
            dto = JSONObject.parseObject(paramJson,BdcCshSlxmDTO.class);
        }catch (Exception e){
            throw new IllegalArgumentException("购物车传递参数错误");
        }
        if(dto != null && StringUtils.isNotBlank(dto.getGzldyid())){
            // 在 revertDTO 中获取BDCDYH List
            List<Map<String,Object>> gzyzParamList = new ArrayList<>();

            // 户室列表不为空时
            if(StringUtils.isNotBlank(fwHsDOListJson)){
                setGzyzParamListByFwHsDOListJson(fwHsDOListJson,gzyzParamList,qjgldm);
            }else if(StringUtils.isNotBlank(fwDcbIndex)){
                // 逻辑幢主键不为空时
                setGzyzParamListByFwDcbIndex(fwDcbIndex,hslx,gzyzParamList,qjgldm);
            }
            if(CollectionUtils.isNotEmpty(gzyzParamList)){
                List<Map<String,Object>> yzResult = yzBdcgz(dto.getGzldyid(),gzyzParamList);
                if(CollectionUtils.isNotEmpty(yzResult)){
                    return yzResult;
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDOListJson
     * @param gzyzParamList
     * @return void
     * @description 根据户室列表JSON数据 构造规则验证数据参数结构
     */
    private void setGzyzParamListByFwHsDOListJson(String fwHsDOListJson,List<Map<String,Object>> gzyzParamList,String qjgldm){
        JSONArray jsonArray = JSONObject.parseArray(fwHsDOListJson);
        if(CollectionUtils.isNotEmpty(jsonArray)){
            for(int i = 0 ;i < jsonArray.size() ; i++){
                Map<String,Object> paramMap = new HashMap<>();
                // BDCDYH
                String bdcdyhJson = jsonArray.getJSONObject(i).getString("bdcdyh");
                if(StringUtils.isNotBlank(bdcdyhJson)){
                    Map map = JSONObject.parseObject(bdcdyhJson, Map.class);
                    String bdcdyh = MapUtils.getString(map,"value");
                    paramMap.put("bdcdyh",bdcdyh);
                    paramMap.put("qjgldm",qjgldm);
                }
                if(StringUtils.isNotBlank(MapUtils.getString(paramMap,"bdcdyh"))){
                    gzyzParamList.add(paramMap);
                }
            }
        }
    }

    /**
     * 根据FW_DCB_INDEX 查询户室列表  构造规则验证参数List
     * @param fwDcbIndex
     * @param hslx
     * @param gzyzParamList
     */
    private void setGzyzParamListByFwDcbIndex(String fwDcbIndex,String hslx,List<Map<String,Object>> gzyzParamList,String qjgldm){
        if(StringUtils.equals("hs",hslx)){
            // 户室类型
            List<FwHsDO> fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(fwDcbIndex,qjgldm);
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    if(StringUtils.isNotBlank(fwHsDO.getBdcdyh())){
                        Map<String,Object> paramMap = new HashMap<>();
                        paramMap.put("bdcdyh",fwHsDO.getBdcdyh());
                        paramMap.put("qjgldm",qjgldm);
                        gzyzParamList.add(paramMap);
                    }
                }
            }
        }else if(StringUtils.equals("ychs",hslx)){
            // 预测户室类型
            List<FwYchsDO> fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(fwDcbIndex,qjgldm);
            if(CollectionUtils.isNotEmpty(fwYchsDOList)){
                for(FwYchsDO fwYchsDO : fwYchsDOList){
                    if(StringUtils.isNotBlank(fwYchsDO.getBdcdyh())){
                        Map<String,Object> paramMap = new HashMap<>();
                        paramMap.put("bdcdyh",fwYchsDO.getBdcdyh());
                        paramMap.put("qjgldm",qjgldm);
                        gzyzParamList.add(paramMap);
                    }
                }
            }
        }
    }

    /**
     * 添加购物车
     * @param paramJson
     * @param fwHsDOListJson
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     */
    @RequestMapping("/add")
    @ResponseBody
    public List<Map<String, Object>> addCart(@NotBlank(message = "购物车参数不能为空") String paramJson,
                                             @NotBlank(message = "添加户室不能为空") String fwHsDOListJson,
                                             @NotBlank(message = "户室类型不能为空") String hslx,
                                             boolean noGzyz, String tdzWlxmJson, String zlcsh) {

        BdcCshSlxmDTO dto;
        try {
            dto = JSONObject.parseObject(paramJson, BdcCshSlxmDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("购物车传递参数错误");
        }
        BdcSlXmLsgxDO bdcSlXmLsgxDO = null;
        if (StringUtils.isNotBlank(tdzWlxmJson)) {
            bdcSlXmLsgxDO = JSONObject.parseObject(tdzWlxmJson, BdcSlXmLsgxDO.class);
        }
        // 在 revertDTO 中获取BDCDYH List
        List<BdcSlYwxxDTO> addList = revertDTO(fwHsDOListJson, hslx, bdcSlXmLsgxDO, zlcsh);
        // 请求规则验证的相应结果
        List<Map<String,Object>> yzResult = new ArrayList<>();
        if(dto != null  && CollectionUtils.isNotEmpty(addList)){
            if(!noGzyz){
                // 获取规则验证的参数列表
                List<Map<String,Object>> gzyzParamList = new ArrayList<>();
                for(BdcSlYwxxDTO slYwxxDTO : addList){
                    Map<String,Object> paramMap = new HashMap<>();
                    paramMap.put("bdcdyh",slYwxxDTO.getBdcdyh());
                    paramMap.put("qjgldm",slYwxxDTO.getQjgldm());
                    gzyzParamList.add(paramMap);
                }

                // 请求规则验证
                yzResult = yzBdcgz(dto.getGzldyid(),gzyzParamList);
                if(CollectionUtils.isNotEmpty(yzResult)){
                    // 如果验证的结果不为空 将没通过的BDCDYH 过滤掉
                    addList = filterAddCartBdcdyh(addList,yzResult);
                }
            }

            // 通过验证的BDCDYH 如果不为空  请求添加购物车
            if(CollectionUtils.isNotEmpty(addList)){
                dto.setBdcSlYwxxDTOList(addList);
                UserDto userDto = userManagerUtils.getCurrentUser();
                if(userDto != null){
                    LOGGER.info("添加购物车begin");
                    bdcSlXmFeignService.cshYxxm(userDto.getId(),dto);
                    LOGGER.info("添加购物车end");
                }
            }
            return yzResult;
        }
        return Collections.emptyList();
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param addList
     * @param yzResultList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO>
     * @description 从所有要添加购物车的列表中 过滤掉没通过验证的BDCDYH
     */
    private List<BdcSlYwxxDTO> filterAddCartBdcdyh(List<BdcSlYwxxDTO> addList
            ,List<Map<String,Object>> yzResultList){
        List<BdcSlYwxxDTO> newList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(yzResultList)){
            Map<String,BdcSlYwxxDTO> bdcdyhToObjectMap = new HashMap<>();
            // 把所有要添加的业务信息实体列表 处理为key为BDCDYH,value为实体的Map
            for(BdcSlYwxxDTO ywxxDTO : addList){
                bdcdyhToObjectMap.put(ywxxDTO.getBdcdyh(),ywxxDTO);
            }

            // 循环验证不用过的结果 获取QJID
            for(Map<String,Object> yzResult : yzResultList){
                String bdcdyh = MapUtils.getString(yzResult,"bdcdyh");
                BdcSlYwxxDTO dto = bdcdyhToObjectMap.get(bdcdyh);
                if(dto != null){
                    yzResult.put("fwHsIndex",dto.getQjid());
                }
            }

            // 将验证不通过的BDCDYH 在MAP中 setNULL
            for(Map<String,Object> yzResult : yzResultList){
                String bdcdyh = MapUtils.getString(yzResult,"bdcdyh");
                BdcSlYwxxDTO dto = bdcdyhToObjectMap.get(bdcdyh);
                if(dto != null){
                    bdcdyhToObjectMap.put(bdcdyh,null);
                }
            }

            // 重新整理 没有被setNull 的 BDCDYH 对应的户室信息List
            Iterator<Map.Entry<String,BdcSlYwxxDTO>> ite = bdcdyhToObjectMap.entrySet().iterator();
            while (ite.hasNext()){
                Map.Entry<String,BdcSlYwxxDTO> entry = ite.next();
                if(entry.getValue() != null){
                    newList.add(entry.getValue());
                }
            }
            return newList;
        }else{
            return addList;
        }
    }

    /**
     * 全选添加购物车
     *
     * @param paramJson
     * @param fwDcbIndex
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     */
    @RequestMapping("/alladd")
    @ResponseBody
    public List<Map<String, Object>> allAddCart(@NotBlank(message = "购物车参数不能为空") String paramJson,
                                                @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,
                                                @NotBlank(message = "户室类型不能为空") String hslx, String qjgldm, String zlcsh) {
        BdcCshSlxmDTO dto;
        try {
            dto = JSONObject.parseObject(paramJson, BdcCshSlxmDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("购物车传递参数错误");
        }
        if (dto != null) {
            List<BdcSlYwxxDTO> addList = revertDTOByFwDcbIndex(fwDcbIndex, hslx, qjgldm, zlcsh);
            // 请求规则验证的相应结果
            List<Map<String, Object>> yzResult;
            if(dto != null  && CollectionUtils.isNotEmpty(addList)){
                // 获取规则验证的参数列表
                List<Map<String,Object>> gzyzParamList = new ArrayList<>();
                for(BdcSlYwxxDTO slYwxxDTO : addList){
                    Map<String,Object> paramMap = new HashMap<>();
                    paramMap.put("bdcdyh",slYwxxDTO.getBdcdyh());
                    paramMap.put("qjgldm",qjgldm);
                    gzyzParamList.add(paramMap);
                }

                // 请求规则验证
                yzResult = yzBdcgz(dto.getGzldyid(),gzyzParamList);
                if(CollectionUtils.isNotEmpty(yzResult)){
                    // 如果验证的结果不为空 将没通过的BDCDYH 过滤掉
                    addList = filterAddCartBdcdyh(addList,yzResult);
                    dto.setBdcSlYwxxDTOList(addList);
                } else {
                    // 全部通过规则验证
                    addList = new ArrayList<>();
                    BdcSlYwxxDTO ywxxDTO = new BdcSlYwxxDTO();
                    ywxxDTO.setDyhcxlx(Constants.DY_CX_LX);
                    ywxxDTO.setFwDcbIndex(fwDcbIndex);
                    ywxxDTO.setQjgldm(qjgldm);
                    ywxxDTO.setLx(hslx);
                    addList.add(ywxxDTO);
                    dto.setBdcSlYwxxDTOList(addList);
                }
                UserDto userDto = userManagerUtils.getCurrentUser();
                if(userDto != null){
                    LOGGER.info("添加购物车begin");
                    LOGGER.info("添加购物车数据{}", JSONObject.toJSONString(dto));
                    bdcSlXmFeignService.cshYxxm(userDto.getId(), dto);
                    LOGGER.info("添加购物车end");
                }
                return yzResult;
            }
        }
        return Collections.emptyList();
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramJson
     * @return java.util.List<java.lang.String>
     * @description
     */
    @ResponseBody
    @RequestMapping("/lisths")
    public List<String> listFwhsInCart(String paramJson){
        if(StringUtils.isNotBlank(paramJson)) {
            LOGGER.info("展示房屋户室查询参数{}",paramJson);
            BdcCshSlxmDTO dto;
            List<String> hsIndexList = new ArrayList<>();
            try {
                dto = JSONObject.parseObject(paramJson,BdcCshSlxmDTO.class);
                if(dto != null && StringUtils.isNotBlank(dto.getJbxxid())){
                    List<BdcSlYwxxDTO> ywxxList = bdcSlXmFeignService.listBdcSlYwxxByJbxxid(dto.getJbxxid());
                    if(CollectionUtils.isNotEmpty(ywxxList)){
                        for(BdcSlYwxxDTO ywxx : ywxxList){
                            if(ywxx != null && StringUtils.isNotBlank(ywxx.getQjid())){
                                hsIndexList.add(ywxx.getQjid());
                            }
                        }
                    }
                }else{
                    throw new IllegalArgumentException("购物车传递参数错误");
                }
            }catch (Exception e){
                LOGGER.error("初始化已选择购物车异常",e);
            }
            return hsIndexList;
        }
        return Collections.emptyList();
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramJson
     * @param fwHsIndexs
     * @return void
     * @description 移除购物车
     */
    @RequestMapping("/remove")
    @ResponseBody
    public void removeCart(@NotBlank(message = "购物车参数不能为空") String paramJson,
                           @NotBlank(message = "移除户室不能为空") String fwHsIndexs){
        BdcCshSlxmDTO dto;
        try {
            dto = JSONObject.parseObject(paramJson,BdcCshSlxmDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("购物车传递参数错误");
        }

        if(dto != null && StringUtils.isNotBlank(dto.getJbxxid())){
            bdcSlXmFeignService.deleteYxFwhs(fwHsIndexs,dto.getJbxxid());
        }else{
            throw new IllegalArgumentException("购物车基本信息ID为空");
        }
    }

    /**
     * @param fwDcbIndex
     * @param hslx
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWDCBINDEX 获取 参数
     */
    private List<BdcSlYwxxDTO> revertDTOByFwDcbIndex(String fwDcbIndex, String hslx, String qjgldm, String zlcsh) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (StringUtils.equals("hs", hslx)) {
            fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(fwDcbIndex, qjgldm);
        } else {
            List<FwYchsDO> fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(fwDcbIndex, qjgldm);
            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                for (FwYchsDO ychsDO : fwYchsDOList) {
                    FwHsDO fwHsDO = new FwHsDO();
                    BeanUtils.copyProperties(ychsDO, fwHsDO);
                    fwHsDOList.add(fwHsDO);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(fwHsDOList)){
            return revertDTO(fwHsDOList, hslx, qjgldm,zlcsh);
        }
        return Collections.emptyList();
    }

    private List<BdcSlYwxxDTO> revertDTO(List<FwHsDO> fwHsDOList, String hslx, String qjgldm, String zlcsh) {
        List<BdcSlYwxxDTO> addList = new ArrayList<>(fwHsDOList.size());
        for (FwHsDO fwhs : fwHsDOList) {
            if (StringUtils.isNotBlank(fwhs.getBdcdyh())) {
                BdcSlYwxxDTO ywxxDTO = new BdcSlYwxxDTO();
                ywxxDTO.setQjid(fwhs.getFwHsIndex());
                ywxxDTO.setZl(fwhs.getZl());
                ywxxDTO.setBdcdyh(fwhs.getBdcdyh());
                ywxxDTO.setLx(hslx);
                ywxxDTO.setDyhcxlx(Constants.DY_CX_LX);
                ywxxDTO.setYt(fwhs.getGhyt());
                ywxxDTO.setQjgldm(qjgldm);
                if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, zlcsh)) {
                    ywxxDTO.setSfzlcsh(CommonConstantUtils.SF_S_DM);
                }
                addList.add(ywxxDTO);
            }
        }
        return addList;
    }

    /**
     * 构造受理添加购物车服务DTO
     *
     * @param fwHsDOListJson
     * @param hslx
     * @param bdcSlXmLsgxDO  土地证外联项目
     * @return
     */
    private List<BdcSlYwxxDTO> revertDTO(String fwHsDOListJson, String hslx, BdcSlXmLsgxDO bdcSlXmLsgxDO, String zlcsh) {
        JSONArray jsonArray = JSONObject.parseArray(fwHsDOListJson);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            List<BdcSlYwxxDTO> addList = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                BdcSlYwxxDTO ywxxDTO = new BdcSlYwxxDTO();

                // 户室主键
                String fwHsIndexJson = jsonArray.getJSONObject(i).getString("fwHsIndex");
                if (StringUtils.isNotBlank(fwHsIndexJson)) {
                    ywxxDTO.setQjid(fwHsIndexJson);
                }

                // 坐落
                String zlJson = jsonArray.getJSONObject(i).getString("zl");
                if(StringUtils.isNotBlank(zlJson)){
                    ywxxDTO.setZl(zlJson);
                }

                // BDCDYH
                String bdcdyhJson = jsonArray.getJSONObject(i).getString("bdcdyh");
                if(StringUtils.isNotBlank(bdcdyhJson)){
                    ywxxDTO.setBdcdyh(bdcdyhJson);
                }
                // yt
                String ghytJson = jsonArray.getJSONObject(i).getString("ghyt");
                if(StringUtils.isNotBlank(ghytJson)){
                    ywxxDTO.setYt(ghytJson);
                }
                // qlr
                String qlrJson = jsonArray.getJSONObject(i).getString("qlr");
                if (StringUtils.isNotBlank(qlrJson)) {
                    ywxxDTO.setQlr(qlrJson);
                }
                if (StringUtils.isNotBlank(ywxxDTO.getBdcdyh())) {
                    ywxxDTO.setLx(hslx);
                    ywxxDTO.setDyhcxlx(Constants.DY_CX_LX);
                }
                // qjgldm
                String qjgldmJson = jsonArray.getJSONObject(i).getString("qjgldm");
                if (StringUtils.isNotBlank(qjgldmJson)) {
                    ywxxDTO.setQjgldm(qjgldmJson);
                }

                if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, zlcsh)) {
                    ywxxDTO.setSfzlcsh(CommonConstantUtils.SF_S_DM);
                }

                // 外联土地证
                if (CheckParameter.checkAnyParameter(bdcSlXmLsgxDO, "yxmid")) {
                    ywxxDTO.setBdcWlSlXmLsgxDOList(new ArrayList<>());
                    ywxxDTO.getBdcWlSlXmLsgxDOList().add(bdcSlXmLsgxDO);
                }
                addList.add(ywxxDTO);
            }
            return addList;
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param processDefKey
     * @param gzYzsjDTOList
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description
     */
    private List<Map<String, Object>> yzBdcgz(String processDefKey,List<Map<String,Object>> gzYzsjDTOList){
        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs(processDefKey+"_XZBDCDY");
        bdcGzYzQO.setParamList(gzYzsjDTOList);
        LOGGER.info("规则验证begin");
        List<Map<String, Object>> resultList = bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        Map<String,Object> returnMap = new HashMap<>();
//        returnMap.put("msg", "GLTDZ"+"关联土地证");
//        returnMap.put("xzxx",getXzxx());
//        returnMap.put("bdcdyh","230522100015GB00217F00020190");
//        returnMap.put("yzlx", "confirm");
//        resultList.add(returnMap);
//
//        Map<String,Object> returnMap2 = new HashMap<>();
//        returnMap2.put("msg", "可忽略提示");
//        returnMap2.put("bdcdyh","230522100015GB00217F00020189");
//        returnMap2.put("yzlx", "confirm");
//        resultList.add(returnMap2);

        LOGGER.info("规则验证end");
        return resultList;
    }

    private List<Map<String,Object>> getXzxx(){
        List<Map<String,Object>> xzxx = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("XMID","123");
        xzxx.add(map);
        return xzxx;
    }

}
