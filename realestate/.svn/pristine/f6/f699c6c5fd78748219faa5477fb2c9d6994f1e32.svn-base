package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.building.YbdcdyhResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.building.YbdcdyhFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/07/05
 * @description 原不动产单元号查询服务
 */
@RestController
@RequestMapping("/rest/v1.0/ybdcdyh")
public class BdcYbdcdyhController extends BaseController {

    /**
     * 原不动产单元号查询服务
     */
    @Autowired
    private YbdcdyhFeignService ybdcdyhFeignService;

    /**
     * @description 原不动产单元号查询服务
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/8 9:02
     * @param bdcBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    @PostMapping("/list")
    public List<YbdcdyhResponseDTO> listBdcZmxxByPage(@RequestBody List<String> bdcBdcdyhList) {
        if (CollectionUtils.isEmpty(bdcBdcdyhList)) {
            throw new AppException("查询条件不动产单元号不能为空");
        }
        // 房屋不动产权证号列表
        List<String> fwBdcdyhList = bdcBdcdyhList.stream().filter(bdcBdcdyh -> bdcBdcdyh.indexOf("F") >= 0).collect(Collectors.toList());
        // 土地不动产权证号列表
        List<String> tdBdcdyhList = bdcBdcdyhList.stream().filter(bdcBdcdyh -> bdcBdcdyh.indexOf("W") >= 0).collect(Collectors.toList());
        // 从权籍库获取房屋和土地的原不动产单元号
        List<YbdcdyhResponseDTO> ybdcdyhResponseDTOList = new ArrayList<>(bdcBdcdyhList.size());
        if (CollectionUtils.isNotEmpty(fwBdcdyhList)) {
            List<YbdcdyhResponseDTO> fwYbdcdyhResponseDTOList = ybdcdyhFeignService.queryFwYbdcdyhList(fwBdcdyhList);
            if (CollectionUtils.isNotEmpty(fwYbdcdyhResponseDTOList)) {
                ybdcdyhResponseDTOList.addAll(fwYbdcdyhResponseDTOList);
            }
        }
        if (CollectionUtils.isNotEmpty(tdBdcdyhList)) {
            List<YbdcdyhResponseDTO> tdYbdcdyhResponseDTOList = ybdcdyhFeignService.queryTdYbdcdyhList(tdBdcdyhList);
            if (CollectionUtils.isNotEmpty(tdYbdcdyhResponseDTOList)) {
                ybdcdyhResponseDTOList.addAll(tdYbdcdyhResponseDTOList);
            }
        }
        return ybdcdyhResponseDTOList;
    }
}
