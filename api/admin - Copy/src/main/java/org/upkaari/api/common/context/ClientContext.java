package org.upkaari.api.common.context;

import java.util.HashSet;
import java.util.Set;

/**
 * Common context for each request.
 * <p>
 * It contains the client/user roles and other information.
 *
 * @author joga.singh
 */
public class ClientContext {
	private String httpMethod; //=HttpMethod.POST.name();
    private String clientId;
    private Set<String> roles; // roles are not used at the moment
    private Set<String> scopes; //scopes are used for data access scope i.e. country
    private boolean isAuthenticated;
    private String requestUri;
    private static final ThreadLocal<ClientContext> instance = new ThreadLocal<ClientContext>() {
        @Override
        protected ClientContext initialValue() {
            ClientContext ctx = new ClientContext();
            return ctx;
        }
    };

    public void init() {
        //it gets called from interceptor, just to make sure to re-init the pooled object
        clientId = null;
        isAuthenticated = false;
        roles = new HashSet<>();
        scopes = new HashSet<>();
 /*       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        isAuthenticated = authentication.isAuthenticated() && !authentication.getName().equalsIgnoreCase("anonymoususer");
        for (GrantedAuthority role : authentication.getAuthorities()) {
            String authority = role.getAuthority();
            if (authority.startsWith("ROLE_")) {
                //roles are prefixed with ROLE_
                authority = authority.replace("ROLE_", "").toLowerCase();
                roles.add(authority);
            } else if (authority.startsWith("SCOPE_")) {
                //scopes are prefixed with SCOPE_
                authority = authority.replace("SCOPE_", "").toLowerCase();
                scopes.add(authority);
            }
        }

        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication o2auth = (OAuth2Authentication) authentication;
            this.clientId = o2auth.getOAuth2Request().getClientId();
        }
*/
    }

    /**
     * Is it an authenticated request.
     *
     * @return
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * Returns ClientContext.
     *
     * @return
     */
    public static ClientContext getInstance() {
        return instance.get();

    }

    /**
     * Returns true, if the user has any of the given role.
     *
     * @param roleAry
     * @return
     */
    public boolean hasAnyRole(String... roleAry) {
        for (String role : roleAry) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true, if the user has any of the given role.
     *
     * @param roleSet
     * @return
     */
    public boolean hasAnyRole(Set<String> roleSet) {
        for (String role : roleSet) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true, if the user has any of the given scope.
     *
     * @param scopes
     * @return
     */
    public boolean hasAnyScope(String... scopes) {
        for (String role : scopes) {
            if (this.scopes.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true, if the user has any of the given scope.
     *
     * @param scopes
     * @return
     */
    public boolean hasAnyScope(Set<String> scopes) {
        for (String role : scopes) {
            if (this.scopes.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the OAuth2 clientId used by the call.
     *
     * @return
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the list of roles, assigned to the current request user.
     *
     * @return
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Returns the list of scopes, assigned to the current request user.
     *
     * @return
     */
    public Set<String> getScopes() {
        return scopes;
    }

    public void clean() {
        instance.remove();
    }

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
    
}