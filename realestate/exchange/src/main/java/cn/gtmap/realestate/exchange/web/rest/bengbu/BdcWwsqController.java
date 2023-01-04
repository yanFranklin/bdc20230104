package cn.gtmap.realestate.exchange.web.rest.bengbu;

import cn.gtmap.realestate.common.core.domain.exchange.BdcNwYwcjRzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.quartz.bengbu.WwsqFtpQuartzService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2022-01-14
 * @see WwsqFtpQuartzService
 * @description 蚌埠外网申请手动创建服务
 *  说明：
 *  1、蚌埠外网创建正常定时任务处理：cn.gtmap.realestate.exchange.quartz.bengbu.WwsqFtpQuartzService
 *  2、有时因为硬件网络问题导致FTP不稳定，下载文件失败，所以这里提供手动触发创建功能，步骤：
 *    将外网申请ZIP文件手动复制到当前应用所在服务器目录；
 *    请求接口读取文件进行外网件创建；
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
public class BdcWwsqController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWwsqController.class);

    @Autowired
    private WwsqFtpQuartzService wwsqFtpQuartzService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 外网申请ZIP文件存放路径
     */
    @Value("${bengbu.wwsq.sdcj.filepath:/home/wwsq}")
    private String filePath;

    /**
     * 登记文件操作缓存目录（可以配置和打印一个路径）
     */
    @Value("${bengbu.wwsqftp.dengji.filepath:}")
    private String localFilePath;


    /**
     * 外网申请创件（为了方便浏览器操作及查看信息，采用GET请求及直接返回结果信息）
     * @return 操作结果信息
     */
    @GetMapping("/wwsq")
    public String readWwsqFileThenCreate() {
        File zipFilePath = new File(filePath);
        if(!zipFilePath.exists()) {
            return "文件路径不存在，外网申请ZIP文件存放路径配置项：bengbu.wwsq.sdcj.filepath，默认路径：/home/wwsq";
        }

        if(!zipFilePath.isDirectory()) {
            return "非文件夹，外网申请ZIP文件存放路径配置项：bengbu.wwsq.sdcj.filepath，默认路径：/home/wwsq";
        }

        File[] files = zipFilePath.listFiles();
        if(null == files || 0 == files.length) {
            return "外网申请手动处理存放文件夹" + filePath + "下无待处理ZIP文件";
        }

        int index = 1;
        StringBuilder responseInfo = new StringBuilder();
        responseInfo.append("手动创建外网申请件结果信息： ");
        for(File file : files) {
            if(file.isDirectory() || !file.getName().endsWith(".zip")) {
                continue;
            }

            responseInfo.append(index++ + "、文件" + file.getName());

            String hasResolved = redisUtils.getHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, file.getName());
            if (StringUtils.equals(hasResolved, "true")) {
                responseInfo.append("已创建，不再处理；  ");
                continue;
            }

            try {
                readFtpRequestThenCreate(file);
                responseInfo.append("处理成功，已删除；  ");
                redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, file.getName(), "true");
            } catch (Exception e) {
                LOGGER.info("申请创件失败：{}", file.getName());
                responseInfo.append("处理失败；  ");
                redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ,  file.getName(), "false");
            }
        }
        return responseInfo.toString();
    }

    /**
     * 读取FTP中申请信息，然后调用原有Exchange外网申请创件接口
     * @param file 外网创建zip文件
     * @throws Exception
     */
    private void readFtpRequestThenCreate(File file) throws Exception {
        LOGGER.info("开始处理申请创件：{}", file.getName());

        // 下载到应用本地的FTP文件，例如 H:\W202011183099\W202011183099.txt
        String localFtpFile = localFilePath + File.separator  + file.getName();
        // 下载到应用本地的FTP文件所在目录，例如 H:\W202011183099
        String localFtpDir = localFilePath + File.separator + file.getName().split("\\.")[0];

        // 2、解压
        wwsqFtpQuartzService.unzipFtpFile(localFtpFile, localFtpDir);

        // 3、读取文件内容
        JSONObject requestData = wwsqFtpQuartzService.getFtpRequestData(localFtpDir);

        // 4、获取要调用的业务接口名称
        String interfaceName = wwsqFtpQuartzService.getFtpInterfaceName(localFtpDir);

        // 5、发送创件请求
        LOGGER.info("{}请求登记创建接口,接口名称:{},申请信息:{}", file.getName(),interfaceName, wwsqFtpQuartzService.getNoSpaceStr(requestData.toJSONString()));
        Object response = exchangeInterfaceFeignService.wwsqRequestInterface(interfaceName, requestData);
        LOGGER.info("{}请求登记创建接口响应结果:{}", file.getName(), response);

        BdcNwYwcjRzDO bdcNwYwcjRzDO = new BdcNwYwcjRzDO();
        bdcNwYwcjRzDO.setQqcs(wwsqFtpQuartzService.getNoSpaceStr(requestData.toJSONString()));
        bdcNwYwcjRzDO.setQqsj(new Date());
        bdcNwYwcjRzDO.setJkmc(interfaceName);
        bdcNwYwcjRzDO.setSlbh("");

        if(Objects.isNull(response)){
            bdcNwYwcjRzDO.setXyjg(null);
            bdcNwYwcjRzDO.setCjjg(1);
            wwsqFtpQuartzService.saveLog(bdcNwYwcjRzDO);
            throw new AppException();
        }
        bdcNwYwcjRzDO.setXyjg(JSON.toJSONString(response));

        // 判断是否创件成功
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(response));
        JSONObject head = jsonObject.getJSONObject("head");
        if(head != null) {
            if(jsonObject.get("data") == null && !StringUtils.equals(head.getString("msg"),"成功")){
                bdcNwYwcjRzDO.setCjjg(1);
                bdcNwYwcjRzDO.setYcxx(head.getString("msg"));
                wwsqFtpQuartzService.saveLog(bdcNwYwcjRzDO);
                // 创件失败抛出异常
                LOGGER.error("调用外网申请创建接口失败，返回信息：{}", head.getString("msg"));
                throw new AppException();
            }else{
                JSONObject data = jsonObject.getJSONObject("data");
                String ywslbh = Objects.nonNull(data) && data.containsKey("ywslbh") ? data.getString("ywslbh") : "";
                bdcNwYwcjRzDO.setSlbh(ywslbh);
                bdcNwYwcjRzDO.setCjjg(0);
                wwsqFtpQuartzService.saveLog(bdcNwYwcjRzDO);
            }
        }

        // 删除已处理文件
        FileUtils.deleteQuietly(file);
        LOGGER.info("手动创建删除已完成文件：{}", file.getName());
        LOGGER.info("结束处理申请创件：{}", file.getName());
    }

}
