package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.core.domain.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href ="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/7/31
 * @description 不动产字典缓存枚举
 */
public enum BdcZdEnum {
    /**
     * 登记类型
     */
    DJLX("BDC_ZD_DJLX", BdcZdDjlxDO.class,"登记类型",false),
    /**
     * 证件种类
     */
    ZJZL("BDC_ZD_ZJZL", BdcZdZjzlDO.class,"证件种类",false),
    /**
     * 面积单位
     */
    MJDW("BDC_ZD_MJDW", BdcZdMjdwDO.class,"面积单位",false),
    /**
     * 权利人类型
     */
    QLRLX("BDC_ZD_QLRLX", BdcZdQlrlxDO.class,"权利人类型",false),
    /**
     * 房屋类型
     */
    FWLX("BDC_ZD_FWLX", BdcZdFwlxDO.class,"房屋类型",false),
    /**
     * 房屋性质
     */
    FWXZ("BDC_ZD_FWXZ", BdcZdFwxzDO.class,"房屋性质",false),
    /**
     * 房屋结构
     */
    FWJG("BDC_ZD_FWJG", BdcZdFwjgDO.class,"房屋结构",false),
    /**
     * 房屋用途
     */
    FWYT("BDC_ZD_FWYT", BdcZdFwytDO.class,"房屋用途",false),
    /**
     * 构(建)筑物类型
     */
    GJZWLX("BDC_ZD_GJZWLX", BdcZdGjzwlxDO.class,"构(建)筑物类型",false),
    /**
     * 养殖业方式
     */
    YZYFS("BDC_ZD_YZYFS", BdcZdYzyfsDO.class,"养殖业方式",false),
    /**
     * 水域滩涂类型
     */
    SYTTLX("BDC_ZD_SYTTLX", BdcZdSyttlxDO.class,"水域滩涂类型",false),
    /**
     * 林种
     */
    LZ("BDC_ZD_LZ", BdcZdLzDO.class,"林种",false),
    /**
     * 抵押方式
     */
    DYFS("BDC_ZD_DYFS", BdcZdDyfsDO.class,"抵押方式",false),
    /**
     * 抵押不动产类型
     */
    DYBDCLX("BDC_ZD_DYBDCLX", BdcZdDybdclxDO.class,"抵押不动产类型",false),
    /**
     * 预告登记种类
     */
    YGDJZL("BDC_ZD_YGDJZL", BdcZdYgdjzlDO.class,"预告登记种类",false),
    /**
     * 权属状态
     */
    QSZT("BDC_ZD_QSZT", BdcZdQsztDO.class,"权属状态",false),
    /**
     * 查封类型
     */
    CFLX("BDC_ZD_CFLX", BdcZdCflxDO.class,"查封类型",false),
    /**
     * 权利类型
     */
    QLLX("BDC_ZD_QLLX", BdcZdQllxDO.class,"权利类型",false),
    /**
     * 权利性质
     */
    QLXZ("BDC_ZD_QLXZ", BdcZdQlxzDO.class,"权利性质",false),
    /**
     * 土地用途
     */
    TDYT("BDC_ZD_TDYT", BdcZdTdytDO.class,"土地用途",false),
    /**
     * 土地等级
     */
    DJ("BDC_ZD_TDDJ", BdcZdTddjDO.class, "土地等级",false),
    /**
     * 权利设定方式
     */
    QLSDFS("BDC_ZD_QLSDFS", BdcZdQlsdfsDO.class, "权利设定方式",false),
    /**
     * 不动产类型
     */
    BDCLX("BDC_ZD_BDCLX", BdcZdBdclxDO.class, "不动产类型",false),

