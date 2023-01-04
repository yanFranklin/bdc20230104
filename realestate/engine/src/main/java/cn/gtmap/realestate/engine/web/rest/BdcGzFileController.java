package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzFileRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
import cn.gtmap.realestate.engine.core.service.BdcGzFileService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/7 18:35
 * @description 规则导出成文件
 */
@RestController
@Api(tags = "规则导出文件服务接口")
public class BdcGzFileController implements BdcGzFileRestService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    BdcGzZgzService bdcGzZgzService;

    @Autowired
    BdcGzZhGzService bdcGzZhGzService;

    @Autowired
    BdcGzFileService bdcGzFileService;


    /**
     * 根据组合规则id查找数据
     *
     * @param zhid
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据组合规则查数据", notes = "根据组合规则查数据")
    public void searchData(HttpServletResponse response, String zhid) {
        BdcGzZhgzDTO bdcGzZhgzDTO = new BdcGzZhgzDTO();
        bdcGzZhgzDTO = bdcGzFileService.queryZhgzDTO(zhid);
        String jsonString = JSONObject.toJSONString(bdcGzZhgzDTO);
        exportTxt(response, jsonString, bdcGzZhgzDTO.getZhmc());
    }

    /**
     * 查询
     *
     * @param zhid zhid
     * @return BdcGzZhgzDTO BdcGzZhgzDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据组合规则id查询DTO", notes = "根据组合规则id查询DTO")
    @ApiImplicitParam(name = "zhid", value = "组合id", required = true, dataType = "String", paramType = "query")
    public BdcGzZhgzDTO queryZhgzDTO(String zhid) {
        return bdcGzFileService.queryZhgzDTO(zhid);
    }


    /**
     * 导出文件
     *
     * @param text text
     * @param mc   mc
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成txt", notes = "生成txt")
    @ApiImplicitParam(name = "text", value = "字符串", required = true, dataType = "String", paramType = "query")
    public void exportTxt(HttpServletResponse response, String text, String mc) {
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        String fileName = "";
        try {
            fileName = java.net.URLEncoder.encode(mc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("文档编码处理异常,fileName:{}", fileName, e);
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream(outStr)) {
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
        } catch (Exception e) {
            throw new AppException("导出文件文件出错！");
        }

    }

    /**
     * 防止文件名中文乱码
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }


}
