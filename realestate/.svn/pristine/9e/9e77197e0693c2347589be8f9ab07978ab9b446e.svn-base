package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.exchange.standard.BdcZsSdExtend;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.service.inf.standard.BdcSdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/10/10
 * @description 标准版-锁定相关服务
 */
@Service(value = "bdcSdServiceImpl")
public class BdcSdServiceImpl implements BdcSdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSdServiceImpl.class);

    @Autowired
    private BdcSdFeignService bdcSdFeignService;


    @Autowired
    private BdcZsFeignService zsFeignService;

    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;


    @Autowired
    private BdcZsxmFeignService bdcZsxmFeignService;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private TaskHandleClientMatcher taskHandleClient;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;


    /**
     * @param bdcZsSdExtend
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * 锁定不动产证书
     */

    @Override
    public ExchangeDsfCommonResponse standardSdZs(@RequestBody BdcZsSdExtend bdcZsSdExtend) {
        if (null != bdcZsSdExtend && StringUtils.isNotBlank(bdcZsSdExtend.getSdr())
                && StringUtils.isNotBlank(bdcZsSdExtend.getSdsqwh())
                && StringUtils.isNotBlank(bdcZsSdExtend.getCqzh())) {

            BdcZsQO zsQO = new BdcZsQO();
            zsQO.setBdcqzh(bdcZsSdExtend.getCqzh());
            List<BdcZsDO> zsDOList = zsFeignService.listBdcZs(zsQO);
            if (CollectionUtils.isNotEmpty(zsDOList)) {
                BdcZsDO bdcZsDO = zsDOList.get(0);
                String zsid = bdcZsDO.getZsid();
                String gzlslid = "";
                List<BdcXmDO> bdcXmDOList = bdcZsxmFeignService.listBdcXmByZsid(zsid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    gzlslid = bdcXmDOList.get(0).getGzlslid();
                    //查询该流程节点信息
                    List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
                    if (CollectionUtils.isNotEmpty(listTask)) {
                        //先认领流程，再修改项目附表的zsrlzt
                        boolean rl = taskHandleClient.taskClaim(bdcZsSdExtend.getSdr(), Collections.singletonList(listTask.get(0).getTaskId()));
                        if (!rl) {
                            LOGGER.info("认领失败！listTask{}", listTask.toString());
                            return ExchangeDsfCommonResponse.fail("认领失败!");
                        }
                        //认领完之后，修改项目附表认领状态
                        int count = bdcXmfbFeignService.updateZsrlzt(Collections.singletonList(gzlslid), CommonConstantUtils.SF_S_DM);
                        if (count > 0) {
                            return ExchangeDsfCommonResponse.ok();
                        } else {
                            return ExchangeDsfCommonResponse.fail("流程认领成功，状态修改失败！");
                        }
                    }

                }
                return ExchangeDsfCommonResponse.fail("未查到项目信息！");
            } else {
                return ExchangeDsfCommonResponse.fail("未查到该证书信息，请核查!");
            }

           /* try {
                BdcZssdDO param = new BdcZssdDO();
                String sdyy = bdcZsSdExtend.getSdyy();
                param.setCqzh(bdcZsSdExtend.getCqzh());
                param.setSdzt(1);
                param.setSdsqwh(bdcZsSdExtend.getSdsqwh());
                List<BdcZssdDO> queryBdczsSd = bdcSdFeignService.queryBdczsSd(param);
                if (CollectionUtils.isNotEmpty(queryBdczsSd)) {
                    return ExchangeDsfCommonResponse.fail("该证书在此锁定申请文号下已存在锁定信息!");
                }
                BdcZssdDO zssdDO = new BdcZssdDO();
                BeanUtils.copyProperties(bdcZsSdExtend, zssdDO);
                BdcZsQO zsQO = new BdcZsQO();
                zsQO.setBdcqzh(param.getCqzh());
                List<BdcZsDO> zsDOList = zsFeignService.listBdcZs(zsQO);
                if (CollectionUtils.isNotEmpty(zsDOList)) {
                    BdcZsDO bdcZsDO = zsDOList.get(0);
                    zssdDO.setZsid(bdcZsDO.getZsid());
                    List<BdcZssdDO> zssdDOList = new ArrayList<>();
                    zssdDOList.add(zssdDO);
                    bdcSdFeignService.sdBdczs(zssdDOList, sdyy);
                    return ExchangeDsfCommonResponse.ok();
                } else {
                    return ExchangeDsfCommonResponse.fail("未查到该证书信息，请核查!");
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }*/
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");

        }
    }

    /**
     * @param bdcZsSdExtend
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * 解锁不动产证书
     */
    @Override
    public ExchangeDsfCommonResponse standardjsBdczs(BdcZsSdExtend bdcZsSdExtend) {
        if (null != bdcZsSdExtend && StringUtils.isNotBlank(bdcZsSdExtend.getCqzh()) &&
                StringUtils.isNotBlank(bdcZsSdExtend.getJsr())) {
           /* try {
                BdcZssdDO param = new BdcZssdDO();
                param.setCqzh(bdcZsSdExtend.getCqzh());
                param.setSdzt(1);
                param.setSdsqwh(bdcZsSdExtend.getSdsqwh());
                //同一产权证号，只存在一条现势锁定数据
                List<BdcZssdDO> queryBdczsSd = bdcSdFeignService.queryBdczsSd(param);
                if (CollectionUtils.isEmpty(queryBdczsSd)) {
                    return ExchangeDsfCommonResponse.fail("根据产权证号和锁定申请文号未查询到该证书锁定信息!");
                }
                BdcZssdDO zssdDO = queryBdczsSd.get(0);
                zssdDO.setJsr(bdcZsSdExtend.getJsr());
                zssdDO.setJsyy(bdcZsSdExtend.getJsyy());
                zssdDO.setSdzt(0);
                zssdDO.setZssdid(zssdDO.getZssdid());
                List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
                bdcZssdDOSd.add(zssdDO);
                BdcZsDO bdcZsDO = zsFeignService.queryBdcZs(zssdDO.getZsid());
                if (bdcZsDO != null) {
                    bdcDbxxFeignService.synQjBdcdyztSd(Arrays.asList(bdcZsDO.getBdcdyh()), CommonConstantUtils.SDZT_JS);
                } else {
                    return ExchangeDsfCommonResponse.fail("根据zsid查询证书数据失败!");
                }
//                bdcSdFeignService.jsBdczsByCqzh(bdcZssdDO);
                bdcSdFeignService.jsBdczs(bdcZssdDOSd, bdcZsSdExtend.getJsyy());
                return ExchangeDsfCommonResponse.ok();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }*/
            BdcZsQO zsQO = new BdcZsQO();
            zsQO.setBdcqzh(bdcZsSdExtend.getCqzh());
            List<BdcZsDO> zsDOList = zsFeignService.listBdcZs(zsQO);
            if (CollectionUtils.isNotEmpty(zsDOList)) {
                BdcZsDO bdcZsDO = zsDOList.get(0);
                String zsid = bdcZsDO.getZsid();
                String gzlslid = "";
                List<BdcXmDO> bdcXmDOList = bdcZsxmFeignService.listBdcXmByZsid(zsid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    gzlslid = bdcXmDOList.get(0).getGzlslid();
                    //查询该流程节点信息
                    List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
                    if (CollectionUtils.isNotEmpty(listTask)) {
                        //先修改项目附表的zsrlzt，再取消认领流程
                        int count = bdcXmfbFeignService.updateZsrlzt(Collections.singletonList(gzlslid), CommonConstantUtils.SF_F_DM);
                        if (count > 0) {
                            boolean rl = taskHandleClient.unClaimTask(listTask.get(0).getTaskId(), "自助打证机取消认领");
                            if (rl) {
                                return ExchangeDsfCommonResponse.ok();
                            } else {
                                LOGGER.info("取消认领失败！listTask{}", listTask.toString());
                                return ExchangeDsfCommonResponse.fail("取消认领失败！");
                            }
                        } else {
                            LOGGER.info("状态修改失败！gzlslid{}", gzlslid);
                            return ExchangeDsfCommonResponse.fail("状态修改失败！");
                        }
                    }else {
                        return ExchangeDsfCommonResponse.fail("未查询到节点信息！");
                    }
                }
                return ExchangeDsfCommonResponse.fail("未查到项目信息！");
            } else {
                return ExchangeDsfCommonResponse.fail("未查到该证书信息，请核查!");
            }
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");
        }
    }
}
