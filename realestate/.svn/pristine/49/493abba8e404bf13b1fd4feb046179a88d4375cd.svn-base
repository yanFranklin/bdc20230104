package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.service.BdcGzGdZgzService;
import cn.gtmap.realestate.engine.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/7 16:17
 * @description 固定子规则实现类
 */
@Service
public class BdcGzGdZgzServiceImpl implements BdcGzGdZgzService {

    /**
     * 文件资源处理
     */
    @Autowired
    private ResourceUtil resourceUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String GDZGZ_FILEPATH = "/engine/src/main/resources/rulefiles/zgz/bdczgz.txt";

    /**
     * @param bdcGzZgzQO bdcGzZgzQO
     * @return List<BdcGzZgzDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 查询固定子规则列表
     */
    @Override
    public List<BdcGzZgzDO> listBdcGzGdZgz(BdcGzZgzQO bdcGzZgzQO) {
        String gdZgzJson = readGdZgzFileContent();
        List<BdcGzZgzDTO> bdcGzZgzDTOList = convertJsonToBdcGzZgzDTOList(gdZgzJson);

        List<BdcGzZgzDO> bdcGzZgzDOList = new ArrayList<>();

        if (null == bdcGzZgzQO) {
            if (CollectionUtils.isNotEmpty(bdcGzZgzDTOList)) {
                bdcGzZgzDTOList.forEach(BdcGzZgzDTO -> {
                    BdcGzZgzDO bdcGzZgzDO = this.getBdcGzZgzDO(BdcGzZgzDTO);
                    bdcGzZgzDOList.add(bdcGzZgzDO);
                });
            }
            return bdcGzZgzDOList;
        }

        if (CollectionUtils.isNotEmpty(bdcGzZgzDTOList)) {
            for (BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOList) {
                boolean gzmcFlag = true;
                boolean ytsmFlag = true;
                boolean pzrFlag = true;

                // 规则名称模糊匹配
                if (StringUtils.isNotBlank(bdcGzZgzQO.getGzmc())) {
                    if (StringUtils.isNotBlank(bdcGzZgzDTO.getGzmc())) {
                        if (!bdcGzZgzDTO.getGzmc().contains(bdcGzZgzQO.getGzmc())) {
                            gzmcFlag = false;
                        }
                    } else {
                        gzmcFlag = false;
                    }
                }
                // 用途说明模糊匹配
                if (StringUtils.isNotBlank(bdcGzZgzQO.getYtsm())) {
                    if (StringUtils.isNotBlank(bdcGzZgzDTO.getYtsm())) {
                        if (!bdcGzZgzDTO.getYtsm().contains(bdcGzZgzQO.getYtsm())) {
                            ytsmFlag = false;
                        }
                    } else {
                        ytsmFlag = false;
                    }
                }
                // 配置人模糊匹配
                if (StringUtils.isNotBlank(bdcGzZgzQO.getPzr())) {
                    if (StringUtils.isNotBlank(bdcGzZgzDTO.getPzr())) {
                        if (!bdcGzZgzDTO.getPzr().contains(bdcGzZgzQO.getPzr())) {
                            pzrFlag = false;
                        }
                    } else {
                        pzrFlag = false;
                    }
                }
                if (gzmcFlag && ytsmFlag && pzrFlag) {
                    BdcGzZgzDO bdcGzZgzDO = this.getBdcGzZgzDO(bdcGzZgzDTO);
                    bdcGzZgzDOList.add(bdcGzZgzDO);
                }
            }
        }

        return bdcGzZgzDOList;
    }

