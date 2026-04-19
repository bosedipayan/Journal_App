package com.example.journal.scheduler;

import com.example.journal.cache.AppCache;
import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.repository.UserRepositoryImpl;
import com.example.journal.service.EmailService;
import com.example.journal.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN") // Runs every Sunday at 9 AM
    public  void fetchUsersAndSendMail(){
        List<User> usersForSentimentAnalysis = userRepository.getUsersForSentimentAnalysis();
        for (User user : usersForSentimentAnalysis) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(entry -> entry.getDate().isAfter(LocalDateTime.now().minus(7,
                            java.time.temporal.ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());

            String entriesText = String.join("\n", filteredEntries);
            String sentiment = sentimentAnalysisService.analyzeSentiment(entriesText);
            emailService.sendEmail(user.getEmail(), "Your Weekly Sentiment Analysis", "Your sentiment score for the " +
                    "past week is: " + sentiment);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void clearAppCache(){
        appCache.init();
    }
}
