package com.company;

import java.time.LocalDate;
import java.util.*;


class Main {

public static void main(String[] args) throws Exception {

        List<Plan> old = new ArrayList<>();
        old.add(new Plan(101, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(102, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(103, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(104, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(105, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(106, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));
        old.add(new Plan(107, LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 20)));

        List<Plan> neww = neww ArrayList<>();
        neww.add(new Plan(101, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 5)));
        neww.add(new Plan(102, LocalDate.of(2019, 5, 5), LocalDate.of(2019, 5, 15)));
        neww.add(new Plan(103, LocalDate.of(2019, 5, 5), LocalDate.of(2019, 5, 25)));
        neww.add(new Plan(104, LocalDate.of(2019, 5, 15), LocalDate.of(2019, 5, 18)));
        neww.add(new Plan(105, LocalDate.of(2019, 5, 15), LocalDate.of(2019, 5, 25)));
        neww.add(new Plan(106, LocalDate.of(2019, 5, 25), LocalDate.of(2019, 5, 30)));
        neww.add(new Plan(107, LocalDate.of(2019, 5, 12), LocalDate.of(2019, 5, 13)));
        neww.add(new Plan(107, LocalDate.of(2019, 5, 16), LocalDate.of(2019, 5, 18)));

        List<Plan> ans = getCancelledPeriodsForTask(oldPlanList, newPlanList);

        System.out.println("Canceled Periods are: ");
        System.out.println(ans);
        }


public static List<Plan> getCancelledPeriodsForTask(List<Plan> oldPlanList, List<Plan> newPlanList) {

    ArrayList<Plan> arr = new ArrayList<>();
    int i=0,j=0;
    while(i<oldPlanList.size()) {
        while(j<newPlanList.size()) {
            if (oldPlanList.get(i).getTaskId() == newPlanList.get(j).getTaskId()) {


                if(newPlanList.get(j).getEndDate().isBefore(oldPlanList.get(i).getStartDate())){
                    arr.add(oldPlanList.get(i));
                } else if(oldPlanList.get(i).getEndDate().isBefore(newPlanList.get(j).getStartDate())){
                    arr.add(oldPlanList.get(i));
                } else if (newPlanList.get(j).getEndDate().isBefore(oldPlanList.get(i).getEndDate())) {
                    if(oldPlanList.get(i).getStartDate().isAfter(newPlanList.get(j).getStartDate())){
                        LocalDate end1=oldPlanList.get(i).getEndDate();
                        LocalDate start1 = newPlanList.get(j).getEndDate();
                        int task=oldPlanList.get(i).getTaskId();
                        arr.add(new Plan(task,start1.plusDays(1),end1));
                    }else{
                        LocalDate end2 =newPlanList.get(j).getStartDate();
                        LocalDate start2 =oldPlanList.get(i).getStartDate();
                        LocalDate end1=oldPlanList.get(i).getEndDate();
                        LocalDate start1 = newPlanList.get(j).getEndDate();
                        int task=oldPlanList.get(i).getTaskId();
                        arr.add(new Plan(task,start2,end2.plusDays(-1)));
                        arr.add(new Plan(task,start1.plusDays(1),end1));
                    }

                }else if(oldPlanList.get(i).getStartDate().isBefore(newPlanList.get(j).getStartDate())){
                    if(oldPlanList.get(i).getEndDate().isBefore(newPlanList.get(j).getEndDate())){
                           LocalDate start1=oldPlanList.get(i).getStartDate();
                           LocalDate end1 = newPlanList.get(j).getStartDate();
                           int task=oldPlanList.get(i).getTaskId();
                           arr.add(new Plan(task,start1,end1.plusDays(-1)));
                    }else{
                        LocalDate start1=oldPlanList.get(i).getStartDate();
                        LocalDate end1 = newPlanList.get(j).getStartDate();
                        LocalDate start2 =newPlanList.get(j).getEndDate();
                        LocalDate end2 =oldPlanList.get(i).getEndDate();
                        int task=oldPlanList.get(i).getTaskId();
                        arr.add(new Plan(task,start1,end1.plusDays(-1)));
                        arr.add(new Plan(task,start2.plusDays(1),end2));
                    }
                }
                j++;
            }else{
                break;
            }
        }
        i++;
    }
    return arr;
 }
}





class Plan {

    private Integer taskId;

    private LocalDate startDate;

    private LocalDate endDate;

    public Plan(Integer taskId, LocalDate startDate, LocalDate endDate) {
        this.taskId = taskId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getTaskId() {
        return taskId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    @Override
    public String toString() {
        return "\n[taskId=" + taskId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}