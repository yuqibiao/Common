package com.smartdevice.aidltestdemo.printer.entity;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class SupermakerBill {

	public String supermaker_name;
	public String serial_number;
	public String purchase_time;
	public String total_amount;
	public String total_cash;
	public String favorable_cash;
	public String receipt_cash;
	public String recived_cash;
	public String odd_change;
	public String vip_number;
	public String add_integral;
	public String current_integral;
	public String supermaker_address;
	public String supermaker_call;
	public Bitmap start_bitmap;
	public Bitmap end_bitmap;
	
	public ArrayList<GoodsInfo> goosInfos = new ArrayList<GoodsInfo>();
}
