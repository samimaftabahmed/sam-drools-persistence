package com.sam.drools.samdroolspersistence.repository;

import com.sam.drools.samdroolspersistence.entity.SessioninfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroolsSessionRepository extends JpaRepository<SessioninfoEntity, Long> {
}
