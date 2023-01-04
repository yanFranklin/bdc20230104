package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcYhUrlCsDO;
import cn.gtmap.realestate.common.core.dto.exchange.YhxxTsspxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcYdyDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.YhxxTsRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.core.dto.yhts.YhDyTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.spxx.request.YhTsspxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.YhTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.cf.request.CfBcfrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.cf.request.CfCfrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.cf.request.CfTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.dy.request.DyqDyqrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.dy.request.DyqDyrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.dy.request.DyqTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.dy.response.DyqTzResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.ysf.request.YsfDyqrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.ysf.request.YsfDyrDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.ysf.request.YsfTsxxDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.build.BuildLogServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新增抵押信息推送给银行
 *
 * @author wyh
 * @version 1.0
 * @date 2022/3/4 9:17
 */
@RestController
@Slf4j
public class YhxxTsRestController implements YhxxTsRestService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    HttpClientService httpClientService;

    @Autowired
    BuildLogServiceImpl buildLogService;

    @Autowired
    StorageClient storageClient;

    /**
     * 操作
     */
    public static final String DYDJ_DYAQXXTS = "dydj_dyaqxxts";
    public static final String DYDJ_CFXXTS = "dydj_cfxxts";
    public static final String DYDJ_YSFXXTS = "dydj_ysfxxts";

    public static final String DYDJ_TGXXTS = "dydj_tgxxts";
    /**
     * 需要通知的银行的名称
     */
    @Value("#{'${exchange.xxts.tzqlrmc:}'.split(',')}")
    private List<String> tzQlrMcList;

    /**
     * 需要通知spzt的银行的名称
     */
    @Value("#{'${exchange.spzt.tzqlrmc:}'.split(',')}")
    private List<String> spztTzQlrMcList;

    /**
     * 需要通知spzt的区县
     */
    @Value("#{'${exchange.spzt.tzQxdm:}'.split(',')}")
    private List<String> spztTzQxdm;

    /**
     * 需要通知spzt的sply,现场要求可配置
     */
    @Value("#{'${exchange.spzt.tzSply:}'.split(',')}")
    private List<Integer> spztTzSply;


    /**
     * 法人机构号
     */
    @Value("${exchange.spzt.corporateOrgId: 075}")
    private String spztTzOrgId;
    /**
     * 抵押物二押实时推送抵押信息
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void tzyyh(@RequestParam(name = "processInsId") String processInsId,
                      @RequestParam(value = "currentUserName", required = false) String currentUserName) {
        if (CollectionUtils.isEmpty(tzQlrMcList)) {
            log.info("新增抵押当前未配置需要进行提醒的银行");
            return;
        }
        /**
         * 取得当前项目信息，
         * 如果当前项目有现势的抵押权，则根据根据不动产单元，银行名称，qszt和zjzt查询是否有之前的抵押，如果有，则读取配置表，
         * 将当前抵押推送给对应银行
         */
        List<BdcDyaqDTO> currentBdcDyaqQls = bdcdjMapper.queryDyaqByBdcdyh(processInsId, null, null, null, 1, null);
        if (CollectionUtils.isEmpty(currentBdcDyaqQls)) {
            log.info("新增抵押提醒银行，未找到当前生成的抵押权利信息，实例为{}", processInsId);
            return;
        }
        //当前这一手抵押权的项目id
        Set<String> xmids = currentBdcDyaqQls.stream().map(BdcDyaqDTO::getXmid).collect(Collectors.toSet());

        //查询抵押权是否有上一手抵押
        List<String> allBdcdyh = currentBdcDyaqQls.stream().map(BdcDyaqDTO::getBdcdyh).collect(Collectors.toList());
        List<BdcDyaqDTO> prevBdcDyaqQls = bdcdjMapper.queryDyaqByBdcdyh(null, tzQlrMcList, 1, allBdcdyh, 1, null);
        if (CollectionUtils.isEmpty(prevBdcDyaqQls)) {
            log.info("新增抵押提醒银行，未找到上一手生成的抵押权利信息，实例为{}", processInsId);
            return;
        }
        //不是当前这一手的其他的项目信息
        prevBdcDyaqQls = prevBdcDyaqQls
                .stream()
                .filter(bdcDyaqDTO -> !xmids.contains(bdcDyaqDTO.getXmid())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(prevBdcDyaqQls)) {
            log.info("新增抵押提醒银行，未找到上一手生成的抵押权利信息，实例为{}", processInsId);
            return;
        }

        //查找所有单元号的原始产权 -- 每个单元号只会有一个原始的房地产权
        List<BdcFdcqDO> bdcFdcqDOS = bdcdjMapper.queryFdcqByBdcdys(allBdcdyh, 1);
        if (CollectionUtils.isEmpty(bdcFdcqDOS)) {
            log.info("新增抵押提醒银行,没有找到单元原始产权{}", allBdcdyh);
            throw new AppException("新增抵押提醒银行,没有找到该单元原始产权");
        }

        //当前这一手的抵押权
        Map<String, BdcDyaqDTO> currentBdcDyaqDtoMap = currentBdcDyaqQls
                .stream()
                .collect(Collectors.toMap(BdcDyaqDTO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));
        //上一手抵押权Map
        Map<String, List<BdcDyaqDTO>> prevBdcDyaqDtoMap = prevBdcDyaqQls
                .stream()
                .collect(Collectors.groupingBy(BdcDyaqDTO::getQlrmc));
        //房地产权map
        Map<String, BdcFdcqDO> fdcqDOMap = bdcFdcqDOS
                .stream().collect(Collectors.toMap(BdcFdcqDO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));


        //对每个银行进行处理
        for (String qlrmc : tzQlrMcList) {
            List<BdcDyaqDTO> bdcDyaqDTOS = new ArrayList<>();
            if (MapUtils.isNotEmpty(prevBdcDyaqDtoMap) && CollectionUtils.isNotEmpty(prevBdcDyaqDtoMap.get(qlrmc))) {
                bdcDyaqDTOS = prevBdcDyaqDtoMap.get(qlrmc);
            } else {
                continue;
            }
            //代表这个银行在当前工作流下之前有过抵押
            //根据单元号进行分组
            Map<String, List<BdcDyaqDTO>> bdcDyaqYhMap = bdcDyaqDTOS
                    .stream()
                    .collect(Collectors.groupingBy(BdcDyaqDTO::getBdcdyh));
            //处理单个银行的单个单元号
            for (Map.Entry<String, List<BdcDyaqDTO>> stringListEntry : bdcDyaqYhMap.entrySet()) {
                List<BdcDyaqDTO> orgBdcDyaqDTOList = stringListEntry.getValue();
                BdcDyaqDTO currentDyaqDTO = currentBdcDyaqDtoMap.get(orgBdcDyaqDTOList.get(0).getBdcdyh());
                BdcFdcqDO bdcFdcqDO = fdcqDOMap.get(orgBdcDyaqDTOList.get(0).getBdcdyh());
                try {
                    handleSignalYhDy(qlrmc, processInsId, currentDyaqDTO, orgBdcDyaqDTOList, bdcFdcqDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理单个银行的二次抵押信息
     *
     * @param qlrmc
     * @param processInsId
     * @param currentBdcDyaqQl
     */
    public void handleSignalYhDy(String qlrmc,
                                 String processInsId,
                                 BdcDyaqDTO currentBdcDyaqQl,
                                 List<BdcDyaqDTO> dyaqDTOList,
                                 BdcFdcqDO bdcFdcqDO
    ) {
        if (Objects.isNull(currentBdcDyaqQl) || CollectionUtils.isEmpty(dyaqDTOList) || Objects.isNull(bdcFdcqDO)) {
            log.info("新增抵押提醒银行{},参数{}{}{}", qlrmc,
                    JSON.toJSONString(currentBdcDyaqQl),
                    JSON.toJSONString(dyaqDTOList),
                    JSON.toJSONString(bdcFdcqDO)
            );
            return;
        }
        log.info("新增抵押提醒银行{},权利信息{},原权利{}，房地产权{}", qlrmc, JSON.toJSONString(currentBdcDyaqQl),
                JSON.toJSONString(dyaqDTOList),
                JSON.toJSONString(bdcFdcqDO));
        //请求地址
        String url = "";
        List<BdcYhUrlCsDO> bdcYhUrlCsDOS = bdcdjMapper.queryYhdz(qlrmc, DYDJ_DYAQXXTS);
        if (CollectionUtils.isEmpty(bdcYhUrlCsDOS)) {
            log.info("新增抵押提醒银行{},未找到银行对应地址", qlrmc);
            return;
        }
        url = bdcYhUrlCsDOS.get(0).getYhurl();


        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(currentBdcDyaqQl.getXmid()));
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            log.info("新增抵押提醒银行{},数据错误，未找到对应项目信息", qlrmc);
            return;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);


        //处理数据
        DyqTsxxDTO dto = new DyqTsxxDTO();

        //原始产权证号 -- 房地产权的
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(bdcFdcqDO.getXmid());
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            log.info("新增抵押提醒银行,没有找到该单元原始产权证号{}", bdcFdcqDO);
            throw new AppException("新增抵押提醒银行,没有找到该单元原始产权证号");
        }
        BdcZsDO orgZsDO = bdcZsDOS.get(0);

        //权利人和义务人
        Map paramMap = new HashMap();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", 2);
        paramMap.put("xmid", currentBdcDyaqQl.getXmid());
        List<BdcQlrDO> yqlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(yqlrDOList)) {
            log.info("未查询到需要的义务人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的义务人信息");
        }
        //获取新用户信息,取权利人
        paramMap.put("qlrlb", 1);
        List<BdcQlrDO> qlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(qlrDOList)) {
            log.info("未查询到需要的权利人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的权利人信息");
        }

        //抵押人
        List<DyqDyrDTO> dyqDyrDTOS = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : yqlrDOList) {
            DyqDyrDTO dyqDyrDTO = new DyqDyrDTO();
            dyqDyrDTO.setDyrzjh(bdcQlrDO.getZjh());
            dyqDyrDTO.setDyr(bdcQlrDO.getQlrmc());
            dyqDyrDTO.setZjzl(bdcQlrDO.getZjzl());
            dyqDyrDTOS.add(dyqDyrDTO);
        }
        dto.setDyrList(dyqDyrDTOS);

        //抵押权人
        List<DyqDyqrDTO> dyqDyqrDTOS = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : qlrDOList) {
            DyqDyqrDTO dyqDyqrDTO = new DyqDyqrDTO();
            dyqDyqrDTO.setDyqr(bdcQlrDO.getQlrmc());
            dyqDyqrDTO.setDyqrzjh(bdcQlrDO.getZjh());
            dyqDyqrDTO.setZjzl(bdcQlrDO.getZjzl());
            dyqDyqrDTOS.add(dyqDyqrDTO);
        }
        dto.setDyqrList(dyqDyqrDTOS);

        //不动产权证号
        dto.setCqzh(orgZsDO.getBdcqzh());
        //抵押开始,结束时间
        dto.setZwlxqssj(currentBdcDyaqQl.getZwlxqssj());
        dto.setZwlxjssj(currentBdcDyaqQl.getZwlxjssj());
        //抵押方式
        dto.setDyfs(currentBdcDyaqQl.getDyfs());
        //抵押金额
        if ("1".equals(currentBdcDyaqQl.getDyfs().toString())) {
            //一般抵押，被担保主债权数额
            dto.setDyje(currentBdcDyaqQl.getBdbzzqse());
        } else {
            //最高额抵押 最高债权确定数额
            dto.setDyje(currentBdcDyaqQl.getZgzqqdse());
        }
        //登簿时间
        dto.setDjsj(currentBdcDyaqQl.getDjsj());
        dto.setZl(bdcXmDO.getZl());
        dto.setSlbh(bdcXmDO.getSlbh());
        dto.setXmid(bdcXmDO.getXmid());
        //抵押证明号
        List<YhDyTsxxDTO> yhDyTsxxs = new ArrayList<>();
        generateYhdaxxList(dyaqDTOList, yhDyTsxxs);
        dto.setYhDyxxList(yhDyTsxxs);


        if (Objects.nonNull(dto)) {
            try {
                log.info("推送银行抵押消息{}", JSON.toJSONString(dto));
                notice(qlrmc, dto, url, "抵押");
            } catch (Exception e) {
                log.info("推送银行抵押消息错误{}：{}", JSON.toJSONString(dto),e.getMessage());
            }
        }
    }


    /**
     * 抵押物被查封时推送查封信息
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void cftzyyh(@RequestParam(name = "processInsId") String processInsId,
                        @RequestParam(value = "currentUserName", required = false) String currentUserName) {
        if (CollectionUtils.isEmpty(tzQlrMcList)) {
            log.info("抵押物被查封未配置需要进行提醒的银行");
            return;
        }

        if (StringUtils.isBlank(processInsId)) {
            log.info("抵押物被查封工作流参数为空");
            return;
        }
        //当前的查封信息
        List<BdcCfDTO> currentBdcCfQls = bdcdjMapper.queryCfByBdcdyh(processInsId, null, null, null, 1, null);
        if (CollectionUtils.isEmpty(currentBdcCfQls)) {
            log.info("抵押物被查封提醒银行，未找到当前生成的查封权利信息，实例为{}", processInsId);
            return;
        }

        //查询是否有对应的抵押权
        List<String> allBdcdyh = currentBdcCfQls.stream().map(BdcCfDTO::getBdcdyh).collect(Collectors.toList());
        List<BdcDyaqDTO> orgBdcDyaqQls = bdcdjMapper.queryDyaqByBdcdyh(null, tzQlrMcList, 1, allBdcdyh, 1, null);
        if (CollectionUtils.isEmpty(orgBdcDyaqQls)) {
            log.info("抵押物被查封提醒银行，未找到抵押权利信息，实例为{}", processInsId);
            return;
        }

        //查找所有单元号的原始产权
        List<BdcFdcqDO> bdcFdcqDOS = bdcdjMapper.queryFdcqByBdcdys(allBdcdyh, 1);
        if (CollectionUtils.isEmpty(bdcFdcqDOS)) {
            log.info("抵押物被查封提醒银行,没有找到单元原始产权{}", allBdcdyh);
            throw new AppException("抵押物被查封提醒银行,没有找到该单元原始产权");
        }

        //当前这一手查封
        Map<String, BdcCfDTO> currentBdcCfDtoMap = currentBdcCfQls
                .stream()
                .collect(Collectors.toMap(BdcCfDTO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));
        //上一手抵押权Map -- 同一个银行可能会有多个
        Map<String, List<BdcDyaqDTO>> orgBdcDyaqDtoMap = orgBdcDyaqQls
                .stream()
                .collect(Collectors.groupingBy(BdcDyaqDTO::getQlrmc));
        //房地产权map
        Map<String, BdcFdcqDO> fdcqDOMap = bdcFdcqDOS
                .stream().collect(Collectors.toMap(BdcFdcqDO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));


        //对每个银行进行处理
        for (String qlrmc : tzQlrMcList) {
            List<BdcDyaqDTO> bdcDyaqDTOS;
            if (MapUtils.isNotEmpty(orgBdcDyaqDtoMap) && CollectionUtils.isNotEmpty(orgBdcDyaqDtoMap.get(qlrmc))) {
                bdcDyaqDTOS = orgBdcDyaqDtoMap.get(qlrmc);
            } else {
                continue;
            }
            //根据单元号进行分组
            Map<String, List<BdcDyaqDTO>> bdcDyaqYhMap = bdcDyaqDTOS
                    .stream()
                    .collect(Collectors.groupingBy(BdcDyaqDTO::getBdcdyh));


            for (Map.Entry<String, List<BdcDyaqDTO>> stringListEntry : bdcDyaqYhMap.entrySet()) {
                List<BdcDyaqDTO> orgBdcDyaqDTOList = stringListEntry.getValue();
                BdcCfDTO currentCfDTO = currentBdcCfDtoMap.get(orgBdcDyaqDTOList.get(0).getBdcdyh());
                BdcFdcqDO bdcFdcqDO = fdcqDOMap.get(orgBdcDyaqDTOList.get(0).getBdcdyh());
                try {
                    handleSignalYhCf(qlrmc, processInsId, currentCfDTO, orgBdcDyaqDTOList, bdcFdcqDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理单个银行的单个查封
     *
     * @param qlrmc
     * @param processInsId
     * @param bdcCfDTO
     * @param bdcDyaqDTOList
     * @param bdcFdcqDO
     */
    public void handleSignalYhCf(String qlrmc,
                                 String processInsId,
                                 BdcCfDTO bdcCfDTO,
                                 List<BdcDyaqDTO> bdcDyaqDTOList,
                                 BdcFdcqDO bdcFdcqDO
    ) {
        log.info("抵押物被查封提醒银行{},参数{}{}{}", qlrmc,
                JSON.toJSONString(bdcCfDTO),
                JSON.toJSONString(bdcDyaqDTOList),
                JSON.toJSONString(bdcFdcqDO)
        );
        if (Objects.isNull(bdcCfDTO) || Objects.isNull(bdcDyaqDTOList) || Objects.isNull(bdcFdcqDO)) {
            return;
        }
        //请求地址
        String url = "";
        List<BdcYhUrlCsDO> bdcYhUrlCsDOS = bdcdjMapper.queryYhdz(qlrmc, DYDJ_CFXXTS);
        if (CollectionUtils.isEmpty(bdcYhUrlCsDOS)) {
            log.info("抵押物被查封提醒银行{},未找到银行对应地址", qlrmc);
            return;
        }
        url = bdcYhUrlCsDOS.get(0).getYhurl();

        //查找查封对应的项目的信息
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(bdcCfDTO.getXmid()));
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            log.info("抵押物被查封提醒银行{},数据错误，未找到对应项目信息", qlrmc);
            return;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);


        //处理数据
        CfTsxxDTO dto = new CfTsxxDTO();

        //原始产权证号--房地产权的证号
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(bdcFdcqDO.getXmid());
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            log.info("抵押物被查封提醒银行,没有找到该单元原始产权证号{}", bdcFdcqDO);
            throw new AppException("新增抵押提醒银行,没有找到该单元原始产权证号");
        }
        BdcZsDO orgZsDO = bdcZsDOS.get(0);

        //权利人和义务人
        Map paramMap = new HashMap();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", 2);
        paramMap.put("xmid", bdcCfDTO.getXmid());
        List<BdcQlrDO> yqlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(yqlrDOList)) {
            log.info("未查询到需要的义务人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的义务人信息");
        }
        //获取新用户信息,取权利人
        paramMap.put("qlrlb", 1);
        List<BdcQlrDO> qlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(qlrDOList)) {
            log.info("未查询到需要的权利人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的权利人信息");
        }

        //被查封人
        List<CfBcfrDTO> cfBcfrDTOS = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : yqlrDOList) {
            CfBcfrDTO bcfrDTO = new CfBcfrDTO();
            bcfrDTO.setBcfrzjh(bdcQlrDO.getZjh());
            bcfrDTO.setBcfr(bdcQlrDO.getQlrmc());
            bcfrDTO.setZjzl(bdcQlrDO.getZjzl());
            cfBcfrDTOS.add(bcfrDTO);
        }
        dto.setBcfrList(cfBcfrDTOS);

        //查封人
        List<CfCfrDTO> cfCfrDTOS = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : qlrDOList) {
            CfCfrDTO cfCfrDTO = new CfCfrDTO();
            cfCfrDTO.setCfr(bdcQlrDO.getQlrmc());
            cfCfrDTO.setCfrzjh(bdcQlrDO.getZjh());
            cfCfrDTO.setZjzl(bdcQlrDO.getZjzl());
            cfCfrDTOS.add(cfCfrDTO);
        }
        dto.setCfrList(cfCfrDTOS);

        //不动产权证号
        dto.setCqzh(orgZsDO.getBdcqzh());
        dto.setZl(orgZsDO.getZl());
        //查封开始,结束时间
        dto.setCfqssj(bdcCfDTO.getCfqssj());
        dto.setCfjssj(bdcCfDTO.getCfjssj());
        //查封方式
        dto.setCflx(bdcCfDTO.getCflx());
        //查封文号
        dto.setCfwh(bdcCfDTO.getCfwh());
        //查封机关
        dto.setCfjg(bdcCfDTO.getCfjg());
        //查封机关证件号
        dto.setCfjgzjh(qlrDOList.get(0).getZjh());
        //登簿时间
        dto.setDjsj(bdcCfDTO.getDjsj());
        dto.setSlbh(bdcXmDO.getSlbh());
        dto.setXmid(bdcXmDO.getXmid());

        // 属于该银行的那一笔抵押对应的产权证号 -- 可能之前会有多笔抵押
        List<YhDyTsxxDTO> yhDyTsxxs = new ArrayList<>();
        generateYhdaxxList(bdcDyaqDTOList, yhDyTsxxs);
        dto.setYhDyxxList(yhDyTsxxs);


        if (Objects.nonNull(dto)) {
            try {
                log.info("推送银行抵押物被查封消息{}", JSON.toJSONString(dto));
                notice(qlrmc, dto, url, "查封");
            } catch (Exception e) {
                log.info("推送银行抵押物被查封消息{}", JSON.toJSONString(dto));
            }
        }
    }

    /**
     * 一手房信息推送,只要当前有抵押的信息就行，如果有预抵押信息把预抵押信息带上
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void ysftzyh(@RequestParam(name = "processInsId") String processInsId,
                        @RequestParam(value = "currentUserName", required = false) String currentUserName) {
        if (CollectionUtils.isEmpty(tzQlrMcList)) {
            log.info("一手房信息推送当前未配置需要进行提醒的银行");
            return;
        }
        //查找当前的抵押权
        List<BdcDyaqDTO> currentBdcDyaqQls = bdcdjMapper.queryDyaqByBdcdyh(processInsId, null, 1, null, 1, null);
        if (CollectionUtils.isEmpty(currentBdcDyaqQls)) {
            log.info("一手房信息推送银行，未找到当前生成的抵押权利信息，实例为{}", processInsId);
            return;
        }

        //查询抵押权是否有预抵押信息,非必须
        List<String> allBdcdyh = currentBdcDyaqQls.stream().map(BdcDyaqDTO::getBdcdyh).collect(Collectors.toList());
        List<BdcYdyDTO> bdcYdyDTOS = bdcdjMapper.queryYdyByBdcdyh(tzQlrMcList, 1, allBdcdyh);

        //查找所有单元号的原始产权（房地产权）
        List<BdcFdcqDO> bdcFdcqDOS = bdcdjMapper.queryFdcqByBdcdys(allBdcdyh, 1);
        if (CollectionUtils.isEmpty(bdcFdcqDOS)) {
            log.info("一手房信息推送银行,没有找到单元原始产权{}", allBdcdyh);
            throw new AppException("一手房信息推送银行,没有找到该单元原始产权");
        }

        //当前这一手的抵押权
        Map<String, List<BdcDyaqDTO>> currentBdcDyaqDtoMap = currentBdcDyaqQls
                .stream()
                .collect(Collectors.groupingBy(BdcDyaqDTO::getQlrmc));
        //预抵押
        Map<String, BdcYdyDTO> orgBdcDyaqDtoMap = bdcYdyDTOS
                .stream()
                .collect(Collectors.toMap(BdcYdyDTO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));

        //房地产权map
        Map<String, BdcFdcqDO> fdcqDOMap = bdcFdcqDOS
                .stream()
                .collect(Collectors.toMap(BdcFdcqDO::getBdcdyh, o -> o, (oldOne, newOne) -> oldOne));


        //对每个银行进行处理
        for (String qlrmc : tzQlrMcList) {
            List<BdcDyaqDTO> bdcDyaqDTOS = new ArrayList<>();
            if (MapUtils.isNotEmpty(currentBdcDyaqDtoMap)
                    && CollectionUtils.isNotEmpty(currentBdcDyaqDtoMap.get(qlrmc))) {
                bdcDyaqDTOS = currentBdcDyaqDtoMap.get(qlrmc);
            } else {
                continue;
            }
            //处理该银行下单个单元号
            for (BdcDyaqDTO currentDyaqDTO : bdcDyaqDTOS) {
                BdcYdyDTO bdcYdyDTO = orgBdcDyaqDtoMap.get(currentDyaqDTO.getBdcdyh());
                BdcFdcqDO bdcFdcqDO = fdcqDOMap.get(currentDyaqDTO.getBdcdyh());
                try {
                    handleSignalYhYsf(qlrmc, processInsId, currentDyaqDTO, bdcYdyDTO, bdcFdcqDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 异步处理单个银行一手房信息
     *
     * @param qlrmc
     * @param processInsId
     * @param currentBdcDyaqQl
     */
    public void handleSignalYhYsf(String qlrmc,
                                  String processInsId,
                                  BdcDyaqDTO currentBdcDyaqQl,
                                  BdcYdyDTO bdcYdyDTO,
                                  BdcFdcqDO bdcFdcqDO
    ) {
        log.info("一手房信息推送银行{},参数{}{}{}", qlrmc,
                JSON.toJSONString(currentBdcDyaqQl),
                JSON.toJSONString(bdcYdyDTO),
                JSON.toJSONString(bdcFdcqDO)
        );
        if (Objects.isNull(currentBdcDyaqQl) || Objects.isNull(bdcFdcqDO)) {
            return;
        }
        //请求地址
        String url = "";
        List<BdcYhUrlCsDO> bdcYhUrlCsDOS = bdcdjMapper.queryYhdz(qlrmc, DYDJ_YSFXXTS);
        if (CollectionUtils.isEmpty(bdcYhUrlCsDOS)) {
            log.info("一手房信息推送银行{},未找到银行对应地址", qlrmc);
            return;
        }
        url = bdcYhUrlCsDOS.get(0).getYhurl();


        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(currentBdcDyaqQl.getXmid()));
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            log.info("一手房信息推送银行{},数据错误，未找到对应项目信息", qlrmc);
            return;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);


        //处理数据
        YsfTsxxDTO dto = new YsfTsxxDTO();

        //原始产权证号
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(bdcFdcqDO.getXmid());
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            log.info("一手房信息推送银行,没有找到该单元原始产权证号{}", bdcFdcqDO);
            throw new AppException("一手房信息推送银行,没有找到该单元原始产权证号");
        }
        BdcZsDO orgFdcqZsDO = bdcZsDOS.get(0);

        //抵押产权证书
        bdcZsQO.setXmid(currentBdcDyaqQl.getXmid());
        bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            log.info("一手房信息推送银行,没有找到该单元抵押产权证号{}", currentBdcDyaqQl);
            throw new AppException("一手房信息推送银行,没有找到该单元抵押证号");
        }
        BdcZsDO dyqZsDO = bdcZsDOS.get(0);

        // 预抵押产权证书
        BdcZsDO ydyZsDO = null;
        if (Objects.nonNull(bdcYdyDTO)) {
            bdcZsQO.setXmid(bdcYdyDTO.getXmid());
            bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                ydyZsDO = bdcZsDOS.get(0);
            }
        }

        //权利人和义务人
        Map paramMap = new HashMap();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", 2);
        paramMap.put("xmid", currentBdcDyaqQl.getXmid());
        List<BdcQlrDO> yqlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(yqlrDOList)) {
            log.info("未查询到需要的义务人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的义务人信息");
        }
        //获取新用户信息,取权利人
        paramMap.put("qlrlb", 1);
        List<BdcQlrDO> qlrDOList = bdcdjMapper.queryQlrList(paramMap);
        if (CollectionUtils.isEmpty(qlrDOList)) {
            log.info("未查询到需要的权利人信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要的权利人信息");
        }

        //抵押人
        List<YsfDyrDTO> ysfDyrs = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : yqlrDOList) {
            YsfDyrDTO ysfDyrDTO = new YsfDyrDTO();
            ysfDyrDTO.setDyrzjh(bdcQlrDO.getZjh());
            ysfDyrDTO.setDyr(bdcQlrDO.getQlrmc());
            ysfDyrDTO.setZjzl(bdcQlrDO.getZjzl());
            ysfDyrs.add(ysfDyrDTO);
        }
        dto.setDyrList(ysfDyrs);

        //抵押权人
        List<YsfDyqrDTO> ysfDyqrs = new ArrayList<>();
        for (BdcQlrDO bdcQlrDO : qlrDOList) {
            YsfDyqrDTO ysfDyqrDTO = new YsfDyqrDTO();
            ysfDyqrDTO.setDyqr(bdcQlrDO.getQlrmc());
            ysfDyqrDTO.setDyqrzjh(bdcQlrDO.getZjh());
            ysfDyqrDTO.setZjzl(bdcQlrDO.getZjzl());
            ysfDyqrs.add(ysfDyqrDTO);
        }
        dto.setDyqrList(ysfDyqrs);

        //不动产权证号
        dto.setCqzh(orgFdcqZsDO.getBdcqzh());
        //抵押开始,结束时间
        dto.setZwlxqssj(currentBdcDyaqQl.getZwlxqssj());
        dto.setZwlxjssj(currentBdcDyaqQl.getZwlxjssj());
        //抵押方式
        dto.setDyfs(currentBdcDyaqQl.getDyfs());
        //抵押金额
        if ("1".equals(currentBdcDyaqQl.getDyfs().toString())) {
            //一般抵押，被担保主债权数额
            dto.setDyje(currentBdcDyaqQl.getBdbzzqse());
        } else {
            //最高额抵押 最高债权确定数额
            dto.setDyje(currentBdcDyaqQl.getZgzqqdse());
        }
        //登簿时间
        dto.setDjsj(currentBdcDyaqQl.getDjsj());
        //抵押不动产权证号
        dto.setDybdcqzh(dyqZsDO.getBdcqzh());
        //抵押电子证照
        try {
            BaseResultDto baseResultDto = storageClient.downloadBase64(dyqZsDO.getStorageid());
            if (null != baseResultDto) {
                dto.setDzzz(baseResultDto.getMsg());
            }
        }catch (Exception e){

        }
        dto.setZl(bdcXmDO.getZl());
        dto.setSlbh(bdcXmDO.getSlbh());
        dto.setXmid(bdcXmDO.getXmid());
        //预抵押证明号
        if (Objects.nonNull(ydyZsDO)) {
            dto.setTzyhdydyzmh(ydyZsDO.getBdcqzh());
        }
        dto.setGyqk(bdcFdcqDO.getGyqk());
        dto.setJzmj(bdcFdcqDO.getJzmj());
        dto.setJgsj(bdcFdcqDO.getJgsj());

        if (Objects.nonNull(dto)) {
            try {
                log.info("推送银行抵押消息{}", JSON.toJSONString(dto));
                notice(qlrmc, dto, url, "一手房");
            } catch (Exception e) {
                log.info("推送银行抵押消息{}", JSON.toJSONString(dto));
            }
        }
    }


    /**
     * 提交请求
     *
     * @param qlrmc
     * @param dto
     * @param url
     */
    private <T extends YhTsxxDTO> void notice(String qlrmc, T dto, String url, String tsxxlx) {
        List<NameValuePair> parametersGh = Lists.newArrayList();
        parametersGh.add(new BasicNameValuePair("", JSON.toJSONString(dto)));
        log.info("---新的{}信息推送给银行 http请求参数:{},请求地址:{}", tsxxlx, JSONObject.toJSONString(parametersGh), url);
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntityGh = new StringEntity(JSONObject.toJSONString(dto), "utf-8");
        stringEntityGh.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        stringEntityGh.setContentType("application/json");
        httpPost.setEntity(stringEntityGh);
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String responseGh = "";
        AuditEventBO auditEventBO = new AuditEventBO();
        auditEventBO.setSlbh(dto.getSlbh());
        auditEventBO.setRequest(JSON.toJSONString(dto));
        LogBO logBO = new LogBO();
        logBO.setLogService("新的" + tsxxlx + "信息推送给银行");
        logBO.setRequester(JSON.toJSONString(dto));
        try {
            responseGh = httpClientService.doPost(httpPost, "UTF-8");
            log.info("---新的{}信息推送给银行请求成功,响应结果:{}", tsxxlx, responseGh);
            if (Objects.nonNull(responseGh)) {
                DyqTzResponseDTO dyqTzDTo = JSON.parseObject(responseGh, DyqTzResponseDTO.class);
                if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, dyqTzDTo.getCode())) {
                    log.info("---新的{}信息推送给银行请求成功,响应结果:{}", tsxxlx, JSONObject.toJSONString(responseGh));
                } else {
                    log.info("新的{}信息推送给银行请求失败！原因：{}", tsxxlx, JSONObject.toJSONString(responseGh));
                    //throw new AppException("推送给银行失败！");
                }
            } else {
                log.info("新的{}信息推送给银行未返回信息！", tsxxlx);
                //throw new AppException("推送给银行失败！");
            }
        } catch (Exception e) {
            log.error("---新的{}信息推送给银行异常:{},请求url:{},请求param:{}", tsxxlx, url, JSONObject.toJSONString(dto), e);
            //throw new AppException("httpPost请求异常");
        } finally {
            auditEventBO.setResponse(JSON.toJSONString(dto));
            logBO.setResponser(JSON.toJSONString(dto));
            auditEventBO.setLogBO(logBO);
            buildLogService.saveAuditLog(auditEventBO);
        }
    }


    /**
     * 处理银行抵押信息
     *
     * @param bdcDyaqDTOList
     * @param yhDyTsxxs
     * @param yhDyTsxxs
     */
    private void generateYhdaxxList(List<BdcDyaqDTO> bdcDyaqDTOList, List<YhDyTsxxDTO> yhDyTsxxs) {
        List<BdcZsDO> bdcZsDOS;
        BdcZsQO bdcZsQO = new BdcZsQO();
        for (BdcDyaqDTO bdcDyaqDTO : bdcDyaqDTOList) {
            bdcZsQO.setXmid(bdcDyaqDTO.getXmid());
            bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isEmpty(bdcZsDOS)) {
                log.info("抵押物被查封提醒银行,没有找到该单元当前产权证号{}", bdcDyaqDTO);
                continue;
            }
            BdcZsDO dyaqZs = bdcZsDOS.get(0);
            YhDyTsxxDTO yhDyTsxxDTO = new YhDyTsxxDTO();
            yhDyTsxxDTO.setTzyhdyzmh(dyaqZs.getBdcqzh());
            yhDyTsxxDTO.setZl(dyaqZs.getZl());
            yhDyTsxxDTO.setDjsj(bdcDyaqDTO.getDjsj());
            if ("1".equals(bdcDyaqDTO.getDyfs().toString())) {
                //一般抵押，被担保主债权数额
                yhDyTsxxDTO.setDyje(bdcDyaqDTO.getBdbzzqse());
            } else {
                //最高额抵押 最高债权确定数额
                yhDyTsxxDTO.setDyje(bdcDyaqDTO.getZgzqqdse());
            }
            yhDyTsxxDTO.setDyfs(bdcDyaqDTO.getDyfs());
            yhDyTsxxDTO.setZwlxqssj(bdcDyaqDTO.getZwlxqssj());
            yhDyTsxxDTO.setZwlxjssj(bdcDyaqDTO.getZwlxjssj());
            yhDyTsxxs.add(yhDyTsxxDTO);
        }
    }

    /**
     * 审批状态通知银行 转发事件
     *
     *@param gzlslid gzlslid
     * @return 接口调用结果
     */
    public YhxxTsspxxResponseDTO spztZfsj(@RequestParam(name = "gzlslid") String gzlslid) {

        return this.tzYh(gzlslid, CommonConstantUtils.PASS, null);
        }

    /**
     * 审批状态通知银行 删除事件
     *
     *@param gzlslid gzlslid
     *@param reason reason
     * @return
     */
    public YhxxTsspxxResponseDTO spztScsj(@RequestParam(name = "gzlslid") String gzlslid,
                                          @RequestParam(value = "reason", required = false) String reason) {

        return this.tzYh(gzlslid, CommonConstantUtils.NOTPASS,reason);
    }

    /**
     * 审批状态通知银行
     *
     *@param yhTsspxxDTO 推送参数
     *@param url 推送地址
     * @return 接口调用结果
     */
    private YhxxTsspxxResponseDTO noticeYh(YhTsspxxDTO yhTsspxxDTO, String url) {
        log.info("---登记办件状态信息推送给银行 http请求参数:{},请求地址:{}", JSONObject.toJSONString(yhTsspxxDTO), url);
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(yhTsspxxDTO), "utf-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";

        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
//            response = "{\n" +
//                    "    \"status\": \"COMPLETE\",\n" +
//                    "    \"code\": \"000000\",\n" +
//                    "    \"desc\": \"接口调用成功\"\n" +
//                    "}";
            log.info("---新的{}信息推送给银行请求成功,响应结果:{}", response);
            if (Objects.nonNull(response)) {
                YhxxTsspxxResponseDTO tsspxxResponseDTO = JSON.parseObject(response, YhxxTsspxxResponseDTO.class);
                if (StringUtils.equals(CommonConstantUtils.SUCCESS_STATUS_COMPLETE, tsspxxResponseDTO.getStatus())) {
                    log.info("------登记办件状态信息推送给银行请求成功,响应结果:{}", JSONObject.toJSONString(response));
                } else {
                    log.info("---登记办件状态信息推送给银行请求失败！响应结果：{}", JSONObject.toJSONString(response));
                }
                return tsspxxResponseDTO;
            } else {
                log.info("登记办件状态信息推送给银行请求未返回信息！");
            }
        } catch (Exception e) {
            log.error("---登记办件状态信息推送给银行异常,请求url:{},请求param:{},异常{}", url, JSONObject.toJSONString(yhTsspxxDTO), e);
            throw new AppException("httpPost请求异常");
        }
        return null;
    }

    /**
     * 获取推送银行
     *
     *@param gzlslid
     * @return 需要推送的银行
     */
    private List<String> getTSyh(String gzlslid) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setGzlslid(gzlslid);
        bdcQlrQO.setQlrmcList(spztTzQlrMcList);
        List<String> yhmcList = commonService.listDyaAndYdyQlrmc(bdcQlrQO);
        log.info("查询条件bdcQlrQO{}, 查询结果yhmcList{}", JSON.toJSONString(bdcQlrQO), JSON.toJSONString(yhmcList));
        return yhmcList;
    }

    /**
     * 获取受理编号slbh，并检查配置项
     *
     *@param gzlslid
     * @return slbh
     */
    private String getSlbh(String gzlslid) {
        List<BdcXmDO> bdcXmDOList = commonService.listBdcXmByGzlslid(gzlslid);
        //判断sply和qxdm,是否需要通知银行
        String qxdm = bdcXmDOList.get(0).getQxdm();
        Integer sply = bdcXmDOList.get(0).getSply();

        if (CollectionUtils.isEmpty(spztTzQlrMcList) || CollectionUtils.isEmpty(spztTzQxdm) || CollectionUtils.isEmpty(spztTzSply)) {
            log.info("当前未配置需要进行spzt通知的银行或区县代码或sply");
            return null;
        }

        if (!spztTzQxdm.contains(qxdm) || !spztTzSply.contains(sply)) {
            log.info("当前gzlslid{}的qxdm{}和sply{}不需要推送通知到银行！", gzlslid, qxdm, sply);
            return null;
        }
        return bdcXmDOList.get(0).getSlbh();
    }

    /**
     * 通知银行通过状态
     *
     *@param gzlslid
     * @param reason 退回原因
     * @param isPassed 是否通过
     * @return 通知接口返回体
     */
    private YhxxTsspxxResponseDTO tzYh(String gzlslid, String isPassed, String reason) {
        //获取受理编号，并检查配置项
        String slbh = this.getSlbh(gzlslid);
        if (StringUtils.isBlank(slbh)) {
            return null;
        }

        //判断权利人是否有需要推送通知的银行
        List<String> yhmcList = this.getTSyh(gzlslid);
        if (CollectionUtils.isEmpty(yhmcList)) {
            log.info("未查询到需要推送通过状态的银行权利人！gzlslid为{}", gzlslid);
            return null;
        }

        //推送通知到银行
        for (String yhmc : yhmcList) {
            //根据权利人名称，判断推送的接口地址
            String url = "";
            List<BdcYhUrlCsDO> bdcYhUrlCsDOS = bdcdjMapper.queryYhdz(yhmc, DYDJ_TGXXTS);
            if (CollectionUtils.isEmpty(bdcYhUrlCsDOS) || StringUtils.isBlank(bdcYhUrlCsDOS.get(0).getYhurl())) {
                log.info("需推送通过状态的银行为:{},未找到该银行推送地址", yhmc);
                return null;
            }
            url = bdcYhUrlCsDOS.get(0).getYhurl();

            //处理推送数据
            YhTsspxxDTO yhTsspxxDTO = new YhTsspxxDTO();
            yhTsspxxDTO.setCorporateOrgId(spztTzOrgId);
            yhTsspxxDTO.setProid(slbh);
            yhTsspxxDTO.setIsPass(isPassed);
            yhTsspxxDTO.setReasonDesc(reason);

            //调用推送接口
            if (Objects.nonNull(yhTsspxxDTO) && StringUtils.isNotBlank(url)) {
                try {
                    log.info("开始推送银行推送通过状态{}", JSON.toJSONString(yhTsspxxDTO));
                    return noticeYh(yhTsspxxDTO, url);
                } catch (Exception e) {
                    log.info("推送银行推送通过状态失败{}", JSON.toJSONString(yhTsspxxDTO));
                }
            }
        }
        return null;
    }

}
