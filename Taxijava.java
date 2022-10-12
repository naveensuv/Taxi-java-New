import java.util.*;
class Customer{
    static Scanner in = new Scanner(System.in);
    static Customer cus[] = new Customer[100];
    static int cuid=0;
    int customerid;
    String name,phno;
    
    Customer(int cuid,String name,String phno){
        this.customerid=cuid;
        this.name=name;
        this.phno=phno;
    }
    
    static void customer_creation(){
        System.out.println("Enter your Name :");
        String name=in.nextLine();
        System.out.println("Enter your phno :");
        String phno=in.nextLine();
        Customer.cus[cuid++] = new Customer(cuid,name,phno);
        System.out.println("--------------------------------Your Customer Has Been Created Successfully----------------------------------------------");
        System.out.println("your customer id is :"+cuid);

    }
    static void customer_list(){
        System.out.println("--------------------------------------------------------");
        System.out.println("Cusid        Name          Phno");
        System.out.println("--------------------------------------------------------");
        for(int i=0;i<cuid;i++)
            System.out.println(cus[i].customerid+"           "+cus[i].name+"       "+cus[i].phno);
        System.out.println("--------------------------------------------------------");
    }
}

class Taxi{
    static Scanner in = new Scanner(System.in);
    static Taxi car[] = new Taxi[100];
    int taxinumber,earning,status,tnumber,stime,etime;
    char from;
    
    Taxi(int earning,int status,char from,int taxinumber,int tno,int stime,int etime){
        this.earning=earning;
        this.status=status;
        this.from=from;
        this.taxinumber=taxinumber;
        this.tnumber=tno;
        this.stime=stime;
        this.etime=etime;  
    }
    
    static void taxi_creation(int n)
    {
        for(int i=0;i<n;i++)
        {
            int earning=0;
            int status=0;
            char from='A';
            int taxinumber=i;
            int tno=n;
            int stime=0;
            int etime=0;
            Taxi.car[i] = new Taxi(earning,status,from,taxinumber,tno,stime,etime);
            System.out.println(car[i].earning+" "+car[i].status+" "+car[i].from+" "+car[i].taxinumber);
        }
        System.out.println("--------------------  "+n+" number of taxi's created successfully---------------");
    }
}

class Booking{
    static Scanner in = new Scanner(System.in);
    static Booking book[] = new Booking[100];
    static char[] arr={'A','B','C','D','E','F'};
    static int tbook=0;
    int bookid,stime,etime,price,taxino,customerid;
    char from,to;
    
    Booking(int tbook,int id,int start,int end,char from,char to,int price,int pos){
        this.bookid=tbook;
        this.customerid=id;
        this.stime=start;
        this.etime=end;
        this.from=from;
        this.to=to;
        this.price=price;
        this.taxino=pos;
    }
    static int assign_taxi(int start,int end,int price,char from,char to){
        int drivercount=Taxi.car[0].tnumber,min=10000,c=0;
        int pos=-1,k=0,count=0,val=0;
        int pos1[] = new int[100];
        char from1=from,from2=from;
        while(k!=1){
            c=0;
            count=0;
            for(int i=0;i<drivercount;i++){
                if(Taxi.car[i].from == from1 || Taxi.car[i].from == from2){
                    pos1[count++]=i;
                    c=1;
                }
            }
            if(c==0){
                from1+=1;
                val++;
                from2-=1;
            }
            else{
                for(int i=0;i<count;i++)
                {
                    if(Taxi.car[i].status==0)
                    {
                        if(min>Taxi.car[i].earning)
                        {
                            min=Taxi.car[i].earning;
                            pos=i;
                        }
                    }
                    else if(Taxi.car[i].etime<=start)
                    {
                       Taxi.car[i].status=0;
                       i--;
                    }
                }
                if(val+Taxi.car[pos].etime>start)
                {
                    from1+=1;
                    from2-=1;
                    val++;
                }
                else
                	k=1;
            }
        }
        Taxi.car[pos].earning-=val*(15*5);
        return(pos);
    }
    
    static void taxi_booking()
    {
        int id,starti=0,endi=0;
        System.out.println("Enter your customer id :");
        id=in.nextInt();
        System.out.println("Enter your Pickup time :");
        int start=in.nextInt();
        System.out.println("Enter your Pickup position :");
        char from=in.next().charAt(0);
        System.out.println("Enter your Drop position :");
        char to=in.next().charAt(0);
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i]==from)
                starti=i;
            else if(arr[i]==to)
                endi=i;
        }
        int end=Math.abs(starti-endi);
        int price=(end*150)+50;
        int pos = assign_taxi(start,end,price,from,to);
        if(pos==-1){
            System.out.println("<--------------------------------there is no taxi available in your location----------------------------------------->");
        }
        else{
            Taxi.car[pos].earning+=price;
            Taxi.car[pos].from = to;
            Taxi.car[pos].status=1;
            Taxi.car[pos].stime=start;
            Taxi.car[pos].etime=start+end;
        System.out.println("your drop will be around    "+end+" hours around "+(start+end)+" O clock");
        System.out.println("your taxi fare is : "+price);
        System.out.println("your taxi alloted is : "+pos);
        end=end+start;
        Booking.book[tbook++]=new Booking(tbook,id,start,end,from,to,price,pos);
        System.out.println("<--------------------------------Your Taxi Has Been Booked Successfully------------------------------------------->");
        }
    }
    static void taxi_details()
    {
        System.out.println("enter your taxi no ");
        int id;
        id=in.nextInt();
        System.out.println("Taxi no  : "+id+"      Total earnings :"+Taxi.car[id].earning);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("bookid  cusid   from       to       Pickup       Drop        Price"); 
        System.out.println("-------------------------------------------------------------------------");
        for(int i=0;i<tbook;i++)
        {
        if(Booking.book[i].taxino==id)    
            System.out.println(Booking.book[i].bookid+"        "+Booking.book[i].customerid+"        "+Booking.book[i].from+"         "+Booking.book[i].to+"        "+Booking.book[i].stime+"           "+Booking.book[i].etime+"            "+Booking.book[i].price);
        }
        System.out.println("-------------------------------------------------------------------------");

    }
}


public class Taxi1
{
	public static void main(String[] args){
		int opt=-1,n;
		Scanner in = new Scanner(System.in);
		System.out.print("                                    ************ Taxi Booking Application *************                            \n");
		while(opt!=6)
		{
    		System.out.print("\n1.Customer creation\n2.taxi initialisation\n3.booking\n4.Taxi details\n5.customer list\n6.logout\n");
    		System.out.print("enter option :");
    		opt=in.nextInt();
    		switch(opt)
    		{
    		    
    		    case 1:
    		        in.nextLine();
    		        Customer.customer_creation();
    		        break;
    		    case 2:
    		        in.nextLine();
    		        System.out.println("enter number of taxi's :");
    		        n=in.nextInt();
    		        Taxi.taxi_creation(n);
    		        break;
    		    case 3:
    		        in.nextLine();
    		        Booking.taxi_booking();
    		        break;
    		    case 4:
    		        in.nextLine();
    		        Booking.taxi_details();
    		        break;
    		    case 5:
    		        in.nextLine();
    		        Customer.customer_list();
    		        break;
    		    case 6:
    		        System.out.println("<----------------------------logged out------------------------->");
    		 }
	    }
	}
}






