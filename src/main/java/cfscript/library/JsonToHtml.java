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
                document.addEventListener('DOMContentLoaded', function () {
                    // This object will store the state of rows' visibility for each table
                    var tableStates = {};
                
                    // Add event listener for the whole document
                    document.addEventListener('click', function (e) {
                    if (e.target && e.target.nodeName === 'TD') {
                        var currentCell = e.target;
                        var currentRow = currentCell.parentNode;
                        var innerTable = currentCell.closest('table'); // Find the closest parent table
                
                        if (innerTable) {
                        // Unique identifier for the table, here we use the index among its siblings
                        var tableId = Array.from(innerTable.parentNode.children).indexOf(innerTable);
                
                        var allRows = innerTable.rows; // HTMLCollection of all the <tr> elements in the table
                
                        // If the table's state has not been stored yet or all rows are visible (state is false)
                        if (!tableStates.hasOwnProperty(tableId) || !tableStates[tableId]) {
                            // Set the state as true meaning some rows are hidden
                            tableStates[tableId] = true;
                
                            // Loop through all rows and hide those that are not the parent of the clicked cell
                            for (var i = 0; i < allRows.length; i++) {
                            if (allRows[i] !== currentRow) {
                                allRows[i].style.display = 'none';
                            }
                            }
                        } else {
                            // If the table's state is true (some rows are hidden), we show them all
                            tableStates[tableId] = false; // Set the state back to false
                
                            // Loop through all rows and make them visible
                            for (var i = 0; i < allRows.length; i++) {
                            allRows[i].style.display = '';
                            }
                        }
                        }
                    }
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
