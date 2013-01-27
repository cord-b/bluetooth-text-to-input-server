package com.github.cordb.bluebot.autotype;

import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_BACK_SLASH;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_COLON;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_GREATER;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LESS;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_QUOTE;
import static java.awt.event.KeyEvent.VK_QUOTEDBL;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.UnsupportedEncodingException;

import com.github.cordb.bluebot.message.Message;
import com.lmax.disruptor.EventHandler;

public class AutoTypeReceiver implements EventHandler<Message> {

	private static final org.slf4j.ext.XLogger LOGGER = org.slf4j.ext.XLoggerFactory.getXLogger(AutoTypeReceiver.class);

	private final Robot robot;
	private final int delay;

	public AutoTypeReceiver() {
		this(20);
	}
	
	public AutoTypeReceiver(int delay) {
		this.delay = delay;
		try {
			this.robot = new Robot();
			this.robot.setAutoDelay(1);
		} catch (AWTException e) {
			throw new IllegalStateException(e);
		}
	}

	public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.entry(event, sequence, endOfBatch);

		if (event.getLength() <= 0) {
			LOGGER.debug("No data");
			LOGGER.exit();
			return;
		}

		String text;
		try {
			byte[] data = event.getBuffer();
			int length = event.getLength();
			text = new String(data, 0, length, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.toString(), e);
			LOGGER.exit();
			return;
		}

		type(text);

