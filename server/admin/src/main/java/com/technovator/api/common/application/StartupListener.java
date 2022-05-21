package com.technovator.api.common.application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class StartupListener{
	private static final Logger LOG = LoggerFactory.getLogger(StartupListener.class);
	@Inject
	private CommonAppInitializer commonInitializer;

	@Inject
	private AppInitializer appInitializer;
    void onStart(@Observes StartupEvent ev) {               
		LOG.info("Initializing application...");
		commonInitializer.onStart();
		appInitializer.onStart();
		LOG.info("Application initialization completed");
    }

    void onStop(@Observes ShutdownEvent ev) {               
		LOG.info("Closing application");
		commonInitializer.onStop();
		appInitializer.onStop();
    }
}
