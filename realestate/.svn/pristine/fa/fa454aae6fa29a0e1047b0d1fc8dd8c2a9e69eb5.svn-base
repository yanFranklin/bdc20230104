package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcSlBysldjService;
import cn.gtmap.realestate.common.config.accept.QcjdConfig;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/9/12
 * @description 受理信息回写方法
 */
@Service
public class BdcSlxxHxServiceImpl implements BdcSlxxHxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlxxHxServiceImpl.class);

    @Autowired
    private BdcSlXmService bdcSlXmService;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    BdcSlQjdcService bdcSlQjdcService;
    @Autowired
    FdjywConfig fdjywConfig;
    @Autowired
    BdcSlCdxxService bdcSlCdxxService;
    @Autowired
    QcjdConfig qcjdConfig;
    @Autowired
    BdcSlXzxxService bdcSlXzxxService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlBysldjService bdcSlBysldjService;

    @Override
    public void hxBdcSlxx(BdcSlJbxxDO bdcSlJbxxDO) throws Exception {
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlJbxxDO.getGzlslid());
            LOGGER.info("回写受理信息时登记项目数据数量：{},基本信息：{}", CollectionUtils.size(bdcXmDTOList), JSON.toJSONString(bdcSlJbxxDO));
            if (CollectionUtils.isEmpty(bdcXmDTOList)) {
                //如果为非登记数据，需要回写
                initProcessInsExtendDto(bdcSlJbxxDO, null);
            }
        }
    }

    @Override
    public void hxBdcSlxxWithZdyxx(BdcSlJbxxDO bdcSlJbxxDO, Map zdyxxMap) throws Exception {
        if(bdcSlJbxxDO != null &&StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())){
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlJbxxDO.getGzlslid());
            LOGGER.info("回写受理信息时登记项目数据数量：{},基本信息：{}", CollectionUtils.size(bdcXmDTOList), JSON.toJSONString(bdcSlJbxxDO));
            if(CollectionUtils.isEmpty(bdcXmDTOList)){
                //如果为非登记数据，需要回写
                initProcessInsExtendDto(bdcSlJbxxDO, zdyxxMap);
            }
        }
    }

    /**
     * @param bdcSlJbxxDO 受理基本信息
     * @param zdyxxMap 自定义信息Map
     * @return ProcessInsExtendDto
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化受理数据
     */
    private Map<String, Object> initProcessInsExtendDto(BdcSlJbxxDO bdcSlJbxxDO, Map zdyxxMap) {
        Map<String, Object> processInsExtendDto;
        //先获取
        List<Map<String, Object>> list=processInsCustomExtendClient.getProcessInsCustomExtend(bdcSlJbxxDO.getGzlslid());
        boolean update=false;
        if(CollectionUtils.isNotEmpty(list)){
            processInsExtendDto = list.get(0);
            update = true;
        } else {
            processInsExtendDto = new HashMap();
        }
        processInsExtendDto.put("PROC_INS_ID", bdcSlJbxxDO.getGzlslid());
        processInsExtendDto.put("SLBH", bdcSlJbxxDO.getSlbh());
        processInsExtendDto.put("QLR", bdcSlJbxxDO.getSqrxm());
        processInsExtendDto.put("ZL", bdcSlJbxxDO.getZl());
        processInsExtendDto.put("DJYY", bdcSlJbxxDO.getDjyy());
        processInsExtendDto.put("QXDM",bdcSlJbxxDO.getQxdm());
        //受理数据回写大云，默认审批来源为内网创建
        processInsExtendDto.put("SPLY", "0");
        // 添加自定义消息
        if(MapUtils.isNotEmpty(zdyxxMap)){
            processInsExtendDto.putAll(zdyxxMap);
        }
        if (StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            LOGGER.info("回写大云基本信息id{},受理项目数量{}", bdcSlJbxxDO.getJbxxid(), CollectionUtils.size(bdcSlXmDOList));
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmDOList.get(0);
                boolean multiple = bdcSlXmDOList.size() > 1;
                if (multiple) {
                    //循环项目查看是否有多个不动产单元
                    Set<String> bdcdyhSet = new HashSet<>();
                    for (BdcSlXmDO slxm : bdcSlXmDOList) {
                        bdcdyhSet.add(slxm.getBdcdyh());
                        //大于1为批量或者组合批量 加等
                        if(bdcdyhSet.size()>1){
                            //加等
                            bdcSlXmDO.setBdcdyh(bdcSlXmDO.getBdcdyh()+CommonConstantUtils.SUFFIX_PL);
                            bdcSlXmDO.setZl(bdcSlXmDO.getZl()+CommonConstantUtils.SUFFIX_PL);
                            break;
                        }
                    }
                    // 是否去除加等
                    if(bdcdyhSet.size()>1 && qcjdConfig.qcjd(bdcSlJbxxDO.getGzldyid())){
                        Pair<String, String> pair = qcjdConfig.getNoTdzZlAndBdcdyhBySlXm(bdcSlXmDOList);
                        bdcSlXmDO.setBdcdyh(pair.getLeft());
                        bdcSlXmDO.setZl(pair.getLeft());
                    }
                }
                //权利人义务人
                List<BdcSlSqrDO> bdcSlSqrDOList =bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(),"");
                if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    List<BdcSlSqrDO> qlrList = bdcSlSqrDOList.stream().filter(
                            bdcQlr -> StringUtils.equals(bdcQlr.getSqrlb(), CommonConstantUtils.QLRLB_QLR)).collect(Collectors.toList());
                    List<BdcSlSqrDO> ywrList = bdcSlSqrDOList.stream().filter(
                            bdcQlr -> StringUtils.equals(bdcQlr.getSqrlb(), CommonConstantUtils.QLRLB_YWR)).collect(Collectors.toList());
                    String qlr = StringToolUtils.resolveBeanToAppendStr(qlrList, "getSqrmc", ",");
                    String ywr = StringToolUtils.resolveBeanToAppendStr(ywrList, "getSqrmc", ",");
                    processInsExtendDto.put("QLR", qlr);
                    processInsExtendDto.put("YWR", ywr);
                }
                acceptDozerMapper.map(bdcSlXmDO, processInsExtendDto);
            }
            //判断是否存在权籍调查数据
            List<BdcSlQjdcsqDO> bdcSlQjdcsqDOList = bdcSlQjdcService.listSlQjdc(bdcSlJbxxDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcSlQjdcsqDOList)) {
                processInsExtendDto.put("QLR", bdcSlQjdcsqDOList.get(0).getDcsqr());
                processInsExtendDto.put("ZL", bdcSlQjdcsqDOList.get(0).getZl());
                processInsExtendDto.put("BDCDYH", bdcSlQjdcsqDOList.get(0).getBdcdyh());
            }
            //如果是查档信息，把需查询人回写到portal
            List<String> cdxxGzldyidList = fdjywConfig.getFdjywlcDyidList("cdxx");
            if (CollectionUtils.isNotEmpty(cdxxGzldyidList) && cdxxGzldyidList.contains(bdcSlJbxxDO.getGzldyid()) && CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcCdxxQO bdcCdxxQO = new BdcCdxxQO();
                bdcCdxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                BdcSlCdxxDO bdcSlCdxxDO = bdcSlCdxxService.queryBdcCdxx(bdcCdxxQO);
                if (Objects.nonNull(bdcSlCdxxDO)) {
                    if (StringUtils.equals("1", bdcSlCdxxDO.getCdlb())) {
                        //选择台账有数据创建回写权利人读取上一手的产权人数据
                        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
                        LOGGER.warn("登记簿查询选择台账创建数据，回写portal读取上一手产权人数据");
                        if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                            BdcQlrQO bdcQlrQO = new BdcQlrQO();
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                            bdcQlrQO.setXmid(bdcSlXmLsgxDOList.get(0).getYxmid());
                            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                processInsExtendDto.put("QLR", StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", CommonConstantUtils.ZF_YW_DH));
                            }
                        }
                    } else {
                        processInsExtendDto.put("QLR", bdcSlCdxxDO.getXcxr());
                    }
                }
            }
            List<String> xzlcGzldyidList = fdjywConfig.getFdjywlcDyidList("xzlc");
            if (CollectionUtils.isNotEmpty(xzlcGzldyidList) && xzlcGzldyidList.contains(bdcSlJbxxDO.getGzldyid()) && CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcSlXzxxDO bdcSlXzxxQO = new BdcSlXzxxDO();
                bdcSlXzxxQO.setGzlslid(bdcSlJbxxDO.getGzlslid());
                BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxService.queryBdcSlXzxx(bdcSlXzxxQO);
                if (Objects.nonNull(bdcSlXzxxDO)) {
                    processInsExtendDto.put("QLR", bdcSlXzxxDO.getQlr());
                    processInsExtendDto.put("ZL", bdcSlXzxxDO.getZl());
                }
            }
            List<String> qsztxzGzldyidList = fdjywConfig.getFdjywlcDyidList("xzqszt");
            if(CollectionUtils.isNotEmpty(qsztxzGzldyidList) && qsztxzGzldyidList.contains(bdcSlJbxxDO.getGzldyid()) && CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                //查询上一手的项目回写上一手项目的权利人和义务人
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(),"", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    //查登记项目数据回写
                    BdcXmQO bdcXmQO = new BdcXmQO(bdcSlXmLsgxDOList.get(0).getYxmid());
                    List<BdcXmDO> ybdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(ybdcXmDOList)) {
                        processInsExtendDto.put("QLR", ybdcXmDOList.get(0).getQlr());
                        processInsExtendDto.put("YWR", ybdcXmDOList.get(0).getYwr());
                    }
                }
            }
            List<String> byslGzldyidList = fdjywConfig.getFdjywlcDyidList("bysl");
            List<String> bydjGzldyidList = fdjywConfig.getFdjywlcDyidList("bydj");
            if (CollectionUtils.isNotEmpty(byslGzldyidList) && byslGzldyidList.contains(bdcSlJbxxDO.getGzldyid()) || CollectionUtils.isNotEmpty(bydjGzldyidList) && bydjGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                List<BdcByslDO> bdcByslDOList = bdcSlBysldjService.queryBdcByslDOBygzlslid(bdcSlJbxxDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcByslDOList)) {
                    processInsExtendDto.put("QLR", bdcByslDOList.get(0).getFdsxsqr());
                    processInsExtendDto.put("ZL", bdcByslDOList.get(0).getZl());
                }
            }
        }
        LOGGER.info("开始回写大云信息是否更新{},工作流实例id为{},数据信息：{}", update, bdcSlJbxxDO.getGzlslid(), JSON.toJSONString(processInsExtendDto));
        //如果更新
        if(update){
            processInsCustomExtendClient.updateProcessInsCustomExtend(bdcSlJbxxDO.getGzlslid(),processInsExtendDto);
        }else{
            processInsCustomExtendClient.addProcessInsCustomExtend(processInsExtendDto);
        }

        return processInsExtendDto;
    }
}
