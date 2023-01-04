package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDrExcelCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDtcxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/2 15:58
 * @description 导入excel查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0//drcx")
public class BdcDrExcelCxController{
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDrExcelCxController.class);

    @Autowired
    private BdcDrExcelCxFeignService service;

    @Autowired
    private BdcDtcxFeignService bdcDtcxFeignService;

    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    @ResponseBody
    @PostMapping("/createExcel")
    public void createExcel(@RequestParam(value = "excelList") String excelList,HttpServletResponse response){
        JSONArray jsonArray = JSONArray.parseArray(excelList);
        // 数据划分
        JSONObject bodyJson = jsonArray.getJSONObject(0);
        JSONObject valJson = jsonArray.getJSONObject(1);
        JSONObject comJson = jsonArray.getJSONObject(2);
        JSONArray vodyArr = JSONArray.parseArray(bodyJson.get("excelBody").toString());
        // 页面配置可能不用精确等查询 excelVal为空
        String excelVal = valJson.get("excelVal").toString();
        List<String> xlkList = new ArrayList<>();
        if(StringUtils.isNotEmpty(excelVal)){
            // excelVal -> cflx:续封,查封,轮候查封,预查封,轮候预查封&&qszt:临时,现势,历史,终止&&sf:否,是
            xlkList = Arrays.asList(valJson.get("excelVal").toString().split("&&"));
        }
        // 获取输入框下拉框值
        Map<String,String> mapkey = new HashMap();
        for(int i = 0; i < xlkList.size(); i++){
            String[] vals = xlkList.get(i).split(":");
            mapkey.put(vals[0],vals[1]);
        }
        // comVal -> 续封:5&&查封:1&&轮候查封:2&&预查封:3&&轮候预查封:4&&临时:0&&现势:1&&历史:2&&终止:3&&否:0&&是:1&&cxid:f16c22f348854028e4156c22e5050111
        Map<String,Object> comkey = new HashMap();
        // 获取隐藏sheet页的值  进行分割
        List<String> comList = Arrays.asList(comJson.get("comVal").toString().split("&&"));
        for(int i = 0; i < comList.size(); i++){
            String[] vals = comList.get(i).split(":");
            comkey.put(vals[0],vals[1]);
        }
        List<List<String>> valList = new ArrayList<>();
        List<String> listCumName = new ArrayList<>();
        // 查询字典对应 List<DtcxCxjgDO>
        List<DtcxCxjgDO> dtcxCxjgDOList = bdcDtcxFeignService.getCxjgList(comkey.get("cxid").toString());
        // 输入值
        Map<String,String> jdzdid = new HashMap<>();
        dtcxCxjgDOList.stream().forEach(eo -> jdzdid.put(eo.getJgzdid().toLowerCase(Locale.ROOT),eo.getZdid()));
        for(int i = 0; i < vodyArr.size(); i++){
            List<String> strings = new ArrayList<>();
            JSONObject object = JSONObject.parseObject(vodyArr.get(i).toString());
            strings.add(object.get("tjzdname").toString());
            String cxlx = object.get("canmhcx").toString().equals("0") ? "" : "=精确,左模糊,右模糊,全模糊";
            strings.add(cxlx((Integer) object.get("mrmhlx")) + cxlx);
            // 输入值是否需要下拉框
            String tjzdid = object.get("tjzdid").toString();
            if(!StringUtils.isEmpty(mapkey.get(jdzdid.get(tjzdid)))){
                strings.add(mapkey.get(jdzdid.get(tjzdid)));
            }
            valList.add(strings);
            listCumName.add(object.get("tjzdname").toString() + ":" + tjzdid);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDHHmmSS");
        try{
            ExcelUtil.exportExcel("查询模板" + simpleDateFormat.format(new Date()) + ".xls",valList,response,listCumName,comList);
        }catch(Exception e){
            throw new AppException("导出Excel失败");
        }
    }

    private static String cxlx(int num){
        switch(num){
            case 1:
                return "精确";
            case 2:
                return "左模糊";
            case 3:
                return "右模糊";
            case 4:
                return "全模糊";
            default:
                return String.valueOf(num);
        }
    }

    @ResponseBody
    @PostMapping("/excelCx")
    public Map excelCx(MultipartHttpServletRequest httpServletRequest,@RequestParam("cxtjCompare") List<String> cxtjCompare) throws IOException{
        // 读取执行sql
        if(httpServletRequest == null){
            return null;
        }
        InputStream fileIn = null;
        Map<String,List<String>> stringListMap = null;
        File excelFile = createExcelFile();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        Map result = new HashMap();
        try{
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while(iterator.hasNext()){
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 这里将上传的Excel缓存到临时文件，为了后续保存日志时候将查询Excel上传，实现查询操作回溯 added by zhuyong
                outputStream = new FileOutputStream(excelFile);
                IOUtils.copy(fileIn,outputStream);
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                inputStream = new FileInputStream(excelFile);
                stringListMap = ExcelUtil.readExcel(inputStream,true);
            }
            //对excel输入值做校验 如果全为空，则不进行查询
            List<String> srzNameList = stringListMap.get("srz");
            boolean srzExistFlag = true;
            for(int i = 0; i < srzNameList.size(); i++){
                if(StringUtils.isNoneEmpty(srzNameList.get(i))){
                    srzExistFlag = false;
                }
            }
            if(srzExistFlag){
                result.put("srzExistFlag","1");
                LOGGER.error("Excel表格输入为空，无法查询数据");
                return result;
            }
        }catch(Exception e){
            LOGGER.error("读取文件失败：",e);
            throw new AppException("读取文件失败");
        }finally{
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(fileIn);
        }

        result = service.excelCx(stringListMap);
        // 返回查询Excel缓存文件路径，后续在保存完日志会删除临时文件
        result.put("excelFilePath",excelFile.getAbsolutePath());
        return result;
    }

    /**
     * 下载指定名称模板文件
     *
     * @param fileName 模板文件名
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/downloadFile")
    public void downloadFile(@RequestParam("fileName") String fileName,HttpServletResponse response){
        if(StringUtils.isBlank(fileName)){
            throw new MissingArgumentException("未定义下载模板文件名");
        }

        File file = new File(printPath + "/excelmodel/" + fileName);
        if(!file.exists()){
            throw new AppException("未查找到指定模板文件" + fileName);
        }

        try(OutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file)){
            // 浏览器下载
            String downloadFile = URLEncoder.encode(fileName,"utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + downloadFile);
            outputStream.write(IOUtils.readFully(inputStream,Math.toIntExact(file.length())));
            outputStream.flush();
        }catch(Exception e){
            throw new AppException("下载指定模板文件出错" + fileName);
        }
    }

    /**
     * 创建临时Excel文件，用于缓存Excel查询文件
     *
     * @return {File} 临时Excel文件
     * @throws IOException
     */
    private File createExcelFile() throws IOException{
        File file = new File(printPath + "/excel/" + UUIDGenerator.generate16() + ".xls");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }
}

