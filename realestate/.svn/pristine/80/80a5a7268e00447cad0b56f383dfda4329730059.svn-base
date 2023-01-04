package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlEmsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFzjlPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcFzjlFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/15
 * @description 发证记录
 */
@RestController
@RequestMapping("/rest/v1.0/fzjl")
public class BdcFzjlController extends BaseController {
    @Autowired
    BdcFzjlFeignService bdcFzjlFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcPrintController bdcPrintController;
    @Autowired
    PdfController pdfController;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    private BdcSlYjxxFeginService bdcSlYjxxFeginService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Value("${sftsYcyh:true}")
    private String sftsYcyh;

    @Value("${print.path}")
    private String printPath;

    @Value("${html.version:standard}")
    private String htmlVersion;

    /**
     * 盐城一体化邮寄信息推送保存附件，文件夹名称
     */
    @Value("${yth.yjxx.wjjmc:一体化EMS}")
    private String yjxxWjjmc;

    /**
     * 盐城发证记录界面获取EMS人员材料信息,上传文件夹名称
     */
    @Value("${emsry.wjjmc:EMS人员}")
    private String emsryWjjmc;

    /**
     * @param zsid 证书ID
     * @return BdcFzjlDTO 项目的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询单个证书的发证记录
     */
    @GetMapping(value = "/zs/{zsid}")
    @ResponseStatus(code = HttpStatus.OK)
    BdcFzjlDTO queryBdcZsFzjl(@PathVariable(name = "zsid") String zsid) {
        BdcFzjlDTO bdcFzjlDTO = bdcFzjlFeignService.queryBdcZsFzjl(zsid);

        return bdcFzjlDTO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 流程的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询流程的发证记录(一个流程一个发证记录 ， sfhb = true时合并展示加 " 等 ")
     */
    @GetMapping(value = "/one/{gzlslid}")
    @ResponseStatus(code = HttpStatus.OK)
    BdcFzjlDTO queryBdcLcOneFzjl(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "sfhb", required = false) Boolean sfhb) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return new BdcFzjlDTO();
        }
        // 针对2.0遗留问题特殊处理
        if (null == sfhb) {
            String djyy = bdcXmDOList.get(0).getDjyy();
            if (StringUtils.indexOf(djyy, "（出证）") > -1 || StringUtils.indexOf(djyy, "（出单）") > -1) {
                sfhb = true;
            } else {
                sfhb = false;
            }
        }
        BdcFzjlDTO bdcFzjlDTO;
        if (sfhb) {
            bdcFzjlDTO = bdcFzjlFeignService.queryHbFzjl(gzlslid, bdcXmDOList);
        } else {
            bdcFzjlDTO = bdcFzjlFeignService.queryLcOneFzjl(gzlslid, bdcXmDOList);
        }
        // 设置是否合并的值是为了后期在同一个发证记录页面便于数据的保存

        String name = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(name);

        bdcFzjlDTO.setFzr(StringUtil.isNullOrEmpty(bdcFzjlDTO.getFzr()) ? userDto.getAlias() : bdcFzjlDTO.getFzr());
        bdcFzjlDTO.setFzrq(bdcFzjlDTO.getFzrq() == null ? new Date() : bdcFzjlDTO.getFzrq());

        bdcFzjlDTO.setSfhb(sfhb);
        bdcFzjlDTO.setSftsYcyh(sftsYcyh);
        return bdcFzjlDTO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 流程的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询流程的发证记录
     */
    @GetMapping(value = "/{gzlslid}")
    @ResponseStatus(code = HttpStatus.OK)
    BdcFzjlDTO queryBdcLcFzjl(@PathVariable(name = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        boolean sfhb = false;
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return new BdcFzjlDTO();
        }
        if (CollectionUtils.size(bdcXmDOList) > 1) {
            // 超过一个项目的流程，设置为合并显示
            sfhb = true;
        }

        if (CommonConstantUtils.SYSTEM_VERSION_BB.equals(htmlVersion) || CommonConstantUtils.SYSTEM_VERSION_STD.equals(htmlVersion)){
            // 蚌埠或者标准版：发证记录按照合并逻辑处理，即领证人展示一个权利人、证号等信息拼接，保存时候多个证书的领证人一致
            sfhb = true;
        }

        BdcFzjlDTO bdcFzjlDTO = new BdcFzjlDTO();
        if (sfhb) {
            bdcFzjlDTO = bdcFzjlFeignService.queryHbFzjl(gzlslid, bdcXmDOList);
        } else {
            bdcFzjlDTO = bdcFzjlFeignService.queryFzjl(bdcXmDOList.get(0).getXmid());
        }

        // 设置是否合并的值是为了后期在同一个发证记录页面便于数据的保存
        bdcFzjlDTO.setSfhb(sfhb);

        return bdcFzjlDTO;
    }

    /**
     * @param xmid       项目ID
     * @param sfhb       是否合并展示
     * @param bdcFzjlDTO 发证记录对象
     * @return int 更新记录数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存发证记录（领证人信息，备注信息）
     */
    @PutMapping(value = "/{xmid}/{sfhb}")
    @ResponseStatus(HttpStatus.OK)
    int updateFzjl(@PathVariable(name = "xmid") String xmid, @PathVariable(name = "sfhb") boolean sfhb, @RequestBody(required = true) BdcFzjlDTO bdcFzjlDTO) {
        return bdcFzjlFeignService.updateFzjl(xmid, sfhb, bdcFzjlDTO);
    }

    @PostMapping(value = "/lzr")
    @ResponseStatus(HttpStatus.OK)
    int updateLzrxx(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestBody(required = true) BdcFzjlZsDTO bdcFzjlZsDTO) {
        // 获取的zsidList为空，gzlslid有值时获取流程所有证书的ID
        if (StringUtils.isNotBlank(gzlslid) && CollectionUtils.isEmpty(bdcFzjlZsDTO.getZsidList())) {
            List<String> zsidList = bdcZsFeignService.queryGzlZsid(gzlslid);
            bdcFzjlZsDTO.setZsidList(zsidList);
        }
        return bdcFzjlFeignService.updateLzrxx(bdcFzjlZsDTO);
    }

    /**
     * 检查领证人信息（南通）
     *
     * @param xmid       项目ID
     * @param bdcFzjlDTO 发证记录对象
     * @return String 提示信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/lzr/check")
    @ResponseStatus(HttpStatus.OK)
    String checkLzr(@RequestParam(name = "xmid") String xmid, @RequestBody(required = true) BdcFzjlDTO bdcFzjlDTO) {
        return bdcFzjlFeignService.checkLzr(xmid, bdcFzjlDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @return BdcFzjlDTO
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新缮证人为当前用户(不管证书表中是否存在缮证人信息都更新)
     */
    @GetMapping(value = "/updateszr")
    @ResponseStatus(code = HttpStatus.OK)
    BdcSzxxVO updateSzr(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失gzlslid和xmid，至少需要一个参数有值！");
        }
        if (StringUtils.isBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0) || StringUtils.isBlank(bdcXmDOList.get(0).getGzlslid())) {
                throw new AppException("xmid:"+xmid+"未获取到项目信息");
            }
            gzlslid = bdcXmDOList.get(0).getGzlslid();
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        return bdcFzjlFeignService.updateSzrByGzlslid(gzlslid, userDto.getUsername());
    }
    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @return BdcFzjlDTO
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 获取发证人信息
     */
    @GetMapping(value = "/fzr")
    @ResponseStatus(code = HttpStatus.OK)
    BdcFzjlDTO getFzr(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid) {
        BdcFzjlDTO bdcFzjlDTO = new BdcFzjlDTO();
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            LOGGER.info("缺失gzlslid和xmid，至少需要一个参数有值！");
            return bdcFzjlDTO;
        }
        if (StringUtils.isBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0) || StringUtils.isBlank(bdcXmDOList.get(0).getGzlslid())) {
                LOGGER.info("xmid未获取到项目信息");
                return bdcFzjlDTO;
            }
            gzlslid = bdcXmDOList.get(0).getGzlslid();
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 无需判空数据库是否有发证人信息，都更新
        Boolean isNullUpdate = false;
        int result = bdcFzjlFeignService.updateFzr(gzlslid, userDto, isNullUpdate);
        if (result > 0) {
            bdcFzjlDTO.setFzr(userDto.getAlias());
            bdcFzjlDTO.setFzrq(new Date());
        }
        return bdcFzjlDTO;
    }

    /**
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存领证人签字图片信息
     */
    @PostMapping("/lzr/qzxx")
    public BdcQzxxDO saveFzjlLzrQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO) {
        return bdcFzjlFeignService.saveFzjlLzrQzxx(bdcFzjlLzrQzxxDTO);
    }

    /**
     * @param xmid 项目ID
     * @param zsid 证书ID
     * @return {String} 领证人签字图片Base64字符串
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取领证人签字图片信息
     */
    @GetMapping("/lzr/qzxx")
    public String getFzjlLzrQzxx(@RequestParam("xmid") String xmid, @RequestParam(value="zsid",required = false) String zsid) {
        return bdcFzjlFeignService.getFzjlLzrQzxx(xmid, zsid);
    }

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     * @param bdcQzxxQO 签字信息查询参数
     * @return {List} 发证记录领证人签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/lzr/qzxxs")
    public List<BdcQzxxDO> getFzjlLzrQzxxs(@RequestBody BdcQzxxQO bdcQzxxQO) {
        return bdcFzjlFeignService.getFzjlLzrQzxxs(bdcQzxxQO);
    }

    /**
     * 下载领证人签字图片 （请求URL地址例如：/realestate-register-ui/rest/v1.0/fzjl/print/57JF2031FZN0509Q/lzrqz）
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/print/{xmid}/lzrqz")
    public void downloadLzrqzSignImage(@PathVariable(name = "xmid") String xmid, @RequestParam(name="zsid", required = false)String zsid,
                                       HttpServletResponse response) {
        if (StringUtils.isBlank(xmid)) {
            return;
        }

        String signImage = this.getFzjlLzrQzxx(xmid, zsid);
        if (StringUtils.isBlank(signImage)) {
            return;
        }
        this.downloadImage(response, xmid, signImage);
    }

    /**
     * 下载指定顺序领证人签字图片（请求URL地址例如：/realestate-register-ui/rest/v1.0/fzjl/print/57JF2031FZN0509Q/1/lzrqz）
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/print/{xmid}/{sxh}/lzrqz")
    public void downloadLzrqzSignImage(HttpServletResponse response,
                                       @PathVariable(name = "xmid") String xmid,
                                       @PathVariable(name = "sxh") Integer sxh) {
        if (StringUtils.isBlank(xmid) || null == sxh) {
            return;
        }

        BdcQzxxQO bdcQzxxQO = new BdcQzxxQO();
        bdcQzxxQO.setXmid(xmid);
        bdcQzxxQO.setSxhs(Collections.singletonList(sxh));
        List<BdcQzxxDO> qzxxDOList = this.getFzjlLzrQzxxs(bdcQzxxQO);
        if(CollectionUtils.isEmpty(qzxxDOList) || null == qzxxDOList.get(0)) {
            return;
        }

        if(StringUtils.isBlank(qzxxDOList.get(0).getQznr()) || null == qzxxDOList.get(0).getSxh()) {
            // 如果查询出来的记录没有顺序号说明是查询默认的批量签字内容，不符合当前指定顺序要求
            return;
        }

        this.downloadImage(response, xmid, qzxxDOList.get(0).getQznr());
    }

    /**
     * @param bdcFzjlPdfDTO 参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成发证记录PDF文件，并保存到当前项目附件中
     */
    @PostMapping("/pdf")
    public void generateFzjlPdf(@RequestBody BdcFzjlPdfDTO bdcFzjlPdfDTO) {
        if (StringUtils.isAnyBlank(bdcFzjlPdfDTO.getGzlslid(), bdcFzjlPdfDTO.getDylx())) {
            throw new MissingArgumentException("发证记录生成PDF操作缺少参数");
        }

        // 获取PDF打印数据
        String xmlData = bdcPrintController.fzjlOnePrintXml(bdcFzjlPdfDTO.getGzlslid(), bdcFzjlPdfDTO.getXmid(), bdcFzjlPdfDTO.getDylx());
        LOGGER.info("发证记录生成PDF文件，PDF打印数据：{}", xmlData);

        // 生成PDF文件（PDF的生成需要UI应用处理）
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "fzjl.docx");
        pdfExportDTO.setFileName("发证记录");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("发证记录生成PDF文件，文件路径：{}", pdfFilePath);

        // 上传文件到storage
        bdcFzjlPdfDTO.setPdfFilePath(pdfFilePath);
        bdcFzjlFeignService.saveFzjlPdfFile(bdcFzjlPdfDTO);
    }

    public void copyFzjlJbxx(BdcFzjlDTO fzjlDTO, List<BdcFzjlDTO> fzjlDTOList) {
        fzjlDTO.setXmid(fzjlDTOList.get(0).getXmid());
        fzjlDTO.setSlbh(fzjlDTOList.get(0).getSlbh());
        fzjlDTO.setFzrq(fzjlDTOList.get(0).getFzrq());
        fzjlDTO.setSqr(fzjlDTOList.get(0).getSqr());
        fzjlDTO.setSqrlxdh(fzjlDTOList.get(0).getSqrlxdh());
        fzjlDTO.setZl(fzjlDTOList.get(0).getZl() + CommonConstantUtils.SUFFIX_PL);
        fzjlDTO.setSzr(fzjlDTOList.get(0).getSzr());
        fzjlDTO.setFzr(fzjlDTOList.get(0).getFzr());
        fzjlDTO.setBz(fzjlDTOList.get(0).getBz());
        fzjlDTO.setSftsYcyh(fzjlDTOList.get(0).getSftsYcyh());
    }

    /**
     * 下载领证人签字图片
     * @param xmid 项目ID
     * @param signImage 签字图片数据
     */
    private void downloadImage(HttpServletResponse response, String xmid, String signImage) {
        response.setContentType("image/png;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=fzjlqz.png");

        File lzrqzImageFile = null;
        try {
            lzrqzImageFile = new File("fzjlqz.png");
            FileUtils.writeByteArrayToFile(lzrqzImageFile, Base64Utils.decodeBase64StrToByte(signImage));
            FileUtils.copyFile(lzrqzImageFile, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("下载领证人签字图片信息失败,对应项目ID: {}", xmid);
        } finally {
            FileUtils.deleteQuietly(lzrqzImageFile);
        }
    }

    /**
     * @param
     * @return true:办结 false：未办结
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 判断 流程是否已经办结
     */
    @GetMapping("/check")
    public Object checkState(@RequestParam String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<TaskData> taskDataList = processTaskClient.processRunningTasks(gzlslid);
        Map<String, Object> result = Maps.newHashMap();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        boolean state = CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDOList.get(0).getAjzt());
        boolean isSz = false;
        if (CollectionUtils.isNotEmpty(taskDataList) && StringUtils.equals(taskDataList.get(0).getTaskName(), CommonConstantUtils.JD_SZ)) {
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                isSz = DateUtils.isSameDay(bdcXmDOList.get(0).getDjsj(), new Date());
            }
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslidHjfk(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("收费信息为空！");
        }
        boolean sfzt = bdcSlSfxxDOList.get(0).getSfzt() != null
                && bdcSlSfxxDOList.get(0).getSfzt() == 2;

        result.put("sfzt", sfzt);
        result.put("state", state);
        result.put("isSz", isSz);
        return result;
    }

    /**
     * @param slbh
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 收费
     */
    @GetMapping("/sf")
    public void sf(@RequestParam String slbh, @RequestParam String gxbz) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("收费信息为空！");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
            BdcXmDO bdcXm = bdcXmFeignService.queryBdcXmByXmid(bdcSlSfxxDO.getXmid());
            if((xmlx == 2 || xmlx == 4)) {
                //验证是否启用电子证照银行
                boolean dzzzyh = this.sfdzzzyh(bdcSlSfxxDO);
                if ( CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXm.getQllx()) && dzzzyh){
                    bdcSlSfxxDO.setSfztczsj(bdcXm.getDjsj());
                }
            } else {
                bdcSlSfxxDO.setSfztczsj(new Date());
            }
            bdcSlSfxxDO.setSfztczrxm(userDto.getAlias());
            // 当更新标志为1 或 收费时间为空时
            if (StringUtils.equals(gxbz, "1") || bdcSlSfxxDO.getSfsj() == null) {
                bdcSlSfxxDO.setSfsj(new Date());
            }
        });
        LOGGER.info("当前受理编号{},在发证记录页面点击收费，更新收费状态和收费时间为当前时间", slbh);
        bdcSlSfxxFeignService.updateBdcSlSfxxList(bdcSlSfxxDOList);
    }

    /**
     * 分页查询ems人员信息
     * @param pageable
     * @param bdcXtjgQO
     * @return
     */
    @GetMapping("/queryEmsRyxx")
    public Object queryEmsRyxx(@LayuiPageable Pageable pageable, BdcXtjgQO bdcXtjgQO) {
        return addLayUiCode(bdcXtJgFeignService.queryJgByPage(pageable, JSON.toJSONString(bdcXtjgQO)));
    }


    /**
     * 读取EMS委托函及EMS工作人员相关证件信息挂到登记业务附件
     *
     * @param bdcFzjlEmsDTO 发证记录ems信息
     * @return
     */
    @PostMapping("/getEmsFjxx")
    public void getEmsFjxx(@RequestBody BdcFzjlEmsDTO bdcFzjlEmsDTO) {
        if (StringUtils.isAnyBlank(bdcFzjlEmsDTO.getJgid(), bdcFzjlEmsDTO.getGzlslid(), bdcFzjlEmsDTO.getXmid())) {
            throw new MissingArgumentException("读取EMS附件信息，缺少必要参数机构id或gzlslid或xmid");
        }
        String gzlslid = bdcFzjlEmsDTO.getGzlslid();
        String jgid = bdcFzjlEmsDTO.getJgid();
        String xmid = bdcFzjlEmsDTO.getXmid();
        // 查询物流订单号
        String wlddh = queryEmsWlddh(gzlslid);
        // 查询一体化业务文件夹id
        String emswjzxid = queryEmsWjzxid(gzlslid);
        // 查询机构配置中的EMS工作人员附件
        String wjzxid = queryJgWjzxid(jgid);
        // 查询bdc_xm的sply
        Integer sply = queryXmSply(gzlslid);
        // 所有业务更新领证人
        bdcFzjlFeignService.updateFzjl(xmid, bdcFzjlEmsDTO.getSfhb(), bdcFzjlEmsDTO.getBdcFzjlDTO());
        // 审批来源为1，且物流订单号不为空，挂载ems人员信息到一体化业务文件夹
        if (CommonConstantUtils.SPLY_YCSL.equals(sply) && StringUtils.isNotBlank(wlddh)) {
            // ems人员附件和一体化业务文件夹存在
            if (StringUtils.isNotBlank(emswjzxid) && StringUtils.isNotBlank(wjzxid)) {
                List<StorageDto> storageDtos = storageClient.listAllSubsetStorages(wjzxid, "", 1, null, null, null);
                for (StorageDto storageDto : storageDtos) {
                    boolean checkExist = storageClient.checkExist( "", gzlslid, emswjzxid,
                            storageDto.getName(),  "",  null);
                    // 判断一体化业务文件夹是否存在EMS工作人员文件
                    if (!checkExist){
                        MultipartDto multipartDto = storageClient.download(storageDto.getId());
                        multipartDto.setNodeId(emswjzxid);
                        StorageDto dto = storageClient.multipartUpload(multipartDto);
                        LOGGER.info("{}附件信息{}，下载地址{}", dto.getName(), JSON.toJSONString(dto), dto.getDownUrl());
                    }
                }
            }
        } else {
            // 其他情况，新建文件夹，上传ems人员信息
            if (StringUtils.isNotBlank(wjzxid)) {
                String nodeid = "";
                // 通过文件夹名称获取附件
                List<StorageDto> NodeStorageDtos=storageClient.listAllRootStorages("",gzlslid,"",emsryWjjmc,
                        1,0,null,"");
                // 判断附件是否存在
                if(CollectionUtils.isNotEmpty(NodeStorageDtos)){
                    // 获取附件id
                    nodeid = NodeStorageDtos.get(0).getId();
                }
                // 获取机构中的ems人员附件
                List<StorageDto> storageDtos = storageClient.listAllSubsetStorages(wjzxid, "", 1, null, null, null);
                for (StorageDto storageDto : storageDtos) {
                    boolean checkExist = storageClient.checkExist("", gzlslid, nodeid,
                            storageDto.getName(),  "",null);
                    // 判断文件夹是否存在EMS工作人员文件
                    if (!checkExist){
                        String fileName = storageDto.getName();
                        String fileSuffix = fileName.substring(fileName.lastIndexOf(CommonConstantUtils.ZF_YW_JH));
                        String pdffjmc = fileName.substring(0, fileName.lastIndexOf(CommonConstantUtils.ZF_YW_JH));
                        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", pdffjmc, emsryWjjmc, fileSuffix);
                        bdcPdfDTO.setPdfUrl(storageDto.getDownUrl());
                        try {
                            bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                        } catch (IOException e) {
                            LOGGER.error("ems人员上传大云失败，工作流实例id:{},下载url地址:{} ，异常信息：{}", gzlslid, storageDto.getDownUrl(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * 查询物流订单号
     *
     * @param gzlslid 工作流实例id
     * @return 物流订单号
     */
    private String queryEmsWlddh(String gzlslid) {
        String wlddh = "";
        // 查询邮寄信息
        List<BdcSlYjxxDTO> bdcSlYjxxDTOS = bdcSlYjxxFeginService.queryBdcSlYjxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlYjxxDTOS)) {
            // EMS订单号
            wlddh = bdcSlYjxxDTOS.get(0).getWlddh();
        }
        return wlddh;
    }


    /**
     * 查询一体化业务文件夹的文件中心id
     *
     * @param gzlslid 工作流实例id
     * @return 文件中心id
     */
    private String queryEmsWjzxid(String gzlslid) {
        String emswjzxid = "";
        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
        bdcSlSjclQO.setGzlslid(gzlslid);
        bdcSlSjclQO.setClmc(yjxxWjjmc);
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
        if (CollectionUtils.isNotEmpty(sjclList)) {
            // 业务附件，一体化业务文件夹的nodeId
            emswjzxid = sjclList.get(0).getWjzxid();
        }
        return emswjzxid;
    }

    /**
     * 查询机构的文件中心id
     *
     * @param jgid 机构id
     * @return 文件中心id
     */
    private String queryJgWjzxid(String jgid) {
        String wjzxid = "";
        BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
        bdcXtJgDO.setJgid(jgid);
        List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.queryBdcXtJg(bdcXtJgDO);
        if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
            // 机构配置，ems人员的附件nodeId
            wjzxid = bdcXtJgDOList.get(0).getWjzxid();
        }
        return wjzxid;
    }

    /**
     * 判断是否启用电子证照银行
     * @return
     */
    private boolean sfdzzzyh(BdcSlSfxxDO bdcSlSfxxDO) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcSlSfxxDO.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> qlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(qlrDOS)){
            for (BdcQlrDO bdcQlrDO : qlrDOS) {
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(),null);
                if (bdcXtJgDO != null && StringUtils.equals(bdcXtJgDO.getSfjrhlw(), "1")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查询bdc_xm的审批来源
     *
     * @param gzlslid 工作流实例id
     * @return 审批来源
     */
    private Integer queryXmSply(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("读取EMS附件信息,不动产项目不存在，工作流实例id为：" + gzlslid);
        }
        return bdcXmDOList.get(0).getSply();
    }



}
