package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbDeleteRequestParam;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.CourtUpdateYwcjzt;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqLcthDTO;
import cn.gtmap.realestate.common.core.enums.BdcZqShztEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.YanchengYthFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.yancheng.BdcZqFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * nantong工作流实现类
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 11:09 上午 2020/6/12
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_yancheng extends BdcWorkFlowAbstactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_yancheng.class);
    private static final String VERSION = "yancheng";
    // 更新法院子系统创建状态
    private static final String UPDATEFYCJZT = "updateCjywzt";
    /**
     * 征迁服务
     */
    @Autowired
    private BdcZqFeignService bdcZqFeignService;
    /**
     * 项目服务
     */
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    /**
     * 盐城一体化服务
     */
    @Autowired
    private YanchengYthFeignService yanchengYthFeignService;

    /**
     * exchange 服务
     */
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService(VERSION, this);
    }

    @Override
    public void processBeforeDelete(EventDTO eventDTO,BdcXmDO bdcXmDO, String userName) {
        if (StringUtils.isBlank(eventDTO.getGzlslid())) {
            throw new MissingArgumentException("删除前方法未传入 gzlslid");
        }
        LOGGER.info("盐城删除操作日志保存处理");
        // 获取流程审批意见
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(userName + ":" + eventDTO.getReason());
        bdcCzrzFeignService.addScCzrzWithOpinionWithXmxx(bdcXmDO.getGzldyid(), joiner.toString(),bdcXmDO);
        LOGGER.info("盐城删除操作日志保存处理结束");
        // 转换互联网受理状态 为 征迁审核状态
        Integer status = null;
        if (StringUtils.equals(eventDTO.getStatus(), HlwSlztEnum.REJECT.getSlzt())) {
            status = BdcZqShztEnum.LCTH.getCode();
        }
        if (StringUtils.equals(eventDTO.getStatus(), HlwSlztEnum.DELETE.getSlzt())) {
            status = BdcZqShztEnum.LCSC.getCode();
        }
        // 状态不符合条件不去查询数据库判断 sply 直接返回
        if (status == null) {
            LOGGER.info("推送数据到征迁 :[{}]", JSON.toJSONString(eventDTO));
            return;
        }

        //判断是否是征迁系统
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(eventDTO.getGzlslid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) || null == bdcXmDOS.get(0)) {
            throw new AppException("数据异常，gzlslid：" + eventDTO.getGzlslid() + "未查询到对应的项目数据");
        }
        Integer sply = bdcXmDOS.get(0).getSply();

        if (CommonConstantUtils.SPLY_YC_ZQ.equals(sply)) {
            // 推送数据到征迁
            BdcZqLcthDTO bdcZqLcthDTO = new BdcZqLcthDTO();
            bdcZqLcthDTO.setGzlslid(eventDTO.getGzlslid());
            bdcZqLcthDTO.setThyy(eventDTO.getReason());
            bdcZqLcthDTO.setThzt(status);
            LOGGER.info("推送数据到征迁 :[{}]", JSON.toJSONString(bdcZqLcthDTO));
            bdcZqFeignService.zxsqLcth(bdcZqLcthDTO);
        } else if (CommonConstantUtils.SPLY_FY.equals(sply) && StringUtils.isNotBlank(bdcXmDOS.get(0).getSpxtywh())) {
            CourtUpdateYwcjzt ywcjzt = new CourtUpdateYwcjzt();
            ywcjzt.setCxqqdh(bdcXmDOS.get(0).getSpxtywh());
            exchangeInterfaceFeignService.requestInterface(UPDATEFYCJZT, ywcjzt);
        } else if (CommonConstantUtils.SPLY_YCSL.equals(sply)) {
            // 审批来源为1，为一体化业务，通知一体化删除
            LOGGER.info("通知一体化执行删除，审批来源：{}，工作流实例id：{}",sply,eventDTO.getGzlslid());
            CommonResponse commonResponse = yanchengYthFeignService.deleteTzYth(eventDTO.getGzlslid(), eventDTO.getReason());
            if(commonResponse.isSuccess()) {
                LOGGER.info("成功通知一体化执行删除。");
            }else{
                LOGGER.error("通知一体化执行删除失败！失败信息：{}", commonResponse.getFail());
                throw new AppException(commonResponse.getFail().getMessage());
            }
        }
    }

}