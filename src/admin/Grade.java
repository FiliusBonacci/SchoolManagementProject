package admin;


public enum Grade {
       niedostateczny("2"),
       dostateczny("3"),
       dobry("4"),
       bardzoDobry("5");

    Grade(String s) {

    }

    public String value(){
            return name();
        }

        public static Grade fromvalue(String v){
            return valueOf(v);
        }

}
