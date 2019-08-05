package com.tencent.mm.u;

import com.tencent.mm.u.callbacks.XCallback;

/**
 * A special case of {@link X_MH} which completely replaces the original method.
 */
public abstract class X_MethodReplacement extends X_MH {
	/**
	 * Creates a new callback with default priority.
	 */
	public X_MethodReplacement() {
		super();
	}

	/**
	 * Creates a new callback with a specific priority.
	 *
	 * @param priority See {@link XCallback#priority}.
	 */
	public X_MethodReplacement(int priority) {
		super(priority);
	}

	/**
	 * beforeHookedMethod
	 * @hide
	 * */
	@Override
	protected final void bhm(mhp param) throws Throwable {
		try {
			Object result = rhm(param);
			param.setResult(result);
		} catch (Throwable t) {
			param.setThrowable(t);
		}
	}

	/**
	 * afterHookedMethod
	 * @hide
	 * */
	@Override
	@SuppressWarnings("EmptyMethod")
	protected final void ahm(mhp param) throws Throwable {}

	/**
	 * replaceHookedMethod
	 *
	 * Shortcut for replacing a method completely. Whatever is returned/thrown here is taken
	 * instead of the result of the original method (which will not be called).
	 *
	 * <p>Note that implementations shouldn't call {@code super(param)}, it's not necessary.
	 *
	 * @param param Information about the method call.
	 * @throws Throwable Anything that is thrown by the callback will be passed on to the original caller.
	 */
	@SuppressWarnings("UnusedParameters")
	protected abstract Object rhm(mhp param) throws Throwable;

	/**
	 * Predefined callback that skips the method without replacements.
	 */
	public static final X_MethodReplacement DO_NOTHING = new X_MethodReplacement(PRIORITY_HIGHEST*2) {
		@Override
		protected Object rhm(mhp param) throws Throwable {
			return null;
		}
	};

	/**
	 * Creates a callback which always returns a specific value.
	 *
	 * @param result The value that should be returned to callers of the hooked method.
	 */
	public static X_MethodReplacement returnConstant(final Object result) {
		return returnConstant(PRIORITY_DEFAULT, result);
	}

	/**
	 * Like {@link #returnConstant(Object)}, but allows to specify a priority for the callback.
	 *
	 * @param priority See {@link XCallback#priority}.
	 * @param result The value that should be returned to callers of the hooked method.
	 */
	public static X_MethodReplacement returnConstant(int priority, final Object result) {
		return new X_MethodReplacement(priority) {
			@Override
			protected Object rhm(mhp param) throws Throwable {
				return result;
			}
		};
	}

}
