package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZgzFeignService;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzGxRestService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcGzZgzFileDTO;
import cn.gtmap.realestate.inquiry.ui.util.EngineUiConstant;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 17:04
 * @description 子规则台账
 */
@RestController
@RequestMapping("/zgz")
public class BdcGzZgzController extends BaseController {

    @Autowired
    BdcGzZgzFeignService bdcGzZgzFeignService;

    @Autowired
    BdcGzGxRestService bdcGzGxFeignService;

    /**
     * 查询子规则列表
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param pageable pageable
     * @param bdcGzZgzQO bdcGzZgzDO
     * @return Object Object
     */
    @GetMapping("/page")
    public Object listBdcZgz(@LayuiPageable Pageable pageable, BdcGzZgzQO bdcGzZgzQO){
        String zgzParamJson = JSON.toJSONString(bdcGzZgzQO);
        Page<BdcGzZgzDO> bdcGzZgzPage = bdcGzZgzFeignService.listBdcGzZgzPage(pageable,zgzParamJson);
        return addLayUiCode(bdcGzZgzPage);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则DTO
     * @return {String} 子规则主键ID
     * @description  保存不动产子规则信息
     */
    @PostMapping(value = "/saveBdcGzZgz")
    public String saveBdcGzZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO){
        //解密
        if(CollectionUtils.isNotEmpty(bdcGzZgzDTO.getBdcGzSjlDTOList())){
            for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzZgzDTO.getBdcGzSjlDTOList()) {
                bdcGzSjlDTO.setSjnr(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcGzSjlDTO.getSjnr()))));
            }
        }

        return bdcGzZgzFeignService.saveBdcGzZgz(bdcGzZgzDTO);
    }

    /**
     * 删除子规则
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcGzZgzDOS
     */
    @DeleteMapping
    public void deteleBdcZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOS){
        bdcGzZgzFeignService.deleteBdcGzZgz(bdcGzZgzDOS);
    }

    /**
     * 根据规则id查询子规则DTO数据
     *
     * @param gzid 规则id
     * @return BdcGzZgzDTO BdcGzZgzDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/queryBdcGzZgzDTOByGzid")
    public BdcGzZgzDTO queryBdcGzZgzDTOByGzid(String gzid) {
        BdcGzZgzDTO bdcGzZgzDTO = bdcGzZgzFeignService.queryBdcGzZgzDTO(gzid);
        //对bdcGzZgzDTO中涉及的SQL进行加密
        if(CollectionUtils.isNotEmpty(bdcGzZgzDTO.getBdcGzSjlDTOList())){
            for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzZgzDTO.getBdcGzSjlDTOList()) {
                bdcGzSjlDTO.setSjnr(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcGzSjlDTO.getSjnr().getBytes())));
            }
        }
        return bdcGzZgzDTO;
    }

    /**
     * @param: [gzid]
     * @return: java.lang.String
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据规则id复制一条当前的规则信息
     * @date: 2019/9/12
     */
    @PostMapping(value = "/copyBdcGzZgz/{gzid}")
    public String copyBdcGzZgzByGzid(@PathVariable("gzid") String gzid){
        return bdcGzZgzFeignService.copyBdcGzZgz(gzid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzCodeDTO  校验代码信息
     * @description  校验子规则动态代码
     */
    @PostMapping("/code/check")
    public String codeCheck(@RequestBody BdcGzCodeDTO bdcGzCodeDTO){
        return bdcGzZgzFeignService.codeCheck(bdcGzCodeDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDO  子规则信息
     * @description  格式化Java代码
     */
    @PostMapping("/code/format")
    public String codeFormat(@RequestBody BdcGzZgzDO bdcGzZgzDO) throws FormatterException {
        if(null == bdcGzZgzDO || StringUtils.isBlank(bdcGzZgzDO.getJydm())){
            return "";
        }
        return new Formatter().formatSource(bdcGzZgzDO.getJydm());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取初始化规则动态代码内容
     */
    @GetMapping("/code/init")
    public String getInitCode() throws FormatterException {
        return new Formatter().formatSource(EngineUiConstant.ENGINE_INIT_CODE);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  子规则测试验证
     */
    @PostMapping("/check")
    public List<BdcGzZgzTsxxDTO> checkZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO){
        if(null == bdcGzZgzDTO){
            throw new AppException("验证子规则测试数据为空！");
        }
        //解密
        if(CollectionUtils.isNotEmpty(bdcGzZgzDTO.getBdcGzSjlDTOList())){
            for (BdcGzSjlDTO bdcGzSjlDTO : bdcGzZgzDTO.getBdcGzSjlDTOList()) {
                bdcGzSjlDTO.setSjnr(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(bdcGzSjlDTO.getSjnr()))));
            }
        }
        return bdcGzZgzFeignService.getBdcZgzCheckResult(new BdcGzZgzsDTO(bdcGzZgzDTO));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  子规则导出
     */
    @PostMapping("/export")
    public void exportBdcZgz(HttpServletResponse response, @ModelAttribute BdcGzZgzFileDTO bdcGzZgzFileDTO){
        if(null == bdcGzZgzFileDTO || StringUtils.isBlank(bdcGzZgzFileDTO.getFiledata())){
            throw new AppException("未定义要导出的子规则数据！");
        }

        List<BdcGzZgzDO> bdcGzZgzDOList = JSON.parseArray(bdcGzZgzFileDTO.getFiledata(), BdcGzZgzDO.class);
        if(CollectionUtils.isEmpty(bdcGzZgzDOList)){
            throw new AppException("未定义要导出的子规则数据！");
        }
        // 获取子规则信息，包括对应的数据流等
        List<BdcGzZgzDTO> bdcGzZgzDTOList = new ArrayList<>(10);
        bdcGzZgzDOList.forEach(bdcGzZgzDO -> {
            BdcGzZgzDTO bdcGzZgzDTO = bdcGzZgzFeignService.queryBdcGzZgzDTO(bdcGzZgzDO.getGzid());
            if(null != bdcGzZgzDO){
                bdcGzZgzDTOList.add(bdcGzZgzDTO);
            }
        });

        // 执行导出
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName =   URLEncoder.encode("不动产子规则导出文件" + DateUtils.formateTimeYmdhms(new Date()) + ".txt", "utf-8");;
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");

            bos.write(JSON.toJSONString(bdcGzZgzDTOList).getBytes("UTF-8"));
            bos.flush();
        } catch (Exception e) {
            throw new AppException("子规则导出出错，请重试！");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  子规则导入
     */
    @PostMapping("/import")
    public Map importBdcZgz(HttpServletRequest request, MultipartFile file){
        StringBuffer buffer = new StringBuffer();

        // 获取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("子规则导入失败，原因：{}", e.getMessage());
            throw new AppException("子规则导入失败!");
        }

        String zgzListJSON = buffer.toString();
        if(StringUtils.isBlank(zgzListJSON)){
            throw new AppException("子规则导入出错，子规则文件内容为空!");
        }

        // 转换对象
        List<BdcGzZgzDTO> bdcGzZgzDTOList = JSON.parseArray(zgzListJSON, BdcGzZgzDTO.class);
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
            throw new AppException("子规则导入出错，子规则文件内容为空!");
        }

        // 重新生成规则ID，保存子规则
        List<String> zgzIdList = new ArrayList<>(bdcGzZgzDTOList.size());
        bdcGzZgzDTOList.stream().forEach(bdcGzZgzDTO -> {
            String zgzid = UUIDGenerator.generate16();
            bdcGzZgzDTO.setGzid(zgzid);
            bdcGzZgzDTO.setPzrq(new Date());

            List<BdcGzBdsTsxxDO> bdsList = bdcGzZgzDTO.getBdcGzBdsTsxxDOList();
            if(CollectionUtils.isNotEmpty(bdsList)){
                bdsList.forEach(bdsTsxxDO -> bdsTsxxDO.setGzid(zgzid));
            }

            List<BdcGzSjlDTO> sjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
            if(CollectionUtils.isNotEmpty(sjlDTOList)){
                sjlDTOList.forEach(sjlDTO -> sjlDTO.setGzid(zgzid));
            }

            String zgzId = bdcGzZgzFeignService.saveBdcGzZgz(bdcGzZgzDTO);
            zgzIdList.add(zgzId);
        });

        Map res = new HashMap(1);
        res.put("code", 0);
        res.put("content", zgzIdList);
        return res;
    }

    /**
     * @param gzid
     * @return bdcGzGxDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 通过gzid获取所有组合关系
     */
    @RequestMapping(value = "/listBdcGzGx")
    public String listBdcGzGx(String gzid) {
        List<BdcGzGxDO> bdcGzGxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzid)) {
            bdcGzGxDOList = bdcGzGxFeignService.listBdcGzGxByGzid(gzid);
        }
        return JSON.toJSONString(bdcGzGxDOList);
    }
}
