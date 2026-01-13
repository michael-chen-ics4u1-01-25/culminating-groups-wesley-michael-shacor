package game;
import game.menu.*;
import java.util.ArrayList;
public class Cutscene {
    private ArrayList<Dialogue> dialogue;
    private int currentScene = 0;
    public Cutscene(ArrayList<Dialogue> dialogue){
        this.dialogue = dialogue;
    }
    public void update() {
        this.dialogue.get(currentScene).update();
    }
    public Dialogue getScene() {
        return this.dialogue.get(currentScene);
    }
    public boolean nextScene() {
        if (currentScene+1 < this.dialogue.size()) {
            currentScene += 1;
            return false;
        } else {
            return true;
        }
    }
}