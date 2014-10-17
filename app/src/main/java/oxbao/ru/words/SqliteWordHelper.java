package oxbao.ru.words;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**

 */
public class SqliteWordHelper extends SQLiteOpenHelper {

    //DataBase Name
    private static final String DB_NAME = "WordsDB";
    //Database Version
    private static final int DB_VERSION = 1;

    //names columns
    private static final String WORDS_TABLE = "words";
    private static final String COL_ID = "_id";
    private static final String COL_ENG = "english";
    private static final String COL_RUS = "russian";

    public SqliteWordHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + WORDS_TABLE +
               "(" + COL_ID + " integer primary key autoincrement, " +
                COL_ENG +  " TEXT, " + COL_RUS + " TEXT );";
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        this.onCreate(sqLiteDatabase);
    }
    public void addWord(Word word){
        Log.d("addWord", word.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        //2. create ContentValues to add key
        ContentValues values = new ContentValues();
        values.put(COL_ENG, word.getEng());
        values.put(COL_RUS, word.getRus());
        //3. insert
        db.insert(WORDS_TABLE, // table
                null,
                values);

        // 4. close
        db.close();
    }

    public List<Word> getAllWords(){

        List<Word> words = new LinkedList<Word>();
        //1. build the query
        String query = "SELECT * FROM " + WORDS_TABLE;

        //2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //3/ go over each row
        Word word = null;
        if (cursor.moveToFirst()){
            do {
                word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setEng(cursor.getString(1));
                word.setRus(cursor.getString(2));

                words.add(word);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        Log.d("getAllWords", words.toString());
        return  words;
    }

    public void deleteWord(Word word){
        //1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        //2. delete
        db.delete(WORDS_TABLE, //table name
                COL_ID+" = ?",
                new String[] {String.valueOf(word.getId())}); //selections args
        //3. close
        db.close();
        //log
        Log.d("deleteWord", word.toString());
    }

    public int updateWord(Word word){
        //1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //2. create ContentValues to add key
        ContentValues values = new ContentValues();
        values.put(COL_ENG, word.getEng());
        values.put(COL_RUS, word.getRus());
        //3. updating
        int i = db.update(WORDS_TABLE, //Table
            values, //colum/value
                COL_ID+" = ?",
                new String[] {String.valueOf(word.getId())});
        //4. close
        db.close();
        Log.d("updateWord", word.toString());
        return i;
    }

/*    public Word getRandomWord(){
        //1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_ID + " FROM " + WORDS_TABLE + " WHERE " + COL_ID + " = 1";
        Cursor cursor = db.rawQuery(query, null);
    }*/

}


