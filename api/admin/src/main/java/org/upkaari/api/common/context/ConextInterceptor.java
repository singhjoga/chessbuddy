package org.upkaari.api.common.context;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * Interceptor for initializing the Client Context
 * specific data access.
 * 
 * @author joga.singh
 *
 */

@Provider
public class ConextInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext context) {
    	ClientContext.getInstance().init(context);
    }
}