package cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 发票打印数据源服务<strong>常州<strong/>
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 5:16 下午 2020/11/30
 */
public interface BdcFpDyRestService {

    /**
     * 查询收费信息「单个」<br>
     * 基于 sfxx 基础上额外查询 qlrmc，gzldymc 和 收费项目合计金额
     *
     * @param slbh   slbh
     * @param sfxmdm sfxmdm 「逗号隔开」
     * @return {List} 收费信息集合
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/fp")
    Object queryFpcxSfxx(@RequestParam(name = "slbh") String slbh, @RequestParam(name = "sfxmdm") String sfxmdm);

    /**
     * 批量查询收费信息<br>
     * 区分权利人，查询 jkfs 为 扫码支付并且 jspzfph 和 fssrfph 均为空的收费信息
     *
     * @param qlrlb qlrlb
     * @param fplb  fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/fps")
    Object listFpcxSfxx(@RequestParam(name = "qlrlb") String qlrlb, @RequestParam(name = "fplb") String fplb);

    /**
     * 批量查询收费信息<br>
     * 核对页面用
     *
     * @param fplb fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/fps/db")
    Object listFpcxSfxx(@RequestBody List<String> sfxxidList, @RequestParam(name = "fplb") String fplb);

    /**
     * 常州缴款书主表 数据源
     *
     * @param fplb   fplb
     * @param sfxxid sfxxid
     * @return 主表数据
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/fp/dy/jks")
    Object getJksZbSjy(@RequestParam(name = "sfxxid") String sfxxid, @RequestParam(name = "fplb") String fplb);

    /**
     * 常州缴款书子表 数据源
     *
     * @param fplb   fplb
     * @param sfxxid sfxxid
     * @return 主表数据
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/fp/dy/jks/zb")
    Object getJksZb(@RequestParam(name = "sfxxid") String sfxxid, @RequestParam(name = "fplb") String fplb);
}
