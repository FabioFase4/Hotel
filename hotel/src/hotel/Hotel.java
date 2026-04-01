package hotel;
import java.util.Scanner;
import java.util.Arrays;

//Arquivo Principal
public class Hotel 
{
    //Lançar "Exceções" de Erro
    private static void throwException (String text)
    {
        System.err.println("Erro: " + text);
    }
    
    //Verificar se um Quarto está com Status de Disponível
    private static boolean isEmptyQuarto (int quarto, boolean statusQuarto[])
    {
        return statusQuarto[quarto] == true;
    }
    
    //Mostrar Opções de Usuário
    private static void users ()
    {
        System.out.println("\n\nOpcoes: ");
        System.out.println("1- Hospede");
        System.out.println("2- Recepcionista");
        System.out.println("Outro- Sair");
    }
    
    //Mostrar Opções de Produtos para Frigobar
    private static void produtos ()
    {
        System.out.println("\n\nOpcoes: ");
        System.out.println("1- Agua");
        System.out.println("2- Refrigetante");
        System.out.println("3- Suco");
        System.out.println("4- Chocolate");
        System.out.println("Outro- Nenhum");
    }
    
    //Mostrar Opções de Hóspede
    private static void menuHospede ()
    {
        System.out.println("\n\nOpcoes: ");
        System.out.println("1- Reserva de Quarto");
        System.out.println("2- Cancelar Reserva");
        System.out.println("3- Registrar Consumo do Frigobar");
        System.out.println("4- Check-out");
        System.out.println("Outro- Sair");
    }
    
    //Mostrar Opções de Recepcionista
    private static void menuRecepcionista ()
    {
        System.out.println("Opcoes: ");
        System.out.println("1- Listar Reservas");
        System.out.println("2- Consultar Hospede");
        System.out.println("3- Editar Hospede");
        System.out.println("Outro- Sair");
    }
    
    //Reserva de Quarto
    private static void reservarQuarto (int quarto, boolean statusQuarto[], String nome, String nomeHospede[], int reservaHospede[])
    {
        if (statusQuarto[quarto] == true)
        {
            nomeHospede[quarto] = nome;
            reservaHospede[quarto] = quarto;
        }
    }
    
    //Ocupação de Quarto
    private static void ocuparQuarto (int quarto, boolean statusQuarto[], int quartoHospede[], int numeroQuarto[])
    {
        statusQuarto[quarto] = false;
        quartoHospede[quarto] = quarto;
        numeroQuarto[quarto] = quarto;
    }
    
    //Desocupação de Quarto
    private static void desocuparQuarto (int quarto, String nomeHospede[], boolean statusQuarto[], int quartoHospede[], int numeroQuarto[])
    {
        statusQuarto[quarto] = true;
        quartoHospede[quarto] = 0;
        numeroQuarto[quarto] = 0;
        nomeHospede[quarto] = "";
    }
    
    //Cancelamento de Reserva
    private static void cancelarReserva (int quarto, String nomeHospede[], int quartoHospede[], boolean statusQuarto[])
    {
        if (statusQuarto[quarto] == false)
        {
            statusQuarto[quarto] = true;
            quartoHospede[quarto] = 0;
            nomeHospede[quarto] = null;
        }
    }
    
    //Zerar o Consumo do Frigobar
    private static void zerarFrigobar(int quarto, int[][] consumo) 
    {
        for (int j = 0; j < 4; j++) 
            consumo[quarto][j] = 0;
    }
    
    //Registro do Consumo no Frigobar
    private static void registrarConsumo (int quarto, int produto, int quantidade, int[][] consumo)
    {
        consumo[quarto][produto] += quantidade;
    }
    
    //Calcular Consumo Total de um Hóspede a Partir do Frigobar
    private static double totalFrigobarHospede (int quarto, int[][] consumo)
    {
        double[] precosProdutos = {7.99, 5.99, 6.99, 10.90}; // Água, Refrigerante, Suco, Chocolate
        double totalFrigobar = 0;
        
        for (int j = 0; j < consumo[quarto].length; j++) 
            totalFrigobar += consumo[quarto][j] * precosProdutos[j];
        return totalFrigobar;
    }
    
