import java.util.Scanner;

class BookNotAvailableException extends RuntimeException     //exception if the book is not available
{
    public BookNotAvailableException(String message)
    {
        super(message);
    }
}
class BookNotExistsException extends RuntimeException        //exception if the book was never available in the library
{
    public BookNotExistsException(String message)
    {
        super(message);
    }
}
class BorrowedLimitExceedException extends RuntimeException   //exception if the borrowed limit of a user has exceeds
{  
    public BorrowedLimitExceedException(String message)
    {
        super(message);
    }
}
class BookStoringLimitExceedException extends RuntimeException   //exception if the adding book limit reached
{
    public BookStoringLimitExceedException(String message)
    {
        super(message);
    }
}
class UserIDNotAvailableException extends RuntimeException    // exception if the id is already occupied
{
    public UserIDNotAvailableException(String message)
    {
        super(message);
    }
}
class UsersLimitExceedException extends RuntimeException
{
    public UsersLimitExceedException(String message)
    {
        super(message);
    }
}

//Class For Book 
 class Book
{
      String title;          //book name
      String author;         //book author
      int availability = 0;  //initializing availability
      int  quantity;         //total quantity of book

      public Book(){}      //Default class Book Constructor
    
    public Book(String title,String author,int quantity)  //custom constructor for class book 
    {
      this.title = title;
      this.author = author;
      this.quantity = quantity;
      this.availability = quantity;
    }
    //checking if book is available to borrow
    public boolean isBookAvailable(String title,String author)  //returning boolean value true if book is available
    {
        if(availability>0)
         return true;
        else
         return false;
    }    
 }

//Class for User
 class User
{
    String userName;  
    int userId;
     int totalBorrowed;   //total number of borrowed books by user
      String[] borrowedBooks;  // details of all borrowed books

     public User(){}          //default constructor

     public  User(String userName,int userId,int maxBorrow)     //Constructor for user with double strings parameters
    {
        this.userName = userName;
        this.userId = userId;
        this.borrowedBooks = new String[maxBorrow];
        this.totalBorrowed = 0;
    }
       
}

//Class for Library
 class Library 
{
     Book[] books;       //arrayObject of class book
     int bookCount;
     User[] users;
     int userCount;      //total quantity of books

    Scanner keyboard = new Scanner(System.in);

    
    //Constructor to ask user for maximum storing limit of user
    public Library()
    {
        System.out.println("Enter Number of Book Limit and User Limit of the Library");
        int maxBooks = keyboard.nextInt();
        int maxUser = keyboard.nextInt();
        this.books = new Book[maxBooks];
        this.users = new User[maxUser];
        this.bookCount = 0;
        this.userCount = 0;
    }

    //method to add new user to the libarary
    public void addUser()
    {
        System.out.println("Enter User Name :");
        keyboard.nextLine();     //to store the next line character
        String name = keyboard.nextLine();
        System.out.println("Enter User Id : ");
        int id = keyboard.nextInt();
        System.out.println("Enter User Borrowing Limit : ");
        int borrowLimit = keyboard.nextInt();

        System.out.println("useer name "+name+" id "+id+" borrow limit "+borrowLimit);

        if(userCount<users.length)
        {
        if(findUserIndex(id) == -1)
          users[userCount++] = new User(name,id,borrowLimit);
          else
            throw new UserIDNotAvailableException("This Id is already in Use");
        }
        else
          throw new UsersLimitExceedException("Adding new user is not available");
          
    }
  
  //return total book quantity in library
  public int totalBookQuantity()
  {
    int value=0;
    for(int i=0;i<bookCount;i++)
        value += books[i].quantity;

    return value;
  }


    //Add book to library
    void addBook()
    {
        //taking input from user
        System.out.println("Enter Book Name : ");
        keyboard.nextLine();   // storing previous next line character
       String Btitle = keyboard.nextLine();
       System.out.println("Enter Author Name : ");
       String Bauthor = keyboard.nextLine();
       System.out.println("Enter Book Quantity : ");
       int Bquantity = keyboard.nextInt();

        boolean status = true;
        
        if(totalBookQuantity()<=books.length)       //checking if books quantity exceeds limit
       {
         for(int i=0;i<bookCount;i++)
         {
            if(books[i].title==Btitle &&  books[i].author==Bauthor)     //if book with name and author already exists then add quantity only
               {
                books[i].quantity += Bquantity;                     //add new quantity to old quantity of the book
                books[i].availability += Bquantity;
                if(totalBookQuantity()<=books.length)                 //add to the availability
                {
                System.out.println("Book with same details already exists so Quanatity is only Increased");
                status = false;
                }
                 else
                   throw new BookStoringLimitExceedException("Adding Limit Exceeded Cannot add new books");
                break;
               }
          }
        if(status) 
        {
         books[bookCount++]= new Book(Btitle,Bauthor,Bquantity);
         System.out.println("Book added successfully");    
        }
       }
       else
        throw new BookStoringLimitExceedException("Adding Limit Exceeded Cannot add new books");
    }
    
