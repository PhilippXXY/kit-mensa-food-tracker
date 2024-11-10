package pack;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.kit.aifb.atks.mensascraper.lib.*;

/**
 * The {@code MensaData} class represents the data for a meal in a mensa.
 * It stores information such as the meal date, name, price, type, and
 * nutritional values.
 * 
 * @author Philipp Schmidt (uggro)
 * @since version 1.0
 */
public class MensaData {

    private String mealDate;
    private String mealName;
    private float mealPrice;
    private MensaMealType mealType;
    private float mealKcal;
    private float mealProteins;
    private float mealCarbs;
    private float mealFat;

    static LocalDate currentDate = LocalDate.now();
    static LocalDate date = currentDate;
    static LocalDate endDate = currentDate.plusWeeks(2);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static DecimalFormat df = new DecimalFormat("#.##");

    public static List<MensaData> mealDataList = new ArrayList<>();
    public static List<MensaData> selectedMealList = new ArrayList<>();

    /**
     * Constructs a new {@code MensaData} object with the specified meal
     * information.
     *
     * @param mealDate     the date of the meal
     * @param mealName     the name of the meal
     * @param mealPrice    the price of the meal
     * @param mealType     the type of the meal
     * @param mealKcal     the number of calories in the meal
     * @param mealProteins the amount of proteins in the meal
     * @param mealCarbs    the amount of carbohydrates in the meal
     * @param mealFat      the amount of fat in the meal
     */
    public MensaData(String mealDate, String mealName, float mealPrice, MensaMealType mealType,
            float mealKcal, float mealProteins, float mealCarbs, float mealFat) {
        this.mealDate = mealDate;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealType = mealType;
        this.mealKcal = mealKcal;
        this.mealProteins = mealProteins;
        this.mealCarbs = mealCarbs;
        this.mealFat = mealFat;
    }

    /**
     * Returns a string representation of the {@code MensaData} object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "MealData{" +
                "mealDate='" + mealDate + '\'' +
                ", mealName='" + mealName + '\'' +
                ", mealPrice=" + mealPrice +
                ", mealType=" + mealType +
                ", mealKcal=" + mealKcal +
                ", mealProteins=" + mealProteins +
                ", mealCarbs=" + mealCarbs +
                ", mealFat=" + mealFat +
                '}';
    }

    /**
     * Fetches the meal data from the library.
     * It uses the KITMensaScraper library to fetch meals for a specific location
     * and date range.
     * The fetched meals are stored in the mealDataList.
     */
    public static void fetchLibrary() {
        final KITMensaScraper mensa = new KITMensaScraper();

        while (date.isBefore(endDate)) {
            final List<MensaMeal> meals = mensa.fetchMeals(MensaLocation.ADENAUERRING, date);
            if (!meals.isEmpty()) {
                for (MensaMeal meal : meals) {
                    String mealDate = date.toString();
                    String mealName = meal.getName();
                    float mealPrice = meal.getPrice();
                    MensaMealType mealType = meal.getType();
                    float mealKcal = meal.getKcal();
                    float mealProteins = meal.getProteins();
                    float mealCarbs = meal.getCarbs();
                    float mealFat = meal.getFat();

                    MensaData mealData = new MensaData(mealDate, mealName, mealPrice, mealType,
                            mealKcal, mealProteins, mealCarbs, mealFat);

                    mealDataList.add(mealData);
                }
            }
            date = date.plusDays(1);
        }
    }

    /**
     * Creates a list of dates from the current date up to the end date.
     *
     * @return a list of LocalDate objects representing the dates
     */
    public static List<LocalDate> createDateList() {
        List<LocalDate> dates = new ArrayList<>();

        while (currentDate.isBefore(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }
    
  // Getters and setters for the class properties

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(float mealPrice) {
        this.mealPrice = mealPrice;
    }

    public MensaMealType getMealType() {
        return mealType;
    }

    public void setMealType(MensaMealType mealType) {
        this.mealType = mealType;
    }

    public float getMealKcal() {
        return mealKcal;
    }

    public void setMealKcal(float mealKcal) {
        this.mealKcal = mealKcal;
    }

    public float getMealProteins() {
        return mealProteins;
    }

    public void setMealProteins(float mealProteins) {
        this.mealProteins = mealProteins;
    }

    public float getMealCarbs() {
        return mealCarbs;
    }

    public void setMealCarbs(float mealCarbs) {
        this.mealCarbs = mealCarbs;
    }

    public float getMealFat() {
        return mealFat;
    }

    public void setMealFat(float mealFat) {
        this.mealFat = mealFat;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(LocalDate currentDate) {
        MensaData.currentDate = currentDate;
    }

    public static LocalDate getDate() {
        return date;
    }

    public static void setDate(LocalDate date) {
        MensaData.date = date;
    }

    public static LocalDate getEndDate() {
        return endDate;
    }

    public static void setEndDate(LocalDate endDate) {
        MensaData.endDate = endDate;
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        MensaData.formatter = formatter;
    }

    public static List<MensaData> getMealDataList() {
        return mealDataList;
    }

    public static void setMealDataList(List<MensaData> mealDataList) {
        MensaData.mealDataList = mealDataList;
    }

    public static List<MensaData> getSelectedMealList() {
        return selectedMealList;
    }

    public static void setSelectedMealList(List<MensaData> selectedMealList) {
        MensaData.selectedMealList = selectedMealList;
    }


}