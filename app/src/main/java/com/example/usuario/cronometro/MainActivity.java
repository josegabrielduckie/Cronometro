package com.example.usuario.cronometro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonStart;
    TextView textView;
    Button buttonStop;
    Button buttonStart2;
    TextView textView2;
    Button buttonStop2;
    Thread hilo = null;
    Boolean activo = true;
    Boolean activo2 = true;
    MiCronometro cronometro= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.buttonStart);
        textView = findViewById(R.id.textView);
        buttonStop = findViewById(R.id.buttonStop);
        buttonStart2 = findViewById(R.id.buttonStart2);
        textView2 = findViewById(R.id.textView2);
        buttonStop2 = findViewById(R.id.buttonStop2);

        buttonStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cronometro!=null) {
                   cronometro.parar();
                }
            }
        });

        buttonStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (cronometro==null) {
                   cronometro = new MiCronometro();
                  // cronometro.execute();
                   cronometro.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
               }else{activo2=true;}


            }
        });


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hilo == null) {
                    hilo = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            int i = 0;
                            while (true) {
                                while (activo) {

                                    try {
                                        sleep(250);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    i++;
                                    int segundos = i % 60;
                                    int minutos = i / 60;
                                    textView.setText("" + minutos + ":" + segundos);
                                }
                            }
                        }
                    };
                    hilo.start();
                } else {

                    activo = true;


                }


            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hilo != null) {
                    activo = !activo;

                }
            }
        });


    }
private class MiCronometro extends AsyncTask<String, String, String>{
        int contador = 0;
        boolean activo = true;
    @Override
    protected String doInBackground(String... strings) {

        while (true) {
            while (activo) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                contador++;
                publishProgress("" + contador);
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        int segundos = Integer.parseInt(values[0]);

        textView2.setText(""+ (segundos/60)+":" +(segundos%60));
    }
    public void  parar(){

        /*
        if (activo == true){
            activo = false;

        }else{
            activo = true;
        }
        */
        activo = !activo;
    }
}





}
