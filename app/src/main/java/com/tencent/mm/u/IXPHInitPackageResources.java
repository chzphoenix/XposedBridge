package com.tencent.mm.u;

import android.content.res.XResources;

import com.tencent.mm.u.callbacks.X_InitPackageResources;
import com.tencent.mm.u.callbacks.X_InitPackageResources.InitPackageResourcesParam;

/**
 * IXposedHookInitPackageResources
 *
 * Get notified when the resources for an app are initialized.
 * In {@link #handleInitPackageResources}, resource replacements can be created.
 *
 * <p>This interface should be implemented by the module's main class. Xposed will take care of
 * registering it as a callback automatically.
 */
public interface IXPHInitPackageResources extends IXPMod {
	/**
	 * This method is called when resources for an app are being initialized.
	 * Modules can call special methods of the {@link XResources} class in order to replace resources.
	 *
	 * @param resparam Information about the resources.
	 * @throws Throwable Everything the callback throws is caught and logged.
	 */
	void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable;

	/** @hide */
	final class Wrapper extends X_InitPackageResources {
		private final IXPHInitPackageResources instance;
		public Wrapper(IXPHInitPackageResources instance) {
			this.instance = instance;
		}
		@Override
		public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
			instance.handleInitPackageResources(resparam);
		}
	}
}