    /**
     * @param bdcGzZgzDTO bdcGzZgzDTO
     * @return String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 保存固定子规则信息
     */
    @Override
    public String saveBdcGzGdZgz(BdcGzZgzDTO bdcGzZgzDTO) {
        boolean bBdsTsxxAndJydmNullFlag = (CollectionUtils.isEmpty(bdcGzZgzDTO.getBdcGzBdsTsxxDOList()) && StringUtils.isBlank(bdcGzZgzDTO.getJydm()));
        if (null == bdcGzZgzDTO || bBdsTsxxAndJydmNullFlag) {
            return null;
        }

        String gdZgzJson = readGdZgzFileContent();

        Map<String, BdcGzZgzDTO> bdcGzZgzDTOMap = this.convertJsonToBdcGzZgzDTOMap(gdZgzJson);

        //判断是新增还是修改
        if (StringUtils.isBlank(bdcGzZgzDTO.getGzid())) {
            //新增
            //生成gzid
            bdcGzZgzDTO.setGzid(UUIDGenerator.generate());
            //生成数据流id
            bdcGzZgzDTO.getBdcGzSjlDTOList().forEach(BdcGzSjlDTO -> {
                BdcGzSjlDTO.setSjlid(UUIDGenerator.generate());
                BdcGzSjlDTO.setGzid(bdcGzZgzDTO.getGzid());

                //每个数据流对应的参数id生成
                BdcGzSjlDTO.getBdcGzSjlCsDOList().forEach(BdcGzSjlCsDO -> {
                    BdcGzSjlCsDO.setSjlcsid(UUIDGenerator.generate());
                    BdcGzSjlCsDO.setSjlid(BdcGzSjlDTO.getSjlid());
                });
            });

            //生成表达式id
            bdcGzZgzDTO.getBdcGzBdsTsxxDOList().forEach(BdcGzBdsTsxxDO -> {
                BdcGzBdsTsxxDO.setBdsid(UUIDGenerator.generate());
                BdcGzBdsTsxxDO.setGzid(bdcGzZgzDTO.getGzid());
            });
        } else {
            //修改
            bdcGzZgzDTOMap.remove(bdcGzZgzDTO.getGzid());
        }

        bdcGzZgzDTOMap.put(bdcGzZgzDTO.getGzid(), bdcGzZgzDTO);
        //刷新固定子规则文件
        String newGdZgzJson = this.convertGdZgzMapToJson(bdcGzZgzDTOMap);
        this.generateGdZgzFile(newGdZgzJson);

        return bdcGzZgzDTO.getGzid();
    }

    /**
     * @param bdcGzZgzDOList bdcGzZgzDOList
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 删除固定子规则
     */
    @Override
    public void deleteBdcGzZgz(List<BdcGzZgzDO> bdcGzZgzDOList) {
        if (CollectionUtils.isEmpty(bdcGzZgzDOList)) {
            throw new AppException("需要删除的固定子规则列表不能为空！");
        }
        //读固定子规则文件转为Map
        String gdZgzJson = readGdZgzFileContent();

        Map<String, BdcGzZgzDTO> bdcGzZgzDTOMap = this.convertJsonToBdcGzZgzDTOMap(gdZgzJson);

        for (BdcGzZgzDO bdcGzZgzDO : bdcGzZgzDOList) {
            if (null != bdcGzZgzDO && StringUtils.isNotBlank(bdcGzZgzDO.getGzid())) {
                bdcGzZgzDTOMap.remove(bdcGzZgzDO.getGzid());
            }
        }

        //刷新固定子规则文件
        String newGdZgzJson = convertGdZgzMapToJson(bdcGzZgzDTOMap);
        this.generateGdZgzFile(newGdZgzJson);
    }

    /**
     * @param gzid gzid
     * @return 固定子规则DTO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     */
    @Override
    public BdcGzZgzDTO queryBdcGzGdZgzDTOByGzid(String gzid) {
        if (StringUtils.isBlank(gzid)) {
            return null;
        }

        String gdZgzJson = readGdZgzFileContent();

        Map<String, BdcGzZgzDTO> bdcGzZgzDTOMap = this.convertJsonToBdcGzZgzDTOMap(gdZgzJson);

        return bdcGzZgzDTOMap.get(gzid);
    }

    /**
     * @param gdZgzJson gdZgzJson
     * @return List<BdcGzZgzDTO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 将固定子规则json转为BdcGzZgzDTO列表
     */
    private List<BdcGzZgzDTO> convertJsonToBdcGzZgzDTOList(String gdZgzJson) {
        if (StringUtils.isBlank(gdZgzJson)) {
            return null;
        }
        return JSONObject.parseArray(gdZgzJson, BdcGzZgzDTO.class);
    }

