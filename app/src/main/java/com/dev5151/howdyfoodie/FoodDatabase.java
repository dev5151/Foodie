package com.dev5151.howdyfoodie;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dev5151.howdyfoodie.Interfaces.FoodDao;
import com.dev5151.howdyfoodie.Models.Recipes;

@Database(entities = {Recipes.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {

    public static FoodDatabase instance;

    public abstract FoodDao foodDao();

    public static synchronized FoodDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FoodDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(foodCallback)
                    .build();
        }
        return instance;
    }

    private static FoodDatabase.Callback foodCallback = new FoodDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public class PopulateDbAsync extends AsyncTask<Void,Void,Void>{

        private final FoodDao foodDao;

        public PopulateDbAsync(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


}