    //Check-out de Hóspede
    private static void checkOut(int quarto, boolean[] statusQuarto, String nomeHospede[], int[][] consumo, int diarias)
    {
        if (statusQuarto[quarto] == false)
        {
            double valorDiaria = 150.00;
            double totalHospedagem = diarias * valorDiaria;
            double totalFrigobar = totalFrigobarHospede (quarto, consumo);
            
            System.out.println("\n======= CONTA FINAL =======");
            System.out.println("Hospede: " + nomeHospede[quarto]);
            System.out.printf("Total Diarias: R$ %.2f\n", totalHospedagem);
            System.out.printf("Total Frigobar: R$ %.2f\n", totalFrigobar);
            System.out.printf("TOTAL A PAGAR: R$ %.2f\n", (totalHospedagem + totalFrigobar));
            System.out.println("================================\n");
            
            statusQuarto[quarto] = true;
            nomeHospede[quarto] = "";
            zerarFrigobar(quarto, consumo);
        }
        else
        {
            System.out.println(">> Nenhum Hospede nesse Quarto!");
        }
    }
    
    //Listar Reservas
    private static void listarReservas(boolean[] statusQuarto, String nomeHospede[])
    {
        System.out.println("\n======= Todas as Reservas =======");
        System.out.println("Quarto x Hospede");
        for (int quarto = 0; quarto < statusQuarto.length; quarto++)
        {
            if (statusQuarto[quarto] == false)
                System.out.printf("%d x %s\n", quarto+1, nomeHospede[quarto]);
        }
    }
    
    //Consulta de Hóspedes
    private static void consultarHospede(int quarto, String nomeHospede[], int reservaHospede[], int numeroQuarto[], boolean statusQuarto[])
    {
        System.out.println("\n======= Dados do Hospede =======");
        
        if (statusQuarto[quarto] == true)
        {
            System.out.println("Quarto não Ocupado");
            throwException("Ausencia de Dados do Quarto" + numeroQuarto[quarto]);
        }
        else
        {
            System.out.printf("Nome: %s\n", nomeHospede[quarto]);
            System.out.printf("Numero da Reserva: %d\n", reservaHospede[quarto]+1);
            System.out.printf("Numero do Quarto: %d\n", numeroQuarto[quarto]+1);
        }
    }
    
    //Mover o Consumo do Frigobar por Atualização de Quarto
    private static void moverFrigobar (int antigo, int novo, int consumo[][])
    {
        for (int i = 0; i < 4; i++) 
        {
            consumo[novo][i] = consumo[antigo][i];
            consumo[antigo][i] = 0;
        }
    }
    