    /**
     * 档案状态
     */
    DAZT("BDC_ZD_DAZT", BdcZdDaztDO.class, "档案状态",false),
    /**
     * 是否
     */
    SF("BDC_ZD_SF", BdcZdSfDO.class, "是否",false),
    /**
     * 不动产单元状态
     */
    BDCDYZT("BDC_ZD_BDCDYZT", BdcZdBdcdyztDO.class, "不动产单元状态",false),
    /**
     * 海域使用类型A表
     */
    HYSYLXA("BDC_ZD_HYSYLXA", BdcZdHysylxaDO.class,"海域使用类型A表",false),
    /**
     * 海域使用类型B表
     */
    HYSYLXB("BDC_ZD_HYSYLXB", BdcZdHysylxbDO.class,"海域使用类型B表",false),
    /**
     * 海域等别
     */
    HYDB("BDC_ZD_HYDB", BdcZdHydbDO.class,"海域等别",false),
    /**
     * 户型
     */
    HX("BDC_ZD_HX", BdcZdHxDO.class,"户型",false),
    /**
     * 户型结构
     */
    HXJG("BDC_ZD_HXJG", BdcZdHxjgDO.class,"户型结构",false),
    /**
     * 区县
     */
    QX("BDC_ZD_QX", BdcZdQxDO.class,"区县",true),
    /**
     * 案件状态
     */
    AJZT("BDC_ZD_AJZT", BdcZdAjztDO.class,"案件状态",false),
    /**
     * 付费方
     */
    FFF("BDC_ZD_FFF", BdcZdFffDO.class,"付费方",false),
    /**
     * 共有方式
     */
    GYFS("BDC_ZD_GYFS", BdcZdGyfsDO.class,"共有方式",false),
    /**
     * 审核意见操作结果
     */
    SHYJCZJG("BDC_ZD_SHYJCZJG", BdcZdShyjczjgDO.class,"审核意见操作结果",false),
    /**
     * 收费类型
     */
    SFLX("BDC_ZD_SFLX", BdcZdSflxDO.class,"收费类型",false),
    /**
     * 收件类型
     */
    SJLX("BDC_ZD_SJLX", BdcZdSjlxDO.class,"收件类型",false),
    /**
     * 证书版式
     */
    ZSBS("BDC_ZD_ZSBS", BdcZdZsbsDO.class,"证书版式",false),
    /**
     * 通知方式
     */
    TZFS("BDC_ZD_TZFS", BdcZdTzfsDO.class,"通知方式",true),
    /**
     * 性别
     */
    XB("BDC_ZD_XB", BdcZdXbDO.class,"性别",false),
    /**
     * 宗地宗海特征码
     */
    ZDZHTZM("BDC_ZD_ZDZHTZM", BdcZdZdzhtzmDO.class,"宗地宗海特征码",false),
    /**
     * 土地所有权性质
     */
    TDSYQXZ("BDC_ZD_TDSYQXZ", BdcZdTdsyqxzDO.class,"土地所有权性质",false),
    /**
     * 建筑物状态
     */
    JZWZT("BDC_ZD_JZWZT", BdcZdJzwztDO.class,"建筑物状态",false),
    /**
     * 项目性质
     */
    XMXZ("BDC_ZD_XMXZ", BdcZdXmxzDO.class,"项目性质",false),
    /**
     * 用海方式
     */
    YHFS("BDC_ZD_YHFS", BdcZdYhfsDO.class,"用海方式",false),
    /**
     * 起源
     */
    QY("BDC_ZD_QY", BdcZdQyDO.class,"起源",false),
    /**
     * 无居民海岛用途
     */
    WJMHDYT("BDC_ZD_WJMHDYT", BdcZdWjmhdytDO.class,"无居民海岛用途",false),
    /**
     * 项目状态
     */
    XMZT("BDC_ZD_XMZT", BdcZdXmztDO.class,"项目状态",false),
    /**
     * 项目来源
     */
    XMLY("BDC_ZD_XMLY", BdcZdXmlyDO.class,"项目来源",false),
    /**
     * 金额单位
     */
    JEDW("BDC_ZD_JEDW", BdcZdJedwDO.class,"金额单位",false),
    /**
     * 收费状态
     */
    SFZT("BDC_ZD_SFZT", BdcZdSfztDO.class,"收费状态",false),
    /**
     * 权利人类别
     */
    QLRLB("BDC_ZD_QLRLB", BdcZdQlrlbDO.class,"权利人类别",false),
    /**
     * 证书使用情况
     */
    ZSSYQK("BDC_ZD_ZSSYQK", BdcZdZssyqkDO.class,"证书使用情况",false),
    /**
     * 证书类型
     */
    ZSLX("BDC_ZD_ZSLX", BdcZdZslxDO.class,"证书类型",false),
    /**
     * 质检类型
     */
    ZJLX("BDC_ZD_ZJLX", BdcZdZjlxDO.class,"质检类型",false),
    /**
     * 异常办件原因
     */
    YCBJYY("BDC_ZD_YCBJYY", BdcZdYcbjyyDO.class,"异常办件原因",true),
    /**
     * 印制号作废原因
     */
    YZHZFYY("BDC_ZD_YZHZFYY", BdcZdYzhzfyyDO.class,"印制号作废原因",true),
    /**
     * 登记小类
     */
    DJXL("BDC_ZD_DJXL", BdcZdDjxlDO.class,"登记小类",false),
    /**
     * 缴费方式
     */
    JFFS("BDC_ZD_JFFS", BdcZdJffsDO.class,"缴费方式",false),
    /**
     * 不动产单元房屋类型
     */
    BDCDYFWLX("BDC_ZD_BDCDYFWLX", BdcZdBdcdyfwlxDO.class,"不动产单元房屋类型",false),
    /**
     * 权利人数据来源字典表
     */
    QLRSJLY("BDC_ZD_QLRSJLY", BdcZdQlrsjly.class,"权利人数据来源",false),
    /**
     * 权利数据来源字典表
     */
    QLSJLY("BDC_ZD_QLSJLY", BdcZdQlsjly.class,"权利数据来源",false),
    /**
     * 查询申请书，查询内容
     */
    CXNR("BDC_ZD_CXNR", BdcZdCxnrDO.class,"查询内容",true),
    /**
     * 查询申请书，查询申请材料
     */
    CXSQCL("BDC_ZD_CXSQCL", BdcZdCxsqclDO.class,"查询申请材料",true),
    /**
     * 交接单，交接单状态
     */
    JJDZT("BDC_ZD_JJDZT", BdcZdJjdztDO.class,"交接单状态",false),
    /**
     * 交接单，交接单类型
     */
    JJDLX("BDC_ZD_JJDLX", BdcZdJjdlxDO.class,"交接单类型",false),
    /**
     * 第三权利人类别字典
     */
    DSQLRLB("BDC_ZD_DSQLRLB", BdcZdDsqlrlbDO.class,"第三权利人类别",false),
    /**
     * 水电气业务办理状态字典
     */
    SDQBLZT("BDC_ZD_SDQBLZT", BdcZdSdqBlztDO.class,"水电气业务办理状态",false),
    /**
     * 水电气业务类型字典
     */
    SDQYWLX("BDC_ZD_SDQGHYWLX", BdcZdSdqYwlxDO.class,"水电气业务类型",false),

