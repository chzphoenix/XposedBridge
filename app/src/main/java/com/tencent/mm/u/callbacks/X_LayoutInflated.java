package com.tencent.mm.u.callbacks;

import android.content.res.XResources;
import android.content.res.XResources.ResourceNames;
import android.view.View;

import com.tencent.mm.u.XPB.CopyOnWriteSortedSet;

/**
 * Callback for hooking layouts. Such callbacks can be passed to {@link XResources#hl}
 * and its variants.
 */
public abstract class X_LayoutInflated extends XCallback {
	/**
	 * Creates a new callback with default priority.
	 */
	@SuppressWarnings("deprecation")
	public X_LayoutInflated() {
		super();
	}

	/**
	 * Creates a new callback with a specific priority.
	 *
	 * @param priority See {@link XCallback#priority}.
	 */
	public X_LayoutInflated(int priority) {
		super(priority);
	}

	/**
	 * Wraps information about the inflated layout.
	 */
	public static final class LayoutInflatedParam extends XCallback.Param {
		/** @hide */
		public LayoutInflatedParam(CopyOnWriteSortedSet<X_LayoutInflated> callbacks) {
			super(callbacks);
		}

		/** The view that has been created from the layout. */
		public View view;

		/** Container with the ID and name of the underlying resource. */
		public ResourceNames resNames;

		/** Directory from which the layout was actually loaded (e.g. "layout-sw600dp"). */
		public String variant;

		/** Resources containing the layout. */
		public XResources res;
	}

	/** @hide */
	@Override
	protected void call(Param param) throws Throwable {
		if (param instanceof LayoutInflatedParam)
			handleLayoutInflated((LayoutInflatedParam) param);
	}

	/**
	 * This method is called when the hooked layout has been inflated.
	 *
	 * @param liparam Information about the layout and the inflated view.
	 * @throws Throwable Everything the callback throws is caught and logged.
	 */
	public abstract void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable;

	/**
	 * Unhook
	 *
	 * An object with which the callback can be removed.
	 */
	public class uh implements IXUh<X_LayoutInflated> {
		private final String resDir;
		private final int id;

		/** @hide */
		public uh(String resDir, int id) {
			this.resDir = resDir;
			this.id = id;
		}

		/**
		 * Returns the resource ID of the hooked layout.
		 */
		public int getId() {
			return id;
		}

		@Override
		public X_LayoutInflated getCallback() {
			return X_LayoutInflated.this;
		}

		@Override
		public void uh() {
			XResources.uhl(resDir, id, X_LayoutInflated.this);
		}

	}
}
