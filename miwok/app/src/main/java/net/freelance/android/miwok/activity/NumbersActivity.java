package net.freelance.android.miwok.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.freelance.android.miwok.R;
import net.freelance.android.miwok.model.Word;
import net.freelance.android.miwok.adapter.WordAdapter;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private static final String Tag = "NumbersActivity";

    /**
     * Handles playback of all the sound files.
     */
    private MediaPlayer mMediaPlayer;

    /**
     * Handles audio focus when playing a sound file.
     */
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
     */

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device.)
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                //The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is allowed to continue playing around but at a lover volume.
                //We'll treat both cases the same way because our app is playing short sound files.

                //Pause playback and reset player to the start of the file. That way, we can play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //The AUDIOFOCUS_LOSS case means we've lost audio focus and Stop playback and clean up resources.
                releaseMediaPlayer();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and Setup the {@link AudioManager} to request audio focus.
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create an array of words.
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        //Create an ArrayAdapter object.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        //Typecast for listView on with XML
        ListView lv = (ListView) findViewById(R.id.listView);

        //Set the listView data with ArrayAdapter.
        lv.setAdapter(adapter);

        //Set a click listener to play the audio when the list item is clicked on
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //Release the media player if it currently exists because we are about to play a different sound file.
                releaseMediaPlayer();

                //Get the {@link Word} object at the given position the user clicked on
                Word eachWordAudio = words.get(position);

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // We've a audio focus now.

                    //Create and setup the {@link MediaPlayer} for the audio resource associated with the current word
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, eachWordAudio.getmAudioResourceId());

                    //Start the audio file.
                    mMediaPlayer.start();

                    //Setup a listener on the media player, so that we can stop and release the media player once the sounds has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because we won't be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {
            //Regardless of the current state of the media player, release its resources because we no longer need it.
            mMediaPlayer.release();

            //Set the media player back to null.For our code, we've decided that setting the media player to null is an easy way to tell that the media player isn't configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}

 /* //Old Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<String> words = new ArrayList<String>();
        //Create an array of words.
        String[] words = new String[10];
        words[0] = "One";
        words[1] = "Two";
        words[2] = "Three";
        words[3] = "Four";
        words[4] = "Five";
        words[5] = "Six";
        words[6] = "Seven";
        words[7] = "Eight";
        words[8] = "Nine";
        words[9] = "Ten";

        /Verify the contents of the Array by printing out each ArrayList element to the logs.
        Log.v(Tag, "OnCreate");
        Log.v(Tag, "Word at Index 0: " + words[0]);
        Log.v(Tag, "Word at Index 1: " + words[1]);


         //Create an array of words.
        ArrayList<String> words = new ArrayList<String>();
        words.add(new Word("One", "lutti"));
        words.add(new Word("Two", "otiko"));
        words.add(new Word("Three", "tolookosu"));
        words.add(new Word("Four", "oyyisa"));
        words.add(new Word("Five", "massokka"));
        words.add(new Word("Six", "temmokka"));
        words.add(new Word("Seven", "kenekaku"));
        words.add(new Word("Eight", "kawinta"));
        words.add(new Word("Nine", "wo'e"));
        words.add(new Word("Ten", "na'aacha"));

        //Verify the contents of the Array by printing out each ArrayList element to the logs.
        Log.v(Tag, "OnCreate");
        Log.v(Tag, "Word at Index 0: " + words.get(0));
        Log.v(Tag, "Word at Index 1: " + words.get(1));



        LinearLayout ll = (LinearLayout) findViewById(R.id.rootView);

        //Create a variable to keep track of the current index position.
        int index = 0;

        //Create a new {@link TextView} that displayed the word at the
        //and add the View as a child to the LinearLayout.
        TextView tv = new TextView(this);
        tv.setText(words.get(index));
        ll.addView(tv);

        index += 1;
        TextView tv2 = new TextView(this);
        tv2.setText(words.get(index));
        ll.addView(tv2);

        index += 1;
        TextView tv3 = new TextView(this);
        tv3.setText(words.get(index));
        ll.addView(tv3);

        int index = 0;
        while (index < words.size()) {
            //Create a new TextView
            TextView tv = new TextView(this);

            //Set the Text to be word at the current index.
            tv.setText(words.get(index));

            //Add this TextView as another child to the LinearLayout (or) rootView of this layout.
            ll.addView(tv);

            //Update counter variable
            ++index;
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.rootView);

        //Create a variable to keep track of the current index position.
        for(int index = 0; index < words.size(); index++){
            //Create a new TextView
            TextView tv = new TextView(this);

            //Set the Text to be word at the current index.
            tv.setText(words.get(index));

            //Add this TextView as another child to the LinearLayout (or) rootView of this layout.
            ll.addView(tv);
        }

           //Create an ArrayAdapter object.
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

        //Typecast for listView on with XML
        ListView lv = (ListView)findViewById(R.id.listView);

        //Set the listView data with ArrayAdapter.
        lv.setAdapter(itemsAdapter);
}*/
