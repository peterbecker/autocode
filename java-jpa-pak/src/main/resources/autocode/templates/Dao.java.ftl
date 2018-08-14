package ${package};

import javax.persistence.EntityManager;
import com.github.peterbecker.jpa.AbstractDao;

public class ${entity.name}Dao extends AbstractDao<${entity.name}> {
    public ${entity.name}Dao(EntityManager em) {
        super(em, ${entity.name}.class);
    }
}