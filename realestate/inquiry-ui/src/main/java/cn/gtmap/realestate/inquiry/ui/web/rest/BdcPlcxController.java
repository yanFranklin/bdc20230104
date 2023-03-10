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
     * @description Excel???????????????????????????
     */
    @Value("${excel.qxdcts:1000}")
    private Integer dcts;

    /**
     * ????????????????????????
     * @param pageDTO
     * @param bdcPlcxQO
     * @return
     *
     * ?????????????????????PageDTO?????????Pageable??????????????????
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
     * @description ?????? ??????????????????
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
     * @description ?????? ???????????????????????????????????????Excel
     */
    @PostMapping(value = "/yancheng/export")
    public void exportExcel(BdcPlcxQO bdcPlcxQO, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExcelExportDTO excelExportDTO) {
        String ip = IpUtils.getIpFromRequest(request);
        bdcPlcxQO.setClientIp(ip);
        List<BdcYcPlcxDTO> resultList = bdcPlcxFeignService.listBdcYcPlcx(JSON.toJSONString(bdcPlcxQO));
        //?????????????????????????????????
        resultList.forEach(bdcYcPlcxDTO -> {
            if(StringUtils.equals(bdcYcPlcxDTO.getQszt(),"1")){
                bdcYcPlcxDTO.setQszt("??????");
            }else {
                bdcYcPlcxDTO.setQszt("??????");
            }
        });
        excelExportDTO.setData(JSON.toJSONString(resultList));
        excelController.exportExcel(response,excelExportDTO);
    }


    /**
     * ?????????????????????????????????
     *
     * @param httpServletRequest ????????????
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
            // ????????????????????????
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // ????????????????????????????????????Excel????????????Workbook??????
                Workbook workbook = Workbook.getWorkbook(fileIn);

                Sheet dataSheet = workbook.getSheet(0);
                Field[] fieldExportDataList = FccxDO.class.getDeclaredFields();

                // ??????Excel
                for (int i = 1; i < dataSheet.getRows(); i++) {
                    Object dataObject = FccxDO.class.newInstance();
                    for (int j = 0; j < fieldExportDataList.length; j++) {
                        fieldExportDataList[j].setAccessible(true);
                        String contentData = dataSheet.getCell(j, i).getContents();
                        // ?????????????????????
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

                    // ??????excel?????????????????????
                    if("any".equals(parameter)) {
                        // ????????????40886??????????????????????????????????????????????????????????????????????????????
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
                // ???????????????10000??????????????????????????????
                if(CollectionUtils.size(dataList) > Constants.PLCX_MAX_SIZE) {
                    return super.addLayUiErrorCode("?????????????????????" + Constants.PLCX_MAX_SIZE + "????????????????????????");
                }
            }
            return dataList;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("????????????");
        } finally {
            IOUtils.closeQuietly(fileIn);
        }
    }


    /**
     * ????????????????????????
     * @param pageDTO
     * @param bdcBaxxCxQO
     * @return
     *
     * ?????????????????????????????????post???????????????get?????????????????????
     * ?????????????????????PageDTO?????????Pageable??????????????????
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
     * @description ???????????????????????????,excel???????????????????????????
     * @return
     */
    @GetMapping("/pz")
    public Integer getPlcxPz(){
        return dcts;
    }

}
