package cn.gtmap.realestate.etl.service;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/9/25.
 * Time: 17:39
 * 推送权力阳光数据
 */
public interface PushQlygService {

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param map
     * @rerutn
     * @description 推送一张网数据
     */
    void pushQlygData(Map map);

}
