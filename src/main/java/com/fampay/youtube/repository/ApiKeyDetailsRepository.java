package com.fampay.youtube.repository;

import com.fampay.youtube.model.ApiKeyDetailsModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface ApiKeyDetailsRepository extends JpaRepository<ApiKeyDetailsModel, String> {

    @Modifying
    @Transactional
    void deleteById(Long id);

    @Query(value = "select * from api_key_details order by id ASC LIMIT 1", nativeQuery = true)
    Optional<ApiKeyDetailsModel> findFirstApi();
}
