package com.github.cordb.bluebot.message;

import com.lmax.disruptor.EventFactory;

public class Message {

	private static final org.slf4j.ext.XLogger LOGGER = org.slf4j.ext.XLoggerFactory.getXLogger(Message.class);

	public static final EventFactory<Message> EVENT_FACTORY = new EventFactory<Message>() {
		@Override
		public Message newInstance() {
			return new Message();
		}
	};

	private final byte[] buffer = new byte[1500];
	private int length;

	public byte[] getBuffer() {
		return buffer;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void reset() {
		length = 0;
	}
}
