package at.jkopec.dezsys09.rest;


import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Named;

/**
 * Created by jakubkopec on 07.04.16.
 */
@Named
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        this.register(Register.class);
        this.register(Login.class);
        this.register(JacksonFeature.class);
    }
}
