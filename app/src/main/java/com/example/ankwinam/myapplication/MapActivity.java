package com.example.ankwinam.myapplication;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapView.CurrentLocationEventListener, MapView.OpenAPIKeyAuthenticationResultListener, MapView.POIItemEventListener {
    public static String API_KEY = "2c4fd138e21a2323748097a5fdaa61ae";

    private String state;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.map);

            MapView mapView = new MapView(this);
            mapView.setDaumMapApiKey(API_KEY);

            ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
            mapViewContainer.addView(mapView);

            mapView.setMapViewEventListener(this);

            MapView.setMapTilePersistentCacheEnabled(true);

            mapView.setShowCurrentLocationMarker(true);

            /////////////////////////////////////////////////////////////////////////////////////
//// Using TedPermission library
//            PermissionListener permissionlistener = new PermissionListener() {
//                @Override
//                public void onPermissionGranted() {
//                    Toast.makeText(MapActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
//
//                    // MapView 객체생성 및 API Key 설정
//                    MapView mapView = new MapView(MapActivity.this);
//                    mapView.setDaumMapApiKey(API_KEY);
//
//                    ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//                    mapViewContainer.addView(mapView);
//
//                    mapView.setMapViewEventListener(this);
//
//                    MapView.setMapTilePersistentCacheEnabled(true);
//
//                    mapView.setShowCurrentLocationMarker(true);
//
////                    // 중심점 변경
////                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
////
////                    // 마커 생성 및 설정
////                    MapPOIItem marker = new MapPOIItem();
////                    marker.setItemName("Default Marker");
////                    marker.setTag(0);
////                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633));
////                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
////                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
////                    mapView.addPOIItem(marker);
//                }
//
//                @Override
//                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                    Toast.makeText(MapActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//                }
//
//            };
//
//            new TedPermission(this)
//                    .setPermissionListener(permissionlistener)
//                    .setRationaleMessage("지도 서비스를 사용하기 위해서는 위치 접근 권한이 필요해요")
//                    .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
//                    .setPermissions(ACCESS_FINE_LOCATION)
//                    .check();

        }



    @Override
    public void onMapViewInitialized(final MapView mapView) {
        // Using TedPermission library
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MapActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();

                //지도 이동
                //MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633);
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.473206, 127.035333);
                mapView.setMapCenterPoint(mapPoint, true);

                MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(37.472943, 127.034134);
                //mapView.setMapCenterPoint(mapPoint2, true);

                //마커 생성
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName("양재천 산책길");
                marker.setTag(0);
                marker.setMapPoint(mapPoint);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);  //기본으로 제공하는 BluePin 마커 모양
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //마커를 클릭했을때 기본으로 제공하는 RedPin 마커 모양

                MapPOIItem marker2 = new MapPOIItem();
                marker2.setItemName("내 위치");
                marker2.setTag(1);
                marker2.setMapPoint(mapPoint2);
                marker2.setMarkerType(MapPOIItem.MarkerType.YellowPin);

                //마커 추가
                mapView.addPOIItem(marker);
                mapView.addPOIItem(marker2);

                MapPolyline polyline = new MapPolyline();
                polyline.setTag(1000);
                polyline.setLineColor(Color.argb(200, 255, 51, 5)); // Polyline 컬러 지정.

// Polyline 좌표 지정.
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473206, 127.035333));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472943, 127.034134));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.471748, 127.032526));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.470290, 127.031072));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467718, 127.029540));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467596, 127.028775));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467955, 127.028740));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.468674, 127.029263));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.469413, 127.029926));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.470162, 127.030411));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.471631, 127.031872));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472167, 127.032459));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472390, 127.032554));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472790, 127.033090));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473200, 127.033798));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473529, 127.034915));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473833, 127.036382));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474714, 127.037518));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474729, 127.038034));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.475225, 127.039502));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479573, 127.043519));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479353, 127.043938));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.475176, 127.040449));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474715, 127.040008));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474052, 127.038835));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473880, 127.038235));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473353, 127.037878));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473277, 127.037718));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473434, 127.036541));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473206, 127.035333));

// Polyline 지도에 올리기.
                mapView.addPolyline(polyline);

// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
                MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                int padding = 100; // px
                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MapActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("지도 서비스를 사용하기 위해서는 위치 접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(ACCESS_FINE_LOCATION)
                .check();

//        //지도 이동
//        //MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633);
//        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.473206, 127.035333);
//        mapView.setMapCenterPoint(mapPoint, true);
//
//        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(37.472943, 127.034134);
//        //mapView.setMapCenterPoint(mapPoint2, true);
//
//        //마커 생성
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("양재천 산책길");
//        marker.setTag(0);
//        marker.setMapPoint(mapPoint);
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);  //기본으로 제공하는 BluePin 마커 모양
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //마커를 클릭했을때 기본으로 제공하는 RedPin 마커 모양
//
//        MapPOIItem marker2 = new MapPOIItem();
//        marker2.setItemName("내 위치");
//        marker2.setTag(1);
//        marker2.setMapPoint(mapPoint2);
//        marker2.setMarkerType(MapPOIItem.MarkerType.YellowPin);
//
//        //마커 추가
//        mapView.addPOIItem(marker);
//        mapView.addPOIItem(marker2);
//
//        MapPolyline polyline = new MapPolyline();
//        polyline.setTag(1000);
//        polyline.setLineColor(Color.argb(200, 255, 51, 5)); // Polyline 컬러 지정.
//
//// Polyline 좌표 지정.
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473206, 127.035333));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472943, 127.034134));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.471748, 127.032526));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.470290, 127.031072));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467718, 127.029540));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467596, 127.028775));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.467955, 127.028740));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.468674, 127.029263));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.469413, 127.029926));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.470162, 127.030411));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.471631, 127.031872));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472167, 127.032459));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472390, 127.032554));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.472790, 127.033090));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473200, 127.033798));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473529, 127.034915));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473833, 127.036382));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474714, 127.037518));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474729, 127.038034));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.475225, 127.039502));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479573, 127.043519));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479353, 127.043938));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.475176, 127.040449));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474715, 127.040008));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.474052, 127.038835));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473880, 127.038235));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473353, 127.037878));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473277, 127.037718));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473434, 127.036541));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.473206, 127.035333));
//
//// Polyline 지도에 올리기.
//        mapView.addPolyline(polyline);
//
//// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
//        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
//        int padding = 100; // px
//        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));



        //좌표 사이의 거리 재기
        double distance;
        String meter;

        Location locationA = new Location("point A");
        locationA.setLongitude(127.035333);
        locationA.setLatitude(37.473206);
        Location locationB = new Location("point B");
        locationB.setLongitude(127.034134);
        locationB.setLatitude(37.472943);

        distance = (int)locationA.distanceTo(locationB);
        meter = Double.toString(distance);

        TextView distanceoutput = (TextView)findViewById(R.id.mapdistance);
        distanceoutput.setText("거리 : "+meter+ " m");

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        //현위치, 나침반 모드
        mapView.setCurrentLocationEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

