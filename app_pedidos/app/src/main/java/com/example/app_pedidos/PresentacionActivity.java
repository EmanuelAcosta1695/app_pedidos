package com.example.app_pedidos;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class PresentacionActivity extends AppCompatActivity {

    private VideoView videoView1, videoView2;
    private int posicion;
    private TextView textoEmpresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_presentacion);

        textoEmpresa = findViewById(R.id.textoEmpresa);
        textoEmpresa.setText("Somos una empresa que se dedica a la venta de panificados desde 2002.");

        videoView1 = findViewById(R.id.videoView1);

    }

    // funcionalidades de pausa, detener e iniciar del video local
    public void pausar1(View view) {
        videoView1.pause();
        posicion = videoView1.getCurrentPosition();
    }

    public void detener1(View view) {
        videoView1.stopPlayback();
        posicion = 0;
    }

    public void iniciar1(View view) {
        videoView1.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.panaderia));
        videoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (posicion > 0) {
                    videoView1.seekTo(posicion);
                    videoView1.start();
                    return;
                } else {
                    videoView1.start();
                }
            }
        });
    }

}