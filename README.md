# SlSurvey

A lightweight and simple application that generates a website with a survey from a questionfile and runs the website.


###QuestionFile:

A questionfile contains a group of to elements. Groups and Questions. 
A questionfile must start with a group that will contain more questions. 
A group is declared with  one „-“. A question is declared with two „-“.

Example of a questionfile:

questionfile.qg

```
-This is group one
–This is question number one?
–This is question number two?
--This is question number three?
-This is group two
–This is question number one of group two?
–This is question number two of group two?
--This is question number three of group two?
```
