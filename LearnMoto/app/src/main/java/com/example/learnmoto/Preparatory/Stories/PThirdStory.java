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

public class PThirdStory extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story3;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pthird_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story3 = "Simula noong nasa unang baitang pa lamang sila sa elementarya ay magkaklase na sina Lino at Tomas hanggang ngayong nasa ikalawang taon na sila sa sekondarya. Sa kabila ng kanilang pagkakaiba ay matalik silang magkaibigan. \n" +
                "Si Lino ay anak ng isang hardinero sa paaralan na pinapasukan nila. Ang ina naman niya ay nagtitinda sa kantina. Ang mga magulang ni Tomas ay isang guro at isang OFW.\n" +
                "\n" +
                "Parehong masayahing bata sina Lino at Tomas. Pareho rin silang aktibo sa klase at sa mga patimpalak. Kahit matumal ang kita ng mga magulang ni Lino ay ni minsan hindi ito nagutom o nawalan ng pambayad sa proyekto dahil nandiyan si Tomas.\n" +
                "Mapagbigay talaga si Tomas hindi lang kay Lino pati na rin sa iba nilang mga kaklase. Sa murang edad, may sariling kotse na ito na bigay ng ama niya noong nagtapos siya sa elementarya.\n" +
                "\n" +
                "Subalit, nagbago ang lahat noong naghiwalay ang mga magulang ni Tomas. Ang dating masayahin at aktibong mag-aaral ay naging tamad sa klase. Palagi siyang pinupuntahan ni Lino sa bahay nila upang yayain pumasok pero ayaw niya.\n" +
                "\n" +
                "Si Lino naman ay patuloy sa pag-aaral. Sumali rin siya sa basketbol team ng paaralan at naging abala siya roon. Nagkaroon siya ng mga bagong kaibigan na sina Francis, Stanley, at Jacob.\n" +
                "Isang araw, pinuntahan ni Tomas si Lino sa paaralan at niyaya mag-merienda.\n" +
                "\n" +
                "Gusto sanang samahan ng binata ang kaibigan kaya lang ay may lakad rin sila ng mga bago niyang kaibigan. Umuwi na lamang si Tomas.\n" +
                "\n" +
                "Lumipas ang isang linggo ng hindi nagkikita sina Lino at Tomas. Dahil sa dami ng mga nangyayari sa buhay niya, nakalimutan ni Lino na may pinagdadaanan ang matalik niyang kaibigan.\n" +
                "\n" +
                "Isang gabi, tinawagan ni Tomas si Lino. Kinumusta niya ito at nagpasalamat rin siya sa kanilang pagkakaibigan.\n" +
                "\n" +
                "“Tol, maraming salamat rin sa lahat ng iyong kabutihan sa akin at sa pamilya ko pero bakit parang ang senti mo talaga ngayon,” tanong ni Lino sa kaibigan.\n" +
                "\n" +
                "Hindi umimik si Tomas. Niyaya niya ulit si Lino na lumabas sila at susunduin niya ito.\n" +
                "\n" +
                "“Pasensya na tol maaga pa kasi ako bukas. Ang kulit kasi nina Jacob niyaya nila akong sumama sa outing nila ng mga kaklase niya,” pagtanggi ni Lino sa kaibigan.\n" +
                "\n" +
                "Hindi ipinahalata ni Tomas na sabik na siyang makausap ang kaibigan. Masaya siya at maganda ang takbo ng buhay ni Lino.\n" +
                "\n" +
                "Kinabukasan, nagising si Lino sa malakas na katok ng ina niya. Sumisigaw ito habang kumakatok sa pintuan ng kwarto ng anak.\n" +
                "\n" +
                "“Lino! Nak! Gising! May nangyari kay Tomas! Nak! Buksan mo ang pinto,” sigaw ni Aling Susan.\n" +
                "\n" +
                "Nagulat at natulala ng saglit si Lino sa mga narinig niya. Noong mahimasmasan na ito ay lumapit siya sa pintuan at binuksan niya ang pinto.\n" +
                "\n" +
                "“Nak wala na si Tomas! Na-aksidente raw siya kagabi dahil sa sobrang kalasingan,” sabi ni Aling Susan sa anak habang niyayakap ito.\n" +
                "\n" +
                "Hindi namalayan ni Lino na tumutulo na ang mga luha mula sa mga mata niya. Wala na ang kanyang matalik na kaibigan na parang kapatid na niya.\n" +
                "\n" +
                "“Sana sinamahan ko na lang siya noong nag-aya siya kagab-i. Baka hindi pa nangyari ‘yon,” sabi ng binata.\n" +
                "\n" +
                "Pumunta si Lino at ang pamilya niya sa lamay ni Tomas. Nandoon ang mama at papa niya. Umuwi ang ama ng binata mula China upang maihatid ang anak sa huling hantungan nito.\n" +
                "\n" +
                "“Tomas, tol patawad. Patawarin mo ko at hindi kita nabigyan ng oras. Patawad dahil wala ako noong kailangan na kailangan mo ako,” sabi ni Lino sa harap ng kabaong ng yumaong kaibigan.\n" +
                "\n" +
                "Masakit para kay Lino ang nangyari kay Tomas. Araw-araw, hindi niya maiwasan na sisihin ang sarili niya sa mga pagkukulang niya bilang isang kaibigan. Subalit, umaasa ang binata na isang araw ay mapatawad niya rin ng lubusan ang sarili niya.";

        jtv.setText(Story3);
        TitleStory.setText(PStories.title3);

    }


    public void BackToStories(View view) {
        startActivity(new Intent(this, PStories.class));
    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.lino_tomas);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PStories.class));
    }
}