    /**
     * 特殊业务配置模块字典
     */
    TSYWPZMK("BDC_ZD_TSYWPZMK", BdcZdTsywPzMkDO.class,"特殊业务配置模块",true),
    /**
     * 特殊业务配置子系统字典
     */
    TSYWPZZXT("BDC_ZD_TSYWPZZXT", BdcZdTsywPzZxtDO.class,"特殊业务配置子系统",false),
    /**
     * 特殊业务配置类型字典
     */
    TSYWPZLX("BDC_ZD_TSYWPZLX", BdcZdTsywPzlxDO.class,"特殊业务配置类型",false),
    /**
     * 第三方共享部门标志字典表
     */
    GXBMBZ("BDC_ZD_GXBMBZ", BdcZdGxbmbzDO.class,"第三方共享部门标志",true),
    /**
     * 上报状态
     */
    SBZT("BDC_ZD_SBZT", BdcZdSbztDO.class,"上报状态",false),
    /**
     * 领证方式
     */
    LZFS("BDC_ZD_LZFS", BdcZdLzfsDO.class,"领证方式",false),
    /**
     * 查封文件
     */
    CFWJ("BDC_ZD_CFWJ", BdcZdCfwjDO.class,"查封文件",true),
    /**
     * 宗地使用类型
     */
    ZDSYLX("BDC_ZD_ZDSYLX", BdcZdZdsylxDO.class,"宗地使用类型",false),
    /**
     * 与户主关系
     */
    YHZGX("BDC_ZD_YHZGX", BdcZdYhzgxDO.class,"与户主关系",false),
    /**
     * 与申请人关系
     */
    YSQRGX("BDC_ZD_YSQRGX", BdcZdYsqrgxDO.class,"与申请人关系",false),
    /**
     * 承包地类等级
     */
    CBDLDJ("BDC_ZD_CBDLDJ", BdcZdCbdldj.class,"承包地类等级",false),
    /**
     * 承包土地用途
     */
    CBTDYT("BDC_ZD_CBTDYT", BdcZdCbtdyt.class,"承包土地用途",false),
    /**
     * 经营权承包方式
     */
    CBFS("BDC_ZD_CBFS", BdcZdCbfs.class,"经营权承包方式",false),
    /**
     * 机构类别
     */
    JGLB("BDC_ZD_JGLB", BdcZdJglbDO.class,"机构类别",false),

