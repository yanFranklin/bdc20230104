package cn.gtmap.realestate.common.util;


import cn.gtmap.realestate.common.core.domain.register.BdcBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/2/24
 * @description 不动产单元状态
 */
public class BdcdyztToolUtils {

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  获取登记状态
      */
    public static boolean getDjzt(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null
                && StringUtils.equals("1", sSjBdcdyhxsztDO.getDjzt())) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取注销状态
     */
    public static boolean getZxzt(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null
                && StringUtils.equals("2", sSjBdcdyhxsztDO.getDjzt())) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取在建工程抵押
     */
    public static boolean getZjgcdy(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXszjgcdycs() != null &&sSjBdcdyhxsztDO.getXszjgcdycs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取预告
     */
    public static boolean getYg(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsygcs() != null &&sSjBdcdyhxsztDO.getXsygcs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取预抵押
     */
    public static boolean getYdya(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsydyacs() != null &&sSjBdcdyhxsztDO.getXsydyacs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取抵押
     */
    public static boolean getDya(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsdyacs() != null &&sSjBdcdyhxsztDO.getXsdyacs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取预查封
     */
    public static boolean getYcf(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsycfcs() != null &&sSjBdcdyhxsztDO.getXsycfcs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取查封
     */
    public static boolean getCf(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXscfcs() != null &&sSjBdcdyhxsztDO.getXscfcs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取异议
     */
    public static boolean getYy(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsyycs() != null &&sSjBdcdyhxsztDO.getXsyycs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取地役
     */
    public static boolean getDyi(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsdyics() !=null &&sSjBdcdyhxsztDO.getXsdyics() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取锁定
     */
    public static boolean getSd(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXssdcs() != null &&sSjBdcdyhxsztDO.getXssdcs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取可售
     */
    public static boolean getKs(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if(sSjBdcdyhxsztDO != null){
            // ks ys是00,10,11,12，代表不可售，可售，已售，草签
            Integer ks = sSjBdcdyhxsztDO.getKs() != null?sSjBdcdyhxsztDO.getKs():0;
            Integer ys = sSjBdcdyhxsztDO.getYs() != null?sSjBdcdyhxsztDO.getYs():0;
            // 根据 YS字段判断是否草签   2为 草签
            if (ks == 1 && ys == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取预售
     */
    public static boolean getYs(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if(sSjBdcdyhxsztDO != null){
            // ks ys是00,10,11,12，代表不可售，可售，已售，草签
            Integer ks = sSjBdcdyhxsztDO.getKs() != null?sSjBdcdyhxsztDO.getKs():0;
            Integer ys = sSjBdcdyhxsztDO.getYs() != null?sSjBdcdyhxsztDO.getYs():0;
            // 根据 YS字段判断是否草签   2为 草签
            if (ks == 1 && ys == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取新建商品房可售
     */
    public static boolean getXjspfks(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXjspfks() != null &&sSjBdcdyhxsztDO.getXjspfks() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取新建商品房已售
     */
    public static boolean getXjspfys(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXjspfys() != null &&sSjBdcdyhxsztDO.getXjspfys() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取存量房可售
     */
    public static boolean getClfks(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getClfks() != null &&sSjBdcdyhxsztDO.getClfks() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取存量房已售
     */
    public static boolean getClfys(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getClfys() != null &&sSjBdcdyhxsztDO.getClfys() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取居住权
     */
    public static boolean getJzq(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getXsjzqcs() != null &&sSjBdcdyhxsztDO.getXsjzqcs() >0) {
            return true;
        }
        return false;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  林权流转状态
     */
    public static boolean getLqlzzt(BdcBdcdyhxsztDO sSjBdcdyhxsztDO) {
        if (sSjBdcdyhxsztDO != null &&sSjBdcdyhxsztDO.getLqlzzt() != null &&CommonConstantUtils.SF_S_DM.equals(sSjBdcdyhxsztDO.getLqlzzt())) {
            return true;
        }
        return false;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 转换状态实体
      */
    public static BdcdyhZtResponseDTO revertDTO(BdcBdcdyhxsztDO sSjBdcdyhxsztDO){
        BdcdyhZtResponseDTO bdcdyhZtResponseDTO =new BdcdyhZtResponseDTO();
        bdcdyhZtResponseDTO.setDj(getDjzt(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setZx(getZxzt(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setZjgcdy(getZjgcdy(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setYg(getYg(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setYdya(getYdya(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setDya(getDya(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setYcf(getYcf(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setCf(getCf(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setYy(getYy(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setDyi(getDyi(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setSd(getSd(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setKs(getKs(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setYs(getYs(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setXjspfks(getXjspfks(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setXjspfys(getXjspfys(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setClfks(getClfks(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setClfys(getClfys(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setJzq(getJzq(sSjBdcdyhxsztDO));
        bdcdyhZtResponseDTO.setLqlzzt(getLqlzzt(sSjBdcdyhxsztDO));
        return bdcdyhZtResponseDTO;

    }
}
