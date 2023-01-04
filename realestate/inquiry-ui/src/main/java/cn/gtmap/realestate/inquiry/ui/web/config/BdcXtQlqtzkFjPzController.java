package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtQlqtzkFjPzQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtQlqtzkFjPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcZsmbpzFileDTO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 权利其他状况、附记配置
 */
@RestController
@RequestMapping("/rest/v1.0/qlqtzkFj")
public class BdcXtQlqtzkFjPzController extends BaseController {
    @Autowired
    BdcXtQlqtzkFjPzFeignService bdcXtQlqtzkFjPzFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
     * @param pageable
     * @param dcXtQlqtzkFjPzQO
     * @return Object
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况、附记
     */
    @GetMapping("/page")
    public Object listBdcXtQlqtzkFjPz(@LayuiPageable Pageable pageable, BdcXtQlqtzkFjPzQO dcXtQlqtzkFjPzQO) {
        String qlqtzkFjParamJson = JSON.toJSONString(dcXtQlqtzkFjPzQO);
        Page<BdcXtQlqtzkFjPzDO> bdcXtMryjDOPage = bdcXtQlqtzkFjPzFeignService.listBdcXtQlqtzkFjPz(pageable, qlqtzkFjParamJson);
        //SQL相关数据加密
        if(bdcXtMryjDOPage.hasContent()) {
            for (BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO : bdcXtMryjDOPage.getContent()) {
                enerypt(bdcXtQlqtzkFjPzDO);
            }
        }
        return super.addLayUiCode(bdcXtMryjDOPage);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 新增的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增不动产默认意见
     */
    @PutMapping
    public Object saveBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        //对获取数据做解密操作
        decode(bdcXtQlqtzkFjPzDO);
        return bdcXtQlqtzkFjPzFeignService.saveBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改不动产默认意见
     */
    @PostMapping
    public Object updateBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        //对获取数据做解密操作
        decode(bdcXtQlqtzkFjPzDO);
        return bdcXtQlqtzkFjPzFeignService.updateBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除默认意见配置
     */
    @DeleteMapping
    public Object deleteBdcXtQlqtzkFjPz(@RequestBody List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDOList) {
        //删除的数据进行解密
        if(CollectionUtils.isNotEmpty(bdcXtQlqtzkFjPzDOList)){
            for (BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO : bdcXtQlqtzkFjPzDOList) {
                decode(bdcXtQlqtzkFjPzDO);
            }
        }

        return bdcXtQlqtzkFjPzFeignService.deleteBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDOList);
    }

    /**
     * @param
     * @return Map<String, List < Map>>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利类型，登记小类字典数据
     */
    @GetMapping("/zd")
    public Map<String, List<Map>> listZd() {
        List<Map> qllx = bdcZdFeignService.queryBdcZd("qllx");
        List<Map> djxl = bdcZdFeignService.queryBdcZd("djxl");
        Map<String, List<Map>> result = Maps.newHashMap();
        result.put("qllx", qllx);
        result.put("djxl", djxl);
        return result;
    }

