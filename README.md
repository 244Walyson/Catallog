# Projeto: walyCatallog - Catálogo de Produtos

O projeto **walyCatallog** é um catálogo de produtos que tem como principal objetivo listar produtos. Ele apresenta uma série de recursos avançados, incluindo controle de acesso e liberação de recursos com base no perfil do usuário. A autenticação é realizada através de tokens JWT, proporcionando um ambiente seguro. Além disso, o projeto é acompanhado por testes automatizados e testes de API utilizando a biblioteca Rest Assured.

## Principais Características:

- **Controle de Acesso e Permissões:** O projeto implementa um sistema de controle de acesso que define as permissões dos usuários com base em seus perfis. Isso garante que apenas usuários autorizados tenham acesso a determinados recursos.

- **Autenticação Segura:** A autenticação é realizada por meio de tokens JWT (JSON Web Tokens), proporcionando uma forma segura de autenticação e autorização para os usuários.

- **Testes Automatizados e Testes de API:** O projeto inclui testes automatizados para garantir a qualidade do código e a funcionalidade correta. Além disso, os testes de API são realizados usando a biblioteca Rest Assured, assegurando que os endpoints da API estejam funcionando conforme o esperado.

- **Recuperação de Senha por E-mail:** Um sistema de recuperação de senha foi implementado, permitindo que os usuários redefinam suas senhas através do envio de um e-mail com um token personalizado para o endereço cadastrado.

- **Documentação com Swagger:** Os endpoints da API estão documentados usando a plataforma Swagger, tornando mais fácil para os desenvolvedores entenderem e utilizarem a API.

- **HATEOAS para uma API Restful:** O projeto utiliza HATEOAS (Hypertext as the Engine of Application State) para melhorar a experiência da API, fornecendo links relacionados e navegáveis para recursos adicionais.

## Tecnologias Utilizadas:

O projeto foi desenvolvido utilizando a tecnologia Java Spring, fazendo uso de diversas tecnologias do ecossistema Spring, incluindo Spring Data JPA, Spring Security e Spring MVC. Além disso, a solução AWS S3 foi empregada para o armazenamento das imagens dos produtos.
