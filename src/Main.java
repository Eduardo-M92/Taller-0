import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * @author Daniel Barrios
 * @author Eduardo Marchandon
 */

public class Main
{
	public static void main(String args[]) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		
		String[][] s1M = new String[10][30];
		String[][] s1T = new String[10][30];
		String[][] s2M = new String[10][30];
		String[][] s2T = new String[10][30];
		String[][] s3M = new String[10][30];
		String[][] s3T = new String[10][30];
		String [] nameList = new String[100];
		String [] surnameList = new String[100];
		String [] rutList = new String[100];
		String [] passwordList = new String[100];
		String [][] moviesMa= new String [3][2];
		String [] movieList = new String[100];
		String [] typeList = new String[100];
		String [] schedule=new String[100];
		String[]clientTicket=new String[100];
		clientTicket[0]="pa";
		int[] dayBalance=new int[100];
		int[] mañanaBalance=new int[100];
		int[] tardeBalance=new int[100];
		int [] incomeList = new int[100];
		int [] balanceList = new int[100];
		boolean [] statusList = new boolean[100];
		/*
		 * save sizes
		 * @param sizeClients set Clients size
		 * @param sizeMovies set Movies size
		 */
		int sizeClients = readClients(nameList,surnameList,rutList,passwordList,balanceList,statusList);
		int sizeMovies = readMovies(moviesMa,movieList,typeList,incomeList,schedule);
		
