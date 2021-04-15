package hotelbookingsproject;

import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HotelBookingsProject {
static int RoomDate;
static double tab = 0;
static String GlobalEmail;
static boolean ReaderCheck = false;
//////////////////////////////////////////////////////////////////////^^^^^^^For whole code
static int chosenRoom;
static String[][] HotelRoomBookings = new String [13][4];
static int HotelRoomTime;
static int HotelRoomHour;
static int HotelAmOrPm = 3;
static boolean creatingTable = true;//for hotel room table
static boolean creatingTable2 = true;//for meeting room table
////////////////////////////////////////////////////////////////////////////////^^For choosing room Number method
static int bookMeetingRoom;
static int MeetingRoomTime;  
static int MeetingRoomHour;
static String[] availability = {"Not available", "available    "};//this array used for availibility AND cleanliness
static int[][] bookingsAvailabilityCheck = new int[13][5];//for availability check. 0 = available. 1 = Not Available
//static String[][] bookingsTimeCheck = new String[13][5];Not needed if bookings is global
static String [][] bookings = new String[13][4];
static int AmOrPm = 3;
////////////////////////////////////////////////////////////////////////////////////////^^For meeting room method
static int roomServiceMealNumber = 0; //for room service method
    static int moreRoomService = 0;
    static int RoomServiceWanted = 1;
////////////////////////////////////////////////////////////^^^^^For room service method
static String emailLine;
static String tabLine;
static int globalLineCount;//count used for text file line number.
    //above are for textFile and textFileCopier ^^^^^^^^^^
    
   
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
       
        TextFile();
   
        System.out.println("Please enter your email address");
        String email = input.next();
        while (!email.contains("@") || (!email.contains(".com") && !email.contains(".uk"))){
            System.out.println("Invalid Email address\nPlease enter you email address");
            String email1 = input.next();
            email = email1;
        }
        GlobalEmail = email;
        try{
            Scanner Reader = new Scanner(System.in);
            int ReaderCount = 0;
            Scanner Reader2 = new Scanner(new FileInputStream("C:\\Users\\raidl\\OneDrive - Reigate College\\Documents\\NetBeansProjects\\HotelBookingsProject\\emails.txt"));
            while (Reader2.hasNextLine()){
                String whatLineSays = Reader2.nextLine();
                //System.out.println(whatLineSays);
                if (whatLineSays.indexOf(email)!=-1){
                    ReaderCheck = true;
                    ReaderCount++;
                }
            }
            
            if (ReaderCheck){
                try{
                System.out.println("Your email is already registered");//sometimes a space is added before the email in emails.txt, not sure why
                System.out.println("Which email is yours. 1 for first, 2 for second, 3 for third etc");//used to ensure users email matchs with their tab, kept in 2d array: tabAndEmail
                globalLineCount = input.nextInt();
                CopyingTextFile();
                System.out.println("Your tab is: "+tabLine);
                    double realTabLine = Double.parseDouble(tabLine);//changing tabLine to a double.
                    tab = tab + realTabLine;
                    }catch (Exception e){
                    System.out.println("Error " +e);
                }
            }else{
 
try (PrintWriter out = new PrintWriter(new FileWriter("emails.txt", true))) {//this paragraph appends email to emails.txt
    String data = "\n " + email;
    File myFile = new File("email.txt");
    out.println(data);
} catch (IOException e) {
}
            }//this is end bracket for else. Meaning it only happens if email is NOT already registerd
        }catch (Exception e){//try catch needed as FileInputStream line needed to be caught or declared
            e.printStackTrace();//this handles exceptions and errors - throws them
        }




        boolean cont = false;
        System.out.println("There are 7 different days you can book a room for, Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6) and Sunday(7)");
        while (cont == false){
        try {
        System.out.println("Please enter the number of the day you which to book a room for");
        RoomDate = input.nextInt();
        
        switch (RoomDate) { //this might go under RoomDate inputmiusmatchexception
                case 1:
                    Monday();
                    break;

                case 2:
                    Tuesday();
                    break;

                case 3:
                    Wednesday();
                    break;

                case 4:
                    Thursday();
                    break;

                case 5:
                    Friday();
                    break;

                case 6:
                    Saturday();
                    break;

                case 7:
                    Sunday();
                    break;
        
        
    }
        
        }catch(InputMismatchException e) {
            System.out.println("Invalid Input");
            input.next();
        }
        if (RoomDate > 0 && RoomDate < 8){
            cont = true;
        }
        }
        
        
     
}

    public static void TextFile(){
        
        
         try {
      File myFile = new File("C:\\Users\\raidl\\OneDrive - Reigate College\\Documents\\NetBeansProjects\\HotelBookingsProject\\emails.txt");
      if (myFile.createNewFile()) {
        System.out.println("File created: " + myFile.getName());
        //add here the initial part of emails.txt so if a new file is creates on a different computer, the correct line numbers sync up.
        try (PrintWriter out = new PrintWriter(new FileWriter("emails.txt", true))) {//this paragraph appends email to emails.txt
    out.println("Emails our hotel has saved:\n");
} catch (IOException e) {
}
      }
    } catch (IOException e) {
      System.out.println("Error: "+e);//creates new file
      e.printStackTrace();
    }       
     
        try {
        File myFile2 = new File("emails.txt");//creates text file, i think
      Scanner Reader = new Scanner(myFile2);
      while (Reader.hasNextLine()) {
        String data = Reader.nextLine();
        System.out.println(data);//prints data of emails.txt
      }
      Reader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: "+e);
      e.printStackTrace();
    }
    }
    
    public static void CopyingTextFile(){      
        try{
   BufferedReader textCopier = new BufferedReader(new FileReader("emails.txt"));
            int Count = 4;
if (globalLineCount >2 ){
    while (globalLineCount > 1){
        Count+=3;
        globalLineCount--;
    }
}else if (globalLineCount == 2){
    Count = 7;
}
while ((emailLine = textCopier.readLine()) != null) {
  Count++;
tabLine = textCopier.readLine();
  if(GlobalEmail.equals(emailLine) || GlobalEmail.equals(" "+emailLine)) {
      break;
  }
} 

    }catch (IOException e){
            System.out.println("Error: "+e);
}
    }
    
    public static void ChoosingRoomNumber(){
    Random rand = new Random();
    boolean chosenRoomClean = false;
        Scanner input = new Scanner(System.in);
        int counter = 0;
        boolean cont = false;
        String[] CleanOrDirty = new String[]{"Clean", "Dirty"};//just holds strings 'clean' and 'dirty'
        String[] CleanOrDirtyAppend = new String[]{};//array that each room's clean or dirty variable appends to
        int Room1Clean = rand.nextInt((1-0)+1)+0;
        int Room2Clean = rand.nextInt((1-0)+1)+0;
        int Room3Clean = rand.nextInt((1-0)+1)+0;
        int Room4Clean = rand.nextInt((1-0)+1)+0;
        int Room5Clean = rand.nextInt((1-0)+1)+0;
        System.out.println("Room 1 - For 2 people - no wheelchair access - £5 an hour - "+CleanOrDirty[Room1Clean]);
        System.out.println("Room 2 - For 4 people - no wheelchair access - £10 an hour - "+CleanOrDirty[Room2Clean]);
        System.out.println("Room 3 - For 8 people - no wheelchair access - £25 an hour - "+CleanOrDirty[Room3Clean]);
        System.out.println("Room 4 - For 15 people - wheelchair access - £50 an hour - "+CleanOrDirty[Room4Clean]);
        System.out.println("Room 5 - For 50 people - no wheelchair access - £200 an hour - "+CleanOrDirty[Room5Clean]);
        //CleanOrDirtyAppend[0] = Room1Clean;
        while (cont == false){
        try{
        System.out.println("Which room would you like? Input the number of the room you want");
        chosenRoom = input.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input " + e);
            input.next();
        }
        if(chosenRoom > 0 && chosenRoom < 6){
            cont = true;
        }
        }
        
        if (chosenRoom == 1){
            tab+=5;
        }else if (chosenRoom == 2){
            tab+=10;
        }else if(chosenRoom == 3){
            tab+=25;
        }else if(chosenRoom == 4){
            tab+=50;
        }else if(chosenRoom == 5){
            tab+= 200;
        }
        
        //////////////////////////////////////Only needs to happen once
        while(creatingTable == true){
    
        int x = 0;
           while (x<13){
           int y = rand.nextInt((1-0)+1)+0;
           HotelRoomBookings[x][1] = availability[y];
           y = rand.nextInt((1-0)+1)+0;
           HotelRoomBookings[x][3] = availability[y];
           x++;
           }
           
        
        
         HotelRoomBookings[0][0] = "\033[31mTime(am)\033[30m";
           HotelRoomBookings[0][1] = "\033[31mAvailability\033[31m";
           HotelRoomBookings[0][2] = "\033[31mTime(pm)\033[31m";
           HotelRoomBookings[0][3] = "\033[31mAvailability\033[31m";
           

          String[] Times = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

           int count = 1;
           int TimeArrayCounter = 0;
           while (count < 13){
               HotelRoomBookings[count][0] = ("  " + Times[TimeArrayCounter]);
               HotelRoomBookings[count][2] = ("  " + Times[TimeArrayCounter]);
               count++;
               TimeArrayCounter++;
           }
           creatingTable = false;
}//ensures table isnt replaced by another table
        
        /////////////////////////////////////////////////
            System.out.println("Here are the times you can book your hotel room for:");
        
        
       
        System.out.println(Arrays.deepToString(HotelRoomBookings).replace("], ", "]\n"));
           
           System.out.println("");   
            boolean cont3 = false;//for later on in method
boolean chosenTimeAndDayContinue = true;//boolean used for loop so user can input correct time and 12 hour period if entered wrong first.
    while (chosenTimeAndDayContinue == true){
          
           boolean cont2 = false;
    while(cont2 == false){
       try{
           cont2 = true;
       System.out.println("Would you like to book the hotel room in the morning(1) or the evening(2).");
       HotelRoomTime = input.nextInt();
       }catch (InputMismatchException e){
           System.out.println("Invalid Input");
           input.next();
       }
       if (HotelRoomTime != 1 && HotelRoomTime != 2){
           cont2 = false;
       }
    }
    
    while (HotelRoomTime != 1 && HotelRoomTime != 2){
        System.out.println("Invalid Input. Morning(1) or Evening(2)");
        int HotelRoomTime2 = input.nextInt();
        HotelRoomTime = HotelRoomTime2;
    }
    if (HotelRoomTime == 1){//the morning times in the array are in row 0, and the evening times are in row 2
        HotelAmOrPm = 1;
    }
  
    while(cont3 == false){
       try{
    System.out.println("Please enter the time you want to book the hotel room for. e.g. '6'");
    HotelRoomHour = input.nextInt();
       }catch (InputMismatchException e){
           System.out.println("Invalid Input");
           input.nextInt();
       }
       cont3 = true;
    }
    //if bookings morning/evening certainTime ==== Not available{
     while (HotelRoomBookings[HotelRoomHour][HotelAmOrPm].equals(availability[0])){//if hour chosen not available
         try{
         System.out.println("This time is unavailable. Please pick another");
         int HotelRoomHour2 = input.nextInt();
         HotelRoomHour = HotelRoomHour2;
         }catch (InputMismatchException e){
             System.out.println("Invalid Input");//catch for invalid data type
             input.nextInt();
         }catch (ArrayIndexOutOfBoundsException e){//catch for invalid int
             System.out.println("Invalid Input");
             input.nextInt();
         }
         chosenTimeAndDayContinue = false;
         cont3 = false;
     }
         System.out.println("Your time has been booked. Enjoy your stay!");
       HotelRoomBookings[HotelRoomHour][HotelAmOrPm] = (availability[0]);   
       cont3 = true;
       break;
    }        
}

    public static void Monday(){
        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
        
int randomCounter = 0;

}

    public static void Tuesday() {

        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
}

    public static void Wednesday() {

        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
}

    public static void Thursday() {

        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
}

    public static void Friday() {

        Scanner input = new Scanner(System.in);  
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
}

    public static void Saturday() {

        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
        meetingRoom();
        
        
}

    public static void Sunday() {
        
        Scanner input = new Scanner(System.in);   
        
        ChoosingRoomNumber();
        
        boolean cont = false;
        while (cont == false){
        try {
            System.out.println("Would you like to order room service, Yes(1) or No(2)");
        RoomServiceWanted = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            input.nextInt();
        }
        if (RoomServiceWanted == 1){
            cont = true;
            RoomService();
        }else if (RoomServiceWanted == 2){
            cont = true;
        }
        }
meetingRoom();
}
    
    public static void RoomService(){
        Scanner input = new Scanner(System.in);
        double multiplier = 1;
        if (RoomDate == 6 || RoomDate == 7){
            multiplier = 1.5;
            System.out.println("Each meal ordered via room service costs 50% more on the weekend than on weekdays");
        }
        
            System.out.println("The prices for room service are as follows:\n");
            System.out.println("Steak and chips - £30 (1)");
            System.out.println("Chicken nuggets + sauce - £15 (2)");
            System.out.println("Bottled Water - £2 (3)");
            System.out.println("Fried fish and mushrooms - £25 (4)");
            System.out.println("Caviar - £65 (5)");
            System.out.println("Mac n Cheese - £15 (6)");
            System.out.println("Pizza - £30 (7)");
            System.out.println("Exit (0)");
        boolean cont = false;
        boolean foodCheck = true;
        boolean foodCheck2 = false;

        //ArrayList<Integer> roomServiceArray = new ArrayList<Integer>(); 
        while (cont == false){
            while (foodCheck == true){//this might need to go back under the try
            try{
                
        System.out.println("Please enter the number of the dish/beverage you would like to order. Or enter '0' to exit");
        roomServiceMealNumber = input.nextInt();
        if (roomServiceMealNumber >=0 && roomServiceMealNumber < 8 ){
            foodCheck = false;
        }
    }catch (InputMismatchException e){
                System.out.println("Invalid Input " + e);
                input.nextInt();
    }
        }//this might need to go back above the catch
            boolean moreMeals = true;
            while (moreMeals == true){
            moreMeals = false;
            
            switch (roomServiceMealNumber){
                
                case 1:
                case 7:{
                    tab = tab + (30*multiplier);
                System.out.println("Your tab is currently £" + tab);
                break;
                }
                case 2:
                case 6:{
                    tab = tab + (15*multiplier);
                System.out.println("Your tab is currently £" + tab);
                break;
                }
                case 3:{
                    tab = tab + (2*multiplier);
                System.out.println("Your tab is currently £" + tab);
                break;
                }
                case 4:{
                    tab = tab + (25*multiplier);
                System.out.println("Your tab is currently £" + tab);
                break;
                }
                case 5:{
                    tab = tab + (65*multiplier);
                System.out.println("Your tab is currently £" + tab);
                break;
                }
                case 0:{
                    System.out.println("No more meals ordered");
                    foodCheck2 = true;//cancels loop
                 foodCheck = false;//cancels loop
                    System.out.println("Your tab is currently £" + tab);
                    meetingRoom();
                    break;
                }
                default:{
                 moreMeals = true;
                 break;
                }
            }
            }
            while (foodCheck2 == false){
            try{
            System.out.println("Would you like to order more, Yes (1) or No (2)");//fix this loop
            moreRoomService = input.nextInt();
            }catch (InputMismatchException e){
                System.out.println("invalid input");
                input.next();
            }
            if (moreRoomService == 1 || moreRoomService == 2){
                foodCheck2 = true;
            }
            }
            if (moreRoomService == 1){
                foodCheck = true;
                cont = false;
            }else if (moreRoomService == 2){
                cont = true;
                System.out.println("Your tab is currently £" + tab);
            }
}
}


