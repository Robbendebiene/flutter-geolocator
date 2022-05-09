package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("deprecation")
public class GeolocationManager
    implements io.flutter.plugin.common.PluginRegistry.ActivityResultListener {

  private final List<LocationClient> locationClients;

  @Override
  public LocationClient createLocationClient(
      Context context,
      boolean forceAndroidLocationManager,
      @Nullable LocationOptions locationOptions) {
    if (forceAndroidLocationManager) {
      return new LocationManagerClient(context, locationOptions);
    }

    return isGooglePlayServicesAvailable(context)
        ? new FusedLocationClient(context, locationOptions)
        : new LocationManagerClient(context, locationOptions);
  }

  @Override
  private boolean isGooglePlayServicesAvailable(Context context) {
    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
    return resultCode == ConnectionResult.SUCCESS;
  }
}
