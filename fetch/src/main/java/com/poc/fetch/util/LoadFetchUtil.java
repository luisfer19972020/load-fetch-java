package com.poc.fetch.util;

import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.model.entity.ICsvModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class LoadFetchUtil<T extends ICsvModel> implements Iterable<T> {
    public static final int QUERY_TYPE = 1;
    public static final int NATIVE_QUERY_TYPE = 2;
    public static final int NAMED_QUERY_TYPE = 3;
    private final EntityManager entityManager;
    private final String querystring;
    private final int fetchSize;
    private final Object[] parameters;
    private final int queryType;
    private final String queryFetchIn;
    private boolean queryWithFetchJoin = false;

    public LoadFetchUtil(EntityManager entityManager, String querystring, int fetchSize, int queryType, Object... parameters) {
        this.entityManager = entityManager;
        this.querystring = querystring;
        this.fetchSize = fetchSize;
        this.parameters = parameters;
        this.queryType = queryType;
        this.queryFetchIn = null;
    }

    public LoadFetchUtil(EntityManager entityManager, String queryIds, String queryFetchIn, int fetchSize, int queryType, Object... parameters) {
        if (queryType != NAMED_QUERY_TYPE) {
            throw new IllegalArgumentException("Para consultas con join solo se puede usar Nameds Querys");
        }
        this.entityManager = entityManager;
        this.querystring = queryIds;
        this.fetchSize = fetchSize;
        this.parameters = parameters;
        this.queryType = queryType;
        this.queryFetchIn = queryFetchIn;
        this.queryWithFetchJoin = true;
    }

    @Override
    public Iterator<T> iterator() {
        return new FetchIterator();
    }

    private class FetchIterator implements Iterator<T> {

        private int startPosition = 0;
        private int totalPostions = 0;
        public List<T> currentResults;
        public List<Object> ids;

        @Override
        public boolean hasNext() {
            if (currentResults == null || startPosition >= currentResults.size()) {
                if (currentResults != null) {
                    currentResults.clear();
                }
                fetchNextSection();
            }
            return currentResults != null && startPosition < currentResults.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is not elements");
            }
            totalPostions++;
            return currentResults.get(startPosition++);
        }

        private void fetchNextSection() {
            if (queryWithFetchJoin) {
                fetWithJoin();
                return;
            }
            fetchWithOutJoin();
        }

        private void fetchWithOutJoin() {
            Query query = getQuery();
            setParameters(query);
            query.setHint("jakarta.persistence.query.timeout", fetchSize);
            query.setFirstResult(totalPostions);
            query.setMaxResults(fetchSize);
            currentResults = query.getResultList();
            startPosition = 0;
        }

        private void fetWithJoin() {
            Query query = entityManager.createNamedQuery(querystring, Object.class);
            setParameters(query);
            query.setHint("jakarta.persistence.query.timeout", fetchSize);
            query.setFirstResult(totalPostions);
            query.setMaxResults(fetchSize);
            Query queryFI = entityManager.createNamedQuery(queryFetchIn, Estudiante.class);
            ids =query.getResultList();
            queryFI.setParameter("ids",ids );
            currentResults = queryFI.getResultList();
            startPosition = 0;
            ids.clear();
        }

        private void setParameters(Query query) {
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
        }


        private Query getQuery() {
            switch (queryType) {
                case QUERY_TYPE:
                    return entityManager.createQuery(querystring);
                case NAMED_QUERY_TYPE:
                    return entityManager.createNamedQuery(querystring, Estudiante.class);
                case NATIVE_QUERY_TYPE:
                default:
                    return entityManager.createNativeQuery(querystring, Estudiante.class);
            }
        }

        @SuppressWarnings("unchecked")
        private Class<T> getGenericClass() {
            return (Class<T>) (getClass().getGenericSuperclass().getClass());
        }

    }
}
