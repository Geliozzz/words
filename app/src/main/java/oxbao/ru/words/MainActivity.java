package oxbao.ru.words;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    private static final String CLIENT_ID = "words_translate";
    private static final String CLIENT_SECRET = "70qhkTNyF9Y42Mljtbt7hUC5rcT8mmOd/QdCjBkRm1U=";

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getClientSecret() {
        return CLIENT_SECRET;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        getSupportActionBar();

        Button btn_addWord = (Button)findViewById(R.id.btn_addWord);
        Button btn_edit_DB = (Button)findViewById(R.id.btn_edit_db);
        Button btn_del = (Button)findViewById(R.id.btn_del_word);
        Button btn_learn = (Button)findViewById(R.id.btn_learn);

        btn_addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddWordActivity.class);
                startActivity(i);
            }
        });

        btn_edit_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditDBActivity.class);
                startActivity(i);
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DeleteDBActivity.class);
                startActivity(i);
            }
        });
        btn_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LearnActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(getApplicationContext(), ActivityHelp.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
