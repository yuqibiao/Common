package com.afrid.common.net.api;

import com.afrid.common.bean.json.BaseJsonResult;
import com.afrid.common.bean.json.return_data.GetTagInfoListReturn;
import com.afrid.common.bean.json.return_data.GetWarehouseReturn;
import com.afrid.common.bean.json.return_data.LoginReturn;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 功能：昆明项目网络请求
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public interface KunmingApi {


    @POST("receipt/v1/saveReceipt")
    Observable<BaseJsonResult<String>> saveReceipt(@Body RequestBody requestBody);

    @POST("tag/v1/getTagInfoList")
    Observable<GetTagInfoListReturn> getTagInfoList(@Body RequestBody requestBody);

    @GET("warehouse/v1/getWarehouse/userId/{userId}")
    Observable<GetWarehouseReturn> getWarehouse(@Path("userId") Integer userId);

    @POST("user/v1/checkUser")
    Observable<LoginReturn> login(@Body RequestBody requestBody);

}
