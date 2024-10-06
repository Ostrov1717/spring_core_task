package org.example.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryStorage {
        // Общее хранилище, где ключ - это класс сущности, а значение - карта с данными
        private Map<Class<?>, Map<Long, Object>> storage = new HashMap<>();

        // Метод для получения карты по типу сущности
        private Map<Long, Object> getNamespace(Class<?> entityClass) {
            return storage.computeIfAbsent(entityClass, k -> new HashMap<>());
        }

        // Сохранение объекта в конкретное пространство имён
        public <T> void save(Class<T> entityClass, Long id, T entity) {
            Map<Long, Object> namespace = getNamespace(entityClass);
            namespace.put(id, entity);
        }

        // Получение объекта по id и типу сущности
        public <T> T findById(Class<T> entityClass, Long id) {
            Map<Long, Object> namespace = getNamespace(entityClass);
            return entityClass.cast(namespace.get(id));
        }

        // Удаление объекта по id и типу сущности
        public <T> void delete(Class<T> entityClass, Long id) {
            Map<Long, Object> namespace = getNamespace(entityClass);
            namespace.remove(id);
        }

        // Получение всех объектов конкретного типа
        public <T> Map<Long, T> getAll(Class<T> entityClass) {
            Map<Long, Object> namespace = getNamespace(entityClass);
            Map<Long, T> result = new HashMap<>();
            for (Map.Entry<Long, Object> entry : namespace.entrySet()) {
                result.put(entry.getKey(), entityClass.cast(entry.getValue()));
            }
            return result;
        }
    }
