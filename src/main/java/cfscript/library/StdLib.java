package cfscript.library;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
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
        return LocalDateTime.now();
    }

    public static LocalDate createDate() {
        return LocalDate.now();
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
        return switch (unit.toLowerCase()) {
            case "yyyy" -> date.plus(number, ChronoUnit.YEARS);
            case "q" -> date.plus(number * 3L, ChronoUnit.MONTHS);
            case "m" -> date.plus(number, ChronoUnit.MONTHS);
            case "y", "d" -> date.plus(number, ChronoUnit.DAYS);
            case "w" -> date.plus(number * 7L, ChronoUnit.DAYS);
            case "ww" -> date.plus(number, ChronoUnit.WEEKS);
            case "h" -> date.plus(number, ChronoUnit.HOURS);
            case "n" -> date.plus(number, ChronoUnit.MINUTES);
            case "s" -> date.plus(number, ChronoUnit.SECONDS);
            default -> throw new IllegalArgumentException("Unsupported unit: " + unit);
        };
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

    // 1. ExpandPath
    public static String expandPath(String basePath, String relativePath) {
        Path path = Paths.get(basePath, relativePath);
        return path.toAbsolutePath().toString();
    }

    // 2. FileAppend
    public static void fileAppend(String filePath, String content) throws IOException {
        Files.writeString(Paths.get(filePath), content, StandardOpenOption.APPEND);
    }

    // 3. FileClose
    public static void fileClose(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    // 4. FileCopy
    public static void fileCopy(String source, String destination) throws IOException {
        Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    // 5. FileDelete
    public static void fileDelete(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    // 6. FileExists
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }


    // 8. FileInfo
    public static BasicFileAttributes fileInfo(String filePath) throws IOException {
        return Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
    }

    // 9. FileIsEOF
    public static boolean fileIsEOF(BufferedReader reader) throws IOException {
        return !reader.ready();
    }

    // 10. FileMove
    public static void fileMove(String source, String destination) throws IOException {
        Files.move(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    // 11. FileOpen
    public static BufferedReader fileOpen(String filePath, String mode) throws FileNotFoundException {
        if (mode.equals("read")) {
            return new BufferedReader(new FileReader(filePath));
        }
        throw new IllegalArgumentException("Unsupported mode: " + mode);
    }

    // 12. FileRead
    public static String fileRead(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    // 13. FileReadBinary
    public static byte[] fileReadBinary(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    // 14. FileReadLine
    public static String fileReadLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    // 15. FileTouch
    public static void fileTouch(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!fileExists(filePath)) {
            Files.createFile(path);
        }
        Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
    }

    // 16. FileWrite
    public static void fileWrite(String filePath, String content) throws IOException {
        Files.writeString(Paths.get(filePath), content);
    }

    // 17. FileWriteLine
    public static void fileWriteLine(String filePath, String line) throws IOException {
        fileAppend(filePath, line + System.lineSeparator());
    }

    public static long getTickCount() {
        return System.nanoTime();
    }

}
