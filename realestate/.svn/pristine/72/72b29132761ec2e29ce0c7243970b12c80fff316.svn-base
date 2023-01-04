package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.register.BdcCflxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbDyxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbXzxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbZsdjxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.sdk.mybatis.plugin.annotation.Crypt;
import cn.gtmap.sdk.mybatis.plugin.annotation.VersionDecryptKeys;
import cn.gtmap.sdk.mybatis.plugin.annotation.VersionEncryptKeys;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/28
 * @description 不动产查封
 */
public interface BdcCfMapper {
    /**
     * @param bdcGyqkDTO 更新参数
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 权利共有情况批量更新
     */
    int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 执行数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新当前权利的登簿信息和权属状态
     */
    int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新上原权利的权属状态和注销登簿信息
     */
    int udpateBdcQlZxDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param bdcCfjgQO 查封机关QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新查封机关和被执行人
     */
    int updateCfjgAndBzxr(BdcCfjgQO bdcCfjgQO);

    /**
     * @param bdcCfjgQO 查封机关QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新解封机关信息
     */
    int updateJfjg(BdcCfjgQO bdcCfjgQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的权利数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新查封信息的权属状态
     */
    int udpateBdcCfQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利登簿人
     */
    int updateBdcQlDbrPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新注销权利登簿人
     */
    int updateZxQlDbrPl(DbxxQO dbxxQO);

    /**
     * @param bdcCfQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/8/19 16:56
     */
    @Crypt(
            encryptKeys = "zl",
            versionEnCryptKeys = "{\"hefei\": \"zl\"}",
            encryptKey = {
                    @VersionEncryptKeys(version = "standard",encryptKeys = {"bdcdyh"}),
                    @VersionEncryptKeys(version = "hefei",encryptKeys = {"ywrmc"})}
    )
    List<BdcCfDO> listBdcCfxx(BdcCfQO bdcCfQO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量更新查封信息的查封类型
      */
    int updateCflxPl(BdcCflxDTO bdcCflxDTO);


    @Crypt(
            encryptKeys = "zl",
            versionEnCryptKeys = "{\"hefei\": \"zl\"}",
            encryptKey = {
                    @VersionEncryptKeys(version = "standard",encryptKeys = {"bdcdyh"}),
                    @VersionEncryptKeys(version = "hefei",encryptKeys = {"ywrmc"})},
            decryptKeys = "cfwh",
            //versionDecryptKeys = "{\"hefei\": \"jfywh\"}",
            decryptKey = {
                    @VersionDecryptKeys(version = "standard",decryptKeys = {"jfywh"}),
                    @VersionDecryptKeys(version = "hefei",decryptKeys = {"jfjg"})
            }
    )
    List<Map<String,Object>> getCfByXmid(BdcCfQO bdcCfQO);
}
