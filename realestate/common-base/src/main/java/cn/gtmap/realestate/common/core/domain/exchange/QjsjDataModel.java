package cn.gtmap.realestate.common.core.domain.exchange;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by zdd on 2015/11/19.
 */
@XmlType(name = "dataModel", propOrder = {"zttGyQlrList", "zttGyJtcyDOList", "kttZdjbxxList", "ktfZdbhqkList", "kttZhjbxxList", "ktfZhYhzkList",
        "ktfZhYhydzbList", "ktfZhbhqkList", "kttFwZrzList", "kttGzwList", "kttGyJzxList", "kttGyJzdList", "kttFwLjzList",
        "kttFwCList", "kttFwHList", "zdKList", "zhkList", "qlfQlTdsyqList", "qlfQlJsydsyqList", "qltFwFdcqDzList",
        "qlfFwFdcqDzXmList", "qltFwFdcqYzList", "qlfFwFdcqQfsyqList", "qlfQlHysyqList", "qltQlGjzwsyqList",
        "qlfQlNydsyqList", "qltQlLqList", "qlfQlDyiqList", "qlfQlYgdjList", "qlfQlDyaqList", "qlfQlYydjList", "qlfQlCfdjList",
        "qlfQlZxdjList", "qlfQlGzdjDOList", "djfDjSqrList", "djtDjSlsqList", "djfDjSjList", "djfDjSfList", "djfDjShList", "djfDjDbList", "djfDjFzList",
        "djfDjGdList", "fjFList",
        "ktfQtDzdzwList", "ktfQtMzdzwList", "ktfQtXzdzwList", "qlfQlQtxgqlList", "ztfGyQlQlrGxList"})


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "Data")
public class QjsjDataModel implements Serializable {

