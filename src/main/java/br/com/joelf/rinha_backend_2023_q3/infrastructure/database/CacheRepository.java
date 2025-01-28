package br.com.joelf.rinha_backend_2023_q3.infrastructure.database;

import java.util.List;

public interface CacheRepository<Key, Value> {
    Value get(Key key);
    List<Value> getList(Key key);
    void set(Key key, Value value);
    void setList(Key key, List<Value> value);
}
