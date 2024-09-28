package com.ultra.vision.database;

//package com.ultravision.admin.database;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import com.ultravision.admin.account.DB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLiteHashMap {

    private SQLiteDatabase database;
    private String tableName;

    public SQLiteHashMap(SQLiteDatabase database, String tableName) {
        this.database = database;
        this.tableName = tableName;
        createTable();
    }

    private void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (key TEXT PRIMARY KEY, value TEXT)";
        database.execSQL(query);
    }

    public void put(String key, HashMap<String, List<String>> hashMap) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("list1", new JSONArray(hashMap.get("list1")));
			jsonObject.put("list2", new JSONArray(hashMap.get("list2")));
			jsonObject.put("list3", new JSONArray(hashMap.get("list3")));
		} catch (JSONException e) {
			e.printStackTrace();
			//dialogLogs("JSONException (SQLiteHashMap) [46]: " + e.toString());
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put("key", key);
		contentValues.put("value", jsonObject.toString());
		database.replace(tableName, null, contentValues);
	}
	

    public HashMap<String, List<String>> get(String key) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE key=?", new String[]{key});
        if (cursor.moveToFirst()) {
            String value = cursor.getString(cursor.getColumnIndex("value"));
            try {
                JSONObject jsonObject = new JSONObject(value);
                List<String> list1 = toList(jsonObject.getJSONArray("list1"));
                List<String> list2 = toList(jsonObject.getJSONArray("list2"));
				List<String> list3 = toList(jsonObject.getJSONArray("list3"));
                hashMap.put("list1", list1);
                hashMap.put("list2", list2);
				hashMap.put("list3", list3);
            } catch (JSONException e) {
                e.printStackTrace();
                //dialogLogs("JSONException (SQLiteHashMap) [46]: " + e.toString());
            }
        }
        cursor.close();
        return hashMap;
    }

    private List<String> toList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public void saveHash(String hashName, List<String> list1, List<String> list2, List<String> list3) {
		HashMap<String, List<String>> hashMap = new HashMap<>();
		hashMap.put("list1", list1);
		hashMap.put("list2", list2);
		hashMap.put("list3", list3);
		put(hashName, hashMap);
	}
	

    public HashMap<String, List<String>> getHash(String hashName) {
        return get(hashName);
    }

	/*private void dialogLogs(String msg){
	 AlertDialog dialog = new AlertDialog.Builder(DB.context)
	 .setTitle("Manda print aqui")
	 .setMessage(msg)
	 .setPositiveButton("Ok", null)
	 //.setNegativeButton("Cancel", null)
	 .create();
	 dialog.show();
	 }*/
}

