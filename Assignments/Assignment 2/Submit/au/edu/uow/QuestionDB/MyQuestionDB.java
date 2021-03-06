package au.edu.uow.QuestionDB;

import java.util.*;
import au.edu.uow.QuestionLibrary.*;

/**
 * Implements question interface
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class MyQuestionDB
{
    Database connection;

    public MyQuestionDB()
    {
        // Set up database connection
        connection = new Database();

        connection.setProperties();
        connection.start();
        connection.createTable();

    }

    /**
     * This method creates and populates a table in the database
     *   storing the questions from the question file. This method
     *   should use the addQuestion method to insert question content
     *   into the database
     * @param questionFile The file name of the question file
     * @return True if the database is successfully populated
     * @see #addQuestion(au.edu.uow.QuestionLibrary.Question)
     */
    public boolean buildQuestionDB(String questionFile)
    {

        List<Question> questions;
        XMLHandler xmlFile = new XMLHandler(questionFile);

        if(xmlFile.readFile()) {
            if (xmlFile.createLibrary()) {

                questions = xmlFile.getLibrary();

                for (Question question : questions) {
                    this.addQuestion(question);
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * This method returns the total number of questions in the database
     * @return The total number of questions in the question database
     * @see #buildQuestionDB
     */
    public int getTotalNumberOfQuestions()
    {
        return connection.totalRows();
    }

    /**
     * This method returns the question from the database at the given position
     * @return The question object
     */
    public List<Question> getQuestionListFromDB()
    {
        return connection.select();
    }

    /**
     * This method makes a quiz from the question database
     *   with the number of questions as specified. This method
     *   should use the getQuestion method to retrieval question
     *   content from the database
     * @param noOfQuestions - the number of questions in a quiz
     * @return Quiz questions in a list
     */
    public List<Question> makeQuiz(int noOfQuestions)
    {

        List<Question> fullQuestions;
        List<Question> finalList = new ArrayList<Question>();

        // Store into all question list
        fullQuestions = getQuestionListFromDB();

        // Shuffle list
        long seed = System.nanoTime();
        Collections.shuffle(fullQuestions, new Random(seed));


        // Setup Iterator
        Iterator<Question> iterator = fullQuestions.iterator();

        // Store from full list, place into small list
        for (int i = 0; i < noOfQuestions; i++) {
            if (iterator.hasNext()) {
                finalList.add(iterator.next());
            }
        }

        return finalList;

    }

    /**
     * This method adds a question to the database
     * @param question The question object to be added to the database
     * @return True if the operation is successful
     */
    public boolean addQuestion(Question question)
    {
        return connection.insert(question);
    }

    /**
     * This method removes the created table from the database
     * @return True if the operation is successful
     * @see #buildQuestionDB(String)
     */
    public boolean cleanUpDB()
    {
        boolean allGood = true;

        if (!connection.removeTable()) {
            allGood = false;
        }

        if (!connection.close()) {
            allGood = false;
        }

        return allGood;
    }
}
