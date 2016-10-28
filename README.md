# SlSurvey

A lightweight and simple application that generates a website with a survey from a questionfile and runs the website.


###Configuration
In the file slsurvey.properties in the config folder you can configure the following options:

server-port                     ---sets the port of the webserver
default-answer-file             ---sets the answerfile
web-directory                   ---sets the directory with the resources
html-file                       ---sets the html file that should be used for the survey(must be in the web-directory)


###HTML

For using your own HTML file slsurvey needs to know where the survey must put in. 
Therefor you need to add the following line to the position where the survey should be.

```
        <!--<sloversurvey></sloversurvey>-->
```


###QuestionFile:

A questionfile contains a group of to elements. Groups and Questions. 
A questionfile must start with a group that will contain more questions. 
A group is declared with  one „-“. A question is declared with two „-“.
An answer is declared with three „-“.

Example of a questionfile:

questionfile.qg

```
-This is group one
--This is question number one?
---option one
---option two
--This is question number two?
---option one
---option two
---option three
--This is question number three?
---option one
---option two
-This is group two
--This is question number one of group two?
---option one
---option two
--This is question number two of group two?
---option one
---option two
--This is question number three of group two?
---option one
---option two
```


###AnswerFile:

If you wanna use the same answers for all questions you can use an answer file. 
Set the location of the answerfile in the configuration and than the answers in 
the questionfile will be ignored.

Example of an answerfile:

answerfile.qg

```
---option one
---option two
---option three
```
