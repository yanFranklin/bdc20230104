package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcTsywPzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcTsywPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description 特殊业务配置
 */
@RestController
@RequestMapping("/rest/v1.0/tsywpz")
public class BdcTsywPzController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcTsywPzController.class);
    @Autowired
    BdcTsywPzFeignService bdcTsywPzFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  配置说明
     */
    public static final String XZ_PZSM = "系统导入,请补充配置说明";

    /**
     * @param pageable 分页对象
     * @param bdcTsywPzQO 查询对象
     * @return 特殊业务配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 特殊业务配置分页
     */
    @GetMapping("/page")
    public Object listBdcTsywPzByPage(@LayuiPageable Pageable pageable, BdcTsywPzQO bdcTsywPzQO) {
        String bdcTsywPzQOStr = JSON.toJSONString(bdcTsywPzQO);
        Page<BdcTsywPzDO> bdcTsywPzDOPage = bdcTsywPzFeignService.listBdcTsywPzByPage(pageable, bdcTsywPzQOStr);
        return super.addLayUiCode(bdcTsywPzDOPage);
    }

    /**
     * @param pzid 配置主键
     * @return 特殊业务配置对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取特殊业务配置
     */
    @GetMapping("")
    public Object queryBdcTsywPz(@RequestParam(name = "pzid")String pzid) {
        if(StringUtils.isBlank(pzid)){
            throw new AppException("缺少特殊业务配置主键！");
        }
        return bdcTsywPzFeignService.queryBdcTsywPzById(pzid);
    }

    /**
     * @param bdcTsywPzDO 更新特殊业务配置信息
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键更新特殊业务配置信息
     */
    @PutMapping("/update")
    public Object updateBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO) {
        if (bdcTsywPzDO == null) {
            throw new AppException("保存的数据不能为空！");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(userDto != null) {
            bdcTsywPzDO.setPzxgrid(userDto.getId());
            bdcTsywPzDO.setPzxgr(userDto.getAlias());
        }
        bdcTsywPzDO.setPzxgsj(new Date());
        return bdcTsywPzFeignService.updateBdcTsywPz(bdcTsywPzDO);
    }

    /**
     * @param bdcTsywPzDOList 更新特殊业务配置信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键更新特殊业务配置信息
     */
    @PutMapping("/update/pl")
    public void updateBdcTsywPz(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList) {
        if (CollectionUtils.isEmpty(bdcTsywPzDOList)) {
            throw new AppException("保存的数据不能为空！");
        }
        for(BdcTsywPzDO bdcTsywPzDO:bdcTsywPzDOList) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (userDto != null) {
                bdcTsywPzDO.setPzxgrid(userDto.getId());
                bdcTsywPzDO.setPzxgr(userDto.getAlias());
            }
            bdcTsywPzDO.setPzxgsj(new Date());
            bdcTsywPzFeignService.updateBdcTsywPz(bdcTsywPzDO);
        }
    }

    /**
     * @param bdcTsywPzDO 特殊业务配置信息
     * @return 特殊业务配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增特殊业务配置信息
     */
    @PostMapping("/insert")
    public Object insertBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO) {
        if (bdcTsywPzDO == null) {
            throw new AppException("保存的数据不能为空！");
        }
        return bdcTsywPzFeignService.insertBdcTsywPz(bdcTsywPzDO);
    }

    @PostMapping("/zdyzdx")
    public Map<String, Object> listZdyzdx(@RequestParam(value="zdyzdxbs") String zdyzdxbs) {
        if (StringUtils.isBlank(zdyzdxbs)) {
            throw new MissingArgumentException("获取自定义字典表的字典项标识不能为空！");
        }
        Map<String, Object> zdyzdxMap =new HashMap<>();
        BdcTsywZdyzdxDO bdcTsywZdyzdxDO =new BdcTsywZdyzdxDO();
        bdcTsywZdyzdxDO.setZdyzdxbs(zdyzdxbs);
        List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList = bdcTsywPzFeignService.listZdyzdx(bdcTsywZdyzdxDO);
        zdyzdxMap.put(zdyzdxbs, bdcTsywZdyzdxDOList);
        return zdyzdxMap;
    }

    /**
     * 校验并获取自定义字典项内容
     * <p>1、通过字典项标识获取自定义字典项配置信息
     * 2、获取所有使用了<code>zdyzdxbs</code>的配置内容，存在其他配置项使用时，无法编辑字典项内容
     * @param zdyzdxbs
     * @param pzmc
     * @return
     */
    @GetMapping("/zdyzdx/check")
    public Map<String, Object> checkAndGetZdyzdx(@RequestParam(value="zdyzdxbs") String zdyzdxbs,
                                                 @RequestParam(value="pzmc")String pzmc) {
        if (StringUtils.isBlank(zdyzdxbs)) {
            throw new MissingArgumentException("获取自定义字典表的字典项标识不能为空！");
        }
        // 通过自定义字典项标识，获取字典项内容
        Map<String, Object> zdyzdxMap =new HashMap<>();
        BdcTsywZdyzdxDO bdcTsywZdyzdxDO =new BdcTsywZdyzdxDO();
        bdcTsywZdyzdxDO.setZdyzdxbs(zdyzdxbs);
        zdyzdxMap.put(zdyzdxbs, bdcTsywPzFeignService.listZdyzdx(bdcTsywZdyzdxDO));
        // 校验是否存在其他配置内容，使用当前字典项标识。存在其他使用则无法进行编辑
        BdcTsywPzDO bdcTsywPzDO = new BdcTsywPzDO();
        bdcTsywPzDO.setPzmc(pzmc);
        bdcTsywPzDO.setPzzzdxbs(zdyzdxbs);
        List<BdcTsywPzDO> otherTsywpz = bdcTsywPzFeignService.listOtherTsywPzByZdxbs(bdcTsywPzDO);
        zdyzdxMap.put("readOnly", CollectionUtils.isNotEmpty(otherTsywpz));
        return zdyzdxMap;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存配置项信息
     */
    @PostMapping(value = "/saveBdcTsywPz")
    public String saveBdcTsywPz(@RequestBody BdcTsywPzDTO bdcTsywPzDTO){
        return bdcTsywPzFeignService.saveBdcTsywPzDTO(bdcTsywPzDTO);
    }

    /**
     * 批量删除不动产特殊业务配置内容
     * @param ids 特殊业务配置ID
     */
    @ResponseBody
    @DeleteMapping("")
    public void deleteBdcTsywpz(@RequestBody List<String> ids){
        if(CollectionUtils.isEmpty(ids)){
            throw new AppException("未获取到配置ID信息。");
        }
        bdcTsywPzFeignService.batchDeleteBdcTsywPz(ids);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取配置字典列表
     */
    @PostMapping("/pzzdxx")
    public List<BdcTsywZdyzdxDO> listPzzdxx(@RequestBody BdcTsywPzDO bdcTsywPzDO) {
        if(bdcTsywPzDO ==null ||bdcTsywPzDO.getPzlx() ==null){
            throw new MissingArgumentException("获取配置字典项缺失参数");
        }
        List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList =new ArrayList<>();
        if(CommonConstantUtils.TSYWPZ_PZLX_LC.equals(bdcTsywPzDO.getPzlx())){
            //流程多选
            List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
            if(CollectionUtils.isNotEmpty(processDefDataList)){
                for(ProcessDefData processDefData:processDefDataList){
                    if(processDefData.getSuspensionState()==1){
                        BdcTsywZdyzdxDO bdcTsywZdyzdxDO =new BdcTsywZdyzdxDO();
                        bdcTsywZdyzdxDO.setZdxsjz(processDefData.getKey());
                        bdcTsywZdyzdxDO.setZdxxsz(processDefData.getName());
                        bdcTsywZdyzdxDOList.add(bdcTsywZdyzdxDO);

                    }

                }
            }
        }else if(CommonConstantUtils.TSYWPZ_PZLX_SJZDDX.equals(bdcTsywPzDO.getPzlx()) ||CommonConstantUtils.TSYWPZ_PZLX_SJZD.equals(bdcTsywPzDO.getPzlx())){
            //数据库字典项
            if (StringUtils.isBlank(bdcTsywPzDO.getPzzzdxbs())) {
                throw new MissingArgumentException("获取字典表的字典项标识不能为空！");
            }
            List<Map> zdxxList = bdcZdFeignService.queryBdcZd(bdcTsywPzDO.getPzzzdxbs());
            if (CollectionUtils.isEmpty(zdxxList)) {
                zdxxList = bdcSlZdFeignService.queryBdcSlzd(bdcTsywPzDO.getPzzzdxbs());
            }
            if(CollectionUtils.isNotEmpty(zdxxList)){
                for(Map zdxx:zdxxList){
                    if(zdxx != null &&zdxx.get("DM") != null &&zdxx.get("MC") != null) {
                        BdcTsywZdyzdxDO bdcTsywZdyzdxDO = new BdcTsywZdyzdxDO();
                        bdcTsywZdyzdxDO.setZdxsjz(zdxx.get("DM").toString());
                        bdcTsywZdyzdxDO.setZdxxsz(zdxx.get("MC").toString());
                        bdcTsywZdyzdxDOList.add(bdcTsywZdyzdxDO);
                    }

                }
            }

        }else{
            //自定义字典项
            if (StringUtils.isBlank(bdcTsywPzDO.getPzzzdxbs())) {
                throw new MissingArgumentException("获取自定义字典表的字典项标识不能为空！");
            }
            BdcTsywZdyzdxDO bdcTsywZdyzdxDO =new BdcTsywZdyzdxDO();
            bdcTsywZdyzdxDO.setZdyzdxbs(bdcTsywPzDO.getPzzzdxbs());
            bdcTsywZdyzdxDOList = bdcTsywPzFeignService.listZdyzdx(bdcTsywZdyzdxDO);

        }

        return bdcTsywZdyzdxDOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 获取配置字典列表
     */
    @PostMapping("/pl/modifyGnmk")
    void batchModifyGnmk(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList) {
        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            this.bdcTsywPzFeignService.batchModifyTsywpzGnmk(bdcTsywPzDOList);
        }
    }

    /**
     * 初始化导入Yml配置内容
     * <p> 每个子系统应用启动时，会读取项目中的Yml配置内容至redis中暂存。
     * 再由该接口来消费redis中暂存的Yml的配置信息，导入至数据库配置中。
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: String 初始化配置内容结果
     */
    @GetMapping("/yml/init")
    public String ymlInit() {
        try{
            bdcTsywPzFeignService.initYmlData();
            return "初始化配置内容成功";
        }catch(Exception e){
            LOGGER.info(e.getMessage(),e);
            return e.getMessage();
        }
    }

    /**
     * 导入Apollo配置信息到数据库配置中
     *
     * @param httpServletRequest 网页请求
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return java.lang.String
     */
    @PostMapping("/yml/import/excel")
    public String ymlImportToExcel(MultipartHttpServletRequest httpServletRequest) {
        if (null == httpServletRequest) {
            return this.getJson(false, null, "未获取到文件内容信息。");
        }

        BdcTsywPzQO bdcTsywPzQO = new BdcTsywPzQO();
        bdcTsywPzQO.setQbcx(CommonConstantUtils.SF_S_DM);
        List<BdcTsywPzDO> bdcTsywPzDOList = bdcTsywPzFeignService.listBdcTsywPz(bdcTsywPzQO);

        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            Map<String, BdcTsywPzDO> tsywPzMap =new HashMap<>();
            for(BdcTsywPzDO bdcTsywPzDO : bdcTsywPzDOList){
                tsywPzMap.put(bdcTsywPzDO.getPzmc(),bdcTsywPzDO);
            }
            //获取子系统列表
            List<Map> djzxtzdList = bdcZdFeignService.queryBdcZd("tsywpzzxt");
            List<String> djzxtList =new ArrayList<>();
            if(CollectionUtils.isNotEmpty(djzxtzdList)){
                for(Map<String,String> map:djzxtzdList){
                    djzxtList.add(map.get("DM"));
                }
            }
            //获取非业务配置项
            List<String> fywpzList =new ArrayList<>();
            List<Map> fywpzzdList = bdcZdFeignService.queryBdcZd("fywpz");
            if(CollectionUtils.isNotEmpty(fywpzzdList)){
                for(Map<String,String> map:fywpzzdList){
                    fywpzList.add(map.get("DM"));
                }
            }
            // 遍历所有上传文件，获取到配置信息
            Map<String, Map<String, String>> importConfigMap = readFile(httpServletRequest,djzxtList,fywpzList);

            if(MapUtils.isEmpty(importConfigMap)){
                return this.getJson(false, null, "未读取到配置内容。");
            }
            //已录入的更新配置值
            for(BdcTsywPzDO bdcTsywPzDO : bdcTsywPzDOList){
                Map<String,String> dataMap = this.getPzxxByPzmc(importConfigMap, bdcTsywPzDO);
                if(MapUtils.isEmpty(dataMap)){
                    continue;
                }
                String pzz = Optional.ofNullable(dataMap.get("pzz")).orElse("");
                bdcTsywPzDO.setPzz(pzz);
                bdcTsywPzDO.setPzzxt(Optional.ofNullable(dataMap.get("pzzxt")).orElse(""));
                LOGGER.info("更新配置名称为：{} ; 配置值为：{}",bdcTsywPzDO.getPzmc(), pzz);
                bdcTsywPzFeignService.updateBdcTsywPz(bdcTsywPzDO);
            }
            //剩下的新增录入
            List<BdcTsywPzDO> insertPzList =new ArrayList<>();
            if(MapUtils.isNotEmpty(importConfigMap)){
                for (Map.Entry<String, Map<String, String>> entry : importConfigMap.entrySet()) {
                    BdcTsywPzDO bdcTsywPzDO =new BdcTsywPzDO();
                    bdcTsywPzDO.setPzid(UUIDGenerator.generate16());
                    bdcTsywPzDO.setPzmc(entry.getKey().replace(entry.getValue().get("pzzxt"),""));
                    bdcTsywPzDO.setPzzt(CommonConstantUtils.SF_S_DM);
                    String pzz = Optional.ofNullable(entry.getValue().get("pzz")).orElse("");
                    bdcTsywPzDO.setPzz(pzz);
                    bdcTsywPzDO.setPzsm(XZ_PZSM);
                    bdcTsywPzDO.setPzlx(CommonConstantUtils.TSYWPZ_PZLX_ZFC);
                    bdcTsywPzDO.setPzzxt(entry.getValue().get("pzzxt"));
                    LOGGER.info("新增配置名称为：{} ; 配置值为：{}",bdcTsywPzDO.getPzmc(), pzz);
                    insertPzList.add(bdcTsywPzDO);
                }
                if(CollectionUtils.isNotEmpty(insertPzList)){
                    bdcTsywPzFeignService.insertBdcTsywPzPl(insertPzList);
                }
            }

            return this.getJson(true, JSON.toJSONString(insertPzList), "导入成功。");
        }
        return this.getJson(true, null , "导入完成，未查询到需要配置的数据库配置内容。");
    }

    private Map<String, String> getPzxxByPzmc(Map<String, Map<String, String>> importConfigMap, BdcTsywPzDO bdcTsywPzDO){
        String pzmc = bdcTsywPzDO.getPzmc();
        if(CommonConstantUtils.TSYWPZ_PZLX_ARR.equals(bdcTsywPzDO.getPzlx())){
            // 配置类型为数组字符串时，进行特殊处理
            Map<String,String> arrayDataMap = new HashMap<>(16);
            String pzzxt = null;
            Iterator it = importConfigMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, Map<String,String>> entry = (Map.Entry<String, Map<String, String>>) it.next();
                String entryKey = entry.getKey();
                if(entryKey.equals(pzmc+bdcTsywPzDO.getPzzxt())){
                    it.remove();
                    continue;
                }
                if(entryKey.indexOf(pzmc)> -1 &&(StringUtils.isBlank(bdcTsywPzDO.getPzzxt()) ||entryKey.indexOf(bdcTsywPzDO.getPzzxt()) >-1)){
                    Map<String, String> dataMap = entry.getValue();
                    if(StringUtils.isNotBlank(bdcTsywPzDO.getPzzxt())){
                        entryKey =entryKey.replace(bdcTsywPzDO.getPzzxt(),"");
                    }
                    arrayDataMap.put(entryKey.substring(pzmc.length()+1), dataMap.get("pzz"));
                    pzzxt = dataMap.get("pzzxt");
                    // 此处删除读取到的Map里面的值，用于导入后提示哪些数据未进行导入
                    it.remove();
                }
            }
            if(MapUtils.isNotEmpty(arrayDataMap)){
                Map<String,String> map = new HashMap<>(3);
                map.put("pzz", JSON.toJSONString(arrayDataMap));
                map.put("pzzxt", pzzxt);
                return map;
            }
        }else{
            if(importConfigMap.containsKey(bdcTsywPzDO.getPzmc()+bdcTsywPzDO.getPzzxt())){
                Map<String, String> dataMap = importConfigMap.get(bdcTsywPzDO.getPzmc()+bdcTsywPzDO.getPzzxt());
                if(MapUtils.isNotEmpty(dataMap)){
                    importConfigMap.remove(bdcTsywPzDO.getPzmc()+bdcTsywPzDO.getPzzxt());
                    return dataMap;
                }
            }
        }
        return null;
    }

    private String getJson(boolean result, Object object, String message){
        Map<String,Object> resultMap = new HashMap<>(3);
        resultMap.put("result", Boolean.valueOf(result));
        resultMap.put("data", object);
        resultMap.put("message", message);
        return JSON.toJSONString(resultMap);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  配置项导出
     */
    @PostMapping("/export")
    public void exportBdcTsywPz(HttpServletResponse response, String filedata){
        if(StringUtils.isBlank(filedata)){
            throw new AppException("未定义要导出的配置项数据！");
        }

        List<BdcTsywPzDO> bdcTsywPzDOList = JSON.parseArray(filedata, BdcTsywPzDO.class);
        if(CollectionUtils.isEmpty(bdcTsywPzDOList)){
            throw new AppException("未定义要导出的配置项数据！");
        }
        // 获取配置信息，包括对应的自定义字典项等
        List<BdcTsywPzDTO> bdcTsywPzDTOList = new ArrayList<>(16);
        bdcTsywPzDOList.forEach(bdcTsywPzDO -> {
            BdcTsywPzDTO bdcTsywPzDTO = bdcTsywPzFeignService.queryBdcTsywPzDTO(bdcTsywPzDO.getPzid());
            if(null != bdcTsywPzDTO){
                bdcTsywPzDTOList.add(bdcTsywPzDTO);
            }
        });

        // 执行导出
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName =   URLEncoder.encode("不动产特殊业务配置项导出文件" + DateUtils.formateTimeYmdhms(new Date()) + ".txt", "utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");

            bos.write(JSON.toJSONString(bdcTsywPzDTOList).getBytes("UTF-8"));
            bos.flush();
        } catch (Exception e) {
            throw new AppException("配置项导出出错，请重试！");
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 配置项导入
     */
    @PostMapping("/import")
    public Map importBdcTsywPz(HttpServletRequest request, MultipartFile file){
        StringBuffer buffer = new StringBuffer();

        // 获取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("配置项导入失败，原因：{}", e.getMessage());
            throw new AppException("配置项导入失败!");
        }

        String tsywpzListJSON = buffer.toString();
        if(StringUtils.isBlank(tsywpzListJSON)){
            throw new AppException("配置项导入出错，配置项文件内容为空!");
        }

        // 转换对象
        List<BdcTsywPzDTO> bdcTsywPzDTOList = JSON.parseArray(tsywpzListJSON, BdcTsywPzDTO.class);
        if(CollectionUtils.isEmpty(bdcTsywPzDTOList)){
            throw new AppException("配置项导入出错，配置项文件内容为空!");
        }

        // 重新生成配置ID，保存配置项
        bdcTsywPzDTOList.stream().forEach(bdcTsywPzDTO -> {
            String pzid = UUIDGenerator.generate16();
            bdcTsywPzDTO.setPzid(pzid);
            bdcTsywPzDTO.setPzxgsj(new Date());


            bdcTsywPzFeignService.saveBdcTsywPzDTO(bdcTsywPzDTO);
        });

        Map res = new HashMap(1);
        res.put("code", 0);
        res.put("content", JSON.toJSONString(bdcTsywPzDTOList));
        return res;
    }

    /**
     * 读取导入的Apollo导出的Excel文件内容
     * 注意：方法中读取的第5列为配置项Key；第6列为配置项Value
     */
    private Map<String, Map<String,String>> readFile(MultipartHttpServletRequest httpServletRequest,List<String> djzxt,List<String> fywpzList){
        Map<String, Map<String, String>> importConfigMap = new HashMap(10);
        Iterator<String> iterator = httpServletRequest.getFileNames();
        while (iterator.hasNext()) {
            String fileName = iterator.next();
            MultipartFile multipartFile = httpServletRequest.getFile(fileName);
            try(InputStream fileIn = multipartFile.getInputStream()) {
                HSSFWorkbook wb = new HSSFWorkbook(fileIn);
                HSSFSheet sheet = wb.getSheetAt(0);
                for (Iterator ite = sheet.rowIterator(); ite.hasNext();) {
                    HSSFRow row = (HSSFRow) ite.next();
                    if(row.getRowNum() == 0){
                        continue;
                    }
                    this.addConfigData(row, importConfigMap,djzxt,fywpzList);
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return importConfigMap;
    }

    /**
     * @param djzxt 需要录入的登记子系统集合
     * @param importConfigMap 导入配置项
     * @param fywpzList 非业务配置项,无需导入的配置项
     * @author
     * @description 获取excel中的配置内容
     */
    private void addConfigData(HSSFRow row, Map<String, Map<String, String>> importConfigMap,List<String> djzxt,List<String> fywpzList){
        String configKey = row.getCell(5).getStringCellValue();
        String configValue = row.getCell(6).getStringCellValue();
        String configPzzxt = row.getCell(0).getStringCellValue();
        if(StringUtils.isNotBlank(configKey) && StringUtils.isNotBlank(configValue)){
            //key为配置名称+配置子系统
            if(CollectionUtils.isNotEmpty(djzxt) &&djzxt.contains(configPzzxt) &&!fywpzList.contains(configKey)){
                Map<String, String> dataMap = new HashMap<>(3);
                dataMap.put("pzmc", configKey);
                dataMap.put("pzz", configValue);
                dataMap.put("pzzxt", configPzzxt);
                importConfigMap.put(configKey+configPzzxt, dataMap);
            }
        }
    }

}
