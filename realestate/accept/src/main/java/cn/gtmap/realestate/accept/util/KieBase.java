package cn.gtmap.realestate.accept.util;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/13,1.0
 * @description
 */
public class KieBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);
    /**
     * @return KieSession
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description
     */
    protected KieSession getKieSession() {

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer.newKieSession("all-rules");
    }

    /**
     * @return KieContainer
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取Kie容器
     */
    protected static KieContainer getKieContainer() {
        KieServices kieServices = KieServices.get();
        return  kieServices.getKieClasspathContainer();
    }

    /**
     * @param sessionName session名称
     * @return KieSession
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据session名称获取KieSession
     */
    public static KieSession getKieSessionBySessionName(String sessionName) {
        KieContainer kieContainer = getKieContainer();
        return kieContainer.newKieSession(sessionName);
    }


    /**
     * @param agendaGroupName 焦点名称
     * @return KieSession
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据session、焦点名称获取KieSession
     */
    protected KieSession getKieSession(KieSession kieSession, String agendaGroupName) {
        kieSession.getAgenda().getAgendaGroup(agendaGroupName).setFocus();
        return kieSession;
    }

    protected KieSession getKieSession(String agendaGroupName) {
        KieSession kieSession = getKieSession();
        kieSession.getAgenda().getAgendaGroup(agendaGroupName).setFocus();
        return kieSession;
    }

    protected StatelessKieSession getStatelessKieSession() {
        KieContainer kieContainer = getKieContainer();
        return kieContainer.newStatelessKieSession("stateless-rules");
    }

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 获取规则引擎KieSession
     */
    public static KieSession getRulesKieSession(String rule) throws UnsupportedEncodingException {
        KieSession kSession = null;
        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kb.getErrors();
        for (KnowledgeBuilderError error : errors) {
            LOGGER.error(error.getMessage(), error);
        }
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(kb.getKnowledgePackages());
        kSession = kBase.newKieSession();
        return kSession;
    }
}
