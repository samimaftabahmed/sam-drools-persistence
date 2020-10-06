package com.sam.drools.samdroolspersistence;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Persistence;

@Configuration
public class PersistentDroolConfig {

    public static Long KIE_SESSION_ID;
    private final KieServices kieServices = KieServices.Factory.get();

    @Bean(name = "kieSession")
    public KieSession kieSession() {
        KieSession kieSession;
        if (KIE_SESSION_ID == null) {
            kieSession = createNewKieSession();
            KIE_SESSION_ID = kieSession.getIdentifier();
            return kieSession;
        } else {
            kieSession = getPersistentKieSession();
            KIE_SESSION_ID = kieSession.getIdentifier();
            return kieSession;
        }
    }

    public KieServices getKieServices() {
        initDataSource();
        return kieServices;
    }

    public KieBase getKieBase() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/rules.drl"));
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        return kieBase;
    }

    public Environment getEnv() {
        Environment env = kieServices.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, Persistence.createEntityManagerFactory("org.drools.persistence.jpa"));
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        return env;
    }

    private KieSession createNewKieSession() {
        KieSession kieSession = kieServices.getStoreServices().newKieSession(getKieBase(), null, getEnv());
        PersistentDroolConfig.KIE_SESSION_ID = kieSession.getIdentifier();
        return kieSession;
    }

    private KieSession getPersistentKieSession() {
        return kieServices.getStoreServices().loadKieSession(KIE_SESSION_ID, getKieBase(), null, getEnv());
    }

    private void initDataSource() {
        PoolingDataSource ds = new PoolingDataSource();
        ds.setUniqueName("jdbc/BitronixJTADataSource");
        ds.setClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setMaxPoolSize(3);
        ds.setAllowLocalTransactions(true);
        ds.getDriverProperties().put("user", "root");
        ds.getDriverProperties().put("password", "1234");
        ds.getDriverProperties().put("URL", "jdbc:mysql://localhost:3306/drool_demo");
        ds.init();
    }

}
