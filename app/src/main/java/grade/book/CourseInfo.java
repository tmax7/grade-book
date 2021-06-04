package grade.book;

import grade.book.wrappers.CourseInfoWrapper;
import grade.book.wrappers.StudentWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;



public class CourseInfo {
    private StringProperty courseName = new SimpleStringProperty("none");
    private StringProperty coursePeriod = new SimpleStringProperty("none");
    private StringProperty semester = new SimpleStringProperty("none");;
    private StringProperty schoolYear = new SimpleStringProperty("none");;
    private ObservableList<GradedItem> gradedItems = FXCollections.observableArrayList();
    private ObservableList<Student> students = FXCollections.observableArrayList();

    private int indexOfGradedItems = 0;

    /*
        Constructors
    */
    public CourseInfo(){
    }

    public CourseInfo(String courseName, String coursePeriod, String semester, String schoolYear, ObservableList<GradedItem> gradedItems, ObservableList<Student> students) {

        if(courseName != null) { this.courseName.setValue(courseName); }
        if(coursePeriod != null){ this.coursePeriod.setValue(coursePeriod); }
        if(semester != null) { this.semester.setValue(semester); }
        if(schoolYear != null) { this.schoolYear.setValue(schoolYear); }



        /* TODO make sure students and Course have same gradedItems
            it is important to maintain this link so therefore any update to graded items should update students gradedItems
         */
        if(gradedItems != null){
            this.gradedItems = gradedItems;
            for(Student student : students){
                student.setGradedItems(gradedItems);
            }
        }

        if(students != null){
        this.students = students;
        }
    }

    // This is the constructor used to unwrap a CourseInfoWrapper object into a CourseInfo object
    public CourseInfo(CourseInfoWrapper courseInfoWrapper) {
        if(courseInfoWrapper.getCourseName() != null) { this.courseName.setValue(courseInfoWrapper.getCourseName()); }
        if(courseInfoWrapper.getCoursePeriod() != null) { this.coursePeriod.setValue(courseInfoWrapper.getCoursePeriod()); }
        if(courseInfoWrapper.getSemester() != null) { this.semester.setValue(courseInfoWrapper.getSemester()); }
        if(courseInfoWrapper.getSchoolYear() != null) { this.schoolYear.setValue(courseInfoWrapper.getSchoolYear()); }

        for(GradedItem gradedItem : courseInfoWrapper.getGradedItems()){
            this.gradedItems.add(new GradedItem(gradedItem));
        }

        for(StudentWrapper studentWrapper : courseInfoWrapper.getStudents()){
            Student student = new Student(studentWrapper);
            this.students.add(student);
        }

        this.indexOfGradedItems = courseInfoWrapper.getIndexOfGradedItems();
    }
    /**/

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

    public String getSemester() {
        return semester.get();
    }

    public StringProperty semesterProperty() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getSchoolYear() {
        return schoolYear.get();
    }

    public StringProperty schoolYearProperty() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear.set(schoolYear);
    }

    public ObservableList<GradedItem> getGradedItems() {
        return gradedItems;
    }

    public void setGradedItems(ObservableList<GradedItem> gradedItems) {
        this.gradedItems = gradedItems;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ObservableList<Student> students) {
        this.students = students;
    }

    public int getIndexOfGradedItems() {
        return indexOfGradedItems;
    }

    // Gets gradedItems and students as an ArrayList for conversion to xml
    public ArrayList<GradedItem> getGradedItemsAsArrayList() {
        return new ArrayList<GradedItem>(this.gradedItems);
    }

    public ArrayList<Student> getStudentsAsArrayList() {
        return new ArrayList<Student>(this.students);
    }

    @Override
    public String toString() {
            if(this.courseName != null && this.coursePeriod != null){
                return this.getCourseName() + " " + this.getCoursePeriod();
            } else {
                return this.courseName + " " + this.coursePeriod;
            }
    }

    public void incrementGradedItemIndex(){
        this.indexOfGradedItems++;
    }

    public void addStudent(Student student){
        if(this.gradedItems != null) {
            for (GradedItem gradedItem : this.gradedItems) {
                student.addGradedItem(new GradedItem(gradedItem));
            }
        }
        this.students.add(student);
    }
}