    private List<DjfDjFzDO> djfDjFzList = Collections.singletonList(new DjfDjFzDO());
    private List<DjfDjGdDO> djfDjGdList = Collections.singletonList(new DjfDjGdDO());
    private List<DjfDjSfDO> djfDjSfList = Collections.singletonList(new DjfDjSfDO());
    private List<DjfDjShDO> djfDjShList = Collections.singletonList(new DjfDjShDO());
    private List<DjfDjSjDO> djfDjSjList = Collections.singletonList(new DjfDjSjDO());
    private List<DjfDjSqrDO> djfDjSqrList = Collections.singletonList(new DjfDjSqrDO());
    private List<DjtDjSlsqDO> djtDjSlsqList = Collections.singletonList(new DjtDjSlsqDO());
    private List<FjFDO> fjFList = Collections.singletonList(new FjFDO());
    private List<KtfQtDzdzwDO> ktfQtDzdzwList = Collections.singletonList(new KtfQtDzdzwDO());
    private List<KtfQtMzdzwDO> ktfQtMzdzwList = Collections.singletonList(new KtfQtMzdzwDO());
    private List<KtfQtXzdzwDO> ktfQtXzdzwList = Collections.singletonList(new KtfQtXzdzwDO());
    private List<KtfZdbhqkDO> ktfZdbhqkList = Collections.singletonList(new KtfZdbhqkDO());
    private List<KtfZhbhqkDO> ktfZhbhqkList = Collections.singletonList(new KtfZhbhqkDO());
    private List<KtfZhYhydzbDO> ktfZhYhydzbList = Collections.singletonList(new KtfZhYhydzbDO());
    private List<KtfZhYhzkDO> ktfZhYhzkList = Collections.singletonList(new KtfZhYhzkDO());
    private List<KttFwCDO> kttFwCList = Collections.singletonList(new KttFwCDO());
    private List<KttFwHDO> kttFwHList = Collections.singletonList(new KttFwHDO());
    private List<KttFwLjzDO> kttFwLjzList = Collections.singletonList(new KttFwLjzDO());
    private List<KttFwZrzDO> kttFwZrzList = Collections.singletonList(new KttFwZrzDO());
    private List<KttGyJzdDO> kttGyJzdList = Collections.singletonList(new KttGyJzdDO());
    private List<KttGyJzxDO> kttGyJzxList = Collections.singletonList(new KttGyJzxDO());
    private List<KttGzwDO> kttGzwList = Collections.singletonList(new KttGzwDO());
    private List<KttZdjbxxDO> kttZdjbxxList = Collections.singletonList(new KttZdjbxxDO());
    private List<KttZhjbxxDO> kttZhjbxxList = Collections.singletonList(new KttZhjbxxDO());
    private List<QlfFwFdcqDzXmDO> qlfFwFdcqDzXmList = Collections.singletonList(new QlfFwFdcqDzXmDO());
    private List<QlfFwFdcqQfsyqDO> qlfFwFdcqQfsyqList = Collections.singletonList(new QlfFwFdcqQfsyqDO());
    private List<QlfQlCfdjDO> qlfQlCfdjList = Collections.singletonList(new QlfQlCfdjDO());
    private List<QlfQlDyaqDO> qlfQlDyaqList = Collections.singletonList(new QlfQlDyaqDO());
    private List<QlfQlDyiqDO> qlfQlDyiqList = Collections.singletonList(new QlfQlDyiqDO());
    private List<QlfQlHysyqDO> qlfQlHysyqList = Collections.singletonList(new QlfQlHysyqDO());
    private List<QlfQlJsydsyqDO> qlfQlJsydsyqList = Collections.singletonList(new QlfQlJsydsyqDO());
    private List<QlfQlNydsyqDO> qlfQlNydsyqList = Collections.singletonList(new QlfQlNydsyqDO());
    private List<QlfQlQtxgqlDO> qlfQlQtxgqlList = Collections.singletonList(new QlfQlQtxgqlDO());
    private List<QlfQlTdsyqDO> qlfQlTdsyqList = Collections.singletonList(new QlfQlTdsyqDO());
    private List<QlfQlYgdjDO> qlfQlYgdjList = Collections.singletonList(new QlfQlYgdjDO());
    private List<QlfQlYydjDO> qlfQlYydjList = Collections.singletonList(new QlfQlYydjDO());
    private List<QlfQlZxdjDO> qlfQlZxdjList = Collections.singletonList(new QlfQlZxdjDO());
    private List<QltFwFdcqDzDO> qltFwFdcqDzList = Collections.singletonList(new QltFwFdcqDzDO());
    private List<QltFwFdcqYzDO> qltFwFdcqYzList = Collections.singletonList(new QltFwFdcqYzDO());
    private List<QltQlGjzwsyqDO> qltQlGjzwsyqList = Collections.singletonList(new QltQlGjzwsyqDO());
    private List<QltQlLqDO> qltQlLqList = Collections.singletonList(new QltQlLqDO());
    private List<QlfQlDyaqDywqdDO> qlfQlDyaqDywqd = Collections.singletonList(new QlfQlDyaqDywqdDO());
    private List<ZdKDO> zdKList = Collections.singletonList(new ZdKDO());
    private List<ZhKDO> zhkList = Collections.singletonList(new ZhKDO());
    private List<ZtfGyQlQlrGxDO> ztfGyQlQlrGxList = Collections.singletonList(new ZtfGyQlQlrGxDO());
    private List<ZttGyQlrDO> zttGyQlrList = Collections.singletonList(new ZttGyQlrDO());
    private List<ZttGyJtcyDO> zttGyJtcyDOList = Collections.singletonList(new ZttGyJtcyDO());
    private List<QlfQlGzdjDO> qlfQlGzdjDOList = Collections.singletonList(new QlfQlGzdjDO());
    private List<DjfDjDb> djfDjDbList = Collections.singletonList(new DjfDjDb());


    @XmlElement(name = "DJF_DJ_FZ")
    public List<DjfDjFzDO> getDjfDjFzList() {
        return djfDjFzList;
    }

