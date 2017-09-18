package com.smartdevice.aidl;

import com.smartdevice.aidl.ICallBack;
import android.graphics.Bitmap;
import android.content.Context;

interface IZKCService
{

	/**
	* 设置当前功能模块
	* @param cb:	实现ICallback接口的回调
	*/
	boolean setModuleFlag(int module);
	/************************************************************
	 *                                                        ***
	 *                 打印机                                 ***
	 *                                                        ***
	 ************************************************************/

	 /**
	* 注册
	* @param cb:	实现ICallback接口的回调
	*/
	void registerCallBack(String flag,ICallBack callback);

	/**
	* 注销回调
	* @param cb:	实现ICallback接口的回调
	*/
	void unregisterCallBack(String flag,ICallBack callback);

	/**
	* 停止回调任务
	*/
	void stopRunningTask();

	/**
	* 判断回调任务是否正在运行
	*/
	boolean isTaskRunning();

	/**
	* 获取打印机固件版本
	*/
	void getFirmwareVersion();

	/**
	* 获取ZKCService服务版本
	*/
	String getServiceVersion();

	/**
	* 获取ZKCService服务版本
	*/
	int getDeviceModel();

	/**
	 * 初始化打印机
	 */
	void printerInit();

	/**
	* 获取打印机状态
	*/
	String getPrinterStatus();

	/**
	* 打印机自检，打印机会打印自检页
	*/
	void printerSelfChecking();

	/**
	* 检查打印机是否空闲
	*/
	boolean checkPrinterAvailable();

	/**
	* 使用原始指令打印
	* @param data 指令
	* @param callback  结果回调
	*/
	void sendRAWData(String flag,in byte[] data);

	/**
	* 设置对齐模式，对之后打印有影响，除非初始化
	* @param alignment:	对齐方式 0--居左, 1--居中, 2--居右
	* @param callback  结果回调
	*/
	void setAlignment(int alignment);

	/**
	* 设置打印字体, 对之后打印有影响，除非初始化
	* (目前只支持两种字体)
	* @param typeface:	字体类型 0--ASCII(12*24) 汉字（24*24），1--ASCII(8*16) 汉字（16*16）
	*/
	void setTypeface(int type);

	/**
	* 设置字体大小, 对之后打印有影响，除非初始化
	* 调整字体大小会影响字符宽度，每行字符数量也会随之改变，
	* @param fontsize:	字体大小类型 0--字符正常： 不放大，1--字符 2 倍高：纵向放大，
	*                                2--字符 2 倍宽：横向放大，3--字符 2 倍整体放大
	*/
	void setFontSize(int type);

	/**
	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
	* @param text:	要打印的文字字符串
	*/
	void printGBKText(String text);

	/**
	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
	* @param text:	要打印的文字字符串
	*/
	void printUnicodeText(String text);

	/**
	* 打印指定字体的文本，字体设置只对本次有效
	* @param text:			要打印文字
	* @param typeface:		字体类型
	* @param fontsize:		字体大小	类型
	*/
	void printTextWithFont(String text, int type, int size);

	/**
	* 打印指定字体,字号、对齐方式的文本，字体设置只对本次有效
	* @param text:			要打印文字
	* @param typeface:		字体类型
	* @param fontsize:		字体大小	类型
	* @param alginStyle:	对齐方式(0居左, 1居中, 2居右)
	*/
	void printTextAlgin(String text, int type, int size, int alginStyle);

	/**
	* 打印表格的一行，可以指定列宽、对齐方式
	* @param colsTextArr   各列文本字符串数组
	* @param colsWidthArr  各列宽度数组(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0)
	* @param colsAlign	        各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
	*/
	void printColumnsText(in String[] colsTextArr, in int[] colsWidthArr, in int[] colsAlign);

	/**
	* 打印图片
	* @param bitmap: 	图片bitmap对象
	*/
	void printBitmap(in Bitmap bitmap);

	/**
	* 打印图片（含对齐方式）
	* @param bitmap: 	图片bitmap对象
	* @parm position:   图片位置 0--居左，1--居中，2--居右
	*/
	void printBitmapAlgin(in Bitmap bitmap, int width, int height,int position);

