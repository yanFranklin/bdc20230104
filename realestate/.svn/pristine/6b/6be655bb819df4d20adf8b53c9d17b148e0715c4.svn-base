package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.ZipUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/09
 * @description 测绘成果查询台账
 */
@RestController
@RequestMapping("/rest/v1.0/chcg")
public class BdcChcgController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcChcgController.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 测绘成果附件地址前缀ip与端口
     */
    @Value("${chcg.fjUrl:}")
    private String chcgUrl;

    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    @ResponseBody
    @GetMapping("/bengbu/chcgcx")
    public Object getChcg(@RequestParam(value = "bdcdyh", required = true) String bdcdyh) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(bdcdyh)) {
            throw new AppException("查询参数缺失！");
        }
        List data = new ArrayList<>();
        Map param = new HashMap(2);
        param.put("queryField", bdcdyh);
        Object chcgxx = exchangeInterfaceFeignService.requestInterface("queryDchyCgfl", param);
        LOGGER.info("测绘成果信息返回:{}",JSON.toJSONString(chcgxx));
        if (null != chcgxx){
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(chcgxx));
            for (int i = 0; i < jsonArray.size(); i++){
               if (CommonConstantUtils.JK_RESPONSE_SUCCESS.equals(jsonArray.getJSONObject(i).getString("msg").toUpperCase())){
                   JSONArray reponseData = jsonArray.getJSONObject(i).getJSONArray("result");
                   if (CollectionUtils.isNotEmpty(reponseData)) {
                       for (int j = 0; j < reponseData.size(); j++){
                            JSONObject res = reponseData.getJSONObject(j);
                            if (res.containsKey("chcgList")) {
                                JSONArray fjRes = res.getJSONArray("chcgList");
                                for (int k = 0; k < fjRes.size(); k++) {
                                    JSONObject fjItem = fjRes.getJSONObject(k);
                                    if (fjItem.containsKey("attachpath")){
                                        fjItem.put("attachpath",chcgUrl+fjItem.getString("attachpath"));
                                    }
                                    data.add(fjItem);
                                }
                            }
                       }
                   }

               }
            }
            Pageable pageable = new PageRequest(1,10,null);
            Page page = PageUtils.listToPageWithTotal(data,pageable,data.size());
            return addLayUiCode(page);
        }
        return null;
    }

    @GetMapping("/fjxzzip")
    public void fjDownload(@RequestParam(value = "bdcdyh") String bdcdyh, HttpServletResponse response) {
        if (Objects.isNull(bdcdyh)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Map cxParam = new HashMap(2);
        cxParam.put("queryField",bdcdyh);
        Object res = exchangeInterfaceFeignService.requestInterface("queryDchyCgfl",cxParam);
        LOGGER.info("测绘成果信息查询:{}",JSON.toJSONString(res));
        if (null != res){
            JSONArray chcgxx = JSONArray.parseArray(JSON.toJSONString(res));
            List<JSONObject> chcgList = new ArrayList<>();
            for (int i = 0; i < chcgxx.size(); i++){
                if (Objects.nonNull(chcgxx.getJSONObject(i).get("result"))) {
                    JSONArray chcgxxResult = chcgxx.getJSONObject(i).getJSONArray("result");
                    if (CollectionUtils.isNotEmpty(chcgxxResult)) {
                        for (int j = 0;j < chcgxxResult.size(); j++) {
                            JSONArray chcgxxList = chcgxxResult.getJSONObject(j).getJSONArray("chcgList");
                            if (StringUtils.isNotBlank(chcgUrl)) {
                                for (int k = 0; k < chcgxxList.size(); k++) {
                                    JSONObject fjItem = chcgxxList.getJSONObject(k);
                                    if(fjItem.containsKey("attachpath")){
                                        fjItem.put("attachpath",chcgUrl + fjItem.getString("attachpath"));
                                        chcgList.add(fjItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(chcgList)){
                //将文件内容都下载下来
                String filePath = printPath + bdcdyh;
                //下载zip文件
                try (OutputStream outputStream = response.getOutputStream()) {
                    //创建目录
                    File dir = new File(filePath);
                    dir.mkdirs();
                    for (JSONObject jsonObject : chcgList){
                        LOGGER.info("---测绘成果信息查询，需要上传的文件：{}",JSON.toJSONString(jsonObject));
                        //获取文件类型
                        byte[] fileByte = Base64Utils.getFile(jsonObject.getString("attachpath"));
                        String fileName = filePath + "/" +jsonObject.getString("attachname");
                        //将文件放在本地
                        try {
                            File localFile = new File(fileName);
                            if (Objects.isNull(localFile)){
                                localFile.createNewFile();
                            }
                            FileOutputStream fos = new FileOutputStream(localFile);
                            fos.write(fileByte);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String zip = ZipUtils.zip(filePath,filePath + ".zip");
                    File zipFile = new File(zip);
                    FileInputStream inputStream = new FileInputStream(zipFile);
                    String fileName = URLDecoder.decode(bdcdyh + ".zip","utf-8");
                    //浏览器下载
                    String downloadFile = URLEncoder.encode(fileName, "utf-8");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
                    outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(zipFile.length())));
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    ZipUtils.deleteDir(dir);
                    zipFile.delete();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                throw new MissingArgumentException("获取附件失败！");
            }
        }
    }
}
