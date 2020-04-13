package ${package};

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Optional;

@Path("/${entity.name?lower_case}")
@Produces(MediaType.APPLICATION_JSON)
public class ${entity.name}Resource {
    private final ${entity.name}AppDao dao;

    public ${entity.name}Resource(${entity.name}AppDao dao) {
        this.dao = dao;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response create(${entity.name} entity) throws URISyntaxException  {
        long id = dao.save(entity);
        return Response.created(new URI("/${entity.name?lower_case}/" + id)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    public List<${entity.name}> getAll() {
        return dao.getAll();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public ${entity.name} get(@PathParam("id") LongParam id) {
        return verifyNotNull(
                dao.findById(id.get())
        );
    }

    @PUT
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response update(@PathParam("id") LongParam id, ${entity.name} entity) {
        if(entity.getId() != id.get()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        dao.save(entity);
        return Response.accepted().build();
    }

    @DELETE
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response delete(@PathParam("id") LongParam id) {
        dao.delete(
                verifyNotNull(
                        dao.findById(id.get())
                )
        );
        return Response.accepted().build();
    }

    private ${entity.name} verifyNotNull(${entity.name} entity) {
        if(entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return entity;
    }
}
