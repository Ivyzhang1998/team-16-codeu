package com.google.codeu.servlets;
// Imports the Google Cloud client library
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.OutputStream;

/** Takes requests that contain text and responds with an MP3 file speaking that text. */
@WebServlet("/a11y/tts")
public class TextToSpeechServlet extends HttpServlet {

 private TextToSpeechClient ttsClient;

 @Override
 public void init() {
   ttsClient = TextToSpeechClient.create();
 }

 /** Responds with an MP3 bytestream from the Google Cloud Text-to-Speech API */
 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


   //String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
   text  = "hello"

   // Text to Speech API
   SynthesisInput input = SynthesisInput.newBuilder()
           .setText(text)
           .build();

   // TODO(you): Fill in the gap here!
   // PS: consider the basic example and the Text-to-Speech documentation!
   VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
       .setLanguageCode("en-US")
       // Try experimenting with the different voices
       .setSsmlGender(SsmlVoiceGender.NEUTRAL)
       .build();

   AudioConfig audioConfig = AudioConfig.newBuilder()
        .setAudioEncoding(AudioEncoding.MP3)
        .build();

   SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
            audioConfig);

        // Get the audio contents from the response
   ByteString audioContents = response.getAudioContent();


   response.setContentType("audio/mpeg");

   try (
     ServletOutputStream output = response.getOutputStream();
     InputStream input = getServletContext().getResourceAsStream(responseFromAPI); // Placeholder!
   ){
     byte[] buffer = new byte[2048];
     int bytesRead;
     while ((bytesRead = input.read(buffer)) != -1) {
        output.write(buffer, 0, bytesRead);
     }
   }
 }
}
