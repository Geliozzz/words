package oxbao.ru.words;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.util.List;


public class EditDBActivity extends Activity {
    private   SqliteWordHelper db = new SqliteWordHelper(this);
    private ListView lvEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_db_layout);

        lvEdit = (ListView) findViewById(R.id.lv_edit);

        List<Word> list = db.getAllWords();
        db.close();
        final EditArrayAdapter adapter = new EditArrayAdapter(getApplicationContext(), list);
        lvEdit.setAdapter(adapter);
        lvEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UpdateWordActivity.class);
                intent.putExtra(Word.class.getCanonicalName(), adapter.getItem(i));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Word> list = db.getAllWords();
        final EditArrayAdapter adapter = new EditArrayAdapter(getApplicationContext(), list);
        lvEdit.setAdapter(adapter);
        db.close();

    }
}
