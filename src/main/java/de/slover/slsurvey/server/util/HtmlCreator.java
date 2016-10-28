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

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class HtmlCreator {

    protected static String End
            = "    <div class=\"submit\">\n"
            + "      <button id=\"ssubmit\"> SUBMIT</button>\n"
            + "    </div>\n";

    protected static String sel
            = "     <select class=\"answer\">\n"
            + "         <option value=\"--\">--</option>\n"
            + "         <option value=\"-\">-</option>\n"
            + "         <option value=\"+\">+</option>\n"
            + "         <option value=\"++\">++</option>\n"
            + "     </select>\n";

    public static String createHtml(String html, Survey s) {
        StringBuilder htmlBuilder = new StringBuilder();
        for (Group g : s.getGroups()) {
            htmlBuilder.append("<div class = \"box\">");
            htmlBuilder.append("<h3>").append(g.getName()).append("</h3>\n");
            for (Question question : g.getQuestions()) {
                htmlBuilder.append("<p>").append(question.getQuestion()).append("</p>\n");
                htmlBuilder.append(sel);
            }
            htmlBuilder.append("</div>");
        }
        htmlBuilder.append(End);

        return html.replace("<!--<sloversurvey></sloversurvey>-->", htmlBuilder.toString());
    }

}