    public void setDjfDjFzList(List<DjfDjFzDO> djfDjFzList) {
        this.djfDjFzList = djfDjFzList;
    }

    @XmlElement(name = "DJF_DJ_GD")
    public List<DjfDjGdDO> getDjfDjGdList() {
        return djfDjGdList;
    }

    public void setDjfDjGdList(List<DjfDjGdDO> djfDjGdList) {
        this.djfDjGdList = djfDjGdList;
    }

    @XmlElement(name = "DJF_DJ_SF")
    public List<DjfDjSfDO> getDjfDjSfList() {
        return djfDjSfList;
    }

    public void setDjfDjSfList(List<DjfDjSfDO> djfDjSfList) {
        this.djfDjSfList = djfDjSfList;
    }

    @XmlElement(name = "DJF_DJ_SH")
    public List<DjfDjShDO> getDjfDjShList() {
        return djfDjShList;
    }

    public void setDjfDjShList(List<DjfDjShDO> djfDjShList) {
        this.djfDjShList = djfDjShList;
    }

    @XmlElement(name = "DJF_DJ_SJ")
    public List<DjfDjSjDO> getDjfDjSjList() {
        return djfDjSjList;
    }

    public void setDjfDjSjList(List<DjfDjSjDO> djfDjSjList) {
        this.djfDjSjList = djfDjSjList;
    }

    @XmlElement(name = "DJF_DJ_SQ")
    public List<DjfDjSqrDO> getDjfDjSqrList() {
        return djfDjSqrList;
    }

    public void setDjfDjSqrList(List<DjfDjSqrDO> djfDjSqrList) {
        this.djfDjSqrList = djfDjSqrList;
    }


    @XmlElement(name = "DJT_DJ_SL")
    public List<DjtDjSlsqDO> getDjtDjSlsqList() {
        return djtDjSlsqList;
    }

    public void setDjtDjSlsqList(List<DjtDjSlsqDO> djtDjSlsqList) {
        this.djtDjSlsqList = djtDjSlsqList;
    }

    @XmlElement(name = "FJ_F_100")
    public List<FjFDO> getFjFList() {
        return fjFList;
    }

    public void setFjFList(List<FjFDO> fjFList) {
        this.fjFList = fjFList;
    }

    public List<KtfQtDzdzwDO> getKtfQtDzdzwList() {
        return ktfQtDzdzwList;
    }

    public void setKtfQtDzdzwList(List<KtfQtDzdzwDO> ktfQtDzdzwList) {
        this.ktfQtDzdzwList = ktfQtDzdzwList;
    }

    public List<KtfQtMzdzwDO> getKtfQtMzdzwList() {
        return ktfQtMzdzwList;
    }

    public void setKtfQtMzdzwList(List<KtfQtMzdzwDO> ktfQtMzdzwList) {
        this.ktfQtMzdzwList = ktfQtMzdzwList;
    }

    public List<KtfQtXzdzwDO> getKtfQtXzdzwList() {
        return ktfQtXzdzwList;
    }

    public void setKtfQtXzdzwList(List<KtfQtXzdzwDO> ktfQtXzdzwList) {
        this.ktfQtXzdzwList = ktfQtXzdzwList;
    }

    @XmlElement(name = "KTF_ZDBHQK")
    public List<KtfZdbhqkDO> getKtfZdbhqkList() {
        return ktfZdbhqkList;
    }

    public void setKtfZdbhqkList(List<KtfZdbhqkDO> ktfZdbhqkList) {
        this.ktfZdbhqkList = ktfZdbhqkList;
    }

    @XmlElement(name = "KTF_ZHBHQK")
    public List<KtfZhbhqkDO> getKtfZhbhqkList() {
        return ktfZhbhqkList;
    }

    public void setKtfZhbhqkList(List<KtfZhbhqkDO> ktfZhbhqkList) {
        this.ktfZhbhqkList = ktfZhbhqkList;
    }