    /**
     * @param gdZgzJson gdZgzJson
     * @return Map<String, BdcGzZgzDTO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 将固定子规则json转为BdcGzZgzDTOMap集合
     */
    private Map<String, BdcGzZgzDTO> convertJsonToBdcGzZgzDTOMap(String gdZgzJson) {
        List<BdcGzZgzDTO> bdcGzZgzDTOList = this.convertJsonToBdcGzZgzDTOList(gdZgzJson);
        Map<String, BdcGzZgzDTO> bdcGzZgzDTOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcGzZgzDTOList)) {
            bdcGzZgzDTOList.forEach(BdcGzZgzDTO -> {
                bdcGzZgzDTOMap.put(BdcGzZgzDTO.getGzid(), BdcGzZgzDTO);
            });
        }
        return bdcGzZgzDTOMap;
    }

    /**
     * @return String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 读取本地固定子规则文件
     */
    private String readGdZgzFileContent() {
        String projectPath = getProjectPath();
        String filePath = projectPath + BdcGzGdZgzServiceImpl.GDZGZ_FILEPATH;

        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            return null;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            StringBuilder builder = new StringBuilder();
            String str = null;
            while (null != (str = bufferedReader.readLine())) {
                builder.append(str);
            }
            return builder.toString();
        } catch (Exception e) {
            logger.error("规则子系统，未获取规则文件{}！原因：{}", filePath, e.getMessage());
        }
        return null;
    }

    /**
     * @param gdZgzJson gdZgzJson
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 刷新固定子规则文件
     */
    public synchronized void generateGdZgzFile(String gdZgzJson) {
        if (StringUtils.isBlank(gdZgzJson)) {
            return;
        }

        try {
            String projectPath = getProjectPath();
            String filePath = projectPath + BdcGzGdZgzServiceImpl.GDZGZ_FILEPATH;
            File file = new File(projectPath + BdcGzGdZgzServiceImpl.GDZGZ_FILEPATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(gdZgzJson);
            bufferedWriter.flush();
            bufferedWriter.close();
            logger.info("成功刷新固定子规则文件");
        } catch (IOException e) {
            logger.info("刷新固定子规则文件失败");
            logger.error("errorMsg:", e);
            throw new AppException("刷新固定子规则文件失败");
        }
    }

    /**
     * @param bdcGzZgzDTOMap bdcGzZgzDTOMap
     * @return String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 将bdcGzZgzDTOMap集合转为json
     */
    private String convertGdZgzMapToJson(Map<String, BdcGzZgzDTO> bdcGzZgzDTOMap) {
        List<BdcGzZgzDTO> bdcGzZgzDTOList = new ArrayList<>(bdcGzZgzDTOMap.values());
        return JSONArray.parseArray(JSON.toJSONString(bdcGzZgzDTOList)).toString();
    }

    /**
     * @param bdcGzZgzDTO 子规则DTO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @description 根据子规则DTO获取对应DO对象
     */
    private BdcGzZgzDO getBdcGzZgzDO(BdcGzZgzDTO bdcGzZgzDTO) {
        BdcGzZgzDO bdcGzZgzDO = new BdcGzZgzDO();
        bdcGzZgzDO.setGzid(bdcGzZgzDTO.getGzid());
        bdcGzZgzDO.setGzmc(bdcGzZgzDTO.getGzmc());
        bdcGzZgzDO.setYtsm(bdcGzZgzDTO.getYtsm());
        bdcGzZgzDO.setYxj(bdcGzZgzDTO.getYxj());
        bdcGzZgzDO.setPzr(bdcGzZgzDTO.getPzr());
        bdcGzZgzDO.setPzrq(bdcGzZgzDTO.getPzrq());
        bdcGzZgzDO.setJydm(bdcGzZgzDTO.getJydm());

        return bdcGzZgzDO;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @return 项目路径
     * @description 获取项目路径
     */
    private String getProjectPath(){
        File f = new File("");
        String projectPath = null;
        try {
            projectPath = f.getCanonicalPath();
        } catch (IOException e) {
            logger.info("获取项目路径失败", e);
            throw new AppException("获取项目路径失败");
        }
        return projectPath;
    }
}
