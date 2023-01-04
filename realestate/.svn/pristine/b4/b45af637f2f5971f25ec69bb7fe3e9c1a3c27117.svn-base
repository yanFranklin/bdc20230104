package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjClfHtxxDataDTO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理交易信息
 */
public interface BdcSlJyxxService {

    /**
     * @param xmid 项目ID
     * @return 不动产受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取不动产受理交易信息
     */
    List<BdcSlJyxxDO> listBdcSlJyxxByXmid(String xmid);

    /**
     * @param htbh 合同编号
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据合同编号获取受理交易信息
     */
    List<BdcSlJyxxDO> listBdcSlJyxxByHtbh(String htbh);

    /**
     * 根据查询参数获取受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlJyxxQO 不动产受理交易信息DO
     * @return 交易信息集合
     */
    List<BdcSlJyxxDO> listBdcSlJyxx(BdcSlJyxxQO bdcSlJyxxQO);

    /**
     * @param bdcSlJyxxDO 不动产受理交易信息
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存受理交易信息
     */
    BdcSlJyxxDO saveBdcSlJyxx(BdcSlJyxxDO bdcSlJyxxDO);

    /**
     * @param jyxxid 交易信息id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    BdcSlJyxxDO queryBdcSlJyxxByJyxxid(String jyxxid);

    /**
     * @param bdcSlJyxxDO 交易信息
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理交易信息
     */
    BdcSlJyxxDO insertBdcSlJyxx(BdcSlJyxxDO bdcSlJyxxDO);

    /**
     * @param jyxxid 交易信息id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    Integer deleteBdcSlJyxxByJyxxid(String jyxxid);

    /**
     * @param xmid 项目id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    Integer deleteBdcSlJyxxByXmid(String xmid);

    /**
     * @param name,cardNo
     * @return http返回信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易限购信息
     */
    XgxxHttpResponseDTO queryFcjyXgxx(String name, String cardNo);


    /**
     * @param
     * @param
     * @return http返回信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易合同信息
     */
    FcjyBaxxDTO queryFcjyHtxx(FcjyxxQO fcjyxxQO);

    /**
     * @param
     * @param
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询淮安房产交易合同信息
     */
    FcjyClfHtxxDTO queryHaFcjyHtxx(FcjyxxQO fcjyxxQO);

    /**
     * @param
     * @param
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询淮安房屋信息是否签售
     */
    Object queryHaFcjyFwsfqs(String fwbh, String xmmc, String ysxkzh, String fh, String qsdm, String beanName);

    /**
     * @param gzlslid
     * @param ywlx
     * @param xmids
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易通知业务信息
     * @date : 2022/8/31 19:11
     */
    void tsHaFcjyTsYwxx(String gzlslid, String ywlx, String xmids);

    /**
     * @param gzlslid
     * @param reason
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易删除业务信息
     * @date : 2022/8/31 19:11
     */
    void tsHaFcjyDelYwxx(String gzlslid, String reason);

    /**
     * @param gzlslid
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送淮安房产交易还原业务信息
     * @date : 2022/8/31 19:11
     */
    void tsHaFcjyHyYwxx(String gzlslid);

    /**
     * @param rzid
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 补推淮安房产交易业务信息
     * @date : 2022/8/31 19:11
     */
    boolean btHaFcjyYwxx(String rzid);


    /**
     * @param fcjyClfHtxxDTO 交易合同信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易合同信息
     */
    void handleClfHtxxResponse(FcjyBaxxDTO fcjyClfHtxxDTO, String xmid, String lclx);

    /**
     * @param gzlslid 工作流实例ID
     * @param jsonStr 更新json对象字符串
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    int updateBatchBdcSlJyxx(String gzlslid, String jsonStr, List<String> xmidList);

    /**
     * @param bdcSlYwxxDTO 受理业务信息
     * @param xmid         项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存房产交易信息
     */
    void dealFcjyBaxxDTO(BdcSlYwxxDTO bdcSlYwxxDTO, String xmid, BdcSlxxInitDTO bdcSlxxInitDTO, String gzldyid);

    /**
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按照项目更新相关受理交易信息
     */
    int updateXmSlJyxx(String xmid, String jsonStr, String djxl);

    /**
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 按照项目更新相关受理交易信息
     */
    int updateSlJyxxByXmid(String xmid, String jsonStr, String djxl);

    /**
     * @param xmid 项目ID
     * @return 维修资金缴纳状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid维修资金缴纳状态
     */
    String queryHfwxzjJnzt(String xmid);


    /**
     * 处理主房关联后,再次关联附房的逻辑
     *
     * @param fcjyBaxxDTO
     * @param xmid
     */
    void dealFsssBaxx(FcjyBaxxDTO fcjyBaxxDTO, String xmid);

