package com.ELK_POC.elasticSearch.repository;

import com.ELK_POC.elasticSearch.entity.CarPromotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPromotionElasticRepository extends ElasticsearchRepository<CarPromotion, String> {
    Page<CarPromotion> findByType(String type, Pageable pageable);
}
