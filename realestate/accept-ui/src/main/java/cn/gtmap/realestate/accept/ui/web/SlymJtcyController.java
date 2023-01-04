package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFileBase64DTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareHyxxResultDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJtcyFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcJtcyCxYmxxVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 受理家庭成员(BDC_SL_JTCY)
 */
@Controller
@RequestMapping("/slym/jtcy")
public class SlymJtcyController extends BaseController {
    @Autowired
    BdcSlJtcyFeignService bdcSlJtcyFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理家庭成员信息
     */
    @ResponseBody
    @PatchMapping("")
    public int updateBdcSlJtcy(@RequestBody String json) {
        int count =0;
        for(BdcSlJtcyDO bdcSlJtcyDO : JSON.parseArray(json,BdcSlJtcyDO.class)){
            if(StringUtils.isBlank(bdcSlJtcyDO.getJtcyid())){
                bdcSlJtcyFeignService.insertBdcSlJtcy(bdcSlJtcyDO);
                count++;
                // 插入家庭成员后 需要把本流程其他项目的家庭成员也新增一份
                int newcount = this.insertOtherJtcy(bdcSlJtcyDO);
                count += newcount;
            }else{
                count += bdcSlJtcyFeignService.updateBdcSlJtcy(bdcSlJtcyDO);
            }
        }
        return count;
    }

    /**
     * @param jtcyid 家庭成员ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除家庭成员
     */
    @ResponseBody
    @DeleteMapping("")
    public void deleteBdcSlJtcy(@RequestParam("jtcyid") String jtcyid){
        bdcSlJtcyFeignService.deleteBdcSlJtcyByJtcyid(jtcyid);

    }

    /**
     * @param sqrid 申请人ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除家庭成员
     */
    @ResponseBody
    @DeleteMapping("/all")
    public void deleteBdcSlJtcyBySqrid(@RequestParam("sqrid") String sqrid){
        bdcSlJtcyFeignService.deleteBdcSlJtcyBySqrid(sqrid);
    }

    /**
     * @param jtcyid 家庭成员ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除多个申请人家庭成员
     */
    @ResponseBody
    @DeleteMapping("/deleteBatchBdcSlJtcy")
    public void deleteBatchBdcSlJtcy(@RequestParam("jtcyid") String jtcyid,@RequestParam("sqridList") List<String> sqridList){
        if(CollectionUtils.isNotEmpty(sqridList) &&StringUtils.isNotBlank(jtcyid)){
            BdcSlJtcyDO bdcSlJtcyDO =bdcSlJtcyFeignService.queryBdcSlJtcyByJtcyid(jtcyid);
            if(bdcSlJtcyDO != null){
                bdcSlJtcyFeignService.deleteBatchBdcSlJtcy(sqridList,bdcSlJtcyDO.getZjh());

            }

        }


    }

    /**
     * @param getJtcyxxQO 接口入参（一般接口入参为：权利人名称、权利人证件号与beanName）
     * @return 接口返回值（Object）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 婚姻信息查询/公安信息查询
     * <p>查询婚姻信息、公安信息、户籍信息通用接口</p>
     */
    @ResponseBody
    @GetMapping("/hygaxx")
    public Object getHygaxx(GetJtcyxxQO getJtcyxxQO){
        if(StringUtils.isAnyBlank(getJtcyxxQO.getQlrzjh(), getJtcyxxQO.getBeanName())){
            throw new MissingArgumentException("查询参数缺失！");
        }
        return bdcSlJtcyFeignService.getHygaxx(getJtcyxxQO);
    }

    /**
     * @param getJtcyxxQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 合肥家庭成员查询（目前合肥蚌埠使用,后期调整接口地址）
     */
    @ResponseBody
    @GetMapping("/hefei/getJtcyxx")
    public Object getJtcyxx(GetJtcyxxQO getJtcyxxQO){
        LOGGER.info("家庭成员查询参数：{}",getJtcyxxQO);
        if(StringUtils.isBlank(getJtcyxxQO.getQlrmc()) ||StringUtils.isBlank(getJtcyxxQO.getQlrzjh()) ||StringUtils.isBlank(getJtcyxxQO.getSqrid()) ||StringUtils.isBlank(getJtcyxxQO.getSlbh()) ){
            throw new MissingArgumentException("查询参数缺失！");
        }
        return bdcSlJtcyFeignService.getJtcyxx(getJtcyxxQO);

    }


