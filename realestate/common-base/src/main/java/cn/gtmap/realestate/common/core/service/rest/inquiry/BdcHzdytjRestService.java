package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-11-15
 * @description 汇总抵押统计
 */
public interface BdcHzdytjRestService {

    /**
     * 汇总抵押统计
     *
     * @param bdcHzdytjQOJson
     * @return List
     * @author <a href="mailto:Listchenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcHzdytj/tj")
    List listBdcHzdytj(@RequestParam(name = "bdcHzdytjQOJson") String bdcHzdytjQOJson);

    /**
     * 汇总抵押统计蚌埠
     *
     * @param bdcHzdytjQOJson
     * @return List
     * @author <a href="mailto:Listchenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcHzdytj/tj/bb")
    List listBdcHzdytjBb(@RequestParam(name = "bdcHzdytjQOJson") String bdcHzdytjQOJson);


    /**
     * 月报表抵押统计
     *
     * @param bdcHzdytjQOJson
     * @return List
     * @author <a href="mailto:Listchenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcHzdytj/ybb")
    List listBdcYbbdytj(@RequestParam(name = "bdcHzdytjQOJson") String bdcHzdytjQOJson);

}
