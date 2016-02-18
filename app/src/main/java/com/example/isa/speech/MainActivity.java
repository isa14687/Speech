package com.example.isa.speech;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public Layout layout;
    //文字轉語音 tts
    public TextToSpeech textToSpeech;
    //    need RECORD_AUDIO permission
//    不要直接new出來，用createSpeechRecognizer方法
    private SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean searchVresion = false;
        String str = "";
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (int i = 0; i < list.size(); i++) {
//            Log.e("tag", list.get(i).packageName);
            if (list.get(i).packageName.equals("com.google.android.tts")) {
                searchVresion = true;
                str = list.get(i).versionName;
                break;
            }
        }
        if (!searchVresion) {

            Toast.makeText(MainActivity.this, "don't have", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "have version" + str, Toast.LENGTH_SHORT).show();
        }
        layout = new Layout(this);
        setContentView(layout);

        layout.button.setOnClickListener(onClickListener());
        layout.button2.setOnClickListener(onClickListener2());

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(recognitionListener());

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
//                    設定語言
//                    textToSpeech.setLanguage(Locale.CHINESE);
                    if (textToSpeech.isLanguageAvailable(Locale.CHINESE) == 0) {
                        Toast.makeText(MainActivity.this, "can speak chinese", Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Message");
                        builder.setMessage("請下載？？？？？？\n或更改設定");
                        builder.setCancelable(false);
                        builder.setNeutralButton("setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
//
                            }
                        });
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent installer = new Intent(Intent.ACTION_VIEW);
                                installer.setData(Uri.parse("market://details?id=com.google.android.tts"));
                                startActivity(installer);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(MainActivity.this, "can't  speak chinese", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
                        , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizer.startListening(intent);
            }
        };
    }

    public View.OnClickListener onClickListener2() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = layout.editText.getText().toString();
//                語音輸出
                textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
            }
        };
    }

    //監聽事件
    public RecognitionListener recognitionListener() {
        return new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                Toast.makeText(MainActivity.this, "BeginningOfSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(MainActivity.this, "EndForSpeech", Toast.LENGTH_SHORT).show();
                recognizer.stopListening();
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "error" + error, Toast.LENGTH_SHORT).show();

                if (error == 1) {
                    Toast.makeText(MainActivity.this, "網路操作逾時", Toast.LENGTH_SHORT).show();
                }//網路操作逾時
                else if (error == 2) {
                    Toast.makeText(MainActivity.this, "網路錯誤", Toast.LENGTH_SHORT).show();
                }//網路錯誤
                else if (error == 3) {
                    Toast.makeText(MainActivity.this, "錄音錯誤", Toast.LENGTH_SHORT).show();
                }//錄音錯誤
                else if (error == 4) {
                    Toast.makeText(MainActivity.this, "伺服器錯誤", Toast.LENGTH_SHORT).show();
                }//伺服器錯誤
                else if (error == 5) {
                    Toast.makeText(MainActivity.this, "客戶端錯誤", Toast.LENGTH_SHORT).show();
                }//客戶端錯誤
//                error=6找不到match 重新啟動
//                error=7 timeout
                else if (error == 6 && error == 7) {
                    recognizer.stopListening();
                }
//                server忙碌
                else if (error == 8) {
                    recognizer.stopListening();
                    recognizer.cancel();
                } else if (error == 9) {
                }//權限不足

            }

            @Override
            public void onResults(Bundle results) {
                //語音辨識的結果
                ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                layout.editText.setText(list.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        }

                ;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        boolean searchVresion = false;
        String str = "";
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (int i = 0; i < list.size(); i++) {
//            Log.e("tag", list.get(i).packageName);
            if (list.get(i).packageName.equals("com.google.android.tts")) {
                searchVresion = true;
                str = list.get(i).versionName;
                break;
            }
        }
        if (!searchVresion) {

            Toast.makeText(MainActivity.this, "don't have", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "have version" + str, Toast.LENGTH_SHORT).show();
        }
        layout = new Layout(this);
        setContentView(layout);

        layout.button.setOnClickListener(onClickListener());
        layout.button2.setOnClickListener(onClickListener2());

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(recognitionListener());

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {
//                    設定語言
                    textToSpeech.setLanguage(Locale.CHINESE);
                    if (textToSpeech.isLanguageAvailable(Locale.CHINESE) == 0) {
                        Toast.makeText(MainActivity.this, "can speak chinese", Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Message");
                        builder.setMessage("請下載？？？？？？\n或更改設定");
                        builder.setCancelable(false);
                        builder.setNeutralButton("setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent installer = new Intent(Intent.ACTION_VIEW);
                                installer.setData(Uri.parse("market://details?id=com.google.android.tts"));
                                startActivity(installer);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(MainActivity.this, "can't  speak chinese", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    @Override
    protected void onStop() {
        if (textToSpeech != null) {
            //停止speak
            textToSpeech.stop();
            //釋放資源
            textToSpeech.shutdown();
        }
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            //停止speak
            textToSpeech.stop();
            //釋放資源
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {}
}
