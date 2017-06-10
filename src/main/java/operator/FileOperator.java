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
        File file = new File(FilePath.WRITED_FILE_NAME);

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

            while (answer != null) {
                Answer answer1 = answerMap.get(answer);
                answer1.setSimilarityValue(similarity);
                answerList.add(answerMap.get(answer));

                answer = bufferedReader.readLine();
                similarity = Double.parseDouble(bufferedReader.readLine());
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
}