package com.elasticSearch.AutoComplete.repository;


import com.elasticSearch.AutoComplete.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    List<User> findAll();
}
