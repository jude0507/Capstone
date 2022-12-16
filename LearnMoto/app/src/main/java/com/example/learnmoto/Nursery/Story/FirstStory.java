package com.example.learnmoto.Nursery.Story;

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
import com.example.learnmoto.Nursery.Stories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class FirstStory extends AppCompatActivity {

    String Story1;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story1 = "Once upon a time, a lion was sleeping in the jungle when a mouse started running up and down his body just for fun. This disturbed the lion’s sleep, and he woke up quite angry. He was about to eat the mouse when the mouse desperately requested the lion to set him free. “I promise you, I will be of great help to you someday if you save me.” The lion laughed at the mouse’s confidence and let him go.\n" +
                "\n" +
                "One day, a few hunters came into the forest and took the lion with them. They tied him up against a tree. The lion was struggling to get out and started to whimper. Soon, the mouse walked past and noticed the lion in trouble. Quickly, he ran and gnawed on the ropes to set the lion free. Both of them sped off into the jungle.\n";

        jtv.setText(Story1);
        TitleStory.setText("The Lion and The Mouse");

//        Story2 ="One summer’s day, in a field, a Grasshopper was hopping about, chirping and singing to its heart's content. An Ant passed by, bearing along with great effort an ear of corn he was taking to his nest.\n" +
//                "\"Why don’t you come and chat with me,\" asked the Grasshopper, \"instead of toiling your life away?\"\n" +
//                "\"I am helping to store up food for the winter,\" said the Ant, \"and I recommend you to do the same.\"\n" +
//                "\"Why bother about winter?\" said the Grasshopper. \"We have got plenty of food at present.\"\n" +
//                "\n" +
//                "But the Ant went on its way and continued its toil.\n" +
//                "When winter came, the Grasshopper found itself dying of hunger, while it saw the ants distributing, every day, corn and grain from the stores they had collected in summer.\n" +
//                "Then the Grasshopper knew...\n";
//
//        Story3= "On a hot scorching day of summer, an ant was walking around in search of water. After walking around for some time, she saw a river and was delighted to see it. She climbed up on a small rock to drink the water, but she slipped and fell into the river. She was drowning but a dove who was sitting on a nearby tree helped her. Seeing the ant in trouble, the dove quickly dropped a leaf into the water. The ant moved towards the leaf and climbed up on it. The dove then carefully pulled the leaf out and placed it on the land. This way, the ant’s life was saved and she was forever indebted to the dove.\n" +
//                "\n" +
//                "The ant and the dove became the best of friends and days passed happily. However, one day, a hunter arrived at the forest. He saw the beautiful dove sitting on the tree and aimed his gun at the dove. The ant, who was saved the dove saw this and bit on the heel of the hunter. He shouted from the pain and dropped the gun. The dove was alarmed by the voice of the hunter and realised what could have happened with him. He flew away!\n";
//
//        Story4 = "A boy named Raj was upset because he had done poorly in his English test. He was sitting in his room when his grandmother came and comforted him. His grandmother sat beside him and gave him a pencil. Raj looked at his grandma puzzled, and said he didn’t deserve a pencil after his performance in the test.\n" +
//                "\n" +
//                "His grandma explained, “You can learn many things from this pencil because it is just like you. It experiences a painful sharpening, just the way you have experienced the pain of not doing well on your test. However, it will help you be a better student. Just as all the good that comes from the pencil is from within itself, you will also find the strength to overcome this hurdle. And finally, just as this pencil will make its mark on any surface, you too shall leave your mark on anything you choose to.” Raj was immediately consoled and promised himself that he would do better.\n";

    }

    public void Play(View view) {

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.mouse_and_lion);
            mediaPlayer.setOnCompletionListener(mp -> stopMediaPlayer());
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

    public void BackToStories(View view) {
        startActivity(new Intent(this, Stories.class));
    }

}