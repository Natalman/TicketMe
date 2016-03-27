package com.Natalman;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

/**
 * Created by TheKingNat on 3/25/16.
 */
public class resolvedTicket {

    private Date resovedDate;
    private String Resolution;
    ArrayList<String> tic;

    public resolvedTicket( Date D, String R){
        this.Resolution = R;
        this.resovedDate = D;

        this.tic = new ArrayList<String>();
    }

    void ArraTic (String resTic) {
        tic.add(resTic);
        System.out.println("The ticket has been added to the resolved ticket");
    }
    void write(){
        System.out.println("Has the problem been resolved: " + this.Resolution + " Resolution date: " + this.resovedDate + "\nTicket resolved:" +
                "**************\n("+ tic + ")");
    }

    //Creating a file for a ticket deleted everyday
    void writeToFile () throws IOException{
        FileWriter deleteFile = new FileWriter("Resolved Ticket of " + this.resovedDate + ".txt");
        BufferedWriter FileDeleted = new BufferedWriter(deleteFile);

        FileDeleted.write("Resolution: " + this.Resolution + " ; Date: " + this.resovedDate);
        FileDeleted.close();
    }


}