    @XmlElement(name = "KTF_ZH_YHYDZB")
    public List<KtfZhYhydzbDO> getKtfZhYhydzbList() {
        return ktfZhYhydzbList;
    }

    public void setKtfZhYhydzbList(List<KtfZhYhydzbDO> ktfZhYhydzbList) {
        this.ktfZhYhydzbList = ktfZhYhydzbList;
    }

    @XmlElement(name = "KTF_ZH_YHZK")
    public List<KtfZhYhzkDO> getKtfZhYhzkList() {
        return ktfZhYhzkList;
    }

    public void setKtfZhYhzkList(List<KtfZhYhzkDO> ktfZhYhzkList) {
        this.ktfZhYhzkList = ktfZhYhzkList;
    }

    @XmlElement(name = "KTT_FW_C")
    public List<KttFwCDO> getKttFwCList() {
        return kttFwCList;
    }

    public void setKttFwCList(List<KttFwCDO> kttFwCList) {
        this.kttFwCList = kttFwCList;
    }

    @XmlElement(name = "KTT_FW_H")
    public List<KttFwHDO> getKttFwHList() {
        return kttFwHList;
    }

    public void setKttFwHList(List<KttFwHDO> kttFwHList) {
        this.kttFwHList = kttFwHList;
    }

    @XmlElement(name = "KTT_FW_LJZ")
    public List<KttFwLjzDO> getKttFwLjzList() {
        return kttFwLjzList;
    }

    public void setKttFwLjzList(List<KttFwLjzDO> kttFwLjzList) {
        this.kttFwLjzList = kttFwLjzList;
    }

    @XmlElement(name = "KTT_FW_ZRZ")
    public List<KttFwZrzDO> getKttFwZrzList() {
        return kttFwZrzList;
    }

    public void setKttFwZrzList(List<KttFwZrzDO> kttFwZrzList) {
        this.kttFwZrzList = kttFwZrzList;
    }

    @XmlElement(name = "KTT_GY_JZD")
    public List<KttGyJzdDO> getKttGyJzdList() {
        return kttGyJzdList;
    }

    public void setKttGyJzdList(List<KttGyJzdDO> kttGyJzdList) {
        this.kttGyJzdList = kttGyJzdList;
    }

    @XmlElement(name = "KTT_GY_JZX")
    public List<KttGyJzxDO> getKttGyJzxList() {
        return kttGyJzxList;
    }

    public void setKttGyJzxList(List<KttGyJzxDO> kttGyJzxList) {
        this.kttGyJzxList = kttGyJzxList;
    }

    @XmlElement(name = "KTT_GZW")
    public List<KttGzwDO> getKttGzwList() {
        return kttGzwList;
    }

    public void setKttGzwList(List<KttGzwDO> kttGzwList) {
        this.kttGzwList = kttGzwList;
    }

    @XmlElement(name = "KTT_ZDJBXX")
    public List<KttZdjbxxDO> getKttZdjbxxList() {
        return kttZdjbxxList;
    }

    public void setKttZdjbxxList(List<KttZdjbxxDO> kttZdjbxxList) {
        this.kttZdjbxxList = kttZdjbxxList;
    }

    @XmlElement(name = "KTT_ZHJBXX")
    public List<KttZhjbxxDO> getKttZhjbxxList() {
        return kttZhjbxxList;
    }

    public void setKttZhjbxxList(List<KttZhjbxxDO> kttZhjbxxList) {
        this.kttZhjbxxList = kttZhjbxxList;
    }

    @XmlElement(name = "QLF_FW_FDCQ_DZ_XM")
    public List<QlfFwFdcqDzXmDO> getQlfFwFdcqDzXmList() {
        return qlfFwFdcqDzXmList;
    }

    public void setQlfFwFdcqDzXmList(List<QlfFwFdcqDzXmDO> qlfFwFdcqDzXmList) {
        this.qlfFwFdcqDzXmList = qlfFwFdcqDzXmList;
    }

