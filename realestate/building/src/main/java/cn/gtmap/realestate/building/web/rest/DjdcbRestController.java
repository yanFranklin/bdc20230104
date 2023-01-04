package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.service.rest.building.DjdcbRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-28
 * @description 地籍调查表页面相关服务
 */
@RestController
@Api(tags = "地籍调查表页面相关服务")
public class DjdcbRestController implements DjdcbRestService {

    @Value("${djdcb.view.tabs:}")
    private String djdcbTabs;
    @Value("${djdcb.view.tabs.ljz:}")
    private String ljzDjdcbTabs;
    /**
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取配置  地籍调查表页面需要显示的 TAB标签
     */
    @Override
    public List<String> listTabName() {
        List<String> tabNameList = new ArrayList<>();
        if(StringUtil.isBlank(djdcbTabs)){
            djdcbTabs = "zddcb,qsdc,jzbsb,fwxx,zdt,fcfht2";
        }
        String[] tabs = djdcbTabs.split(",");
        tabNameList = Arrays.asList(tabs);
        return tabNameList;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从逻辑装页面打开的地籍调查表页面展示Tab标签
     * @date : 2020/10/22 11:21
     */
    @Override
    public List<String> listTabNameFromLjz() {
        List<String> tabNameList = new ArrayList<>();
        if(StringUtil.isBlank(ljzDjdcbTabs)){
            ljzDjdcbTabs = "zddcb,qsdc,jzbsb,fwxx,zdt";
        }
        String[] tabs = ljzDjdcbTabs.split(",");
        tabNameList = Arrays.asList(tabs);
        return tabNameList;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证不动产单元是否有经营权地块")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public boolean hasJyqdkDcb(@PathVariable("bdcdyh") String bdcdyh){
        return TzmUtils.hasJyqdkDcb(bdcdyh);

    }
}
