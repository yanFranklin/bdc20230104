package cn.gtmap.interchange.util;

import cn.gtmap.interchange.core.dto.CommonResponse;
import cn.gtmap.interchange.core.dto.XtggProperties;
import cn.gtmap.interchange.core.vo.BdcXtggVO;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021-04-22
 * @description 杂项逻辑处理（从各个类提取出来，减少对应类凌乱度）
 */
@Data
@Service
public class CommonService {
    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private XtggProperties properties;


    public List<Integer> getGglx() {
        return parseInteger(properties.getGglx());
    }

    public List<Integer> getSply() {
        return parseInteger(properties.getSply());
    }

    public List<Integer> parseInteger(String data) {
        String[] array = data.split(",");
        List<Integer> result = new ArrayList<>();
        for (String item : array) {
            result.add(Integer.parseInt(item));
        }
        return result;
    }

    public String startTime() throws ParseException {
        String starttime = StringUtils.isBlank(properties.getStarttime()) ? "00:00:00" : properties.getStarttime();
        return paseDate(starttime);
    }

    public String endTime() throws ParseException {
        String endtime = StringUtils.isBlank(properties.getEndtime()) ? "23:59:59" : properties.getEndtime();
        return paseDate(endtime);
    }

    public String paseDate(String timeStr) throws ParseException {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " " + timeStr;
    }

    public CommonResponse failResult(String spaceId, String failMsg) {
        logger.error(spaceId + " " + failMsg);
        return CommonResponse.fail("1", failMsg);
    }

    public CommonResponse successResult(String spaceId, String data) {
        logger.info(spaceId + " " + data);
        return CommonResponse.ok("成功推送公告信息，对应xmid：" + data);
    }

    public boolean checkParamIsNull(BdcXtggVO bdcXtggVO) {
        if (null == bdcXtggVO) return true;

        if (null == bdcXtggVO.getStartTime() || null == bdcXtggVO.getEndTime()) {
            logger.error("查询公告信息BdcXtggVO参数开始时间startTime、结束时间endTime存在空值！");
            return true;
        }

        if (CollectionUtils.isEmpty(bdcXtggVO.getGglx())) {
            logger.error("查询公告信息BdcXtggVO参数公告类型gglx存在空值！");
            return true;
        }

        if (CollectionUtils.isEmpty(bdcXtggVO.getSply())) {
            logger.error("查询公告信息BdcXtggVO参数审批来源sply存在空值！");
            return true;
        }

        return false;
    }
}