	/**
	* 创建一维条码图片
	* @param data: 		条码数据
	* @param symbology: 	条码类型
	*    0 -- UPC-A，
	*    1 -- UPC-E，
	*    2 -- JAN13(EAN13)，
	*    3 -- JAN8(EAN8)，
	*    4 -- CODE39，
	*    5 -- ITF，
	*    6 -- CODABAR，
	*    7 -- CODE93，
	*    8 -- CODE128
	* @param height: 		条码高度, 取值1到255, 默认162
	* @param width: 		条码宽度, 取值0至5, 默认3
	* @param displayText:	是否显示文字
	*/
	Bitmap createBarCode(String data, int codeFormat, int width, int height, boolean displayText);

	/**
	* 创建二维条码图片
	* @param data:			二维码数据
	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	*/
	Bitmap createQRCode(String data, int width, int height);

	/********************************
	********添加日期 2017/03/14******
	*********************************/

	/*
	*打印不干胶获取间隙
	*/
	void generateSpace();

	/********************************
	********添加日期 2017/04/25******
	*********************************/

	/*
	*打印图片（灰度）
	*/
	boolean printImageGray(in Bitmap bitmap);

	/*
	*打印图片（光栅）
	*/
	boolean printRasterImage(in Bitmap bitmap);

	/*
	*打印UNINCODE（光栅）
	*/
	boolean printUnicode_1F30(String textStr);

	/*
	*设置打印语言
	*/
	void setPrintLanguage(String language);

	/*
	*获取固件版本
	*/
	String getFirmwareVersion1();

	/*
	*获取固件版本
	*/
	String getFirmwareVersion2();



	/************************************************************
	 *                                                        ***
	 *                 客显屏                                 ***
	 *                                                        ***
	 ************************************************************/

	 /**
	 * 控制屏背光电源
	 * @param btFlg 控制背光亮度 0===》亮；1===》不亮
	 */

	 void openBackLight(int btFlg);

	 /**
	 *  显示彩色图片，图片格式不限
	 * @param bitmapSrc 显示图片位图
	 */

	 boolean showRGB565Image(in Bitmap bitmapSrc);

	 /**
	 *  显示彩色图片，图片格式不限
	 * @param path 图片路径
	 */

	 boolean showRGB565ImageByPath(String path);

	 /**
	 * 指定位置显示彩色图片，图片格式不限
	 * @param bitmapSrc
	 *            图片
	 * @param x
	 *            起点X坐标
	 * @param y
	 *            起点Y坐标
	 * @param width
	 *            显示宽度
	 * @param height
	 *            显示高度
	 * @return
	 */

	 boolean showRGB565ImageLocation(in Bitmap bitmapSrc,int x, int y, int width, int height);

	 /**
	 * 更新屏幕LOGO
	 * @param bitmapSrc
	 * @return
	 */

	 boolean updateLogo(in Bitmap bitmapSrc);

	 /**
	 * 更新屏幕LOGO
	 * @param path
	 * @return
	 */

	 boolean updateLogoByPath(String path);

	 /**
	 * 显示双色图片
	 *
	 * @param BackColor
	 * @param ForeColor
	 * @param bitmapSrc
	 * @return
	 */

	 boolean showDotImage(int BackColor, int ForeColor,in Bitmap bitmapSrc);

	  /**
	 * 屏幕中心显示彩色图片
	 * @param bitmapSrc
	 * @return
	 */

	 boolean showRGB565ImageCenter(in Bitmap bitmapSrc);

	 /************************************************************
	 *                                                        ***
	 *                	PSAM卡                                ***
	 *                                                        ***
	 ************************************************************/

	/**
	* 打开PSAM电源
	*/

	int Open();

	/**
	* 关闭PSAM电源
	*/

	int Close(long fd);

	/**
	* 打开/关闭GPIO口
	* @return  true--success; false--fail
	*/

	boolean setGPIO(int io,int status);

	/**
	* 打开指定PSAM卡
	* @param carPositin: PSAM卡位置
	* @return 返回0成功
	*/

	int openCard(int carPositin);

	/**
	 * 打开设备2
	 *
	 * @param 卡槽编号
	 */

