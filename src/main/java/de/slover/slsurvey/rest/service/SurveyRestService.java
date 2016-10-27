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
package de.slover.slsurvey.rest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.slover.slsurvey.Slsurvey;
import de.slover.slsurvey.data.Survey;
import java.io.Reader;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
@Path("/survey")
public class SurveyRestService {

    @PUT
    @Path("/answered")
    public Response writeNewAnsweredSurvey(final Reader jsonReader) {
        Gson gson = new GsonBuilder().setVersion(1.0).create();
        Survey asurvey = gson.fromJson(jsonReader, Survey.class);
        Slsurvey.getSurveyService().addAnswer(asurvey);

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/surveyform")
    public Response readSurveyForm() {
        Gson gson = new GsonBuilder().setVersion(1.0).create();

        return Response.ok(gson.toJson(Slsurvey.getSurveyService().getSurvey())).build();
    }

    @GET
    @Path("/all")
    public Response readAllSurveyAnswers() {
        Gson gson = new GsonBuilder().setVersion(1.0).create();

        return Response.ok(Slsurvey.getSurveyService().getResults()).build();
    }

}
