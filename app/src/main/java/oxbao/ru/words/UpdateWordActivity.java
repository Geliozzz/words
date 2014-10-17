package oxbao.ru.words;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**Activity need for upgrade word in database*/
public class UpdateWordActivity extends Activity {
    final String LOG_TAG = "myLogs";
    private EditText edt_eng;
    private EditText edt_rus;
    private Button btn_update_db;
    private Word word;
    private   SqliteWordHelper db = new SqliteWordHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upadte_word_layout);
        Log.d(LOG_TAG, "getParcelExtra");
        word = (Word) getIntent().getParcelableExtra(Word.class.getCanonicalName()); // Recive object
        /*Can word be null?*/
        if (word == null){
            finish();
        }
        initGUI();
        edt_eng.setText(word.getEng());
        edt_rus.setText(word.getRus());

    }

    private void initGUI(){
        edt_eng = (EditText)findViewById(R.id.edt_update_ENG);
        edt_rus = (EditText)findViewById(R.id.edt_update_RUS);
        btn_update_db = (Button) findViewById(R.id.btn_update_save);

        btn_update_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setRus(edt_rus.getText().toString());
                word.setEng(edt_eng.getText().toString());
                db.updateWord(word);
                db.close();
                Toast toast = Toast.makeText(getApplicationContext(), word.toString() , Toast.LENGTH_SHORT);
                toast.show();
                finish(); // need for close activity
            }
        });
    }
}
