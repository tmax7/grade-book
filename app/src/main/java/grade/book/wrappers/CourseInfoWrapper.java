package grade.book.wrappers;

import grade.book.GradedItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import grade.book.CourseInfo;
import grade.book.Student;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class CourseInfoWrapper {
    private StringProperty courseName = new SimpleStringProperty("none");
    private StringProperty coursePeriod = new SimpleStringProperty("none");
    private StringProperty semester = new SimpleStringProperty("none");
    private StringProperty schoolYear = new SimpleStringProperty("none");
    private ArrayList<GradedItem> gradedItems = new ArrayList<>();
    private ArrayList<StudentWrapper> students = new ArrayList<>();
    private int indexOfGradedItems = 0;

    public CourseInfoWrapper(){
    }

    CourseInfoWrapper(CourseInfo courseInfo){
        if(courseInfo.getCourseName() != null){ this.courseName.setValue(courseInfo.getCourseName()); }
        if(courseInfo.getCoursePeriod() != null) {this.coursePeriod.setValue(courseInfo.getCoursePeriod()); }
        if(courseInfo.getSemester() != null) { this.semester.setValue(courseInfo.getSemester()); }
        if(courseInfo.getSchoolYear() != null) { this.schoolYear.setValue(courseInfo.getSchoolYear()); }
        for(GradedItem gradedItem : courseInfo.getGradedItemsAsArrayList()){
            this.gradedItems.add(new GradedItem(gradedItem));
        }
        for(Student student : courseInfo.getStudentsAsArrayList()){
            StudentWrapper studentWrapper = new StudentWrapper(student);
            this.students.add(studentWrapper);
        }
        this.indexOfGradedItems = courseInfo.getIndexOfGradedItems();
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

    @XmlElementWrapper
    @XmlElement(name ="GradedItem")
    public ArrayList<GradedItem> getGradedItems() {
        return gradedItems;
    }

    public void setGradedItems(ArrayList<GradedItem> gradedItems) {
        this.gradedItems = gradedItems;
    }

    @XmlElementWrapper
    @XmlElement(name = "Student")
    public ArrayList<StudentWrapper> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentWrapper> students) {
        this.students = students;
    }

    public int getIndexOfGradedItems() {
        return indexOfGradedItems;
    }

    public void setIndexOfGradedItems(int indexOfGradedItems) {
        this.indexOfGradedItems = indexOfGradedItems;
    }
}
