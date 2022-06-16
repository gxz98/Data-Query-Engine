# Data Query Engine

This is a Java application for querying data files in the command line. For now, it only supports
CSV datasets. Similar to SQL, this engine can execute query operations as follows:

* `FROM` : loads data file from disk and returns to standard output.
    - Every query command should start with `FROM`, to choose working with which data file.
    - e.g. `FROM city.csv`
  
* `SELECT`: pick specified columns by their column names from the dataset.
    - The parameter can be multiple column names or a single column.
    - e.g. `FROM city.csv SELECT CityName` ; `FROM city.csv SELECT CityName,CountryCode`

* `TAKE`: limit the maximum amount of data entries to display
    - The parameter must be positive, if it is larger than the number of entries in the dataset, all entries will be displayed.
    - e.g. `FROM city.csv TAKE 5`

* `ORDERBY`: sort the dataset by the one particular column 
    - If the parameter specifies a numeric column, then the dataset is sorted in descending order. Otherwise, the sorting is in lexicographical order.
    - e.g. `FROM city.csv ORDERBY CountryCode TAKE 10`
  
* `COUNTBY`: count the entries by a specified column. Reduce the table into two columns: specified column and the count results.
    - The parameter should be a valid column name.
    - e.g. `FROM language.csv COUNTBY Language ORDERBY count TAKE 7`
  
* `JOIN`: merge two datasets by the shared column into a larger table. Multiple JOIN calls are also allowed. 
          The join method used in this engine is [sort-merge join](https://en.wikipedia.org/wiki/Sort-merge_join).
    - The parameters should include the data file to be joined with and the column name.
    - e.g. `FROM city.csv JOIN country.csv CountryCode JOIN language.csv CountryCode`

* `EXIT`: terminate the query engine


## Project Structure

Data Query Engine has 3 main modules.

* `reader`: read datasets from the disk
* `executor`: execute valid commands and output the results
* `data`: core data structures, including
    - `Entry`: stores a row of data
    - `Table`: contains a list of entries and the column names

## User Guide
1. To run Data Query Engine you need to [set up Java environment](https://www.java.com/en/download/help/index_installing.html). 
We recommend to use Java 8 or above version. 

2. Run `Main.java`, if you see`"Welcome to Data Query Engine!"` in the console, you are ready to play with the engine!
Note that if you enter incorrect command, the system will terminate with the exception message.

3. This engine will only fetch data files in `resources` folder, you can add your own datasets in it.

4. For now, the engine only supports querying for CSV files, if you want to add more data types, feel free to write your own `reader` class.