    /**
     * 地段级别
     */
    DDJB("BDC_ZD_DDJB", BdcZdDdjbDO.class,"地段级别",false),

    /**
     * 出具证明类型
     */
    CJZMLX("BDC_ZD_CJZMLX", BdcZdCjzmlxDO.class,"出具证明类型",false),

    /**
     * 取得方式
     */
    QDFS("BDC_ZD_QDFS", BdcZdQdfsDO.class,"取得方式",false),

    /**
     * 查档类型
     */
    CDLX("BDC_ZD_CDLX", BdcZdCdlxDO.class,"查档类型",false),

    /**
     * 币种
     */
    BIZ("BDC_ZD_BIZ", BdcZdBizDO.class,"币种",false),

    /**
     * 国籍
     */
    GJ("BDC_ZD_GJ", BdcZdGjDO.class,"国籍",false),

    /**
     * 中编办事业单位业务类型
     */
    ZBBSYDWYWLX("BDC_ZD_ZBBSYDWYWLX", BDCZdZbbsydwywlxDO.class,"中编办事业单位业务类型",false),

    /**
     * 金融许可证查询类型
     */
    JRXKZCXLX("BDC_ZD_JRXKZCXLX", BdcZdJrxkzcxlxDO.class,"金融许可证查询类型",false),

    /**
     * 审批来源类型
     */
    SPLY("BDC_ZD_SPLY", BdcZdSplyDO.class,"审批来源类型",false),

    /**
     * 发票类型
     */
    FPLX("BDC_ZD_FPLX", BdcZdFplxDO.class,"发票类型",false),

    /**
     * 收费信息备注
     */
    SFXXBZ("BDC_ZD_SFXXBZ", BdcZdSfxxbzDO.class,"收费信息备注",true),

    /**
     * 查询目的或用途
     */
    CXMDHYT("BDC_ZD_CXMDHYT", BdcZdCxmdhytDO.class,"查询目的或用途",true),

    /**
     * 登记簿记载信息
     */
    DJBJZXX("BDC_ZD_DJBJZXX", BdcZdDjbjzxxDO.class,"登记簿记载信息",true),

