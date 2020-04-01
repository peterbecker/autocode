package ${package};

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ${entity.name}AppDao extends AbstractDAO<${entity.name}> {
    public ${entity.name}AppDao(SessionFactory factory) {
        super(factory);
    }

    public ${entity.name} findById(long id) {
        return get(id);
    }

    public long save(${entity.name} entity) {
        return persist(entity).getId();
    }
}
