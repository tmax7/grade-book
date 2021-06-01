package grade.book;

import grade.book.wrappers.CourseInfoWrapper;
import grade.book.wrappers.SchoolYearInfoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

// TODO listOfFallCourses and listOfSpringCourses should not be set to null in other code but should have .clearAll() used, but check that default constructor doesn't cause problems.
public class SchoolYearInfo {
    private String schoolYear = "none";
    private ObservableList<CourseInfo> listOfFallCourses = FXCollections.observableArrayList() ;
    private ObservableList<CourseInfo> listOfSpringCourses = FXCollections.observableArrayList();

    SchoolYearInfo(){
    }

    public SchoolYearInfo(SchoolYear schoolYear) {
        if(schoolYear != null) { this.schoolYear = schoolYear.getString(); }
    }

    SchoolYearInfo(SchoolYear schoolYear, ObservableList<CourseInfo> listOfFallCourses, ObservableList<CourseInfo> listOfSpringCourses){
        if(schoolYear != null) { this.schoolYear = schoolYear.getString(); }
        if(this.listOfFallCourses != null) { this.listOfFallCourses = listOfFallCourses; }
        if(this.listOfSpringCourses != null) { this.listOfSpringCourses = listOfSpringCourses; }
    }

    //unwrap constructor
    SchoolYearInfo(SchoolYearInfoWrapper schoolYearInfoWrapper){
        if(schoolYearInfoWrapper.getSchoolYear() != null) { this.schoolYear = schoolYearInfoWrapper.getSchoolYear(); }

        for(CourseInfoWrapper courseInfoWrapper : schoolYearInfoWrapper.getListOfFallCourses()){
            CourseInfo courseInfo = new CourseInfo(courseInfoWrapper);
            this.listOfFallCourses.add(courseInfo);
        }
        for(CourseInfoWrapper courseInfoWrapper : schoolYearInfoWrapper.getListOfSpringCourses()){
            CourseInfo courseInfo = new CourseInfo(courseInfoWrapper);
            this.listOfSpringCourses.add(courseInfo);
        }
    }

    //getters and setters
    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public ObservableList<CourseInfo> getListOfFallCourses() {
        return listOfFallCourses;
    }

    public void setListOfFallCourses(ObservableList<CourseInfo> listOfFallCourses) {
        this.listOfFallCourses = listOfFallCourses;
    }

    public ObservableList<CourseInfo> getListOfSpringCourses() {
        return listOfSpringCourses;
    }

    public void setListOfSpringCourses(ObservableList<CourseInfo> listOfSpringCourses) {
        this.listOfSpringCourses = listOfSpringCourses;
    }


    public ArrayList<CourseInfo> getFallCourseListAsArrayList(){
        return new ArrayList<CourseInfo>(this.listOfFallCourses);
    }

    public ArrayList<CourseInfo> getSpringCourseListAsArrayList(){
        return new ArrayList<CourseInfo>(this.listOfSpringCourses);
    }

    public void addToFallCourses(CourseInfo courseInfo){
        listOfFallCourses.add(courseInfo);
    }

    public void addToSpringCourses(CourseInfo courseInfo){
        listOfSpringCourses.add(courseInfo);
    }

    public void clearAll() {
        schoolYear = null;
        listOfFallCourses.clear();
        listOfSpringCourses.clear();
    }


}
