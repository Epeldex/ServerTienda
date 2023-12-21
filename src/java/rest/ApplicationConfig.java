package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * This is the User management RESTful web service application class.
 *
 * @author
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     * Gets classes for web service application resources.
     *
     * @return A Set containing Class objects for resources.
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Adds needed resource's classes to a Set of resources.
     *
     * @param resources The resource's classes Set.
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(rest.AdminREST.class);
        resources.add(rest.CustomerREST.class);
        resources.add(rest.ProductREST.class);
        resources.add(rest.ProductsBoughtREST.class);
        resources.add(rest.SupplierREST.class);
        resources.add(rest.TagREST.class);
        resources.add(rest.UserREST.class);
    }

}
