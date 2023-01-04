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
 * @version 1.0, 2018/12/14
 * @description 不动产权信息查询rest接口
 */
public interface CqxxRestService {

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzhList 不动产权证集合
     * @return
     * @description 通过不动产权证号查询所有的不动产权信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cqxx/cqxx-bdcqzh")
    ResponseEntityDTO queryAllCqxxByBdcqzh(@RequestParam(value = "bdcqzh") List<String> bdcqzhList);

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzh 不动产权证号
     * @param qlrmc 权利人名称
     * @param qlrzjh 权利人证件号
     * @return
     * @description 通过不动产权证号、权利人名称、权利人证件号查询所有的不动产权信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cqxx/cqxx-bdcqzh-qlr")
    ResponseEntityDTO queryAllCqxxByBdcqzhAndQlrxx(@RequestParam(value = "bdcqzh",required = false) String bdcqzh, @RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false) String qlrzjh);


    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param qlrQOList 权利人信息
     * @return
     * @description 通过权利人信息批量查询所有的不动产权信息
     */
    @PostMapping(value = "/realestate-public/rest/v1.0/cqxx/cqxx-qlr")
    ResponseEntityDTO queryAllCqxxByQlr(@RequestBody List<QlrQO> qlrQOList);


    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzhList 不动产权证集合
     * @return
     * @description 通过不动产权证号查询限制信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cqxx/xzzt-bdcqzh")
    ResponseEntityDTO queryXzxxByBdcqzh(@RequestParam(value = "bdcqzh") List<String> bdcqzhList);


    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcdyhList 不动产单元号集合
     * @return
     * @description 通过不动产单元号查询限制信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cqxx/xzzt-bdcdyh")
    ResponseEntityDTO queryXzxxByBdcdyh(@RequestParam(value = "bdcdyh") List<String> bdcdyhList);

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcdywybhList 不动产单元唯一编号集合
     * @return
     * @description 通过不动产单元唯一编号查询限制信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cqxx/xzzt-bdcdywybh")
    ResponseEntityDTO queryXzxxByBdcdywybh(@RequestParam(value = "bdcdywybh") List<String> bdcdywybhList);


}