public static void meetingRoom(){
    boolean MeetingRoomActive = true;
    while (MeetingRoomActive == true){

    Scanner input = new Scanner(System.in);
    Random rand = new Random();
    boolean cont = false;
    while(cont == false){
    try{   
    System.out.println("Would you like to book a meeting room, Yes(1) No(2)?");
    bookMeetingRoom = input.nextInt();
    }catch (InputMismatchException e){
        System.out.println("Invalid input");//fix this - done
        input.next();
    }
    if (bookMeetingRoom == 1){
    cont = true;
    }else if(bookMeetingRoom == 2){
        cont = true;
        MeetingRoomActive = false;
        System.out.println("Your tab is £"+tab+ "\nNext Customer please");
        break;
    }
    }
       if (bookMeetingRoom == 1){
           System.out.println("This is a table of the avaliable bookings for the meeting room. The room is booked in 1 hour slots");
           String[] Times = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
           ////////////////////////////////////////////////////////////////////////////////////////////
           while (creatingTable2 == true){   
           int x = 1;
           while (x<13){
           int y = rand.nextInt((1-0)+1)+0;
           bookings[x][1] = availability[y];
           y = rand.nextInt((1-0)+1)+0;
           bookings[x][3] = availability[y];
 
           x++;
           }
           

                   
           bookings[0][0] = "\033[31mTime(am)\033[30m";
           bookings[0][1] = "\033[31mAvailability\033[31m";
           bookings[0][2] = "\033[31mTime(pm)\033[31m";
           bookings[0][3] = "\033[31mAvailability\033[31m";
           

           int count = 1;
           int TimeArrayCounter = 0;
           while (count < 13){
               bookings[count][0] = ("  " + Times[TimeArrayCounter]);
               bookings[count][2] = ("  " + Times[TimeArrayCounter]);
               count++;
               TimeArrayCounter++;
           }
          creatingTable2 = false;
           }
           //////////////////////////////////////////////////////////////////^^Ensures table isnt replaced by another table
           ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                   int z = 1;
           while (z < 12){         //before it was.equals("available" OR availability array wasnt global. However it didnt work so i made it global and it still thinks bookings[1][z] is null
               if (bookings[z][1].equals(availability[0])){     //This needs to go somewhere where it can read "bookings", but doesnt break bookings
                   bookingsAvailabilityCheck[z][1] = 0;
           }else if(bookings[z][1].equals(availability[1])){
                   bookingsAvailabilityCheck[z][1] = 1;
               }
           z++;
           }
           /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
           
           System.out.println(Arrays.deepToString(bookings).replace("], ", "]\n"));
           
           System.out.println("");
                  
    boolean chosenTimeAndDayContinue = true;//boolean used for loop so user can input correct time and 12 hour period if entered wrong first.
    while (chosenTimeAndDayContinue == true){
          
    boolean cont2 = false;
    while(cont2 == false){
       try{
       System.out.println("Would you like to book the meeting room in the morning(1) or the evening(2).");
       MeetingRoomTime = input.nextInt();
       }catch (InputMismatchException e){
           System.out.println("Invalid Input");
           input.next();
       }
       if (MeetingRoomTime == 1 || MeetingRoomTime == 2){
           cont2 = true;
       }
    }
    
    while (MeetingRoomTime != 1 && MeetingRoomTime != 2){
        System.out.println("Invalid Input. Morning(1) or Evening(2)");
        int MeetingRoomTime2 = input.nextInt();
        MeetingRoomTime = MeetingRoomTime2;
    }
    if (MeetingRoomTime == 1){//the morning times in the array are in row 0, and the evening times are in row 2
        AmOrPm = 1;
    }else{
        cont2 = true;
        chosenTimeAndDayContinue = false;
        
    }
  
    boolean cont3 = false;
    while(cont3 == false){
       try{
    System.out.println("Please enter the time you want to book the meeting room for. e.g. '6'");
    MeetingRoomHour = input.nextInt();
       }catch (InputMismatchException e){
           System.out.println("Invalid Input");//figure out why after this point, code loops back to
           input.nextInt();
       }
       cont3 = true;
    
    //if bookings morning/evening certainTime ==== Not available{
     while (bookings[MeetingRoomHour][AmOrPm].equals(availability[0])){//if its not available
         System.out.println("This time is unavailable. Please pick another");
         int MeetingRoomHour2 = input.nextInt();
         MeetingRoomHour = MeetingRoomHour2;
         chosenTimeAndDayContinue = false;
         cont3 = false;
     }
     
         System.out.println("Your time has been booked. Enjoy the room!");
       bookings[MeetingRoomHour][AmOrPm] = (availability[0]);
       cont3 = false;
       boolean cont4 = true;
       while (cont4 == true){
         System.out.println("Would you like some stationary brought up to your meeting room, Y or N");
         String stationary = input.next();
      
       if (stationary.equalsIgnoreCase("y")){
           
           System.out.println("All stationary is complementary and will be brought up to you");
           System.out.println("*\033[35mSuddenly some pens, pencils, a calcualator and a ruler magically appear next to you*\033[33m");
           cont4 = false;
       }else if (stationary.equalsIgnoreCase("n")){
           cont4 = false;
       }else{
           System.out.println("Invalid Input");
     
    }
     }
       int y = 1;
       cont3 = true;
       cont2 = true;
       chosenTimeAndDayContinue = false;
       MeetingRoomActive = false;
       continue;//not sure if this is needed
    }//past this bracket means time is available
   
    }

}//This bracker for if statement asking if they want a meeting room   
    if (ReaderCheck == false){//if email already uses, doont append a new tab.
           try (PrintWriter out = new PrintWriter(new FileWriter("emails.txt", true));) {//this should add the tab to each email line
    double data = tab;
    File myFile = new File("email.txt");
    out.println(data);
} catch (IOException e) {
} 
    }
   System.out.println("Your tab is £"+tab);
    System.out.println("Next customer please run the code");
    System.exit(0);         
}
    
}
}