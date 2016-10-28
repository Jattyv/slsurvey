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
package de.slover.slsurvey.service;

import de.slover.slsurvey.data.Survey;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class SurveyService {

    Survey form;
    List<Survey> result;

    public SurveyService() {
        result = new LinkedList<>();
    }

    public void addAnswer(Survey survey) {
        result.add(survey);
    }

    public void setSurvey(Survey survey) {
        form = survey;
    }

    public Survey getSurvey() {
        return form;
    }

    public String getResults(String type) {
        StringBuilder resBuilder = new StringBuilder();
        Map<String, Integer> answers = new TreeMap<>();

        for (int i = 0; i < form.getGroups().size(); i++) {
            resBuilder.append(form.getGroups().get(i).getName()).append("\n");
            for (int i2 = 0; i2 < form.getGroups().get(i).getQuestions().size(); i2++) {
                if (type.equals("text")) {
                    resBuilder.append(form.getGroups().get(i).getQuestions().get(i2).getQuestion()).append("\n");
                } else if (type.equals("csv")) {
                    resBuilder.append(",").append(form.getGroups().get(i).getQuestions().get(i2).getQuestion()).append("\n");
                }
                answers.clear();
                for (int i3 = 0; i3 < result.size(); i3++) {
                    try {
                        if (answers.containsKey(result.get(i3).getGroups().get(i).getQuestions().get(i2).getAnswer())) {
                            answers.replace(result.get(i3).getGroups().get(i).getQuestions().get(i2).getAnswer(), answers.get(result.get(i3).getGroups().get(i).getQuestions().get(i2).getAnswer()) + 1);
                        } else {
                            answers.put(result.get(i3).getGroups().get(i).getQuestions().get(i2).getAnswer(), 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (type.equals("text")) {
                for (String ans : answers.keySet()) {
                    resBuilder.append(ans).append(": ").append(answers.get(ans)).append("\n");
                    }
                } else if (type.equals("csv")) {
                    for (String ans : answers.keySet()) {
                        resBuilder.append(",,").append(ans).append(":,").append(answers.get(ans)).append("\n");
                    }
                }

            }
        }

        return resBuilder.toString();
    }

}
