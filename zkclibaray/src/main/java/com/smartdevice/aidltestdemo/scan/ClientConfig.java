package com.smartdevice.aidltestdemo.scan;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClientConfig {

	private static final String PREFERENCES_NAME = "com.zkc.smartsdk";

	private static Context mContext;

	private static SharedPreferences mSharedPreferences;

	private static Map<String, Object> configs = new HashMap<String, Object>();

	// private static PersistentCookieStore clientCookieStore;
	
	public static final String OPEN_SCAN = "open_scan";
	public static final String DATA_APPEND_ENTER = "data_append_enter";
	public static final String APPEND_RINGTONE = "append_ringtone";
	public static final String APPEND_VIBRATE = "append_vibate";
	public static final String CONTINUE_SCAN= "continue_scan";
	public static final String SCAN_REPEAT= "scan_repeat";
	public static final String RESET = "reset";


	private static void initDefaultValue() {

	}

	public static void init(Context context) {
		mContext = context;
		// clientCookieStore=new PersistentCookieStore(mContext);
		getSharedPreferences();
		if (mSharedPreferences != null) {
			initDefaultValue();
			try {
				Map<String, Object> maps = (Map<String, Object>) mSharedPreferences
						.getAll();
				for (Entry<String, Object> properites : maps.entrySet()) {
					configs.put(properites.getKey(), properites.getValue());
				}
			} catch (Exception e) {
				Log.e("init error:", e.toString());
			}
		}
	}

	/**
	 * 获取分享参数
	 * 
	 * @return
	 */
	private static synchronized SharedPreferences getSharedPreferences() {
		if (mSharedPreferences == null) {
			if (mContext != null) {
				mSharedPreferences = mContext.getSharedPreferences(
						PREFERENCES_NAME, Context.MODE_PRIVATE);
			} else {
				Log.e("SharedPreferencesNUll", "getSharedPreferences error:mContext is null");
			}
		}
		return mSharedPreferences;
	}

	public static boolean hasValue(String key) {
		if (mSharedPreferences != null) {
			return mSharedPreferences.contains(key);
		}
		return false;
	}

	public static String getString(String key) {
		return getString(key, "");
	}

	public static String getString(String key, String defaultValue) {
		String msg = defaultValue;
		try {
			Object obj = configs.get(key);
			msg = obj == null ? "" : obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static int getInt(String key, int defaultValue) {
		int msg = defaultValue;
		try {
			Object obj = configs.get(key);
			if (obj != null) {
				if (obj instanceof Integer) {
					msg = (Integer) obj;
				} else {
					msg = Integer.valueOf(obj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static long getLong(String key, long defaultValue) {
		long msg = defaultValue;
		try {
			Object obj = configs.get(key);
			if (obj != null) {
				if (obj instanceof Long) {
					msg = (Long) obj;
				} else {
					msg = Long.valueOf(obj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static long getLong(String key) {
		return getLong(key, 0);
	}

	public static float getFloat(String key, float defaultValue) {
		float msg = defaultValue;
		try {
			Object obj = configs.get(key);
			if (obj != null) {
				if (obj instanceof Float) {
					msg = (Float) obj;
				} else {
					msg = Float.valueOf(obj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static float getFloat(String key) {
		return getFloat(key, 0);
	}

	public static Double getDouble(String key, double defaultValue) {
		double msg = defaultValue;
		try {
			Object obj = configs.get(key);
			if (obj != null) {
				if (obj instanceof Double) {
					msg = (Double) obj;
				} else {
					msg = Double.valueOf(obj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static Double getDouble(String key) {
		return getDouble(key, 0);
	}

	public static Boolean getBoolean(String key, boolean defaultValue) {
		boolean msg = defaultValue;
		try {
			Object obj = configs.get(key);
			if (obj != null) {
				msg = Boolean.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static Boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public static void setValue(String key, Object value) {
		try {
			configs.put(key, value);
			SharedPreferences mSharedPreferences = getSharedPreferences();
			Editor edit = mSharedPreferences.edit();
			if (null == value) {
				edit.remove(key);
			} else {
				if (value instanceof String || value instanceof Double) {
					edit.putString(key, value.toString());
				} else if (value instanceof Integer) {
					edit.putInt(key, (Integer) value);
				} else if (value instanceof Long) {
					edit.putLong(key, (Long) value);
				} else if (value instanceof Float) {
					edit.putFloat(key, (Float) value);
				} else if (value instanceof Boolean) {
					edit.putBoolean(key, (Boolean) value);
				}
			}
			edit.commit();
		} catch (Exception e) {
			Log.e("setValue error:", e.toString());
		}
	}

	// public static PersistentCookieStore getCookieStore(){
	// return clientCookieStore;
	// }

	public static void clearAccount() {

	}
}
