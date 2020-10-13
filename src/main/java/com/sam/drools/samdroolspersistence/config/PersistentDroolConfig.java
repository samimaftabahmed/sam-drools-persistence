package com.sam.drools.samdroolspersistence.config;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.Persistence;

@Configuration
public class PersistentDroolConfig {

    public static final String UNIQUE_NAME = "jdbc/BitronixJTADataSource";
    public static final String DRL_LOCATION = "rules/rules-template.drt";
    public static Long KIE_SESSION_ID;
    private KieServices kieServices;

    @PostConstruct
    private void init() {
        this.initDataSource();
        this.kieServices = KieServices.Factory.get();
    }

    @Bean
    public StatefulKnowledgeSession getPersistentKnowledgeSession() {

        StatefulKnowledgeSession session = JPAKnowledgeService.newStatefulKnowledgeSession(getKieBase(), null, getEnv());
        KIE_SESSION_ID = session.getIdentifier();
        return session;
    }

    //    @Bean
    public KieSession getPersistentKieSession() {
        KieSession kieSession = kieServices.getStoreServices().newKieSession(getKieBase(), null, getEnv());
        KIE_SESSION_ID = kieSession.getIdentifier();
        return kieSession;
    }

    @Bean
    public KieServices getKieServices() {
        return this.kieServices;
    }

    @Bean
    public KieBase getKieBase() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(DRL_LOCATION));
        //todo remove final if problem faced in rule templating
        final KieRepository kieRepository = kieServices.getRepository();

        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer.getKieBase();
    }

    @Bean
    public Environment getEnv() {
        Environment env = kieServices.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, Persistence.createEntityManagerFactory("org.drools.persistence.jpa"));
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        return env;
    }

    private void initDataSource() {
        PoolingDataSource ds = new PoolingDataSource();
        ds.setUniqueName(UNIQUE_NAME);
        ds.setClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setMaxPoolSize(3);
        ds.setAllowLocalTransactions(true);
        ds.getDriverProperties().put("user", "root");
        ds.getDriverProperties().put("password", "1234");
        ds.getDriverProperties().put("URL", "jdbc:mysql://localhost:3306/drool_demo");
        ds.init();
    }
}