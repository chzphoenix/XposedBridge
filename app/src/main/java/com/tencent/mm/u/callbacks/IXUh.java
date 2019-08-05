package com.tencent.mm.u.callbacks;

import com.tencent.mm.u.IXPHZygoteInit;

/**
 * IXUnhook
 *
 * Interface for objects that can be used to remove callbacks.
 *
 * <p class="warning">Just like hooking methods etc., unhooking applies only to the current process.
 * In other process (or when the app is removed from memory and then restarted), the hook will still
 * be active. The Zygote process (see {@link IXPHZygoteInit}) is an exception, the hook won't
 * be inherited by any future processes forked from it in the future.
 *
 * @param <T> The class of the callback.
 */
public interface IXUh<T> {
	/**
	 * Returns the callback that has been registered.
	 */
	T getCallback();

	/**
	 * unHook
	 *
	 * Removes the callback.
	 */
	void uh();
}
