package com.tencent.mm.u;

import android.app.Application;

import com.tencent.mm.u.callbacks.X_LoadPackage;
import com.tencent.mm.u.callbacks.X_LoadPackage.LoadPackageParam;

/**
 * IXposedHookLoadPackage
 *
 * Get notified when an app ("Android package") is loaded.
 * This is especially useful to hook some app-specific methods.
 *
 * <p>This interface should be implemented by the module's main class. Xposed will take care of
 * registering it as a callback automatically.
 */
public interface IXPHLoadPackage extends IXPMod {
	/**
	 * handleLoadPackage
	 *
	 * This method is called when an app is loaded. It's called very early, even before
	 * {@link Application#onCreate} is called.
	 * Modules can set up their app-specific hooks here.
	 *
	 * @param lpparam Information about the app.
	 * @throws Throwable Everything the callback throws is caught and logged.
	 */
	void hlp(LoadPackageParam lpparam) throws Throwable;

	/** @hide */
	final class Wrapper extends X_LoadPackage {
		private final IXPHLoadPackage instance;
		public Wrapper(IXPHLoadPackage instance) {
			this.instance = instance;
		}
		@Override
		public void hlp(LoadPackageParam lpparam) throws Throwable {
			instance.hlp(lpparam);
		}
	}
}
