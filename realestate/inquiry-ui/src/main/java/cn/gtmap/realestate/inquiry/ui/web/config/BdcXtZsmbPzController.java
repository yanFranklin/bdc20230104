package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbSqlDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcZsmbpzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsmbPzQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsmbPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcZsmbpzFileDTO;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXtZsmbPzVO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description 业务配置系统：证书模板配置请求处理控制器
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcXtZsmbPzController extends BaseController {
    @Autowired
    private BdcXtZsmbPzFeignService bdcXtZsmbPzFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * @param pageable      分页对象
     * @param bdcXtZsmbPzQO 查询条件
     * @return 证书模板配置
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询证书模板配置数据列表
     */
    @GetMapping("/zsmbpz")
    public Object listBdcXtZsmbPz(@LayuiPageable Pageable pageable, BdcXtZsmbPzQO bdcXtZsmbPzQO) {
        // 查询字典信息
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        // 1、查询证书模板配置信息
        Page<BdcXtZsmbPzDO> bdcXtZsmbPzDOPage = bdcXtZsmbPzFeignService.listBdcXtZsmbPz(pageable, JSON.toJSONString(bdcXtZsmbPzQO));
        // 2、权利类型字典转换
        List<BdcXtZsmbPzVO> bdcXtZsmbPzVOList = new ArrayList<>(bdcXtZsmbPzDOPage.getContent().size());
        for (BdcXtZsmbPzDO zsmbPzDO : bdcXtZsmbPzDOPage.getContent()) {
            BdcXtZsmbPzVO bdcXtZsmbPzVO = new BdcXtZsmbPzVO();
            // 2.1、复制属性值
            BeansResolveUtils.clonePropertiesValue(zsmbPzDO, bdcXtZsmbPzVO);
            // 2.1、字典转换
            bdcXtZsmbPzVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(zsmbPzDO.getQllx(), zdMap.get("qllx")));
            // 2.1、mbsql数据加密
            bdcXtZsmbPzVO.setMbsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(zsmbPzDO.getMbsql().getBytes())));
            bdcXtZsmbPzVOList.add(bdcXtZsmbPzVO);
        }
        // 封装返回分页信息
        Pageable pageRequest = new PageRequest(bdcXtZsmbPzDOPage.getNumber(), bdcXtZsmbPzDOPage.getSize());
        return super.addLayUiCode(new PageImpl(bdcXtZsmbPzVOList, pageRequest, bdcXtZsmbPzDOPage.getTotalElements()));
    }

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {Boolean} 正确：true，不正确：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 校验证书模板配置SQL是否正确
     */
    @PostMapping("/zsmbpz/sql")
    public Boolean checkBdcXtZsmbPzSql(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO) {
        if (StringUtils.isNotBlank(bdcXtZsmbPzDO.getMbsql())) {
            bdcXtZsmbPzDO.setMbsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtZsmbPzDO.getMbsql()))));
        }
        return bdcXtZsmbPzFeignService.checkBdcXtZsmbPzSql(bdcXtZsmbPzDO);
    }
    /**
     * @param bdcXtZsmbSqlDTO 证书模板配置sql实体
     *  @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     *  @description 检验证书模板配置SQL执行结果
     */
    @PostMapping("/zsmbpz/check")
    public List<String>  checkBdcXtZsmppz(@RequestBody BdcXtZsmbSqlDTO bdcXtZsmbSqlDTO) {
        if(StringUtils.isNotBlank(bdcXtZsmbSqlDTO.getSql())){
            bdcXtZsmbSqlDTO.setSql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtZsmbSqlDTO.getSql()))));
        }
        return bdcXtZsmbPzFeignService.checkBdcXtZsmbPzSqlcs(bdcXtZsmbSqlDTO);
    }

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存证书模板配置配置
     */
    @PutMapping("/zsmbpz")
    public int saveBdcXtZsmbPz(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO) {
        if (StringUtils.isNotBlank(bdcXtZsmbPzDO.getMbsql())) {
            bdcXtZsmbPzDO.setMbsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtZsmbPzDO.getMbsql()))));
        }
        return bdcXtZsmbPzFeignService.saveBdcXtZsmbPz(bdcXtZsmbPzDO);
    }

    /**
     * @param bdcXtZsmbPzDOList 证书模板配置实体集合
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 删除证书模板配置
     */
    @DeleteMapping("/zsmbpz")
    public int deleteBdcXtZsmbPz(@RequestBody List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList) {
        //前台获取数据中SQL解密
        if(CollectionUtils.isNotEmpty(bdcXtZsmbPzDOList)){
            for (BdcXtZsmbPzDO bdcXtZsmbPzDO : bdcXtZsmbPzDOList) {
                bdcXtZsmbPzDO.setMbsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcXtZsmbPzDO.getMbsql()))));
            }
        }
        return bdcXtZsmbPzFeignService.deleteBdcXtZsmbPz(bdcXtZsmbPzDOList);
    }

    /**
     * @return 字典
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询字典
     */
    @GetMapping("/zsmbpz/zd")
    public List<Map> listZd() {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        if (MapUtils.isNotEmpty(zdMap)) {
            return (List<Map>) MapUtils.getObject(zdMap, "qllx");
        }
        // sonar要求返回空List
        return Collections.emptyList();
    }

    /**
     * 证书模板配置导出
     *
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:19 2020/8/10
     */
    @PostMapping("zsmbpz/export")
    public void exportBdcZsmbpz(HttpServletResponse response, @ModelAttribute BdcZsmbpzFileDTO zsmbpzFileDTO) {
        if (null == zsmbpzFileDTO || StringUtils.isBlank(zsmbpzFileDTO.getFiledata())) {
            throw new AppException("未定义要导出的证书模板数据！");
        }
        List<BdcZsmbpzDTO> bdcXtZsmbPzDOList = JSON.parseArray(zsmbpzFileDTO.getFiledata(), BdcZsmbpzDTO.class);
        if (CollectionUtils.isEmpty(bdcXtZsmbPzDOList)) {
            throw new AppException("未定义要导出的证书模板数据");
        }
        //对前台获得数据中SQL进行解密
        if(CollectionUtils.isNotEmpty(bdcXtZsmbPzDOList)){
            for (BdcZsmbpzDTO bdcZsmbpzDTO : bdcXtZsmbPzDOList) {
                bdcZsmbpzDTO.setMbsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcZsmbpzDTO.getMbsql()))));
            }
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
            String fileName = URLEncoder.encode("不动产证书模板导出文件" + DateUtils.formateTimeYmdhms(new Date()) + ".txt", "utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");

            bos.write(JSON.toJSONString(bdcXtZsmbPzDOList).getBytes("UTF-8"));
            bos.flush();

        } catch (IOException e) {
            throw new AppException("证书模板导出出错，请重试！");
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 16:35 2020/8/10
     */
    @PostMapping("/zsmbpz/importYz")
    public Map importYz(HttpServletRequest request, MultipartFile file) {
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("证书模板配置导入失败，原因：{}", e.getMessage());
            throw new AppException("证书模板配置导入失败!");
        }
        String zsmbpzListJSON = buffer.toString();
        if (StringUtils.isBlank(zsmbpzListJSON)) {
            throw new AppException("证书模板导出错，导入文件内容为空！");
        }
        List<BdcZsmbpzDTO> zsmbpzDTOList = JSON.parseArray(zsmbpzListJSON, BdcZsmbpzDTO.class);
        if (CollectionUtils.isEmpty(zsmbpzDTOList)) {
            throw new AppException("证书模板导出错，导入文件内容为空！");
        }
        Map res = new HashMap(3);
        String qllxmessage = "";
        //对数据中SQL加密
        if(CollectionUtils.isNotEmpty(zsmbpzDTOList)){
            for (BdcZsmbpzDTO bdcZsmbpzDTO : zsmbpzDTOList) {
                bdcZsmbpzDTO.setMbsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcZsmbpzDTO.getMbsql().getBytes())));
            }
        }
        res.put("savedata", zsmbpzDTOList);

        //开始判断是否有重复配置
        for (BdcZsmbpzDTO zsmbDTO : zsmbpzDTOList) {
            BdcXtZsmbPzDO bdcXtZsmbPzDO = bdcXtZsmbPzFeignService.queryBdcXtZsmbPzByQllx(zsmbDTO.getQllx());
            if (null != bdcXtZsmbPzDO) {
                qllxmessage += bdcXtZsmbPzDO.getQllx().toString() + "，";
            }
        }
        LOGGER.info("重复的有{}", qllxmessage);
        if (StringUtils.isNotBlank(qllxmessage)) {
            res.put("code", 2);
            res.put("message", qllxmessage);
            return res;
        }

        return res;
    }

    /**
     * 开始数据导入
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:29 2020/8/11
     */
    @PostMapping("/zsmbpz/import")
    public Map importZsmb(@RequestBody List<BdcZsmbpzDTO> zsmbpzDTOList) {
        Integer count = 0;
        if (CollectionUtils.isEmpty(zsmbpzDTOList)) {
            throw new AppException("导入数据为空！");
        }
        //前台获取数据进行解密
        if(CollectionUtils.isNotEmpty(zsmbpzDTOList)){
            for (BdcZsmbpzDTO bdcZsmbpzDTO : zsmbpzDTOList) {
                bdcZsmbpzDTO.setMbsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcZsmbpzDTO.getMbsql()))));
            }
        }
        for (BdcXtZsmbPzDO bdczsmbpzDo : zsmbpzDTOList) {
            BdcXtZsmbPzDO zsmbPzDO = bdcXtZsmbPzFeignService.queryBdcXtZsmbPzByQllx(bdczsmbpzDo.getQllx());
            if (null != zsmbPzDO) {
                bdczsmbpzDo.setZsmbid(zsmbPzDO.getZsmbid());
                bdczsmbpzDo.setPzrq(new Date());
                count += bdcXtZsmbPzFeignService.saveBdcXtZsmbPz(bdczsmbpzDo);
            } else {
                bdczsmbpzDo.setPzrq(new Date());
                count += bdcXtZsmbPzFeignService.saveBdcXtZsmbPz(bdczsmbpzDo);
            }
        }
        Map data = new HashMap(2);
        data.put("code", "1");
        data.put("count", count);
        return data;
    }

}