package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import py.com.abf.domain.Cursos;

public interface CursosRepositoryWithBagRelationships {
    Optional<Cursos> fetchBagRelationships(Optional<Cursos> cursos);

    List<Cursos> fetchBagRelationships(List<Cursos> cursos);

    Page<Cursos> fetchBagRelationships(Page<Cursos> cursos);
}
