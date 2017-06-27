package layout;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.asthma.R;
import com.example.user.asthma.WeatherFetch;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment
implements LocationListener{
    private TextView coord_txt;
    private boolean getService = false;     //是否已開啟定位服務
    private LocationManager lms;
    private Location location;
    private String bestProvider = LocationManager.GPS_PROVIDER;
    private static String lastProvider = LocationManager.GPS_PROVIDER;
    private final int FINE_LOCATION_PERMISSION = 9999;
    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    Handler handler;

    public class LocationResult {
        double lat, lon;
        public LocationResult(double a, double b){
            this.lon = a;
            this.lat = b;
        }
    }
    public HomeFragment() {
        handler = new Handler();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons.ttf");
            LocationManager status = (LocationManager) (this.getActivity().getSystemService(LOCATION_SERVICE));
            if(status.isProviderEnabled(LocationManager.GPS_PROVIDER)|| status.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                getService = true; //確認開啟定位服務
                //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
                locationServiceInitial();
//                if(lr != null) {
//                    updateWeatherData(lr.lon, lr.lat);
//                    Toast.makeText(getActivity(), lr.lon + " : " + lr.lat, Toast.LENGTH_LONG).show();
//                }
            } else {
                Toast.makeText(getActivity(), "請開啟定位服務", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //開啟設定頁面
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        coord_txt = (TextView) rootView.findViewById(R.id.coordination);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        return rootView;
    }

    private void updateWeatherData(final double lon, final double lat){
//        Toast.makeText(getActivity(), lon + " : " + lat, Toast.LENGTH_LONG).show();
        new Thread(){
            public void run(){
                final JSONObject json = WeatherFetch.getJSON(getActivity(), lon, lat);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }
    private void renderWeather(JSONObject json){
        try {
            coord_txt.setText("coordination: " + json.getJSONObject("coord").getDouble("lon") + " " + json.getJSONObject("coord").getDouble("lat"));
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp") - 273)+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }
    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }
//    public void changeCity(String city){
//        updateWeatherData(city);
//    }

    private void locationServiceInitial() {
        lms = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE); //取得系統定位服務
        Criteria criteria = new Criteria();  //資訊提供者選取標準
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        bestProvider = lms.getBestProvider(criteria, true);    //選擇精準度最高的提供者
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
            return;
        }

//        if(bestProvider != null){
//            Toast.makeText(getActivity(), bestProvider, Toast.LENGTH_LONG).show();
//        }
//        lms.requestLocationUpdates(bestProvider, 10, 1, this);
//        Location location = lms.getLastKnownLocation(bestProvider);
//        onLocationChanged(location);
//        LocationResult lr = getLocation(location);
//        updateWeatherData(lr.lon, lr.lat);
//        Toast.makeText(getActivity(), String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();

        //return getLocation(location);
    }

    private LocationResult getLocation(Location location) { //將定位資訊顯示在畫面中
        if(location != null) {
            Double longitude = location.getLongitude();   //取得經度
            Double latitude = location.getLatitude();     //取得緯度
            //Toast.makeText(getActivity(), String.valueOf(longitude) + " : " + String.valueOf(latitude), Toast.LENGTH_LONG).show();
            return new LocationResult(longitude, latitude);
        }
        else {
            Toast.makeText(getActivity(), "無法定位座標", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(getActivity(), bestProvider, Toast.LENGTH_LONG).show();
        LocationResult lr = getLocation(location);
        if(lr == null)
            Log.d("locationResult", "null");
        if(lr != null) {
            updateWeatherData(lr.lon, lr.lat);
            lastProvider = location.getProvider();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "請開啟gps或3G網路", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(getService) {
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
            }
            else {
                lms.requestLocationUpdates("gps", 100, 1, this);
                lms.requestLocationUpdates("network", 100, 1, this);
                Location location = lms.getLastKnownLocation(lastProvider);
                onLocationChanged(location);
                //Toast.makeText(getActivity(), "Update " + bestProvider, Toast.LENGTH_LONG).show();
                //服務提供者、更新頻率60000毫秒=1分鐘、最短距離、地點改變時呼叫物件
            }
        }
    }
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(getService) {
            lms.removeUpdates(this);   //離開頁面時停止更新
        }
    }
}
