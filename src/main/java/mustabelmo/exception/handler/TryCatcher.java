package mustabelmo.exception.handler;

import mustabelmo.exception.handler.functional.CatchBlock;
import mustabelmo.exception.handler.functional.TryBlock;

import java.util.List;

public class TryCatcher {

	public TryCatcher() {
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

	public static void tryBlockWithMultiCatches(TryBlock tryBlock, List<Pair> handlers) {
		try {
			tryBlock.perform();
		} catch (Throwable throwable) {
			multipleCatchBlocks(handlers);
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
