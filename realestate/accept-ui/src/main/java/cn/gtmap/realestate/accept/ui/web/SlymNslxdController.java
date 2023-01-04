package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2019/11/6,1.0
 * @description 受理纳税联系单页面
 */
@Controller
@RequestMapping("/rest/v1.0/nslxd")
public class SlymNslxdController extends BaseController {

    //纳税联系单单个页面地址
    private static final String NSLXD_SINGLE_HTML = "/nantong/nslxd/nslxd.html";
    //纳税联系单批量页面地址
    private static final String NSLXD_PL_HTML = "/nantong/nslxd/nslxdList.html";

    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcSlFeignService bdcSlFeignService;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: String 纳税联系单页面
     * @description 通过工作流实例id获取证书数量，证书数量大于1时，返回纳税联系单列表页面。否则返回单个纳税联系单页面
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardNslxdHtml(@RequestParam(name = "processInsId") String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new NullPointerException("未获取到工作流实例id信息");
        }
        //通过证书初始化服务获取当前流程生成多少证书，判断生成多少纳税单
        final int nslxdCount = this.bdcZsInitFeignService.countLcBdcqzWithFdcq(processInsId);
        if (0 == nslxdCount) {
            throw new NullPointerException("未获取到纳税联系单信息，由于对应的证书数量为0。请联系管理员。");
        }
        if (1 == nslxdCount) {
            return NSLXD_SINGLE_HTML;
        }
        return NSLXD_PL_HTML;
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流程实例id
     * @return: String 纳税联系单打印xml数据
     * @description 通过工作流程实例id，获取打印的xml数据
     */
    @GetMapping("/print/{processInsId}/xml/list")
    @ResponseBody
    public String getNslxdPrintXml(@PathVariable("processInsId") String processInsId) throws Exception {
        List<Map<String, String>> resultList = this.bdcSlFeignService.getNslxdData(processInsId);
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(resultList.size());
        for(int i = 0;i<resultList.size();i++){
            BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
            bdcDysjDTO.setDysj(JSONObject.toJSONString(resultList.get(i)));
            bdcDysjDTO.setDyzd(Constants.nslxd_fr3_xml);
            bdcDysjDTO.setDyzbsj("{}");
            bdcDysjDTOList.add(bdcDysjDTO);
        }
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: Object List<Map<Key,value>> 纳税联系单数据 key：纳税联系单字段名，value：纳税联系单值
     * @description 通过工作流实例id获取纳税联系单表单数据，返回值为List类型
     */
    @PostMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getNslxdData(@RequestParam("processInsId") String processInsId) throws Exception {
        return this.bdcSlFeignService.getNslxdData(processInsId);
    }

}
