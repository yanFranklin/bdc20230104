package cn.gtmap.realestate.init.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcXtQlqtzkFjPzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 初始化的权利表内的附记内容
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class InitQlfjThread extends CommonThread{
    private static final Logger LOGGER = LoggerFactory.getLogger(InitQlfjThread.class);

    /**
     * 参数 项目
     */
    private BdcXmDO bdcXmDO;

    /**
     * 参数
     */
    private List<InitServiceQO> listQO;
    /**
     * 服务
     */
    private BdcQllxService bdcQllxService;

    /**
     * 服务
     */
    private BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;

    /**
     * 全局配置
     */
    private InitBeanFactory initBeanFactory;

    /**
     * 服务
     */
    private EntityMapper entityMapper;

    /**
     *
     */
    BdcXmService bdcXmService;

    /**
     * 配置项
     */
    List<String> fjyqlgzl;

    /**
     * 配置项
     */
    List<String> fjpjdsfgzl;
    /**
     * 构造函数
     * @param bdcXtQlqtzkFjPzService
     * @param bdcQllxService
     * @param bdcXmDO
     * @param initBeanFactory
     */
    public InitQlfjThread(BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService,BdcQllxService bdcQllxService, BdcXmDO bdcXmDO,InitBeanFactory initBeanFactory,EntityMapper entityMapper,List<InitServiceQO> listQO,
                          List<String> fjyqlgzl, BdcXmService bdcXmService, List<String> fjpjdsfgzl){
        this.bdcXtQlqtzkFjPzService=bdcXtQlqtzkFjPzService;
        this.bdcQllxService=bdcQllxService;
        this.bdcXmDO=bdcXmDO;
        this.initBeanFactory=initBeanFactory;
        this.entityMapper=entityMapper;
        this.listQO = listQO;
        this.fjyqlgzl = fjyqlgzl;
        this.bdcXmService = bdcXmService;
        this.fjpjdsfgzl = fjpjdsfgzl;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        LOGGER.info("初始化后处理附记");
        if(bdcXmDO!=null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXmDO.getXmid());
            if(Objects.nonNull(bdcQl)) {
                Map<String, InitServiceQO> initQOMap = listQO.stream().collect(Collectors.toMap(InitServiceQO::getXmid, initServiceQO -> initServiceQO));
                LOGGER.info("权利信息{}", bdcXmDO.getXmid());
                /**
                 * 处理附记
                 * 如果配置了从上一手中取附记则优先按照这个配置项来
                 */
                LOGGER.info("初始化后处理附记");
                if (CollectionUtils.isNotEmpty(fjyqlgzl) && fjyqlgzl.contains(bdcXmDO.getGzldyid())) {
                    LOGGER.info("{} 从上一手权利获取附记", bdcXmDO.getXmid());
                    //去上一手权利的附记
                    String yxmid;
                    if (Objects.nonNull(initQOMap.get(bdcXmDO.getXmid()))
                            && StringUtils.isNotBlank(initQOMap.get(bdcXmDO.getXmid()).getYxmid())) {
                        yxmid = initQOMap.get(bdcXmDO.getXmid()).getYxmid();
                    } else {
                        yxmid = bdcXmService.queryYxmid(bdcXmDO.getXmid());
                    }
                    LOGGER.info("{} 从上一手权利获取附记,上一手{}", bdcXmDO.getXmid(), yxmid);
                    if (StringUtils.isBlank(yxmid)) {
                        return;
                    }

                    BdcQl ybdcQl = bdcQllxService.queryQllxDO(yxmid);
                    if (Objects.isNull(ybdcQl)) {
                        LOGGER.info("{} 未获取到上一手权利", yxmid);
                        return;
                    }

                    LOGGER.info("{} 设置附记 {}", bdcXmDO.getXmid(), ybdcQl.getFj());
                    bdcQl.setFj(ybdcQl.getFj());

                    //如果上一手存在且不为空则更新结束，如果上一手存在但是为空则继续走之前的逻辑
                    if(StringUtils.isNotBlank(bdcQl.getFj())){
                        bdcQllxService.updateBdcQl(bdcQl);
                        return;
                    }
                }

                if (initBeanFactory.isInitFj()) {
                    String fjmb = bdcXtQlqtzkFjPzService.getFjMessageByXmid(bdcXmDO.getXmid());
                    if (StringUtils.isNotBlank(fjmb)) {
                        // 当第三方接口传递附记时，权利附记拼接第三方附记
                        if (initQOMap.get(bdcXmDO.getXmid()) != null && initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO() != null) {
                            LOGGER.info("获取第三方附记");
                            LOGGER.info("第三方附记：{}", initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj());
                            if (StringUtils.isNotBlank(initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj())) {
                                if (StringUtils.isBlank(fjmb)) {
                                    fjmb = initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj();
                                } else {
                                    fjmb = fjmb + "\r\n" + initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj();
                                }
                            }
                        }
                        LOGGER.info("设置附记 {}", fjmb);
                        bdcQl.setFj(fjmb);
                        //更新附记
                        bdcQllxService.updateBdcQl(bdcQl);
                    }
                } else {
                    // 判断是否需要拼接第三方传的附记信息
                    if (CollectionUtils.isNotEmpty(fjpjdsfgzl) && fjpjdsfgzl.contains(bdcXmDO.getGzldyid())) {
                        String qlfj = bdcQl.getFj();
                        if (initQOMap.get(bdcXmDO.getXmid()) != null && initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO() != null) {
                            LOGGER.info("获取第三方附记");
                            LOGGER.info("第三方附记：{}", initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj());
                            if (StringUtils.isNotBlank(initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj())) {
                                if (StringUtils.isBlank(qlfj)) {
                                    qlfj = initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj();
                                } else {
                                    qlfj = qlfj + "\r\n" + initQOMap.get(bdcXmDO.getXmid()).getDsfSlxxDTO().getFj();
                                }
                                LOGGER.info("设置附记 {}", qlfj);
                                bdcQl.setFj(qlfj);
                                //更新附记
                                bdcQllxService.updateBdcQl(bdcQl);
                            }
                        }
                    }
                    // 蚌埠房屋所有权首次登记 把户室表的备注信息带入到权利附记字段，留作证书附记读取带入（附记模板配置读取权利附记）
                    if (CommonConstantUtils.SYSTEM_VERSION_BB.equals(initBeanFactory.getVersion())) {
                        if (null != bdcQl && (bdcQl instanceof BdcFdcqDO || bdcQl instanceof BdcFdcq3DO)) {
                            // 获取权籍户室表备注信息
                            FwHsDO fwHsDO = bdcQllxService.queryFwHsXx(bdcQl);
                            if (null != fwHsDO && StringUtils.isNotBlank(fwHsDO.getBz())) {
                                //更新附记为对应权籍户室表备注信息
                                bdcQl.setFj(fwHsDO.getBz());
                                bdcQllxService.updateBdcQl(bdcQl);
                                LOGGER.info("蚌埠房屋所有权首次登记把户室表的备注信息带入到权利附记字段，权利ID：{}，备注信息：{}", bdcQl.getQlid(), fwHsDO.getBz());
                            }
                        }
                    }
                }
            }
            //处理项目表的权利其他状况
            if(initBeanFactory.isDqXmQlqtzk()){
                String qlqtzkmb = bdcXtQlqtzkFjPzService.getQlqtzkMessageByXmid(bdcXmDO.getXmid());
                if (StringUtils.isNotBlank(qlqtzkmb)) {
                    bdcXmDO.setBfqlqtzk(qlqtzkmb);
                    entityMapper.updateByPrimaryKeySelective(bdcXmDO);
                }
            }
        }
    }
}
