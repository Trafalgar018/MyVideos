package com.example.trafalgar.myplaylist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.trafalgar.myplaylist.Videos;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import com.example.trafalgar.myplaylist.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyCTkK2UmSVbjNLteWq1zjHQpJucD_w0bO4";


    public Button mDownloadRss;
    public ListView mListVideos;
    public Videos videos;
    public String idVideo;

    private String mFileContent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadRss = (Button) findViewById(R.id.downloadRss);
        mListVideos = findViewById(R.id.listViewVideos);

        mDownloadRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseVideos parseVideos = new ParseVideos(mFileContent);
                parseVideos.process();

                Adapter adapterVideos = new Adapter(
                        MainActivity.this,parseVideos.getVideos()

                );

                mListVideos.setAdapter(adapterVideos);

                mListVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        videos = parseVideos.getVideos(position);
                        idVideo = videos.getId();
                        Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(
                                MainActivity.this, YOUTUBE_API_KEY, idVideo);
                        startActivity(videoIntent);
                    }
                });
            }
        });


        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://www.youtube.com/feeds/videos.xml?playlist_id=PLYHgAaCzZGSivX9I39k7SdS974QmY01_o");

    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... strings) {
            mFileContent = downloadXmlFile(strings[0]);

            if( mFileContent == null ){
                Log.d(TAG, "Problema descargando el XML");
            }

            return mFileContent;
        }

        /**
         * Construyo un String con el contenido del archivo XML
         * indicado en la url que se pasa como parámetro.
         *
         * @param urlPath Url del recurso que se quiere parsear
         * @return
         */
        public String downloadXmlFile(String urlPath){

            //StringBuilder vs StringBuffer
            StringBuilder tempBuffer = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new char[500];

                while( true ) {
                    charsRead = isr.read(inputBuffer);

                    if( charsRead <= 0 ) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();

            }catch (IOException e){
                Log.d(TAG, "Error: No se pudo descargar el RSS - " + e.getMessage());
            }

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param result The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
