package at.jkopec.dezsys09.rest;

import at.jkopec.dezsys09.db.CrudInterface;


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
@Path("/login")
@Produces({MediaType.APPLICATION_JSON})
public class Login {

    @Inject
    private CrudInterface crudInterface;


    @POST
    public Response login(at.jkopec.dezsys09.db.User user) {
        if (user.getEmail() == null)
            return Response.status(Response.Status.FORBIDDEN).entity("No email specified!").type(MediaType.TEXT_PLAIN).build();

        at.jkopec.dezsys09.db.User saved_user = crudInterface.findOne(user.getEmail());
        if (saved_user != null && saved_user.getPassword().equals(user.getPassword())) {
            return Response.status(Response.Status.OK).entity("Welcome " + saved_user.getName() + "!").type(MediaType.TEXT_PLAIN).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid account data!").type(MediaType.TEXT_PLAIN).build();
        }
    }
}