    /**
     * 登记原始凭证
     */
    DJYSPZ("BDC_ZD_DJYSPZ", BdcZdDjyspzDO.class,"登记原始凭证",true),

    /**
     * 查询结果要求
     */
    CXJGYQ("BDC_ZD_CXJGYQ", BdcZdCxjgyqDO.class,"查询结果要求",true),

    /**
     * 盐城征迁审核状态
     */
    ZQSHZT("BDC_ZD_ZQSHZT", BdcZdZqshztDO.class,"盐城征迁审核状态",false),

    /**
     * 盐城征迁审核阶段
     */
    ZQSHJD("BDC_ZD_ZQSHJD", BdcZdZqshjdDO.class,"盐城征迁审核阶段",false),

    /**
     * 盐城注销原因
     */
    ZQZXYY("BDC_ZD_ZQZXYY", BdcZdZqzxyyDO.class,"注销原因",true),
    /**
     * 工程进度
     */
    GCJD("BDC_ZD_GCJD", BdcZdGcjdDO.class,"工程进度",false),
    /**
     * 贷款方式
     */
    DKFS("BDC_ZD_DKFS", BdcZdDkfsDO.class,"贷款方式",false),
    /**
     * 修改原因
     */
    XGYY("BDC_ZD_XGYY", BdcZdXgyyDO.class,"修改原因",true),

    /**
     * 预约分中心
     */
    YYFZX("BDC_ZD_YYFZX", BdcZdYyfzxDO.class,"预约分中心",true),

    /**
     * 公告类型
     */
    GGLX("BDC_ZD_GGLX", BdcZdGglxDO.class,"公告类型",false),


    /**
     * 补录方式
     */
    BLFS("BDC_ZD_BLFS", BdcZdBlfsDO.class, "补录方式",false),
    /**
     * 权利人来源
     */
    QLRLY("BDC_ZD_QLRLY", BdcZdQlrlyDO.class, "权利人来源",false),
    /**
     * 权籍管理代码
     */
    QJGLDM("BDC_ZD_QJGLDM", BdcZdQjgldmDO.class, "权籍管理代码",true),
    /*
     * 房屋建设状态字典表
     */
    FWJSZT("BDC_ZD_FWJSZT", BdcZdFwjsztDO.class, "房屋建设状态",false),
    /**
     * 资料核实字典表
     */
    ZLHS("BDC_ZD_ZLHS", BdcZdZlhsDO.class, "资料核实",false),
    /*
     * 公告情况字典表
     */
    GGQK("BDC_ZD_GGQK", BdcZdGgqkDO.class, "公告情况",true),
    /*
     * 解封文件字典表
     * */
    JFWJ("BDC_ZD_JFWJ", BdcZdJfwjDO.class, "解封文件",true),
    /*
     * 产权来源字典表
     */
    CQLY("BDC_ZD_CQLY", BdcZdCqlyDO.class, "产权来源",false),
    /*
     * 权利人特征字典表
     */
    QLRTZ("BDC_ZD_QLRTZ", BdcZdQlrtz.class, "权利人特征",false),
    /*
     * 更正登记类型字典表
     */
    GZDJLX("BDC_ZD_GZDJLX", BdcZdGzdjlxDO.class, "更正登记类型",false),
    /*
     * 用地用海分类字典表
     */
    YDYHFL("BDC_ZD_YDYHFL", BdcZdYdyhflDO.class, "用地用海分类",false),
    /*
     * 抵押金额类型字典表
     */
    DYJELX("BDC_ZD_DYJELX", BdcZdDyjelxDO.class, "抵押金额类型",false),
    /*
     * 森林类别字典表
     */
    SLLB("BDC_ZD_SLLB", BdcZdSllbDO.class, "森林类别",false),
    /*
     * 电子签章推送状态字典表
     */
    DZQZTSZT("BDC_ZD_DZQZTSZT", BdcZdDzqzTsztDO.class, "电子签章推送状态",false),
    /*
     * 锁定类型字典表
     */
    SDLX("BDC_ZD_SDLX", BdcSdlxDO.class, "不动产锁定类型",false),
    /**
     * 委托书字典表
     */
    WTS("BDC_ZD_WTS",BdcWtsZtDO.class,"不动产委托书状态",false),
    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流事件类型
     * @date : 2022/3/24 14:51
     */
    GZLSJLX("BDC_ZD_GZLSJLX", BdcZdGzlsjlxDO.class, "工作流事件类型",false),
    /*
     * 问题数据类别
     */
    WTSJLB("BDC_ZD_WTSJLB", BdcZdWtsjlbDO.class, "问题数据类别",false),
    /*
     * 问题数据状态
     */
    WTSJZT("BDC_ZD_WTSJZT", BdcZdWtsjztDO.class, "问题数据类别",false),
    /**
     * 响应信息字典表
     */
    XYXX("BDC_ZD_XYXX", BdcZdXyxxDO.class, "不动产响应信息", false),
    /**
     * 黑名单类别字典表
     */
    HMDLB("BDC_ZD_HMDLB", BdcZdHmdlbDO.class, "黑名单类别", false),
    /**
     * 日志操作状态
     */
    CZRZCZZT("BDC_ZD_CZRZCZZT", BdcZdCzrzczztDO.class, "日志操作状态", false),

