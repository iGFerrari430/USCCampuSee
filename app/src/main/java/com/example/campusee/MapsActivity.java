package com.example.campusee;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener {

    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private List<LatLng> area;

    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                      @Override
                      public void onPermissionGranted(PermissionGrantedResponse response) {
                          // Obtain the SupportMapFragment and get notified when the map is ready to be used.

                          buildLocationReuqest();
                          buildLocationCallBack();
                          fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                          SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                  .findFragmentById(R.id.map);
                          mapFragment.getMapAsync(MapsActivity.this);

                          initArea();
                          settingGeoFire();
                      }

                      @Override
                      public void onPermissionDenied(PermissionDeniedResponse response) {
                          Toast.makeText(MapsActivity.this, "You must enable permission", Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                      }
                }).check();



    }

    private void initArea(){
        area = new ArrayList<>();
        area.add(new LatLng(37.422,-122.044));
        area.add(new LatLng(37.422,-123.044));
        area.add(new LatLng(37.422,-124.044));
    }

    private void settingGeoFire(){
        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);
    }

    private void buildLocationReuqest(){
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }


    private void buildLocationCallBack(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                if(mMap != null)
                {
                    if(currentUser != null) currentUser.remove();
                    currentUser = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()))
                    .title("You"));
                    //after add marker, move camera
                    mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom (currentUser.getPosition(),12.0f));
                }
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        if(fusedLocationProviderClient != null) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                    return;
//                }
//
//            }
//        }
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());
//
//        //add circle for area
//        for(LatLng latLng : area)
//        {
//            mMap.addCircle(new CircleOptions().center(latLng)
//                    .radius(500)
//                    .strokeColor(Color.BLUE)
//                    .fillColor(0x220000FF)
//                    .strokeWidth(5.0f)
//            );
//
//            //geoquery
//            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude),0.5f);
//            geoQuery.addGeoQueryEventListener(MapsActivity.this);
//        }
    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        sendNotification("EDMTDev",String.format("%s enter the area",key));
    }

    @Override
    public void onKeyExited(String key) {
        sendNotification("EDMTDev",String.format("%s exist the area",key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("EDMTDev",String.format("%s move within the area",key));
    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String title, String content) {
        String NOTIFICATION_CHANNEL_ID = "edmt_multiple_location";
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            //config
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(),notification);

    }
}