//    static final LatLng SEOUL = new LatLng(37.56, 126.97);
//    private static final String TAG = "@@@";
//    private GoogleApiClient mGoogleApiClient;
//    private LocationRequest mLocationRequest;
//    private static final int REQUEST_CODE_LOCATION = 2000;//임의의 정수로 정의
//    private GoogleMap googleMap;
//
////    ///////////////////////////보행자 이동/////////////////////////////
////    Marker mMarkerStart;
////    Marker mMarkerMan;
////    boolean mFirstLoc = true;
////    LocationManager mLocMgr;
////    //////////////////////////////////////////////////////////////////
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map);
//
//
////        ///////////////////////보행자 이동/////////////////////////////
////        initMap();
////        mLocMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
////        ///////////////////////////////////////////////////////////////////
//
//
//        //권한검사
//        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Marshmallow이상이면 코드에서 권한요청이 필요
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION);
//            }
//        }
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//    }
//
////    //////////////////////////////보행자 이동/////////////////////////////////////
////    LocationListener mLocListener = new LocationListener() {
////        public void onLocationChanged(Location location) {
////            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
////            if( mFirstLoc ) {
////                mFirstLoc = false;
////                mMarkerStart.setPosition(position);
////            }
////
////            mMarkerMan.setPosition(position);
////            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position) );
////        }
////
////        public void onProviderDisabled(String provider) {}
////        public void onProviderEnabled(String provider) {}
////        public void onStatusChanged(String provider, int status, Bundle extras) {}
////    };
////
////    public void onResume() {
////        super.onResume();
////        String locProv = LocationManager.GPS_PROVIDER;
////        mLocMgr.requestLocationUpdates(locProv, (long)3000, 3.f, mLocListener);
////    }
////
////    public void onPause() {
////        super.onPause();
////        mLocMgr.removeUpdates(mLocListener);
////    }
////
////    private void initMap() {
////        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
////        googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map1)).getMap();
////        LatLng pos = new LatLng(37.4980, 127.027);
////        // 맵 중심 위치 이동
////        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
////
////        // 시작 위치에 마커 추가
////        MarkerOptions moStart = new MarkerOptions();
////        moStart.position(pos);
////        moStart.title("출발");
////        mMarkerStart = googleMap.addMarker(moStart);
////        mMarkerStart.showInfoWindow();
////        //mMarkerStart = mGoogleMap.addMarker(new MarkerOptions().position(pos).title("출발"));
////
////        //마커 클릭 리스너
////        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
////            @Override
////            public boolean onMarkerClick(Marker marker) {
////                Toast.makeText(getApplication(), "출발 위치", Toast.LENGTH_SHORT).show();
////                return true;
////            }
////        });
////
////        // 보행자 마커 추가
////        MarkerOptions moMan = new MarkerOptions();
////        moMan.position(pos);
////        moMan.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
////        mMarkerMan = googleMap.addMarker( moMan );
////    }
////
//////////////////////////////////////////////////////////////////////////////////////
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_CODE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//
//                    LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
//                    if (locationAvailability.isLocationAvailable()) {
//                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//                    } else {
//
//                        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
//                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//                        Toast.makeText(this,"Location Unavialable",Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onMapReady(final GoogleMap map) {
//        googleMap = map;
//
//        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//    }
//
//    /**
//     * Runs when a GoogleApiClient object successfully connects.
//     */
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(3000);
//        mLocationRequest.setFastestInterval(1500);
//
//
//        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            // Gets the best and most recent location currently available, which may be null
//            // in rare cases when a location is not available.
//            LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
//            if (locationAvailability.isLocationAvailable()) {
//                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//            } else {
//                Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL)
//                        .title("Seoul"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//                Toast.makeText(this, "Location Unavialable", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        // The connection to Google Play services was lost for some reason. We call connect() to
//        // attempt to re-establish the connection.
//        Log.i(TAG, "Connection suspended");
//        mGoogleApiClient.connect();
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        LatLng CURRENT_LOCATION = new LatLng(location.getLatitude(), location.getLongitude());
//        googleMap.clear();
//        Marker seoul = googleMap.addMarker(new MarkerOptions().position(CURRENT_LOCATION).title("Here"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 15));
//    }


}

