package oxbao.ru.words;
/*Проверят ьпри пустой БД. При одном слове в БД валится исключание*/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import android.os.Handler;
import android.widget.Toast;


public class LearnActivity extends Activity {
    private SqliteWordHelper db = new SqliteWordHelper(this);
    private ArrayList<Word> wordArrayList;
    private TextView txtQuesWord;
    private Button btnVersion1;
    private Button btnVersion2;
    private Button btnVersion3;
    private Button btnVersion4;
    private int trueAnswer;
    private Handler handler;
    private final static long DELAY_MS = 1200; //Задержка отображения Верно не верно


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wordArrayList = new ArrayList<Word>(db.getAllWords());
        setContentView(R.layout.learn_layout);
        if (wordArrayList.size() < 4) {

            AlertDialog.Builder ad = new AlertDialog.Builder(LearnActivity.this)
                    .setTitle(getResources().getString(R.string.alert_title))
                    .setMessage(getResources().getString(R.string.alert_message))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.alert_no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    //Отказ от стандартных слов. Дальнейшая работа невозможна
                                    finish();
                                    dialog.cancel();
                                }
                            }
                    )
                    .setPositiveButton(getResources().getString(R.string.alert_yes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int ii) {
                                    //finish();
                                    //Заполняем базу данных стандартным набором слов если пользователь согласен
                                    String[] arr = getResources().getStringArray(R.array.standart_words);
                                    for (int i = 0; i < arr.length; i++){
                                       String[] arr_split = arr[i].split("=");
                                       db.addWord(new Word(arr_split[0], arr_split[1]));

                                    }
                                    db.close();
                                    wordArrayList = new ArrayList<Word>(db.getAllWords());
                                    dialog.cancel();
                                    initGUI();
                                    initQuestion(wordArrayList);
                                }
                            }
                    );
            AlertDialog alertDialog = ad.create();
            alertDialog.show();
        } else {
            initGUI();
            initQuestion(wordArrayList);
        }
    }

    private Word getRandomWord(ArrayList<Word> words) {
        Random random = new Random();
        int index = random.nextInt(words.size() - 1);
        return wordArrayList.get(index);
    }

    private void initQuestion(ArrayList<Word> wordArrayList) {
        boolean needMoreWords = false; // Нужен в случае если слов не хватает
        Word trueWord = getRandomWord(wordArrayList);
        /*Пользователь может сделать английское слово пустым*/
        int cnt = 0;
        while (trueWord.getRus().equals("") || trueWord.getEng().equals("")){
            cnt++;
            if (cnt > 10){
                trueWord = new Word("fill", "заполнить");
                break;
            }
        }

        txtQuesWord.setText(trueWord.getEng());

        Word fake1 = getRandomWord(wordArrayList);
        Word fake2 = getRandomWord(wordArrayList);
        Word fake3 = getRandomWord(wordArrayList);
        int count = 0;
        /*Цикл. Если слова совпадают между собой или если переводы или слово пустое*/
        while (trueWord.getRus().equals(fake1.getRus()) || trueWord.getRus().equals(fake2.getRus())
                || trueWord.getRus().equals(fake3.getRus())
                || fake1.getRus().equals(fake2.getRus()) || fake1.getRus().equals(fake3.getRus())
                || fake2.getRus().equals(fake3.getRus())
                /*Проверка на не пустые строчки*/
                || fake1.getRus().equals("") || fake2.getRus().equals("") || fake3.getRus().equals("")) {
            count++;
            fake1 = getRandomWord(wordArrayList);
            fake2 = getRandomWord(wordArrayList);
            fake3 = getRandomWord(wordArrayList);
            if (count > 10) {
                needMoreWords = true; // Флаг нужен для записания стандартный значений в кнопки
                break;
            }
        }
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                btnVersion1.setText(trueWord.getRus());
                trueAnswer = 1;
                if (!needMoreWords) {
                    btnVersion2.setText(fake1.getRus());
                    btnVersion3.setText(fake2.getRus());
                    btnVersion4.setText(fake3.getRus());
                } else {
                    btnVersion2.setText(getResources().getString(R.string.add));
                    btnVersion3.setText(getResources().getString(R.string.more));
                    btnVersion4.setText(getResources().getString(R.string.words));
                }

                break;
            case 1:
                trueAnswer = 2;
                btnVersion2.setText(trueWord.getRus());
                if (!needMoreWords) {
                    btnVersion1.setText(fake1.getRus());
                    btnVersion3.setText(fake2.getRus());
                    btnVersion4.setText(fake3.getRus());
                } else {
                    btnVersion1.setText(getResources().getString(R.string.add));
                    btnVersion3.setText(getResources().getString(R.string.more));
                    btnVersion4.setText(getResources().getString(R.string.words));
                }
                break;
            case 2:
                trueAnswer = 3;
                btnVersion3.setText(trueWord.getRus());
                if (!needMoreWords) {
                    btnVersion2.setText(fake1.getRus());
                    btnVersion1.setText(fake2.getRus());
                    btnVersion4.setText(fake3.getRus());
                } else {
                    btnVersion2.setText(getResources().getString(R.string.add));
                    btnVersion1.setText(getResources().getString(R.string.more));
                    btnVersion4.setText(getResources().getString(R.string.words));
                }
                break;
            case 3:
                trueAnswer = 4;
                btnVersion4.setText(trueWord.getRus());
                if (!needMoreWords) {
                    btnVersion2.setText(fake1.getRus());
                    btnVersion3.setText(fake2.getRus());
                    btnVersion1.setText(fake3.getRus());
                } else {
                    btnVersion2.setText(getResources().getString(R.string.add));
                    btnVersion3.setText(getResources().getString(R.string.more));
                    btnVersion1.setText(getResources().getString(R.string.words));
                }
                break;
        }
    }


    private void initGUI() {
        txtQuesWord = (TextView) findViewById(R.id.tv_quest_word);
        btnVersion1 = (Button) findViewById(R.id.btn_version_1);
        btnVersion2 = (Button) findViewById(R.id.btn_version_2);
        btnVersion3 = (Button) findViewById(R.id.btn_version_3);
        btnVersion4 = (Button) findViewById(R.id.btn_version_4);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                initQuestion(wordArrayList);
            }
        };

        btnVersion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trueAnswer == 1) {
                    txtQuesWord.setText(getResources().getString(R.string.right));
                } else txtQuesWord.setText(getResources().getString(R.string.wrong));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(DELAY_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                });
                t.start();
            }
        });
        btnVersion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trueAnswer == 2) {
                    txtQuesWord.setText(getResources().getString(R.string.right));
                } else txtQuesWord.setText(getResources().getString(R.string.wrong));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(DELAY_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                });
                t.start();
            }
        });
        btnVersion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trueAnswer == 3) {
                    txtQuesWord.setText(getResources().getString(R.string.right));
                } else txtQuesWord.setText(getResources().getString(R.string.wrong));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(DELAY_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                });
                t.start();
            }
        });
        btnVersion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trueAnswer == 4) {
                    txtQuesWord.setText(getResources().getString(R.string.right));
                } else txtQuesWord.setText(getResources().getString(R.string.wrong));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(DELAY_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                });
                t.start();
            }
        });


    }
}