    /**
     * @param bdcSlDeleteCsDTO 受理信息删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据参数批量删除受理交易信息
     */
    void deleteBdcSlJyxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * 调用合同交易备案信息接口，获取房产交易合同交易信息
     *
     * @param htbh 合同编号
     * @param fwlx 房屋类型
     * @param xmid 项目ID
     * @param sfck 是否查库
     * @return 房产交易合同信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object queryFcjyHtxxByHtbh(String htbh, String fwlx, String xmid, boolean sfck);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目ids查询受理交易信息
     */
    List<BdcSlJyxxDO> listBdcSlJyxxByXmids(BaseQO baseQO);

    /**
     * @param htbh 合同编号
     * @param fwyt 房屋用途
     * @return true：已关联备案信息 false:未关联备案信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 校验当前合同编号是否已关联备案号
     */
    boolean checkHtbhLinked(String htbh, String fwyt);

    /**
     * @param jsonObject 需要更新的对象
     * @param htbh       合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新交易信息
     */
    void updateJyxxByHtbh(JSONObject jsonObject, String htbh);

    /**
     * 查询房产交易信息并导入接口返回的交易信息
     *
     * @param fcjyxxQO 房产交易QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 房产交易存量房合同信息
     */
    FcjyBaxxDTO queryFcjyxxWithImport(FcjyxxQO fcjyxxQO);

    /**
     * 处理房产交易信息包含以下几点内容：
     * 1、处理不动产项目中的交易合同号与权利信息中的交易金额
     * 2、处理不动产受理交易信息，更新为房产接口中的交易信息内容
     * 3、处理房屋信息，使用接口返回的房屋信息补全受理库中的房屋信息
     * 4、更新登记权利人信息，先删除原有权利人与义务人信息，在新增接口返回人员信息
     * 5、更新受理申请人信息，只删除权利人与家庭成员信息，保留义务人数据。在新增交易接口中的权利人信息
     *
     * @param fcjyBaxxDTO 房产交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void handleFcjyxx(FcjyBaxxDTO fcjyBaxxDTO, String lclx);

    /**
     * @param qlrxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人是否限购，返回名称证件号和是否限购
     * @date : 2021/4/20 15:01
     */
    XgxxDTO listQlrXgxx(List<Object> qlrxx);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人限购信息
     * @date : 2021/4/21 11:30
     */
    List<XgxxDTO> listXgxx(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    void dealSlJyxx(FcjyBaxxDTO fcjyBaxxDTO, String gzlslid, String cqxmid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理交易获取登记信息()
     */
    void dealNtDjxx(JSONObject param, String gzlslid) throws Exception;

    /**
     * @param fcjyClfHtxxDTOList 房产存量房合同信息
     * @param gzlslid            工作流实例id
     * @param xmid               项目id
     * @return FcjyHtxxDTO 房产交易的页面数据
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 处理住建交易获取登记信息, 上传文件
     */
    List<FcjyHtxxDTO> dealNtZjjyxx(List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList, String gzlslid, String xmid, String foldName);

    /**
     * @param ntSpfBaJyxxList 南通住建商品房备案交易信息
     * @param gzlslid            工作流实例id
     * @param xmid               项目id
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 处理南通住建商品房备案交易信息, 上传文件
     */
    void dealNtSpfBaJyxx(List<JSONObject> ntSpfBaJyxxList, String gzlslid, String xmid, String foldName);

    /**
     * @param ntSpfBaJyxxList 通州住建商品房备案交易信息
     * @param gzlslid            工作流实例id
     * @param xmid               项目id
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 处理住建商品房备案交易信息, 上传文件
     */
    void dealTzSpfBaJyxx(List<HtxxDTO> ntSpfBaJyxxList, String gzlslid, String xmid, String foldName);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 记录接口获取的一手房交易信息，更新不动产受理交易信息与房屋信息
     */
    void dealSpfClfJyxx(FcjyBaxxDTO fcjyBaxxDTO, String xmid, Boolean uploadFj);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承预告的交易信息
     * @date : 2022/1/6 15:02
     */
    void extendYgjyxx(String gzlslid);


    /**
     * @param fcjyBaxxDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/8/3 18:20
     */
    void dealYrbjJyxx(FcjyBaxxDTO fcjyBaxxDTO, String xmid);

    /**
     * 交易核验（使用原产权证号和义务人证件号查询住建接口，根据xzzt和dyzt判断住建系统中该产权是否有抵押或者查封信息）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param xmid  项目ID
     * @return 核验信息
     */
    Object jyhy(String gzlslid, String xmid);

    /**
     * 推送反馈状态给住建交易系统
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @param zjh 证件号
     */
    void fkzjzt(String gzlslid, String htbh, String zjh);

    /**
     * 接收住建合同信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zjClfHtxxDataDTO 住建存量房合同信息DTO
     */
    void jsZjHtxx(ZjClfHtxxDataDTO zjClfHtxxDataDTO);

    /**
     * 推送受理信息给住建交易系统
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    void tsZjSlxx(String gzlslid);

    /**
     * @param fcjyxxQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取二手房网签合同数据
     */
    String queryEsfWqHtxx(FcjyxxQO fcjyxxQO);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送不动产转移和抵押登记登簿信息
     */
    void tsBdcZyDyDjxx(String gzlslid);
}
