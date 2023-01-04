package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.config.SftpHelper;
import cn.gtmap.realestate.etl.core.convert.OldSystemFileConverter;
import cn.gtmap.realestate.etl.core.domian.IRecvimgDataDO;
import cn.gtmap.realestate.etl.core.domian.IRecvsDO;
import cn.gtmap.realestate.etl.core.vo.PopupFileDataVO;
import cn.gtmap.realestate.etl.service.OldSystemFileService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OldSystemFileServiceImpl implements OldSystemFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OldSystemFileServiceImpl.class);

    @Autowired
    private SftpHelper sftpHelper;

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;

    @Autowired
    private OldSystemFileConverter oldSystemFileConverter;

    @Value("${old.system.file.path.prefix:/home}")
    private String filePathPrefix;

    @Value("${old.system.file.download.url:http://127.0.0.1:8800/realestate-etl/rest/v1.0/old/system/download/file?}")
    private String fileDownloadUrl;

    /**
     * 老系统中文件没有后缀名，文件路径中存在WJ,JTAN,之类的文件读取时，不加后缀名
     */
    @Value("#{'${old.system.file.nosuffix:WJ}'.split(',')}")
    private List<String> fileNoSuffixList;

    /**
     * 打印访问的ip地址, http://127.0.0.1:8800
     */
    @Value("${url.print-ip:}")
    protected String printip;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取项目对应的目录信息
     *
     * @param gzlslid
     * @return
     */
    @Override
    public CommonResponse<List<PopupFileDataVO>> fetchCataLog(String gzlslid) throws Exception {
        Example xmExample = new Example(BdcXmDO.class);
        xmExample.createCriteria().andEqualTo("gzlslid", gzlslid);
        List<BdcXmDO> bdcXmList = bdcEntityMapper.selectByExample(xmExample);
        if (CollectionUtils.isNotEmpty(bdcXmList) && StringUtils.isNotBlank(bdcXmList.get(0).getSlbh())) {
            String oinsid = bdcXmList.get(0).getSlbh();
            Example iRecvsExample = new Example(IRecvsDO.class);
            iRecvsExample.createCriteria().andEqualTo("oinsid", gzlslid);
            List<IRecvsDO> iRecvsList = bdcEntityMapper.selectByExample(iRecvsExample);
            if (CollectionUtils.isNotEmpty(iRecvsList)) {
                List<PopupFileDataVO> parentList = new ArrayList<>(iRecvsList.size());
                for (IRecvsDO iRecvsDO : iRecvsList) {
                    PopupFileDataVO parent = PopupFileDataVO.PopupFileDataVOBuilder.aPopupFileDataVO().withId(iRecvsDO.getRecvid().toString())
                            .withClientId(oinsid)
                            .withName(iRecvsDO.getRname())
                            .withType("0")
                            .build();

                    Example iRecvingDataExample = new Example(IRecvimgDataDO.class);
                    iRecvingDataExample.createCriteria().andEqualTo("recvid", iRecvsDO.getRecvid());
                    List<IRecvimgDataDO> iRecvingDataList = bdcEntityMapper.selectByExample(iRecvingDataExample);
                    if (CollectionUtils.isNotEmpty(iRecvingDataList)){
                        List<PopupFileDataVO> childrens = oldSystemFileConverter.getPopupFileDataListByIRecvingDataList(iRecvingDataList,filePathPrefix,fileDownloadUrl,fileNoSuffixList);
                        parent.setChildren(childrens);
                    }
                    parentList.add(parent);
                }
                if (CollectionUtils.isNotEmpty(parentList)){
                    return CommonResponse.ok(parentList);
                }else {
                    return CommonResponse.fail("未查询到文件信息");
                }
            } else {
                return CommonResponse.fail("未找到关联的iRecvsList信息");
            }
        } else {
            return CommonResponse.fail("未找到关联的xm信息");
        }
    }

    /**
     * 下载指定文件信息
     *
     * @param path
     * @param fileName
     * @param response
     * @return
     */
    @Override
    public void downloadFile(String path, String fileName, HttpServletResponse response) throws Exception {
        sftpHelper.downloadFile(path, fileName, response.getOutputStream());
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 打印勾选的数据
     * @date : 2021/8/16 10:21
     */
    @Override
    public String getSjclXml(String key) {
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
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 设置需要打印的收件材料的地址到redis ，生存时长20s
     * @date : 2021/8/16 13:50
     */
    @Override
    public String setSjclUrlToRedis(String json) {
        if (StringUtils.isBlank(json)) {
            return "";
        }
        //转为需要的url地址数组
        JSONArray jsonArray = JSON.parseArray(json);
        Set<String> urlSet = new HashSet<>(1);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(jsonArray.get(i)));
            String downUrl = jsonObject.getString("downUrl");
            if (StringUtils.isNotBlank(downUrl)) {
                urlSet.add(this.handleUrl(downUrl));
            }
        }
        String redisKey = redisUtils.addSetValue("old_file_print" + UUIDGenerator.generate16(), urlSet, 60);
        LOGGER.info("当前打印生成rediskey{}", redisKey);
        return redisKey;
    }

    /**
     * 常州地市采用网络映射方式，所以地市进行打印时附件的ip地址市区是无法访问的。
     * 此方法将地市的ip替换为市区的ip从而实现可下载
     * 例： 将http://10.1.2.99:8800/etl-cz/download 替换为 http://127.0.0.1:8800/etl-cz/download
     */
    private String handleUrl(String url){
        if(StringUtils.isNotBlank(printip)){
            if(url.startsWith("http://")){
                url = printip + url.substring(url.indexOf("/", url.indexOf(".")));
            }else{
                url = printip + url;
            }
        }
        return url;
    }
}
