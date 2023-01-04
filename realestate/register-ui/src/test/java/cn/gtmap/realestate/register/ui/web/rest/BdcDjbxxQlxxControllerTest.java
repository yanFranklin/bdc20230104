package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.qo.register.BdcDjbqlQO;
import cn.gtmap.realestate.register.ui.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcDjbxxQlxxControllerTest {
    @Autowired
    BdcDjbxxQlxxController bdcDjbxxQlxxController;

    @Test
    public void queryJsydsyqZjdsyq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340104000000GB00000F05990084");
        bdcDjbqlQO.setQllx("3");
        bdcDjbxxQlxxController.queryJsydsyqZjdsyq(bdcDjbqlQO);
    }

    @Test
    public void queryBdcQljb() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("4");
        bdcDjbxxQlxxController.queryBdcQljb(bdcDjbqlQO);
    }

    @Test
    public void queryTdsyq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("1");
        bdcDjbxxQlxxController.queryTdsyq(bdcDjbqlQO);
    }

    @Test
    public void queryTdcbjyqNyddqtsyq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("9");
        bdcDjbxxQlxxController.queryTdcbjyqNyddqtsyq(bdcDjbqlQO);
    }

    @Test
    public void queryLq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("10");
        bdcDjbxxQlxxController.queryLq(bdcDjbqlQO);
    }

    @Test
    public void queryDyiq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("19");
        bdcDjbxxQlxxController.queryDyiq(bdcDjbqlQO);
    }

    @Test
    public void queryDyaq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("95");
        bdcDjbxxQlxxController.queryDyaq(bdcDjbqlQO);
    }

    @Test
    public void queryCf() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("98");
        bdcDjbxxQlxxController.queryCf(bdcDjbqlQO);
    }

    @Test
    public void queryQtxgql() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("99");
        bdcDjbxxQlxxController.queryQtxgql(bdcDjbqlQO);
    }

    @Test
    public void queryTdjyq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("35");
        bdcDjbxxQlxxController.queryTdjyq(bdcDjbqlQO);
    }

    @Test
    public void queryJzq() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("92");
        bdcDjbxxQlxxController.queryJzq(bdcDjbqlQO);
    }

    @Test
    public void queryBdcYyList() {
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setPage(0);
        bdcDjbqlQO.setSize(4);
        bdcDjbqlQO.setBdcdyh("340111306008GB00012F00030024");
        bdcDjbqlQO.setQllx("97");
        bdcDjbxxQlxxController.queryBdcYyList(bdcDjbqlQO);
    }
}