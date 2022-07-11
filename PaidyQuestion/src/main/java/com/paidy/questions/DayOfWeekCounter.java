package com.paidy.questions;

import com.paidy.questions.exception.QuestionException;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Count numbers of weeks for day of week between two date.
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
public final class DayOfWeekCounter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;

    public DayOfWeekCounter(){}

    public DayOfWeekCounter(int dayOfWeek){
        if(dayOfWeek > 8 || dayOfWeek < 1){
            throw new QuestionException(String.format("Input day of week error. Input number should between 1 and 7. input: %s", dayOfWeek));
        } else {
            this.dayOfWeek = DayOfWeek.of(dayOfWeek);
        }
    }

    /**
     * Count the number of day of weeks between given fromDate and toDate.
     * The date format is "dd-MM-yyyy".
     *
     * Example:
     * fromDate: 01-05-2021
     * toDate: 30-05-2021
     * result: 5
     *
     * @param fromDate given from date
     * @param toDate given to date
     * @return number
     * @throws QuestionException if the fromDate is after the toDate.
     * @throws DateTimeParseException if given date format is wrong.
     * @throws NullPointerException if given fromDate or toDate is null.
     */
    public int count(@NonNull String fromDate, @NonNull String toDate){

        var fromLocalDate = LocalDate.parse(fromDate, FORMATTER);
        var toLocalDate = LocalDate.parse(toDate, FORMATTER);

        if(fromLocalDate.isAfter(toLocalDate)){
            throw new QuestionException(String.format("From date should before to date. fromDate: %s, toDate: %s", fromDate, toDate));
        }

        if(fromLocalDate.getDayOfWeek() != dayOfWeek){
            fromLocalDate = fromLocalDate.with(TemporalAdjusters.next(dayOfWeek));
        }
        if(toLocalDate.getDayOfWeek() != dayOfWeek){
            toLocalDate = toLocalDate.with(TemporalAdjusters.next(dayOfWeek));
        }

        return (int) ChronoUnit.WEEKS.between(fromLocalDate, toLocalDate);
    }
}