		LOGGER.exit();
	}

	public void type(String string) {
		LOGGER.entry(string);

		int i = 0;
		while (i < string.length()) {
			int codePoint = string.codePointAt(i);
			i += Character.charCount(codePoint);
			type(codePoint);
		}

		LOGGER.exit();
	}

	protected void type(int codePoint) {
		LOGGER.entry(codePoint <= Character.MAX_VALUE ? (char) codePoint : codePoint);

		switch (codePoint) {
		case 'a':
			keyPress(VK_A);
			break;
		case 'b':
			keyPress(VK_B);
			break;
		case 'c':
			keyPress(VK_C);
			break;
		case 'd':
			keyPress(VK_D);
			break;
		case 'e':
			keyPress(VK_E);
			break;
		case 'f':
			keyPress(VK_F);
			break;
		case 'g':
			keyPress(VK_G);
			break;
		case 'h':
			keyPress(VK_H);
			break;
		case 'i':
			keyPress(VK_I);
			break;
		case 'j':
			keyPress(VK_J);
			break;
		case 'k':
			keyPress(VK_K);
			break;
		case 'l':
			keyPress(VK_L);
			break;
		case 'm':
			keyPress(VK_M);
			break;
		case 'n':
			keyPress(VK_N);
			break;
		case 'o':
			keyPress(VK_O);
			break;
		case 'p':
			keyPress(VK_P);
			break;
		case 'q':
			keyPress(VK_Q);
			break;
		case 'r':
			keyPress(VK_R);
			break;
		case 's':
			keyPress(VK_S);
			break;
		case 't':
			keyPress(VK_T);
			break;
		case 'u':
			keyPress(VK_U);
			break;
		case 'v':
			keyPress(VK_V);
			break;
		case 'w':
			keyPress(VK_W);
			break;
		case 'x':
			keyPress(VK_X);
			break;
		case 'y':
			keyPress(VK_Y);
			break;
		case 'z':
			keyPress(VK_Z);
			break;
		case 'A':
			keyPress(VK_SHIFT, VK_A);
			break;
		case 'B':
			keyPress(VK_SHIFT, VK_B);
			break;
		case 'C':
			keyPress(VK_SHIFT, VK_C);
			break;
		case 'D':
			keyPress(VK_SHIFT, VK_D);
			break;
		case 'E':
			keyPress(VK_SHIFT, VK_E);
			break;
		case 'F':
			keyPress(VK_SHIFT, VK_F);
			break;
		case 'G':
			keyPress(VK_SHIFT, VK_G);
			break;
		case 'H':
			keyPress(VK_SHIFT, VK_H);
			break;
		case 'I':
			keyPress(VK_SHIFT, VK_I);
			break;
		case 'J':
			keyPress(VK_SHIFT, VK_J);
			break;
		case 'K':
			keyPress(VK_SHIFT, VK_K);
			break;
		case 'L':
			keyPress(VK_SHIFT, VK_L);
			break;
		case 'M':
			keyPress(VK_SHIFT, VK_M);
			break;
		case 'N':
			keyPress(VK_SHIFT, VK_N);
			break;
		case 'O':
			keyPress(VK_SHIFT, VK_O);
			break;
		case 'P':
			keyPress(VK_SHIFT, VK_P);
			break;
		case 'Q':
			keyPress(VK_SHIFT, VK_Q);
			break;
		case 'R':
			keyPress(VK_SHIFT, VK_R);
			break;
		case 'S':
			keyPress(VK_SHIFT, VK_S);
			break;
		case 'T':
			keyPress(VK_SHIFT, VK_T);
			break;
		case 'U':
			keyPress(VK_SHIFT, VK_U);
			break;
		case 'V':
			keyPress(VK_SHIFT, VK_V);
			break;
		case 'W':
			keyPress(VK_SHIFT, VK_W);
			break;
		case 'X':
			keyPress(VK_SHIFT, VK_X);
			break;
		case 'Y':
			keyPress(VK_SHIFT, VK_Y);
			break;
		case 'Z':
			keyPress(VK_SHIFT, VK_Z);
			break;
		case '`':
			keyPress(VK_BACK_QUOTE);
			break;
		case '0':
			keyPress(VK_0);
			break;
		case '1':
			keyPress(VK_1);
			break;
		case '2':
			keyPress(VK_2);
			break;
		case '3':
			keyPress(VK_3);
			break;
		case '4':
			keyPress(VK_4);
			break;
		case '5':
			keyPress(VK_5);
			break;
		case '6':
			keyPress(VK_6);
			break;
		case '7':
			keyPress(VK_7);
			break;
		case '8':
			keyPress(VK_8);
			break;
		case '9':
			keyPress(VK_9);
			break;
		case '-':
			keyPress(VK_MINUS);
			break;
		case '=':
			keyPress(VK_EQUALS);
			break;
		case '~':
			keyPress(VK_SHIFT, VK_BACK_QUOTE);
			break;
		case '!':
			keyPress(VK_SHIFT, VK_1);
			break;
		case '@':
			keyPress(VK_SHIFT, VK_2);
			break;
		case '#':
			keyPress(VK_SHIFT, VK_3);
			break;
		case '$':
			keyPress(VK_SHIFT, VK_4);
			break;
		case '%':
			keyPress(VK_SHIFT, VK_5);
			break;
		case '^':
			keyPress(VK_SHIFT, VK_6);
			break;
		case '&':
			keyPress(VK_SHIFT, VK_7);
			break;
		case '*':
			keyPress(VK_SHIFT, VK_8);
			break;
		case '(':
			keyPress(VK_SHIFT, VK_9);
			break;
		case ')':
			keyPress(VK_SHIFT, VK_0);
			break;
		case '_':
			keyPress(VK_SHIFT, VK_MINUS);
			break;
		case '+':
			keyPress(VK_SHIFT, VK_EQUALS);
			break;
		case '\t':
			keyPress(VK_TAB);
			break;
		case '\n':
			keyPress(VK_ENTER);
			break;
		case '[':
			keyPress(VK_OPEN_BRACKET);
			break;
		case ']':
			keyPress(VK_CLOSE_BRACKET);
			break;
		case '\\':
			keyPress(VK_BACK_SLASH);
			break;
		case '{':
			keyPress(VK_SHIFT, VK_OPEN_BRACKET);
			break;
		case '}':
			keyPress(VK_SHIFT, VK_CLOSE_BRACKET);
			break;
		case '|':
			keyPress(VK_SHIFT, VK_BACK_SLASH);
			break;
		case ';':
			keyPress(VK_SEMICOLON);
			break;
		case ':':
			keyPress(VK_COLON);
			break;
		case '\'':
			keyPress(VK_QUOTE);
			break;
		case '"':
			keyPress(VK_QUOTEDBL);
			break;
		case ',':
			keyPress(VK_COMMA);
			break;
		case '<':
			keyPress(VK_LESS);
			break;
		case '.':
			keyPress(VK_PERIOD);
			break;
		case '>':
			keyPress(VK_GREATER);
			break;
		case '/':
			keyPress(VK_SLASH);
			break;
		case '?':
			keyPress(VK_SHIFT, VK_SLASH);
			break;
		case ' ':
			keyPress(VK_SPACE);
			break;
		default:
			System.out.println("no key");
			break;
		}

		LOGGER.exit();
	}

	protected void keyPress(int c) {
		robot.keyPress(c);
		robot.keyRelease(c);
		robot.delay(delay);
	}

	protected void keyPress(int mod, int c) {
		robot.keyPress(mod);
		robot.keyPress(c);
		robot.keyRelease(c);
		robot.keyRelease(mod);
		robot.delay(delay);
	}

}