		/*
		 * fill matrix
		 */
		for(int a=0;a<10;a++)
		{
			for(int b=0;b<30;b++)
			{
				s1M[a][b]=" ";
				s1T[a][b]=" ";
				s2M[a][b]=" ";
				s2T[a][b]=" ";
				s3M[a][b]=" ";
				s3T[a][b]=" ";
			}
		}
		/*
		 * seats not available
		 */
		for(int a=0;a<4;a++)
		{
			for(int b=0;b<5;b++)
			{
				s1M[a][b]="0";s1M[a][25+b]="0";
				s1T[a][b]="0";s1T[a][25+b]="0";
				s2M[a][b]="0";s2M[a][25+b]="0";
				s2T[a][b]="0";s2T[a][25+b]="0";
				s3M[a][b]="0";s3M[a][25+b]="0";
				s3T[a][b]="0";s3T[a][25+b]="0";
			}
		}
		login(clientTicket,tardeBalance,mañanaBalance,dayBalance,sizeClients,nameList,surnameList,rutList,passwordList,balanceList,statusList,s1M,s1T,s2M,s2T,s3M,s3T,moviesMa,movieList,typeList,incomeList,sizeMovies,schedule,sc);
		
	}
	
	/*
	 * Read file clientes.txt and status.txt to complete data
	 */
	public static int readClients(String []name,String []lName,String []rut,String []password,int []balance,boolean[]status) throws FileNotFoundException
	{
		
		File clients = new File("clientes.txt");
		File stats= new File("status.txt");
		int size=0;
		try (Scanner c = new Scanner(clients)) 
		{
			while(c.hasNext())
			{
				String line=c.nextLine();
				String[] dato=line.split(",");
				name[size]=dato[0];
				lName[size]=dato[1];
				String[] parts=dato[2].split("");
				String completo="";
				for (int a=0;a<parts.length;a++)
				{
					if(!parts[a].equals(".") && !parts[a].equals("-"))
					{
						completo = completo +parts[a];
					}
				}
				rut[size]=completo;
				password[size]=dato[3];
				balance[size]=Integer.parseInt(dato[4]);
				size+=1;
			}
		}
		try (Scanner s = new Scanner(stats))
		{
			while(s.hasNext())
			{
				String line=s.nextLine();
				String[] dato=line.split(",");
				String[] parts=dato[0].split("");
				String completo="";
				for (int a=0;a<parts.length;a++)
				{
					if(!parts[a].equals(".") && !parts[a].equals("-"))
					{
						completo = completo +parts[a];
					}
				}
				for (int a=0;a<size;a++)
				{
					if(completo.equals(rut[a]))
					{
						if (dato[1].equals("HABILITADO")){ status[a]=true; }
						else { status[a]=false; }
					}
				}
			}
		}
		return size;
	}
	
	/*
	 * Read file peliculas.txt to complete data
	 */
	public static int readMovies(String[][]movieM,String[]movieL,String[]typeL,int[]incomeL,String[] schedule) throws FileNotFoundException
	{
		File p= new File("peliculas.txt");
		try(Scanner c=new Scanner(p))
		{
			int size=0;
			while(c.hasNext())
			{
				String linea = c.nextLine();
				String[] dato =linea.split(",");
				movieL[size]=dato[0];
				typeL[size]=dato[1];
				incomeL[size]=Integer.parseInt(dato[2]);
				schedule[size]="";
				for(int i=3;i<dato.length;i++)
				{
					schedule[size]+=dato[i];
					if(i<(dato.length-1))
					{
						schedule[size]+=",";
					}
					if(dato[i].equals("M"))
					{ 
						movieM[Integer.parseInt(dato[i-1])-1][0]=dato[0];
					}
					else { if(dato[i].equals("T")) { movieM[Integer.parseInt(dato[i-1])-1][1]=dato[0];}}
				}
				size++;
			}
			return size;
		}
	}
	
	/*
	 * interface for entering startup data
	 */
	public static void login(String[]clientTicket,int[]tardeBalance,int[]mañanaBalance,int[]dayBlance,int size,String []names,String []lNames,String []ruts,String []passwords,int []balances,boolean[]status,String[][] s1M,String[][] s1T,String[][] s2M,String[][] s2T,String[][] s3M,String[][] s3T,String[][] moviesMa,String[] movieList,String[] typeList,int[] incomeList,int sizeMovies,String[] schedule,Scanner sc) throws IOException
	{
		boolean cycle= true;
		String password="";
		String[] totalTick = new String[100];
		Scanner sc2=new Scanner(System.in);
		while (cycle)
		{
			System.out.println("Bienvenido a Cuevana");
			System.out.println("--------------------");
			System.out.println("a)Iniciar sesion ");
			System.out.println("b)Registrarse ");
			System.out.println("c)Cerrar el sistema");
			System.out.println("--------------------");
			System.out.print("Ingrese operación a realizar: ");
			String option = sc.next().toLowerCase();
			
			switch (option)
			{
			case "a":
				System.out.println("--------------------");
				System.out.println("Iniciar sesion");
				System.out.print("Ingrese su rut:");
				String rut=sc.next();
				if(rut.equals("ADMIN"))
				{
					System.out.print("Ingrese su contraseña: ");
					password=sc.next();
					if(password.equals("ADMIN"))
					{
						adminMenu(clientTicket,tardeBalance,mañanaBalance,dayBlance,totalTick,size,names,lNames,ruts,passwords,balances,status,movieList,typeList,incomeList,sizeMovies,schedule);
						break;
					}
				}
				if(!authenticateID(rut)) { System.out.println("--------------------");System.out.println("**Rut incorrecto**");System.out.println("--------------------"); } 
				else
					{
						String [] parts = rut.split("");
						String completo="";
						for (int a=0;a<parts.length;a++)
						{
							if(!parts[a].equals(".") && !parts[a].equals("-"))
							{
								completo = completo +parts[a];
							}
						}
						int b;
						for (b=0;b<size;b++)
						{
							if(completo.equals(ruts[b]))
							{
								System.out.print("Ingrese su contraseña: ");
								password=sc.next();
								if (password.equals(passwords[b])) { userMenu(clientTicket,tardeBalance,mañanaBalance,dayBlance,b,names,lNames,ruts,passwords,balances,status,s1M,s1T,s2M,s2T,s3M,s3T,moviesMa,movieList,typeList,incomeList,sizeMovies,totalTick,sc);break;
								}else { System.out.println("--------------------");System.out.println("contraseña icorrecta");break; }
							}
						}
						if(b==size){System.out.println("--------------------");System.out.println("Usuario no registrado, favor de registrarse");break;}
					}
				option="";
				break;
			case "b":
				System.out.println("--------------------");System.out.println("Menu de Registro");System.out.println("--------------------");
				System.out.print("Ingrese su nombre: ");
				String temporalName=sc2.next();
				System.out.print("Ingrese su apellido: ");
				String temporalLname=sc2.next();
				String temporalPassword="";
				System.out.print("Ingrese su rut: ");
				String temporalRut=sc.next();
				String[] parts = temporalRut.split("");
				String completo="";
				for (int a=0;a<parts.length;a++)
				{
					if(!parts[a].equals(".") && !parts[a].equals("-"))
					{
						completo = completo +parts[a];
					}
				}
				while (!authenticateID(completo))
				{
					System.out.println("--------------------");System.out.println("**Rut incorrecto**");System.out.println("Esciba Salir para volver al menú");System.out.println("--------------------");
					System.out.print("Ingrese su rut o Salir: ");
					temporalRut=sc.next();
					if (temporalRut.toLowerCase().equals("salir")) {break;}
				}
				if (!temporalRut.toLowerCase().equals("salir"))
				{
					System.out.print("Ingrese su contaseña: ");
					temporalPassword=sc.next();
					names[size]=temporalName;
					lNames[size]=temporalLname;
					ruts[size]=completo;
					passwords[size]=temporalPassword;
					System.out.print("Ingrese dinero a su cartera, ¿con cuanto desea iniciar?: ");
					balances[size]=sc.nextInt();
					
					System.out.println("Usuario registrado con exito, puede iniciar sesion");
					size++;
				}
				break;
			case "c":
				closeSystem(size,names,lNames,ruts,passwords,balances,status,movieList,typeList,incomeList,sizeMovies,schedule);
				cycle=false;
				break;
			}
			
		}
		sc2.close();
	}
	
	/*
	 * Interface to enter data for the user.
	 */
	public static void userMenu(String[] clientTicket,int[]tardeBalance,int[]mañanaBalance,int[]dayBlance,int position,String[] names,String[] lNames,String[] ruts,String[] passwords,int[] balances,boolean[] status,String[][] s1M,String[][] s1T,String[][] s2M,String[][] s2T,String[][] s3M,String[][] s3T,String[][] moviesMa,String[] movieList,String[] typeList,int[] incomeList,int sizeMovies,String[] totalTick,Scanner sc)
	{
		boolean cycle=true;
		int var=0;
		int sum=0;
		String icomeVar="";
		int temporalPrice=0;
		boolean seat=false;
		String[][] matrix=new String[0][0];
		/*
		 * @param abcList is a vector that stores the vertical numbering of the rows
		 */
		String[] abcList= {"A","B","C","D","E","F","G","H","I","J"};
		clientTicket[0]="pa";
		String var2="";
		
		while(cycle)
		{
			System.out.println("Bienvenido al menu de usuario"+" "+names[position]+" "+lNames[position]);
			System.out.println("a)Comprar entrada ");
			System.out.println("b)Información de usuario");
			System.out.println("c)Devolución de entrada");
			System.out.println("d)Cartelera");
			System.out.println("e)Salir");
			System.out.println("--------------------");
			System.out.print("Ingrese operación a realizar: ");
			String option=sc.next().toLowerCase();
			/*
			 * @param option select data
			 */
			switch (option)
			{
			case "a":
				sum=buyTicket(tardeBalance,mañanaBalance,dayBlance,position,ruts,balances,status,s1M,s1T,s2M,s2T,s3M,s3T,moviesMa,typeList,incomeList,sizeMovies,movieList,clientTicket,abcList,totalTick,sc);
				balances[position]-=sum;
				break;
			case "b":
				System.out.println("User info");
				System.out.println("Nombre: "+names[position]+". Apellido: "+lNames[position]+". Saldo: "+balances[position]);
				if(!clientTicket[0].equals("pa"))
				{
					for(int a=1;a<=Integer.parseInt(clientTicket[0]);a++)
					{
						if (a==1) { System.out.println("Pelicula: "+clientTicket[1]); }
						else { 
							if(a==2) { String[] part=clientTicket[2].split(",");System.out.println("Horario: "+part[1]); }
							else{System.out.println("Asiento "+(a-2)+" "+clientTicket[a]); }
							}
					}
				}
				System.out.println(" ");
				
				break;
			case "c":
				int e=0;
				
				String[] part=clientTicket[2].split(",");
				if(!clientTicket[0].equals("pa"))
				{
					for(e=0;e<movieList.length;e++)
					{
						if (movieList[e].equals(clientTicket[1]))
						{
							break;
						}
					}
					System.out.print("Cuantas entradas desea devolver: ");
					var=sc.nextInt();
					if (part[1].equals("mañana"))
					{
						icomeVar="mañana";
						if(part[0].equals("1"))
						{
							matrix=s1M;
						}
						if(part[0].equals("2"))
						{
							matrix=s2M;
						}
						if(part[0].equals("1"))
						{
							matrix=s3M;
						}
					}
					else
					{
						icomeVar="tarde";
						if(part[0].equals("1"))
						{
							matrix=s1T;
						}
						if(part[0].equals("2"))
						{
							matrix=s2T;
						}
						if(part[0].equals("3"))
						{
							matrix=s3T;
						}
					}
					temporalPrice=0;
					for (int a=0;a<sizeMovies;a++)
					{
						if(movieList[a].equals(clientTicket[1]))
						{
							if(typeList[a].equals("estreno")) { temporalPrice=5500; }
							else{temporalPrice=4000;}
						}
					}
					if(var==(Integer.parseInt(clientTicket[0])-2))
					{
						
						for (int a=3;a<var;a++)
						{
							seat=seatAvailable(" ",clientTicket[a],matrix,abcList);
						}
						balances[position]+=(var*temporalPrice)*0.80;
						System.out.println("Fueron devueltos: "+(var*temporalPrice)*0.80+"$");
						dayBlance[e]-=(var*temporalPrice)*0.80;
						incomeList[e]-=(var*temporalPrice)*0.80;
						if (icomeVar.equals("mañana")) { mañanaBalance[e]-=(var*temporalPrice)*0.80;; }
						if (icomeVar.equals("tarde")) { tardeBalance[e]-=(var*temporalPrice)*0.80;; }
						clientTicket[0]="pa";
					}
					else
					{
						if((var<=Integer.parseInt(clientTicket[0])-2))
						{
							int c=0;
							for (int a=0;a<var;a++)
							{
								boolean cycle2=true;
								int b;
								c+=1;
								while(cycle2)
								{
									System.out.print("Ingrese entrada numero "+(c)+" a devolver: ");
									var2=sc.next();
									for (b=0;b<clientTicket.length;b++)
									{
										if(clientTicket[b].equals(var2))
										{
											seat=seatAvailable(" ",var2,matrix,abcList);
											for(int i=b;i<Integer.parseInt(clientTicket[0]);i++)
											{
												String aux=clientTicket[b+1];
												clientTicket[b]=aux;
											}
											System.out.println("Entrada "+var2+" devuenla");
											clientTicket[0]=String.valueOf(Integer.parseInt(clientTicket[0])-1);
											cycle2=false;
											break;
										}
									}
									if (b==clientTicket.length)
									{
										System.out.println("No encontrado");
									}
									
								}
							}
							if (icomeVar.equals("mañana")) { mañanaBalance[e]-=(var*temporalPrice)*0.80;; }
							if (icomeVar.equals("tarde")) { tardeBalance[e]-=(var*temporalPrice)*0.80;; }
							dayBlance[e]-=(var*temporalPrice)*0.80;
							balances[position]+=(var*temporalPrice)*0.80;
							System.out.println("Fueron devueltos: "+(var*temporalPrice)*0.80+"$");
							incomeList[e]-=(var*temporalPrice)*0.80;
						}else { System.out.println("Supera la cantidad de entradas en posesión"); }
					}
				}else { System.out.println("No hay entradas para devolver"); }
				
				break;
			case "d":
				for(int i=0;i<sizeMovies;i++)
				{
					System.out.println("Pelicula: "+movieList[i]);
					for(int a=0;a<moviesMa.length;a++)
					{
						for(int b=0;b<moviesMa[0].length;b++)
						{
							if(moviesMa[a][b].equals(movieList[i]))
							{
								System.out.print("sala "+(a+1)+" ");
								if(b==0)
								{
									System.out.println("en la Mañana");
								}
								if(b==1)
								{
									System.out.println("en la Tarde");
								}
							}
							
						}
					}
				}
				break;
			case "e":
				cycle=false;
				break;
			}
		}
	}
	
	/*
	 * buyTicket this interface is in charge of selecting and updating the data
	 */
	public static int buyTicket(int[]tardeBalance,int[]mañanaBalance,int[]dayBlance,int position,String[] ruts,int[] balances,boolean[] status,String[][] s1M,String[][] s1T,String[][] s2M,String[][] s2T,String[][] s3M,String[][] s3T,String[][] moviesMa,String[] typeList,int[] incomeList,int sizeMovies,String[] movieList,String[]clientTicket,String [] abcList,String[] totalTick,Scanner sc)
	{
		String[][] temporalMa=new String[0][0];
		String movie="";
		int var;
		/*
		 * @param e saves the place of the selected movie, in the list
		 */
		int e;
		String icomeVar="";
		int temporalPrice = 0;
		int seating=0;
		/*
		 * @param buy is a boolean type and check if a ticket was bought
		 */
		boolean buy=false;
		String seat="";
		String option="";
		String q="";
		String[] dat=new String[0];
		boolean temporaldat=true;
		Scanner sc2=new Scanner(System.in);
		System.out.println("Peliculas disponibles: ");
		System.out.println("• Valor entrada estreno: $5.500");
		System.out.println("• Valor entrada liberada: $4.000");
		for(int a=0;a<sizeMovies;a++)
		{
			System.out.println((a+1)+") "+movieList[a]+"-- "+typeList[a]);
		}
		System.out.print("Ingrese nombre de la pelicula: ");
		movie=sc2.nextLine();
		
		for(e=0;e<sizeMovies;e++)
		{
			if(movieList[e].equals(movie)) { break; }
		}
		if(sizeMovies==e) {System.out.println("Pelicula no encontrada");return 0;}
		else 
		{ 
			clientTicket[1]=movie;
			for(int a=0;a<moviesMa.length;a++)
			{
				for(int b=0;b<moviesMa[0].length;b++)
				{
					if(moviesMa[a][b].equals(movie))
					{
						if(b==0) { System.out.println("Sala "+(a+1)+"- en la Mañana "); }
						else 
						{ 
							if(b==1) {System.out.println("Sala "+(a+1)+"- en la Tarde ");} 
						}
					}
				}
			}
		}
		try
		{
			/*
			 * while(temporaldat) check formats
			 */
			while(temporaldat)
			{
				
				System.out.print("Eliga la función en el formato(1,Mañana): ");
				option=sc2.nextLine().toLowerCase();
				dat = option.split(",");
				if(dat[1].equals("mañana")) { var=0; }else { if(dat[1].equals("tarde")) { var=1; }else { var=-1; } }
				
				if(var!=-1 && (Integer.parseInt(dat[0])<0) || (Integer.parseInt(dat[0])>3) || (dat[1].equals("mañana")) || (dat[1].equals("tarde")))
				{
					if(moviesMa[Integer.parseInt(dat[0])-1][var].equals(movie))	
					{
						temporaldat=false;
						break;
					}
				}
				System.out.println("Función no encontrada o formato incorrecto");
			}
		}catch (java.lang.ArrayIndexOutOfBoundsException ex) {System.out.println("--------------------");System.out.println("Funcion format invalid");System.out.println("--------------------");return 0;}
		
		
		clientTicket[2]=option;
		System.out.println("Asientos disponibles");
		
		if (dat[0].equals("1"))
		{
			if (dat[1].equals("mañana"))
			{
				temporalMa=s1M;
				icomeVar="mañana";
				for(int a=0;a<s1M.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s1M[0].length;b++)
					{
						if(s1M[a][b].equals(" ") && !s1M[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
			else
			{
				temporalMa=s1T;
				icomeVar="tarde";
				for(int a=0;a<s1T.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s1T[0].length;b++)
					{
						if(s1T[a][b].equals(" ") && !s1T[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
		}
		if (dat[0].equals("2"))
		{
			if (dat[1].equals("mañana"))
			{
				icomeVar="mañana";
				temporalMa=s2M;
				for(int a=0;a<s2M.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s2M[0].length;b++)
					{
						if(s2M[a][b].equals(" ") && !s2M[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
			else
			{
				icomeVar="tarde";
				temporalMa=s2T;
				for(int a=0;a<s2T.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s2T[0].length;b++)
					{
						if(s2T[a][b].equals(" ") && !s2T[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
		}
		if (dat[0].equals("3"))
		{
			if (dat[1].equals("mañana"))
			{
				icomeVar="mañana";
				temporalMa=s3M;
				for(int a=0;a<s3M.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s3M[0].length;b++)
					{
						if(s3M[a][b].equals(" ") && !s3M[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
			else
			{
				icomeVar="tarde";
				temporalMa=s3T;
				for(int a=0;a<s3T.length;a++)
				{System.out.println(" ");
					for(int b=0;b<s3T[0].length;b++)
					{
						if(s3T[a][b].equals(" ") && !s3T[a][b].equals("0")) 
						{
							System.out.print(abcList[a]+b+" ");
						}
					}
				}
			}
		}
		if (typeList[e].equals("estreno"))
		{
			temporalPrice=5500;
		}
		else { temporalPrice=4000; }
		System.out.println(" ");
		System.out.print("Cuantos asientos desea comprar: ");
		try
		{
			seating = sc2.nextInt();
		}catch(java.util.InputMismatchException m){System.out.println("--------------------");System.out.println("Error Invalid Number");System.out.println("--------------------");return 0;}
		
		int pos=seating+2;
		clientTicket[0]=String.valueOf(pos);
		pos=2;
		if(balances[position]>=seating*temporalPrice)
		{
				for (int a=0;a<seating;a++)
				{
					System.out.print("Ingrese el asiento "+(a+1)+": ");
					seat=sc2.next();
					buy=seatAvailable(ruts[position],seat,temporalMa,abcList);
					while(!buy)
						{
						System.out.println("Asiento No listado o no disponible");
						System.out.print("Ingrese el asiento "+(a+1)+": ");
						seat=sc2.next();
						buy=seatAvailable(ruts[position],seat,temporalMa,abcList);
						}
					totalTick[pos]+=seat+",";
					clientTicket[pos+=1]=seat;
				}
				System.out.print("El precio de las entradas es: "+seating*temporalPrice);
				System.out.print("¿Quiere confirmar la compra?(Si-No): ");
				q =sc2.next().toLowerCase();
				if(q.equals("si"))
				{ 
					if (icomeVar.equals("mañana")) { mañanaBalance[e]+=seating*temporalPrice; }
					if (icomeVar.equals("tarde")) { tardeBalance[e]+=seating*temporalPrice; }
					dayBlance[e]+=seating*temporalPrice;
					incomeList[e]+=seating*temporalPrice;
					return seating*temporalPrice;
				}
		}
		else
		{
			System.out.println("Saldo insuficiente");
			System.out.print("¿Quiere Recargar dinero a la cuenta?(Si-No): ");
			q =sc2.next().toLowerCase();
			if(q.equals("si"))
			{ 
				System.out.print("ingrese saldo a recargar: ");
				int price=sc2.nextInt();
				System.out.println("Carga relizada con exito su nuevo saldo es: "+(balances[position]+price));
				return -(price);
			}
		}
		return 0;
	}
	/*
	 * check and modify the available seats according to the criteria in which the function was called
	 */
	public static boolean seatAvailable(String rut,String seat,String[][] matrix,String[] abcList)
	{
		String[] parts=seat.split("");
		int horizontal=0;
		int vertical=0;
		if(seat.length()>1)
		{
			for (int a=0;a<abcList.length;a++)
			{
				
				if(abcList[a].equals(parts[0]))
				{
					horizontal=a;
					if(parts.length==3) { vertical=Integer.parseInt(parts[1]+parts[2]); }else { vertical=Integer.parseInt(parts[1]); }
				}
			}
			if(vertical<30&&vertical>=0)
			{
				if(horizontal<=3&&vertical==5||horizontal<=3&&vertical==24)
				{
					if(vertical==5&&(matrix[horizontal][vertical].equals(" ")&&matrix[horizontal][vertical+1].equals(" ")))
					{
						matrix[horizontal][vertical]=rut;
						return true;
					}
					else
					{
						if(vertical==24&&(matrix[horizontal][vertical].equals(" ")&&matrix[horizontal][vertical-1].equals(" ")))
						{
							matrix[horizontal][vertical]=rut;
							return true;
						}
						return false;
					}
					
				}else 
				{
					if(vertical==0||vertical==29)
					{
						if(vertical==0&&(matrix[horizontal][vertical].equals(" ")&&matrix[horizontal][vertical+1].equals(" ")))
						{
							matrix[horizontal][vertical]=rut;
							return true;
						}
						else
						{
							if(vertical==29&&(matrix[horizontal][vertical].equals(" ")&&matrix[horizontal][vertical-1].equals(" ")))
							{
								matrix[horizontal][vertical]=rut;
								return true;
							}
							return false;
						}
					}
					else
					{
						if(matrix[horizontal][vertical].equals(" ")&&matrix[horizontal][vertical-1].equals(" ")&&matrix[horizontal][vertical+1].equals(" "))
						{
							matrix[horizontal][vertical]=rut;
							return true;
						}
						else
						{
							return false;
						}
					}
				}
			}else { return false; }
		}else { return false; }
	}
	/*
	 * this console is to review the data of the day
	 */
	public static void adminMenu(String[]clientTicket,int[]tardeBalance,int[]mañanaBalance,int[] dayBlance,String[] totaltick,int size,String []names,String []lNames,String []ruts,String []passwords,int []balances,boolean[]status,String[] movieList,String[] typeList,int[] incomeList,int sizeMovies,String[] schedule)
	{
		Scanner sc2=new Scanner(System.in);
		boolean cycle=true;
		String option=" ";
		while(cycle)
		{
			System.out.println("Bienvenido al menu del admin");
			System.out.println("a)Taquilla");
			System.out.println("b)Client info");
			System.out.println("c)Salir");
			System.out.print("Seleccione opcion: ");
			option=sc2.next().toLowerCase();
			switch (option)
			{
			
			case "a":
				for(int a=0;a<sizeMovies;a++)
				{
					System.out.println("pelicula: "+movieList[a]+" recaudado total: "+incomeList[a]+"$ recaudado durante el dia: "+dayBlance[a]+"$");
				}
				int sum=0;
				for(int a=0;a<mañanaBalance.length;a++)
				{
					sum+=mañanaBalance[a];
				}
				System.out.println("Total recaudado en la mañana: "+sum+"$");
				
				sum=0;
				for(int a=0;a<tardeBalance.length;a++)
				{
					sum+=tardeBalance[a];
				}
				System.out.println("Total recaudado en la tarde: "+sum+"$");
				break;
			case "b":
				System.out.print("Ingrese Rut a consultar: ");
				String rut=sc2.next();
				boolean cycle2=true;
				while(cycle2)
					{
						int a=0;
						for(a=0;a<size;a++)
						{
							if(ruts[a].equals(rut))
							{
								break;
							}
						}
						if(a==size) {System.out.print("Usuario no encontado");}
						else
						{
							System.out.println("Usuario: "+names[a]+" "+lNames[a]);
							cycle2=false;
						}
					}
				if(!clientTicket[0].equals("pa"))
				{
					for(int a=1;a<=Integer.parseInt(clientTicket[0]);a++)
					{
						if (a==1) { System.out.println("Pelicula: "+clientTicket[1]); }
						else { 
							if(a==2) { String[] part=clientTicket[2].split(",");System.out.println("Horario: "+part[1]); }
							else{System.out.println("Asiento "+(a-2)+" "+clientTicket[a]); }
							}
					}
				}
				break;
			case "c":
				cycle=false;
				break;
			}
		}
		
	}
	/*
	 * ShutDown the system and save the day's data
	 */
	public static void closeSystem(int size,String []names,String []lNames,String []ruts,String []passwords,int []balances,boolean[]status,String[] movieList,String[] typeList,int[] incomeList,int sizeMovies,String[] schedule) throws IOException
	{
		
		FileWriter clients = new FileWriter("clientes.txt");
		PrintWriter pw = new PrintWriter(clients);
		for (int i = 0; i<size; i++)
		{
			pw.println(names[i]+","+lNames[i]+","+ruts[i]+","+passwords[i]+","+balances[i]);
		}
		
		FileWriter statusF = new FileWriter("status.txt");
		PrintWriter pw2 = new PrintWriter(statusF);
		for (int i = 0; i<size; i++)
		{
			if (status[i]&&true)
			{
				pw2.println(ruts[i]+",HABILITADO");
			}
			else
			{
				pw2.println(ruts[i]+",NO HABILITADO");
			}
			
		}
		
		FileWriter peliculas = new FileWriter("peliculas.txt");
		PrintWriter pw3 = new PrintWriter(peliculas);
		for (int i = 0; i<sizeMovies; i++)
		{
			pw3.println(movieList[i]+","+typeList[i]+","+incomeList[i]+","+schedule[i]);
		}
		System.out.println("Cerrando el sistema...");
		clients.close();
		statusF.close();
		peliculas.close();
		
	}
	/*
	 * authenticateID(String id) enter a Rut in any format, decomposing it, and returning a boolean
	 */
	public static boolean authenticateID(String id)
	{
		try {
			int max=0;
			String verifi="";
			double[] array= {2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7};
			String[] part=id.split("");
			double [] data= new double[id.length()];
			for  (int a=0;a<id.length();a++) {
				if (!part[a].equals("-") && !part[a].equals(".") && !part[a].equals("K")) {
					data[max]= Double.parseDouble(part[a]);
					max ++;
				}
				if (part[a].equals("K")) {
					max++;
				}
			}
			max-=1;
			verifi=part[id.length()-1];
			double aux;
		    for (int i = 0; i < (max)/2; i++) {
		        aux = data[i];
		        data[i] = data[max-1-i];
		        data[max-1-i] = aux;
		    }
			int sum=0;
			for (int a=0;a<max;a++) {
				sum+=data[a]*array[a];
			}
			int G=sum;
			sum = (sum/11);
			sum= (sum*11)-G;
			sum=11+sum;
			switch (sum){
			case 11:
				if (verifi.equals("0")) {
					return true;
				}
				else {
					return false;
				}
			case 10:
				if (verifi.equals("K")) {
					return true;
				}
				
				else {
					return false;
				}
			}
			if (Integer.toString(sum).equals(verifi)) {
				return true;
			}
			else {
				return false;
			}
		}catch (Exception ex) {System.out.println("--------------------");System.out.println("Rut format invalid");return false;}
	}
}