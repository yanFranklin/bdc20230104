package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyYwxxFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 选择自然资源单元号
 */
@Controller
@RequestMapping("/rest/v1.0/zrzydjdyh")
public class SelectZrzydyhController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectZrzydyhController.class);

    @Value("${zrzy.cqdwurl:}")
    private String cqdwurl;

    @Value("${zrzy.fjckurl:}")
    private String fjckurl;
    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 选择自然资源单元号
      */
    private static final String ZRZRDYH_URL = "/view/lccj/zrzydjdyh.html";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 选择产权证
      */
    private static final String CQZ_URL = "/view/lccj/zs.html";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 选择台账类型-单元号
     */
    private static final String XZTZLX_ZRZYDYH = "1";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 选择台账类型-单元号
     */
    private static final String XZTZLX_CQZ = "2";

    @Autowired
    ZrzyPzFeignService zrzyPzFeignService;

    @Autowired
    private ZrzyXmFeignService zrzyXmFeignService;
    /**
      * @param processDefKey 工作流定义ID
      * @return 跳转页面
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 选择自然资源单元号页面统一入口
      */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSelectZrzydyhHtml(@RequestParam(name = "") String processDefKey){
        if(StringUtils.isBlank(processDefKey)){
            throw new AppException("工作流定义ID为空");
        }
        ZrzyXtLcpzDO zrzyXtLcpzDO =zrzyPzFeignService.queryZrzyXtLcpz(processDefKey);
        if(zrzyXtLcpzDO ==null){
            throw new AppException("流程基本配置未配置,无法选择数据");
        }
        if(StringUtils.equals(XZTZLX_ZRZYDYH,zrzyXtLcpzDO.getXztzlx())){
            return ZRZRDYH_URL;
        }else if(StringUtils.equals(XZTZLX_CQZ,zrzyXtLcpzDO.getXztzlx())){
            return CQZ_URL;

        }else{
            throw new AppException("不支持当前配置页面,请检查配置");
        }

    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">zhangxinyu</a>
     * @description 新标签页打开产权定位资源地址
     */
    @RequestMapping("/cqdw")
    @ResponseBody
    public void cqdw(HttpServletResponse response, @RequestParam(name = "processInsId") String processInsId) throws IOException {
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("未获取到gzlslid。");
        }
        if(StringUtils.isBlank(cqdwurl)){
            throw new MissingArgumentException("未获取到产权定位地址。");
        }
        //查询自然资源登记单元号
        String url = cqdwurl;
        String zrzydjdyh = "";
        List<ZrzyXmDO>  zrzyXmDOList = zrzyXmFeignService.listZrzyXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(zrzyXmDOList)) {
            zrzydjdyh = zrzyXmDOList.get(0).getZrzydjdyh();
        }
        if (StringUtils.isBlank(zrzydjdyh)) {
            throw new MissingArgumentException("自然资源登记单元号为空。");
        }
//        zrzydjdyh = "123";
        String redirectUrl =url + zrzydjdyh;
        LOGGER.info("产权定位redirect跳转 zrzydjdyh:{},redirectUrl:{}",zrzydjdyh,redirectUrl);
        response.sendRedirect(redirectUrl);
        LOGGER.info("产权定位执行跳转结束 zrzydjdyh:{},redirectUrl:{}",zrzydjdyh,redirectUrl);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">zhangxinyu</a>
     * @description 新标签页打开产权定位资源地址
     */
    @RequestMapping("/fjck")
    @ResponseBody
    public void fjck(HttpServletResponse response, @RequestParam(name = "processInsId") String processInsId) throws IOException {
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("未获取到gzlslid。");
        }
        if(StringUtils.isBlank(fjckurl)){
            throw new MissingArgumentException("未获取到附件查看地址。");
        }
        //查询自然资源登记单元号
        String url = fjckurl;
        String zrzydjdyh = "";
        List<ZrzyXmDO>  zrzyXmDOList = zrzyXmFeignService.listZrzyXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(zrzyXmDOList)) {
            zrzydjdyh = zrzyXmDOList.get(0).getZrzydjdyh();
        }
        if (StringUtils.isBlank(zrzydjdyh)) {
            throw new MissingArgumentException("自然资源登记单元号为空。");
        }
//        zrzydjdyh = "123";
        String redirectUrl = url + zrzydjdyh;
        LOGGER.info("附件查看redirect跳转 zrzydjdyh:{},redirectUrl:{}",zrzydjdyh,redirectUrl);
        response.sendRedirect(redirectUrl);
        LOGGER.info("附件查看执行跳转结束 zrzydjdyh:{},redirectUrl:{}",zrzydjdyh,redirectUrl);
    }
}
