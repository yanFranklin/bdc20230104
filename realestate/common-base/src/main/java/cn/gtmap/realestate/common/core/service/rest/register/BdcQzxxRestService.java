package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/12/23 17:12
 * @description 评价器签名接口
 */
public interface BdcQzxxRestService {

    /**
     * @param bdcQzxxDO bdcQzxxDO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存签字信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/qzxx/SaveQzxx")
    BdcQzxxDO insertBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO);

    /**
     * @param bdcQzxxDO 评价器签字Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产评价器签字信息
     */
    @PostMapping("/realestate-register/rest/v1.0/qzxx/UpdateQzxx")
    Integer updateBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO);

    /**
     * 获取pdf文件，进行转码
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-register/rest/v1.0/qzxx/pdfBase64")
    String pdfBase64(@RequestParam(name = "pdf") String pdf);

    /**
     * 查询签字信息
     *
     * @param bdcQzxxDO bdcQzxxDO
     * @return BdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-register/rest/v1.0/qzxx/queryBdcQzxx")
    List<BdcQzxxDO> queryBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO);

    /**
     * @param bdcQzxxDO
     * @return
     * @Date 2020/7/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-register/rest/v1.0/qzxx/SaveOrUpdateQzxx")
    Object SaveOrUpdateQzxx(@RequestBody BdcQzxxDO bdcQzxxDO);

    /**
     * @param bdcQzxxDO
     * @return
     * @Date 2020/7/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-register/rest/v1.0/qzxx/uploadQzxx")
    void uploadQzxx(@RequestBody BdcQzxxDO bdcQzxxDO);

    /**
     * 存量签字数据上传到文件中心服务
     *
     * @param slbh
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:03 2020/8/3
     */
    @PostMapping("/realestate-register/rest/v1.0/qzxx/uploadQzxxClsj")
    Integer clsjUpload(@RequestParam(name = "slbh") String slbh, @RequestParam("bdlx") Integer bdlx);
}