    //Método Principal
    //Conexão com Orientação a Objetos
    //Semelhanças ao Reconhecimento de main() da Windows
    public static void main(String[] args) 
    {
        int maxQuartos = 100, maxProdutos = 4, maxQuantProduto = 100000, maxDiarias = 10000;
        Scanner scanner = new Scanner(System.in);
        
        //Hóspedes
        String nomeHospede[] = new String[maxQuartos];
        int reservaHospede[] = new int[maxQuartos];
        int quartoHospede[] = new int[maxQuartos];
        
        //Quartos
        int numeroQuarto[] = new int[maxQuartos];
        boolean statusQuarto[] = new boolean[maxQuartos];
        Arrays.fill(statusQuarto, true);
        /* true = disponível
         * false = ocupado
        */
        
        //Consumo do Frigobar
        int consumoFrigobar[][] = new int[maxQuartos][maxProdutos];
        /* 1. Água -> 7.99
         * 2. Refrigerante -> 5.99
         * 3. Suco -> 6.99
         * 4. Chocolate -> 10.90 
        */
        
        //Outros
        int user, opcao, quarto, produto, quantidade, diarias, novoQuarto, escolha, i;
        String nome, nomeHotel = "Chaves";
        boolean stop = false;
        
        //Boas Vindas!
        System.out.printf("Seja Bem Vindo ao Hotel %s\n", nomeHotel);
        System.out.printf("Aqui, sua Alegria e o nosso Presente! \n\n");
        System.out.printf("Regras: ");
        System.out.printf("Bom Tratamento com os Recepcionistas \n");
        System.out.printf("Tenha Bons Modos com os Outros Hospedes\n");
        System.out.printf("O Frigobar e Sensivel a Mudancas, Cuidado!\n");
        System.out.print ("\nVamos em Frente? ");
        scanner.nextLine();
        
        //Laço de Repetição para Todos os Usuários 
        //Hóspede e Recepcionista
        while (true)
        {
            if (stop)
                break;
            
            stop = false;
            users();
            System.out.print("Usuario: ");
            user = scanner.nextInt();
            
            //Decisão de Usuário
            switch (user)
            {
                //Clinte: Hóspede
                case 1:
                    stop = false;
                    
                    //Loop de Hóspede
                    while (true)
                    {
                        if (stop)
                            break;
                        
                        System.out.println("\n\n\nModo Hospede Ativado");
                        menuHospede();
                        System.out.print("Digite: ");
                        opcao = scanner.nextInt();
                        scanner.nextLine();
                        
                        //Validar Opção
                        if (opcao < 0 || opcao > 4)
                        {
                            throwException("Opcao Invalida");
                            break;
                        }
                        
                        //Decisão de Opção
                        switch (opcao)
                        {
                            //Reservar Quarto
                            case 1:
                                //Entrada de Nome de Hóspede
                                System.out.println("\n\n\nHora de Reservar um Quarto!");
                                System.out.print("Nome do Hospede: ");
                                nome = scanner.nextLine();
                                
                                //Validação do Nome
                                if (nome.isEmpty())
                                {
                                    throwException("Nome Vazio não é Disponivel para esse Sistema!");
                                    break;
                                }
                                
                                //Entrada de Número do Quarto
                                System.out.print("Numero do Quarto: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validação de Número do Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Numero do Quarto Invalido!");
                                    break;
                                }
                                    
                                //Validar Ocupação do Quarto
                                if (!isEmptyQuarto(quarto-1, statusQuarto))
                                {
                                    throwException ("Quarto Ocupado! ");
                                    break;
                                }
                                
                                //Reserva de Quarto
                                reservarQuarto(quarto-1, statusQuarto, nome, nomeHospede, reservaHospede);
                                ocuparQuarto(quarto-1, statusQuarto, quartoHospede, numeroQuarto);
                                System.out.println(">> Quarto Reservado! ");                                    
                                break;
                            
                            //Cancelar Reserva
                            case 2:
                                System.out.println("\n\n\nHora de Cancelar uma Reserva!");
                                System.out.print("Numero do Quarto: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validação de Número do Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Numero do Quarto Invalido!");
                                    break;
                                }
                                
                                //Validar Ocupação do Quarto
                                if (statusQuarto[quarto-1] == true)
                                {
                                    throwException("Quarto Inválido!");
                                    break;
                                }
                                
                                //Cancelar Reserva
                                cancelarReserva(quarto-1, nomeHospede, quartoHospede, statusQuarto);
                                desocuparQuarto(quarto-1, nomeHospede, statusQuarto, quartoHospede, numeroQuarto);
                                zerarFrigobar(quarto-1, consumoFrigobar);
                                System.out.println(">> Reserva Cancelada!");
                                break;
                                
                            //Consumo do Frigobar
                            case 3:
                                produtos();
                                System.out.print("Numero do Quarto: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validar Número do Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Numero do Quarto Invalido!");
                                    break;
                                }
                                
                                //Validar Ocupação do Quarto
                                if (statusQuarto[quarto-1] == true)
                                {
                                    throwException("Quarto Inválido!");
                                    break;
                                }
                                
                                System.out.print("Numero do Produto Consumido: ");
                                produto = scanner.nextInt();
                                scanner.nextLine();
                                    
                                //Validar Número do Produto
                                if (produto < 1 || produto > 4)
                                {
                                    throwException("Produto Invalido!");
                                    break;
                                }
                                    
                                //Entrada da Quantidade Consumida
                                System.out.print("Quantidade Consumida: ");
                                quantidade = scanner.nextInt();
                                scanner.nextLine();
                                    
                                //Validar Quantidade Consumida
                                if (quantidade < 1 || quantidade > maxQuantProduto)
                                {
                                    throwException("Quantidade Invalida!");
                                    break;
                                }
                                    
                                //Atualizar Frigobar
                                registrarConsumo(quarto-1, produto-1, quantidade, consumoFrigobar);
                                System.out.println(">> Registrado no Frigobar");
                                break;
                                
                            //Check-out de Hóspede
                            case 4:
                                System.out.println("\n\n\nHora do Check-Out!");
                                System.out.print("Numero do Quarto: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validar Número do Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Produto Invalido!");
                                    break;
                                }
                                
                                //Validar Ocupação do Quarto
                                if (statusQuarto[quarto-1] == true)
                                {
                                    throwException("Quarto Invalido");
                                    break;
                                }
                                
                                //Entrada da Quantidade de Diárias
                                System.out.print("Numero de Diarias: ");
                                diarias = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validar Quantidade de Diárias
                                if (diarias < 1 || diarias > maxDiarias)
                                {
                                    throwException("Quantidade de Diarias Invalida");
                                    break;
                                }
                                
                                //Check-out de Hóspede
                                checkOut(quarto-1, statusQuarto, nomeHospede, consumoFrigobar, diarias);
                                desocuparQuarto(quarto-1, nomeHospede, statusQuarto, quartoHospede, numeroQuarto);
                                System.out.println(">> Check-Out COncluido");
                                break;

                            default:
                                stop = true;
                                break;
                        } 
                        
