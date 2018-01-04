package mustabelmo.exception.handler.test;

import java.util.Scanner;

import org.junit.Test;

import junit.framework.TestCase;
import mustabelmo.exception.handler.TryCatcherFinally;
import mustabelmo.exception.handler.functional.CatchBlock;
import mustabelmo.exception.handler.functional.FinallyBlock;
import mustabelmo.exception.handler.functional.TryBlock;

public class TryCatcherFinallyTest extends TestCase {

	public static final String MESSAGE = "here is your exception";
	public static final String MESSAGE_NO_EXCEPTION = "no previous IO Exception";

	@Test
	public void testUniqueTryCatchBlock() {
		final boolean[] check = { false };
		TryCatcherFinally.tryBlock(() -> {
			throw new Exception(MESSAGE);
		} , throwable -> {
			throwable.printStackTrace();
			check[0] = throwable.getMessage().equals(MESSAGE);
		});
		assertEquals(check[0], true);
	}

	@Test
	public void testUniqueTryCatchBlockWithConstructor() {
		final boolean[] check = { false };
		final TryBlock tryBlock = () -> {
			throw new Exception(MESSAGE);
		};
		final CatchBlock catchBlock = throwable -> {
			throwable.printStackTrace();
			check[0] = true;
		};

		TryCatcherFinally.tryBlock(tryBlock, catchBlock);
		assertEquals(check[0], true);
	}

	@Test
	public void testWithoutTryBlock() {
		final boolean[] check = { true };

		final TryBlock tryBlock = () -> {
			// don't throw any exception from here

		};
		final CatchBlock catchBlock = throwable -> {
			check[0] = false;
			throwable.printStackTrace();
		};
		TryCatcherFinally.tryBlock(tryBlock, catchBlock);
		assertEquals(check[0], true);
	}

	@Test
	public void testUniqueTryCatchFinallyBlock() {
		final boolean[] check = { false };
		final Scanner sc = new Scanner(System.in);
		TryCatcherFinally.tryBlockFinalized(() -> {
			throw sc.ioException() != null ? sc.ioException() : new Exception(MESSAGE_NO_EXCEPTION);
		}, throwable -> {
			throwable.printStackTrace();
			check[0] = throwable.getMessage().equals(MESSAGE_NO_EXCEPTION);
		}, () -> {
			sc.close();
		});
		assertEquals(check[0], true);
	}

	@Test
	public void testUniqueTryCatchFinalyBlockWithConstructor() {
		final boolean[] check = { false };
		final Scanner sc = new Scanner(System.in);
		final TryBlock tryBlock = () -> {
			throw sc.ioException() != null ? sc.ioException() : new Exception(MESSAGE_NO_EXCEPTION);
		};
		final CatchBlock catchBlock = throwable -> {
			throwable.printStackTrace();
			check[0] = throwable.getMessage().equals(MESSAGE_NO_EXCEPTION);
		};
		final FinallyBlock finallyBlock = () -> {
			sc.close();
		};
		
		TryCatcherFinally.tryBlockFinalized(tryBlock, catchBlock, finallyBlock);
		assertEquals(check[0], true);
	}

	@Test
	public void testCatchFinallyWithNoTryBlock() {
		final boolean[] check = { true };
		final Scanner sc = new Scanner(System.in);
		final TryBlock tryBlock = () -> {
			
		};
		final CatchBlock catchBlock = throwable -> {
			throwable.printStackTrace();
			check[0] = false;
		};
		final FinallyBlock finallyBlock = () -> {
			sc.close();
		};
		TryCatcherFinally.tryBlockFinalized(tryBlock, catchBlock, finallyBlock);
		assertEquals(check[0], true);
	}
}
