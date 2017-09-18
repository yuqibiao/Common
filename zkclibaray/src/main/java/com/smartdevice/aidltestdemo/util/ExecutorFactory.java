package com.smartdevice.aidltestdemo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FileName : ExecutorFactory.java Description :
 **/
public class ExecutorFactory {

	private static ExecutorService EXCUTOR_LOGIC_POOL = Executors
			.newFixedThreadPool(10);

	private static ExecutorService EXCUTOR_THREAD_POOL = Executors
			.newFixedThreadPool(10);

	private ExecutorFactory() {
	}

	public static void executeLogic(Runnable runnable) {
		if (runnable != null) {
			EXCUTOR_LOGIC_POOL.execute(runnable);
		}
	}

	public static void executeThread(Runnable runnable) {
		if (runnable != null) {
			EXCUTOR_THREAD_POOL.execute(runnable);
		}
	}
}
