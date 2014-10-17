package oxbao.ru.words;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddWordActivity extends Activity {
   private   SqliteWordHelper db = new SqliteWordHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word_layout);

        final EditText edtEng = (EditText)findViewById(R.id.edtEng);
        final EditText edtRus = (EditText)findViewById(R.id.edtRus);
        Button btn_add_DB = (Button)findViewById(R.id.btn_write);


        btn_add_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eng = edtEng.getText().toString();
                String rus = edtRus.getText().toString();
                Word addWord = new Word(eng, rus);
                db.addWord(addWord);
                db.close();
                finish();
            }
        });
    }
}