    //Remove book from library
  void removeBook()
    {
        //taking input from user
       System.out.println("Enter Book Name : ");
       keyboard.nextLine();   // storing previous next line character
       String title = keyboard.nextLine();
       System.out.println("Enter Author Name : ");
       String author = keyboard.nextLine();

        boolean status = false;        //setting boolean value 
        int foundIndex=0;              
        for(int i=0;i<bookCount;i++)  //searching for index  of required book
        {
            if(title==books[i].title && author==books[i].author)    //matching title & author
             {
               foundIndex = i;
               status = true;
               break;           
             }
        }
       
       if(status)
        {
            if(foundIndex==bookCount-1)   //if index is the last book count 
             bookCount--;                 //decrementing book count
            else
            {
                for(int i=foundIndex;i<bookCount-1;i++)
                 books[i] = books[i+1];                  //replace content of index with next index
                 bookCount--;                            //decrementing book count
            }
        }
        if(status)
          System.out.println("Book Removed Successfully");
        else
         throw new BookNotExistsException("Unable to perform action"); //if book not found to remove
    }


 //check the availability of the book
    public void findVoidAvailablity()
    {
        
        System.out.println("Enter the book name : ");
         keyboard.nextLine();   //store the next line character 
        String title = keyboard.nextLine();
        System.out.println("Enter Author Name : ");
        String author = keyboard.nextLine();
        System.out.println(title+"  "+author);
        System.out.println("count = "+bookCount);
        System.out.println("0 = "+books[0].title+"-"+books[0].author);
        
        int i;
        boolean status=false;
        //checking if book with the name and author exists
      for( i=0;i<bookCount;i++)
      {
        if( title.equals(books[i].title)  &&  author.equals(books[i].author) )
        {
            status = true;
            break;
        }
      }
      if(status)   //if book is avialable
      {
        if(books[i].isBookAvailable(title,author)) 
        {
            System.out.println("Book is Available");
            
        }
        else    //if book is borrowed 
        {
            throw new BookNotAvailableException("Already Borrowed");
        }
      }
      else   //if book doesnot exists
      {
       
        throw new BookNotExistsException("Not available in our library");
      }
    }

    //find book availability in boolean return value
    public boolean findBooleanAvailablity(String title,String author)
    {
        int i;
        boolean status=false;
        //checking if book with the name and author exists
      for( i=0;i<bookCount;i++)
      {
        if( title.equals(books[i].title)  &&  author.equals(books[i].author) )
        {
            status = true;
            break;
        }
      }
      if(status)   //if book is avialable
      {
        if(books[i].isBookAvailable(title,author)) 
           status = true;
        else    //if book is borrowed 
           status = false;
      }
      else   //if book doesnot exists
        status = false;

        return status;
    }

    //find books with author names 
      public void findBook()
    {
        //taking input from user
        System.out.println("Enter Author Name : ");
        keyboard.nextLine();    //storing previously generated next line
        String author = keyboard.nextLine();
       boolean status = false;
        for(int i=0;i<bookCount;i++)
        {
            if(author.equals(books[i].author))
            {
            System.out.println("Author : "+author+"| Book : "+books[i].title);
            System.out.println("Quantity: "+books[i].quantity + " | Available : "+books[i].availability);
            System.out.println();
            status = true;
            }
        }
        if(!status)
        {
            throw new BookNotExistsException("No book is available of this author");
        }
    }
    //method to find the index of the book location
      public int findBookIndex(String title,String author)
    {
        boolean status = false;
        int index=-1;
        for(int i=0;i<bookCount;i++)
        {
            if(author.equals(books[i].author))
            {
            index = i; 
            status = true;
            break;
            }
        }
        if(!status)
            index = -1; 
        return index;

    }

    
    //find authors with book names 
      public void findAuthor()
    {
        //taking input from user
        System.out .println("Enter Book Name : ");
        keyboard.nextLine();    //storing previously generated next line character
        String title = keyboard.nextLine();
       boolean status = false;
        for(int i=0;i<bookCount;i++)
        {
            if(title.equals(books[i].title))
            {
            System.out.println("Book : "+title+"| Author : "+books[i].author);
            System.out.println("Quantity: "+books[i].quantity + " | Available : "+books[i].availability);
            System.out.println();
            status = true;
            }
        }
        if(!status)
        {
            throw new BookNotExistsException("No author is found with this book title");
        }
    }

//Display lists of all books from Library
    public void displayBooks()
    {
        System.out.println("Library Books : ");
        for(int i=0;i<bookCount;i++)
        {
            System.out.println("- "+books[i].title+" by "+books[i].author);
            System.out.println("Available Quantity: "+books[i].availability+" | Total Quantity : "+books[i].quantity);
            System.out.println();
        }
    }
    //Display lists of all user
    public void displayUsers()
    {   
        System.out.println();
        System.out.println(" Users : ");
        for(int i=0;i<userCount;i++)
        {
        System.out.println("User Name : "+users[i].userName+" | UserId : "+users[i].userId);
        System.out.println("Borrowing Limit : "+users[i].borrowedBooks.length);
        System.out.println("Total Number of books borrowed : "+users[i].totalBorrowed);
        System.out.println("Borrowed books : ");
        if(users[i].totalBorrowed>0)
        {
        for(int j=0;j<users[i].totalBorrowed;j++)
         {
          System.out.println("-"+users[i].borrowedBooks[j]);
          System.out.println();
         }
        }
         else 
         System.out.println("null");
      }
    }
    public void displayUser()
    {
        //taking input from user
        System.out.println("Enter User ID");
        int userID = keyboard.nextInt();

        int u = findUserIndex(userID);
      if(u!=-1)
      {
        System.out.println("User Name : "+users[u].userName+" | UserId : "+users[u].userId);
        System.out.println("Borrowing Limit : "+users[u].borrowedBooks.length);
        System.out.println("Total Number of books borrowed : "+users[u].totalBorrowed);
        System.out.println("Borrowed books : ");
        if(users[u].totalBorrowed>0)
        {
        for(int j=0;j<users[u].totalBorrowed;j++)
         {
          System.out.println("-"+users[u].borrowedBooks[j]);
          System.out.println();
         }
        }
        else
         System.out.println("null");
      }
       else throw new UserIDNotAvailableException("User not found with this id");
    }


