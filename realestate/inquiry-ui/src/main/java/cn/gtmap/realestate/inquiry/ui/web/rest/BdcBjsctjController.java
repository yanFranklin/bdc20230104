package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BjsctjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBjscTjFeignService;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * @author: <a href="@mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version: V1.0, 2020.07.28
 * @description: 办件时长统计前端控制层, 主要是调用平台接口获取数据并组织
 */
@RestController
@RequestMapping("/bjsctj")
public class BdcBjsctjController extends BaseController {

    @Autowired
    private BdcBjscTjFeignService bdcBjscTjFeignService;

    /**
     * 办件时长统计
     * @param bjsctjQO
     * @param pageable
     * @return
     */
    @GetMapping("/count")
    public Object countBjsc(BjsctjQO bjsctjQO, @LayuiPageable Pageable pageable){
        List<Map> list = bdcBjscTjFeignService.listBjscCount(JSON.toJSONString(bjsctjQO));
        Collections.sort(list, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                String avgBjsc1 =  o1.get("avgBjsc").toString();
                String avgBjsc2 =  o2.get("avgBjsc").toString();
                return avgBjsc1.compareTo(avgBjsc2);
            }
        });
        return super.addLayUiCode(PageUtils.listToPage(list, pageable));
    }

    /**
     * 时间段配置
     * @return
     */
    @GetMapping("/sjdpz")
    public Object sjdpz(){
        return bdcBjscTjFeignService.sjdpz();
    }

}