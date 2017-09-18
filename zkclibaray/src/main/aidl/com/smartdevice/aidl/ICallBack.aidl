/**
* Version: 1.0
*/
package com.smartdevice.aidl;

interface ICallBack
{
	/**
	 *接收回调消息
	 */
	void onReturnValue(in byte[] data, int size);
}
