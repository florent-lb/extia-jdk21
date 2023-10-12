package dev.flb.infra;

import dev.flb.service.AccessService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("security")
public class SecureAccessResources {


    @Inject
    AccessService accessService;


    @POST
    @RunOnVirtualThread
    @Path("virtual")
    public boolean isAuthorizeOnVirtual() throws InterruptedException {
        return accessService.checkIfAuthorized();
    }

    @POST
    @RunOnVirtualThread
    @Path("normal")
    public boolean isAuthorize() throws InterruptedException {
        return accessService.checkIfAuthorized();
    }

    @POST
    @RunOnVirtualThread
    @Path("virtual/multi")
    public boolean isAuthorizeOnVirtualMulti() throws InterruptedException {
        return accessService.checkIfAuthorizedMulti();
    }

    @POST
    @RunOnVirtualThread
    @Path("normal/multi")
    public boolean isAuthorizeMulti() throws InterruptedException {
        return accessService.checkIfAuthorizedMulti();
    }

}
