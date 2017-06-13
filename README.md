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

A few different command-line switches can be used to affect the parsing process:


	-i, --indent-character
		Single character that indents, specify 'tab' to use tab. Default is
		single space (optionsal)
  
 	-f, --input-file
		The file to process (required)
       
	--no-parent-text
		The text to render if there is no parent of the member. Defaults to "null".
		Optional
       
    -o, --output-file
    	File to output to. Optional. If none specified will go to stdout


## Usage

The command-line version of this library can be invoked like the following:

    java -jar parent-inferrer.jar -f input-file.txt --indent-character=tab

If embedding into a Java solution, you can call the code simply as follows:

	BasicOptions options = new BasicOptions();
    ParentInferrer pi = new ParentInferrer(options);
    pi.process("input-file.txt");
		
There are also convenience variants of `process()` that take an `InputStream` or a `Reader` in addition to a filename.

You may also wish to get a simple `Map` structure that gives you the ability to look up the children of a given entry. 
This is possible with the `processString(String text)` method:

	String hier = "Time\n Qtr1\n Qtr2\n  Jan\n  Feb\n  Mar\n";
	BasicOptions options = new BasicOptions();
	options.setCreateEntriesForChildless(true);
	ParentInferrer pi = new ParentInferrer(options);
		
	Map<String, List<String>> children = pi.processString(hier);

You can then quickly get the children of the `Qtr1` member:

	List<String> qtr1Months = children.get("Qtr1");


## History

1.0.2

 * New `processString()`  method to process text hierarchy into a simple `Map<String, List<String>>` of parents to children
 * New option for previous method so that childless entries can be counted or not

1.0.1
 
 * Updated after four years! Added command line options
 * Option for custom indent
 * Option for custom text when no parent
 
1.0.0

 * Initial release


## License

Hyperion Parent Inferrer is licensed under the Apache Software License version 2. You can pretty much do what you want with it. Please email me if you have questions, comments, improvements, or anything else.
