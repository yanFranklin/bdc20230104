package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblCompareService;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblLogService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 补录对比服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 5:27 下午 2020/4/24
 */
@Service
public class BdcXxblCompareServiceImpl implements BdcXxblCompareService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblCompareServiceImpl.class);
    /**
     * 当前类名
     */
    private static final String CLASS_NAME = BdcXxblCompareServiceImpl.class.getName();

    /**
     * 补录日志服务
     */
    @Autowired
    private BdcXxblLogService bdcXxblLogService;

    /**
     * 项目服务
     */
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    /**
     * 历史关系服务
     */
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    /**
     * 历史关系服务
     */
    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    /**
     * 字典服务
     */
    @Autowired
    private CompareHandler compareHandler;

    /**
     * 信息对比
     * 根据当前项目的 processInsId 获取日志中存储的初始化是的备份信息
     *
     * @param processInsId 修改流程的 processInsId
     */
    @Override
    public List<BdcDbVO> xxdb(String processInsId) throws Exception {
        CompareParam compareParam = this.queryCompareParam(processInsId);
        // 根据当前项目的 xmid 去查询
        BdcYwxxDTO before = this.queryYwxxFormLog(compareParam.getXmid());

        // 获取被修改项目的 xmid，也就是上一手的 xmid
        BdcYwxxDTO after = bdcInitFeignService.queryYwxx(compareParam.getYxmid());
        // 业务数据变化内容
        List<BdcDbVO> ywxxChange = Lists.newArrayList();
        if (before != null || after != null) {
            ywxxChange = compareHandler.compareBdcYwxx(before, after);
        }
        LOGGER.debug("{}：项目工作流：{},对比 xmid：{}, yxmid：{}，对比信息：{}", CLASS_NAME, processInsId, compareParam.getXmid(),
                compareParam.getYxmid(), JSON.toJSONString(ywxxChange));
        return ywxxChange;
    }

    /**
     * 查询页面高亮显示内容
     *
     * @param processInsId 修改流程的 processInsId
     * @return {List<BdcDbDetailVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcDbDetailVO> queryGlxx(String processInsId) throws Exception {
        CompareParam compareParam = this.queryCompareParam(processInsId);
        // 根据当前项目的 xmid 去查询
        BdcYwxxDTO before = queryYwxxFormLog(compareParam.getXmid());

        // 获取被修改项目的 xmid，也就是上一手的 xmid
        BdcYwxxDTO after = bdcInitFeignService.queryYwxx(compareParam.getYxmid());
        // 高亮信息
        List<BdcDbDetailVO> glDetails = Lists.newArrayList();
        if (before != null || after != null) {
            glDetails = compareHandler.compareYwxxDetail(before, after);
        }
        LOGGER.debug("{}：项目工作流：{},对比 xmid：{}, yxmid：{}，对比信息：{}", CLASS_NAME, processInsId, compareParam.getXmid(),
                compareParam.getYxmid(), JSON.toJSONString(glDetails));
        return glDetails;
    }

    /**
     * 从日志中查询出修改初始化的数据
     *
     * @param xmid 修改流程的 xmid
     * @return {BdcYwxxDTO} 业务数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private BdcYwxxDTO queryYwxxFormLog(String xmid) {
        String ywxx = "";
        // 获取日志中存储的 ywxx
        Page<AuditLogDto> auditLogDtos = bdcXxblLogService.listLog(null, xmid, LogEventEnum.BLLC_MODIFY);
        // 解析日志
        if (auditLogDtos != null && auditLogDtos.hasContent()) {
            // 取第一条数据
            List<DataValue> dataValueList = auditLogDtos.getContent().get(0).getBinaryAnnotations();
            if (CollectionUtils.isNotEmpty(dataValueList)) {
                for (DataValue dataValue : dataValueList) {
                    String key = dataValue.getKey().toLowerCase();
                    if ("ywxx".equals(key)) {
                        ywxx = dataValue.getValue();
                    }
                }
            }
        }
        return StringUtils.isBlank(ywxx) ? null : bdcXxblLogService.convertJsonToYwxx(ywxx);
    }

    /**
     * 从日志中查询出修改初始化的数据
     *
     * @param gzlslid 修改流程的 xmid
     * @return {BdcYwxxDTO} 业务数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private CompareParam queryCompareParam(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("对比数据缺少 gzlslid");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("未查询到修改项目信息");
        }

        // 获取到修改项目的 xmid
        String xmid = bdcXmDTOS.get(0).getXmid();
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if (CollectionUtils.isEmpty(bdcXmLsgxDOS)) {
            throw new AppException("信息对比未查询到对应历史关系");
        }
        String yxmid = bdcXmLsgxDOS.get(0).getYxmid();
        return new CompareParam(xmid, yxmid);
    }

    /**
     * 对比参数 <br/>
     * xmid 补录修改流程 xmid
     * yxmid 被修改流程的 xmid，也就是项目关系中的 yxmid
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @version 5:02 下午 2020/4/26
     */
    private static class CompareParam {
        private String xmid;
        private String yxmid;

        public CompareParam(String xmid, String yxmid) {
            this.xmid = xmid;
            this.yxmid = yxmid;
        }

        public String getXmid() {
            return xmid;
        }

        public void setXmid(String xmid) {
            this.xmid = xmid;
        }

        public String getYxmid() {
            return yxmid;
        }

        public void setYxmid(String yxmid) {
            this.yxmid = yxmid;
        }
    }
}
