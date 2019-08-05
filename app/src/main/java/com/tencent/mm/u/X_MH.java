package com.tencent.mm.u;

import java.lang.reflect.Member;

import com.tencent.mm.u.callbacks.IXUh;
import com.tencent.mm.u.callbacks.XCallback;

/**
 * XC_MethodHook
 *
 * Callback class for method hooks.
 *
 * <p>Usually, anonymous subclasses of this class are created which override
 * {@link #bhm} and/or {@link #ahm}.
 */
public abstract class X_MH extends XCallback {
	/**
	 * Creates a new callback with default priority.
	 */
	@SuppressWarnings("deprecation")
	public X_MH() {
		super();
	}

	/**
	 * Creates a new callback with a specific priority.
	 *
	 * <p class="note">Note that {@link #ahm} will be called in reversed order, i.e.
	 * the callback with the highest priority will be called last. This way, the callback has the
	 * final control over the return value. {@link #bhm} is called as usual, i.e.
	 * highest priority first.
	 *
	 * @param priority See {@link XCallback#priority}.
	 */
	public X_MH(int priority) {
		super(priority);
	}

	/**
	 * Called before the invocation of the method.
	 *
	 * <p>You can use {@link mhp#setResult} and {@link mhp#setThrowable}
	 * to prevent the original method from being called.
	 *
	 * <p>Note that implementations shouldn't call {@code super(param)}, it's not necessary.
	 *
	 * @param param Information about the method call.
	 * @throws Throwable Everything the callback throws is caught and logged.
	 */
	protected void bhm(mhp param) throws Throwable {}

	/**
	 * Called after the invocation of the method.
	 *
	 * <p>You can use {@link mhp#setResult} and {@link mhp#setThrowable}
	 * to modify the return value of the original method.
	 *
	 * <p>Note that implementations shouldn't call {@code super(param)}, it's not necessary.
	 *
	 * @param param Information about the method call.
	 * @throws Throwable Everything the callback throws is caught and logged.
	 */
	protected void ahm(mhp param) throws Throwable {}

	/**
     * MethodHookParam
     *
	 * Wraps information about the method call and allows to influence it.
	 */
	public static final class mhp extends XCallback.Param {
		/** @hide */
		@SuppressWarnings("deprecation")
		public mhp() {
			super();
		}

		/** The hooked method/constructor. */
		public Member method;

		/** The {@code this} reference for an instance method, or {@code null} for static methods. */
		public Object thisObject;

		/** Arguments to the method call. */
		public Object[] args;

		private Object result = null;
		private Throwable throwable = null;
		/* package */ boolean returnEarly = false;

		/** Returns the result of the method call. */
		public Object getResult() {
			return result;
		}

		/**
		 * Modify the result of the method call.
		 *
		 * <p>If called from {@link #bhm}, it prevents the call to the original method.
		 */
		public void setResult(Object result) {
			this.result = result;
			this.throwable = null;
			this.returnEarly = true;
		}

		/** Returns the {@link Throwable} thrown by the method, or {@code null}. */
		public Throwable getThrowable() {
			return throwable;
		}

		/** Returns true if an exception was thrown by the method. */
		public boolean hasThrowable() {
			return throwable != null;
		}

		/**
		 * Modify the exception thrown of the method call.
		 *
		 * <p>If called from {@link #bhm}, it prevents the call to the original method.
		 */
		public void setThrowable(Throwable throwable) {
			this.throwable = throwable;
			this.result = null;
			this.returnEarly = true;
		}

		/** Returns the result of the method call, or throws the Throwable caused by it. */
		public Object getResultOrThrowable() throws Throwable {
			if (throwable != null)
				throw throwable;
			return result;
		}
	}

	/**
     * uh
     *
	 * An object with which the method/constructor can be unhooked.
	 */
	public class uh implements IXUh<X_MH> {
		private final Member hookMethod;

		/*package*/ uh(Member hookMethod) {
			this.hookMethod = hookMethod;
		}

		/**
         * getHookedMethod
         *
		 * Returns the method/constructor that has been hooked.
		 */
		public Member ghm() {
			return hookMethod;
		}

		@Override
		public X_MH getCallback() {
			return X_MH.this;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void uh() {
			XPB.uhm(hookMethod, X_MH.this);
		}

	}
}
