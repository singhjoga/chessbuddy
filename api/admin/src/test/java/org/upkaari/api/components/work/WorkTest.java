package org.upkaari.api.components.work;

import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upkaari.api.CrudTest;
import org.upkaari.api._01controllers.WorkController;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
@QuarkusTest
public class WorkTest extends CrudTest<Work, String>{
	@TestHTTPEndpoint(WorkController.class)
	@TestHTTPResource
    URL url;
	
	public WorkTest() {
		super(Work.class, Work[].class);
	}
	@BeforeEach
	public void init() {
		super.setResourceUri(url.toString());
	}
    @Test
    public void testAdd() throws Exception {
    	super.testAdd();
    }
    protected Work create(String name, String description) {
    	Work obj = new Work();
    	obj.setName(name);
    	obj.setDescription(description);

    	return obj;	
    }
    protected Work create() {
    	return create("Work11", "Work1 Desc");
    }
}