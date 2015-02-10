Java API for Workbook Binding (JAWB)
====================================

Provides a JAXB-like API for binding the contents of an Excel workbook to
a graph of Java beans.

### `@Bound`

### `@Cell`

### `@Sheet`

Cell Data
---------

Non-empty cells in an Excel workbook can contain one value whose type is either
numeric, date, string, or boolean.  A blank cell contains no value and thus has
no data type.


Iterating over Rows, Columns, and Sheets
----------------------------------------

An Excel workbook is essentially a three-dimensional data structure, 
consisting of cells laid out in rows and columns on sheets.  JAWB provides the
ability to iterate over any of these dimensions, and in any combination to
produce object graphs of arbitrary complexity.


Skip and Stop Expressions
-------------------------

JAWB provides a powerful expression language to control iteration, allowing you
to evaluate workbook cells to determine which rows, columns, or sheets to skip
and the conditions that should terminate iterations.

### Cell Reference

Cell references in the expression language are the same as they are in
Excel, with the same semantics: 
* `Q3` -- column Q row 3
* `$Q3` -- absolute reference to column Q (not affected by iteration)
* `Q$3` -- absolute reference to row 3 (not affected by iteration)
* `$Q$3` -- absolute reference to cell Q3 (not affected by iteration)
* `Sheet4!Q3` -- cell Q3 on _Sheet4_

### Literals

* string -- any text delimited by single quotes
* number -- digits with optional decimal point and exponent; e.g. `1`, `3.14`,
  `2.7818e-35`
* date -- in year-month-day format; e.g. `1920-03-18`
* time -- in twenty-four hour:minute:second format; e.g. `21:15:30`
* boolean -- `true` or `false`
* blank -- `blank` (also allows `empty` or `null`)

### Operators

* arithmetic -- `+`, `-`, `*`, `/`
* continuation -- `,`
* equality -- `==`, `!=`
* logical -- `&&` (and), `||` (or), `^` (exclusive or)
* relational -- `<`, `<=`, `>`, `>=`
* type -- `is`, `is not`; e.g. `is blank`, `is not number`
* unary -- `-` (numeric negation), `!` (logical negation)


### Functions

* `IF(cond1, expr1, cond1, expr2, ... , condN, exprN [, exprN+1])` -- 
  for K in [1..N], `condK` is evaluated, and if the result is true, then 
  `exprK` is evaluated and returned as the function result.  If none of
  `condK` evaluates to a true value, and an `exprN+1` is provided, it is 
  evaluated and returned as the result, otherwise a blank value is returned.
  


