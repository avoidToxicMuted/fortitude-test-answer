package asia.fourtitude.interviewq.jumble.controller;

import java.time.ZonedDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.model.ExistsForm;
import asia.fourtitude.interviewq.jumble.model.PrefixForm;
import asia.fourtitude.interviewq.jumble.model.ScrambleForm;
import asia.fourtitude.interviewq.jumble.model.SearchForm;
import asia.fourtitude.interviewq.jumble.model.SubWordsForm;

@Controller
@RequestMapping(path = "/")
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    private final JumbleEngine jumbleEngine;

    @Autowired(required = true)
    public RootController(JumbleEngine jumbleEngine) {
        this.jumbleEngine = jumbleEngine;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("timeNow", ZonedDateTime.now());
        return "index";
    }

    @GetMapping("scramble")
    public String doGetScramble(Model model) {model.addAttribute("form", new ScrambleForm());
        return "scramble";
    }

    @PostMapping("scramble")
    public String doPostScramble(@ModelAttribute(name = "form") ScrambleForm form, BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#scramble()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */

        String scrambleResult = "";

        if (form.getWord().isEmpty()) {
            scrambleResult = "Word must not be blank";
        } else if (form.getWord().length() < 3 || form.getWord().length() > 30) {
            scrambleResult = "Word size must be between 3 and 30";
        } else {
            scrambleResult = jumbleEngine.scramble(form.getWord());
        }
        form.setScramble(scrambleResult);

        return "scramble";
    }

    @GetMapping("palindrome")
    public String doGetPalindrome(Model model) {model.addAttribute("words", this.jumbleEngine.retrievePalindromeWords());
        return "palindrome";
    }

    @GetMapping("exists")
    public String doGetExists(Model model) {
        model.addAttribute("form", new ExistsForm());
        return "exists";
    }

    @PostMapping("exists")
    public String doPostExists(@ModelAttribute(name = "form") ExistsForm form, BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#exists()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
        boolean exists = false;

        if (form.getWord().isEmpty()) {
            form.setWord("must not be blank");
        } else {
                exists = jumbleEngine.exists(form.getWord());
        }
        form.setExists(exists);
        return "exists";
    }

    @GetMapping("prefix")
    public String doGetPrefix(Model model) {
        model.addAttribute("form", new PrefixForm());
        return "prefix";
    }

    @PostMapping("prefix")
    public String doPostPrefix(@ModelAttribute(name = "form") PrefixForm form, BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#wordsMatchingPrefix()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */

        Collection<String> matchedWord = new ArrayList<>();

        if (form.getPrefix().isEmpty()) {
            form.setPrefix("must not be blank");
        } else {
            matchedWord = jumbleEngine.wordsMatchingPrefix(form.getPrefix());
        }
        form.setWords(matchedWord);

        return "prefix";
    }

    @GetMapping("search")
    public String doGetSearch(Model model) {
        model.addAttribute("form", new SearchForm());
        return "search";
    }

    @PostMapping("search")
    public String doPostSearch(@ModelAttribute(name = "form") SearchForm form, BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) Show the fields error accordingly: "Invalid startChar", "Invalid endChar", "Invalid length".
         * c) To call JumbleEngine#searchWords()
         * d) Presentation page to show the result
         * e) Must pass the corresponding unit tests
         */

        char start = '\0';
        char end = '\0';

        Collection<String> searchResult = new ArrayList<>();
        if(form.getStartChar() != null && !form.getStartChar().isEmpty()) {
            start = form.getStartChar().charAt(0);
        }
        if(form.getEndChar() != null && !form.getEndChar().isEmpty()) {
            end = form.getEndChar().charAt(0);
        }

        searchResult = jumbleEngine.searchWords(start, end, form.getLength());

        form.setWords(searchResult);

        return "search";
    }


    @GetMapping("subWords")
    public String goGetSubWords(Model model) {
        model.addAttribute("form", new SubWordsForm());
        return "subWords";
    }

    @PostMapping("subWords")
    public String doPostSubWords(
            @ModelAttribute(name = "form") SubWordsForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#generateSubWords()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */

        return "subWords";
    }

}
