package cfscript.library;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

/**
 * This static library is imported where ever these built-in functions are needed.
 */
public class StdLib {
    /**
     ******************
     * ARRAY FUNCTIONS
     ******************
     */
    /**
     * Adds an element to an array
     * @param list The Arraylist to add the item to.
     * @param element Any element to add (int, string, struct etc)
     */
    public static void arrayAppend(List<Object> list, Object element) {
        list.add(element);
    }

    /**
     * Checks if an element is present in an array
     * @param list The Array to check for the item.
     * @param element Any item to use for the lookup.
     */
    public static boolean arrayContains(List<Object> list, Object element) {
        return list.contains(element);
    }

    /**
     * Clears all elements from the array
     * @param list The Array to clear.
     */
    public static void arrayClear(List<Object> list) {
        list.clear();
    }

    /**
     * Returns the size of an array
     * @param list The Array to return the size of.
     */
    public static int arrayLen(List<Object> list) {
        return list.size();
    }

    /**
     * Returns the size of an array
     * @param list The Array to return the size of.
     */
    public static void arrayDeleteAt(List<Object> list, int index) throws IllegalArgumentException, IndexOutOfBoundsException {
        // Validate parameters
        if (list == null) {
            throw new IllegalArgumentException("List must not be null");
        }
        if (index < 1 || index > list.size()) {  // Adjusting index to 1-based index to match CFScript
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        // TODO: Decide on 0 or 1 based indexing...if 1, Remove element at index - 1 (converting from 1-based index to 0-based index to match Java's indexing)
        list.remove(index);
    }

    public static boolean arrayIsDefined(List<Object> list, int index) throws IllegalArgumentException, IndexOutOfBoundsException {
        // Validate parameters
        if (list == null || index < 1 || index > list.size()) {  // Adjusting index to 1-based index to match CFScript
            return false;
        }

        return true;
    }

    /**
     * ******************
     * LIST FUNCTIONS
     * ******************
     */
    public static List<Object> listToArray(String list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();  // Return an empty ArrayList if the list is null or empty.
        }
        if (delimiter == null || delimiter.isEmpty()){
            delimiter = ",";
        }
        String[] array = list.split(delimiter);  // Split the list string into an array using the specified delimiter.
        return new ArrayList<Object>(Arrays.asList(array));  // Convert the array to a mutable ArrayList and return it.
    }

    /**
     * Returns the length of an array. 0 if the array is null or empty.
     * @param list
     * @param delimiter
     * @return The length of the list
     */
    public static int listLen(String list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        if (delimiter == null || delimiter.isEmpty()){
            delimiter = ",";
        }
        String[] array = list.split(delimiter);  // Split the list string into an array using the specified delimiter.
        return array.length;  // Convert the array to get the length
    }

    /**
     * ******************
     * STRUCT FUNCTIONS
     * ******************
     */
    public static boolean structKeyExists(Map<String, Object> map, String key) {
        return map.containsKey(key);
    }

    /**
     * STRING FUNCTIONS
     */
    // LCase function
    public static String lCase(String str) {
        return str.toLowerCase();
    }

    // UCase function
    public static String uCase(String str) {
        return str.toUpperCase();
    }

    // Trim function
    public static String trim(String str) {
        return str.trim();
    }

    // Left function
    public static String left(String str, int count) {
        return str.substring(0, Math.min(count, str.length()));
    }

    // Right function
    public static String right(String str, int count) {
        return str.substring(Math.max(0, str.length() - count));
    }

    // Replace function
    public static String replace(String str, String subStr, String replacement) {
        return str.replace(subStr, replacement);
    }

    // REReplace function
    public static String reReplace(String str, String regex, String replacement) {
        return str.replaceAll(regex, replacement);
    }

    // REReplaceNoCase function
    public static String reReplaceNoCase(String str, String regex, String replacement) {
        return str.replaceAll("(?i)" + regex, replacement);
    }

    /**
     *******************
     * DATE TIME FUNCTIONS
     *******************
     */

