package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.enums.FileContentTypeEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcOfdToImgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcXmRestService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import cn.gtmap.realestate.portal.ui.core.qo.FileYsQO;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.fonts.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/02/25
 * @description 附件操作请求处理控制器，包括：附件上传、下载、删除
 */
@RestController
@RequestMapping("/rest/v1.0/files")
@Api(tags = "附件操作服务接口")
public class BdcStorageController extends BaseController {
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcOfdToImgFeignService bdcOfdToImgFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    private BdcXmRestService bdcXmRestService;

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ZipUtils zipUtils;
    /**
     * 字体
     */
    private Mapper fontMapper;

    /**
     * 附件平铺隐藏的文件夹以及附件
     */
    @Value("#{'${fjpp.hide.wjj:}'.split(',')}")
    private List<String> hideWjj;

    /**
     * 附件目录隐藏的文件夹以及附件
     */
    @Value("#{'${fjml.hide.wjj:}'.split(',')}")
    private List<String> fjmlHideWjj;

    /**
     * 附件目录按照受理页面排序的gzldyid
     */
    @Value("#{'${fjml.sort.gzldyid:}'.split(',')}")
    private List<String> fjmlSortDyid;

    /**
     * 字体存放目录，同打印模板同一目录
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * @param clientId 应用 Id，
     * @param spaceId  存储空间 Id，
     * @param owner    拥有者，
     * @param name     文件名称，
     * @param enabled: 0: 删除状态， 1：正常状态，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @return 文件列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 文档拥有者所有根节点文件
     */
    @GetMapping("/root")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("文档拥有者所有根节点文件")
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 Id", paramType = "query"),
            @ApiImplicitParam(name = "spaceId", value = "存储空间 Id", paramType = "query"),
            @ApiImplicitParam(name = "owner", value = "拥有者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "文件状态", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型", paramType = "query")})
    public List<StorageDto> listRootStorages(@RequestParam(name = "clientId", required = false) String clientId,
                                             @RequestParam(name = "spaceId", required = false) String spaceId,
                                             @RequestParam(name = "owner") String owner,
                                             @RequestParam(name = "name", required = false) String name,
                                             @RequestParam(name = "enabled", required = false) Integer enabled,
                                             @RequestParam(name = "type", required = false) Integer type) {
        if (StringUtils.isBlank(spaceId)) {
            throw new MissingArgumentException("存储空间 ID 不能为空！");
        }
        return storageClient.listAllRootStorages(clientId, spaceId, owner, name, enabled, type, null, null);
    }

    /**
     * @param id:要查询的文件节点， name 文件名称，
     * @param enabled:     0: 删除状态， 1：正常状态
     * @param type:        0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他, null:全部
     * @param name
     * @return 文件列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 根据目录id查询目录下的文件信息
     */
    @GetMapping("/subfiles")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据目录id查询目录下的文件信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "应用 Id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "文件状态", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型", paramType = "query")})
    public List<StorageDto> listSubStorages(@RequestParam(name = "id") String id,
                                            @RequestParam(name = "name", required = false) String name,
                                            @RequestParam(name = "enabled", required = false) Integer enabled,
                                            @RequestParam(name = "type", required = false) Integer type) {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("根据目录id查询目录下的文件信息必须传入应用 ID！");
        }
        return storageClient.listAllSubsetStorages(id, name, enabled, type, null, null);
    }

    /**
     * @param clientId 应用 Id，
     * @param spaceId  存储空间 Id，
     * @param owner    拥有者，
     * @param name     文件名称，
     * @param enabled: 0: 删除状态， 1：正常状态，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @return 文件列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 文档拥有者所有文件及文件夹
     */
    @GetMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "文档拥有者所有文件及文件夹", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 Id", paramType = "query"),
            @ApiImplicitParam(name = "spaceId", value = "存储空间 Id", paramType = "query"),
            @ApiImplicitParam(name = "owner", value = "拥有者", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "文件状态", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型", paramType = "query")})
    public List<StorageDto> listAllStorages(String clientId, String spaceId, String owner, String name, Integer enabled, Integer type) {
        List<StorageDto> storageDtoList = storageClient.queryMenus(clientId, spaceId, "", enabled, type, null, null, null);
        this.filterStorageFiles(storageDtoList);
        List<StorageDto> sortedFiles = storageDtoList;
        if (CollectionUtils.isNotEmpty(fjmlSortDyid) && StringUtils.isNotBlank(fjmlSortDyid.get(0))) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(spaceId);
            List<BdcXmDO> bdcXmDOList= bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && fjmlSortDyid.contains(bdcXmDOList.get(0).getGzldyid())) {
                sortedFiles = this.sortFiles(storageDtoList, spaceId);
            }
        }

        return sortedFiles;
    }


    /**
     * 递归方法，根据配置过滤文件夹不展示
     *
     * @param storageDtoList 附件信息
     */
    public void filterStorageFiles(List<StorageDto> storageDtoList) {
        if (CollectionUtils.isNotEmpty(fjmlHideWjj) && CollectionUtils.isNotEmpty(storageDtoList)) {
            Iterator<StorageDto> it = storageDtoList.iterator();
            while (it.hasNext()) {
                StorageDto storageDto = it.next();
                if (fjmlHideWjj.contains(storageDto.getName())) {
                    it.remove();
                } else {
                    if (CollectionUtils.isNotEmpty(storageDto.getChildren())) {
                        this.filterStorageFiles(storageDto.getChildren());
                    }
                }
            }
        }
    }

    @GetMapping("/allinone")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询所有文件到父级目录", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 Id", paramType = "query"),
            @ApiImplicitParam(name = "spaceId", value = "存储空间 Id", paramType = "query"),
            @ApiImplicitParam(name = "owner", value = "拥有者", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "文件状态", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型", paramType = "query")})
    public StorageDto queryFiles(String clientId, String spaceId, String owner, String name, Integer enabled, Integer type) {
        List<StorageDto> storageDtos = storageClient.queryMenus(clientId, spaceId, "", enabled, type, null, null, null);
        this.filterStorageFiles(storageDtos);

        List<StorageDto> sortedFiles = storageDtos;
        if (CollectionUtils.isNotEmpty(fjmlSortDyid) && StringUtils.isNotBlank(fjmlSortDyid.get(0))) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(spaceId);
            List<BdcXmDO> bdcXmDOList= bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && fjmlSortDyid.contains(bdcXmDOList.get(0).getGzldyid())) {
                sortedFiles = this.sortFiles(storageDtos, spaceId);
            }
        }

        StorageDto storageDto = new StorageDto();
        storageDto.setChildren(sortedFiles);
        String fileName = bdcXmFeignService.querySlbh(spaceId);
        storageDto.setName(fileName);
        return storageDto;
    }

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param owner    拥有者，
     * @param name     文件名称，
     * @param nodeId:  上级文件夹Id，
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 新建文件夹， nodeId 为空将建在用户根目录下
     */
    @PostMapping("/new")
    @ApiOperation("新建文件夹")
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 Id", paramType = "query"),
            @ApiImplicitParam(name = "spaceId", value = "存储空间 Id", paramType = "query"),
            @ApiImplicitParam(name = "nodeId", value = "上级文件夹Id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "owner", value = "拥有者", required = true, paramType = "query")})
    public void createFolder(@RequestParam(name = "clientId", required = false) String clientId,
                             @RequestParam(name = "spaceId", required = false) String spaceId,
                             @RequestParam(name = "nodeId", required = false) String nodeId,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "owner") String owner) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(owner)) {
            throw new MissingArgumentException("新建文件夹时文件名称和拥有者均不为空！");
        }
        storageClient.createFolder(clientId, spaceId, nodeId, name, owner);
    }

    /**
     * @param id 待删除文件的 id 拼接的字符串
     * @return 是否成功删除文件
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除指定 id 的附件
     */
    @DeleteMapping()
    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "id", value = "待删除文件 id", paramType = "query")
    public boolean deleteFile(String id) {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("删除文件未传入指定附件的 id！");
        }
        List<String> ids = Arrays.asList(id.split(","));
        return storageClient.deleteStorages(ids);
    }

    /**
     * @param id   选中文件的 id
     * @param name 新文件名
     * @return 是否重命名文件成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 文件重命名
     */
    @PostMapping("/name")
    @ApiOperation("文件重命名")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "选中文件 id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件新名称", paramType = "query")})
    public boolean renameFile(String id, String name) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
            throw new MissingArgumentException("文件重命名时需传入选中文件的 id 和文件新名称！");
        }
        return storageClient.rename(id, name);
    }

    /**
     * 文件压缩
     *
     * @param requestBody
     */
    @PostMapping("/ys")
    @ApiOperation("文件压缩")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "选中文件 id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件新名称", paramType = "query")})
    public void zipFile(@RequestBody FileYsQO requestBody) {
        List<String> zipfile = requestBody.getZipfile();
        String parentId = requestBody.getParentId();
        String gzlslid = requestBody.getGzlslid();
        String slbh = requestBody.getSlbh();
        if (CollectionUtils.isEmpty(zipfile)) {
            throw new MissingArgumentException("选择要压缩的文件！");
        }
        //生成一个随机文件夹
        String randomMl = UUIDGenerator.generate16();
        String randomPath = printPath + randomMl + "/";
        File fileMl = new File(randomPath);
        boolean mkdir = fileMl.mkdir();
        String zipFilePath = printPath + randomMl + ".zip";
        if (!mkdir) {
            throw new MissingArgumentException("压缩失败！");
        }
        try {
            //获取要压缩的文件，并存到
            zipUtils.createFiles(randomPath, zipfile, true);
            //压缩目标文件夹
            ZipUtils.zip(randomPath, zipFilePath);
            //获取文件并且上传到大云
            zipUtils.uploadFile(parentId, zipFilePath, gzlslid);
            //删除本地目录和文件
            File fileZip = new File(zipFilePath);
            fileZip.delete();
            ZipUtils.deleteDir(fileMl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param idJsonStr
     * @return 文件列表
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 下载选中文件
     */
    @PostMapping("/dxxz")
    @ApiOperation("下载多个文件")
    public void downLoadZipFiles(@RequestParam Map<String, Object> parm, HttpServletResponse response) {
        String[] ids =parm.get("idJsonStr").toString().split(CommonConstantUtils.ZF_YW_DH);
        List<String> idList = new ArrayList<>(Arrays.asList(ids));

        if (CollectionUtils.isEmpty(idList)) {
            throw new MissingArgumentException("选择要下载的文件！");
        }
        if (!Objects.nonNull(parm.get("gzlslid"))) {
            throw new MissingArgumentException("gzlslid");
        }

        //过滤重复的文件
        this.glcfwj(idList);

        String gzlslid = (String)parm.get("gzlslid");
        String fileName = bdcXmRestService.querySlbh(gzlslid);
        if (!StringUtils.isNotBlank(fileName)) {
            fileName = UUIDGenerator.generate16();
        }
        String path = printPath + fileName + "/";
        File filePath = new File(path);
        //判断目录是否存在
        if (!filePath.isDirectory()) {
            // 如果文件夹不存在   创建
            boolean mkdir = filePath.mkdir();
            if (!mkdir) {
                throw new MissingArgumentException("下载失败:原因创建路径失败！");
            }
        }

        //压缩文件 路径+名称.zip
        String zipFilePath = printPath + fileName + ".zip";

        InputStream inputStream  = null;
        try (OutputStream outputStream = response.getOutputStream()){
            // 清空response
            response.reset();
            //获取要压缩的文件，并存到本地路径randomPath
            zipUtils.createFiles(path, idList, true);
            //压缩目标文件夹到本地路径zipFilePath
            ZipUtils.zip(path, zipFilePath);
            String name = URLDecoder.decode(fileName, "utf-8");
            // 浏览器下载
            String downloadFile = URLEncoder.encode(name, "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile + ".zip");
            File fileZip = new File(zipFilePath);
            inputStream  = new FileInputStream(fileZip);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            //删除本地目录和文件
            fileZip.delete();
            ZipUtils.deleteDir(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 文件解压缩
     *
     * @param requestBody
     */
    @PostMapping("/jys")
    @ApiOperation("文件解压缩")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "选中文件 id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件新名称", paramType = "query")})
    public void unzipFile(@RequestBody FileYsQO requestBody) {
        List<String> zipfile = requestBody.getZipfile();
        String parentId = requestBody.getParentId();
        String gzlslid = requestBody.getGzlslid();
        String slbh = requestBody.getSlbh();
        if (CollectionUtils.isEmpty(zipfile)) {
            throw new MissingArgumentException("选择要解压缩的文件！");
        }
        //生成一个随机文件夹
        String randomMl = UUIDGenerator.generate16();
        String zipFilePath = printPath + randomMl + "/";
        String unzipFilePath = zipFilePath + "unzip";
        File fileUnZipMl = new File(unzipFilePath);
        boolean mkdir = fileUnZipMl.mkdirs();
        if (!mkdir) {
            throw new MissingArgumentException("解压缩失败！");
        }
        //文件解压目录

        try {
            //获取要解压缩的文件
            zipUtils.createFiles(zipFilePath, zipfile, true);
            //解压缩
            for (String fileId : zipfile) {
                StorageDto storageDto = storageClient.findById(fileId);
                if (Objects.nonNull(storageDto)) {
                    if (!storageDto.getName().endsWith("zip")) {
                        continue;
                    }
                    //解压缩目标文件夹
                    String zipfileUnzipPath = unzipFilePath + "/" + storageDto.getName().substring(0,storageDto.getName().lastIndexOf("."));
                    fileUnZipMl = new File(zipfileUnzipPath);
                    fileUnZipMl.mkdirs();
                    ZipUtils.unZip(zipFilePath + storageDto.getName(),
                            zipfileUnzipPath,
                            "gbk");
                    //上传指定目录下所有的文件
                    zipUtils.uploadFiles(parentId, zipfileUnzipPath, gzlslid);
                }
            }
            ZipUtils.deleteDir(new File(zipFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件解压缩
     *
     * @param requestBody
     */
    @GetMapping("/xz")
    @ApiOperation("文件下载")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "选中文件 id", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件新名称", paramType = "query")})
    public void downloadFile(@RequestParam(required = true) String zipfile, HttpServletResponse response) {

        if (StringUtils.isBlank(zipfile)) {
            throw new MissingArgumentException("选择要下载的文件！");
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            StorageDto storageDto = storageClient.findById(zipfile);
            MultipartDto fileContent = storageClient.download(storageDto.getId());
            String fileName = URLDecoder.decode(storageDto.getName(), "utf-8");

            // 浏览器下载
            String downloadFile = URLEncoder.encode(fileName, "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
            outputStream.write(fileContent.getData());
            outputStream.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上传图片
    @PostMapping("upload")
    @ApiOperation("文件上传")
    public JSONObject userupimg(@RequestParam(value = "file") MultipartFile file,
                                @RequestParam(value = "processInstanceId") String processInstanceId,
                                @RequestParam(value = "currentml",required = false) String currentml
                                ) throws Exception {
        byte[] bytes = file.getBytes();
        String name = file.getOriginalFilename();
        String contentType = file.getContentType();
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(currentml);
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        multipartDto.setData(bytes);
        multipartDto.setContentType(contentType);
        multipartDto.setSize(bytes.length);
        multipartDto.setOriginalFilename(name);
        multipartDto.setName(name);
        multipartDto.setSpaceCode(processInstanceId);
        StorageDto storageDto = storageClient.multipartUpload(multipartDto);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "上传成功");

        return jsonObject;
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取需要隐藏的文件夹
     */
    @GetMapping("/hidewjj")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取需要隐藏的文件夹")
    @ApiResponse(code = 200, message = "请求获取成功")
    public List<String> hidewjj() {
        return hideWjj;
    }

    /**
     * @param downUrl 下载url
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description WORD转换为PDF, 并返回文件流
     */
    @GetMapping("/wordToPdf/download")
    public void fjxxDownload(HttpServletResponse response, @RequestParam(name = "downUrl") String downUrl) {
        if (StringUtils.isNotBlank(downUrl)) {
            // 加载字体
            fontMapper = OfficeUtil.getFontMapper(printPath);

            try (InputStream inputStream = new URL(downUrl).openStream();
                 OutputStream outputStream = response.getOutputStream();) {
                if (inputStream != null) {
                    // 设置浏览器下载
                    String contentType = new MimetypesFileTypeMap().getContentType(new File("WORD转换文件.pdf"));
                    response.setContentType(contentType);
                    OfficeUtil.convertDocxToPDF(inputStream, outputStream, fontMapper);
                }
            } catch (Exception e) {
                throw new AppException("WORD转换PDF异常," + e.getMessage());
            }

        }

    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 勾选数据存到redis
     * @date : 2022/2/23 14:28
     */

    @PostMapping("/sjcl/redis")
    public String setFjclToRedis(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            return "";
        }
        //转为需要的url地址数组
        JSONArray jsonArray = JSON.parseArray(json);
        Set<String> urlSet = new HashSet<>(1);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jsonArray.get(i)));
            String downUrl = jsonObject.getString("downUrl");
            if (StringUtils.isNotBlank(downUrl)) {
                urlSet.add(downUrl);
            }
        }
        String redisKey = redisUtils.addSetValue("file_print" + UUIDGenerator.generate16(), urlSet, 60);
        LOGGER.info("当前打印生成rediskey{}", redisKey);
        return redisKey;
    }


    /**
     * @param key
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从redis获取附件地址打印
     * @date : 2022/2/23 14:27
     */
    @GetMapping("/print")
    public String printFjcl(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        //转为需要的url地址数组
        StringBuilder fjUrl = new StringBuilder();
        LOGGER.info("当前打印获取的redisKey{}", key);
        Set urlSet = redisUtils.getSetValue(key);
        List<String> urlList = new ArrayList<>(urlSet);
        for (int i = 0; i < urlList.size(); i++) {
            fjUrl.append("<row ID=\"").append(UUIDGenerator.generate16()).append("\">\n").append("<data  name=\"url").append("\"").append(" type=\"image\">").append(StringUtils.deleteWhitespace(urlList.get(i))).append("</data>\n").append("</row>\n");
        }
        String headXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<fetchdatas>\n" +
                "    <page>\n" +
                "        <datas>\n" +
                "            <data  name=\"slbh\" type=\"String\">slbh</data>" +
                "        </datas>\n" +
                "        <detail  ID=\"ZT_fjclList\">\n";
        String resultXml = "        </detail>\n" +
                "    </page>\n" +
                "</fetchdatas>";
        //拼接需要展示的xml
        String xml = headXml + fjUrl + resultXml;
        LOGGER.info("附件材料打印xml{}", xml);
        return xml;
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @Date 2022/9/6
     * @description ofd转图片
     */
    @GetMapping("/getofdimg")
    public Map<String, String> getofdimg(@RequestParam(value = "storageid") String storageid) {

        if (org.apache.commons.lang.StringUtils.isBlank(storageid)) {
            throw new MissingArgumentException("参数storageid不能为空");
        }
        Map<String, String> map = new HashMap<>();
        String result = bdcOfdToImgFeignService.ofdtoimg(storageid);
        map.put("img", result);
        return map;
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description  将需要打印图片横向放置转换为纵向
    */
    @PostMapping("getImage")
    public CommonResponse getImage(@RequestBody List<String> picUrlList) {
        LOGGER.info("getImage {}", JSON.toJSONString(picUrlList));
        if (CollectionUtils.isEmpty(picUrlList)) {
            throw new NullPointerException("选择要打印的图片");
        }
        //生成一个缓存文件夹
        String picPath = printPath + "picCache/";
        File picFile = new File(picPath);
        picFile.mkdir();

        List<String> picPrintUrl = new ArrayList<>();
        LOGGER.info("开始循环判断图片高宽");
        for (String picUrl : picUrlList) {
            try{
                String storageId = picUrl.substring(picUrl.lastIndexOf("/")+1);
                MultipartDto downloadFile = storageClient.download(storageId);
                byte[] byteArr = downloadFile.getData();
                InputStream is = new ByteArrayInputStream(byteArr);
                LOGGER.info("getImage is输出流");
                BufferedImage bufferedImage = ImageIO.read(is);
                LOGGER.info("getImage ImageIO.read(is), 高{}, 宽{}", JSON.toJSONString(bufferedImage.getHeight()), JSON.toJSONString(bufferedImage.getWidth()));
                if (bufferedImage.getHeight() < bufferedImage.getWidth()) {
                    LOGGER.info("getImage 高小于宽，开始旋转");
                    BufferedImage image = FileUtils.rotateImage(bufferedImage,90);
                    ImageIO.write(image,"jpg",new File(picPath,"1.jpg"));
                    InputStream inputStream = new FileInputStream(new File(picPath,"1.jpg"));
                    String imageBase64 = Base64Util.ImageToBase64ByIn(inputStream);
                    inputStream.close();
                    picPrintUrl.add(imageBase64);
                }else {
                    LOGGER.info("getImage 高小于宽,不旋转");
                    picPrintUrl.add(picUrl);
                }
                is.close();
            }catch (Exception e) {
                LOGGER.error("将需要打印图片横向放置转换为纵向 出现错误, e{}", e.getMessage());
                e.printStackTrace();
            }
        }
        return CommonResponse.ok(picUrlList);
    }

    private List<StorageDto> sortFiles(List<StorageDto> storageDtos, String processInsId) {
        List<StorageDto> sortedFiles = new ArrayList<StorageDto>();
        //获取受理页面排序后附件
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(processInsId);
        List<String> idList = new ArrayList<>();
        //根据文件中心NodeId排序
        for (BdcSlSjclDO sjclDO: bdcSlSjclDOList) {
            StorageDto storageDto = storageDtos.stream().filter(s -> StringUtils.equals(s.getId(), sjclDO.getWjzxid())).findFirst().orElse(null);
            if (storageDto != null) {
                idList.add(storageDto.getId());
                sortedFiles.add(storageDto);
            }
        }
        if (idList.size() < storageDtos.size()) {
            List<StorageDto> others = storageDtos.stream().filter(s -> !idList.contains(s.getId())).collect(Collectors.toList());
            sortedFiles.addAll(others);
        }
        return sortedFiles;
    }

    /**
     * @param idList
     * @return 文件列表
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 过滤重复文件id
     */
    private void glcfwj( List<String> idList) {
        List<String> cfjd = new ArrayList<>();
            //取出文件的所有外层文件id
        for (String fileId : idList) {
            List<String> wcidList = new ArrayList<>();
            this.getParentId(fileId, wcidList);
            wcidList.retainAll(idList);
            if (CollectionUtils.isNotEmpty(wcidList)) {
                cfjd.add(fileId);
            }
        }
        if (CollectionUtils.isNotEmpty(cfjd)) {
            idList.removeAll(cfjd);
        }
    }


    /**
     * @param wcidList
     * @return 文件列表
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 获取父节点
     */
    private void getParentId(String fileId, List<String> wcidList) {

        StorageDto storageDto = storageClient.findById(fileId);
        if (Objects.nonNull(storageDto) && Objects.nonNull(storageDto.getParent())) {
            StorageDto parent = storageDto.getParent();
            wcidList.add(parent.getId());
            getParentId(parent.getId(), wcidList);

        }
    }
}
