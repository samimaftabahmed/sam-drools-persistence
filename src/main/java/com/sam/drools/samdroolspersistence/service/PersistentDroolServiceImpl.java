package com.sam.drools.samdroolspersistence.service;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieSession;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.StatefulKnowledgeSession;

//@Service
public class PersistentDroolServiceImpl implements IPersistentKieService {

    private static Long KIE_SESSION_ID = null;

//    @Autowired
    private KieBase kieBase;
//    @Autowired
    private Environment env;
//    @Autowired
    private KieServices kieServices;

    @Override
    public StatefulKnowledgeSession getPersistentKnowledgeSession() {

        return JPAKnowledgeService.newStatefulKnowledgeSession(kieBase, null, env);
    }

    @Override
    public KieSession getPersistentKieSession() {
        KieSession kieSession = kieServices.getStoreServices().newKieSession(kieBase, null, env);
        KIE_SESSION_ID = kieSession.getIdentifier();
        return kieSession;
    }


}
