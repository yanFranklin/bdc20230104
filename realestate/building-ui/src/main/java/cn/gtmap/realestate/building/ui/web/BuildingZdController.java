package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-15
 * @description 为页面提供 字典 服务
 */
@Controller
@RequestMapping("/zd")
public class BuildingZdController extends BaseController{
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingZdController.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdDoNames 逗号隔开的字典实体名
     * @return java.util.Map
     * @description
     */
    @RequestMapping("/mul")
    @ResponseBody
    public Map mulListZd(String zdDoNames){
        if(StringUtils.isNotBlank(zdDoNames)){
            String[] arr = zdDoNames.split(",");
            List<Class> classList = new ArrayList<>();
            for(int i= 0 ; i < arr.length ; i++){
                String doName = arr[i];
                try {
                    Class clazz = Class.forName(Constants.COMMON_DOMAIN_PATH + "." + doName);
                    classList.add(clazz);
                } catch (ClassNotFoundException e) {
                    LOGGER.error("{}字典类不存在",Constants.COMMON_DOMAIN_PATH + "." + doName,e);
                }
            }
            if(CollectionUtils.isNotEmpty(classList)){
                Class[] classArr = new Class[classList.size()];
                return getZdList(classList.toArray(classArr));
            }
        }
        return new HashMap(0);
    }
}
