package com.smartdevice.aidltestdemo.printer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.os.SystemClock;

import com.smartdevice.aidl.IZKCService;
import com.smartdevice.aidltestdemo.R;
import com.smartdevice.aidltestdemo.printer.entity.GoodsInfo;
import com.smartdevice.aidltestdemo.printer.entity.SupermakerBill;

public class PrinterHelper {

	/* 等待打印缓冲刷新的时间 */
	private static final int mIzkcService_BUFFER_FLUSH_WAITTIME = 150;
	/* 分割线 */
	private static final String mIzkcService_CUT_OFF_RULE = "--------------------------------\n";

	// 品名占位长度
	private static final int GOODS_NAME_LENGTH = 6;
	// 单价占位长度
	private static final int GOODS_UNIT_PRICE_LENGTH = 6;
	// 价格占位长度
	private static final int GOODS_PRICE_LENGTH = 6;
	// 数量占位长度
	private static final int GOODS_AMOUNT = 6;

	private Context mContext;

	private static PrinterHelper _instance;

	private PrinterHelper(Context mContext) {
		this.mContext = mContext;
	}

	synchronized public static PrinterHelper getInstance(Context mContext) {
		if (null == _instance)
			_instance = new PrinterHelper(mContext);
		return _instance;
	}

	synchronized public void printPurchaseBillModelOne(
			IZKCService mIzkcService, SupermakerBill bill, int imageType) {

		try {
			if (mIzkcService!=null&&mIzkcService.checkPrinterAvailable()) {
				mIzkcService.printGBKText("\n\n");
				if (bill.start_bitmap != null) {
//					mIzkcService.printBitmapAlgin(bill.start_bitmap, 376, 120, 1);
					switch (imageType){
						case 0:
							mIzkcService.printBitmap(bill.start_bitmap);
							break;
						case 1:
							mIzkcService.printImageGray(bill.start_bitmap);
							break;
						case 2:
							mIzkcService.printRasterImage(bill.start_bitmap);
							break;
					}
				}
				SystemClock.sleep(50);
//				mIzkcService.printGBKText("\n");
				mIzkcService.printGBKText(bill.supermaker_name + "\n\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SERIAL_NUMBER_TAG
						+ bill.serial_number + "\t\t\n" + bill.purchase_time
						+ "\n");

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.GOODS_NAME_TAG
						+ "          " + PrintTag.PurchaseBillTag.GOODS_UNIT_PRICE_TAG
						+ "  " + PrintTag.PurchaseBillTag.GOODS_AMOUNT_TAG + "  "
						+ PrintTag.PurchaseBillTag.GOODS_PRICE_TAG + "  " + "\n");

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

				for (int i = 0; i < bill.goosInfos.size(); i++) {

					String space0 = "";
					String space1 = "";
					String space2 = "";
					String space3 = "";

					String name = bill.goosInfos.get(i).goods_name;
					String unit_price = bill.goosInfos.get(i).goods_unit_price;
					String amount = bill.goosInfos.get(i).goods_amount;
					String price = bill.goosInfos.get(i).goods_price;

					int name_length = name.length();
					int unit_price_length = unit_price.length();
					int amount_length = amount.length();
					int price_length = price.length();

					int space_length0 = GOODS_NAME_LENGTH - name_length;
					int space_length1 = GOODS_UNIT_PRICE_LENGTH
							- unit_price_length;
					int space_length2 = GOODS_AMOUNT - amount_length;
					int space_length3 = GOODS_PRICE_LENGTH - price_length;

					String name1 = "";
					String name2 = "";

					if (name_length > GOODS_NAME_LENGTH) {
						name1 = name.substring(0, 6);
						name2 = name.substring(6, name_length);

						for (int j = 0; j < space_length1; j++) {
							space1 += " ";
						}
						for (int j = 0; j < space_length2 - 1; j++) {
							space2 += " ";
						}

						mIzkcService
								.printGBKText(name1 + "  " + unit_price
										+ space1 + " " + amount + space2
										+ price + "\n");

						mIzkcService.printGBKText(name2 + "\n");

					} else {
						for (int j = 0; j < space_length0; j++) {
							space0 += "  ";
						}
						for (int j = 0; j < space_length1; j++) {
							space1 += " ";
						}
						for (int j = 0; j < space_length2 - 1; j++) {
							space2 += " ";
						}

						mIzkcService.printGBKText(name + space0 + "  "
								+ unit_price + space1 + " " + amount + space2
								+ price + "\n");
					}
				}

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.FAVOURABLE_CASH_TAG
						+ bill.favorable_cash + "\t\t"
						+ PrintTag.PurchaseBillTag.RECEIPT_CASH_TAG + bill.receipt_cash
						+ "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.RECEIVED_CASH_TAG
						+ bill.recived_cash + "\t\t"
						+ PrintTag.PurchaseBillTag.ODD_CHANGE_TAG + bill.odd_change
						+ "\n");
				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.VIP_NUMBER_TAG
						+ bill.vip_number + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.ADD_INTEGRAL_TAG
						+ bill.add_integral + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.CURRENT_INTEGRAL_TAG
						+ bill.current_integral + "\n");
				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SUPERMAKER_ADDRESS
						+ bill.supermaker_address + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SUPERMAKER_CALL
						+ bill.supermaker_call + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.WELCOM_TO_HERE
						+ "\n\n");

				// if(mIzkcService.getBufferState(100)){
				if (bill.end_bitmap != null) {
					SystemClock.sleep(200);
//					mIzkcService.printBitmap(bill.end_bitmap);
					switch (imageType){
						case 0:
							mIzkcService.printBitmap(bill.end_bitmap);
							break;
						case 1:
							mIzkcService.printImageGray(bill.end_bitmap);
							break;
						case 2:
							mIzkcService.printRasterImage(bill.end_bitmap);
							break;
					}
				}
				// }

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SupermakerBill getSupermakerBill(IZKCService mIzkcService,
			boolean display_start_pic, boolean display_end_pic) {
		SupermakerBill bill = new SupermakerBill();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		bill.supermaker_name = "XXXX超市";
		bill.serial_number = System.currentTimeMillis()+"";
		bill.purchase_time = dateStr;
		bill.total_amount = "36";
		bill.total_cash = "1681.86";
		bill.favorable_cash = "81.86";
		bill.receipt_cash = "1600";
		bill.recived_cash = "1600";
		bill.odd_change = "0.0";
		bill.vip_number = "111111111111";
		bill.add_integral = "1600";
		bill.current_integral = "36000";
		bill.supermaker_address = "深圳市宝安区鹤州xxxxxxxx";
		bill.supermaker_call = "0755-99991668";

		generalBitmap(mIzkcService, bill, display_start_pic, display_end_pic);
		addGoodsInfo(bill.goosInfos);

		return bill;

	}

	private void generalBitmap(IZKCService mIzkcService, SupermakerBill bill,
			boolean display_start_pic, boolean display_end_pic) {

		if (display_start_pic) {
			Bitmap mBitmap = null;
//			try {
//				mBitmap = mIzkcService.createBarCode("4333333367", 1, 384, 120, false);
				mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.zkc);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
			bill.start_bitmap = mBitmap;
		}
		if (display_end_pic) {
			Bitmap btMap;
			try {
				btMap = mIzkcService.createQRCode("扫描关注本店，有惊喜喔", 240, 240);
				bill.end_bitmap = btMap;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addGoodsInfo(ArrayList<GoodsInfo> goosInfos) {

		GoodsInfo goodsInfo0 = new GoodsInfo();
		goodsInfo0.goods_name = "黑人牙膏";
		goodsInfo0.goods_unit_price = "14.5";
		goodsInfo0.goods_amount = "2";
		goodsInfo0.goods_price = "29";
		
		GoodsInfo goodsInfo1 = new GoodsInfo();
		goodsInfo1.goods_name = "啤酒";
		goodsInfo1.goods_unit_price = "2.5";
		goodsInfo1.goods_amount = "12";
		goodsInfo1.goods_price = "30";
		
		GoodsInfo goodsInfo2 = new GoodsInfo();
		goodsInfo2.goods_name = "美的电饭煲";
		goodsInfo2.goods_unit_price = "288";
		goodsInfo2.goods_amount = "1";
		goodsInfo2.goods_price = "288";
		
		GoodsInfo goodsInfo3 = new GoodsInfo();
		goodsInfo3.goods_name = "剃须刀";
		goodsInfo3.goods_unit_price = "78";
		goodsInfo3.goods_amount = "1";
		goodsInfo3.goods_price = "78";
		
		GoodsInfo goodsInfo4 = new GoodsInfo();
		goodsInfo4.goods_name = "泰国进口红提";
		goodsInfo4.goods_unit_price = "22";
		goodsInfo4.goods_amount = "2";
		goodsInfo4.goods_price = "44";
		
		GoodsInfo goodsInfo5 = new GoodsInfo();
		goodsInfo5.goods_name = "太空椒";
		goodsInfo5.goods_unit_price = "4.5";
		goodsInfo5.goods_amount = "2";
		goodsInfo5.goods_price = "9";
		
		GoodsInfo goodsInfo6 = new GoodsInfo();
		goodsInfo6.goods_name = "进口香蕉";
		goodsInfo6.goods_unit_price = "3.98";
		goodsInfo6.goods_amount = "3";
		goodsInfo6.goods_price = "11.86";
		
		GoodsInfo goodsInfo7 = new GoodsInfo();
		goodsInfo7.goods_name = "烟熏腊肉";
		goodsInfo7.goods_unit_price = "33";
		goodsInfo7.goods_amount = "2";
		goodsInfo7.goods_price = "66";
		
		GoodsInfo goodsInfo8 = new GoodsInfo();
		goodsInfo8.goods_name = "长城红葡萄干酒";
		goodsInfo8.goods_unit_price = "39";
		goodsInfo8.goods_amount = "2";
		goodsInfo8.goods_price = "78";
		
		GoodsInfo goodsInfo9 = new GoodsInfo();
		goodsInfo9.goods_name = "白人牙刷";
		goodsInfo9.goods_unit_price = "14";
		goodsInfo9.goods_amount = "2";
		goodsInfo9.goods_price = "28";
		
		GoodsInfo goodsInfo10 = new GoodsInfo();
		goodsInfo10.goods_name = "苹果醋";
		goodsInfo10.goods_unit_price = "4";
		goodsInfo10.goods_amount = "5";
		goodsInfo10.goods_price = "20";
		
		GoodsInfo goodsInfo11 = new GoodsInfo();
		goodsInfo11.goods_name = "这个商品名有点长有点长有点长不是一般的长";
		goodsInfo11.goods_unit_price = "500";
		goodsInfo11.goods_amount = "2";
		goodsInfo11.goods_price = "1000";

		goosInfos.add(goodsInfo0);
		goosInfos.add(goodsInfo1);
		goosInfos.add(goodsInfo2);
		goosInfos.add(goodsInfo3);
		goosInfos.add(goodsInfo4);
		goosInfos.add(goodsInfo5);
		goosInfos.add(goodsInfo6);
		goosInfos.add(goodsInfo7);
		goosInfos.add(goodsInfo8);
		goosInfos.add(goodsInfo9);
		goosInfos.add(goodsInfo10);
		goosInfos.add(goodsInfo11);

	}

}
