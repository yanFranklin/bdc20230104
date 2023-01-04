package cn.gtmap.realestate.common.core.service.rest.pub;

import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import cn.gtmap.realestate.common.core.qo.pub.QlrQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/16
 * @description 查询土地相关产权信息
 */
public interface TdxxRestService {
    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzhList 不动产权证集合
     * @return
     * @description 通过不动产权证号查询所有的土地相关产权信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/tdxx/cqxx-bdcqzh")
    ResponseEntityDTO queryTdCqxxByBdcqzh(@RequestParam(value = "bdcqzh") List<String> bdcqzhList);

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzh 不动产权证号
     * @param qlrmc 权利人名称
     * @param qlrzjh 权利人证件号
     * @return
     * @description 通过不动产权证号、权利人名称、权利人证件号查询所有的土地相关产权信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/tdxx/cqxx-bdcqzh-qlr")
    ResponseEntityDTO queryTdCqxxByBdcqzhAndQlrxx(@RequestParam(value = "bdcqzh",required = false) String bdcqzh, @RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false) String qlrzjh);


    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param qlrQOList 权利人信息
     * @return
     * @description 通过权利人信息批量查询所有的土地相关产权信息
     */
    @PostMapping(value = "/realestate-public/rest/v1.0/tdxx/cqxx-qlr")
    ResponseEntityDTO queryTdCqxxByQlr(@RequestBody List<QlrQO> qlrQOList);
}
