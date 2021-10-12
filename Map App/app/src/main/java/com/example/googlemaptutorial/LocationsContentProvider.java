package com.example.googlemaptutorial;

import java.sql.SQLException;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public class LocationsContentProvider extends ContentProvider{

    public static final String PROVIDER_NAME = "com.example.googlemaptutorial";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/locations" );
    private static final int LOCATIONS = 1;

    private static final UriMatcher uriMatcher ;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "locations", LOCATIONS);
    }

    LocationsDB locationsDB;

    @Override
    public boolean onCreate() {
        locationsDB = new LocationsDB(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = locationsDB.insert(values);
        Uri uri1=null;
        if(rowID>0){
            uri1 = ContentUris.withAppendedId(CONTENT_URI, rowID);
        }else {
            try {
                throw new SQLException("Failed to insert : " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return uri1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delete = 0;
        delete = locationsDB.del();
        return delete;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uriMatcher.match(uri)==LOCATIONS){
            return locationsDB.getAllLocations();
        }
        return null;
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }
}