    /**
     * 接口名称
     */
    GXJKMC("BDC_ZD_GXJKMC", BdcZdGxjkmcDO.class,"第三方接口名称",true),

    /**
     * 落宗状态
     */
    LZZT("BDC_ZD_LZZT", BdcZdLzztDO.class, "落宗状态", true),

    /**
     * 匹配状态
     */
    PPZT("BDC_ZD_PPZT", BdcZdPpztDO.class, "匹配状态", true),
    /**
     * 日志操作类型
     */
    CZRZCZLX("BDC_ZD_CZRZCZLX", BdcZdCzrzczlxDO.class, "日志操作类型", false),

    /*
     * 销账状态
     * */
    XZZT("BDC_ZD_XZZT", BdcZdXzztDO.class, "销账状态", true),


    /*
     * 销账报文类型
     * */
    XZBWLX("BDC_ZD_XZBWLX", BdcZdXzbwlxDO.class, "销账报文类型", true),

    /*
     * 更正登记-更正依据
     * */
    GZDJGZYJ("BDC_ZD_GZDJGZYJ", BdcZdGzdjgzyjDO.class, "更正依据", true),

    /*
     * 一体化邮寄领证人
     * */
    YTHYJLZR("BDC_ZD_YTHYJLZR", BdcZdYthyjlzrDO.class, "一体化邮寄领证人", true),
    /*
     * 历史遗留问题类型
     * */
    LSYLWTLX("BDC_ZD_LSYLWTLX", BdcZdLsylwtlxDO.class, "历史遗留问题类型", true),
    /*
     * 接入业务字典表
     * */
    JRYW("BDC_ZD_JRYW", BdcZdJrywDO.class, "接入业务字典表", true),

    /*
     * 接入业务服务字典白
     * */
    JRYWFW("BDC_ZD_JRYWFW", BdcZdJrYwFwDO.class, "接入业务服务字典表", true),

    /*
     * 接入标识字典表
     * */
    JRBS("BDC_ZD_JRBS", BdcZdJrbsDO.class, "接入标识字典表", true),

    /*
     * 是否集体经济组织成员
     * */
    SFJTJJZZCY("BDC_ZD_SFJTJJZZCY", BdcZdSfjtjjzzcyDO.class, "是否集体经济组织成员", true),

    /*
     * 一体化审批业务类型
     * */
    YTHSPYWLX("BDC_ZD_YTHSPYWLX", BdcZdYthspywlxDO.class, "一体化审批业务类型", true),

