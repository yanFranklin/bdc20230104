package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.dto.dbxxts.zjjg.ZjjgCqxxDTO;
import cn.gtmap.realestate.exchange.core.dto.dbxxts.zjjg.ZjjgDbxxDTO;
import cn.gtmap.realestate.exchange.core.dto.dbxxts.zjjg.ZjjgDyaxxDTO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@OpenController(consumer = "登簿信息推送相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
public class BdcZjtgRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZjtgRestController.class);

    @Autowired
    BdcXmFeignService xmFeignService;

    @Autowired
    ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcShijiRestController bdcShijiRestController;

    @Autowired
    private UserManagerUtils userManagerUtils;
    //资金监管需要查询的产权权利类型
    @Value("#{'${zjjg.cqxx:4,6,8}'.split(',')}")
    private List<Integer> cqxxqllxList;

    //资金监管需要查询的抵押权利类型
    @Value("#{'${zjjg.dyaxx:37}'.split(',')}")
    private List<Integer> dyxxqllxList;

    /**
     * 推送登簿信息  cqxx dyxx
     *
     * @param processInsId
     */
    @PostMapping("/zjtg/tsdbxx")
    Object zjtgTsdbxx(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName) {
        LOGGER.info("需要推送dbxx的流程gzlslid：{}", processInsId);
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例id参数不能为空！");
        }
        ZjjgDbxxDTO zjjgDbxxDTO = new ZjjgDbxxDTO();
        List<ZjjgCqxxDTO> cqxxDTOList = new ArrayList<>();
        List<ZjjgDyaxxDTO> dyaxxDTOList = new ArrayList<>();
        //根据gzlslid查询项目表信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = xmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (cqxxqllxList.contains(bdcXmDO.getQllx())) {
                    //组织产权信息
                    ZjjgCqxxDTO zjjgCqxxDTO = new ZjjgCqxxDTO();
                    zjjgCqxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                    zjjgCqxxDTO.setCqzh(bdcXmDO.getBdcqzh());
                    zjjgCqxxDTO.setDjsj(null != bdcXmDO.getDjsj() ? DateUtils.formateTime(bdcXmDO.getDjsj(), DateUtils.DATE_FORMAT_2) : "");
                    zjjgCqxxDTO.setQlr(bdcXmDO.getQlr());
                    zjjgCqxxDTO.setZjh(bdcXmDO.getQlrzjh());
                    zjjgCqxxDTO.setYcqzh(StringUtils.replace(bdcXmDO.getYcqzh(), ",", "/"));
                    cqxxDTOList.add(zjjgCqxxDTO);
                }
                if (dyxxqllxList.contains(bdcXmDO.getQllx())) {
                    //组织抵押信息
                    ZjjgDyaxxDTO zjjgDyaxxDTO = new ZjjgDyaxxDTO();
                    zjjgDyaxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                    zjjgDyaxxDTO.setBdcdjzmh(bdcXmDO.getBdcqzh());
                    zjjgDyaxxDTO.setDjsj(null != bdcXmDO.getDjsj() ? DateUtils.formateTime(bdcXmDO.getDjsj(), DateUtils.DATE_FORMAT_2) : "");
                    zjjgDyaxxDTO.setYcqzh(StringUtils.replace(bdcXmDO.getYcqzh(), ",", "/"));
                    zjjgDyaxxDTO.setQlr(bdcXmDO.getQlr());
                    zjjgDyaxxDTO.setZjh(bdcXmDO.getQlrzjh());
                    dyaxxDTOList.add(zjjgDyaxxDTO);
                }
            }
            if (CollectionUtils.isEmpty(cqxxDTOList)) {
                cqxxDTOList.add(new ZjjgCqxxDTO());
            }
            if (CollectionUtils.isEmpty(dyaxxDTOList)) {
                dyaxxDTOList.add(new ZjjgDyaxxDTO());
            }
            zjjgDbxxDTO.setCqxx(cqxxDTOList);
            zjjgDbxxDTO.setDyxx(dyaxxDTOList);
            LOGGER.info("推送的登簿数据为：{}", zjjgDbxxDTO.toString());
            //推送数据给监管方
            UserDto userDto = null;
            String department = "";
            Object response = null;
            try {
                response = exchangeBeanRequestService.request("zjjg_tsdbxx", JSONObject.parseObject(JSONObject.toJSONString(zjjgDbxxDTO, SerializerFeature.WriteNullStringAsEmpty)));
            } catch (Exception e) {
                LOGGER.info("推送资金监管异常:{}", e.getMessage());
            } finally {
                LOGGER.info("执行记录操作");
                if (StringUtils.isNotBlank(currentUserName)) {
                    LOGGER.info("当前用户操作人：{}", currentUserName);

                    userDto = userManagerUtils.getUserByName(currentUserName);
                    department = userManagerUtils.getOrganizationByUserName(userDto.getUsername());
                }
                bdcShijiRestController.insertShijiInterfaceLog("zjjg_tsdbxx", StringUtils.isNotBlank(userDto.getAlias()) ? userDto.getAlias() : userDto.getUsername(), department, JSON.toJSONString(zjjgDbxxDTO), JSON.toJSONString(response), "zjjg_tsdbxx");
                LOGGER.info("市级推送资金监管操作结束");
            }

            if (response != null) {
                LOGGER.info("推送资金监管返回：{}", response.toString());
                return response.toString();
            }

        }
        return null;

    }
}
