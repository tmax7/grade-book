package grade.book;

public class SchoolYear {
    private String startYear;
    private String endYear;

    public SchoolYear(int startYear, int endYear){
       if(startYear < endYear){
           this.startYear = Integer.toString(startYear);
           this.endYear = Integer.toString(endYear);
       }
    }
    
    public String getString() {
        return startYear + " - " + endYear;
    }
}
