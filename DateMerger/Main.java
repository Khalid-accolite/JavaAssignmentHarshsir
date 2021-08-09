package com.company;

import java.time.LocalDate;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        List<DateRange> dateRanges = new ArrayList<>();
        dateRanges.add(new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 30)));
        dateRanges.add(new DateRange(LocalDate.of(2019, 1, 15), LocalDate.of(2019, 2, 15)));
        dateRanges.add(new DateRange(LocalDate.of(2019, 3, 10), LocalDate.of(2019, 4, 15)));
        dateRanges.add(new DateRange(LocalDate.of(2019, 4, 10), LocalDate.of(2019, 5, 15)));

        System.out.println("## INPUT--->");
        dateRanges.stream().forEach(dateRange -> System.out.println(dateRange.getStartDate() + " - " + dateRange.getEndDate()));

        List<DateRange> mergedDateRange = mergeDateRange(dateRanges);

        System.out.println("## OUTPUT-->");
        mergedDateRange.stream().forEach(dateRange -> System.out.println(dateRange.getStartDate() + " - " + dateRange.getEndDate()));
    }


    private static List<DateRange> mergeDateRange(List<DateRange> dateRanges) {
        Set<DateRange> mergedDateRangeSet = new HashSet<>();
        Collections.sort(dateRanges, DateRange.START_DATE_COMPARATOR);

        mergedDateRangeSet.add(dateRanges.get(0));
        for (int index = 1; index < dateRanges.size(); index++) {
            DateRange current = dateRanges.get(index);
            List<DateRange> toBeAdded = new ArrayList<>();
            Boolean rangeMerged = false;
            for (DateRange mergedRange : mergedDateRangeSet) {
                DateRange merged = checkOverlap(mergedRange, current);
                if (merged == null) {
                    toBeAdded.add(current);
                }
                else {
                    mergedRange.setEndDate(merged.getEndDate());
                    mergedRange.setStartDate(merged.getStartDate());
                    rangeMerged = true;
                    break;
                }
            }
            if (!rangeMerged) {
                mergedDateRangeSet.addAll(toBeAdded);
            }
            toBeAdded.clear();
        }
        List<DateRange> mergedDateRangeList = new ArrayList<>(mergedDateRangeSet);
        Collections.sort(mergedDateRangeList, DateRange.START_DATE_COMPARATOR);
        return mergedDateRangeList;
    }


    private static DateRange checkOverlap(DateRange mergedRange, DateRange current) {
        if (mergedRange.getStartDate().isAfter(current.getEndDate()) || mergedRange.getEndDate().isBefore(current.getStartDate())) {
            return null;
        }
        else {
            return new DateRange(mergedRange.getStartDate().isBefore(current.getStartDate()) ? mergedRange.getStartDate() : current.getStartDate(),
                    mergedRange.getEndDate().isAfter(current.getEndDate()) ? mergedRange.getEndDate() : current.getEndDate());
        }
    }
}

class DateRange {

    private LocalDate startDate;

    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public static final Comparator<DateRange> START_DATE_COMPARATOR = (DateRange o1, DateRange o2) -> {
        if (o1.getStartDate() != null && o2.getStartDate() != null) {
            if (o1.getStartDate().isBefore(o2.getStartDate())) {
                return -1;
            }
            else {
                return o1.getStartDate().isAfter(o2.getStartDate()) ? 1 : 0;
            }
        }
        else if (o1.getStartDate() != null && o2.getStartDate() == null) {
            return -1;
        }
        else if (o1.getStartDate() == null && o2.getStartDate() != null) {
            return 1;
        }
        else {
            return 0;
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        }
        else if (!endDate.equals(other.endDate))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        }
        else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }
}