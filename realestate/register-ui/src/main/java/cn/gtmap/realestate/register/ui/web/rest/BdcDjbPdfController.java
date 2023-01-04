package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.clients.AuthorityManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OperationDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/1 17:08
 * @description 登记簿打印Controller
 */
@RestController
@RequestMapping("/rest/v1.0/djbpdf")
@Api("pdfController")
public class BdcDjbPdfController {
    /**
     * 是否打印注销项目
     */
    @Value("#{'${djb.sfdyzxxm:}'.split(',')}")
    private List<String> sfdyzxxm;

    /**
     * 是否打印历史产权
     */
    @Value("${djb.sfdylscq:false}")
    private Boolean sfdylscq;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjbPdfController.class);


    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    BdcDjbPdfPrintController bdcDjbPdfPrintController;

    @Autowired
    private AuthorityManagerClient authorityManagerClient;

    @Autowired
    private UserManagerClient userManagerClient;

    /**
     * 登记簿打印控制
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = {"/djbPdf", "/djbPdf.pdf"})
    public String djbPdf(@RequestParam(name = "bdcdyh", required = true) String bdcdyh,
                         @RequestParam(name = "qllx", required = true) String qllx,
                         @RequestParam(name = "moduleCode") String moduleCode,
                         HttpServletResponse response) {

        List<Integer> qsztList = new ArrayList<>();
        qsztList.add(1);
        if (sfdylscq){
            qsztList.add(2);
        }
        //获取当前用户信息
        List<OperationDto> operationDtoList  = authorityManagerClient.findModuleAuthorityOperation(userManagerClient.getCurrentUser().getUsername(),moduleCode);
        String userName = userManagerClient.getCurrentUser().getAlias();
        //获取用户拥有的权限
        if (CollectionUtils.isNotEmpty(operationDtoList)) {
            for (int i = 0; i < operationDtoList.size(); i++) {
                OperationDto operationDto = operationDtoList.get(i);
                if ("djbPrintAll".equals(operationDto.getCode())) {
                    qsztList.clear();
                }
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //根据配置项，获取注销业务信息
        List<BdcQl> listBdcZxQlxx = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sfdyzxxm) && sfdyzxxm.contains(qllx)) {
            listBdcZxQlxx = bdcDjbxxFeignService.listBdcZxQlxx(bdcdyh, qllx);
        }

        List<BdcQl> listBdcQlxx = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        if (CollectionUtils.isNotEmpty(listBdcZxQlxx)){
            listBdcQlxx.addAll(listBdcZxQlxx);
        }

        //按时间排序
        if (CollectionUtils.isNotEmpty(listBdcQlxx)){
            this.listSort(listBdcQlxx);
        }

        //判断权利类型，组织数据
        if (CollectionUtils.isNotEmpty(listBdcQlxx)) {
            BdcQl bdcQl = listBdcQlxx.get(listBdcQlxx.size() - 1);
            if (bdcQl instanceof BdcFdcqDO) {
                //判断是否是项目多幢
                if (null != ((BdcFdcqDO) bdcQl).getBdcdyfwlx() && 1 == ((BdcFdcqDO) bdcQl).getBdcdyfwlx()) {
                    byteArrayOutputStream = bdcDjbPdfPrintController.fdcqDzPdf(listBdcQlxx, response,userName);
                } else {
                    byteArrayOutputStream = bdcDjbPdfPrintController.fdcqPdf(listBdcQlxx, response,userName);
                }
            }
            if (bdcQl instanceof BdcJsydsyqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.jsydZjdPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcTdsyqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.tdsyqPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcFdcq3DO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.gybfPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcDyaqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.dyaPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcHysyqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.hysyqPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcGjzwsyqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.gjzwsyqPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcYyDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.yyPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcYgDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.ygPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcCfDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.cfPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcDyiqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.dyiPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcLqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.lqPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcTdcbnydsyqDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.tdcbjyNydPdf(listBdcQlxx, response,userName);
            }
            if (bdcQl instanceof BdcQtxgqlDO) {
                byteArrayOutputStream = bdcDjbPdfPrintController.qtxgqlPdf(listBdcQlxx, response,userName);
            }
            try {
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String pdfBasse64 = encoder.encodeToString(bytes);
                return pdfBasse64;
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
                throw new AppException("pdf转base64异常!");
            }
        } else {
            //数据为空，根据权利类型抛出提示信息
            throw new AppException("该不动产单元号无现势权利信息或注销业务！");
        }
    }

    /**
     * 判断打印时，当前用户是否有print权限，如果有，则有权限打印所有记录，是否有该现势权利信息
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcdyh qllx qsztList
     * @return 0无；1有
     */
    @GetMapping("/qlxx")
    public Integer getQlxx(@RequestParam(name = "bdcdyh", required = true) String bdcdyh,
                           @RequestParam(name = "qllx", required = true) String qllx,
                           @RequestParam(name = "moduleCode") String moduleCode ){
        List<Integer> qsztList = new ArrayList<>();
        qsztList.add(1);
        //获取当前用户信息
        List<OperationDto> operationDtoList  = authorityManagerClient.findModuleAuthorityOperation(userManagerClient.getCurrentUser().getUsername(),moduleCode);
        //获取用户拥有的权限
        if (CollectionUtils.isNotEmpty(operationDtoList)) {
            for (int i = 0; i < operationDtoList.size(); i++) {
                OperationDto operationDto = operationDtoList.get(i);
                if ("djbPrintAll".equals(operationDto.getCode())) {
                    qsztList.clear();
                }
            }
        }


        //根据配置项，获取注销业务信息
        List<BdcQl> listBdcZxQlxx = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sfdyzxxm) && sfdyzxxm.contains(qllx)) {
            listBdcZxQlxx = bdcDjbxxFeignService.listBdcZxQlxx(bdcdyh, qllx);
        }

        List<BdcQl> listBdcQlxx = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);

        if (CollectionUtils.isNotEmpty(listBdcZxQlxx)){
            listBdcQlxx.addAll(listBdcZxQlxx);
        }

        /**
         * 无权限：只查现势权利信息，若为空，则表示无该权利现势信息
         * 有权限：查所有状态的权利信息，返回为空，则表示无该权利信息
         */
        if (CollectionUtils.isEmpty(listBdcQlxx)) {
            return 0;
        }
        return 1;
    }

    //按时间排序
    private  void listSort(List<BdcQl> list) {
        Collections.sort(list, new Comparator<BdcQl>() {
            @Override
            public int compare(BdcQl o1, BdcQl o2) {
                try {
                    if (o1.getDjsj() != null && o2.getDjsj() != null) {
                        return o1.getDjsj().compareTo(o2.getDjsj());
                    } else {
                        return o1 == null ? 1 : -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
