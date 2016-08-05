package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksa on 7/29/16.
 *
 * Represents a single step in the recipe
 * TODO
 */
public class Step extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("seq_num")
    private int sequenceIndex;
    @SerializedName("timer")
    private int timer;
    @SerializedName("timer_name")
    private String timerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSequenceIndex() {
        return sequenceIndex;
    }

    public void setSequenceIndex(int sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }
}
