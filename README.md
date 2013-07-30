# Hyperion Parent Inferrer

A simple Java runnable JAR/library for taking a text file that has a hierarchy of strings and outputting that hierarchy with explicit parent/child references.

For example, given the following input text file:

    Time
     Q1
      January
      February
      March
     Q2
      April
      May
      June
     Q3
      July
      August
      September
     Q4
      October
      November
      December

The following output is generated:

    null,Time
    Time,Q1
    Q1,January
    Q1,February
    Q1,March
    Time,Q2
    Q2,April
    Q2,May
    Q2,June
    Time,Q3
    Q3,July
    Q3,August
    Q3,September
    Time,Q4
    Q4,October
    Q4,November
    Q4,December

The output is suitable for using as the basis of a Parent/Child build that is typical in Hyperion Essbase dimension building, loading data to a relational table for use with EIS or Studio, using with ODI, and so on.

## Command-line switches

A few different command-line switches can be used to affect the parsing process.

1. (TBD)

## Usage

The command-line version of this library can be invoked like the following:

    java -jar parent-inferrer.jar input-file.txt output-file.txt

If embedding into a Java solution, you can call the code simply as follows:

    ParentInferrer pi = new ParentInferrer();
    pi.process("input-file.txt");
		
There are also convenience variants of `process()` that take an `InputStream` or a `Reader` in addition to a filename.

# License

Hyperion Parent Inferrer is licensed under the Apache Software License version 2. You can pretty much do what you want with it. Please email me if you have questions, comments, improvements, or anything else.
