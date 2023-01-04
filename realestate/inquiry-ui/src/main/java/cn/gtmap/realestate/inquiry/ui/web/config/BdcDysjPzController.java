package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcZsmbpzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcDysjPzDateQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYzSqlFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcDysjPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDysjPzVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcZsmbpzFileDTO;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description 打印数据源配置
 */
@RestController
@RequestMapping("/rest/v1.0/dysjpz")
public class BdcDysjPzController extends BaseController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDysjPzController.class);

    @Autowired
    StorageClientMatcher storageClient;
    /**
     * PDF打印服务
     */
    @Autowired
    PdfController pdfController;
    /**
     * 打印数据源配置服务
     */
    @Autowired
    BdcDysjPzFeignService bdcDysjPzFeignService;
    /**
     * 受理系统验证sql服务
     */
    @Autowired
    BdcSlYzSqlFeignService bdcSlYzSqlFeignService;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcZdFeignService bdcZdFeignService;
    /**
     * 打印模板地址
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * @param pageable 分页参数
     * @param bdcDysjPzDateQO 打印数据时间查询参数
     * @return 打印数据源配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/dysj/page")
    public Object listBdcDysjPz(@LayuiPageable Pageable pageable, BdcDysjPzDateQO bdcDysjPzDateQO) {
        Page<BdcDysjPzDO> bdcDysjPzDOPage = bdcDysjPzFeignService.listBdcDysjPz(pageable, JSON.toJSONString(bdcDysjPzDateQO));
        //对数据中相关SQL进行加密
        if(bdcDysjPzDOPage.hasContent()){
            for (BdcDysjPzDO bdcDysjPzDO : bdcDysjPzDOPage.getContent()) {
                if (StringUtils.isNotBlank(bdcDysjPzDO.getDysjy())) {
                    bdcDysjPzDO.setDysjy(Base64Utils.encodeByteToBase64Str(StringToolUtils.replaceBracket(bdcDysjPzDO.getDysjy()).getBytes()));
                }
            }
        }
        return super.addLayUiCode(bdcDysjPzDOPage);
    }

    /**
     * @param id 主键ID
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/dysj")
    public Object queryBdcDysjPz(@RequestParam(name = "id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AppException("缺少打印数据配置主键！");
        }
        return bdcDysjPzFeignService.queryBdcDysjPzDO(id);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/dysjzb")
    public Object queryBdcDysjZbPz(@RequestParam(name = "id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AppException("缺少打印数据子表配置主键！");
        }
        return bdcDysjPzFeignService.queryBdcDysjZbPzDO(id);
    }

    /**
     * @param bdcDysjPzDO
     * @return 新增的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增打印数据源配置
     */
    @PutMapping("/dysj")
    public Object saveBdcDysjPz(@RequestBody BdcDysjPzDO bdcDysjPzDO) {
        if (bdcDysjPzDO == null) {
            throw new AppException("保存的打印数据配置不能为空！");
        }
        if (StringUtils.isNotBlank(bdcDysjPzDO.getDylx())) {
            bdcDysjPzDO.setDylx(StringToolUtils.replaceBracket(bdcDysjPzDO.getDylx()));
        }
        if (StringUtils.isNotBlank(bdcDysjPzDO.getDysjy())) {
            bdcDysjPzDO.setDysjy(StringToolUtils.replaceBracket(bdcDysjPzDO.getDysjy()));
        }
        return bdcDysjPzFeignService.saveBdcDysjPzDO(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据源配置
     */
    @PostMapping("/dysj")
    public Object updateBdcDysjPz(@RequestBody BdcDysjPzDO bdcDysjPzDO) {
        if (bdcDysjPzDO == null) {
            throw new AppException("保存的数据不能为空！");
        }
        if (StringUtils.isNotBlank(bdcDysjPzDO.getDylx())) {
            bdcDysjPzDO.setDylx(StringToolUtils.replaceBracket(bdcDysjPzDO.getDylx()));
        }
        if (StringUtils.isNotBlank(bdcDysjPzDO.getDysjy())) {
            bdcDysjPzDO.setDysjy(StringToolUtils.replaceBracket(bdcDysjPzDO.getDysjy()));
        }
        return bdcDysjPzFeignService.updateBdcDysjPzDO(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印数据源配置
     */
    @DeleteMapping("/dysj")
    public Object deleteBdcDysjPz(@RequestBody List<BdcDysjPzDO> bdcDysjPzDOList) {
        if(CollectionUtils.isNotEmpty(bdcDysjPzDOList)){
            for (BdcDysjPzDO bdcDysjPzDO : bdcDysjPzDOList) {
                bdcDysjPzDO.setDysjy(new String(Base64Utils.decodeBase64StrToByte(StringToolUtils.replaceBracket(bdcDysjPzDO.getDysjy()))));
            }
        }
        return bdcDysjPzFeignService.deleteBdcDysjPzDO(bdcDysjPzDOList);
    }

    //子表
    /**
     * @param pageable
     * @param bdcDysjZbPzDO
     * @return 打印数据源子表配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/dysjzb/page")
    public Object listBdcDysjZbPz(@LayuiPageable Pageable pageable, BdcDysjZbPzDO bdcDysjZbPzDO) {
        String dysjZbParamJson = JSON.toJSONString(bdcDysjZbPzDO);
        Page<BdcDysjZbPzDO> bdcDysjZbPzDOS = bdcDysjPzFeignService.listBdcDysjZbPz(pageable, dysjZbParamJson);
        return super.addLayUiCode(bdcDysjZbPzDOS);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return 新增的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增打印数据源子表配置
     */
    @PutMapping("/dysjzb")
    public Object saveBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null) {
            throw new AppException("保存的打印数据源子表不能为空！");
        }
        if (StringUtils.isNotBlank(bdcDysjZbPzDO.getDyzbsjy())) {
            bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(bdcDysjZbPzDO.getDyzbsjy()));
        }
        return bdcDysjPzFeignService.saveBdcDysjZbPzDO(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据源子表配置
     */
    @PostMapping("/dysjzb")
    public Object updateBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null) {
            throw new AppException("保存的打印数据源子表数据不能为空！");
        }
        if (StringUtils.isNotBlank(bdcDysjZbPzDO.getDyzbsjy())) {
            bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(bdcDysjZbPzDO.getDyzbsjy()));
        }
        return bdcDysjPzFeignService.updateBdcDysjZbPzDO(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印子表数据源子表配置
     */
    @DeleteMapping("/dysjzb")
    public Object deleteBdcDysjZbPz(@RequestBody List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        return bdcDysjPzFeignService.deleteBdcDysjZbPzDO(bdcDysjZbPzDOList);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询打印子表唯一值（detailId）是否在该打印类型下已经有值
     */
    @PostMapping("/dysjzb/checkZbId")
    public boolean checkZbId(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null) {
            throw new AppException("获取子表配置数量的参数不能为空！");
        }
        return bdcDysjPzFeignService.countBdcDysjZbPz(bdcDysjZbPzDO) >= 1;
    }


    /**
     * @param sqls       sql
     * @param checkValue 测试数据
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证受理库sql
     */
    @GetMapping("/check")
    public Object check(@RequestParam(name = "sqls") String sqls, @RequestParam(name = "checkValue") String checkValue) {
        if (StringUtils.isBlank(sqls) || StringUtils.isBlank(checkValue)) {
            throw new AppException("验证的sql或者测试数据不能为空");
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("sqls", StringToolUtils.replaceBracket(sqls));
        params.put("cs", StringToolUtils.replaceBracket(checkValue));
        return bdcSlYzSqlFeignService.checkSql(params);
    }

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDTO 打印配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型查询主表和子表的配置信息，如果打印类型在库里由重复，则会给出报错信息
     */

    @GetMapping("/pzxx/{dylx}")
    public BdcDysjPzDTO getPzxx(@PathVariable(value = "dylx") String dylx) {
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("缺失查询参数：打印类型（dylx）！");
        }
        //SQL相关数据加密
        BdcDysjPzDTO bdcDysjPzDTO = bdcDysjPzFeignService.getPzxx(dylx);
        if(StringUtils.isNotBlank(bdcDysjPzDTO.getDysjy())) {
            //1.1 主表sql加密
            bdcDysjPzDTO.setDysjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDysjPzDTO.getDysjy().getBytes())));
        }
        //1.2子表sql加密
        if(CollectionUtils.isNotEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())){
            for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjPzDTO.getBdcDysjZbPzDOList()) {
                bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDysjZbPzDO.getDyzbsjy().getBytes())));
            }
        }
        return bdcDysjPzDTO;
    }

    /**
     * @param bdcDysjPzDTO 打印配置信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新打印配置信息
     */
    @PostMapping("/pzxx")
    public BdcDysjPzDTO saveOrUpdate(@RequestBody BdcDysjPzDTO bdcDysjPzDTO) {
        //对SQL进行解密
        if(StringUtils.isNotBlank(bdcDysjPzDTO.getDysjy())) {
            bdcDysjPzDTO.setDysjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjPzDTO.getDysjy()))));
        }
        if(CollectionUtils.isNotEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())){
            for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjPzDTO.getBdcDysjZbPzDOList()) {
                bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjZbPzDO.getDyzbsjy()))));
            }
        }
        BdcDysjPzDTO bdcDysjPzDTO1 = bdcDysjPzFeignService.saveOrUpdatePzxx(bdcDysjPzDTO);
        //对传输到前端的数据加密
        if(StringUtils.isNotBlank(bdcDysjPzDTO1.getDysjy())) {
            bdcDysjPzDTO1.setDysjy(Base64Utils.encodeByteToBase64Str(bdcDysjPzDTO1.getDysjy().getBytes()));
        }
        for (BdcDysjZbPzDO bdcDysjZbPzDO1 : bdcDysjPzDTO1.getBdcDysjZbPzDOList()) {
            bdcDysjZbPzDO1.setDyzbsjy(Base64Utils.encodeByteToBase64Str(bdcDysjZbPzDO1.getDyzbsjy().getBytes()));
        }
        return bdcDysjPzDTO1;
    }

    /**
     * @param bdcDysjPzVO 打印配置信息
     * @return object
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @PostMapping("/pzxx/jgjy")
    public Object jypzxx(@RequestBody BdcDysjPzVO bdcDysjPzVO) {
        //数据SQL进行解密
        if(StringUtils.isNotBlank(bdcDysjPzVO.getBdcDysjPzDO().getDysjy())){
            bdcDysjPzVO.getBdcDysjPzDO().setDysjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjPzVO.getBdcDysjPzDO().getDysjy()))));
        }
        for(int i=0;i<bdcDysjPzVO.getBdcDysjZbPzDOList().size();i++){
            bdcDysjPzVO.getBdcDysjZbPzDOList().get(i).setDyzbsjy(StringToolUtils.replaceBracket(new String(
                    Base64Utils.decodeBase64StrToByte(bdcDysjPzVO.getBdcDysjZbPzDOList().get(i).getDyzbsjy()))));
        }
        return bdcPrintFeignService.jypzxx(bdcDysjPzVO);
    }

    /**
     * @param xml xml信息
     * @return String  redisKey 保存到redis中的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将xml信息保存到redis中，设置有效期为60秒
     */
    @PostMapping("/pzxx/xml")
    public String sendXmlToRedis(@RequestBody String xml) {
        return bdcDysjPzFeignService.sendXmlToRedis(xml);
    }

    /**
     * @return String printPath 打印模板路径
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印摹本路径配置
     */
    @PostMapping("/pzxx/file")
    public StorageDto filePath(@RequestBody MultipartFile file) {
        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = StringUtils.split(fileName, '.')[1];

            byte[] fileBytes = file.getBytes();
            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID_PRINT, Constants.SPACEID_PRINT_MODEL, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                LOGGER.warn("上传信息：{}", JSONObject.toJSONString(storageDto));
            }
        } catch (IOException e) {
            LOGGER.error("文件解析异常！{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("文件操作异常{}", e.getMessage());
        }
        return storageDto;
    }

    /**
     * @param currentUserName 当前用户名
     * @param fileByte        文件字节
     * @param fileName        文件名称
     * @return MultipartDto 大云上传参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 组织大云上传参数
     */
    private MultipartDto getUploadParamDto(String currentUserName, StorageDto storageDto, byte[] fileByte, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(storageDto.getClientId());
        multipartDto.setData(fileByte);
        if (fileByte != null) {
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(storageDto.getName());
        }
        return multipartDto;
    }

    /**
     * @param redisKey redis健
     * @return String xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 从redis中获取保存的xml信息
     */
    @GetMapping("/print/xml/{redisKey}")
    public String getXmlFromRedis(@PathVariable(value = "redisKey") String redisKey) {
        return bdcDysjPzFeignService.getXmlFromRedis(redisKey);
    }

    /**
     * @return String printPath 打印模板路径
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印摹本路径配置
     */
    @GetMapping("/print/model/path")
    public String getPrintPath() {
        return printPath;
    }

    /**
     * @param redisKey 缓存参数的Redis键名
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 打印数据源PDF预览数据获取服务
     */
    @GetMapping("/pdf/{redisKey}")
    public void getPdfViewData(HttpServletResponse response, @PathVariable("redisKey") String redisKey) {
        if (StringUtils.isBlank(redisKey)) {
            throw new AppException("PDF打印预览失败，原因：未获取到参数信息！");
        }

        // 获取缓存的参数等信息
        String data = this.getXmlFromRedis(redisKey);
        if (StringUtils.isBlank(data)) {
            throw new AppException("PDF打印预览失败，原因：未获取到参数信息！");
        }
        Map<String, String> dataMap = JSON.parseObject(data, Map.class);

        // 获取打印XML数据
        String xmlData = this.getPrintData(dataMap);
        if (StringUtils.isBlank(xmlData)) {
            throw new MissingArgumentException("证明单导出pdf中止，原因：未获取到数据信息！");
        }

        // 调用PDF打印服务
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(dataMap.get("pdfpath"));
        /// 文件名称临时用打印类型代替下
        pdfExportDTO.setFileName(dataMap.get("dylx"));
        pdfExportDTO.setXmlData(xmlData);
        pdfController.exportPdf(response, pdfExportDTO);
    }

    /**
     * @param dataMap 测试打印信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取PDF要展示的打印数据
     */
    private String getPrintData(Map<String, String> dataMap) {
        Map<String, List<Map>> params = new HashMap<>(1);
        String paramsStr = JSON.toJSONString(dataMap.get("params"));

        // 根据JSON数组与否动态获取参数
        List<Map> paramMapList = new ArrayList<>(1);
        if (JSON.isValidObject(paramsStr)) {
            Map paramsMap = JSON.parseObject(paramsStr, Map.class);
            paramMapList.add(paramsMap);
        } else {
            paramMapList = JSON.parseArray(paramsStr, Map.class);
        }

        params.put(dataMap.get("dylx"), paramMapList);
        return bdcPrintFeignService.print(params);
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/8/13
     * @description 导出打印配置数据
     */
    @PostMapping("/export")
    public void exportDypz(HttpServletResponse response, @ModelAttribute BdcZsmbpzFileDTO dypzDTO) {
        if (null == dypzDTO || StringUtils.isBlank(dypzDTO.getFiledata())) {
            throw new AppException("未定义要导出的打印数据源模板数据");
        }
        List<BdcDysjPzDTO> dysjPzDTOList = JSON.parseArray(dypzDTO.getFiledata(), BdcDysjPzDTO.class);
        //前台获取数据中SQL解密
        if(CollectionUtils.isNotEmpty(dysjPzDTOList)){
            for (BdcDysjPzDTO bdcDysjPzDTO : dysjPzDTOList) {
                bdcDysjPzDTO.setDysjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjPzDTO.getDysjy()))));
                if(CollectionUtils.isNotEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())) {
                    for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjPzDTO.getBdcDysjZbPzDOList()) {
                        bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjZbPzDO.getDyzbsjy()))));
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(dysjPzDTOList)) {
            throw new AppException("未定义要导出的打印数据源模板数据");
        }
        List<BdcDysjPzDTO> dysjPzDTOList1 = new ArrayList<>();
        //根据打印主表，查出所有数据
        for (BdcDysjPzDTO dypzDTO1 : dysjPzDTOList) {
            BdcDysjPzDTO dysjPzDTO = bdcDysjPzFeignService.getPzxx(dypzDTO1.getDylx());
            dysjPzDTOList1.add(dysjPzDTO);
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = URLEncoder.encode("不动产权利打印数据源配置导出文件" + DateUtils.formateTimeYmdhms(new Date()) + ".txt", "utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            bos.write(JSON.toJSONString(dysjPzDTOList1).getBytes("UTF-8"));
            bos.flush();

        } catch (Exception e) {
            throw new AppException("权利其他状况附记模板导出出错，请重试！");

        }
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/8/13
     * @description 导入时验证是否存在重复
     */
    @PostMapping("/importYz")
    public Map importDypzYz(HttpServletRequest request, MultipartFile file) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        StringBuffer buff = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("打印数据源配置导入失败，原因：{}", e.getMessage());
            throw new AppException("打印数据源配置模板导入失败!");
        }
        String dypzListJson = buff.toString();
        if (null == dypzListJson) {
            throw new AppException("打印数据源配置模板导入文件内容为空!");
        }
        List<BdcDysjPzDTO> dysjPzDTOList = JSON.parseArray(dypzListJson, BdcDysjPzDTO.class);
        if (CollectionUtils.isEmpty(dysjPzDTOList)) {
            throw new AppException("模板导出错，导入文件内容为空！");
        }
        Map res = new HashMap(3);
        String dylxMessage = "";
        //对数据中SQL加密
        if(CollectionUtils.isNotEmpty(dysjPzDTOList)){
            for (BdcDysjPzDTO bdcDysjPzDTO : dysjPzDTOList) {
                bdcDysjPzDTO.setDysjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDysjPzDTO.getDysjy().getBytes())));
                if(CollectionUtils.isNotEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())){
                    for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjPzDTO.getBdcDysjZbPzDOList()) {
                        bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDysjZbPzDO.getDyzbsjy().getBytes())));
                    }
                }
            }
        }
        res.put("savedata", dysjPzDTOList);
        for (BdcDysjPzDTO dypzDTO : dysjPzDTOList) {
            BdcDysjPzDTO dysjPzDTO1 = bdcDysjPzFeignService.getPzxx(dypzDTO.getDylx());
            if (null != dysjPzDTO1 && StringUtils.isNotBlank(dysjPzDTO1.getDylx())) {
                dylxMessage += dysjPzDTO1.getDylx() + "，";
            }
        }
        res.put("savedata", dysjPzDTOList);

        LOGGER.info("打印类型重复的有{}", dylxMessage);
        if (StringUtils.isNotBlank(dylxMessage)) {
            res.put("code", 2);
            res.put("message", dylxMessage);
            return res;
        }
        return res;
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/8/13
     * @description 导入打印配置
     */
    @PostMapping("/import")
    public Map importDypz(@RequestBody List<BdcDysjPzDTO> bdcDysjPzDTOList) {
        Integer count = 0;
        if (CollectionUtils.isEmpty(bdcDysjPzDTOList)) {
            throw new AppException("导入数据为空！");
        }
        if(CollectionUtils.isNotEmpty(bdcDysjPzDTOList)){
            for (BdcDysjPzDTO bdcDysjPzDTO : bdcDysjPzDTOList) {
                //对SQL进行解密
                if(StringUtils.isNotBlank(bdcDysjPzDTO.getDysjy())) {
                    bdcDysjPzDTO.setDysjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjPzDTO.getDysjy()))));
                }
                if(CollectionUtils.isNotEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())){
                    for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjPzDTO.getBdcDysjZbPzDOList()) {
                        bdcDysjZbPzDO.setDyzbsjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcDysjZbPzDO.getDyzbsjy()))));
                    }
                }
            }
        }
        for (BdcDysjPzDTO dypzDTO : bdcDysjPzDTOList) {
            BdcDysjPzDTO dysjPzDTO = bdcDysjPzFeignService.getPzxx(dypzDTO.getDylx());
            if (null != dysjPzDTO && StringUtils.isNotBlank(dypzDTO.getDylx())) {
                dypzDTO.setId(dysjPzDTO.getId());
                bdcDysjPzFeignService.saveOrUpdatePzxx(dypzDTO);
            } else {
                bdcDysjPzFeignService.saveOrUpdatePzxx(dysjPzDTO);
            }
        }

        Map data = new HashMap(2);
        data.put("code", "1");
        data.put("count", bdcDysjPzDTOList);

        return data;
    }

    /**
     * 查看打印类型是否重复
     *
     * @param dylx 打印类型
     * @return
     */
    @GetMapping("/pzxx/checkDylx")
    public int checkDylx(@RequestParam(value = "dylx") String dylx) {
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("缺失查询参数：打印类型（dylx）！");
        }
        return bdcDysjPzFeignService.checkDylx(dylx);
    }

}
