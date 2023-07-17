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


    // AGREGAR EL ID Y METODO ENVIO por si el user vuelve para aca

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_presentacion);

        textoEmpresa = findViewById(R.id.textoEmpresa);
        textoEmpresa.setText("Somos una empresa que se dedica \na la venta de panificados \ndesde 2002.");

        videoView1 = findViewById(R.id.videoView1);
        //videoView2 = findViewById(R.id.videoView2);

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


//    // funcionalidades de pausa, detener e iniciar del video online
//    public void pausar2(View view) {
//        videoView2.pause();
//        posicion = videoView2.getCurrentPosition();
//    }
//
//    public void detener2(View view) {
//        videoView2.stopPlayback();
//        posicion = 0;
//    }
//
//
//    public void iniciar2(View view) {
//
//        videoView2.setVideoPath("https://media.istockphoto.com/id/1221221384/es/v%C3%ADdeo/panes-y-productos-horneados.mp4?s=mp4-640x640-is&k=20&c=X2ePBfd_7q_aO6ym5r4iFxDywQ0bnTKSN0L-y2J8eA4=");
//        videoView2.requestFocus();
//        videoView2.setMediaController(new MediaController(this));
//
//        videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                if (posicion > 0) {
//                    videoView2.seekTo(posicion);
//                    videoView2.start();
//                    return;
//                } else {
//                    videoView2.start();
//                }
//            }
//        } );
//    }


}