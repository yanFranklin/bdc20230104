package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.engine.*;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityExistsException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzCheckRelatedQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzFileFeginService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzGxFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZgzFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcGzZhgzYzDTO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description 不动产组合规则ui服务
 */
@RestController
@RequestMapping("bdcZhGz")
public class BdcGzZhGzController extends BaseController {
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private BdcGzGxFeignService bdcGzGxFeignService;
    @Autowired
    BdcGzFileFeginService bdcGzFileFeginService;
    @Autowired
    ProcessDefinitionClient processDefinitionClient;
    @Autowired
    BdcGzZgzFeignService bdcGzZgzFeignService;

    /**
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 分页查询不动产规则限制验证例外信息
     */
    @RequestMapping(value = "/listBdcZhGzByPageJson")
    public Object listBdcZhGzByPageJson(@LayuiPageable Pageable pageable, String zhbs, String zhmc, String zhsm, String lcmc, String glgx, String gzid) {
        return addLayUiCode(bdcGzZhGzFeignService.listBdcGzZhgzPage(zhmc, zhbs, zhsm, lcmc, glgx,gzid, pageable));
    }

    /**
     * @param  zhidList 组合id集合
     * @param  gzid 子规则id
     * @return int 新增关联关系记录数
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     * @description 子规则关联批量组合规则
     */
    @PostMapping("/glgx")
    public int glgx(@RequestBody String[] zhidList, @RequestParam(value ="gzid", required = true) String gzid){
        return bdcGzZhGzFeignService.glgx(zhidList,gzid);
    }

    /**
     * @param zhid
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据组合id删除组合规则信息
     */
    @RequestMapping(value = "/deleteBdcGzZhGzByZhid")
    public Map deleteBdcGzZhGzByZhid(String zhid) {
        Map map = Maps.newHashMap();
        if (StringUtils.isNotEmpty(zhid)) {
            List<BdcGzGxDO> bdcGzGxDOList = bdcGzGxFeignService.listBdcGzGxByZhid(zhid);
            if (CollectionUtils.isNotEmpty(bdcGzGxDOList)) {
                for (BdcGzGxDO bdcGzGxDO : bdcGzGxDOList) {
                    bdcGzGxFeignService.delBdcGzGxByGxid(bdcGzGxDO.getGxid());
                }
            }
            bdcGzZhGzFeignService.delBdcGzZhGz(zhid);
        }
        map.put("msg", "success");
        return map;
    }

