package com.fampay.youtube.repository;

import com.fampay.youtube.model.VideoDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoDetailsRepository extends JpaRepository<VideoDetailsModel, Long> {
    @Query("SELECT v FROM VideoDetailsModel v WHERE v.title LIKE %:title% OR v.description LIKE %:description%")
    List<VideoDetailsModel> findByTitleOrDescriptionContaining(@Param("title") String title, @Param("description") String description);

}
