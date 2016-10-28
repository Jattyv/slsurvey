/*
 * Copyright (C) 2016 Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.slover.slsurvey.server.util;

import de.slover.slsurvey.data.Group;
import de.slover.slsurvey.data.Question;
import de.slover.slsurvey.data.Survey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class HtmlCreator {

    protected static String Submit
            = "    <div class=\"submit\">\n"
            + "      <button id=\"ssubmit\"> SUBMIT</button>\n"
            + "    </div>\n";

    public static String createHtml(String html, Survey s, String answers) {
        StringBuilder htmlBuilder = new StringBuilder();
        for (Group g : s.getGroups()) {
            htmlBuilder.append("<div class = \"box\">");
            htmlBuilder.append("<h3>").append(g.getName()).append("</h3>\n");
            for (Question question : g.getQuestions()) {
                htmlBuilder.append("<p>").append(question.getQuestion()).append("</p>\n");
                if (answers == null) {
                    htmlBuilder.append(createAnswerBlock(question));
                } else {
                    htmlBuilder.append(createAnswerBlock(answers));
                }
            }
            htmlBuilder.append("</div>\n");
        }
        htmlBuilder.append(Submit);

        return html.replace("<!--<sloversurvey></sloversurvey>-->", htmlBuilder.toString());
    }

    public static String createAnswerBlock(Question question) {
        StringBuilder blockBuilder = new StringBuilder();
        blockBuilder.append("<select class=\"answer\">\n");
        for (String answer : question.getAnswerField()) {
            blockBuilder.append("<option value=\"").append(answer)
                    .append("--").append("\">").append(answer)
                    .append("--</option>\n");
        }
        blockBuilder.append("</select>\n");

        return blockBuilder.toString();
    }

    public static String createAnswerBlock(String answers) {
        if (answers.charAt(0) == '-') {
            StringBuilder blockBuilder = new StringBuilder();
            blockBuilder.append("<select class=\"answer\">\n");
            String[] splittenString = answers.split("\n");
            for (String split : splittenString) {
                String answer = split.substring(3, split.length());
                blockBuilder.append("<option value=\"").append(answer)
                        .append("--").append("\">").append(answer)
                        .append("--</option>\n");
            }
            blockBuilder.append("</select>\n");
            return blockBuilder.toString();
        }

        Logger.getLogger(HtmlCreator.class.getName()).log(Level.WARNING, "The QuestionFile is invalid.");
        return null;
    }

}
