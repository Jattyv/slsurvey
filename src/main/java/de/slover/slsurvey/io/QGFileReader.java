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
package de.slover.slsurvey.io;

import de.slover.slsurvey.data.Group;
import de.slover.slsurvey.data.Question;
import de.slover.slsurvey.data.Survey;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dimitrios Diamantidis &lt;Dimitri.dia@ledimi.com&gt;
 */
public class QGFileReader {

    public static Survey readSurvey(File file) {
        Survey survey = new Survey();
        String text = readFile(file);
        String[] splittenText = text.split("\n");
        Group group = new Group();
        Question question = new Question();
        for (String split : splittenText) {
            if (split.charAt(0) == '-') {
                if (split.charAt(1) == '-') {
                    if (split.charAt(2) == '-') {
                        question.getAnswerField().add(split.substring(3, split.length()));
                    } else {
                        question = new Question(split.substring(2, split.length()));
                        group.addQuestion(question);
                    }
                } else {
                    if (!group.getName().equals("")) {
                        survey.addGroup(group);
                    }
                    group = new Group();
                    group.setName(split.substring(1, split.length()));
                }

            }
        }
        survey.addGroup(group);
        return survey;
        }

    public static String readFile(String file) {
        try {
            return readFile(new File(file));
        } catch (Exception e) {
            return null;
        }
    }

    public static String readFile(File file) {
        StringBuilder sbuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                sbuilder.append(line).append("\n");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QGFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QGFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QGFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sbuilder.toString();
    }

    public static File chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("QuestionsFile", "qg"));
        int state = fc.showOpenDialog(null);
        if (state == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        } else {
            System.out.println("something went wrong while reading the file.");
            System.exit(1);
        }
        return null;
    }

}
