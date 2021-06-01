package grade.book;

import grade.book.wrappers.StudentWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Student {
    private StringProperty courseName = new SimpleStringProperty("none");
    private StringProperty coursePeriod  = new SimpleStringProperty("none");
    private StringProperty studentId  = new SimpleStringProperty("none");
    private StringProperty firstName  = new SimpleStringProperty("none");
    private StringProperty lastName  = new SimpleStringProperty("none");
    //Can't call attribute "class" because "class" is obviously a reserved work in Java.

    private ObservableList<GradedItem> gradedItems = FXCollections.observableArrayList();

    private StringProperty percent = new SimpleStringProperty("none");
    private StringProperty grade = new SimpleStringProperty("none");

    //TODO possibly add notes
    // private StringProperty notes

    public Student() {
    }

    public Student(String courseName, String coursePeriod, String studentId, String firstName, String lastName, ObservableList<GradedItem> gradedItems) {
        if(courseName != null){ this.courseName.setValue(courseName); }
        if(coursePeriod != null) { this.coursePeriod.setValue(coursePeriod); }
        if(studentId != null) { this.studentId.setValue(studentId); }
        if(firstName != null) { this.firstName.setValue(firstName); }
        if(lastName != null) { this.lastName.setValue(lastName); }


        if(gradedItems != null) {
            this.gradedItems = gradedItems;
            calculatePercentAndGrade();
        }
    }

    //unwrap constructor
    public Student(StudentWrapper studentWrapper){
        if(studentWrapper.getCourseName() != null) { this.courseName.setValue(studentWrapper.getCourseName()); }
        if(studentWrapper.getCoursePeriod() != null) { this.coursePeriod.setValue(studentWrapper.getCoursePeriod()); }
        if(studentWrapper.getStudentId() != null) { this.studentId.setValue(studentWrapper.getStudentId()); }
        if(studentWrapper.getFirstName() != null) { this.firstName.setValue(studentWrapper.getFirstName()); }
        if(studentWrapper.getLastName() != null) { this.lastName.setValue(studentWrapper.getLastName()); }

        if(studentWrapper.getGradedItems() != null){
            for(GradedItem gradedItem : studentWrapper.getGradedItems()){
                this.gradedItems.add(gradedItem);
            }
            calculatePercentAndGrade();
        }


    }

   //getters and setters
    public String getCourseName() {
        return courseName.get();
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public String getCoursePeriod() {
        return coursePeriod.get();
    }

    public StringProperty coursePeriodProperty() {
        return coursePeriod;
    }

    public void setCoursePeriod(String coursePeriod) {
        this.coursePeriod.set(coursePeriod);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public StringProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    //TODO find out if making this package private has any negative effects.
    public ObservableList<GradedItem> getGradedItems() {
        return gradedItems;
    }

    public void setGradedItems(ObservableList<GradedItem> gradedItems) {
        this.gradedItems = gradedItems;
    }

    public String getPercent() {
        return percent.get();
    }

    public StringProperty percentProperty() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent.set(percent);
    }

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    //get gradedItems as an ArrayList for conversion to xml
    public ArrayList<GradedItem> getGradedItemsAsArrayList() {
        return new ArrayList<>(this.gradedItems);
    }


    //add to gradedItems and recalculate percent and letter grade
    public void addGradedItem(GradedItem gradedItem) {
        this.gradedItems.add(gradedItem);
        calculatePercentAndGrade();
    }

    public void calculatePercentAndGrade() {

        double percent;
        int score = 0;
        int maxScore = 0;
//TODO make sure that seting score+= to maxScore if no score is assigned to it works out. And find out if later updating the score
// actually changes it as well
        //if there are no graded items then there is no percent or grade
        if(gradedItems.size() > 0){
            for (GradedItem item : gradedItems) {
                if(item != null){
                    if(!(item.getScore().equals("none")) || item.getMaxScore().equals("none")){
                        score += Integer.parseInt(item.getScore());
                        maxScore += Integer.parseInt(item.getMaxScore());
                    } else if(!(item.getMaxScore().equals("none"))) {
                        score += Integer.parseInt(item.getMaxScore());
                        maxScore += Integer.parseInt(item.getMaxScore());
                    } else {
                        score += 0;
                        maxScore += 0;
                    }
                }
            }
            //prevent division by 0.0 if maxScore is somehow 0;
            double scoreAsDouble;
            double maxScoreAsDouble;
            if(maxScore > 0){
                scoreAsDouble = score;
                maxScoreAsDouble = maxScore;
            } else {
                scoreAsDouble = 1.0;
                maxScoreAsDouble = 1.0;
            }

            percent = (scoreAsDouble / maxScoreAsDouble) * 100.0;

            String percentString = String.format("%.2f", percent);
            this.percent.setValue(percentString);
            calculateGrade();
        }
    }
    // changed from public
    private void calculateGrade() {
            String letter = percentToLetter(this.percent.get());
            this.grade.setValue(letter);
    }

    public String percentToLetter(String percent){
        double percentDouble = Double.parseDouble(percent);
        String letter = "None";

        if      (percentDouble >= 90.0) { letter = "A"; }
        else if (percentDouble >= 80.0) { letter = "B"; }
        else if (percentDouble >= 70.0) { letter = "C"; }
        else if (percentDouble >= 60.0) { letter = "D"; }
        else if (percentDouble <  60.0) { letter = "F"; }

        return letter;
    }
}
