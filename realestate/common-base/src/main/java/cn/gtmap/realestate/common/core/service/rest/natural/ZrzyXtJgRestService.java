package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJgLzrGxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产系统银行配置查询接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/05/02
 */
public interface ZrzyXtJgRestService {
    /**
     * 查询全部的银行配置信息
     *
     * @return List<BdcXtJgDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/list")
    List<BdcXtJgDO> listBdcXtJg(@RequestParam("jglb") Integer jglb);

    /**
     * 根据机构名称获取机构信息
     *
     * @return BdcXtJgDO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg")
    BdcXtJgDO queryJgByJgmc(@RequestParam(value = "jgmc") String jgmc, @RequestParam(value = "jglb", required = false) String jglb);

    /**
     * 根据系统机构查询参数，获取不动产系统机构信息
     *
     * @param bdcXtJgDO 不动产系统机构DO
     * @return 系统机构信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/realestate-natural/rest/v1.0/xtjg/list")
    List<BdcXtJgDO> queryBdcXtJg(@RequestBody BdcXtJgDO bdcXtJgDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取按月结算 银行结构
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/list/ayjs")
    List<BdcXtJgDO> listAyjsBdcXtJg();

	/**
	 * @param
	 * @return
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 获取按月结算 银行结构
	 */
	@GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/list/ayjs/yhmc")
	List<BdcXtJgDO> listAyjsBdcXtJgYhmc(@RequestParam("yhmc") String yhmc);

    /**
     * 根据银行名称查询是否按月结算银行（模糊匹配）
     * <p>用于模糊匹配按月结算的银行名称。 由于有些地区页面查询时，下拉选择框只展示部分名称。
     * @param yhmc 银行名称
     * @return 月结银行
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/list/ayjs/yhmc/like")
	List<BdcXtJgDO> listAyjsBdcXtJgYhmcLike(@RequestParam("yhmc") String yhmc);

    /**
     * 根据机构id获取配置的lzr
     * @return BdcXtJgDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/xtjglzr")
    List<BdcJgLzrGxDO> queryJgLzrByJgid(@RequestParam(value = "jgid") String jgid);

    /**
     * 分页查询机构领证人
     * @return BdcXtJgDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(path = "/realestate-natural/rest/v1.0/xtjg/xtjglzrByPage")
    Page<BdcJgLzrGxDTO> queryJgLzrByPage(Pageable pageable,
                                         @RequestParam(name = "bdcXtjgLzrQOJSON", required = false) String bdcXtjgLzrQOJSON);


    /**
     * @param gxid 关系id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据关系id删除机构领证人
     */
    @DeleteMapping(path = "/realestate-natural/rest/v1.0/xtjg/{gxid}")
    void deleteJglzr(@PathVariable("gxid") String gxid);

    /**
     * @param bdcJgLzrGxDO 机构领证人信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增机构领证人
     */
    @PostMapping(path = "/realestate-natural/rest/v1.0/xtjg/jglzr")
    BdcJgLzrGxDO insertBdcJgLzr(@RequestBody BdcJgLzrGxDO bdcJgLzrGxDO);

    /**
     * 批量修改系统机构是否可用
     * @param yhmcList  银行名称集合
     * @param sfky  是否可用（1：可用 0：不可用）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/realestate-natural/rest/v1.0/xtjg/sfky/{sfky}")
    void batchModifyXtJgSfky(@RequestBody List<String> yhmcList, @PathVariable(name = "sfky") int sfky);

    /**
     * @param bdcXtJgDO 机构信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增机构
     */
    @PutMapping(path = "/realestate-natural/rest/v1.0/xtjg")
    BdcXtJgDO insertBdcJg(@RequestBody BdcXtJgDO bdcXtJgDO);

    /**
     * @param jgmclist 机构名称信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @PutMapping(path = "/realestate-natural/rest/v1.0/queryBatchBdcXtJg")
    List<BdcXtJgDO> queryBatchBdcXtJg(@RequestBody List<String> jgmclist);
}
