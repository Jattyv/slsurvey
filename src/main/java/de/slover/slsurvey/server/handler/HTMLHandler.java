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
package de.slover.slsurvey.server.handler;

import de.slover.slsurvey.data.Survey;
import de.slover.slsurvey.io.PropertiesReader;
import de.slover.slsurvey.io.QGFileReader;
import de.slover.slsurvey.server.util.HtmlCreator;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class HTMLHandler extends AbstractHandler {

    String html = "";

    public HTMLHandler(Survey survey, Properties conf) {
        html = QGFileReader.readFile(new File(conf.get(PropertiesReader.WEB_DIR) + File.separator + conf.get(PropertiesReader.HTML_FILE)));
        html = HtmlCreator.createHtml(html, survey);
    }

    @Override
    public void handle(String string, Request request, HttpServletRequest hsr, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(html);
        request.setHandled(true);
    }

}
