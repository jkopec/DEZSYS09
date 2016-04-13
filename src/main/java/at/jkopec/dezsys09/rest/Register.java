package at.jkopec.dezsys09.rest;

import at.jkopec.dezsys09.db.CrudInterface;
import at.jkopec.dezsys09.db.User;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jakubkopec on 06.04.16.
 */
@Named
@Path("/register")
@Produces({MediaType.APPLICATION_JSON})
public class Register {

    @Inject
    private CrudInterface crudInterface;

    @POST
    public Response register(User user) {
        try {
            if (!crudInterface.exists(user.getEmail())) {
                User saved_user = this.crudInterface.save(user);
                return Response.status(Response.Status.CREATED).entity("User " + saved_user.getEmail() + " saved!").type(MediaType.TEXT_PLAIN).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("User " + user.getEmail() + " already exists!").type(MediaType.TEXT_PLAIN).build();
            }
        } catch (TransactionSystemException | InvalidDataAccessApiUsageException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameters!").type(MediaType.TEXT_PLAIN).build();
        }
    }
}
