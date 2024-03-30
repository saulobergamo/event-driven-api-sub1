# Sistemas Distribuídos - CARS001 - PPGCA - UTFPR

>Desenvolvam uma aplicação simples orientada a microsserviços. Em cada microsserviço deve ocorrer eventos que sejam de interesse de outros microsserviços. A comunicação entre os microsserviços será orientada a eventos.


## Representação genérica de uma loja.


Microsserviço event-driven-api-sub1 responsável por consumir mensagens em uma exchange fanout RabbitMQ.

![img.png](img.png)



>>### Breve descrição da proposta de arquitetura orientada a eventos
> - Ator é o cliente final que realiza o pedido através de uma pessoa/atendente(event-drive-api-pub1) ou por aplicativo(event-drive-api-pub2).
> - event-drive-api-pub1 representa o a pessoa/atendente que estaria utilizando a api em uma máquina POS(Points of Sales)
> - event-drive-api-pub2 representa o aplicativo disponível em um dispositivo móvel operado pelo usuário/cliente final
> - event-drive-api-sub1 representa o microsserviço responsável pelo gerenciamento do estoque e que faz alterações no banco de dados
> - event-drive-api-sub2 representa o microsserviço responsável por emitir os pedidos para produção/preparo/entrega dos produtos
> - event-drive-api-sub3(não desenvolvida) poderia representar o microsserviço responsável por fechamento de fatura e cobrança
> - event-drive-api-sub4(não desenvolvida) poderia representar o microsserviço responsável por compras e reposição de estoque
> - outros microsserviços e outras filas poderiam ser desenvolvidos de acordo com a necessidade
> - utiliza-se um banco de dados não relacional(MongoDB) para armazenar os produtos e suas respoectivas decrições, preços e quantidades disponíveis

>>### FanoutExchange
> - FanoutExchange é reponsável por enviar as mensagens recebidas dos microsserviços pub1 e pub2 e redirecionar para as filas inscritas:
    >   - queue1,
    >   - queue2,
    >   - queue3
>   - e assim por diante


## Documentação

Utilização da ferramenta de mensageria RabbitMQ.

Tutoriais disponíveis no [site oficial](https://www.rabbitmq.com/tutorials)

### Acesso ao Swagger:

local: ```http://localhost:9081/event-driven-api-sub1/swagger-ui/index.htm```

### Pré-requisitos

O que será necessário instalar para que o microsserviço funcione corretamente

- Intellij IDEA
- Git
- Java 11
- Gradle
- MongoDB
- RabbitMQ

### Instalação

Passo a passo de execução para que o microsserviço fique em execução e disponível para uso.

- Realizar o clone do projeto:

  Comando: ```git clone git@github.com:saulobergamo/event-driven-api-sub1.git```

>>demais microsserviços também disponível no [github](https://github.com/saulobergamo)


- Acessar a pasta do projeto:

  - Executar script para o docker compose para instanciar imagens do mongoDB e RabbitMQ:

      Comando: ```docker-compose up```


- Fazer o build do projeto:

  Comando: ```./gradlew build```


- Subir a aplicação local:

  Comando: ```./gradlew bootRun'```

> Lembre-se de utilizar um gerenciador de banco de dados não relacional por interface ou CLI, popular sua collection
>
> Acessar a [interface local do RabbitMQ](localhost:15672) com as credenciais default:
> - username: guest
> - password: guest
>
> Exchanges, queues e bindings podem ser configurados a nível de código no microsserviço mas também podem ser criadas manualmente via interface

### Qualidade de código

- Executar inspeção com SonarQube:

  Comando: ```./cmds/build-sonar.sh```


- Executar inspeção e correção com Spotless:

  Comando: ```./gradlew spotlessCheck```

  Comando: ```./gradlew spotlessApply```

- Executar inspeção com Detekt:

  Comando: ```./gradlew detekt```
