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
package de.slover.slsurvey;

import de.slover.slsurvey.data.Survey;
import de.slover.slsurvey.io.QGFileReader;
import de.slover.slsurvey.rest.service.SurveyRestService;
import de.slover.slsurvey.server.handler.HTMLHandler;
import de.slover.slsurvey.service.SurveyService;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class Slsurvey {

    private static SurveyService sservice;


    public static void main(String[] args) {
        Slsurvey sl = new Slsurvey();
        if (args.length == 0) {
            sl.init();
        } else if (args[0].equals("-help")) {
            System.out.println("java -jar SlSurvey.jar [options]\n"
                    + "\n"
                    + "options:"
                    + "(file)   Loads this Questionfile instead of starting the JFileChooser.");
        } else {
            sl.init(new File(args[0]));
        }
    }

    public void init() {
        init(QGFileReader.chooseFile());
    }

    public void init(File file) {
        Survey survey = QGFileReader.readSurvey(file);
        sservice = new SurveyService();
        sservice.setSurvey(survey);
        startWebServer(survey);
    }

    public void startWebServer(Survey survey) {
        ResourceConfig config = new ResourceConfig();
        config.packages("jettyjerseytutorial");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8080);
        try {

            ContextHandler contextSurvey = new ContextHandler("/survey");
            contextSurvey.setHandler(new HTMLHandler(survey));

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("web");

            ContextHandler contextSurveyCSS = new ContextHandler("/resources");
            contextSurveyCSS.setHandler(resourceHandler);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            ContextHandlerCollection contexts = new ContextHandlerCollection();
            contexts.setHandlers(new Handler[]{contextSurvey, contextSurveyCSS, context});

            ServletHolder jerseyServlet = context.addServlet(
                    org.glassfish.jersey.servlet.ServletContainer.class, "/slsurvey/*");
            jerseyServlet.setInitOrder(0);
            jerseyServlet.setInitParameter(
                    "jersey.config.server.provider.classnames",
                    SurveyRestService.class.getCanonicalName());

            server.setHandler(contexts);
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Slsurvey.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            server.destroy();
        }
    }

    public static SurveyService getSurveyService() {
        return sservice;
    }

}
