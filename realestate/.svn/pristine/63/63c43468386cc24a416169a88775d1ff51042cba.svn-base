package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/19
 * @description 获取配置的URL的Controller服务
 */
@RestController
@RequestMapping("/rest/v1.0/url")
public class BdcUrlController extends BaseController {

    /**
     * 打印是否预览配置
     */
    @Value("${url.hideModel}")
    protected String hideModel;

    /**
     * 蚌埠：登记历史查看1.0项目受理图片指定档案模型
     */
    @Value("${bengbu.djls-damx:}")
    protected String damx;


    /**
     * @return url对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取系统配置的Url地址
     */
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public BdcUrlDTO getBdcUrl() {
        return this.bdcUrl();
    }

    /**
     * 打印是否预览配置
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/dyModel")
    @ResponseStatus(HttpStatus.OK)
    public String getHideModel() {
        return this.hideModel;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return 档案模型
     * @description 蚌埠：登记历史查看1.0项目受理图片指定档案模型
     */
    @GetMapping(value = "/damx")
    public String getDamx() {
        return this.damx;
    }
}