    /**
     * Returns the local datetime.
     * @return LocalDateTime
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime createDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    public static LocalDate createDate() {
        LocalDate now = LocalDate.now();
        return now;
    }

    /**
     * Formats a DateTime based on some pattern.
     * @param dt DateTime defaults to Now
     * @param pattern Default pattern "dd-MMM-yyyy HH:mm:ss"
     * @return The formatted datetime string.
     */
    public static String dateTimeFormat(LocalDateTime dt, String pattern) {
        if (dt == null) {
            dt = StdLib.now();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = null;
        if (pattern != null && !pattern.isEmpty()) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }else{
            formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"); // default pattern
        }
        return now.format(formatter);
    }

    public static long dateDiff(String unit, LocalDate date1, LocalDate date2) throws IllegalArgumentException {
        ChronoUnit chronoUnit;
        switch (unit.toLowerCase()) {
            case "yyyy":
                chronoUnit = ChronoUnit.YEARS;
                break;
            case "m":
                chronoUnit = ChronoUnit.MONTHS;
                break;
            case "d":
                chronoUnit = ChronoUnit.DAYS;
                break;
            default:
                throw new IllegalArgumentException("Unsupported unit: " + unit);
        }
        return chronoUnit.between(date1, date2);
    }

    // DateAdd function
    public static Temporal dateAdd(String unit, int number, Temporal date) throws IllegalArgumentException {
        switch (unit.toLowerCase()) {
            case "yyyy":
                return date.plus(number, ChronoUnit.YEARS);
            case "q":
                return date.plus(number * 3, ChronoUnit.MONTHS);
            case "m":
                return date.plus(number, ChronoUnit.MONTHS);
            case "y":
            case "d":
                return date.plus(number, ChronoUnit.DAYS);
            case "w":
                return date.plus(number * 7, ChronoUnit.DAYS);
            case "ww":
                return date.plus(number, ChronoUnit.WEEKS);
            case "h":
                return date.plus(number, ChronoUnit.HOURS);
            case "n":
                return date.plus(number, ChronoUnit.MINUTES);
            case "s":
                return date.plus(number, ChronoUnit.SECONDS);
            default:
                throw new IllegalArgumentException("Unsupported unit: " + unit);
        }
    }

    /**
     *******************
     * LANGUAGE SPECIFIC
     *******************
     */
    public static boolean isNull(Object any) {
        if (any == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object any) {
        if (any != null) {
            return true;
        }
        return false;
    }

    // len function for strings
    public static int len(String str) {
        return str.length();
    }

    // len function for arrays
    public static int len(Object[] arr) {
        return arr.length;
    }

    // len function for lists
    public static int len(List<?> list) {
        return list.size();
    }

    // len function for collections
    public static int len(Collection<?> collection) {
        return collection.size();
    }

    // len function for maps
    public static int len(Map<?, ?> map) {
        return map.size();
    }

    public static void doThrow(String msg) throws Exception {
        throw new Exception(msg);
    }

    public static boolean isNumeric(Object obj) {
        return obj instanceof Double || obj instanceof Integer;
    }

    public static boolean isNumericDate(String str) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            LocalDate.parse(str, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isSimpleValue(Object obj) {
        return obj instanceof Boolean || obj instanceof String || obj instanceof Double || obj instanceof Integer;
    }

    public static boolean isStruct(Object obj) {
        return obj instanceof HashMap;
    }

    public static boolean isArray(Object obj) {
        return obj instanceof ArrayList;
    }

    public static boolean isBoolean(Object obj) {
        return obj instanceof Boolean;
    }

    public static boolean isDate(String str) {
        try {
            LocalDate.parse(str);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isDateObject(Object obj) {
        return obj instanceof LocalDate;
    }

    public static boolean isObject(Object obj) {
        return (obj instanceof HashMap || obj instanceof ArrayList) && !(isSimpleValue(obj));
    }

    public static boolean structIsEmpty(HashMap<?, ?> hashMap) {
        return hashMap.isEmpty();
    }
}
