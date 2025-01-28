package br.com.joelf.rinha_backend_2023_q3.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.redis.CacheRepositoryImpl;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Pessoa> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Pessoa> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public CacheRepository<String, Pessoa> redisRepository(RedisTemplate<String, Pessoa> template) {
        return new CacheRepositoryImpl<>(template);
    }
}