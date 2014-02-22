package com.example.classlocus.data;

import android.os.Looper;

public class ThreadPreconditions {
	
	public static void checkOnMainThread() {
		if (com.example.classlocus.BuildConfig.DEBUG) {
			if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
				throw new IllegalStateException("This method should be called from the Main Thread");
			}
		}
	}
}