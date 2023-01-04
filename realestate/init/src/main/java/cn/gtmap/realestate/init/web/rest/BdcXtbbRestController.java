package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.service.rest.init.BdcXtbbRestService;
import cn.gtmap.realestate.init.core.service.BdcXtBbService;
import cn.gtmap.realestate.init.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不动产系统版本服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 14:01
 */
@RestController
@Api(tags = "不动产系统版本服务接口")
public class BdcXtbbRestController extends BaseController implements BdcXtbbRestService {

    @Autowired
    private BdcXtBbService bdcXtBbService;

    /**
     * 获取更新日志列表
     *
     * @return 版本更新日志列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ApiOperation(value = "版本更新日志列表")
    @ResponseStatus(HttpStatus.OK)
    public Object getGxrzList() {
        List<Map> list = bdcXtBbService.listGxrzList();
        ArrayList<Map> result = new ArrayList<>(list.size());
        // 对照表，通过对照表确定在 list 中的位置
        Map dz = new HashMap();
        int index = 0;
        for (Map map : list) {
            // 对照表中没有对应的 版本编号，直接加入新的编号
            if (dz.get(map.get("BBBH")) == null) {
                dz.put(map.get("BBBH"), index);
                index++;
                // 存放版本更新信息
                Map temp = Maps.newHashMap();
                temp.put("BBBH", map.get("BBBH"));
                temp.put("FBSJ", map.get("FBSJ"));
                // 存入 版本更新日志 列表
                ArrayList<Map> details = Lists.newArrayList();
                details.add(map);
                temp.put("DETAILS", details);
                result.add(temp);
            } else {
                // 对照表中有对应 版本编号，直接在 更新日志列表中加入
                ArrayList<Map> details = (ArrayList<Map>) result.get(Integer.valueOf(dz.get(map.get("BBBH")).toString())).get("DETAILS");
                details.add(map);
            }
        }
        return result;
    }
}
