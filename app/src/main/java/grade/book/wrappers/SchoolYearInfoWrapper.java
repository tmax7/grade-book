package grade.book.wrappers;

import grade.book.CourseInfo;
import grade.book.SchoolYearInfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement
public class SchoolYearInfoWrapper {
    private String schoolYear = "none";
    private ArrayList<CourseInfoWrapper> listOfFallCourses = new ArrayList<>();
    private ArrayList<CourseInfoWrapper> listOfSpringCourses = new ArrayList<>();

    public SchoolYearInfoWrapper() {
    }

    public SchoolYearInfoWrapper(SchoolYearInfo schoolYearInfo){
        if(schoolYearInfo.getSchoolYear() != null) { this.schoolYear = schoolYearInfo.getSchoolYear(); }
        for(CourseInfo courseInfo: schoolYearInfo.getFallCourseListAsArrayList()){
            CourseInfoWrapper courseInfoWrapper = new CourseInfoWrapper(courseInfo);
            listOfFallCourses.add(courseInfoWrapper);
        }
        for(CourseInfo courseInfo: schoolYearInfo.getSpringCourseListAsArrayList()){
            CourseInfoWrapper courseInfoWrapper = new CourseInfoWrapper(courseInfo);
            listOfSpringCourses.add(courseInfoWrapper);
        }
    }

    @XmlElement(name = "SchoolYear")
    public String getSchoolYear(){
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    @XmlElementWrapper
    @XmlElement(name = "FallCourse")
    public ArrayList<CourseInfoWrapper> getListOfFallCourses() {
        return listOfFallCourses;
    }

    public void setListOfFallCourses(ArrayList<CourseInfoWrapper> listOfFallCourses) {
        this.listOfFallCourses = listOfFallCourses;
    }

    @XmlElementWrapper
    @XmlElement(name = "SpringCourse")
    public ArrayList<CourseInfoWrapper> getListOfSpringCourses() {
        return listOfSpringCourses;
    }

    public void setListOfSpringCourses(ArrayList<CourseInfoWrapper> listOfSpringCourses) {
        this.listOfSpringCourses = listOfSpringCourses;
    }
}
