package com.github.cordb.bluebot.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.github.cordb.bluebot.Bluebot;

public class BluebotServer {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BluebotServer.class);

	public static final String SERVER_URL = "btspp://localhost:" + Bluebot.UUID_STRING + ";name=bluebot";

	private final AtomicReference<BluebotHandlerThread> readThreadRef = new AtomicReference<BluebotHandlerThread>();

	public void start() throws IOException, BluetoothStateException {
		LOGGER.entry();

		synchronized (readThreadRef) {
			BluebotHandlerThread readThread = readThreadRef.get();
			if (readThread != null) {
				LOGGER.error("Server is alredy started");
				LOGGER.exit();
				return;
			}

			LocalDevice localDevice = LocalDevice.getLocalDevice();

			// Host the connection
			StreamConnectionNotifier serverSocket =
					(StreamConnectionNotifier) Connector.open(SERVER_URL);

			// Setup service record?
			ServiceRecord serviceRecord = localDevice.getRecord(serverSocket);
			LOGGER.debug("serviceRecord {}", serviceRecord);
			serviceRecord.setAttributeValue(3, new DataElement(DataElement.UUID, Bluebot.UUID));
			localDevice.updateRecord(serviceRecord);
			LOGGER.debug("serviceRecord {}", serviceRecord);

			readThread = new BluebotHandlerThread(serverSocket);
			readThread.start();
			readThreadRef.set(readThread);
		}

		LOGGER.exit();
	}

	public void stop() throws InterruptedException {
		LOGGER.entry();

		synchronized (readThreadRef) {
			BluebotHandlerThread readThread = readThreadRef.get();
			if (readThread == null) {
				LOGGER.error("Server is already stopped");
				LOGGER.exit();
				return;
			}
			readThreadRef.set(null);
			readThread.stopReadThread();
		}

		LOGGER.exit();
	}
}
