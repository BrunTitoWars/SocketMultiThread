# Conteúdo
1. [Classe Benchmark em Java](#classe-benchmark-em-java)  
   - [Objetivo](#objetivo)  
   - [Funcionamento](#funcionamento)  
     - [Estrutura Principal](#estrutura-principal)  
     - [Método benchmarkServer](#método-benchmarkserver)  
     - [Fluxo de Execução](#fluxo-de-execução)  

2. [Classe MultiThreadServer em Java](#classe-multithreadserver-em-java)  
   - [Funcionamento](#funcionamento-1)  
     - [Estrutura Principal](#estrutura-principal-1)  
     - [Método main](#método-main)  
     - [Método handleClient](#método-handleclient)  
   - [Exemplo de Uso](#exemplo-de-uso)  

3. [Classe SingleThreadServer em Java](#classe-singlethreadserver-em-java)  
   - [Funcionamento](#funcionamento-2)  
     - [Estrutura Principal](#estrutura-principal-2)  
     - [Método main](#método-main-1)  
     - [Método handleClient](#método-handleclient-1)  
   - [Exemplo de Uso](#exemplo-de-uso-1)  

4. [Comparação com MultiThreadServer](#comparação-com-multithreadserver)
5. [Documentação: Como Criar e entrar em uma rede Hamachi](#documentação-como-criar-e-entrar-em-uma-rede-hamachi)


# Classe `Benchmark` em Java

Esta classe realiza testes de desempenho (benchmarking) em servidores de rede para comparar o tempo de resposta de servidores com e sem threads.

## Objetivo

Avaliar o tempo necessário para que um grupo de clientes conecte-se a um servidor e receba uma resposta, comparando:

- **Servidor sem threads** (porta 5000)
- **Servidor com threads** (porta 6000)

## Funcionamento

### Estrutura Principal

- **Constante `CLIENT_COUNT`**: Define o número de clientes simultâneos (10 por padrão).
- **Método `main`**: 
  - Inicia o processo de benchmarking.
  - Mede os tempos de resposta para os servidores em `localhost` nas portas 5000 e 6000.
  - Imprime os tempos obtidos.

### Método `benchmarkServer`

1. **Host e Porta**: Aceita o endereço do servidor e a porta como parâmetros.
2. **ExecutorService**: Cria uma pool de threads para simular conexões simultâneas com o servidor.
3. **Processo de Conexão**:
   - Cada thread cria um socket para se conectar ao servidor.
   - Lê a resposta do servidor utilizando `BufferedReader`.
4. **Medida de Tempo**:
   - Calcula o tempo decorrido entre o início e o término de todas as conexões.
5. **Finalização**:
   - Aguarda a conclusão de todas as threads ou interrompe após 10 segundos.

### Fluxo de Execução

1. Simula 10 conexões simultâneas para cada servidor.
2. Mede o tempo total necessário para atender todas as conexões.
3. Imprime os resultados:

Tempo do servidor sem threads: X ms
Tempo do servidor com threads: Y ms


# Classe `MultiThreadServer` em Java

Este programa implementa um servidor multi-threaded que escuta conexões na porta 6000 e atende cada cliente em uma thread separada.

## Funcionamento

### Estrutura Principal

- **Porta 6000**: O servidor escuta conexões nesta porta.
- **Aceitação de Conexões**: Cada cliente conectado é tratado em uma nova thread para garantir que múltiplas conexões sejam atendidas simultaneamente.

### Método `main`

1. Cria um `ServerSocket` na porta 6000.
2. Imprime uma mensagem indicando que o servidor foi iniciado.
3. Loop infinito:
   - Aguarda conexões de clientes (`accept`).
   - Para cada conexão:
     - Exibe o endereço IP do cliente.
     - Inicia uma nova thread que executa o método `handleClient`.

### Método `handleClient`

1. Processa a conexão de um cliente:
   - Cria streams para leitura (`BufferedReader`) e escrita (`PrintWriter`).
   - Envia uma mensagem de resposta ao cliente.
   - Simula uma operação de leitura do cliente.
2. Fecha o socket do cliente após o atendimento.

### Tratamento de Exceções

- Exceções de I/O são tratadas com `e.printStackTrace()`, garantindo que o servidor continue executando mesmo em caso de erros.

## Fluxo de Execução

1. O servidor inicia e aguarda conexões na porta 6000.
2. Ao receber uma conexão:
   - Cria uma thread para atender o cliente.
   - Envia uma resposta ao cliente.
3. Continua aceitando novas conexões enquanto atende os clientes existentes.

## Exemplo de Uso

1. Compile o programa:

   ```bash
   javac MultiThreadServer.java


# Classe `SingleThreadServer` em Java

Este programa implementa um servidor single-threaded que atende conexões de clientes de forma sequencial, utilizando a porta 5000.

## Funcionamento

### Estrutura Principal

- **Porta 5000**: O servidor escuta conexões nesta porta.
- **Atendimento Sequencial**: Cada cliente é atendido um de cada vez, e o servidor não aceita novas conexões enquanto não finalizar a interação com o cliente atual.

### Método `main`

1. Cria um `ServerSocket` na porta 5000.
2. Imprime uma mensagem indicando que o servidor foi iniciado.
3. Loop infinito:
   - Aguarda conexões de clientes (`accept`).
   - Para cada conexão:
     - Exibe o endereço IP do cliente.
     - Chama o método `handleClient` para atender o cliente.
     - Fecha o socket do cliente após o atendimento.

### Método `handleClient`

1. Processa a conexão de um cliente:
   - Cria streams para leitura (`BufferedReader`) e escrita (`PrintWriter`).
   - Envia uma mensagem de resposta ao cliente.
   - Lê uma linha de entrada do cliente, simulando uma operação de leitura.

### Tratamento de Exceções

- Exceções de I/O são tratadas com `e.printStackTrace()`, garantindo que o servidor continue funcionando mesmo em caso de erros.

## Fluxo de Execução

1. O servidor inicia e aguarda conexões na porta 5000.
2. Ao receber uma conexão:
   - Atende o cliente atual.
   - Fecha a conexão ao final.
3. Repete o processo para o próximo cliente.

## Exemplo de Uso

1. Compile o programa:

   javac SingleThreadServer.java


# Comparação com MultiThreadServer
### SingleThreadServer:
- Atende um cliente por vez.
- Simples, mas não é escalável para cenários com múltiplos clientes.
### MultiThreadServer:
- Atende múltiplos clientes simultaneamente.
- Escalável, mas consome mais recursos do sistema.

## Benchmark Comparação
 (50k conexões)
```BASH
Resposta do servidor (sem threads): Processamento concluído
Tempo total sem threads: 10057 ms
Tempo médio por cliente sem threads: 1005.7 ms
Resposta do servidor (com threads): Processamento concluído
Tempo total com threads: 1006 ms
Tempo médio por cliente com threads: 100.6 ms
```

# Socket
![image](https://github.com/user-attachments/assets/cce2eb95-73d8-408a-8c66-e0f7a54fd4da)

# [Documentação: Como Criar e Entrar em uma Rede Hamachi](#documentação-como-criar-e-entrar-em-uma-rede-hamachi)

O Hamachi é um software de rede privada virtual (VPN) que permite criar uma rede local (LAN) sobre a Internet. Ele é útil para jogos online ou outras situações onde você precisa se conectar a um ambiente virtual seguro. Abaixo estão os passos para criar uma rede no Hamachi e permitir que outra pessoa entre.

## Passo 1: Instalar o Hamachi

1. Acesse o site oficial do Hamachi [aqui](https://www.vpn.net/).
2. Baixe a versão do Hamachi para o seu sistema operacional (Windows, macOS ou Linux).
3. Após o download, execute o instalador e siga as instruções para concluir a instalação.

## Passo 2: Criar uma Rede no Hamachi

1. **Abrir o Hamachi:**  
   Após a instalação, abra o Hamachi.
   
2. **Criar uma Rede:**
   - Clique no botão **"Rede"** no canto superior esquerdo da interface do Hamachi.
   - Selecione **"Criar uma nova rede..."**.

3. **Configurar a Rede:**
   - **Nome da Rede:** Escolha um nome único para a sua rede (exemplo: "MinhaRedeHamachi").
   - **Senha da Rede:** Defina uma senha para que apenas pessoas autorizadas possam entrar na rede.
   - Clique em **"Criar"** para finalizar a criação da rede.

Agora sua rede está criada, e você verá o nome da rede na lista de redes no Hamachi.

## Passo 3: Convidar Outra Pessoa para Entrar na Rede

1. **Compartilhar Detalhes com a Outra Pessoa:**
   - Compartilhe o nome da rede e a senha com a pessoa que deseja convidar para a rede.
   
2. **A outra pessoa deve instalar o Hamachi:**
   - Ela precisa seguir os mesmos passos de instalação descritos acima.

3. **Entrar na Rede:**
   - Após a instalação, a outra pessoa deve abrir o Hamachi, clicar em **"Rede"** e escolher **"Entrar em uma rede existente..."**.
   - Ela deve inserir o nome da rede e a senha que você forneceu.
   - Clique em **"Entrar"** para finalizar o processo.

## Passo 4: Verificar a Conexão

1. **Verifique se a Conexão foi Bem-Sucedida:**
   - No Hamachi, os dispositivos conectados à sua rede aparecerão na lista de membros da rede.
   - Cada pessoa que entra na rede terá um endereço IP virtual do Hamachi atribuído, que pode ser usado para se conectar a jogos, servidores ou outros serviços na rede local.

## Passo 5: Conectar e Usar a Rede Hamachi

- Após a outra pessoa ter entrado na rede, vocês podem começar a utilizar a rede como se estivessem em uma rede local física.
- Por exemplo, se for para jogar, basta usar o IP do Hamachi de cada pessoa para se conectar ao jogo.

## Passo 6: Desconectar da Rede

1. Se desejar sair da rede Hamachi, clique com o botão direito sobre o nome da rede e selecione **"Sair da rede"**.
2. A rede permanecerá ativa, mas você não estará mais conectado a ela.

## Considerações Finais

- **Segurança:** A senha da rede deve ser compartilhada apenas com pessoas confiáveis para garantir que ninguém não autorizado tenha acesso à sua rede.
- **Desempenho:** O desempenho da conexão pode variar dependendo da qualidade da sua conexão com a Internet.
- **Limitações:** O Hamachi permite criar redes com até 5 dispositivos na versão gratuita. Se precisar de mais dispositivos, você pode considerar a versão paga do Hamachi.

Essa documentação cobre o processo básico de criação de redes e entrada em redes Hamachi. Caso haja alguma dúvida ou problemas com a conexão, verifique as configurações de rede ou consulte o suporte oficial do Hamachi.


