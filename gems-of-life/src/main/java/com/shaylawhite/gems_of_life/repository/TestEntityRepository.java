package com.shaylawhite.gems_of_life.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findByTestValue(String testValue);
}
