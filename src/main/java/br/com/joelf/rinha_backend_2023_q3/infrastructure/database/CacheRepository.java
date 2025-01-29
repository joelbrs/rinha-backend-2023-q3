package br.com.joelf.rinha_backend_2023_q3.infrastructure.database;

public interface CacheRepository<Key, Value> {
    Value get(Key key);
    void set(Key key, Value value);
}