	int openCard2(inout int[] fd,int slotno);

	/**
	 * 打开设备3
	 *
	 * @param 卡槽编号
	 */

	int openCard3(long fd,int slotno);

	/**
	* 关闭打开的PSAM卡
	* @return 返回0成功
	*/
	int CloseCard();

	/**
	 *功能：	关闭设备2
	 *c参数 v2 true:设备3/设备2
	 *参数： [in]unsigned long fd传入要关闭的设备句柄
	 *返回值：正确为0，错误为非0
	*/

	int CloseCard2(long fd, boolean v2);

	/**
	* 重置当前打开的PSAM卡
	* @param power: 指定的电压大小
	* @return 以字节数组返回PSAM卡号
	*/
	byte[] ResetCard(int power);

	/**
	 *功能：	设备复位2
	 *参数： [in]unsigned long fd传入要关闭的设备句柄
	 *       [out]unsigned char *atr 传出设备复位信息
	 *       [in/out]int *atrLen 传出设备复位信息长度
	 *返回值：正确为0，错误为非0
	*/

	int ResetCard2(long fd, inout byte[] atr,inout int[] atrLen);

	/**
	 *功能：	设备复位3
	 *参数： [in]unsigned long fd传入要关闭的设备句柄
	 *       [out]unsigned char *atr 传出设备复位信息
	 *       [in/out]int *atrLen 传出设备复位信息长度
	 *返回值：正确为0，错误为非0
	*/

	byte[] ResetCard3(long fd,int slotno,int pw);

	/**
	* 给当前PSAM卡发送APDU命令
	* @param apdu: APDU命令
	* @return 以字节数组返回命令结果
	*/
	byte[] CardApdu(in byte[] apdu);

	/**
	 *功能：	发送APDU指令2
	 *参数： [in]unsigned long fd传入设备句柄
	 *     [in]unsigned char *apdu 要发送的apdu指令
	 *     [in]int apduLength 要发送的apdu指令长度
	 *     [out]unsigned char*response 返回数据内容
	 *     [in/out]int* respLength 返回数据长度
	 *返回值：正确为0，错误为非0
	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
	*/

	int CardApdu2(long fd, in byte[] apdu,int apduLength, inout byte[] response,inout int[] respLength);

	/**
	 *功能：	发送APDU指令3
	 *参数： [in]unsigned long fd传入设备句柄
	 *     [in]unsigned char *apdu 要发送的apdu指令
	 *     [in]int apduLength 要发送的apdu指令长度
	 *     [out]unsigned char*response 返回数据内容
	 *     [in/out]int* respLength 返回数据长度
	 *返回值：正确为0，错误为非0
	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
	*/

	byte[] CardApdu3(long fd, in byte[] apdu,int apduLength);

	/************************************************************
	 *                                                        ***
	 *                	扫描                                  ***
	 *                                                        ***
	 ************************************************************/

	/**
	* 打开扫描
	* @param status: true:打开；false:关闭
	*/
	void openScan(boolean status);

	/**
	* 扫描
	*/
	void scan();

	/**
	* 数据末尾追加回车
	* @param status: true:追加；false:不追加
	*/
	void dataAppendEnter(boolean  status);

	/**
	* 扫描成功提示音
	* @param status:  true:需要；false:不需要
	*/
	void appendRingTone(boolean status);

	/**
	* 连续扫描
	* @param status: true:需要；false:不需要
	*/
	void continueScan(boolean status);

	/**
	* 扫码重复提示
	* @param status: true:提示；false:不提示
	*/
	void scanRepeatHint(boolean status);

	/**
	* 恢复出厂设置
	* @param status: true:设置生效；false:设置不生效
	*/
	void recoveryFactorySet(boolean status);

	/**
	* 发送扫描指令
	* @param byte[] 发送的指令数据
	* @return 接收的结果
	*/
	byte[] sendCommand(in byte[] buffer);

	/**
	* 是否可以正常扫描
	* @return true:扫描已打开；false:扫描已关闭；
	*/
	boolean isScanning();

	/**
	* 获取身份证信息
	* @return 身份证信息
	*/
	String getIdentifyInfo();

}