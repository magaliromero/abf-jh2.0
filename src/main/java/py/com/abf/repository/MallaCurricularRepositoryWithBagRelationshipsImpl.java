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
import py.com.abf.domain.MallaCurricular;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MallaCurricularRepositoryWithBagRelationshipsImpl implements MallaCurricularRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MallaCurricular> fetchBagRelationships(Optional<MallaCurricular> mallaCurricular) {
        return mallaCurricular.map(this::fetchTemas);
    }

    @Override
    public Page<MallaCurricular> fetchBagRelationships(Page<MallaCurricular> mallaCurriculars) {
        return new PageImpl<>(
            fetchBagRelationships(mallaCurriculars.getContent()),
            mallaCurriculars.getPageable(),
            mallaCurriculars.getTotalElements()
        );
    }

    @Override
    public List<MallaCurricular> fetchBagRelationships(List<MallaCurricular> mallaCurriculars) {
        return Optional.of(mallaCurriculars).map(this::fetchTemas).orElse(Collections.emptyList());
    }

    MallaCurricular fetchTemas(MallaCurricular result) {
        return entityManager
            .createQuery(
                "select mallaCurricular from MallaCurricular mallaCurricular left join fetch mallaCurricular.temas where mallaCurricular is :mallaCurricular",
                MallaCurricular.class
            )
            .setParameter("mallaCurricular", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<MallaCurricular> fetchTemas(List<MallaCurricular> mallaCurriculars) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, mallaCurriculars.size()).forEach(index -> order.put(mallaCurriculars.get(index).getId(), index));
        List<MallaCurricular> result = entityManager
            .createQuery(
                "select distinct mallaCurricular from MallaCurricular mallaCurricular left join fetch mallaCurricular.temas where mallaCurricular in :mallaCurriculars",
                MallaCurricular.class
            )
            .setParameter("mallaCurriculars", mallaCurriculars)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
