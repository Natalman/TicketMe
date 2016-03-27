package com.Natalman;

import java.util.*;
import java.io.*;

public class TicketManager {

    public static void main(String[] args) throws IOException{

        LinkedList<ticket> ticketQueue = new LinkedList<ticket>();

        //Writing to the file openTicket.txt
        FileOutputStream open = new FileOutputStream("openTicket.txt");
        ObjectOutputStream OpenTic = new ObjectOutputStream(open) ;


        //Reading from the file and filling out ticketQueue with it data
        try {
            FileInputStream read = new FileInputStream("openTicket.txt");
            ObjectInputStream OPT = new ObjectInputStream(read);

            while (OPT!= null){
                ticketQueue = (LinkedList<ticket>) OPT.readObject();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Scanner scan = new Scanner(System.in);

        while(true){

            System.out.println("1. Enter Ticket\n2. Delete by ID\n3. Delete by Issue\n4. Search by Name\n5. Display All Tickets\n6. Quit");
            int task = Integer.parseInt(scan.nextLine());

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);

            } else if (task == 2) {
                //delete a ticket by ID
                deleteTicket(ticketQueue);

            }else if (task ==3) {
                //Deleting By issue
                deleteByIssue(ticketQueue);

            }else if (task == 4){
                //Searching for name
                SearchName(ticketQueue);

            } else if ( task == 6 ) {

                //creating a file and closing the program
                for (ticket tic : ticketQueue){
                    String nt = tic.toString();
                    OpenTic.writeObject(nt);
                }
                OpenTic.close();
                System.out.println("Quitting program");

                break;
            }
            else {
                //this will happen for 3 or any other selection that is a valid int
                //TODO Program crashes if you enter anything else - please fix
                //Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }

        scan.close();

    }

    protected static void deleteTicket(LinkedList<ticket> ticketQueue) throws IOException{
        printAllTickets(ticketQueue);   //display list for user

        if (ticketQueue.size() == 0) {    //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }

        Scanner deleteScanner = new Scanner(System.in);
        Scanner resScanner = new Scanner(System.in);

        int deleteID = 0;
        boolean isNumber;

        //User input validation
        do {
            System.out.println("Enter ID of ticket to delete");
            if (deleteScanner.hasNextInt()) {
                deleteID = deleteScanner.nextInt();
                isNumber=true;
            }else {
                System.out.println("Enter a valid integer");
                isNumber=false;
                deleteScanner.next();
            }
        } while (!(isNumber));

        //Loop over all tickets. Delete the one with this ticket ID
        boolean found = false;
        for (ticket ticket : ticketQueue) {
            if (ticket.getTicketID() == deleteID) {
                found = true;

                //Getting data for the Resolved Ticket
                System.out.println("Enter yes if the problem has been solved, Otherwise enter No");
                String resolution = resScanner.next();
                Date dateResolved = new Date();

                // Making a resolved ticket object
                resolvedTicket TK = new resolvedTicket(dateResolved, resolution);
                TK.ArraTic( dateResolved + " ; Resolution: " + resolution);
                TK.write();
                TK.writeToFile();
                ticketQueue.remove(ticket);
                System.out.println(String.format("Ticket %d deleted", deleteID));
                break; //don't need loop any more.
            }
        }
        if (found == false) {
            System.out.println("Ticket ID not found, no ticket deleted");

        }
        printAllTickets(ticketQueue);  //print updated list

    }
    protected static void deleteByIssue(LinkedList<ticket> ticketQueue)throws IOException{
        printAllTickets(ticketQueue);   //display list for user

        if (ticketQueue.size() == 0){
            System.out.println("No Ticket to delete!\n");
            return;
        }

        Scanner IssueScanner = new Scanner(System.in);
        System.out.println("Enter the description to delete");
        String deleteIssue = IssueScanner.next();


        //Getting the desc
        for (ticket ticket : ticketQueue){
            if (deleteIssue.equalsIgnoreCase(ticket.getDescription())){

                System.out.println("The ticket to delete");
                System.out.println("***********!!!!!!!*********");
                System.out.println(ticket);
                break;
            }
        }

        Scanner deleteScanner = new Scanner(System.in);

        int deleteID = 0;
        boolean isNumber;
        Scanner resScanner = new Scanner(System.in);

        //User validation
        do {
            System.out.println("Enter ID of ticket to delete");
            if (deleteScanner.hasNextInt()) {
                deleteID = deleteScanner.nextInt();
                isNumber=true;
            }else {
                System.out.println("Enter a valid integer");
                isNumber=false;
                deleteScanner.next();
            }
        } while (!(isNumber));

        //Loop over all tickets. Delete the one with this ticket ID
        boolean found = false;
        for (ticket ticket : ticketQueue) {
            if (ticket.getTicketID() == deleteID) {
                found = true;
                ticketQueue.remove(ticket);

                //Getting data for the Resolved Ticket
                System.out.println("Enter yes if the problem has been solved, Otherwise enter No");
                String resolution = resScanner.next();
                Date dateResolved = new Date();

                //// Making a resolved ticket object
                resolvedTicket TK = new resolvedTicket(dateResolved, resolution);
                TK.ArraTic( dateResolved + " ; Resolution: " + resolution);
                TK.writeToFile();
                TK.write();
                ticketQueue.remove(ticket);
                System.out.println(String.format("Ticket %d deleted", deleteID));
                break; //don't need loop any more.
            }
        }
        if (found == false) {
            System.out.println("Ticket ID not found, no ticket deleted");

        }
        printAllTickets(ticketQueue);

    }
    protected static void SearchName(LinkedList<ticket> ticketQueue){
        printAllTickets(ticketQueue);

        if (ticketQueue.size() == 0){
            System.out.println("No Ticket to look for\n");
            return;
        }
        //Searching ticket by name
        Scanner nameScanner = new Scanner(System.in);
        System.out.println("Enter the description to delete");
        String nameSearch = nameScanner.next();

        for (ticket ticket : ticketQueue){
            if (nameSearch.equalsIgnoreCase(ticket.getDescription())){

                System.out.println("The ticket(s) found by your search are:");
                System.out.println("*************^^^^^^^********************");
                System.out.println(ticket);
                break;
            }
        }
    }


    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        boolean moreProblems = true;
        String description, reporter;
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;

        while (moreProblems){
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());

            ticket t = new ticket(description, priority, reporter, dateReported);
            //ticketQueue.add(t);
            addTicketInPriorityOrder(ticketQueue, t);

            printAllTickets(ticketQueue);

            System.out.println("More tickets to add?");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }

    protected static void addTicketInPriorityOrder(LinkedList<ticket> tickets, ticket newTicket){

        //Logic: assume the list is either empty or sorted

        if (tickets.size() == 0 ) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size() ; x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }

    protected static void printAllTickets(LinkedList<ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");

        for (ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
            //println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");

    }
}

