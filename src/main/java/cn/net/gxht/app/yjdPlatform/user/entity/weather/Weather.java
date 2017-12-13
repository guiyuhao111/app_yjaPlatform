package cn.net.gxht.app.yjdPlatform.user.entity.weather;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Weather {
    public final static String url="https://route.showapi.com/9-5";
    /**
     * 输入的坐标类型：
     * 1：标准GPS设备获取的角度坐标，国际标准，WGS84坐标系;
     * 2：GPS获取的米制坐标、sogou地图所用坐标;
     * 3：google地图、高徳、soso地图、aliyun地图、mapabc地图和amap地图所用坐标，也称为火星坐标系GCJ02。
     * 4：3中列表地图坐标对应的米制坐标
     * 5：百度地图采用的经纬度坐标，也称为Bd09坐标系。
     * 6：百度地图采用的米制坐标
     * 7：mapbar地图坐标;
     * 8：51地图坐标
     */
    public final static String form="5";
    public final static String showapi_appid="47060";
    public static String showapi_sign="e70949e611eb4583beb89f3d86978784";
    /**
     * 是否需要返回7天数据中的后4天。1为返回，0为不返回。
     */
    public static String needMoreDay="0";
    /**
     * 是否需要返回指数数据，比如穿衣指数、紫外线指数等。1为返回，0为不返回。
     */
    public static String needMorIndex="0";
    /**
     * 是否需要每小时数据的累积数组。由于本系统是半小时刷一次实时状态，因此实时数组最大长度为48。每天0点长度初始化为0.
     * 1为需要
     * 0为不
     */
    public static String needHourData="0";
    /**
     * 是否需要当天每3/6小时一次的天气预报列表。1为需要，0为不需要。
     */
    public static String need3HourForcast="0";
    /**
     * 是否需要天气预警。1为需要，0为不需要。
     */
    public static String needAlarm="0";
}
