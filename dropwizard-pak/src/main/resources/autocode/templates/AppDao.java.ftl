package ${package};

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ${entity.name}AppDao extends AbstractDAO<${entity.name}> {
    public ${entity.name}AppDao(SessionFactory factory) {
        super(factory);
    }
}
