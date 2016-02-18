//package com.example.isa.speech;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.speech.RecognitionListener;
//import android.speech.RecognizerIntent;
//import android.speech.SpeechRecognizer;
//import android.speech.tts.TextToSpeech;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class SpeechModule {
//    public Layout layout;
//    //文字轉語音 tts
//    public TextToSpeech textToSpeech;
//    //    need RECORD_AUDIO permission
////    不要直接new出來，用createSpeechRecognizer方法
//    private SpeechRecognizer recognizer;
//public  Context context;
//    public SpeechModule(Context context) {
//        super();
//        this.context=context;
//        boolean searchVresion = false;
//        String str = "";
//        PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> list = packageManager.getInstalledPackages(0);
//        for (int i = 0; i < list.size(); i++) {
////            Log.e("tag", list.get(i).packageName);
//            if (list.get(i).packageName.equals("com.google.android.tts")) {
//                searchVresion = true;
//                str = list.get(i).versionName;
//                break;
//            }
//        }
//        if (!searchVresion) {
//
//            Toast.makeText(context, "don't have", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "have version" + str, Toast.LENGTH_SHORT).show();
//        }
//        layout = new Layout(this);
//        setContentView(layout);
//
////        Intent intent = new Intent();
////        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
////        startActivityForResult(intent, CHECK_TTS_CODE);
//
//
//        layout.button.setOnClickListener(onClickListener());
//        layout.button2.setOnClickListener(onClickListener2());
//
//        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
//        recognizer.setRecognitionListener(recognitionListener());
//
//        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//
//                if (status != TextToSpeech.ERROR) {
////                    設定語言
//                    textToSpeech.setLanguage(Locale.CHINESE);
//                    if (textToSpeech.isLanguageAvailable(Locale.CHINESE) == 0) {
//                        Toast.makeText(context, "can speak chinese", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setTitle("Message");
//                        builder.setMessage("請下載？？？？？？");
//                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                context.finish();
//                            }
//                        });
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent installer = new Intent(Intent.ACTION_VIEW);
//                                installer.setData(Uri.parse("market://details?id=com.google.android.tts"));
//                                context.startActivity(installer);
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                        Toast.makeText(context, "can't  speak chinese", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            }
//        });
//    }
//
//
//    public View.OnClickListener onClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
//                        , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                recognizer.startListening(intent);
//            }
//        };
//    }
//
//    public View.OnClickListener onClickListener2() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = layout.editText.getText().toString();
////                語音輸出
//                textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
//
//            }
//        };
//    }
//
//    //監聽事件
//    public RecognitionListener recognitionListener() {
//        return new RecognitionListener() {
//            @Override
//            public void onReadyForSpeech(Bundle params) {
//
//            }
//
//            @Override
//            public void onBeginningOfSpeech() {
//                Toast.makeText(context, "BeginningOfSpeech", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRmsChanged(float rmsdB) {
//
//            }
//
//            @Override
//            public void onBufferReceived(byte[] buffer) {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//                Toast.makeText(context, "EndForSpeech", Toast.LENGTH_SHORT).show();
//                recognizer.stopListening();
//            }
//
//            @Override
//            public void onError(int error) {
//                Toast.makeText(context, "error" + error, Toast.LENGTH_SHORT).show();
//
////                error=6找不到match 重新啟動
////                error=7 timeout
//                if (error == 6 && error == 7) {
//                    recognizer.stopListening();
//                }
////                server忙碌
//                if (error == 8) {
//                    recognizer.stopListening();
//                    recognizer.cancel();
//                }
//            }
//
//            @Override
//            public void onResults(Bundle results) {
//                //語音辨識的結果
//                ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                layout.editText.setText(list.get(0));
//            }
//
//            @Override
//            public void onPartialResults(Bundle partialResults) {
//
//            }
//
//            @Override
//            public void onEvent(int eventType, Bundle params) {
//
//            }
//        };
//    }
//
//    public void chechRest() {
//
//        boolean searchVresion = false;
//        String str = "";
//        PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> list = packageManager.getInstalledPackages(0);
//        for (int i = 0; i < list.size(); i++) {
////            Log.e("tag", list.get(i).packageName);
//            if (list.get(i).packageName.equals("com.google.android.tts")) {
//                searchVresion = true;
//                str = list.get(i).versionName;
//                break;
//            }
//        }
//        if (!searchVresion) {
//
//            Toast.makeText(context, "don't have", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "have version" + str, Toast.LENGTH_SHORT).show();
//        }
//        layout = new Layout(this);
//        setContentView(layout);
//
////        Intent intent = new Intent();
////        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
////        startActivityForResult(intent, CHECK_TTS_CODE);
//
//
//        layout.button.setOnClickListener(onClickListener());
//        layout.button2.setOnClickListener(onClickListener2());
//
//        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
//        recognizer.setRecognitionListener(recognitionListener());
//
//        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//
//                if (status != TextToSpeech.ERROR) {
////                    設定語言
//                    textToSpeech.setLanguage(Locale.CHINESE);
//                    if (textToSpeech.isLanguageAvailable(Locale.CHINESE) == 0) {
//                        Toast.makeText(context, "can speak chinese", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setTitle("Message");
//                        builder.setMessage("請下載？？？？？？");
//                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//                        });
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent installer = new Intent(Intent.ACTION_VIEW);
//                                installer.setData(Uri.parse("market://details?id=com.google.android.tts"));
//                                startActivity(installer);
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                        Toast.makeText(context, "can't  speak chinese", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            }
//        });
//    }
//}