                        //Encorajar Usuário
                        System.out.print ("\nVamos em Frente? ");
                        scanner.nextLine();
                    }
                    break;
                    
                case 2:
                    stop = false;
                    
                    //Loop de Recepcionista
                    while (true)
                    {
                        if (stop)
                            break;
                        
                        //Validação de Opção de Recepcionista
                        System.out.println("\n\n\nModo Recepcionista Ativado");
                        menuRecepcionista();
                        System.out.print("Digite: ");
                        opcao = scanner.nextInt();
                        scanner.nextLine();
                        
                        //Validação da Opção
                        if (opcao < 1 || opcao > 4)
                        {
                            throwException("Opcao Invalida");
                            break;
                        }
                        
                        //Escolha da Opção
                        switch (opcao)
                        {
                            //Listar Reservas
                            case 1:
                                System.out.println("\n\n\nHora de Visualizar todos os Quartos Disponíveis!");
                                listarReservas(statusQuarto, nomeHospede);
                                break;
                            
                            //Consultar Hóspede
                            case 2:
                                //Entrada de Número do Quarto
                                System.out.println("\n\n\nHora de Consultar um Hospede!");
                                System.out.print("Numero do Quarto: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validação de Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Quarto Invalido");
                                    break;
                                }
                                
                                //Consultar Hóspede
                                consultarHospede(quarto-1, nomeHospede, reservaHospede, numeroQuarto, statusQuarto);
                                break;
                                
                            //Edição de Hóspede
                            case 3:
                                System.out.println("\n\n\nHora de Alterar um Hospede!");
                                System.out.print("Numero do Quarto Atual: ");
                                quarto = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validação de Quarto
                                if (quarto < 1 || quarto > maxQuartos)
                                {
                                    throwException("Quarto Invalido");
                                    break;
                                }
                                
                                //Entrada de Opção de Atualização
                                System.out.println("Hospede: " + nomeHospede[quarto-1]);
                                System.out.print("Deseja mudar [1] Nome, [2] Numero do Quarto, [3] Ambos: ");
                                escolha = scanner.nextInt();
                                scanner.nextLine();
                                
                                //Validação da Opção I
                                if (escolha < 1 || escolha > 3)
                                {
                                    throwException("Escolha Invalida!");
                                    break;
                                }
                                
                                //Validação da Opção II
                                if (escolha == 1 || escolha == 3)
                                {
                                    System.out.print("Novo nome: ");
                                    nome = scanner.nextLine();
                                    nomeHospede[quarto-1] = nome;
                                }
                                
                                //Validação da Opção III
                                if (escolha == 2 || escolha == 3)
                                {
                                    //Entrada do Novo Número do Quarto
                                    System.out.print("Novo Numero do Quarto: ");
                                    novoQuarto = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    //Validar Disponibilidade de Quarto
                                    if (statusQuarto[novoQuarto-1] == true)
                                    {
                                        throwException("Quarto Já Ocupado!");
                                        break;
                                    }
                                    
                                    //Edição de Hóspede
                                    nome = nomeHospede[quarto-1];
                                    moverFrigobar(quarto-1, novoQuarto-1, consumoFrigobar);
                                    desocuparQuarto(quarto-1, nomeHospede, statusQuarto, quartoHospede, numeroQuarto);
                                    nomeHospede[quarto-1] = "";
                                    reservarQuarto(novoQuarto-1, statusQuarto, nome, nomeHospede, reservaHospede);
                                    ocuparQuarto(novoQuarto-1, statusQuarto, quartoHospede, numeroQuarto);
                                    System.out.println(">> Alteracao Feita com Sucesso! ");
                                }
                                break;
                                
                            default:
                                stop = true;
                                break;
                        }
                        
                        //Encorajar o Usuário
                        System.out.print ("\nVamos em Frente? ");
                        scanner.nextLine();
                        
                    }
                    break;
                
                default:
                    stop = true;
                    break;
            }
            
            //Mensagem de Agradecimento
            System.out.println("\n\n>> Obrigado por Contribuir com Nosso Hotel! ");
            System.out.println(">> Somos Gratos pelo seu Bem :)");
        }
    }
}
