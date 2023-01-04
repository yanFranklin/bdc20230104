package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
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
public class DataModelBdc implements Serializable {

    private List<DjfDjFzDO> djfDjFzList;
    private List<DjfDjGdBdc> djfDjGdList;
    private List<DjfDjSfDO> djfDjSfList;
    private List<DjfDjShDO> djfDjShList;
    private List<DjfDjSjDO> djfDjSjList;
    private List<DjfDjSqrDO> djfDjSqrList;
    private List<DjtDjSlsqBdc> djtDjSlsqList;
    private List<FjFDO> fjFList;
    private List<KtfQtDzdzwDO> ktfQtDzdzwList;
    private List<KtfQtMzdzwDO> ktfQtMzdzwList;
    private List<KtfQtXzdzwDO> ktfQtXzdzwList;
    private List<KtfZdbhqkDO> ktfZdbhqkList;
    private List<KtfZhbhqkDO> ktfZhbhqkList;
    private List<KtfZhYhydzbDO> ktfZhYhydzbList;
    private List<KtfZhYhzkBdc> ktfZhYhzkList;
    private List<KttFwCDO> kttFwCList;
    private List<KttFwHBdc> kttFwHList;
    private List<KttFwLjzBdc> kttFwLjzList;
    private List<KttFwZrzBdc> kttFwZrzList;
    private List<KttGyJzdBdc> kttGyJzdList;
    private List<KttGyJzxBdc> kttGyJzxList;
    private List<KttGzwDO> kttGzwList;
    private List<KttZdjbxxBdc> kttZdjbxxList;
    private List<KttZhjbxxBdc> kttZhjbxxList;
    private List<QlfFwFdcqDzXmBdc> qlfFwFdcqDzXmList;
    private List<QlfFwFdcqQfsyqBdc> qlfFwFdcqQfsyqList;
    private List<QlfQlCfdjBdc> qlfQlCfdjList;
    private List<QlfQlDyaqBdc> qlfQlDyaqList;
    private List<QlfQlDyiqBdc> qlfQlDyiqList;
    private List<QlfQlHysyqBdc> qlfQlHysyqList;
    private List<QlfQlJsydsyqBdc> qlfQlJsydsyqList;
    private List<QlfQlNydsyqBdc> qlfQlNydsyqList;
    private List<QlfQlQtxgqlBdc> qlfQlQtxgqlList;
    private List<QlfQlTdsyqBdc> qlfQlTdsyqList;
    private List<QlfQlYgdjBdc> qlfQlYgdjList;
    private List<QlfQlYydjDO> qlfQlYydjList;
    private List<QlfQlZxdjDO> qlfQlZxdjList;
    private List<QltFwFdcqDzBdc> qltFwFdcqDzList;
    private List<QltFwFdcqYzBdc> qltFwFdcqYzList;
    private List<QltQlGjzwsyqBdc> qltQlGjzwsyqList;
    private List<QltQlLqBdc> qltQlLqList;
    private List<QlfQlDyaqDywqdDO> qlfQlDyaqDywqd;
    private List<ZdKDO> zdKList;
    private List<ZhKDO> zhkList;
    private List<ZtfGyQlQlrGxDO> ztfGyQlQlrGxList;
    private List<ZttGyQlrBdc> zttGyQlrList;
    private List<ZttGyJtcyDO> zttGyJtcyDOList;
    private List<QlfQlGzdjDO> qlfQlGzdjDOList;
    private List<DjfDjDb> djfDjDbList;


    @XmlElement(name = "DJF_DJ_FZ")
    public List<DjfDjFzDO> getDjfDjFzList() {
        return djfDjFzList;
    }

    public void setDjfDjFzList(List<DjfDjFzDO> djfDjFzList) {
        this.djfDjFzList = djfDjFzList;
    }

    @XmlElement(name = "DJF_DJ_GD")
    public List<DjfDjGdBdc> getDjfDjGdList() {
        return djfDjGdList;
    }

