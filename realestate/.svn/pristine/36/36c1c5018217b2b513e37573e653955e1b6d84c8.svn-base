package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjQO;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjRequestData;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjRequestHead;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjCqxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjCzxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjFwxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response.GjjResponDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/5/18
 * @description 南通公积金接口
 */
@Service(value = "nantongGjjService")
public class NantongGjjService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NantongGjjService.class);

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Value("#{'${ckytlist:}'.split(',')}")
    private List<Integer> ckytList;
    @Value("#{'${glytlist:}'.split(',')}")
    private List<Integer> glytList;
    @Value("#{'${zfytlist:}'.split(',')}")
    private List<Integer> zfytList;

    public GjjResponDTO queryFwxx(GjjQO gjjQo) {
        LOGGER.info("come=-=-=-=-=-=-=-=-=-");

        GjjResponDTO responDTO = new GjjResponDTO();
        GjjRequestHead head = gjjQo.getHead();
        //校验参数
        GjjRequestData requestData = gjjQo.getData();

        if (null != requestData && StringUtils.isNotBlank(requestData.getCqzh()) && StringUtils.isNotBlank(requestData.getXm()) && StringUtils.isNotBlank(requestData.getZjhm())) {
            //开始查询
            List<GjjFwxxResponseData> fwxxResponseDataList = bdcdjMapper.queryFwxx(requestData);
            if (CollectionUtils.isNotEmpty(fwxxResponseDataList)) {
                LOGGER.info("查出房屋信息有：{}条",fwxxResponseDataList.size());
                Double cwckmj = 0.0;
                Double glmj = 0.0;
                List<GjjFwxxResponseData> responseData = new ArrayList<>();
                for (GjjFwxxResponseData fwxx : fwxxResponseDataList) {
                    //循环yt，组织车库面积和阁楼面积 TODO
                    if (ckytList.contains(fwxx.getGhyt())) {
                        cwckmj += Double.valueOf(fwxx.getJzmj());
                    }
                    if (glytList.contains(fwxx.getGhyt())) {
                        glmj += Double.valueOf(fwxx.getJzmj());
                    }
                }
                for (GjjFwxxResponseData fwxxForzf : fwxxResponseDataList) {
                    //确定为主房，组织数据
                    LOGGER.info("配置住房用途为：{}",zfytList.toString());
                    LOGGER.info("住房用途为：{}",fwxxForzf.getGhyt());
                    if (zfytList.contains(fwxxForzf.getGhyt())) {
                        LOGGER.info("包含！：{}",fwxxForzf.getGhyt());
                        fwxxForzf.setCkmj(String.valueOf(cwckmj));
                        fwxxForzf.setGlmj(String.valueOf(glmj));
                        responseData.add(fwxxForzf);
                    }
                }
                head.setRetcode("00001");
                head.setRetmsg("正确");
                responDTO.setHead(head);
                responDTO.setData(responseData);
                return responDTO;
            }
            head.setRetcode("00001");
            head.setRetmsg("正确");
            responDTO.setHead(head);
            responDTO.setData(new ArrayList<>());
            return responDTO;

        } else {
            head.setRetcode("9999");
            head.setRetmsg("其它异常错误");
            responDTO.setHead(head);
            responDTO.setData(new ArrayList<>());
            return responDTO;
        }
    }

    /**
     * 人和证件号查询产证信息
     *
     * @param gjjQo 查询参数
     * @return
     * @Date 2021/5/19
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public GjjCqxxResponseDTO queryCzxx(GjjQO gjjQo) {
        GjjCqxxResponseDTO responDTO = new GjjCqxxResponseDTO();
        GjjRequestHead head = gjjQo.getHead();
        //校验参数
        GjjRequestData requestData = gjjQo.getData();
        if (null != requestData && StringUtils.isNotBlank(requestData.getXm()) && StringUtils.isNotBlank(requestData.getZjhm())) {
            //开始查询
            List<GjjCzxxResponseData> czxxResponseData = bdcdjMapper.queryCzxx(requestData);
            head.setRetcode("00001");
            head.setRetmsg("正确");
            responDTO.setHead(head);
            responDTO.setData(czxxResponseData);
            return responDTO;

        } else {
            head.setRetcode("9999");
            head.setRetmsg("其它异常错误");
            responDTO.setHead(head);
            responDTO.setData(new ArrayList<>());
            return responDTO;
        }
    }

}