    //return user index
    public int findUserIndex(int userID)
    {  
        int index=-1;
        for(int i=0;i<userCount;i++)
        {
            if(users[i].userId == userID)
            {
            index = i;
            break;
            }
        }
        return index;
    }


//method to borrow a book by user
public void borrowBook()
    {   
        //taking input from users
        System.out.println("Enter User Id : ");
        int userId = keyboard.nextInt();
        System.out.println("Enter Book Name : ");
        keyboard.nextLine();    //store previously generated next line character
        String title = keyboard.nextLine();
        System.out.println("Enter Author Name :");
        String author = keyboard.nextLine();

        int u = findUserIndex(userId);
        int b = findBookIndex(title,author);
        if(u != -1)
        {
        if(findBooleanAvailablity(title,author))
        {

            if(users[u].totalBorrowed<users[u].borrowedBooks.length)
            {
                users[u].borrowedBooks[users[u].totalBorrowed++] = books[b].title +" by "+books[b].author;
                books[b].availability--;

                System.out.println("Book issued Sucessfully");
                System.out.println();
            }
            else
            throw new BorrowedLimitExceedException("Return previous borrowed books to borrow another");
        }
        else
        throw new BookNotAvailableException("Book is not available to borrow");
        }
        else 
          throw new UserIDNotAvailableException("User with this id not found");
    }

  // method to return a book
  public void returnBook()
  {
    //taking input from user
    System.out.println("Enter User ID : ");
    int userID = keyboard.nextInt();
    keyboard.nextLine();//Store the new line character
    System.out.println("Enter Book Name : ");
    String title = keyboard.nextLine();
    System.out.println("Enter Author Name: ");
    String author = keyboard.nextLine();

    int u = findUserIndex(userID);
    int b = findBookIndex(title,author);
    if(u != -1)
    {
      if (b != -1)
      {
         users[u].totalBorrowed--;
         books[b].availability++;
      }
      else throw new BookNotExistsException("This is not a library's book ");
    }
    else 
    throw new UserIDNotAvailableException("User not recognized");
    
  }

//asking user for permission to exit or countinue
public int askFinalChoice()
{
    System.out.println("Do you want to Continue ? \n1 : Yes \n2 : No");
    return keyboard.nextInt();
}

}


//Main Funstion Class
public class LibraryManagementSystem
{
    public static void main(String[] args)
    {
        int finalChoice=2;

    Library libraryObj = new Library();
    Scanner keyboard = new Scanner(System.in);

do{
  System.out.printf("\nChoose Options --> \n1 : Add a New Book \n2 : Remove Existing book \n3 : Add a New User");
  System.out.printf("\n4 : Find Book Availability \n5 : Borrow a Book \n6 : Return a Book \n7 : Display All Books");
  System.out.printf("\n8 : Display All Users \n9 : Display Individual User Details \n10 : Find Book \n11 : Exit\n");
  int choice = keyboard.nextInt(); 

  switch(choice)
  {
    case 1:
        {
            libraryObj.addBook();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 2:
        {
            libraryObj.removeBook();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 3:
        {
            libraryObj.addUser();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 4:
        {
            libraryObj.findVoidAvailablity();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 5:
        {
            libraryObj.borrowBook();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 6:
        {
           libraryObj.returnBook();
           finalChoice = libraryObj.askFinalChoice();
           break;
        }
     case 7:
        {
            libraryObj.displayBooks();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 8:
        {
            libraryObj.displayUsers();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 9:
        {
            libraryObj.displayUser();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
     case 10:
        {
            libraryObj.findBook();
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
      case 11:
        {
            finalChoice = libraryObj.askFinalChoice();
            break;
        }
        
    }
 }while(finalChoice==1);
System.out.println("***End Of The Program***");


 




    }
}