    /**
     * @param sqls       sql
     * @param checkValue 测试数据
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证sql
     */
    @GetMapping("/check")
    public Object check(@RequestParam(name = "sqls") String sqls, @RequestParam(name = "checkValue") String checkValue) {
        //sql数据解密
        if(StringUtils.isNotBlank(sqls)){
            sqls = StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(sqls)));
        }
       try {
           sqls = URLDecoder.decode(sqls, "utf-8");
       }catch (Exception e){

       }
        if (StringUtils.isBlank(sqls) || StringUtils.isBlank(checkValue)) {
            throw new AppException("验证的sql或者测试数据不能为空");
        }
        List<String> checkValueList = Lists.newArrayList(checkValue.split(";|；"));
        Map<String, String> params = Maps.newHashMap();
        checkValueList.forEach(check -> {
            String[] checks = check.split("=");
            params.put(StringUtils.deleteWhitespace(checks[0]), StringUtils.deleteWhitespace(checks[1]));
        });
        sqls = StringToolUtils.replaceBracket(sqls);
        LOGGER.info("验证sql:{}",sqls);
        List<String> sqlList = Lists.newArrayList(sqls.split(";|；"));
        return bdcXtQlqtzkFjPzFeignService.checkBdcXtQlqtzkFjPz(sqlList, params);
    }

    /**
     * 导出权力其他状况附记
     *
     * @param response qlzkfjDTO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:28 2020/8/12
     */
    @PostMapping("/export")
    public void exportQlqtzkFj(HttpServletResponse response, @ModelAttribute BdcZsmbpzFileDTO qlzkfjDTO) {
        if (null == qlzkfjDTO || StringUtils.isBlank(qlzkfjDTO.getFiledata())) {
            throw new AppException("未定义要导出的权利其他状况附记模板数据");
        }
        List<BdcXtQlqtzkFjPzDO> qlqtzkFjPzDOList = JSON.parseArray(qlzkfjDTO.getFiledata(), BdcXtQlqtzkFjPzDO.class);
        if (CollectionUtils.isEmpty(qlqtzkFjPzDOList)) {
            throw new AppException("未定义要导出的权利其他状况附记模板数据");
        }
        //导出数据中SQL解密
        if(CollectionUtils.isNotEmpty(qlqtzkFjPzDOList)){
            for (BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO : qlqtzkFjPzDOList) {
                decode(bdcXtQlqtzkFjPzDO);
            }
        }
        OutputStream out;
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = URLEncoder.encode("不动产权利其他状况附记导出文件" + DateUtils.formateTimeYmdhms(new Date()) + ".txt", "utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            bos.write(JSON.toJSONString(qlqtzkFjPzDOList).getBytes("UTF-8"));
            bos.flush();

        } catch (Exception e) {
            throw new AppException("权利其他状况附记模板导出出错，请重试！");

        }
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/8/13
     * @description 导入数据验证
     */
    @PostMapping("/importYz")
    public Map importQlqtzkfjYz(HttpServletRequest request, MultipartFile file) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        StringBuffer buff = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("权利其他状况附记模板配置导入失败，原因：{}", e.getMessage());
            throw new AppException("权利其他状况附记模板导入失败!");
        }
        String qlqtzkfjListJson = buff.toString();
        if (null == qlqtzkfjListJson) {
            throw new AppException("权利其他状况附记模板导入文件内容为空!");
        }
        List<BdcXtQlqtzkFjPzDO> qlqtzkFjPzDOList = JSON.parseArray(qlqtzkfjListJson, BdcXtQlqtzkFjPzDO.class);
        if (CollectionUtils.isEmpty(qlqtzkFjPzDOList)) {
            throw new AppException("模板导出错，导入文件内容为空！");
        }
        Map res = new HashMap(3);
        String djxlmessage = "";
        //数据中sql加密
        if(CollectionUtils.isNotEmpty(qlqtzkFjPzDOList)){
            for (BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO : qlqtzkFjPzDOList) {
                enerypt(bdcXtQlqtzkFjPzDO);
            }
        }

        res.put("savedata", qlqtzkFjPzDOList);
        //开始判断是否有重复配置
        for (BdcXtQlqtzkFjPzDO qlqtzkFjPzDO : qlqtzkFjPzDOList) {
            BdcXtQlqtzkFjPzDO qlqtzkFjPzDO1 = new BdcXtQlqtzkFjPzDO();
            qlqtzkFjPzDO1.setDjxl(qlqtzkFjPzDO.getDjxl());
            List<BdcXtQlqtzkFjPzDO> cfPzList = bdcXtQlqtzkFjPzFeignService.listQlqtzkFjpz(qlqtzkFjPzDO1);
            if (CollectionUtils.isNotEmpty(cfPzList)) {
                djxlmessage += StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(cfPzList.get(0).getDjxl()),
                        zdMap.get(CommonConstantUtils.DJXL_ZD)) + "，";
//                djxlmessage += cfPzList.get(0).getDjxl();

            }

        }
        LOGGER.info("权利附记重复的有{}", djxlmessage);
        if (StringUtils.isNotBlank(djxlmessage)) {
            res.put("code", 2);
            res.put("message", djxlmessage);
            return res;
        }
        return res;
    }

    /**
     * 导入模板数据
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 18:30 2020/8/12
     */
    @PostMapping("/import")
    public Map importQlqtzkfj(@RequestBody List<BdcXtQlqtzkFjPzDO> qlqtzkFjPzDOList) {
        Integer count = 0;
        if (CollectionUtils.isEmpty(qlqtzkFjPzDOList)) {
            throw new AppException("导入数据为空！");
        }
        //数据解密
        if(CollectionUtils.isNotEmpty(qlqtzkFjPzDOList)){
            for (BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO : qlqtzkFjPzDOList) {
                decode(bdcXtQlqtzkFjPzDO);
            }
        }
        for (BdcXtQlqtzkFjPzDO qlfjDO : qlqtzkFjPzDOList) {
            BdcXtQlqtzkFjPzDO qlqtzkFjPzDO = new BdcXtQlqtzkFjPzDO();
            qlqtzkFjPzDO.setDjxl(qlfjDO.getDjxl());
            List<BdcXtQlqtzkFjPzDO> qlqtzkFjPzDOList1 = bdcXtQlqtzkFjPzFeignService.listQlqtzkFjpz(qlqtzkFjPzDO);
            if (CollectionUtils.isNotEmpty(qlqtzkFjPzDOList1)) {
                qlfjDO.setPzid(qlqtzkFjPzDOList1.get(0).getPzid());
                qlfjDO.setPzrq(new Date());
                count += bdcXtQlqtzkFjPzFeignService.updateBdcXtQlqtzkFjPz(qlfjDO);
            } else {
                qlfjDO.setPzrq(new Date());
                count += bdcXtQlqtzkFjPzFeignService.saveBdcXtQlqtzkFjPz(qlfjDO);
            }
        }
        Map data = new HashMap(2);
        data.put("code", "1");
        data.put("count", count);
        return data;
    }

    /**
     * 对数据中sql进行解密
     * @param bdcXtQlqtzkFjPzDO
     * @return
     */
    public static BdcXtQlqtzkFjPzDO decode(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO){
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getQlqtzksjy())) {
            bdcXtQlqtzkFjPzDO.setQlqtzksjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getQlqtzksjy()))));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getFjsjy())) {
            bdcXtQlqtzkFjPzDO.setFjsjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getFjsjy()))));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjsjy())) {
            bdcXtQlqtzkFjPzDO.setZxqlfjsjy(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getZxqlfjsjy()))));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getQlqtzkmb())) {
            bdcXtQlqtzkFjPzDO.setQlqtzkmb(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getQlqtzkmb()))));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getFjmb())) {
            bdcXtQlqtzkFjPzDO.setFjmb(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getFjmb()))));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjmb())) {
            bdcXtQlqtzkFjPzDO.setZxqlfjmb(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtQlqtzkFjPzDO.getZxqlfjmb()))));
        }
    return bdcXtQlqtzkFjPzDO;
    }
    /**
     * 对数据中sql进行加密
     * @param bdcXtQlqtzkFjPzDO
     * @return
     */
    public static BdcXtQlqtzkFjPzDO enerypt(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO){
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getQlqtzksjy())) {
            bdcXtQlqtzkFjPzDO.setQlqtzksjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getQlqtzksjy().getBytes())));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getFjsjy())) {
            bdcXtQlqtzkFjPzDO.setFjsjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getFjsjy().getBytes())));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjsjy())) {
            bdcXtQlqtzkFjPzDO.setZxqlfjsjy(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getZxqlfjsjy().getBytes())));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getQlqtzkmb())) {
            bdcXtQlqtzkFjPzDO.setQlqtzkmb(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getQlqtzkmb().getBytes())));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getFjmb())) {
            bdcXtQlqtzkFjPzDO.setFjmb(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getFjmb().getBytes())));
        }
        if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjmb())) {
            bdcXtQlqtzkFjPzDO.setZxqlfjmb(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcXtQlqtzkFjPzDO.getZxqlfjmb().getBytes())));
        }
        return bdcXtQlqtzkFjPzDO;
    }


}
