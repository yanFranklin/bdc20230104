package cn.gtmap.realestate.init.web.rest.hefei;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.init.hefei.BdcBjbhRestService;
import cn.gtmap.realestate.init.service.bjbh.BdcBjbhService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
@RestController
@Api(tags = "合肥办件编号")
public class BdcBjbhRestController extends BaseController implements BdcBjbhRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBjbhRestController.class);

    @Autowired
    private BdcBjbhService bdcBjbhService;

    @Override
    public Page<BdcBjbhDTO> listBjbhByPage(Pageable pageable, @RequestParam(name = "bdcBjbhQOStr", required = false) String bdcBjbhQOStr) {
        return bdcBjbhService.listBjbhByPage(pageable, bdcBjbhQOStr);
    }

    @Override
    public BdcZwQhResponseDTO takeNumber(@RequestParam(name = "processInsId") String processInsId) {
        return bdcBjbhService.takeNumber(processInsId);
    }

    /**
     * 4.3.1 申报登记
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public BdcZwQhResponseDTO sbdj(@RequestParam(name = "processInsId") String processInsId) {
        return bdcBjbhService.sbdj(processInsId);
    }

    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param processInsId
     * @param blzt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjSlxx(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "blzt") String blzt) {
        return bdcBjbhService.sbdjSlxx(processInsId, blzt);
    }

    /**
     * 4.3.8	办结
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjBjxx(@RequestParam(name = "processInsId") String processInsId) {
        return bdcBjbhService.sbdjBjxx(processInsId);
    }

    /**
     * 工作流事件 依次触发以上接口
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void sbdjWorkFlow(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("申报登记工作流id为空！：{}", processInsId);
            throw new MissingArgumentException("申报登记工作流id为空");
        }
        //依次调用4.3.1     4.3.3  4.3.8
//        BdcZwQhResponseDTO responseDTO = bdcBjbhService.sbdj(processInsId);
        SbdjfkResponseDTO slxx = bdcBjbhService.sbdjSlxx(processInsId, "1");
        SbdjfkResponseDTO bjxx = bdcBjbhService.sbdjBjxx(processInsId);

//        LOGGER.info("推送申报信息返回结果：{}", responseDTO.toString());
        LOGGER.info("推送受理信息返回结果：{}", slxx.toString());
        LOGGER.info("推送办结信息返回结果：{}", bjxx.toString());
    }
}
