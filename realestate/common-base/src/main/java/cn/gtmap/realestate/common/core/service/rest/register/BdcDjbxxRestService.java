package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.qo.register.BdcDjbqlQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/25
 * @description 登记簿信息
 */
public interface BdcDjbxxRestService {
    /**
     * @param zdzhh 宗地宗海号
     * @return List<BdcQlDjMlDTO> 不动产权利登记目录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取某宗地宗海的不动产登记权利
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{zdzhh}/qldjml", method = RequestMethod.GET)
    List<BdcQlDjMlDTO> listBdcQlDjMl(@PathVariable(value = "zdzhh") String zdzhh);

    /**
     * @param zdzhh 宗地宗海号
     * @return bdcdyh 不动产单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元在登记簿的权利信息，包括返回在登记簿的排序
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{zdzhh}/{bdcdyh}/qldjml", method = RequestMethod.GET)
    BdcQlDjMlDTO indexBdcdyhQlDjMl(@PathVariable(value = "zdzhh") String zdzhh, @PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param bdcDjbqlQO 登记簿查询QO
     * @return Page<BdcQlDjMlDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利目录
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/qldjml", method = RequestMethod.POST)
    Page<BdcQlDjMlDTO> bdcQlDjMlByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO);


    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbDO 不动产登记簿
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产登记簿信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{zdzhh}", method = RequestMethod.GET)
    BdcBdcdjbDO queryBdcBdcdjb(@PathVariable(value = "zdzhh") String zdzhh);

    /**
     * @param bdcdyh 宗地不动产单元号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产宗地基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zdjbxx", method = RequestMethod.GET)
    BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxx(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param zdzhh 宗地宗海号
     * @return BdcBdcdjbZdjbxxDO 不动产宗地基本信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据宗地宗海号查询宗地宗海基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/zdjbxx", method = RequestMethod.GET)
    BdcBdcdjbZdjbxxDO queryBdcBdcdjbZdjbxxByZdzhh(@RequestParam(value = "zdzhh") String zdzhh);


    /**
     * @param bdcdyh 不动产单元号
     * @return List<BdcBdcdjbZdjbxxZdbhqkDO> 不动产宗地变化情况列表
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询不动产宗地变化情况
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zdjbxx/zdbhqk", method = RequestMethod.GET)
    List<BdcBdcdjbZdjbxxZdbhqkDO> listBdcBdcdjbZdjbxxZdbhqk(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh 不动产单元号
     * @return {BdcBdcdjbZhjbxxDO} 宗海基本信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zhjbxx", method = RequestMethod.GET)
    BdcBdcdjbZhjbxxDO queryBdcBdcdjbZhjbxx(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海状况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海状况
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zhjbxx/yhzk", method = RequestMethod.GET)
    List<BdcBdcdjbZhjbxxYhzkDO> listBdcBdcdjbZhjbxxYhzk(@PathVariable(value = "bdcdyh") String bdcdyh);


    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息用海用岛坐标
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海用岛坐标
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zhjbxx/yhydzb", method = RequestMethod.GET)
    List<BdcBdcdjbZhjbxxYhydzbDO> listBdcBdcdjbZhjbxxYhydzb(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 宗海基本信息宗海变化情况
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的宗海变化情况
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/zhjbxx/zhbhqk", method = RequestMethod.GET)
    List<BdcBdcdjbZhjbxxZhbhqkDO> listBdcBdcdjbZhjbxxZhbhqk(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的指定类型的权利信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/{qllx}/qlxx", method = RequestMethod.POST)
    List<BdcQl> listBdcQlxx(@PathVariable(value = "bdcdyh") String bdcdyh, @PathVariable(value = "qllx") String qllx, @RequestBody List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/{qllx}/zxqlxx", method = RequestMethod.POST)
    List<BdcQl> listBdcZxQlxx(@PathVariable(value = "bdcdyh") String bdcdyh, @PathVariable(value = "qllx") String qllx);

    /**
     * @param qlid 房地产权的权利ID
     * @return List<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的房地产权项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{qlid}/fdcqxm", method = RequestMethod.GET)
    List<BdcFdcqFdcqxmDO> listBdcFdcqxm(@PathVariable(value = "qlid") String qlid);

    /**
     * @param xmid 项目ID
     * @return List<BdcFdcq3DO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取业主共有部分的主信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{xmid}/fdcq3", method = RequestMethod.GET)
    List<BdcFdcq3DO> listBdcFdcq3(@PathVariable(value = "xmid") String xmid);
    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利信息, 返回Json字符串
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/qlJson", method = RequestMethod.POST)
    String bdcQlJsonByPage(@RequestBody BdcDjbqlQO bdcDjbqlQO);

    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿权利信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/bdcQl", method = RequestMethod.POST)
    Page<BdcQl> bdcQlByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO);
    /**
     * @param bdcDjbqlQO 登记簿查询QO
     * @return Page<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询房地产权项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/fdcqxm", method = RequestMethod.POST)
    Page<BdcFdcqFdcqxmDO> bdcFdcqxmByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO);

    /**
     * @param bdcDjbqlQO 登记簿权利查询QO
     * @return Page<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询业主共有部分信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/fdcq3Gyxx", method = RequestMethod.POST)
    Page<BdcFdcq3GyxxDTO> bdcFdcq3GyxxDOByPageJson(@RequestBody BdcDjbqlQO bdcDjbqlQO);


    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList 权属状态
     * @return BdcQlQtsxDTO 不动产权利及其他事项DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产权利及其他事项信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/qlqtsx", method = RequestMethod.POST)
    BdcQlQtsxDTO queryBdcQlQtSx(@PathVariable(value = "bdcdyh") String bdcdyh, @RequestBody List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @return BdcDjbQlDTO 登记簿权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的登记簿权利信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/{bdcdyh}/djbql", method = RequestMethod.POST)
    BdcDjbQlDTO queryBdcDjbQl(@PathVariable(value = "bdcdyh") String bdcdyh, @RequestBody List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @return String 格式化后的单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 格式化单元号
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/format/{bdcdyh}", method = RequestMethod.GET)
    String formatBdcdyh(@PathVariable(value = "bdcdyh") String bdcdyh);

    /**
     * @param zdzhh 宗地宗海号
     * @param bdcdyh 不动产单元号
     * @param onlyQlfm
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成登记簿目录树（不动产单元号不为空时，只返回当前宗地的这个单元号信息）
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djbmls", method = RequestMethod.GET)
    List generateDjbMls(@RequestParam(name = "zdzhh") String zdzhh, @RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "onlyQlfm") Boolean onlyQlfm);

    /**
     * @param bdcdyh   不动产单元号
     * @param onlyQlfm 是否只查询权利封面，不查询具体权利
     * @return BdcDjbQlMlVO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 每个单元号在登记簿中的权利目录树
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djbQlmls", method = RequestMethod.GET)
    BdcDjbQlMlVO generateDyhQlMl(@RequestParam(name = "bdcdyh") String bdcdyh, @RequestParam(name = "onlyQlfm") Boolean onlyQlfm);

    /**
     * @param fwBdcXmDO 房屋不动产项目
     * @param tdqllx    需要查询的土地的qllx
     * @return BdcXmDO 查询到的土地的不动产权证
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知单元号的房屋产权信息，查询同单元号的土地项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/fwTdXm", method = RequestMethod.POST)
    BdcXmDO queryFwTdXm(@RequestBody BdcXmDO fwBdcXmDO, @RequestParam(name = "tdqllx") Integer tdqllx);

    /**
     * @return 电子证照版本
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 电子证照版本
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/getDzzzVersion")
    String getDzzzVersion();
    

    /**
     * @param bdcBdcdjbZhjbxxDO 不动产登记簿宗海基本信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海不动产登记簿宗海基本信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/updateBdcBdcdjbZdjbxx")
    int updateBdcBdcdjbZdjbxx(@RequestBody BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO);

    /**
     * @param bdcBdcdjbZhjbxxYhzkDO 宗海基本信息用海状况
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新宗海基本信息用海状况
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/updateBdcBdcdjbZhjbxxYhzk")
    int updateBdcBdcdjbZhjbxxYhzk(@RequestBody BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO);

}
