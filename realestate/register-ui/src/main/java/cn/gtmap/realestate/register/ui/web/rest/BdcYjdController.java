package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdGdxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYjdFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/2
 * @description 移交单服务
 */
@RestController
@RequestMapping("/rest/v1.0/yjd")
public class BdcYjdController extends BaseController {

    @Autowired
    BdcYjdFeignService bdcYjdFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return listBdcXmYjd
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目拥有的移交单
     */
    @PostMapping("/xmYjdList")
    public List<BdcXmYjdDTO> listBdcXmYjd(@RequestBody BdcYjdQO bdcYjdQO) {
        return bdcYjdFeignService.listXmOwnYjd(bdcYjdQO);
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return BdcYjdDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成并报错移交单信息
     */
    @PostMapping("/yjdxx")
    public BdcYjdDO generateAndSaveYjdxx(@RequestBody BdcYjdQO bdcYjdQO) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcYjdQO.setYjr(userDto.getAlias());
        return bdcYjdFeignService.generateAndSaveYjdxx(bdcYjdQO);
    }

    @PutMapping("")
    public int updateBdcYjd(@RequestBody BdcYjdDO bdcYjdDO) {
        return bdcYjdFeignService.updateBdcYjd(bdcYjdDO);
    }

    @GetMapping("/page")
    public Object listBdcYjdxx(@LayuiPageable Pageable pageable, BdcYjdQO bdcYjdQO) {
        String yjdQO = JSON.toJSONString(bdcYjdQO);
        Page<BdcYjdDTO> yjdPage = bdcYjdFeignService.listBdcYjdByPage(pageable, yjdQO);
        return super.addLayUiCode(yjdPage);
    }

    @GetMapping("/checkYjd/{yjdid}")
    public Object listBdcYjdGdxx(@LayuiPageable Pageable pageable,@PathVariable(name = "yjdid") String yjdid,
                                 BdcYjdQO bdcYjdQO) {
        bdcYjdQO.setYjdid(yjdid);
        String yjdQO = JSON.toJSONString(bdcYjdQO);
        Page<BdcYjdGdxxDTO> yjdGdxxPage = bdcYjdFeignService.listBdcYjdGdxx(pageable,yjdQO);
        return super.addLayUiCode(yjdGdxxPage);
    }

    @DeleteMapping("/delGdxx/{gxid}")
    public void delGdxx(@PathVariable("gxid") String gxid) {
        bdcYjdFeignService.delGdxx(gxid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页信息
     * @param bdcYjdQO 移交单项目信息查询参数
     * @description  查询流程项目信息（用于海门新增移交单场景，根据流程移交）
     */
    @GetMapping("/xmxx")
    public Object listBdcYjdXmxx(@LayuiPageable Pageable pageable, BdcYjdQO bdcYjdQO) {
        Page<BdcYjdXmxxDTO> yjdXmxxDTOPage = bdcYjdFeignService.listBdcYjdXmxx(pageable, JSON.toJSONString(bdcYjdQO));
        return super.addLayUiCode(yjdXmxxDTOPage);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdXmxxDTO 项目信息
     * @return wjy : 没有移交过； yyj : 已经移交过
     * @description  核查当前项目是否已经移交过
     */
    @PostMapping("/check")
    public String checkState(@RequestBody BdcYjdXmxxDTO bdcYjdXmxxDTO) {
        return bdcYjdFeignService.checkState(bdcYjdXmxxDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 移交单信息
     * @description  （海门）生成移交单
     */
    @PostMapping("/new")
    public BdcYjdDO generateBdcYjd(@RequestBody List<String> slbhList){
        if(CollectionUtils.isEmpty(slbhList)) {
            throw new MissingArgumentException("未设置需要移交的项目");
        }
        return bdcYjdFeignService.generateBdcYjd(slbhList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDOList 移交单集合
     * @return 操作返回信息
     * @description  （海门）进行移交单移交到档案
     */
    @PostMapping("/yj")
    public String executeYj(@RequestBody List<BdcYjdDO> bdcYjdDOList){
        if(CollectionUtils.isEmpty(bdcYjdDOList)) {
            throw new MissingArgumentException("未设置需要移交的移交单");
        }
        return bdcYjdFeignService.executeYj(bdcYjdDOList);
    }

}
