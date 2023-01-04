package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.MessageDto;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.exchange.BdcNwYwcjRzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.bengbu.BdcNwCjRzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.util.FtpUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-12-01
 * @description 外网申请FTP文件监听定时任务
 * <p>
 * 背景说明：
 * 因为硬件网闸，互联网+创件后将信息包装成压缩包放到中间文件服务器，登记监控该目录文件，
 * 提取内容进行登记系统创件，创件完成后同样需要把返回信息包装成压缩文件放到中间文件服务器
 */
@Service
public class WwsqFtpQuartzStandardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WwsqFtpQuartzStandardService.class);

    private static final String FUNCTION = "监听外网申请FTP压缩文件进行登记创件功能";
    private static final String ZIP = ".zip";

    private static final String MSG_CODE = "WWSQ_FTP_QUARTZ";

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private RoleManagerClient roleManagerClient;
    @Autowired
    private BdcNwCjRzFeignService bdcNwCjRzFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 是否开启监听外网申请FTP压缩文件进行创件功能
     */
    @Value("${wwsqftp.enable:false}")
    private Boolean openWwsqFtpListener;

    /**
     * 是否开启
     */
    @Value("${wwsqftp.send.msg.enable:false}")
    private Boolean openWwsqFtpSendMsg;

    /**
     * 外网申请FTP压缩文件存放服务器 IP username password port
     */
    @Value("${wwsqftp.ip:}")
    private String ip;

    @Value("${wwsqftp.username:}")
    private String username;

    @Value("${wwsqftp.password:}")
    private String password;

    @Value("${wwsqftp.port:}")
    private String port;

    /**
     * 外网申请FTP压缩文件在FTP服务器存放路径
     */
    @Value("${wwsqftp.ftpFilePath:}")
    private String ftpFilePath;

    /**
     * 登记处理完成后FTP文件移动到已完成目录路径
     */
    @Value("${wwsqftp.hasResolvedFtpFilePath:}")
    private String hasResolvedFtpFilePath;

    /**
     * 登记创件完成返回信息文件在FTP服务器存放路径
     */
    @Value("${wwsqftp.responseFilePath:}")
    private String responseFilePath;

    /**
     * 登记文件操作缓存目录（可以配置和打印一个路径）
     */
    @Value("${wwsqftp.dengji.filepath:}")
    private String localFilePath;

    /**
     * 文件编码方式
     */
    @Value("${wwsqftp.encoding:GBK}")
    private String encoding;

    @Value("${msg.center.clientId:}")
    private String clientId;

    @Value("${msg.center.msgType:}")
    private String msgType;

    @Value("${wwsqftp.receive.msg.role:admin}")
    private String receiveMsgRole;

    @Value("${wwsqftp.msg.content:已完成互联网+创建任务,检测出%s个问题,请查看!}")
    private String msgContent;

    @Autowired
    private EntityMapper entityMapper;


    /**
     * 初始化连接FTP服务器
     */
    @PostConstruct
    public void init() {
        LOGGER.info("{}开始初始化", FUNCTION);

        // 未设置解压文件路径，默认当前应用路径
        if (StringUtils.isBlank(localFilePath)) {
            localFilePath = System.getProperty("user.dir") + File.separator + "cache";
        }
        File file = new File(localFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 没配置返回信息FTP保存路径则默认采用和互联网存放FTP同一个路径
        if (StringUtils.isBlank(responseFilePath)) {
            responseFilePath = ftpFilePath;
        }
    }

    /**
     * 开始定时任务处理 (TODO 后续需要考虑批量异步处理和同步问题，这里时间紧急先不处理)
     */
    @Scheduled(cron = "${wwsqftp.cron:0 0 2 1 * ?}")
    @RedissonLock(lockKey = Constants.BENGBU_WWSQDTP_REDIS_LOCK_NAME, description = "蚌埠外网申请定时任务", waitTime = 10L, leaseTime = 600L)
    public void listenWwsqFtpFiles() {
        if (!openWwsqFtpListener || StringUtils.isBlank(ftpFilePath)) {
            LOGGER.info("{}处理结束，因为未开启此功能、或连接FTP失败、或未定义文件目录信息", FUNCTION);
            return;
        }

        LOGGER.info("FTP连接信息：ip-{}, username-{}, password-{}, port-{}, 压缩包存放目录：{}，返回信息保存目录：{}, 应用本地处理文件目录：{}",
                ip, username, password, port, ftpFilePath, responseFilePath, localFilePath);

        FTPClient ftpClient = null;
        try {
            ftpClient = this.getConnectFtpServer();

            // 进行FTP监听，获取压缩文件
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpFilePath);
            if (null == ftpFiles || 0 == ftpFiles.length) {
                LOGGER.info("未监听到外网申请FTP文件，处理结束");
                return;
            }

            for (FTPFile ftpFile : ftpFiles) {
                try {
                    String ftpFileName = ftpFile.getName();
                    if (!ftpFileName.contains(ZIP)) {
                        continue;
                    }

                    LOGGER.info("获取到FTP文件{}", ftpFileName);
                    ftpFileName = ftpFileName.replace(ZIP, "");

                    if (redisUtils.isExistHashKey(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName)) {
                        // 需要先判断是否处理过，避免前一次没处理完后一次定时任务又扫描发送任务过来
                        String hasResolved = redisUtils.getHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName);
                        if (StringUtils.equals(hasResolved, "false")) {
                            redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName, "true");
                            try {
                                this.readFtpRequestThenCreate(ftpClient, ftpFile);
                            } catch (Exception e) {
                                LOGGER.info("外网申请FTP文件{},处理时出现异常", ftpFileName + ZIP, e);
                                //外网创建任务异常将redis中对应的值置为未处理
                                redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName, "false");
                            }
                        } else {
                            LOGGER.info("{}文件{}已经处理过，不再处理", FUNCTION, ftpFileName + ZIP);
                        }
                    } else {
                        // 初始默认处理状态
                        redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName, "true");
                        try {
                            this.readFtpRequestThenCreate(ftpClient, ftpFile);
                        } catch (Exception e) {
                            LOGGER.info("外网申请FTP文件{},处理时出现异常:{}", ftpFileName + ZIP, e.getMessage());
                            //外网创建任务异常将redis中对应的值置为未处理
                            redisUtils.addHashValue(CommonConstantUtils.REDIS_BENGBU_WWSQ, ftpFileName, "false");
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ftpClient) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取FTP中申请信息，然后调用原有Exchange外网申请创件接口
     *
     * @param ftpClient   ftp连接
     * @param wwsqFtpFile 申请的FTP文件
     * @throws Exception
     */
    private void readFtpRequestThenCreate(FTPClient ftpClient, FTPFile wwsqFtpFile) throws Exception {
        LOGGER.info("{}开始处理申请创件信息", FUNCTION);

        // 下载到应用本地的FTP文件，例如 H:\W202011183099\W202011183099.txt
        String localFtpFile = localFilePath + File.separator + wwsqFtpFile.getName();
        // 下载到应用本地的FTP文件所在目录，例如 H:\W202011183099
        String localFtpDir = localFilePath + File.separator + wwsqFtpFile.getName().split("\\.")[0];

        // 1、下载
        this.downloadFtpFile(ftpClient, wwsqFtpFile, localFtpFile);

        // 2、解压
        this.unzipFtpFile(localFtpFile, localFtpDir);

        // 3、读取文件内容
        JSONObject requestData = this.getFtpRequestData(localFtpDir);

        // 4、获取要调用的业务接口名称
        String interfaceName = this.getFtpInterfaceName(localFtpDir);

        // 5、发送创件请求
        LOGGER.info("请求登记创建接口,接口名称:{},申请信息:{}", interfaceName, this.getNoSpaceStr(requestData.toJSONString()));
        Object response = exchangeInterfaceFeignService.wwsqRequestInterface(interfaceName, requestData);
        LOGGER.info("请求登记创建接口响应结果:{}", response);

        BdcNwYwcjRzDO bdcNwYwcjRzDO = new BdcNwYwcjRzDO();
        bdcNwYwcjRzDO.setQqcs(this.getNoSpaceStr(requestData.toJSONString()));
        bdcNwYwcjRzDO.setQqsj(new Date());
        bdcNwYwcjRzDO.setJkmc(interfaceName);
        bdcNwYwcjRzDO.setSlbh("");

        if (Objects.isNull(response)) {
            bdcNwYwcjRzDO.setXyjg(null);
            bdcNwYwcjRzDO.setCjjg(1);
            saveLog(bdcNwYwcjRzDO);
            throw new AppException();
        }
        bdcNwYwcjRzDO.setXyjg(JSON.toJSONString(response));

        // 6、上传返回信息
        // 不管创件成功或失败，都会把创建接口的返回值以TXT格式上传到FTP
        String responseFileName = localFilePath + File.separator + wwsqFtpFile.getName().split("\\.")[0] + ".txt";
        File file = new File(responseFileName);
        IOUtils.write(JSON.toJSONString(response), new FileOutputStream(file), encoding);
        uploadResponseFile(responseFileName);
        LOGGER.info("上传登记创件返回信息到FTP服务器成功，FTP相对路径：{}", responseFilePath + File.separator + file.getName());

        // 判断是否创件成功
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(response));
        JSONObject head = jsonObject.getJSONObject("head");
        if (head != null) {
            if (jsonObject.get("data") == null && !StringUtils.equals(head.getString("msg"), "成功")) {

                bdcNwYwcjRzDO.setCjjg(1);
                bdcNwYwcjRzDO.setYcxx(head.getString("msg"));
                saveLog(bdcNwYwcjRzDO);
                // 创件失败抛出异常
                LOGGER.error("调用外网申请创建接口失败，返回信息：{}", head.getString("msg"));
                throw new AppException();
            } else {
                JSONObject data = jsonObject.getJSONObject("data");
                String ywslbh = Objects.nonNull(data) && data.containsKey("ywslbh") ? data.getString("ywslbh") : "";
                bdcNwYwcjRzDO.setSlbh(ywslbh);
                bdcNwYwcjRzDO.setCjjg(0);
                saveLog(bdcNwYwcjRzDO);
            }
        }


        // 7、将申请的FTP文件移动到已完成目录 TODO

        // **************************************************
        // 不删除临时下载的和缓存返回结果信息文件，便于后续问题排查
        // **************************************************
    }

    /**
     * 获取FTP服务器连接
     *
     * @return FTPClient
     */
    private FTPClient getConnectFtpServer() throws IOException {
        if (StringUtils.isAnyBlank(ip, username, password, port)) {
            LOGGER.error("{}异常，未配置端口用户名等信息，无法连接", FUNCTION);
            throw new AppException();
        }

        FTPClient ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
        FtpUtil.getLocalCharSet(ftpClient);

        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            LOGGER.info("{}处理结束，连接FTP失败", FUNCTION);
            throw new AppException();
        }

        LOGGER.info("{}连接FTP服务器成功", FUNCTION);
        return ftpClient;
    }

    /**
     * 下载FTP文件
     *
     * @param ftpClient    ftp连接
     * @param wwsqFtpFile  ftp上申请文件
     * @param localFtpFile 下载到本地的文件
     * @throws IOException
     */
    private void downloadFtpFile(FTPClient ftpClient, FTPFile wwsqFtpFile, String localFtpFile) throws IOException {
        InputStream inputStream = FtpUtil.downloadFtpFile(ftpClient, ftpFilePath, wwsqFtpFile.getName());
        if (null == inputStream) {
            LOGGER.error("下载外网申请FTP文件{}失败", wwsqFtpFile.getName());
            throw new AppException();
        }

        try {
            IOUtils.copy(inputStream, new FileOutputStream(new File(localFtpFile)));
            LOGGER.info("{}下载FTP文件{}成功，路径：{}", FUNCTION, wwsqFtpFile.getName(), localFtpFile);
        } catch (IOException exception) {
            LOGGER.error("下载外网申请FTP文件{}失败:", wwsqFtpFile.getName(), exception);
            throw exception;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 解压下载到本地的FTP文件
     *
     * @param localFtpFile 下载到本地的文件
     * @param localFtpDir  下载到本地的文件所在目录
     * @throws Exception
     */
    public void unzipFtpFile(String localFtpFile, String localFtpDir) throws Exception {
        try {
            ZipUtils.unZip(localFtpFile, localFilePath, encoding);
            LOGGER.info("{}解压FTP文件{}成功,路径：{}", FUNCTION, localFtpFile, localFtpDir);
        } catch (Exception exception) {
            LOGGER.error("解压外网申请FTP文件{}失败", localFtpFile, exception);
            throw exception;
        }
    }

    /**
     * 获取 FTP 压缩包 Txt 文件中的申请信息（因为需要处理附件，所以不好直接获取内容返回）
     *
     * @param localFtpDir FTP解压目录
     * @return JSONObject 外网申请信息
     * @throws IOException
     */
    public JSONObject getFtpRequestData(String localFtpDir) throws IOException {
        // 下载到本地的FTP文件解压目录，例如 /home/ftp/W2020010199 或 H:\W202011183099
        File localFtpDirFile = new File(localFtpDir);
        if (!localFtpDirFile.exists() || !localFtpDirFile.isDirectory()) {
            return null;
        }

        // 申请信息txt文件
        File requestFile = this.getRequestFile(localFtpDirFile);
        String sqxx = FileUtils.readFileToString(requestFile, encoding);
        if (StringUtils.isBlank(sqxx)) {
            LOGGER.error("外网申请FTP压缩文件中{}的申请信息txt文件内容为空或格式不对", localFtpDir);
            throw new AppException();
        }
        LOGGER.info("外网申请FTP压缩文件中{} txt文件申请信息：{}", localFtpDir, getNoSpaceStr(sqxx));

        // 解析请求中的文件信息
        JSONObject sqxxJson = JSON.parseObject(sqxx);

        JSONArray dataArray = new JSONArray();
        Object dataObj = sqxxJson.get("data");
        if (dataObj instanceof JSONArray) {
            dataArray = (JSONArray) dataObj;
        } else if (dataObj instanceof JSONObject) {
            dataArray.add(dataObj);
        } else {
            LOGGER.error("外网申请{}数据格式错误", localFtpDir);
            throw new AppException();
        }

        if (null == dataArray || 0 == dataArray.size()) {
            LOGGER.info("外网申请FTP压缩文件{}中无data数据信息，处理结束", localFtpDir);
        }

        for (int d = 0; d < dataArray.size(); d++) {
            JSONObject data = (JSONObject) dataArray.get(d);
            this.uploadFileThenReplaceUrl(data, localFtpDirFile);
        }
        return sqxxJson;
    }

    /**
     * 获取FTP解压目录下的申请信息文件
     *
     * @param localFtpDir FTP解压目录
     * @return
     */
    private File getRequestFile(File localFtpDir) {
        // 读取以受理编号命名的申请信息txt文件，例如 H:\W202011183099\W202011183099.txt
        Collection<File> files = FileUtils.listFiles(localFtpDir, new String[]{"txt"}, false);
        if (CollectionUtils.isEmpty(files)) {
            LOGGER.error("未获取到外网申请FTP压缩文件中的申请信息txt文件");
            throw new AppException();
        }

        File sqxxFile = null;
        Iterator iterator = files.iterator();
        while (iterator.hasNext()) {
            File file = (File) iterator.next();
            if (!StringUtils.contains(file.getName(), "interface")) {
                sqxxFile = file;
            }
        }
        return sqxxFile;
    }

    /**
     * 处理附件子节点信息，上传压缩包中附件，然后将fjurl替换为大云文件中心地址
     *
     * @param dataJSON    data节点内容
     * @param localFtpDir 解压目录，即下载到本地的申请附件目录
     * @throws IOException
     */
    private void uploadFileThenReplaceUrl(JSONObject dataJSON, File localFtpDir) throws IOException {
        LOGGER.info("{}处理前信息:{}", localFtpDir.getName(), JSON.toJSONString(dataJSON));
        JSONArray fjxxDirArray = dataJSON.getJSONArray("fjxx");
        if (dataJSON.getJSONArray("bdcdyxx") != null && (fjxxDirArray == null || 0 == fjxxDirArray.size())) {
            JSONObject bdcdyxx = JSON.parseObject(JSON.toJSONString(dataJSON.getJSONArray("bdcdyxx").get(0)));
            if (bdcdyxx.getJSONArray("fjxx") != null && bdcdyxx.getJSONArray("fjxx").size() > 0) {
                LOGGER.info("{}开始处理fjxx1", localFtpDir.getName());
                JSONArray tempJSONArray = bdcdyxx.getJSONArray("fjxx");
                for (int i = 0; i < tempJSONArray.size(); i++) {
                    dealFjxx(localFtpDir, tempJSONArray, i);
                }
                bdcdyxx.put("fjxx", tempJSONArray);
                dataJSON.put("bdcdyxx", bdcdyxx);
            } else {
                LOGGER.info("{}开始处理fjxx2", localFtpDir.getName());
                JSONArray cfxxArray = bdcdyxx.getJSONArray("cfxx");
                JSONArray tempJSONArray = new JSONArray();
                for (Object fjxx : cfxxArray) {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(fjxx));
                    if (jsonObject.getJSONArray("fjxx") != null && jsonObject.getJSONArray("fjxx").size() > 0) {
                        for (int i = 0; i < jsonObject.getJSONArray("fjxx").size(); i++) {
                            dealFjxx(localFtpDir, jsonObject.getJSONArray("fjxx"), i);
                        }
                        tempJSONArray.add(jsonObject);
                    } else {
                        tempJSONArray.add(fjxx);
                    }
                }
                if (tempJSONArray.size() > 0) {
                    bdcdyxx.put("cfxx", tempJSONArray);
                }
                dataJSON.put("bdcdyxx", bdcdyxx);
            }
            LOGGER.info("{}处理后信息:{}", localFtpDir.getName(), JSON.toJSONString(dataJSON));
            return;
        }
        if (null == fjxxDirArray || 0 == fjxxDirArray.size()) {
            LOGGER.info("外网申请FTP压缩文件{}中的无fjxx附件信息，不处理附件", localFtpDir.getName());
            return;
        }
        for (int i = 0; i < fjxxDirArray.size(); i++) {
            dealFjxx(localFtpDir, fjxxDirArray, i);
        }
        LOGGER.info("{}处理后信息2:{}", localFtpDir.getName(), JSON.toJSONString(dataJSON));
    }

    private void dealFjxx(File localFtpDir, JSONArray fjxxDirArray, int i) throws IOException {
        // 获取附件文件夹
        JSONObject fjxxDir = (JSONObject) fjxxDirArray.get(i);
        if (null == fjxxDir) {
            return;
        }

        // 获取附件文件夹下附件材料内容信息
        JSONArray clnrArray = fjxxDir.getJSONArray("clnr");
        if (null == clnrArray || 0 == clnrArray.size()) {
            return;
        }

        for (int j = 0; j < clnrArray.size(); j++) {
            JSONObject clnr = (JSONObject) clnrArray.get(j);
            if (null == clnr) {
                continue;
            }

            String fjurl = StringUtils.deleteWhitespace(clnr.getString("fjurl"));
            if (StringUtils.isBlank(fjurl)) {
                continue;
            }

            File file = new File(localFtpDir + fjurl);
            if (!file.exists()) {
//                     不抛异常，默认继续处理
                LOGGER.info("{}外网申请FTP压缩文件中未找到声明的附件：{}", localFtpDir.getName(), fjurl);
            } else {
//                     附件存在，则把附件上传到文件中心，方便后续内容解析
                String downloadUrl = this.uploadFile(file);
                clnr.put("fjurl", downloadUrl);
            }
        }
    }

    /**
     * 获取解压目录下interface.txt文件中标识的Exchange创建接口
     *
     * @param unzipDir FTP解压目录
     * @return String Exchange创建接口标识
     * @throws IOException
     */
    public String getFtpInterfaceName(String unzipDir) throws IOException {
        File file = new File(unzipDir, "interface.txt");
        if (!file.exists()) {
            LOGGER.error("{}未获取到定义接口名称的文件信息", unzipDir);
            throw new AppException();
        }
        String interfaceName = FileUtils.readFileToString(file, encoding);
        LOGGER.info("{}获取到定义接口名称{}", unzipDir, interfaceName);
        return interfaceName;
    }

    /**
     * 将互联网FTP压缩包中的附件上传到大云文件中心
     *
     * @param file 目标文件
     * @return String 文件下载地址
     * @throws IOException
     */
    private String uploadFile(File file) throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setClientId("clientId");
        multipartDto.setData(multipartFile.getBytes());
        multipartDto.setContentType(multipartFile.getContentType());
        multipartDto.setName(multipartFile.getName());
        multipartDto.setOriginalFilename(multipartFile.getOriginalFilename());
        multipartDto.setSize(multipartFile.getSize());
        multipartDto.setNodeId(UUIDGenerator.generate16());
        multipartDto.setSpaceCode(UUIDGenerator.generate16());
        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        return storageDto.getDownUrl();
    }

    /**
     * 上传返回信息文件到FTP服务器
     *
     * @param responseFileName
     * @throws IOException
     */
    private void uploadResponseFile(String responseFileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = this.getConnectFtpServer();
            FtpUtil.uploadFile(ftpClient, responseFileName, responseFilePath);
        } catch (Exception e) {
            LOGGER.error("{}上传返回信息文件到FTP服务器失败", responseFileName);
            e.printStackTrace();
        } finally {
            if (null != ftpClient) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getNoSpaceStr(String str) {
        return str.replaceAll("\r|\n", "").replaceAll(" ", "");
    }


    public void saveLog(BdcNwYwcjRzDO bdcNwYwcjRzDO) {
        try {
            bdcNwYwcjRzDO.setRzid(UUIDGenerator.generate());
            bdcNwYwcjRzDO.setCjjg(StringUtils.isBlank(bdcNwYwcjRzDO.getSlbh()) ? 1 : 0);
            entityMapper.insertSelective(bdcNwYwcjRzDO);
        } catch (Exception e) {
            LOGGER.error("内网创建日志记录异常,异常信息:{},异常:{},参数:{}", e.getMessage(), e, bdcNwYwcjRzDO);
        }
    }

    @Scheduled(cron = "${bengbu.wwsqftp.send.msg.corn:0 0 2 1 * ?}")
    public void sendMessage() {
        if (!openWwsqFtpSendMsg) {
            LOGGER.info("{}处理结束，因为未开启此功能", FUNCTION);
            return;
        }
        Integer number = bdcNwCjRzFeignService.countFailedRecord();
        ProduceMsgDto produceMsgDto = new ProduceMsgDto();
        produceMsgDto.setClientId(clientId);
        produceMsgDto.setMsgCode(MSG_CODE);
        produceMsgDto.setMsgType(msgType);
        produceMsgDto.setMsgTypeName("自定义消息");
        produceMsgDto.setMsgTitle("无");
        if (StringUtils.isBlank(receiveMsgRole)) {
            LOGGER.warn("管理员角色为空，无法发送提示信息！");
            return;
        }
        RoleDto roleDto = roleManagerClient.queryRoleByRoleName(receiveMsgRole);
        if (roleDto == null || StringUtils.isBlank(roleDto.getId())) {
            LOGGER.warn("未查询到角色信息，无法发送提示信息！");
            return;
        }
        List<UserDto> userDtoList = roleManagerClient.listEnableUsersByRoleId(roleDto.getId());
        if (CollectionUtils.isEmpty(userDtoList)) {
            LOGGER.warn("未查询到管理员账号，无法发送提示信息！");
            return;
        }
        List<String> usernames = userDtoList.stream().filter(Objects::nonNull).
                filter(userDto -> StringUtils.isNotBlank(userDto.getUsername()))
                .collect(Collectors.mapping(userDto -> userDto.getUsername(), Collectors.toList()));

        if (CollectionUtils.isEmpty(usernames)) {
            LOGGER.warn("用户名列表为空，无法发送提示信息！");
            return;
        }
        // 根据MSG_CODE删除之前的消息
        List<MessageDto> listMessage = messageClient.listMessage(clientId, MSG_CODE, msgType, null, null, null, null);
        if (CollectionUtils.isNotEmpty(listMessage)) {
            List<String> msgIds = listMessage.stream().map(MessageDto::getId).collect(Collectors.toList());
            String ids = StringUtils.join(msgIds, CommonConstantUtils.ZF_YW_DH);
            messageClient.deleteMessages(ids);
        }
        produceMsgDto.setConsumer(StringUtils.join(usernames, CommonConstantUtils.ZF_YW_DH));
        produceMsgDto.setConsumerType("batch");
        produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setProducerType("personal");
        produceMsgDto.setNotifyType("rabbitmq");
        produceMsgDto.setMsgContent(String.format(msgContent, number));
        produceMsgDto.setRead(0);
        produceMsgDto.setOptions("save");
        messageClient.saveProduceMsg(produceMsgDto);
    }

}
