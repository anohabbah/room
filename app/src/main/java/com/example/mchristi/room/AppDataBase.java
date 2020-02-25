package com.example.mchristi.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {User.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract UserDao userDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao noteDao;

        private PopulateDbAsyncTask(AppDataBase db) {
            noteDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            User u1 = new User("Ada","Lovelace");
            User u2 = new User("Charles","Babbage");

            noteDao.insert(u1);
            noteDao.insert(u2);
            noteDao.insert(u1);
            noteDao.insert(u2);


            return null;
        }
    }

}
