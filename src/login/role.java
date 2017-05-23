package login;

/**
 * Created by pawel on 15.04.2017.
 */
public enum role {
    Admin, Student;

    public String value(){
        return name();
    }

    public static role fromvalue(String v){
        return valueOf(v);
    }
}
