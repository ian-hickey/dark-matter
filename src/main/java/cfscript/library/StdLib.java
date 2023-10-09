package cfscript.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.owasp.encoder.Encode;

import java.io.*;
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
    private static final ObjectMapper objectMapper = new ObjectMapper();
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

    public static long dateDiff(String interval, LocalDateTime date1, LocalDateTime date2) {
        return switch (interval.toLowerCase()) {
            case "yyyy" -> // Years
                    ChronoUnit.YEARS.between(date1, date2);
            case "m" -> // Months
                    ChronoUnit.MONTHS.between(date1, date2);
            case "d" -> // Days
                    ChronoUnit.DAYS.between(date1, date2);
            case "h" -> // Hours
                    ChronoUnit.HOURS.between(date1, date2);
            case "n" -> // Minutes
                    ChronoUnit.MINUTES.between(date1, date2);
            case "s" -> // Seconds
                    ChronoUnit.SECONDS.between(date1, date2);
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };
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

    public static int dateCompare(LocalDateTime date1, LocalDateTime date2, String part) {
        switch (part.toLowerCase()) {
            case "s": // Second
                date1 = date1.withNano(0);
                date2 = date2.withNano(0);
                break;
            case "n": // Minute
                date1 = date1.withSecond(0).withNano(0);
                date2 = date2.withSecond(0).withNano(0);
                break;
            case "h": // Hour
                date1 = date1.withMinute(0).withSecond(0).withNano(0);
                date2 = date2.withMinute(0).withSecond(0).withNano(0);
                break;
            case "d": // Day
                date1 = date1.toLocalDate().atStartOfDay();
                date2 = date2.toLocalDate().atStartOfDay();
                break;
            case "m": // Month
                date1 = date1.withDayOfMonth(1).toLocalDate().atStartOfDay();
                date2 = date2.withDayOfMonth(1).toLocalDate().atStartOfDay();
                break;
            case "yyyy": // Year
                date1 = date1.withDayOfYear(1).toLocalDate().atStartOfDay();
                date2 = date2.withDayOfYear(1).toLocalDate().atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("Invalid date part: " + part);
        }

        return date1.compareTo(date2);
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
        return System.currentTimeMillis();
    }

    public static String serializeJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    public static Object deserialize(String json) throws Exception {
        // Check if the JSON string represents an array
        if (json.trim().startsWith("[")) {
            return objectMapper.readValue(json, ArrayList.class);
        }
        // Otherwise, assume it's an object
        return objectMapper.readValue(json, HashMap.class);
    }

    public static String encodeForCSS(String input) {
        return Encode.forCssString(input);
    }

    public static String encodeForDN(String input) {
        // OWASP Java Encoder does not provide DN encoding out-of-the-box.
        // This is a basic example, and for more complex DN structures, we may need a specialized approach.
        return input.replace(",", "\\,")
                .replace("=", "\\=")
                .replace("+", "\\+")
                .replace("<", "\\<")
                .replace(">", "\\>")
                .replace("#", "\\#")
                .replace(";", "\\;");
    }

    public static String encodeForHTML(String input) {
        return Encode.forHtml(input);
    }

    public static String encodeForHTMLAttribute(String input) {
        return Encode.forHtmlAttribute(input);
    }

    public static String encodeForJavaScript(String input) {
        return Encode.forJavaScript(input);
    }

    public static String encodeForLDAP(String input) {
        // OWASP Java Encoder does not provide LDAP encoding out-of-the-box.
        // This is a basic example, and for more complex LDAP structures, we may need a specialized approach.
        return input.replace("\\", "\\5c").replace("*", "\\2a")
                .replace("(", "\\28").replace(")", "\\29")
                .replace("\u0000", "\\00");
    }

    public static String encodeForURL(String input) throws Exception {
        return Encode.forUriComponent(input);
    }

    public static String encodeForXML(String input) {
        return Encode.forXml(input);
    }

    public static String encodeForXMLAttribute(String input) {
        return Encode.forXmlAttribute(input);
    }

    public static String encodeForXPath(String input) {
        // OWASP Java Encoder does not provide XPath encoding out-of-the-box.
        // This is a basic example, and for more complex XPath queries, we may need a specialized approach.
        return input.replace("'", "&apos;").replace("\"", "&quot;");
    }

}
