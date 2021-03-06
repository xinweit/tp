package seedu.address.model;

import static seedu.address.model.util.LocalDatabasePopulator.getDatabaseOfFlashcards;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.collections.ObservableList;
import seedu.address.model.person.Flashcard;
import seedu.address.model.person.UniqueFlashcardList;

/**
 * Class Quiz represents a quiz session.
 */
public class Quiz {

    public static final String QUIZ_END_MESSAGE = "The Quiz is over! \n"
            + "Enter \"end\" to end the quiz.";

    private static Queue<Flashcard> quizSessionQueue;

    /**
     * Initializes the quiz session with a queue of all flashcards with randomized order.
     */
    public Quiz() {
        Flashcard[] flashcardsReadFromDB = getDatabaseOfFlashcards();
        quizSessionQueue = getRandomizedQueue(flashcardsReadFromDB);
    }

    /**
     * Checks whether the quiz session is supposed to have ended. A session has to end if there is
     * no flashcard to display as quiz question.
     *
     * @return True if there is no flashcard to display.
     */
    public static boolean hasSessionEnded() {
        return quizSessionQueue.isEmpty();
    }

    /**
     * Gets the next flashcard question to show to the user.
     *
     * @return The next flashcard in the queue, if the queue is not empty.
     * Returns null if the queue is empty and the session should be ended.
     */
    public Flashcard getNextQuestion() {
        if (hasSessionEnded()) {
            return null;
        } else {
            return quizSessionQueue.poll();
        }
    }

    /**
     * Gets the next flashcard question to show to the user in the type accepted by MainWindow.
     *
     * @return The next flashcard in the queue as an UnmodifiableObservableList, if the queue is not empty.
     */
    public ObservableList<Flashcard> getNextFlashcard() {
        UniqueFlashcardList temp = new UniqueFlashcardList();
        temp.setFlashcards(List.of(this.getNextQuestion()));
        return temp.asUnmodifiableObservableList();
    }

    /**
     * Generates randomized queue from the given array of flashcards.
     *
     * @param flashcardsReadFromDB An array of flashcards, previously read from database.
     * @return A queue of flashcards with randomized order.
     */
    private Queue<Flashcard> getRandomizedQueue(Flashcard[] flashcardsReadFromDB) {
        List<Flashcard> flashcardsToShuffle = Arrays.asList(flashcardsReadFromDB);
        Collections.shuffle(flashcardsToShuffle);
        Queue<Flashcard> randomizedQueue = new LinkedList<>();
        for (Flashcard f : flashcardsToShuffle) {
            randomizedQueue.offer(f);
        }
        return randomizedQueue;
    }
}
