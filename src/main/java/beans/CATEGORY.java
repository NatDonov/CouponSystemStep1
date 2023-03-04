package beans;

public enum CATEGORY {
    FOOD,
    ELECTRICITY,

    RESTAURANT,
    VACATION,
    COURSES,
    TOYS;

    public static CATEGORY getCategoryById(int id){
        return values()[id-1];
    }

}
