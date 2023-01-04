package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response;

/**
 * @description 权属状态信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 10:53
 */
public class BdcQsztResponseDTO {

    /**
     * 是否存在所有权转移
     */
    private String ownershiptransfer;

    /**
     * 是否存在预抵押
     */
    private String heraldDebt;

    /**
     * 是否存在抵押
     */
    private String debt;

    /**
     * 是否存在查封
     */
    private String seal;

    /**
     * 是否存在预告
     */
    private String herald;

    /**
     * 是否存在异议
     */
    private String dissent;

    /**
     * 是否存在锁定
     */
    private String lock;

    /**
     * 是否存在居住权
     */
    private String live;

    /**
     * 是否存在办理过程中业务
     */
    private String isHandling;

    public String getOwnershiptransfer() {
        return ownershiptransfer;
    }

    public void setOwnershiptransfer(String ownershiptransfer) {
        this.ownershiptransfer = ownershiptransfer;
    }

    public String getHeraldDebt() {
        return heraldDebt;
    }

    public void setHeraldDebt(String heraldDebt) {
        this.heraldDebt = heraldDebt;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public String getHerald() {
        return herald;
    }

    public void setHerald(String herald) {
        this.herald = herald;
    }

    public String getDissent() {
        return dissent;
    }

    public void setDissent(String dissent) {
        this.dissent = dissent;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getIsHandling() {
        return isHandling;
    }

    public void setIsHandling(String isHandling) {
        this.isHandling = isHandling;
    }

    @Override
    public String toString() {
        return "BdcQsztResponseDTO{" +
                "ownershiptransfer='" + ownershiptransfer + '\'' +
                ", heraldDebt='" + heraldDebt + '\'' +
                ", debt='" + debt + '\'' +
                ", seal='" + seal + '\'' +
                ", herald='" + herald + '\'' +
                ", dissent='" + dissent + '\'' +
                ", lock='" + lock + '\'' +
                ", live='" + live + '\'' +
                ", isHandling='" + isHandling + '\'' +
                '}';
    }
}
