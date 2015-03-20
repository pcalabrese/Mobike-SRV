package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Thumbnail {
	private static final int maxEncodedPoints = 100;
	private static final String staticMapURL = "https://maps.googleapis.com/maps/api/staticmap?size=100x100&path=weight:5%7Ccolor:0xff0000ff%7Cenc:";
	
	public Thumbnail() { };
	
	public String getEncodedPolylineURL(String gpxString) throws IOException {
        ArrayList<LatLng> input = gpxToMapPoints(gpxString), result = new ArrayList<>();
        if (input.size() < maxEncodedPoints) {
            result = input;
        } else {
            int n = (input.size()/maxEncodedPoints) + 1;
            for (int i = 0; i < input.size() - 1; i++) {
                if (i%n == 0)
                    result.add(input.get(i));
            }
        }
        return staticMapURL + encode(result);
    }
	
	// this method returns a List of all <LatLng> points in the gpx
	private ArrayList<LatLng> gpxToMapPoints(String gpxString) throws IOException {
        ArrayList<LatLng> list = new ArrayList<>();
        double latitude;
        double longitude;
        LatLng couple;
        String[] array = gpxString.split("trkpt ");
        for (int i = 0; i < array.length; i++) {
            if(array[i].startsWith("lat=") ){
                String lat = array[i].substring(array[i].indexOf("lat=")+5 , array[i].indexOf("lon=")-2);
                String lon = array[i].substring(array[i].indexOf("lon=")+5, array[i].indexOf("lon=")+13);

                latitude = Double.parseDouble(lat);
                longitude = Double.parseDouble(lon);
                couple = new LatLng(latitude, longitude);
                list.add(couple);
            }
        }

        return list;
    }
	
	/**
     * Encodes a sequence of LatLngs into an encoded path string.
     */
    private static String encode(final List<LatLng> path) {
        long lastLat = 0;
        long lastLng = 0;

        final StringBuffer result = new StringBuffer();

        for (final LatLng point : path) {
            long lat = Math.round(point.latitude * 1e5);
            long lng = Math.round(point.longitude * 1e5);

            long dLat = lat - lastLat;
            long dLng = lng - lastLng;

            encode(dLat, result);
            encode(dLng, result);

            lastLat = lat;
            lastLng = lng;
        }
        return result.toString();
    }

    private static void encode(long v, StringBuffer result) {
        v = v < 0 ? ~(v << 1) : v << 1;
        while (v >= 0x20) {
            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
            v >>= 5;
        }
        result.append(Character.toChars((int) (v + 63)));
    }
	
	// classe LatLng che rappresenta coppie <latitudine, longitudine>
	class LatLng {
		double latitude;
		double longitude;
		
		public LatLng(double lat, double lon) {
			latitude = lat;
			longitude = lon;
		}
	}
			
}