    @XmlElement(name = "QLF_FW_FDCQ_QFSYQ")
    public List<QlfFwFdcqQfsyqDO> getQlfFwFdcqQfsyqList() {
        return qlfFwFdcqQfsyqList;
    }

    public void setQlfFwFdcqQfsyqList(List<QlfFwFdcqQfsyqDO> qlfFwFdcqQfsyqList) {
        this.qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqList;
    }

    @XmlElement(name = "QLF_QL_CFDJ")
    public List<QlfQlCfdjDO> getQlfQlCfdjList() {
        return qlfQlCfdjList;
    }

    public void setQlfQlCfdjList(List<QlfQlCfdjDO> qlfQlCfdjList) {
        this.qlfQlCfdjList = qlfQlCfdjList;
    }

    @XmlElement(name = "QLF_QL_DYAQ")
    public List<QlfQlDyaqDO> getQlfQlDyaqList() {
        return qlfQlDyaqList;
    }

    public void setQlfQlDyaqList(List<QlfQlDyaqDO> qlfQlDyaqList) {
        this.qlfQlDyaqList = qlfQlDyaqList;
    }

    @XmlElement(name = "QLF_QL_DYIQ")
    public List<QlfQlDyiqDO> getQlfQlDyiqList() {
        return qlfQlDyiqList;
    }

    public void setQlfQlDyiqList(List<QlfQlDyiqDO> qlfQlDyiqList) {
        this.qlfQlDyiqList = qlfQlDyiqList;
    }

    @XmlElement(name = "QLF_QL_HYSYQ")
    public List<QlfQlHysyqDO> getQlfQlHysyqList() {
        return qlfQlHysyqList;
    }

    public void setQlfQlHysyqList(List<QlfQlHysyqDO> qlfQlHysyqList) {
        this.qlfQlHysyqList = qlfQlHysyqList;
    }

    @XmlElement(name = "QLF_QL_JSYDSYQ")
    public List<QlfQlJsydsyqDO> getQlfQlJsydsyqList() {
        return qlfQlJsydsyqList;
    }

    public void setQlfQlJsydsyqList(List<QlfQlJsydsyqDO> qlfQlJsydsyqList) {
        this.qlfQlJsydsyqList = qlfQlJsydsyqList;
    }

    @XmlElement(name = "QLF_QL_NYDSYQ")
    public List<QlfQlNydsyqDO> getQlfQlNydsyqList() {
        return qlfQlNydsyqList;
    }

    public void setQlfQlNydsyqList(List<QlfQlNydsyqDO> qlfQlNydsyqList) {
        this.qlfQlNydsyqList = qlfQlNydsyqList;
    }

    public List<QlfQlQtxgqlDO> getQlfQlQtxgqlList() {
        return qlfQlQtxgqlList;
    }

    public void setQlfQlQtxgqlList(List<QlfQlQtxgqlDO> qlfQlQtxgqlList) {
        this.qlfQlQtxgqlList = qlfQlQtxgqlList;
    }

    @XmlElement(name = "QLF_QL_TDSYQ")
    public List<QlfQlTdsyqDO> getQlfQlTdsyqList() {
        return qlfQlTdsyqList;
    }

    public void setQlfQlTdsyqList(List<QlfQlTdsyqDO> qlfQlTdsyqList) {
        this.qlfQlTdsyqList = qlfQlTdsyqList;
    }

    @XmlElement(name = "QLF_QL_YGDJ")
    public List<QlfQlYgdjDO> getQlfQlYgdjList() {
        return qlfQlYgdjList;
    }

    public void setQlfQlYgdjList(List<QlfQlYgdjDO> qlfQlYgdjList) {
        this.qlfQlYgdjList = qlfQlYgdjList;
    }

    @XmlElement(name = "QLF_QL_YYDJ")
    public List<QlfQlYydjDO> getQlfQlYydjList() {
        return qlfQlYydjList;
    }

