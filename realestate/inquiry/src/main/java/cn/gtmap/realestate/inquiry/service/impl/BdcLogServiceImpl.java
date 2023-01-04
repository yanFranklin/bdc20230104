package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDyzmTjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcZszmPrintDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.service.BdcLogService;
import cn.gtmap.realestate.inquiry.thread.BdcZszmDylThread;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 日志处理
 */
@Service
public class BdcLogServiceImpl implements BdcLogService {
    private static final Logger logger = LoggerFactory.getLogger(BdcLogServiceImpl.class);
    private static final String BDC_ZSZM_PRINT_INFO = "BDC_ZSZM_PRINT_INFO_";

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZhcxFeignService bdcZhcxFeignService;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repo repo;

    @Autowired
    private BdcZszmDylThread bdcZszmDylThread;

    @Autowired
    OrganizationManagerClient organizationManagerClient;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 打印信息
     * @description 保存打印的证书、证明、证明单数量日志信息
     */
    @Override
    public Boolean saveBdcZszmPrintInfo(BdcZszmPrintDTO bdcZszmPrintDTO) {
        if(null == bdcZszmPrintDTO || null == bdcZszmPrintDTO.getZslx()){
            return false;
        }

        // 获取机构信息
        String orgCode = userManagerUtils.getCurrentUserOrgCode();
        if(StringUtils.isBlank(orgCode)){
            return false;
        }

        // 当前时间点，为了后期查询时间区间
        double time = Double.valueOf(String.valueOf(System.currentTimeMillis()));
        UserDto currentUser = userManagerUtils.getCurrentUser();
        // 保存打印数量日志信息
        Map map = new HashMap(3);
        map.put("ORG", orgCode);
        map.put("ZSLX", bdcZszmPrintDTO.getZslx());
        map.put("COUNT", this.getPrintCount(bdcZszmPrintDTO));
        // 加随机ID，避免ZSET键值覆盖
        map.put("CODE", UUIDGenerator.generate16());
        if(currentUser!=null){
            String alias = currentUser.getAlias();
            map.put("NAME", alias);
        }
        // 开启线程同步打印量数据记录至数据库中
        bdcZszmDylThread.recordBdcZszmDyl(map);
        return redisUtils.addZsetKey(this.getKey(), JSON.toJSONString(map), time);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsTjQO 统计参数
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @Override
    public BdcZhcxDyzmTjDTO countZszmPrint(BdcZsTjQO bdcZsTjQO) {
        // 获取指定区间数据
        double startTime = this.getStartTime(bdcZsTjQO.getKssj());
        double endTime = this.getEndTime(bdcZsTjQO.getJzsj());
        // 修改为支持多部门筛选查询
        String djbmdm = bdcZsTjQO.getDjbmdm();
        List<String> djbmdmList = null;
        if (StringUtils.isNotBlank(djbmdm)) {
            String[] djbmdmArray = StringUtils.split(djbmdm, ",");
            djbmdmList = Arrays.asList(djbmdmArray);
        }


        Set<String> logSet = redisUtils.getZsetValue(this.getKey(), startTime, endTime);
        if(CollectionUtils.isEmpty(logSet)){
            return null;
        }
        // 过滤出所有的机构
        Map<String, Integer> orgKeyMap = new LinkedHashMap<>();
        int index = 0;
        for(String json : logSet){
            if(StringUtils.isNotBlank(json)){
                Map map = JSON.parseObject(json, Map.class);
                String org = (String) map.get("ORG");
                if(djbmdmList !=null && !djbmdmList.contains(org)){
                    continue;
                }

                if(!orgKeyMap.containsKey(org)){
                    orgKeyMap.put(org, index++);
                }
            }
        }
        if(MapUtils.isEmpty(orgKeyMap)){
            return null;
        }

        // 每个证明对应不同机构的打印数量
        Map<String, List<Integer>> valueMap = new HashMap<>(3);
        String[] arr = {"1","2","3"};
        if(StringUtils.isNotBlank(bdcZsTjQO.getZslx())){
            arr = bdcZsTjQO.getZslx().split(",");
        }

        for(String zmxx : arr){
            // 新建初始数组，代表证书、证明、证明单数量都为0
            List<Integer> countList = new ArrayList<>(orgKeyMap.size());
            for(int i = 0 ; i < orgKeyMap.size(); i++){
                countList.add(0);
            }
            valueMap.put(zmxx, countList);
        }

        // 遍历数据设置每个机构对应的证书、证明、证明单数量
        for(String json : logSet){
            if(StringUtils.isNotBlank(json)){
                Map map = JSON.parseObject(json, Map.class);
                String org = (String) map.get("ORG");
                if(djbmdmList !=null && !djbmdmList.contains(org)){
                    continue;
                }

                String zslx = String.valueOf(map.get("ZSLX"));
                if(StringUtils.isNotBlank(bdcZsTjQO.getZslx()) && bdcZsTjQO.getZslx().indexOf(zslx) == -1){
                    continue;
                }

                // 获取设置数量
                int count = (Integer) map.get("COUNT");
                int orgIndex = orgKeyMap.get(org);
                int num = valueMap.get(zslx).get(orgIndex);

                valueMap.get(zslx).set(orgIndex, num + count);
            }
        }

        BdcZhcxDyzmTjDTO bdcZhcxDyzmTjDTO = new BdcZhcxDyzmTjDTO();
        LinkedHashSet<String> statisticKeySet = new LinkedHashSet<>();
        statisticKeySet.addAll(orgKeyMap.keySet());
        bdcZhcxDyzmTjDTO.setKeySet(statisticKeySet);
        bdcZhcxDyzmTjDTO.setValueMap(valueMap);
        return bdcZhcxDyzmTjDTO;

    }

    /**
     * 保存查询台账查询日志
     * @param bdcXtCxLogDO 日志信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public void saveBdcCxLog(BdcXtCxLogDO bdcXtCxLogDO) {
        if(null == bdcXtCxLogDO) {
            throw new MissingArgumentException("保存系统查询日志异常：未定义日志信息");
        }

        if(StringUtils.isBlank(bdcXtCxLogDO.getId())) {
            bdcXtCxLogDO.setId(UUIDGenerator.generate16());
        }

        if(StringUtils.isAnyBlank(bdcXtCxLogDO.getYhm(), bdcXtCxLogDO.getYhzh())) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto) {
                bdcXtCxLogDO.setYhm(userDto.getAlias());
                bdcXtCxLogDO.setYhzh(userDto.getUsername());
                bdcXtCxLogDO.setYhid(userDto.getId());

                List<OrganizationDto> orgs = userDto.getOrgRecordList();
                if(CollectionUtils.isNotEmpty(orgs) && null != orgs.get(0)) {
                    bdcXtCxLogDO.setSzbm(orgs.get(0).getName());
                    bdcXtCxLogDO.setSzbmid(orgs.get(0).getId());
                }
            }
        }

        // 如果是Excel查询，将Excel查询文件上传大云
        if(StringUtil.isNotBlank(bdcXtCxLogDO.getExcel())) {
            if(bdcXtCxLogDO.getExcel().contains("/storage")) {
                // 无需处理
            } else {
                // 默认传过来的是文件磁盘路径
                File file = new File(bdcXtCxLogDO.getExcel());
                if(file.exists()) {
                    String fileUrl = this.uploadFile(file);
                    bdcXtCxLogDO.setExcel(fileUrl);
                }
            }
        }

        bdcXtCxLogDO.setCzsj(new Date());
        // 查询条件VARCHAR2(4000)，导入excel查询，数量过多时，会造成cxtj超过4000，sql报错
        if (StringUtils.isNotBlank(bdcXtCxLogDO.getCxtj()) && bdcXtCxLogDO.getCxtj().length() > 2000) {
            bdcXtCxLogDO.setCxtj(bdcXtCxLogDO.getCxtj().substring(0, 2000));
        }
        entityMapper.insertSelective(bdcXtCxLogDO);
        logger.info("保存系统查询日志成功，日志信息：{}", bdcXtCxLogDO);
    }

    /**
     * 上传文件到大云
     * @param file 目标文件
     * @return 文件下载路径
     */
    private String uploadFile(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)){
            String fileName = file.getName();
            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName,ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setName(fileName);
            multipartDto.setNodeId("");
            multipartDto.setClientId("clientId");
            multipartDto.setContentType("application/msexcel");
            multipartDto.setData(multipartFile.getBytes());
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setSize(multipartFile.getSize());

            StorageDto storageDto = storageClient.multipartUpload(multipartDto);
            if(null == storageDto || StringUtil.isBlank(storageDto.getDownUrl())) {
                throw new AppException("上传系统查询Excel文件失败");
            }
            return storageDto.getDownUrl();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("上传系统查询Excel文件失败");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmPrintDTO 打印信息
     * @description 获取打印的证书、证明、证明单数量
     */
    private int getPrintCount(BdcZszmPrintDTO bdcZszmPrintDTO) {
        int count = 0;
        if(StringUtils.isNotBlank(bdcZszmPrintDTO.getZsid())){
            // 证书ID存在说明是单个或者部分打印
            count = bdcZszmPrintDTO.getZsid().split(",").length;
        } else {
            // 全部打印，需要根据工作流实例ID查询数量
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setZslx(bdcZszmPrintDTO.getZslx());
            bdcZsQO.setGzlslid(bdcZszmPrintDTO.getGzlslid());
            List<String> zsidList =  bdcZsFeignService.queryZsid(bdcZsQO);
            if(CollectionUtils.isNotEmpty(zsidList)){
                count = zsidList.size();
            }
        }
        return count;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取查询开始时间节点
     *
     *   如果没有指定则默认当天0点时刻
     */
    private double getStartTime(String time){
        if(StringUtils.isBlank(time)){
            return Double.valueOf(DateUtils.getDayTimeOfZeroHMS(new Date()));
        }

        Date date = DateUtils.formatDate(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        if(null == date){
            return Double.valueOf(DateUtils.getDayTimeOfZeroHMS(new Date()));
        }
        return Double.valueOf(String.valueOf(date.getTime()));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取查询开始时间节点
     *
     *   如果没有指定则默认当天23点59分59秒时刻
     */
    private double getEndTime(String time){
        if(StringUtils.isBlank(time)){
            return Double.valueOf(DateUtils.getDayTimeOfLastHMS(new Date()));
        }

        Date date = DateUtils.formatDate(time+ " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        if(null == date){
            return Double.valueOf(DateUtils.getDayTimeOfLastHMS(new Date()));
        }
        return Double.valueOf(String.valueOf(date.getTime()));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取日志保存KEY，采用 固定前缀+年份+当前季度，避免单key数据过多
     */
    private String getKey(){
        return  BDC_ZSZM_PRINT_INFO + LocalDate.now().getYear() + "_" + DateUtils.getSeason(new Date());
    }

    /**
     * 查询本地保存的综合查询日志
     * @param pageable pageable
     * @param bdcXtCxLogCxtj 查询条件
     * @return return
     */
    @Override
    public Page<BdcXtCxLogDO> bdcCxZhcxLogByPage(Pageable pageable,String bdcXtCxLogCxtj){
        return repo.selectPaging("listBdcXtcxByPageOrder",JSONObject.parseObject(bdcXtCxLogCxtj,Map.class), pageable);
    }

    /**
     * 根据Id获取本地综合日志查询结果
     * @param id key
     * @return return
     */
    @Override
    public BdcXtCxLogDO bdcCxZhcxLogById(String id){
        Example example = new Example(BdcXtCxLogDO.class);
        example.createCriteria().andEqualTo("id",id);
        List<BdcXtCxLogDO> bdcSlDyaqDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcSlDyaqDOList)){
            return bdcSlDyaqDOList.get(0);
        }
        return null;
    }

    @Override
    public void syncZszmPrintCountToDB() {
        int startYear = 2020;
        int currentYear = LocalDate.now().getYear();

        List<OrganizationDto> orgDtoList = organizationManagerClient.findRootOrgs();
        Map<String, String> orgListMap = new HashMap<>(orgDtoList.size());
        if(CollectionUtils.isNotEmpty(orgDtoList)){
            orgListMap = orgDtoList.stream().collect(Collectors.toMap(org -> org.getCode(), org -> org.getName()));
        }
        for(;startYear <= currentYear; startYear++){
            for(int season = 1; season <=4; season++){
                String redisKey = BDC_ZSZM_PRINT_INFO + startYear + "_" + season;
                Set<String> logset = redisUtils.getZsetRangeValue(redisKey, 0 , -1);
                if(CollectionUtils.isNotEmpty(logset)){
                    List<Map> mapList = new ArrayList<>(logset.size());
                    for(String json : logset){
                        if(StringUtils.isNotBlank(json)){
                            Map map = JSON.parseObject(json, Map.class);
                            mapList.add(map);
                        }
                    }

                    this.bdcZszmDylThread.plEecordBdcZszmDyl(mapList, orgListMap);
                }
            }
        }
    }
}
