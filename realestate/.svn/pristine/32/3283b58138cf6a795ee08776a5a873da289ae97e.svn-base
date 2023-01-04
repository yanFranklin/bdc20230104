package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.property.configui.SshConfiguration;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzConfigurationVo;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzParameterVo;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzServerVo;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzVO;
import cn.gtmap.realestate.inquiry.ui.util.SshUtils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="mailto:yuhao@gtmap.cn">yuhao</a>
 * @version 1.0, 2021/1/29
 * @description 下载日志功能
 */
@Controller
@RequestMapping("/xzrz/zdc")
public class BdcXzrzController{
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private SshConfiguration sshConfiguration;
    @Value("${xzrz-config.version:}")
    private String version;
    /**
     * 返回应用服务
     * @return applicationName
     */
    @ResponseBody
    @GetMapping("/applicationName")
    public List<String> applicationName(){
        List<String> applications = new ArrayList<>();
        List<SshConfiguration.MpConfig> configs = sshConfiguration.getConfigs();
        if(configs.size() != 0) {
            for (int i = 0; i < configs.size(); i++) {
                SshConfiguration.MpConfig mpConfig = configs.get(i);
                applications.add(mpConfig.getName());
            }
        }
        return applications;
    }

    /**
     * 返回具体应用实例
     * @return instanceName
     */
    @ResponseBody
    @GetMapping("/instanceName")
    public List<BdcXzrzServerVo> instanceName(@RequestParam String applicationName) {
        List<String> services = discoveryClient.getServices();
        List<List<ServiceInstance>> lists = new ArrayList<>();
        for (String service : services) {
            if(service != null && !service.equals("")) {
                lists.add(discoveryClient.getInstances(service));
            }
        }
        List<BdcXzrzServerVo> bdcXzrzServerVoList = new ArrayList<>();
        for(List<ServiceInstance> serviceInstanceList : lists) {
            for(ServiceInstance serviceInstance : serviceInstanceList) {
                Map<String, BdcXzrzConfigurationVo> maps = this.configurationParameter();
                //通过遍历map的key进行取值，用实例的host与map的key进行判断，并用value的getName与参数进行判断是否是同一个应用
                for (String key : maps.keySet()) {
                    String finalyServiceId = null;
                    BdcXzrzConfigurationVo bdcXzrzConfigurationVo = maps.get(key);
                    if(StringUtils.equals(serviceInstance.getHost(),key) && StringUtils.equals(bdcXzrzConfigurationVo.getName(),applicationName)) {
                        finalyServiceId = getFinalyServiceId(serviceInstance);
                        bdcXzrzServerVoList.add(new BdcXzrzServerVo(finalyServiceId));
                    }
                }
            }
        }
        return bdcXzrzServerVoList;
    }
    /**
     * 判断是否有日志，并处理没有日志的情况，标识符为true
     * @param bdcXzrzVO
     * @param response
     */
    @PostMapping("/judgeLog")
    public void judgeBdcXzrz(@ModelAttribute("bdcXzrzVO") BdcXzrzVO bdcXzrzVO, HttpServletResponse response) {
        try {
            this.getCoreLog(bdcXzrzVO,response,true);
        } catch (Exception e) {
            throw new AppException(0,"无对应日志下载");
        }
    }

    /**
     * 处理有日志的情况 标识符为false
     * @param bdcXzrzVO
     * @param response
     */
    @PostMapping("/downLog")
    public void downLoadBdcHasXzrz(@ModelAttribute("bdcXzrzVO") BdcXzrzVO bdcXzrzVO,HttpServletResponse response) {
        try{
            this.getCoreLog(bdcXzrzVO,response,false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 前台传来的服务与yml文件的取值进行匹配
     */
    public void getCoreLog(BdcXzrzVO logs, HttpServletResponse response, boolean mark) throws IOException, JSchException, ParseException, SftpException, ServletException {
        Map<String, BdcXzrzConfigurationVo> maps = this.configurationParameter();
        //通过遍历map的key取值，将前台传过来的应用与配置文件中的应用取值进行判断
        for (String key : maps.keySet()) {
            BdcXzrzConfigurationVo bdcXzrzConfigurationVo = maps.get(key);
            if(StringUtils.equals(logs.getApply(),bdcXzrzConfigurationVo.getName())) {
                SshUtils.connectJsch(bdcXzrzConfigurationVo);
                this.getConfiguration(logs,response,mark,bdcXzrzConfigurationVo.getPath(),bdcXzrzConfigurationVo.getApplyName());
                SshUtils.close();
            }
        }
    }

    /**
     * 前台传过来的应用实例是否包含远程服务器对应的实例目录名称(包含的话下载)
     */
    public void getConfiguration(BdcXzrzVO logs, HttpServletResponse accept,boolean mark,String path,String applyName) throws SftpException, JSchException, ParseException, IOException, ServletException {
        // /home/realestate/hefei/ 得到所有log文件夹的父文件夹
        Vector parentFile = SshUtils.getParentFile(path);
        //得到前台传过来的应用实例
        List<String> listUiapply = logs.getUiapply();
        //用来存储符合条件的应用实例
        List<String> listParentFile = new ArrayList<>();
        if(parentFile.size() != 0) {
            for(int i = 0;i < parentFile.size();i++) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)parentFile.get(i);
                //判断是否符合条件 传过来的应用实例是否对应远程服务器上的实例名称
                if(listUiapply.contains(file.getFilename())) {
                    //符合条件就存储
                    listParentFile.add(file.getFilename());
                }
            }
        }

        //判断前台传过来的应用实例是否包含了listParentFile，包含就下载
        if(listUiapply.containsAll(listParentFile)) {
            BdcXzrzParameterVo parameterVo = new BdcXzrzParameterVo(path,listUiapply,applyName,SshUtils.getStringConvertTime(logs.getStart_time()),SshUtils.getStringConvertTime(logs.getEnd_time()));
            SshUtils.download(parameterVo,accept,mark);
        }
    }

    /**
     * 返回最终的服务实例名称
     * @param serviceInstance
     * @return targetServiceId 对应的实例名称
     */
    public String getFinalyServiceId(ServiceInstance serviceInstance) {
        String lowerCaseServiceId = StringUtils.lowerCase(serviceInstance.getServiceId());
        boolean isContains = lowerCaseServiceId.contains("realestate");
        String targetServiceId = null;
        if(isContains) {
            targetServiceId = lowerCaseServiceId + version;
        }else {
            targetServiceId ="realestate-"+(lowerCaseServiceId.contains("app")
                    ? StringUtils.removeEnd(lowerCaseServiceId,"-app") : lowerCaseServiceId) + version;
        }
        return targetServiceId;
    }

    /**
     * 得到配置文件内容 将对象的host设置为map的key进行对应
     * @return maps 配置文件的数据
     */
    public Map<String,BdcXzrzConfigurationVo> configurationParameter() {
        Map<String,BdcXzrzConfigurationVo> maps = new HashMap<>();
        List<SshConfiguration.MpConfig> configs = sshConfiguration.getConfigs();
        for (int i = 0; i < configs.size(); i++) {
            SshConfiguration.MpConfig mpConfig = configs.get(i);
            maps.put(mpConfig.getHost(),new BdcXzrzConfigurationVo(mpConfig.getHost(),mpConfig.getPort(),mpConfig.getUsername(),mpConfig.getPassword(),mpConfig.getPath(),mpConfig.getApplyName(),mpConfig.getName()));
        }
        return maps;
    }
}
