package com.quickticket.api;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final EntityManager entityManager;

    public TestController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/hello")
    @Transactional
    public String helloWorld() {
        // 1. 엔티티 생성 및 데이터 세팅
        TestEntity testEntity = new TestEntity();
        testEntity.setMessage("Hello World! DB 저장 완료!");

        // 2. DB에 저장
        entityManager.persist(testEntity);

        // 3. 결과 반환
        return "Nginx를 거쳐 Spring Boot 도착 -> DB 연동 성공! 저장된 ID: " + testEntity.getId();
    }
}