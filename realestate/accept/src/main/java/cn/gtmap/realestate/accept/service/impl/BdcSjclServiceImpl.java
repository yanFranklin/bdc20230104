package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcSjclService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcBahtQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.etl.RudongFcjyDataFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/29
 * @description 不动产受理收件材料服务
 */
@Service
@Validated
public class BdcSjclServiceImpl implements BdcSjclService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSjclServiceImpl.class);

    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    BdcSlSjclPzService bdcSlSjclPzService;
    @Autowired
    BdcDjyySjclPzService bdcDjyySjclPzService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    EntityService entityService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlYwlzService bdcSlYwlzService;

    @Autowired
    BdcSlSqrService bdcSlSqrService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;


    @Value("${slbhscfs.version:}")
    private String version;

    @Value("${sjcl.gxys:true}")
    private boolean sfgxys;

    @Value("${zhlc.splitsjcl:false}")
    private boolean splitSjcl;

    @Value("#{'${sjcl.extendYxm.exceptclmc:}'.split(',')}")
    private List<String> exceptSjclmcList;

    /**
     * 用于判断重新创建收件材料时是否删除全部收件材料
     */
    @Value("${sjcl.recreate.deleteAll: true}")
    private boolean recreateSjclDeleteAll;

    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    @Autowired
    EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;

    @Autowired
    RudongFcjyDataFeignService rudongFcjyDataFeignService;

    @Value("${sjcl.sfUploadHtbaFjcl:false}")
    private boolean sfUploadHtbaFjcl;

    /**
     * 从第三方获取获取委托信息文件的固定token
     */
    @Value("${sjcl.wtxxToken:}")
    private String wtxxToken;

    /**
     * 从第三方获取获取委托信息文件的文件夹名称
     */
    @Value("${sjcl.wtxxWjjmc: app委托材料}")
    private String wtxxWjjmc;

    /**
     * 登记原因有值,登记原因没有收件材料，是否根据登记小类生成收件材料
     */
    @Value("${sjcl.djyynocl.sfcjsjcl:true}")
    private boolean sfcjsjcl;

    @Value("${sjcl.yzsfbc:false}")
    private boolean sjclSfbc;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Override
    public List<BdcSlSjclDO> listCshBdcSlSjclByLc(String gzlslid, List<BdcSlXmDO> bdcSlXmDOList) {
        Map<String, String> djxlMap = new HashMap<>();
        List<BdcSlSjclPzDO> bdcSlSjclPzDOList = new ArrayList<>();
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        Map<String, List<BdcSlSjclPzDO>> zhlcSjclMap = new HashMap<>(1);
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            if (!djxlMap.containsKey(bdcSlXmDO.getDjxl())) {
                List<BdcSlSjclPzDO> slSjclPzDOList = new ArrayList<>();
                //不存在该登记小类，生成新的收件材料，否则不生成
                djxlMap.put(bdcSlXmDO.getDjxl(), bdcSlXmDO.getDjxl());
                //合肥地区根据登记原因切换收件材料
                if (!StringUtils.equals(Constants.VERSION_NT, version)) {
                    // 根据登记原因获取收件材料
                    slSjclPzDOList = this.getScjlbyDjyy(bdcSlXmDO);
                    if (splitSjcl && (CommonConstantUtils.LCLX_ZH.equals(lclx) ||CommonConstantUtils.LCLX_PLZH.equals(lclx))) {
                        //配置了区分收件材料的组合流程实现逻辑--暂时不考虑相同登记小类下的组合流程。后续优化为增加顺序号作为区分
                        //1.用登记小类名称创建两个文件夹
                        if (StringUtils.isNotBlank(bdcSlXmDO.getDjxl())) {
                            zhlcSjclMap.put(bdcSlXmDO.getDjxl(), slSjclPzDOList);
                        }
                    }
                } else {
                    slSjclPzDOList = bdcSlSjclPzService.listBdcSlSjclPzByDjxl(bdcSlXmDO.getDjxl());
                }
                if (CollectionUtils.isNotEmpty(slSjclPzDOList)) {
                    bdcSlSjclPzDOList.addAll(slSjclPzDOList);
                }
            }
        }

        if (sfUploadHtbaFjcl) {
            try {
                uploadNtHtbaxxFjcl(bdcSlXmDOList.get(0).getXmid(), gzlslid);
            } catch (Exception e) {
                LOGGER.error("上传合同备案附件材料异常", e);
            }
        }

        if (MapUtils.isNotEmpty(zhlcSjclMap)) {
            return createZhSjcl(zhlcSjclMap, gzlslid);
        }
        return createSjcl(bdcSlSjclPzDOList, gzlslid);
    }

    private List<BdcSlSjclPzDO> getScjlbyDjyy(BdcSlXmDO bdcSlXmDO){
        List<BdcSlSjclPzDO> slSjclPzDOList = new ArrayList<>();
        String djyy = "";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcSlXmDO.getXmid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            djyy = bdcXmDO.getDjyy();
        } else {
            djyy = bdcSlXmDO.getDjyy();
        }
        if (StringUtils.isNotBlank(djyy)) {
            BdcDjyySjclPzQO bdcDjyySjclPzQO = new BdcDjyySjclPzQO();
            bdcDjyySjclPzQO.setDjxl(bdcSlXmDO.getDjxl());
            bdcDjyySjclPzQO.setDjyy(djyy);
            if (sjclSfbc) {
                bdcDjyySjclPzQO.setSfbc(CommonConstantUtils.SF_S_DM);
            }
            List<BdcDjyySjclPzDO> bdcDyjjSjclPzDOList = bdcDjyySjclPzService.querySjclPz(bdcDjyySjclPzQO);
            if (CollectionUtils.isNotEmpty(bdcDyjjSjclPzDOList)) {
                for (BdcDjyySjclPzDO bdcDjyySjclPzDO : bdcDyjjSjclPzDOList) {
                    BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
                    entityZdConvertUtils.convertEntityToMc(bdcDjyySjclPzDO);
                    BeanUtils.copyProperties(bdcDjyySjclPzDO, bdcSlSjclPzDO);
                    slSjclPzDOList.add(bdcSlSjclPzDO);
                }
            }
        }
        //未获取到登记小类登记原因的配置还是读取默认配置
        if(CollectionUtils.isEmpty(slSjclPzDOList) &&(StringUtils.isBlank(djyy) ||sfcjsjcl)){
            slSjclPzDOList = bdcSlSjclPzService.listBdcSlSjclPzByDjxl(bdcSlXmDO.getDjxl());
        }
        return slSjclPzDOList;
    }

    @Override
    public Integer updateSjclYs(String gzlslid, String xmid) {
        Integer count = 0;
        String spaceId = StringUtils.isBlank(xmid) ? gzlslid : xmid;
        List<StorageDto> storageDtoList = storageClient.listAllRootStorages(Constants.WJZX_CLIENTID, spaceId, null, null, 1, 0, 1,null);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            for (StorageDto storage : storageDtoList) {
                BdcSlSjclDO bdcSlSjcl = bdcSlSjclService.queryBdcSlSjclByWjzxid(storage.getId());
                if (Objects.nonNull(bdcSlSjcl) && Objects.nonNull(storage.getFileCount())) {
                    if (sfgxys) {
                        bdcSlSjcl.setYs(storage.getFileCount().intValue());
                    }
                    //重命名
                    bdcSlSjcl.setClmc(storage.getName());
                    count += bdcSlSjclService.updateBdcSlSjcl(bdcSlSjcl);
                }
            }
        }
        return count;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新组合流程收件材料
     * @date : 2021/12/20 13:50
     */
    @Override
    public Integer updateZhlcSjclYs(String gzlslid) {
        Integer count = 0;
        //查的是根目录的文件夹名称，组合流程就是登记小类的名称
        List<StorageDto> storageDtoList = storageClient.listAllRootStorages(Constants.WJZX_CLIENTID, gzlslid, null, null, 1, 0, 1, null);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            for (StorageDto storage : storageDtoList) {
                List<StorageDto> subFuileList = storageClient.listAllSubsetStorages(storage.getId(), "", 1, null, 1, null);
                for (StorageDto storageDto : subFuileList) {
                    BdcSlSjclDO bdcSlSjcl = bdcSlSjclService.queryBdcSlSjclByWjzxid(storageDto.getId());
                    if (Objects.nonNull(bdcSlSjcl) && Objects.nonNull(storageDto.getFileCount()) && !Objects.equals(0, storageDto.getFileCount().intValue())) {
                        if (sfgxys) {
                            bdcSlSjcl.setYs(storageDto.getFileCount().intValue());
                        }
                        //重命名
                        bdcSlSjcl.setClmc(storageDto.getName());
                        count += bdcSlSjclService.updateBdcSlSjcl(bdcSlSjcl);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public Integer changeSjclSxh(String sjclid, String czlx) {
        Integer count = 0;
        List<BdcSlSjclDO> bdcSlSjclDOS = new ArrayList<>();
        BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
        if (bdcSlSjclDO != null) {
            List<BdcSlSjclDO> bdcSlSjclList = null;
            if (StringUtils.isNotBlank(bdcSlSjclDO.getGzlslid())) {
                bdcSlSjclList = bdcSlSjclService.listBdcSlSjclByGzlslid(bdcSlSjclDO.getGzlslid());
            }
            if (CollectionUtils.isNotEmpty(bdcSlSjclList) && bdcSlSjclList.size() > 1) {
                for (int i = 0; i < bdcSlSjclList.size(); i++) {
                    BdcSlSjclDO bdcSlSjcl = bdcSlSjclList.get(i);
                    if (StringUtils.equals(bdcSlSjcl.getSjclid(), sjclid)) {
                        BdcSlSjclDO changeBdcSlSjcl = null;
                        if (StringUtils.equals(czlx, Constants.SXH_UP) && i != 0) {
                            changeBdcSlSjcl = bdcSlSjclList.get(i - 1);
                        }
                        if (StringUtils.equals(czlx, Constants.SXH_DOWN) && i != (bdcSlSjclList.size() - 1)) {
                            changeBdcSlSjcl = bdcSlSjclList.get(i + 1);
                        }
                        if (changeBdcSlSjcl != null && bdcSlSjclDO.getXh() != null && changeBdcSlSjcl.getXh() != null) {
                            int sxh1 = bdcSlSjclDO.getXh();
                            int sxh2 = changeBdcSlSjcl.getXh();
                            bdcSlSjclDO.setXh(sxh2);
                            changeBdcSlSjcl.setXh(sxh1);
                            bdcSlSjclDOS.add(bdcSlSjclDO);
                            bdcSlSjclDOS.add(changeBdcSlSjcl);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
            for (BdcSlSjclDO slSjclDO : bdcSlSjclDOS) {
                count += bdcSlSjclService.updateBdcSlSjcl(slSjclDO);
            }
        }
        return count;
    }

    @Override
    public Integer saveSjcl(String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        String userId = "0";
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            userId = userDto.getId();
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (!StringUtils.isNotBlank(obj.get("sjclid").toString())) {
                count += insertBdcSlSjcl(JSONObject.toJSONString(obj), userId);
            } else {
                count += updateBdcSlSjcl(JSONObject.toJSONString(obj));
            }
        }
        return count;
    }

    /**
     * @param json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 保存收件材料
     */
    public Integer updateBdcSlSjcl(String json) {
        BdcSlSjclDO bdcSlSjclDO = JSONObject.parseObject(json, BdcSlSjclDO.class);
        if (StringUtils.isNotBlank(bdcSlSjclDO.getClmc())) {
            BdcSlSjclDO bdcSlSjcl = bdcSlSjclService.queryBdcSlSjclBySjclid(bdcSlSjclDO.getSjclid());
            if (bdcSlSjcl != null && !StringUtils.equals(bdcSlSjcl.getClmc(), bdcSlSjclDO.getClmc())) {
                storageClient.rename(bdcSlSjclDO.getWjzxid(), bdcSlSjclDO.getClmc());
            }
        }
        return entityService.updateByJsonEntity(json, BdcSlSjclDO.class);
    }

    /**
     * @param json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 保存收件材料
     */
    public Integer insertBdcSlSjcl(String json, String userId) {
        BdcSlSjclDO bdcSlSjclDO = JSONObject.parseObject(json, BdcSlSjclDO.class);
        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, StringUtils.isBlank(bdcSlSjclDO.getXmid()) ? bdcSlSjclDO.getGzlslid() : bdcSlSjclDO.getXmid(), bdcSlSjclDO.getClmc(), userId);
        bdcSlSjclDO.setWjzxid(storageDto.getId());
        if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
            bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        }
        return entityMapper.insertSelective(bdcSlSjclDO);
    }

    /**
     * @param bdcSlSjclDO 新增的收件材料信息
     * @param userId 用户ID
     * @return 收件材料信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增收件材料，返回收件材料信息
     */
    public BdcSlSjclDO insertSjcl(BdcSlSjclDO bdcSlSjclDO, String userId) {
        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, StringUtils.isBlank(bdcSlSjclDO.getXmid()) ? bdcSlSjclDO.getGzlslid() : bdcSlSjclDO.getXmid(), bdcSlSjclDO.getClmc(), userId);
        bdcSlSjclDO.setWjzxid(storageDto.getId());
        if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
            bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        }
        entityMapper.insertSelective(bdcSlSjclDO);
        return bdcSlSjclDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSjcl(String sjclid) {
        BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
        if (bdcSlSjclDO != null) {
            List<String> wjzxidList = new ArrayList<>();
            LOGGER.warn("流程实例id{} 附件名称{} 删除收件材料和附件wjzxid={}", bdcSlSjclDO.getGzlslid(), bdcSlSjclDO.getClmc(), bdcSlSjclDO.getWjzxid());
            if (StringUtils.isNotBlank(bdcSlSjclDO.getWjzxid())) {
                wjzxidList.add(bdcSlSjclDO.getWjzxid());
                storageClient.deleteStatus(Constants.WJZX_CLIENTID, bdcSlSjclDO.getGzlslid(), wjzxidList);
            }
            bdcSlSjclService.deleteBdcSlSjclBySjclid(sjclid);
            if (bdcSlSjclDO.getXh() == null) {
                LOGGER.info("{}：处理收件材料序号中止，原因：序号为空！", sjclid);
                return;
            }
            List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
            if (StringUtils.isNotBlank(bdcSlSjclDO.getGzlslid())) {
                bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(bdcSlSjclDO.getGzlslid());
            }
            for (BdcSlSjclDO bdcSlSjcl : bdcSlSjclDOList) {
                if (bdcSlSjcl.getXh() != null && bdcSlSjcl.getXh() > bdcSlSjclDO.getXh()) {
                    bdcSlSjcl.setXh(bdcSlSjcl.getXh() - 1);
                    bdcSlSjclService.updateBdcSlSjcl(bdcSlSjcl);
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/12 16:53
     */
    @Override
    public void deleteAllSjcl(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //        大云新接口全部删除收件材料，传spaceid就行
            storageClient.deleteStatus(Constants.WJZX_CLIENTID, gzlslid, Collections.emptyList());
            //删除收件材料
            Example example = new Example(BdcSlSjclDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.deleteByExample(example);
        }
    }

    /**
     * @param gzlslid    工作流实例id
     * @param sjclIdsStr
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid，gzlslid，dylx 获取收件材料名称
     */
    @Override
    public List<String> listSjclMc(String gzlslid, String sjclIdsStr) {
        List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
        List<String> sjclList = new ArrayList<>();

        // 先根据材料ID查询获取材料信息
        if (StringUtils.isNotBlank(sjclIdsStr)) {
            List<String> sjclidList = Arrays.asList(sjclIdsStr.split(CommonConstantUtils.ZF_YW_DH));
            for (String sjclid : sjclidList) {
                BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
                if (bdcSlSjclDO != null) {
                    sjclList.add(bdcSlSjclDO.getClmc());
                }
            }
            return sjclList;
        }

        // 获取流程的收件材料信息
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        }
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                sjclList.add(bdcSlSjclDO.getClmc());
            }
        }
        return sjclList;
    }

    @Override
    public Boolean checkSjclYsFs(String gzlslid) {
        Boolean yzpass = true;
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                if (bdcSlSjclDO.getFs() == null || bdcSlSjclDO.getFs() == 0 || bdcSlSjclDO.getYs() == null || bdcSlSjclDO.getYs() == 0) {
                    yzpass = false;
                    break;
                }
            }
        }
        return yzpass;
    }

    @Override
    public void saveDsfSjcl(String gzlslid, List<BdcDsfSjclDTO> bdcDsfSjclDTOList) throws IOException {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        if (CollectionUtils.isNotEmpty(bdcDsfSjclDTOList)) {
            // 上传后根据文件名称和工作流实例id查找当前流程是否存在相同文件数据
            for (BdcDsfSjclDTO bdcDsfSjclDTO : bdcDsfSjclDTOList) {
                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, bdcDsfSjclDTO.getClmc());
                LOGGER.info("gzlslid:{}保存第三方收件材料获取文件夹{}",gzlslid,CollectionUtils.size(bdcSlSjclDOList));
                if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                    bdcSlSjclDO = bdcSlSjclDOList.get(0);
                }
                BeanUtils.copyProperties(bdcDsfSjclDTO, bdcSlSjclDO);
                bdcSlSjclDO.setGzlslid(gzlslid);
                UserDto userDto = userManagerUtils.getCurrentUser();
                String userId = "";
                if (userDto != null) {
                    userId = userDto.getId();
                }
                //新建文件夹
                StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, bdcSlSjclDO.getClmc(), userId);
                if (storageDto != null) {
                    bdcSlSjclDO.setWjzxid(storageDto.getId());
                    //上传文件
                    if (StringUtils.isNotBlank(bdcDsfSjclDTO.getBase64fj())) {
                        MultipartFile file = Base64Utils.base64ToMultipart(bdcDsfSjclDTO.getBase64fj());
                        MultipartDto multipartDto = getUploadParamDto(userDto != null ? userDto.getUsername() : "", storageDto, file, bdcDsfSjclDTO.getFjmc());
                        storageClient.multipartUpload(multipartDto);
                    }
                    if (StringUtils.isNotBlank(bdcDsfSjclDTO.getBytes())) {
                        MultipartFile file = Base64Utils.base64ToMultipart(bdcDsfSjclDTO.getBytes());
                        MultipartDto multipartDto = getUploadParamDto(userDto != null ? userDto.getUsername() : "", storageDto, file, bdcDsfSjclDTO.getFjmc());
                        storageClient.multipartUpload(multipartDto);
                    }
                    if (CollectionUtils.isNotEmpty(bdcDsfSjclDTO.getBase64fjList())) {
                        for (String fj : bdcDsfSjclDTO.getBase64fjList()) {
                            MultipartFile file = Base64Utils.base64ToMultipart(fj);
                            MultipartDto multipartDto = getUploadParamDto(userDto != null ? userDto.getUsername() : "", storageDto, file, bdcDsfSjclDTO.getFjmc());
                            storageClient.multipartUpload(multipartDto);
                        }
                    }
                }
                LOGGER.info("需要保存的第三方附件信息{}", JSONObject.toJSONString(bdcSlSjclDO));
                if (StringUtils.isNotBlank(bdcSlSjclDO.getSjclid())) {
                    bdcSlSjclService.updateBdcSlSjcl(bdcSlSjclDO);
                } else {
                    bdcSlSjclService.insertBdcSlSjcl(bdcSlSjclDO);
                }
            }

        }

    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据登记原因重新创建收件材料
     * @date : 2019/12/12 17:47
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BdcSlSjclDO> reCreateSjcl(String gzlslid) {
        //1.先根据gzlslid删除流程所有的收件材料
        /**
         * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
         * @description 需求29427  不需要把之前生成的收件材料删除
         * 先判断当前流程是否已经上传了收件材料，如果存在上传的材料，不清空原有的信息
         * ------------ 获取配置的收件材料，跟流程中的收件材料根据名称比对，如果存在不再新增
         * @date : 2020/5/27 16:23
         */
        //2. 根据登记小类和登记原因创建新的收件材料
        Map<String, String> djxlMap = new HashMap<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcSlSjclPzDO> bdcSlSjclPzDOList = new ArrayList<>();
        Map<String, List<BdcSlSjclPzDO>> zhlcSjclMap = new HashMap<>(1);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (!djxlMap.containsKey(bdcXmDO.getDjxl())) {
                    List<BdcSlSjclPzDO> slSjclPzDOList = new ArrayList<>();
                    List<BdcSlSjclPzDO> zhSjclPzList = new ArrayList<>();
                    //不存在该登记小类，生成新的收件材料，否则不生成
                    djxlMap.put(bdcXmDO.getDjxl(), bdcXmDO.getDjxl());
                    //根据保存后的登记小类和登记原因去获取收件材料
                    BdcDjyySjclPzQO bdcDjyySjclPzQO = new BdcDjyySjclPzQO();
                    bdcDjyySjclPzQO.setDjxl(bdcXmDO.getDjxl());
                    if (StringUtils.isNotBlank(bdcXmDO.getDjyy())) {
                        bdcDjyySjclPzQO.setDjyy(bdcXmDO.getDjyy());
                    }
                    if (sjclSfbc) {
                        bdcDjyySjclPzQO.setSfbc(CommonConstantUtils.SF_S_DM);
                    }
                    //走重新创建不管是否配置了登记原因和收件材料关系，都去判断删除大云数据
                    List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList = bdcDjyySjclPzService.querySjclPz(bdcDjyySjclPzQO);
                    if (recreateSjclDeleteAll) {
                        List<StorageDto> storageDtoList = storageClient.queryMenus("clientId", gzlslid, null, 1, 1, null, null, null);
                        LOGGER.info("重新生成收件材料时，文件中心已存在文件的数量{},工作流实例id{}", CollectionUtils.size(storageDtoList), gzlslid);
                        if (CollectionUtils.isEmpty(storageDtoList)) {
                            deleteAllSjcl(gzlslid);
                        }
                    } else {
                        //不删除所有的，判断每个文件夹是否有文件，有文件不删除，没有文件删除
                        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                        bdcSlSjclQO.setGzlslid(gzlslid);
                        bdcSlSjclQO.setDjxl(bdcXmDO.getDjxl());
                        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                                List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), "", 1, null, null, null);
                                if (CollectionUtils.isEmpty(storageDtoList)) {
                                    LOGGER.warn("改变登记原因，当前流程{}，文件夹名称：{}下不存在文件，执行删除", bdcSlSjclDO.getGzlslid(), bdcSlSjclDO.getClmc());
                                    deleteSjcl(bdcSlSjclDO.getSjclid());
                                }
                            }
                        }
                    }
                    //未获取到登记小类登记原因的配置还是读取默认配置
                    if (CollectionUtils.isNotEmpty(bdcDjyySjclPzDOList)) {
                        for (BdcDjyySjclPzDO bdcDjyySjclPzDO : bdcDjyySjclPzDOList) {
                            BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
                            entityZdConvertUtils.convertEntityToMc(bdcDjyySjclPzDO);
                            BeanUtils.copyProperties(bdcDjyySjclPzDO, bdcSlSjclPzDO);
                            slSjclPzDOList.add(bdcSlSjclPzDO);
                        }
                    } else {
                        slSjclPzDOList = bdcSlSjclPzService.listBdcSlSjclPzByDjxl(bdcXmDO.getDjxl());
                        LOGGER.warn("当前流程{}改变登记原因{}未找到新的登记原因收件材料配置，查询登记小类登记原因配置{}", gzlslid, bdcXmDO.getDjyy(), JSON.toJSONString(slSjclPzDOList));
                    }
                    // 查询当前流程所有的收件材料
                    List<String> lcSjclMcList = this.listSjclMc(gzlslid, "");
                    if (splitSjcl && (CommonConstantUtils.LCLX_ZH.equals(lclx) ||CommonConstantUtils.LCLX_PLZH.equals(lclx))) {
                        //查询当前登记小类下是否有相同文件夹
                        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                        bdcSlSjclQO.setDjxl(bdcXmDO.getDjxl());
                        bdcSlSjclQO.setGzlslid(gzlslid);
                        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                            lcSjclMcList = bdcSlSjclDOList.stream().map(BdcSlSjclDO::getClmc).collect(Collectors.toList());
                        }
                    }
                    //后生成的材料排序跟在已生成的后面
                    Integer sxh = 0;
                    if(CollectionUtils.isNotEmpty(lcSjclMcList)){
                        sxh = lcSjclMcList.size();
                    }
                    if (CollectionUtils.isNotEmpty(slSjclPzDOList)) {
                        for (BdcSlSjclPzDO bdcSlSjclPzDO : slSjclPzDOList) {
                            //当前流程不包含配置的文件名称
                            if (!lcSjclMcList.contains(bdcSlSjclPzDO.getClmc())) {
                                sxh++;
                                bdcSlSjclPzDO.setXh(sxh);
                                bdcSlSjclPzDOList.add(bdcSlSjclPzDO);
                                zhSjclPzList.add(bdcSlSjclPzDO);
                            }
                        }
                    }
                    if (splitSjcl && (CommonConstantUtils.LCLX_ZH.equals(lclx) ||CommonConstantUtils.LCLX_PLZH.equals(lclx))) {
                        //配置了区分收件材料的组合流程实现逻辑--暂时不考虑相同登记小类下的组合流程。后续优化为增加顺序号作为区分
                        if (StringUtils.isNotBlank(bdcXmDO.getDjxl())) {
                            zhlcSjclMap.put(bdcXmDO.getDjxl(), zhSjclPzList);
                        }
                    }
                }
            }
        }
        if (MapUtils.isNotEmpty(zhlcSjclMap)) {
            return createZhSjcl(zhlcSjclMap, gzlslid);
        }
        return createSjcl(bdcSlSjclPzDOList, gzlslid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private static MultipartDto getUploadParamDto(String userName, StorageDto storageDto, MultipartFile file, String fileName) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        if (file != null) {
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(userName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(StringUtils.isNotBlank(fileName) ? fileName : file.getOriginalFilename());
            multipartDto.setName(StringUtils.isNotBlank(fileName) ? fileName : file.getName());
        }
        return multipartDto;
    }

    private List<BdcSlSjclDO> createSjcl(List<BdcSlSjclPzDO> bdcSlSjclPzDOList, String gzlslid) {
        LOGGER.warn("流程实例id{}材料开始创建，配置信息{}", gzlslid, JSON.toJSONString(bdcSlSjclPzDOList));
        List<BdcSlSjclDO> bdcSlSjclList = new ArrayList<>();
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = "";
        if (userDto != null && StringUtils.isNotBlank(userDto.getId())) {
            userid = userDto.getId();
        }
        Map<String, BdcSlSjclPzDO> clmcMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcSlSjclPzDOList)) {
            StringBuilder clmcs =new StringBuilder();
            for (BdcSlSjclPzDO bdcSlSjclPzDO : bdcSlSjclPzDOList) {
                //材料名称不允许为空
                if (StringUtils.isNotBlank(bdcSlSjclPzDO.getClmc())) {
                    //合并取其中一个配置即可,无需合并份数
                    //去除前后空格
                    bdcSlSjclPzDO.setClmc(StringUtils.trim(bdcSlSjclPzDO.getClmc()));
                    if (!clmcMap.containsKey(bdcSlSjclPzDO.getClmc())) {
                        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, bdcSlSjclPzDO.getClmc());
                        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                            if (StringUtils.isBlank(clmcs)) {
                                clmcs.append(bdcSlSjclPzDO.getClmc());
                            } else {
                                clmcs.append(CommonConstantUtils.ZF_YW_DH).append(bdcSlSjclPzDO.getClmc());
                            }
                            clmcMap.put(bdcSlSjclPzDO.getClmc(), bdcSlSjclPzDO);
                        }
                    }
                }

            }
            if(StringUtils.isNotBlank(clmcs)) {
                LOGGER.warn("流程实例id{}开始创建storage文件夹,名称为{}", gzlslid, clmcs.toString());
                List<StorageDto> storageDtoList = storageClient.createRootFolderMulti(Constants.WJZX_CLIENTID, gzlslid, clmcs.toString(), userid);
                if(CollectionUtils.isNotEmpty(storageDtoList)){
                    for(StorageDto storageDto:storageDtoList) {
                        bdcSlSjclList.add(generateSjclDo(clmcMap.get(storageDto.getName()), storageDto, gzlslid));
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcSlSjclList)) {
                    entityMapper.insertBatchSelective(bdcSlSjclList);
                }
            }

        }
        return bdcSlSjclList;
    }

    private List<BdcSlSjclDO> createZhSjcl(Map<String, List<BdcSlSjclPzDO>> zhSjclMap, String gzlslid) {
        LOGGER.warn("组合流程实例id{}分开材料开始创建，配置信息{}", gzlslid, JSON.toJSONString(zhSjclMap));
        List<BdcSlSjclDO> bdcSlSjclList = new ArrayList<>(1);
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userid = userDto.getId();
        }
        if (MapUtils.isNotEmpty(zhSjclMap)) {
            List<Map> zdList = bdcZdFeignService.queryBdcZd("djxl");
            for (Map.Entry<String, List<BdcSlSjclPzDO>> entry : zhSjclMap.entrySet()) {
                String parentFolder = StringToolUtils.convertBeanPropertyValueOfZdByString(entry.getKey(), zdList);
                StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, parentFolder, userid);
                Map<String, BdcSlSjclPzDO> clmcMap = new HashMap<>(1);
                BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                bdcSlSjclQO.setDjxl(entry.getKey());
                bdcSlSjclQO.setGzlslid(gzlslid);
                StringBuilder clmcs =new StringBuilder();
                for (BdcSlSjclPzDO bdcSlSjclPzDO : entry.getValue()) {
                    //材料名称不允许为空
                    if (StringUtils.isNotBlank(bdcSlSjclPzDO.getClmc())) {
                        //合并取其中一个配置即可,无需合并份数
                        bdcSlSjclPzDO.setClmc(StringUtils.trim(bdcSlSjclPzDO.getClmc()));
                        if (!clmcMap.containsKey(bdcSlSjclPzDO.getClmc())) {
                            bdcSlSjclQO.setClmc(bdcSlSjclPzDO.getClmc());
                            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                                if(StringUtils.isBlank(clmcs)) {
                                    clmcs.append(bdcSlSjclPzDO.getClmc());
                                }else{
                                    clmcs.append(CommonConstantUtils.ZF_YW_DH).append(bdcSlSjclPzDO.getClmc());
                                }
                                clmcMap.put(bdcSlSjclPzDO.getClmc(), bdcSlSjclPzDO);
                            }
                        }
                    }
                }
                if(StringUtils.isNotBlank(clmcs)) {
                    LOGGER.warn("流程实例id{}开始创建storage文件夹,名称为{}", gzlslid, clmcs.toString());
                    List<StorageDto> childFolderList = storageClient.createFolderMulti(Constants.WJZX_CLIENTID, gzlslid, storageDto.getId(), clmcs.toString(), userid);
                    if (CollectionUtils.isNotEmpty(childFolderList)) {
                        for(StorageDto childFolder:childFolderList){
                            bdcSlSjclList.add(generateSjclDo(clmcMap.get(childFolder.getName()), childFolder, gzlslid));
                        }

                    }
                }
            }

            if (CollectionUtils.isNotEmpty(bdcSlSjclList)) {
                entityMapper.insertBatchSelective(bdcSlSjclList);
            }
        }
        return bdcSlSjclList;
    }

    private static BdcSlSjclDO generateSjclDo(BdcSlSjclPzDO bdcSlSjclPzDO, StorageDto storageDto, String gzlslid) {
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        BeanUtils.copyProperties(bdcSlSjclPzDO, bdcSlSjclDO);
        if (Objects.nonNull(storageDto) && StringUtils.isNotBlank(storageDto.getId())) {
            bdcSlSjclDO.setWjzxid(storageDto.getId());
        }
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setMrfs(bdcSlSjclPzDO.getMrfs());
        bdcSlSjclDO.setFs(bdcSlSjclPzDO.getMrfs());
        bdcSlSjclDO.setYs(bdcSlSjclPzDO.getMrfs());
        bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        bdcSlSjclDO.setDjxl(bdcSlSjclPzDO.getDjxl());
        return bdcSlSjclDO;
    }

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据受理编号查询不动产受理收件材料
     */
    @Override
    public List<BdcSlSjclDTO> listSjclBySlbh(String slbh) {
        List<BdcSlSjclDTO> listFjclDto = new ArrayList<>();
        if (StringUtils.isNotBlank(slbh)) {
            String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
            if (StringUtils.isNotBlank(gzlslid)) {
                List<BdcSlSjclDO> list = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
                LOGGER.info("查询收件材料slbh:{},材料集合：{}", slbh, list);
                if (CollectionUtils.isNotEmpty(list)) {
                    for (BdcSlSjclDO bdcSlSjclDO : list) {
                        BdcSlSjclDTO dto = new BdcSlSjclDTO();
                        BeanUtils.copyProperties(bdcSlSjclDO, dto);
                        List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, bdcSlSjclDO.getClmc(), 1, 0);
                        if (CollectionUtils.isNotEmpty(listFjcl)) {
                            String dz = StringToolUtils.resolveBeanToAppendStr(listFjcl, "getDownUrl", CommonConstantUtils.ZF_YW_DH);
                            dto.setSjcldz(StringUtils.isNotBlank(dz) ? dz : StringUtils.EMPTY);
                        }
                        listFjclDto.add(dto);
                    }
                }
                return listFjclDto;
            }
        } else {
            throw new MissingArgumentException("缺失参数！");
        }

        return new ArrayList<>();
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存税务税票附件
     */
    @Override
    public void saveSwspfj(List<BdcSwspFjDTO> bdcSwspFjDTOList, String gzlslid, String clmc) {
        List<BdcDsfSjclDTO> bdcDsfSjclDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSwspFjDTOList)) {
            List<String> fjList = new ArrayList<>();
            for (BdcSwspFjDTO bdcSwspFjDTO : bdcSwspFjDTOList) {
                if (StringUtils.isNotBlank(bdcSwspFjDTO.getFile()) && StringUtils.isNotBlank(bdcSwspFjDTO.getPzhm())) {
                    if (StringUtils.isNotBlank(bdcSwspFjDTO.getBase64qz())) {
                        String base64Fj = bdcSwspFjDTO.getBase64qz() + bdcSwspFjDTO.getFile();
                        fjList.add(base64Fj);
                    } else {
                        fjList.add(bdcSwspFjDTO.getFile());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(fjList)) {
                BdcDsfSjclDTO bdcDsfSjclDTO = new BdcDsfSjclDTO();
                bdcDsfSjclDTO.setClmc(clmc);
                bdcDsfSjclDTO.setBase64fjList(fjList);
                bdcDsfSjclDTOList.add(bdcDsfSjclDTO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcDsfSjclDTOList)) {
            try {
                saveDsfSjcl(gzlslid, bdcDsfSjclDTOList);
            } catch (Exception e) {
                LOGGER.error("保存税票附件异常:{}", e);
            }
        }
    }

    /**
     * @param json 收件材料信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/3/29 15:38
     */
    @Override
    public Integer saveZhSjcl(String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        String userId = "0";
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            userId = userDto.getId();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (!StringUtils.isNotBlank(obj.get("sjclid").toString())) {
                count += insertZhSjcl(JSONObject.toJSONString(obj), userId);
            } else {
                count += updateBdcSlSjcl(JSONObject.toJSONString(obj));
            }
        }
        return count;
    }

    /**
     * @param sjclUploadDTO 收件材料内容， 文件夹名称， 登记小类， 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建收件材料并上传，受理库没有对应文件夹则新增文件夹
     * @date : 2021/5/11 14:20
     */
    @Override
    public void createAndUploadFile(SjclUploadDTO sjclUploadDTO) throws IOException {
        LOGGER.info("上传收件材料并创建文件夹信息{}", JSON.toJSONString(sjclUploadDTO));
        //1.判断受理是否存在该文件夹，没有新增
        UserDto userDto = userManagerUtils.getCurrentUser();
        String wjzxid = "";
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(sjclUploadDTO.getGzlslid(), sjclUploadDTO.getSjclmc());
        //新建文件夹
        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, sjclUploadDTO.getGzlslid(), sjclUploadDTO.getSjclmc(), userDto != null ? userDto.getId() : "");
        if (storageDto != null) {
            wjzxid = storageDto.getId();
            //上传文件
            if (StringUtils.isNotBlank(sjclUploadDTO.getSjclnr())) {
                MultipartFile file = Base64Utils.base64ToMultipart(sjclUploadDTO.getSjclnr());
                MultipartDto multipartDto = getUploadParamDto(userDto != null ? userDto.getUsername() : "", storageDto, file, "");
                storageClient.multipartUpload(multipartDto);
            }
            //批量文件信息
            if (CollectionUtils.isNotEmpty(sjclUploadDTO.getFjList())) {
                for (TsswDataFjclXxDTO tsswDataFjclXxDTO : sjclUploadDTO.getFjList()) {
                    if (StringUtils.isNotBlank(tsswDataFjclXxDTO.getBase64())) {
                        MultipartFile file = Base64Utils.base64ToMultipart(tsswDataFjclXxDTO.getBase64());
                        MultipartDto multipartDto = getUploadParamDto(userDto != null ? userDto.getUsername() : "", storageDto, file, tsswDataFjclXxDTO.getFjmc());
                        storageClient.multipartUpload(multipartDto);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
            bdcSlSjclDO.setClmc(sjclUploadDTO.getSjclmc());
            bdcSlSjclDO.setGzlslid(sjclUploadDTO.getGzlslid());
            bdcSlSjclDO.setDjxl(sjclUploadDTO.getDjxl());
            bdcSlSjclDO.setFs(1);
            bdcSlSjclDO.setYs(1);
            bdcSlSjclDO.setSjlx(1);
            bdcSlSjclDO.setWjzxid(wjzxid);
            bdcSlSjclService.insertBdcSlSjcl(bdcSlSjclDO);
        }
    }

    /**
     * @param sjclJson@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料
     * @date : 2021/7/12 14:24
     */
    @Override
    public void copySjcl(String sjclJson) {
        JSONObject jsonObject = JSONObject.parseObject(sjclJson);
        //当前流程工作流实例id
        String gzlslid = jsonObject.getString("gzlslid");
        // 登记小类
        String djxl = jsonObject.getString("djxl");
        //需要复制的收件材料的id
        JSONArray sjclIdArray = jsonObject.getJSONArray("sjclIdList");
        LOGGER.info("流程：{}执行复制收件材料操作：对应收件材料id:{}",gzlslid,sjclIdArray);
        UserDto userDto = userManagerUtils.getUserDto();
        if (StringUtils.isNotBlank(gzlslid) && CollectionUtils.isNotEmpty(sjclIdArray)) {
            List<String> sjclidList = sjclIdArray.toJavaList(String.class);
            for (String sjclid : sjclidList) {
                //复制逻辑根据sjclid查询收件材料，根据名称和工作流实例id判断当前流程是否能存在相同文件夹，存在把大云的文件夹下的收件材料复制过来，不存在先新增，再复制
                BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
                //根据名称判断当前流程是否存在该文件夹，存在直接复制文件
                if (Objects.nonNull(bdcSlSjclDO)) {
                    List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, bdcSlSjclDO.getClmc());
                    BdcSlSjclDO bdcSlSjcl = new BdcSlSjclDO();
                    if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                        //不存在复制当前数据新增
                        BeanUtils.copyProperties(bdcSlSjclDO, bdcSlSjcl);
                        bdcSlSjcl.setWjzxid("");
                        bdcSlSjcl.setGzlslid(gzlslid);
                        bdcSlSjcl.setSjclid(UUIDGenerator.generate16());
                        bdcSlSjcl.setDjxl(djxl);
                        bdcSlSjcl = insertSjcl(bdcSlSjcl, Objects.nonNull(userDto) ? userDto.getId() : "");
                    } else {
                        //存在直接使用当前数据
                        bdcSlSjcl = bdcSlSjclDOList.get(0);
                    }
                    List<StorageDto> storageFjList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), null, null, 1, null,null);
                    if (CollectionUtils.isNotEmpty(storageFjList)) {
                        for (StorageDto storageDto : storageFjList) {
                            //先下载之前的文件
                            MultipartDto multipartDto = storageClient.download(storageDto.getId());
                            //上传文件到当前的目录
                            multipartDto.setNodeId(bdcSlSjcl.getWjzxid());
                            storageClient.multipartUpload(multipartDto);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param sjclJson@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料
     * @date : 2021/7/12 14:24
     */
    @Override
    public void copyzhSjcl(String sjclJson) {
        JSONObject jsonObject = JSONObject.parseObject(sjclJson);
        // 当前流程工作流实例id
        String gzlslid = jsonObject.getString("gzlslid");
        // 登记小类
        String djxl = jsonObject.getString("djxl");
        // 字典转换登记小类名称
        List<Map> djxlZdList = bdcZdFeignService.queryBdcZd("djxl");
        String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(djxl, djxlZdList);
        // 需要复制的收件材料的id
        JSONArray sjclIdArray = jsonObject.getJSONArray("sjclIdList");
        LOGGER.info("流程：{}执行复制收件材料操作：对应收件材料id:{}",gzlslid,sjclIdArray);
        UserDto userDto = userManagerUtils.getUserDto();
        String userid = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userid = userDto.getId();
        }
        if (StringUtils.isNotBlank(gzlslid) && CollectionUtils.isNotEmpty(sjclIdArray)) {
            List<String> sjclidList = sjclIdArray.toJavaList(String.class);
            for (String sjclid : sjclidList) {
                //复制逻辑根据sjclid查询收件材料，根据名称和工作流实例id判断当前流程是否能存在相同文件夹，存在把大云的文件夹下的收件材料复制过来，不存在先新增，再复制
                BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid(sjclid);
                //根据名称判断当前流程是否存在该文件夹，存在直接复制文件
                if (Objects.nonNull(bdcSlSjclDO)) {
                    BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                    bdcSlSjclQO.setDjxl(djxl);
                    bdcSlSjclQO.setClmc(bdcSlSjclDO.getClmc());
                    bdcSlSjclQO.setGzlslid(gzlslid);
                    List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                    BdcSlSjclDO bdcSlSjcl = new BdcSlSjclDO();
                    if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                        //不存在复制当前数据新增
                        BeanUtils.copyProperties(bdcSlSjclDO, bdcSlSjcl);
                        bdcSlSjcl.setWjzxid("");
                        bdcSlSjcl.setGzlslid(gzlslid);
                        bdcSlSjcl.setSjclid(UUIDGenerator.generate16());
                        bdcSlSjcl.setDjxl(bdcSlSjclQO.getDjxl());

                        StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, djxlmc, userid);
                        //材料名称不允许为空
                        if (StringUtils.isNotBlank(bdcSlSjclDO.getClmc())) {
                            StorageDto childFolder = storageClient.createFolder(Constants.WJZX_CLIENTID, gzlslid, storageDto.getId(), bdcSlSjclDO.getClmc(), userid);
                            bdcSlSjcl.setWjzxid(childFolder.getId());
                        }
                        if (StringUtils.isBlank(bdcSlSjcl.getSjclid())) {
                            bdcSlSjcl.setSjclid(UUIDGenerator.generate16());
                        }
                        entityMapper.insertSelective(bdcSlSjcl);
                    } else {
                        //存在直接使用当前数据
                        bdcSlSjcl = bdcSlSjclDOList.get(0);
                    }
                    List<StorageDto> storageFjList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), null, null, 1, null,null);
                    if (CollectionUtils.isNotEmpty(storageFjList)) {
                        for (StorageDto storageDto1 : storageFjList) {
                            //先下载之前的文件
                            MultipartDto multipartDto = storageClient.download(storageDto1.getId());
                            //上传文件到当前的目录
                            multipartDto.setNodeId(bdcSlSjcl.getWjzxid());
                            storageClient.multipartUpload(multipartDto);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承收件材料
     * @date : 2021/9/22 14:18
     */
    @Override
    public void extendSjcl(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmDOList.get(0);
                String cqzh = bdcSlXmDO.getYbdcqz();
                //根据产权证号查找业务流转信息表的数据
                if (StringUtils.isNotBlank(cqzh)) {
                    BdcSlYwlzDO bdcSlYwlzQO = new BdcSlYwlzDO();
                    bdcSlYwlzQO.setBdcqzh(cqzh);
                    List<BdcSlYwlzDO> bdcSlYwlzDOList = bdcSlYwlzService.listBdcSlYwlz(bdcSlYwlzQO);
                    if (CollectionUtils.isNotEmpty(bdcSlYwlzDOList)) {
                        BdcSlYwlzDO bdcSlYwlzDO = bdcSlYwlzDOList.get(0);
                        //找到办理业务流转生成的收件材料
                        List<StorageDto> storageDtoList = storageClient.listAllRootStorages(CommonConstantUtils.WJZX_CLIENTID, bdcSlYwlzDO.getGzlslid(), "", "公证处出具法律调查意见书", 1, null, null,null);
                        if (CollectionUtils.isNotEmpty(storageDtoList)) {
                            List<StorageDto> fileList = storageClient.listAllSubsetStorages(storageDtoList.get(0).getId(), "", 1, null, null,null);
                            if (CollectionUtils.isNotEmpty(fileList)) {
                                //查询是否存在文件夹，没有新增一个文件夹
                                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, CommonConstantUtils.CLMC_JCGZS);
                                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                                    bdcSlSjclDO.setFs(1);
                                    bdcSlSjclDO.setYs(fileList.size());
                                    bdcSlSjclDO.setSqbm("登记");
                                    bdcSlSjclDO.setGzlslid(gzlslid);
                                    bdcSlSjclDO.setDjxl(bdcSlXmDO.getDjxl());
                                    bdcSlSjclDO.setClmc(CommonConstantUtils.CLMC_JCGZS);
                                    bdcSlSjclDO.setSjlx(9);
                                    bdcSlSjclDO = insertSjcl(bdcSlSjclDO, "");
                                } else {
                                    bdcSlSjclDO = bdcSlSjclDOList.get(0);
                                }
                                Collections.reverse(fileList);
                                for (int i = 0; i < fileList.size(); i++) {
                                    //先下载之前的文件
                                    MultipartDto multipartDto = storageClient.download(fileList.get(i).getId());
                                    //上传文件到当前的目录
                                    multipartDto.setNodeId(bdcSlSjclDO.getWjzxid());
                                    //获取后缀名
                                    int index = multipartDto.getOriginalFilename().lastIndexOf(".");
                                    LOGGER.info("原文件名称{}", multipartDto.getOriginalFilename());
                                    multipartDto.setOriginalFilename(CommonConstantUtils.CLMC_JCGZS + CommonConstantUtils.BDCQ_BH_LEFT_BRACKET + (i + 1) + CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET + multipartDto.getOriginalFilename().substring(index));
                                    storageClient.multipartUpload(multipartDto);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Integer updateSjclSfpz(String gzlslid) {
        LOGGER.info("更新收件材料是否批注，传入参数gzlslid：{}", gzlslid);
        Integer count = 0;
        List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOS) {
            if(bdcSlSjclDO.getSfpz()==null){
                bdcSlSjclDO.setSfpz(0);
                count += bdcSlSjclService.updateBdcSlSjcl(bdcSlSjclDO);
            }
        }
        return count;
    }

    public Integer insertZhSjcl(String json, String userId) {
        BdcSlSjclDO bdcSlSjclDO = JSONObject.parseObject(json, BdcSlSjclDO.class);
        //先根据登记小类创建父文件夹
        if (StringUtils.isNotBlank(bdcSlSjclDO.getDjxl())) {
            List<Map> djxlZdList = bdcZdFeignService.queryBdcZd("djxl");
            String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcSlSjclDO.getDjxl(), djxlZdList);
            StorageDto parentFolder = storageClient.createRootFolder(Constants.WJZX_CLIENTID, bdcSlSjclDO.getGzlslid(), djxlmc, userId);
            if (Objects.nonNull(parentFolder)) {
                StorageDto childFolder = storageClient.createFolder(Constants.WJZX_CLIENTID, bdcSlSjclDO.getGzlslid(), parentFolder.getId(), bdcSlSjclDO.getClmc(), userId);
                bdcSlSjclDO.setWjzxid(childFolder.getId());
                if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                    bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
                }
            }
            return entityMapper.insertSelective(bdcSlSjclDO);
        } else {
            //如果登记小类不存在，比如之前的老数据组合流程需要新增收件材料
            return insertBdcSlSjcl(json, userId);
        }
    }

    /**
     * 上传南通合同备案信息的附件
     *
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     */
    private void uploadNtHtbaxxFjcl(String xmid, String gzlslid) throws Exception {
        // 上传备案合同的附件
        LOGGER.info("开始上传备案合同的附件");
        String fclx = CommonConstantUtils.FCJY_TYPE_CLF;
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(spfdyidList) && spfdyidList.contains(bdcSlJbxxDO.getGzldyid())) {
            fclx = CommonConstantUtils.FCJY_TYPE_SPF;
        }
        List<BdcSlJyxxDO> listJyxx = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(listJyxx)) {
            String htbh = listJyxx.get(0).getHtbh();
            // 有合同编号
            if (StringUtils.isNotBlank(htbh)) {
                // 查询htbh之后，再查询sqr信息。因为sqr信息只要非抵押人的信息，所以这里要再调一次接口
                BdcNtFjParamDTO bdcNtFjParamDTO = getSlSqr(htbh, fclx);
                if (bdcNtFjParamDTO != null) {
                    rudongFcjyDataFeignService.uploadFcjyfj(fclx, gzlslid, htbh, bdcNtFjParamDTO);
                }
            } else {
                LOGGER.info("交易信息缺失htbh，无法查询上传附件");
            }
        } else {
            LOGGER.info("缺失交易信息，无法查询上传附件");
        }
    }

    /**
     * 获取sqr信息
     */
    private BdcNtFjParamDTO getSlSqr(String htbh, String fclx) {
        FcjyBaxxDTO fcjyBaxxDTO;
        BdcBahtQO bdcBahtQO = new BdcBahtQO();
        bdcBahtQO.setHtbh(htbh);
        Object response = null;
        if (StringUtils.equals(fclx, CommonConstantUtils.FCJY_TYPE_SPF)) {
            LOGGER.info("南通调用接口beanId:rd_spfhtxx,参数：{}", JSONObject.toJSONString(bdcBahtQO));
            response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx", bdcBahtQO);
        } else {
            LOGGER.info("南通调用接口beanId:rd_clfhtxx,参数：{}", JSONObject.toJSONString(bdcBahtQO));
            response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx", bdcBahtQO);
        }
        if (null != response) {
            LOGGER.info("接口返回值：{}", JSONObject.toJSONString(response));
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONArray jsonArr = jsonObj.getJSONArray("data");
            if (CollectionUtils.isNotEmpty(jsonArr)) {
                JSONObject data = (JSONObject) jsonArr.get(0);
                JSONObject jsonObject = data.getJSONObject("jyxxDTO");
                fcjyBaxxDTO = JSONObject.parseObject(jsonObject.toJSONString(), FcjyBaxxDTO.class);
                if (fcjyBaxxDTO != null && CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcSlSqr())) {
                    List<BdcSlSqrDO> bdcSlSqrDOList = fcjyBaxxDTO.getBdcSlSqr();
                    LOGGER.info("初始化上传附件时，sqr信息为：{}", JSONObject.toJSONString(bdcSlSqrDOList));
                    Set<String> ywrzjh = new HashSet<>();
                    Set<String> qlrzjh = new HashSet<>();
                    for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                        if (bdcSlSqrDO != null && bdcSlSqrDO.getSqrlb() != null
                                && StringUtils.isNotBlank(bdcSlSqrDO.getZjh())) {
                            if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_QLR)) {
                                qlrzjh.add(bdcSlSqrDO.getZjh());
                            } else {
                                ywrzjh.add(bdcSlSqrDO.getZjh());
                            }
                        }
                    }
                    BdcNtFjParamDTO bdcNtFjParamDTO = new BdcNtFjParamDTO();

                    bdcNtFjParamDTO.setQlrzjh(qlrzjh);
                    bdcNtFjParamDTO.setYwrzjh(ywrzjh);
                    return bdcNtFjParamDTO;
                }
            }
        }

        return null;
    }

    @Override
    public void uploadYthSpfj(String json,String gzlslid) {
        if(StringUtils.isBlank(json) ||StringUtils.isBlank(gzlslid)){
            throw new AppException("上传审批附件失败,未获取到附件信息或工作流实例ID");
        }
        List<StorageDto> storageDtoList;
        try {
            storageDtoList = JSON.parseArray(json, StorageDto.class);
        }catch (Exception e){
            LOGGER.error("JSON转换失败:{}",json,e);
            throw new AppException("上传审批附件失败,失败原因：文件结构错误");
        }

        if(CollectionUtils.isEmpty(storageDtoList)){
            throw new AppException("上传审批附件失败,未获取到附件信息");
        }
        String userid ="";
        UserDto userDto =userManagerUtils.getCurrentUser();
        if(userDto != null){
            userid =userDto.getId();
        }
        for(StorageDto storageDto:storageDtoList){
            //第一层肯定是文件夹目录
            if(CollectionUtils.isNotEmpty(storageDto.getChildren())){
                StorageDto storageFolder;
                boolean zhsjcl =false;
                String djxl ="";
                if(Boolean.TRUE.equals(splitSjcl)) {
                    int xmlx =bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                    if(CommonConstantUtils.LCLX_PLZH.equals(xmlx) ||CommonConstantUtils.LCLX_ZH.equals(xmlx)){
                        zhsjcl =true;
                        //组合收件
                        djxl =getZhsjclDjxl(gzlslid);
                    }
                }
                String nodeId ="";
                //判断收件材料是否存在
                BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                bdcSlSjclQO.setGzlslid(gzlslid);
                bdcSlSjclQO.setClmc(storageDto.getName());
                bdcSlSjclQO.setDjxl(djxl);
                List<BdcSlSjclDO> sjclList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                if(CollectionUtils.isEmpty(sjclList)) {
                    //创建文件夹
                    if (Boolean.FALSE.equals(zhsjcl)) {
                        storageFolder = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, storageDto.getName(), userid);
                    } else {
                        List<Map> djxlZdList = bdcZdFeignService.queryBdcZd("djxl");
                        String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(djxl, djxlZdList);
                        StorageDto parentStorage = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, djxlmc, userid);
                        storageFolder = storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, parentStorage.getId(), storageDto.getName(), userid);
                    }
                    nodeId =storageFolder.getId();
                    //添加收件材料信息
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", "", storageFolder.getName(), "");
                    bdcPdfDTO.setDjxl(djxl);
                    bdcUploadFileUtils.addSjclxx(bdcPdfDTO,nodeId);
                }else{
                    nodeId =sjclList.get(0).getWjzxid();
                }
                for (StorageDto children : storageDto.getChildren()) {
                    createFileOrFolder(children, nodeId, gzlslid, userid);
                }

            }
        }

    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承原项目的收件材料信息
     * @date : 2022/1/6 14:40
     */
    @Override
    public void extendYxmSjcl(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //根据gzlslid查 项目历史关系数据
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxBySlid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                List<String> yXmidList = new ArrayList<>(CollectionUtils.size(bdcXmLsgxDOList));
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                    yXmidList.add(bdcXmLsgxDO.getYxmid());
                }
                if (CollectionUtils.isNotEmpty(yXmidList)) {
                    List<BdcXmDO> yBdcXmDOList = bdcXmFeignService.listBdcXmByXmids(yXmidList);
                    if (CollectionUtils.isNotEmpty(yBdcXmDOList)) {
                        for (BdcXmDO bdcXmDO : yBdcXmDOList) {
                            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(bdcXmDO.getGzlslid());
                            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                                List<String> sjclIdList = bdcSlSjclDOList.stream().map(BdcSlSjclDO::getSjclid).collect(Collectors.toList());
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("gzlslid", gzlslid);
                                jsonObject.put("sjclIdList", sjclIdList);
                                this.copySjcl(JSON.toJSONString(jsonObject));
                            }
                        }
                        //去除不需要继承的文件夹
                        if (CollectionUtils.isNotEmpty(exceptSjclmcList)) {
                            for (String clmc : exceptSjclmcList) {
                                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, clmc);
                                if (CollectionUtils.isNotEmpty(bdcSlSjclDOList) && StringUtils.isNotBlank(bdcSlSjclDOList.get(0).getWjzxid())) {
                                    List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, null, null, "");
                                    for (StorageDto storageDto : storageDtoList) {
                                        storageClient.deleteStorages(Collections.singletonList(storageDto.getId()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取组合收件登记小类文件夹
     */
    private String getZhsjclDjxl(String gzlslid) {
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            String djxl =bdcXmDTOList.get(0).getDjxl();
            bdcXmDTOList = bdcXmDTOList.stream().filter(t-> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, t.getQllx())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                djxl =bdcXmDTOList.get(0).getDjxl();
            }
            return djxl;
        }
        return "";

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 递归生成文件夹或文件
      */
    public void createFileOrFolder(StorageDto storageDto,String parentId,String gzlslid,String userid){
        if(StringUtils.isNotBlank(storageDto.getDownUrl())){
            //当前为文件
            LOGGER.info("上传附件：{}，url:{},工作流实例ID:{}",storageDto.getName(),storageDto.getDownUrl(),gzlslid);
            downAndUploadFjFromUrl(storageDto.getDownUrl(),storageDto.getName(),parentId);
        }else if(StringUtils.isNotBlank(storageDto.getName()) &&CollectionUtils.isNotEmpty(storageDto.getChildren())){
            List<StorageDto> existFolders = storageClient.listAllSubsetStorages(parentId, storageDto.getName(),1,0,0,null);
            String folderid ="";
            if(CollectionUtils.isEmpty(existFolders)) {
                //新建文件夹
                StorageDto storageFolder = storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, parentId, storageDto.getName(), userid);
                folderid =storageFolder.getId();
            }else{
                folderid =existFolders.get(0).getId();
            }
            for(StorageDto child:storageDto.getChildren()){
                createFileOrFolder(child,folderid,gzlslid,userid);
            }
        }

    }

    /**
     * @param fjmc  附件名称
     * @param fjurl 附件URL
     * @param wjjid 文件夹ID
     * @description 根据附件URL地址下载并上传附件
     */
    private void downAndUploadFjFromUrl(String fjurl, String fjmc, String wjjid) {
        if(StringUtils.isBlank(fjmc)){
            throw new AppException("附件名称不能为空");
        }
        MultipartFile file = null;
        try {
            URL url = new URL(fjurl);
            String wjhz =fjmc.split(CommonConstantUtils.ZF_YW_XG)[fjmc.split(CommonConstantUtils.ZF_YW_XG).length - 1];
            if (fjmc.endsWith(CommonConstantUtils.WJHZ_PNG)) {
                String base64 = Base64Utils.encodeDzzzImageToBase64(url);
                if (StringUtils.isNotBlank(base64)) {
                    base64 = "data:image/png;base64," + base64;
                    file = Base64Utils.base64ToMultipart(base64);
                }
            } else {
                file = Base64Utils.createFileItem(url, wjhz);
            }
            // 创建文件前 先删除同名的文件
            List<StorageDto> existFiles = storageClient.listAllSubsetStorages(wjjid, fjmc,1,null,0,null);
            if(CollectionUtils.isNotEmpty(existFiles)){
                List<String> ids = new ArrayList<>();
                for(StorageDto delStorageDto : existFiles){
                    ids.add(delStorageDto.getId());
                }
                storageClient.deleteStorages(ids);
            }
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
            multipartDto.setName(fjmc);
            multipartDto.setNodeId(wjjid);
            multipartDto.setData(file.getBytes());
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(fjmc);
            StorageDto dto = storageClient.multipartUpload(multipartDto);
            LOGGER.info("附件上传成功 fjmc:{},storageid:{}", fjmc, dto.getId());
        } catch (Exception e) {
            LOGGER.error("下载附件 {} 获取流异常", fjmc, e);
            throw new AppException("下载附件异常");

        }
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件保存到附件材料
     * @date : 2022/11/25
     * @return object
     * @exception IOException
     */
    @Override
    public Boolean downloadWtcl(String gzlslid) throws IOException {
        if (StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("工作流实例id不能为空");
        }
        // 通过gzlslid获取bdcdyh集合
        HashSet<String> bdcdyhSet = listBdcdyhByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcdyhSet)){
            throw new MissingArgumentException("不动产单元号为空");
        }
        List<String> storageDtoIds= new ArrayList<>(3);

        for (String bdcdyh : bdcdyhSet){
            String beanName = "wtxx_fjcx";
            Map<String,String> paramMap = new HashMap<>(2);
            paramMap.put("bdcdyh",bdcdyh);
            paramMap.put("token",wtxxToken);
            // 第三方请求获取委托材料文件信息
            Object object = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

            try {
                if (null != object) {
                    JSONObject jsonObj =  JSONObject.parseObject(JSONObject.toJSONString(object));
                    JSONObject dataObj = jsonObj.getJSONObject("data");

                    String[]  wtclArr = new String[]{"fyzksms", "grwtsUrl", "proveVideo"};
                    for(String wtcl : wtclArr) {
                        // 获取婚姻状况说明书,个人委托书,视频的文件URL的下载地址
                        String wtclUrl = dataObj.getString(wtcl);
                        LOGGER.info("获取委托材料:{},下载地址:{}",getfjmc(wtclUrl),wtclUrl);

                        if (StringUtils.isNotBlank(getfjmc(wtclUrl)) && getfjmc(wtclUrl).contains(CommonConstantUtils.ZF_YW_JH)) {
                            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
                            bdcPdfDTO.setGzlslid(gzlslid);
                            bdcPdfDTO.setPdfUrl(wtclUrl);
                            bdcPdfDTO.setFoldName(wtxxWjjmc);
                            bdcPdfDTO.setPdffjmc(getfjmc(wtclUrl).split("\\.")[0]);
                            bdcPdfDTO.setFileSuffix(CommonConstantUtils.ZF_YW_JH + getfjmc(wtclUrl).split("\\.")[1]);
                            // 根据附件URL地址下载并上传附件
                            String storageDtoId = bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                            if (StringUtils.isNotBlank(storageDtoId)) {
                                storageDtoIds.add(storageDtoId);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取委托材料解析失败:", e);
                throw new AppException("获取委托信息文件保存到附件材料失败");
            }
        }
        return CollectionUtils.isNotEmpty(storageDtoIds);
    }

    /**
     * @param slymDchyDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 多测合一附件获取
     * @date : 2022/12/20 8:46
     */
    @Override
    public void downDchyfj(SlymDchyDTO slymDchyDTO) {
        if (StringUtils.isNoneBlank(slymDchyDTO.getBeanName(), slymDchyDTO.getClmc(), slymDchyDTO.getGzlslid())) {
            LOGGER.info("当前流程实例id{}，调取接口{}获取{}，入参{}", slymDchyDTO.getGzlslid(), slymDchyDTO.getBeanName(), slymDchyDTO.getClmc());
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(slymDchyDTO.getGzlslid());
            if (CollectionUtils.isEmpty(bdcXmDTOList)) {
                throw new AppException("当前流程实例id未获取到项目信息" + slymDchyDTO.getGzlslid());
            }
            //根据不动产单元号去重
            Map paramMap = new HashMap(2);
            List<Map> pathMapList = new ArrayList<>(1);
            if (StringUtils.equals("xcDchyFcfht", slymDchyDTO.getBeanName())) {
                paramMap.put("Method", 0);
                Set<String> bdcdyhSet = bdcXmDTOList.stream().map(BdcXmDTO::getBdcdyh).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
                for (String bdcdyh : bdcdyhSet) {
                    paramMap.put("Bdcdyh", bdcdyh);
                    LOGGER.info("当前流程实例id{}，调取接口{}获取{}，入参{}", slymDchyDTO.getGzlslid(), slymDchyDTO.getBeanName(), slymDchyDTO.getClmc(), paramMap);
                    Object response = exchangeInterfaceFeignService.requestInterface(slymDchyDTO.getBeanName(), paramMap);
                    LOGGER.info("当前流程实例id{}，调取接口{}返回值{}", slymDchyDTO.getGzlslid(), slymDchyDTO.getBeanName(), JSON.toJSONString(response));
                    if (Objects.nonNull(response)) {
                        Map resultMap = (Map) response;
                        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("Paths")));
                        for (Object object : jsonArray) {
                            Map pathMap = (Map) object;
                            pathMapList.add(pathMap);
                        }
                    }
                }
                uploadFile(pathMapList, CommonConstantUtils.WJHZ_JPG, slymDchyDTO.getGzlslid(), slymDchyDTO.getClmc());
            } else if (StringUtils.equals("xcDchyZddcb", slymDchyDTO.getBeanName())) {
                paramMap.put("Method", 2);
                //宗地调查表根据单元号前19 为去重
                Set<String> zddmSet = bdcXmDTOList.stream().map(bdcXmDTO -> StringUtils.substring(bdcXmDTO.getBdcdyh(), 0, 19)).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
                for (String zddm : zddmSet) {
                    paramMap.put("Zddm", zddm);
                    Object response = exchangeInterfaceFeignService.requestInterface(slymDchyDTO.getBeanName(), paramMap);
                    //根据返回值url下载附件
                    LOGGER.info("当前流程实例id{}，调取接口{}返回值{}", slymDchyDTO.getGzlslid(), slymDchyDTO.getBeanName(), JSON.toJSONString(response));
                    if (Objects.nonNull(response)) {
                        Map resultMap = (Map) response;
                        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("Paths")));
                        for (Object object : jsonArray) {
                            Map pathMap = (Map) object;
                            pathMapList.add(pathMap);
                        }
                    }
                }
                uploadFile(pathMapList, CommonConstantUtils.WJHZ_DOCX, slymDchyDTO.getGzlslid(), slymDchyDTO.getClmc());
            } else if (StringUtils.equals("xcDchyChbg", slymDchyDTO.getBeanName())) {
                paramMap.put("Method", 1);
                paramMap.put("Type", slymDchyDTO.getCllx());
                //测绘报告收件材料根据单元号前24 为去重
                Set<String> zrzhSet = bdcXmDTOList.stream().map(bdcXmDTO -> StringUtils.substring(bdcXmDTO.getBdcdyh(), 0, 24)).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
                for (String zrzh : zrzhSet) {
                    paramMap.put("Zrzh", zrzh);
                    Object response = exchangeInterfaceFeignService.requestInterface(slymDchyDTO.getBeanName(), paramMap);
                    LOGGER.info("当前流程实例id{}，调取接口{}返回值{}", slymDchyDTO.getGzlslid(), slymDchyDTO.getBeanName(), JSON.toJSONString(response));
                    if (Objects.nonNull(response)) {
                        Map resultMap = (Map) response;
                        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("Paths")));
                        for (Object object : jsonArray) {
                            Map pathMap = (Map) object;
                            pathMapList.add(pathMap);
                        }
                    }
                }
                //特殊处理，先创建一个受理文件夹，再上传子文件到该文件夹下
                uploadChildFile(pathMapList, slymDchyDTO.getGzlslid(), slymDchyDTO.getClmc(), slymDchyDTO.getZwjmc());
            }

        }
    }


    private void uploadFile(List<Map> pathList, String fileSuffix, String gzlslid, String clmc) {
        if (CollectionUtils.isNotEmpty(pathList)) {
            for (Map pathMap : pathList) {
                if (StringUtils.isNotBlank(MapUtils.getString(pathMap, "Url"))) {
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", MapUtils.getString(pathMap, "Name"), clmc, fileSuffix);
                    bdcPdfDTO.setPdfUrl(MapUtils.getString(pathMap, "Url"));
                    try {
                        bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                    } catch (IOException e) {
                        LOGGER.error("流程实例id{}上传{}异常", gzlslid, clmc, e);
                    }
                }
            }
        }
    }

    private void uploadChildFile(List<Map> pathList, String gzlslid, String clmc, String zwjmc) {
        if (CollectionUtils.isNotEmpty(pathList)) {
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, clmc, "");
            //先判断子文件夹是否存在，存在先删除
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", zwjmc, 1, null);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageClient.deleteStorages(storageDtoList.stream().map(StorageDto::getId).collect(Collectors.toList()));
            }
            StorageDto childFolder = storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, storageDto.getId(), zwjmc, "");
            for (Map pathMap : pathList) {
                try {
                    MultipartFile file = Base64Utils.createFileItem(new URL(MapUtils.getString(pathMap, "Url")), "jpg");
                    MultipartDto multipartDto = new MultipartDto();
                    multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
                    multipartDto.setName(file.getName());
                    multipartDto.setNodeId(childFolder.getId());
                    multipartDto.setData(file.getBytes());
                    multipartDto.setContentType(file.getContentType());
                    multipartDto.setSize(file.getSize());
                    multipartDto.setOriginalFilename(MapUtils.getString(pathMap, "Name") + "jpg");
                    storageClient.multipartUpload(multipartDto);
                } catch (Exception e) {
                    LOGGER.error("流程实例id{}生成文件{}异常", gzlslid, clmc, e);
                }
            }
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, clmc);
            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                bdcSlSjclDO.setClmc(clmc);
                bdcSlSjclDO.setGzlslid(gzlslid);
                bdcSlSjclDO.setFs(1);
                bdcSlSjclDO.setYs(pathList.size());
                bdcSlSjclDO.setWjzxid(storageDto.getId());
                bdcSlSjclDO.setSjlx(99);
                bdcSlSjclService.insertBdcSlSjcl(bdcSlSjclDO);
            }
        }
    }

    /**
     * @param fjurl 第三方获取获取委托信息文件URL下载地址
     * @return fjmc 附件名称
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件下载地址中获取文件名称
     * @date : 2022/11/29
     */
    public String getfjmc(String fjurl) {
        String fjmc = "";
        String nul = "null";
        if (StringUtils.isNotBlank(fjurl)) {
            List<String> fjurlList = Lists.newArrayList(fjurl.split("/"));
            if (CollectionUtils.isNotEmpty(fjurlList) && !nul.equals(fjurlList.get(fjurlList.size() - 1))) {
                fjmc = fjurlList.get(fjurlList.size() - 1);
            }
        }
        return fjmc;

    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 通过gzlslid获取bdcdyh集合
     * @date : 2022/12/01
     * @return bdcdyhSet 不动产单元号集合
     */
    private HashSet<String> listBdcdyhByGzlslid(String gzlslid) {
        HashSet<String> bdcdyhSet = new HashSet<>();
        if (StringUtils.isBlank(gzlslid)) {
            return bdcdyhSet;
        }
        List<BdcXmDTO> list = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmDTO bdcXmDTO : list) {
                if (bdcXmDTO != null && StringUtils.isNotBlank(bdcXmDTO.getBdcdyh())) {
                    bdcdyhSet.add(bdcXmDTO.getBdcdyh());
                }
            }
        }
        return bdcdyhSet;
    }
}
