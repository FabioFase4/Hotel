package hotel;
import java.util.Scanner;
import java.util.Arrays;

public class Hotel 
{    
    private static final int maxQuartos = 100;
    private static final int maxProdutos = 4;
    private static final int maxQuantProduto = 1000000;
    private static final int maxDiarias = 100000;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) 
    {
        String nomeHospede[] = new String[maxQuartos];
        int quartoHospede[] = new int[maxQuartos];
        boolean statusQuarto[] = new boolean[maxQuartos];
        Arrays.fill(statusQuarto, true);
        /*
            true = não ocupado
            false = ocupado
        */
        
        int consumoFrigobar[][] = new int[maxQuartos][maxProdutos];
        
        int opcao, quarto, produto, quantidade, diarias, novoQuarto, escolha;
        String nome = null, nomeHotel = "Chaves";
        boolean stop = false, error = false;
        
        System.out.printf("Seja Bem Vindo ao Hotel %s\n", nomeHotel);
        System.out.printf("Aqui, sua Alegria e o nosso Presente! \n\n");
        System.out.printf("Regras: ");
        System.out.printf("Bom Tratamento com os Recepcionistas \n");
        System.out.printf("Tenha Bons Modos com os Outros Hospedes\n");
        System.out.printf("O Frigobar e Sensivel a Mudancas, Cuidado!\n");
        System.out.print ("\nVamos em Frente? ");
        scanner.nextLine();
        
        while (true)
        {
            error = false;
            
            if (stop)
                break;
            
            menuUsuario();
            System.out.print("Digite: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
                        
            switch (opcao)
            {
                case 1:
                    System.out.println("\n\n\nHora de Reservar um Quarto!");
                    System.out.print("Nome do Hospede: ");
                    nome = scanner.nextLine();
                                
                    if (nome.isEmpty())
                    {
                        throwException("Nome Vazio não é Disponivel para esse Sistema!");
                        error = true;
                        break;
                    }

                    System.out.print("Numero do Quarto: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (quarto < 1 || quarto > maxQuartos || statusQuarto[quarto-1] == false)
                    {
                        throwException("Quarto Invalido ou Ocupado!");
                        error = true;
                        break;
                    }
                                
                    reservarQuarto(quarto-1, statusQuarto, nome, nomeHospede, quartoHospede);                                  
                    break;
                            
                case 2:
                    System.out.println("\n\n\nHora de Cancelar uma Reserva!");
                    System.out.print("Numero do Quarto: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                                
                    if (quarto < 1 || quarto > maxQuartos || statusQuarto[quarto-1] == true)
                    {
                        throwException("Numero do Quarto Invalido!");
                        error = true;
                        break;
                    }
       
                    cancelarReserva(quarto-1, nomeHospede, quartoHospede, statusQuarto);
                    zerarFrigobar(quarto-1, consumoFrigobar);
                    break;
                                
                case 3:
                    System.out.print("\n\nNumero do Quarto: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                                
                    if (quarto < 1 || quarto > maxQuartos || statusQuarto[quarto-1] == true)
                    {
                        throwException(" Quarto Invalido!");
                        error = true;
                        break;
                    }
                            
                    produtos();
                    System.out.print("\nNumero do Produto Consumido: ");
                    produto = scanner.nextInt();
                    scanner.nextLine();
                                   
                    if (produto < 1 || produto > 4)
                    {
                        throwException("Produto Invalido!");
                        error = true;
                        break;
                    }
                                  
                    System.out.print("Quantidade Consumida: ");
                    quantidade = scanner.nextInt();
                    scanner.nextLine();
                                 
                    if (quantidade < 1 || quantidade > maxQuantProduto)
                    {
                        throwException("Quantidade Invalida!");
                        error = true;
                        break;
                    }
                                    
                    registrarConsumo(quarto-1, produto-1, quantidade, consumoFrigobar);
                    System.out.println(">> Registrado no Frigobar");
                    break;
                                
                case 4:
                    System.out.println("\n\n\nHora do Check-Out!");
                    System.out.print("Numero do Quarto: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                              
                    if (quarto < 1 || quarto > maxQuartos || statusQuarto[quarto-1] == true)
                    {
                        throwException("Quarto Invalido!");
                        error = true;
                        break;
                    }
                                
                    System.out.print("Numero de Diarias: ");
                    diarias = scanner.nextInt();
                    scanner.nextLine();
                               
                    if (diarias < 1 || diarias > maxDiarias)
                    {
                        throwException("Quantidade de Diarias Invalida");
                        error = true;
                        break;
                    }
                              
                    checkOut(quarto-1, statusQuarto, nomeHospede, quartoHospede, consumoFrigobar, diarias);
                    break;
                    
                case 5:
                    System.out.println("\n\n\nHora de Visualizar todos os Quartos Disponíveis!");
                    listarReservas(statusQuarto, nomeHospede);
                    break;
                            
                case 6:
                    System.out.println("\n\n\nHora de Consultar um Hospede!");
                    System.out.print("Numero do Quarto: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                                
                    if (quarto < 1 || quarto > maxQuartos)
                    {
                        throwException("Quarto Invalido");
                        error = true;
                        break;
                    }
                    if (statusQuarto[quarto-1] == true)
                    {
                        throwException("Quarto Vazio!");
                        error = true;
                        break;
                    }
                                
                    consultarHospede(quarto-1, nomeHospede, quartoHospede);
                    break;
                                
                case 7:
                    System.out.println("\n\n\nHora de Alterar um Hospede!");
                    System.out.print("Numero do Quarto Atual: ");
                    quarto = scanner.nextInt();
                    scanner.nextLine();
                              
                    if (quarto < 1 || quarto > maxQuartos)
                    {
                        throwException("Quarto Invalido");
                        error = true;
                        break;
                    }
                    String nomeTemporario = nomeHospede[quarto-1];
                                
                    System.out.println("Hospede: " + nomeTemporario);
                    System.out.print("Deseja mudar [1] Nome, [2] Numero do Quarto, [3] Ambos: ");
                    escolha = scanner.nextInt();
                    scanner.nextLine();
                                
                    if (escolha < 1 || escolha > 3)
                    {
                        throwException("Escolha Invalida!");
                        error = true;
                        break;
                    }
                              
                    if (escolha == 1 || escolha == 3)
                    {
                        if (statusQuarto[quarto-1] == true)
                        {
                            throwException("Quarto Vazio!");
                            error = true;
                            break;
                        }
                        
                        System.out.print("Novo nome: ");
                        nome = scanner.nextLine();
                        nomeHospede[quarto-1] = nome;                   
                    }
                                
                    if (escolha == 2 || escolha == 3)
                    {
                        System.out.print("Novo Numero do Quarto: ");
                        novoQuarto = scanner.nextInt();
                        scanner.nextLine();
                                   
                        if (statusQuarto[novoQuarto-1] == false)
                        {
                            throwException("Quarto Ja Ocupado!");
                            error = true;
                            break;
                        }
                        
                        if (novoQuarto != quarto) 
                        {
                            quartoHospede[quarto-1] = 0;
                            moverFrigobar(quarto-1, novoQuarto-1, consumoFrigobar);
                            cancelarReserva(quarto-1, nomeHospede, quartoHospede, statusQuarto);
                            reservarQuarto(novoQuarto-1, statusQuarto, nome, nomeHospede, quartoHospede);
                        } 
                        else 
                            nomeHospede[quarto-1] = nomeTemporario;
                                        
                        break;
                    }
                    
                    break;
                        
                default:
                    stop = true;
                    break;
            }
            
            if (!error)
                System.out.println("Sucesso!");
            
            if (!stop)
            {
                System.out.print ("\nVamos em Frente? ");
                scanner.nextLine();
            }           
        }
        System.out.println("\n\n>> Obrigado por Contribuir com Nosso Hotel! ");
        System.out.println(">> Somos Gratos pelo seu Bem :)");
    }
    
    private static void throwException (String text)
    {
        System.err.println("Erro: " + text);
    }
    
    private static void produtos ()
    {
        System.out.println("\n\nOpcoes: ");
        System.out.println("1- Agua");
        System.out.println("2- Refrigetante");
        System.out.println("3- Suco");
        System.out.println("4- Chocolate");
        System.out.println("Outro- Nenhum");
    }
    
    private static void menuUsuario ()
    {
        System.out.println("\n\nOpcoes: ");
        System.out.println("1- Reserva de Quarto");
        System.out.println("2- Cancelar Reserva");
        System.out.println("3- Registrar Consumo do Frigobar");
        System.out.println("4- Check-out");
        System.out.println("5- Listar Reservas");
        System.out.println("6- Consultar Hospede");
        System.out.println("7- Editar Hospede");
        System.out.println("Outro- Sair");
    }
    
    private static void reservarQuarto (int quarto, boolean statusQuarto[], String nome, String nomeHospede[], int quartoHospede[])
    {
        nomeHospede[quarto] = nome;
        quartoHospede[quarto] = quarto;
        statusQuarto[quarto] = false;
    }

    private static void cancelarReserva (int quarto, String nomeHospede[], int quartoHospede[], boolean statusQuarto[])
    {
        statusQuarto[quarto] = true;
        quartoHospede[quarto] = 0;
        nomeHospede[quarto] = "";
    }
    
    private static void zerarFrigobar(int quarto, int[][] consumo) 
    {
        for (int j = 0; j < 4; j++) 
            consumo[quarto][j] = 0;
    }
    
    private static void registrarConsumo (int quarto, int produto, int quantidade, int[][] consumo)
    {
        consumo[quarto][produto] += quantidade;
    }
    
    private static double totalFrigobarHospede (int quarto, int[][] consumo)
    {
        double[] precosProdutos = {7.99, 5.99, 6.99, 10.90}; // Água, Refrigerante, Suco, Chocolate
        double totalFrigobar = 0;
        
        for (int j = 0; j < consumo[quarto].length; j++) 
            totalFrigobar += consumo[quarto][j] * precosProdutos[j];
        return totalFrigobar;
    }
    
    private static void checkOut(int quarto, boolean[] statusQuarto, String nomeHospede[], int quartoHospede[], int[][] consumo, int diarias)
    {
        double valorDiaria = 150.00;
        double totalHospedagem = diarias * valorDiaria;
        double totalFrigobar = totalFrigobarHospede (quarto, consumo);
            
        System.out.println("\n========= CONTA FINAL ==========");
        System.out.println("Hospede: " + nomeHospede[quarto]);
        System.out.printf("Total Diarias: R$ %.2f\n", totalHospedagem);
        System.out.printf("Total Frigobar: R$ %.2f\n", totalFrigobar);
        System.out.printf("TOTAL A PAGAR: R$ %.2f\n", (totalHospedagem + totalFrigobar));
        System.out.println("================================\n");
            
        statusQuarto[quarto] = true;
        nomeHospede[quarto] = "";
        quartoHospede[quarto] = 0;
        zerarFrigobar(quarto, consumo);      
    }
    
    private static void listarReservas(boolean[] statusQuarto, String nomeHospede[])
    {
        int vazios = 0;
        System.out.println("\n======= Todas as Reservas =======");
        System.out.println("Quarto x Hospede");
        for (int quarto = 0; quarto < maxQuartos; quarto++)
        {
            if (statusQuarto[quarto] == true)
            {
                vazios++;
                continue;
            }
            System.out.printf("%d x %s\n", quarto+1, nomeHospede[quarto]);
        }
        if (vazios == maxQuartos)
        {
            System.out.println("Nao hao Quartos Ocupados!");
        }
    }
    
    private static void consultarHospede(int quarto, String nomeHospede[], int quartoHospede[])
    {
        System.out.println("\n======= Dados do Hospede =======");
        System.out.printf("Nome: %s\n", nomeHospede[quarto]);
        System.out.printf("Numero da Reserva: %d\n", quartoHospede[quarto]+1);
        System.out.printf("Numero do Quarto: %d\n", quartoHospede[quarto]+1);
    }
    
    private static void moverFrigobar (int antigo, int novo, int consumo[][])
    {
        for (int i = 0; i < 4; i++) 
        {
            consumo[novo][i] = consumo[antigo][i];
            consumo[antigo][i] = 0;
        }
    }
}
