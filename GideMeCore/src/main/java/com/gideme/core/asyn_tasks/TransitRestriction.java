package com.gideme.core.asyn_tasks;

import android.os.AsyncTask;
import android.util.Log;



import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.kml.KmlGeometry;
import com.google.maps.android.kml.KmlLineString;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;
import com.rm.androidesentials.utils.ErrorCodes;
import com.rm.androidesentials.utils.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oscargallon on 4/18/16.
 */
public class TransitRestriction extends AsyncTask<String, Boolean, Boolean>  implements Serializable{

    private Iterable<KmlPlacemark> placemarkList;
    private LatLng userLatLng;
    private ITransitRestriction iTransitRestriction;
    private LatLng nestedLocation;


    public TransitRestriction(Iterable<KmlPlacemark> placemarkList, LatLng userLatLng, ITransitRestriction iTransitRestriction) {
        this.placemarkList = placemarkList;
        this.userLatLng = userLatLng;
        this.iTransitRestriction = iTransitRestriction;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        KmlLineString lineString = null;
        List<LatLng> latLngs = null;
        for (KmlPlacemark placemark : this.placemarkList) {
            KmlGeometry geometry = placemark.getGeometry();
            if(geometry instanceof KmlPolygon) {
                KmlPolygon polygon = (KmlPolygon) geometry;
                latLngs = polygon.getOuterBoundaryCoordinates();
            }

            if(latLngs == null){
                continue;
            }

//            try {
//                lineString = (KmlLineString) placemark.getGeometry();
//            } catch (ClassCastException e) {
//                continue;
//            }
//
//            if (lineString == null) {
//                lineString = null;
//                latLngs = null;
//                return false;
//            }
//


            double dist = 0;
//            latLngs = lineString.getGeometryObject();
            for (LatLng latLng : latLngs) {

                dist = Utils.calculateDistanceBetweenTwoLocations(latLng.latitude,
                        latLng.longitude, userLatLng.latitude, userLatLng.longitude,
                        Utils.KILOMETERS);
                Log.i("DIST", Double.toString(dist));
                if (dist < 1) {
                    lineString = null;
                    latLngs = null;
                    nestedLocation = latLng;
                    return false;
                }
            }
        }

        lineString = null;
        latLngs = null;
        return true;
    }




    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            iTransitRestriction.userHasTransitRestriction(ErrorCodes.USER_HAS_NOT_TRANSIT_RESTRICTION);
        } else {
            iTransitRestriction.userHasNotTransitRestriction(ErrorCodes.USER_HAS_TRANSIT_RESTRICTION,
                    nestedLocation);
        }


    }
}
