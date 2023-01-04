package cn.gtmap.interchange.service.impl;

import cn.gtmap.interchange.core.domain.BdcGgDO;
import cn.gtmap.interchange.core.dto.CommonResponse;
import cn.gtmap.interchange.core.dto.XtggProperties;
import cn.gtmap.interchange.core.vo.BdcXtggVO;
import cn.gtmap.interchange.service.BdcXtggService;
import cn.gtmap.interchange.util.CommonService;
import cn.gtmap.interchange.util.RestRpcUtils;
import cn.gtmap.interchange.util.UuidUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021-04-21
 * @description 公告信息处理
 */
@Service
public class BdcXtggServiceImpl implements BdcXtggService {
    private static final Logger logger = LoggerFactory.getLogger(BdcXtggServiceImpl.class);

    private static final String NODATA = "【当前没有待推送公告数据！】";

    @Autowired
    private RestRpcUtils restRpcUtils;

    @Autowired
    private XtggProperties properties;

    @Autowired
    private CommonService commonService;


    /**
     * 根据指定查询参数从登记系统获取公告信息并推送至局网站系统
     * @param bdcXtggVO 公告信息查询参数
     * @return {CommonResponse} 操作结果
     */
    @Override
    public CommonResponse pushXtgg(BdcXtggVO bdcXtggVO){
        String spaceId = UuidUtils.generate16();
        if (commonService.checkParamIsNull(bdcXtggVO)) {
            return commonService.failResult(spaceId,"请求参数存在空值，请设值后查询！");
        }

        try {
            // 1、获取登记公告信息
            logger.info("{} 1.1、开始获取登记3.0系统待推送公告信息，登记系统地址：{}，查询参数：{}", spaceId, properties.getBdcdjurl(), JSON.toJSONString(bdcXtggVO));
            CommonResponse ggxx = restRpcUtils.postRpcRequest(properties.getBdcdjurl(), bdcXtggVO, CommonResponse.class.getName());
            if (null == ggxx || !ggxx.isSuccess()) {
                return commonService.failResult(spaceId, "未获取到待推送公告信息或公告信息登记处理失败，请核查登记系统日志！");
            }

            if(null == ggxx.getData()) {
                return commonService.successResult(spaceId, NODATA);
            }

            JSONObject data = (JSONObject) ggxx.getData();
            JSONArray successXmids = (JSONArray) data.get("successXmids");
            JSONArray detail = (JSONArray) data.get("detail");
            if(null == detail) {
                return commonService.successResult(spaceId, NODATA);
            }

            List<BdcGgDO> bdcGgDOList = JSONArray.parseArray(JSON.toJSONString(detail), BdcGgDO.class);
            if(CollectionUtils.isEmpty(bdcGgDOList)) {
                return commonService.successResult(spaceId, NODATA);
            }
            logger.info("{} 1.2、获取到登记3.0系统待推送公告信息，共{}条，对应xmid为：{}", spaceId, bdcGgDOList.size(), successXmids);


            // 2、推送局网站接口进行推送
            logger.info("{} 2.1、推送局网站系统公告信息开始，局网站地址：{}", spaceId, properties.getJwzurl());
            for(BdcGgDO bdcGgDO : bdcGgDOList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("columnname", properties.getColumnname());
                jsonObject.put("columncode", properties.getColumncode());
                jsonObject.put("msgtitle", bdcGgDO.getGgbt());
                jsonObject.put("msgcontent", bdcGgDO.getGgnr());
                jsonObject.put("instime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                jsonObject.put("headimg", "");
                jsonObject.put("headimgurl", "");
                logger.info("{} 2.2、推送公告数据：{}", spaceId, JSON.toJSONString(jsonObject));

                String res = RestRpcUtils.send(properties.getJwzurl(), jsonObject);
                logger.info("{} 2.3、推送一条公告信息成功，对应公告信息ID：{}，对应xmid:{}，返回信息：{}", spaceId, bdcGgDO.getGgid(), bdcGgDO.getXmid(), res);
            }
            logger.info("{} 2.4、推送局网站系统公告信息结束！", spaceId);

            // 3、推送登记记录已推送数据
            logger.info("{} 3.1、回调登记3.0系统接口记录成功推送公告信息日志开始，登记系统地址：{}！", spaceId, properties.getBdcdjlogurl());
            Integer num = restRpcUtils.postRpcRequest(properties.getBdcdjlogurl(), this.resolveBdcGgDO(bdcGgDOList), Integer.class.getName());
            logger.info("{} 3.2、回调登记3.0系统接口记录成功推送公告信息日志结束，保存记录{}条", spaceId, num);

            List<String> xmids = JSON.parseArray(JSON.toJSONString(successXmids), String.class);
            return commonService.successResult(spaceId, StringUtils.join(xmids));
        } catch (Exception e) {
            e.printStackTrace();
            return commonService.failResult(spaceId,"公告信息处理失败，请查看日志或联系管理员");
        }
    }

    /**
     * 处理公告信息部分字段数据
     * @param bdcGgDOList 公告信息
     * @return {List} 公告信息
     */
    private List<BdcGgDO> resolveBdcGgDO(List<BdcGgDO> bdcGgDOList) {
        for(BdcGgDO bdcGgDO : bdcGgDOList) {
            if(StringUtils.isBlank(bdcGgDO.getGgid())) {
                bdcGgDO.setGgid(UuidUtils.generate16());
            }

            if(StringUtils.isBlank(bdcGgDO.getGgrxm())) {
                bdcGgDO.setGgrxm(properties.getGgrxm());
            }

            if(null == bdcGgDO.getGgsj()) {
                bdcGgDO.setGgsj(new Date());
            }
        }
        return bdcGgDOList;
    }
}
