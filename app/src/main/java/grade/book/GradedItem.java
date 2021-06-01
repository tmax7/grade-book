package grade.book;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import my.student.information.system.wrappers.GradedItemWrapper;

import javax.xml.bind.annotation.XmlRootElement;

//requires Xml annotation because class is used in marshalling of xml
@XmlRootElement
public class GradedItem {
    StringProperty ID = new SimpleStringProperty("none");
    StringProperty name = new SimpleStringProperty("none");
    StringProperty score = new SimpleStringProperty("none");
    StringProperty maxScore = new SimpleStringProperty("none");

    //IMPORTANT: percentGrade is a derived property of score and must be recalculated if score or maxScore is changed
    //using calculatePercentGrade(score, maxScore);
    StringProperty percentGrade = new SimpleStringProperty("none");

    // can/should it be enum? "LATE" "MISSING" etc.
    StringProperty note = new SimpleStringProperty("none");
    StringProperty description = new SimpleStringProperty("none");

    public GradedItem() {
        this(null, null, null, null, null, null);
    }

    public GradedItem(String ID, String name, String score, String maxScore, String note, String description) {
        if(ID != null){ this.ID.setValue(ID); }
        if(name != null) { this.name.setValue(name); }
        if(score != null) { this.score.setValue(score); }
        if(maxScore != null) { this.maxScore.setValue(maxScore); }
        if(score != null && maxScore != null) {
            double scoreInt = Double.parseDouble(score);
            double maxScoreInt = Double.parseDouble(maxScore);
            double percentGradeDouble = (scoreInt/maxScoreInt) * 100;
            String percentGradeString = String.format("%.2f", percentGradeDouble);
            this.percentGrade.setValue(percentGradeString);
        }
        if(note != null) { this.note.setValue(note); }
        if(description != null) {this.description.setValue(description); }

    }
    //deep copy constructor
    public GradedItem(GradedItem gradedItem){
       if(gradedItem.getID() != null) { this.ID.setValue(gradedItem.getID()); }

       if(gradedItem.getName() != null) { this.name.setValue(gradedItem.getName()); }
       if(gradedItem.getScore() != null) { this.score.setValue(gradedItem.getScore()); }
       if(gradedItem.getMaxScore() != null) { this.maxScore.setValue(gradedItem.getMaxScore()); }
       if(     gradedItem.getScore() != null
               && gradedItem.getMaxScore() != null
               && !(gradedItem.getScore().equals("none"))
               && !(gradedItem.getMaxScore().equals("none"))
       ) {

           double scoreInt = Double.parseDouble(gradedItem.getScore());
           double maxScoreInt = Double.parseDouble(gradedItem.getMaxScore());
           double percentGradeDouble = (scoreInt/maxScoreInt) * 100;
           String percentGradeString = String.format("%.2f", percentGradeDouble);
           this.percentGrade.setValue(percentGradeString);
       }
       if(gradedItem.getNote() != null) { this.note.setValue(gradedItem.getNote()); }
       if(gradedItem.getDescription() != null) { this.description.setValue(gradedItem.getDescription()); }
    }

    //unwrap constructor
//    public GradedItem(GradedItemWrapper gradedItemWrapper){
//        this.ID =gradedItemWrapper.IDProperty();
//        this.name = gradedItemWrapper.nameProperty();
//        this.score = gradedItemWrapper.scoreProperty();
//        this.maxScore = gradedItemWrapper.maxScoreProperty();
//        this.percentGrade = gradedItemWrapper.percentGradeProperty();
//        this.note = gradedItemWrapper.noteProperty();
//        this.description = gradedItemWrapper.descriptionProperty();
//    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getScore() {
        return score.get();
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
        calculatePercentGrade(score, this.maxScore.getValue());
    }

    public String getMaxScore() {
        return maxScore.get();
    }

    public StringProperty maxScoreProperty() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore.set(maxScore);
        calculatePercentGrade(this.score.getValue(), maxScore);
    }

    public String getPercentGrade() {
        return percentGrade.get();
    }

    public StringProperty percentGradeProperty() {
        return percentGrade;
    }

    //TODO see if can safely get rid of this method because it is unsafe because percentGrade should be derived propertly
    public void setPercentGrade(String percentGrade) {
        this.percentGrade.set(percentGrade);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    //
    public void calculatePercentGrade(String score, String maxScore){
        if(!(score.equals("none") || maxScore.equals("none"))){
            double scoreInt = Double.parseDouble(score);
            double maxScoreInt = Double.parseDouble(maxScore);

            double percentGradeDouble = (scoreInt/maxScoreInt) * 100;
            String percentGradeString = String.format("%.2f", percentGradeDouble);

            this.percentGrade.setValue(percentGradeString);
        }

    }
}


