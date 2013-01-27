package com.github.cordb.bluebot.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.github.cordb.bluebot.autotype.AutoTypeReceiver;
import com.github.cordb.bluebot.message.Message;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class BluebotHandlerThread extends Thread {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BluebotHandlerThread.class);

	private final StreamConnectionNotifier serverSocket;
	private final RingBuffer<Message> disruptor;

	private volatile boolean stopRequested = false;
	private final CountDownLatch exitLatch = new CountDownLatch(1);

	@SuppressWarnings("unchecked")
	public BluebotHandlerThread(StreamConnectionNotifier notifier) {
		this.serverSocket = notifier;
		Disruptor<Message> disruptor = new Disruptor<Message>(Message.EVENT_FACTORY, 256, Executors.newCachedThreadPool());
		disruptor.handleEventsWith(new AutoTypeReceiver());
		this.disruptor = disruptor.start();
		super.setName("Bluebot Handler Thread");
	}

	public void run() {
		LOGGER.entry();
		try {
			while (!stopRequested) {
				StreamConnection streamConnection = waitForConnection();
				if (streamConnection == null) {
					continue;
				}
				processConnection(streamConnection);
			}
		} finally {
			exitLatch.countDown();
			LOGGER.exit();
		}
	}

	public void stopReadThread() throws InterruptedException {
		LOGGER.entry();
		this.stopRequested = true;
		this.interrupt();
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			LOGGER.catching(e);
		}
		exitLatch.await();
		LOGGER.exit();
	}

	protected StreamConnection waitForConnection() {
		LOGGER.entry();

		while (!stopRequested) {
			try {
				StreamConnection conn = serverSocket.acceptAndOpen();
				LOGGER.exit(conn);
				return conn;

			} catch (IOException e) {
				LOGGER.catching(e);
			}
		}

		LOGGER.exit(null);
		return null;
	}

	protected void processConnection(StreamConnection connection) {
		LOGGER.entry(connection);

		if (stopRequested) {
			LOGGER.exit();
			return;
		}

		DataInputStream dis;
		try {
			dis = connection.openDataInputStream();
		} catch (IOException e) {
			LOGGER.catching(e);
			LOGGER.exit();
			return;
		}

		boolean connectionOpen = true;

		try {
			while (!stopRequested && connectionOpen) {

				// Get ready to publish the message
				long sequence = disruptor.next();
				Message event = disruptor.get(sequence);
				event.reset();

				// Read data from the socket, place into the event
				// This will publish the message when "-1" is returned as well.
				int len = dis.read(event.getBuffer());
				event.setLength(len);

				// Publish the event
				disruptor.publish(sequence);

				if (len == -1) {
					connectionOpen = false;
				}
			}

		} catch (IOException e) {
			LOGGER.catching(e);
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				LOGGER.catching(e);
			}
		}

		LOGGER.exit();
	}

}