    /*
     * 一体化审批业务类型
     * */
    FYWPZ("BDC_ZD_TSYW_FYWPZ", BdcZdTsywFywpzDO.class, "非业务配置项", true),

    /**
     * ES日志字段名称字典表
     */
    ESZDMC("BDC_ZD_ESZDMC", BdcZdEszdmcDO.class, "ES日志字段名称字典表", true),

    /**
     * 例外审核状态字典表
     */
    LWSHZT("BDC_ZD_LWSHZT", BdcZdLwshztDO.class, "例外审核状态字典表", true),

    /**
     * 初始化时区县代码与登记机构对照
     */
    CSHDJBMC("BDC_ZD_QXDMDJBMC", BdcQxdmZddjbmc.class, "初始化时区县代码与登记机构对照", true),

    /**
     * 有房无房查询范围字典表
     */
    YFWFCXFW("BDC_ZD_YFWFCXFW", BdcZdYfwfcxfwDO.class, "有房无房查询范围字典表", true),

    /*
     * 销账比对类型
     * */
    XZBDLX("BDC_ZD_XZBDLX", BdcZdXzBdlxDO.class, "销账比对类型字典表", true),

    /*
     * 共享查询的日志名称
     * */
    GXCXJKMC("BDC_ZD_GXCXJKMC", BdcZdGxcxjkmcDO.class, "共享查询接口名称", true),

    /**
     * 业务类型
     */
    YWLX("BDC_ZD_YWLX", BdcZdYwlxDO.class, "业务类型", true),

    /**
     * 设立情形
     */
    SLQX("BDC_ZD_SLQX", BdcZdSlqxDO.class, "设立情形", true)



    ;


    /**
     * 表名
     */
    private String tableName;
    /**
     * 表对应实体
     */
    private Class tableClass;

    /**
     * 表对应名称
     */
    private String mc;

    /**
     * 是否可编辑
     */
    private Boolean editable;

    BdcZdEnum(String tableName, Class tableClass, String mc, Boolean editable) {
        this.tableName = tableName;
        this.tableClass = tableClass;
        this.mc = mc;
        this.editable = editable;
    }

    /**
     * @param name 名字
     * @return BdcZfxxCxlyEnum
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据名字查询BdcZdEnum
     */
    public static BdcZdEnum getBdcZdEnumByName(String name) {
        for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
            if (StringUtils.equalsIgnoreCase(bdcZdEnum.name(), name)) {
                return bdcZdEnum;
            }
        }
        return null;
    }

    /**
     * @param tableName 名字
     * @return BdcZfxxCxlyEnum
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据表名查询BdcZdEnum
     */
    public static BdcZdEnum getBdcZdEnumByTableName(String tableName) {
        for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
            if (StringUtils.equalsIgnoreCase(bdcZdEnum.getTableName(), tableName)) {
                return bdcZdEnum;
            }
        }
        return null;
    }

    /**
     * @param bdcZdEnum 不动产字典缓存枚举
     * @return BdcZfxxCxlyEnum
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据表名查询BdcZdEnum
     */
    public static JSONObject toJsonObjByBdcZdEnum(BdcZdEnum bdcZdEnum) {
        JSONObject jsonObject = new JSONObject();
        if (bdcZdEnum != null) {
            jsonObject.put("name", bdcZdEnum.name());
            jsonObject.put("tableName", bdcZdEnum.getTableName());
            jsonObject.put("tableClass", bdcZdEnum.getTableClass());
            jsonObject.put("mc", bdcZdEnum.getMc());
            jsonObject.put("editable", bdcZdEnum.getEditable());
        }
        return jsonObject;
    }


    public String getTableName() {
        return tableName;
    }

    public Class getTableClass() {
        return tableClass;
    }

    public String getMc() {
        return mc;
    }

    public Boolean getEditable() {
        return editable;
    }
}
