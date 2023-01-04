package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.FccxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhjgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZhjgQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhjgFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/07/10
 * @description 综合监管
 */
@RestController
@RequestMapping(value = "/zhjg")
public class BdcZhjgController extends BaseController {

    @Autowired
    private BdcZhjgFeignService bdcZhjgFeignService;

    /**
     * 分页查询房产信息
     * @param pageable
     * @param bdcZhjgQO
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object listZhjgByPage(@LayuiPageable Pageable pageable, BdcZhjgQO bdcZhjgQO) {

        if(StringUtils.isNotBlank(bdcZhjgQO.getType()) && bdcZhjgQO.getType().equals("export")) {// 导出全部
            List list = bdcZhjgFeignService.listBdcZhjg(JSON.toJSONString(bdcZhjgQO));
            return list;
        }else{
            return super.addLayUiCode(bdcZhjgFeignService.listBdcZhjgByPage(pageable, JSON.toJSONString(bdcZhjgQO)));
        }
    }

}
