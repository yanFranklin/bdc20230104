package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPjqFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.IPPortUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/28
 * @description 评价器功能
 */
@RestController
@RequestMapping("/rest/v1.0/pjq")
public class BdcPjqController {

    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
     * @param gzlslid 工作流实例ID
     * @param taskid 任务ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同评价器
     */
    @GetMapping("/commonpj")
    @ResponseBody
    public Object commonPj(String gzlslid,String taskid, HttpServletRequest httpServletRequest) {
        if(StringUtils.isBlank(gzlslid) ||StringUtils.isBlank(taskid)){
            throw new AppException("评价器评价缺失参数");
        }
        String ip= IPPortUtils.getClientIp(httpServletRequest);
        return bdcSlPjqFeignService.commonPj(gzlslid,taskid,ip);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同人证对比
     */
    @GetMapping("/commonrzdb")
    @ResponseBody
    public Object commonPjqRzdb(HttpServletRequest request, String qlrmc, String qlrzjh, String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("人证对比缺失参数");
        }
        String ip=IPPortUtils.getClientIp(request);
        return bdcSlPjqFeignService.commonRzdb(qlrmc,qlrzjh,gzlslid,ip);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传base64文件到文件中心
     */
    @ResponseBody
    @PostMapping("/upload/base64")
    public void uploadBase64File(@RequestBody BdcPdfDTO bdcPdfDTO) throws IOException {
        bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 摩科签字
     */
    @GetMapping("/mkqz")
    @ResponseBody
    public Object mkqz(HttpServletRequest request, String qlrmc, String qlrzjh, String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("摩科签字缺失参数");
        }
        String ip="";
        if (StringUtils.isNotBlank(userManagerUtils.getCurrentUserName())) {
            //尝试从配置中获取当前用户的ip
            ip = converZd("BDC_PJQ_IP", "pjq", userManagerUtils.getCurrentUserName());
        }
        if (StringUtils.isBlank(ip)) {
            ip = IPPortUtils.getClientIp(request);
        }
        return bdcSlPjqFeignService.mkqz(qlrmc,qlrzjh,gzlslid,ip);
    }

    public String converZd(String zdTable, String xtbs, String dsfzdz) {
        try {
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setDsfzdz(dsfzdz);
            bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
            if (Objects.nonNull(result)) {
                return result.getBdczdz();
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }
}
