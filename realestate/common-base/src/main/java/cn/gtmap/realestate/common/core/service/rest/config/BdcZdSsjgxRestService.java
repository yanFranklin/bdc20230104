package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcZdSsjGxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/8/23
 */
public interface BdcZdSsjgxRestService {

    /**
     * 查询BdcZdSsjGxDO，bdcZdSsjGxQO为空默认查全部激活状态的数据
     * @param bdcZdSsjGxQO
     * @return
     */
    @PostMapping("/realestate-config/rest/v1.0/zd/ssjgx/list")
    List<BdcZdSsjGxDO> listSsjgx(@RequestBody BdcZdSsjGxQO bdcZdSsjGxQO);

    /**
     * 通过子目录代码获取BdcZdSsjGxDO
     * @param zmldm
     * @return
     */
    @GetMapping("/realestate-config/rest/v1.0/zd/ssjgx/get/by/zmldm")
    BdcZdSsjGxDO getSsjgxByZmldm(@RequestParam(value = "zmldm") String zmldm);

    /**
     * 通过父目录代码获取BdcZdSsjGxDO
     * @param fmldms
     * @return
     */
    @GetMapping("/realestate-config/rest/v1.0/zd/ssjgx/get/by/fmldms")
    List<BdcZdSsjGxVO> getSsjgxByFmldms(@RequestParam(value = "fmldms") String fmldms);

    /**
     * @param json 查询条件
     * @return 分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询共享接口字典
     */
    @PostMapping("/realestate-config/rest/v1.0/zd/ssjgx/page")
    Page<BdcZdSsjGxDO> listBdcZdSsjGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据子目录代码删除共享接口字典
     */
    @DeleteMapping("/realestate-config/rest/v1.0/zd/ssjgx/{zmldm}")
    void deleteBdcZdSsjGxByZmldm(@PathVariable("zmldm") String zmldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存共享接口字典
     */
    @PostMapping("/realestate-config/rest/v1.0/zd/ssjgx")
    void saveBdcZdSsjgx(@RequestBody BdcZdSsjGxDO bdcZdSsjGxDO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询省市级共享字典数据
     */
    @GetMapping("/realestate-config/rest/v1.0/zd/ssjgx")
    BdcZdSsjGxDO queryBdcZdSsjGx(@RequestParam(value = "id") String id);

}
