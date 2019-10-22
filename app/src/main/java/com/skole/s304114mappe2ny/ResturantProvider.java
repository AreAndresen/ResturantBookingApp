package com.skole.s304114mappe2ny;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static com.skole.s304114mappe2ny.DBhandler.KEY_ID;
import static com.skole.s304114mappe2ny.DBhandler.KEY_NAME;
import static com.skole.s304114mappe2ny.DBhandler.KEY_PH_NO;
import static com.skole.s304114mappe2ny.DBhandler.KEY_TYPE;
import static com.skole.s304114mappe2ny.DBhandler.TABLE_RESTURANTER;

public class ResturantProvider extends ContentProvider {

    public final static String PROVIDER = "com.skole.s304114mappe2ny";
    private static final int BOK = 1;
    private static final int MBOK = 2;

    DBhandler DBhelper;
    SQLiteDatabase db;

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/Resturant");

    private static final UriMatcher uriMatcher;
    static{uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER,"Resturant", MBOK);
        uriMatcher.addURI(PROVIDER,"Resturant/#", BOK);}


    @Override
    public boolean onCreate() {
        DBhelper = new DBhandler(getContext());
        db = DBhelper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case MBOK:return"vnd.android.cursor.dir/vnd.skole.Resturant";
            case BOK:return"vnd.android.cursor.item/vnd.skole.Resturant";
            default:throw new IllegalArgumentException("Ugyldig URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        //FUNKER
        values.put(KEY_NAME, values.get(KEY_NAME).toString());
        values.put(KEY_PH_NO, values.get(KEY_PH_NO).toString());
        values.put(KEY_TYPE, values.get(KEY_TYPE).toString());
        db.insert(TABLE_RESTURANTER, null, values);

        Cursor c = db.query(TABLE_RESTURANTER,null,null,null,null,null,null);
        c.moveToLast();
        long minid = c.getLong(0);
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, minid);
        //FUNKER SLUTT
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cur = null;
        if(uriMatcher.match(uri) == BOK) {
            cur = db.query(TABLE_RESTURANTER, projection, KEY_ID + "=" + uri.getPathSegments().get(1), selectionArgs, null, null, sortOrder);
            return cur;
        }
        else{
            cur = db.query(TABLE_RESTURANTER, null, null, null, null, null, null);
            return cur;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