    public void setDjfDjGdList(List<DjfDjGdBdc> djfDjGdList) {
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
    public List<DjtDjSlsqBdc> getDjtDjSlsqList() {
        return djtDjSlsqList;
    }

    public void setDjtDjSlsqList(List<DjtDjSlsqBdc> djtDjSlsqList) {
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
    public List<KtfZhYhzkBdc> getKtfZhYhzkList() {
        return ktfZhYhzkList;
    }

    public void setKtfZhYhzkList(List<KtfZhYhzkBdc> ktfZhYhzkList) {
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
    public List<KttFwHBdc> getKttFwHList() {
        return kttFwHList;
    }

    public void setKttFwHList(List<KttFwHBdc> kttFwHList) {
        this.kttFwHList = kttFwHList;
    }

    @XmlElement(name = "KTT_FW_LJZ")
    public List<KttFwLjzBdc> getKttFwLjzList() {
        return kttFwLjzList;
    }

    public void setKttFwLjzList(List<KttFwLjzBdc> kttFwLjzList) {
        this.kttFwLjzList = kttFwLjzList;
    }

    @XmlElement(name = "KTT_FW_ZRZ")
    public List<KttFwZrzBdc> getKttFwZrzList() {
        return kttFwZrzList;
    }

    public void setKttFwZrzList(List<KttFwZrzBdc> kttFwZrzList) {
        this.kttFwZrzList = kttFwZrzList;
    }

    @XmlElement(name = "KTT_GY_JZD")
    public List<KttGyJzdBdc> getKttGyJzdList() {
        return kttGyJzdList;
    }

    public void setKttGyJzdList(List<KttGyJzdBdc> kttGyJzdList) {
        this.kttGyJzdList = kttGyJzdList;
    }

    @XmlElement(name = "KTT_GY_JZX")
    public List<KttGyJzxBdc> getKttGyJzxList() {
        return kttGyJzxList;
    }

    public void setKttGyJzxList(List<KttGyJzxBdc> kttGyJzxList) {
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
    public List<KttZdjbxxBdc> getKttZdjbxxList() {
        return kttZdjbxxList;
    }

    public void setKttZdjbxxList(List<KttZdjbxxBdc> kttZdjbxxList) {
        this.kttZdjbxxList = kttZdjbxxList;
    }

    @XmlElement(name = "KTT_ZHJBXX")
    public List<KttZhjbxxBdc> getKttZhjbxxList() {
        return kttZhjbxxList;
    }

    public void setKttZhjbxxList(List<KttZhjbxxBdc> kttZhjbxxList) {
        this.kttZhjbxxList = kttZhjbxxList;
    }

    @XmlElement(name = "QLF_FW_FDCQ_DZ_XM")
    public List<QlfFwFdcqDzXmBdc> getQlfFwFdcqDzXmList() {
        return qlfFwFdcqDzXmList;
    }

    public void setQlfFwFdcqDzXmList(List<QlfFwFdcqDzXmBdc> qlfFwFdcqDzXmList) {
        this.qlfFwFdcqDzXmList = qlfFwFdcqDzXmList;
    }

    @XmlElement(name = "QLF_FW_FDCQ_QFSYQ")
    public List<QlfFwFdcqQfsyqBdc> getQlfFwFdcqQfsyqList() {
        return qlfFwFdcqQfsyqList;
    }

    public void setQlfFwFdcqQfsyqList(List<QlfFwFdcqQfsyqBdc> qlfFwFdcqQfsyqList) {
        this.qlfFwFdcqQfsyqList = qlfFwFdcqQfsyqList;
    }

    @XmlElement(name = "QLF_QL_CFDJ")
    public List<QlfQlCfdjBdc> getQlfQlCfdjList() {
        return qlfQlCfdjList;
    }

    public void setQlfQlCfdjList(List<QlfQlCfdjBdc> qlfQlCfdjList) {
        this.qlfQlCfdjList = qlfQlCfdjList;
    }

    @XmlElement(name = "QLF_QL_DYAQ")
    public List<QlfQlDyaqBdc> getQlfQlDyaqList() {
        return qlfQlDyaqList;
    }

    public void setQlfQlDyaqList(List<QlfQlDyaqBdc> qlfQlDyaqList) {
        this.qlfQlDyaqList = qlfQlDyaqList;
    }

    @XmlElement(name = "QLF_QL_DYIQ")
    public List<QlfQlDyiqBdc> getQlfQlDyiqList() {
        return qlfQlDyiqList;
    }

    public void setQlfQlDyiqList(List<QlfQlDyiqBdc> qlfQlDyiqList) {
        this.qlfQlDyiqList = qlfQlDyiqList;
    }

    @XmlElement(name = "QLF_QL_HYSYQ")
    public List<QlfQlHysyqBdc> getQlfQlHysyqList() {
        return qlfQlHysyqList;
    }

    public void setQlfQlHysyqList(List<QlfQlHysyqBdc> qlfQlHysyqList) {
        this.qlfQlHysyqList = qlfQlHysyqList;
    }

    @XmlElement(name = "QLF_QL_JSYDSYQ")
    public List<QlfQlJsydsyqBdc> getQlfQlJsydsyqList() {
        return qlfQlJsydsyqList;
    }

    public void setQlfQlJsydsyqList(List<QlfQlJsydsyqBdc> qlfQlJsydsyqList) {
        this.qlfQlJsydsyqList = qlfQlJsydsyqList;
    }

    @XmlElement(name = "QLF_QL_NYDSYQ")
    public List<QlfQlNydsyqBdc> getQlfQlNydsyqList() {
        return qlfQlNydsyqList;
    }

    public void setQlfQlNydsyqList(List<QlfQlNydsyqBdc> qlfQlNydsyqList) {
        this.qlfQlNydsyqList = qlfQlNydsyqList;
    }

    public List<QlfQlQtxgqlBdc> getQlfQlQtxgqlList() {
        return qlfQlQtxgqlList;
    }

    public void setQlfQlQtxgqlList(List<QlfQlQtxgqlBdc> qlfQlQtxgqlList) {
        this.qlfQlQtxgqlList = qlfQlQtxgqlList;
    }

    @XmlElement(name = "QLF_QL_TDSYQ")
    public List<QlfQlTdsyqBdc> getQlfQlTdsyqList() {
        return qlfQlTdsyqList;
    }

    public void setQlfQlTdsyqList(List<QlfQlTdsyqBdc> qlfQlTdsyqList) {
        this.qlfQlTdsyqList = qlfQlTdsyqList;
    }

    @XmlElement(name = "QLF_QL_YGDJ")
    public List<QlfQlYgdjBdc> getQlfQlYgdjList() {
        return qlfQlYgdjList;
    }

    public void setQlfQlYgdjList(List<QlfQlYgdjBdc> qlfQlYgdjList) {
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
    public List<QltFwFdcqDzBdc> getQltFwFdcqDzList() {
        return qltFwFdcqDzList;
    }

    public void setQltFwFdcqDzList(List<QltFwFdcqDzBdc> qltFwFdcqDzList) {
        this.qltFwFdcqDzList = qltFwFdcqDzList;
    }

    @XmlElement(name = "QLT_FW_FDCQ_YZ")
    public List<QltFwFdcqYzBdc> getQltFwFdcqYzList() {
        return qltFwFdcqYzList;
    }

    public void setQltFwFdcqYzList(List<QltFwFdcqYzBdc> qltFwFdcqYzList) {
        this.qltFwFdcqYzList = qltFwFdcqYzList;
    }

    @XmlElement(name = "QLT_QL_GJZWSYQ")
    public List<QltQlGjzwsyqBdc> getQltQlGjzwsyqList() {
        return qltQlGjzwsyqList;
    }

    public void setQltQlGjzwsyqList(List<QltQlGjzwsyqBdc> qltQlGjzwsyqList) {
        this.qltQlGjzwsyqList = qltQlGjzwsyqList;
    }

    @XmlElement(name = "QLT_QL_LQ")
    public List<QltQlLqBdc> getQltQlLqList() {
        return qltQlLqList;
    }

    public void setQltQlLqList(List<QltQlLqBdc> qltQlLqList) {
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
    public List<ZttGyQlrBdc> getZttGyQlrList() {
        return zttGyQlrList;
    }

    public void setZttGyQlrList(List<ZttGyQlrBdc> zttGyQlrList) {
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