    public void setQlfQlYydjList(List<QlfQlYydjDO> qlfQlYydjList) {
        this.qlfQlYydjList = qlfQlYydjList;
    }

    @XmlElement(name = "QLF_QL_ZXDJ")
    public List<QlfQlZxdjDO> getQlfQlZxdjList() {
        return qlfQlZxdjList;
    }

    public void setQlfQlZxdjList(List<QlfQlZxdjDO> qlfQlZxdjList) {
        this.qlfQlZxdjList = qlfQlZxdjList;
    }

    @XmlElement(name = "QLT_FW_FDCQ_DZ")
    public List<QltFwFdcqDzDO> getQltFwFdcqDzList() {
        return qltFwFdcqDzList;
    }

    public void setQltFwFdcqDzList(List<QltFwFdcqDzDO> qltFwFdcqDzList) {
        this.qltFwFdcqDzList = qltFwFdcqDzList;
    }

    @XmlElement(name = "QLT_FW_FDCQ_YZ")
    public List<QltFwFdcqYzDO> getQltFwFdcqYzList() {
        return qltFwFdcqYzList;
    }

    public void setQltFwFdcqYzList(List<QltFwFdcqYzDO> qltFwFdcqYzList) {
        this.qltFwFdcqYzList = qltFwFdcqYzList;
    }

    @XmlElement(name = "QLT_QL_GJZWSYQ")
    public List<QltQlGjzwsyqDO> getQltQlGjzwsyqList() {
        return qltQlGjzwsyqList;
    }

    public void setQltQlGjzwsyqList(List<QltQlGjzwsyqDO> qltQlGjzwsyqList) {
        this.qltQlGjzwsyqList = qltQlGjzwsyqList;
    }

    @XmlElement(name = "QLT_QL_LQ")
    public List<QltQlLqDO> getQltQlLqList() {
        return qltQlLqList;
    }

    public void setQltQlLqList(List<QltQlLqDO> qltQlLqList) {
        this.qltQlLqList = qltQlLqList;
    }

    @XmlTransient
    public List<QlfQlDyaqDywqdDO> getQlfQlDyaqDywqd() {
        return qlfQlDyaqDywqd;
    }

    public void setQlfQlDyaqDywqd(List<QlfQlDyaqDywqdDO> qlfQlDyaqDywqd) {
        this.qlfQlDyaqDywqd = qlfQlDyaqDywqd;
    }

    @XmlElement(name = "ZD_K_103")
    public List<ZdKDO> getZdKList() {
        return zdKList;
    }

    public void setZdKList(List<ZdKDO> zdKList) {
        this.zdKList = zdKList;
    }

    @XmlElement(name = "ZH_K_105")
    public List<ZhKDO> getZhkList() {
        return zhkList;
    }

    public void setZhkList(List<ZhKDO> zhkList) {
        this.zhkList = zhkList;
    }

    public List<ZtfGyQlQlrGxDO> getZtfGyQlQlrGxList() {
        return ztfGyQlQlrGxList;
    }

    public void setZtfGyQlQlrGxList(List<ZtfGyQlQlrGxDO> ztfGyQlQlrGxList) {
        this.ztfGyQlQlrGxList = ztfGyQlQlrGxList;
    }

    @XmlElement(name = "ZTT_GY_QLR")
    public List<ZttGyQlrDO> getZttGyQlrList() {
        return zttGyQlrList;
    }

    public void setZttGyQlrList(List<ZttGyQlrDO> zttGyQlrList) {
        this.zttGyQlrList = zttGyQlrList;
    }


    @XmlElement(name = "ZTT_GY_JTCY")
    public List<ZttGyJtcyDO> getZttGyJtcyDOList() {
        return zttGyJtcyDOList;
    }

    public void setZttGyJtcyDOList(List<ZttGyJtcyDO> zttGyJtcyDOList) {
        this.zttGyJtcyDOList = zttGyJtcyDOList;
    }

