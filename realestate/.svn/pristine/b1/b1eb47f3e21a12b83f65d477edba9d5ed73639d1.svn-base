package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;


import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcGgFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzqzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.vo.inquiryui.BdcGgVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 不动产公告信息
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version:
 * @date 2021/07/20 17:06
 * @Description:
 */
@RestController
@RequestMapping(value = "/rest/v1.0/ggxx")
public class BdcGgController extends BaseController {

    //    文件管理器权限设置
    private static final String wjglqqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";

    @Autowired
    private BdcGgFeignService bdcGgFeignService;


    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    @Autowired
    ECertificateFeignService eCertificateFeignService;

    @Autowired
    BdcDzqzFeignService bdcDzqzFeignService;

    @Autowired
    BdcBhFeignService bdcBhFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    /**
     * 获取公告信息
     * @param ggid 公告ID
     * @return 公告页面DTO
     */
    @GetMapping("/{ggid}")
    public BdcGgDTO queryBdcGgxx(@PathVariable(value = "ggid") String ggid){
        if(StringUtils.isBlank(ggid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少公告ID参数");
        }
        BdcGgDO bdcGgDO = this.bdcGgFeignService.queryBdcGgByGgid(ggid);
        if(Objects.isNull(bdcGgDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到公告信息");
        }
        BdcGgDTO bdcGgDTO = new BdcGgDTO();
        BeanUtils.copyProperties(bdcGgDO, bdcGgDTO);
        // 获取公告业务数据
        List<BdcGgywsjDO> bdcGgywsjDOS = bdcGgFeignService.queryBdcGgywsjByGgid(ggid);
        if(CollectionUtils.isNotEmpty(bdcGgywsjDOS)){
            bdcGgDTO.setZl(bdcGgywsjDOS.stream().map(BdcGgywsjDO::getZl).collect(Collectors.joining(",")));
        }
        return bdcGgDTO;
    }

    @PostMapping("/save")
    public BdcGgDO saveGgxx(@RequestBody BdcGgDO bdcGgDO){
        return bdcGgFeignService.inserBdcGg(bdcGgDO);
    }

    @PostMapping("/saveGgywsj")
    public Object saveGgywsj(@RequestBody List<BdcGgywsjDO> bdcGgywsjDOList){
        bdcGgywsjDOList.toString();
        return null;
    }

    @PostMapping("/saveGggx")
    public int saveGggx(@RequestBody List<String> xmidList,@RequestParam(name = "ggid") String ggid){
        return bdcGgFeignService.insertBdcGggx(xmidList,ggid);
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @param bdcGgDTO  公告DTO
     * @description  保存公告业务数据
     */
    @PostMapping(value = "/saveYwsj")
    public Object saveYwsj(@RequestBody BdcGgDTO bdcGgDTO){
        return bdcGgFeignService.insertBdcGgYwsj(bdcGgDTO,true);
    }

    @PostMapping("/updateggzt")
    public int batchUpdatebdcggzt(@RequestBody List<String> ggids, @RequestParam(name = "ggzt") String ggzt){
        return bdcGgFeignService.batchUpdatebdcggzt(ggids,ggzt);
    }

    @GetMapping("/sjcl/{gzlslid}")
    public Object bdcggsjcl(@PathVariable(value = "gzlslid") String processInsId){
        return bdcSlSjclFeignService.listBdcSlSjclByGzlslid(processInsId);
    }


    /**
     * @param sfhqqx       是否获取权限
     * @return 文件上传参数
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 组织文件上传参数
     */
    @ResponseBody
    @GetMapping("/bdcSlWjscDTO")
    public Object bdcSlWjscDTO(Boolean sfhqqx) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if (sfhqqx != null && sfhqqx) {
            bdcSlWjscDTO.setsFrmOption(wjglqqx);
        }
        return bdcSlWjscDTO;

    }

    /**
     * @param  ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州公告类型对应不同的公告页面
     * @date : 2022/3/1 9:34
     */
    @ResponseBody
    @GetMapping("/czgg")
    public Object queryGgxx(String ggid) {
        BdcGgDTO bdcGgDTO = new BdcGgDTO();
        BdcGgDO bdcGgDO = new BdcGgDO();
        bdcGgDTO.setBdcGgVO(new BdcGgVO());
        bdcGgDTO.setBdcXmDOList(new ArrayList<>(1));
        if (StringUtils.isNotBlank(ggid)) {
            bdcGgDO = bdcGgFeignService.queryBdcGgByGgid(ggid);
        }
        if (StringUtils.isNotBlank(bdcGgDO.getGgid())) {
            BeanUtils.copyProperties(bdcGgDO, bdcGgDTO);
            if (StringUtils.isNotBlank(bdcGgDO.getGgmbnr())) {
                try {
                    JSONObject ggnrObj = JSON.parseObject(bdcGgDTO.getGgmbnr());
                    bdcGgDTO.setBdcGgVO(JSON.toJavaObject(ggnrObj, BdcGgVO.class));
                } catch (Exception e) {
                    LOGGER.error("当前公告内容非json串，无法转换");
                }
            }

            //关联的项目信息
            List<BdcGgywsjDO> bdcGgywsjDOList =new ArrayList<BdcGgywsjDO>();
            //查询公告id关联的项目id
            List<BdcGggxDO> bdcGggxDOList = bdcGgFeignService.queryBdcGggxByGgid(ggid);
            if (CollectionUtils.isNotEmpty(bdcGggxDOList)) {
                for (BdcGggxDO bdcGggxDO : bdcGggxDOList) {
                    if (StringUtil.isNotBlank(bdcGggxDO.getYwsjid())) {
                        List<BdcGgywsjDO> bdcGgywsjList = bdcGgFeignService.queryBdcGgywsjByYwsjid(bdcGggxDO.getYwsjid());
                        if (CollectionUtils.isNotEmpty(bdcGgywsjList)) {
                            bdcGgywsjDOList.addAll(bdcGgywsjList);
                        }
                    }
                }
            }
            //查询项目信息

            if (CollectionUtils.isNotEmpty(bdcGgywsjDOList)) {
                //单元号去重
                bdcGgywsjDOList = bdcGgywsjDOList.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getBdcdyh())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcGgywsjDO::getBdcdyh))), ArrayList::new));
                bdcGgDTO.setBdcGgywsjDOList(bdcGgywsjDOList);
                bdcGgDTO.setZl(bdcGgywsjDOList.get(0).getZl());
                if (CollectionUtils.size(bdcGgywsjDOList) > 1) {
                    //根据单元号去重后仍旧数量大于1 再根据坐落去重，去重后大于1 加“等”
                    bdcGgywsjDOList = bdcGgywsjDOList.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getZl())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcGgywsjDO::getZl))), ArrayList::new));
                    if (CollectionUtils.size(bdcGgywsjDOList) > 1) {
                        bdcGgDTO.setZl(bdcGgywsjDOList.get(0).getZl() + CommonConstantUtils.SUFFIX_PL);
                    }
                }
                bdcGgDTO.setQlr(bdcGgywsjDOList.get(0).getQlr());
                bdcGgDTO.setYwr(StringToolUtils.resolveBeanToAppendStr(bdcGgywsjDOList, "getYwr", CommonConstantUtils.ZF_YW_DH));
                bdcGgDTO.setYcqzh(StringToolUtils.resolveBeanToAppendStr(bdcGgywsjDOList, "getYcqzh", CommonConstantUtils.ZF_YW_DH));
            }
        }
        return bdcGgDTO;
    }

    @PutMapping("")
    public Object updateGgxx(@RequestBody BdcGgDTO bdcGgDTO) {
        return  bdcGgFeignService.insertBdcGgYwsj(bdcGgDTO, false);
    }

    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 公告关联业务
     * @date : 2022/3/3 9:36
     */
    @ResponseBody
    @GetMapping("/linkProcess")
    public Object linkProcess(String slbh, String ggid, Integer gglx, String cqzh, String qlr) {
        if (StringUtils.isBlank(slbh) && StringUtils.isBlank(cqzh) && StringUtils.isBlank(qlr)) {
            throw new AppException("请输入受理编号关联");
        }
        //查询项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        bdcXmQO.setBdcqzh(cqzh);
        bdcXmQO.setQlr(qlr);

        List<BdcXmAndFbDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
        List<String> xmidList= new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for(BdcXmDTO bdcXmDTO : bdcXmDTOList){
                if(StringUtil.isNotBlank(bdcXmDTO.getXmid())){
                    xmidList.add(bdcXmDTO.getXmid());
                }
            }
        } else {
            //如果用受理编号未查询到流程信息，抛出异常
            throw new AppException("当前受理编号:" + slbh + "受理编号:" + slbh + "未获取到项目信息");
        }
        BdcGgDO bdcGgDO = new BdcGgDO();
        // 用来存储没有关联公告的xmid
        List<String> glxmidList = new ArrayList<>();
        for(String xmid : xmidList){
            //用工作流实例id查询当前流程是否已经有了公告了
            List<BdcGggxDO> bdcGgDOList = bdcGgFeignService.listBdcGggxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcGgDOList)) {
                LOGGER.info("流程项目id" + xmid + "已经生成了公告，无法再次关联");
            }else{
                glxmidList.add(xmid);
            }
        }
        if (StringUtils.isNotBlank(ggid)) {
            bdcGgDO = bdcGgFeignService.queryBdcGgByGgid(ggid);
        }
        if(CollectionUtils.isEmpty(glxmidList)){
            throw new AppException("当前流程受已经生成了公告，无法再次关联");
        }
        if (Objects.nonNull(bdcGgDO)) {
            bdcGgDO.setGzlslid("1");
            bdcGgDO.setGglx(gglx);
            bdcGgDO = saveGgxx(bdcGgDO);
            //保存到历史关系表
            saveGggx(glxmidList,bdcGgDO.getGgid());

        }
        return bdcGgDO;

    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 初始化公告信息
     * @date : 2022/3/3 9:36
     */
    @ResponseBody
    @GetMapping("/initGgxx")
    public Object initGgxx(Integer gglx) {
        if (gglx == null) {
            throw new AppException("公告类型不能为空");
        }
        BdcGgDO bdcGgDO=new BdcGgDO();
        bdcGgDO.setGglx(gglx);
        BdcGgDO res=saveGgxx(bdcGgDO);
        return res;
    }

    /**
     * @param slbh,cqzh,qlr
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 公告关联业务
     * @date : 2022/4/29 9:36
     */
    @ResponseBody
    @GetMapping("/listLinkProcess")
    public Object linkProcess(@LayuiPageable Pageable pageable, String slbh, String cqzh, String qlr) {
        if (StringUtils.isBlank(slbh) && StringUtils.isBlank(cqzh) && StringUtils.isBlank(qlr)) {
            throw new AppException("请输入受理编号关联");
        }
        //查询项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        bdcXmQO.setBdcqzh(cqzh);
        bdcXmQO.setQlr(qlr);
        List<BdcXmAndFbDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
        return super.addLayUiCode(PageUtils.listToPage(bdcXmDTOList, pageable));
    }

    /**
     * @param ggid 公告ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 签章推送
     * @date : 2022/3/15 14:51
     */
    @ResponseBody
    @GetMapping("/tsqz")
    public void tsggqz(String ggid) {
        if (StringUtils.isNotBlank(ggid)) {
            eCertificateFeignService.tsggqz(ggid);
        }
    }

    /**
     * @param qzbs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 签章下载
     * @date : 2022/3/15 14:51
     */
    @ResponseBody
    @GetMapping("/qzxz")
    public Object qzxz(String qzbs) {
        if (StringUtils.isNotBlank(qzbs)) {
            return bdcDzqzFeignService.queryQzfj(qzbs);
        }
        return null;
    }

    /**
     * 获取当前公告关联的流程信息
     * @param ggid 公告ID
     * @return Map<String, String> key: slbh, value: gzlslid
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/gllcxx")
    public Object gllcxx(String ggid) {
        if(StringUtils.isBlank(ggid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到公告信息ID");
        }
        List<BdcXmDO> bdcXmDTOList = this.bdcGgFeignService.queryBdcGgGlBdcXmxx(ggid);

        // 按受理编号进行分组
        Map<String, String> resultMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            resultMap = bdcXmDTOList.stream().collect(Collectors.toMap(xmxx -> xmxx.getSlbh(), xmxx -> xmxx.getGzlslid()));
        }
        return resultMap;
    }

    /**
     * 作废公告需要带如选择数据的上一手信息
     * @param xmids 项目Id
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @ResponseBody
    @GetMapping("/getZfggYwsj")
    public Object getZfggYwsj(@RequestParam("xmids") String[] xmids){
        if (ArrayUtils.isEmpty(xmids)) {
            throw new AppException("缺失参数xmid！");
        }

        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>();
        for (String xmid : xmids) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOS)){
                bdcXmLsgxDOList.addAll(bdcXmLsgxDOS);
            }
        }

        if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
            List<BdcXmAndFbDTO> result = new ArrayList<>();
            List<String> yXmidList = bdcXmLsgxDOList.stream().map(bdcXmLsgxDO -> bdcXmLsgxDO.getYxmid()).collect(Collectors.toList());
            for (String yxmid : yXmidList) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(yxmid);
                List<BdcXmAndFbDTO> bdcXmAndFbDTOS = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmAndFbDTOS)){
                    result.addAll(bdcXmAndFbDTOS);
                }
            }
            result = result.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmAndFbDTO::getXmid))), ArrayList::new));
            return result;
        }

        return null;
    }

    /**
     * 删除公告
     * @param ggids 公告Ids
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    @ResponseBody
    @DeleteMapping("/deleteGg")
    public int deleteBdcGgxx(@RequestBody List<String> ggids){
        if (CollectionUtils.isEmpty(ggids)) {
            return 0;
        }
        return bdcGgFeignService.deleteBdcGgxx(ggids);
    }

}
