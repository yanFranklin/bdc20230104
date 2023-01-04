package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaListResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlBbHtbaMsfDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.etl.BengbuFcjyDataFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.PageUtils.addLayUiCode;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/10/9
 * @description 蚌埠合同信息查询，走中间库！
 */
@RestController
@RequestMapping(value = "/rest/v1.0/bengbu/htxx")
public class BdcHtxxController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcHtxxController.class);

    @Autowired
    BengbuFcjyDataFeignService bengbuFcjyDataFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;


    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/9
     * @description 根据合同编号查询存量房合同信息
     */
    @ResponseBody
    @GetMapping("/clfHtxx")
    public EtlClfHtbaResponseDTo clfHtxx(@RequestParam(name = "contractNo") String contractNo) {
        if (StringUtils.isBlank(contractNo)) {
            throw new AppException("合同编号查询参数不能为空！请输入合同编号！");
        }
        return bengbuFcjyDataFeignService.clfHtxx(contractNo, null, null);

    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/9
     * @description 根据合同编号查询商品房合同信息
     */
    @ResponseBody
    @GetMapping("/spfHtxx")
    public EtlSpfHtbaResponseDTo spfHtxx(@RequestParam(name = "contractNo") String contractNo) {
        if (StringUtils.isBlank(contractNo)) {
            throw new AppException("合同编号查询参数不能为空！请输入合同编号！");
        }

        return bengbuFcjyDataFeignService.spfHtxx(contractNo, null);
    }

    /**
     * @param htbh
     * @param bdcdyh
     * @param qzh
     * @param zjhm
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseBody
    @GetMapping("/baht")
    public Object listBahtPage(@LayuiPageable Pageable pageable, @RequestParam(name = "htbh", required = false) String htbh,
                               @RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "qzh", required = false) String qzh,
                               @RequestParam(name = "zjhm", required = false) String zjhm) {
        String beanName = "fcjySpfBaxx_bbbahttz";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("flag", "504");
        paramMap.put("type", "1");
        paramMap.put("contractNo", htbh);
        paramMap.put("bdcdyh", bdcdyh);
        paramMap.put("qzh", qzh);
        paramMap.put("idcard", zjhm);
        LOGGER.info("获取住建一手房网签合同信息接口，beanName:{}，请求参数参数:{}", beanName, JSONObject.toJSONString(paramMap));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        LOGGER.info("获取住建一手房网签合同信息接口，响应信息:{}", JSONObject.toJSONString(response));
        if (Objects.nonNull(response)) {
            EtlSpfHtbaResponseDTo etlSpfHtbaResponseDTo = JSONObject.parseObject(JSONObject.toJSONString(response), EtlSpfHtbaResponseDTo.class);
            List<EtlBbHtbaMsfDTO> msfDTOList = etlSpfHtbaResponseDTo.getMsfDTOList();
            if (CollectionUtils.isNotEmpty(msfDTOList)) {
                String msr = msfDTOList.stream().map(msfDTO -> msfDTO.getMsr()).collect(Collectors.joining("/"));
                String msrzjhm = msfDTOList.stream().map(msfDTO -> msfDTO.getMsrzjhm()).collect(Collectors.joining("/"));
                etlSpfHtbaResponseDTo.setMsr(msr);
                etlSpfHtbaResponseDTo.setMsrzjhm(msrzjhm);
            }
            EtlSpfHtbaListResponseDTO etlSpfHtbaListResponseDTO = new EtlSpfHtbaListResponseDTO();
            List<EtlSpfHtbaResponseDTo> etlSpfHtbaResponseDTos = new ArrayList<>(4);
            etlSpfHtbaResponseDTos.add(etlSpfHtbaResponseDTo);
            etlSpfHtbaListResponseDTO.setEtlSpfHtbaResponseDTos(etlSpfHtbaResponseDTos);
            etlSpfHtbaListResponseDTO.setTotal(Long.valueOf(etlSpfHtbaResponseDTos.size()));
            if (etlSpfHtbaListResponseDTO != null) {
                Page page = PageUtils.listToPageWithTotal(etlSpfHtbaListResponseDTO.getEtlSpfHtbaResponseDTos(), pageable, etlSpfHtbaListResponseDTO.getTotal().intValue());
                return addLayUiCode(page);
            }
        }
        return PageUtils.initEmptyLayUiCode();
    }
}
