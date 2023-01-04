package cn.gtmap.realestate.inquiry.service.huaian.impl;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcCstjxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZqxGzltjXmxxVO;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcGzltjXmxxMapper;
import cn.gtmap.realestate.inquiry.service.huaian.BdcGzltjXmxxService;
import cn.gtmap.realestate.inquiry.service.huaian.thread.*;
import cn.gtmap.realestate.inquiry.util.Constants;
import cn.gtmap.realestate.inquiry.util.ThreadPoolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/27
 * @description  工作量统计实现类, 根据项目表信息统计工作量
 */
@Service
public class BdcGzltjXmxxServiceImpl implements BdcGzltjXmxxService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzltjXmxxServiceImpl.class.getName());

    /**
     * RedisKey: 工作量统计所有人员角色信息
     */
    private static final String GZLTJ_ALLUSER_ROLES_REDIS_KEY = "GZLTJ_ALL_USER_ROLES";
    /**
     * RedisKey: 工作量统计所有部门信息
     */
    private static final String GZLTJ_ALL_ORGS_REDIS_KEY = "GZLTJ_ALL_ORGS";
    /**
     * RedisKey: 工作量统计所有人员信息
     */
    private static final String GZLTJ_ALL_USERS_REDIS_KEY = "GZLTJ_ALL_USERS";
    /**
     * Redis 过期时间单位：秒（默认为15天）
     */
    private static final long REDIS_EXPIRE_SECONDS = 15*12*3600;

    @Value("${gzltj.tjjd:复审}")
    private String ggzltjJdmc;

    /**
     * 合并流程的工作流定义ID
     */
    @Value("${hblc.gzldyids:}")
    private String hbGzldyids;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    BdcGzltjXmxxMapper bdcGzltjXmxxMapper;

    @Autowired
    BdcZhcxFeignService bdcZhcxFeignService;

    @Autowired
    OrganizationManagerClientMatcher organizationManagerClientMatcher;

    @Autowired
    UserManagerClient userManagerClient;

    @Autowired
    ProcessTaskCustomExtendClient processTaskCustomExtendClient;

    /**
     * 人员工作量统计内容如下
     *     1、收件量： 统计BDC_XM表数据，按项目为xmid分组， 按受理编号为 slbh 分组
     *     2、复审量： BDC_XM 表关联BDC_SHXX表，查询审核节点办理数据
     *     3、缮证量： BDC_ZS表数据统计
     *     4、发证量： BDC_ZS表，qzysxlh、fzsj、lzr 不为空的数据
     *     5、打印量根据 BDC_ZHCX_LOG 表数据，进行分组统计
     */
    @Override
    public List<BdcRyGzltjXmxxVO> listRyGzltjByBdcxx(GzltjQO gzltjQO) {
        if(StringUtils.isBlank(gzltjQO.getTjlx())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数统计类型。");
        }
        long startTime = System.currentTimeMillis();

        // 获取需要统计的人员信息
        List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList = this.initRyxx(gzltjQO.getBmid());
        if(CollectionUtils.isEmpty(bdcRyGzltjXmxxVOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到人员信息。");
        }

        // 判断查询条件中，是否存在部门，存在部门则查询部门下拥有的人员进行查询
        if(StringUtils.isNotBlank(gzltjQO.getBmid())){
            gzltjQO.setRyidList(bdcRyGzltjXmxxVOList.stream().map(BdcRyGzltjXmxxVO::getRyid).collect(Collectors.toList()));
        }

        // 并行计算人员工作量信息
        try {
            this.excuteRyGzltjTask(gzltjQO, bdcRyGzltjXmxxVOList);
        } catch(TimeoutException e1){
            throw new AppException(ErrorCode.CUSTOM, "计算人员工作量信息超时");
        }catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.CUSTOM, "计算人员工作量信息时报错，错误内容：" + e.getMessage());
        }

        LOGGER.info("统计人员工作量结束，耗时：{}。", System.currentTimeMillis() - startTime);
        return bdcRyGzltjXmxxVOList;
    }

    /**
     * 执行工作量统计计算任务，采用多线程并行计算结果
     */
    private void excuteRyGzltjTask(GzltjQO gzltjQO,  List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList) throws ExecutionException, InterruptedException, TimeoutException {
        // 设置需要统计审核量的节点名称
        gzltjQO.setJdmc(ggzltjJdmc);

        // 1、统计人员收件量
        ComputeRySjlTask computeRySjlTask = new ComputeRySjlTask(gzltjQO, bdcRyGzltjXmxxVOList, bdcGzltjXmxxMapper);
        // 2、统计人员复审量
        ComputeRyFslTask computeRyFslTask = new ComputeRyFslTask(gzltjQO, bdcRyGzltjXmxxVOList, bdcGzltjXmxxMapper);
        // 3、统计人员缮证量
        ComputeRySzlTask computeRySzlTask = new ComputeRySzlTask(gzltjQO, bdcRyGzltjXmxxVOList, bdcGzltjXmxxMapper);
        // 4、统计人员发证量
        ComputeRyFzlTask computeRyFzlTask = new ComputeRyFzlTask(gzltjQO, bdcRyGzltjXmxxVOList, bdcGzltjXmxxMapper);
        // 5、统计有房无房证明量
        ComputeRyPrintTask yfwfzmPrintTask = new ComputeRyPrintTask(gzltjQO, bdcRyGzltjXmxxVOList, Constants.GZLTJ_DYLX_YFWFZM, bdcZhcxFeignService);
        // 6、统计权属证明证明量
        ComputeRyPrintTask qszmPrintTask = new ComputeRyPrintTask(gzltjQO, bdcRyGzltjXmxxVOList, Constants.GZLTJ_DYLX_QSZM, bdcZhcxFeignService);
        // 7、统计不动产登记簿量
        ComputeRyPrintTask bdcdjbPrintTask = new ComputeRyPrintTask(gzltjQO, bdcRyGzltjXmxxVOList, Constants.GZLTJ_DYLX_BDCDJB, bdcZhcxFeignService);
        // 7、统计人员综合查询量
        ComputeRyZhcxTask computeRyZhcxTask = new ComputeRyZhcxTask(gzltjQO, bdcRyGzltjXmxxVOList, bdcZhcxFeignService);

        List<CompletableFuture<Void>> futures = new ArrayList<>(10);
        futures.add(CompletableFuture.runAsync(computeRySjlTask));
        futures.add(CompletableFuture.runAsync(computeRyFslTask));
        futures.add(CompletableFuture.runAsync(computeRySzlTask));
        futures.add(CompletableFuture.runAsync(computeRyFzlTask));
        futures.add(CompletableFuture.runAsync(yfwfzmPrintTask));
        futures.add(CompletableFuture.runAsync(qszmPrintTask));
        futures.add(CompletableFuture.runAsync(bdcdjbPrintTask));
        futures.add(CompletableFuture.runAsync(computeRyZhcxTask));

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get(90, TimeUnit.SECONDS);
    }

    /**
     * 初始化统计的人员信息
     */
    private List<BdcRyGzltjXmxxVO> initRyxx(String bmids){
        // 1、根据部门id查询部门下所有人信息
        if(StringUtils.isNotBlank(bmids)){
            List<UserDto> userDtoList = this.getUserDtoByBmids(bmids);
            if(CollectionUtils.isNotEmpty(userDtoList)){
                List<BdcRyGzltjXmxxVO> allUserRoles = this.queryAllUserWithRoles();
                Map<String, String> userRoleMap = allUserRoles.stream().collect(Collectors.toMap(BdcRyGzltjXmxxVO::getRyid, BdcRyGzltjXmxxVO::getJsmc, (k1, k2)->k1));
                List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList = new ArrayList<>(userDtoList.size());
                for(UserDto userDto : userDtoList){
                    String roleName = Optional.ofNullable(userRoleMap.get(userDto.getId())).orElse("");
                    bdcRyGzltjXmxxVOList.add(new BdcRyGzltjXmxxVO(userDto.getAlias(), userDto.getId(), roleName));
                }
                return bdcRyGzltjXmxxVOList;
            }
        }else{
            // 获取所有人员角色信息
            return queryAllUserWithRoles();
        }
        return null;
    }

    /**
     * 全部人员信息，从缓存中读取，没有获取到人员信息调用大云接口获取
     * 缓存存在失效时间
     */
    private List<BdcRyGzltjXmxxVO> queryAllUserWithRoles(){
        String allUserJson = redisUtils.getStringValue(GZLTJ_ALLUSER_ROLES_REDIS_KEY);
        if(StringUtils.isNotBlank(allUserJson)){
            return JSONObject.parseArray(allUserJson, BdcRyGzltjXmxxVO.class);
        }else{
            // 调用大云接口，获取每个人员的角色信息
            List<UserDto> userDtoList = userManagerClient.allUsers(null,null , 1);
            List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList = new ArrayList<>(userDtoList.size());
            List<Future<BdcRyGzltjXmxxVO>> futureList = new ArrayList<>(userDtoList.size());
            for(UserDto userDto : userDtoList){
                Future<BdcRyGzltjXmxxVO> ryxxFuture = ThreadPoolUtils.threadPool.submit(new RyxxInitTask(userManagerClient, userDto));
                futureList.add(ryxxFuture);
            }
            try {
                for(Future<BdcRyGzltjXmxxVO> future: futureList){
                    BdcRyGzltjXmxxVO bdcRyGzltjXmxxVO = future.get();
                    if(null == bdcRyGzltjXmxxVO){
                        continue;
                    }
                    bdcRyGzltjXmxxVOList.add(bdcRyGzltjXmxxVO);
                }
            } catch (Exception e) {
                LOGGER.error("获取统计的人员信息出错，错误内容：{}", e.getMessage());
            }

            if(CollectionUtils.isNotEmpty(bdcRyGzltjXmxxVOList)){
                redisUtils.addStringValue(GZLTJ_ALLUSER_ROLES_REDIS_KEY, JSON.toJSONString(bdcRyGzltjXmxxVOList), REDIS_EXPIRE_SECONDS);
            }
            return bdcRyGzltjXmxxVOList;
        }
    }

    /**
     * 周期性工作量统计内容如下：（按人员 或 按部门）
     *     1、收件量： 统计BDC_XM表数据，按项目为xmid分组， 按受理编号为 slbh 分组
     *     2、打印量： 分 yfwfzm 、 qszm 、 bdcdjb
     *     3、缮证证书量 4、缮证证明量 ： zslx 区分 1(证书) 2（证明）
     *     5、纸质证书量、纸质证明量： qzysxlh有值为纸质证
     *     6、电子证书量、电子证明量： zzbs有值为电子证
     *     7、纸质与电子证书量、 纸质与电子证明量： qzysxlh有值、zzbs有值
     *     8、按djlx统计收件量
     */
    @Override
    public List<BdcZqxGzltjXmxxVO> listZqxGzltjByBdcxx(GzltjQO gzltjQO) {
        if(StringUtils.isAnyBlank(gzltjQO.getTjlx(), gzltjQO.getDimension())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数统计类型或统计维度。");
        }
        long startTime = System.currentTimeMillis();

        // 1、获取需要统计的人员或部门信息
        List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList = null;
        if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(gzltjQO.getDimension())){
            bdcZqxGzltjXmxxVOList = this.initZqxBmxx(gzltjQO.getBmid());
        }else{
            bdcZqxGzltjXmxxVOList = this.initZqxRyxx(gzltjQO.getBmid());
        }

        if(CollectionUtils.isEmpty(bdcZqxGzltjXmxxVOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到部门信息。");
        }
        // 并行计算工作量信息
        try {
            this.excutZqxGzltjTask(gzltjQO, bdcZqxGzltjXmxxVOList);
        } catch(TimeoutException e1){
            throw new AppException(ErrorCode.CUSTOM, "计算周期性工作量信息超时");
        }catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.CUSTOM, "计算周期性工作量信息时报错，错误内容：" + e.getMessage());
        }

        LOGGER.info("统计周期性工作量结束，耗时：{}。", System.currentTimeMillis() - startTime);
        return bdcZqxGzltjXmxxVOList;
    }

    /**
     * 计算周期性工作量信息， 异步执行所有计算任务，执行时间大概为：最长的计算任务时间
     * <p>设置了异步任务最长执行时间30s, 超过这个时间后，会抛出异常计算超时</p>
     */
    private void excutZqxGzltjTask(GzltjQO gzltjQO,  List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList) throws InterruptedException, ExecutionException, TimeoutException {
        // 1、统计收件量
        ComputeZqxSjlTask computeZqxSjlTask = new ComputeZqxSjlTask(gzltjQO, bdcZqxGzltjXmxxVOList, bdcGzltjXmxxMapper);
        // 2、统计有房无房证明量
        ComputeZqxPrintTask yfwfzmPrintTask = new ComputeZqxPrintTask(gzltjQO, bdcZqxGzltjXmxxVOList, Constants.GZLTJ_DYLX_YFWFZM, bdcZhcxFeignService);
        // 3、统计权属证明证明量
        ComputeZqxPrintTask qszmPrintTask = new ComputeZqxPrintTask(gzltjQO, bdcZqxGzltjXmxxVOList, Constants.GZLTJ_DYLX_QSZM, bdcZhcxFeignService);
        // 4、统计不动产登记簿量
        ComputeZqxPrintTask bdcdjbPrintTask = new ComputeZqxPrintTask(gzltjQO, bdcZqxGzltjXmxxVOList, Constants.GZLTJ_DYLX_BDCDJB, bdcZhcxFeignService);
        // 5、统计缮证证书量
        ComputeZqxSzlTask computeZslTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZS, null, bdcGzltjXmxxMapper);
        // 6、统计缮证证明量
        ComputeZqxSzlTask computeZmlTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZM, null, bdcGzltjXmxxMapper);
        // 7、统计纸质证书量
        ComputeZqxSzlTask computeZzzslTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZS, CommonConstantUtils.ZSYS_ZZZ, bdcGzltjXmxxMapper);
        // 8、统计纸质证明量
        ComputeZqxSzlTask computeZzzmlTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZM, CommonConstantUtils.ZSYS_ZZZ, bdcGzltjXmxxMapper);
        // 9、统计电子证书量
        ComputeZqxSzlTask computeDzzslTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZS, CommonConstantUtils.ZSYS_DZZ, bdcGzltjXmxxMapper);
        // 10、统计电子证明量
        ComputeZqxSzlTask computeDzzmlTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZM, CommonConstantUtils.ZSYS_DZZ, bdcGzltjXmxxMapper);
        // 11、统计纸质与电子证书量
        ComputeZqxSzlTask computeZzDzzslTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZS, CommonConstantUtils.ZSYS_ZZZDZZ, bdcGzltjXmxxMapper);
        // 12、统计纸质与电子证明量
        ComputeZqxSzlTask computeZzDzzmlTask = new ComputeZqxSzlTask(gzltjQO, bdcZqxGzltjXmxxVOList, CommonConstantUtils.ZSLX_ZM, CommonConstantUtils.ZSYS_ZZZDZZ, bdcGzltjXmxxMapper);
        // 13、统计登记小类办件量
        ComputeZqxDjlxSjlTask computeZqxDjlxSjlTask = new ComputeZqxDjlxSjlTask(gzltjQO, bdcZqxGzltjXmxxVOList, hbGzldyids, bdcGzltjXmxxMapper);
        // 14、统计权利类型办件量
        ComputeZqxQllxSjlTask computeZqxQllxSjlTask = new ComputeZqxQllxSjlTask(gzltjQO, bdcZqxGzltjXmxxVOList, hbGzldyids, bdcGzltjXmxxMapper);
        // 15、统计合并流程办件量
        ComputeZqxHblcSjlTask computeZqxHblcSjlTask = new ComputeZqxHblcSjlTask(gzltjQO, bdcZqxGzltjXmxxVOList, hbGzldyids, bdcGzltjXmxxMapper);
        // 16、统计综合查询量
        ComputeZqxZhcxTask computeZqxZhcxTask = new ComputeZqxZhcxTask(gzltjQO, bdcZqxGzltjXmxxVOList, bdcZhcxFeignService);
        // 17、统计复审量
        ComputeZqxFslTask computeZqxFslTask = new ComputeZqxFslTask(gzltjQO, bdcZqxGzltjXmxxVOList,bdcZhcxFeignService, bdcGzltjXmxxMapper);
        // 18、统计人员发证量
        ComputeZqxFzlTask computeZqxFzlTask = new ComputeZqxFzlTask(gzltjQO, bdcZqxGzltjXmxxVOList,bdcZhcxFeignService, bdcGzltjXmxxMapper);
        // 19、统计重点流程办件量
        ComputeZqxZdlcSjlTask computeZqxZdlcTask = new ComputeZqxZdlcSjlTask(gzltjQO, bdcZqxGzltjXmxxVOList, bdcGzltjXmxxMapper);

        List<CompletableFuture<Void>> futures = new ArrayList<>(15);
        futures.add(CompletableFuture.runAsync(computeZqxSjlTask));
        futures.add(CompletableFuture.runAsync(yfwfzmPrintTask));
        futures.add(CompletableFuture.runAsync(qszmPrintTask));
        futures.add(CompletableFuture.runAsync(bdcdjbPrintTask));
        futures.add(CompletableFuture.runAsync(computeZslTask));
        futures.add(CompletableFuture.runAsync(computeZmlTask));
        futures.add(CompletableFuture.runAsync(computeZzzslTask));
        futures.add(CompletableFuture.runAsync(computeZzzmlTask));
        futures.add(CompletableFuture.runAsync(computeDzzslTask));
        futures.add(CompletableFuture.runAsync(computeDzzmlTask));
        futures.add(CompletableFuture.runAsync(computeZzDzzslTask));
        futures.add(CompletableFuture.runAsync(computeZzDzzmlTask));
        futures.add(CompletableFuture.runAsync(computeZqxDjlxSjlTask));
        futures.add(CompletableFuture.runAsync(computeZqxQllxSjlTask));
        futures.add(CompletableFuture.runAsync(computeZqxHblcSjlTask));
        futures.add(CompletableFuture.runAsync(computeZqxZhcxTask));
        futures.add(CompletableFuture.runAsync(computeZqxFslTask));
        futures.add(CompletableFuture.runAsync(computeZqxFzlTask));
        futures.add(CompletableFuture.runAsync(computeZqxZdlcTask));
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get(90, TimeUnit.SECONDS);
    }

    /**
     * 初始化部门信息
     * <p>1、存在 bmid 时，调用大云接口获取部门下信息
     * 2、没有bmid 时，查询所有所有部门信息。先查看缓存中是否有全部部门信息，有直接返回缓存内容。无则查询后设置 redis 缓存中，在返回</p>
     */
    private List<BdcZqxGzltjXmxxVO> initZqxBmxx(String bmid){
        List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList = null;
        // 查询参数重存在部门ID, 则查询单个部门信息
        if(StringUtils.isNotBlank(bmid)){
            List<String> bmidList = Arrays.asList(bmid.split(CommonConstantUtils.ZF_YW_DH));
            List<OrganizationDto> organizationDtoList = this.organizationManagerClientMatcher.findOrgByIds(bmidList);
            bdcZqxGzltjXmxxVOList = new ArrayList<>(1);
            for(OrganizationDto organizationDto : organizationDtoList){
                bdcZqxGzltjXmxxVOList.add(new BdcZqxGzltjXmxxVO(organizationDto.getName(), organizationDto.getCode()));
            }
            return bdcZqxGzltjXmxxVOList;
        }else{
            // 获取所有组织信息
            String allOrgsJson = redisUtils.getStringValue(GZLTJ_ALL_ORGS_REDIS_KEY);
            if(StringUtils.isNotBlank(allOrgsJson)){
                return JSONObject.parseArray(allOrgsJson, BdcZqxGzltjXmxxVO.class);
            }else{
                List<OrganizationDto> organizationDtoList = this.organizationManagerClientMatcher.listOrgs(1);
                if(CollectionUtils.isNotEmpty(organizationDtoList)){
                    bdcZqxGzltjXmxxVOList = new ArrayList<>(organizationDtoList.size());
                    for(OrganizationDto org : organizationDtoList){
                        bdcZqxGzltjXmxxVOList.add(new BdcZqxGzltjXmxxVO(org.getName(), org.getCode()));
                    }
                    redisUtils.addStringValue(GZLTJ_ALL_ORGS_REDIS_KEY, JSON.toJSONString(bdcZqxGzltjXmxxVOList), REDIS_EXPIRE_SECONDS);
                }
            }
        }
        return bdcZqxGzltjXmxxVOList;
    }

    /**
     * 初始化周期性人员信息
     * <p>1、存在 bmids 时，调用大云接口获取部门下所有人员信息
     *    2、没有bmid 时，查询所有人员信息。先查看缓存中是否有全部人员信息，有直接返回缓存内容。无则查询后设置 redis 缓存中，在返回</p>
     */
    private List<BdcZqxGzltjXmxxVO> initZqxRyxx(String bmids){
        if(StringUtils.isNotBlank(bmids)){
            List<UserDto> userDtoList = this.getUserDtoByBmids(bmids);
            return this.convertUserDTOList(userDtoList);
        }else{
            String allUserJson = redisUtils.getStringValue(GZLTJ_ALL_USERS_REDIS_KEY);
            if(StringUtils.isNotBlank(allUserJson)){
                return JSONObject.parseArray(allUserJson, BdcZqxGzltjXmxxVO.class);
            }else{
                List<UserDto> userDtoList = userManagerClient.allUsers(null,null , 1);
                List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList = this.convertUserDTOList(userDtoList);
                if(CollectionUtils.isNotEmpty(bdcZqxGzltjXmxxVOList)){
                    redisUtils.addStringValue(GZLTJ_ALL_USERS_REDIS_KEY, JSON.toJSONString(bdcZqxGzltjXmxxVOList), REDIS_EXPIRE_SECONDS);
                }
                return bdcZqxGzltjXmxxVOList;
            }
        }
    }

    /**
     * UserDTO 转换为 BdcZqxGzltjXmxxVO 信息
     */
    private List<BdcZqxGzltjXmxxVO> convertUserDTOList(List<UserDto> userDtoList){
        List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList = new ArrayList<>(userDtoList.size());
        if(CollectionUtils.isNotEmpty(userDtoList)){
            for(UserDto userDto : userDtoList){
                bdcZqxGzltjXmxxVOList.add(new BdcZqxGzltjXmxxVO( userDto.getAlias(), userDto.getId(), null));
            }
        }
        return bdcZqxGzltjXmxxVOList;
    }

    /**
     * 根据部门ID获取人员信息
     * @param bmids 部门ids
     * @return 部门下的用户信息
     */
    private List<UserDto> getUserDtoByBmids(String bmids){
        List<String> bmidList = Arrays.asList(bmids.split(CommonConstantUtils.ZF_YW_DH));
        List<UserDto> userDtoList = new ArrayList<>(bmidList.size()*10);
        for(String bmid : bmidList){
            String bmUserJson = redisUtils.getStringValue(GZLTJ_ALL_USERS_REDIS_KEY + bmid);
            if(StringUtils.isNotBlank(bmUserJson)){
                userDtoList.addAll(JSONObject.parseArray(bmUserJson, UserDto.class));
            }else{
                List<UserDto> bmUserDtoList = this.organizationManagerClientMatcher.listUsersByOrg(bmid);
                if(CollectionUtils.isNotEmpty(bmUserDtoList)){
                    userDtoList.addAll(bmUserDtoList);
                }
            }
        }
        return userDtoList;
    }

    @Override
    public List<BdcCstjxxVO> listBdcCstjxx(String conditionStr) {
        if(StringUtils.isBlank(conditionStr)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到查询条件信息");
        }
        List<RequestCondition> conditionList = JSONArray.parseArray(conditionStr, RequestCondition.class);
        if(CollectionUtils.isEmpty(conditionList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到查询条件信息");
        }
        List<RequestCondition> bmCondition = conditionList.stream().filter(t->t.getRequestKey().equals("startUserDepId")).collect(Collectors.toList());
        List<CompletableFuture<Void>> futures = new ArrayList<>(15);
        List<BdcCstjxxVO> bdcCstjxxVOList = new ArrayList<>(10);
        if(CollectionUtils.isEmpty(bmCondition)){
            List<OrganizationDto> organizationDtoList = organizationManagerClientMatcher.findRootOrgs();
            if(CollectionUtils.isNotEmpty(organizationDtoList)){
                for(OrganizationDto org : organizationDtoList){
                    futures.add(CompletableFuture.runAsync(new BdcLcCsjTjTask(bdcCstjxxVOList, conditionList, org, processTaskCustomExtendClient)));
                }
            }
        }else{
            List<String> bmidList = (List<String>) bmCondition.get(0).getRequestValue();
            if(CollectionUtils.isNotEmpty(bmidList)){
                List<OrganizationDto> organizationDtoList = this.organizationManagerClientMatcher.findOrgByIds(bmidList);
                if(CollectionUtils.isNotEmpty(organizationDtoList)){
                    List<RequestCondition> otherCondition = conditionList.stream().filter(t->!t.getRequestKey().equals("startUserDepId")).collect(Collectors.toList());
                    for(OrganizationDto org : organizationDtoList){
                        futures.add(CompletableFuture.runAsync(new BdcLcCsjTjTask(bdcCstjxxVOList, otherCondition, org, processTaskCustomExtendClient)));
                    }
                }
            }
        }
        try{
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get(90, TimeUnit.SECONDS);
        }catch(TimeoutException e1){
            throw new AppException(ErrorCode.CUSTOM, "统计超时件信息超时");
        }catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.CUSTOM, "统计超时件信息报错，错误内容：" + e.getMessage());
        }
        return bdcCstjxxVOList;
    }

}
