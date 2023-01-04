package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.FccxDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcBaxxCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcPlcxFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import cn.gtmap.realestate.inquiry.ui.util.IpUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.*;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/07/10
 * @description
 */
@RestController
@RequestMapping(value = "/plcx")
public class BdcPlcxController extends BaseController {

    @Autowired
    private BdcPlcxFeignService bdcPlcxFeignService;

    @Autowired
    private ExcelController excelController;

    /**
     * @description Excel全部导出的导出条数
     */
    @Value("${excel.qxdcts:1000}")
    private Integer dcts;

    /**
     * 分页查询房产信息
     * @param pageDTO
     * @param bdcPlcxQO
     * @return
     *
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object listFcxxByPage(PageDTO pageDTO, BdcPlcxQO bdcPlcxQO, HttpServletRequest request) {
        String ip = IpUtils.getIpFromRequest(request);
        bdcPlcxQO.setClientIp(ip);
        Page<BdcPlcxDTO> bdcPlcxDTOPage = bdcPlcxFeignService.listBdcPlcxByPage(JSONObject.toJSONString(pageDTO), bdcPlcxQO);
        return super.addLayUiCode(bdcPlcxDTOPage);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [pageable, bdcPlcxQO, request]
     * @return java.lang.Object
     * @description 盐城 分页批量查询
     */
    @PostMapping(value = "/yancheng/list")
    @ResponseStatus(HttpStatus.OK)
    public Object listYcFcxxByPage(@LayuiPageable Pageable pageable, BdcPlcxQO bdcPlcxQO, HttpServletRequest request) {
        String ip = IpUtils.getIpFromRequest(request);
        bdcPlcxQO.setClientIp(ip);
        List<BdcYcPlcxDTO> resultList = bdcPlcxFeignService.listBdcYcPlcx(JSON.toJSONString(bdcPlcxQO));
        Page page = PageUtils.listToPage(resultList,pageable);
        return super.addLayUiCode(page);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcPlcxQO, request, response, excelExportDTO]
     * @return void
     * @description 盐城 批量查询数据一次性全部导出Excel
     */
    @PostMapping(value = "/yancheng/export")
    public void exportExcel(BdcPlcxQO bdcPlcxQO, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExcelExportDTO excelExportDTO) {
        String ip = IpUtils.getIpFromRequest(request);
        bdcPlcxQO.setClientIp(ip);
        List<BdcYcPlcxDTO> resultList = bdcPlcxFeignService.listBdcYcPlcx(JSON.toJSONString(bdcPlcxQO));
        //权属状态只有现势和历史
        resultList.forEach(bdcYcPlcxDTO -> {
            if(StringUtils.equals(bdcYcPlcxDTO.getQszt(),"1")){
                bdcYcPlcxDTO.setQszt("现势");
            }else {
                bdcYcPlcxDTO.setQszt("历史");
            }
        });
        excelExportDTO.setData(JSON.toJSONString(resultList));
        excelController.exportExcel(response,excelExportDTO);
    }


    /**
     * 解析房产查询的导入条件
     *
     * @param httpServletRequest 网页请求
     * @return list
     * @date 2019/11/16
     * @author chenyucheng
     */
    @PostMapping("/import/fccx/excel")
    public Object configFccxImportToExcel(MultipartHttpServletRequest httpServletRequest, @RequestParam(name = "parameter", required = false)String parameter) {
        if (httpServletRequest == null) {
            return null;
        }
        InputStream fileIn = null;
        List dataList = new ArrayList();
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);

                Sheet dataSheet = workbook.getSheet(0);
                Field[] fieldExportDataList = FccxDO.class.getDeclaredFields();

                // 遍历Excel
                for (int i = 1; i < dataSheet.getRows(); i++) {
                    Object dataObject = FccxDO.class.newInstance();
                    for (int j = 0; j < fieldExportDataList.length; j++) {
                        fieldExportDataList[j].setAccessible(true);
                        String contentData = dataSheet.getCell(j, i).getContents();
                        // 判断属性的类型
                        if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.String")) {
                            fieldExportDataList[j].set(dataObject, contentData);
                        } else if (StringToolUtils.existItemEquals(fieldExportDataList[j].getType().toString(), "int", "Integer")) {
                            fieldExportDataList[j].set(dataObject, Integer.valueOf(contentData));
                        } else if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.util.Date")) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
                            Date dateData = simpleDateFormat.parse(contentData);
                            fieldExportDataList[j].set(dataObject, dateData);
                        }
                    }

                    boolean isQlrmcNotNull = StringUtils.isNotBlank(((FccxDO) dataObject).getQlrmc());
                    boolean isZjhNotNull = StringUtils.isNotBlank(((FccxDO) dataObject).getZjh());

                    // 防止excel中空白行被解析
                    if("any".equals(parameter)) {
                        // 盐城需求40886：不要求姓名和身份证都必须要有内容，其中一列有值就行
                        if(isQlrmcNotNull || isZjhNotNull){
                            if(!isQlrmcNotNull) {
                                ((FccxDO) dataObject).setQlrmc(BDC_GZ_SJL_RC_MRZ);
                            }
                            if(!isZjhNotNull) {
                                ((FccxDO) dataObject).setZjh(BDC_GZ_SJL_RC_MRZ);
                            }

                            dataList.add(dataObject);
                        }
                    } else {
                        if(isQlrmcNotNull && isZjhNotNull){
                            dataList.add(dataObject);
                        }
                    }
                }
                // 数据行超过10000条时，返回分批次导入
                if(CollectionUtils.size(dataList) > Constants.PLCX_MAX_SIZE) {
                    return super.addLayUiErrorCode("导入数据量超过" + Constants.PLCX_MAX_SIZE + "条，请分批次导入");
                }
            }
            return dataList;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("导入失败");
        } finally {
            IOUtils.closeQuietly(fileIn);
        }
    }


    /**
     * 分页查询备案信息
     * @param pageDTO
     * @param bdcBaxxCxQO
     * @return
     *
     * 分页查询备案信息修改为post请求，避免get请求头过大问题
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping(value = "/baxx/list")
    @ResponseStatus(HttpStatus.OK)
    public Object listBaxxByPage(PageDTO pageDTO, BdcBaxxCxQO bdcBaxxCxQO) {
        if(StringUtils.isBlank(bdcBaxxCxQO.getExport())){
            Page<BdcBaxxPlcxDTO> bdcPlcxDTOPage = bdcPlcxFeignService.listBdcBaxxPlcxByPage(JSON.toJSONString(pageDTO), bdcBaxxCxQO);
            return super.addLayUiCode(bdcPlcxDTOPage);
        }else{
            return bdcPlcxFeignService.listBdcBaxxPlcx(bdcBaxxCxQO);
        }

    }

    /**
     * @description 获取批量查询配置项,excel导出全部的导出条数
     * @return
     */
    @GetMapping("/pz")
    public Integer getPlcxPz(){
        return dcts;
    }

}
