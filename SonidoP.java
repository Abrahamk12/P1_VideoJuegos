import javax.sound.sampled.*;
import java.io.*;
public class SonidoP {
    private Clip clip;
    private String ruta;
    private AudioInputStream audioStream;
    private Long microSegundos;
    public SonidoP(String ruta){
        this.ruta = ruta;
        try{
            this.audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource(ruta));
            clip = AudioSystem.getClip();
        }catch(Exception ex){}
    }

    public void setSong(String ruta){
        try{
            this.audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource(ruta));
            clip = AudioSystem.getClip();
        }catch(Exception ex){}
    }
    
    public void play(){
        try{
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception ex){}
    }//end play

    public void pause(){
        microSegundos = clip.getMicrosecondPosition();
        clip.stop();
    }//end pause

    public void resume(){
        clip.close();
        try{
            audioStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
            play();
            clip.setMicrosecondPosition(microSegundos);
        }catch(Exception ex){}
    }//end resumen

    public void stopAlto(){
        microSegundos = 0L;
        clip.stop();
        clip.close();
    }//end stop audio
}
