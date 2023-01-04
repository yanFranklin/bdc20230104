package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.vo.inquiryui.BdcRzxzVO;
import cn.gtmap.realestate.common.util.FileUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXzrzServerVo;

import cn.gtmap.realestate.inquiry.ui.util.RsaUtil;
import cn.gtmap.realestate.inquiry.ui.util.RzxzUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

import cn.gtmap.realestate.common.core.vo.inquiryui.BdcRzxzDO;

import static cn.gtmap.realestate.inquiry.ui.util.RzxzUtils.delfile;

/**
 * @author <a href="mailto:hongqin\hejian@gtmap.cn">hongqin\hejian</a>
 * @version 1.0, 2022/8/25
 * @description 日志下载功能
 */
@Controller
@RequestMapping("config/v1.0/rzxz")
public class BdcRzxzController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RedisUtils redisUtils;
    private static String BdcRzxzHost="BdcRzxzHost";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BdcRzxzController.class);

    /**
     * @param
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 加载日志配置数据
     * @date : 2022/8/23 19:33
     */
    @ResponseBody
    @GetMapping("")
    public Map renderBdcRzxz(){
        List<BdcRzxzDO> hostList = findAllBdcXzrzConfig();
        String count = ""+hostList.size();
        Map<String, Object> hostMap = new HashMap<String, Object>();
        hostMap.put("code", 0);
        hostMap.put("msg", "");
        hostMap.put("count", count);
        hostMap.put("data", hostList);
        return (hostMap);
    }

    /**
     * @param bdcRzxzDO
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 存入日志配置数据
     * @date : 2022/8/23 19:33
     */
    @ResponseBody
    @PutMapping(value = "/add")
    public int saveBdcRzxz (@RequestBody BdcRzxzDO bdcRzxzDO){
        String hashKey = BdcRzxzHost + bdcRzxzDO.getHost();
        // 判断是否已经存在redis缓存中
        if(this.redisUtils.isExistHashKey(BdcRzxzHost,hashKey)){
            return 0;
        }
        // 向redis缓存中存入数据
        this.redisUtils.addHashValue(BdcRzxzHost,hashKey,JSON.toJSONString(bdcRzxzDO));
        LOGGER.info("日子下载功能：向redis中新增主机配置数据{}", bdcRzxzDO);
        // 判断是否已经存入redis缓存中
        if(this.redisUtils.isExistHashKey(BdcRzxzHost,hashKey)){
            return 1;
        }else {
            return 2;
        }
    }

    /**
     * @param bdcRzxzDO
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 编辑日志配置数据
     * @date : 2022/8/23 19:33
     */
    @ResponseBody
    @PutMapping(value = "/edit")
    public boolean editBdcRzxz (@RequestBody BdcRzxzDO bdcRzxzDO){
        String hashKey = BdcRzxzHost + bdcRzxzDO.getHost();
        // 向redis缓存中存入数据
        this.redisUtils.addHashValue(BdcRzxzHost,hashKey,JSON.toJSONString(bdcRzxzDO));
        LOGGER.info("日志下载：修改redis中主机配置数据{}",bdcRzxzDO);
        // 判断是否已经存入redis缓存中
        if(this.redisUtils.isExistHashKey(BdcRzxzHost,hashKey)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @param bdcRzxzDOList
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 删除日志配置数据
     * @date : 2022/8/23 19:33
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public boolean deleteBdcRzxz(@RequestBody List<BdcRzxzDO> bdcRzxzDOList){
        System.out.println(bdcRzxzDOList);
        for (BdcRzxzDO bdcRzxzDO : bdcRzxzDOList){
            String hashKey = BdcRzxzHost + bdcRzxzDO.getHost();
            // 删除redis中该数据
            this.redisUtils.deleteHashValue(BdcRzxzHost,hashKey );
            LOGGER.info("日志下载：删除redis中的主机配置数据{}",bdcRzxzDO);
            // 判断是否已经删除redis缓存中
            if(this.redisUtils.isExistHashKey(BdcRzxzHost,hashKey )){
                return false;
            }
        }
        return true;
    }

    /**
     * 从redis中获取主机名称如：国图登记服务应用
     * 前端会获取到该主机名称列表并展示
     * @return
     */
    @ResponseBody
    @GetMapping("/applicationName")
    public List<String> applicationName(){
        List<String> applications = new ArrayList<>();
        List<BdcRzxzDO> list = findAllBdcXzrzConfig();
        if(CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i <list.size(); i++) {
                applications.add(list.get(i).getName());
            }
        }
        LOGGER.info("日志下载：从redis获取到的主机名称{}",applications);
        return applications;
    }

    /**
     * 返回具体主机名称对应的应用实例(应用实例从注册中心选取)
     * @param applicationName
     * @return List<BdcXzrzServerVo>
     */
    @ResponseBody
    @GetMapping("/instanceName")
    public List<BdcXzrzServerVo> instanceName(@RequestParam String applicationName) {
        List<String> services = discoveryClient.getServices();
        List<List<ServiceInstance>> lists = new ArrayList<>();
        for (String service : services) {
            if(service != null && !"".equals(service)) {
                lists.add(discoveryClient.getInstances(service));
            }
        }
        List<BdcXzrzServerVo> bdcXzrzServerVoList = new ArrayList<>();
        for(List<ServiceInstance> serviceInstanceList : lists) {
            for(ServiceInstance serviceInstance : serviceInstanceList) {
                Map<String, BdcRzxzDO> maps = this.configurationParameter();
                //通过遍历map的key进行取值，用实例的host与map的key进行判断，并用value的getName与参数进行判断是否是同一个应用
                for (String key : maps.keySet()) {
                    String finalyServiceId = null;
                    BdcRzxzDO BdcRzxzDO = maps.get(key);
                    if(StringUtils.equals(serviceInstance.getHost(),key) && StringUtils.equals(BdcRzxzDO.getName(),applicationName)) {
                        finalyServiceId = getFinalyServiceId(serviceInstance);
                        bdcXzrzServerVoList.add(new BdcXzrzServerVo(finalyServiceId));
                    }
                }
            }
        }
        LOGGER.info("日志下载：从redis获取到的主机名称对应到的具体服务名称{}", bdcXzrzServerVoList);
        return bdcXzrzServerVoList;
    }

    /**
     * 将远程日志下载到本地
     * @param list 前台获取的需要下载日志的主机与服务名
     */
    @ResponseBody
    @PostMapping("/downLog")
    public List<String> downLoadBdcHasXzrz(@RequestBody List<BdcRzxzVO> list) {
        List<String> pathlist=new ArrayList<>();
        try{
            for(int i=0;i<list.size();i++){
                String path = this.getCoreLog(list.get(i));
                LOGGER.info("/downlog:"+path);
                if(!Objects.equals(path, "")){
                    pathlist.add(path);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        LOGGER.info("日志下载：远程日志下载到本地的地址{}",pathlist);
        return (pathlist);
    }

    /**
     * 浏览器下载打包成zip的目录文件
     * @param pathlist 文件存放地址
     * @param httpResponse
     */
    @PostMapping("/downLogZip")
    public void downLoadZip(@RequestParam("list") String pathlist, HttpServletResponse httpResponse) {
        String[] patharr = pathlist.substring(0,pathlist.length()-2).split(",");
        LOGGER.info("日志下载：本地临时目录地址{}", Arrays.toString(patharr));
        ZipOutputStream zos = null;
        File file = new File(patharr[patharr.length - 1]+".txt");
        String path =file.getParent();
        for(int i=0;i<patharr.length-1;i++){
            File file1 = new File(patharr[i]+".txt");
            String path1 =file1.getParent();
            RzxzUtils.copyDir(path1,path);
            FileUtils.deleteDirectory(new File(path1));
            delfile(path1);
        }
        try {
            String filePathAndName=path+".zip";
            //实际上此压缩包并不存在，只是目标压缩包文件
            FileOutputStream fos = new FileOutputStream(filePathAndName);
            //浏览器请求下载zip
            OutputStream os = httpResponse.getOutputStream();
            zos = new ZipOutputStream(os);
            //更改文件编码，将压缩包命名为中文
            httpResponse.reset();
            httpResponse.setContentType("application/msexcel");
            httpResponse.setHeader("Content-disposition", "attachment; filename="+"log"+".zip");
            RzxzUtils.writeZip(new File(path), "", zos);
            os.flush();
            fos.close();
            zos.close();
            os.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("创建ZIP文件失败",e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File file1 = new File(path+".zip");
            file1.delete();
            FileUtils.deleteDirectory(new File(path));
            delfile(path);
        }
    }

    /**
     * 前台传来的服务与redis中存储的值进行匹配
     */
    public String getCoreLog(BdcRzxzVO logs) throws Exception{
        String path = "";
        Map<String, BdcRzxzDO> maps = this.configurationParameter();
        //通过遍历map的key取值，将前台传过来的应用与配置文件中的应用取值进行判断
        for (String key : maps.keySet()) {
            BdcRzxzDO BdcRzxzDO = maps.get(key);
            if(StringUtils.equals(logs.getApply(),BdcRzxzDO.getName())) {
                //对BdcRzxzDO解密，并建立连接
                RzxzUtils.connectJsch(decode(BdcRzxzDO));
                path = RzxzUtils.getConfiguration(logs,BdcRzxzDO.getPath(),BdcRzxzDO.getApplyname());
                RzxzUtils.close();
            }
        }
        return path;
    }
    /**
     * @param
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 获取redis中日志配置信息列表并加密信息
     * @date : 2022/8/23 19:33
     */
    public List<BdcRzxzDO>  findAllBdcXzrzConfig(){
        List<BdcRzxzDO> redisList = new ArrayList<>();
        // redis中存日志配置信息的Key为BdcRzxzHost，hashKey为BdcRzxzHost+端口号
        Set<String> userSet = redisUtils.getHashKeySet("BdcRzxzHost");
        if(CollectionUtils.isNotEmpty(userSet)){
            for (String setkey : userSet){
                JSONObject bdcRzxzDO1 = JSON.parseObject(this.redisUtils.getHashValue("BdcRzxzHost",setkey));
                // 将日志配置信息加密以BdcRzxzDO类型存入redisList
                BdcRzxzDO bdcRzxzDO2 = new BdcRzxzDO();
                bdcRzxzDO2.setHost(bdcRzxzDO1.get("host").toString());
                bdcRzxzDO2.setPort(bdcRzxzDO1.get("port").toString());
                bdcRzxzDO2.setUsername(bdcRzxzDO1.get("username").toString());
                bdcRzxzDO2.setPassword(bdcRzxzDO1.get("password").toString());
                bdcRzxzDO2.setPath(bdcRzxzDO1.get("path").toString().replace("\\",File.separator));
                bdcRzxzDO2.setApplyname(bdcRzxzDO1.get("applyname").toString().replace("\\",File.separator));
                bdcRzxzDO2.setName(bdcRzxzDO1.get("name").toString());
                redisList.add(bdcRzxzDO2);
            }
        }
        return redisList;
    }

    /**
     * 返回最终的服务实例名称
     * @param serviceInstance 获取到服务名
     * @return targetServiceId 对应的实例名称
     */
    public String getFinalyServiceId(ServiceInstance serviceInstance) {
        String lowerCaseServiceId = StringUtils.lowerCase(serviceInstance.getServiceId());
        boolean isContains = lowerCaseServiceId.contains("realestate");
        String targetServiceId = null;
        if(isContains) {
            targetServiceId = lowerCaseServiceId;
        }else {
            targetServiceId ="realestate-"+(lowerCaseServiceId.contains("app")
                    ? StringUtils.removeEnd(lowerCaseServiceId,"-app") : lowerCaseServiceId)    ;
        }
        return targetServiceId;
    }
    /**
     * @param bdcRzxzDO
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 日志配置信息解密
     * @date : 2022/8/24 14:14
     */
    public static BdcRzxzDO decode(BdcRzxzDO bdcRzxzDO){
        bdcRzxzDO.setPassword(RsaUtil.deCode(bdcRzxzDO.getPassword()));
        return bdcRzxzDO;
    }
    /**
     * 得到配置文件内容 将对象的host设置为map的key进行对应
     * @return maps 配置文件的数据
     */
    public Map<String,BdcRzxzDO> configurationParameter() {
        Map<String,BdcRzxzDO> maps = new HashMap<>();
        List<BdcRzxzDO> list = findAllBdcXzrzConfig();
        if(list.size() != 0) {
            for (int i = 0; i <list.size(); i++) {
                BdcRzxzDO bdcRzxzDO = (list.get(i));
                maps.put(bdcRzxzDO.getHost(),bdcRzxzDO);
            }
        }
        return maps;
    }
}