    /**
     * 通过南通省级平台接口获取家庭成员信息，并同步至受理家庭成员中
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param qlrmc 申请人名称
     * @param qlrzjh 申请人证件号
     * @param sqrid 申请人ID
     * @return: void 无返回值
     */
    @ResponseBody
    @GetMapping("/nt/getJtcyxx")
    public void updateJtcyxxNt(@RequestParam(value = "qlrmc",required = false)String qlrmc,
                             @RequestParam(value = "qlrzjh",required = false)String qlrzjh,
                             @RequestParam(value = "sqrid",required = false)String sqrid){
        if(StringUtils.isBlank(qlrmc) ||StringUtils.isBlank(qlrzjh) ||StringUtils.isBlank(sqrid)){
            throw new MissingArgumentException("查询参数缺失！");
        }
        bdcSlJtcyFeignService.getJtcyxxNt(qlrmc,qlrzjh,sqrid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询获取家庭成员
     */
    @ResponseBody
    @GetMapping("/listBdcSlJtcy")
    public Object listBdcSlJtcy(BdcSlJtcyQO bdcSlJtcyQO){
        if (!CheckParameter.checkAnyParameter(bdcSlJtcyQO)) {
            throw new MissingArgumentException("查询家庭成员参数不能为空");
        }
        return this.bdcSlJtcyFeignService.listBdcSlJtcy(bdcSlJtcyQO);
    }

    /**
     * 新增家庭成员信息后 ，批量的流程其余的项目要保存同样jtcy信息
     * 保存除当前项目下的其他项目的jtcy
     * @param bdcSlJtcyDO
     */
    private int insertOtherJtcy(BdcSlJtcyDO bdcSlJtcyDO){
        int count = 0;
        BdcSlJtcyDO bdcSlJtcyDOTemp =  bdcSlJtcyDO;
        // sqrid查询xmid
        String sqrid = bdcSlJtcyDO.getSqrid();
        BdcSlSqrDO bdcSlSqrDO = bdcSlSqrFeignService.queryBdcSlSqrBySqrid(sqrid);
        if(bdcSlSqrDO != null) {
            String xmid = bdcSlSqrDO.getXmid();
            if(StringUtils.isNotBlank(xmid)){
                // xmid查询jbxxid
                BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                bdcSlXmQO.setXmid(xmid);
                // jbxxid查询所有xm信息
                List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
                if (CollectionUtils.isNotEmpty(list)) {
                    String jbxxid = list.get(0).getJbxxid();
                    bdcSlXmQO.setXmid(null);
                    bdcSlXmQO.setJbxxid(jbxxid);
                    list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
                    // 遍历该流程，把当前项目以外的权利人的sqrid拿到
                    for (int i = 0; i < list.size(); i++) {
                        // 排除当前项目
                        if (!xmid.equals(list.get(i).getXmid())) {
                            String exincludeXmid = list.get(i).getXmid();
                            BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                            bdcSlSqrQO.setXmid(exincludeXmid);
                            bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                            // 查询非本流程的sqr信息
                            List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                            if (CollectionUtils.isNotEmpty(listSqr)) {
                                // 遍历申请人
                                for (int j = 0; j < listSqr.size(); j++) {
                                    String thisSqrid = listSqr.get(j).getSqrid();
                                    List<BdcSlJtcyDO> listjtcy = bdcSlJtcyFeignService.listBdcSlJtcyBySqrid(thisSqrid);
                                    // 如果项目之前的申请人长度不等 则不是批量流程
                                    // 没有家庭成员的sqr才去新增
                                    // 当前申请人与已新增的申请人是同一个（zjh）
                                    if (CollectionUtils.isEmpty(listjtcy)&& StringUtils.isNotBlank(listSqr.get(j).getZjh()) &&listSqr.get(j).getZjh().equals(bdcSlSqrDO.getZjh())) {
                                        bdcSlJtcyDOTemp.setSqrid(listSqr.get(j).getSqrid());
                                        bdcSlJtcyFeignService.insertBdcSlJtcy(bdcSlJtcyDOTemp);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 民政婚姻信息比对
     */
    @ResponseBody
    @GetMapping("/compareHyxx")
    public Object compareHyxx(GetJtcyxxQO getJtcyxxQO,@RequestParam(name = "hyzk", required = false) String hyzk){
        if(StringUtils.isBlank(hyzk)){
            throw new MissingArgumentException("未输入婚姻状况！");
        }
        if(StringUtils.isBlank(getJtcyxxQO.getQlrmc()) ||StringUtils.isBlank(getJtcyxxQO.getQlrzjh()) &&StringUtils.isBlank(getJtcyxxQO.getSqrid())){
            throw new MissingArgumentException("查询婚姻接口缺失申请人名称或证件号");
        }
        //先保存婚姻状况
        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setHyzk(hyzk);
        bdcSlSqrDO.setSqrid(getJtcyxxQO.getSqrid());
        bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
        return bdcSlJtcyFeignService.compareHyxx(getJtcyxxQO);
    }

    /**
     * @param compareHyxxQO
     * @return 提示信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 南通一体化民政婚姻信息比对
     */
    @ResponseBody
    @PostMapping("nantong/compareHyxx")
    public Object nantongCompareHyxx(@RequestBody @Valid CompareHyxxQO compareHyxxQO, Errors errors){
        if(errors.hasErrors()){
            throw new MissingArgumentException(CommonUtil.validMsg(errors));
        }
        //先保存婚姻状况
        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setHyzk(compareHyxxQO.getHyzt());
        bdcSlSqrDO.setSqrid(compareHyxxQO.getSqrid());
        bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
        return bdcSlJtcyFeignService.compareHyznxx(compareHyxxQO);
    }

    /**
     * 通过工作流实例ID获取户籍信息
     * <p>先获取权利人信息集合，在循环通过权利人证件号，调用户籍接口获取户籍信息。
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<Map> 权利人和户籍信息
     */
    @ResponseBody
    @PostMapping("/bengbu/queryHjxx")
    public Object queryHjxx(@RequestParam(name="gzlslid") String gzlslid){
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未获取到工作流实例ID。");
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listAllBdcQlr(gzlslid, null,null);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(bdcQlrDOList), "未获取到权利人信息。");
        // 按权利人类别、顺序号进行从小到大排序，增加数据去重：按权利人名称、证件号、权利人类别进行去重
        bdcQlrDOList = bdcQlrDOList.stream().sorted(Comparator.comparing(BdcQlrDO::getQlrlb)
                .thenComparing(t->t.getSxh(),Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(o -> o.getQlrmc()+ ";" + o.getZjh() + ";" + o.getQlrlb())
                        )), ArrayList::new));
        List<Map<String,Object>> list = new ArrayList<>(bdcQlrDOList.size());
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            for(BdcQlrDO bdcQlrDO: bdcQlrDOList){
                Map<String,Object> map = new HashMap<>(2);
                map.put("qlrxx", bdcQlrDO);
                map.put("hjxx", bdcSlJtcyFeignService.getHygaxx(new GetJtcyxxQO(bdcQlrDO.getZjh(),"acceptJtcyxx")));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * @param resultDTO 婚姻查询信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入婚姻比对信息
     */
    @ResponseBody
    @PostMapping("import/hybdxx")
    public void drhybdxx(@RequestBody CompareHyxxResultDTO resultDTO){
        bdcSlJtcyFeignService.drhybdxx(resultDTO);
    }

    /**
     * 南通查询婚姻信息，并生成查询文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping(value = "/hyxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public void queryHyxxAndGenerateFile(@PathVariable(name = "gzlslid") String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID.");
        }
        this.bdcSlJtcyFeignService.queryHyxxAndGenerateFile(gzlslid, isFirstHand);
    }

    /**
     * 南通查询户籍信息，并生成查询文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping(value = "/hjxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public void queryHjxxAndGenerateFile(@PathVariable(name = "gzlslid")String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID.");
        }
        this.bdcSlJtcyFeignService.queryHjxxAndGenerateFile(gzlslid, isFirstHand);
    }

    /**
     * 上传截图信息
     * @param bdcFileBase64DTO 文件Base64信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping(value = "/upload/screenshot")
    @ResponseStatus(HttpStatus.OK)
    public void uploadScreenShot(@RequestBody BdcFileBase64DTO bdcFileBase64DTO){
        this.bdcSlJtcyFeignService.uploadScreenShot(bdcFileBase64DTO);
    }

    /**
     * 根据工作流实例ID，获取家庭成员查询页面信息
     * @param gzlslid 工作流实例ID
     * @return 不动产家庭成员查询页面信息VO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping(value = "/ymxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public BdcJtcyCxYmxxVO queryJtcyYmxx(@PathVariable(name = "gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID.");
        }
        return this.bdcSlJtcyFeignService.queryJtcyYmxx(gzlslid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 修改家庭成员信息
     */
    @ResponseBody
    @PostMapping("/updateJtcyxx")
    public Object updateJtcycym(@RequestParam(value="id") String id,@RequestParam(value="cym",required = false) String cym,@RequestParam(value="hyzk",required = false) String hyzk,@RequestParam(value="lx") String lx){
        if(StringUtils.isNotBlank(lx)){
            BdcSlJtcyDO bdcSlJtcyDO = new BdcSlJtcyDO();
            if(StringUtils.isNotBlank(cym)){
                bdcSlJtcyDO.setCym(cym);
            }
            if(StringUtils.isNotBlank(hyzk)){
                bdcSlJtcyDO.setHyzk(hyzk);
            }
            bdcSlJtcyDO.setJtcyid(id);

            return bdcSlJtcyFeignService.updateBdcSlJtcy(bdcSlJtcyDO);
        }
        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setSqrid(id);
        if(StringUtils.isNotBlank(cym)) {
            bdcSlSqrDO.setCym(cym);
        }
        if(StringUtils.isNotBlank(hyzk)){
            bdcSlSqrDO.setHyzk(hyzk);
        }
        return bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
    }

}
