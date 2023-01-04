package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXzZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产信息补录相关服务定义
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/11 18:22
 */
public interface BdcXxblZsRestService {

    /**
     * 补录更新不动产权证号(新增证书)
     * @param xmid xmid
     * @param bdcqzh bdcqzh 录入的不动产权证号，分别持证的逗号隔开，英文中文均可
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/zs/bdcqzh")
    void updateBlBdcqzh(@RequestParam("xmid") String xmid, @RequestParam("bdcqzh") String bdcqzh);

    /**
     * 更新部分证书信息，同时处理冗余字段
     * 权利其他状况、附记以及不动产权证号
     *
     * @param zsid   证书 id
     * @param xmid   项目 id
     * @param qlqtzk 权利其他状况
     * @param bdcqzh 不动产权证号
     * @param fj     附记
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/zs/bfxx")
    void updateBfZsxx(@RequestParam(value = "zsid") String zsid,
                      @RequestParam(value = "xmid") String xmid, @RequestParam(value = "qlqtzk") String qlqtzk,
                      @RequestParam(value = "bdcqzh") String bdcqzh, @RequestParam(value = "fj") String fj);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsid, xmid, bdcqzh]
     * @return void
     * @description 更新不动产权证号
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/zs/bfxx/bdcqzh")
    void updateBdcqzh(@RequestParam(value = "zsid") String zsid, @RequestParam(value = "xmid") String xmid,
                      @RequestParam(value = "bdcqzh") String bdcqzh);

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param xmid       xmid
     * @param bdcZssdDOS 证书锁定
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping(value = "/realestate-register/rest/v1.0/blxx/zs/sd")
    int updateZssd(@RequestParam(value = "xmid") String xmid, @RequestBody List<BdcZssdDO> bdcZssdDOS);

    /**
     * 查询证书锁定信息 <br>
     *
     * @param xmid xmid
     * @param sdzt 锁定状态
     * @return 证书锁定集合
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/zs/sd")
    List<BdcZssdDO> listZssdByXmid(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "sdzt") Integer sdzt);

    /**
     * 修正更新证书信息
     *
     * @param bdcXzZsVO
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/xz/zs")
    void updateXzBdczs(@RequestBody BdcXzZsVO bdcXzZsVO);

    /**
     * 添加项目和已有证书的关联关系
     * @param bdcZsQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 添加项目和已有证书的关联关系
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/zs/addConnection")
    void addXmZsConnection(@RequestBody BdcZsQO bdcZsQO);
    /**
     * 查询信息补录证书
     * @param bdcZsQO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询信息补录证书
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/zs/page")
    Page<BdcZsVO> bdcZsByPageJson(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcZsQO bdcZsQO);
}
