package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjClfHtxxDataDTO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理交易信息rest服务
 */
public interface BdcSlJyxxRestService {

    /**
     * @param bdcSlJyxxDO 不动产受理交易信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 保存不动产受理交易信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/jyxx/")
    BdcSlJyxxDO saveBdcSlJyxx(@RequestBody BdcSlJyxxDO bdcSlJyxxDO);

    /**
     * @param xmid 项目id
     * @return 不动产受理交易信息集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目id获取不动产受理交易信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/list/{xmid}")
    List<BdcSlJyxxDO> listBdcSlJyxxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param jyxxid 交易信息id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/{jyxxid}")
    BdcSlJyxxDO queryBdcSlJyxxByJyxxid(@PathVariable(value = "jyxxid") String jyxxid);

    /**
     * @param bdcSlJyxxDO 不动产交易信息Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理交易信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/")
    BdcSlJyxxDO insertBdcSlJyxx(@RequestBody BdcSlJyxxDO bdcSlJyxxDO);

    /**
     * @param jyxxid 交易信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jyxx/{jyxxid}")
    Integer deleteBdcSlJyxxByJyxxid(@PathVariable(value = "jyxxid") String jyxxid);

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jyxx/xm/{xmid}")
    Integer deleteBdcSlJyxxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param name,cardNo 姓名，证件号码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 房产交易限购信息接口查询
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/fcjyxgxx")
    XgxxHttpResponseDTO queryFcjyXgxx(@RequestParam(name = "name") String name, @RequestParam(name = "cardNo") String cardNo);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 房产交易存量房合同信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/fcjyhtxx/htbh")
    FcjyBaxxDTO queryFcjyClfHtxx(@RequestBody FcjyxxQO fcjyxx);

    /**
     * @param
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 淮安房产交易存量房合同信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/hafcjyhtxx/htbh")
    FcjyClfHtxxDTO queryHaFcjyxx(@RequestBody FcjyxxQO fcjyxx);

    /**
     * @param
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 查询淮安房屋信息是否签售
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/queryHaFcjyFwsfqs")
    Object queryHaFcjyFwsfqs(@RequestParam(value = "fwbh", required = false) String fwbh, @RequestParam(value = "xmmc", required = false) String xmmc,
                             @RequestParam(value = "ysxkzh", required = false) String ysxkzh, @RequestParam(value = "fh", required = false) String fh,
                             @RequestParam(value = "qsdm", required = false) String qsdm, @RequestParam(value = "beanName") String beanName);

    /**
     * @param porcessInsId 工作流实例id
     * @param ywlx 业务类型型 0:正常业务;1:一键注销业务;2：更新业务;
     * @param xmids 项目id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送淮安房产交易通知业务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/tsHaFcjyTsYwxx")
    void tsHaFcjyTsYwxx(@RequestParam(value = "processInsId") String porcessInsId,
                        @RequestParam(value = "ywlx") String ywlx,
                        @RequestParam(value = "xmids", required = false) String xmids);

    /**
     * @param porcessInsId 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送淮安房产交易删除业务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/tsHaFcjyDelYwxx")
    void tsHaFcjyDelYwxx(@RequestParam(value = "processInsId") String porcessInsId, @RequestParam(value = "reason", required = false) String reason);

    /**
     * @param porcessInsId 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送淮安房产交易还原业务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/tsHaFcjyHyYwxx")
    void tsHaFcjyHyYwxx(@RequestParam(value = "processInsId") String porcessInsId);

    /**
     * @param rzid 操作日志id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 补推淮安房产交易业务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/btHaFcjyYwxx")
    boolean btHaFcjyYwxx(@RequestParam(value = "rzid") String rzid);

    /**
     * @param gzlslid 工作流实例ID
     * @param jsonStr 更新json字符串
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    @PutMapping(path = "/realestate-accept/rest/v1.0/jyxx/pl")
    int updateBatchBdcSlJyxx(@RequestParam("gzlslid")String gzlslid,@RequestParam(name = "jsonStr") String jsonStr,@RequestBody List<String> xmidList);

    /**
     * @param xmid 项目ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按照项目更新相关受理交易信息
     */
    @PutMapping(path = "/realestate-accept/rest/v1.0/jyxx/xm")
    int updateXmSlJyxx(@RequestParam("xmid") String xmid, @RequestParam(name = "jsonStr") String jsonStr, @RequestParam(name = "djxl", required = false) String djxl);

    /**
     * @param xmid 项目ID
     * @return 更新数据量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 按照项目更新相关受理交易信息
     */
    @PutMapping(path = "/realestate-accept/rest/v1.0/jyxx/xmid")
    int updateSlJyxxByXmid(@RequestParam("xmid") String xmid, @RequestParam(name = "jsonStr") String jsonStr, @RequestParam(name = "djxl", required = false) String djxl);

