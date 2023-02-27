package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import py.com.abf.domain.MallaCurricular;

public interface MallaCurricularRepositoryWithBagRelationships {
    Optional<MallaCurricular> fetchBagRelationships(Optional<MallaCurricular> mallaCurricular);

    List<MallaCurricular> fetchBagRelationships(List<MallaCurricular> mallaCurriculars);

    Page<MallaCurricular> fetchBagRelationships(Page<MallaCurricular> mallaCurriculars);
}
