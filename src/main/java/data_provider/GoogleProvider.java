package data_provider;

import model.Answer;
import model.QuestionUI;
import operator.AnswerOperator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zemberek.tokenization.TurkishSentenceExtractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
public class GoogleProvider implements Provider {
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    private static final int RESULT_LINK_COUNT = 5;

    public Set<Answer> findCandidateList(QuestionUI questionUI) throws IOException {
        String searchableForm = convertSearchableForm(questionUI.getQuestion());
        Elements results = fetchGoogleSearchResult(searchableForm);
        List<String> sourceLinks = findSourceLinks(results);
        List<Answer> sentenceList = fetchSentenceList(sourceLinks);
        Set<Answer> candidateSet = new AnswerOperator().chooseCandidateSentences(sentenceList);
        return candidateSet;
    }

    protected String convertSearchableForm(String questionText) {
        return questionText.replaceAll("( )+", "+");
    }

    protected Elements fetchGoogleSearchResult(String searchableForm) throws IOException {
        String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchableForm + "&num=" + RESULT_LINK_COUNT;
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

        Elements results = doc.select("h3.r > a");
        return results;
    }

    protected List<String> findSourceLinks(Elements results) {
        List<String> sourceLinks = new ArrayList<String>();

        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", Url:" + linkHref.substring(7, linkHref.indexOf("&")));
            sourceLinks.add(linkHref.substring(7, linkHref.indexOf("&")));
        }

        return sourceLinks;
    }

    protected List<Answer> fetchSentenceList(List<String> sourceLink) throws IOException{
        List<Answer> sentenceList = new ArrayList<Answer>();

        for(String sourceName: sourceLink) {
            String content = fetchContent(sourceName);

            if(content.length() > 1) {
                List<String> sentenceText = getSentencesFromParagraph(content);

                for(String sentence: sentenceText) {
                    if(!sentence.contains(Character.toString((char) 160)))
                        sentenceList.add(new Answer(sentence, sourceName));
                }
            }
        }

        return sentenceList;
    }

    protected String fetchContent(String sourceName) {
        try {
            Document document = Jsoup.connect(sourceName).get();
            return document.body().text();
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return " ";
    }

    protected List<String> getSentencesFromParagraph(String paragraph){
        TurkishSentenceExtractor extractor = TurkishSentenceExtractor.DEFAULT;
        return extractor.fromDocument(paragraph);
    }
}
