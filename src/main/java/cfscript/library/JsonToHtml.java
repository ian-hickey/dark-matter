package cfscript.library;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonToHtml {

    // Convert a JSONObject into HTML table rows
    private static String jsonToTableRows(JSONObject json) {
        StringBuilder rows = new StringBuilder();

        for (String key : json.keySet()) {
            rows.append("<tr>");

            // Key cell
            if (!key.equals("type") && !key.equals("value")) {
                //rows.append("<td>").append(key).append("</td>");
            }

            // Value cell
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                rows.append("<td>").append(jsonToTable((JSONObject) value)).append("</td>");
            } else if (value instanceof JSONArray) {
                rows.append("<td>").append(jsonArrayToTable((JSONArray) value)).append("</td>");
            } else {
                if (key.equals("type") || key.equals("component")) {
                    rows.append("<td  class='type-header'>").append(value.toString()).append("</td>");
                }else{
                    rows.append("<td>").append(value.toString()).append("</td>");
                }
            }

            rows.append("</tr>");
        }

        return rows.toString();
    }

    // Convert a JSONArray into HTML table rows
    private static String jsonArrayToTableRows(JSONArray array) {
        StringBuilder rows = new StringBuilder();

        for (int i = 0; i < array.length(); i++) {
            rows.append("<tr>");

            // Key cell (as index)
            rows.append("<td>").append(i).append("</td>");

            // Value cell
            Object value = array.get(i);
            if (value instanceof JSONObject) {
                rows.append("<td>").append(jsonToTable((JSONObject) value)).append("</td>");
            } else if (value instanceof JSONArray) {
                rows.append("<td>").append(jsonArrayToTable((JSONArray) value)).append("</td>");
            } else {

                rows.append("<td>").append(value.toString()).append("</td>");
            }

            rows.append("</tr>");
        }

        return rows.toString();
    }

    // Convert a JSONObject to an HTML table
    public static String jsonToTable(JSONObject json) {
        StringBuilder table = new StringBuilder();
        table.append("<table>");

        // Add the rows from the JSON object
        table.append(jsonToTableRows(json));

        table.append("</table>");

        // Add CSS
        table.append("""
            <style>
                /* Style the table */
                table {
                    font-family: Arial, sans-serif;
                    border-collapse: collapse;
                    width: 100%;
                    border: 1px solid #ddd; /* Light gray border */
                    margin-bottom: 10px; /* For spacing between nested tables */
                    background: lightgrey;

                }

                /* Style the table cells */
                td, th {
                    border: none;
                    text-align: left;
                    padding: 8px;
                }

                /* Style the table header */
                th {
                    background-color: #4CAF50; /* Green background */
                    color: white;
                }

                /* Style for nested tables */
                td table {
                    background-color: #E0F2E9; /* background for nested tables */
                    margin: 0; /* Reset margin */
                    
                    overflow: hidden; 
                    text-overflow: ellipsis;
                }

                td table td, td table th {
                    padding: 4px; /* Less padding for nested table cells */
                    text-align: left;
                }

                td table .type-header  {
                    background-color: #66c86a; /* Green background for nested table headers */
                    color: white;
                }

                .type-header {
                    background-color: #4CAF50; /* Vibrant green */
                    color: white; /* Choose a text color that ensures readability */
                }
                .hidden {
                    display: none;
                }
                
                .nested-content {
                    margin: 10px 0;
                    padding: 10px;
                    border: 1px solid #ddd;
                }
            </style>    
            <script>
                // Function to toggle the visibility of the nested content
                function toggleVisibility(element) {
                if (element.classList.contains('hidden')) {
                    element.classList.remove('hidden');
                } else {
                    element.classList.add('hidden');
                }
                }

                // Add the click event listeners
                document.addEventListener('DOMContentLoaded', function() {
                // Get all the 'td' elements in the table
                var cells = document.querySelectorAll('td');

                // Add a click event listener to each cell
                cells.forEach(function(cell) {
                    cell.addEventListener('click', function() {
                    // Find the nested content within the clicked cell
                    var nestedContent = cell.querySelector('.nested-content');
                    if (nestedContent) {
                        // If nested content is found, toggle its visibility
                        toggleVisibility(nestedContent);
                    }
                    });
                });
                });
            </script>
        """);
        return table.toString();
    }

    // Convert a JSONArray to an HTML table
    public static String jsonArrayToTable(JSONArray array) {
        StringBuilder table = new StringBuilder();
        table.append("<table border=1>");

        // Add the rows from the JSON array
        table.append(jsonArrayToTableRows(array));

        table.append("</table>");

        return table.toString();
    }

}
