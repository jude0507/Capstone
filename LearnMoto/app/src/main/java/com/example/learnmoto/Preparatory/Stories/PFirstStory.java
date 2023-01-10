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

public class PFirstStory extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story1;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfirst_story);
        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story1 = "Lima ang naging anak ni Mang Ramon at Aling Mila. Ang bunso na isang lalaki ay abnormal. Ang tawag dito ay mongoloid. Ang batang abnormal pinangalanan nilang Pilo. Malambot ang mga paa at mga kamay ni Pepe. Kahit na malaki na siya ay kailangan parin siyang alalayan ng kanyang ina sa paglalakad para hindi siya mabuwal. Ang kanyang bibig ay nakakibit kaya kung magsalita siya ay mahirap maintindihan.\n" +
                "Kahit naman abnormal ay mahal na mahal ng mag-asawa si Pepe. Noong maliit pa ito ay palitan ang mag-asawa sa pag-aalaga sa kanya. Hindi kinakitaan ng panghihinawa ang mag-asawa sa pag-aalaga sa anak. Kahit binata na ay palagi pa ring nakasunod sa kanya ang kanyang ina. Inaakay siya. Minsan ay sinusubuan siya. Pinapaliguan. Ano pa at malaking panahon ng kanyang inay ay sa kanya lamang naiuukol.\n" +
                "Ang hindi alam ni Aling Mila ay nagseselos na ang iba pa niyang anak. Napapansin ng mga ito na mas malaking oras ang ibinigay niya kay Pilo kaysa sa mga ito. Lingid sa kanya ay nag-usap-usap ang apat na magkakapatid. Napagkasunduan ng mga ito na kausapin siya para ipahayag sa kaniya ang kanilang mga hinanakit. Isang gabi matapos niyang patulugin si Pilo ay nilapitan siya ng apat na anak. Sinabi ng mga ito ang kanilang malaking mga hinanakit.\n" +
                "Gulat na gulat si Aling Mila. Hindi niya alam na nagseselos na pala ang apat niyang anak dahil sa sobrang pag aasikaso niya kay Pilo. Pero nakahanda na ang kanyang paliwanag sa mga ito.\n" +
                "“Kayo ay mga buo, walang kulang,” pagsisimula ni Aling Mila.\n" +
                "“Kahit wala kami ng itay ninyo ay mabubuhay kayo ng maayos. Pero ang kapatid ninyo ay hindi, kung kaya siya ang higit naming inaasikaso,” isa-isang tinitigan ni Aling Mila ang kanilang mga anak.” Pero hindi naman namin kayo pinababayaan hindi ba?”\n" +
                "Walang nakasagot sa isa man sa apat. Hiyang-hiya silang lahat.\n" +
                "Aral: Iwasan ang pagiging mainggitin. Bagamat may natatanging atensyon na ibinibigay lalo na sa mga mga special child, unawain na lamang natin sila. Higit na pinagpala ka pa rin dahil hindi mo nararanasan ang pinagdadaanan nila sa araw-araw.";

        jtv.setText(Story1);
        TitleStory.setText(PStories.title1);

    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.ang_batang_espesyal);
            mediaPlayer.setOnCompletionListener(mp -> {
                stopMediaPlayer();
            });
        }
        mediaPlayer.start();
    }

    public void Stop(View view) {
        stopMediaPlayer();
    }

    public void Pause(View view) {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
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

    public void BackToStories(View view) {
        startActivity(new Intent(this, PStories.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PStories.class));
    }
}