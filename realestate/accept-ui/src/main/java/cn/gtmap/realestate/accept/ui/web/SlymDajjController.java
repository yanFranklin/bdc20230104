package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.OpinionDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDajjDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcJjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: ????????????????????????
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-06-10 15:38
 **/
@RestController
@RequestMapping("/slym/dajj")
@Validated
public class SlymDajjController extends BaseController {

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcJjdFeignService bdcJjdFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;

    @ResponseBody
    @GetMapping("")
    public Object queryDajjJbxx(@NotBlank(message = "?????????????????????????????????id????????????") String gzlslid,
                                @NotBlank(message = "??????????????????taskid????????????") String taskid) {
        BdcDajjDTO bdcDajjDTO = new BdcDajjDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.isNull(bdcSlJbxxDO)) {
            bdcSlJbxxDO = new BdcSlJbxxDO();
        }
        bdcDajjDTO.setBdcSlJbxxDO(bdcSlJbxxDO);
        TaskData taskData = processTaskClient.getTaskById(taskid);
        //???????????????????????????
        OpinionDto opinionDto = processTaskClient.queryProcessOpinion(gzlslid, taskid, "back_opinion");
        LOGGER.info("??????????????????{}", JSON.toJSONString(taskData));
        if (Objects.nonNull(opinionDto)) {
            bdcDajjDTO.setThyy(opinionDto.getOpinion());
            bdcDajjDTO.setThsj(simpleDateFormat.format(opinionDto.getTime()));
        }
        //???????????????????????????
        BdcJjdQO bdcJjdQO = new BdcJjdQO();
        bdcJjdQO.setDajjGzlslid(gzlslid);
        List<BdcJjdDO> bdcJjdDOList = bdcJjdFeignService.listBdcJjd(bdcJjdQO);
        //??????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(bdcJjdDOList)) {
            BdcJjdDO bdcJjdDO = bdcJjdDOList.get(0);
            if (Objects.equals(0, bdcJjdDO.getJjdzt())) {
                bdcDajjDTO.setAjzt("?????????");
            } else if (Objects.equals(5, bdcJjdDO.getJjdzt())) {
                bdcDajjDTO.setAjzt("?????????");
            } else if (Objects.equals(7, bdcJjdDO.getJjdzt())) {
                bdcDajjDTO.setAjzt("??????");
            }
            List<BdcXmYjdGxDO> bdcXmYjdGxDOList = bdcJjdFeignService.queryJjdGx(bdcJjdDO.getJjdid());
            if (CollectionUtils.isNotEmpty(bdcXmYjdGxDOList)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                //????????????????????????????????????????????????xmid ??????????????????gzlslid???
                bdcXmQO.setGzlslid(bdcXmYjdGxDOList.get(0).getXmid());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcDajjDTO.setQlr(bdcXmDOList.get(0).getQlr());
                } else {
                    //??????????????????
                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                            bdcDajjDTO.setQlr(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", CommonConstantUtils.ZF_YW_DH));
                        } else {
                            bdcDajjDTO.setQlr(bdcSlJbxxDO.getSqrxm());
                        }
                    } else {
                        bdcDajjDTO.setQlr(bdcSlJbxxDO.getSqrxm());
                    }
                }
                //?????????????????????????????????????????????
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(bdcXmYjdGxDOList.get(0).getXmid());
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    bdcSlSjclDOList = new ArrayList<>(1);
                }
                bdcDajjDTO.setBdcSlSjclDOList(bdcSlSjclDOList);
                bdcDajjDTO.setYxmGzlslid(bdcXmYjdGxDOList.get(0).getXmid());
                bdcDajjDTO.setBdcJjdDO(bdcJjdDO);
            }
        }
        return bdcDajjDTO;
    }
}