    @XmlElement(name = "QLF_QL_GZDJ")
    public List<QlfQlGzdjDO> getQlfQlGzdjDOList() {
        return qlfQlGzdjDOList;
    }

    public void setQlfQlGzdjDOList(List<QlfQlGzdjDO> qlfQlGzdjDOList) {
        this.qlfQlGzdjDOList = qlfQlGzdjDOList;
    }

    @XmlElement(name = "DJF_DJ_DB")
    public List<DjfDjDb> getDjfDjDbList() {
        return djfDjDbList;
    }

    public void setDjfDjDbList(List<DjfDjDb> djfDjDbList) {
        this.djfDjDbList = djfDjDbList;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "djfDjFzList=" + djfDjFzList +
                ", djfDjGdList=" + djfDjGdList +
                ", djfDjSfList=" + djfDjSfList +
                ", djfDjShList=" + djfDjShList +
                ", djfDjSjList=" + djfDjSjList +
                ", djfDjSqrList=" + djfDjSqrList +
                ", djtDjSlsqList=" + djtDjSlsqList +
                ", fjFList=" + fjFList +
                ", ktfQtDzdzwList=" + ktfQtDzdzwList +
                ", ktfQtMzdzwList=" + ktfQtMzdzwList +
                ", ktfQtXzdzwList=" + ktfQtXzdzwList +
                ", ktfZdbhqkList=" + ktfZdbhqkList +
                ", ktfZhbhqkList=" + ktfZhbhqkList +
                ", ktfZhYhydzbList=" + ktfZhYhydzbList +
                ", ktfZhYhzkList=" + ktfZhYhzkList +
                ", kttFwCList=" + kttFwCList +
                ", kttFwHList=" + kttFwHList +
                ", kttFwLjzList=" + kttFwLjzList +
                ", kttFwZrzList=" + kttFwZrzList +
                ", kttGyJzdList=" + kttGyJzdList +
                ", kttGyJzxList=" + kttGyJzxList +
                ", kttGzwList=" + kttGzwList +
                ", kttZdjbxxList=" + kttZdjbxxList +
                ", kttZhjbxxList=" + kttZhjbxxList +
                ", qlfFwFdcqDzXmList=" + qlfFwFdcqDzXmList +
                ", qlfFwFdcqQfsyqList=" + qlfFwFdcqQfsyqList +
                ", qlfQlCfdjList=" + qlfQlCfdjList +
                ", qlfQlDyaqList=" + qlfQlDyaqList +
                ", qlfQlDyiqList=" + qlfQlDyiqList +
                ", qlfQlHysyqList=" + qlfQlHysyqList +
                ", qlfQlJsydsyqList=" + qlfQlJsydsyqList +
                ", qlfQlNydsyqList=" + qlfQlNydsyqList +
                ", qlfQlQtxgqlList=" + qlfQlQtxgqlList +
                ", qlfQlTdsyqList=" + qlfQlTdsyqList +
                ", qlfQlYgdjList=" + qlfQlYgdjList +
                ", qlfQlYydjList=" + qlfQlYydjList +
                ", qlfQlZxdjList=" + qlfQlZxdjList +
                ", qltFwFdcqDzList=" + qltFwFdcqDzList +
                ", qltFwFdcqYzList=" + qltFwFdcqYzList +
                ", qltQlGjzwsyqList=" + qltQlGjzwsyqList +
                ", qltQlLqList=" + qltQlLqList +
                ", qlfQlDyaqDywqd=" + qlfQlDyaqDywqd +
                ", zdKList=" + zdKList +
                ", zhkList=" + zhkList +
                ", ztfGyQlQlrGxList=" + ztfGyQlQlrGxList +
                ", zttGyQlrList=" + zttGyQlrList +
                ", zttGyJtcyDOList=" + zttGyJtcyDOList +
                ", qlfQlGzdjDOList=" + qlfQlGzdjDOList +
                ", djfDjDbList=" + djfDjDbList +
                '}';
    }
}
