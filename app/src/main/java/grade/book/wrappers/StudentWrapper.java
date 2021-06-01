package grade.book.wrappers;

import grade.book.GradedItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import grade.book.Student;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class StudentWrapper {
    private StringProperty courseName = new SimpleStringProperty("none");
    private StringProperty coursePeriod = new SimpleStringProperty("none");
    private StringProperty studentId = new SimpleStringProperty("none");
    private StringProperty firstName = new SimpleStringProperty("none");
    private StringProperty lastName = new SimpleStringProperty("none");
    private ArrayList<GradedItem> gradedItems = new ArrayList<>();
    private StringProperty percent = new SimpleStringProperty("none");
    private StringProperty grade = new SimpleStringProperty("none");

    public StudentWrapper(){
    }

    public StudentWrapper(Student student){
        if(student.getCourseName() != null) { this.courseName.setValue(student.getCourseName()); }
        if(student.getCoursePeriod() != null) { this.coursePeriod.setValue(student.getCoursePeriod()); }
        if(student.getStudentId() != null) { this.studentId.setValue(student.getStudentId()); }
        if(student.getFirstName() != null)  {this.firstName.setValue(student.getFirstName()); }
        if(student.getLastName() != null) { this.lastName.setValue(student.getLastName()); }
        if(student.getGradedItemsAsArrayList() != null) { this.gradedItems.addAll(student.getGradedItemsAsArrayList()); }
        if(student.getPercent() != null) { this.percent.setValue(student.getPercent()); }
        if(student.getGrade() != null) { this.grade.setValue(student.getGrade()); }
    }

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

    @XmlElementWrapper
    @XmlElement(name = "GradedItem")
    public ArrayList<GradedItem> getGradedItems() {
        return gradedItems;
    }

    public void setGradedItems(ArrayList<GradedItem> gradedItems) {
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
}
