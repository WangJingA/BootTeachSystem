//package com.boot.teach.common.config.es;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//
///**
// * es config class
// * @author : hzx
// * @date : 2023/5/16
// */
//@Configuration
//public class RestHightClient extends AbstractElasticsearchConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean
//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("192.168.219.137:9200")
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//}