    /**
     * @param zhid
     * @return bdcGzGxListJson
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过zhid获取所有相关的组合规则的组合关系
     */
    @RequestMapping(value = "/listBdcGzGx")
    public String listBdcGzGx(String zhid) {
        List<BdcGzGxDO> bdcGzGxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(zhid)) {
            bdcGzGxDOList = bdcGzGxFeignService.listBdcGzGxByZhid(zhid);
        }
        return JSON.toJSONString(bdcGzGxDOList);
    }

    /**
     * @param zhid
     * @return bdcGzZhgzDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据zhid获取组合规则信息
     */
    @GetMapping(value = "/queryBdcGzZhgz")
    public BdcGzZhgzDO queryBdcGzZhgz(@RequestParam("zhid") String zhid) {
        BdcGzZhGzQO bdcGzZhGzQO = new BdcGzZhGzQO();
        BdcGzZhgzDO bdcGzZhgzDO = new BdcGzZhgzDO();
        bdcGzZhGzQO.setZhid(zhid);
        List<BdcGzZhgzDO> bdcGzZhgzDOList = bdcGzZhGzFeignService.queryBdcGzZhGzDOList(bdcGzZhGzQO);
        if (CollectionUtils.isNotEmpty(bdcGzZhgzDOList) && bdcGzZhgzDOList.get(0) != null) {
            bdcGzZhgzDO = bdcGzZhgzDOList.get(0);
        }
        return bdcGzZhgzDO;
    }

    /**
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新保存不动产组合规则
     */
    @PostMapping(value = "/saveOrUpdatZhGz")
    public Map saveOrUpdatZhGz(@RequestBody BdcGzSaveZhgzDTO bdcGzSaveZhgzDTO) {
        BdcGzZhgzDO bdcGzZhgzDO = bdcGzSaveZhgzDTO;

        String gzid = bdcGzSaveZhgzDTO.getGzid();

        if(null == bdcGzZhgzDO){
            throw new EntityExistsException("保存内容为空！");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(new Date());
        Date pzrq = null;
        try {
            pzrq = formatter.parse(date);
        } catch (ParseException e) {
            LOGGER.error("配置日期格式转换错误");
        }

        bdcGzZhgzDO.setPzrq(pzrq);
        //判断规则标识是否唯一
        if (StringUtils.isNotBlank(bdcGzZhgzDO.getZhbs())) {
            int count = bdcGzZhGzFeignService.countZhbs(bdcGzZhgzDO);
            if (count > 0) {
                throw new EntityExistsException("规则标识已经存在，请重新输入！");
            }
        }

        if (StringUtils.isNotEmpty(bdcGzZhgzDO.getZhid())) {
            bdcGzZhGzFeignService.updateBdcGzZhGz(bdcGzZhgzDO);
        } else {
            bdcGzZhgzDO = bdcGzZhGzFeignService.insertBdcGzZhGz(bdcGzZhgzDO);
        }
        BdcGzGxDO bdcGzGxDO = new BdcGzGxDO();
        if (bdcGzZhgzDO != null && StringUtils.isNotBlank(bdcGzZhgzDO.getZhid())) {
            bdcGzGxFeignService.delBdcGzGxByZhid(bdcGzZhgzDO.getZhid());
            bdcGzGxDO.setZhid(bdcGzZhgzDO.getZhid());
            if (StringUtils.isNotBlank(gzid)) {
                List<String> gzidList = Arrays.asList(gzid.split(","));
                // 去重
                Set<String> gzidSet = new HashSet<>(gzidList.size());
                for (String gzids : gzidList) {
                    gzidSet.add(gzids);
                }
                for (String gzids : gzidSet) {
                    bdcGzGxDO.setGzid(gzids);
                    bdcGzGxFeignService.insertBdcGzGx(bdcGzGxDO);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("zhid", bdcGzZhgzDO.getZhid());
        return result;
    }


    /**
     * 根据zhid导出组合规则数据
     *
     * @param zhid zhid
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */

    @RequestMapping(value = "/exportFile")
    public void exportFile(HttpServletResponse response, String zhid) {
        BdcGzZhgzDTO bdcGzZhgzDTO = new BdcGzZhgzDTO();
        if (StringUtils.isNotBlank(zhid)) {
            bdcGzZhgzDTO = bdcGzFileFeginService.queryZhgzDTO(zhid);
        }

        String jsonString = JSONObject.toJSONString(bdcGzZhgzDTO);
        try {
            exportTxt(response, jsonString, bdcGzZhgzDTO.getZhmc());
        } catch (Exception e) {
            throw new AppException("导出文件失败！");
        }
    }

    /**
     * @param: [gzid]
     * @return: java.lang.String
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据规则id复制一条当前的规则信息
     * @date: 2019/9/12
     */
    @PostMapping(value = "/copyBdcZhgz/{zhid}")
    public String copyBdcGzZgzByGzid(@PathVariable("zhid") String zhid){
        return this.bdcGzZhGzFeignService.copyBdcGzZhGZ(zhid);
    }

    /**
     * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param: MultipartFile file
     * @return: BdcCommonResponse
     * @description 导入组合规则
     */
    @PostMapping(value = "/import")
    public BdcCommonResponse importZhgz(MultipartFile file){
        StringBuffer buffer = new StringBuffer();

        // 获取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;//读取一行文本；
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("组合规则导入失败，原因：{}", e.getMessage());
            throw new AppException("组合规则导入失败!");
        }

        String zhgzJSON = buffer.toString();
        if(StringUtils.isBlank(zhgzJSON)){
            throw new AppException("组合规则导入出错，组合规则文件内容为空!");
        }

        return this.bdcGzZhGzFeignService.importZhgz(zhgzJSON);
    }

    /**
     * 导出文件
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */

    public void exportTxt(HttpServletResponse response, String text, String mc) throws IOException {
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
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcGzQzyzYzDTO} 验证结果
     * @description  验证强制验证项
     *
     *  强制验证项组合规则标识：流程标识_QZYZ
     */
    @GetMapping("/qzyz/check")
    public BdcGzQzyzYzDTO checkQzyz(){
        return bdcGzZhGzFeignService.checkQzyz();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  导出强制验证项配置
     */
    @RequestMapping(value = "/exportQzyz")
    public void exportQzyz(HttpServletResponse response) {
        BdcGzQzyzDTO bdcGzQzyzDTO = bdcGzZhGzFeignService.listBdcGzQzyzDTO();
        if(null == bdcGzQzyzDTO){
            throw new AppException("不存在对应强制验证规则，无法导出！");
        }

        String json = JSONObject.toJSONString(bdcGzQzyzDTO);
        try {
            exportTxt(response, json, CommonConstantUtils.GZ_QZYZ_FILE_NAME);
        } catch (Exception e) {
            throw new AppException("导出文件失败！");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取所有最新版本流程信息
     */
    @GetMapping("/process/definitions")
    public List<ProcessDefData> listProcessDefinitions(){
        return processDefinitionClient.getAllProcessDefData();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zhid  组合规则ID
     * @description  获取组合规则关联的所有子规则数据流参数信息
     */
    @GetMapping("/zgz/cs")
    public List<BdcGzSjlCsDO> listBdcZhgzCs(@RequestParam("zhid")String zhid){
        // 获取组合规则关联子规则集合
        List<BdcGzZgzDTO> bdcGzZgzDTOList = bdcGzZgzFeignService.queryBdcGzZgzDTOList(zhid);
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList)){
            return Collections.emptyList();
        }

        // 遍历获取对应数据流参数信息
        List<BdcGzSjlCsDO> bdcGzSjlCsDOList = new ArrayList<>(10);
        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOList){
            List<BdcGzSjlDTO> bdcGzSjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
            if(CollectionUtils.isEmpty(bdcGzSjlDTOList)){
                continue;
            }

            for(BdcGzSjlDTO bdcGzSjlDTO : bdcGzSjlDTOList){
                bdcGzSjlCsDOList.addAll(bdcGzSjlDTO.getBdcGzSjlCsDOList());
            }
        }

        return bdcGzSjlCsDOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhgzYzDTO 验证参数信息
     * @description  组合规则动态验证
     */
    @PostMapping("/check")
    public BdcGzZhgzTsxxDTO checkZhgz(@RequestBody BdcGzZhgzYzDTO bdcGzZhgzYzDTO){
        if(null == bdcGzZhgzYzDTO || StringUtils.isBlank(bdcGzZhgzYzDTO.getZhbs())
                || CollectionUtils.isEmpty(bdcGzZhgzYzDTO.getBdcGzSjlCsDOList())){
            throw new AppException("组合规则动态验证异常，原因：未定义组合标识或没有参数信息！");
        }

        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs(bdcGzZhgzYzDTO.getZhbs());

        Map<String, Object> paramMap = new HashMap<>(5);
        bdcGzZhgzYzDTO.getBdcGzSjlCsDOList().stream().forEach(csDO -> paramMap.put(csDO.getSjlcsmc(), csDO.getSjlcsz()));
        bdcGzYzQO.setParamMap(paramMap);

        return bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcGzZhGzQO  组合规则参数信息
     * @description 分页获取组合规则关联的子规则信息
     */
    @GetMapping("/zgz")
    public Object listBdcZszm(@LayuiPageable Pageable pageable, BdcGzZhGzQO bdcGzZhGzQO) {
        Page<BdcGzZgzDO> bdcGzZgzDOPage = bdcGzZhGzFeignService.listBdcZhgzZgzPage(pageable, JSON.toJSONString(bdcGzZhGzQO));
        return super.addLayUiCode(bdcGzZgzDOPage);
    }

    @ApiOperation( value = "校验规则是否关联流程", notes = "校验规则是否关联流程", httpMethod = "GET" )
    @PostMapping("/checkRelated")
    public BdcGzZhGzCheckRelatedDTO checkRelated(@RequestBody BdcGzZhGzCheckRelatedQO bdcGzZhGzCheckRelatedQO){
        BdcGzZhGzCheckRelatedDTO bdcGzZhGzCheckRelatedDTO = bdcGzZhGzFeignService.checkRelated(bdcGzZhGzCheckRelatedQO);
        return bdcGzZhGzCheckRelatedDTO;
    }
}
