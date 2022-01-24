package com.newtech.newtech_sfm.Metier_Manager

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import com.newtech.newtech_sfm.Activity.SyncV2Activity
import com.newtech.newtech_sfm.Configuration.AppConfig
import com.newtech.newtech_sfm.Configuration.AppConfig.DATABASE_NAME
import com.newtech.newtech_sfm.Configuration.AppConfig.DATABASE_VERSION
import com.newtech.newtech_sfm.Configuration.AppController
import com.newtech.newtech_sfm.Configuration.SQLiteHandler
import com.newtech.newtech_sfm.Metier.LogSync
import com.newtech.newtech_sfm.Metier.VisibiliteRayon
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class VisibiliteRayonManager(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TAG = SQLiteHandler::class.java.simpleName





    override fun onCreate(db: SQLiteDatabase) {
        try {
            //db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
            db.execSQL(CREATE_VISIBILITE_RAYON_TABLE)
        } catch (e: SQLException) {
        }
        Log.d(TAG, "table VisibiliteRayon created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${Companion.TABLE_VISIBILITE_RAYON}")
        onCreate(db)
    }

    fun add(visibiliteRayon: VisibiliteRayon) {
        val db: SQLiteDatabase = this.getWritableDatabase()

        val values = ContentValues()
        values.put(KEY_VISIBILITE_RAYON_CODE, visibiliteRayon.VISIBILITE_RAYON_CODE)
        values.put(KEY_VISIBILITE_CODE, visibiliteRayon.VISIBILITE_CODE)
        values.put(KEY_TYPE_CODE, visibiliteRayon.TYPE_CODE)
        values.put(KEY_STATUT_CODE, visibiliteRayon.STATUT_CODE)
        values.put(KEY_CATEGORIE_CODE, visibiliteRayon.CATEGORIE_CODE)
        values.put(KEY_FAMILLE_CODE, visibiliteRayon.FAMILLE_CODE)
        values.put(KEY_ARTICLE_CODE, visibiliteRayon.ARTICLE_CODE)
        values.put(KEY_LARGEUR, visibiliteRayon.LARGEUR)
        values.put(KEY_HAUTEUR, visibiliteRayon.HAUTEUR)
        values.put(KEY_COMMENTAIRE, visibiliteRayon.COMMENTAIRE)
        values.put(KEY_COMMENTAIRE_RAYON, visibiliteRayon.COMMENTAIRE_RAYON)
        values.put(KEY_DATE_VISIBILITE_RAYON, visibiliteRayon.DATE_VISIBILITE_RAYON)
        values.put(KEY_DATE_CREATION, visibiliteRayon.DATE_CREATION)
        values.put(KEY_FIRST_IMAGE, visibiliteRayon.FIRST_IMAGE)
        values.put(KEY_SECOND_IMAGE, visibiliteRayon.SECOND_IMAGE)
        values.put(KEY_VERSION, visibiliteRayon.VERSION)

        // Inserting Row
        val id =
            db.insertWithOnConflict(
                Companion.TABLE_VISIBILITE_RAYON,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        db.close() // Closing database connection
    }

    fun delete(visibiliteRayonCode: String): Int {
        val db: SQLiteDatabase = this.getWritableDatabase()
        return db.delete(
            Companion.TABLE_VISIBILITE_RAYON,
            "$KEY_VISIBILITE_RAYON_CODE=?",
            arrayOf(visibiliteRayonCode)
        )
    }

    fun updateVisibiliteRayon(visibiliteRayonCode: String, msg: String) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val req =
            "UPDATE ${Companion.TABLE_VISIBILITE_RAYON} SET $KEY_COMMENTAIRE= '$msg' WHERE $KEY_VISIBILITE_RAYON_CODE= '$visibiliteRayonCode'"
        db.execSQL(req)
        db.close()
    }

    fun deleteVisibiliteRayonSynchronisee() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val DatefCmdAS = sdf.format(Date())
        val Where =
            " $KEY_COMMENTAIRE='inserted' and date($KEY_DATE_CREATION)!='$DatefCmdAS'"
        val db: SQLiteDatabase = this.getWritableDatabase()
        // Delete All Rows verified
        db.delete(Companion.TABLE_VISIBILITE_RAYON, Where, null)
        db.close()
    }

    fun getList(): ArrayList<VisibiliteRayon>? {
        val visibiliteRayons = ArrayList<VisibiliteRayon>()
        val selectQuery = "SELECT  * FROM ${Companion.TABLE_VISIBILITE_RAYON}"
        val db: SQLiteDatabase = this.getReadableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val visibiliteRayon = VisibiliteRayon()
                visibiliteRayon.VISIBILITE_RAYON_CODE = cursor.getString(0)
                visibiliteRayon.VISIBILITE_CODE = cursor.getString(1)
                visibiliteRayon.TYPE_CODE = cursor.getString(2)
                visibiliteRayon.STATUT_CODE = cursor.getString(3)
                visibiliteRayon.CATEGORIE_CODE = cursor.getString(4)
                visibiliteRayon.FAMILLE_CODE = cursor.getString(5)
                visibiliteRayon.ARTICLE_CODE = cursor.getString(6)
                visibiliteRayon.LARGEUR = cursor.getDouble(7)
                visibiliteRayon.HAUTEUR = cursor.getDouble(8)
                visibiliteRayon.COMMENTAIRE = cursor.getString(9)
                visibiliteRayon.COMMENTAIRE_RAYON = cursor.getString(10)
                visibiliteRayon.DATE_VISIBILITE_RAYON = cursor.getString(11)
                visibiliteRayon.DATE_CREATION = cursor.getString(12)
                visibiliteRayon.FIRST_IMAGE = cursor.getString(13)
                visibiliteRayon.SECOND_IMAGE = cursor.getString(14)
                visibiliteRayon.VERSION = cursor.getString(15)
                visibiliteRayons.add(visibiliteRayon)
            } while (cursor.moveToNext())
        }
        //returner la listArticles;
        cursor!!.close()
        db.close()
        return visibiliteRayons
    }

    fun ListNotInserted(): ArrayList<VisibiliteRayon>? {
        val visibiliteRayons = ArrayList<VisibiliteRayon>()
        val selectQuery =
            "SELECT  * FROM ${Companion.TABLE_VISIBILITE_RAYON} WHERE $KEY_COMMENTAIRE = 'to_insert' "
        val db: SQLiteDatabase = this.getReadableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val visibiliteRayon = VisibiliteRayon()
                visibiliteRayon.VISIBILITE_RAYON_CODE = cursor.getString(0)
                visibiliteRayon.VISIBILITE_CODE = cursor.getString(1)
                visibiliteRayon.TYPE_CODE = cursor.getString(2)
                visibiliteRayon.STATUT_CODE = cursor.getString(3)
                visibiliteRayon.CATEGORIE_CODE = cursor.getString(4)
                visibiliteRayon.FAMILLE_CODE = cursor.getString(5)
                visibiliteRayon.ARTICLE_CODE = cursor.getString(6)
                visibiliteRayon.LARGEUR = cursor.getDouble(7)
                visibiliteRayon.HAUTEUR = cursor.getDouble(8)
                visibiliteRayon.COMMENTAIRE = cursor.getString(9)
                visibiliteRayon.COMMENTAIRE_RAYON = cursor.getString(10)
                visibiliteRayon.DATE_VISIBILITE_RAYON = cursor.getString(11)
                visibiliteRayon.DATE_CREATION = cursor.getString(12)
                visibiliteRayon.FIRST_IMAGE = cursor.getString(13)
                visibiliteRayon.SECOND_IMAGE = cursor.getString(14)
                visibiliteRayon.VERSION = cursor.getString(15)
                visibiliteRayons.add(visibiliteRayon)
            } while (cursor.moveToNext())
        }
        //returner la listArticles;
        cursor!!.close()
        db.close()
        return visibiliteRayons
    }

    fun getListByDate(date: String): ArrayList<VisibiliteRayon>? {
        val visibiliteRayons = ArrayList<VisibiliteRayon>()
        val selectQuery =
            "SELECT  * FROM ${Companion.TABLE_VISIBILITE_RAYON} WHERE date($KEY_DATE_VISIBILITE_RAYON) = '$date'"
        val db: SQLiteDatabase = this.getReadableDatabase()
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val visibiliteRayon = VisibiliteRayon()

                visibiliteRayon.VISIBILITE_RAYON_CODE = cursor.getString(0)
                visibiliteRayon.VISIBILITE_CODE = cursor.getString(1)
                visibiliteRayon.TYPE_CODE = cursor.getString(2)
                visibiliteRayon.STATUT_CODE = cursor.getString(3)
                visibiliteRayon.CATEGORIE_CODE = cursor.getString(4)
                visibiliteRayon.FAMILLE_CODE = cursor.getString(5)
                visibiliteRayon.ARTICLE_CODE = cursor.getString(6)
                visibiliteRayon.LARGEUR = cursor.getDouble(7)
                visibiliteRayon.HAUTEUR = cursor.getDouble(8)
                visibiliteRayon.COMMENTAIRE = cursor.getString(9)
                visibiliteRayon.COMMENTAIRE_RAYON = cursor.getString(10)
                visibiliteRayon.DATE_VISIBILITE_RAYON = cursor.getString(11)
                visibiliteRayon.DATE_CREATION = cursor.getString(12)
                visibiliteRayon.FIRST_IMAGE = cursor.getString(13)
                visibiliteRayon.SECOND_IMAGE = cursor.getString(14)
                visibiliteRayon.VERSION = cursor.getString(15)

                visibiliteRayons.add(visibiliteRayon)
            } while (cursor.moveToNext())
        }
        //returner la listArticles;
        cursor!!.close()
        db.close()
        return visibiliteRayons
    }


    fun deleteVisibiliteRayons() {
        val db: SQLiteDatabase = this.getWritableDatabase()
        // Delete All Rows
        db.delete(Companion.TABLE_VISIBILITE_RAYON, null, null)
        db.close()
    }

    companion object {
        //private val DATABASE_VERSION = AppConfig.DATABASE_VERSION
        //private val DATABASE_NAME = AppConfig.DATABASE_NAME

        const val KEY_VISIBILITE_RAYON_CODE = "VISIBILITE_RAYON_CODE"
        const val KEY_VISIBILITE_CODE = "VISIBILITE_CODE"
        const val KEY_TYPE_CODE = "TYPE_CODE"
        const val KEY_STATUT_CODE = "STATUT_CODE"
        const val KEY_CATEGORIE_CODE = "CATEGORIE_CODE"
        const val KEY_FAMILLE_CODE = "FAMILLE_CODE"
        const val KEY_ARTICLE_CODE = "ARTICLE_CODE"
        const val KEY_LARGEUR = "LARGEUR"
        const val KEY_HAUTEUR = "HAUTEUR"
        const val KEY_COMMENTAIRE = "COMMENTAIRE"
        const val KEY_COMMENTAIRE_RAYON = "COMMENTAIRE_RAYON"
        const val KEY_DATE_VISIBILITE_RAYON = "DATE_VISIBILITE_RAYON"
        const val KEY_DATE_CREATION = "DATE_CREATION"
        const val KEY_FIRST_IMAGE = "FIRST_IMAGE"
        const val KEY_SECOND_IMAGE = "SECOND_IMAGE"
        const val KEY_VERSION = "VERSION"

        const val TABLE_VISIBILITE_RAYON = "visibiliteRayon"
        const val CREATE_VISIBILITE_RAYON_TABLE = ("CREATE TABLE " + Companion.TABLE_VISIBILITE_RAYON + "("
                + KEY_VISIBILITE_RAYON_CODE + " TEXT PRIMARY KEY, "
                + KEY_VISIBILITE_CODE + " TEXT, "
                + KEY_TYPE_CODE + " TEXT, "
                + KEY_STATUT_CODE + " TEXT, "
                + KEY_CATEGORIE_CODE + " TEXT,"
                + KEY_FAMILLE_CODE + " TEXT,"
                + KEY_ARTICLE_CODE + " TEXT,"
                + KEY_LARGEUR + " NUMERIC,"
                + KEY_HAUTEUR + " NUMERIC,"
                + KEY_COMMENTAIRE + " TEXT,"
                + KEY_COMMENTAIRE_RAYON + " TEXT,"
                + KEY_DATE_VISIBILITE_RAYON + " TEXT,"
                + KEY_DATE_CREATION + " TEXT,"
                + KEY_FIRST_IMAGE + " TEXT,"
                + KEY_SECOND_IMAGE + " TEXT,"
                + KEY_VERSION + " TEXT " + ")")

        fun synchronisationVisibiliteRayon(context: Context) {
            val visibiliteRayonManager = VisibiliteRayonManager(context)
            visibiliteRayonManager.deleteVisibiliteRayonSynchronisee()
            val tag_string_req = "VISIBILITE_RAYON"
            val strReq: StringRequest = object : StringRequest(
                Method.POST, AppConfig.URL_SYNC_VISIBILITE_RAYON,
                Response.Listener { response ->

                    var cptInsert = 0
                    var cptDelete = 0
                    Log.d(visibiliteRayonManager.TAG, "onResponse: $response")
                    try {
                        val jObj = JSONObject(response)
                        val error = jObj.getInt("statut")
                        if (error == 1) {
                            val visibiliteRayons = jObj.getJSONArray("result")

                            Log.d("debugg", "--@--les VisibiliteRayons : $response")
                            if (visibiliteRayons.length() > 0) {
                                val visibiliteRayonManager = VisibiliteRayonManager(context)
                                for (i in 0 until visibiliteRayons.length()) {
                                    val uneVisibiliteRayon = visibiliteRayons.getJSONObject(i)
                                    if (uneVisibiliteRayon.getString("Statut") == "true") {
                                        visibiliteRayonManager.updateVisibiliteRayon(
                                            uneVisibiliteRayon.getString("VISIBILITE_RAYON_CODE"),
                                            "inserted"
                                        )
                                        cptInsert++
                                    } else {
                                        cptDelete++
                                    }
                                }
                            }
                            if (SyncV2Activity.logSyncViewModel != null) {
                                SyncV2Activity.logSyncViewModel.setListMutableLiveData(
                                    LogSync(
                                        "VISIBILITE_RAYON: Inserted: $cptInsert NotInserted: $cptDelete",
                                        "VISIBILITE_RAYON",
                                        1
                                    )
                                )
                            }
                        } else {
                            val errorMsg = jObj.getString("info")
                            if (SyncV2Activity.logSyncViewModel != null) {
                                SyncV2Activity.logSyncViewModel.setListMutableLiveData(
                                    LogSync(
                                        "VISIBILITE_RAYON: Error: $errorMsg",
                                        "VISIBILITE_RAYON",
                                        0
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("VR", "synchronisationVisibiliteRayon: "+e.message)
                        if (SyncV2Activity.logSyncViewModel != null) {
                            SyncV2Activity.logSyncViewModel.setListMutableLiveData(
                                LogSync(
                                    "VISIBILITE_RAYON: Error: " + e.message,
                                    "VISIBILITE_RAYON",
                                    0
                                )
                            )
                        }
                    }
                },
                Response.ErrorListener { error -> //Toast.makeText(context, "visibiliteRayon : "+error.getMessage(), Toast.LENGTH_LONG).show();

                    Log.d("VR", "synchronisationVisibiliteRayon: "+error.message)
                    if (SyncV2Activity.logSyncViewModel != null) {
                        SyncV2Activity.logSyncViewModel.setListMutableLiveData(
                            LogSync(
                                "VISIBILITE_RAYON: Error: " + error.message,
                                "VISIBILITE_RAYON",
                                0
                            )
                        )
                    }
                }) {
                override fun getParams(): Map<String, String> {
                    val arrayFinale = HashMap<String, String>()
                    val pref = context.getSharedPreferences("MyPref", 0)
                    if (pref.getString("is_login", null) == "ok") {
                        val visibiliteRayonManager = VisibiliteRayonManager(context)

                        Log.d("Rayons : ", "getParams: "+visibiliteRayonManager.ListNotInserted())
                        val TabParams = HashMap<String, String>()
                        TabParams["Table_Name"] = "tableArticle"
                        val builder = GsonBuilder()
                        val gson = builder.create()
                        arrayFinale["Params"] = gson.toJson(TabParams)
                        arrayFinale["Data"] = gson.toJson(visibiliteRayonManager.ListNotInserted())

                    }
                    return arrayFinale
                }
            }

            Log.d("sync", "synchronisationVisibiliteRayon: "+strReq.toString())
            // Adding request to request queue
            strReq.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req)
        }

    }
}