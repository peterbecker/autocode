package ${package};

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import org.hibernate.SessionFactory;

public class ${entity.name}AppDao extends AbstractDAO<${entity.name}> {
    public ${entity.name}AppDao(SessionFactory factory) {
        super(factory);
    }

    public List<${entity.name}> getAll() {
        return currentSession().createCriteria(${entity.name}.class).list();
    }

    public ${entity.name} findById(long id) {
        return get(id);
    }

    public long save(${entity.name} entity) {
        return persist(entity).getId();
    }

    public void delete(${entity.name} entity) {
        currentSession().delete(entity);
    }
}
