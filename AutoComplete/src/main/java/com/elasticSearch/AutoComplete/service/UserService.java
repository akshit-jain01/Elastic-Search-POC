package com.elasticSearch.AutoComplete.service;

//import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
//import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.elasticSearch.AutoComplete.model.User;
import com.elasticSearch.AutoComplete.repository.UserRepository;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.client.elc.NativeQuery;
//import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public long count() {
        return this.userRepository.count();
    }

    public List<User> search(String keywords) {

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("country", keywords).fuzziness(2).minimumShouldMatch("80%");
        System.out.println("matchQueryBuilder = " + matchQueryBuilder);

//        String regexExpression = ".*" + locationTerm + "*";
//        QueryBuilder regexQuery = QueryBuilders.regexpQuery("name",regexExpression);

//        String nestedPath="locationType";
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("locationType.level", level);
//        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(nestedPath, boolQueryBuilder.must(matchQuery), ScoreMode.Avg);

//        QueryBuilder finalQuery = QueryBuilders.boolQuery()
//                .must(tenantQuery)
//                .must(regexQuery)
//                .must(nestedQuery);

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(matchQueryBuilder)
                .build();
//                .setPageable(PageRequest.of(0, 10));


//        Float nonExistingBoost = null; // even though it exists in SpringBoot, ElasticSearch has no boost for this type of query
        // when you analyze what matchQuery returns, it also has nothing related to boost
//        QueryBuilders.matchQuery("country", keywords);
//        NativeQuery nativeQuery = NativeSearchQueryBuilder.withQuery(query).build();
        SearchHits<User> result = this.elasticsearchOperations.search(build, User.class);
        return result.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
