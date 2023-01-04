package com.example.learnmoto.Preparatory.Stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Preparatory.PStories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class PSecondStory extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story2;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psecond_story);
        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story2 = "Masama ang loob ni Zandrey habang minamasdan niya ang kahabaan ng bakuran nila. Bitbit niya sa kanang kamay ang isang lata ng pinturang puti at sa kaliwang kamay ang brotsang gagamitin sa pagpipintura.\n" +
                "\n" +
                "Nag-aalmusal pa siya nang sabihin sa kanya ng nanay niya na, “huwag kang aalis, Zandrey, dahil may ipagagawa ako sa iyo.”\n" +
                "\n" +
                "“Ano po iyon, inay?” tanong ni Zandrey na nag-uumpisa nang mag-alala. Kasunduan nilang mga magkababata na maglalaro ng basketbol sa parke ngayong umaga.\n" +
                "\n" +
                "“Pangit na ang pintura ng ating bakod. Kupas at marumi pa. Nakabili na ako ng pinturang puti at brotsa at maaari mo nang masimulan pagkakain mo.”\n" +
                "\n" +
                "Hindi maaring hindi susunod sa utos. Mabait ito kung sa mabait, ngunit ang mga utos niya ay parang utos ng reyna na di mababali.\n" +
                "\n" +
                "Habang minamasdan ni Zandrey ang bakuran, lalo namang nagsisiksikan sa pag-iisip niya ang tiyak na nasa laruan nang mga kababata, malamang ay inip na inip na sa pag-aantay sa kanya, o di kaya’y naglalaro na at hindi na siya hinintay.\n" +
                "\n" +
                "Isinawsaw niya ang brotsa sa lata ng pintura at dahan-dahang idinampi sa isang sulok ng mahabang bakuran.\n" +
                "\n" +
                "“Sa lahat ng trabaho, ito talaga ang nakakapundi,” wika niya sa sarili. “Binabalak pa naman namin ni James na ilampaso ngayon sina Henry.”\n" +
                "\n" +
                "Nang walang kagana-ganang isasawsaw na uli ang brotsa sa lata ng pintura, natanaw niyang dumarating ang kapwa bata ring si Vince.\n" +
                "\n" +
                "“Hoy, Zandrey!” Di siya lumingon at kunwari ay walang narinig. Binilisan niya ang paghahaplos ng pintura sa bakod.\n" +
                "\n" +
                "“Ano ang ginagawa mo, Zandrey?”\n" +
                "\n" +
                "Wala pa rin siyang narinig. Humakbang siya sa likod at sinipat ang napinturahan na.\n" +
                "\n" +
                "“Kawawa ka naman, Zandrey. Nagpipintura ka.”\n" +
                "\n" +
                "“Oy, nandiyan ka pala, Vince. Di kita napansin.”\n" +
                "\n" +
                "Isinawsaw na uli ni Zandrey sa lata ang brotsa at ganadong-ganadong itinuloy ang pagpipintura. Maya’t-maya’y sinisipat ang napintahan na. Sisipol-sipol siya at tila tuwang-tuwa sa ginagawa. Si\n" +
                "Vince ay nakatungangang nagmamasid.\n" +
                "\n" +
                "“Papinta rin nga,” maya-maya’y pakiusap, nito. “Titikman ko lang magpintura.”\n" +
                "\n" +
                "“Ay, huwag! Baka hindi mo kaya. At saka, magagalit ang nanay ko. Kailangang maayos ang pintura nito.”\n" +
                "\n" +
                "“Sige na, patikim lang. Aayusin ko. O, ibibigay ko sa iyo ang trumpo ko, pagpintahin mo lang ako.”\n" +
                "\n" +
                "“O, sige na nga.” Naupo si Zandrey sa isang tabi at hinayaang magpintura ang kababata. Habang pinapanood niya si Vince, nabuo sa isipan niya ang isang balak para mapadali ang trabaho niya at makaipon pa siya ng mga regalo.\n" +
                "\n" +
                "Nang umalis na ang napagod nang si Vince, sumunod namang naisahan ni Zandrey si Armel. Binigyan siya nito ng yoyo kapalit ng pagpipintura sa bakod. Sumunod naman dito si Erick, saka si Ethan, si Alexander, si Richard.\n" +
                "\n" +
                "Nang matapos ang araw, malaking kayamanan ang inisa-isang bilangin ng matalinong si Zandrey – may yoyo, trumpo, tatlong malalaking sigay, kandadong walang susi, larawan ni Batman at Robin, komiks na Superman, mahabang tali ng borador, plastic na baril-barilan, singsing na tanso….\n" +
                "\n" +
                "Pinagmasdan niya ang bakod na pininturahan. May makapal, may manipis, may paayon, pasalungat, may kulang-kulang na pintura. Ngunit sa paningin ni Zandrey, ang bakod ay napakaganda, dahil ni-kaunti man ay di siya napagod at nakakamal pa siya ng maraming yaman.\n" +
                "\n" +
                "May mga aral pa sa buhay na kanyang natutuhan. Ano kaya ang mga iyon?\n" +
                "\n" +
                "Aral: Huwag manlamang sa kapwa.";

        jtv.setText(Story2);
        TitleStory.setText(PStories.title2);

    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, PStories.class));
    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.ang_matalinong_pintor);
            mediaPlayer.setOnCompletionListener(mp -> {
                stopMediaPlayer();
            });
        }
        mediaPlayer.start();
    }

    public void Pause(View view) {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public void Stop(View view) {
        stopMediaPlayer();
    }

    private void stopMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        stopMediaPlayer();
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}