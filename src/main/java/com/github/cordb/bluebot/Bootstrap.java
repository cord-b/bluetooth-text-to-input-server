package com.github.cordb.bluebot;

import java.io.IOException;

import com.github.cordb.bluebot.server.BluebotServer;

/**
 * Main class. Launches the server.
 */
public class Bootstrap {

	private static final org.slf4j.ext.XLogger LOGGER = org.slf4j.ext.XLoggerFactory.getXLogger(Bootstrap.class);

	public static void main(String[] args) throws InterruptedException, IOException {
		LOGGER.entry();

		BluebotServer server = new BluebotServer();
		server.start();

		LOGGER.exit();
	}

}
