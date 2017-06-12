package operator;

import common.FilePath;
import model.Answer;

import java.io.*;
import java.util.*;

/**
 * Created by mustafa on 10.06.2017.
 */
public class FileOperator {

    public void saveListForDeepLearning(Set<Answer> answerList) {
        File file = new File(FilePath.WRITTEN_FILE_NAME);

        try {
            FileWriter fileWriter = new FileWriter(file);

            for(Answer answer: answerList) {
                fileWriter.write(answer.getDeepLearingForm() + "\n");
            }

            fileWriter.close();
            System.out.println("Dosya başarıyla oluşturuldu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Answer> parseOutput(Map<String, Answer> answerMap) {
        List<Answer> answerList = new ArrayList<Answer>();

        try {
            File file = new File(FilePath.OUTPUT_FILE_NAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String answer = bufferedReader.readLine();
            double similarity = Double.parseDouble(bufferedReader.readLine());

            boolean finished = false;
            while (!finished) {
                System.out.println(answer);
                Answer answer1 = answerMap.get(answer);
                answer1.setSimilarityValue(similarity);
                answerList.add(answerMap.get(answer));

                answer = bufferedReader.readLine();
                if(answer != null) {
                    similarity = Double.parseDouble(bufferedReader.readLine());
                } else {
                    finished = true;
                }
            }

            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return answerList;
    }

    public Set<String> readStopWords() {
        Set<String> stopWordSet = new HashSet<String>();

        try {
            File file = new File(FilePath.STOP_WORD);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String stopWord = bufferedReader.readLine();

            while(stopWord != null) {
                stopWordSet.add(stopWord);
                stopWord = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return stopWordSet;
    }

    public void writeUserQuestionToFile(String question) {
        File file = new File(FilePath.QUESTION_FILE_NAME);

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(question);
            fileWriter.close();
            System.out.println("Dosya başarıyla oluşturuldu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
