package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.common.config.accept.QcjdConfig;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmFbService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.BdcYwsjHxService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
@Validated
public class BdcYwsjHxServiceImpl implements BdcYwsjHxService {

    // 缴税状态--未缴税
    private static final String JSZT_WJS = "0";

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYwsjHxServiceImpl.class);


    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    private BdcQlrService bdcQlrService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private QcjdConfig qcjdConfig;
    @Value("${init.dozerVersion:standard}")
    private String dozerVersion;
    @Autowired
    private BdcXmFbService bdcXmFbService;

    /**
     * 是否加等
     */
    @Value("${init.sfjd}")
    private boolean sfjd;

    @Value("#{${init.qlrhx:{'':''}}}")
    private Map<String, String> qlrhxPzMap;

    /**
     * 保存或更新业务信息到平台
     *
     * @param gzlslid
     * @throws Exception
     */
    @Override
    public void saveBdcYwsj(@NotBlank(message = "参数不能为空") String gzlslid) throws Exception {
        initProcessInsExtendDto(gzlslid);
    }


    /**
     * @param gzlslid
     * @return ProcessInsExtendDto
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 初始化业务数据
     */
    private Map<String, Object> initProcessInsExtendDto(String gzlslid) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmDO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("项目信息不能为空！");
        }
        //排序
        bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        bdcXmDO = bdcXmDOList.get(0);
        //是否是有虚拟的单元号  存text6
        Integer isXn=CommonConstantUtils.SF_F_DM;
        boolean multiple = bdcXmDOList.size() > 1;
        if (multiple) {
            //循环项目查看是否有多个不动产单元
            Set<String> bdcdyhSet = new HashSet<>();
            for(BdcXmDO xm:bdcXmDOList){
                bdcdyhSet.add(xm.getBdcdyh());
                //大于1为批量或者组合批量 加等
                if(bdcdyhSet.size()>1){
                    //批量的抵押,义务人处理
                    if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())){
                        BdcCshFwkgSlDO bdcCshFwkgSlDO=bdcXmService.queryCshFwkgSl(bdcXmDO.getXmid());
                        //批量发一本证的(批量组合也会全部查询出来)
                        if(bdcCshFwkgSlDO!=null && bdcCshFwkgSlDO.getZsxh()!=null){
                            bdcXmDO.setYwr(bdcQlrService.queryQlrsYbzs(gzlslid,CommonConstantUtils.QLRLB_YWR,null));
                        }
                    }
                    //加等
                    boolean qcjd = qcjdConfig.qcjd(bdcXmDO.getGzldyid());
                    if(sfjd && !qcjd) {
                        bdcXmDO.setBdcdyh(bdcXmDO.getBdcdyh() + CommonConstantUtils.SUFFIX_PL);
                        bdcXmDO.setZl(bdcXmDO.getZl() + CommonConstantUtils.SUFFIX_PL);
                    } else if (qcjd) {
                        Pair<String, String> pair = qcjdConfig.getNoTdzZlAndBdcdyhByBdcXm(bdcXmDOList);
                        bdcXmDO.setBdcdyh(pair.getLeft());
                        bdcXmDO.setZl(pair.getRight());
                    }
                    break;
                }
            }
            if (MapUtils.isNotEmpty(qlrhxPzMap) && qlrhxPzMap.containsKey(bdcXmDO.getGzldyid())) {
                LOGGER.warn("配置了根据流程定义id读取对应的登记小类的权利人{}", qlrhxPzMap);
                //根据配置取对应的登记下类的的项目的权利人
                for (BdcXmDO bdcXm : bdcXmDOList) {
                    if (StringUtils.equals(qlrhxPzMap.get(bdcXmDO.getGzldyid()), bdcXm.getDjxl())) {
                        bdcXmDO.setQlr(bdcXm.getQlr());
                        break;
                    }
                }
            }
        }
        //循环判定是否有虚拟单元号
        for(BdcXmDO xm:bdcXmDOList){
            boolean xndyh=BdcdyhToolUtils.checkXnbdcdyh(xm.getBdcdyh());
            if(xndyh){
                isXn=CommonConstantUtils.SF_S_DM;
                break;
            }
        }
        Map<String, Object> processInsExtendDto;
        //先获取
        List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(gzlslid);
        boolean update = false;
        if (CollectionUtils.isNotEmpty(list)) {
            processInsExtendDto = list.get(0);
            update = true;
        } else {
            processInsExtendDto = new HashMap();
            processInsExtendDto.put("PROC_INS_ID", gzlslid);
        }
        //登记原因去重拼接展示
        String djyy = bdcXmDOList.stream().map(t -> t.getDjyy()).filter(t->StringUtils.isNotBlank(t)).distinct().collect(Collectors.joining(CommonConstantUtils.ZF_YW_XG));
        bdcXmDO.setDjyy(djyy);
        LOGGER.info("当前流程受理编号{},回写平台登记原因{}", bdcXmDO.getSlbh(), djyy);
        dozerUtils.initBeanDateConvert(bdcXmDO, processInsExtendDto);
        processInsExtendDto.put("ISXN", isXn.toString());
        // 处理地方特殊字端
        initCustomizedExtendDTO(bdcXmDO, processInsExtendDto);
        //添加领证方式
        if (CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dozerVersion)) {
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setGzlslid(gzlslid);
            List<BdcXmFbDO> bdcXmFbDOS = bdcXmFbService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOS)){
                for (BdcXmFbDO bdcXmFbDO : bdcXmFbDOS) {
                    if(Objects.nonNull(bdcXmFbDO.getLzfs())){
                        processInsExtendDto.put("LZFS", bdcXmFbDO.getLzfs());
                        break;
                    }
                }
            }
        }
        //如果更新
        if (update) {
            processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid, processInsExtendDto);
        } else {
            // 盐城新增时，一体化业务（审批来源为1）大云JSZT默认回写未缴税
            if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(dozerVersion) &&
                    CommonConstantUtils.SPLY_YCSL.equals(bdcXmDO.getSply())) {
                processInsExtendDto.put("JSZT", JSZT_WJS);
            }
            processInsCustomExtendClient.addProcessInsCustomExtend(processInsExtendDto);
        }
        return processInsExtendDto;
    }

    /**
     * 处理南通地区特殊字端的回写
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void initCustomizedExtendDTO(BdcXmDO bdcXmDO, Map<String, Object> processInsExtendDto) {
        if (processInsExtendDto == null || !processInsExtendDto.containsKey("QLRLX")) {
            return;
        }
        List<BdcQlrDO> bdcQlrDOS = bdcQlrService.listAllBdcQlr(bdcXmDO.getGzlslid(), bdcXmDO.getSlbh(), null, null, null);
        if (CollectionUtils.isEmpty(bdcQlrDOS)) {
            return;
        }
        Set<Integer> qlrlxSet = bdcQlrDOS.stream().map(BdcQlrDO::getQlrlx).collect(Collectors.toSet());
        if (qlrlxSet.isEmpty()) {
            return;
        }
        // 查询字典信息
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        StringJoiner qlrlxSj = new StringJoiner(",");
        for (Integer integer : qlrlxSet) {
            String qlrlx = StringToolUtils.convertBeanPropertyValueOfZd(integer, zdMap.get("qlrlx"));
            if (StringUtils.isNotBlank(qlrlx)) {
                qlrlxSj.add(qlrlx);
            }
        }
        processInsExtendDto.put("QLRLX", qlrlxSj.toString());
    }
}
