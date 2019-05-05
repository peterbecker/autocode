package ${package};

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/${entity.name}")
@Produces(MediaType.APPLICATION_JSON)
public class ${entity.name}Resource {
    private final ${entity.name}Dao dao;

    public ${entity.name}Resource(${entity.name}Dao dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    public ${entity.name} get() {
        return null;
    }
}
