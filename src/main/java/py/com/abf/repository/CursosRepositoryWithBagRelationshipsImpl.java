package py.com.abf.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import py.com.abf.domain.Cursos;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CursosRepositoryWithBagRelationshipsImpl implements CursosRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cursos> fetchBagRelationships(Optional<Cursos> cursos) {
        return cursos.map(this::fetchTemas);
    }

    @Override
    public Page<Cursos> fetchBagRelationships(Page<Cursos> cursos) {
        return new PageImpl<>(fetchBagRelationships(cursos.getContent()), cursos.getPageable(), cursos.getTotalElements());
    }

    @Override
    public List<Cursos> fetchBagRelationships(List<Cursos> cursos) {
        return Optional.of(cursos).map(this::fetchTemas).orElse(Collections.emptyList());
    }

    Cursos fetchTemas(Cursos result) {
        return entityManager
            .createQuery("select cursos from Cursos cursos left join fetch cursos.temas where cursos is :cursos", Cursos.class)
            .setParameter("cursos", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Cursos> fetchTemas(List<Cursos> cursos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cursos.size()).forEach(index -> order.put(cursos.get(index).getId(), index));
        List<Cursos> result = entityManager
            .createQuery("select distinct cursos from Cursos cursos left join fetch cursos.temas where cursos in :cursos", Cursos.class)
            .setParameter("cursos", cursos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