    /**
     * @param xmid 项目ID
     * @return 缴纳状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid验证合肥维修资金缴纳状态
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/hfwxzjyz")
    String queryHfwxzjJnzt(@RequestParam(name = "xmid") String xmid);

    /**
     * @param htbh 交易合同编号
     * @return 不动产受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据交易合同编号获取不动产受理交易信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/htbh")
    List<BdcSlJyxxDO> queryBdcSlJyxxByHtbh(@RequestParam(value = "htbh") String htbh);

    /**
     *
     * 处理主房关联后 再次关联附房的逻辑
     * @param fcjyBaxxDTO
     * @param xmid
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/dealFsssBaxx")
    void dealFsssBaxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "xmid") String xmid);

    /**
     * 常州获取房产交易合同信息
     * @param htbh 合同编号
     * @param fwlx 房屋类型
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 房产交易合同信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/cz/fcjyhtxx")
    Object queryFcjyHtxxByHtbh(@RequestParam(value = "htbh") String htbh,
                               @RequestParam(value="fwlx",required = false)String fwlx, @RequestParam(value="xmid")String xmid, @RequestParam(value="sfck",required = false)boolean sfck);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目ids查询受理交易信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/list")
    List<BdcSlJyxxDO> listBdcSlJyxxByXmids(@RequestBody BaseQO baseQO);

    /**
     * @param htbh 合同编号
     * @param fwyt 房屋用途
     * @return true：已关联备案信息 false:未关联备案信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 校验当前合同编号是否已关联备案号
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/checkHtbhLinked")
    boolean checkHtbhLinked(@RequestParam(value = "htbh") String htbh,@RequestParam(value = "fwyt",required = false) String fwyt);

    /**
     * @param jsonObject 需要更新的对象
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新交易信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/updateJyxxByHtbh")
    void updateJyxxByHtbh(@RequestBody JSONObject jsonObject, @RequestParam(value = "htbh")String htbh);

    /**
     * 查询房产交易信息并导入接口返回的交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 房产交易存量房合同信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/fcjyhtxx")
    FcjyBaxxDTO queryFcjyxxWithImport(@RequestBody FcjyxxQO fcjyxxQO) throws Exception;

    /**
     * 处理不动产受理交易信息，更新为房产接口中的交易信息内容
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 房产交易存量房合同信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/handleFcjyxx/{lclx}")
    void handleFcjyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @PathVariable(value = "lclx") String lclx) throws Exception;


    /**
     * @param qlrxx 权利人查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人是否限购，返回名称证件号和是否限购
     * @date : 2021/4/20 14:51
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/xgxx")
    XgxxDTO listQlrXgxx(@RequestBody(required = false) List<Object> qlrxx);


    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人限购信息
     * @date : 2021/4/20 14:51
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/xgxxs/{gzlslid}")
    List<XgxxDTO> listXgxx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param fcjyBaxxDTO 房产交易信息
     * @param gzlslid 工作流实例ID
     * @param cqxmid 产权项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理并生成受理交易相关信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/sljyxx/{cqxmid}")
    void dealSlJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "gzlslid",required = false) String gzlslid, @PathVariable(value = "cqxmid") String cqxmid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理交易获取登记信息()
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/nt/djxx")
    void dealNtDjxx(@RequestBody JSONObject param, @RequestParam(name = "processInsId", required = false) String processInsId) throws Exception;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/nt/yth")
    void dealSpfClfJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "uploadFj", required = false) Boolean uploadFj);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取预告交易信息
     * @date : 2022/1/6 16:16
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/extendyg/{gzlslid}")
    void extendYgJyxx(@PathVariable(value = "gzlslid") String gzlslid);


    /**
     * @param fcjyClfHtxxDTOList 房产存量房合同信息 gzlslid 工作流实例id xmid 项目id foldName 存入的文件夹名称
     * @return FcjyHtxxDTO 房产交易的页面数据
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 处理住建交易获取登记信息, 上传文件
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/nt/zjjyxx")
    List<FcjyHtxxDTO> dealNtZjjyxx(@RequestBody List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList, @RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                                   @RequestParam(name = "foldName", required = false) String foldName);


    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件处理交易信息
     * @date : 2022/8/3 17:35
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/yrbj")
    void dealYrbjJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "xmid") String xmid);

    /**
     * 交易核验（使用原产权证号和义务人证件号查询住建接口，根据xzzt和dyzt判断住建系统中该产权是否有抵押或者查封信息）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param xmid  项目ID
     * @return 核验信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/jyhy")
    Object jyhy(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * 推送反馈状态给住建交易系统
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/fkzjzt")
    void fkzjzt(@RequestParam(value = "gzlslid", required = false) String gzlslid, @RequestParam(value = "htbh") String htbh,
                @RequestParam(value = "zjh", required = false) String zjh);

    /**
     * 接收住建合同信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zjClfHtxxDataDTO 住建存量房合同信息DTO
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/jszjhtxx")
    void jsZjHtxx(@RequestBody ZjClfHtxxDataDTO zjClfHtxxDataDTO);

    /**
     * 推送受理信息给住建交易系统
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/tszjslxx")
    void tsZjSlxx(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param ntSpfBaJyxxList 南通房产商品房房备案合同信息 gzlslid 工作流实例id xmid 项目id foldName 存入的文件夹名称
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 处理住建交易获取登记信息, 上传文件
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/nt/spfbajyxx")
    void dealNtSpfBaJyxx(@RequestBody List<JSONObject> ntSpfBaJyxxList, @RequestParam(name = "gzlslid", required = false) String gzlslid,
                         @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "foldName", required = false) String foldName);

    /**
     * @param ntSpfBaJyxxList 通州房产商品房房备案合同信息 gzlslid 工作流实例id xmid 项目id foldName 存入的文件夹名称
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 处理住建交易获取登记信息, 上传文件
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/tz/spfbajyxx")
    void dealTzSpfBaJyxx(@RequestBody List<HtxxDTO> ntSpfBaJyxxList, @RequestParam(name = "gzlslid", required = false) String gzlslid,
                         @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "foldName", required = false) String foldName);

    /**
     * @param
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取二手房网签合同数据
     */
    @PostMapping("/realestate-accept/rest/v1.0/jyxx/esfwqhtxx/htbh")
    String queryEsfWqHtxx(@RequestBody FcjyxxQO fcjyxx);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送不动产转移和抵押登记登簿信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/jyxx/tsbdczydydjxx")
    void tsBdcZyDyDjxx(@RequestParam(value = "processInsId") String gzlslid);
}
