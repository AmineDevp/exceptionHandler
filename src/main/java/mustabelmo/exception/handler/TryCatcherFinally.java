package mustabelmo.exception.handler;

import java.util.List;

import mustabelmo.exception.handler.functional.CatchBlock;
import mustabelmo.exception.handler.functional.FinallyBlock;
import mustabelmo.exception.handler.functional.TryBlock;

public class TryCatcherFinally {

	public TryCatcherFinally() {
	}

	public static void throwIfnoHandler(Throwable throwable) throws Throwable {
		throw throwable;
	}

	public static void tryBlock(TryBlock tryBlock, CatchBlock catchBlock) {
		try {
			tryBlock.perform();
		} catch (Throwable throwable) {
			catchBlock(throwable, catchBlock);
		}
	}

	public static void tryBlockFinalized(TryBlock tryBlock, CatchBlock catchBlock, FinallyBlock finallyBlock) {
		try {
			tryBlock.perform();
		} catch (Throwable throwable) {
			catchBlock(throwable, catchBlock);
		} finally {
			finallyBlock.finalize();
		}
	}

	public static void tryBlockWithMultiCatches(TryBlock tryBlock, List<Pair> handlers) {
		try {
			tryBlock.perform();
		} catch (Throwable throwable) {
			multipleCatchBlocks(handlers);
		}
	}

	public static void tryBlockWithMultiCatchesFinalized(TryBlock tryBlock, List<Pair> handlers,
			FinallyBlock finallyBlock) {
		try {
			tryBlock.perform();
		} catch (Throwable throwable) {
			multipleCatchBlocks(handlers);
		} finally {
			finallyBlock.finalize();
		}
	}

	private static void catchBlock(Throwable throwable, CatchBlock catchBlock) {
		catchBlock.handle(throwable);
	}

	private static void multipleCatchBlocks(List<Pair> handlers) {
		for (Pair pair : handlers) {
			pair.getHandler().handle(pair.getThrowable());
		}
	}

}
